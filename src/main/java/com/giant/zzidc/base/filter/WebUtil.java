package com.giant.zzidc.base.filter;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import java.io.UnsupportedEncodingException;  

import org.apache.commons.codec.binary.Base64;  

public class WebUtil {
	/***
     * 获取URI的路径,如路径为http://www.babasport.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request){     
        return request.getRequestURI();
    }
    /**
     * 获取完整请求路径(含内容路径及请求参数)
     * @param request
     * @return
     */
    public static String getRequestURIWithParam(HttpServletRequest request){     
        return getRequestURI(request) + (request.getQueryString() == null ? "" : "?"+ request.getQueryString());
    }
    /**
     * 添加cookie
     * @param response
     * @param name cookie的名称
     * @param value cookie的值
     * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
     * a==1 不加密 a==2加密
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge,int a) { 
    	if(a==1){
    		Cookie cookie = new Cookie(name, value);
    		cookie.setPath("/");
            if (maxAge>0) cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
    	}else{
    		Cookie cookie = new Cookie(name, encodeBase64(value));
    		cookie.setPath("/");
            if (maxAge>0) cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
    	}
        
        
    }
    /**
     * 获取cookie的值
     * @param request
     * @param name cookie的名称
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
     Map<String, Cookie> cookieMap = WebUtil.readCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie.getValue();
        }else{
            return null;
        }
    }
    public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }
    /**
     * 随机数
     * [描述信息] <br>
     * @author Mr.Li<br>
     * @date 2016-9-13 上午11:54:28 <br>
     * @return <br>
     */
    public static String getUuidRandom() {
		return UUID.randomUUID().toString().replaceAll("-","")
				+ RandomStringUtils
						.random(Math.abs(new Random().nextInt(10) + 1),
								"0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}
    /** 
     * 编码 
     * @param cookieStr 
     * @return 
     */  
    public static String encodeBase64(String cookieStr){  
          
        try {  
            cookieStr = new String(Base64.encodeBase64(cookieStr.getBytes("UTF-8")));  
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return cookieStr;  
    }  
  
    /** 
     * 解码 
     * @param cookieStr 
     * @return 
     */  
    public static String decodeBase64(String cookieStr){  
        try {  
            cookieStr = new String(Base64.decodeBase64(cookieStr.getBytes()), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return cookieStr;  
    }
    /**
     * 
     * [判断时间格式] <br>
     * @author Mr.Li<br>
     * @date 2016-10-8 下午2:28:12 <br>
     * @param str
     * @return <br>
     */
    public static boolean isValidDate(String str) {
    	        boolean convertSuccess=true;
    	     // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
    	         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    	         try {
    	        	// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
    	            format.setLenient(false);
    	            format.parse(str);
    	         } catch (Exception e) {
    	           // e.printStackTrace();
    	 // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
    	            convertSuccess=false;
    	        } 
    	        return convertSuccess;
    	 }
}
