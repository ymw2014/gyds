
var prefix = "/proxybusi/apply"
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
								type:1
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
									field : 'name', 
									title : '代理商名称' 
								},
								{
									field : 'cardBackImg', 
									title : '用户形象照' ,
									formatter : function(value, row, index) {
										
										return '<img src="' + value + '" onmousemove="showBigPic(this,this.src)"  onmouseout="closeimg()"  style="width:47px;">';
									
								}
								},
																
																{
									field : 'name', 
									title : '法人' 
								},
								{
									field : 'nation', 
									title : '民族' 
								},
								
								{
									field : 'regionLevel', 
									title : '代理类型' ,
									formatter : function(value, row, index) {
										if (value == "1") {
											return '<span style="color:green;">省代理</span>';
										}
										if (value == "2") {
											return '<span style="color:green;">市代理</span>';
										}
										if (value == "3") {
											return '<span style="color:green;">县代理</span>';
										}
										if (value == "4") {
											return '<span style="color:green;">街道办代理</span>';
										}
								}
								},{
									field : 'daili', 
									title : '代理区域' 
								},
																{
									field : 'mobile', 
									title : '联系方式  ' 
								},
																{
									field : 'email', 
									title : '邮箱' 
								},
									
								{
									field : 'cardNo', 
									title : '身份证号' 
								},
																{
									field : 'cardFrontImg', 
									title : '身份证正面照' ,
									formatter : function(value, row, index) {
										
										return '<img src="' + value + '"  onmousemove="showBigPic(this,this.src)"  onmouseout="closeimg()" style="width:47px;">';
									
								}
								},
								{
									field : 'regImg', 
									title : '地区图片' ,
									formatter : function(value, row, index) {
										
										return '<img src="' + value + '"  onmousemove="showBigPic(this,this.src)"  onmouseout="closeimg()" style="width:47px;">';
									
								}
								},
																{
									field : 'sex', 
									title : '性别' ,
									formatter : function(value, row, index) {
										if (value == '1') {
											return '女'
										} 
										if (value == '0') {
											return '男'
										}
									}
								},
								
								{
									field : 'createTime', 
									title : '申请时间' 
									
								},
								{
									field : 'status', 
									title : '审核' ,
									formatter : function(value, row, index) {
										if (row.status == "1") {
											return '<a class="label label-success"  onclick="examineStatus('+row.id+',2)" >通过</a>&nbsp;&nbsp'+
											'<a class="label label-danger"  onclick="examineStatus('+row.id+',3)" >拒绝</a>';
										}
										if (value == "2") {
											return '<span style="color:green;">已审核</span>';
										}
										if (value == "3") {
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
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
										+ row.id
										+ '\')"><i class="fa fa-remove"></i></a> ';
										return  d ;
									}
								}
						]
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
}

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
 * 建团审核
 * @param id
 * @param status
 * @returns
 */
function examineStatus(id,status) {
	console.log(id);
	layer.confirm('确定要进行代理商审核吗？', {
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