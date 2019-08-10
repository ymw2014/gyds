
function queryCost(){
		var id = $("#teamType option:selected").val();
		var para ={"id":id};
		$.ajax({
			type : "POST",
			url : "/pc/regTeam/queryCost",
			data : para,
			error : function(request) {
				parent.layer.alert("Connection error");
			},
			success : function(data) {
				if (data.code == 0) {
					var price = data.price==null?0:data.price;
					$("#price").attr("value", price);
				} else {
					parent.layer.alert(data.msg)
				}
			}
		});
		
		
	}


$("#downTeam").on('click', function() {
		$('#creatTeam').show();
		$('#signupForm').hide();
	});
	
	$("#upTeam").on('click', function() {
		$('#creatTeam').hide();
		$('#signupForm').show();
	});
	
	
	$("#downProxy").on('click', function() {
		$('#proxybusi').show();
		$('#signupForm').hide();
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
			size : 1000,
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
			size : 1000,
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

	$().ready(function() {
		$("#save").on('click', function() {
			$("#signupForm").submit();
		});
	})

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
			success : function(r) {
				if (r.code == 0) {
					$("#signupForm").hide();
					$("#proxybusi").hide();
					$("#creatTeam").hide();
					$("#message").show();
				} else {
					layer.msg(r.msg);
					$(".reloadverify").click();
				}
			},
		});
	}

	$().ready(function() {
		validateRule();
	});

	$.validator.setDefaults({
		submitHandler : function() {
			save();
		}
	});
	function validateRule() {
		var icon = "<i class='fa fa-times-circle' ></i> ";
		$("#signupForm")
				.validate(
						{
							rules : {
								name : {
									required : true
								},
								mobile : {
									required : true,
									isMobile : true
								},
								liveAddress : {
									required : true
								},
								cardNo : {
									required : true,
									isIdCardNo : true
								},
								email : {
									required : true
								},//district
								sex : {
									required : true
								},
								district : {
									required : true
								}
							},
							messages : {
								name : {
									required : "<span style='color:red;margin-left:10px;'>请输入姓名</span>"
								},
								mobile : {
									required : "<span style='color:red;margin-left:10px;'>请输入手机号</span>",
									isMobile : "<span style='color:red;margin-left:10px;'>请输入正确的手机号</span>"
								},
								liveAddress : {
									required : "<span style='color:red;margin-left:10px;'>请输入详细地址</span>"
								},
								cardNo : {
									required : "<span style='color:red;margin-left:10px;'>请输入身份证</span>",
									isIdCardNo : "<span style='color:red;margin-left:10px;'>请输入正确的身份证号</span>"
								},
								email : {
									required : "<span style='color:red;margin-left:10px;'>请输入邮箱地址</span>"
								},
								sex : {
									required : "<span style='color:red;margin-left:10px;'>性别不能为空</span>"
								},
								district : {
									required : "<span style='color:red;margin-left:10px;'>请选择地区</span>"
								}
							}
						})
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
			size : 1000,
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
									size : 1000,
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