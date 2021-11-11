<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
  
<!-- 스프링 태그 라이브러리를 쓰기위한 선언? -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- 
	 property="principal" 다이렉트하게 엑세스하는걸 허용한다. 현재 커런트 유저 오브젝트에
	 var="principal" 이라고 밸류값을 주면 EL 표기법으로 사용가능 해당 페이지에서 사용할수있는 변수로 등록된다.
	 principal 변수에 오브젝트가 저장되는듯
	  -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
 
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Go 블로그</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>

<body>
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
  <a class="navbar-brand" href="/">결결</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <!-- if else 문법임
    	 sessionScope 키워드를 사용하면 sessionScope.{세션에 담겨있는놈}
    	 으로 jsp 에서 세션에 있는놈을 가져다 쓸수있다.	
     -->
    <c:choose>
      <c:when test="${empty principal }">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="/auth/loginForm">로그인</a>
          </li>  
          <li class="nav-item">
            <a class="nav-link" href="/auth/joinForm">회원가입</a>
          </li>
        </ul>
      </c:when>	
      <c:otherwise>
        <ul class="navbar-nav">
          <li class="nav-item" style="color:white">
            ${principal.username } 님 환영합니다.
          </li>	
          <li class="nav-item">
            <a class="nav-link" href="/board/saveForm">글쓰기</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/user/updateForm">회원정보</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/logout">로그아웃</a>
          </li>
        </ul>
      </c:otherwise>
    </c:choose>
    
    
  </div>  
</nav>
<br/>