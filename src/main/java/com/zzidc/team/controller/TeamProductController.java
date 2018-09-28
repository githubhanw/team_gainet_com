package com.zzidc.team.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.StringUtil;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.TaskProduct;
import com.zzidc.team.entity.TaskProject;
import com.zzidc.team.service.FilemanageService;
import com.zzidc.team.service.TeamProductService;

import net.sf.json.JSONObject;

/**
 * 产品管理
 */
@Controller
@RequestMapping({ "/team/product" })
public class TeamProductController extends GiantBaseController {
	@Autowired
	private TeamProductService teamProductService;
	@Autowired
	private FilemanageService filemanageService;
	@Autowired
	private GiantBaseService baseService;
	private GiantPager conditionPage = null;
	private String requestURL = "team/product/index";

	public void publicResult(Model model) {
		model.addAttribute("m", "product");//模块
		model.addAttribute("s", "product");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 产品列表
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
			mvm.put("type", "1");
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
		pageList = teamProductService.getPageList(conditionPage);
		requestURL = "team/product/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("members", teamProductService.getAllMember());
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "team/product/list";
	}

	/**
	 * 项目详情
	 */
	@RequestMapping("/detail")
	public String pro_detail(@RequestParam Map<String, String> mvm, Model model) {
		//添加项目页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProduct p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
			Member member = (Member) teamProductService.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(p.getMemberId(), 0));
			model.addAttribute("member_name", member == null ? "" : member.getName());
			
