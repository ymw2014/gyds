
var prefix = "/order/order";
var url = $('#url').val();
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					
					{	
						
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/listCashOut", // 服务器数据的加载地址
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
									field : 'orderNumber', 
									title : '订单编号' 
								},
																{
									field : 'name', 
									title : '用户姓名' 
								},
								{
									field : 'nikeName', 
									title : '用户昵称' 
								},
																/*{
									field : 'teamName', 
									title : '团队名称' 
								},*/
																{
									field : 'businessTime', 
									title : '下单时间' 
								},
																{
									field : 'orderType', 
									title : '订单类型',
									formatter: function (value, index){
										if(value == 1){
											return '收入'
										}
										if(value == 2){
											return '支出'
										}
									}
								},
																{
									field : 'username', 
									title : '审核人' 
								},
																{
									field : 'remake', 
									title : '备注信息' 
								},
																{
									field : 'cashUpType', 
									title : '充值类型',
									formatter: function (value, index){
										if(value == 0){
											return '支付宝'
										}
										if(value == 1){
											return '微信'
										}
										if(value == 2){
											return '其他'
										}
									}
								},
																{
									field : 'cashOutType', 
									title : '提现类型' ,
									formatter: function (value, index){
										if(value == 0){
											return '支付宝'
										}
										if(value == 1){
											return '微信'
										}
										if(value == 2){
											return '其他'
										}
									}
								},
																{
									field : 'expIncType', 
									title : '类型',
									formatter: function (value, index){
										if(value == 0){
											return '提现'
										}
										if(value == 1){
											return '充值'
										}
										if(value == 2){
											return '打赏'
										}
										if(value == 3){
											return '红包'
										}
										if(value == 4){
											return '广告费用'
										}
										if(value == 5){
											return '商城支付'
										}
									}
								},
																{
									field : 'price', 
									title : '金额' 
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										if (row.examineStatus == 2) {
											return '<a class="label label-success '+s_audit_h+'" onclick="audit('+row.id+',1)" >通过</a>&nbsp;&nbsp'+
											'<a class="label label-danger '+s_audit_h+'" onclick="audit('+row.id+',3)" >拒绝</a>';
										}
										if (row.examineStatus == 1) {
											return '<span style="color:green;">已完成</span>';
										}
										if (row.examineStatus == 3) {
											return '<span style="color:red;">已拒绝</span>';
										}
										
									}
								}],onLoadSuccess : function(){  //加载成功时执行  
									if(url != '/listCashUp'){
									     $('#exampleTable').bootstrapTable('hideColumn', 'cashUpType');
									}
								if(url != '/listCashOut'){
									     $('#exampleTable').bootstrapTable('hideColumn', 'cashOutType');
									 	$('#exampleTable').bootstrapTable('hideColumn', 'username');
									 	$('#exampleTable').bootstrapTable('hideColumn', 'id');
									}
										}
					}
			);
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

function audit(id,status) {
	layer.confirm('确定要审核吗？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/update",
			type : "post",
			data : {
				'id' : id,
				'examineStatus':status
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