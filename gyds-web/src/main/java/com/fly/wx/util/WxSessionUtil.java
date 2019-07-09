package com.fly.wx.util;


import org.springframework.stereotype.Component;

import com.fly.domain.MemberUserDO;
import com.fly.utils.SessionUtil;

import javax.servlet.http.HttpServletRequest;


@Component
public class WxSessionUtil extends SessionUtil {

    public static Integer getUserId(){
        return getUser().getId();
    }


    public static void setSellerId(Integer sellerId){
        setSessionAttribute("sellerid",sellerId);
    }

    public static Integer getSellerId(){
        return (Integer) getSessionAttribute("sellerid");
    }

    public static String getOpenid(){
        return getUser().getOpenid();
    }

    public static MemberUserDO getUser(){
        return (MemberUserDO)getSessionAttribute("user");
    }

    public static String getBasePath(){
        HttpServletRequest request=getRequest();
        return request.getScheme()+"://"+request.getServerName()+request.getContextPath()+request.getServletPath();
    }

}
