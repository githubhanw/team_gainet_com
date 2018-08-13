package com.zzidc.my.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.TaskNeed;
import com.zzidc.team.service.TeamNeedService;

import net.sf.json.JSONObject;

/**
 * 需求管理
 */
@Controller
@RequestMapping({ "/my/need" })
public class MyNeedController extends GiantBaseController {
	@Autowired
	private TeamNeedService teamNeedService;
//	private GiantPager conditionPage = null;
	private String requestURL = "my/need";

	public void publicResult(Model model) {
		model.addAttribute("m", "my");//模块
		model.addAttribute("s", "need");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 需求列表
	 */
	/*@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "tn.update_time");
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
		queryCondition.clear();
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(GiantUtil.intOf(mvm.get("pageSize"), 15));
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = teamNeedService.getPageList(conditionPage);
		requestURL = "my/need/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		if("1".equals(mvm.get("type")) || "2".equals(mvm.get("type"))) {
			teamNeedService.getSubNeedList(pageList.getPageResult(), mvm.get("type"));
		}
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "my/need/list";
	}*/

	/**
	 * 需求详情
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			Map<String, Object> needDetail = teamNeedService.getNeedDetail(GiantUtil.intOf(mvm.get("id"), 0));
			if(needDetail == null) {
				return "comm/notexists";
			}
			model.addAttribute("needM", needDetail);
			List<Map<String, Object>> subNeed = teamNeedService.getSubNeedList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("subNeed", subNeed);
			//相关任务
			List<Map<String, Object>> task = teamNeedService.getTaskList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("task", task);
			//关联需求
			if(needDetail.get("link") != null) {
				List<Map<String, Object>> linkNeed = teamNeedService.getLinkNeed(needDetail.get("link").toString());
				model.addAttribute("linkNeed", linkNeed);
			}
			//日志
			List<Map<String, Object>> logList = teamNeedService.getLogList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("logList", logList);
		}
		publicResult(model);
		return "my/need/detail";
	}

	/**
	 * 跳转添加需求页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		model.addAttribute("project", teamNeedService.getTeamProject());
		model.addAttribute("needSrc", teamNeedService.getNeedSrc());
		model.addAttribute("project_id", GiantUtil.intOf(mvm.get("project_id"), 0));
		model.addAttribute("members", teamNeedService.getAllMember());
		publicResult(model);
		return "my/need/add";
	}

	/**
	 * 添加需求
	 */
	@RequestMapping("/add")
	public void add(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("project_id")) || GiantUtil.isEmpty(mvm.get("need_name")) || 
				GiantUtil.isEmpty(mvm.get("level")) || GiantUtil.isEmpty(mvm.get("assigned_id")) || 
				GiantUtil.isEmpty(mvm.get("start_date")) || GiantUtil.isEmpty(mvm.get("end_date")) || 
				GiantUtil.isEmpty(mvm.get("need_remark")) || GiantUtil.isEmpty(mvm.get("check_remark")) || 
				GiantUtil.isEmpty(mvm.get("member_id")) || GiantUtil.isEmpty(mvm.get("src_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		
		boolean flag = teamNeedService.add(mvm);
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
	 * 跳转批量添加、分解需求页面
	 */
	@RequestMapping("/toBatchAdd")
	public String toBatchAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		model.addAttribute("project", teamNeedService.getTeamProject());
		model.addAttribute("members", teamNeedService.getAllMember());
		model.addAttribute("needSrc", teamNeedService.getNeedSrc());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/batchAdd";
	}

	/**
	 * 批量添加、分解需求
	 */
	@RequestMapping("/batchAdd")
	public void batchAdd(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("project_id")) || GiantUtil.isEmpty(mvm.get("need_name")) || 
				GiantUtil.isEmpty(mvm.get("level")) || GiantUtil.isEmpty(mvm.get("src_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		
		Integer needId = teamNeedService.batchAdd(mvm);
		if(needId > 0){
			json.put("code",0);
			json.put("message", "添加成功");
		}else{
			json.put("code",1);
			json.put("message", "添加失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 跳转编辑需求页面
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		model.addAttribute("project", teamNeedService.getTeamProject());
		model.addAttribute("members", teamNeedService.getAllMember());
		model.addAttribute("needSrc", teamNeedService.getNeedSrc());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
			//日志
			List<Map<String, Object>> logList = teamNeedService.getLogList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("logList", logList);
		}
		publicResult(model);
		return "my/need/edit";
	}

	/**
	 * 编辑需求
	 */
	@RequestMapping("/edit")
	public void edit(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("project_id")) || GiantUtil.isEmpty(mvm.get("level")) || GiantUtil.intOf(mvm.get("id"), 0) == 0 ||
				GiantUtil.isEmpty(mvm.get("src_id")) || GiantUtil.isEmpty(mvm.get("member_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		
		boolean flag = teamNeedService.edit(mvm);
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
	 * 跳转指派页面
	 */
	@RequestMapping("/toAssign")
	public String toAssign(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		model.addAttribute("members", teamNeedService.getAllMember());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/assign";
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
		boolean flag = teamNeedService.assign(mvm);
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
	 * 跳转变更需求页面
	 */
	@RequestMapping("/toChange")
	public String toChange(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/change";
	}

	/**
	 * 变更需求
	 */
	@RequestMapping("/change")
	public void change(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("need_name")) || GiantUtil.isEmpty(mvm.get("need_remark")) || 
				GiantUtil.isEmpty(mvm.get("check_remark"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamNeedService.change(mvm);
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
	 * 跳转验收需求页面
	 */
	@RequestMapping("/toCheck")
	public String toCheck(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/check";
	}

	/**
	 * 需求验收
	 */
	@RequestMapping("/check")
	public void check(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// TODO 是否需要对需求的状态进行判断，例如：只有测试完成的需求才能进行验收操作，获取再toCheck中进行验证
		// mvm eg :{r=0.29616789999172366, stage=y, comment=, id=25}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("stage"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamNeedService.check(mvm);
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
	 * 跳转关联需求页面
	 */
	@RequestMapping("/toRelevance")
	public String toRelevance(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
			model.addAttribute("needs", teamNeedService.getCanRelevanceNeeds(GiantUtil.intOf(mvm.get("id"), 0)));
		}
		publicResult(model);
		return "my/need/relevance";
	}

	/**
	 * 需求关联
	 */
	@RequestMapping("/relevance")
	public void relevance(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// TODO 是否需要对需求的状态进行判断，例如：只有测试完成的需求才能进行验收操作，获取再toCheck中进行验证
		// mvm eg :{r=0.4018686015158184, id=25, needs=1,3,5, comment=dsdsdsd}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("needs"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamNeedService.relevance(mvm);
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
	 * 跳转关闭需求页面
	 */
	@RequestMapping("/toClose")
	public String toClose(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/close";
	}

	/**
	 * 关闭需求
	 */
	@RequestMapping("/close")
	public void close(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("closedReason"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamNeedService.close(mvm);
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
	 * 跳转激活需求页面
	 */
	@RequestMapping("/toActive")
	public String toActive(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/active";
	}

	/**
	 * 激活需求
	 */
	@RequestMapping("/active")
	public void active(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("state"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamNeedService.active(mvm);
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
	 * 删除需求
	 */
	@RequestMapping("/del")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed p = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setState((short)0);
			boolean flag = teamNeedService.saveUpdateOrDelete(p, null);
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
	 * 跳转接收需求页面
	 */
	@RequestMapping("/toOpen")
	public String toOpen(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/open";
	}

	/**
	 * 需求接收
	 */
	@RequestMapping("/open")
	public void open(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// TODO 是否需要对需求的状态进行判断，例如：只有测试完成的需求才能进行验收操作，获取再toCheck中进行验证
		// mvm eg :{r=0.29616789999172366, stage=y, comment=, id=25}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("plan_end_date"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamNeedService.open(mvm);
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
