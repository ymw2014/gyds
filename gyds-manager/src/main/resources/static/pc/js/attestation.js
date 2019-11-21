
function queryCost(){
		var id = $("#teamType option:selected").val();
		var para ={"id":id};
		var price = '';
		$.ajax({
			type : "POST",
			url : "/pc/regTeam/queryCost",
			data : para,
			async:false,
			error : function(request) {
				parent.layer.alert("Connection error");
			},
			success : function(data) {
				if (data.code == 0) {
					 price = data.price==null?0:data.price;
					$("#price").attr("value", price);
				} else {
					parent.layer.alert(data.msg)
				}
			}
		});
		return price;
		
	}
layui.use('upload', function() {
	var upload = layui.upload;
	var uploadInst = upload.render({
		elem : '#file4', //绑定元素
		url : '/common/sysFile/upload', //上传接口
		size : 10000,
		accept : 'file',
		done : function(r) {
			var fileName = r.fileName;
			$("#regImg").val(fileName);
			$("#regImg1").attr("src", fileName);
		},
		error : function(r) {
			layer.msg(r.msg);
		}
	});
});

$("#downTeam").on('click', function() {
	var cardBackImg = $('input[name=cardBackImg]').val();
	var name = $('input[name=name]').val();
	var politicalStatus = $('#politicalStatus option:selected').text();
	var nation = $('#nation option:selected').text();
	var education = $('#education option:selected').text();
	var post = $('#post option:selected').text();
	var cardNo = $('input[name=cardNo]').val();
	var cardFrontImg = $('input[name=cardFrontImg]').val();
	var email = $('input[name=email]').val();
	var mobile = $('input[name=mobile]').val();
	var address = $('input[name=address]').val();
	if(cardBackImg!=''&name!=''&politicalStatus!=''&nation!=''&education!=''&post!=''&cardNo!=''&cardFrontImg!=''&email!=''&mobile!=''&address!=''){
		$('#creatTeam').show();
		$('#signupForm').hide();
	}else{
		alert("请补全完整信息");
	}
	});
	
	$("#upTeam").on('click', function() {
		$('#creatTeam').hide();
		$('#signupForm').show();
	});
	
	
	$("#downProxy").on('click', function() {
		var cardBackImg = $('input[name=cardBackImg]').val();
		var name = $('input[name=name]').val();
		var politicalStatus = $('#politicalStatus option:selected').text();
		var nation = $('#nation option:selected').text();
		var education = $('#education option:selected').text();
		var post = $('#post option:selected').text();
		var cardNo = $('input[name=cardNo]').val();
		var cardFrontImg = $('input[name=cardFrontImg]').val();
		var email = $('input[name=email]').val();
		var mobile = $('input[name=mobile]').val();
		var address = $('input[name=address]').val();
		if(cardBackImg!=''&name!=''&politicalStatus!=''&nation!=''&education!=''&post!=''&cardNo!=''&cardFrontImg!=''&email!=''&mobile!=''&address!=''){
			$('#proxybusi').show();
			$('#signupForm').hide();
		}else{
			alert("请补全完整信息");
		}
	});
	
	$("#upProxy").on('click', function() {
		$('#proxybusi').hide();
		$('#signupForm').show();
	});
	
	layui.use('upload', function() {
		var upload = layui.upload;

		//执行实例
		var uploadInst = upload.render({
			elem : '#file', //绑定元素
			url : '/common/sysFile/upload', //上传接口
			size : 10000,
			accept : 'file',
			done : function(r) {
				var fileName = r.fileName;
				$("#imgUrl").val(fileName);
				$("#img").attr("src", fileName);
			},
			error : function(r) {
				layer.msg(r.msg);
			}
		});

		//执行实例
		var uploadInst = upload.render({
			elem : '#file1', //绑定元素
			url : '/common/sysFile/upload', //上传接口
			size : 10000,
			accept : 'file',
			done : function(r) {
				var fileName = r.fileName;
				$("#imgUrl1").val(fileName);
				$("#img1").attr("src", fileName);
			},
			error : function(r) {
				layer.msg(r.msg);
			}
		});

	});
	//通过省编号获取市
	function pronv() {
		$("#province").val($("#pron").find("option:selected").text());
		$.ajax({
			type : "post",
			dataType : "json",
			url : "/pc/proxybusi/city",
			data : {
				'areaId' : $("#pron").val()
			},
			success : function(result) {
				var areaList = result.areaList;
				$("#cityId").html('');
				var html = '<option value ="">市</option>';
				$.each(areaList, function(n, value) {
					html += '<option value ="'+value.regionCode+'">'
							+ value.regionName + '</option>';
				});
				$("#cityId").append(html);
			}
		});
	}
	//通过市编号获取县区
	function cityQuery() {
		$("#city").val($("#cityId").find("option:selected").text());
		$.ajax({
			type : "post",
			dataType : "json",
			url : "/pc/proxybusi/city",
			data : {
				'areaId' : $("#cityId").val()
			},
			success : function(result) {
				var areaList = result.areaList;
				$("#xian").html('');
				var html = '<option value ="">区/县</option>';
				$.each(areaList, function(n, value) {
					html += '<option value ="'+value.regionCode+'">'
							+ value.regionName + '</option>';
				});
				$("#xian").append(html);
			}
		});
	}
	//通过区编号获取街道
	function streetQuery() {
		$("#district").val($("#xian").find("option:selected").text());
		$.ajax({
			type : "post",
			dataType : "json",
			url : "/pc/proxybusi/city",
			data : {
				'areaId' : $("#xian").val()
			},
			success : function(result) {
				var areaList = result.areaList;
				$("#jiedao").html('');
				var html = '<option value ="">街道</option>';
				$.each(areaList, function(n, value) {
					html += '<option value ="'+value.regionCode +'">'
							+ value.regionName + '</option>';
				});
				$("#jiedao").append(html);
			}
		});
	}
	function streetDist() {
		$("#street").val($("#jiedao").find("option:selected").text());
	}
	
	var m1 = new MyModal.modal(function() {
		alert("你点击了确定");
	});
	$('.btn1').on("click", function() {
		m1.show();
	});

	$.validator.setDefaults({
		ignore:":hidden:not(select)",
		submitHandler : function() {
			var type = $('#type').val();
			if(type==2){
				if($('input[name=teamTitleImg]').val()==null||$('input[name=teamTitleImg]').val()=="") {
					 return alert("团队logo不能为空");
				}
				if($('input[name=teamImg]').val()==null||$('input[name=teamImg]').val()=="") {
					return alert("团队照片不能为空");
				}
				if($('input[name=cardFrontImg]').val()==null||$('input[name=cardFrontImg]').val()=="") {
					return  alert("身份证正面不能为空");
				}
				if($('input[name=cardBackImg]').val()==null||$('input[name=cardBackImg]').val()=="") {
					return alert("个人形象照不能为空");
				}
				
				var price = queryCost();
				$('#blance').text("付款金额 " +price + "元");
				if(price==0||price==null){
					save();
				}else{
					$(".adddd").show();
					var orderNum = createOrder(price,7);
					if (orderNum != '-1') {
						$('#codeUrl').attr("src","http://www.48936.com/wxpay/pay?data=" + (price * 100) + "&orderNum=" + orderNum);
						timer = setInterval(function () {
							var msg = queryOrder(orderNum)
							if (msg == '1') {
								window.clearInterval(timer);
								save();
								$('.m-modal').hide();
								//window.location.href = "/pc/personalCenter";
							}
						},500);
					}
				}
			}else{
				save();
			}
		}
	});

	function save() {
		var type = $('#type').val();
		var jsonStr = '';
		$('#addres').val(
				$('#pro1 option:selected').text() + ','
						+ $('#city1 option:selected').text() + ','
						+ $('#area1 option:selected').text() + ','
						+ $('#street1 option:selected').text());
		if(type==2){
			 jsonStr = JSON.stringify($('#creatTeam').serializeJSON());
			 console.log(jsonStr);
		}
		if(type==3){
			 jsonStr = JSON.stringify($('#proxybusi').serializeJSON());
		}
		$('#text').val(jsonStr);
		$.ajax({
			type : "POST",
			url : "/pc/realName",
			data : $('#signupForm').serialize(),
			 beforSend:function(){ 
		         // 禁用按钮防止重复提交
		        $("#save").attr({ disabled: "disabled" });
		    }, 
			success : function(r) {
				if (r.code == 0) {
					$("#save").attr({ disabled: "disabled" });
					$("#signupForm").hide();
					$("#proxybusi").hide();
					$("#creatTeam").hide();
					$("#message").show();
				} else {
					layer.msg(r.msg);
					$("#save").attr({ disabled: "disabled" });
					$(".reloadverify").click();
				}
			},
		});
	}

	

	function byPro() {
		var area = $('#pro1 option:selected').val();
		var para = {
			"area" : area
		};
		$.ajax({
			type : "GET",
			url : "/pc/regTeam/area",
			data : para,
			error : function(request) {
				alert("请求异常");
			},
			success : function(data) {
				if (data.code == 0) {
					$("#city1").empty();
					$("#area1").empty();
					$("#street1").empty();
					// $("#area").append($("<option/>").text("区/县").attr("value",""));
					$.each(data.regList, function(i, item) {
						$("#city1").append(
								$("<option/>").text(item.regionName).attr(
										"value", item.regionCode));
					});
					$.each(data.areaList, function(i, item) {
						$("#area1").append(
								$("<option/>").text(item.regionName).attr(
										"value", item.regionCode));
					});
					$.each(data.streetList, function(i, item) {
						$("#street1").append(
								$("<option/>").text(item.regionName).attr(
										"value", item.regionCode));
					});
				} else {
					alert("数据请求异常");
				}
			}
		});
	};
	function byCity() {
		var area = $('#city1 option:selected').val();
		var para = {
			"area" : area
		};
		$.ajax({
			type : "GET",
			url : "/pc/regTeam/area",
			data : para,
			error : function(request) {
				alert("请求异常");
			},
			success : function(data) {
				if (data.code == 0) {
					$("#area1").empty();
					$.each(data.regList, function(i, item) {
						$("#area1").append(
								$("<option/>").text(item.regionName).attr(
										"value", item.regionCode));
					});
				} else {
					alert("数据请求异常");
				}
				;

			}
		});
	};
	function byStreet() {
		var street = $('#area1 option:selected').val();
		var para = {
			"area" : street
		};
		$.ajax({
			type : "GET",
			url : "/pc/regTeam/area",
			data : para,
			error : function(request) {
				alert("请求异常");
			},
			success : function(data) {
				if (data.code == 0) {
					$("#street1").empty();
					$.each(data.regList, function(i, item) {
						$("#street1").append(
								$("<option/>").text(item.regionName).attr(
										"value", item.regionCode));
					});
				} else {
					alert("数据请求异常");
				}
				;

			}
		});
	};
	

	layui.use('upload', function() {
		var upload = layui.upload;
		var uploadInst = upload.render({
			elem : '#file2', //绑定元素
			url : '/common/sysFile/upload', //上传接口
			size : 10000,
			accept : 'file',
			done : function(r) {
				var fileName = r.fileName;
				$("#imgStr").val(fileName);
				$("#img2").attr("src", fileName);
			},
			error : function(r) {
				layer.msg(r.msg);
			}
		});
	});
	
	layui.use('upload', function() {
		var upload = layui.upload;
		var uploadInst = upload.render({
			elem : '#file3', //绑定元素
			url : '/common/sysFile/upload', //上传接口
			size : 10000,
			accept : 'file',
			done : function(r) {
				var fileName = r.fileName;
				$("#imgStr0").val(fileName);
				$("#img0").attr("src", fileName);
			},
			error : function(r) {
				layer.msg(r.msg);
			}
		});
	});

	layui.use('upload',function() {
						var upload = layui.upload;
						var multi = 1;
						var name = "imgs";
						if (multi) {
							name = "imgs";
							$("#imgUpload_imgs").attr('multiple', 'multiple');
						} else {
							$("#imgUpload_imgs").removeAttr('multiple');
						}

						//执行实例
						var uploadInst = upload
								.render({
									elem : '#imgUpload_imgs', //绑定元素
									url : '/common/sysFile/upload', //上传接口
									size : 10000,
									accept : 'file',
									done : function(r) {
										var fileName = r.fileName;
										var id = r.id;
										var img = '<div class="imgbox">';
										img += '<div class="removePic" onclick="removePic(this,\'/common/sysFile/remove\')">×</div>';
										img += '<input type="hidden" name="'+name+'" value="' + fileName + '">';
										img += '<img src="' + fileName + '">';
										img += '</div>';
										if (multi) {
											$("#imgsbox_imgs").append(img);
										} else {
											$("#imgsbox_imgs").html(img);
										}

										var imgs = "";
										$("#imgsbox_imgs").find(
												"input[name='imgs']").each(
												function(i, item) {
													if (imgs == '') {
														imgs += item.value;
													} else {
														imgs += ','
																+ item.value;
													}
													$('#imgStr0').val(imgs);
												})
									}
								});

					});
	
	$(function(){
		$(":radio").click(function(){
			var level = $(this).val();
			if (level == 1) {
				$("#bail").val($("#pr").val())
			} else if(level == 2){
				$("#bail").val($("#ct").val())
			} else if(level == 3){
				$("#bail").val($("#ar").val())
			}else{
				$("#bail").val($("#ag").val())
			}
		})
	})
	function pronv2() {
		$.ajax({
            type : "post",
            dataType: "json",
            url : "/pc/proxybusi/city",
            data : {
            	'areaId': $("#pron2").val()
            },
            success : function(result) {
            	console.log(result.areaList);
            	var areaList = result.areaList;
            	$("#cityId2").html('');
            	var html = '<option value ="">市</option>';
            	$.each(areaList, function (n, value) {
            		html += '<option value ="'+ value.regionCode + '">' + value.regionName + '</option>';
            	});
            	//$("#count").html('<option value ="">区/县</option>');
            	$("#cityId2").append(html);
            }
        });
	}
	
	function cityQuery2() {
		$.ajax({
            type : "post",
            dataType: "json",
            url : "/pc/proxybusi/city",
            data : {
            	'areaId': $("#cityId2").val()
            },
            success : function(result) {
            	console.log(result.areaList);
            	var areaList = result.areaList;
            	$("#count").html('');
            	var html = '<option value ="">区/县</option>';
            	$.each(areaList, function (n, value) {
            		html += '<option value ="'+ value.regionCode + '">' + value.regionName + '</option>';
            	});
            	$("#count").append(html);
            }
        });
	}
	//通过区编号获取街道
	function streetQuery2() {
		$.ajax({
			type : "post",
			dataType : "json",
			url : "/pc/proxybusi/city",
			data : {
				'areaId' : $("#count").val()
			},
			success : function(result) {
				var areaList = result.areaList;
				$("#jiedao2").html('');
				var html = '<option value ="">街道</option>';
				$.each(areaList, function(n, value) {
					html += '<option value ="'+ value.regionCode + '">'
							+ value.regionName + '</option>';
				});
				$("#jiedao2").append(html);
			}
		});
	}
	
	/**
	 * 移除图片
	 */
	function removePic(_self,deleteurl){
	    $(_self).parent().remove();
	    if(deleteurl){
	        $.post(deleteurl,{path:$(_self).parent().find('img').attr('src')});
	    }
	}
	
	
	//创建订单
	function createOrder(fee,orderType){
		var orderNum = '';
		$.ajax({
			type : "POST",
			url : "/pc/news/createOrder",
			data : {'data': fee,
					'orderType':orderType},
			async:false,
			success : function(r) {
				if (r.code == 0) {
					orderNum = r.msg;
				} else if(r.code == -1){
					orderNum =-1;
				}else if(r.code == -2){
					orderNum =-2;
				}
				}
		});
		return orderNum;
	}
	//查询订单支付状态
	function queryOrder(orderNum){
		var stauts = '';
		$.ajax({
			type : "POST",
			url : "/wxpay/queryOrder",
			data : {'orderNum': orderNum},
			async:false,
			success : function(r) {
				if (r.code == 0) {
					stauts = r.msg;
				} else if(r.code == -1){
					stauts =-1;
				}else if(r.code == -2){
					stauts =-2;
				}
				}
		});
		return stauts;
	}
	$(".m-modal,.m-modal-close,.m-btn-cancel").on("click", function(event) {
		$('.m-modal').hide();
	});