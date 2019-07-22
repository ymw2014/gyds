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
				$("#headImg").attr("src",data.sucess.head_img);
				$("#userName").text(data.sucess.name);
			} else {
				
			}

		}
	});
})