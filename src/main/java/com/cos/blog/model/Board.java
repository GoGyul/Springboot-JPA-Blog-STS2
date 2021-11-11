package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 데이터베이스에 매핑을 해주는 클래스이다.
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터쓸때 쓴다.
	private String content;//섬머노트 라이브러리를 쓸것이다 <html> 태그가 섞여서 디자인이된다.

	private int count;//조회수
	
	
	// Board를 누가 적었는지 알아야함
	// DB는 오브젝트를 저장할수 없다. FK,자바는 오브젝트를 저장할수있다.
	// JPA(ORM)을 사용하면 오브젝트를 저장할수 있다.
	// 필드값은 = userId , 연관관계는 ManyToOne 으로 만들어진다.
	// User객체를 참조하고 fkey가 만들어 진다.
	
	// Board 는 userId를 들고있다. (join을 해서 가져와야한다 일반적으로는)
	// 하지만 JPA가 자동으로 JOIN 문을 날린다.
	// 이후, Board 오브젝트와 Board 오브젝트 내부에 User 오브젝트의 정보가 들어오게 된다 !!
	// Board 오브젝트를 select 하게되면 User 정보도 따라오게 된다.
	
	@ManyToOne(fetch = FetchType.EAGER) // Many = Board, User = One : 한명의 유저는 여러개의 게시글을 쓸수있다.
	@JoinColumn(name="userId")
	private User user; 
	
	/*
	 	 하나의 게시글은 한명만 작성 할수 잇지만 
	 	 댓글은 수만, 수천개가 달릴수 있다.
	 	 그래서 컬렉션으로 들고와야한다.
	 	 하나의 게시글은 (one) 여래개의 답변(Many)을 가질수 있다.
	 	 JoinColumn(name="replyId)가 필요가 없다고 한다.
	 	 
	 	 게시판(Board) 의 컬럼
	 	 id , title , content , userId  , createDate
	 	 1    안녕      반가워     2          2020.5.17
	 	 
	 	 댓글 (Reply) 테이블의 컬럼
	 	 id , content , userId , boardId
	 	 1		좋아요 	2			1
	 	 2		같이해요   3			1	
	 */
	
	//	mappedBy : 연관관계의 주인이 아니다 (난 FP가 아님) DB에 칼럼을 만들지 마!
	//	reply는 연관관계의 주인이 아니다 , 데이터 베이스에 컬럼을 만들지 마라
	//	그냥 단지 Board를  select할때 Join 문을 통해 값을 얻기 위해 필요한것.
	//	(mappedBy = "board") board = reply클래스의 필드이름. 
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
	private List<Reply> reply;
	
	
	@CreationTimestamp
	private Timestamp createDate;

}
