package com.zzidc.team.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.zzidc.team.entity.MilepostManage;
import com.zzidc.team.entity.MilepostTaskneed;
import com.zzidc.team.entity.TaskNeed;
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
			mvm.put("orderColumn", "tb.solvestatus");
			mvm.put("orderByValue", "ASC");
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
	public void add(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) throws ParseException {
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
				GiantUtil.isEmpty(mvm.get("end_date")) || GiantUtil.isEmpty(mvm.get("need_remark"))){
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
		boolean flag = testMilepostService.add(mvm, id, name);
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
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			MilepostManage m = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("mi", m);
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
		//添加需求页面的项目列表
	if(GiantUtil.intOf(mvm.get("mi_id"), 0) != 0){
			//获取对象
			MilepostManage t = (MilepostManage) testMilepostService.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("mi_id"), 0));
			Date date =new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String checktime = sdf.format(date);
			t.setMilepostState("2");
		    t.setCheckTime(checktime);
		    t.setCheckName(mvm.get("mi_name"));
		boolean flag = testMilepostService.saveUpdateOrDelete(t,null);
		if(flag){
			json.put("code",0);
			json.put("message", "验收成功");
		}else{
			json.put("code",1);
			json.put("message", "验收失败");
		}
	}else {
		json.put("code",1);
		json.put("message", "获取参数失败");
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
	 * 跳转关联页面
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
}
