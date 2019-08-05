
var prefix = "/member/bill"
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
						// 发送到服务器的数据编码类型
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
                                transactionId:$('#transactionId').val(),
					           	nickname:$('#nickname').val(),
                                sellerName:$('#sellerName').val()
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
									title : 'ID'
								},
								{
									field : 'transactionId',
									title : '交易流水号'
								},
																{
									field : 'billType',
									title : '账单类型',
									formatter : function(value, row, index) {
										if (value == '0') {
                                            return '支出';
                                        }
                                        if(value=='1'){
											return '收入';
										}
                                        return '-';
									}
								},
								// {
								// 	field : 'transactionType',
								// 	title : '交易类型',
                                 //    formatter : function(value, row, index) {
                                 //        //交易类型 1充值 2下单 3退款  4提现 5抽奖
                                 //        if (value == '1') {
                                 //            return '充值';
                                 //        }
                                 //        if(value=='2'){
                                 //            return '购物';
                                 //        }
                                 //        if(value=='3'){
                                 //            return '退款';
                                 //        }
                                 //        if(value=='4'){
                                 //            return '提现';
                                 //        }
                                 //        if(value=='5'){
                                 //            return '抽奖';
                                 //        }
                                 //        return '-';
                                 //    }
								// },
																{
									field : 'state', 
									title : '交易状态',
									formatter : function(value, row, index) {
										if (value == '0') {
											return '交易失败';
										}
										if(value=='1'){
											return '交易成功';
										}
										return '-';
									}
								},
																{
									field : 'userId', 
									title : '用户ID' 
								},
								{
									field : 'nickname',
									title : '用户昵称'
								},
																{
									field : 'createTime', 
									title : '交易时间'
								},
																{
									field : 'remarks', 
									title : '交易说明' 
								},
																{
									field : 'merchantId', 
									title : '商户id' 
								},
								{
									field : 'sellerName',
									title : '商户名称'
								},
																{
									field : 'amount', 
									title : '交易金额'
								},
								// 								{
								// 	field : 'goodsId',
								// 	title : '商品ID'
								// },
																{
									field : 'currentBalance', 
									title : '用户余额'
								},
								{
									field : 'payMethod',
									title : '支付方式',
                                    formatter : function(value, row, index) {
                                        if (value == '1') {
                                            return '余额';
                                        }
                                        if(value=='2'){
                                            return '微信';
                                        }
                                        if(value=='3'){
                                            return '支付宝';
                                        }
                                        if(value=='4'){
                                            return '其他';
                                        }
                                        return '-';
                                    }
								},
								]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}