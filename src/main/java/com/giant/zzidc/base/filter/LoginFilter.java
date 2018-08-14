package com.giant.zzidc.base.filter;

import java.io.IOException;
import java.util.Map;

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

public class LoginFilter implements Filter {
	private Logger logger = Logger.getLogger(LoginFilter.class);
	private String baseUrl;
	
	public void destroy() {

	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String requestPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + request.getServletPath();
		if (requestPath == null || requestPath.isEmpty() || requestPath.endsWith(".js") || requestPath.endsWith(".gif") || 
				requestPath.endsWith(".png") || requestPath.endsWith(".swf") || requestPath.endsWith(".jpg") || 
				requestPath.endsWith(".css") || requestPath.endsWith(".woff") || requestPath.endsWith(".ttf")) {
			/* 空、JavaScript、图片、 flash、样式表放行 */
			arg2.doFilter(arg0, arg1);
			return;
		}
		if("/toLogin".equals(request.getServletPath()) || "/login".equals(request.getServletPath()) || "".equals(request.getServletPath()) || "/my".equals(request.getServletPath())) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		Map<String, Object> memberSession = null; 
		if (session.getAttribute("memberInfo") == null) {
			response.sendRedirect(baseUrl + "toLogin");
			return;
		} else {
			memberSession = (Map<String, Object>) session.getAttribute("memberInfo");
		}
		if(memberSession == null){
			logger.debug("未获取到登陆信息");
			response.sendRedirect(baseUrl + "logout");
			return;
		}
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		logger.info("初始化过滤器...");
//		pushApplicatinIp(arg0);
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
