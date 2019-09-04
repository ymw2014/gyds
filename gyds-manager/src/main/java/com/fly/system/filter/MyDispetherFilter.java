package com.fly.system.filter;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fly.domain.UserDO;
import com.fly.index.utils.JudgeIsMoblieUtil;
import com.fly.system.utils.ShiroUtils;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

public class MyDispetherFilter extends AccessControlFilter  {
	private static final Logger log= LoggerFactory.getLogger(MyDispetherFilter.class);
	private String backUrl="http://zhgy.61966.com/auth/callback";
	private StringBuffer sb=new StringBuffer();
	@Autowired
    WxMpService wxMpService;
	@Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.token}")
    private String token;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		Subject subject = getSubject(request,response);
		String url = getPathWithinApplication(request);
		log.info("当前用户正在访问的 url => " + url);
		log.info("subject.isPermitted(url);"+subject.isPermitted(url));
		if(JudgeIsMoblieUtil.judgeIsMoblie(req)) {
			if(url.endsWith(".js")||url .endsWith(".css")||url.endsWith(".jpg")||url.endsWith(".png")||url.endsWith(".txt")) {
	            return true;
	        }
			return false;
		}else {
			return true;
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse)response;
		WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId("wx561ae40290380b04"); // 设置微信公众号的appid
        config.setSecret("2b01a7d96a1e34d3cecf80be87852d53"); // 设置微信公众号的app corpSecret
        config.setToken("omJNpZEhZeHj1ZxFECKkP48B5VFbk1HP"); // 设置微信公众号的token
//        config.setAesKey("..."); // 设置微信公众号的EncodingAESKey
        
        WxMpService wxService = new WxMpServiceImpl();// 实际项目中请注意要保持单例，不要在每次请求时构造实例，具体可以参考demo项目
        wxService.setWxMpConfigStorage(config);
		
		
		if(JudgeIsMoblieUtil.judgeIsMoblie(req)) {
       	UserDO user = ShiroUtils.getUser();
		//UserDO user= (UserDO)SecurityUtils.getSubject();
       	String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
       	if(path.endsWith(".js")||path .endsWith(".css")||path.endsWith(".jpg")||path.endsWith(".png")||path.endsWith(".txt")) {
            return true;
        }
       	if(user==null) {
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
               path=path.replace("&", "|");
               log.info("++++++++++++++++++++++++++url=============="+path);
               try {
                   String url=backUrl+"?redUrl="+path;
                   String c=wxService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
                   log.info("++++++++++++++++++++++++++url=============="+url);
                   log.info("++++++++++++++++++++++++++"+c);
                   res.sendRedirect(c);
                   return true;
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }else {
               return true;
           }
       }else {
       	return true;
       }
		return false;
	}

}
