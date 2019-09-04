package com.fly.count.utils;

import java.text.DecimalFormat;

public class NumberUtils {

	public String getPercent(int x,int total){
		String result="";//接受百分比的值
		double x_double=x*1.0;
		double tempresult=x/total;
		//NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法
		//nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
		DecimalFormat df1 = new DecimalFormat("0.00%"); //##.00% 百分比格式，后面不足2位的用0补齐
		result= df1.format(tempresult);
		return result;
	}
	
}
