package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//	html파일이 아니라 data를 리턴해주는 Controller = @RestController
@RestController
public class DummyControllerTest {
	
	@Autowired  // 의존성 주입
	private UserRepository userRepository;
	
	// -------------------	join() 메서드 시작 ----------------

	//	http://localhost:8000/blog/dummy/join(요청)
	//	http의 body에 username, password, email 데이터를 가지고 (요청)
	//	위에 3개의 값이 아래 메서드의 파라미터 변수에 들어가게됨
	// 	key=value (약속된 규칙)  String username, String password, String email
	//	근데 파라미터에 Object로도 받아진다  ㄷ ㄷ ㄷ ㄷ 
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id === "+ user.getId());
		System.out.println("username === "+ user.getUsername());
		System.out.println("password === "+ user.getPassword());
		System.out.println("email === "+ user.getEmail());
		System.out.println("role === "+ user.getRole());
		System.out.println("createDate === "+ user.getCreateDate());
		
		// User 필드의 role은 현재 RoleType의 Enum으로 타입이 지정되어있다
		// 그래서 매개변수를 저렇게 써줘야 한다.
		user.setRole(RoleType.USER);
		userRepository.save(user);
		
		return "회원가입이 완료되었습니다.";
	}
	
	// -------------------	join() 메서드 종료 ----------------
	
	// -------------------	detail() 메서드 시작 ----------------
	
	// {id}  주소로 파라미터를 전달 받을수 있음
	//	http://localhost:8000/blog/dummy/user/2
	//	매개변수와 GetMapping 에서 의 값이 정확히 동일해야함
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {

		/*
			userRepository.findById() 의 리턴한 타입은 Optional이다.!!
		  	DB의 USER 테이블에는 3명밖에 존재하지 않는다고 가정했을떄
		  	4번쨰 유저를 찾겠다고 파라미터로 값을 넣어버리면 null이 리턴 될것이다.
		  	그럼 return 할떄 null이 리턴이 되고 프로그램에 문제가 있게 된다.
		  	그것을 방지하기 위해 Optional로 User 객체를 감싸서 가져 오고 
		  	null인지 아닌지 판단후 return 하게 된다.
		  	
		  	Optional의 메서드 :	get()
		  			User user = userRepository.findById(id).get();
		  			null 이건 아니건 무조건 리턴해줌
		  			
		  				   : 	orElseGet()
		  			User user = userRepository.findById(id).orElseGet();
		  			null이면 객체를 만들어서 리턴해줌 orElseGet() 의 매개변수로
		  			new Supplier<User>() 처럼 넣어줘야함 (익명객체)
		  			또한 Supplier 인터페이스 인데 get이라는 함수를 Override 해줘야함
		  			
		  				  : .orElseThrow(); --> 권장되는 방법 
		  			User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>();
		  			좀 복잡하다. orElseThrow 도 역시 Supplier 타입의 객체를 만들어 줘야 하는데
		  			Supplier는 인터페이스다 그래서 Override 로 get() 함수를 구현해 주어야함 (익명함수다) 
		 */
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		//	return 할때의 user 객체 = 자바 오브젝트이다
		//	하지만 user를  요청한곳은 = 웹 브라우저이다. 그래서 반환해줄떄 html이 알아 먹도록 해주어야함
		//  변환 (웹 브라우저가 이해할수 있는 데이터로 변환해주어야함 !) -> json
		//	스프링부트 = MessageConverter 라는 애가 <<응답>> 시에 자동으로 작동한다.
		//	만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		//	user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		//	그래서 html에는 반환된 값이 json 형태로 출력이 되게 된다.
		return user;
	}
	
	// -------------------	detail() 메서드 종료 ----------------
	
	// -------------------	list() 메서드 시작 ----------------
	
	//	어떤 파라미터를 굳이 받을 필요가 없다.
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// -------------------	list() 메서드 종료 ----------------
	
	// -------------------	pageList() 메서드 시작 ----------------
	
	//	페이징 처리 메서드
	//	한 페이지당 2건의 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		
		//	findAll(pageable) 이라는 강력한 기능이있음
		//	findAll() 함수의 리턴타입은 Page <- 라는 타입임
		//	근데 또 그 뒤에 .getContect() 함수를 써주면 List로 반환됨 
		Page<User> pages = userRepository.findAll(pageable);
		List<User> users = pages.getContent();

		return users;
	}
	
	// -------------------	pageList() 메서드 종료 ----------------
	
	// -------------------	updateUser() 메서드 시작 ----------------
	
	// email과 password만 수정하려고 한다.
	// detail() 메서드에도 URL이 동일하지만 
	// detail() 에는 GetMapping 으로 되어있기 때문에 충돌이일어나지 않음
	
	/*
	   save 함수는 id를 전달하지 않으면 insert를 해주고 (결국엔 DB의 컬럼)
	   save 함수는 id를 전달하면 해당 id 에 대한 데이터가 있으면 update를 해주고
	   save 함수는 id를 전달하면 해당 id 에 대한 데이터가 없으면 insert를 해준다
	 */
	
	// web 에서 json 데이터를 요청했는데(포스트맨으로 했음) => Java Object로 변환해서 받아준다.
	// MassageConverter 의 Jackson 라이브라리가 다~ 해준다 , 그때 필요한 어노테이션은 바로 밑에것
	@Transactional // 함수 종료시에 자동으로 커밋이 된다.
	@PutMapping("/dummy/user/{id}")	// Json 데이터를 받고싶으면 @RequestBody 어노테이션이 필요하다
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
		System.out.println("id == "+ id);
		System.out.println("password == "+ requestUser.getPassword());
		System.out.println("email == "+ requestUser.getEmail());
		
		/*
			request 요청으로 들어온 값들만 update를 해줘야 하는데 홈페이지에 수정할수 있는 컬럼이 한정적이라면,
			컬럼은 무수히 많고 업데이트 하고픈 컬럼은 한정적이라면 어떻게 해야할까?
		  	
		  	DB에서 select만 해서 그 값을 받아오고 
		  	그 객체에 값만 변경하고 
		  	위에 @Transaction 만 걸면 update 가 된다 그것이 더티체킹이다.
		  	
		  	1 . User user = userRepository.findById(id)
		  	모델 User 의 객체 생성해서 findById로 DB에 있으면 
		  	User 객체 만들고 없으면 Exception 던지도록 해놓고
		  	User 객체인 user에 requestUser로 받아온 password,email을
		  	set 해준뒤 userRepository에 save() 함수 걸어서 user를
		    매개변수로 넣어 update 해도 되지만 
		    @Transaction 으로 죠져주었다.
		 */
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패했습니다~");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
//		userRepository.save(user);
		
		// 더티 체킹 
		// 까먹거나 하면 데어프로그래밍 스프링부트 강좌 29 영속성 다시보기 !
		
		return user;
	}
	
	// -------------------	updateUser() 메서드 종료 ----------------
	
	// -------------------	delete() 메서드 시작 ----------------
	
	//	삭제하는 메서드라 @DeleteMapping으로 어노테이션 걸고
	//	url 걸어준다.
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		
		try {
			userRepository.deleteById(id);			
		}catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제 되었습니다. id : " + id;
	}
	
}
