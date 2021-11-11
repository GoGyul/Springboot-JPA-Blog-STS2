package com.cos.blog.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 	이녀석의 타입은 제네릭으로 되어있다 
 	제네릭은 클래스, 메소드에서 사용할 데이터 타입을 나중에 확정하는 기법이다. 
 	나중에라는 말은 클래스나 메소드를 선언할 때가 아닌 사용할 때, 즉 인스턴스를 생성할 때나 메소드를 호출할 때 정한다는 의미이다.
	제네릭의 사용 방법과 특징은 메소드의 매개변수와 굉장히 유사한데,
	메소드의 매개변수가 '값'과 관련되어 있다면 제네릭은 데이터의 
	'타입'과 관련이 있다. 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {

	int status;
	T data;
	
}
