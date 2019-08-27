package com.fly.wx.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.ServerEndpoint;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fly.common.config.WechatOpenProperties;
import com.fly.domain.UserDO;
import com.fly.system.service.UserService;
import com.fly.system.utils.ShiroUtils;
import com.fly.wx.service.WxOpenServiceDemo;
import com.fly.wx.utils.EasyTypeToken;
import com.fly.wx.utils.HttpUtils;

import me.chanjar.weixin.common.error.WxErrorException;
/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Controller
@RequestMapping("/wx")
public class WechatApiController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WxOpenServiceDemo wxOpenServiceDemo;
    
    private String pcUserInfoUrl="https://api.weixin.qq.com/sns/userinfo?";
    
    private String pcAccessTokenUrl="https://api.weixin.qq.com/sns/oauth2/access_token?";
    
    @Autowired
    private WechatOpenProperties properties;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/auth/goto_auth_url_show")
    @ResponseBody
    public String gotoPreAuthUrlShow(){
        return "<a href='goto_auth_url'>go</a>";
    }
    @GetMapping("/auth/goto_auth_url")
    public void gotoPreAuthUrl(HttpServletRequest request, HttpServletResponse response){
        String host = request.getHeader("host");
        String url = "http://"+host+"/api/auth/jump";
        try {
            url = wxOpenServiceDemo.getWxOpenComponentService().getPreAuthUrl(url);
            // 添加来源，解决302跳转来源丢失的问题
            response.addHeader("Referer", "http://"+host);
            response.sendRedirect(url);
        } catch (WxErrorException | IOException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/auth/jump")
    public String jump(@RequestParam("code") String authorizationCode,Model model){
    	logger.info("WeiXinLoginController ==> getPcWXAccessToken(){}");
		Map<String, String> resMap = new HashMap<String, String>();
		StringBuffer target = new StringBuffer();
		target.append(pcAccessTokenUrl).append("appid=").append(properties.getComponentAppId()).append("&secret=").append(properties.getComponentSecret())
				.append("&code=").append(authorizationCode).append("&grant_type=authorization_code");
		logger.info("WeiXinLoginController ==> getPcWXAccessToken(){} target: " + target);
		String responceEntity = HttpUtils.doGet(target.toString());
		JSONObject jSONObject = JSON.parseObject(responceEntity);
		if (jSONObject != null && jSONObject.get("errcode") != null) { // 有错误码
			String errcode = String.valueOf(jSONObject.get("errcode"));
			String errmsg = String.valueOf(jSONObject.get("errmsg"));
			resMap.put("errmsg", errmsg);
			resMap.put("errcode", errcode);
		} else {
/*			String accessToken = String.valueOf(jSONObject.get("access_token"));
			String refreshToken = String.valueOf(jSONObject.get("refresh_token"));
			String openid = String.valueOf(jSONObject.get("openid"));
			String expiresIn = String.valueOf(jSONObject.get("expires_in"));
			String unionid = String.valueOf(jSONObject.get("unionid"));*/
			StringBuffer url = new StringBuffer(pcUserInfoUrl);
	    	url.append("access_token=").append(jSONObject.get("access_token")).append("&").append("openid=").append(jSONObject.get("openid")).append("&")
			.append("lang=zh_CN");
	    	logger.info("WeiXinLoginController ==> getPcWeiXinUserInfo(){} url: " + url);
	    	String response = HttpUtils.doGet(url.toString());
	    	JSONObject json = JSON.parseObject(response);
	    	if (json != null && json.get("errcode") != null) {
				String errcode = String.valueOf(json.get("errcode"));
				String errmsg = String.valueOf(json.get("errmsg"));
				resMap.put("errmsg", errmsg);
				resMap.put("errcode", errcode);
				logger.info("WeiXinLoginController ==> 微信获取用户信息错误: " + resMap);
			} else {
				Map<String, Object> map =new HashMap<>(16);
				map.put("openId", String.valueOf(json.get("openid")));
				List<UserDO> userList = userService.list(map);
				logger.info("WeiXinLoginController ==> 微信获取用户信息: " + json);
				if(userList!=null&&userList.size()>0) {
					UserDO user = userList.get(0);
					Subject subject = SecurityUtils.getSubject();
					logger.info("WeiXinLoginController ==> username: " + user.getUsername());
					EasyTypeToken token = new EasyTypeToken(user.getUsername());
					subject.login(token);
					if(user.getIsBinding()!=1) {
						return "redirect:/pc/personalCenter";
					}
				}else {//新用户登录
					logger.info("WeiXinLoginController ==> resultJson: " + json);
					UserDO user=new UserDO();
					user.setUsername(String.valueOf(json.get("nickname")));
					user.setIsIdentification(0);
					user.setIsManage(0);
					user.setIsBinding(0);
					user.setAccount(new BigDecimal(0));
					user.setNikeName(String.valueOf(json.get("nickname")));
					user.setOpenId(String.valueOf(json.get("openid")));
					user.setHeadImg(String.valueOf(json.get("headimgurl")));
					user.setSex(String.valueOf(json.get("sex")).equals("男")?0:1);
					user.setStatus(1);
					user.setCity(String.valueOf(json.get("city")));
					user.setProvince(String.valueOf(json.get("province")));
					if(userService.saveUser(user)>0) {
						logger.info("WeiXinLoginController ==> resultJson:  用户保存成功"  +user.toString());
					}else {
						logger.info("WeiXinLoginController ==> resultJson:  用户保存失败" );
						return json.toJSONString();
						
					}
					Subject subject = SecurityUtils.getSubject();
					EasyTypeToken token = new EasyTypeToken(user.getUsername());
					subject.login(token);
					return "redirect:/pc/personalCenter";
				}
				
			}
	    	return "redirect:/";
		}
		System.out.println(resMap);
		return "/";
        /*try {
            WxOpenQueryAuthResult queryAuthResult = wxOpenServiceDemo.getWxOpenComponentService().getQueryAuth(authorizationCode);
            logger.info("getQueryAuth", queryAuthResult);
            return queryAuthResult;
        } catch (WxErrorException e) {
            logger.error("gotoPreAuthUrl", e);
            throw new RuntimeException(e);
        }*/
    }
    @GetMapping("/get_authorizer_info")
    @ResponseBody
    public Map<String, String> getAuthorizerInfo(@RequestParam String appId,String accessToken){
    	Map<String, String> resMap = new HashMap<String, String>();
    	StringBuffer url = new StringBuffer(pcUserInfoUrl);
    	url.append("access_token=").append(accessToken).append("&").append("openid=").append(appId).append("&")
		.append("lang=zh_CN");
    	logger.info("WeiXinLoginController ==> getPcWeiXinUserInfo(){} url: " + url);
    	String response = HttpUtils.doGet(url.toString());
    	/*= HttpUtils.getMethod(url.toString(), "zh_CN");*/
    	JSONObject jSONObject = JSON.parseObject(response);
    	if (jSONObject != null && jSONObject.get("errcode") != null) {
			String errcode = String.valueOf(jSONObject.get("errcode"));
			String errmsg = String.valueOf(jSONObject.get("errmsg"));
			resMap.put("errmsg", errmsg);
			resMap.put("errcode", errcode);
		} else {
			String nickname = String.valueOf(jSONObject.get("nickname"));
			String openid = String.valueOf(jSONObject.get("openid"));
			String sex = String.valueOf(jSONObject.get("sex"));
			String province = String.valueOf(jSONObject.get("province"));
			String city = String.valueOf(jSONObject.get("city"));
			String country = String.valueOf(jSONObject.get("country"));
			String headimgurl = String.valueOf(jSONObject.get("headimgurl"));
			String unionid = String.valueOf(jSONObject.get("unionid"));
			resMap.put("nickname", nickname);
			resMap.put("openid", openid);
			resMap.put("sex", sex);
			resMap.put("province", province);
			resMap.put("city", city);
			resMap.put("country", country);
			resMap.put("headimgurl", headimgurl);
			resMap.put("unionid", unionid);
		}
		return resMap;
	}

       /* try {
            return wxOpenServiceDemo.getWxOpenComponentService().getAuthorizerInfo(appId);
        } catch (WxErrorException e) {
            logger.error("getAuthorizerInfo", e);
            throw new RuntimeException(e);
        }*/
    
    
}
