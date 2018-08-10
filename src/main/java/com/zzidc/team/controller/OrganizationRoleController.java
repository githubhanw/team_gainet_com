package com.zzidc.team.controller;

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
import com.zzidc.team.entity.Role;
import com.zzidc.team.service.OrganizationRoleService;

import net.sf.json.JSONObject;

/**
 * [角色管理]
 * @author likai
 * @date 2018年8月3日 下午1:52:05
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Controller
@RequestMapping({ "/organization/role" })
public class OrganizationRoleController extends GiantBaseController {

	@Autowired
	private OrganizationRoleService organizationRoleService;
	private GiantPager conditionPage = null;
	private String requestURL = "organization/role/index";

	
	public void publicResult(Model model) {
		model.addAttribute("m", "organization");//模块
		model.addAttribute("s", "role");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "r.id");
			mvm.put("orderByValue", "DESC");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "0");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("search")))){
			mvm.put("search", "");
		}
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		//查询条件封装
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(GiantUtil.intOf(mvm.get("pageSize"), 15));
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = organizationRoleService.getPageList(conditionPage);
		requestURL = "organization/role/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "organization/role/list";
	}

	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		publicResult(model);
		return "organization/role/add";
	}

	/**
	 * 跳转到修改页面
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Role n = (Role) organizationRoleService.getEntityByPrimaryKey(new Role(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("entity", n);
		}
		publicResult(model);
		return "organization/role/add";
	}

	/**
	 * 添加/修改功能
	 */
	@RequestMapping("/addOrUpdate")
	public void addOrUpdate(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("name"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = organizationRoleService.addOrUpdate(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "操作成功");
		}else{
			json.put("code",1);
			json.put("message", "操作失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 跳转到授权页面
	 */
	@RequestMapping("/toAuth")
	public String toAuth(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Role n = (Role) organizationRoleService.getEntityByPrimaryKey(new Role(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("entity", n);
			model.addAttribute("allPrivileges", organizationRoleService.getAllPrivileges());
		}
		publicResult(model);
		return "organization/role/auth";
	}

	/**
	 * 授权功能
	 */
	@RequestMapping("/auth")
	public void auth(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = organizationRoleService.auth(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "操作成功");
		}else{
			json.put("code",1);
			json.put("message", "操作失败");
		}
		resultresponse(response,json);
	}
}
