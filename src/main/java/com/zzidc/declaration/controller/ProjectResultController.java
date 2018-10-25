package com.zzidc.declaration.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.FileUploadUtil;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.declaration.service.ProjectResultService;
import com.zzidc.log.LogMethod;
import com.zzidc.log.LogModule;
import com.zzidc.log.PMLog;
import com.zzidc.team.entity.DeclarationProject;
import com.zzidc.team.entity.DeclarationProjectDoc;
import com.zzidc.team.entity.DeclarationProjectDocType;
import com.zzidc.team.entity.DeclarationProjectResult;

import net.sf.json.JSONObject;

/**
 * 科技（science and technology，简称st）申报--项目成果管理
 */
@Controller
@RequestMapping({ "/declaration/result" })
public class ProjectResultController extends GiantBaseController {
	@Autowired
	private ProjectResultService projectResultService;
	private GiantPager conditionPage = null;
	private String requestURL = "declaration/result/index";

	public void publicResult(Model model) {
		model.addAttribute("m", "pd");//模块
		model.addAttribute("s", "result");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 成果列表导出为excel
	 * @param mvm
	 * @param response
	 */
	@RequestMapping("/exportResult")
	public void exportResult(@RequestParam Map<String, String> mvm, HttpServletResponse response) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "pr.id");
			mvm.put("orderByValue", "DESC");
			mvm.put("currentPage", "1");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "2");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("search")))){
			mvm.put("search", "");
		}
		
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		//查询条件封装
		queryCondition.clear();
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(10000);
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = projectResultService.getPageList(conditionPage);
		HSSFWorkbook workbook = new HSSFWorkbook();
		projectResultService.exportResult(pageList, workbook);////导出excel表格       
        try {
			response.setHeader("Content-disposition", "attachment; filename=" + new String("成果列表".getBytes("gb2312" ), "ISO8859-1") + ".xls");
			response.setContentType("application/msexcel");
			OutputStream out = response.getOutputStream();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 项目成果列表
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "pr.id");
			mvm.put("orderByValue", "DESC");
			mvm.put("currentPage", "1");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "2");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("search")))){
			mvm.put("search", "");
		}
		
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		//查询条件封装
		queryCondition.clear();
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(GiantUtil.intOf(mvm.get("pageSize"), 15));
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = projectResultService.getPageList(conditionPage);
		requestURL = "declaration/result/index";
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "declaration/project_result/list";
	}

	/**
	 * 项目成果详情
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			List<Map<String, Object>> pr = projectResultService.getProjectResult(GiantUtil.intOf(mvm.get("id"), 0));
			if (pr != null && pr.size() > 0) {
				model.addAttribute("pr", pr.get(0));
			}
			//获取成果文档
			List<Map<String, Object>> doc = projectResultService.getResultDoc(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("doc", doc);
			//日志
			List<Map<String, Object>> logList = projectResultService.getLogList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("logList", logList);
		}
		publicResult(model);
		return "declaration/project_result/detail";
	}
	
	/**
	 * 跳转添加 / 修改项目成果页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加项目成果页面的项目列表
		model.addAttribute("project", projectResultService.getDeclarationProject());
		model.addAttribute("members", projectResultService.getAllMembers());
		String querySql = "SELECT dp.id, dp.project_doc_type, rt.required_or_optional FROM result_type_doc_type rt "
				        + "LEFT JOIN declaration_project_doc_type dp ON rt.doc_type_id = dp.id "
				        + "where rt.result_type = 1";
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			DeclarationProjectResult pr = (DeclarationProjectResult) projectResultService.getEntityByPrimaryKey(new DeclarationProjectResult(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("pr", pr);
			querySql = "SELECT dp.id, dp.project_doc_type, rt.required_or_optional FROM result_type_doc_type rt "
			         + "LEFT JOIN declaration_project_doc_type dp ON rt.doc_type_id = dp.id "
			         + "where rt.result_type = " + pr.getType();
		}
		List<Map<String, Object>> docTypeList = projectResultService.getMapListBySQL(querySql, null);
		model.addAttribute("docTypeList", docTypeList);
		publicResult(model);
		return "declaration/project_result/add";
	}
	
	/**
	 * 添加 / 修改项目成果
	 */
	@RequestMapping("/addOrUpd")
	public void addOrUpd(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtils.isEmpty(mvm.get("type")) || GiantUtils.isEmpty(mvm.get("company")) || GiantUtils.isEmpty(mvm.get("agent")) || GiantUtils.isEmpty(mvm.get("state")) || 
				GiantUtils.isEmpty(mvm.get("registration_number")) || GiantUtils.isEmpty(mvm.get("project_id")) || GiantUtils.isEmpty(mvm.get("project_result_name")) || 
				GiantUtils.isEmpty(mvm.get("member_id")) || GiantUtils.isEmpty(mvm.get("apply_date"))){
			json.put("code",1);
			json.put("message", "参数不完整！！");
			resultresponse(response,json);
			return;
		}
		boolean flag = projectResultService.addOrUpd(mvm, request);
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
	 * 删除项目成果
	 */
	@RequestMapping("/del")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			DeclarationProjectResult pr = (DeclarationProjectResult) projectResultService.getEntityByPrimaryKey(new DeclarationProjectResult(), GiantUtil.intOf(mvm.get("id"), 0));
			pr.setState((short) 0);
			boolean flag = projectResultService.saveUpdateOrDelete(pr, null);
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
	
	@RequestMapping("changeDocType")
	public void changeDocType(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String type = mvm.get("type");
		String querySql = "SELECT dp.id, dp.project_doc_type, rt.required_or_optional FROM result_type_doc_type rt "
		        + "LEFT JOIN declaration_project_doc_type dp ON rt.doc_type_id = dp.id "
		        + "where rt.result_type = " + type;
		List<Map<String, Object>> docTypeLists = projectResultService.getMapListBySQL(querySql, null);
		json.put("docTypeLists", docTypeLists);
		resultresponse(response, json);
	}
	
	/**
	 * 文档上传
	 * @param mvm
	 * @param model
	 * @return
	 */
	@RequestMapping("/toUploadDoc")
	public String toUploadDoc(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			DeclarationProjectDoc pdt = (DeclarationProjectDoc) projectResultService.getEntityByPrimaryKey(new DeclarationProjectDoc(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("obj", pdt);
			DeclarationProjectResult result = (DeclarationProjectResult) projectResultService.getEntityByPrimaryKey(new DeclarationProjectResult(), pdt.getResultId());
			DeclarationProject project = (DeclarationProject) projectResultService.getEntityByPrimaryKey(new DeclarationProject(), pdt.getProjectId());
			DeclarationProjectDocType doc = (DeclarationProjectDocType) projectResultService.getEntityByPrimaryKey(new DeclarationProjectDocType(), pdt.getTypeId());
			model.addAttribute("result", result);
			model.addAttribute("project", project);
			model.addAttribute("doc", doc);
		}
		publicResult(model);
		return "declaration/project_result/toUploadDoc";
	}
	
	@RequestMapping("/uploadDoc")
	public void uploadDoc(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,
			@RequestParam("file") MultipartFile[] file) {
		JSONObject json = new JSONObject();
		DeclarationProjectDoc pdt = null;
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			// 获取文档对象
			pdt = (DeclarationProjectDoc) projectResultService
					.getEntityByPrimaryKey(new DeclarationProjectDoc(), GiantUtil.intOf(mvm.get("id"), 0));
		}
		DeclarationProjectResult result = (DeclarationProjectResult) projectResultService.getEntityByPrimaryKey(new DeclarationProjectResult(), pdt.getResultId());
		DeclarationProjectDocType doc = (DeclarationProjectDocType) projectResultService.getEntityByPrimaryKey(new DeclarationProjectDocType(), pdt.getTypeId());
		Map<String, String> conf = projectResultService.getSysConfig();
		FileUploadUtil.SetParam(conf.get("accesskey"), conf.get("secreteky"), conf.get("resource"));
		String docName = result.getProjectResultName() + "_" + doc.getProjectDocType() 
                       + file[0].getOriginalFilename().substring(file[0].getOriginalFilename().lastIndexOf("."));
		String fileName = "";
		String newSuffix = file[0].getOriginalFilename().substring(file[0].getOriginalFilename().lastIndexOf("."));
		if (pdt.getDocState() == 1) {
			String name = pdt.getProjectDocUrl().substring(pdt.getProjectDocUrl().lastIndexOf("/") + 1, pdt.getProjectDocUrl().lastIndexOf("."));
			String oldSuffix = pdt.getProjectDocUrl().substring(pdt.getProjectDocUrl().lastIndexOf("."));
			if (oldSuffix.equals(newSuffix)) {
				fileName = pdt.getProjectDocUrl().substring(pdt.getProjectDocUrl().lastIndexOf("/") + 1);
			} else {
				fileName = name + newSuffix;
			}
			
		} else {
			fileName = System.currentTimeMillis() + newSuffix;
		}
		
		Object url = FileUploadUtil.uploadFiles(file, fileName);
		pdt.setProjectDocUrl(url.toString());
		pdt.setDocName(docName);
		pdt.setOriginalName(file[0].getOriginalFilename());
		pdt.setDocState(1);
		boolean flag = projectResultService.saveUpdateOrDelete(pdt, null);
		if (flag) {
			json.put("code", 0);
			json.put("message", "创建成功");
		} else {
			json.put("code", 1);
			json.put("message", "创建失败");
		}
		resultresponse(response, json);
	}
	
}
