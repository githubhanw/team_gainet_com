package com.zzidc.team.controller;

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

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.Publish;
import com.zzidc.team.service.ReleaseService;

import net.sf.json.JSONObject;

/**
 * 发布
 */
@Controller
@RequestMapping({ "/release" })
public class ReleaseController extends GiantBaseController {
	@Autowired
	private ReleaseService releaseService;
	private GiantPager conditionPage = null;
	private String requestURL = "release/index";

	public void publicResult(Model model) {
		model.addAttribute("m", "release");// 模块
		model.addAttribute("s", "release");// 子模块
		model.addAttribute("u", requestURL);// 请求地址
	}

	/**
	 * 发布列表
	 * 
	 * @param mvm
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String list(@RequestParam Map<String, String> mvm, Model model) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		if ("".equals(GiantUtil.stringOf(mvm.get("orderColumn")))) {
			mvm.put("orderColumn", "update_time");
			mvm.put("orderByValue", "DESC");
			mvm.put("currentPage", "1");
		}
		if ("".equals(GiantUtil.stringOf(mvm.get("type")))) {
			mvm.put("type", "0");// 未发布
		}
		Map<String, String> queryCondition = conditionPage.getQueryCondition();
		// 查询条件封装
		queryCondition.clear();
		queryCondition.putAll(mvm);
		conditionPage.setCurrentPage(GiantUtil.intOf(mvm.get("currentPage"), 1));
		conditionPage.setPageSize(GiantUtil.intOf(mvm.get("pageSize"), 15));
		conditionPage.setOrderColumn(GiantUtil.stringOf(mvm.get("orderColumn")));
		pageList = releaseService.getPageList(conditionPage);
		requestURL = "release/index";
		pageList.setDesAction(requestURL);
		model.addAttribute("pageList", pageList);
		model.addAttribute("prm", mvm);
		publicResult(model);
		return "release/list";
	}

	/**
	 * 提交更新页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/toSubmit")
	public String toSubmit(Model model) {
		publicResult(model);
		return "release/toSubmit";
	}

	/**
	 * 提交更新
	 * 
	 * @param mvm
	 * @param response
	 */
	@RequestMapping("/submit")
	public void submit(@RequestParam Map<String, String> mvm, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if (GiantUtil.isEmpty(mvm.get("name"))) {
			json.put("code", 1);
			json.put("message", "参数不足");
			resultresponse(response, json);
			return;
		}
		Publish p = new Publish();
		p.setName(GiantUtil.stringOf(mvm.get("name")));
		p.setContent(GiantUtil.stringOf(mvm.get("content")));
		p.setSite(GiantUtil.stringOf(mvm.get("site")));
		p.setPerformSql(GiantUtil.stringOf(mvm.get("performSql")));
		p.setSpecial(GiantUtil.stringOf(mvm.get("special")));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		p.setState((short) 0);// 未发布
		p.setCreateTime(new Timestamp(System.currentTimeMillis()));
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean flag = releaseService.saveUpdateOrDelete(p, null);
		if (flag) {
			json.put("code", 0);
			json.put("message", "提交更新成功");
		} else {
			json.put("code", 1);
			json.put("message", "提交更新失败");
		}
		resultresponse(response, json);
	}

	/**
	 * 编辑更新页面
	 * 
	 * @param mvm
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEdit")
	public String toEdit(@RequestParam Map<String, String> mvm, Model model) {
		if (GiantUtil.intOf(mvm.get("id"), 0) != 0) {
			Publish p = (Publish) releaseService.getEntityByPrimaryKey(new Publish(),
					GiantUtil.intOf(mvm.get("id"), 0));
			model.addAttribute("p", p);
		}
		publicResult(model);
		return "release/toEdit";
	}

	/**
	 * 编辑更新
	 * 
	 * @param mvm
	 * @param response
	 */
	@RequestMapping("/edit")
	public void edit(@RequestParam Map<String, String> mvm, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if (GiantUtil.isEmpty(mvm.get("name"))) {
			json.put("code", 1);
			json.put("message", "参数不足");
			resultresponse(response, json);
			return;
		}
		Publish p = (Publish) releaseService.getEntityByPrimaryKey(new Publish(), GiantUtil.intOf(mvm.get("id"), 0));
		if (p == null) {
			json.put("code", 1);
			json.put("message", "此更新不存在");
			resultresponse(response, json);
			return;
		}
		p.setName(GiantUtil.stringOf(mvm.get("name")));
		p.setContent(GiantUtil.stringOf(mvm.get("content")));
		p.setSite(GiantUtil.stringOf(mvm.get("site")));
		p.setPerformSql(GiantUtil.stringOf(mvm.get("performSql")));
		p.setSpecial(GiantUtil.stringOf(mvm.get("special")));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean flag = releaseService.saveUpdateOrDelete(p, null);
		if (flag) {
			json.put("code", 0);
			json.put("message", "编辑更新成功");
		} else {
			json.put("code", 1);
			json.put("message", "编辑更新失败");
		}
		resultresponse(response, json);
	}

