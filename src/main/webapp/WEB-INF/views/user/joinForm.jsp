<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
  	
  <form>
    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" class="form-control" placeholder="Enter username" id="username">
    </div>
    
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" class="form-control" placeholder="Enter password" id="password">
    </div>
    
    <div class="form-group">
      <label for="email">Email</label>
      <input type="email" class="form-control" placeholder="Enter email" id="email">
    </div>
  </form>
  <button id="btn-save" class="btn btn-primary">회원가입완료</button>

</div>


<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>