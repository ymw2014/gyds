package com.fly.wx.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fly.domain.UserDO;
import com.fly.system.service.UserService;
import com.fly.utils.R;
import com.fly.wx.utils.EasyTypeToken;
import com.fly.wx.utils.HttpUtils;

import me.chanjar.weixin.mp.bean.result.WxMpUser;


@Controller
@RequestMapping("/appwx")
public class APPWxController {

    private static  Logger log = LoggerFactory.getLogger(WxController.class);
     
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    /**
     * app登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/weixinLogin")
    public String weixinLogin(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        // 用户同意授权后，能获取到code
        Map<String, String[]> params = request.getParameterMap();//针对get获取get参数  
        String[] codes = params.get("code");//拿到code的值 
        String code = codes[0];//code  
        String appId = "wx561ae40290380b04";
        String appSecret = "2b01a7d96a1e34d3cecf80be87852d53";
        // 用户同意授权
        if (!"authdeny".equals(code)) {
             // 获取网页授权access_token
        	Map<String,String>  oauth2Token = getOauth2AccessToken(appId, appSecret, code);
        	log.info("****oauth2Token信息："+oauth2Token.toString());
            // 网页授权接口访问凭证
            String accessToken = oauth2Token.get("access_token");
            // 用户标识
            String openId = oauth2Token.get("openid");
            // 获取用户信息
         // 拼接请求地址
            String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
            requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
            // 通过网页授权获取用户信息
            com.alibaba.fastjson.JSONObject json =  JSON.parseObject(HttpUtils.doGet(requestUrl));
            log.info("通过网页授权获取用户信息" + json.toJSONString());
            if (null != json) {
                try {
                	Map<String, Object> map =new HashMap<>(16);
    				map.put("openId", String.valueOf(json.get("openid")));
    				List<UserDO> userList = userService.list(map);
    				log.info("appwxlogController ==> 微信获取用户信息: " + json);
    				if(userList!=null&&userList.size()>0) {
    					UserDO user = userList.get(0);
    					Subject subject = SecurityUtils.getSubject();
    					EasyTypeToken token = new EasyTypeToken(user.getUsername());
    					subject.login(token);
    					if(user.getIsBinding()!=1) {
    						return "redirect:/pc/persion_main";
    					}
    				}else {//新用户登录
    					log.info("appwxlogController ==> resultJson: " + json);
    					UserDO user=new UserDO();
    					user.setUsername(String.valueOf(json.get("nickname")));
    					user.setIsIdentification(0);
    					user.setIsManage(0);
    					user.setIsBinding(0);
    					user.setAccount(new BigDecimal(0));
    					user.setNikeName(String.valueOf(json.get("nickname")));
    					user.setOpenId(String.valueOf(json.get("openid")));
    					user.setHeadImg(String.valueOf(json.get("headimgurl")));
    					user.setSex(Integer.valueOf(String.valueOf(json.get("sex"))));
    					user.setStatus(1);
    					user.setCity(String.valueOf(json.get("city")));
    					user.setProvince(String.valueOf(json.get("province")));
    					if(userService.saveUser(user)>0) {
    						log.info("appwxlogController ==> resultJson:  用户保存成功"  +user.toString());
    					}else {
    						log.info("appwxlogController ==> resultJson:  用户保存失败" );
    						return json.toJSONString();
    						
    					}
    					Subject subject = SecurityUtils.getSubject();
    					EasyTypeToken token = new EasyTypeToken(user.getUsername());
    					subject.login(token);
    					return "redirect:/pc/persion_main";
    				}
    				return "redirect:/";
                } catch (Exception e) {
					/*
					 * int errorCode = json.getInteger("errcode"); String errorMsg =
					 * json.getString("errmsg"); log.error("获取用户信息失败 errcode:{} errmsg:{}",
					 * errorCode, errorMsg);
					 */
                	log.error("获取用户信息失败     " + e.getMessage());
                }
            }

        }
        
        return "redirect:/";
    }  

    
    /**
                   * 获取网页授权凭证
     * 
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public Map<String,String> getOauth2AccessToken(String appId, String appSecret, String code) {
    	Map<String,String> wat = new HashMap<String, String>();
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = JSON.parseObject(HttpUtils.doGet(requestUrl));
        if (null != jsonObject) {
            try {
                wat.put("access_token", jsonObject.getString("access_token"));
                wat.put("openid", jsonObject.getString("openid"));
            } catch (Exception e) {
                wat = null;
                String errorCode = jsonObject.getString("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;
    }
    
    /**
     * URL编码（utf-8）
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * PC微信扫码登录
     * @return 
     */
    @ResponseBody
    @RequestMapping("/QrLogin")
    public R QrLogin(@RequestParam Map<String,Object> params) {
    	R r=new R();
		String openId=params.get("openId")==null?"":params.get("openId").toString();
		if(openId==null) {
			r.put("code", -1);
			r.put("msg", "登录失败");
			return r;
		}
		log.info("登录传入参数****************************************************: " + params.toString());
		List<UserDO> userList = userService.list(params);
		if(userList!=null&&userList.size()>0) {
			UserDO user = userList.get(0);
			Subject subject = SecurityUtils.getSubject();
			log.info("WeiXinLoginController ==> username: " + user.toString());
			EasyTypeToken token = new EasyTypeToken(user.getUsername());
			subject.login(token);
			if(user.getIsBinding()!=1) {
				r.put("code", 1);
				r.put("url", "/pc/personalCenter");
				return r;
			}else {
				r.put("code", 1);
				r.put("url", "/");
				return r;
			}
		}else {//新用户登录
			HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
			String userJson = (String) opsForHash.get("user",params.get("strKey"));
			userJson=userJson.replace("true", "1");
			JSONObject jsonObject = JSON.parseObject(userJson);
			log.info("WeiXinLoginController ==> resultJson: " + userJson);
			WxMpUser wxMpuser = WxMpUser.fromJson(userJson);
			log.info("WeiXinLoginController ==> resultJson: " + wxMpuser);
			UserDO user=new UserDO();
			user.setUsername(wxMpuser.getNickname());
			user.setIsIdentification(0);
			user.setIsManage(0);
			user.setIsBinding(0);
			user.setAccount(new BigDecimal(0));
			user.setNikeName(wxMpuser.getNickname());
			user.setOpenId(jsonObject.getString("openId"));
			user.setHeadImg(jsonObject.getString("headImgUrl"));
			user.setSex(wxMpuser.getSex());
			user.setStatus(1);
			user.setCity(wxMpuser.getCity());
			user.setProvince(wxMpuser.getProvince());
			if(userService.saveUser(user)>0) {
				log.info("WeiXinLoginController ==> resultJson:  用户保存成功"  +user.toString());
			}else {
				log.info("WeiXinLoginController ==> resultJson:  用户保存失败" );
				
			}
			Subject subject = SecurityUtils.getSubject();
			EasyTypeToken token = new EasyTypeToken(user.getUsername());
			subject.login(token);
			r.put("code", 1);
			r.put("url", "/pc/personalCenter");
			return r;
		}

	}
    
    
}