package com.cos.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;


@Service
public class BoardService {


	@Autowired // DI 설정
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		
		board.setUser(user);
		board.setCount(0);
		boardRepository.save(board);
		
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 아이디를 찾을수없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
			
	}
	
	
	/*
	 	매개변수로 받아온 id 로 DB에 해당 id가 있는지 찾고 있다면
	 	Board 객체로 받아온다.
	 	그 해당 객체의 title, content 를 
	 	매개변수로 받아온 requestBoard 의 title,content 값을 덮어 써준다.
	 	서비스가 종료될때 트랜잭션이 종료된다. 이떄 더티체킹 -> 자동 업데이트가 된다. DB flush
	 */
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을수없습니다.");
				});
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		
		/*
		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 유저 아이디를 찾을수 없습니다.");
		});

		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 아이디를 찾을수 없습니다.");
		});
		
		Reply reply = new Reply();
		reply.update(user, board, replySaveRequestDto.getContent());
		 */
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("BoardService : " + result);
	}
	
	@Transactional
	public void 댓글삭제(int reply) {
		replyRepository.deleteById(reply);
	}
	
	
	
	
}
