package com.zzidc.my.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.giant.zzidc.base.utils.PlusCut;
import com.giant.zzidc.base.utils.PropertyUtils;
import com.giant.zzidc.base.utils.ResultInfo;
import com.zzidc.my.service.MyService;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.Role;
import com.zzidc.team.service.OrganizationRoleService;
import com.zzidc.team.service.TeamNeedService;
import com.zzidc.team.service.TeamTaskService;
import com.zzidc.team.service.TestApplyService;
import com.zzidc.team.service.TestBugService;

import net.sf.json.JSONObject;

/**
 * 我的地盘
 */
@Controller
@RequestMapping({ "/my" })
public class MyController extends GiantBaseController {
	@Autowired
	private MyService myService;
	@Autowired
	private TeamTaskService teamTaskService;
	@Autowired
	private TestBugService testBugService;
	@Autowired
	private TestApplyService testApplyService;
	@Autowired
	private TeamNeedService teamNeedService;
	@Autowired
	private OrganizationRoleService organizationRoleService;
	private GiantPager conditionPage = null;
	private String requestURL = "";

	public void publicResult(Model model) {
		model.addAttribute("m", "my");//模块
		model.addAttribute("s", "task");//子模块
	}
	
	/**
	 * 首页
	 */
	@RequestMapping("")
	public String index(@RequestParam Map<String, String> mvm, Model model) {
		//统计数据
		//所有任务相关统计
		Map<String, Object> taskCount = myService.getTaskCount();
		//所有bug相关统计
		Map<String, Object> bugCount = myService.getBugCount();
		//所有测试相关统计
		Map<String, Object> testCount = myService.getTestCount();
		//所有模块相关统计
		Map<String, Object> needCount = myService.getNeedCount();

		model.addAttribute("taskCount", taskCount);
		model.addAttribute("bugCount", bugCount);
		model.addAttribute("testCount", testCount);
		model.addAttribute("needCount", needCount);
		publicResult(model);
		model.addAttribute("s", "");//子模块
		return "my/index";
	}
	
