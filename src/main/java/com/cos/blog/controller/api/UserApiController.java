package com.cos.blog.controller.api;





import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

/*
 	데이터만 return 해줄것이기 떄문에  
 	@RestController 어노테이션을 쓰자 !
 */

@RestController
public class UserApiController {
	
	/*
	    스프링이 컴포넌트 스캔할때 !
	    @Service 어노테이션이 붙은 클래스를 찾아서 IoC 를 해주고
	    @Autowired 어노테이션으로 디펜던시 인젝션 하면 된다.
	 */
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	

	/*
	 	요청 받는게 JSON 이라 @RequestBody 어노테이션 활용
	 	
	 	ResponseDto 클래스는 제네릭 클래스로 선언되어있고 두개의 필드가 있다.
	 	필드하나는 HttpStatus 타입이고 하나는 제네릭 변수이다 !
	 	즉 여기서 save() 메서드를 만들때 ResponseDto<> 의 타입을
	 	괄호안에 넣어 지정해줄수 있는것이다. 그리고 리턴문을 보면
	 	매개변수 넣는곳에 ResponseDto<T> 클래스에 있는 필드타입들을 넣어줄수있다.
	 	
	 	UserService 클래스에 있는 회원가입() 이라는 메서드는 
	 	1과 -1 을 리턴한다
	 	메서드의 정상작동은 1
	 	비정상작동은 -1 으로 구분할수 있다. 
	 	* 여기서는 오류가터질시 GlobalExceptionHandler 클래스에서 잡아주고있다.
	 	
	 	실제로 DB에 insert를 하고 아래에서 return이 되면 완료 !
	 	
	 	또한 이 클래스는 컨트롤러 로직이다 클라이언트에서 직결적으로 데이터를
	 	받아내는 곳이다
	 */
	
//	============================ save() 메서드 실행 =======================
	
	@PostMapping("auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println(" UserApiController : save()  호출");
		System.out.println(" UserApiController : user.toString()  호출"+ user.toString());

		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	
	/*
	 ============================ 시큐리티를 쓰기전 로그인 구현 메서드 ================== 
	  
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user, HttpServletRequest request){
		System.out.println("User ApiController : login이 호출됨");
		User principal = userService.로그인(user); // principal 접근주체라는 용어
		
		HttpSession session = request.getSession();
		
		
		로그인() 메서드가 성공하면 해당되는 User 객체가 반환 될것이고 
		그 User 객체는 principal 이라는 변수에 다시 담기게 된다.
		이떄 principal 객체가 null 이 아닐때 세션에 저장을 해야한다. (로그인에 성공했으니까)
		세션을 만들어 주면 되는데 (로그인하고 기억하기 위해)
		스프링에서 컨트롤러에 매개변수로 받을수 있다 ! 
		HttpSession 만 선언해주면 세션 객체 가져와서 
		setAttribute로 세션에 담아주기만 하면됨
		
		혹은 HttpServletRequest 를 매개변수에 넣어서
		HttpServletRequest 변수를 request 로 잡고 getSession() 메서드로 세션을 가져와서쓸수있다.
		HttpSession session = request.getSession();
		그리고 나서 session.setAttribute("principal", principal);
		처럼 세션에 저장! 
		
		
		System.out.println("UserApiController 의 login 메서드의 user 값 ==========" + principal.toString());
		
		if(principal != null) {
			session.setAttribute("principal", principal);
			
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	 */
	
	/*
	 	시큐리티 라이브러리가 설치가 되면 우리 홈페이지 어느곳에 접근을 하든지
	 	스프링 시큐리티가 가로채서 localhost:8000/login 페이지로 가게된다.
	 	모든 대문이 닫혀 버린다 !!!
	 	
	 	시큐리티가 걸려있을때 아이디는 user 비밀번호는 콘솔에 나와있는 비밀번호로 로그인 하면된다.
	 	localhost:8000/login 페이지에서 로그인을 했다면 ! 세션이 자동으로 생기게된다.
	 	
	 */
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		//여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음
		//하지만 세션값은 변경되지 않은 상태이기떄문에 우리가 직접 세션값을 변경해줄것임
		//강제로 세션값을 바꾸는것.
		
		//세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	
}
