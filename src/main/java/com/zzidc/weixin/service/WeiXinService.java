package com.zzidc.weixin.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.giant.zzidc.base.dao.GiantBaseDao;
import com.giant.zzidc.base.utils.GiantUtils;
import com.giant.zzidc.base.utils.Param_Info;
import com.giant.zzidc.base.utils.PlusCut;
import com.giant.zzidc.base.utils.RestBaseVerify;
import com.giant.zzidc.base.utils.WechatUtil;

import net.sf.json.JSONObject;

/**
 * [微信接口调用处理]
 * @author ZhangBinbin
 * @date 2017-7-6 下午3:42:38
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2017
 */

public class WeiXinService extends RestBaseVerify{

	private GiantBaseDao giantBaseDaoemail;
	public GiantBaseDao getGiantBaseDaoemail() {
		return giantBaseDaoemail;
	}

	public void setGiantBaseDaoemail(GiantBaseDao giantBaseDaoemail) {
		this.giantBaseDaoemail = giantBaseDaoemail;
	}
	
	/**
	 * 
	 * [获取微信开发者信息] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-7-6 下午3:49:36 <br>
	 * @return <br>
	 */
	public Map<String, Object> getWeiXinConfigData() {
		Map<String, Object> weiXinConfig = null;
		try {
			String querySql = "SELECT id,mark,mark_name,appid,mchid,device_info,signkey,trade_type,notifyurl,signtype,remark FROM nianhui.payconfig WHERE mark = 'NEWWX'";
			List<Map<String, Object>> listMap = getMapListBySQL(querySql, null);
			if (listMap == null || listMap.size() == 0)
				return null;
			Map<String, Object> config = listMap.get(0);
			weiXinConfig = new HashMap<String, Object>();
			weiXinConfig.put("appid", GiantUtils.stringOf(config.get("weixin_open_appid")));
			weiXinConfig.put("sercret",
					GiantUtils.stringOf(config.get("weixin_open_sercret")));
		} catch (Exception excc) {
			logger.error("获取微信开发者信息appid，secret失败：" + excc);
		}
		return weiXinConfig;
	}

	/**
	 * 
	 * [微信推送消息] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-7-6 下午8:21:39 <br>
	 * @param param
	 * @return <br>
	 */
	public String sendMsg(JSONObject jsonParam) {
		//1.获取openid
		//2.获取accesstoken
		//3.调用微信接口
		Param_Info resultinfo = new Param_Info();
		if(jsonParam==null||jsonParam.size()==0){
			resultinfo.setCode(1);
			resultinfo.setMessage("传入参数为空");
			return resultinfo.entityToJson();
		}
		String touser = GiantUtils.stringOf(jsonParam.get("openid"));
		if(GiantUtils.isEmptyJson(touser)){
			resultinfo.setCode(1);
			resultinfo.setMessage("用户OpenId传入为空");
			return resultinfo.entityToJson();
		}
		String[] arr = touser.split(",");
		String data =GiantUtils.stringOf( jsonParam.get("data"));
		if(GiantUtils.isEmptyJson(data)){
			resultinfo.setCode(1);
			resultinfo.setMessage("推送内容为空");
			return resultinfo.entityToJson();
		}
		if(weiXinConfig==null||weiXinConfig.size()==0){
			weiXinConfig=this.getWeiXinConfig();
		}
		if(weiXinConfig==null||weiXinConfig.size()==0){
			resultinfo.setCode(1);
			resultinfo.setMessage("获取配置信息失败");
			resultinfo.entityToJson();
		}
		if(GiantUtils.isEmpty(weiXinConfig.get("appid"))||GiantUtils.isEmpty(weiXinConfig.get("sercret"))||GiantUtils.isEmpty(weiXinConfig.get("template_id"))){
			resultinfo.setCode(1);
			resultinfo.setMessage("获取配置信息失败");
			resultinfo.entityToJson();
		}
		//String content = "touser="+touser+"&template_id="+template_id+"&data="+data;
		if(GiantUtils.isEmptyJson(jsonParam.get("template_id"))){
			jsonParam.put("template_id", weiXinConfig.get("template_id"));
		}
		String content;
		Map<String,Object> resultMap =null;
		int erro = 0;
		for (int i = 0; i < arr.length; i++) {
			jsonParam.put("touser", arr[i]);
			content=jsonParam.toString();
			resultMap = WechatUtil.sendMsg(content, weiXinConfig);
			resultinfo.setCode(GiantUtils.intOf(resultMap.get("errcode"), -1));
			resultinfo.setMessage(GiantUtils.stringOf(resultMap.get("errmsg")));
			if(resultinfo.getCode() != 0){
				logger.info("发送微信推送结束，code:"+resultMap.get("errcode")+"，info:"+resultMap.get("errmsg")+"，openid："+PlusCut.getInstance().plus(touser));
				if(resultinfo.getCode() == 43004 || GiantUtils.stringOf(resultMap.get("errmsg")).indexOf("require subscribe hint") >= 0){//未关注公众号
					logger.info("发送微信模板消息失败，未关注公众号，openid："+PlusCut.getInstance().plus(touser));
				}
				erro++;
			}
		}
		if(arr.length == 1){//只有一条
			return resultinfo.entityToJson();
		}else if(erro == arr.length){//全部失败
			resultinfo.setCode(1);
		}else{
			resultinfo.setCode(0);
		}
		resultinfo.setMessage("发送完毕，总数："+arr.length+",成功个数："+(arr.length-erro));
		return resultinfo.entityToJson();
	}
	

