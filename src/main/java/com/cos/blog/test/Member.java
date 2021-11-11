package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor // 컨스트럭터 자동생성
@NoArgsConstructor  // 빈 생성자
public class Member {


	private  int id;
	private  String username;
	private  String password;
	private  String email;
	
	// 빌더는 내가 어떤 값을 넣을떄 순서를 지키지 않아도됨
	// 생성자를 통해서 넣을떄는 순서를 지켜야한다.
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	
}