			List<Map<String, Object>> needTask = teamProductService.getRelationTaskList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("needTask", needTask);
		}
		publicResult(model);
		return "team/product/detail";
	}

	/**
	 * 跳转添加项目页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("members", teamProductService.getAllMember());
		//添加项目页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProduct p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		publicResult(model);
		return "team/product/add";
	}
	
	/**
	 * 添加产品
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpd")
	public void addOrUpd(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,@RequestParam("file_one")MultipartFile[] file1,@RequestParam("file_two")MultipartFile[] file2
			,@RequestParam("file_three")MultipartFile[] file3,@RequestParam("file_four")MultipartFile[] file4) throws ParseException {
		System.out.println("数据："+mvm);
		JSONObject json=new JSONObject();
		int id=baseService.getMemberId();	//登录id	        
        String name=baseService.getMemberName();   //登录姓名
        if(name.equals("")){
        	json.put("code", 4);
        	json.put("message", "请重新登录");
        	resultresponse(response,json);
			return;
        }
		if(mvm.get("product_name") == null || mvm.get("company") == null || mvm.get("member_id") == null ||
				"".equals(mvm.get("product_name")) || "".equals(mvm.get("company")) || "".equals(mvm.get("member_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamProductService.addOrUpd(mvm,id,name, file2, file3, file4);
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
	 * 跳转修改项目页面
	 */
	@RequestMapping("/toedit")
	public String toedit(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("members", teamProductService.getAllMember());
		//添加项目页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProduct p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		publicResult(model);
		return "team/product/edit";
	}
	/**
	 * 修改项目
	 * @throws ParseException 
	 */
	@RequestMapping("/edit")
	public void edit(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) throws ParseException {
		JSONObject json=new JSONObject();
		if(mvm.get("product_name") == null || mvm.get("company") == null || mvm.get("member_id") == null ||
				"".equals(mvm.get("product_name")) || "".equals(mvm.get("company")) || "".equals(mvm.get("member_id"))
				 || "".equals(mvm.get("start_date")) || "".equals(mvm.get("end_date")) ||
					GiantUtil.isEmpty(mvm.get("type"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		//比较时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sd1=df.parse(mvm.get("start_date")+":00");
		Date sd2=df.parse(mvm.get("end_date")+":00");
		if(sd2.before(sd1) || mvm.get("start_date").equals(mvm.get("end_date"))){
				json.put("code",3);
				json.put("message", "结束时间必须在开始时间之后");
				resultresponse(response,json);
				return;
		}
		boolean flag = teamProductService.edit(mvm);
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
	 * 删除产品
	 */
	@RequestMapping("/del")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProduct p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setState((short)0);
			boolean flag = teamProductService.saveUpdateOrDelete(p, null);
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
	
	@RequestMapping("/toEditReport")
	public String toEditReport(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		Integer productId = GiantUtil.intOf(mvm.get("id"), 0);
		TaskProduct p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		if(p != null
				/*&& p.getState() == 4*/
				) {
			//获取项目下所有模块
			List<Map<String, Object>> need = teamProductService.getNeedByProject(productId);
			//获取项目下所有子模块
			List<Map<String, Object>> subNeed = teamProductService.getSubNeedByProject(productId);
			//获取所有模块下任务
			List<Map<String, Object>> needTask = teamProductService.getNeedTaskByProject(productId);
			//获取所有子模块下任务
			List<Map<String, Object>> subNeedTask = teamProductService.getSubNeedTaskByProject(productId);
			//获取所有任务下的所有测试用例列表
			List<Map<String, Object>> testCase = teamProductService.getTestCaseByProject(productId);
			//获取所有测试用例下的所有步骤
			List<Map<String, Object>> testCaseStep = teamProductService.getTestCaseStepByProject(productId);
			
			model.addAttribute("need", need);
			model.addAttribute("subNeed", subNeed);
			model.addAttribute("needTask", needTask);
			model.addAttribute("subNeedTask", subNeedTask);
			model.addAttribute("testCase", testCase);
			model.addAttribute("testCaseStep", testCaseStep);
		}
		publicResult(model);
		return "team/product/editReport";
	}
	
	@RequestMapping("/editReport")
	public void editReport(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) throws ParseException {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProduct p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setState((short)2);
			String actualEndTime = StringUtil.timestampToString(new Timestamp(System.currentTimeMillis()),StringUtil.SHORT_FORMATTER);
//			p.setActualEndTime(actualEndTime);
			boolean flag = teamProductService.saveUpdateOrDelete(p, null);
			if(flag){
				json.put("code",0);
				json.put("message", "编写验收报告成功");
			}else{
				json.put("code",1);
				json.put("message", "编写验收报告失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}
	
	@RequestMapping("/toCheck")
	public String toCheck(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		Integer productId = GiantUtil.intOf(mvm.get("id"), 0);
		TaskProduct p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		if(p != null
				/*&& p.getState() == 4*/
				) {
			//获取项目下所有模块
			List<Map<String, Object>> need = teamProductService.getNeedByProject(productId);
			//获取项目下所有子模块
			List<Map<String, Object>> subNeed = teamProductService.getSubNeedByProject(productId);
			//获取所有模块下任务
			List<Map<String, Object>> needTask = teamProductService.getNeedTaskByProject(productId);
			//获取所有子模块下任务
			List<Map<String, Object>> subNeedTask = teamProductService.getSubNeedTaskByProject(productId);
			//获取所有任务下的所有测试用例列表
			List<Map<String, Object>> testCase = teamProductService.getTestCaseByProject(productId);
			//获取所有测试用例下的所有步骤
			List<Map<String, Object>> testCaseStep = teamProductService.getTestCaseStepByProject(productId);
			
			model.addAttribute("need", need);
			model.addAttribute("subNeed", subNeed);
			model.addAttribute("needTask", needTask);
			model.addAttribute("subNeedTask", subNeedTask);
			model.addAttribute("testCase", testCase);
			model.addAttribute("testCaseStep", testCaseStep);
		}
		publicResult(model);
		return "team/product/check";
	}
	
	@RequestMapping("/check")
	public void check(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) throws ParseException {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProduct p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			String notThrough =  GiantUtil.stringOf(mvm.get("notThrough"));
			//项目验收不通过的情况
			/*if ("0".equals(notThrough)) {
				
			}*/
			p.setState((short)3);
			boolean flag = teamProductService.saveUpdateOrDelete(p, null);
			if(flag){
				json.put("code",0);
				json.put("message", "产品验收成功");
			}else{
				json.put("code",1);
				json.put("message", "产品验收失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}
	
	@RequestMapping("/toFinish")
	public String toFinish(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		Integer productId = GiantUtil.intOf(mvm.get("id"), 0);
		TaskProduct p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		if(p != null
				/*&& p.getState() == 4*/
				) {
			//获取项目下所有模块
			List<Map<String, Object>> need = teamProductService.getNeedByProject(productId);
			//获取项目下所有子模块
			List<Map<String, Object>> subNeed = teamProductService.getSubNeedByProject(productId);
			//获取所有模块下任务
			List<Map<String, Object>> needTask = teamProductService.getNeedTaskByProject(productId);
			//获取所有子模块下任务
			List<Map<String, Object>> subNeedTask = teamProductService.getSubNeedTaskByProject(productId);
			//获取所有任务下的所有测试用例列表
			List<Map<String, Object>> testCase = teamProductService.getTestCaseByProject(productId);
			//获取所有测试用例下的所有步骤
			List<Map<String, Object>> testCaseStep = teamProductService.getTestCaseStepByProject(productId);
			
			model.addAttribute("need", need);
			model.addAttribute("subNeed", subNeed);
			model.addAttribute("needTask", needTask);
			model.addAttribute("subNeedTask", subNeedTask);
			model.addAttribute("testCase", testCase);
			model.addAttribute("testCaseStep", testCaseStep);
		}
		publicResult(model);
		return "team/product/finish";
	}
	
	@RequestMapping("/finish")
	public void finish(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) throws ParseException {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProduct p = (TaskProduct) teamProductService.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setState((short)4);
			boolean flag = teamProductService.saveUpdateOrDelete(p, null);
			if(flag){
				json.put("code",0);
				json.put("message", "确认产品完成成功");
			}else{
				json.put("code",1);
				json.put("message", "确认产品完成失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}
	
}
