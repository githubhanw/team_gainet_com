package com.zzidc.my.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
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
import com.zzidc.log.LogMethod;
import com.zzidc.log.LogModule;
import com.zzidc.log.PMLog;
import com.zzidc.team.entity.TaskNeed;
import com.zzidc.team.service.FilemanageService;
import com.zzidc.team.service.TeamNeedService;

import net.sf.json.JSONObject;

/**
 * 模块管理
 */
@Controller
@RequestMapping({ "/my/need" })
public class MyNeedController extends GiantBaseController {
	@Autowired
	private TeamNeedService teamNeedService;
	@Autowired
	private FilemanageService filemanageService;
	@Autowired
	private GiantBaseService baseService;
//	private GiantPager conditionPage = null;
	private String requestURL = "my/need";

	public void publicResult(Model model) {
		model.addAttribute("m", "my");//模块
		model.addAttribute("s", "need");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	
	/**
	 * 模块列表在:MyController
	 */

	/**
	 * 模块详情
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			Map<String, Object> needDetail = teamNeedService.getNeedDetail(GiantUtil.intOf(mvm.get("id"), 0));
			if(needDetail == null) {
				return "comm/notexists";
			}
			model.addAttribute("needM", needDetail);
			//获取相关文档
			String sql = "SELECT file_name,file_url,file_realname FROM file_manage WHERE file_classification=1 AND access_control=1 AND gl_id=" + GiantUtil.intOf(mvm.get("id"), 0);
			model.addAttribute("files", teamNeedService.getMapListBySQL(sql, null));
			
			List<Map<String, Object>> subNeed = teamNeedService.getSubNeedList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("subNeed", subNeed);
			//相关任务
			List<Map<String, Object>> task = teamNeedService.getTaskList(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("task", task);
			//关联模块
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
	 * 跳转添加原型图和流程图页面
	 */
	@RequestMapping("/toaddPicture")
	public String toaddPicture(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed t = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/need/addPicture";
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
		boolean flag = teamNeedService.addPicture(mvm, filePrototype, filetree);
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
			TaskNeed t = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("t", t);
		}
		publicResult(model);
		return "my/need/delPicture";
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
		boolean flag = teamNeedService.delPicture(mvm,request);
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
	 * 跳转添加 项目模块页面
	 */
	@RequestMapping("/toaddproject")
	public String toAddProject(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		model.addAttribute("project", teamNeedService.getTeamProject());
		model.addAttribute("needSrc", teamNeedService.getNeedSrc());
		model.addAttribute("project_id", GiantUtil.intOf(mvm.get("project_id"), 0));
		model.addAttribute("projectmems", teamNeedService.getProjectMems( GiantUtil.intOf(mvm.get("project_id"), 0)));
		model.addAttribute("members", teamNeedService.getAllMember());
		publicResult(model);
		return "my/need/addproject";
	}

	/**
	 * 添加 项目模块
	 */
	@RequestMapping("/addproject")
	public void addProject(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,@RequestParam("file")MultipartFile[] file,
			@RequestParam("filePrototype")MultipartFile[] filePrototype,@RequestParam("filetree")MultipartFile[] filetree) {
		JSONObject json=new JSONObject();
		int id=baseService.getMemberId();	//登录id	        
        String name=baseService.getMemberName();   //登录姓名
        if(name.equals("")){
        	json.put("code", 4);
        	json.put("message", "请重新登录");
        	resultresponse(response,json);
			return;
        }
        String prototypeName = filePrototype[0].getOriginalFilename();
		String treeName = filetree[0].getOriginalFilename();
		if(prototypeName.equals("")){
			json.put("code",2);
			json.put("message", "请选择界面原型文件");
			resultresponse(response,json);
			return;
		}
		if(treeName.equals("")){
			json.put("code",3);
			json.put("message", "请选择流程图");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.isEmpty(mvm.get("project_id")==null?mvm.get("product_id"):mvm.get("project_id")) || GiantUtil.isEmpty(mvm.get("need_name")) || 
				GiantUtil.isEmpty(mvm.get("level")) || GiantUtil.isEmpty(mvm.get("assigned_id")) || 
				GiantUtil.isEmpty(mvm.get("start_date")) || GiantUtil.isEmpty(mvm.get("end_date")) || 
				GiantUtil.isEmpty(mvm.get("need_remark")) || GiantUtil.isEmpty(mvm.get("check_remark")) || 
				GiantUtil.isEmpty(mvm.get("member_name")) || GiantUtil.isEmpty(mvm.get("src_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		boolean flag = teamNeedService.addproject(mvm,id,name,file,filePrototype,filetree);
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
	 * 跳转添加 产品模块页面
	 */
	@RequestMapping("/toaddproduct")
	public String toAddProduct(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		model.addAttribute("product", teamNeedService.getTeamProduct());
		model.addAttribute("needSrc", teamNeedService.getNeedSrc());
		model.addAttribute("product_id", GiantUtil.intOf(mvm.get("product_id"), 0));
		model.addAttribute("members", teamNeedService.getAllMember());
		model.addAttribute("departinfo", teamNeedService.getDepartmentInfo());
		publicResult(model);
		return "my/need/addproduct";
	}

	/**
	 * 添加 产品模块
	 */
	@RequestMapping("/addproduct")
	public void addProduct(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,@RequestParam("file")MultipartFile[] file,
			@RequestParam("filePrototype")MultipartFile[] filePrototype,@RequestParam("filetree")MultipartFile[] filetree) {
		JSONObject json=new JSONObject();
		int id=baseService.getMemberId();	//登录id	        
        String name=baseService.getMemberName();   //登录姓名
        if(name.equals("")){
        	json.put("code", 4);
        	json.put("message", "请重新登录");
        	resultresponse(response,json);
			return;
        }
        String prototypeName = filePrototype[0].getOriginalFilename();
		String treeName = filetree[0].getOriginalFilename();
		if(prototypeName.equals("")){
			json.put("code",2);
			json.put("message", "请选择界面原型文件");
			resultresponse(response,json);
			return;
		}
		if(treeName.equals("")){
			json.put("code",3);
			json.put("message", "请选择流程图");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.isEmpty(mvm.get("project_id")==null?mvm.get("product_id"):mvm.get("project_id")) || 
				GiantUtil.isEmpty(mvm.get("need_name")) || GiantUtil.isEmpty(mvm.get("level")) || 
				GiantUtil.isEmpty(mvm.get("department_id")) || GiantUtil.isEmpty(mvm.get("end_date")) ||
				GiantUtil.isEmpty(mvm.get("need_remark")) || GiantUtil.isEmpty(mvm.get("check_remark")) || 
				GiantUtil.isEmpty(mvm.get("member_id")) || GiantUtil.isEmpty(mvm.get("src_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		if (!GiantUtil.isEmpty(mvm.get("id"))) {
			if (GiantUtil.isEmpty(mvm.get("start_date")) || GiantUtil.isEmpty(mvm.get("cend_date")) || 
					GiantUtil.isEmpty(mvm.get("tend_date")) || GiantUtil.isEmpty(mvm.get("end_date"))) {
				json.put("code",1);
				json.put("message", "参数不足【有时间未填写】");
				resultresponse(response,json);
				return;
			}
		}
		boolean flag = teamNeedService.addproduct(mvm,id,name,file,filePrototype,filetree);
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
	 * 跳转添加 项目子模块页面
	 */
	@RequestMapping("/toAddPJSon")
	public String toAddPJSon(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		model.addAttribute("need_id",GiantUtil.intOf(mvm.get("need_id"), 0));
		model.addAttribute("project", teamNeedService.getTeamProject());
		model.addAttribute("needSrc", teamNeedService.getNeedSrc());
		model.addAttribute("project_id", GiantUtil.intOf(mvm.get("project_id"), 0));
		model.addAttribute("members", teamNeedService.getAllMember());
		model.addAttribute("projectmems", teamNeedService.getProjectMems( GiantUtil.intOf(mvm.get("project_id"), 0)));
		publicResult(model);
		return "my/need/addpjson";
	}

	/**
	 * 跳转添加 产品子模块页面
	 */
	@RequestMapping("/toAddPDSon")
	public String toAddPDSon(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		model.addAttribute("need_id",GiantUtil.intOf(mvm.get("need_id"), 0));
		if(GiantUtil.intOf(mvm.get("need_id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("need_id"), 0));
			model.addAttribute("n", n);
		}
		model.addAttribute("product", teamNeedService.getTeamProduct());
		model.addAttribute("needSrc", teamNeedService.getNeedSrc());
		model.addAttribute("product_id", GiantUtil.intOf(mvm.get("product_id"), 0));
		model.addAttribute("members", teamNeedService.getAllMember());
		publicResult(model);
		return "my/need/addpdson";
	}
	
	/**
	 * [项目(拆分模块)]跳转到 选中项目的添加模块列表页面
	 */
	@RequestMapping("/toEachAdd")
	public String toEachAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		GiantPager conditionPage = new GiantPager();
		List<Map<String, Object>> List = teamNeedService.getPageListThisProject(GiantUtil.intOf(mvm.get("project_id"), 0),conditionPage);
		model.addAttribute("project", teamNeedService.getTeamProjectByID(GiantUtil.intOf(mvm.get("project_id"), 0)));
		model.addAttribute("project_id", GiantUtil.intOf(mvm.get("project_id"), 0));
		model.addAttribute("List", List);
		return "my/need/eachAdd";
	}


	/**
	 * 跳转批量添加、分解模块页面
	 */
	@RequestMapping("/toBatchAdd")
	public String toBatchAdd(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
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
	 * 批量添加、分解模块
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
	 * 跳转编辑模块页面
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
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
	 * 编辑模块
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
		//添加模块页面的项目列表
		model.addAttribute("members", teamNeedService.getAllMember());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamNeedService.isCanAssign(mvm)) {
				return "nopower";
			}
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
		if(!teamNeedService.isCanAssign(mvm)) {
			json.put("code",1);
			json.put("message", "不能对该模块进行指派操作");
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
	 * 跳转安排页面
	 */
	@RequestMapping("/toArrange")
	public String toArrange(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		model.addAttribute("members", teamNeedService.getAllMember());
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(n != null && !teamNeedService.isCurrentMember(n.getDepartmentId())) {
				return "nopower";
			}
			model.addAttribute("n", n);
			//获取相关文档
			String sql = "SELECT file_name,file_url,file_realname FROM file_manage WHERE file_classification=1 AND access_control=1 AND gl_id=" + GiantUtil.intOf(mvm.get("id"), 0);
			model.addAttribute("files", teamNeedService.getMapListBySQL(sql, null));
		}
		publicResult(model);
		return "my/need/arrange";
	}

	/**
	 * 安排给
	 */
	@RequestMapping("/arrange")
	public void arrange(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("assigned_id")) || GiantUtil.isEmpty(mvm.get("end_date")) ){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(n != null && !teamNeedService.isCurrentMember(n.getDepartmentId())) {
				json.put("code",2);
				json.put("message", "你没有权限安排当前模块！");
				resultresponse(response,json);
				return;
			}
		}
		boolean flag = teamNeedService.arrange(mvm);
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
	 * 跳转变更模块页面
	 */
	@RequestMapping("/toChange")
	public String toChange(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamNeedService.isCanOperation(mvm)) {
				return "nopower";
			}
			model.addAttribute("n", n);
		}
		String querySql = "select id, project_name from task_project where state = 1";
		List<Map<String, Object>> projectList = teamNeedService.getMapListBySQL(querySql, null);
		model.addAttribute("projectList", projectList);
		publicResult(model);
		return "my/need/change";
	}

	/**
	 * 变更模块
	 */
	@RequestMapping("/change")
	public void change(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) {
		JSONObject json=new JSONObject();
		int id=baseService.getMemberId();	//登录id	        
        String name=baseService.getMemberName();   //登录姓名
        if(name.equals("")){
        	json.put("code", 4);
        	json.put("message", "请重新登录");
        	resultresponse(response,json);
			return;
        }
        
		if(GiantUtil.isEmpty(mvm.get("need_name")) || GiantUtil.isEmpty(mvm.get("need_remark")) || 
				GiantUtil.isEmpty(mvm.get("projectId")) ||
				GiantUtil.isEmpty(mvm.get("check_remark"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		if(!teamNeedService.isCanOperation(mvm)) {
			json.put("code",1);
			json.put("message", "你没有权限变更当前模块！");
			return;
		}
		
		boolean flag = teamNeedService.change(mvm);
		if(file != null && file.length > 0) {
			String fileName = file[0].getOriginalFilename();
			if(fileName != null && !"".equals(fileName)) {
				//创建文档
				JSONObject jsonupload=new JSONObject();
				jsonupload=filemanageService.uploadfiles(file);
				if(jsonupload!=null){
					filemanageService.changexq(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),mvm.get("id"),mvm.get("need_name"));
				}else{
					json.put("code",0);
					json.put("message", "上传成功");  
					resultresponse(response,json);
					return;
				}
			}
		}
		
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
	 * 跳转验收模块页面
	 */
	@RequestMapping("/toCheck")
	public String toCheck(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		Integer parentId = 0;
		TaskNeed n = new TaskNeed();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (!teamNeedService.isCurrentMember(n.getCreateId())) {
				return "nopower";
			}
			model.addAttribute("n", n);
			parentId = n.getParentId();
		}
		if (parentId == 0) {
			if (!teamNeedService.isCurrentMember(n.getMemberId())) {
				return "nopower";
			}
			Integer needId = n.getId();
			//获取父模块下所有任务
			List<Map<String, Object>> needTask = teamNeedService.getNeedTaskByProject(needId);
			//获取父模块下所有子模块
			List<Map<String, Object>> subNeed = teamNeedService.getSubNeedByProject(needId);
			//获取所有子模块下任务
			List<Map<String, Object>> subNeedTask = teamNeedService.getSubNeedTaskByProject(needId);
			//获取所有任务下的所有测试用例列表
			List<Map<String, Object>> testCase = teamNeedService.getTestCaseByProject(needId);
			//获取所有测试用例下的所有步骤
			List<Map<String, Object>> testCaseStep = teamNeedService.getTestCaseStepByProject(needId);
			
			model.addAttribute("needTask", needTask);
			model.addAttribute("subNeed", subNeed);
			model.addAttribute("subNeedTask", subNeedTask);
			model.addAttribute("testCase", testCase);
			model.addAttribute("testCaseStep", testCaseStep);
			publicResult(model);
			return "my/need/checkParent";
		} else {
			TaskNeed parentNeed = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), parentId);
			Integer assignedId = parentNeed.getAssignedId();//父模块的ID
			if (!teamNeedService.isCurrentMember(assignedId)) {
				return "nopower";
			}
			Map<String, Object> needDetail = teamNeedService.getNeedDetail(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("needM", needDetail);
			List<Map<String, Object>> codeReport = teamNeedService.getCodeReport(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("codeReport", codeReport);
			List<Map<String, Object>> codeInterface = teamNeedService.getCodeInterface(GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("codeInterface", codeInterface);
			publicResult(model);
		}
		return "my/need/check";
	}

	/**
	 * 子模块验收
	 */
	@RequestMapping("/check")
	public void check(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) {
		// TODO 是否需要对模块的状态进行判断，例如：只有测试完成的模块才能进行验收操作，获取再toCheck中进行验证
		// mvm eg :{r=0.29616789999172366, stage=y, comment=, id=25}
		JSONObject json=new JSONObject();
		int id=baseService.getMemberId();	//登录id	        
        String name=baseService.getMemberName(); 
        int codeNum = teamNeedService.getCodeNum(GiantUtil.intOf(mvm.get("id"), 0));
		if(GiantUtil.isEmpty(mvm.get("stage")) || codeNum>0){
			json.put("code",1);
			if (codeNum > 0) {
				json.put("message", "有"+codeNum+"个任务代码未审查!");
			}else {
				json.put("message", "参数不足!");
			}
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			Integer parentId = n.getParentId();
			TaskNeed parentNeed = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), parentId);
			Integer assignedId = parentNeed.getAssignedId();
			//子模块的验收人需为父模块的指派人
			if (!teamNeedService.isCurrentMember(assignedId)) {
				json.put("code",1);
				json.put("message", "不能对该模块进行验收操作");
				return;
			}
		}
		
		boolean flag = teamNeedService.check(mvm);
		if(file != null && file.length > 0) {
			String fileName = file[0].getOriginalFilename();
			if(fileName != null && !"".equals(fileName)) {
			    //创建文档
			  	JSONObject jsonupload=new JSONObject();
			  	jsonupload=filemanageService.uploadfiles(file);
			  	if(jsonupload!=null){
			  		filemanageService.checkxq(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),mvm.get("id"),mvm.get("need_name"));
			  	}else{
				  	json.put("code",0);
				  	json.put("message", "上传成功");  
				  	resultresponse(response,json);
				  	return;
			  	}
			}
		}
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
	 * 验收模块
	 */
	@RequestMapping("/checkParent")
	public void checkParent(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) throws ParseException {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (n != null && !teamNeedService.isCurrentMember(n.getMemberId())) {
				json.put("code",1);
				json.put("message", "不能对该模块进行验收操作！");
				return;
			}
			String notThrough =  GiantUtil.stringOf(mvm.get("notThrough"));
			//项目验收不通过的情况
			if ("0".equals(notThrough)) {
				n.setCheckedId(teamNeedService.getMemberId());
				n.setCheckedName(teamNeedService.getMemberName());
				n.setCheckedTime(new Timestamp(System.currentTimeMillis()));
				n.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				n.setState((short)2);
			} else {
				n.setCheckedId(teamNeedService.getMemberId());
				n.setCheckedName(teamNeedService.getMemberName());
				n.setCheckedTime(new Timestamp(System.currentTimeMillis()));
				n.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				n.setState((short)4);
			}
			boolean flag = teamNeedService.saveUpdateOrDelete(n, null);
			if(flag){
				if ("0".equals(notThrough)) {
					json.put("code",0);
					json.put("message", "模块验收不通过");
				} else {
					json.put("code",0);
					json.put("message", "模块验收成功");
				}
			}else{
				json.put("code",1);
				json.put("message", "模块验收失败");
			}
		}else {
			json.put("code",1);
			json.put("message", "获取参数失败");
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
			boolean flag = teamNeedService.updateCodeReport(mvm);
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
	 * 跳转关联模块页面
	 */
	@RequestMapping("/toRelevance")
	public String toRelevance(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamNeedService.isCanOperation(mvm)) {
				return "nopower";
			}
			model.addAttribute("n", n);
			model.addAttribute("needs", teamNeedService.getCanRelevanceNeeds(GiantUtil.intOf(mvm.get("id"), 0)));
		}
		publicResult(model);
		return "my/need/relevance";
	}

	/**
	 * 模块关联
	 */
	@RequestMapping("/relevance")
	public void relevance(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// TODO 是否需要对模块的状态进行判断，例如：只有测试完成的模块才能进行验收操作，获取再toCheck中进行验证
		// mvm eg :{r=0.4018686015158184, id=25, needs=1,3,5, comment=dsdsdsd}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("needs"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		if(!teamNeedService.isCanOperation(mvm)) {
			json.put("code",1);
			json.put("message", "不能对该模块进行关联操作");
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
	 * 跳转关联月会议页面
	 */
	@RequestMapping("/toRelate")
	public String toRelate(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamNeedService.isCurrentMember(n.getCreateId()) && !teamNeedService.isCurrentMember(n.getMemberId()) && !teamNeedService.isCurrentMember(n.getAssignedId())) {
				return "nopower";
			}
			model.addAttribute("n", n);
			model.addAttribute("meetings", teamNeedService.getCanRelateMeetings());
		}
		publicResult(model);
		return "my/need/relate";
	}

	/**
	 * 跳转月会议
	 */
	@RequestMapping("/relate")
	public void relate(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("meeting_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamNeedService.isCurrentMember(n.getCreateId()) && !teamNeedService.isCurrentMember(n.getMemberId()) && !teamNeedService.isCurrentMember(n.getAssignedId())) {
				json.put("code",1);
				json.put("message", "无权限关联");
				resultresponse(response,json);
				return;
			}
		}
		boolean flag = teamNeedService.relate(mvm);
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
		if(!teamNeedService.isCanOperation(mvm)) {
			return "nopower";
		}
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/close";
	}

	/**
	 * 关闭模块
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
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (n != null && !teamNeedService.isCurrentMember(n.getMemberId())) {
				json.put("code",2);
				json.put("message", "你没有权限关闭当前模块！");
				resultresponse(response,json);
				return;
			}
		}
		if(!teamNeedService.isCanOperation(mvm)) {
			json.put("code",1);
			json.put("message", "不能对该模块进行关闭操作");
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
	 * 跳转激活模块页面
	 */
	@RequestMapping("/toActive")
	public String toActive(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/active";
	}

	/**
	 * 激活模块
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
	 * 删除模块
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
	 * 跳转接收模块页面
	 */
	@RequestMapping("/toOpen")
	public String toOpen(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (!teamNeedService.isCurrentMember(n.getAssignedId())) {
				return "nopower";
			}
			model.addAttribute("n", n);
		}
		publicResult(model);
		return "my/need/open";
	}

	/**
	 * 模块接收
	 */
	@RequestMapping("/open")
	public void open(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// TODO 是否需要对模块的状态进行判断，例如：只有测试完成的模块才能进行验收操作，获取再toCheck中进行验证
		// mvm eg :{r=0.29616789999172366, stage=y, comment=, id=25}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("plan_end_date"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (n != null && !teamNeedService.isCurrentMember(n.getAssignedId())) {
				json.put("code",2);
				json.put("message", "你没有权限接收当前模块！");
				resultresponse(response,json);
				return;
			}
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
	
	/**
	 * 跳转提交验收页面
	 */
	@RequestMapping("/toSubmitCheck")
	public String toSubmitCheck(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		Integer parentId = 0;
		TaskNeed n = new TaskNeed();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (n != null && !teamNeedService.isCurrentMember(n.getAssignedId())) {
				return "nopower";
			}
			parentId = n.getParentId();
			model.addAttribute("n", n);
		}
		if (parentId == 0) {
			Integer needId = n.getId();
			//获取父模块下所有任务
			List<Map<String, Object>> needTask = teamNeedService.getNeedTaskByProject(needId);
			//获取父模块下所有子模块
			List<Map<String, Object>> subNeed = teamNeedService.getSubNeedByProject(needId);
			//获取所有子模块下任务
			List<Map<String, Object>> subNeedTask = teamNeedService.getSubNeedTaskByProject(needId);
			//获取所有任务下的所有测试用例列表
			List<Map<String, Object>> testCase = teamNeedService.getTestCaseByProject(needId);
			//获取所有测试用例下的所有步骤
			List<Map<String, Object>> testCaseStep = teamNeedService.getTestCaseStepByProject(needId);
			
			model.addAttribute("needTask", needTask);
			model.addAttribute("subNeed", subNeed);
			model.addAttribute("subNeedTask", subNeedTask);
			model.addAttribute("testCase", testCase);
			model.addAttribute("testCaseStep", testCaseStep);
			publicResult(model);
			return "my/need/submitCheckParent";
		}
		model.addAttribute("members", teamNeedService.getAllMember());
		publicResult(model);
		return "my/need/submitCheck";
	}

	/**
	 * 提交验收
	 */
	@RequestMapping("/submitCheck")
	public void submitCheck(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// TODO 是否需要对模块的状态进行判断，例如：只有测试完成的模块才能进行验收操作，获取再toCheck中进行验证
		// mvm eg :{r=0.29616789999172366, stage=y, comment=, id=25}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id")) || GiantUtil.isEmpty(mvm.get("checked_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (n != null && !teamNeedService.isCurrentMember(n.getAssignedId())) {
				json.put("code",2);
				json.put("message", "你没有权限提交验收当前模块！");
				resultresponse(response,json);
				return;
			}
		}
		boolean flag = teamNeedService.submitCheck(mvm);
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
	 * 提交验收
	 */
	@RequestMapping("/submitCheckParent")
	public void submitCheckParent(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		// TODO 是否需要对模块的状态进行判断，例如：只有测试完成的模块才能进行验收操作，获取再toCheck中进行验证
		// mvm eg :{r=0.29616789999172366, stage=y, comment=, id=25}
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (n != null && !teamNeedService.isCurrentMember(n.getAssignedId())) {
				json.put("code",2);
				json.put("message", "你没有权限提交验收当前模块！");
				resultresponse(response,json);
				return;
			}
		}
		boolean flag = teamNeedService.submitCheck(mvm);
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
	 * 跳转确认原型图页面
	 */
	@RequestMapping("/toConfirmPrototypeFigure")
	public String toConfirmPrototypeFigure(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("n", n);
			if (!teamNeedService.isCurrentMember(n.getMemberId())) {
				return "nopower";
			}
			String querySql = "select * from task_need where state!=0 and parent_id = " + mvm.get("id");
			List<Map<String, Object>> subList = teamNeedService.getMapListBySQL(querySql, null);
			model.addAttribute("subList", subList);
			
		}
		publicResult(model);
		return "my/need/confirmPrototypeFigure";
	}
    
