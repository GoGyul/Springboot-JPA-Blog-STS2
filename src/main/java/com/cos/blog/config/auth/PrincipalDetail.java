package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;
import lombok.Getter;

/*
    스프링 시큐리티가 로그인 요청을 가로채서
    로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
    스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다.
    그때 저장되는게 UserDetails 를 implements 한 PrincipalDetail 타입의
    오브젝트가 저장이 되도록 해주는 것이다 . 반드시 UserDetails 인터페이스를
    implements 해서 메소드를 오버로딩 해야한다.
    PrincipalDetail 가 저장이 될때 우리가 디비에 저장한 User 오브젝트가 포함되어 있도록 한다.
 */

@Getter
public class PrincipalDetail implements UserDetails {

	//	model이 들고있는 User 오브젝트 하나 생성
	//	객체를 품고있다 = 콤포지션이라고 한다.
	private User user;
	
	//	생성자를 이용한 의존성 주입 
	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getUsername();
	}

	//	계정이 만료되지 않았는지 리턴한다. (true : 만료안됨) 
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//	계정이 잠겨있지 않았는지 리턴한다 (true:잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//	비밀번호가 만료되지 않았는지 리턴한다.
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//	계정 활성화(사용가능) 인지 리턴한다. true: 활성화
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//	계정이 
	/*
	  	Collection 타입을 리턴 해야 하는데 GrantedAuthority 타입을 상속받은
	  	Collection 을 리턴 해야한다. 좀 빡셈 이해하기
	  	Collection<GrantedAuthority> collectors = new ArrayList<>();
	  	ArrayList 는 Collection 인터페이스를 구현한 List 를 상속받고 있으므로 
	  	new ArrayList를 써주고
	  	collectors 에 add 할때 new GrantedAuthority() 로 GrantedAuthority
	  	오브젝트를 생성 해준뒤 익명함수를 만들고있다. 하지만 GrantedAuthority 인터페이스 임으로
	  	추상메서드가 오버라이딩이 된다. 
	  	
	  	계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을수 있어서 루프를 돌아야하는데
	  	우리는 한개만.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(new GrantedAuthority() {

			
//      ROLE_ 써주는게 규칙이다. ROLE_USER, ROLE_ADMIN 등등 아래는 람다식으로 표현한것.
//		collectors.add(()->{return "ROLE_"+user.getRole();});
			@Override
			public String getAuthority() {
				return "ROLE_"+user.getRole();
			}
		});
		
		
		return collectors;
	}
	
}
