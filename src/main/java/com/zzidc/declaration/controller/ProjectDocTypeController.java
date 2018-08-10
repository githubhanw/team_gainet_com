package com.zzidc.declaration.controller;

import java.sql.Timestamp;
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
import com.zzidc.declaration.service.ProjectDocTypeService;
import com.zzidc.team.entity.DeclarationProjectDocType;

import net.sf.json.JSONObject;

/**
 * 项目申报--项目成果文档类型管理
 */
@Controller
@RequestMapping({ "/declaration/doctype" })
public class ProjectDocTypeController extends GiantBaseController {
	@Autowired
	private ProjectDocTypeService projectDocTypeService;
	private GiantPager conditionPage = null;

	public void publicResult(Model model) {
		model.addAttribute("m", "pd");//模块
		model.addAttribute("s", "doctype");//子模块
	}
	
	/**
	 * 项目成果文档类型列表
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
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		//查询条件封装
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(GiantUtil.intOf(mvm.get("pageSize"), 15));
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = projectDocTypeService.getPageList(conditionPage);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "declaration/project_doc_type/list";
	}
	
	/**
	 * 跳转添加 / 修改项目成果文档类型页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加项目成果页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			DeclarationProjectDocType pdt = (DeclarationProjectDocType) projectDocTypeService.getEntityByPrimaryKey(new DeclarationProjectDocType(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("obj", pdt);
		}
		publicResult(model);
		return "declaration/project_doc_type/add";
	}
	
	/**
	 * 添加 / 修改项目成果文档类型
	 */
	@RequestMapping("/addOrUpd")
	public void addOrUpd(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		String projectDocType = GiantUtil.stringOf(mvm.get("projectDocType"));
		JSONObject json=new JSONObject();
		
		DeclarationProjectDocType pdt = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			pdt = (DeclarationProjectDocType) projectDocTypeService.getEntityByPrimaryKey(new DeclarationProjectDocType(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			pdt = new DeclarationProjectDocType();
			pdt.setCreateTime(new Timestamp(System.currentTimeMillis()));
			pdt.setState(1);
		}
		pdt.setProjectDocType(projectDocType);
		pdt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean flag = projectDocTypeService.saveUpdateOrDelete(pdt, null);
		if(flag){
			json.put("code",0);
			json.put("message", "添加/修改成功");
		}else{
			json.put("code",1);
			json.put("message", "添加/修改失败");
		}
		resultresponse(response, json);
	}

	/**
	 * 删除项目成果文档类型页面
	 */
	@RequestMapping("/del")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			DeclarationProjectDocType pdt = (DeclarationProjectDocType) projectDocTypeService.getEntityByPrimaryKey(new DeclarationProjectDocType(), GiantUtil.intOf(mvm.get("id"), 0));
			pdt.setState(0);
			boolean flag = projectDocTypeService.saveUpdateOrDelete(pdt, null);
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
