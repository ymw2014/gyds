var district;
function prevent_reloading(){
    var pendingRequests = {};
    jQuery.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
        var key = options.url;
        console.log(key);
        if (!pendingRequests[key]) {
            pendingRequests[key] = jqXHR;
        }else{
            //jqXHR.abort();    //放弃后触发的提交
            pendingRequests[key].abort();   // 放弃先触发的提交
        }
        var complete = options.complete;
        options.complete = function(jqXHR, textStatus) {
            pendingRequests[key] = null;
            if (jQuery.isFunction(complete)) {
                complete.apply(this, arguments);
            }
        };
    });
}
/*$(function() {


});*/
//个人中心
function PersonalCenter(){
    window.location.href="/member/mine";
}
//商家列表
function ShowShangjia(){
    district=$("#areaName").val();
    window.location.href="/seller/list?areaName="+district;
}

/**
 * 商家入驻
 */
function Shangjiaruzhu(){
    window.location.href="/seller/enter";
}
//客服二维码
var app_qrcode=new Vue({
    el:".mes",
    data:{
        qrcode:""
    }
});
var app_phone=new Vue({
    el:".asideNav",
    data:{
      phone:""
    }
});

/**
 *
 * @constructor
 */

function ShowQRCode(){
    $.ajax({
        type: 'POST',
        url: "/wx/setup/info",
        success: function(result){
            if(result.status=="100000")  {
                app_qrcode.qrcode=result.data.serviceImg;
                app_phone.phone=result.data.serviceTelephone;
            }else{
                layer.open({
                    content:result.message,
                    skin:'msg',
                    time:2
                })
            }
        }

    });
}
//显示更多
function classAdd(){

    loadData();

}

function loadData(){

    var offset=parseInt($("#pagesnum").val());
    var url="/wx/shop/sGoods/list";
    var limit=6;
    var param={
        offset:(offset-1)*limit,
        limit:limit
    }
    $.ajax({
        type: 'POST',
        url: url,
        data:param,
        success: function(result){
            if(result.status=="100000")  {
                var dlist = result.data;
                if(dlist.length==0){
                    $(".lookMore").hide();
                    $(".noData").show();
                }
                for (var i = 0; i <dlist.length ; i++) {
                    var dl=dlist[i];
                    $('.scoreCount').append('<li >'
                        + '<a href="javascript:; " onclick="goodsDetail(this)" data-id="' + dl.id + '">'
                        + '<img src="' + dl.img + '" />'
                        + '<div class="gms">'
                        + '<p>' + dl.title + '</p>'
                        + '<span>' + dl.score + '<b>积分</b></span>'
                        + '</div>'
                        + '</a>'
                        + '</li>'
                    );
                }
                var num=offset+1;
                $("#pagesnum").val(num);
            }else{
                layer.open({
                    content:result.message,
                    skin:'msg',
                    time:2
                })
            }
        }

    });
}
window.wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: wxJsapiSignature.appId, // 必填，公众号的唯一标识
    timestamp: wxJsapiSignature.timestamp, // 必填，生成签名的时间戳
    nonceStr:wxJsapiSignature.nonceStr, // 必填，生成签名的随机串
    signature: wxJsapiSignature.signature,// 必填，签名
    jsApiList: ['checkJsApi', 'scanQRCode'] // 必填，需要使用的JS接口列表
});

//begin ready
wx.ready(function () {
    ShowQRCode();
    //classAdd();
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    wx.checkJsApi({
        jsApiList: ['scanQRCode'],
        success: function (res) {
            if(res.checkResult.scanQRCode != true){
                alert('抱歉，当前客户端版本不支持扫一扫');
            }
        }, fail: function (res) { //检测getNetworkType该功能失败时处理
            alert('checkJsApi error 调取微信官方接口失败');
        }
    });
    wx.getLocation({
        type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
        success: function (res) {
            var latitude = res.latitude; //纬度
            var longitude = res.longitude; //经度
            $.ajax({
                url:'https://api.map.baidu.com/geocoder/v2/?ak=sHr3E8XZyrWta5QRiDckFgAue8BfzCiY&location=' + latitude + ',' + longitude + '&output=json',    //请求的url地址
                dataType:"jsonp",
                success:function(data){
                    district = data.result.addressComponent.district;
                    $("#areaName").val(district);
                    alert(district);
                    console.log(province);//省
                    console.log(cityname);//市
                    console.log(district);//区
                }
            });
        }
    });

    wx.error(function (res) {
        alert("出错了：" + res.errMsg);
        //这个地方的好处就是wx.config配置错误，会弹出窗口哪里错误，然后根据微信文档查询即可。
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    });
    ////start_document_scanQRCode
    document.querySelector('#scanQRCode').onclick = function () {
        wx.scanQRCode({
            needResult: 1,
            //desc: 'scanQRCode desc',
            scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
                localStorage.clear();
                var resultStr=res.resultStr;
                window.location.href="/wx/shop/tGoodsClass?resultStr="+resultStr;

            },
            error: function(res) {
                if (res.errMsg.indexOf('function_not_exist') > 0) {
                    alert('版本过低请升级')
                }
            }
        });
    };//end_document_scanQRCode

});//end_ready


