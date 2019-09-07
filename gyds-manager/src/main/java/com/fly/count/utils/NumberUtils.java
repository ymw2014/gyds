package com.fly.count.utils;

import java.text.DecimalFormat;


public class NumberUtils {

	public static String getPercent(Object x,Object total){
		String result="";//接受百分比的值
		double a = Double.valueOf(x.toString());
		double b = Double.valueOf(total.toString());
		double tempresult = a/b;
		//NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法
		//nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
		DecimalFormat df1 = new DecimalFormat("0.00%"); //##.00% 百分比格式，后面不足2位的用0补齐
		result= df1.format(tempresult);
		return result;
	}
	
}
