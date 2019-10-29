package com.fly.utils;

/**
 * 常量
 */
public class Constant {
    //系统账户
    public static String ADMIN = "admin";
    //自动去除表前缀
    public static String AUTO_REOMVE_PRE = "true";
    //停止计划任务
    public static String STATUS_RUNNING_STOP = "stop";
    //开启计划任务
    public static String STATUS_RUNNING_START = "start";
    //通知公告阅读状态-未读
    public static String OA_NOTIFY_READ_NO = "0";
    //通知公告阅读状态-已读
    public static int OA_NOTIFY_READ_YES = 1;
    //部门根节点id
    public static Long DEPT_ROOT_ID = 0l;
    //缓存方式
    public static String CACHE_TYPE_REDIS ="redis";

    public static String LOG_ERROR = "error";
    
    //
    
    public static class Project{
    	/**
    	 * 未登录
    	 */
    	public static int WEI_DENG_LU = 0;
    	/**
    	 * 可以承接
    	 */
    	public static int CHENG_JIE = 1;
    	/**
    	 * 以承接
    	 */
    	public static int YI_CHENG_JIE = 2;
    	/**
    	 * 不是团战不能承接
    	 */
    	public static int FEI_TUAN_ZHANG = 3;
    	/**
    	 * 未认证
    	 */
    	public static int WEI_REN_ZHENG = 4;
    	/**
    	 * 已过期
    	 */
    	public static int YI_GUO_QI = 5;
    }
    
}
