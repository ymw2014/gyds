package com.fly.wxpay.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/wxshare/")
public class WXShare {

	private final Logger logger = LoggerFactory.getLogger(getClass());


	@RequestMapping(value = "/getSignature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> WeixinController(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    Map<String,String> ret = new HashMap<String,String>();
	    //获取前台传来的三个参数
	    //String url = "http://zhgy.61966.com";
	    String url = request.getParameter("url");
	    String appId = "wx561ae40290380b04";
	    String appSecret = "2b01a7d96a1e34d3cecf80be87852d53";
	    
	    logger.info("url "+url);
	     
	    String accessToken =  getAccessToken(appId,appSecret);
	    String ticket = getTicket(accessToken);    // 获取ticket
	    Map<String, String> sign = sign(ticket,url); //获取签名
	    logger.info("accessToken "+accessToken);
	    logger.info("ticket "+ticket);
	    logger.info("signature "+sign.get("signature"));
	    logger.info("timestamp "+sign.get("timestamp"));
	    ret.put("nonceStr", sign.get("nonceStr"));
	    ret.put("timestamp", sign.get("timestamp"));
	    ret.put("signature", sign.get("signature"));
	    ret.put("appId", appId);
	    ret.put("url", url);
	    return ret;
	}
	
	
	   // 获取token
	private String getAccessToken(String appId,String secret) {
	    String access_token = "";
	     String grant_type = "client_credential";// 获取access_token填写client_credential
	    // 这个url链接地址和参数皆不能变
	        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + appId + "&secret="
	               + secret; // 访问链接

	        try {
	            URL urlGet = new URL(url);
	            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
	            http.setRequestMethod("GET"); // 必须是get方式请求
	            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	            http.setDoOutput(true);
	            http.setDoInput(true);
	            /*
	              * System.setProperty("sun.net.client.defaultConnectTimeout",
	             * "30000");// 连接超时30秒
	             * System.setProperty("sun.net.client.defaultReadTimeout", "30000");
	             * // 读取超时30秒
	             */
	           http.connect();
	             InputStream is = http.getInputStream();
	            int size = is.available();
	            byte[] jsonBytes = new byte[size];
	            is.read(jsonBytes);
	             String message = new String(jsonBytes, "UTF-8");
	            JSONObject demoJson = JSONObject.fromObject(message);
	            logger.info("getAccessToken :   "+demoJson.toString());
	           access_token = demoJson.getString("access_token");
	            is.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return access_token;
	}
	    
	
	// 获取ticket
    private String getTicket(String access_token) {
      String ticket = null;
      String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";// 这个url链接和参数不能变
      try {
           URL urlGet = new URL(url);
           HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
           http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
           http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
             http.connect();
           InputStream is = http.getInputStream();
             int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
             JSONObject demoJson = JSONObject.fromObject(message);
             logger.info("getTicket :   "+demoJson.toString());
           ticket = demoJson.getString("ticket");
            is.close();
      } catch (Exception e) {
         e.printStackTrace();
     }
     return ticket;
    }
	
	
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
         String string1;
         String signature = "";
         String timestamp = Long.toString(System.currentTimeMillis() / 1000);
         String nonce_str = WXPayUtil.generateNonceStr();
          //注意这里参数名必须全部小写，且必须有序
           string1 = "jsapi_ticket=" + jsapi_ticket +
                    "&noncestr=" + nonce_str +
                    "&timestamp=" + timestamp +
                    "&url=" + url;
         System.out.println( "string1 : " + string1);
        // jsapi_ticket=kgt8ON7yVITDhtdwci0qeSqpOyec2QSkrVg1_FRuvE0we1uRAVH_PciRvkueIVMoAFy5UOAwGq7SxWzOCvLIGA&noncestr=a7e126d630b24d089d05622c23c1422c&timestamp=1566437882&url=http://zhgy.61966.com
         //jsapi_ticket=kgt8ON7yVITDhtdwci0qeSqpOyec2QSkrVg1_FRuvE0we1uRAVH_PciRvkueIVMoAFy5UOAwGq7SxWzOCvLIGA&noncestr=a7e126d630b24d089d05622c23c1422c&timestamp=1566437973&url=http://zhgy.61966.com
         try
          {
              MessageDigest crypt = MessageDigest.getInstance("SHA-1");
               crypt.reset();
              crypt.update(string1.getBytes("UTF-8"));
             signature = byteToHex(crypt.digest());
          }
         catch (NoSuchAlgorithmException e)
          {
              e.printStackTrace();
         }
          catch (UnsupportedEncodingException e)
         {
               e.printStackTrace();
          }

          ret.put("url", url);
           ret.put("jsapi_ticket", jsapi_ticket);
          ret.put("nonceStr", nonce_str);
          ret.put("timestamp", timestamp);
          ret.put("signature", signature);

          return ret;
       }
      
      // 生成签名
       private static String byteToHex(final byte[] hash) {
           Formatter formatter = new Formatter();
          for (byte b : hash)
           {
               formatter.format("%02x", b);
           }
           String result = formatter.toString();
          formatter.close();
           return result;
      }
}
