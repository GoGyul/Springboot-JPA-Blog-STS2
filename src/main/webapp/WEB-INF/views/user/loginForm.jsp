<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
  	
  <!-- form 의 액션이 /auth/loginProc , method가 post 로 되어있는데
  	   컨트롤러에서 /auth/loginProc 에 대한 post 처리를 하지 않을것이다.
  	   스프링 시큐리티가 가로채서 처리하도록 할것이다. -->	
  	
  <form action="/auth/loginProc" method="post">
    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
    </div>
    
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
    </div>
    
	<button id="btn-login"class="btn btn-primary">로그인</button>
    <a href="https://kauth.kakao.com/oauth/authorize?client_id=a2c4b14206d031ec10baf8a8228e804d&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_button.png"></a>
  </form>

</div>
<!-- 
<script src="/js/user.js"></script>
 -->

<%@ include file="../layout/footer.jsp" %>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>