package com.giant.zzidc.base.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

/***
 * [XXS跨站脚本攻击过滤处理转换器]
 * 
 * @author LiBaozhen
 * @date 2014-9-29 下午12:47:21
 * @company 景安(Giant)网络
 * @version v1.3
 * @see 相关类
 * @since 相关/版本
 */
public class OnRequestWrap extends HttpServletRequestWrapper{
	
	/* XSS跨站脚本攻击转换签 */
	private String[] xssSource = new String[]{"\"", "'", "<", ">","data:", 
			/* 依次是URL编码， HTML10HEX，HTML16HEX，JSunicode，JS16HEX，JS8HEX */
			"data%3A", "&#100;&#97;&#116;&#97;&#58;", "&#x64;&#x61;&#x74;&#x61;&#x3a;", "\\u0064\\u0061\\u0074\\u0061\\u003a", "\\x64\\x61\\x74\\x61\\x3a", "\\144\\141\\164\\141\\72", /*英文冒号*/
			"%3C", "&#60;", "&#x3c;", "\\u003c", "\\x3c", "\\74",/*英文冒号右尖括号*/
			"%3E", "&#62;", "&#x3e;", "\\u003e", "\\x3e", "\\76"}; /*英文冒号左尖括号*/
	/* XSS跨站脚本攻击转换后 */
	private String[] xssTarget = new String[]{"“", "‘", "《", "》","data：",
			/* 依次是URL编码， HTML10HEX，HTML16HEX，JSunicode，JS16HEX，JS8HEX */
			"data%3A", "&#100;&#97;&#116;&#97;&#65306;", "&#x64;&#x61;&#x74;&#x61;&#xff1a;", "\\u0064\\u0061\\u0074\\u0061\\u00ff1a", "\\x64\\x61\\x74\\x61\\xff1a", "\\144\\141\\164\\141\\177432",/* 中文冒号 */
			"%E3%80%8A", "&#12298;", "&#x300a;", "\\u00300a", "\\x300a", "\\30012",/* 中文左尖括号 */
			"%E3%80%8B", "&#12299;", "&#x300b;", "\\u00300b", "\\x300b", "\\30013"};/* 中文右尖括号 */
	
	public OnRequestWrap(HttpServletRequest request) {
		super(request);
	}
	
	/* 格式化替换 */
	private String format(String name){
		return StringUtils.replaceEach(name, xssSource, xssTarget);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map getParameterMap() {
		HashMap<String, String[]> paramMap = (HashMap<String, String[]>) super.getParameterMap();
		paramMap = (HashMap<String, String[]>) paramMap.clone();
		for (java.util.Iterator<Entry<String, String[]>> iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
			Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>) iterator.next();
			String [] values = entry.getValue();
			for (int i = 0; i < values.length; i++) {
				if(values[i] instanceof String){
					values[i] = format(values[i]);
				}
			}
			entry.setValue(values);
		}
		return paramMap; 
	}
		

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if ( values != null ){
			for ( int i = 0; i < values.length; i++ ){
				values[i] = format(values[i]);
			}
		}
		return values;
	}

	public Object getAttribute(String name){
		Object value = super.getAttribute(name);
		if ( value instanceof String ){
			value = format(String.valueOf(value));
		}
		return value;
	}

	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if ( value == null ){
			return null;
		}
		return format(value);
	}
	
}
