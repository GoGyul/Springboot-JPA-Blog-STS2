package com.cos.blog.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;

/*
 	데이터만 return 해줄것이기 떄문에  
 	@RestController 어노테이션을 쓰자 !
 */

@RestController
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("api/board")
	public ResponseDto<Integer> save(
			@RequestBody Board board,
			@AuthenticationPrincipal PrincipalDetail principal) {
		
		/*
			@AuthenticationPrincipal PrincipalDetail principal
		 	 어노테이션을 사용해 스프링 시큐리티 세션에 저장되어있는
		 	 User 객체를 들고 올수 있다.
		 	 
		 	 PrincipalDetail 클래스 필드를 보면
		 	 User user 로 참조타입 변수가 하나 선언되어있고 
		 	 PrincipalDetail 생성자에 User 객체를 매개변수로 넣어
		 	 DI 를 주입해주고 있다 
		 	 또한 @Getter 어노테이션을 이용해 
		 	 .getUser 메서드를 사용할수 있다.
		 */
		
		boardService.글쓰기(board,principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		System.out.println("id=================="+id);
		boardService.글삭제하기(id); 
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
		boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	//데이터를 받을때 컨트롤러에서 DTO 를 만들어서 받는게 좋다.
	//DTO 사용하지 않은 이유는 
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto){
		boardService.댓글쓰기(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId){
		boardService.댓글삭제(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
}
