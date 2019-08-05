package com.fly.system.utils;

import com.fly.domain.UserDO;
import com.fly.exception.utils.RRException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * @author
 * @date 2019-01-25
 * 权限工具类
 */

public class ShiroUtils {
    @Autowired
    private static SessionDAO sessionDAO;

    /**
     * 获取subject
     * @return
     */
    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    
    /**
     *  	切换身份，登录后，动态更改subject的用户属性
     * @param userInfo
     */
    public static void setUser(UserDO userDO) {
    	Subject subject = SecurityUtils.getSubject();
    	PrincipalCollection principalCollection = subject.getPrincipals(); 
    	String realmName = principalCollection.getRealmNames().iterator().next();
    	PrincipalCollection newPrincipalCollection = 
    			new SimplePrincipalCollection(userDO, realmName);
    	subject.runAs(newPrincipalCollection);
    }



    /**
     * 获取去用户信息
     * @return
     */
    public static UserDO getUser() {
        Object object = getSubjct().getPrincipal();
        return (UserDO)object;
    }

    /**
     * 获取用户id
     * @return
     */
    public static Long getUserId() {
        return getUser().getUserId();
    }

    /**
     * 用户登出
     */
    public static void logout() {
        getSubjct().logout();
    }

    public static List<Principal> getPrinciples() {
        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        return principals;
    }

    /**
     * 设置session
     * @param key
     * @param value
     */
    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取session 值
     * @param key
     * @return
     */
    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }
    /**
     * 根据key 获取验证码
     * @param key
     * @return
     */
    public static String getKaptcha(String key) {
        Object kaptcha = getSessionAttribute(key);
        if(kaptcha == null){
            throw new RRException("验证码已失效");
        }
        getSession().removeAttribute(key);
        return kaptcha.toString();
    }


}
