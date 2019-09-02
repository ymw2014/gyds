var prefix = "/volunteer/volunteer"
$(function() {
	load();
	
	$('#datetimepicker2').datetimepicker({
        format: 'YYYY-MM-DD hh:mm:ss',
        locale: moment.locale('zh-cn')
    });
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
								volunteerName:$('#searchName').val(),
					            mobile:$('#searchPhone').val(),
					            startTime:$('#startTime').val(),
					            endTime:$('#endTime').val(),
					            teamId:$('#teamId').val()
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
									field : 'volunteerNumber', 
									title : '志愿者编号' 
								},
								{
									field : 'volunteerName', 
									title : '志愿者名称' 
								},
								{
									field : 'headImg', 
									title : '头像' ,
									formatter : function(value, row, index) {
										
										return '<img src="' + value + '"  style="width:47px;">';
									
									}
								},
																{
									field : 'teamName', 
									title : '所属团队名称' 
								},
																{
									field : 'city', 
									title : '所在地区' 
								},
								{
									field : 'street', 
									title : '所在街道' 
								},
																{
									field : 'mobile', 
									title : '电话' 
								},
																{
									field : 'sex', 
									title : '性别' ,
									formatter : function(value, row, index) {
										if (value == '1') {
											return '女'
										} 
										return '男';
									}
								},
																{
									field : 'level', 
									title : '志愿者等级' 
								},
								{
									field : 'createTime',
									title : '申请时间'
								}]
					});
}
function reLoad() {
	var startTime = $('#startTime').val()
	var endTime = $('#endTime').val()
	if (startTime != '' && endTime != '') {
		if (startTime > endTime) {
			layer.msg("开始时间不能大于结束时间");
			return;
		}
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

function quitTeam(id) {
	layer.confirm('确定要请离团队？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/quitTeam",
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


function audit(id,status) {
	if (status == '2') {
		reject(id,status);
	} else {
		accept(id,status);
	}
}

function reject(id,status) {
	layer.confirm('确定要拒绝该申请吗？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/update",
			type : "post",
			data : {
				'id' : id,
				'auditStatus' : status
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

function accept(id,status) {
		$.ajax({
			url : prefix+"/update",
			type : "post",
			data : {
				'id' : id,
				'auditStatus' : status
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
}