	/**
	 * 
	 * [更新微信配置信息] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-9-4 下午3:06:18 <br>
	 * @param jsonParam
	 * @return <br>
	 */
	public String updateSysConfig(JSONObject jsonParam) {
		Param_Info resultinfo = new Param_Info();
		if(jsonParam==null||jsonParam.size()==0){
			resultinfo.setCode(1);
			resultinfo.setMessage("传入参数为空");
			return resultinfo.entityToJson();
		}
		if(GiantUtils.stringOf(jsonParam.get("doupdate")).equals("yes")){
			weiXinConfig = new HashMap<String, Object>();
			weiXinConfig = getWeiXinConfig();
		}else{
			resultinfo.setCode(1);
			resultinfo.setMessage("更新失败，参数错误");
			return resultinfo.entityToJson();
		}
		if(weiXinConfig==null||weiXinConfig.size()==0){
			resultinfo.setCode(1);
			resultinfo.setMessage("更新失败");
			return resultinfo.entityToJson();
		}
		resultinfo.setCode(0);
		resultinfo.setMessage("更新成功");
		return resultinfo.entityToJson();
	}

	/**
	 * 
	 * [获取微信accesstoken ，因为微信accesstoken有效期为2小时，获取新的会导致老的失效，所以特此将此战作为中控站，缓存有效的token] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-11-28 上午11:56:47 <br>
	 * @param jsonParam
	 * @return <br>
	 */
	public String getAccessToken(JSONObject jsonParam) {
		Param_Info resultinfo = new Param_Info();
		String accessToken = GetWXAccessToken.getAccessToken(weiXinConfig);
		if(accessToken != null){
			resultinfo.setCode(0);
			resultinfo.setMessage(accessToken);
		}else{
			resultinfo.setCode(1);
			resultinfo.setMessage("获取失败");
		}
		return resultinfo.entityToJson();
	}
	
	
	
	
	/**
	 * 
		 *  @Description    :  通知
		 *  @Method_Name    :  sendWeChat
		 *  @return 
		 *  @Creation Date  :  2018-1-17 
		 *  @version        :  v1.00
		 *  @Author         :  Lipingyuan
		 *  @Update Date    :  
		 *  @Update Author  :
	 */
	public String sendWeChat(JSONObject paramMap) {
		String result = "本次微信发送结果：";
		String emailresult = "本次邮件发送结果：";
    	Map<String,Object> p=new HashMap<String,Object>();
    	p.put("resource","VG3OQ81wtrNGANeuq8IdwTBhgkAF53lFOIY6aCIBeo0=");
    	p.put("template_id", "MIgq4jGPPeZ4EHwWcJDt2hV29N0Xyl8ujn0e4hvn5vk");
    	Map<String, Object> d = new HashMap<String,Object>();
    	Map<String, Object> first = new HashMap<String,Object>();
    	first.put("color", "#173177");
    	first.put("value", paramMap.get("first"));
    	Map<String, Object> keyword1 = new HashMap<String,Object>();
    	keyword1.put("color", "#173177");
    	keyword1.put("value", paramMap.get("keyword1"));
    	Map<String, Object> keyword2 = new HashMap<String,Object>();
    	keyword2.put("color", "#173177");
    	keyword2.put("value", paramMap.get("keyword2"));
    	Map<String, Object> keyword3 = new HashMap<String,Object>();
    	keyword3.put("color", "#173177");
    	keyword3.put("value", paramMap.get("keyword3"));
    	Map<String, Object> remark = new HashMap<String,Object>();
    	remark.put("color", "#173177");
    	remark.put("value", paramMap.get("remark"));
    	d.put("first", first);
    	d.put("keyword1", keyword1);
    	d.put("keyword2", keyword2);
    	d.put("keyword3", keyword3);
    	d.put("remark", remark);
    	p.put("data", d);
		String keyValue = GiantUtils.stringOf(paramMap.get("keyValue"));
		if(!weixin(keyValue,3001)){
			result +=  "keyValue:"+keyValue+"未查到正常的相关数据，不能发送微信";
		}else{
			Set<Object> set = getEmp(keyValue,3001);
			for(Object empid : set){
				if(empid==null){
					continue;
				}
	    		String openid = getOpenid(empid.toString());
	    		if(openid!=null){
	    			p.put("openid", PlusCut.getInstance().cut(openid));
	    			JSONObject  js = JSONObject.fromObject(p);
	    			JSONObject json = new JSONObject();
	    			String re = sendMsg(js);
	    			try {
						json = JSONObject.fromObject(re); 
					} catch (Exception e) {
						e.printStackTrace();
						json.put("code", 1);
						json.put("message", re);
					}
	    			
	    			if("0".equals(json.getString("code"))){
	    				result += "员工："+empid+",发送成功;";
	    			}else{
	    				result += "员工："+empid+",发送失败:"+json.getString("message")+";";
	    			}
	    		}else{
	    			result += "员工："+empid+",发送失败:该员工未绑定微信;";
	    		}
			}
	    	String empids = GiantUtils.stringOf(paramMap.get("empids"));
	    	if(!crweixin(keyValue,3001)){
	    		result +="员工："+empids+",状态为不能发送;";
	    	}else if(empids!=null && !"".equals(empids)){
		    	String [] eids = empids.split(",");
		    	for(int i = 0;i<eids.length;i++){
		    		String openid = getOpenid(eids[i]);
		    		if(openid!=null){
		    			p.put("openid", PlusCut.getInstance().cut(openid));
		    			JSONObject  js = JSONObject.fromObject(p);
		    			JSONObject json = new JSONObject();
		    			String re = sendMsg(js);
		    			try {
							json = JSONObject.fromObject(re); 
						} catch (Exception e) {
							e.printStackTrace();
							json.put("code", 1);
							json.put("message", re);
						}
		    			
		    			if("0".equals(json.getString("code"))){
		    				result += "员工："+eids[i]+",发送成功;";
		    			}else{
		    				result += "员工："+eids[i]+",发送失败:"+json.getString("message")+";";
		    			}
		    		}else{
		    			result += "员工："+eids[i]+",发送失败:该员工未绑定微信;";
		    		}
		    	}
	    	}
	    	String openids = GiantUtils.stringOf(paramMap.get("openid"));
	    	if(openids!=null&&!"".equals(openids)){
	    		String [] opens = openids.split(",");
	    		for(int i = 0;i<opens.length;i++){
					p.put("openid", opens[i]);
	    			JSONObject  js = JSONObject.fromObject(p);
	    			JSONObject json = new JSONObject();
	    			String re = sendMsg(js);
					try {
						json = JSONObject.fromObject(re); 
					} catch (Exception e) {
						e.printStackTrace();
						json.put("code", 1);
						json.put("message", re);
					}
					
					if("0".equals(json.getString("code"))){
						result += "openid："+opens[i]+",发送成功;";
					}else{
						result += "openid："+opens[i]+",发送失败:"+json.getString("message")+";";
					}    			
	    		}
	    	}		
	    	List<Map<String, Object>> list = getEmpcontactinfo(keyValue,3001);
	    	if(list!=null && list.size() > 0){
		    	for(Map<String, Object> map : list){
		    		if(map.get("openid") != null){
		    			p.put("openid", PlusCut.getInstance().cut(map.get("openid").toString()));
		    			JSONObject  js = JSONObject.fromObject(p);
		    			JSONObject json = new JSONObject();
		    			String re = sendMsg(js);
		    			try {
							json = JSONObject.fromObject(re); 
						} catch (Exception e) {
							e.printStackTrace();
							json.put("code", 1);
							json.put("message", re);
						}
		    			if("0".equals(json.getString("code"))){
		    				result += "未注册账号员工："+map.get("empname")+",发送成功;";
		    			}else{
		    				result += "未注册账号员工："+map.get("empname")+",发送失败:"+json.getString("message")+";";
		    			}
		    		}else{
		    			result += "未注册账号员工："+map.get("empname")+",发送失败:该员工未绑定微信;";
		    		}
		    	}	    		
	    	}
		}
		return  result+";"+emailresult;
	}
	
	
	
