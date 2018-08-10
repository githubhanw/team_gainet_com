package com.giant.zzidc.base.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zzidc.weixin.service.GetWXAccessToken;


/**
 * 微信工具类
 * @author Administrator
 *
 */
public class WechatUtil {
	private static Logger log = Logger.getLogger(WechatUtil.class);
	private static final String WECHAT_AUTHO_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";//微信autho2.0权限
	private static final String WECHAT_AUTHO_REFRESHTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";//?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
	private static final String WECHAT_AUTHO_CHECKTOKEN_URL = "https://api.weixin.qq.com/sns/auth";//?access_token=ACCESS_TOKEN&openid=OPENID
	private static final String WECHAT_API_COMPONENT_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";//?access_token=ACCESS_TOKEN&openid=OPENID
	private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	private static final String WECHAT_AUTHO_USERINFO = "https://api.weixin.qq.com/sns/userinfo";
	private static final String WECHAT_AUTHO_SENDMSG = "https://api.weixin.qq.com/cgi-bin/message/template/send";
	/**
	 * 获取关注者微信推送信息[通过code获取access_token的接口]
	 * @return Map<String, Object> 返回结果
	 */
	public static Map<String, Object> getWechatAutho(String appid, String sercret, String code){
		String content="appid="+appid+"&secret="+sercret+"&grant_type=authorization_code&code="+code;
		String result=HttpRequestUtils.sendPost(WECHAT_AUTHO_URL, content);
		log.info("通过code获取access_token的接口："+result);
		return JSONHelper.parseJSON2Map(result);
	}
	
	/**
	 * 获取关注者微信推送信息[通过openid和access_token获取userinfo的接口]
	 * @return Map<String, Object> 返回结果
	 */
	public static Map<String, Object> getWechatUserInfo(String openid, String access_token){
		String content="access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		String result=HttpRequestUtils.sendPost(WECHAT_AUTHO_USERINFO, content);
		log.info("通过openid和access_token获取userinfo的接口："+result);
		return JSONHelper.parseJSON2Map(result);
	}
	
	public static Map<String, Object> getWechatRefreshToken(String appid, String refresh_token){
		String content="appid="+appid+"&grant_type=refresh_token&refresh_token="+refresh_token;
		String result=HttpRequestUtils.sendPost(WECHAT_AUTHO_REFRESHTOKEN_URL, content);
		log.info("通过refresh_token重新获取access_token的接口："+result);
		return JSONHelper.parseJSON2Map(result);
		/*
		 * {"access_token":"ACCESS_TOKEN","expires_in":7200,"refresh_token":"REFRESH_TOKEN","openid":"OPENID","scope":"SCOPE"}
		 * {"errcode":40030,"errmsg":"invalid refresh_token"}
		 */
	}
	
	public static Map<String, Object> getWechatCheckToken(String openid, String access_token){
		String content="openid="+openid+"&access_token="+access_token;
		String result=HttpRequestUtils.sendPost(WECHAT_AUTHO_CHECKTOKEN_URL, content);
		log.info("检验授权凭证（access_token）是否有效接口："+result);
		return JSONHelper.parseJSON2Map(result);
		/*
		 * {"errcode":0,"errmsg":"ok"}
		 * {"errcode":40003,"errmsg":"invalid openid"}
		 */
	}
	/**
	 * 公众号获取授权凭证（access_token）
	 * @author xiaxiaobing <br>
	 * @date 2015-12-10 下午8:48:34 <br>
	 * @param openid 无用
	 * @param weiXinConfig
	 * @return
	 */
	public static String getAccessToken(String openid, Map<String, Object> weiXinConfig){
		String accessToken = GetWXAccessToken.getAccessToken(weiXinConfig);
		log.info("公众号获取授权凭证（access_token）：" + accessToken);
		return accessToken;
		/*
		 * {"errcode":0,"errmsg":"ok"}
		 * {"errcode":40003,"errmsg":"invalid openid"}
		 */
	}
	
	/**
	 * 生成签名之前必须先了解一下jsapi_ticket，
	 * jsapi_ticket是公众号用于调用微信JS接口的临时票据。正常情况下，jsapi_ticket的有效期为7200秒;
	 * 通过access_token来获取。由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限;
	 * 影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket 。
	 */
	public static Map<String, Object> getJsapi_ticket(String trade_type, String access_token){
		//"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		String content="access_token="+access_token+"&type="+trade_type;
		String result=HttpRequestUtils.sendPost(JSAPI_TICKET_URL, content);
		log.info("检验授权凭证（access_token）是否有效接口："+result);
		return JSONHelper.parseJSON2Map(result);
	}
	
