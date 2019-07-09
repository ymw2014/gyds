package com.fly.system.controller;

import com.fly.system.utils.RandomValidateCodeUtil;
import com.fly.system.utils.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;


@RestController
public class VifityCodeController {
	@Autowired
	private Producer producer;


	private Logger logger = LoggerFactory.getLogger(VifityCodeController.class);

	/**
	 * 获取验证码方法一
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/vifityCodeController/getVerify2")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
			response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expire", 0);
			RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
			randomValidateCode.getRandcode(request,response);//输出验证码图片方法
		} catch (Exception e) {
			logger.error("获取验证码失败>>>> ", e);
		}
	}

	/**
	 * 获取goole验证码2
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/vifityCodeController/getVerify")
	public void getVerify2(HttpServletRequest request, HttpServletResponse response)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY,text);
		//request.getSession().setAttribute("vifityCode", text);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);


	}
	
	@RequestMapping(value = "/vifityCodeController/checkVerify", method = RequestMethod.POST, headers = "Accept=application/json")
	public boolean checkVerify(@RequestBody Map<String, Object> requestMap, HttpSession session) {
	 try{
	  //从session中获取随机数
	  String inputStr = requestMap.get("inputStr").toString();
	  String random=(String)session.getAttribute("vifityCode");
	  if (random == null) {
	   return false;
	  }
	  if (random.equals(inputStr)) {
	   return true;
	  } else {
	   return false;
	  }
	 }catch (Exception e){
	  logger.error("验证码校验失败", e);
	  return false;
	 }
	}
}
