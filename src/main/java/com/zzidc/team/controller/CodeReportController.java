package com.zzidc.team.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.Task;
import com.zzidc.team.service.CodeReportService;

import net.sf.json.JSONObject;

/**
 *编写代码审查
 */
@Controller
@RequestMapping({ "/code/report" })
public class CodeReportController extends GiantBaseController {
	@Autowired
	private CodeReportService codeReportService;
	@Autowired
	private GiantBaseService baseService;
	private GiantPager conditionPage = null;

	public void publicResult(Model model) {
		model.addAttribute("m", "task");//模块
		model.addAttribute("s", "task");//子模块
	}
	public void publicResults(Model model) {
		model.addAttribute("m", "my");//模块
		model.addAttribute("s", "task");//子模块
	}
	
	/**
	 * 跳转创建代码审查页面（管理中心）
	 */
	@RequestMapping("/toAdd")
	public String toAdd(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			Task t=codeReportService.getTask(mvm);
			model.addAttribute("task", t);
		}
		publicResult(model);
		return "team/task/codeAdd";
	}
	/**
	 * 跳转创建代码审查页面（我的）
	 */
	@RequestMapping("/toMyAdd")
	public String toMyAdd(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			Task t=codeReportService.getTask(mvm);
			model.addAttribute("task", t);
		}
		publicResults(model);
		return "my/task/codeAdd";
	}
	
	/**
	 * 创建代码审查
	 */
	@RequestMapping("/add")
	public void add(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		System.out.println("数据："+mvm);
		int id=baseService.getMemberId();	//登录id	        
        String name=baseService.getMemberName();   //登录姓名
        //判断是否登录
        if(name.equals("")){
        json.put("code", 4);
        json.put("message", "请重新登录");
        resultresponse(response,json);
		return;
        }
		if(GiantUtil.isEmpty(mvm.get("report_interface")) || GiantUtil.isEmpty(mvm.get("entry_point")) || 
				GiantUtil.isEmpty(mvm.get("online_url")) || GiantUtil.isEmpty(mvm.get("source_file"))
				 || GiantUtil.isEmpty(mvm.get("report_remark")) || GiantUtil.isEmpty(mvm.get("task_id"))
				 || GiantUtil.isEmpty(mvm.get("report_type"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = codeReportService.add(mvm,id);
		if(flag){
			json.put("code",0);
			json.put("message", "创建成功");
		}else{
			json.put("code",1);
			json.put("message", "创建失败");
		}
		resultresponse(response,json);
	}

}
