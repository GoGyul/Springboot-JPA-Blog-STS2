package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

/*
 	 UserDetailsService 를 implements 해야함.
 	 빈등록을 하기 위해  @Service 어노테이션을 붙여줘야 한다.
 	 loadUserByUsername() 메서드를 오버라이드 해줘야한다.
 */

@Service
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	/*
	 	스프링을 로그인 요청을 가로챌때, username, password 변수 2개를 가로채는데,
	 	password 부분 처리는 알아서 한다.
	 	username이 DB에 있는지만 확인해주면된다. 
	 	그 확인을 loadUserByUsername() 메서드에서 하는 것이다.
	 	
	 	로그인이 진행될때 loadUserByUsername() 메서드가 자동으로 실행되면서
	 	findByUsername() 메서드로 유저이름을 찾고 해당 유저가 없으면
	 	사용자를 찾을수 없다고 해주고, 비밀번호가 틀렸으면 그건 알아서 다른데서 처리,
	 	모든게 완벽하면 User 타입 principal 변수에 오브젝트가 담기게 될것이다.
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을수 없습니다.");
				});
		/*
			UserDetails 타입을 리턴해줘야 하는데 PrincipalDetail 클래스는 UserDetails
			인터페이스를 임플리먼츠 하고 있으므로 PrincipalDetail 객체를 만들어 리턴해주면된다.
			PrincipalDetail 클래스의 생성자에는 User 타입 객체를 넣어줘야 한다. (의존성 주입되어있음)
			return new PrincipalDetail(principal); 이 될떄,
		    시큐리티의 세션에 유저정보가 저장이 된다.		  
		    그떄 타입은 UserDetail 타입이어야 한다.
		 */
		return new PrincipalDetail(principal);
	}

	
	
}
