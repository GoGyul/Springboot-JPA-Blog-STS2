package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 	ORM : Object -> 테이블로 매핑해주는 기술
 */
		// User 클래스가 Mysql에 테이블이 생성이 된다. 스프링 부트의 프로젝트가 실행될떄
		// 유저 클래스를 통해서 얘네를 읽어서 자동으로 
/*
  	연관 관계의 주인 : FK(Foreign Key) 를 누가 가졌느냐.
  	
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity	
//@DynamicInsert // insert할떄 null인 필드를 제외시켜준다. 제외시켜줌으로써 default로 세팅된 컬럼에 자동적으로 값을 넣을수 있음
public class User {

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)// 해당프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 오라클의 시퀀스, mysql = auto_increment
	
	@Column(nullable = false, length=100, unique=true)
	private String username;
	
	@Column(nullable = false, length=100) // 123456 => 해쉬(비밀번호 암호화) 그렇기 떄문에 length를 넉넉히줌
	private String password;
	
	@Column(nullable = false, length=50)
	private String email;
	
//	@ColumnDefault("'user'")
//	model 패키지에 RoleType 의 Enum 이 있고 필드는 현재 ADMIN , USER 만 정의되어있다.
//	DB는 RoleType이란게 없다. @Enumerated(EnumType.STRING)이라고 알려줘야함
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. // ADMIN , USER, manager
	
	private String oauth; //kakao, google 등등에 대한 oAuth 사용자
	
	@CreationTimestamp // 시간이 자동입력
	private Timestamp createDate;
	
}
