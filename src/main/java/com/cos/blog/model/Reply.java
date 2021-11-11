package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

	//	답변 테이블

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {

	@Id	// PrimaryKey
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 200)
	private String content;
	
	//	누가 어느, 게시글의 답글인가?
	//	하나의 게시글에는 여러개의 답글이 올수 있다.
	//	여러개의 답글은 하나의 게시글에 존재할수 있다.
	@ManyToOne
	@JoinColumn(name="boardId")
	private Board board;
	
	//	누가 답글을 적었는지도 알아야함
	//	하나의 유저는 여러개의 답변을 적을수 있음.
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
