package com.fly.member.controller;

import com.fly.member.service.MemberService;
import com.github.binarywang.java.emoji.EmojiConverter;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private MemberService memberService;
    

    private EmojiConverter emojiConverter = EmojiConverter.getInstance();

    @RequestMapping("/callback")
    public String callback(String code, String redUrl, HttpSession session) {
        WxMpUser wxMpUser = null;
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        if(wxMpUser==null){
            return "";
        }

       /* MemberUserDO memberUserDO = memberUserService.getByOpenid(wxMpUser.getOpenId());
        if(memberUserDO ==null){
            memberUserDO =new MemberUserDO();
            memberUserDO.setCity(wxMpUser.getCity());
            memberUserDO.setCountry(wxMpUser.getCountry());
            memberUserDO.setHeadimgurl(wxMpUser.getHeadImgUrl());
            memberUserDO.setLanguage(wxMpUser.getLanguage());
            memberUserDO.setNickname(emojiConverter.toHtml(wxMpUser.getNickname()));
            memberUserDO.setOpenid(wxMpUser.getOpenId());
            memberUserDO.setSex(wxMpUser.getSex());
            memberUserDO.setSexdesc(wxMpUser.getSexDesc());
            memberUserDO.setIntegral(0);
            int num = memberUserService.save(memberUserDO);
            if(num>0){//添加新用户抽奖次数信息
                List<SetupDO> setupList = setupService.list(null);
                if(setupList!=null&&setupList.size()>0){
                    SetupDO setup=setupList.get(0);
                    CountDO count=new CountDO();
                    count.setMemberId(memberUserDO.getId());
                    count.setNumber(setup.getPrizeNumberOfDay());
                    countService.save(count);
                }

            }
        }*/
        //session.setAttribute("user",memberUserDO);
        if(redUrl==null||redUrl.trim().equals("")){
            redUrl="/";
        }
        return "redirect:"+redUrl;

    }


}
