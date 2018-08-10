package com.zzidc.team.controller;

import java.util.HashMap;
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
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.MemberConfig;
import com.zzidc.team.service.OrganizationDepartmentService;
import com.zzidc.team.service.OrganizationUserService;

import net.sf.json.JSONObject;

/**
 * [组织->用户]
 * @author likai
 * @date 2018年7月30日 下午7:14:42
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Controller
@RequestMapping({ "/organization/user" })
public class OrganizationUserController extends GiantBaseController {
	@Autowired
	private OrganizationUserService organizationUserService;
	@Autowired
	private OrganizationDepartmentService organizationDepartmentService;
	private GiantPager conditionPage = null;
	private String requestURL = "organization/user/index";
	
	public void publicResult(Model model) {
		model.addAttribute("m", "organization");//模块
		model.addAttribute("s", "user");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 用户列表
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "m.id");
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
		pageList = organizationUserService.getPageList(conditionPage);
		requestURL = "organization/user/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "organization/user/list";
	}

	/**
	 * 跳转到配置页面
	 */
	@RequestMapping("/toConfig")
	public String toConfig(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Member n = (Member) organizationUserService.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("id"), 0));
			if(n != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("number", n.getNumber());
				n.setConfig((MemberConfig) organizationUserService.getEntityByHQL("MemberConfig", map));
			}
			model.addAttribute("entity", n);
			model.addAttribute("departmentTree", organizationDepartmentService.getDepartments());
			model.addAttribute("roles", organizationDepartmentService.getRoles());
		}
		publicResult(model);
		return "organization/user/config";
	}

	/**
	 * 配置
	 */
	@RequestMapping("/config")
	public void config(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) 
				|| GiantUtil.isEmpty(mvm.get("department_id"))
				|| GiantUtil.isEmpty(mvm.get("role_ids"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = organizationUserService.config(mvm);
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
