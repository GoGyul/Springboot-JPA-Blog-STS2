<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp" %>

<div class="container">
	
<c:forEach var="board" items="${boards.content }">
  <div class="card m-2" >
    <div class="card-body">
    <!-- 컨트롤러에서 Model에 "boards" 라는 String 을 첫번째 매개변수로 하여
    	 컬렉션인 List<Board> 를 즉, Board 객체들을 두번째 매개변수로 넣어 던져주었다
    	 c:forEach 의 items로 매칭시켜서 c:forEach 에서 쓸 변수명을 board 라고 명명한뒤
    	 ${board.title} 처럼 써주면 Board클래스의 getTitle 메서드가 실행되는 것과 같은 효과를 내게 된다.  
         -->
      <h4 class="card-title">${board.title }</h4>
      	<a href="/board/${board.id }" class="btn btn-primary">상세보기</a>
    </div>
  </div>
</c:forEach>	

<ul class="pagination justify-content-center">
  <c:choose>
  	<c:when test="${boards.first }">
  		<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li>
  	</c:when>
  	<c:otherwise>
  		<li class="page-item"><a class="page-link" href="?page=${boards.number-1 }">Previous</a></li>
  	</c:otherwise>
  </c:choose>	
  <c:forEach var="i" begin="1" end="${boards.totalPages }">
  	<li class="page-item"><a class="page-link" href="?page=${i-1 }">${i}</a></li>
  </c:forEach>
  <c:choose>
  	<c:when test="${boards.last }">
  		<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li>
  	</c:when>
  	<c:otherwise>
  		<li class="page-item"><a class="page-link" href="?page=${boards.number+1 }">Next</a></li>
  	</c:otherwise>
  </c:choose>	
</ul>
  
</div>

<%@ include file="layout/footer.jsp" %>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>