package com.fly.system.shiro;

import com.fly.common.config.ApplicationContextRegister;
import com.fly.domain.UserDO;
import com.fly.system.dao.UserDao;
import com.fly.system.service.MenuService;
import com.fly.system.utils.ShiroUtils;
import com.fly.wx.utils.EasyTypeToken;
import com.fly.wx.utils.LoginType;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义Realm
 * @author
 * @date 2019-01-25
 */
public class UserRealm extends AuthorizingRealm {
/*	@Autowired
	UserDao userMapper;
	@Autowired
	MenuService menuService;*/

	/**
	 * 获取用户的权限
	 * @param arg0
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		//获取用户id
		Long userId = ShiroUtils.getUserId();
		//创建菜单的实例
		MenuService menuService = ApplicationContextRegister.getBean(MenuService.class);
		//调取菜单的方法  根据用户id获取关联的菜单
		Set<String> perms = menuService.listPerms(userId);
		//给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//添加 用户权限
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		EasyTypeToken userToken=(EasyTypeToken)token;
		//获取页面传过来的username
		String username =(String) token.getPrincipal(); //userToken.getUsername();//
		
		Map<String, Object> map = new HashMap<>(16);
		map.put("username", username);
		//获取加密后的密码
		String password =new String((char[]) token.getCredentials());// new String((char[])userToken.getPassword());//

		//拿到userMapper
		//public interface UserDao

		UserDao userMapper = ApplicationContextRegister.getBean(UserDao.class);
		// 查询用户信息
		UserDO user = userMapper.list(map).get(0);

		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		if(!userToken.getType().equals(LoginType.NOPASSWD)) {
			// 密码错误
			if (!password.equals(user.getPassword())) {
				throw new IncorrectCredentialsException("账号或密码不正确");
			}
		}
		// 账号锁定
		if (user.getStatus() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}

}
