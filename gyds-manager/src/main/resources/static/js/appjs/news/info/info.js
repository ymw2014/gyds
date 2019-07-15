
var prefix = "/news/info"
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
								status:$('#status').val(),
								title:$('#title').val(),
								isTop:$('#top').val(),
								startTime:$('#startTime').val(),
								endTime:$('#endTime').val()
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
								{
									checkbox : true
								},
																{
									field : 'title', 
									title : '标题' 
								},
																{
									field : 'author', 
									title : '作者' 
								},
								
								{
									field : 'source', 
									title : '来源' 
								},
																{
									field : 'teamName', 

									title : '发布团队' 
								},
								
																{

									field : 'isTop', 
									title : '是否置顶',
									formatter: function (value, index){
										if(value == 0){
											return "否"
										}
										if(value == 1){
											return "是"
										}
										if(value == 2){
											return "申请中"
										}
									}
							   },
																{
									field : 'rewardMax', 
									title : '打赏总额度' 
								},
																{
									field : 'rewardNumOfPeople', 
									title : '打赏人数' 
								},
																{
									field : 'rewardCount', 
									title : '打赏次数' 
								},
																{
									field : 'rewardPrice', 
									title : '打赏总金额' 
								},
																{
									field : 'paymentDuration', 
									title : '置顶时长' 
								},
														{
									field : 'createTime', 
									title : '创建日期' 
								},
																
																/*{
									field : 'publicTime', 
									title : '发布时间' 
								},*/
																{
									field : 'status', 
									title : '状态',
									align : 'center',
									formatter: function (value,row,index){
										if(value == 0){
											var s= '<a class="btn btn-success btn-sm"'+s_status_h+' href="#" title=""  mce_href="#" ><span class="" onclick="status('+row.id+',1)">未发布</span></a>'
											return s;
										}
										if(value == 1){
											var s= '<a class="btn btn-success btn-sm"'+s_status_h+' href="#" title=""  mce_href="#" ><span class="" onclick="status('+row.id+',0)">发布</span></a>'
											return s;
										}
									}
																},
																
																{
									title : '操作',
									field : 'id',
									width : '200',
									align : 'center',
									width : '260px',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')"><i class="fa fa-remove"></i></a> ';
										var s= '<a class="btn btn-success btn-sm"'+s_audit_h+' href="#" title=""  mce_href="#" ><span class="" onclick="audit('+row.id+')">置顶审核</span></a>'
										
										var c= '<a class="btn btn-success btn-sm"'+s_comList_h+' href="#" title=""  mce_href="#" ><span class="" onclick="commentList('+row.id+')">评论列表</span></a>'
										
										var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
												+ row.id
												+ '\')"><i class="fa fa-key"></i></a> ';
										return e + d + s + c;
									}
								} ]
					});
}
function reLoad() {
	var startTime = $('#startTime').val()
	var endTime = $('#endTime').val()
	if (startTime > endTime) {
		layer.msg("开始时间不能大于结束时间");
		return;
	}
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
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
function audit(id) {
	layer.open({
		type : 2,
		title : '审核',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/audit/' + id // iframe的url
	});
}
function commentList(id) {
	layer.open({
		type : 2,
		title : '评论列表',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '1000px', '620px' ],
		content : '/news/comment/comList/' + id // iframe的url
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
function status(id,status) {
	var info = {"id":id,"status":status};
	$.ajax({
		cache : true,
		type : "POST",
		url : "/news/info/update",
		data : info,// 你的formid
		async : true,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				history.go(0);
			} else {
				parent.layer.alert(data.msg)
			}

		}
	});
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
}