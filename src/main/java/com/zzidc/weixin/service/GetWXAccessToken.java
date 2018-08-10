package com.zzidc.weixin.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.HttpRequestUtils;
import com.giant.zzidc.base.utils.JSONHelper;
import com.giant.zzidc.base.utils.PropUtils;

public class GetWXAccessToken extends GiantBaseController{
	private static final long serialVersionUID = 1L;
	protected static Logger logger = LoggerFactory.getLogger(GetWXAccessToken.class.getClass());
	/**
	 * 静态缓存Map
	 */
	private static Map<String, Object> accessTokenMap = new HashMap<String, Object>();
	/**
	 * 获取access_token
	 * @return
	 */
	public static String getAccessToken(Map<String, Object> weiXinConfig){
		//如果access_token不存在或者已过期则通过get请求重新获取
		if(null == accessTokenMap||accessTokenMap.size()<=0 || null==accessTokenMap.get("accessToken")
			|| GetWXAccessToken.isTimeout((Date)accessTokenMap.get("udateTime"))){
			logger.info("ACCESS_TOKEN已经失效或不存在，重新获取");
			//通过接口获取access_token并设置
			String result=setAccessToken(weiXinConfig);
			if(null ==result || "".equals(result)){
				logger.error("获取微信ACCESS_TOKEN失败");
				return null;
			}
			//返回access_token
			return accessTokenMap.get("accessToken").toString();
		}else{
			//返回access_token
			return accessTokenMap.get("accessToken").toString();
		}
	}
	/**
	 *获取access_token并放入map
	 * @return
	 */
	private static synchronized String setAccessToken(Map<String, Object> weiXinConfig) {
		try {
			
			String url="https://api.weixin.qq.com/cgi-bin/token";
			if(null == url || "".equals(url)){
				logger.error("获取微信ACCESS_TOKEN错误：获取weixin_gettoken_url失败");
				return null;
			}
			String param="grant_type=client_credential&appid="+weiXinConfig.get("appid")+"&secret="+weiXinConfig.get("sercret");
			String accessTokenJson=HttpRequestUtils.post(url+"?"+param, null,"utf-8");
			if(null == accessTokenJson || "".equals(accessTokenJson)){
				logger.error("获取微信ACCESS_TOKEN错误：请求返回信息为空");
				return null;
			}
			Map<String, Object> tempMap=null;
			tempMap=JSONHelper.parseJSON2Map(accessTokenJson);
			if(null == tempMap.get("access_token") || "".equals(tempMap.get("access_token").toString())){
				logger.error("获取微信ACCESS_TOKEN错误,access_token为空");
				return null;
			}
			accessTokenMap.put("accessToken", tempMap.get("access_token"));
			accessTokenMap.put("udateTime", GetWXAccessToken.getTime());
			return "ok";
		} catch (Exception e) {
			logger.error("获取微信并设置ACCESS_TOKEN异常"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取配置文件值<br>
	 * XiaXiaoBing <br>
	 * 2013-5-27下午7:49:17 <br>
	 * @param str
	 * @return
	 */
	public static String getProStr(String str) {
		return PropUtils.getValue(str);
	}
	/**
	 * 判断时间是否超过11分钟
	 * access_token过期时间为11分钟
	 * @param date
	 * @return
	 * @author Sword.L
	 * @date 2013-3-15
	 */
	private static boolean isTimeout(Date date) {
		Long diff = GetWXAccessToken.getTime().getTime() - date.getTime();
		return (diff / (60 * 1000)) > 11;
	}
	/**
	 * 获取系统时间
	 * 
	 * @return
	 * @author Sword.L
	 * @date 2013-3-15
	 */
	private static Date getTime() {
		// 获取上海时间时区
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("Asia/Shanghai"));
		return calendar.getTime();
	}
}
