package com.giant.zzidc.base.utils;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

/**
 * [工具类]
 * 
 * @author Mr.Liboary
 * @date 2017-6-21 上午11:03:40
 * @company
 * @version 1.0
 * @copyright copyright (c) 2017
 */
public class GiantUtil {

	private static Logger _logger = Logger.getLogger(GiantUtil.class);// log4j记录日志

	public final static int changeLinuxPwdPort = 21567;

	/**
	 * 
	 * 根据下标获取指定字符串
	 * 
	 * @author l0uj1e,2013-01-29 17:30
	 * @param splitStr
	 *            要截取的字符串
	 * @param splitChare
	 *            分隔符(不可为.)
	 * @param index
	 *            第几个,从1开始
	 * @return String
	 */
	public static String splitStrByChare(String splitStr, String splitChare, int index) {
		// 1.分割字符串根据指定分隔符
		String[] strs = splitStr.split(splitChare);
		// 2.获取指定位置上的字符串
		if (index > 0 && index <= strs.length) {
			return strs[index - 1];
		} else {
			return null;
		}
	}

	/**
	 * 
	 * [获取调用该方法的类名及方法名称
	 * CN:ClassName,MN:MethodName,LN:LineNumber,SE:StrackTraceException] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午8:58:30 <br>
	 * @return 类及方法名字符串String<br>
	 */
	public static String nameClassAndMethod() {
		try {
			// 1.存放返回信息
			StringBuffer stringBuffer = new StringBuffer();
			// 2.获取堆栈信息
			StackTraceElement[] itemStackTraces = new Throwable().getStackTrace();
			// 3.遍历堆栈信息
			if (itemStackTraces == null || itemStackTraces.length <= 1) {
				stringBuffer.append("UNNONE");
			} else {
				// 4.获取调用方的信息
				stringBuffer.append("CN:[" + itemStackTraces[1].getFileName() + "];MN:[" + itemStackTraces[1].getMethodName() + "];LN:["
						+ itemStackTraces[1].getLineNumber() + "];");
			}
			// 5.返回信息
			return stringBuffer.toString();
		} catch (Exception e) {
			// 5.异常,返回信息
			_logger.warn("获取类,方法名称出现异常:[" + e.getMessage() + "]");
			return "STE:[" + e.getMessage() + "]";
		}
	}

	/**
	 * 
	 * [获取调用该方法的类名及方法名称,不小于index下标的记录
	 * CN:ClassName,MN:MethodName,LN:LineNumber,SE:StrackTraceException] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午8:58:51 <br>
	 * @param index
	 *            堆栈数组的下标,0:是nameClassAndMethod,1:0的直接调用方,2:0的间接调用方,.....[不合法时为1
	 *            ]
	 * @return <br>
	 */
	public static String nameClassAndMethod(int index) {
		try {
			// 处理参数
			if (index < 0) {
				index = 1;
			}
			// 1.存放返回信息
			StringBuffer stringBuffer = new StringBuffer();
			// 2.获取堆栈信息
			StackTraceElement[] itemStackTraces = new Throwable().getStackTrace();
			// 3.遍历堆栈信息
			if (itemStackTraces == null || itemStackTraces.length < index) {
				stringBuffer.append("UNNONE");
			} else {
				// 4.获取调用方的信息
				for (int i = index; i < itemStackTraces.length; i++) {
					stringBuffer.append("index[" + i + "];CN:[" + itemStackTraces[i].getFileName() + "];MN:["
							+ itemStackTraces[i].getMethodName() + "];LN:[" + itemStackTraces[i].getLineNumber() + "];");
				}
			}
			// 5.返回信息
			return stringBuffer.toString();
		} catch (Exception e) {
			// 5.异常,返回信息
			_logger.warn("获取类,方法名称出现异常:[" + e.getMessage() + "]");
			return "STE:[" + e.getMessage() + "]";
		}
	}

	/**
	 * 
	 * [字符串过滤：防XSS] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午8:59:38 <br>
	 * @param str
	 *            过滤的字符串
	 * @return <br>
	 */
	public static String filterXSS(String str) {
		if (str == null || "".equals(str = str.trim())) {
			return "";
		}
		try {
			/* 把XSS攻击脚本转义成HTML代码，可以有效的防止XSS攻击 */
			return StringEscapeUtils.escapeHtml4(str);
		} catch (Exception e) {
			_logger.warn(nameClassAndMethod(1) + "过滤字符串:[" + str + "];出现异常E:[" + e.getMessage() + "]");
			return "";
		}
	}

