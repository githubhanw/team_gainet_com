/**
 * Qyb
 */
package com.zzidc.my.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.TestBug;
import com.zzidc.team.service.TestBugService;

import net.sf.json.JSONObject;

/**
 * 我的地盘-Bug模块
 * @author Qyb
 *
 */
@Controller
@RequestMapping({ "/my/bug" })
public class MyBugController extends GiantBaseController {
	@Autowired
	private TestBugService testBugService;
	private String requestURL = "my/bug/index";
	
	public void publicResult(Model model) {
		model.addAttribute("m", "my");//模块
		model.addAttribute("s", "bug");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 跳转添加BUG页面
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		if (GiantUtils.isEmpty(mvm.get("id"))) {
		}else {
			String taskId=mvm.get("id").toString();
			model.addAttribute("taskId", taskId);
		}
		model.addAttribute("task", testBugService.getFinishedTasksByMember());
		model.addAttribute("members", testBugService.getAllMember());
		publicResult(model);
		return "my/bug/add";
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
			json.put("message", "添加/修改成功");
		}else{
			json.put("code",1);
			json.put("message", "添加/修改失败");
		}
		resultresponse(response,json);
	}

	/**
	 * 跳转解决页面
	 */
	@RequestMapping("/toSolve")
	public String toSolve(@RequestParam Map<String, String> mvm, Model model) {
		//添加需求页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestBug t = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			if (!testBugService.isCurrentMember(t.getDeveloperId())) {
				return "nopower";
			}
			model.addAttribute("members", testBugService.getAllMember());
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/bug/solve";
	}

	/**
	 * 确认解决
	 */
	@RequestMapping("/solve")
	public void solve(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("solver_id"))){
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
			if (!testBugService.isCurrentMember(t.getDeveloperId())) {
				return "nopower";
			}
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/bug/vali";
	}
	
	/**
	 * 确认验证
	 */
	@RequestMapping("/vali")
	public void vali(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		TestBug t = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
		if(t.getTaskid() == null || t.getTasktype() == null || t.getTaskdes() == null || t.getBugproject() == null || 
				t.getBugrank() == null || t.getBugfen() == null || t.getBugtype() == null || t.getSolver() == null ||
				t.getBugdes() == null || t.getCreater() == null ||t.getDeveloper() == null || t.getSolution() == null ||
				t.getMark() == null ||t.getKaifamark() == null || t.getCreatetime() == null || t.getSolvetime() == null){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = testBugService.vali(mvm);
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
	 * 跳转编辑页面
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam Map<String, String> mvm, Model model) {
		model.addAttribute("task", testBugService.getFinishedTasksByMember());
		model.addAttribute("members", testBugService.getAllMember());
		String taskId=mvm.get("id").toString();
		model.addAttribute("taskId", taskId);
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TestBug t = (TestBug) testBugService.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			if (!testBugService.isCurrentMember(t.getCreaterId())) {
				return "nopower";
			}
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/bug/edit";
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
			json.put("message", "添加/修改成功");
		}else{
			json.put("code",1);
			json.put("message", "添加/修改失败");
		}
		resultresponse(response,json);
	}


}
