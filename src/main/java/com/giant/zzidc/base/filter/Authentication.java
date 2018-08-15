package com.giant.zzidc.base.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.giant.zzidc.base.service.GiantBaseService;

/**
 * 鉴权工具：加载/卸载权限、权限鉴定
 */
public class Authentication {
	private static Logger logger = Logger.getLogger(Authentication.class);

	/** 权限容器：用于存放所有已登录用户的权限信息 */
	private static Map<String, List<String>> powerMap = new HashMap<String, List<String>>();

	/**
	 * 加载某管理员的权限
	 * 
	 * @param id
	 *            管理员编号
	 * @param departmentManage
	 *            部门管理（内含从数据库加载权限的方法）
	 */
	public static void addPower(String id, GiantBaseService giantBaseService) {
		if (id == null || "".equals(id) || giantBaseService == null)
			return;

		if (powerMap == null) {
			logger.error("初始化权限容器...");
			powerMap = new HashMap<String, List<String>>();
		}
		if (powerMap.isEmpty() || !powerMap.containsKey(id)) {
			logger.info("内存中没有管理员[" + id + "]的权限！");
			List<String> powerList = getPowerList(id, giantBaseService);
			if (powerList == null || powerList.isEmpty()) {
				logger.error("查询不到管理员[" + id + "]的权限！");
				return;
			}

			logger.info("加载管理员[" + id + "]的权限...");
			powerMap.put(id, powerList);
		}
	}

	/**
	 * 卸载某管理员的权限
	 * 
	 * @param id
	 *            管理员编号
	 */
	public static void removePower(String id) {
		if (id == null || "".equals(id))
			return;

		if (powerMap == null || powerMap.isEmpty()) {
			logger.error("权限容器为空！");
			return;
		}
		if (powerMap.containsKey(id)) {
			logger.info("卸载管理员[" + id + "]的权限...");
			powerMap.remove(id);
		}
	}

	/**
	 * 判断用户是否有权限
	 * 
	 * @param id
	 *            管理员编号
	 * @param path
	 *            权限路径
	 * @return true:有此路径访问权限 false:无访问权限
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasAuthPath(HttpSession session, String path) {
		try {
			List<String> list = (List<String>) session.getAttribute("power");
			if (list==null || list.size()==0)
				return false;
			return list.contains(path);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/** 加载权限列表 */
	public static List<String> getPowerList(String number, GiantBaseService giantBaseService) {
		try {
			if (number == null || "".equals(number) || giantBaseService == null)
				return null;
			Map<String, Object> prm = new HashMap<String, Object>();
			// 	TODO 用户有多个角色时，需要修改此sql
			String sql = "SELECT p.url FROM member_config cnf, role_privilege rp, privilege p "
					+ "WHERE cnf.role_ids=rp.role_id AND rp.privilege_id=p.id AND cnf.number=:number";
			prm.put("number", number);
			List<Map<String, Object>> powers = giantBaseService.getMapListBySQL(sql, prm);
			if (powers == null || powers.isEmpty())
				return null;
	
			List<String> powerList = new ArrayList<String>();
			for (Map<String, Object> power : powers) {
				if (!StringUtils.isEmpty(String.valueOf(power.get("url")))) {
					powerList.add(String.valueOf(power.get("url")));
				}
			}
			return powerList;			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
