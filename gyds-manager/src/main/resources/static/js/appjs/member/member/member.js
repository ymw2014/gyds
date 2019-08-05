
var prefix = "/member/member"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
					            name:$('#searchName').val(),
					            telephone:$('#searchPhone').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [// src="/files/7da67300-00e3-4514-a8b2-3437aa5aa93e.jpg"
								{
									checkbox : true
								},
																{
									field : 'userId', 
									title : '用户编号' 
								},
																{
									field : 'name', 
									title : '用户名称' 
								},
																{
									field : 'username', 
									title : '登录账号' 
								},
																{
									field : 'sex', 
									title : '性别' ,
									formatter : function(value, row, index) {
										if (value = '0') {
											return '男';
										} else if (value = '1'){
											return '女';
										}
									}
								},
																{
									field : 'headImg', 
									title : '用户头像' ,
									formatter : function(value, row, index) {
										
											return '<img src="' + value + '" onmousemove="showBigPic(this,this.src)"  onmouseout="closeimg()"  style="width:47px;">';
										
									}
								},

								{
									field : 'cardFrontImg', 
									title : '身份证正面' ,
									formatter : function(value, row, index) {
										
											return '<img src="' + value + '"  onmousemove="showBigPic(this,this.src)"  onmouseout="closeimg()" style="width:47px;">';
										
									}
								},

								{
									field : 'cardBackImg', 
									title : '身份证背面' ,
									formatter : function(value, row, index) {
										
											return '<img src="' + value + '" onmousemove="showBigPic(this,this.src)"  onmouseout="closeimg()"  style="width:47px;">';
										
									}
								},
								{
									field : 'province', 
									title : '省' 
								},
								{
									field : 'city', 
									title : '市' 
								},
																{
									field : 'district', 
									title : '县' 
								},
																{
									field : 'liveAddress', 
									title : '家庭住址' 
								},
																{
									field : 'mobile', 
									title : '电话' 
								},
																{
									field : 'username', 
									title : '登录账号' 
								},/*
																{
									field : 'password', 
									title : '登录密码' 
								},*//*
																{
									field : 'teamId', 
									title : '所属团队编号' 
								},*/
																{
									field : 'account', 
									title : '账户余额' 
								},
																{
									field : 'redEnvelopeIncome', 
									title : '红包收益' 
								},
																{
									field : 'platformIntegral', 
									title : '平台积分' 
								},
															/*	{
									field : 'regioncode', 
									title : '所属区域编号' 
								},*/
																{
									field : 'isIdentification', 
									title : '是否实名认证',
									formatter : function(value, row, index) {
										console.log(value)
										if (value == '1') {
											return '是';
										} else
										if (value == '-1') {
											return '<span style="color:red;">待审核</span>';
										}else {
											return '否';
										}
									}
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										if (row.isIdentification == "-1") {
											return '<a class="label label-success '+s_examine_h+'" onclick="examineStatus('+row.userId+',1)" >通过</a>&nbsp;&nbsp'+
											'<a class="label label-danger '+s_examine_h+'" onclick="examineStatus('+row.userId+',2)" >拒绝</a>';
										}
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
/*function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}

function resetPwd(id) {
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
	
}*/


function showBigPic(obj,filepath) {
	
    //将文件路径传给img大图
    document.getElementById('pre_view').src = filepath;
    //获取大图div是否存在
    var div = document.getElementById("bigPic");
    if (!div) {
        return;
    }
    //如果存在则展示
    var ImgObj=new Image();
    	ImgObj.src= filepath;
    if(ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0))
     {
        document.getElementById("bigPic").style.display="block";
     }else{
    	 return;
     }
    //获取鼠标坐标
    var e = event || window.event;
   	var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
   	var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
  	var intX = e.pageX || e.clientX + scrollX;
   	var intY = e.pageY || e.clientY + scrollY;
    //var intX = window.event.clientX;
    //var intY = window.event.clientY;
    //设置大图左上角起点位置
    div.style.left = intX +10+ "px";
    div.style.top = intY - 150+"px";
}

function closeimg(){
    document.getElementById("bigPic").style.display="none";
}

/**
 * 实名认证审核
 * @param id
 * @param status
 * @returns
 */
function examineStatus(id,status) {
	console.log(id);
	layer.confirm('确定要进行实名认证审核吗？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/examine",
			type : "post",
			data : {
				'id' : id,
				'status':status
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}