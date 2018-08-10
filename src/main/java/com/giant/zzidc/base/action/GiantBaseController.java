package com.giant.zzidc.base.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtils;

/*******************************************************************************
 * Controller层基础类
 */
public class GiantBaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	/** 信息 */
	protected String message;
	protected String url;
	public GiantBaseService giantBaseService; // 基础的Service

	private String passW0rd; // 生成随机密码值

	private ServletContext application;
	public PrintWriter out = null;

	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected String s = "";
	/* 会员编号 */
	protected Long hybh = 0l;
	/* 级别编号 */
	protected Long levelid = 1l;
	/* int类型编号 */
	protected int ibh = 0;
	/* long类型编号 */
	protected long lbh = 0;
	/* 结果页面-结果信息 */
	protected String resultInfo = null;
	/* 结果页面-跳转Action地址 */
	protected String actionForward = null;
	/* 详情MAP */
	protected Map<String,Object> detailM = null;
	protected Map<String,Object> infoMap = new HashMap<String, Object>();
	/* MAP列表 */
	protected List<Map<String,Object>> listM = null;
	/* 分页结果对象 */
	protected GiantPager pageList = null;
	/* 定位导航菜单 */
	protected String nav = "";
	protected String diquid;
	private Integer gx;
	private String ss;
	/**
	 * 获取客户端IP
	 * 
	 * @author LiBaozhen
	 * @date 2014-2-16 上午11:08:15
	 * @param request
	 * @return
	 */
	public String getClientIP4(HttpServletRequest request) {
		return giantBaseService.getClientIP4(request);
	}
	/**
	 * 
	 * [输入ajax信息] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-4-20 下午5:38:21 <br>
	 * @param code  根据需要自定
	 * @param info  一般是提示信息
	 * @return <br>
	 */
	public String printAjax(HttpServletResponse response, int code,String info){
		if(out==null){
			out=this.getPrintWriter(response);
		}
		out.print("{\"code\":\""+code+"\",\"info\":\""+info+"\"}");
		out.flush();
		out.close();
		return null;
	}
	
	public  void printStr(HttpServletResponse response, String str) {
		if(out==null){
			out=this.getPrintWriter(response);
		}
		out.print(str);
		out.flush();
		out.close();
	}
	
	/**
	 * 
	 * [validform插件回调函数返回值] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-4-20 下午5:53:22 <br>
	 * @param status 状态值 只能为 y 表示成功； 或者 n 表示失败
	 * @param info 提示信息
	 * @return <br>
	 */
	public String printValidForm(HttpServletResponse response, String status,String info){
		if(out==null){
			out=this.getPrintWriter(response);
		}
		out.print("{\"status\":\""+status+"\",\"info\":\""+info+"\"}");
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 获取一个输出流
	 */
	public PrintWriter getPrintWriter(HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			return response.getWriter();
		} catch (Exception e) {
			logger.error("获取输出流出错：" + e.toString());
			return null;
		}
	}
	
	/**
	 * 
	 * [输出信息] <br>
	 * 
	 * @author LiBaozhen <br>
	 * @date 2015-12-21 上午10:55:54 <br>
	 * @param msg
	 */
	public void output(HttpServletResponse response, String msg) {
		out = getPrintWriter(response);
		if (out != null) {
			out.println(msg);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 是否post请求
	 * @author xiaxiaobing <br>
	 * @date 2016-5-4 下午3:12:34 <br>
	 * @return
	 */
	public boolean ispost(){
		String method = getRequest().getMethod();
		if("POST".equalsIgnoreCase(method)){
			return true;
		}
		return false;
	}	
	/**
	 * 获取当前请求全路径
	 * @author xiaxiaobing <br>
	 * @date 2015-11-27 下午3:39:35 <br>
	 * @return
	 */
	public String getCurrentRequestUrl(){
		String url = getRequest().getRequestURL().toString();
		return url + getRequestParams();
	}
	
	/**
	 * 获取请求的参数拼接
	 * icesummer
	 * 2015-9-23下午7:00:04
	 * @return
	 */
	public String getRequestParams(){
		Enumeration<?> paramNames = getRequest().getParameterNames();
		String paramurl ="";
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = getRequest().getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					paramurl+="&"+paramName+"="+paramValue;
				}
			}else{
				for(int i=0;i<paramValues.length;i++){
					String paramValue = paramValues[i];
					paramurl+="&"+paramName+"="+paramValue;
				}
			}
		}
		if(paramurl.length()>1){
			paramurl = paramurl.replaceFirst("&", "?").trim();
		}
		return paramurl;
	}
	// **********************************************************
	// ********************以下是setter和getter********************
	// **********************************************************

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public ServletContext getApplication() {
		application = this.getRequest().getSession().getServletContext();
		return application;
	}
	/**
	 * 获取请求
	 */
	public HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	/**
	 * 获取Session
	 */
	public HttpSession getSession() {
		HttpSession session = this.getRequest().getSession();
		return session;
	}

	/**
	 * 将Object里面Value的值为Null的转换成""
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object changeNullToSopt(Object obj) {
		try {
			if (obj == null) {
				return obj;
			}
			if (obj instanceof Map) {
				if (((Map<String, Object>) obj).size() == 0) {
					return obj;
				}
				Map<String, Object> returnMap = new HashMap<String, Object>();
				for (Map.Entry<String, Object> en : ((Map<String, Object>) obj)
						.entrySet()) {
					returnMap.put(en.getKey(),
							en.getValue() == null ? "" : en.getValue());
				}
				return returnMap;
			} else if (obj instanceof List) {
				if (((List<Object>) obj).size() == 0)
					return obj;
				List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
				for (Object object : (List<Object>) obj) {
					if (object instanceof Map) {
						Map<String, Object> nodeMap = new HashMap<String, Object>();
						for (Map.Entry<String, Object> en : ((Map<String, Object>) object)
								.entrySet()) {
							nodeMap.put(en.getKey(), en.getValue() == null ? ""
									: en.getValue());
						}
						returnList.add(nodeMap);
					} else {
						return obj;
					}
				}
				return returnList;
			} else {
				return obj;
			}
		} catch (Exception e) {
			return obj;
		}
	}

	/**
	 * 生成随机密码值
	 * 
	 * @return
	 */
	public String getPassW0rd() {
		passW0rd = GiantUtils.createRandomPassword();
		return passW0rd;
	}
	/**
	 * 重定向地址
	 */
	public void sendRedirect(HttpServletResponse response, String url){
		try {
			response.sendRedirect(getRequest().getContextPath()+"/"+url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 错误提示print<br>
	 * writer: XiaXiaobing <br>
	 * rq: 2014-2-23下午6:20:25<br>
	 * 
	 * @param string
	 * @return
	 */
	public String alertAction(HttpServletResponse response, String string) {
		PrintWriter out = getPrintWriter(response);
		out.print("<script>alert('" + string + "');history.back();</script>");
		out.close();
		return null;
	}
	/**
	 * 
	 * @description: 从request中获取请求的IP
	 * @param request
	 *            :HttpServletRequest
	 * @return ip: String
	 */
	public String getRequestUserIpAddr() {
		HttpServletRequest request = getRequest();
		if (request == null) {
			return "unknown";
		}
		// 防止增加了代理之后获取不到用户IP
		// #reference# http://guozilovehe.iteye.com/blog/1218390
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	public void setGiantBaseService(GiantBaseService giantBaseService) {
		this.giantBaseService = giantBaseService;
		try {
			this.giantBaseService.setRequest(getRequest());
			this.giantBaseService.setSession(getSession());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String toindex() {
		return "";
	}
	

	/**
	 * 根据response返回参数
	 */
	protected void resultresponse(HttpServletResponse response,Object obj){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(obj);  
			out.flush();  
			out.close();  			
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			if(out != null){
				out.flush();
				out.close();				
			}
		}
	}
	
	public Long getHybh() {
		return hybh;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	public String getActionForward() {
		return actionForward;
	}

	public void setActionForward(String actionForward) {
		this.actionForward = actionForward;
	}

	public void setHybh(Long hybh) {
		this.hybh = hybh;
	}

	public Long getLevelid() {
		return levelid;
	}

	public void setLevelid(Long levelid) {
		this.levelid = levelid;
	}

	public int getIbh() {
		return ibh;
	}

	public void setIbh(int ibh) {
		this.ibh = ibh;
	}

	public long getLbh() {
		return lbh;
	}

	public void setLbh(long lbh) {
		this.lbh = lbh;
	}

	public Map<String, Object> getInfoMap() {
		return infoMap;
	}

	public void setInfoMap(Map<String, Object> infoMap) {
		this.infoMap = infoMap;
	}

	public Map<String, Object> getDetailM() {
		return detailM;
	}

	public void setDetailM(Map<String, Object> detailM) {
		this.detailM = detailM;
	}
	
	public List<Map<String, Object>> getListM() {
		return listM;
	}

	public void setListM(List<Map<String, Object>> listM) {
		this.listM = listM;
	}

	public GiantPager getPageList() {
		return pageList;
	}

	public void setPageList(GiantPager pageList) {
		this.pageList = pageList;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getDiquid() {
		return diquid;
	}

	public void setDiquid(String diquid) {
		this.diquid = diquid;
	}

	public Integer getGx() {
		return gx;
	}

	public void setGx(Integer gx) {
		this.gx = gx;
	}

	public String getSs() {
		return ss;
	}

	public void setSs(String ss) {
		this.ss = ss;
	}

	public String getNav() {
		return nav;
	}

	public void setNav(String nav) {
		this.nav = nav;
	}
	
}