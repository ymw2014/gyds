package com.fly.index.utils;

public class OrderType {
	/**
	 * 	体现
	 */
	public static final Integer TI_XIAN = 0;
	/**
	 *	充值
	 */
	public static final Integer CHONG_ZHI = 1;
	/**
	 * 	打赏
	 */
	public static final Integer DA_SHANG = 2;
	/**
	 * 	红包
	 */
	public static final Integer HONG_BAO = 3;
	/**
	 * 	广告
	 */
	public static final Integer GUANG_GAO_GOU_MAI = 4;
	/**
	 * 	置顶
	 */
	public static final Integer ZHI_DING = 5;
	/**
	 * 	收入
	 */
	public static final Integer SHOU_RU = 1;
	
	/**
	 * 	支出
	 */
	public static final Integer ZHI_CHU = 2;

	 /**
	  * 	代理商入驻
	  */
	public static final Integer DAI_LI_SHANG = 6;
	 /**
	  * 	建团
	  */
	public static final Integer JIAN_TUAN = 7;
	/**
	 * 	红包返佣
	 */
	public static final Integer HONG_BAO_FAN_YONG =8;
	/**
	 * 	打赏返佣
	 */
	public static final Integer DA_SHANG_FAN_YONG =9;
	/**
	 * 	置顶返佣
	 */
	public static final Integer ZHI_DING_FAN_YONG =10;
	/**
	 * 	广告返佣
	 */
	public static final Integer GUANG_GAO_FAN_YONG =11;
	/***
	 * 	建团返佣
	 */
	public static final Integer JIAN_TUAN_FAN_YONG =12;
	
	/**
	 * 	团队认证
	 */
	public static final Integer TUAN_DUI_REN_ZHENG =13;
	
	/**
	 * 	发布项目
	 */
	public static final Integer FA_BU_XIANG_MU =14;
	/***
	 * 	加入项目
	 */
	public static final Integer JIA_RU_XIANG_MU =15;
	
	/***
	 * 	项目分佣
	 */
	public static final Integer XIANG_MU_FEN_YONG =16;
	
	public static class CommissionType{
        //    1:团队返佣 2:街道办返佣 3:县代理分佣 4.市代理分佣 5.省代理分佣
        public static final String TUAN_DUI_FEN_YONG="团队分佣";
        public static final String DAI_LI_FEN_YONG="代理分佣";
    }

}
