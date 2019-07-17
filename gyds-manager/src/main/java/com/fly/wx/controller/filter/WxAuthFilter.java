package com.fly.wx.controller.filter;


import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@Order(value = 2)
//@WebFilter(filterName = "WxAuthFilter", urlPatterns = {"*"})
public class WxAuthFilter implements Filter {
    @Value("${wx.backUrl}")
    private String backUrl;
    @Value("${wx.appid}")
    private String appId;

    @Autowired
    private WxMpService wxMpService;


    private StringBuffer sb;
    @Override
    public void init(FilterConfig filterConfig) {
        sb=new StringBuffer();
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader( "Pragma", "no-cache" );
        res.addHeader( "Cache-Control", "must-revalidate" );
        res.addHeader( "Cache-Control", "no-cache" );
        res.addHeader( "Cache-Control", "no-store" );
        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
        if(path.endsWith("callback")||path .endsWith("accept")||path.endsWith("notify")||path.endsWith(".txt")) {
            chain.doFilter(request, response);
            return;
        }

        //MemberUserDO user=WxSessionUtil.getUser();
        String url=backUrl+"?redUrl="+path;
        String c=wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
        res.sendRedirect(c);
        return;
        /*if(user==null) {
            Map<String, String[]>  parameterMap=req.getParameterMap();
            for(Map.Entry<String,String[]> sub:parameterMap.entrySet()){
                String paramName=sub.getKey();
                String [] paramValues=sub.getValue();
                for(int i=0;i<paramValues.length;i++){
                    sb.append("&"+paramName+"="+paramValues[i]);
                }
            }
            if(parameterMap!=null&&parameterMap.size()!=0){
                path=path+sb.toString().replaceFirst("&","?");
                sb.setLength(0);
            }
            try {
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            chain.doFilter(request, response);
        }*/

    }

    @Override
    public void destroy() {

    }
}
