package com.fly.count.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class NumberUtils {

	public static String getPercent(Object x,Object total){
		String result="";//接受百分比的值
		double a = Double.valueOf(x.toString());
		double b = Double.valueOf(total.toString());
		double tempresult=0.0;
		if(b!=0.0) {
			tempresult = a/b;
		}else {
			return "0.00%";
		}
		
		//NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法
		//nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
		DecimalFormat df1 = new DecimalFormat("0.00%"); //##.00% 百分比格式，后面不足2位的用0补齐
		result= df1.format(tempresult);
		return result;
	}
	public static String getRatio(Object x,Object total){
		double today = Double.valueOf(x.toString());
		double yesterday = Double.valueOf(total.toString());
		if("0".equals(total.toString())) {
			return "增长100%";
		}
		//计算百分比
		double baiFenBi = (today/yesterday)*100;
		String high = "",low = "";
		if(baiFenBi >= 100){//增长
			double h = baiFenBi - 100;
			BigDecimal c = new BigDecimal(h);
			//这里四舍五入保留两位小数(保留多少位可以自行调整)
			double f1 = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			high = f1 + "%";
		}
		if(baiFenBi < 100){//降低
			double l = 100 - baiFenBi;
			BigDecimal c = new BigDecimal(l);
			double f1 = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			low = f1 + "%";
		}
		if(!"".equals(high)){
			return "增长"+high;
		}
		if(!"".equals(low)){
			return "降低"+low;
		}
			return "0";
	}
	
}
