$().ready(function() {
	console.log(0);
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		console.log(0);
		var cost = $('#cost').val();
		if(cost==0){
			save();
		}else{
		var orderNum = createOrder(cost,14);
		if (orderNum != '-1') {
			$('#order').val(orderNum);
			layer.open({
				type : 2,
				title : '扫码支付',
				maxmin : true,
				shadeClose : false, // 点击遮罩关闭层
				load : true,
				area : [ '430px', '430px' ],
				content : 'http://www.48936.com/wxpay/pay?data=' + (cost * 100) + '&orderNum=' + orderNum // iframe的url
			});
			timer = setInterval(function () {
					var msg = queryOrder(orderNum)
					if (msg == '1') {
						sava();
					}
				},500);
		} else {
			layer.msg("未知错误");
		}
		}
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/project/info/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			},
			projectName : {
				required : true
			},
			projectType : {
				required : true
			},
			tickets : {
				required : true
			},
			intro : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			},
			projectName : {
				required : icon + "请输入项目名称"
			},
			projectType : {
				required : icon + "请选择项目类型"
			},
			tickets : {
				required : icon + "请输入承接费用"
			},
			intro : {
				required : icon + "请输入简介"
			}
		}
	})
}