package com.giant.zzidc.base.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
	/**
	 * 读取指定配置文件的指定项。
	 * 
	 * @param propertiesFileName
	 *            配置文件路径与名称
	 * @param key
	 *            配置项
	 * @return 对应配置项的值
	 */
	public static String readProperty(String propertiesFileName, String key) {
		Properties prop = new Properties();
		InputStream input = null;

		String value = null;
		try {
			input = PropertyUtils.class.getResourceAsStream("/" + propertiesFileName);
			prop.load(input);
			value = prop.getProperty(key);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (null != input) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}
