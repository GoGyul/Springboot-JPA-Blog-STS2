package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

/*
 	스프링이 컴포넌트 스캔을 통해서 Bean 등록을 해줌 IoC를 해준다 !
  	
  	Service 서비스는 왜 필요할까????
  	
  	1. 트랜잭션을 관리하기 위해서 !
  	
  	2. 서비스라는 의미를 알아야한다.
  	
  	 송금이라는 서비스가 있다고 가정해보자.
  	 홍길동이 임꺽정에게 송금을 해야한다. 
  	 홍 50000, 임 20000 원씩 있다.
  	 홍이 임에게 30000원을 입금한다고 해보자
  	 
  	 첫번째로 홍길동의 돈을 업데이트를 한번 해야한다.
  	 그럼 홍의 돈은 20000원이 될것이다. 홍 == 20000
  	 두번째는 임의 돈을 업데이트 해야한다.
  	 그럼 임의 돈은 50000원이 될것이다. 임 == 50000
  	 이떄 데이터에는 변경이 일어나기때문에 commit이 일어나게된다.
  	 
  	 서비스라는것은 데이터베이스에서 한번 업데이트가 되는걸로 처리하는게 아니라
  	 하나의 어떤 서비스, 기능이 되야한다. 
  	 예를들면 업데이트, 인서트 등의 여러가지 로직들이 하나로 묶여 하나의 서비스가 될수 있다.
  	 
  	 만약 송금 서비스가 아니라 입금 서비스라고 가정하면,
  	 홍길동이 10000 원을 들고있을때 자기자신에게 20000원을 입금해서 30000원이 
  	 나오게 하고 싶다면 이때는 홍길동의 금액만 업데이트 하면 된다. 
 */

@Service
public class UserService {

	//	의존성 주입
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		
		/*
		  	컨트롤러에서 save() 메서드가 호출될떄 User 객체를 만들어서 ( save 함수의 매개변수에 requestBody로 작성되어있다.)
		  	UserService의 회원가입()  메서드에 그 해당 User 객체를 
		  	매개변수로 넣어 던진다. 
		  	UserService 클래스의 회원가입() 메서드에서는 save()메서드로부터 받아온
		  	User 객체의 초기 패스워드를 꺼내서 String rawPassword 라는 변수에 저장해놓은뒤
		  	변수에 저장된 초기 password를 BCryptPasswordEncoder 객체의
		  	encode()  메서드를 사용해 해쉬화 시키는 작업을 한다.
		 */
		
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);  // 여기서 해쉬가 된다.
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);

	}
	
	/*
	
	SELECT 할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성유지)
	@Transactional(readOnly = true)
	public User 로그인(User user) {
		
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

	}
	  
	 */
	
	@Transactional
	public void 회원수정(User user) {
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					return new IllegalArgumentException("회원찾기실패");
				});
		//	Validate 체크 => oauth 필드에 값이 없으면 수정가능
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());			
		}
		
		
		// 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 된다.
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update 문을 날려준다(자동으로)
		
	}
	
	
	//orElseGet == 만약에 회원을 찾았는데 없으면 빈 객체를 리턴해라./강제로 만들어서 리턴해도됨
	@Transactional(readOnly=true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
	
	
	
}