	/**
	 * 
		 *  @Description    :  是否发送微信或邮件（微信3001 邮件3002）
		 *  @Method_Name    :  weixin
		 *  @return 
		 *  @Creation Date  :  2018-1-17 
		 *  @version        :  v1.00
		 *  @Author         :  Lipingyuan
		 *  @Update Date    :  
		 *  @Update Author  :
	 */
	public boolean weixin(String keyValue,Integer type) {
		String sql = " SELECT cr.id FROM zzidc_core.core_remind cr "
			+" WHERE cr.key_value = '"+keyValue+"'  AND cr.remind_send = 0  AND cr.remind_fslx_state = "+type;
			List<Map<String, Object>> list = getMapListBySQL(sql, null);
			if(list!=null && list.size()>0){
				return true;
			}else{
				return false;
			}
	}
	
	/**
	 * 
		 *  @Description    :  根据键值获取员工
		 *  @Method_Name    :  getEmp
		 *  @return 
		 *  @Creation Date  :  2018-1-17 
		 *  @version        :  v1.00
		 *  @Author         :  Lipingyuan
		 *  @Update Date    :  
		 *  @Update Author  :
	 */
	public Set<Object> getEmp(String keyValue,Integer type) {
		Set<Object> set = new HashSet<Object>();
		String sql = " SELECT cer.emp_id,cer.dept_id FROM zzidc_core.core_remind cr LEFT JOIN zzidc_core.core_emp_remind cer ON cr.id = cer.remind_id "
					+" WHERE cr.key_value = '"+keyValue+"'  AND cr.remind_send = 0 AND cr.remind_fslx_state = "+type;
		List<Map<String, Object>> list = getMapListBySQL(sql, null);
		if(list!=null && list.size()>0){
			for(Map<String, Object> map:list){
				if(map.get("emp_id")!=null){
					set.add(map.get("emp_id"));
				}
				if(map.get("dept_id")!=null){
				 Set<Object> rSet =	getEmp(map.get("dept_id"));
				 set.addAll(rSet);
				}
			}
		}
		return set;
	}
	/**
	 * 
		 *  @Description    :  获取该部门下所有的员工
		 *  @Method_Name    :  getEmp
		 *  @return 
		 *  @Creation Date  :  2018-1-17 
		 *  @version        :  v1.00
		 *  @Author         :  Lipingyuan
		 *  @Update Date    :  
		 *  @Update Author  :
	 */
	public Set<Object> getEmp(Object deptid){
		Set<Object> set = new HashSet<Object>();
		String sql = " SELECT cde.login_id FROM zzidc_core.core_dept_emp cde LEFT JOIN zzidc_db.tablogins ta ON cde.login_id = ta.id	WHERE ta.state = 1 AND dept_id = "+deptid;
		List<Map<String, Object>> list = getMapListBySQL(sql, null);
		if(list!=null && list.size()>0){
			for(Map<String, Object> map : list){
				set.add(map.get("login_id"));
			}
		}
		sql = " SELECT id FROM zzidc_core.core_department WHERE state = 0 AND  parent_id = "+deptid;
		list = getMapListBySQL(sql, null);
		if(list!=null && list.size()>0){
			for(Map<String, Object> map : list){
				Set<Object> rSet = getEmp(map.get("id"));
				set.addAll(rSet);
			}
		}
		return set;
	}
	
