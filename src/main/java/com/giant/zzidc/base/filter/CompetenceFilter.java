package com.giant.zzidc.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * 权限拦截,仅仅拦截(.action,.do结尾的权限连接)
 * 
 * @author l0uj1e
 * 
 */
public class CompetenceFilter implements Filter {

	private Logger logger = Logger.getLogger(CompetenceFilter.class);
	
	public void destroy() {

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	public static void main(String[] args) {
		System.out.println("gasdfadf".contains("."));
	}
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String uri = "";
		try {
			uri = request.getRequestURI().toLowerCase();
		} catch (Exception e) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		// 1.当时JavaScript,图片,flash,样式的时候,不做权限拦截
		if (uri == null || uri.isEmpty()/* 为空 */
				|| uri.endsWith(".js")/* JavaScript */
				|| uri.endsWith(".gif") || uri.endsWith(".png")
				|| uri.endsWith(".swf") || uri.endsWith(".jpg")/* 图片、flash */
				|| uri.endsWith(".css")/* 样式 */ || uri.endsWith(".ico")
				|| uri.endsWith(".woff") || uri.endsWith(".ttf")//字体
				|| uri.endsWith("index.jsp")) {//首页
			arg2.doFilter(arg0, arg1);
			return;
		}
		if (!uri.contains(".")) {
			if (uri.endsWith("nopower") || uri.endsWith("login") || uri.endsWith("logout") || 
					uri.endsWith("toLogin") || uri.equals("/") || uri.equals("/team_gainet_com/")
					|| uri.endsWith("/my/weixin") || uri.endsWith("/my/unbindwx") || uri.endsWith("/my/checkbindwx") || uri.endsWith("/my/getOpenId")) {
				arg2.doFilter(arg0, arg1);
				logger.debug("用户跳转没有权限页面或登录页面或登录、登出");
				return;
			}
			HttpSession session = request.getSession();
			String path = "";
			try {
				path = request.getServletPath().replaceFirst("/", "");
			} catch (Exception e) {
				e.printStackTrace();
				arg2.doFilter(arg0, arg1);
				return;
			}
			if (isHasPower(session, path)) {// 存在权限
				arg2.doFilter(arg0, arg1);
				return;
			} else {// 没有权限
				logger.debug("管理员[" + String.valueOf(session.getAttribute("memberId")) + "]没有[" +path+"]该路径权限");
				HttpServletResponse response = (HttpServletResponse) arg1;
				response.sendRedirect(request.getContextPath()
						+ "/nopower");
				return;
			}
		}
		/* 3.当没有遇到上面的主当时,会直接的放行 */
		arg2.doFilter(arg0, arg1);
	}

	/**
	 * 获取用户权限连接列表
	 * 
	 * @param adminId
	 *            用户编号
	 * @param path
	 *            路径地址
	 * @return
	 */
	private boolean isHasPower(HttpSession session, String path) {
		try {
			if("my/getOpenId".equals(path)) {//微信接口不拦截
				return true;
			}
			return Authentication.hasAuthPath(session, path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
