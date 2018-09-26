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
import com.zzidc.team.entity.TaskProject;
import com.zzidc.team.service.TeamProjectService;

import net.sf.json.JSONObject;

/**
 * 项目管理
 */
@Controller
@RequestMapping({ "/team/project" })
public class TeamProjectController extends GiantBaseController {
	@Autowired
	private TeamProjectService teamProjectService;
	@Autowired
	private GiantBaseService baseService;
	private GiantPager conditionPage = null;
	private String requestURL = "team/project/index";

	public void publicResult(Model model) {
		model.addAttribute("m", "project");//模块
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
		pageList = teamProjectService.getPageList(conditionPage);
		requestURL = "team/project/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("members", teamProjectService.getAllMember());
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "team/project/list";
	}

	/**
	 * 项目详情
	 */
	@RequestMapping("/pro_detail")
	public String pro_detail(@RequestParam Map<String, String> mvm, Model model) {
		//添加项目页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProject p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
			Member member = (Member) teamProjectService.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(p.getMemberId(), 0));
			model.addAttribute("member_name", member == null ? "" : member.getName());
			
			List<Map<String, Object>> needTask = teamProjectService.getRelationTaskList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("needTask", needTask);
		}
		publicResult(model);
		return "team/project/pro_detail";
	}
	
	/**
	 * 科技申报-项目详情
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取成果对象
			Map<String, Object> projectDetail = teamProjectService.getProjectDetail(GiantUtil.intOf(mvm.get("id"), 0));
			if(projectDetail == null) {
				return "comm/notexists";
			}
			model.addAttribute("projectM", projectDetail);

			//获取项目成果
			List<Map<String, Object>> pr = teamProjectService.getProjectResult(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("pr", pr);
			
			//获取项目文档
			List<Map<String, Object>> doc = teamProjectService.getProjectDoc(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("doc", doc);
		}
		publicResult(model);
		return "team/project/detail";
	}
	
	/**
	 * 跳转添加项目页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("members", teamProjectService.getAllMember());
		//添加项目页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProject p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		publicResult(model);
		return "team/project/add";
	}
	
	/**
	 * 添加项目
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpd")
	public void addOrUpd(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("file_one")MultipartFile[] file1,@RequestParam("file_two")MultipartFile[] file2
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
        if(mvm.get("project_name") == null || mvm.get("company") == null || 
 		   mvm.get("customer_name") == null || mvm.get("demand_id") == null || 
 		   mvm.get("time_limit") == null || mvm.get("budget") == null || 
 		   mvm.get("file_url") == null || mvm.get("assessment") == null || 
 		   mvm.get("member_id") == null || mvm.get("projectMembers") == null ||
 		   mvm.get("testMembers") == null || mvm.get("overtime") == null ||
 		   mvm.get("start_date") == null || mvm.get("end_date") == null ||
 		   "".equals(mvm.get("project_name")) || "".equals(mvm.get("company")) || 
 		   "".equals(mvm.get("customer_name")) || "".equals(mvm.get("demand_id")) || 
 		   "".equals(mvm.get("time_limit")) || "".equals(mvm.get("budget")) || 
 		   "".equals(mvm.get("file_url")) || "".equals(mvm.get("assessment")) || 
 		   "".equals(mvm.get("member_id")) || "".equals(mvm.get("projectMembers")) ||
 		   "".equals(mvm.get("testMembers")) || "".equals(mvm.get("overtime")) ||
 		   "".equals(mvm.get("start_date")) || "".equals(mvm.get("end_date"))
//     	   || GiantUtil.isEmpty(mvm.get("type"))
 				 ){
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
		boolean flag = teamProjectService.addOrUpd(mvm,request,id,name,file1, file2, file3, file4);
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
		model.addAttribute("members", teamProjectService.getAllMember());
		//添加项目页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProject p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		String querySqlProject = "select me.id memberId, me.name name from project_member pm " + 
				"LEFT JOIN task_project tp on pm.project_id = tp.id " + 
		        "LEFT JOIN member me on pm.member_id = me.id " + 
		        "where pm.member_type = 1 and tp.id = " + mvm.get("id");
		String querySqlTest = "select me.id memberId, me.name name from project_member pm " + 
				"LEFT JOIN task_project tp on pm.project_id = tp.id " + 
				"LEFT JOIN member me on pm.member_id = me.id " + 
				"where pm.member_type = 2 and tp.id = " + mvm.get("id");//
		//项目人员
		List<Map<String, Object>> listProject = teamProjectService.getMapListBySQL(querySqlProject, null);
		//测试人员
		List<Map<String, Object>> listTest = teamProjectService.getMapListBySQL(querySqlTest, null);
		model.addAttribute("listProject", listProject);
		model.addAttribute("listTest", listTest);
		publicResult(model);
		return "team/project/edit";
	}
	/**
	 * 修改项目
	 * @throws ParseException 
	 */
	@RequestMapping("/edit")
	public void edit(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) throws ParseException {
		JSONObject json=new JSONObject();
		if(mvm.get("project_name") == null || mvm.get("company") == null || 
		   mvm.get("customer_name") == null || mvm.get("demand_id") == null || 
		   mvm.get("time_limit") == null || mvm.get("budget") == null || 
		   mvm.get("file_url") == null || mvm.get("assessment") == null || 
		   mvm.get("member_id") == null || mvm.get("projectMembers") == null ||
		   mvm.get("testMembers") == null || mvm.get("overtime") == null ||
		   mvm.get("start_date") == null || mvm.get("end_date") == null ||
		   "".equals(mvm.get("project_name")) || "".equals(mvm.get("company")) || 
		   "".equals(mvm.get("customer_name")) || "".equals(mvm.get("demand_id")) || 
		   "".equals(mvm.get("time_limit")) || "".equals(mvm.get("budget")) || 
		   "".equals(mvm.get("file_url")) || "".equals(mvm.get("assessment")) || 
		   "".equals(mvm.get("member_id")) || "".equals(mvm.get("projectMembers")) ||
		   "".equals(mvm.get("testMembers")) || "".equals(mvm.get("overtime")) ||
		   "".equals(mvm.get("start_date")) || "".equals(mvm.get("end_date"))
//				   || GiantUtil.isEmpty(mvm.get("type"))
				 ){
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
		boolean flag = teamProjectService.edit(mvm,request);
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
			TaskProject p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setState((short)0);
			boolean flag = teamProjectService.saveUpdateOrDelete(p, null);
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
		Integer projectId = GiantUtil.intOf(mvm.get("id"), 0);
		TaskProject p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		if(p != null
				/*&& p.getState() == 4*/
				) {
			//获取项目下所有模块
			List<Map<String, Object>> need = teamProjectService.getNeedByProject(projectId);
			//获取项目下所有子模块
			List<Map<String, Object>> subNeed = teamProjectService.getSubNeedByProject(projectId);
			//获取所有模块下任务
			List<Map<String, Object>> needTask = teamProjectService.getNeedTaskByProject(projectId);
			//获取所有子模块下任务
			List<Map<String, Object>> subNeedTask = teamProjectService.getSubNeedTaskByProject(projectId);
			//获取所有任务下的所有测试用例列表
			List<Map<String, Object>> testCase = teamProjectService.getTestCaseByProject(projectId);
			//获取所有测试用例下的所有步骤
			List<Map<String, Object>> testCaseStep = teamProjectService.getTestCaseStepByProject(projectId);
			
			model.addAttribute("need", need);
			model.addAttribute("subNeed", subNeed);
			model.addAttribute("needTask", needTask);
			model.addAttribute("subNeedTask", subNeedTask);
			model.addAttribute("testCase", testCase);
			model.addAttribute("testCaseStep", testCaseStep);
		}
		publicResult(model);
		return "team/project/editReport";
	}
	
	@RequestMapping("/editReport")
	public void editReport(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) throws ParseException {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProject p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setState((short)2);
			String actualEndTime = StringUtil.timestampToString(new Timestamp(System.currentTimeMillis()),StringUtil.SHORT_FORMATTER);
			p.setActualEndTime(actualEndTime);
			boolean flag = teamProjectService.saveUpdateOrDelete(p, null);
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
		Integer projectId = GiantUtil.intOf(mvm.get("id"), 0);
		TaskProject p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		if(p != null
				/*&& p.getState() == 4*/
				) {
			//获取项目下所有模块
			List<Map<String, Object>> need = teamProjectService.getNeedByProject(projectId);
			//获取项目下所有子模块
			List<Map<String, Object>> subNeed = teamProjectService.getSubNeedByProject(projectId);
			//获取所有模块下任务
			List<Map<String, Object>> needTask = teamProjectService.getNeedTaskByProject(projectId);
			//获取所有子模块下任务
			List<Map<String, Object>> subNeedTask = teamProjectService.getSubNeedTaskByProject(projectId);
			//获取所有任务下的所有测试用例列表
			List<Map<String, Object>> testCase = teamProjectService.getTestCaseByProject(projectId);
			//获取所有测试用例下的所有步骤
			List<Map<String, Object>> testCaseStep = teamProjectService.getTestCaseStepByProject(projectId);
			
			model.addAttribute("need", need);
			model.addAttribute("subNeed", subNeed);
			model.addAttribute("needTask", needTask);
			model.addAttribute("subNeedTask", subNeedTask);
			model.addAttribute("testCase", testCase);
			model.addAttribute("testCaseStep", testCaseStep);
		}
		publicResult(model);
		return "team/project/check";
	}
	
	@RequestMapping("/check")
	public void check(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) throws ParseException {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProject p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			String notThrough =  GiantUtil.stringOf(mvm.get("notThrough"));
			//项目验收不通过的情况
			/*if ("0".equals(notThrough)) {
				
			}*/
			p.setState((short)3);
			boolean flag = teamProjectService.saveUpdateOrDelete(p, null);
			if(flag){
				json.put("code",0);
				json.put("message", "项目验收成功");
			}else{
				json.put("code",1);
				json.put("message", "项目验收失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}
	
	@RequestMapping("/toFinish")
	public String toFinish(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		Integer projectId = GiantUtil.intOf(mvm.get("id"), 0);
		TaskProject p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		if(p != null
				/*&& p.getState() == 4*/
				) {
			//获取项目下所有模块
			List<Map<String, Object>> need = teamProjectService.getNeedByProject(projectId);
			//获取项目下所有子模块
			List<Map<String, Object>> subNeed = teamProjectService.getSubNeedByProject(projectId);
			//获取所有模块下任务
			List<Map<String, Object>> needTask = teamProjectService.getNeedTaskByProject(projectId);
			//获取所有子模块下任务
			List<Map<String, Object>> subNeedTask = teamProjectService.getSubNeedTaskByProject(projectId);
			//获取所有任务下的所有测试用例列表
			List<Map<String, Object>> testCase = teamProjectService.getTestCaseByProject(projectId);
			//获取所有测试用例下的所有步骤
			List<Map<String, Object>> testCaseStep = teamProjectService.getTestCaseStepByProject(projectId);
			
			model.addAttribute("need", need);
			model.addAttribute("subNeed", subNeed);
			model.addAttribute("needTask", needTask);
			model.addAttribute("subNeedTask", subNeedTask);
			model.addAttribute("testCase", testCase);
			model.addAttribute("testCaseStep", testCaseStep);
		}
		publicResult(model);
		return "team/project/finish";
	}
	
	@RequestMapping("/finish")
	public void finish(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) throws ParseException {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskProject p = (TaskProject) teamProjectService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setState((short)4);
			boolean flag = teamProjectService.saveUpdateOrDelete(p, null);
			if(flag){
				json.put("code",0);
				json.put("message", "确认项目完成成功");
			}else{
				json.put("code",1);
				json.put("message", "确认项目完成失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}
	
}
