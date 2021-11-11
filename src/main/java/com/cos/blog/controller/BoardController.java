package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	/*
	 ==================== 흐름 ==========================
	 
	  1. /board/saveForm 을 클라이언트가 요청을 하면
	  2. WEB-INF/view/board/saveform.jsp 가 호출된다.
	  3. saveform.jsp 파일 아래에 <script src="/js/board.js"></script>
	  	 가 스크립트 하나를 호출하고 있고.
	  	 id 값이 <button id="btn-save" 인 button을 클릭하게되면
	  4. title,content 데이터를 가지고 js를 타고 간다.
	     /js/board.js 에 정의 되어있는 save()함수가 호출되고 다시
	   	 ajax를 타고 POST 방식으로 
	   	 "/api/board" 로 매핑 되어있는 컨트롤러의 메서드를 찾는다.
	  5. "/api/board" 와 매핑되어있는 메서드에는
	  	 들고온 title,content 외에도 어떤 유저가 글을 썻는지 식별하기 위해
	  	 유저객체를 만들어 글쓰기() 메서드에 매개변수로 Board, User 오브젝트를 넣어준다.
	  
	 */
	
	@GetMapping({"","/"})
	public String index(Model model,
			            @PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC)Pageable pageable) {
		
		//WEB-INF/views/index.jsp
		/*
			index 페이지로 boardService.글목록()메서드로 가져온 
			List<Board>(BoardService 클래스에 정의되어있는것을 보면 리턴타입이 List<Board> 이다
			오브젝트들이 "boards" 라는 변수? 명으로 넘어간다.
		  	
		  	@Controller 로는 리턴할때 viewResolver 라는 애가 작동을 하는데
		  	해당 index 페이지로 Model의 정보를 들고 이동을 한다.
		  	viewResolver라는 애는 리턴값 index 에 앞 뒤로 prefix,suffix 가 붙는다(yml 파일에 다 설정되어있음)
		  	
		  	Model을 jsp 에서 request 정보라고 생각하면된다.
		  	Model에다가 데이터를 담으면 view 까지 데이터를 끌고 이동을 한다.
		  	
		 */
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index";
	}
	
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	
	
	/*
	  	@PathVariable 의 쓰임새
	  	@PathVariable로 받아온 변수 ex) @PathVariable int id
	  	~Mapping 에서 {id} 처럼 쓸수가 있다.
	  	
	  	이처럼 @PathVariable 은 메서드 인자에 사용되어 
	  	URI 템플릿 변수의 값을 메서드 인자로 할당하는데 사용된다.
	  	
	  	글상세보기() 메서드의 리턴타입은 Board 객체이다.
	  	findById 매개변수로는 id 랑 Model객체가 있는데
	 */
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail";
	}
	
}
