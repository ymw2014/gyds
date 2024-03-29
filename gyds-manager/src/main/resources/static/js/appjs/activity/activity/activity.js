
var prefix = "/activity/activity"
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
						offset:params.offset
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
						field : 'actTitle', 
						title : '活动名称' 
					},
					{
						field : 'actIntro', 
						title : '活动简介' 
					},
					{
						field : 'name', 
						title : '发布人' 
					},
					{
						field : 'actPrice', 
						title : '报名费用' 
					},
					{
						field : 'typeName', 
						title : '活动类型' 
					},
					{
						field : 'startTime', 
						title : '报名开始时间' 
					},
					{
						field : 'endTime', 
						title : '报名截止时间' 
					},
					{
						field : 'activiStartTime', 
						title : '活动开始时间' 
					},
					{
						field : 'activiEndTime', 
						title : '活动截止时间' 
					},
					{
						field : 'createTime', 
						title : '活动创建时间' 
					},
					{
						field : 'ticketCount', 
						title : '门票数量' 
					},
					{
						field : 'actType', 
						title : '票种' ,
						formatter: function (value, index) {
							if (value == 0) {
								return '免费';
							}
							if (value == 1) {
								return '付费';
							}
						}	
					},
					{
						field : 'status',
						title : '活动状态',
						align : 'center',
						formatter: function (value,row,index){
							if(row.examineStatus==1){
								if (value == 1) {
									return '<a class="" onclick="staStatus('+row.id+',2)" ><span class="label label-warning">报名中</span></a>';
								}
								if (value == 2) {
									return '<a class="" onclick="entStatus('+row.id+',3)" ><span class="label label-primary">进行中</span></a>';
								}
								if (value == 3) {
									return '<span class="label label-danger">已结束</span>';
								}
							}
						}	
					},
					{
						field : 'examineStatus',
						title : '审核状态',
						align : 'center',
						formatter: function (value,row, index){
							if (value == 0) {
								return '<a class="label label-success '+s_examine_h+'" onclick="examineStatus('+row.id+',1)" >通过</a>&nbsp;&nbsp'+
								'<a class="label label-danger '+s_examine_h+'" onclick="examineStatus('+row.id+',2)" >拒绝</a>';
							}
							if (value == 1) {
								return '<span style="color:green;">已审核</span>';
							}
							if (value == 2) {
								return '<span style="color:red;">已拒绝</span>';
							}
						}	
					},
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var e='<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
							+ row.id
							+ '\')"><i class="fa fa-edit"></i></a> ';
							var f = '';
							if(row.status!=3&&row.status!=4){
								f = '<a class="btn btn-success btn-sm"'+s_audit_h+' href="#" title="审核"  mce_href="#" onclick="resetPwd(\''
								+ row.id
								+ '\')"><i class="fa fa-key"></i></a> ';
							}
							var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
							+ row.id
							+ '\')"><i class="fa fa-remove"></i></a> ';
							var u='<a class="btn btn-success btn-sm"'+s_auditUser_h+' href="#" title=""  mce_href="#" ><span class="" onclick="auditUser('+row.id+')">报名详情</span></a>'
							//var u = '<a class="btn btn-success btn-sm"'+s_auditUser_h+' href="#" title=""  mce_href="#" ><i class="fa fa-eye"></i></a> ';
							return e + d + f + u ;
						}
					} ]
			});
}
function reLoad() {
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
		area : [ '90%', '90%' ],
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
function staStatus(id,status){
	layer.confirm('确定要开始活动 ?', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/upStatus",
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
function entStatus(id,status){
	layer.confirm('确定要开始活动 ?', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/upStatus",
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
function resetPwd(id) {
	layer.open({
		type : 2,
		title : '审核',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '90%', '90%' ],
		content : prefix + '/audit/' + id // iframe的url
	});
}

function auditUser(id) {
	layer.open({
		type : 2,
		title : '审核报名人员',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '90%', '90%' ],
		content : '/actApply/apply/auditList/'+ id // iframe的url
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

function examineStatus(id,status) {
	console.log(id);
	layer.confirm('确定要审核改活动吗？', {
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

