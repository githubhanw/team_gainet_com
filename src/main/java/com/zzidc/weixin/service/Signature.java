package com.zzidc.weixin.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.giant.zzidc.base.utils.PayConfig;


/**
 * 签名 Date: 2015/07/14 Time: 15:23
 */
public class Signature {
	protected static Logger logger = LoggerFactory.getLogger(Signature.class
			.getClass());

	/**
	 * 签名算法
	 * 
	 * @param o
	 *            要参与签名的数据对象
	 * @param payConfig
	 * @return 签名
	 * @throws IllegalAccessException
	 */
	public static String getSign(Object o, PayConfig payConfig)
			throws IllegalAccessException {
		ArrayList<String> list = new ArrayList<String>();
		Class<?> cls = o.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			if (f.get(o) != null && !f.get(o).toString().equals("")) {
				list.add(f.getName() + "=" + f.get(o) + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		String key = payConfig.getRemark();
		result += "key=" + key;// 密钥
		logger.info("qianmingzifuchuan：" + result);
		// logger.info("Sign Before MD5:" + result);
		result = MD5Wx.MD5Encode(result).toUpperCase();
		// logger.info("Sign Result:" + result);
		return result;
	}

	/**
	 * 
	 * [获取微信支付前面] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-7-4 下午5:32:09 <br>
	 * @param map
	 * @param payConfig
	 * @return <br>
	 */
	public static String getSign(Map<String, Object> map,
			PayConfig payConfig) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != "") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + payConfig.getRemark();//支付用key
		// Util.log("Sign Before MD5:" + result);
		result = MD5Wx.MD5Encode(result).toUpperCase();
		// Util.log("Sign Result:" + result);
		return result;
	}

	/**
	 * 从API返回的XML数据里面重新计算一次签名
	 * 
	 * @param responseString
	 *            API返回的XML数据
	 * @return 新鲜出炉的签名
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws IllegalAccessException
	 */
	public static String getSignFromResponseString(String responseString,
			PayConfig payConfig) throws IOException, SAXException,
			ParserConfigurationException, IllegalAccessException {
		Map<String, Object> map = XMLParser.getMapFromXML(responseString);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		return Signature.getSign(map, payConfig);
	}

	/**
	 * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
	 * 
	 * @param responseString
	 *            API返回的XML数据字符串
	 * @return API签名是否合法
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws IllegalAccessException
	 */
	public static boolean checkIsSignValidFromResponseString(
			Map<String, Object> responseMap, PayConfig payConfig) {
		if (responseMap == null || responseMap.size() < 1
				|| responseMap.get("sign") == null) {
			return false;
		}
		String signFromAPIResponse = responseMap.get("sign").toString();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			return false;
		}
		logger.info("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		responseMap.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		String signForAPIResponse = Signature.getSign(responseMap, payConfig);

		if (!signForAPIResponse.equals(signFromAPIResponse)) {
			// 签名验不过，表示这个API返回的数据有可能已经被篡改了
			logger.info("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
			return false;
		}
		logger.info("恭喜，API返回的数据签名验证通过!!!");
		return true;
	}

	public static boolean checkIsSignValidFromResponseString(
			String responseString, PayConfig payConfig)
			throws ParserConfigurationException, IOException, SAXException,
			IllegalAccessException {
		Map<String, Object> map = XMLParser.getMapFromXML(responseString);
		return checkIsSignValidFromResponseString(map, payConfig);
	}

}
