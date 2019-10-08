$(function(){
	$.ajax({
		cache : true,
		type : "GET",
		url : "/setup",
		async : true,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			console.log(data);
			if (data.code == 0) {
				$("#preparation_no").text(data.sucess.preparationNo);
				$("#copyright_into").text(data.sucess.copyrightInto);
			}
		}
	});
})


