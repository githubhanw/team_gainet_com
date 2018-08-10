package com.giant.zzidc.base.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.giant.zzidc.base.service.GiantBaseService;

import net.sf.json.JSONObject;
/**
 * Rest方式公共验证类
 * @author LiuPan
 * @title RestBaseVerify 
 * @time 2017-4-1
 */
public class RestBaseVerify extends GiantBaseService{
	/**
	 * 验证调用来源权限
	 * @param key 来源key(加密的)
	 * @return
	 */
	public boolean verifySource(String key){
		if(key == null||"".equals(key)){
			logger.info("[rest]-----验证用户调用资格,接收到的key为空");
			return false;
		}
		PlusCut pc = PlusCut.getInstance();
		String keyCut = pc.cut(key);
		String verifySql = "select state from restful_verify where verifykey = '"+keyCut+"'";
		/*Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.clear();
		paramMap.put("key", key);*/
		List<Map<String,Object>> resultList = null;
		try {
			resultList = getMapListBySQL(verifySql, null);
		} catch (Exception e) {
			logger.error("[rest]-----获取调用来源记录失败；原因："+e.getMessage());
			return false;
		}
		if(resultList == null||resultList.size() != 1){
			logger.info("[rest]-----获取调用来源数据记录为空");
			return false;
		}
		Map<String,Object> resultMap = resultList.get(0);
		if(resultMap == null){
			return false;
		}
		
		try {
			int state = Integer.parseInt(resultMap.get("state").toString());
			String msg = state == 0 ? "开启":"禁用";
			System.out.println("[rest]-----权限信息  " + msg );
			if(state != 0){
				logger.info("[rest]-----加载调用权限失败；原因：权限被禁用");
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			logger.error("[rest]-----加载调用权限失败；原因"+e.getMessage());
			return false;
		}
	}
	/**
	 * 
	 * [调用restful post请求] <br>
	 * @author LiangLu<br>
	 * @date 2017-4-25 下午5:11:59<br>
	 * @param urlString
	 * @param object
	 * @param pamaMap
	 * @return<br>
	 */
	public ResultInfo operationsRestful(String urlString,JSONObject object,Map<String, Object> pamaMap){
		ResultInfo resultInfo=new ResultInfo();	
		try{
			//urlString="http://192.168.101.56:8098/isp_kuaiyun_interface"+urlString;
			if(urlString.indexOf("http")==-1){
				urlString=PropUtils.getValue("SET_INTERFACE_HREF")+"/"+urlString;
			}
			//需要请求的restful地址 
			URL url = new URL(urlString);
			//打开restful链接  
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 提交模式 
			conn.setRequestMethod("POST");
			if(!GiantUtils.isEmpty(pamaMap)){
				if(!GiantUtils.isEmpty(pamaMap.get("type"))){
					conn.setRequestMethod(GiantUtils.stringOf(pamaMap.get("type")));
				}
			}
			conn.setConnectTimeout(200000);//连接超时 单位毫秒
			conn.setReadTimeout(200000);//读取超时 单位毫秒
			conn.setDoOutput(true);// 是否输入参数
			conn.setDoInput(true); 
			//设置访问提交模式，表单提交 
			if(!GiantUtils.isEmpty(pamaMap) && "GET".equals(GiantUtils.stringOf(pamaMap.get("type")))){
				logger.info("*******************************调用接口，urlString："+urlString);
			}else {
				conn.setRequestProperty("Content-Type", "application/json");
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(object.toString().getBytes());
				//因参数过多，此处不打印存储上传文件的参数。
				if(urlString!=null&&urlString!=""){
					if(urlString.indexOf("CloudStorage/upload")<0){
						logger.info("*******************************调用接口，传入参数：businessId"+object.toString());
					}
				}
			}
	//	        outputStream.flush();
		        if(conn.getResponseCode() == 200){//成功
		        	InputStream in = conn.getInputStream();
					BufferedReader bReader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
					String str = "";
					StringBuffer sBuffer = new StringBuffer();
					while((str=bReader.readLine())!=null){
						sBuffer.append(str);
					}
					String mes=sBuffer.toString();
					Map<String,Object> jsonmap=null;
					int code=-1;
					String info="";
					if(mes!=null && !"".equals(mes)){
						jsonmap=JSONObject.fromObject(mes);
					}
					String codevalue = GiantUtils.stringOf(jsonmap.get("code"));
					if(codevalue.matches("\\w+\\d+")){//新版返回值errorcode
						resultInfo.setErrorcode(codevalue);
					}else{
						code=GiantUtils.intOf(jsonmap.get("code"), -1);
						resultInfo.setErrorcode(GiantUtils.stringOf(jsonmap.get("errorcode")));
					}
					if(jsonmap!=null){
						Map<String, Object>extend=(Map<String, Object>) jsonmap.get("extend");
						resultInfo.setExtend(extend);
					}
					
					info=GiantUtils.stringOf(jsonmap.get("message"));
					if(mes!=null&&mes.length()<100){
						logger.info("*******************************调用接口"+mes);
					}
					resultInfo.setCode(code);
					resultInfo.setInfo(info);
					
				}else{//失败--不做任何处理
					logger.info("调用接口失败，Failes:HTTP error code:" + conn.getResponseCode());
					resultInfo.setCode(-1);
					resultInfo.setInfo("调用接口失败，请重新操作或联系您的售后经理。");
					return resultInfo;
				}
		        conn.disconnect();
				}catch (Exception e) {
					// TODO: handle exception
					logger.info("调用接口restful失败，operationsRestful方法异常" );
					resultInfo.setCode(-1);
					resultInfo.setInfo("调用接口失败，请重新操作或联系您的售后经理。");
					e.printStackTrace();
				}
				return resultInfo;
		}
}
