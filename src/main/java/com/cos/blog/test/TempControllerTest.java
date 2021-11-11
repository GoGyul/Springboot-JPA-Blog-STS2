package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	//	http://localhost:8000/blog/temp/home
	/*
	  	스프링은 기본적으로 파일을 리턴한다고 할때, 컨트롤러라는 어노테이션이 붙으면
	  	이 메서드는 파일을 리턴한다 근데 어떤 파일을 리턴하냐?
	  	파일 리턴 기본경로 : src/main/resource/static 하위에 있는 파일을 리턴한다.
	  	리턴명 :/home.html / 키워드를 붙여줘야함
	  	전체 풀경로 : src/main/resource/static/home.html
	  	
	  	RestController는 문자 그자체를 리턴해주었다면,
	  	Controller는 해당 경로 이하에 있는 파일을 리턴해준다.
	 */
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome");
		return "/home.html";
	}
	
	//	GetMapping으로는 정적 파일들밖에 찾을수가 없다.
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/qr코드.jpeg";
	}
	
	//	jsp는 정적인 파일이 아니라서
	@GetMapping("/temp/jsp")
	public String tempJsp() {
//		prefix : /WEB-INF/views/
//		suffix : .jsp
//		풀네임 : /WEB-INF/views/test.jsp
		System.out.println("jsp");
		return "test";
	}
	
}
