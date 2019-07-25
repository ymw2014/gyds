$(function(){
	$.ajax({
		cache : true,
		type : "GET",
		url : "/pc/setup",
		async : true,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			console.log(data);
			if (data.code == 0) {
				$("#logo").attr("src",data.sucess.logo);
				$("#preparation_no").text(data.sucess.preparationNo);
				$("#copyright_into").text(data.sucess.copyrightInto);
				$("#official_account_img").attr("src",data.sucess.officialAccountImg);
				console.log(data.sucess.name);
				if(data.sucess.name==undefined){
					$("#login_ok").hide();//no_login
					$("#no_login").show();//
				}else{
					$("#login_ok").show();
					$("#no_login").hide();
					$("#headImg").attr("src",data.sucess.head_img);
					$("#userName").text(data.sucess.name);
				}
			} else {
				
			}

		}
	});
})