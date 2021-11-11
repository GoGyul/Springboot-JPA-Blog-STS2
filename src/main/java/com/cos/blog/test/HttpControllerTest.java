package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//	사용자가 요청 -> 응답(HTML 파일)
//	@Controller

//	사용자가 요청 -> 응답(Data)
//	@RestController
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest";
	
	//  http://localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//  id=4&username=마이&password=1234&ema=rhruf11@naver.com
		//	Member 클래스의 생성자는 현재 Builder로 매핑되어있음
		
		Member m = Member.builder().username("마이").password("1234").email("마이@").build();
		System.out.println(TAG+"getter : "+ m.getUsername());
		m.setUsername("티티");
		System.out.println(TAG+"setter : "+  m.getUsername());
			
			return "lombok test 완료";
	}

	//인터넷 브라우저 요청은 get요청으로만 할수있다.
	//http:localhost:8080/http/get 이하 동일
	@GetMapping("/http/get")
	public String getTest(Member m) {
		
		return "get 요청 : "+m.getId() +"," +m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}

	// row = text/plane
	//       application/json
	// 스프링부트의 MessageConverter가 JSON으로 파싱해줌
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post 요청 : "+ m.getId() +"," +m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청"+ m.getId() +"," +m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
