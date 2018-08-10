/**
 * 
 */
package com.giant.zzidc.base.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** <br>
 * [zzidcProduct] <br>
 * [XiaXiaobing] <br>
 * [2013-12-16 下午1:51:05]
 */
public class PropUtils {
	/**
	 * KEY键名称
	 */
	public static String SET_MEMBER_HREF = "SET_MEMBER_HREF", SET_CAS_HREF = "SET_CAS_HREF",
			SET_ZZHT_HREF = "SET_ZZHT_HREF",SET_SSP_HREF="SET_SSP_HREF",SET_CLOUDHOST_HREF="SET_CLOUDHOST_HREF",
			SET_REST_BW_HREF="SET_REST_BW_HREF",SET_REST_CW_HREF="SET_REST_CW_HREF",SET_REST_VHOST_HREF="SET_REST_VHOST_HREF",SET_IDC_RESTFUL="SET_IDC_RESTFUL",
			SET_STORAGE_RESTFUL="SET_STORAGE_RESTFUL",SET_TANXINGWEB_RESTFUL="SET_TANXINGWEB_RESTFUL",SET_REST_CLOUDDB_HREF="SET_REST_CLOUDDB_HREF",SET_ROOT_PATH="SET_ROOT_PATH",
					SET_REST_YAQ_HREF="SET_REST_YAQ_HREF",CSAFEAPI_RESTFUL_HREF="CSAFEAPI_RESTFUL_HREF",SET_RESTFUL_CAIWU="SET_RESTFUL_CAIWU";
	
	private static Properties prop = new Properties();
	/**
	 * 
	 * [获取properties值] <br>
	 * @author LiBaozhen <br>
	 * @date 2015-11-19 下午12:07:02 <br>
	 * @param key
	 * @return <br>
	 */
	public static String getValue(String key){
		return getPropt().getProperty(key);
	}
	
	/**
	 * 
	 * [直接读取编译后的文件] <br>
	 * 
	 * @author LiBaozhen <br>
	 * @date 2015-11-19 下午12:07:25 <br>
	 * @return <br>
	 */
	public static Properties getPropt() {
		try {
			InputStream in = new FileInputStream(PropUtils.getProjectPath()+"WEB-INF/classes/prop/config.properties");
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	/**
	 * 获得工程路径
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		String path = null;
		String folderPath = PropUtils.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath();
		if (folderPath.indexOf("WEB-INF") > 0) {
			path = folderPath.substring(0, folderPath
					.indexOf("WEB-INF/classes"));
		}
		return path;
	}
	
}
