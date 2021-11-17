<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
  	
  <form>
  	<input type="hidden" id="id" value="${principal.user.id }"/>
    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" value="${principal.user.username }" class="form-control" placeholder="Enter username" id="username" readonly>
    </div>
    
    <c:if test="${empty principal.user.oauth }">
	  <div class="form-group">
	    <label for="password">Password</label>
	    <input type="password" class="form-control" placeholder="Enter password" id="password" required>
	  </div>
	  
    </c:if>
    
    <div class="form-group">
      <label for="email">Email</label>
      <input type="email" value="${principal.user.email }" class="form-control" placeholder="Enter email" id="email" readonly>
    </div>
  </form>
  <button id="btn-update" class="btn btn-primary">수정완료</button>
  <button onclick="index.withdrawal(${principal.user.id})" class="btn btn-primary">회원탈퇴</button>
</div>


<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>