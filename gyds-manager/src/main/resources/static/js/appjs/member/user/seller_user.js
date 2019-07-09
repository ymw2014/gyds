
var prefix = "/member/user"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/slist", // 服务器数据的加载地址
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
                        pageList: [10, 25, 50, 100],
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
					            id:$('#userId').val(),
								nickname:$('#nickname').val(),
                                phone:$('#phone').val(),
                                sellerName:$('#sellerName').val()

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
									field : 'id', 
									title : 'id'
								},
								{
									field : 'headimgurl',
									title : '头像',
									formatter : function (value, row, index) {
										if(value!=null){
											return '<img src="'+value+'"/>';
										}
										return '-';
                                    }
								},
																{
									field : 'nickname', 
									title : '微信昵称' 
								},
																{
									field : 'sexdesc', 
									title : '性别'
								},

																{
									field : 'balance', 
									title : '余额'
								},
								{
									field : 'sellerName',
									title : '商户名称'
								},
                                {
                                    field : 'totalCommission',
                                    title : '佣金总计'
                                },
                                {
                                    field : 'refundCommission',
                                    title : '退款应扣佣金',
                                    width : 200,
                                    formatter : function (value, row, index) {
                                        if(value!=null&&value!=0){
                                            return value+'</br><a class="btn btn-primary btn-sm" href="javascript:;"  title="扣除" onclick="deduction('+row.id+','+value+')">扣除</a>';
                                        }
                                        return '0';
                                    }
                                },
								{
									field : 'phone',
									title : '手机号'
								},
								{
									field : 'integral',
									title : '积分'
								}
								]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}

function deduction(id,value) {
    layer.confirm("系统将从该用户余额中扣除"+value+"元", {
        btn : [ '确定', '取消' ]
    }, function() {
        $.get("/member/user/deduction/"+id,function (result) {
            console.log(result);
            layer.msg(result.msg);
            if(result.code==0){
                reLoad();
            }
        })
    });
}