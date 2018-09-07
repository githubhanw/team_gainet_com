package com.zzidc.team.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.MonthMeeting;
import com.zzidc.team.entity.Privilege;
import com.zzidc.team.service.OrganizationPrivilegeService;

import net.sf.json.JSONObject;

/**
 * 权限管理
 * 
 * @author hanwei
 *
 */
@Controller
@RequestMapping("/organization/privilege")
public class OrganizationPrivilegeController extends GiantBaseController {

	@Autowired
	private OrganizationPrivilegeService organizationPrivilegeService;
	private GiantPager conditionPage = null;
	private String requestURL = "organization/privilege/index";

	public void publicResult(Model model) {
		model.addAttribute("m", "organization");// 模块
		model.addAttribute("s", "privilege");// 子模块
		model.addAttribute("u", requestURL);// 请求地址
	}

	/**
	 * 权限列表
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		if ("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))) {
			mvm.put("orderColumn", "id");
			mvm.put("orderByValue", "ASC");
			mvm.put("currentPage", "1");
		}
		if ("".equals(GiantUtil.stringOf(mvm.get("type")))) {
			mvm.put("type", "1");
		}
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		// 查询条件封装
		queryCondition.clear();
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(GiantUtil.intOf(mvm.get("pageSize"), 15));
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = organizationPrivilegeService.getPageList(conditionPage);
		requestURL = "organization/privilege/index";
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "organization/privilege/list";
	}

	/**
	 * 跳转添加 / 修改会议页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		// 添加项目页面的项目列表
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			Privilege p = (Privilege) organizationPrivilegeService.getEntityByPrimaryKey(new Privilege(),
					GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		String querySql = "select id, name from privilege where parent_id = 0";
		List<Map<String, Object>> parentList = organizationPrivilegeService.getMapListBySQL(querySql, null);
		model.addAttribute("parentList", parentList);
		publicResult(model);
		return "organization/privilege/add";
	}

	/**
	 * 添加 / 修改月会议
	 */
	@RequestMapping("/addOrUpd")
	public void addOrUpd(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		Integer parentId = GiantUtil.intOf(mvm.get("parent_id"), 0);
		if (parentId == 0) {
			if (mvm.get("name") == null || "".equals(mvm.get("name")) || mvm.get("parent_id") == null
					|| "".equals(mvm.get("parent_id"))) {
				json.put("code", 1);
				json.put("message", "参数不足");
				resultresponse(response, json);
				return;
			}
		} else {
			if (mvm.get("name") == null || "".equals(mvm.get("name")) || mvm.get("parent_id") == null
					|| "".equals(mvm.get("parent_id")) || mvm.get("url") == null || "".equals(mvm.get("url"))
					|| mvm.get("rank") == null || "".equals(mvm.get("rank"))) {
				json.put("code", 1);
				json.put("message", "参数不足");
				resultresponse(response, json);
				return;
			}
		}

		boolean flag = organizationPrivilegeService.addOrUpd(mvm);
		if (flag) {
			json.put("code", 0);
			json.put("message", "添加/修改成功");
		} else {
			json.put("code", 1);
			json.put("message", "添加/修改失败");
		}
		resultresponse(response, json);
	}

	/**
	 * 删除月会议
	 */
	@RequestMapping("/del")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取对象
			Privilege p = (Privilege) organizationPrivilegeService.getEntityByPrimaryKey(new Privilege(),
					GiantUtil.intOf(mvm.get("id"), 0));
			p.setState((short) 0);
			boolean flag = organizationPrivilegeService.saveUpdateOrDelete(p, null);
			if (flag) {
				json.put("code", 0);
				json.put("message", "删除成功");
			} else {
				json.put("code", 1);
				json.put("message", "删除失败");
			}
		} else {
			json.put("code", 1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response, json);
	}

}
