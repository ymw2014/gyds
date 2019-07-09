package com.fly.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class Signature {
    
	/**
	 * 
	 * @param strarray
	 * @return
	 */
	public static String Signaturetik(String[] strarray) {
		Arrays.sort(strarray);//排序

		String str2= StringUtils.join(strarray, "&");
		String sha1 = DigestUtils.sha1Hex(str2);
		sha1=sha1.toLowerCase();//小写
		String sha1mi=sha1+"#l_vle_ll_e%+$^@0608)[";
		String md5str= DigestUtils.md5Hex(sha1mi);
		md5str=md5str.toLowerCase();
		
		return md5str;
	}
	/*public static void main(String[] args) {
		*//*Map<String,String> map=new HashMap<String,String>();
	    String[] valueArray = {"token=1541993249","code=0333ffFi2EgAuH0kcQGi2JlkFi23ffFH"};
		 
		 System.out.println(Signature.Signaturetik(valueArray));
		 String str="e24b37a0c8b1cc442972c3c868c10fb9";
		 System.out.println(str);
		 if(Signature.Signaturetik(valueArray).equals(str)) {
			 System.out.println(true);
		}*//*

		List<String> list = new ArrayList <String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		List <String> subList = list.subList(1, 1);
		System.out.println("subList: " + subList); // subList: []
	}*/
	
}
