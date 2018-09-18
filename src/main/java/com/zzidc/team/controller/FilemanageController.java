package com.zzidc.team.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.FileUploadUtil;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.FileManage;
import com.zzidc.team.service.FilemanageService;
import net.sf.json.JSONObject;

/**
 * [文档 Controller]
 * @author chenmenghao
 * @date 2018年9月9日
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
@Controller
@RequestMapping({ "/filemanage/manage" })
public class FilemanageController extends GiantBaseController{
	private GiantPager conditionPage = null;
	@Autowired
	private FilemanageService filemanageService;
	@Autowired
	private GiantBaseService baseService;
	private String requestURL = "filemanage/manage/index";
	public void publicResult(Model model) {
		model.addAttribute("m", "filemanage");//模块
		model.addAttribute("s", "manage");//子模块
		model.addAttribute("u", requestURL);//请求地址
	}
	/**
	 * 跳到首页面
	 */
	@RequestMapping("/index")
	public String index(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
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
		pageList = filemanageService.getPageList(conditionPage);
		requestURL = "filemanage/manage/index?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		System.out.println("路径:"+requestURL);
		pageList.setDesAction(requestURL);
		model.addAttribute("members", filemanageService.getAllMember());
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "filemanage/manage/index";
	}
	/**
	 * 跳转到详情页面
	 */
	@RequestMapping("/detail")
	public String detail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			FileManage f = (FileManage) filemanageService.getEntityByPrimaryKey(new FileManage(), GiantUtil.intOf(mvm.get("id"), 0));
			if(f != null) {
				model.addAttribute("FileManage", f);
			}
		}
		publicResult(model);
		return "filemanage/manage/detail";
	}
	/**
	 * 跳转到我的详情页面
	 */
	@RequestMapping("/mydetail")
	public String mydetail(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			FileManage f = (FileManage) filemanageService.getEntityByPrimaryKey(new FileManage(), GiantUtil.intOf(mvm.get("id"), 0));
			if(f != null) {
				model.addAttribute("FileManage", f);
			}
			if(f.getFileClassification().equals("0")){
				Object a=baseService.getSingleDataBySql("select project_name from task_project where id = "+f.getGlId(),null);
				model.addAttribute("glname", a.toString());
			}else{
				Object a=baseService.getSingleDataBySql("select need_name from task_need where id = "+f.getGlId(),null);
				model.addAttribute("glname", a.toString());
			}
		}
		publicResult(model);
		return "filemanage/manage/mydetail";
	}
	/**
	 * 跳转到我的文档编辑页面
	 */
	@RequestMapping("/tomyedit")
	public String tomyedit(@RequestParam Map<String, String> mvm, Model model) {
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			FileManage f = (FileManage) filemanageService.getEntityByPrimaryKey(new FileManage(), GiantUtil.intOf(mvm.get("id"), 0));
			if(f != null) {
			model.addAttribute("FileManage", f);
			}
			if(f.getFileClassification().equals("0")){
				Object a=baseService.getSingleDataBySql("select project_name from task_project where id = "+f.getGlId(),null);
				model.addAttribute("glname", a.toString());
			}else{
				Object a=baseService.getSingleDataBySql("select need_name from task_need where id = "+f.getGlId(),null);
				model.addAttribute("glname", a.toString());
			}
			String sqlone="select id, project_name from task_project";//查询项目的id和名称
			String sqltwo="select id, need_name from task_need";//查询需求的id和名称
			List<Map<String, Object>> map=new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> map1=new ArrayList<Map<String, Object>>();
			map=filemanageService.getMapListBySQL(sqlone, null);
			map1=filemanageService.getMapListBySQL(sqltwo, null);
			model.addAttribute("tp", map);//查询出来的项目数据
			model.addAttribute("tn", map1);//查询出来的需求模块
		}
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "filemanage/manage/myedit";
	}
	/**
	 * 编辑我的文档(执行更新操作)
	 */
	@RequestMapping("/myedit")
	public void myedit(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) {
		JSONObject json=new JSONObject();
		int id=baseService.getMemberId();	//登录id	        
        String name=baseService.getMemberName();   //登录姓名
        //判断是否登录
        if(name.equals("")){
        json.put("code", 4);
        json.put("message", "请重新登录");
        resultresponse(response,json);
		return;
        }
        if(mvm.get("file_type").equals("0")&&GiantUtil.isEmpty(mvm.get("xm"))){
		json.put("code",2);
		json.put("message", "请选择项目");
		resultresponse(response,json);
		return;
        }else if(mvm.get("file_type").equals("1")&&GiantUtil.isEmpty(mvm.get("xq"))){
		json.put("code",3);
		json.put("message", "请选择需求");
		resultresponse(response,json);
		return;
        }else{
		if(GiantUtil.isEmpty(mvm.get("file_name"))||GiantUtil.isEmpty(mvm.get("file_type"))
			||GiantUtil.isEmpty(mvm.get("xm"))&&GiantUtil.isEmpty(mvm.get("xq"))){
		json.put("code",1);
		json.put("message", "参数不完整");
		resultresponse(response,json);
		return;
		}else {
			//获取对象
		FileManage f = (FileManage) filemanageService.getEntityByPrimaryKey(new FileManage(), GiantUtil.intOf(mvm.get("fi_id"), 0));
		boolean flag = filemanageService.edit(mvm,f,file);
		if(flag){
			json.put("code",0);
			json.put("message", "修改成功");
			}else{
			json.put("code",1);
			json.put("message", "修改失败");
			}
		}
		resultresponse(response,json);
		return;
	}
	}
	/**
	 * 删除(执行删除操作)
	 */
	@RequestMapping("/delete")
	public void delete(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			FileManage f = (FileManage) filemanageService.getEntityByPrimaryKey(new FileManage(), GiantUtil.intOf(mvm.get("id"), 0));
			f.setAccessControl("0");
			boolean flag = filemanageService.saveUpdateOrDelete(f,null);
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
	 * 跳到创建文档页面
	 */
	@RequestMapping("/toaddwd")
	public String toaddwd(@RequestParam Map<String, String> mvm, Model model) {
		String sqlone="select id, project_name from task_project";//查询项目的id和名称
		String sqltwo="select id, need_name from task_need";//查询需求的id和名称
		List<Map<String, Object>> map=new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> map1=new ArrayList<Map<String, Object>>();
		map=filemanageService.getMapListBySQL(sqlone, null);
		map1=filemanageService.getMapListBySQL(sqltwo, null);
		model.addAttribute("tp", map);//查询出来的项目数据
		model.addAttribute("tn", map1);//查询出来的需求模块
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "filemanage/manage/addwd";
		//return "milepost/manage/add";
	}
	/**
	 * 创建文档
	 * 
	 */
	@RequestMapping("/addwd")
	public void addwd(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response,@RequestParam("file")MultipartFile[] file) {
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
       if(mvm.get("file_type").equals("0")&&GiantUtil.isEmpty(mvm.get("xm"))){
			json.put("code",2);
			json.put("message", "请选择项目");
			resultresponse(response,json);
			return;
       }else if(mvm.get("file_type").equals("1")&&GiantUtil.isEmpty(mvm.get("xq"))){
			json.put("code",3);
			json.put("message", "请选择需求");
			resultresponse(response,json);
			return;
       }else{
		if(GiantUtil.isEmpty(mvm.get("file_name"))||GiantUtil.isEmpty(mvm.get("file_type"))
		   ||GiantUtil.isEmpty(mvm.get("xm"))&&GiantUtil.isEmpty(mvm.get("xq"))){
			json.put("code",1);
			json.put("message", "参数不完整");
			resultresponse(response,json);
			return;
		}else {
			Object url=FileUploadUtil.uploadFile(file);
			String fileName = file[0].getOriginalFilename();
			MultipartFile f =file[0];
			String hz = fileName.substring(fileName.lastIndexOf("."));
			String gs=hz.substring(1);
			if(url==null){
			json.put("code",5);
			json.put("message", "上传失败，只支持JPG、PNG、GIF、JPEG、BMP");
			resultresponse(response,json);
			return;
			}
		    boolean flag = filemanageService.add(mvm,id,name,gs,url.toString(),fileName);
			if(flag){
			json.put("code",0);
			json.put("message", "创建成功");
			}else{
			json.put("code",1);
			json.put("message", "创建失败");
			}
		resultresponse(response,json);
		return;
		}
		}	
		}	
	/**
	 * 跳到创建文档库页面
	 */
	@RequestMapping("/toaddwdk")
	public String toaddwdk(@RequestParam Map<String, String> mvm, Model model) {
		publicResult(model);
		model.addAttribute("s", "manage");//子模块
		return "filemanage/manage/addwdk";
		//return "milepost/manage/add";
	}
}
