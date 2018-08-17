package com.zzidc.team.task;

import java.util.HashMap;
import java.util.Map;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.HttpUtils;
import com.giant.zzidc.base.utils.PropertyUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zzidc.team.entity.Member;

/**
 * [同步]
 * @author likai
 * @date 2018年8月17日 下午4:53:35
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
public class SyncUser extends Thread {
	
	/**
	 * 为ture表示在同步中
	 */
	public static boolean sync = false;
	
	private GiantBaseService gbs;
	public SyncUser(GiantBaseService gbs) {
		this.gbs = gbs;
	}

	@Override
	public void run() {
		SyncUser.sync = true;
		String url = PropertyUtils.readProperty("config.properties", "OA_GET_USERINFO");
		String result = HttpUtils.sendGet(url);
		JsonElement e = new JsonParser().parse(result);
		if(e instanceof JsonObject) {
			JsonObject ret = e.getAsJsonObject();
			if(ret.has("code") && ret.get("code").getAsInt() == 0) {
				JsonArray userInfos = ret.has("userinfo") ? ret.get("userinfo").getAsJsonArray() : new JsonArray();
				if(!userInfos.isJsonNull() && userInfos.size() > 0) {
					for(int i=0; i<userInfos.size(); i++) {
						JsonObject userInfo = userInfos.get(i).getAsJsonObject();
						syncUser(userInfo);
					}
				}
			}
		}
		SyncUser.sync = false;
	}

	
	private void syncUser(JsonObject userInfo) {
		JsonElement je = userInfo.get("NUMBER");
		if(je.isJsonNull()) {
			return;
		}
		
		String number = je.getAsString();
		try {
			String name = userInfo.get("NAME").isJsonNull() ? null : userInfo.get("NAME").getAsString();
			String phone = userInfo.get("PHONE").isJsonNull() ? null : userInfo.get("PHONE").getAsString();
			String sex = userInfo.get("SEX").isJsonNull() ? null : userInfo.get("SEX").getAsString();
			String username = userInfo.get("USERNAME").isJsonNull() ? null : userInfo.get("USERNAME").getAsString();
			String status = userInfo.get("STATUS").isJsonNull() ? null : userInfo.get("STATUS").getAsString();
			String password = userInfo.get("PASSWORD").isJsonNull() ? null : userInfo.get("PASSWORD").getAsString();
			String deptID = userInfo.get("deptID").isJsonNull() ? null : userInfo.get("deptID").getAsString();
			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("number", number);
			Member member = new Member();
			Object obj = gbs.getEntityByHQL("Member", conditionMap);
			if(obj != null) {
				member = (Member) obj;
			} else {
				member.setNumber(number);
				member.setName(name);
			}
			member.setPhone(phone);
			member.setSex(sex);
			member.setUsername(username);
			member.setStatus(status);
			member.setPassword(password);
			member.setDeptId(deptID);
			gbs.saveUpdateOrDelete(member, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