	/**
	 * 接收更新页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/toReceive")
	public String toReceive(Model model) {
		String querySql = "select * from publish where state = 0";
		List<Map<String, Object>> publishList = releaseService.getMapListBySQL(querySql, null);
		model.addAttribute("publishList", publishList);
		publicResult(model);
		return "release/toReceive";
	}
    
	/**
	 * 接收更新
	 * @param mvm
	 * @param request
	 * @param response
	 */
	@RequestMapping("/receive")
	public void receive(@RequestParam Map<String, String> mvm, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String[] test = request.getParameterValues("test");
		if (GiantUtil.isEmpty(mvm.get("version")) || test == null || test.length == 0) {
			json.put("code", 1);
			json.put("message", "请选择模块并填写版本号");
			resultresponse(response, json);
			return;
		}
		List<Publish> publishList = new ArrayList<Publish>();
		for (int i = 0; i < test.length; i++) {
			Integer id = Integer.valueOf(test[i]);
			Publish p = (Publish) releaseService.getEntityByPrimaryKey(new Publish(), id);
			p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			p.setState((short) 1);//待发布
			p.setVersion(GiantUtil.stringOf(mvm.get("version")));
			p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
			publishList.add(p);
		}
		boolean flag = releaseService.saveUpdateOrDelete(publishList, null);
		if (flag) {
			json.put("code", 0);
			json.put("message", "接收更新成功");
		} else {
			json.put("code", 1);
			json.put("message", "接收更新失败");
		}
		resultresponse(response, json);
	}
    
	/**
	 * 确认更新页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toConfirm")
	public String toConfirm(Model model) {
		String querySql = "select * from publish where state=1";
		List<Map<String, Object>> publishList = releaseService.getMapListBySQL(querySql, null);
		model.addAttribute("publishList", publishList);
		publicResult(model);
		return "release/toConfirm";
	}
    
	/**
	 * 确认更新
	 * @param mvm
	 * @param request
	 * @param response
	 */
	@RequestMapping("/confirm")
	public void confirm(@RequestParam Map<String, String> mvm, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		String[] test = request.getParameterValues("test");
		if (test == null || test.length == 0) {
			json.put("code", 1);
			json.put("message", "没有需要确认更新的模块，请原路返回！");
			resultresponse(response, json);
			return;
		}
		String isUdpate = GiantUtil.stringOf(mvm.get("isUpdate"));
		List<Publish> publishList = new ArrayList<Publish>();
		for (int i = 0; i < test.length; i++) {
			Integer id = Integer.valueOf(test[i]);
			Publish p = (Publish) releaseService.getEntityByPrimaryKey(new Publish(), id);
			p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			if ("1".equals(isUdpate)) {
				p.setState((short) 2);
			}
			p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
			publishList.add(p);		
		}
		boolean flag = releaseService.saveUpdateOrDelete(publishList, null);
		if (flag) {
			json.put("code", 0);
			json.put("message", "确认更新成功");
		} else {
			json.put("code", 1);
			json.put("message", "确认更新失败");
		}
		resultresponse(response, json);
	}
	
	/**
	 * 确认更新页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/details")
	public String details(Model model) {
		String querySql = "select * from publish where state=1";
		List<Map<String, Object>> publishList = releaseService.getMapListBySQL(querySql, null);
		model.addAttribute("publishList", publishList);
		publicResult(model);
		return "release/details";
	}

}
