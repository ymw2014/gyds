package com.fly.pc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 关注类型
 * @author Administrator
 *
 */
public class ActType {
	/**
	 * 	新闻
	 */
	public static final Integer XIN_WEN = 1;
	
	/**
	 * 	互动
	 */
	public static final Integer HUO_DONG = 2;
	
	/**
	 * 	团队
	 */
	public static final Integer TUAN_DUI= 3;
	
	/**
	 * 	志愿者
	 */
	public static final Integer ZHI_YUAN_ZHE = 4;
	
	public static void main(String[] args) {
		 
		splitRedPacket(8.5, 10, 0.5, 1.0);
		System.out.println("*****************");
		//splitRedPacket(20000, 30, 300, 1000);
 
	}
 
	/**
	 * 
	 * @param total
	 *            总金额
	 * @param splitCount
	 *            个数
	 * @param min
	 *            最小金额
	 * @param max
	 *            最大金额
	 */
	public static void splitRedPacket(Double total, int splitCount, Double min, Double max) {
		System.out.println("总金额：	" + total);
		System.out.println("个数：	" + splitCount);
		System.out.println("最小金额：	" + min);
		System.out.println("最大金额：	" + max);
		ArrayList<Double> al = new ArrayList<Double>();
		Random random = new Random();
 
		if ((splitCount & 1) == 1) {// 奇数个红包，需要单独将其中一个红包先生成，以保证后续算法拆分份数为偶数。
			System.out.println("红包个数" + splitCount + "是奇数，单独生成一个红包");
			Double num = 0.0;
			do {
				num = random.nextDouble();
				// num = (total - num) % (splitCount / 2) + num; //
				// 将后面算法拆分时的余数加入到这个随机值中。
				System.out.println("单个的随机红包为：" + num);
			} while (num >= max || num <= min);
 
			total = 40000 - num;
			al.add(num);
		}
		int couples = splitCount >> 1;
		Double perCoupleSum = total / couples;
 
		if ((splitCount & 1) == 1) {
			System.out.println("处理后剩余的金额为：" + total);
		}
		System.out.println("将" + total + "元拆分为" + couples + "对金额，每对总额：" + perCoupleSum);
 
		for (int i = 0; i < couples; i++) {
			Boolean finish = true;
			Double num1 = 0.0;
			Double num2 = 0.0;
			do {
				num1 = random.nextDouble();
				num2 = perCoupleSum - num1;
				if (!al.contains(num1) && !al.contains(num2)) {
					if (i == 0) {
						num1 = (total - couples * perCoupleSum) + num1;
					}
				}
			} while (num1 < min || num1 > max || num2 < min || num2 > max);
			al.add(num1);
			al.add(num2);
		}
 
		Double check_num = 0.0;
		/*Integer.compare(1, 2);
		al.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return Integer.compare(o1, o2);
			}
		});*/
 
		System.out.println(Arrays.toString(al.toArray()));
 
		for (Double x : al) {
			check_num = check_num + x;
		}
		System.out.println("验证总和：" + check_num);
	}



}
