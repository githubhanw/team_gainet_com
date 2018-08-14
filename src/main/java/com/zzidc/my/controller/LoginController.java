package com.zzidc.my.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.giant.zzidc.base.action.GiantBaseController;
import com.giant.zzidc.base.filter.Authentication;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.my.service.LoginService;
import com.zzidc.team.entity.MemberConfig;

import net.sf.json.JSONObject;

/**
 * 登录
 */
@Controller
@RequestMapping({ "/" })
public class LoginController extends GiantBaseController {
	@Autowired
	private LoginService loginService;
	/**
	 * 登录页面
	 */
	@RequestMapping("/toLogin")
	public String task(@RequestParam Map<String, String> mvm, Model model) {
		return "login";
	}
	
	/**
	 * 登录
	 */
	@RequestMapping("/login")
	public void login(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response) {
		JSONObject json=new JSONObject();
		if(GiantUtil.isEmpty(mvm.get("u")) || GiantUtil.isEmpty(mvm.get("p"))){
			json.put("result", "参数不足");
			resultresponse(response,json);
			return;
		}
		String username = GiantUtil.stringOf(mvm.get("u"));
		String password = GiantUtil.stringOf(mvm.get("p"));
		Map<String, Object> prm = new HashMap<String, Object>();
		prm.put("username", username);
		prm.put("password", new SimpleHash("SHA-1",password).toString());
		String sql = "select * from member where USERNAME=:username and PASSWORD=:password and STATUS=0";
		List<Map<String, Object>> memberInfoList = loginService.dao.getMapListBySQL(sql, prm);
		if (memberInfoList == null || memberInfoList.size() != 1) {
			json.put("result", "usererror");
			resultresponse(response,json);
			return;
		}
		Map<String, Object> memberInfo = memberInfoList.get(0);
		memberInfo.put("PASSWORD", "");//密码设置为空
		HttpSession session = request.getSession();
		session.setAttribute("memberInfo", memberInfo);
		session.setAttribute("memberName", String.valueOf(memberInfo.get("NAME")));
		session.setAttribute("loginName", String.valueOf(memberInfo.get("USERNAME")));
		session.setAttribute("memberId", Integer.parseInt(String.valueOf(memberInfo.get("id"))));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("number", memberInfo.get("NUMBER").toString());
		MemberConfig mc = (MemberConfig) loginService.getEntityByHQL("MemberConfig", map);
		if(mc != null) {
			session.setAttribute("roleIds", mc.getRoleIds());
		}
		//设置权限 暂无
		session.setAttribute("power", Authentication.getPowerList(memberInfo.get("NUMBER").toString(), loginService));
		json.put("result", "success");
		resultresponse(response,json);
	}

	/**
	 * 登出
	 */
	@RequestMapping("/logout")
	public String logout(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("memberInfo");
		session.removeAttribute("memberName");
		session.removeAttribute("memberId");
		session.removeAttribute("power");
		return "redirect:/toLogin";
	}
	
	/**
	 * 没权限
	 */
	@RequestMapping("/nopower")
	public String nopower(@RequestParam Map<String, String> mvm, Model model, HttpServletRequest request, HttpServletResponse response) {
		return "nopower";
	}
}