	/**
	 * 
		 *  @Description    :  根据会有编号获取openid
		 *  @Method_Name    :  getOpenid
		 *  @return 
		 *  @Creation Date  :  2018-1-17 
		 *  @version        :  v1.00
		 *  @Author         :  Lipingyuan
		 *  @Update Date    :  
		 *  @Update Author  :
	 */
	public String getOpenid(String empid) {
		String sql = " SELECT openid FROM zzidc_db.tablogins WHERE id = "+empid;
		List<Map<String, Object>> list = getMapListBySQL(sql, null);
		if(list!=null && list.size() == 1 && list.get(0).get("openid")!=null){
			return list.get(0).get("openid").toString();
		}
		return null;
	}
	/**
	 * 
		 *  @Description    :  获取未注册员工信息
		 *  @Method_Name    :  getEmpcontactinfo
		 *  @return 
		 *  @Creation Date  :  2018-1-17 
		 *  @version        :  v1.00
		 *  @Author         :  Lipingyuan
		 *  @Update Date    :  
		 *  @Update Author  :
	 */
	public List<Map<String, Object>> getEmpcontactinfo(String keyValue,Integer type) {
		String sql = " SELECT ec.* FROM zzidc_core.core_remind cr LEFT JOIN zzidc_core.core_emp_remind cer ON cer.remind_id = cr.id  LEFT JOIN zzidc_db.empcontactinfo ec ON ec.id = cer.ec_id "
					+" WHERE cr.key_value = '"+keyValue+"'  AND cr.remind_send = 0 AND cr.remind_fslx_state = "+type;
		List<Map<String, Object>> list = getMapListBySQL(sql, null);
		return list;
	}
	
