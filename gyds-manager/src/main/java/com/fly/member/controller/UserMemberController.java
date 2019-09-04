package com.fly.member.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fly.domain.UserDO;
import com.fly.system.service.UserService;
import com.fly.wx.utils.EasyTypeToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
@RequestMapping("/auth")
public class UserMemberController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private UserService memberUserService;

    private static  Logger log = LoggerFactory.getLogger(UserMemberController.class);

    @RequestMapping("/callback")
    public String callback(String code, String redUrl, HttpSession session) {
    	log.info("***************************微信自动登录回调方法");
    	log.info("appwxlogController ==> redUrl: 回调链接"  +redUrl);
        WxMpUser wxMpUser = null;
        redUrl=redUrl.replace("|", "&");
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

      /*  if(wxMpUser==null){
            return "";
        }
        */
        Map<String, Object> map =new HashMap<>(16);
		map.put("openId", wxMpUser.getOpenId());
		List<UserDO> userList=memberUserService.list(map);
		if(userList!=null&&userList.size()>0) {
			UserDO user = userList.get(0);
			Subject subject = SecurityUtils.getSubject();
			EasyTypeToken token = new EasyTypeToken(user.getUsername());
			subject.login(token);
			if(redUrl==null||redUrl.trim().equals("")){
	            redUrl="/";
	        }
			return "redirect:"+redUrl;
		}else {//新用户登录
			UserDO user=new UserDO();
			user.setUsername(wxMpUser.getNickname());
			user.setIsIdentification(0);
			user.setIsManage(0);
			user.setIsBinding(0);
			user.setAccount(new BigDecimal(0));
			user.setNikeName(wxMpUser.getNickname());
			user.setOpenId(wxMpUser.getOpenId());
			user.setHeadImg(wxMpUser.getHeadImgUrl());
			user.setSex(wxMpUser.getSex());
			user.setStatus(1);
			user.setCity(wxMpUser.getCity());
			user.setProvince(wxMpUser.getProvince());
			if(memberUserService.saveUser(user)>0) {
				log.info("appwxlogController ==> resultJson:  用户保存成功"  +user.toString());
			}else {
				log.info("appwxlogController ==> resultJson:  用户保存失败" );
			}
			Subject subject = SecurityUtils.getSubject();
			EasyTypeToken token = new EasyTypeToken(user.getUsername());
			subject.login(token);
			if(redUrl==null||redUrl.trim().equals("")){
	            redUrl="/";
	        }
			return "redirect:"+redUrl;
		}
    }


}
