package com.fly.system.controller;

import com.fly.common.annotation.Log;
import com.fly.common.controller.BaseController;
import com.fly.common.service.FileService;
import com.fly.domain.FileDO;
import com.fly.domain.MenuDO;
import com.fly.domain.Tree;
import com.fly.domain.UserDO;
import com.fly.system.service.MenuService;
import com.fly.system.utils.MD5Utils;
import com.fly.system.utils.ShiroUtils;
import com.fly.utils.Constant;
import com.fly.utils.R;
import com.fly.wx.utils.EasyTypeToken;
import com.google.code.kaptcha.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 马凌冰
 * @date 2019-01-25
 */
@Controller
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;
	@Autowired
	FileService fileService;
	@GetMapping({ "/admin", "" })
	String welcome(Model model) {

		return "redirect:/login";//redirect  重定向
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		//菜单树
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		UserDO userDO=getUser();
		model.addAttribute("menus", menus);
		model.addAttribute("name", userDO.getName());
		FileDO fileDO = fileService.get(userDO.getPicId());
		if(fileDO!=null&&fileDO.getUrl()!=null){
			if(fileService.isExist(fileDO.getUrl())){
				model.addAttribute("picUrl",fileDO.getUrl());
			}else {
				model.addAttribute("picUrl","/img/photo_s.jpg");
			}
		}else {
			model.addAttribute("picUrl","/img/photo_s.jpg");
		}
		model.addAttribute("username", userDO.getUsername());
		if(userDO.getIsManage()==1) {
			return "index_v1";
		}else {
			return "redirect:/";
		}
		
	}

	/**
	 *  登录页面
	 * @return
	 */
	@GetMapping("/login")
	String login() {
		return "pc/login";
	}

	/**
	 * 接口登陆
	 * @param username 用户名
	 * @param password 用户密码
	 * @param verify 验证码
	 * @param request
	 * @return
	 */
	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password, String verify, HttpServletRequest request) {
		//String inputVerify=verify;
		R r=new R();
		password = MD5Utils.encrypt(username, password);
		String katha= ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);//(String)request.getSession().getAttribute("vifityCode");
        if(!katha.equals(verify)){
        	return R.error("验证码不正确");
		}
		try {
			//1、获取subject
			Subject subject = SecurityUtils.getSubject();
			//2、封装用户数据
			EasyTypeToken token = new EasyTypeToken(username,password);
			//UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			//3、执行登录方法
			subject.login(token);
			//UserDO user = (UserDO)subject.getPrincipal();
			if(Constant.ADMIN.equals(username)) {
				r.put("url", "/index");
			}else {
				r.put("url", "/");
			}
			return r;//登录成功
		}catch (UnknownAccountException e){//用户名不存在
			return R.error(e.getMessage());
		} catch (IncorrectCredentialsException e) {//密码错误
			return R.error("用户或密码错误");
		}catch (LockedAccountException e){
			return R.error("账号已被锁定,请联系管理员");
		}catch (AuthenticationException e){
			return R.error("用户验证失败");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

}
