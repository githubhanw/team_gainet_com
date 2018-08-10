package com.giant.zzidc.base.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.giant.zzidc.base.dao.GiantBaseDao;
import com.giant.zzidc.base.filter.WebUtil;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.log.PMLog;
import com.zzidc.log.PMLogItem;
import com.zzidc.team.entity.ActionHistory;
import com.zzidc.team.entity.ActionLog;
import com.zzidc.team.entity.Task;

import net.sf.json.JSONObject;

/*******************************************************************************
 * [Service层基础类/主要作用是为上层(Control层)或其它层提供服务及提供公用的方法供其子类调用]
 */
@Service("baseService")
public class GiantBaseService {
	@Autowired
	public GiantBaseDao dao;
	
	protected Logger logger = Logger.getLogger(super.getClass());

	private HttpServletRequest request = null;
	private HttpSession session = null;
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected java.text.DecimalFormat dfxiao = new java.text.DecimalFormat("#.##");
	protected java.text.DecimalFormat dfzheng = new java.text.DecimalFormat("###");
	// 记录正在处理业务的宿主机key宿主机编号,
	protected static final Map<Integer, Map<String, Object>> sjzbhHashMap = new Hashtable<Integer, Map<String, Object>>();

	/**
	 * 获取登录名
	 * @return
	 */
	public String getLoginName() {
		if(getSession() == null || getSession().getAttribute("loginName") == null) {
			return "";
		}
		return String.valueOf(getSession().getAttribute("loginName"));
	}

	/**
	 * 获取登录姓名
	 * @return
	 */
	public String getMemberName() {
		if(getSession() == null || getSession().getAttribute("memberName") == null) {
			return "";
		}
		return String.valueOf(getSession().getAttribute("memberName"));
	}

	/**
	 * 获取登录id
	 * @return
	 */
	public Integer getMemberId() {
		if(getSession() == null || getSession().getAttribute("memberId") == null) {
			return 0;
		}
		return Integer.parseInt(String.valueOf(getSession().getAttribute("memberId")));
	}

	/**
	 * [描述信息]获取微信开发者账号信息
	 * @author HeFuHua
	 * @date 2014-5-4 上午10:32:49
	 * @return
	 */
	public static Map<String,Object> weiXinConfig=new HashMap<String, Object>();
	public Map<String,Object> getWeiXinConfig(){
		if(weiXinConfig.size()<=0){
		try {
			String querySql = "SELECT sysconfigkey,sysconfigvalue FROM sysconfig";
			List<Map<String, Object>> listMap = dao.getListMapBySql(
					querySql, null);
			if (listMap == null || listMap.size() == 0)
				return null;
			Map<String, Object> okMap = new HashMap<String, Object>();
			for (Map<String, Object> itemMap : listMap) {
				try {
					okMap.put(itemMap.get("sysconfigkey").toString(), itemMap
							.get("sysconfigvalue"));
				} catch (Exception exc) {
				}
			}
			try {
				//weiXinConfig.put("appid",okMap.get("weixin_open_appid").toString());
				weiXinConfig.put("appid","wx6bd8db2c5c9a1d3b");
			} catch (Exception exc) {
			}
			try {
				//weiXinConfig.put("sercret",okMap.get("weixin_open_sercret").toString());
				weiXinConfig.put("sercret","76df5f1e12eacfee4b08352749576f15");
			} catch (Exception exc) {
			}
			try {
				//weiXinConfig.put("template_id",okMap.get("weixin_msgsend_muban").toString());
				weiXinConfig.put("template_id","MIgq4jGPPeZ4EHwWcJDt2hV29N0Xyl8ujn0e4hvn5vk");
			} catch (Exception exc) {
			}
		} catch (Exception excc) {
		}
		return weiXinConfig;
	}else{
			return weiXinConfig;
		}
	}

	/**
	 * 获取客户端IP
	 * 
	 * @author LiBaozhen
	 * @date 2014-2-16 上午11:08:15
	 * @param request
	 * @return
	 */
	public String getClientIP4(HttpServletRequest request) {
		return GiantUtils.getClientIP4(request);
	}

