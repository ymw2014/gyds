package com.fly.utils.xss;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublicUtils {

	
	public static int IdNOToAge(String IdNO){
        int leh = IdNO.length();
        String dates="";
        if (leh == 18) {
            //int se = Integer.valueOf(IdNO.substring(leh - 1)) % 2;
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            System.out.println(df);
            String year=df.format(new Date());
            int u=Integer.parseInt(year)-Integer.parseInt(dates);
            return u;
        }else{
            dates = IdNO.substring(6, 8);
            return Integer.parseInt(dates);
        }

    }
	
	/**
	 * 
	 * @param IdNO
	 * @return 
	 * @return
	 * @throws ParseException 
	 */
	public static Date IdNOToBirth(String IdNO) throws ParseException{
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format1.parse(IdNO.substring(6, 10)+"-"+IdNO.substring(10, 12)+"-"+IdNO.substring(12, 14)); 
		System.out.println(date);
		return date;

    }
	
	public static void main(String[] args) throws ParseException {
		IdNOToBirth("41032519900611107X");
	}

}
