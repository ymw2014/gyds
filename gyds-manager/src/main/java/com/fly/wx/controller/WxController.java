package com.fly.wx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.fly.common.redis.shiro.RedisSessionDAO;
import com.fly.utils.StringUtils;
import com.fly.websocket.controller.WebSocketController;
import com.fly.wx.utils.MessageUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.sf.json.JSONObject;

/**
 *
 */
@Controller
public class WxController {

	private String Token = "omJNpZEhZeHj1ZxFECKkP48B5VFbk1HP";
    @Autowired
    private WxMpService wxMpService;
    private Logger logger = LoggerFactory.getLogger(WxController.class);
    
    @Autowired
    WebSocketController webSocketController;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    RedisSessionDAO sessionDao;
    @RequestMapping("/wx/accept")
    @ResponseBody
    public void accept(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("请求进来了");
        req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		Map<String, String[]> parameterMap = req.getParameterMap();
		System.out.println("requestData:" + JSON.toJSONString(parameterMap));
		logger.info("requestData:" + JSON.toJSONString(parameterMap));
		// 微信加密签名
		String signature = req.getParameter("signature");
		// 时间戳
		String timestamp = req.getParameter("timestamp");
		// 随机数
		String nonce = req.getParameter("nonce");
		// 随机字符串
		String echostr = req.getParameter("echostr");
		System.out.println(
				"signature=" + signature + ";timestamp=" + timestamp + ";nonce=" + nonce + ";echostr=" + echostr);
		logger.info("signature=" + signature + ";timestamp=" + timestamp + ";nonce=" + nonce + ";echostr=" + echostr);
		PrintWriter out = resp.getWriter();
		processRequest(req);
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			System.out.println("success");
			logger.info("success");
			String message = "success";
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				out.println(message);
				if (out != null) {
					out.close();
				}
			}
		}

  }
    
    @RequestMapping("/wx/getCode")
    public String getCode(Model model) {
    	 logger.debug("调用微信接口获取永久带参二维码");
         try {
			WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 30);
			String url = wxMpService.getQrcodeService().qrCodePictureUrl(ticket.getTicket());
			model.addAttribute("code_url", url);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return "/pc/code";
	}
    
    public String processRequest(HttpServletRequest req) {
    	//String message = "success";
    	String respMessage = null;
    	// 把微信返回的xml信息转义成map
    	Map<String, String> map = MessageUtil.parseXml(req);
    	//Map<String, String> map = XmlUtil.xmlToMap(req);
    	System.out.println(JSON.toJSONString(map));
    	boolean isreturn = false;
    	/*if (map.get("MsgType") != null) {
				if ("event".equals(map.get("MsgType"))) {
					logger.info("2.1解析消息内容为：事件推送");
					if ("subscribe".equals(map.get("Event")) || "CLICK".equals(map.get("Event"))) {
						logger.info("2.2用户第一次关注 返回true哦");
						isreturn = true;
					}
				}
			}*/
    	String eventType = map.get("Event");
    	// 关注
    	
    	WxMpUser user;
    	if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
    		String eventKey=map.get("EventKey");
    		try {
    			String token = wxMpService.getAccessToken();
    		} catch (WxErrorException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		try {
    			logger.info("FromUserName************************"+map.get("FromUserName"));
    			user = wxMpService.getUserService().userInfo(map.get("FromUserName"));
    			logger.info(user.getNickname()+"*****"+user.getOpenId());
    			if(StringUtils.isNotBlank(eventKey)){
                    //放到redis缓存中
				user = wxMpService.getUserService().userInfo(map.get("FromUserName"));
				logger.info("qrcode redis key ==> "+eventKey);
				logger.info("qrcode redis key ==> "+user.toString());
				HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
    			opsForHash.put("user",eventKey.replace("qrscene_", ""), user.toString());
    			Hashtable params = new Hashtable();
                params.put("openId", user.getOpenId());
                params.put("location", user.getCity());
                params.put("equipmentType", eventKey.replace("qrscene_", ""));
                logger.info("params ==>************************* "+params);
                JSONObject object = JSONObject.fromObject(params);
    			webSocketController.onMessage(object.toString(), null);
                logger.info("qrcode redis key ==> "+eventKey.replace("qrscene_", ""));
            }
    			
    		} catch (WxErrorException e) {
    			logger.info("用户扫码关注登录失败 ==> "+eventKey.replace("qrscene_", ""));
    			e.printStackTrace();
    		}
    	}
    	// 取消关注
    	if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
    		// TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
    		

    	}
    	// 扫描带参数二维码
    	if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
    		// TODO 处理扫描带参数二维码事件
    		String eventKey=map.get("EventKey");
    		System.out.println("二维码扫描成功了");
    		if(StringUtils.isNotBlank(eventKey)){
    			
    			try {
    				String token = wxMpService.getAccessToken();
				} catch (WxErrorException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
    			logger.info("FromUserName************************"+map.get("FromUserName"));
    			try {
					user = wxMpService.getUserService().userInfo(map.get("FromUserName"));
					logger.info("qrcode redis key ==> "+eventKey);
					logger.info("qrcode redis key ==> "+user.toString());
					HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
	    			opsForHash.put("user", eventKey, user.toString());
					Hashtable params = new Hashtable();
					params.put("openId", user.getOpenId());
                    params.put("location", user.getCity());
                    params.put("equipmentType", eventKey);
                    JSONObject object = JSONObject.fromObject(params);
	    			webSocketController.onMessage(object.toString(), null);

				} catch (WxErrorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		return respMessage;
    	}

    return respMessage;

}


    @Test
    private void sdgsd() {
    	HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
		opsForHash.put("openId", "openId", "aaaaa");
		System.out.println(opsForHash.get("openId", "openId"));
	}
   


}