	/**
	 * 
	 * [防止XSS跨脚本攻击（替换，根据实际情况调整）] <br>
	 * 
	 * @deprecated 请查看filterXSS方法
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午10:00:56 <br>
	 * @param value
	 *            要过滤的字符串
	 * @return <br>
	 */
	@Deprecated
	public static String stripXSSAndSql(String value) {
		if (value != null) {
			/*
			 * NOTE: 是强烈建议使用ESAPI类库代替下面代码，避免XSS攻击
			 * 例如：value = ESAPI.encoder().canonicalize(value);
			 * 避免null字符串
			 * value = value.replaceAll("", "");
			 * 避免脚本标记之间的任何内容
			 */ 
			Pattern scriptPattern = Pattern
					.compile(
							"<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>",
							Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// 删除所有的src及其内容
			scriptPattern = Pattern.compile(
					"src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			/* 删除所有的</script>标签 */
			scriptPattern = Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			/* 删除所有的<script ...>标签 */
			scriptPattern = Pattern.compile("<[\r\n| | ]*script(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			/* 避免 eval(...)表达式 */
			scriptPattern = Pattern.compile("eval\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			/* 避免 e-xpression(...)表达式 */
			scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			/* 避免javascript:...表达式 */
			scriptPattern = Pattern.compile(
					"javascript[\r\n| | ]*:[\r\n| | ]*",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			/* 避免vbscript:...表达式 */
			scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*",
					Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			/* 避免onload=表达式 */
			scriptPattern = Pattern.compile("onload(.*?)=",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
							| Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}

	/**
	 * 
	 * [过滤URL中的字符] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:56:04 <br>
	 * @param sb
	 *            构建一个字符串
	 * @param s
	 *            字符串
	 * @param index
	 *            索引 <br>
	 */
	public static void processUrlEncoder(StringBuilder sb, String s, int index) {
		if (s.length() >= index + 2) {
			if (s.charAt(index + 1) == '3'
					&& (s.charAt(index + 2) == 'c' || s.charAt(index + 2) == 'C')) {
				// %3c, %3C
				// HTML代码转义
				sb.append('＜');
				return;
			}
			if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '0') {
				// %3c (0x3c=60)
				sb.append('＜');
				return;
			}
			if (s.charAt(index + 1) == '3'
					&& (s.charAt(index + 2) == 'e' || s.charAt(index + 2) == 'E')) {
				// %3e, %3E
				sb.append('＞');
				return;
			}
			if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '2') {
				// %3e (0x3e=62)
				sb.append('＞');
				return;
			}
		}
		sb.append(s.charAt(index));
	}

	/**
	 * 
	 * [判断对象为空返回TRUE] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-6 上午9:18:54 <br>
	 * @param o
	 *            对象
	 * @return true:空字符串 false:非空字符串<br>
	 */
	public static boolean isEmpty(Object o) {
		return isEmpty(String.valueOf(o));
	}

	/**
	 * 
	 * [判断对象不为空返回TRUE] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-6 上午9:19:40 <br>
	 * @param s
	 *            字符串
	 * @return true:空字符串 false:非空字符串<br>
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	/**
	 * 
	 * [是否为空字符串，包括：null/空白字符] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:09:30 <br>
	 * @param str
	 *            字符串
	 * @return true:空字符串 false:非空字符串<br>
	 */
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}

	/**
	 * 
	 * [过滤Map,防XSS] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:09:07 <br>
	 * @param map
	 *            查询条件
	 * @return null（条件为null 或 empty） 或 Map&lt;String, String&gt;<br>
	 */
	public final static Map<String, String> filterMap(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> result = new HashMap<String, String>();
		for (Map.Entry<String, String> en : map.entrySet()) {
			result.put(en.getKey(), filterXSS(en.getValue()));
		}
		return result;
	}

	/**
	 * 
	 * [将一个对象转换成int] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:08:42 <br>
	 * @param obj
	 *            一个长数字或其他对象
	 * @param initVal
	 *            不合法返回该值
	 * @return int或initVal<br>
	 */
	public static int intOf(Object obj, int initVal) {
		// 1.判断参数是否合法
		if (obj == null || "".equals(obj)) {
			return initVal;
		} else {
			try {
				return Integer.parseInt(obj.toString());
			} catch (Exception e) {
				_logger.warn("将对象转换成整型出现异常:[obj:" + obj + "],E:[" + e.getMessage() + "]");
				return initVal;
			}
		}
	}

	/**
	 * 
	 * [将一个对象转换成long] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:04:54 <br>
	 * @param obj
	 *            一个长数字或其他对象
	 * @param initVal
	 *            不合法返回该值
	 * @return long或initVal<br>
	 */
	public static long longOf(Object obj, long initVal) {
		/* 1.判断参数是否合法 */
		if (obj == null || "".equals(obj)) {
			return initVal;
		} else {
			try {
				return Long.parseLong(obj.toString());
			} catch (Exception e) {
				_logger.warn("将对象转换成长整型出现异常:[obj:" + obj + "],E:[" + e.getMessage() + "]");
				return initVal;
			}
		}
	}

	/**
	 * 
	 * [将一个对象转换成double] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:04:14 <br>
	 * @param obj
	 *            一个长数字或其他对象
	 * @param initVal
	 *            不合法返回该值
	 * @return double或initVal<br>
	 */
	public static double doubleOf(Object obj, double initVal) {
		/* 1.判断参数是否合法 */
		if (obj == null || "".equals(obj)) {
			return initVal;
		} else {
			try {
				return Double.parseDouble(obj.toString());
			} catch (Exception e) {
				_logger.warn("将对象转换成double类型出现异常:[obj:" + obj + "],E:["
						+ e.getMessage() + "]");
				return initVal;
			}
		}
	}

	/**
	 * 
	 * [将一个对象转换成float] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:03:53 <br>
	 * @param obj
	 *            一个长数字或其他对象
	 * @param initVal
	 *            不合法返回该值
	 * @return float或者initVal<br>
	 */
	public static float floatOf(Object obj, float initVal) {
		// 1.判断参数是否合法
		if (obj == null || "".equals(obj)) {
			return initVal;
		} else {
			try {
				return Float.parseFloat(obj.toString());
			} catch (Exception e) {
				_logger.warn("将对象转换成float类型出现异常:[obj:" + obj + "],E:[" + e.getMessage() + "]");
				return initVal;
			}
		}
	}

	/***
	 * 
	 * [将一个对象转换成字符串] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:03:25 <br>
	 * @param obj
	 *            一个字符串或其他对象
	 * @return 字符串<br>
	 */
	public static String stringOf(Object obj) {
		if (obj == null || "".equals(obj)) {
			return "";
		} else {
			try {
				return URLDecoder.decode(String.valueOf(obj), "utf-8");
			} catch (Exception e) {
				return "";
			}
		}
	}

	/**
	 * 
	 * [将一个对象转换成Date数据库格式] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:02:52 <br>
	 * @param obj
	 *            一个符合转换的字符串
	 * @return 字符串yyyy-MM-dd HH:mm:ss<br>
	 */
	public static String dateOf(Object obj) {
		if (obj == null || "".equals(obj)) {
			return new SimpleDateFormat().format("00-00-00 00:00:00");
		} else {
			try {
				return new SimpleDateFormat().format(obj);
			} catch (Exception e) {
				return new SimpleDateFormat().format("00-00-00 00:00:00");
			}
		}
	}

	/**
	 * 
	 * [创建一个随机密码] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:02:40 <br>
	 * @return <br>
	 */
	public static String createRandomPassword() {
		Random random = new Random();
		int passwordLength = 0;
		String password = "qazxswedcvfrtgbnhyujmASDFGHJQWE13570!@#$RTYUIOBVCXkiolp";
		String pw = "";
		do {
			passwordLength = random.nextInt(12);
		} while (passwordLength <= 8);
		for (int i = 0; i < passwordLength; i++) {
			pw += password.charAt(random.nextInt(password.length()));
		}
		return pw;
	}

	/**
	 * 
	 * [修改Linux系统密码] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:02:00 <br>
	 * @param ip
	 *            服务器IP地址
	 * @param port
	 *            端口号
	 * @param userName
	 *            系统用户名称
	 * @param passwd
	 *            系统密码
	 * @return <br>
	 */
	public static boolean changeLinuxPwd(String ip, int port, String userName, String passwd) {
		try {
			Socket telnetSocket = new Socket(ip, port);
			OutputStream os = telnetSocket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			String echoString = "echo -e \"" + passwd + "\\n" + passwd + "\\n\" | passwd " + userName;
			dos.write(echoString.getBytes());
			dos.flush();
			dos.close();
			telnetSocket.close();
			return true;
		} catch (Exception e) {
			_logger.warn(e.getMessage());
			Socket telnetSocket;
			try {
				Thread.sleep(10000);
				telnetSocket = new Socket(ip, port);
				OutputStream os = telnetSocket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				String echoString = "echo -e \"" + passwd + "\\n" + passwd + "\\n\" | passwd " + userName;
				dos.write(echoString.getBytes());
				dos.flush();
				dos.close();
				telnetSocket.close();
				return true;
			} catch (Exception e1) {
				return false;
			}
		}
	}


	/**
	 * 
	 * [判断正则表达式与字符串是否匹配] <br>
	 * 
	 * @author Mr.Liboary <br>
	 * @date 2017-7-19 上午9:01:42 <br>
	 * @param reg
	 *            正则
	 * @param str
	 *            字符串
	 * @return <br>
	 */
	public static boolean regexMatches(String reg, String str) {
		boolean flag = false;
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 方法描述：分页查询条件防注入过滤 创建人：XueHaotian 创建时间：2012-12-25 下午01:04:54
	 * 
	 * @param map
	 *            条件
	 * @return Map<String,String>
	 */
	public final static Map<String, String> filterSQLMap(Map<String, String> map) {
		if (map == null) {
			return null;
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		Set<Entry<String, String>> set = map.entrySet();
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			resultMap.put(entry.getKey(), filterStr(entry.getValue()));
		}
		return resultMap;
	}
	
	/**
	 * 字符串过滤：防SQL注入及XSS<br>
	 * 过滤内容：<>'"();%&~^及两端的空字符<br>
	 * 
	 * @param str
	 *            要过滤的字符串
	 * @return String
	 */
	public static String filterStr(String str) {
		if (str == null || "".equals(str = str.trim()))
			return "";

		try {
			return str.replaceAll("<|>|'|\"|;|/|%|~|\\^", "");
		} catch (Exception e) {
			_logger.warn(nameClassAndMethod(1) + "过滤字符串[" + str + "]出现异常："
					+ e.toString());
			return "";
		}
	}

}