	/**
	 * 确认原型图
	 */
	@RequestMapping("/confirmPrototypeFigure")
	public void confirmPrototypeFigure(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		List<TaskNeed> list = new ArrayList<TaskNeed>();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (n != null && !teamNeedService.isCurrentMember(n.getMemberId())) {
				json.put("code",2);
				json.put("message", "你没有权限确认当前模块的原型图！");
				resultresponse(response,json);
				return;
			}
			n.setPrototypeFigure((short)1);
			list.add(n);
			String querySql = "select * from task_need where state!=0 and parent_id = " + mvm.get("id");
			List<Object> object = teamNeedService.getEntityListBySQL(querySql, null, new TaskNeed());
			if (object != null && object.size() > 0) {
				for (Object o : object) {
					TaskNeed taskNeed = (TaskNeed)o;
					taskNeed.setPrototypeFigure((short)1);
					list.add(taskNeed);
				}
			}
		}
		boolean flag = teamNeedService.saveUpdateOrDelete(list, null);
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
	 * 驳回原型图
	 */
	@RequestMapping("/toRejected")
	public void rejected(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		boolean flag = false;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.REJECTED, mvm.toString(), GiantUtil.stringOf(mvm.get("remark")));
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (n != null && !teamNeedService.isCurrentMember(n.getMemberId())) {
				json.put("code",2);
				json.put("message", "你没有权限确认当前模块的原型图！");
				resultresponse(response,json);
				return;
			}
			TaskNeed oldT = new TaskNeed();
			BeanUtils.copyProperties(n, oldT);
			flag = teamNeedService.saveUpdateOrDelete(n, null);
			if (flag) {
				pmLog.add(n.getId(), oldT, n,"remark");
				teamNeedService.log(pmLog);
			}
		}
		
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
