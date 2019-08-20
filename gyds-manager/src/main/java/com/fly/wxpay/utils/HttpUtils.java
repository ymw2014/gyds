package com.fly.wxpay.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static Logger logger_ = LoggerFactory.getLogger(HttpUtils.class);
	
	    private final static int CONNECT_TIMEOUT = 5000; // in milliseconds
	    private final static String DEFAULT_ENCODING = "UTF-8";
	    
	    public static String postData(String urlStr, String data){
	        return postData(urlStr, data, null);
	    }
	    
	    public static String postData(String urlStr, String data, String contentType) {
	        BufferedReader reader = null;
	        try {
	            URL url = new URL(urlStr);
	            URLConnection conn = url.openConnection();
	            conn.setDoOutput(true);
	            conn.setConnectTimeout(CONNECT_TIMEOUT);
	            conn.setReadTimeout(CONNECT_TIMEOUT);
	            if(StringUtils.isNotBlank(contentType))
	                conn.setRequestProperty("content-type", contentType);
	            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
	            if(data == null)
	                data = "";
	            writer.write(data); 
	            writer.flush();
	            writer.close();  
	
	            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line);
	                sb.append("\r\n");
	            }
	            return sb.toString();
	        } catch (IOException e) {
	            logger_.error("Error connecting to " + urlStr + ": " + e.getMessage());
	        } finally {
	            try {
	                if (reader != null)
	                    reader.close();
	            } catch (IOException e) {
	            }
	        }
	        return null;
	    }
}

