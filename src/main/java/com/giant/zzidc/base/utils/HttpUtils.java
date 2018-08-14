package com.giant.zzidc.base.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;






/**
 * [说明/描述]
 * @author XiaYu
 * @date 2016-9-22 下午12:14:17
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2016
 */
/**
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
public class HttpUtils {
	
		/**
	     * 
	     * [发送GET请求] <br>
	     * @param url
	     * @param param
	     * @return <br>
	     */
	    public static String sendGet(String url, String param) {
	        String result = "";
	        HttpClient client = null;
	        url = url+"?"+param;
	        HttpGet get = new HttpGet(url);
	        get.setHeader("content-type","application/json;charset=UTF-8");
	        try {
	        	if(url.startsWith("https")){
	        		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
	        			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	        				return true;
	        			}
	        		}).build();
	        		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
	        		client= HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        	}else{
	        		client = new DefaultHttpClient();
	        	}
	        	HttpResponse response = client.execute(get);;
	    		if (response == null){
	    			result="408";
	    			return result;
	    		}
	    		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	    			result = EntityUtils.toString(response.getEntity(),"utf-8");
	    		}else{
	    			result=response.getStatusLine().getStatusCode()+"";
	    		}
	        } catch (Exception e) {
	        	result="503";
	            return result;
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	        	get.releaseConnection();
	        }
	        return result;
	    }
	    /**
	     * 
	     * [发送PUT请求] <br>
	     * @param url
	     * @param param
	     * @return <br>
	     */
		public static String sendPut(String url, String param) {
	    		String result = "";
	            HttpClient client = null;
		        HttpPut put = new HttpPut(url);
		        put.setHeader("content-type","application/json;charset=UTF-8");
		        try {
		        	if(url.startsWith("https")){
		        		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
		        			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		        				return true;
		        			}
		        		}).build();
		        		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		        		client= HttpClients.custom().setSSLSocketFactory(sslsf).build();
		        	}else{
		        		client = new DefaultHttpClient();
		        	}
		        	HttpEntity entity = new StringEntity(param,"UTF-8");
		        	put.setEntity(entity); 
		        	HttpResponse response = client.execute(put);;
		    		if (response == null){
		    			result="408";
		    			return result;
		    		}
		    		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
		    			result = EntityUtils.toString(response.getEntity(),"utf-8");
		    			if(result.length()==0){
		    				result=response.getStatusLine().getStatusCode()+"";
		    			}
		    		}else{
		    			result=response.getStatusLine().getStatusCode()+"";
		    		}	
		        } catch (Exception e) {
		        	result="503";
		            return result;
		        }
		        //使用finally块来关闭输出流、输入流
		        finally{
		        	put.releaseConnection();
		        }
		        return result;
	    }
		/**
		 * 
		 * [发送DELETE请求] <br>
		 * @param url
		 * @param param
		 * @return <br>
		 */
	    public static String sendDelte(String url, String param) {
	        String result = "";
	        HttpClient client = null;
	        HttpDelete delete = new HttpDelete(url);
	        delete.setHeader("content-type","application/json;charset=UTF-8");
	        try {
	        	if(url.startsWith("https")){
	        		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
	        			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	        				return true;
	        			}
	        		}).build();
	        		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
	        		client= HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        	}else{
	        		client = new DefaultHttpClient();
	        	}
	        	HttpEntity entity = new StringEntity(param,"UTF-8");

	        	((HttpResponse) delete).setEntity(entity); 

	        	HttpResponse response = client.execute(delete);;
	        	if (response == null){
	    			result="408";
	    			return result;
	    		}
	    		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	    			result = EntityUtils.toString(response.getEntity(),"utf-8");
	    		}else{
	    			result=response.getStatusLine().getStatusCode()+"";
	    		}	
	        } catch (Exception e) {
	        	result="503";
	            return result;
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	        	delete.releaseConnection();
	        }
	        return result;
	    }

	    /**
	     * [发送POST请求] <br>
	     * @param url
	     * @param param
	     * @return
	     */
	    public static String sendPost(String url, String param) {
	        String result = "";
	        HttpClient client = null;
	        HttpPost post = new HttpPost(url);
	        post.setHeader("content-type","application/json; charset=UTF-8");
	        try {
	        	if(url.startsWith("https")){
	        		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
	        			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	        				return true;
	        			}
	        		}).build();
	        		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
	        		client= HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        	}else{
	        		client = new DefaultHttpClient();
	        	}
	        	HttpEntity entity = new StringEntity(param,"UTF-8");
	        	post.setEntity(entity); 

	        	HttpResponse response = client.execute(post);
	        	if (response == null){
	    			result="408";
	    			return result;
	    		}
	    		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	    			result = EntityUtils.toString(response.getEntity(),"UTF-8");
	    		}else if(response.getStatusLine().getStatusCode()==HttpStatus.SC_CREATED){
	    			result = EntityUtils.toString(response.getEntity(),"UTF-8");
	    			if(result.length()==0){
	    				result=response.getStatusLine().getStatusCode()+"";
	    			}
	    		}else{
	    			result=response.getStatusLine().getStatusCode()+"";
	    		}	
	        } catch (Exception e) {
	        	result="503";
	            return result;
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	        	post.releaseConnection();
	        }
	        return result;
	    } 
	    
	    /**
	     * 
	     * @param param
	     * @return
	     */
	    public static String weiXinSendPost(String param) {
//	    	String url="http://192.168.66.162:8080/restful_mczzidc_com/api/weixin/template/messageSend";
	    	String url="http://mcapi.zzidc.com:60023/api/weixin/template/messageSend";
	        String result = "";
	        HttpClient client = null;
	        HttpPost post = new HttpPost(url);
	        post.setHeader("content-type","application/json; charset=UTF-8");
	        try {
	        	if(url.startsWith("https")){
	        		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
	        			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	        				return true;
	        			}
	        		}).build();
	        		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
	        		client= HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        	}else{
	        		client = new DefaultHttpClient();
	        	}
	        	HttpEntity entity = new StringEntity(param,"UTF-8");
	        	post.setEntity(entity); 

	        	HttpResponse response = client.execute(post);
	        	if (response == null){
	    			result="408";
	    			return result;
	    		}
	    		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	    			result = EntityUtils.toString(response.getEntity(),"UTF-8");
	    		}else if(response.getStatusLine().getStatusCode()==HttpStatus.SC_CREATED){
	    			result = EntityUtils.toString(response.getEntity(),"UTF-8");
	    			if(result.length()==0){
	    				result=response.getStatusLine().getStatusCode()+"";
	    			}
	    		}else{
	    			result=response.getStatusLine().getStatusCode()+"";
	    		}	
	        } catch (Exception e) {
	        	result="503";
	            return result;
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	        	post.releaseConnection();
	        }
	        return result;
	    } 
}
