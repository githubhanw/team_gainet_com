package com.zzidc.my.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.Task;
import com.zzidc.team.entity.TestApply;
import com.zzidc.team.service.TeamTaskService;
import com.zzidc.team.service.TestApplyService;

import net.sf.json.JSONObject;

/**
 * 任务管理
 */
@Controller
@RequestMapping({ "/my/task" })
public class MyTaskController extends GiantBaseController {
	@Autowired
	private TeamTaskService teamTaskService;
	@Autowired
	private TestApplyService testApplyService;
	private String requestURL = "my/task";

	public void publicResult(Model model) {
		model.addAttribute("m", "my");//模块
		model.addAttribute("s", "task");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}

	/**
	 * 任务列表在:MyController
	 */

	/**
	 * 任务详情
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			Map<String, Object> taskDetail = teamTaskService.getTaskDetail(GiantUtil.intOf(mvm.get("id"), 0));
			if(taskDetail == null) {
				return "comm/notexists";
			}
			model.addAttribute("taskM", taskDetail);
			List<Map<String, Object>> subTask = teamTaskService.getSubTaskList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("subTask", subTask);

			//同模块任务
			if ("3".equals(taskDetail.get("task_type"))) {//测试任务
				List<Map<String, Object>> needTask = teamTaskService.getRelationTaskList(null, GiantUtil.intOf(mvm.get("id"), 0), 2);
				model.addAttribute("needTask1", needTask);
			} else {//非测试任务
				List<Map<String, Object>> needTask = teamTaskService.getRelationTaskList(GiantUtil.intOf(taskDetail.get("need_id"), 0), GiantUtil.intOf(mvm.get("id"), 0), 1);
				model.addAttribute("needTask1", needTask);
			}
			//非测试任务对应的测试任务
			if (!"3".equals(taskDetail.get("task_type"))) {
				List<Map<String, Object>> testTask = teamTaskService.getRelationTaskList(null, GiantUtil.intOf(mvm.get("id"), 0), 2);
				model.addAttribute("testTask", testTask);
			}
			//关联任务
			if(taskDetail.get("link") != null && !"".equals(taskDetail.get("link"))) {
				List<Map<String, Object>> linkTask = teamTaskService.getLinkTask(taskDetail.get("link").toString());
				model.addAttribute("linkTask", linkTask);
			}
			//开发任务获取 测试用例和代码审查
			if("1".equals(taskDetail.get("task_type").toString())) {
				List<Map<String, Object>> testCase = teamTaskService.getTestCase(GiantUtil.intOf(mvm.get("id"), 0));
				model.addAttribute("testCase", testCase);
				List<Map<String, Object>> testCaseStep = teamTaskService.getTestCaseStep(GiantUtil.intOf(mvm.get("id"), 0));
				model.addAttribute("testCaseStep", testCaseStep);
				List<Map<String, Object>> codeReport = teamTaskService.getCodeReport(GiantUtil.intOf(mvm.get("id"), 0));
				model.addAttribute("codeReport", codeReport);
			}
			//测试任务获取 模块详情树
			if("2".equals(taskDetail.get("task_type").toString())) {
				TestApply n = (TestApply) testApplyService.getEntityByPrimaryKey(new TestApply(), GiantUtil.intOf(taskDetail.get("test_apply_id"), 0));
				if(n != null) {
					model.addAttribute("apply", n);
					testApplyService.showTreeMsg(model, n.getId(), n.getTaskId(), n.getNeedId(), n.getProjectId(), n.getProductId(), "3,4,5", GiantUtil.intOf(taskDetail.get("assigned_id"), 0));
				}
			}
			//日志
			List<Map<String, Object>> logList = teamTaskService.getLogList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("logList", logList);
		}
		publicResult(model);
		return "my/task/detail";
	}

	/**
	 * 跳转添加任务页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("need", teamTaskService.getNeed());
		model.addAttribute("need_id", GiantUtil.intOf(mvm.get("need_id"), 0));
		model.addAttribute("members", teamTaskService.getAllMember());
		publicResult(model);
		return "my/task/add";
	}
	/**
	 * 跳转添加原型图和流程图页面
	 */
	@RequestMapping("/toaddPicture")
	public String toaddPicture(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "team/task/addPicture";
	}
	/**
	 * 添加原型图和流程图
	 */
	@RequestMapping("/addPicture")
	public void addPicture(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response
			,@RequestParam("filePrototype")MultipartFile[] filePrototype,@RequestParam("filetree")MultipartFile[] filetree) {
		JSONObject json=new JSONObject();
		String prototypeName = filePrototype[0].getOriginalFilename();
		String treeName = filetree[0].getOriginalFilename();
		if(prototypeName.equals("") && treeName.equals("")){
			json.put("code",2);
			json.put("message", "请选择");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.addPicture(mvm, filePrototype, filetree);
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
	 * 跳转删除原型图和流程图页面
	 */
	@RequestMapping("/todelPicture")
	public String todelPicture(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "team/task/delPicture";
	}
	/**
	 * 删除原型图和流程图
	 */
	@RequestMapping("/delPicture")
	public void delPicture(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,HttpServletRequest request){
		JSONObject json=new JSONObject();
		if(request.getParameterValues("checkinterface")==null && request.getParameterValues("checkfolw")==null){
			json.put("code",2);
			json.put("message", "请选择要删除的图片");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.delPicture(mvm,request);
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
	 * 添加任务
	 */
	@RequestMapping("/add")
	public void add(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("task_name")) || GiantUtil.isEmpty(mvm.get("assigned_id")) || 
				GiantUtil.isEmpty(mvm.get("task_type")) || GiantUtil.isEmpty(mvm.get("need_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		
		boolean flag = teamTaskService.add(mvm);
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
	 * 添加任务
	 */
	@RequestMapping("/addTask")
	public void addTask(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response
			,@RequestParam("filePrototype")MultipartFile[] filePrototype,@RequestParam("filetree")MultipartFile[] filetree) {
		JSONObject json=new JSONObject();
		String prototypeName = filePrototype[0].getOriginalFilename();
		String treeName = filetree[0].getOriginalFilename();
		if(prototypeName.equals("") && treeName.equals("")){
			json.put("code",2);
			json.put("message", "请选择界面原型文件/流程图");
			resultresponse(response,json);
			return;
		}
//		if(treeName.equals("")){
//			json.put("code",3);
//			json.put("message", "请选择流程图");
//			resultresponse(response,json);
//			return;
//		}
		if(GiantUtil.isEmpty(mvm.get("task_name")) || GiantUtil.isEmpty(mvm.get("assigned_id")) || 
				GiantUtil.isEmpty(mvm.get("task_type")) || GiantUtil.isEmpty(mvm.get("need_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		
		boolean flag = teamTaskService.addTask(mvm,filePrototype, filetree);
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
	 * 跳转编辑模块页面
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		model.addAttribute("need", teamTaskService.getNeed());
		model.addAttribute("members", teamTaskService.getAllMember());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
			//日志
			List<Map<String, Object>> logList = teamTaskService.getLogList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("logList", logList);
		}
		publicResult(model);
		return "my/task/edit";
	}

	/**
	 * 编辑任务
	 */
	@RequestMapping("/edit")
	public void edit(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("level")) || GiantUtil.isEmpty(mvm.get("state")) || 
				GiantUtil.isEmpty(mvm.get("task_type")) || GiantUtil.isEmpty(mvm.get("need_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		
		boolean flag = teamTaskService.edit(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "编辑成功");
		}else{
			json.put("code",1);
			json.put("message", "编辑失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 跳转批量添加任务页面
	 */
	@RequestMapping("/toBatchAdd")
	public String toBatchAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		model.addAttribute("need", teamTaskService.getNeed());
		model.addAttribute("members", teamTaskService.getAllMember());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamTaskService.isCurrentMember(t.getMemberId())) {
				return "nopower";
			}
			model.addAttribute("t", t);
		}
		model.addAttribute("need_id", mvm.get("need_id"));
		publicResult(model);
		return "my/task/batchAdd";
	}

	/**
	 * 跳转指派页面
	 */
	@RequestMapping("/toAssign")
	public String toAssign(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("members", teamTaskService.getAllMember());
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/assign";
	}

	/**
	 * 指派给
	 */
	@RequestMapping("/assign")
	public void assign(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("assigned_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.assign(mvm);
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
	 * 跳转变更任务页面
	 */
	@RequestMapping("/toChange")
	public String toChange(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamTaskService.isCurrentMember(t.getMemberId())) {
				return "nopower";
			}
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/change";
	}

	/**
	 * 变更任务
	 */
	@RequestMapping("/change")
	public void change(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("task_name"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.change(mvm);
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
	 * 跳转关闭模块页面
	 */
	@RequestMapping("/toClose")
	public String toClose(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamTaskService.isCurrentMember(t.getMemberId())) {
				return "nopower";
			}
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/close";
	}

	/**
	 * 关闭模块
	 */
	@RequestMapping("/close")
	public void close(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.close(mvm);
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
	 * 跳转激活模块页面
	 */
	@RequestMapping("/toActive")
	public String toActive(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/active";
	}

	/**
	 * 激活模块
	 */
	@RequestMapping("/active")
	public void active(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.active(mvm);
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
	 * 跳转开始/接收任务页面
	 */
	@RequestMapping("/toOpen")
	public String toOpen(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamTaskService.isCurrentMember(t.getAssignedId())) {
				return "nopower";
			}
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/open";
	}

	/**
	 * 开始/接收任务
	 */
	@RequestMapping("/open")
	public void open(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("plan_end_date"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.open(mvm);
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
	 * 跳转任务暂停页面
	 * @param mvm
	 * @param model
	 * @return
	 */
	@RequestMapping("/toPause")
	public String toPause(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/pause";
	}

	/**
	 * 任务暂停
	 * @param mvm
	 * @param model
	 * @param response
	 */
	@RequestMapping("/pause")
	public void pause(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.pause(mvm);
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
	 * 跳转任务取消页面
	 * @param mvm
	 * @param model
	 * @return
	 */
	@RequestMapping("/toCancel")
	public String toCancel(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/cancel";
	}

	/**
	 * 任务取消
	 * @param mvm
	 * @param model
	 * @param response
	 */
	@RequestMapping("/cancel")
	public void cancel(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.cancel(mvm);
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
	 * 跳转任务完成页面
	 * @param mvm
	 * @param model
	 * @return
	 */
	@RequestMapping("/toFinish")
	public String toFinish(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t != null && !teamTaskService.isCurrentMember(t.getAssignedId())) {
				return "nopower";
			}
			model.addAttribute("members", teamTaskService.getAllMember());
			model.addAttribute("t", t);
			if(t.getTaskType() == 2) {
				TestApply n = (TestApply) testApplyService.getEntityByPrimaryKey(new TestApply(), t.getTestApplyId());
				if(n != null) {
					model.addAttribute("apply", n);
					testApplyService.showTreeMsg(model, n.getId(), n.getTaskId(), n.getNeedId(), n.getProjectId(), n.getProductId(), "3,4,5", t.getAssignedId());
				}
			}
		}
		publicResult(model);
		return "my/task/finish";
	}

	/**
	 * 任务完成=>变更状态为审核中
	 * @param mvm
	 * @param model
	 * @param response
	 */
	@RequestMapping("/finish")
	public void finish(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("checkedid"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		String checkedid = mvm.get("checkedid")+"";
		Task task = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
		if(task != null && !teamTaskService.isCurrentMember(task.getAssignedId())) {
			json.put("code",2);
			json.put("message", "你没有权限完成当前任务！");
			resultresponse(response,json);
			return;
		}
		String task_assignedid = task.getAssignedId()+"";
		if(checkedid.equals(task_assignedid)){
			json.put("code",2);
			json.put("message", "自己不能审核自己的任务！请选择你的直接领导审核！");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.finish(mvm);
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
	 * 跳转任务完成审核页面
	 */
	@RequestMapping("/toFinishCheck")
	public String toCheck(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t != null && !teamTaskService.isCurrentMember(t.getCheckedId())) {
				return "nopower";
			}
			model.addAttribute("t", t);
			if(t.getTaskType() == 2) {
				TestApply n = (TestApply) testApplyService.getEntityByPrimaryKey(new TestApply(), t.getTestApplyId());
				if(n != null) {
					model.addAttribute("apply", n);
					testApplyService.showTreeMsg(model, n.getId(), n.getTaskId(), n.getNeedId(), n.getProjectId(), n.getProductId(), "3,4,5", t.getAssignedId());
				}
			}
			List<Map<String, Object>> codeReport = teamTaskService.getTaskCodeReport(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("codeReport", codeReport);
			List<Map<String, Object>> codeInterface = teamTaskService.getTaskCodeInterface(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("codeInterface", codeInterface);
		}
		publicResult(model);
		return "my/task/finishCheck";
	}

	/**
	 * 任务完成审核=>变更状态为完成(由主管审核)
	 * @param mvm
	 * @param model
	 * @param response
	 */
	@RequestMapping("/finishCheck")
	public void check(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		int codeNum = teamTaskService.getCodeNum(GiantUtil.intOf(mvm.get("id"), 0));
		if(GiantUtil.isEmpty(mvm.get("id")) || codeNum>0){
			json.put("code",1);
			if (codeNum > 0) {
				json.put("message", "有"+codeNum+"个任务代码未审查!");
			}else {
				json.put("message", "参数不足!");
			}
			resultresponse(response,json);
			return;
		}
		Task task = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
		if(task != null && !teamTaskService.isCurrentMember(task.getCheckedId())) {
			json.put("code",2);
			json.put("message", "你没有权限审核当前任务！");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.finishCheck(mvm);
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
	 * 代码审查
	 */
	@RequestMapping("/exam")
	public void exam(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			boolean flag = teamTaskService.updateCodeReport(mvm);
			if(flag){
				json.put("code",0);
				json.put("message", "操作成功");
			}else{
				json.put("code",1);
				json.put("message", "操作失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}
	
	/**
	 * 删除任务
	 */
	@RequestMapping("/del")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task p = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setDeleted((short) 1);
			boolean flag = teamTaskService.saveUpdateOrDelete(p, null);
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
	 * 跳转关联任务页面
	 */
	@RequestMapping("/toRelevance")
	public String toRelevance(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
			model.addAttribute("needs", teamTaskService.getCanRelevanceTasks(GiantUtil.intOf(mvm.get("id"), 0)));
			model.addAttribute("isCan", "");
		}
		publicResult(model);
		return "my/task/relevance";
	}

	/**
	 * 关联任务
	 */
	@RequestMapping("/relevance")
	public void relevance(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// TODO 是否需要对任务的状态进行判断，例如：只有已完成的任务才能进行验收操作
		// mvm eg :{r=0.4018686015158184, id=25, needs=1,3,5, comment=dsdsdsd}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("needs"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.relevance(mvm);
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
	 * 跳转延期任务页面
	 */
	@RequestMapping("/toDelay")
	public String toDelay(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("members", teamTaskService.getAllMemberByRole("14"));
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamTaskService.isCurrentMember(t.getAssignedId())) {
				return "nopower";
			}
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/delay";
	}

	/**
	 * 延期任务
	 */
	@RequestMapping("/delay")
	public void delay(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// TODO 是否需要对任务的状态进行判断，例如：只有已完成的任务才能进行验收操作
		// mvm eg :{r=0.16247026563442657, id=18, delayed_date=2018-07-03, delayed_review_id=1, comment=dsdsdsdsdsdsdsdsdsdsdsdsd}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("delayed_date")) || GiantUtil.intOf(mvm.get("delayed_review_id"), 0) <= 0){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.delay(mvm);
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
	 * 跳转任务延期审核页面
	 * @param mvm
	 * @param model
	 * @return
	 */
	@RequestMapping("/toDelayCheck")
	public String toDelayCheck(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamTaskService.isCurrentMember(t.getDelayedReviewId())) {
				return "nopower";
			}
			model.addAttribute("members", teamTaskService.getAllMember());
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/delayCheck";
	}

	/**
	 * 任务延期审核
	 * @param mvm
	 * @param model
	 * @param response
	 */
	@RequestMapping("/delayCheck")
	public void delayCheck(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(t != null && !teamTaskService.isCurrentMember(t.getDelayedReviewId())) {
				json.put("code", 2);
				json.put("message", "你没有权限延期审核当前任务！");
				resultresponse(response,json);
				return;
			}
		}
		boolean flag = teamTaskService.delayCheck(mvm);
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
	 * 跳转交接页面
	 */
	@RequestMapping("/toHandover")
	public String toHandover(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamTaskService.isCurrentMember(t.getAssignedId())) {
				return "nopower";
			}
			model.addAttribute("members", teamTaskService.getAllMember());
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/task/handover";
	}

	/**
	 * 交接任务
	 */
	@RequestMapping("/handover")
	public void handover(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamTaskService.handover(mvm);
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
	 * 测试任务通过测试
	 */
	@RequestMapping("/adopt")
	public void adopt(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取单个非测试任务对象
			Task task = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			task.setTestTime(new Timestamp(System.currentTimeMillis()));
			task.setTestState(4);
			boolean flag = teamTaskService.saveUpdateOrDelete(task, null);
			if(flag){
				json.put("code",0);
				json.put("message", "通过成功");
			}else{
				json.put("code",1);
				json.put("message", "通过失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 一键通过测试任务下所有任务
	 */
	@RequestMapping("/adoptAll")
	public void adoptAll(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取测试任务对象
			Task task = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			List<Object> list = new ArrayList<Object>();
			if(task.getDeveloperTaskId() != null && !"".equals(task.getDeveloperTaskId())) {
				String taskIdArray[] = task.getDeveloperTaskId().split(",");
				for(String taskId: taskIdArray) {
					Task t = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(taskId, 0));
					t.setTestTime(new Timestamp(System.currentTimeMillis()));
					t.setTestState(4);
					list.add(t);
				}
			}
			boolean flag = teamTaskService.saveUpdateOrDelete(list, null);
			if(flag){
				json.put("code",0);
				json.put("message", "通过成功");
			}else{
				json.put("code",1);
				json.put("message", "通过失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 测试任务驳回测试
	 */
	@RequestMapping("/reject")
	public void reject(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			Task task = (Task) teamTaskService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
			task.setTestTime(new Timestamp(System.currentTimeMillis()));
			task.setTestState(5);
			boolean flag = teamTaskService.saveUpdateOrDelete(task, null);
			if(flag){
				json.put("code",0);
				json.put("message", "驳回成功");
			}else{
				json.put("code",1);
				json.put("message", "驳回失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
		}
		resultresponse(response,json);
	}


}