	//************************以下是[代公众号]调用接口APIopen.weixin.qq.com::API:****/
	/** 
	 * DOC:https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318587&lang=zh_CN
	//获取第三方平台令牌（component_access_token），增加了component_verify_ticket参数。
	//component_verify_ticket由公众平台每隔10分钟，持续推送给第三方平台方（在创建公众号第三方平台审核通过后，才会开始推送）。
	 * 
	 */
	public static Map<String, Object> getWechatComponent_verify_ticket(String openid, String access_token){
		String content="openid="+openid+"&access_token="+access_token;
		String result=HttpRequestUtils.sendPost(WECHAT_API_COMPONENT_TOKEN_URL, content);
		log.info("获取第三方平台access_token："+result);
		return JSONHelper.parseJSON2Map(result);
		/* "{component_access_token":"61W3mEpU66027wgNZ_MhGHNQDHnFATkDa9-2llqrMBjUwxRSNPbVsMmyD-yq8wZETSoE5NQgecigDrSHkPtIYA",
		 * "expires_in":7200}
		 */
	}
	
	/**
	 * 获取预授权码 该API用于获取预授权码。预授权码用于公众号授权时的第三方平台方安全验证。
	 * icesummer
	 * 2015-9-11下午2:05:52
	 * @param openid
	 * @param access_token
	 * @return
	 */
	public static Map<String, Object> getWechatPreauthcode(String openid, String access_token){
		String content="openid="+openid+"&access_token="+access_token;
		String result=HttpRequestUtils.sendPost(WECHAT_API_COMPONENT_TOKEN_URL, content);
		log.info("获取预授权码pre_auth_code："+result);
		return JSONHelper.parseJSON2Map(result);
		// {"pre_auth_code":"Cx_Dk6qiBE0Dmx4EmlT3oRfArPvwSQ-oa3NL_fwHM7VI08r52wazoZX2Rhpz1dEw","expires_in":600}
	}
	
	//***********************以上是微信代公众号调用接口APIopen.weixin.qq.com::API:****/
	
	/**
	 * 获取关注者微信推送信息
	 * @return Map<String, Object> 返回结果
	 */
	public static Map<String, Object> getQRcode_URL(String action_name, String action_info, String scene_id,Map<String, Object> weiXinConfig){
		String qrcodeurl = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
		String accessToken = GetWXAccessToken.getAccessToken(weiXinConfig);
		qrcodeurl += "?access_token=" + accessToken + "";
		// {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
		String content="access_token="+accessToken+"&action_name="+action_name+"&action_info="+action_info+"&scene_id="+scene_id;
		content="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
		String result=HttpRequestUtils.sendPost(qrcodeurl, content);
		return JSONHelper.parseJSON2Map(result);
	}
	
	/**
	 * 
	 * [发送消息] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-7-6 下午9:10:27 <br>
	 * @return <br>
	 */
	public static Map<String, Object> sendMsg(String text,String[] touser,Map<String, Object> weiXinConfig){
		String accessToken = GetWXAccessToken.getAccessToken(weiXinConfig);
		String content="access_token="+accessToken+"&msgtype=text&text="+text;
		content="{\"access_token\": \""+accessToken+"\", \"msgtype\": \"text\",\"text\":{\"content\":\""+text+"\"}}";
		String result=HttpRequestUtils.sendPost(WECHAT_AUTHO_SENDMSG, content);
		try {
			return JSONHelper.parseJSON2Map(result);
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 
	 * [发送模板消息] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-8-31 上午11:51:38 <br>
	 * @param content
	 * @param weiXinConfig
	 * @return <br>
	 */
	public static Map<String, Object> sendMsg(String content,
			Map<String, Object> weiXinConfig) {
		String accessToken = GetWXAccessToken.getAccessToken(weiXinConfig);
		String result=HttpRequestUtils.sendPost(WECHAT_AUTHO_SENDMSG+"?access_token="+accessToken, content);
		try {
			return JSONHelper.parseJSON2Map(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("errcode", -1);
			map.put("errmsg", "解析返回值异常");
			return map;
		}
	}
}
