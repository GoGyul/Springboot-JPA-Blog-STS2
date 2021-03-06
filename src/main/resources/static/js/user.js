let index = {
	init:function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		$("#btn-update").on("click", ()=>{
			this.update();
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
		
			if(resp.status === 500){
				alert("회원가입에 싶래하였습니다.");
			}else{
				alert("회원가입이 완료 되었습니다.");
				location.href = "/";
			}		
			console.log("resp =========="+resp);
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
		
	},
	
	update:function(){
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email : $("#email").val()
		};
		
		if(data.password == ""){
			alert("비밀번호의 값이 없습니다.");
			$("#password").focus();
			return false;
		}else{
			$.ajax({
				type : "PUT",
				url : "/user",
				data : JSON.stringify(data), // http body 데이터
				contentType : "application/json; charset=utf-8", //body 데이터가 어떤 타입인지 (mine)
				dataType : "json" // 요청을 서버로 해서 응답이 왔을떄 기본적으로 모든것이 문자열 (생긴게 json 이라면 => javaScript)
			}).done(function(resp){
				alert("회원수정이 완료 되었습니다.");
				console.log(resp);
				location.href = "/";
			}).fail(function(error){
			    alert(JSON.stringify(error));
			});
		}
	},
	
	withdrawal:function(userId){
		
		if(confirm("정말 탈퇴하겠습니까?")){
		console.log(userId);
		    $.ajax({
		    	type : "DELETE",
		    	url : "/auth/withdraw/"+userId,
		    	contentType : "application/json; charset=utf-8",
		    	dataType : "json"
		    }).done(function(resp){
		        alert("회원탈퇴가 완료 되었습니다.");	
		        location.href = "/logout";
		    }).fail(function(error){
		    
		    })
		}else{
			console.log("취소");
		}
	}
	
	
	
}

index.init();