package com.fly.test;

public class Test {
	
	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			Integer random =(int) (Math.random()*(999-100+1)+100);
			System.out.println(random);
		}
	}
	  
}
