
var prefix = "/member/integral"
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
							var obj= {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
                                goodsName:$('#goodsName').val(),
					           state:$('#state').val()==""?null:$('#state').val()
							};
							return obj;
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
									title : 'ID'
								},
																{
									field : 'userId', 
									title : '用户ID'
								},
																{
									field : 'createTime', 
									title : '兑换时间'
								},
																{
									field : 'remarks', 
									title : '交易说明' 
								},
																{
									field : 'amount', 
									title : '交易积分' 
								},
																{
									field : 'goodsId', 
									title : '积分商品ID' 
								},
																{
									field : 'goodsName', 
									title : '商品名' 
								},
																{
									field : 'quantity', 
									title : '数量' 
								},
																{
									field : 'state', 
									title : '状态',
									formatter : function (value, row, index) {
										if(value ==1){
											return '待发货';
										}
                                        if(value ==2){
                                            return "已发货";
                                        }
                                        if(value ==3){
                                            return "已收货";
                                        }
                                        return '';
                                    }
								},
																{
									field : 'phone', 
									title : '联系方式'
								},
																{
									field : 'address', 
									title : '收货地址'
								},
																{
									field : 'id',
                                	title : '操作',
									formatter : function (value, row, index) {
										if(row.state==1){
                                        	return '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="发货" onclick="send_goods('+row.id+')">发货</a>';
										}
										return '-';
									}

								}
								]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function send_goods(id) {
    layer.confirm('确定将状态改为已发货？', {
        btn : [ '确定', '取消' ]
    }, function() {
        $.get(prefix+"/sendGoods?id="+id,function (data) {
            layer.msg(data.msg);
            setTimeout(function () {
                reLoad();
            },1000);
        });
    })
}