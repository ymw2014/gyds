package com.fly.wxpay.controller;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fly.wxpay.utils.HtmlUtils;
import com.github.binarywang.utils.qrcode.MatrixToImageWriter;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import io.netty.channel.ConnectTimeoutException;
import jodd.http.HttpResponse;

@Controller
@RequestMapping("/wxpay")
public class WxPayController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/*
	 * @Value("${wxpay.appid}") private String app_id;
	 * 
	 * @Value("${wxpay.key}") private String wx_pay_key;
	 * 
	 * @Value("${wxpay.mch_id}") private String wx_pay_mch_id;
	 */
	
    @RequestMapping("/pay")
    public String payWeChat(String ids,String content,HttpServletResponse response,HttpServletRequest request, ModelMap model){
    	 logger.info("进入支付方法，payWeChat");
        try {
            content = this.getImageUrl("余额充值", "1", "1", "");
            logger.info("wxpay codeUrl，content" + content);
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
            response.setContentType("image/jpeg");
            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", response.getOutputStream());
         } catch (Exception e) {
            e.printStackTrace();
            logger.warn("订单已支付，请勿重新支付");
         }
        return null;
    }
    
    
	public String getImageUrl(String body,String out_trade_no,String product_id,String total_fee) throws Exception{
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appid", "wx561ae40290380b04"); //appid：每个公众号都有一个appid 
        paramMap.put("body", body); //商品描述 
        paramMap.put("mch_id", "1309497501"); //商户号：开通微信支付后分配 
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());  // 随机字符串
        paramMap.put("notify_url", "http://yqhdb.com/payment/wechat/pay.php"); //支付成功后，回调地址
        paramMap.put("out_trade_no",  out_trade_no);  //商户订单号
        paramMap.put("product_id", product_id); // 商户根据自己业务传递的参数 当trade_type=NATIVE时必填
        paramMap.put("spbill_create_ip", this.localIp()); //本机的Ip
        paramMap.put("total_fee", "10"); //金额必须为整数  单位为分
        paramMap.put("trade_type", "NATIVE"); //交易类型
        paramMap.put("sign", WXPayUtil.generateSignature(paramMap, "fenlegou20160226fenlegou20160226"));//根据微信签名规则，生成签名。随机参数可以在商户后台管理系统中进行设置。
       
        String xmlData = WXPayUtil.mapToXml(paramMap);//把参数转换成XML数据格式
        String codeUrl = getCodeUrl(xmlData);   //获取二维码链接
        return codeUrl;
    }
	
	
	 private String localIp(){
         String ip = null;
         Enumeration allNetInterfaces;
         try {
             allNetInterfaces = NetworkInterface.getNetworkInterfaces();            
             while (allNetInterfaces.hasMoreElements()) {
                 NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                 List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                 for (InterfaceAddress add : InterfaceAddress) {
                     InetAddress Ip = add.getAddress();
                     if (Ip != null && Ip instanceof Inet4Address) {
                         ip = Ip.getHostAddress();
                     }
                 }
             }
         } catch (SocketException e) {
        	 logger.warn("获取本机Ip失败:异常信息:"+e.getMessage());
         }
         return ip;
     }
	 
	 
	 /**
	     * 获取二维码链接
     * @param xmlData
     * @return
     */
    private String getCodeUrl(String xmlData) {
        String resXml = null;
        String code_url = "";
        Map<String, String> map;
        try {
            HtmlUtils.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlData);
            map = WXPayUtil.xmlToMap(resXml);
            Object returnCode = map.get("return_code");
            if("SUCCESS".equals(returnCode)) {
                Object resultCode = map.get("result_code");
                if("SUCCESS".equals(resultCode)) {
                    code_url = map.get("code_url").toString();
                }
            }
        } catch (UnrecoverableKeyException e1) {
        	logger.error(e1.getMessage());
        } catch (KeyManagementException e1) {
        	logger.error(e1.getMessage());
        } catch (KeyStoreException e1) {
        	logger.error(e1.getMessage());
        } catch (NoSuchAlgorithmException e1) {
        	logger.error(e1.getMessage());
        } catch (IOException e1) {
        	logger.error(e1.getMessage());
        }catch (Exception e) {
        	logger.error(e.getMessage());
            return "";
        }
        
        return code_url;
    }
}
