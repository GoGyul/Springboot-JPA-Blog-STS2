package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;

/*
 
    빈 등록: 스프링 컨테이너에서 객체를 관리할수있게 하는것
	시큐리티가 모드 request를 가로챈다.
	컨트롤러가서 머떤 메서드가 실행되기 전에 이 시큐리티가 동작을 해서
    해당 주소로 들어오면 요청을 허용하고 그게 아닌 모든 주소는 인증이 필요하다는 필터링이 필요함
  
  @EnableWebSecurity ==  시큐리티에 필터가 등록이 된다.
  그리고 설정을 configure 메서드를 오버라이딩 받아서 하면 된다.
  
  =======================  흐름 =========================
  
  1 . 로그인 요청이 오는 순간.
  2 . configure(HttpSecurity http) 메서드의
  	. loginProcessingUrl("auth/loginProc") 메서드가 해당 url 에서의 요청을 가로챈다.
  3 . 가로챈	username, password 정보를 PrincipalDetailService 클래스의
  	  loadUserByUsername() 메서드에 매개변수로 던진다 !
  	  해당 메서드에서 비교를 해서 new PrincipalDetail(principal) 를 리턴해준다.
  	  그 전에 !
  4 . 패스워드 비교를 한번 더 한다. 3에서 리턴할떄,
  	  auth.userDetailsService 를 통해서, principalDetailService
  	  가 로그인 요청을 하고 
  	  3 이 리턴이 되면.
  	  passwordEncoder() 메서드를 통해 사용자가 적은 패스워드를 
  	  encodePWD() 메서드로 암호화 해서 데이터 베이스랑 비교를 해준다
  5 . 유저 데이터 베이스에 있고, 패스워드도 정상이라면 
  	  스프링 시큐리티 영역에 유저정보가 new PrincipalDetail(principal) 통해
  	  감싸져서 저장이 된다.
  
 */

@Configuration // 빈등록 IoC
@EnableWebSecurity  // 시큐리티 필터 추가 (스프링 시큐리티가 활성화가 되어있는데 어떤 설정을 해당 파일에서 하겠다.)
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 수로소 접근을 하면 권한및 인증을 미리 체크하겠다는 뜻)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	/*
	 	 encodePWD() 함수만 호출하면
	 	 BCryptPasswordEncoder 객체를 리턴 받을수 있다.
	 	 BCryptPasswordEncoder 객체를 통해서 
	 	 String encPassword = new BCryptPasswordEncoder().encode("1234");
	 	 이런식으로 패스워드를 인코드 할수있다.
	 	 
	 */
	
// 	=================== BCryptPasswordEncoder() 메서드 ====================
	
	@Bean // IoC 가 된다. return 하는 BCryptPasswordEncoder() 를 스프링이 관리한다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	
//	===================== configure(HttpSecurity http) 메서드 ==========================
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()  // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests()
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**") // 이곳에 해당하는 페이지가 아닌 모든 페이지의 요청은 2. 으로 간다.
				.permitAll()
				.anyRequest() // 1. 인증이 되지 않은 어떤 요청은
				.authenticated() 
			.and()
				.formLogin()
				.loginPage("/auth/loginForm") // 2. 로그인 폼으로 온다.
				//	3. 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인을 해준다.
				//	/auth/loginForm 버튼은 "auth/loginProc" <- 여기로 가게한다.
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/"); // 4. 정상이면 "/" 여기로 오게한다.
				
	}
	
//	======================== configure(AuthenticationManagerBuilder auth) 메서드 =================	
	
	/*
	 	 시큐리티가 대신 로그인해주는데 password를 가로채기 하는데
	 	 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	 	 같은 해쉬로 암호화 해서 DB에 있는 해쉬랑 비교를 할수있음.
	 	 
	 	 principalDetailService 를 통해서 우리가 로그인을 할때 
	 	 패스워드를 encodePWD() 로 인코드 해서 비교를 아래 코드에서 알아서 해줌
	 	 auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	 	 principalDetailService 객체를 넣어주지 않으면 패스워드 비교를 못해준다.
	 */
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
		
	}
	
}
