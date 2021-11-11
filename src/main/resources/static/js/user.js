let index = {
	init:function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
	},
	
	save:function(){
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email : $("#email").val()
		};
		//console.log(data);
		//ajax 통신을 이용해서 3개의 데이터를 제이슨으로 변경하여 insert요청
		//ajax 호출시 default가 비동기 호출
		// 회원가입 수행 요청
		
		$.ajax({
			type : "POST",
			url : "/auth/joinProc",
			data : JSON.stringify(data), // http body 데이터
			contentType : "application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (mine)
			dataType : "json" // 요청을 서버로 해서 응답이 왔을떄 기본적으로 모든것이 문자열 (생긴게 json 이라면 => javaScript)
		}).done(function(resp){
			alert("회원가입이 완료 되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
		
	},
	
	
	
}

index.init();