	/**
	 * 保存或更新 或者 删除实体
	 * 
	 * @param saveUpdateObject
	 *            单个对象 或 List&lt;Object&gt;
	 * @param deleteObject
	 *            单个对象 或 List&lt;Object&gt;
	 * @return true:成功 false:失败
	 */
	public boolean saveUpdateOrDelete(Object saveUpdateObject, Object deleteObject) {
		if (dao == null) {
			return false;
		}
		try {
			return dao.saveUpdateOrDelete(saveUpdateObject, deleteObject);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 保存或更新 或者 删除日志实体
	 * 
	 * @param saveUpdateObject
	 *            单个对象 或 List&lt;Object&gt;
	 * @param deleteObject
	 *            单个对象 或 List&lt;Object&gt;
	 * @return true:成功 false:失败
	 */
	public boolean saveUpdateOrDeleteLog(Object saveUpdateObject, Object deleteObject) {
		if (dao == null) {
			return false;
		}
		try {
			return dao.saveUpdateOrDeleteLog(saveUpdateObject, deleteObject);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 保存或更新 或者 删除实体
	 * 
	 * @param saveObject
	 *            单个对象
	 * @return
	 */
	public boolean saveOne(Object saveObject) {
		if (dao != null) {
			return dao.saveOne(saveObject);
		} else {
			return false;
		}
	}
	
	/**
	 * 保存或更新 或者 删除日志实体
	 * 
	 * @param saveObject
	 *            单个对象
	 * @return
	 */
	public boolean saveLog(Object saveObject) {
		if (dao != null) {
			return dao.saveLog(saveObject);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * [保存操作日志] <br>
	 * 
	 * @author zhangyubing <br>
	 * @date 2018-7-26 09:01:09 <br>
	 * @param al
	 *            操作日志对象
	 */
	public void saveLog(ActionLog al) {
		try {
//			al.setActor("zhangyubing");
//			al.setName("张玉兵");
//			al.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dao.saveUpdateOrDeleteLog(al, null);
		} catch (Exception ex) {
			logger.error("==记录日志异常==", ex);
		}
	}

	/**
	 * 根据SQL和条件查询数据<br>
	 * 结果封装为List&lt;Map&lt;String, Object&gt;&gt;
	 * 
	 * @param querySql
	 *            SQL语句
	 * @param conditionMap
	 *            条件
	 * @return
	 */
	public List<Map<String, Object>> getMapListBySQL(String querySql, Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		try {
			return dao.getMapListBySQL(querySql, conditionMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据HQL和条件查询数据
	 * 
	 * @param querySql
	 *            SQL语句
	 * @param conditionMap
	 *            条件
	 * @return
	 */
	public List<Map<String, Object>> getMapListByHQL(String queryHql,
			Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		return dao.getMapListByHQL(queryHql, conditionMap);
	}

	/**
	 * 根据主键获取唯一实体对象
	 * 
	 * @author 李保振
	 * 
	 * @param entity
	 *            实体[需要实例化即：new Entity()]
	 * @param primaryKey
	 *            主键(类型一定要一致)
	 * 
	 * @return Object 实体对象
	 */
	public Object getEntityByPrimaryKey(Object entity, Object primaryKey) {
		if (dao == null) {
			return null;
		}
		try {
			return dao.getEntityByPrimaryKey(entity, primaryKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据条件采用HQL语句获取实体
	 * 
	 * @author 李保振
	 * 
	 * @param entityName
	 *            实体的类名(String类型)
	 * @param conditionMap
	 *            条件
	 * 
	 * @return Object 实体对象
	 */
	public Object getEntityByHQL(String entityName, Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		try {
			return dao.getEntityByHQL(entityName, conditionMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 采用HQL语句获取相应的实体列表
	 * 
	 * @author 李保振
	 * 
	 * @param entityName
	 *            实体的类名
	 * @param conditionMap
	 *            条件
	 * 
	 * @return List<Object>实体对象列表
	 */
	public List<Object> getEntityListByHQL(String entityName,
			Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		return dao.getEntityListByHQL(entityName, conditionMap);
	}

	/**
	 * 根据条件以SQL方式查询对象列表
	 * 
	 * @author 李保振
	 * 
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * 
	 * @return List<Object>实体对象列表
	 */
	public List<Object> getObjectListBySQL(String querySql,
			Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		return dao.getObjectListBySQL(querySql, conditionMap);
	}

	/**
	 * 根据条件查询出一页的信息
	 * 
	 * @author 李保振
	 * 
	 * @param querySql
	 *            查询语句
	 * @param currentPageIndex
	 *            当前是第几页
	 * @param showCounts
	 *            一页展示几条数据
	 * @param conditionMap
	 *            查询的条件
	 * 
	 * @return GiantPage 分页实体对象
	 */
	public GiantPager getPage(String querySql, int currentPageIndex,
			int showCounts, Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		return dao.getPage(querySql, currentPageIndex, showCounts,
				conditionMap);
	}

	/**
	 * 新分页:同百度分页效果<i>首页 上一页 1 2 3 4 5 下一页<i><br>
	 * writer: XiaXiaobing <br>
	 * rq: 2014-3-24下午2:59:35<br>
	 * 
	 * @param querySql
	 * @param currentPageIndex
	 * @param showCounts
	 * @param conditionMap
	 * @param counts
	 * @return
	 */
	public GiantPager getPageNew(String querySql, int currentPageIndex,
			int showCounts, Map<String, Object> conditionMap, int counts) {
		if (dao == null) {
			return null;
		}
		return dao.getPageNew(querySql, currentPageIndex, showCounts,
				conditionMap, counts);
	}

	/**
	 * 获取记录总个数
	 * 
	 * @author 李保振
	 * 
	 * @param queryCountSql
	 *            sql语句
	 * @param conditionsMap
	 *            条件
	 * @return Integer
	 */
	public Integer getGiantCounts(String queryCountSql,
			Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		return dao.getGiantCounts(queryCountSql, conditionMap);
	}

	/**
	 * 根据语句获取有且仅有的一个值
	 * 
	 * @author 李保振
	 * 
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * @return 要获取的实体
	 */
	public Object getSingleDataBySql(String querySql,
			Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		return dao.getSingleDataBySql(querySql, conditionMap);
	}


	/**
	 * 利用SQL语句获取实体
	 * 
	 * @author 李保振
	 * 
	 * @param querySql
	 *            SQL语句
	 * @param conditionMap
	 *            条件
	 * @param objItem
	 *            返回指定实体（需要实例化即new Object()）
	 * 
	 * @return List&lt;Object&gt;实体对象列表
	 */
	public List<Object> getEntityListBySQL(String querySql, Map<String, Object> conditionMap, Object objItem) {
		if (dao == null) {
			return null;
		}
		return dao.getEntityListBySQL(querySql, conditionMap, objItem);
	}

	/**
	 * 执行update语句
	 * 
	 * @author Prince
	 * @param querySql
	 *            查询语句
	 * @param conditionMap
	 *            条件
	 * @return 结果
	 */
	public boolean runUpdateSql(String querySql,
			Map<String, Object> conditionMap) {
		if (dao == null) {
			return false;
		}
		return dao.runUpdateSql(querySql, conditionMap);
	}

	public List<Object> getListBySql(String querySql,
			Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		return dao.getListBySql(querySql, conditionMap);
	}

	public List<Object[]> getArrayBySql(String querySql,
			Map<String, Object> conditionMap) {
		if (dao == null) {
			return null;
		}
		return dao.getArrayBySql(querySql, conditionMap);
	}

	/**
	 * 排序的设置，私有方法
	 * 
	 * @param sourcePage
	 * @return 要排序的字符串
	 */
	public String orderByMethod(GiantPager sourcePage) {
		if (sourcePage == null)
			return "";
		if (sourcePage.getOrderColumn() == null
				|| sourcePage.getOrderColumn().trim().equals("")) {
			return "";
		} else {
			if (sourcePage.get("orderByValue").toUpperCase().equals("ASC"))
				return " ORDER BY " + sourcePage.getOrderColumn().trim()
						+ " ASC";
			else
				return " ORDER BY " + sourcePage.getOrderColumn().trim()
						+ " DESC";
		}
	}


	/**
	 * 将obj里面Value的值为Null的转换成""
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

	// ---------------------------------------------------------------------------------------------

	/**
	 * 设置HttpServletRequest的值
	 * 
	 * @param request
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取HttpServletRequest的值
	 * 
	 * @param request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 设置HttpSession的值
	 * 
	 * @param request
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * 获取HttpSession的值
	 * 
	 * @param request
	 */
	public HttpSession getSession() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		session = attrs.getRequest().getSession();
		return session;
	}

	@SuppressWarnings("unchecked")
	public Workbook generationExcel(String sql, String[] title,
			Workbook workbook, String sheetName) throws Exception {
		try {
			/* 创建出一个工作区 */
			Sheet sheet = workbook.createSheet(sheetName);
			/* 根据传入的标题长度生成标题 */
			Row itemRow1 = sheet.createRow(0);
			for (int j = 0; j < title.length; j++) {
				Cell itemCell = itemRow1.createCell(j);
				itemCell.setCellValue(title[j]);
			}
			/* 执行SQL查询要导出的数据 */
			List<Object[]> list = (List<Object[]>) dao.codeSql(sql);
			for (int i = 0; i < list.size(); i++) {
				Object[] cell = list.get(i);
				Row itemRow = sheet.createRow(i + 1);
				for (int j = 0; j < cell.length; j++) {
					if (cell[j] != null) {
						Cell itemCell = itemRow.createCell(j);
						itemCell.setCellValue(String.valueOf(cell[j]));
					} else {
						Cell itemCell = itemRow.createCell(j);
						itemCell.setCellValue("");
					}
				}
			}
			return workbook;
		} catch (Exception e) {
			throw new Exception("生成Excel发生异常:" + e.getMessage());
		}
	}

	/**
	 * 
	 * [获取请求ip及端口号] <br>
	 * @author ZhangBinbin <br>
	 * @date 2017-10-13 下午9:41:52 <br>
	 * @param request
	 * @return <br>
	 */
	public Map<String,Object> getRequestIp(HttpServletRequest request) {
		logger.info("开始获取客户端ip~~~~~~~~~~~");
		Enumeration<String> result=request.getHeaderNames();
		while(result.hasMoreElements()){
			String next=result.nextElement();
			logger.info(next+":"+request.getHeader(next));
		}
		String manyip="";
		try {
			
			InetAddress address = InetAddress.getLocalHost();
			String hostAddress = address.getHostAddress();
			manyip+="serverIps:"+hostAddress;
			logger.info("本地服务器Ip："+hostAddress);
			InetAddress address1 = InetAddress.getByName("ac.zzidc.com");
			String hostAddress1 = address1.getHostAddress();
			InetAddress[] addresses = InetAddress.getAllByName("ac.zzidc.com");
			logger.info("ac服务器Ip："+hostAddress1);
			for(InetAddress addr:addresses){ 
				logger.info("循环获取所有可能的Ip："+addr.getHostAddress());
				manyip+=","+addr.getHostAddress();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Map<String,Object> map = new HashMap<String, Object>();
		String ip = request.getHeader("x-forwarded-for");
		logger.info("获取x-forwarded-for："+ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		logger.info("获取Proxy-Client-IP："+request.getHeader("Proxy-Client-IP"));
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		logger.info("获取WL-Proxy-Client-IP："+request.getHeader("WL-Proxy-Client-IP"));
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		logger.info("获取remoteAddr："+request.getRemoteAddr());
		if (ip == null) {
			return null;
		} else if (ip.contains(",")) {
			map.put("ips", ip);
			logger.info("获取客户端ip结束，返回ips："+ip+"，返回ip："+ip.substring(0, ip.indexOf(',')));
			map.put("ip", ip.substring(0, ip.indexOf(',')));
			map.put("code", 0);
			map.put("serverIP", manyip);
			return map;
		} else {
			map.put("code", 1);
			map.put("ip", ip);
			map.put("ips", ip);
			map.put("serverIP", manyip);
			logger.info("获取客户端ip结束，返回ips："+ip+"，返回ip："+ip);
			return map;
		}
	}
	public  int getRemotePort( HttpServletRequest request){
		 int port = request.getRemotePort();
		return port;
		 
	 } 
	
	/**
	 * 防止SQL注入及页面信息影响
	 * @param filterStr
	 *            需要过滤的字符串
	 */
	public String filterSQLInjection(String filterStr) {
		return GiantUtils.filterStr(filterStr);
	}
	
    /**
     * [保存cookie（idcfy）] <br>
     */
    public void newsaveCk(ServletRequest request, ServletResponse response,int idcfy,String ckname,int ckage){
    	try {
			HttpServletResponse _rResponse= (HttpServletResponse)response;//响应
			WebUtil.addCookie(_rResponse, ckname, String.valueOf(idcfy), ckage,2);//设置时间3天
    	}catch (Exception e) {
			// handle exception
		}
    }
    /**
     * [保存cookie（pand）] <br>
     */
    public void saveCk(ServletRequest request, ServletResponse response,int pand){
    	try {
			HttpServletResponse _rResponse= (HttpServletResponse)response;//响应
			WebUtil.addCookie(_rResponse, "goodLuckCookie", String.valueOf(pand), 3*24*60*60,2);//设置时间3天
    	}catch (Exception e) {
		}
    }
    
	/**
	 * [描述信息] <br>
	 * 
	 * @author LiShan <br>
	 * @date 2017-4-24 下午4:12:24 <br>
	 * @param baseurl
	 *            域名
	 * @param type
	 *            podeclaration/put等
	 * @param urlString
	 *            方法名【和baseurl组装】
	 * @param object
	 *            参数
	 * @return <br>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> OperationsRestful(String baseurl, String type,
			String urlString, JSONObject object) {
		Map<String, Object> jsonmap = new HashMap<String, Object>();
		try {
			urlString = baseurl + urlString;
			// urlString="http://web2.zzidc.com:8117"+urlString;
			// 需要请求的restful地址
			URL url = new URL(urlString);
			// 打开restful链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 提交模式
			conn.setRequestMethod(type);
			conn.setConnectTimeout(200000);// 连接超时 单位毫秒
			conn.setReadTimeout(200000);// 读取超时 单位毫秒
			conn.setDoOutput(true);// 是否输入参数
			conn.setDoInput(true);
			// 设置访问提交模式，表单提交
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(object.toString().getBytes());
			// outputStream.flush();
			if (conn.getResponseCode() == 200) {// 成功
				InputStream in = conn.getInputStream();
				BufferedReader bReader = new BufferedReader(
						new InputStreamReader(in, "UTF-8"));
				String str = "";
				StringBuffer sBuffer = new StringBuffer();
				while ((str = bReader.readLine()) != null) {
					sBuffer.append(str);
				}
				String mes = sBuffer.toString();
				if (mes != null && !"".equals(mes)) {
					jsonmap = JSONObject.fromObject(mes);
				}
			}
			conn.disconnect();
		} catch (Exception e) {
			// handle exception
			logger.info("调用接口restful失败，OperationsRestful方法异常");
			e.printStackTrace();
		}
		return jsonmap;
	}

	/**
	 * 过滤所有传过来的参数
	 * 
	 * @param sourcePage
	 * @return
	 */
	protected GiantPager filterStr(GiantPager sourcePage) {
		Map<String, String> conditionMap = sourcePage.getQueryCondition();
		for (String key : conditionMap.keySet()) {
			conditionMap.put(key, GiantUtils.filterStr(conditionMap.get(key)));
		}
		sourcePage.setQueryCondition(conditionMap);
		return sourcePage;
	}


	/**
	 * 获取所有人员列表
	 */
	public List<Map<String, Object>> getAllMember(){
		String sql = "select id, name, number from member where status=0";
		List<Map<String, Object>> list = dao.getMapListBySQL(sql, null);
		if(list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		return list;
	}
	
	/**
	 * [更新任务的父任务状态] <br>
	 * <pre>
	 * 1. 所有子任务为已关闭，父任务为已关闭；
	 * 2. 所有子任务为待接收，父任务为待接收；
	 * 3. 所有子任务为已完成，父任务为已完成；
	 * 4. 只要有一个子任务没有完成，父任务为进行中；
	 * </pre> 
	 * @author likai <br>
	 * @date 2018年8月1日 下午9:29:58 <br>
	 * @param taskId 任务ID<br>
	 */
	public void updateParentTaskState(int taskId) {
		try {
			// 1. 获取taskId对应的任务
			Task task = (Task) dao.getEntityByPrimaryKey(new Task(), taskId);
			if(task == null) {
				return;
			}
			// 2. 判断task是否为子任务，如果不是子任务直接返回
			int parentId = task.getParentId();
			if(parentId == 0) {
				return;
			}
			// 3. 获取该子任务的所有兄弟任务
			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("parentId", parentId);
			List<Object> tasks = dao.getEntityListByHQL("Task", conditionMap);
			if(tasks == null || tasks.size() == 0) {
				return;
			}
			// 4. 所有的任务是否都为已关闭状态
			if(checkState(tasks, 7)) {
				// 更新主任务的状态为已关闭状态
				Task parentTask = (Task) dao.getEntityByPrimaryKey(new Task(), parentId);
				if(parentTask == null) {
					return ;
				}
				parentTask.setState((short) 7);
				dao.saveUpdateOrDelete(parentTask, null);
				return;
			}
			// 5. 所有子任务是否都为待接收
			if(checkState(tasks, 1)) {
				return;
			}
			// 6. 所有的任务是否都为已完成状态
			Task parentTask = (Task) dao.getEntityByPrimaryKey(new Task(), parentId);
			if(checkState(tasks, 4)) {
				// 更新主任务的状态为已完成状态
				parentTask.setState((short) 4);
			} else { // 7. 只要有一个子任务没有完成，父任务为进行中
				parentTask.setState((short) 2);
			}
			dao.saveUpdateOrDelete(parentTask, null);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * [判断任务列表的状态是否都为state] <br>
	 * @author likai <br>
	 * @date 2018年8月1日 下午9:28:01 <br>
	 * @param tasks 任务列表
	 * @param state 状态
	 * @return <br>
	 */
	private boolean checkState(List<Object> tasks, int state) {
		if(tasks == null) {
			return true;
		}
		for(Object obj : tasks) {
			Task item = (Task) obj;
			if(item.getState() != state) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * [记录日志] <br>
	 * <pre>
	 * 记录日志失败只会有console输出错误信息
	 * </pre>
	 * @author likai <br>
	 * @date 2018年8月6日 下午4:09:09 <br>
	 * @param pmLog <br>
	 */
	public void log(PMLog pmLog) {
		// 如果pmLog为空直接退出
		if(pmLog == null) {
			return;
		}
		if(pmLog.getEndTime() == null) {
			pmLog.setEndTime();
		}
		// 创建 ActionLog对象
		ActionLog actionLog = new ActionLog();
		BeanUtils.copyProperties(pmLog, actionLog, new String[] {"module", "method", "items"});
		actionLog.setLoginName(this.getLoginName());
		actionLog.setMemberName(this.getMemberName());
		actionLog.setModule(pmLog.getModule().value());
		actionLog.setMethod(pmLog.getMethod().value());
		actionLog.setTimeLength(pmLog.getTimeLength());
		// 保存actionLog
		boolean b = this.saveOne(actionLog);
		// 保存actionLog失败直接结束
		if(!b ) {
			return;
		}
		// 判断是否存在history,不存在结束
		if(pmLog.getItems() == null || pmLog.getItems().size() == 0) {
			return;
		}
		// 获取actionId
		int actionId = actionLog.getId();
		// 遍历 pmLog下的items
		for(PMLogItem item : pmLog.getItems()) {
			// 创建 ActionHistory
			ActionHistory ah = new ActionHistory();
			BeanUtils.copyProperties(item, ah);
			ah.setActionId(actionId);
			// 保存ActionHistory,不考虑结果
			this.saveOne(ah);
		}
	}
	
	/**
	 * [微信提醒] <br>
	 * <pre>
	 * 记录日志失败只会有console输出错误信息
	 * </pre>
	 * @param openid 需要发送通知的微信openid
	 * @param f
	 * @param kw1
	 * @param kw2
	 * @param kw3
	 * @param rm 此次模板消息的备注信息(必须为json格式)
	 * @param resource 	验证来源
	 * @param template_id 模板ID
	 * @return
	 */
	public String sendWeChatUtil(String openid,String f,String kw1,String kw2,String kw3,String rm) {
		
//		String url="http://192.168.103.156:8080/restful_mczzidc_com/api/weixin/template/messageSend"; //李平源本地
//		/*String a2 = "{\"resource\":\"VG3OQ81wtrNGANeuq8IdwTBhgkAF53lFOIY6aCIBeo0=\",\"openid\":\"o-GQDj8vVvfH2715yROC1aqY4YM0\","
//				+ "\"template_id\":\"4IODUT4wFgczTj5m5qIjX2M1De22nsasMcgYKHhG2N0\",\"data\":{\"first\":{\"color\":\"#173177\",\"value\":\"工资表\"},"
//				+ "\"keyword1\":{\"color\":\"#173177\",\"value\":\"干啥去\"},\"keyword2\":{\"color\":\"#173177\",\"value\":\"18\"},"
//				+ "\"keyword3\":{\"color\":\"#173177\",\"value\":\"10000\"},\"remark\":{\"color\":\"#173177\",\"value\":\"阿斯顿发的撒罚多少\"}}}\n";*/
//		String openid = "o-GQDj8vVvfH2715yROC1aqY4YM0";
//		String first = "工资表";
//		String keyword1 = "干啥去";
//		String keyword2 = "18";
//		String keyword3 = "10000";
//		String remark = "阿斯顿发的撒罚多少";
//		String str=sendWeChatUtil(openid,first,keyword1,keyword2,keyword3,remark);
//		String a1 = JSONObject.fromObject(str).toString();
//		HttpUtils.sendPost(url, a1);
		
		String value="{\"resource\":\"VG3OQ81wtrNGANeuq8IdwTBhgkAF53lFOIY6aCIBeo0=\",\"openid\":\""+openid+"\","
				+ "\"template_id\":\"MIgq4jGPPeZ4EHwWcJDt2hV29N0Xyl8ujn0e4hvn5vk\",\"data\":{\"first\":{\"color\":\"#173177\",\"value\":\""+f+"\"},"
				+ "\"keyword1\":{\"color\":\"#173177\",\"value\":\""+kw1+"\"},\"keyword2\":{\"color\":\"#173177\",\"value\":\""+kw2+"\"},"
				+ "\"keyword3\":{\"color\":\"#173177\",\"value\":\""+kw3+"\"},\"remark\":{\"color\":\"#173177\",\"value\":\""+rm+"\"}}}\n";
    	Map<String,Object> p=new HashMap<String,Object>();
    	p.put("resource", "VG3OQ81wtrNGANeuq8IdwTBhgkAF53lFOIY6aCIBeo0=");//"VG3OQ81wtrNGANeuq8IdwTBhgkAF53lFOIY6aCIBeo0="
    	p.put("openid", openid);
    	p.put("template_id", "MIgq4jGPPeZ4EHwWcJDt2hV29N0Xyl8ujn0e4hvn5vk" );//"MIgq4jGPPeZ4EHwWcJDt2hV29N0Xyl8ujn0e4hvn5vk"
    	Map<String, Object> first = new HashMap<String,Object>();
    	first.put("color", "#173177");
    	first.put("value", f);
    	Map<String, Object> keyword1 = new HashMap<String,Object>();
    	keyword1.put("color", "#173177");
    	keyword1.put("value", kw1);
    	Map<String, Object> keyword2 = new HashMap<String,Object>();
    	keyword2.put("color", "#173177");
    	keyword2.put("value", kw2);
    	Map<String, Object> keyword3 = new HashMap<String,Object>();
    	keyword3.put("color", "#173177");
    	keyword3.put("value", kw3);
    	Map<String, Object> remark = new HashMap<String,Object>();
    	remark.put("color", "#173177");
    	remark.put("value", rm);
    	
    	Map<String, Object> d = new HashMap<String,Object>();	
    	d.put("first", first);
    	d.put("keyword1", keyword1);
    	d.put("keyword2", keyword2);
    	d.put("keyword3", keyword3);
    	d.put("remark", remark);
    	p.put("data", d);
    	String a = p.toString();
		return value;
	}
}
