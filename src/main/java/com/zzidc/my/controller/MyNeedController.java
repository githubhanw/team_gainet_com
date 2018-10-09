package com.zzidc.my.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	 * 模块列表
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
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String needId=dfs.format(new Date());
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
				GiantUtil.isEmpty(mvm.get("department_id")) || GiantUtil.isEmpty(mvm.get("start_date")) ||
				GiantUtil.isEmpty(mvm.get("need_remark")) || GiantUtil.isEmpty(mvm.get("check_remark")) || 
				GiantUtil.isEmpty(mvm.get("member_id")) || GiantUtil.isEmpty(mvm.get("src_id"))){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
		}
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String needId=dfs.format(new Date());
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
	public String toAddSon(@RequestParam Map<String, String> mvm, Model model) {
		//添加模块页面的项目列表
		model.addAttribute("need_id",GiantUtil.intOf(mvm.get("need_id"), 0));
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
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "tn.update_time");
			mvm.put("orderByValue", "DESC");
			mvm.put("currentPage", "1");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "");//未关闭
		}
		if("".equals(GiantUtil.stringOf(mvm.get("search")))){
			mvm.put("search", "");
		}
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		//查询条件封装
		queryCondition.clear();
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(300);
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = teamNeedService.getPageListThisProject(GiantUtil.intOf(mvm.get("project_id"), 0),conditionPage);
		model.addAttribute("project", teamNeedService.getTeamProjectByID(GiantUtil.intOf(mvm.get("project_id"), 0)));
		model.addAttribute("project_id", GiantUtil.intOf(mvm.get("project_id"), 0));
		model.addAttribute("pageList", pageList);
		requestURL = "team/need/toEachAdd";
		pageList.setDesAction(requestURL);
		publicResult(model);
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
			model.addAttribute("n", n);
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
		if(GiantUtil.isEmpty(mvm.get("assigned_id")) || GiantUtil.isEmpty(mvm.get("plan_end_date")) ){
			json.put("code",1);
			json.put("message", "参数不足");
			resultresponse(response,json);
			return;
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
			json.put("message", "不能对该模块进行变更操作");
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
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(!teamNeedService.isCanOperation(mvm)) {
				return "nopower";
			}
			if (!teamNeedService.isCurrentMember(n.getCheckedId())) {
				return "nopower";
			}
			model.addAttribute("n", n);
		}
		Map<String, Object> needDetail = teamNeedService.getNeedDetail(GiantUtil.intOf(mvm.get("id"), 0));
		model.addAttribute("needM", needDetail);
		List<Map<String, Object>> codeReport = teamNeedService.getCodeReport(GiantUtil.intOf(mvm.get("id"), 0));
		model.addAttribute("codeReport", codeReport);
		List<Map<String, Object>> codeInterface = teamNeedService.getCodeInterface(GiantUtil.intOf(mvm.get("id"), 0));
		model.addAttribute("codeInterface", codeInterface);
		publicResult(model);
		return "my/need/check";
	}

	/**
	 * 模块验收
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
		if(!teamNeedService.isCanOperation(mvm)) {
			json.put("code",1);
			json.put("message", "不能对该模块进行验收操作");
			return;
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
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed n = (TaskNeed) teamNeedService.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if (!teamNeedService.isCurrentMember(n.getAssignedId())) {
				return "nopower";
			}
			model.addAttribute("n", n);
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

	
}