	/**
	 * 
		 *  @Description    :  获取邮箱
		 *  @Method_Name    :  getEmail
		 *  @return 
		 *  @Creation Date  :  2018-1-17 
		 *  @version        :  v1.00
		 *  @Author         :  Lipingyuan
		 *  @Update Date    :  
		 *  @Update Author  :
	 */
	public String getEmail(String empid) {
		String sql = " SELECT email FROM zzidc_db.tabprofiles WHERE account_id = "+empid;
		List<Map<String, Object>> list = getMapListBySQL(sql, null);
		if(list!=null && list.size() == 1 && list.get(0).get("email")!=null){
			return list.get(0).get("email").toString();
		}
		return null;
	}
	
	/**
	 * 
		 *  @Description    :  传入员工是否发送
		 *  @Method_Name    :  weixin
		 *  @return 
		 *  @Creation Date  :  2018-1-18 
		 *  @version        :  v1.00
		 *  @Author         :  Lipingyuan
		 *  @Update Date    :  
		 *  @Update Author  :
	 */
		public boolean crweixin(String keyValue,Integer type) {
			String sql = " SELECT cr.id FROM zzidc_core.core_remind cr "
				+" WHERE cr.key_value = '"+keyValue+"' AND cr.remind_state = 0   AND cr.remind_send = 0  AND cr.remind_fslx_state = "+type;
				List<Map<String, Object>> list = getMapListBySQL(sql, null);
				if(list!=null && list.size()>0){
					return true;
				}else{
					return false;
				}
		}
	
}
