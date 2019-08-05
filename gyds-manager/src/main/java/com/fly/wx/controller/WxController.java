package com.fly.wx.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 */
@Controller
public class WxController {
    @Autowired
    private WxMpService wxMpService;
    private Logger logger = LoggerFactory.getLogger(WxController.class);

    @RequestMapping("/wx/accept")
    @ResponseBody
    public void accept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("请求进来了");
        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            String value = request.getParameter(name);
            // out.print(name + "=" + value);

            String log = "name =" + name + "     value =" + value;
            System.out.println(log);
        }

        String signature = request.getParameter("signature");/// 微信加密签名
        String timestamp = request.getParameter("timestamp");/// 时间戳
        String nonce = request.getParameter("nonce"); /// 随机数
        String echostr = request.getParameter("echostr"); // 随机字符串
        PrintWriter out = response.getWriter();

//		name =signature     value =bbca6c0ab694e6ee8d53d88170e7a5b4169687ad
//				name =echostr     value =7875609054712659963
//				name =timestamp     value =1537497131
//				name =nonce     value =869808670



//		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
        System.out.println("echostr="+echostr);
        out.print(echostr);
//		}
        out.close();
        out = null;

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

   


}
ss