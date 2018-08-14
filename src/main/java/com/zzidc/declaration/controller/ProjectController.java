package com.zzidc.declaration.controller;

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
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.declaration.service.ProjectService;
import com.zzidc.team.entity.DeclarationProject;

import net.sf.json.JSONObject;

/**
 * 科技申报--项目管理
 */
@Controller
@RequestMapping({ "/declaration/project" })
public class ProjectController extends GiantBaseController {
	@Autowired
	private ProjectService projectService;
	private GiantPager conditionPage = null;
	private String requestURL = "declaration/project/index";

	public void publicResult(Model model) {
		model.addAttribute("m", "pd");//模块
		model.addAttribute("s", "project");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 项目列表
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "id");
			mvm.put("orderByValue", "DESC");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "2");
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
		pageList = projectService.getPageList(conditionPage);
		requestURL = "declaration/project/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "declaration/project/list";
	}

	/**
	 * 项目详情
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			Map<String, Object> projectDetail = projectService.getProjectDetail(GiantUtil.intOf(mvm.get("id"), 0));
			if(projectDetail == null) {
				return "comm/notexists";
			}
			model.addAttribute("projectM", projectDetail);

			//获取项目成果
			List<Map<String, Object>> pr = projectService.getProjectResult(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("pr", pr);
			
			//获取项目文档
			List<Map<String, Object>> doc = projectService.getProjectDoc(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("doc", doc);
		}
		publicResult(model);
		return "declaration/project/detail";
	}
	
	/**
	 * 跳转添加 / 修改项目页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加项目页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			DeclarationProject p = (DeclarationProject) projectService.getEntityByPrimaryKey(new DeclarationProject(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		publicResult(model);
		return "declaration/project/add";
	}
	
	/**
	 * 添加 / 修改项目
	 */
	@RequestMapping("/addOrUpd")
	public void addOrUpd(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtils.isEmpty(mvm.get("declaration_number"))|| GiantUtils.isEmpty(mvm.get("company")) || GiantUtils.isEmpty(mvm.get("stage")) || 
				GiantUtils.isEmpty(mvm.get("project_name")) || GiantUtils.isEmpty(mvm.get("start_date")) || GiantUtils.isEmpty(mvm.get("end_date"))){
			json.put("code",1);
			json.put("message", "编号不正确，不能跟踪");
			resultresponse(response,json);
			return;
		}
		
		boolean flag = projectService.addOrUpd(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "添加/修改成功");
		}else{
			json.put("code",1);
			json.put("message", "添加/修改失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 删除项目
	 */
	@RequestMapping("/del")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			DeclarationProject p = (DeclarationProject) projectService.getEntityByPrimaryKey(new DeclarationProject(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setState(0);
			boolean flag = projectService.saveUpdateOrDelete(p, null);
			if(flag){
				json.put("code",0);
				json.put("message", "删除成功");
			}else{
				json.put("code",1);
				json.put("message", "删除失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}
	
}
