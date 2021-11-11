package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//	해당 jpa레파지토리는 User 테이블을 관리하는 레파지토리이고, 
//	User 테이블의 primaryKey는 Integer라는 의미가 된다.
//	DAO 라고 생각하면 된다.
//	자동으로 bean 등록이 된다.@Repository 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {
	 
	// SELECT * FROM user WHERE username = ?(첫번째 파라미터)
	Optional<User> findByUsername(String username);
	
}

/*
	  
	JPA Naming 쿼리전략
	SELECT * FROM user WHERE username = ? AND password = ?; 쿼리가 동작됨
	첫번째 ? 에는 매개변수의 username, 두번쨰 ? 에는 매개변수의 password 가 들어온다.
			아래처럼도 쓸수 있다.
			
	@Query(value="SELECT * FROM user WHERE username=?1 AND password = ?2", nativeQuery = true)
	User login(String username, String password);
	
	User findByUsernameAndPassword(String username, String password);
 */