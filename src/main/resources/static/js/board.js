let index = {
	init:function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		$("#btn-delete").on("click", ()=>{
			this.deleteById();
		});
		$("#btn-update").on("click", ()=>{
			this.update();
		});
		$("#btn-reply-save").on("click", ()=>{
			this.replySave();
		});
	},
	
	save:function(){
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type : "POST",
			url : "/api/board",
			data : JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			dataType : "json" 
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	deleteById:function(){
		alert("삭제해봐");
		// id 값이 id인 태그의 text 값을 id 라는 변수에 담았다.
		let id = $("#id").text();
	
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id,
			dataType: "json"
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	update:function(){
		let id = $("#id").val();
	
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type : "PUT",
			url : "/api/board/"+id,
			data : JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			dataType : "json" 
		}).done(function(resp){
			alert("글수정이 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	replySave:function(){
		let data = {
			userId: $("#userId").val(),
			content: $("#reply-content").val(),
			boardId: $("#boardId").val()
		};
	
		console.log(data);

		if(data.content == ""){
			alert("내용을 입력해 주세요");
			
			$.ajax({
				type : "GET",
				url : `/board/{data.boardId}`
			});
		}else{
			$.ajax({
				type : "POST",
				//자바스크립트의 변수값에 스트링이 들어온다
				// 백틱 (``) 으로 한 이유는 let boardId 라는 변수값을 직접 쓰고 싶어서 저렇게 한것이라고 한다.
				url : `/api/board/${data.boardId}/reply`,
				data : JSON.stringify(data),
				contentType : "application/json; charset=utf-8", 
				dataType : "json" 
			}).done(function(resp){
				alert("댓글작성이 완료되었습니다.");
				console.log(resp);
				location.href = `/board/${data.boardId}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
		}
	},
	
	replyDelete: function(boardId, replyId){
		//alert(replyId);
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"	
		}).done(function(resp){
		   alert("댓글 삭제 성공");
		   location.href = `/board/${boardId}`;
		}).fail(function(error){
		   alert(JSON.stringify(error));
		});
	},
	
	
	
}

index.init();