	/**
	 * 我的任务
	 */
	@RequestMapping("/task")
	public String task(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "t.update_time");
			mvm.put("orderByValue", "DESC");
			mvm.put("currentPage", "1");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "8");
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
		pageList = teamTaskService.getPageList(conditionPage);
		if("1".equals(mvm.get("type")) || "2".equals(mvm.get("type"))) {
			teamTaskService.getSubTaskList(pageList.getPageResult(), mvm.get("type"), mvm.get("orderColumn"), mvm.get("orderByValue"));
		}
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "my/task";
	}

	/**
	 * 我的Bug
	 */
	@RequestMapping("/bug")
	public String bug(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "tb.id");
			mvm.put("orderByValue", "DESC");
			mvm.put("currentPage", "1");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "6");
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
		requestURL = "my/bug";
		pageList.setDesAction(requestURL);
		model.addAttribute("members", testBugService.getAllMember());
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		model.addAttribute("s", "bug");//子模块
		return "my/bug";
	}

	/**
	 * 我的测试
	 */
	@RequestMapping("/test")
	public String test(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "id");
			mvm.put("orderByValue", "DESC");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "1");
			try {
				int juese =Integer.parseInt(super.getSession().getAttribute("roleIds").toString());
				if (juese != 5 && juese != 9) {
					mvm.put("type", "5");
				}
			} catch (Exception e) {
			}
		}
		if("1".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("orderColumn", "apply_time");
			mvm.put("orderByValue", "ASC");
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
		pageList = testApplyService.getPageList(conditionPage);
		requestURL = "my/test?type=" + mvm.get("type") + "&currentPage=" + pageList.getCurrentPage() + "&pageSize=" + pageList.getPageSize() + "&search=" + mvm.get("search");
		pageList.setDesAction(requestURL);
		model.addAttribute("members", testApplyService.getAllMember());
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		model.addAttribute("s", "test");//子模块
		return "my/test";
	}

	/**
	 * 我的模块
	 */
	@RequestMapping("/need")
	public String need(@RequestParam Map<String, String> mvm, Model model) {
		if(conditionPage == null){
			conditionPage = new GiantPager();
		}
		if("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))){
			mvm.put("orderColumn", "tn.update_time");
			mvm.put("orderByValue", "DESC");
			mvm.put("currentPage", "1");
		}
		if("".equals(GiantUtil.stringOf(mvm.get("type")))){
			mvm.put("type", "7");
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
		if("20".equals(mvm.get("type")) || "21".equals(mvm.get("type"))) {
			teamNeedService.getSubNeedList(pageList.getPageResult(), mvm.get("type"));
		}
		model.addAttribute("pageList", pageList);
		model.addAttribute("needSrc", teamNeedService.getNeedSrc());
		model.addAttribute("meetings", teamNeedService.getRelateMeetings());
		model.addAttribute("prm", mvm);
		publicResult(model);
		model.addAttribute("s", "need");//子模块
		return "my/need";
	}
	
	/**
	 * 跳转到权限页面
	 */
	@RequestMapping("/auth")
	public String toAuth(@RequestParam Map<String, String> mvm, Model model) {
		//获取对象
		Role n = (Role) organizationRoleService.getEntityByPrimaryKey(new Role(), Integer.parseInt(super.getSession().getAttribute("roleIds").toString()));
		model.addAttribute("entity", n);
		model.addAttribute("allPrivileges", organizationRoleService.getAllPrivileges());
		publicResult(model);
		model.addAttribute("s", "auth");//子模块
		return "my/auth";
	}
	
	/**
	 * 绑定微信
	 * @param model
	 * @return
	 */
	@RequestMapping("/weixin")
	public String toWeixin(Model model){
		int adminid=myService.getMemberId();//当前登录人ID
		Member login = (Member)teamNeedService.getEntityByPrimaryKey(new Member(),adminid);
		System.out.println("adminid="+adminid+" login="+login);
		if (GiantUtil.isEmpty(login.getNewOpenid())) {//未绑定微信
			try {
				String getOpenIdInterface = PropertyUtils.readProperty("config.properties", "WEIXIN_OPENID_INTERFACE");//手机站接口提供openID
				String getOpenIdThisUrl = PropertyUtils.readProperty("config.properties", "WEIXIN_OPENID_THISURL");//回调路径
				model.addAttribute("bindUrl", URLEncoder.encode(getOpenIdInterface+"?userId="+adminid+"&redirectUrl="+getOpenIdThisUrl, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			model.addAttribute("bdwx","yes");
		}
		return "my/weixin";
	}
	
	/**
	 * 更新openID
	 */
	@RequestMapping("/getOpenId")
	public String getOpenId(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request) {
		int userId =0;
		if (GiantUtils.isEmpty(mvm.get("userId"))) {
			//userId =Integer.parseInt(super.getSession().getAttribute("roleIds").toString());
			userId =myService.getMemberId();//当前登录人ID
		}else {
			userId = Integer.valueOf(mvm.get("userId"));
		}
		String openId = mvm.get("openId");
		openId =openId.replaceAll(" ","+" );
		openId = PlusCut.getInstance().cut(openId);//解密
		boolean a = myService.updateOpenId(openId,userId);
		if (a) {//session信息内添加openID 信息
//			HttpSession session = request.getSession();
//			session.setAttribute("newOpenId", openId);
//			Map<String, Object> memberInfo =(Map<String, Object>) session.getAttribute("memberInfo");
//			memberInfo.put("NEW_OPENID", openId);
//			session.setAttribute("memberInfo", memberInfo);
		}
		System.out.println(" ****************userId ="+userId+" ****************openID ="+openId+" ****************是否更新openID:"+a);
		return "my/weixin";
	}

	/**
	 * 解除微信绑定
	 */
	@RequestMapping("/unbindwx")
	public void add(@RequestParam Map<String, String> mvm, Model model, HttpServletResponse response, HttpServletRequest request) {
		Member login = (Member)teamNeedService.getEntityByPrimaryKey(new Member(),myService.getMemberId());
		JSONObject json=new JSONObject();
		if(login==null){
			json.put("code",1);
			json.put("message", "会员信息不存在");
			resultresponse(response,json);
			return;
		}
		if(GiantUtils.isEmpty(login.getNewOpenid())){
			json.put("code",1);
			json.put("message", "未绑定微信");
			resultresponse(response,json);
			return;
		}
		
		boolean flag = myService.unbindwx(login);
		if(flag){
			json.put("code",0);
			json.put("message", "解除微信绑定成功");
			//清除session信息的 openID
			HttpSession session = request.getSession();
			session.setAttribute("newOpenId", null);
			Map<String, Object> memberInfo =(Map<String, Object>) session.getAttribute("memberInfo");
			memberInfo.put("NEW_OPENID", null);
			session.setAttribute("memberInfo", memberInfo);
		}else{
			json.put("code",1);
			json.put("message", "解除微信绑定失败");
		}
		resultresponse(response,json);
	}
	/**
	 * 检查微信绑定状态
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkbindwx")
	public @ResponseBody ResultInfo checkbindwx(Model model){
		Member login = (Member)teamNeedService.getEntityByPrimaryKey(new Member(),myService.getMemberId());
		return myService.checkbindwx(login);
	}


}
