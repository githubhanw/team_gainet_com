package com.zzidc.team.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.MilepostManage;
import com.zzidc.team.entity.MilepostTaskneed;
import com.zzidc.team.entity.TaskNeed;
import com.zzidc.team.entity.TaskProject;
import com.zzidc.team.service.TestMilepostService;

import net.sf.json.JSONObject;
/**
 * [里程碑 Controller]
 * @author chenmenghao
 * @date 2018年7月30日 下午2:53:26
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Controller
@RequestMapping({ "/test/milepost" })
public class TestMilepostController extends GiantBaseController {
	private GiantPager conditionPage = null;
	@Autowired
	private TestMilepostService testMilepostService;
	@Autowired
	private GiantBaseService baseService;
	private String requestURL = "test/milepost/index";
	public void publicResult(Model model) {
		model.addAttribute("m", "milepost");//模块
		model.addAttribute("s", "manage");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	/**
	 * 首页
	 */
	@RequestMapping("/manage")
	public String manage(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "id");
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
		pageList = testMilepostService.getPageList(conditionPage);
		requestURL = "test/milepost/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("members", testMilepostService.getAllMember());
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "test/milepost/index";
	}
	/**
	 * 跳到详情页面
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			MilepostManage m = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("mi", m);
			List<Map<String, Object>> needNameList=baseService.getMapListBySQL("SELECT tn.need_name FROM milepost_taskneed mt LEFT JOIN task_need tn ON mt.taskneed_id=tn.id WHERE mt.milepost_id = "+mvm.get("id"),null);
			model.addAttribute("needNameList", needNameList);
			Object a=baseService.getSingleDataBySql("SELECT tp.project_name FROM milepost_manage mm LEFT JOIN task_project tp ON mm.project_id = tp.id WHERE mm.id = "+mvm.get("id"),null);
			model.addAttribute("projrckname", a.toString());
		}
		
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/detail";
		//return "milepost/manage/add";
	}	
	/**
	 * 跳到编辑页面
	 */
	@RequestMapping("/toedit")
	public String toedit(@RequestParam Map<String, String> mvm, Model model) {
		String id=mvm.get("id");
		model.addAttribute("id", id );
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			MilepostManage m = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("mi", m);
		}
		
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/edit";
		//return "milepost/manage/add";
	}	
	/**
	 * 编辑
	 * @throws ParseException 
	 */
	@RequestMapping("/edit")
	public void edit(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) throws ParseException {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("mi_id")) || GiantUtil.isEmpty(mvm.get("milepost_name")) || GiantUtil.isEmpty(mvm.get("starttime")) ||GiantUtil.isEmpty(mvm.get("endtime")) || 
				GiantUtil.isEmpty(mvm.get("author_name")) || GiantUtil.isEmpty(mvm.get("mark"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		//比较时间
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date sd1=df.parse(mvm.get("starttime")+":00");
		Date sd2=df.parse(mvm.get("endtime")+":00");
		if(sd2.before(sd1) || mvm.get("starttime").equals(mvm.get("endtime"))){
			json.put("code",3);
			json.put("message", "结束时间必须在开始时间之后");
			resultresponse(response,json);
			return;
		}
		boolean flag = testMilepostService.edit(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "添加成功");
		}else{
			json.put("code",1);
			json.put("message", "添加失败");
		}
		resultresponse(response,json);
	}
	/**
	 * 跳到创建页面
	 */
	@RequestMapping("/toadd")
	public String toadd(@RequestParam Map<String, String> mvm, Model model) {
		List<Map<String, Object>> taskNeedList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> taskProjectList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT id,need_name FROM `task_need`";
		String sqls = "SELECT id,project_name FROM `task_project`";
		taskNeedList = baseService.getMapListBySQL(sql, null);
		taskProjectList = baseService.getMapListBySQL(sqls, null);
		model.addAttribute("taskNeed", taskNeedList);//查询出来的需求id和名称
		model.addAttribute("taskProjectList", taskProjectList);//查询出来的项目id和名称
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/add";
		//return "milepost/manage/add";
	}
	/**
	 * 创建
	 * @throws ParseException 
	 */
	@RequestMapping("/add")
	public void add(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,HttpServletRequest request) throws ParseException {
		JSONObject json=new JSONObject();
		int id=baseService.getMemberId();	//登录id	        
        String name=baseService.getMemberName();   //登录姓名
        if(name.equals("")){
        	json.put("code", 2);
        	json.put("message", "请重新登录");
        	resultresponse(response,json);
			return;
        }
		if(GiantUtil.isEmpty(mvm.get("need_name")) || GiantUtil.isEmpty(mvm.get("start_date")) || 
				GiantUtil.isEmpty(mvm.get("end_date")) || GiantUtil.isEmpty(mvm.get("need_remark"))
				 || GiantUtil.isEmpty(mvm.get("task_needid"))){
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
		boolean flag=testMilepostService.add(mvm, id, name, request);
		if(flag){
			json.put("code",0);
			json.put("message", "添加成功");
		}else{
			json.put("code",1);
			json.put("message", "添加失败");
		}
		resultresponse(response,json);
	}
	/**
	 * 跳到批量创建页面
	 */
	@RequestMapping("/toBatchAdd")
	public String toBatchAdd(@RequestParam Map<String, String> mvm, Model model) {
		List<Map<String, Object>> taskNeedList = new ArrayList<Map<String, Object>>();
		if(!GiantUtil.isEmpty(mvm.get("project_id"))){
		String sql = "SELECT id,need_name FROM `task_need`";
		TaskProject tp = (TaskProject) testMilepostService.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("project_id"), 0));
		taskNeedList = baseService.getMapListBySQL(sql, null);
		model.addAttribute("taskNeed", taskNeedList);//查询出来的任务id和名称
		model.addAttribute("tp", tp);//查询出来的项目数据
		}
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/batchAdd";
	}
	/**
	 * 跳到确认页面
	 */
	@RequestMapping("/tosure")
	public String tosure(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			MilepostManage m = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("mi", m);
		}
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/sure";
	}
	/**
	 * 确认
	 */
	@RequestMapping("/sure")
	public void sure(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		boolean flag = testMilepostService.sure(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "成功");
		}else{
			json.put("code",1);
			json.put("message", "失败");
		}
		resultresponse(response,json);
	}
	/**
	 * 跳转到删除界面
	 */
	@RequestMapping("/todelete")
	public String todelete(@RequestParam Map<String, String> mvm, Model model) {
		        //添加需求页面的项目列表
				if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
					//获取对象
					MilepostManage t = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
					model.addAttribute("mi", t);
				}
				publicResult(model);
				return "test/milepost/delete";
	}
	/**
	 * 删除界面
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		//添加需求页面的项目列表
	if(GiantUtil.intOf(mvm.get("mi_id"), 0) != 0){
			//获取对象
			MilepostManage t = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("mi_id"), 0));
		    t.setMilepostState("0");
		    t.setMilepostRemarks(mvm.get("comment"));
		boolean flag = testMilepostService.saveUpdateOrDelete(t,null);
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
	/**
	 * 跳转验收页面
	 */
	@RequestMapping("/tovali")
	public String tovali(@RequestParam Map<String, String> mvm, Model model) {
        String name=baseService.getMemberName();   //登录姓名
        Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			MilepostManage m = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
			//获取相关模块
			List<Map<String, Object>> milNeedList=testMilepostService.getMilNeedList(mvm);
			model.addAttribute("milNeedList", milNeedList);
			model.addAttribute("mi", m);
			model.addAttribute("name", name);
			model.addAttribute("finishtime", createTime);
		}
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/vali";
	}
	/**
	 * 验收
	 */
	@RequestMapping("vali")
	public void vali(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		int id=baseService.getMemberId();	//登录id	        
        String name=baseService.getMemberName();   //登录姓名
        if(name.equals("")){
        	json.put("code", 4);
        	json.put("message", "请重新登录");
        	resultresponse(response,json);
			return;
        }
        boolean flag = testMilepostService.vali(mvm,id,name);
		if(flag){
			json.put("code",0);
			json.put("message", "验收成功");
		}else{
			json.put("code",1);
			json.put("message", "验收失败");
		}
			resultresponse(response,json);
		}	
	/**
	 * 跳转关联页面
	 */
	@RequestMapping("/toass")
	public String toass(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			List<Map<String, Object>> taskNeedList = new ArrayList<Map<String, Object>>();
			String sql = "SELECT id,need_name FROM `task_need`";
			taskNeedList = baseService.getMapListBySQL(sql, null);
			//获取对象
			MilepostManage m = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("taskNeed", taskNeedList);
			model.addAttribute("mi", m);
		}
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/ass";
	}
	/**
	 * 关联
	 */
	@RequestMapping("/ass")
	public void ass(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("handover_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testMilepostService.ass(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "添加成功");
		}else{
			json.put("code",1);
			json.put("message", "添加失败");
		}
		resultresponse(response,json);
	}
	/**
	 * 跳转确认里程碑和界面原型页面
	 */
	@RequestMapping("/tosureui")
	public String tosureui(@RequestParam Map<String, String> mvm, Model model) {
		
		
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			MilepostManage m = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("mi", m);
			List<Map<String, Object>> milNeedList=testMilepostService.getMilNeedList(mvm);
			model.addAttribute("milNeedList", milNeedList);
		}
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/sureui";
	}
	/**
	 * 确认里程碑和界面原型页面
	 */
	@RequestMapping("/sureui")
	public void sureui(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		boolean flag = testMilepostService.sureui(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "确认成功");
		}else{
			json.put("code",1);
			json.put("message", "确认失败");
		}
		resultresponse(response,json);
	}
	/**
	 * 跳转编写里程碑报告页面
	 */
	@RequestMapping("/toreport")
	public String toreport(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			MilepostManage m = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("mi", m);
			showTreeMsg(model, null, m.getProjectId());
		}
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/report";
	}
	/**
	 * 提交里程碑报告
	 */
	@RequestMapping("/report")
	public void report(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		boolean flag = testMilepostService.report(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "提交成功");
		}else{
			json.put("code",1);
			json.put("message", "提交失败");
		}
		resultresponse(response,json);
	}
	/**
	 * 跳转验收里程碑报告页面
	 */
	@RequestMapping("/tovailreport")
	public String tovailreport(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			MilepostManage m = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("mi", m);
			showTreeMsg(model, null, m.getProjectId());
		}
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "test/milepost/vailreport";
	}
	/**
	 * 验收里程碑报告
	 */
	@RequestMapping("/vailreport")
	public void vailreport(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		boolean flag = testMilepostService.vailreport(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "提交成功");
		}else{
			json.put("code",1);
			json.put("message", "提交失败");
		}
		resultresponse(response,json);
	}
	/**
	 * 显示添加/编辑页面树信息
	 * @param model
	 * @param taskId
	 * @param needId
	 * @param projectId
	 * @param productId
	 */
	public void showTreeMsg(Model model, Integer needId, Integer projectId) {
		int applyType = 0;
		if (needId != null && needId > 0) {//需求（模块）：获取模块下所有子模块，模块、子模块下所有任务（包含原型图、流程图），所有任务下的测试用例；【获取已完成的任务、已验收的模块】
			applyType = 2;
			TaskNeed n = (TaskNeed) testMilepostService.getEntityByPrimaryKey(new TaskNeed(), needId);
			if(n != null && n.getState() == 4) {
				//获取子模块
				List<Map<String, Object>> subNeed = testMilepostService.getSubNeedByNeed(needId);
				//获取子模块下的任务
				List<Map<String, Object>> subNeedTask = testMilepostService.getSubNeedTaskByNeed(needId);
				//获取模块下的任务
				List<Map<String, Object>> needTask = testMilepostService.getTaskByNeed(needId);
				//获取所有任务下的所有测试用例列表
				List<Map<String, Object>> testCase = testMilepostService.getTestCaseByNeed(needId);
				//获取所有测试用例下的所有步骤
				List<Map<String, Object>> testCaseStep = testMilepostService.getTestCaseStepByNeed(needId);
				
				model.addAttribute("n", n);
				model.addAttribute("subNeed", subNeed);
				model.addAttribute("subNeedTask", subNeedTask);
				model.addAttribute("needTask", needTask);
				model.addAttribute("testCase", testCase);
				model.addAttribute("testCaseStep", testCaseStep);
			}
		} else if (projectId != null && projectId > 0) {//项目：获取项目下所有模块，模块下所有子模块，模块、子模块下所有任务（包含原型图、流程图），任务下的测试用例；【获取已完成的任务、已验收的模块】
			applyType = 3;
			TaskProject p = (TaskProject) testMilepostService.getEntityByPrimaryKey(new TaskProject(), projectId);
			if(p != null && p.getState() == 4) {
				//获取项目下所有模块
				List<Map<String, Object>> need = testMilepostService.getNeedByProject(projectId);
				//获取项目下所有子模块
				List<Map<String, Object>> subNeed = testMilepostService.getSubNeedByProject(projectId);
				//获取所有模块下任务
				List<Map<String, Object>> needTask = testMilepostService.getNeedTaskByProject(projectId);
				//获取所有子模块下任务
				List<Map<String, Object>> subNeedTask = testMilepostService.getSubNeedTaskByProject(projectId);
				//获取所有任务下的所有测试用例列表
				List<Map<String, Object>> testCase = testMilepostService.getTestCaseByProject(projectId);
				//获取所有测试用例下的所有步骤
				List<Map<String, Object>> testCaseStep = testMilepostService.getTestCaseStepByProject(projectId);
				
				model.addAttribute("p", p);
				model.addAttribute("need", need);
				model.addAttribute("subNeed", subNeed);
				model.addAttribute("needTask", needTask);
				model.addAttribute("subNeedTask", subNeedTask);
				model.addAttribute("testCase", testCase);
				model.addAttribute("testCaseStep", testCaseStep);
			}
		} else {//原来的不动
			
		}
		model.addAttribute("applyType", applyType);
	}
}
