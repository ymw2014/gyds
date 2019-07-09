function wxConfig(jsApiList) {
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: wxJsapiSignature.appId, // 必填，公众号的唯一标识
        timestamp: wxJsapiSignature.timestamp, // 必填，生成签名的时间戳
        nonceStr: wxJsapiSignature.nonceStr, // 必填，生成签名的随机串
        signature: wxJsapiSignature.signature,// 必填，签名
        jsApiList: jsApiList // 必填，需要使用的JS接口列表
        //['chooseWXPay']
    });
    wx.ready(function(){
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    });
    wx.error(function(res){
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    });
}

//调起支付
function pay(a,backUrl) {
    wx.chooseWXPay({
        appId:a.appId,
        timestamp: a.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
        nonceStr: a.nonceStr, // 支付签名随机串，不长于 32 位
        package: a.packageValue, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
        signType: a.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
        paySign: a.paySign, // 支付签名
        success: function (res) {
// 支付成功后的回调函数
            location.href=backUrl;
        },cancel:function (res) {
        }
    });
}