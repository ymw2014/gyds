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
				if(data.sucess.userName==undefined||data.sucess.userName==null){
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

function byPro(){
		var area = $('#pro option:selected').val();
		var para = {"area":area};
		$.ajax({
			type : "GET",
			url : "/pc/regTeam/area",
			data : para,
			error : function(request) {
				alert("请求异常");
			},
			success : function(data) {
				if (data.code == 0) {
					$("#city").empty();
					$("#area").empty();
					 $("#area").append($("<option/>").text("区/县").attr("value",""));
					  $.each(data.regList, function(i, item){ 
					   $("#city").append($("<option/>").text(item.regionName).attr("value",item.regionCode));
					   }); 
				}else{
					alert("数据请求异常");
				}
			}
		});
	};
	function byCity(){
		var area = $('#city option:selected').val();
		var para = {"area":area};
		$.ajax({
			type : "GET",
			url : "/pc/regTeam/area",
			data : para,
			error : function(request) {
				alert("请求异常");
			},
			success : function(data) {
				if (data.code == 0) {
					$("#area").empty();
					  $.each(data.regList, function(i, item){ 
					   $("#area").append($("<option/>").text(item.regionName).attr("value",item.regionCode));
					   }); 
				}else{
					alert("数据请求异常");
				}
			}
		});
	}
