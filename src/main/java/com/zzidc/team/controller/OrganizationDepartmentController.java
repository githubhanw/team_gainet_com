package com.zzidc.team.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.Department;
import com.zzidc.team.service.OrganizationDepartmentService;

import net.sf.json.JSONObject;

/**
 * [组织->团队管理 Controller]
 * @author likai
 * @date 2018年7月30日 下午2:53:26
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Controller
@RequestMapping({ "/organization/department" })
public class OrganizationDepartmentController extends GiantBaseController {
	@Autowired
	private OrganizationDepartmentService organizationDepartmentService;
	private String requestURL = "organization/department/index";

	public void publicResult(Model model) {
		model.addAttribute("m", "organization");//模块
		model.addAttribute("s", "department");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 团队首页
	 */
	@RequestMapping("/index")
	public String index(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("departmentTree", organizationDepartmentService.getDepartments());
		model.addAttribute("departments", organizationDepartmentService.getDepartmentsByParentId(0));
		model.addAttribute("members", organizationDepartmentService.getMembers());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Department d = (Department) organizationDepartmentService.getEntityByPrimaryKey(new Department(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("entity", d);
		}
		model.addAttribute("mvm", mvm);
		publicResult(model);
		return "organization/department/list";
	}
	
	/**
	 * 添加/修改操作
	 */
	@RequestMapping("/addOrUpdate")
	public void addOrUpdate(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("name"))
				|| GiantUtil.intOf(mvm.get("parent_id"), -1) == -1
//				|| GiantUtil.intOf(mvm.get("leader_id"), 0) == 0
				|| GiantUtil.intOf(mvm.get("sort"), -1) == -1){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = organizationDepartmentService.addOrUpdate(mvm);
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
