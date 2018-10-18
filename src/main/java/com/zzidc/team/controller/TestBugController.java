/**
 * Qyb
 */
package com.zzidc.team.controller;

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
import com.zzidc.team.entity.Task;
import com.zzidc.team.entity.TestBug;
import com.zzidc.team.service.TestBugService;

import net.sf.json.JSONObject;

/**
 * Bug模块
 * @author Qyb
 *
 */
@Controller
@RequestMapping({ "/test/bug" })
public class TestBugController extends GiantBaseController {
	@Autowired
	private TestBugService testBugService;
	private GiantPager conditionPage = null;
	private String requestURL = "test/apply/index";
	
	public void publicResult(Model model) {
		model.addAttribute("m", "apply");//模块
		model.addAttribute("s", "bug");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * BUg列表
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "tb.id");
			mvm.put("orderByValue", "DESC");
			mvm.put("currentPage", "1");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "0");
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
		pageList = testBugService.getPageList(conditionPage);
		requestURL = "test/bug/index";
		pageList.setDesAction(requestURL);
		model.addAttribute("members", testBugService.getAllMember());
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "test/bug/list";
	}
	
	/**
	 * 跳转删除页面
	 * @param mvm
	 * @param model
	 * @return
	 */
	@RequestMapping("/toDelete")
	public String toDelete(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestBug t = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "test/bug/delete";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping("/delete")
	public void del(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestBug p = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			p.setSolvestatus(3);
			boolean flag = testBugService.saveUpdateOrDelete(p, null);
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
	 * 跳转添加BUG页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		if (GiantUtils.isEmpty(mvm.get("id"))) {
		}else {
			String taskId=mvm.get("id");
			model.addAttribute("taskId", taskId);
		}
		model.addAttribute("task", testBugService.getFinishedTasksByMember());
		model.addAttribute("members", testBugService.getAllMember());
		publicResult(model);
		model.addAttribute("s", "add");//子模块
		return "test/bug/add";
	}
	
	/**
	 * 添加BUG
	 */
	@RequestMapping("/add")
	public void add(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();//{r=0.07599744503051853, taskid=36, bugproject=虚拟主机, bugrank=1, bugfen=2, bugtype=2, bugdes=省时省事翁额, creater_id=7, developer_id=7, mark=<p>阿斯打发打发</p>}
		if(GiantUtil.isEmpty(mvm.get("taskid")) || GiantUtil.isEmpty(mvm.get("bugproject")) || GiantUtil.isEmpty(mvm.get("bugrank")) || 
				GiantUtil.isEmpty(mvm.get("bugfen")) || GiantUtil.isEmpty(mvm.get("bugtype")) || GiantUtil.isEmpty(mvm.get("bugdes")) || 
				GiantUtil.isEmpty(mvm.get("creater_id")) || GiantUtil.isEmpty(mvm.get("developer_id")) || GiantUtil.isEmpty(mvm.get("mark"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testBugService.add(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "添加BUG成功");
		}else{
			json.put("code",1);
			json.put("message", "添加BUG失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 跳转编辑页面
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("task", testBugService.getFinishedTasksByMember());
		model.addAttribute("members", testBugService.getAllMember());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestBug t = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
			model.addAttribute("taskId", t.getTaskid());
		}
		publicResult(model);
		model.addAttribute("s", "add");//子模块
		return "test/bug/edit";
	}
	
	/**
	 * 确认编辑
	 */
	@RequestMapping("/edit")
	public void edit(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("taskid")) || GiantUtil.isEmpty(mvm.get("bugproject")) || GiantUtil.isEmpty(mvm.get("bugrank")) || 
				GiantUtil.isEmpty(mvm.get("bugfen")) || GiantUtil.isEmpty(mvm.get("bugtype")) || GiantUtil.isEmpty(mvm.get("bugdes")) || 
				GiantUtil.isEmpty(mvm.get("creater_id")) || GiantUtil.isEmpty(mvm.get("developer_id")) || GiantUtil.isEmpty(mvm.get("mark"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testBugService.edit(mvm);
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
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestBug t = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("members", testBugService.getAllMember());
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "test/bug/assign";
	}

	/**
	 * 指派给
	 */
	@RequestMapping("/assign")
	public void assign(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("solver_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testBugService.assign(mvm);
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
	 * 跳转解决页面
	 */
	@RequestMapping("/toSolve")
	public String toSolve(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestBug t = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("members", testBugService.getAllMember());
			model.addAttribute("loginId", testBugService.getMemberId());
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "test/bug/solve";
	}

	/**
	 * 确认解决
	 */
	@RequestMapping("/solve")
	public void solve(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("solver_id")) || GiantUtil.isEmpty(mvm.get("kaifamark"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testBugService.solve(mvm);
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
	 * 跳转验证页面
	 */
	@RequestMapping("/toVali")
	public String toVali(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("need", testBugService.getNeed());
		model.addAttribute("need_id", GiantUtil.intOf(mvm.get("need_id"), 0));
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestBug t = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			if (testBugService.isCurrentMember(t.getDeveloperId()) || testBugService.isCurrentMember(t.getSolverId())) {
				return "nopower";
			}
			model.addAttribute("t", t);
			Task task = (Task) testBugService.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(t.getTaskid(), 0));
			model.addAttribute("taskName", task.getTaskName());
		}
		publicResult(model);
		model.addAttribute("s", "add");//子模块
		return "test/bug/vali";
	}
	
	/**
	 * 确认验证
	 */
	@RequestMapping("/vali")
	public void vali(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		TestBug t = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
		if(GiantUtil.isEmpty(t.getTaskid()) || GiantUtil.isEmpty(t.getBugproject()) || 
				GiantUtil.isEmpty(t.getBugrank()) || GiantUtil.isEmpty(t.getBugfen()) || GiantUtil.isEmpty(t.getBugtype()) || GiantUtil.isEmpty(t.getSolver()) ||
				GiantUtil.isEmpty(t.getBugdes()) || GiantUtil.isEmpty(t.getCreater()) ||GiantUtil.isEmpty(t.getDeveloper()) || GiantUtil.isEmpty(t.getSolution()) ||
				GiantUtil.isEmpty(t.getKaifamark()) || GiantUtil.isEmpty(t.getCreatetime()) || GiantUtil.isEmpty(t.getSolvetime())){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testBugService.vali(mvm);
		if(flag){
			json.put("code",0);
			json.put("message", "BUG验证成功");
		}else{
			json.put("code",1);
			json.put("message", "BUG验证失败");
		}
		resultresponse(response,json);
	}

}
