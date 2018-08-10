package com.giant.zzidc.base.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpRequestUtils {
	private final static Logger log = Logger.getLogger(HttpRequestUtils.class);

private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
}
private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
}
/**
* post方式请求服务器(https协议)
* @param url
*            请求地址
* @param content
*            参数
* @param charset
*            编码
* @return
* @throws NoSuchAlgorithmException
* @throws KeyManagementException
* @throws IOException
 * @throws NoSuchProviderException 
*/
public static String post(String url, String content, String charset) {
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());

			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setDoOutput(true);
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			if(null!=content && !"".equals(content)){
				out.write(content.getBytes(charset));
			}
			// 刷新、关闭
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			String result = buffer.toString();
			return result;
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
}
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String urlStr, String content) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection
					.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(content);
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));

			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			String result = buffer.toString();
			// System.out.println(result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection
					.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取qq的assess_token
	 * 
	 * @param result
	 * @return
	 */
	public static String getTokenString(String result) {
		int begin = 0;
		int end = 0;
		String flag = null;
		if (result != null && !result.equals("") && result.contains("=")
				&& result.contains("&")) {
			begin = result.indexOf("=") + 1;
			end = result.indexOf("&");
			if (begin < end) {
				flag = result.substring(begin,end);
			}
		}
		return flag;
	}
	/**
	 * 获取返回字符串的json部分
	 * @param tokenString
	 * @return
	 */
	public static String getOpenJson(String tokenString){
		int begin = 0;
		int end = 0;
		String flag = null;
		if (tokenString != null && !tokenString.equals("") && tokenString.contains("{")
				&& tokenString.contains("}")) {
			begin = tokenString.indexOf("{");
			end = tokenString.indexOf("}")+1;
			if (begin < end) {
				flag = tokenString.substring(begin,end);
			}
		}
		return flag;
	}
	/**
	 * 获取sina微博的token请求
	 * @param urlStr
	 * @param content
	 * @return
	 */
	public static String sendSinaPost(String urlStr, String content) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection
					.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(content);
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));

			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			String result = buffer.toString();
			// System.out.println(result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}

	public static String sendPostXml(String urlString, String xmlText)
			throws UnrecoverableKeyException, KeyManagementException,
			KeyStoreException, NoSuchAlgorithmException, IOException {
		if (!hasInit) {
			//init();
		}
		if (httpClient == null) {
			httpClient = HttpClients.createDefault();
			requestConfig = RequestConfig.custom()
					.setSocketTimeout(socketTimeout)
					.setConnectTimeout(connectTimeout).build();
		}
		HttpPost httpPost = new HttpPost(urlString);
		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(xmlText, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);
		
		// 设置请求器的配置
		httpPost.setConfig(requestConfig);
		
		log.info("executing request" + httpPost.getRequestLine());
		
		String result = "";
		try {
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			
			result = EntityUtils.toString(entity, "UTF-8");
			
		} catch (ConnectionPoolTimeoutException e) {
			log.error("http get throw ConnectionPoolTimeoutException(wait time out)");
			
		} catch (ConnectTimeoutException e) {
			log.error("http get throw ConnectTimeoutException");
			
		} catch (SocketTimeoutException e) {
			log.error("http get throw SocketTimeoutException");
			
		} catch (Exception e) {
			log.error("http get throw Exception");
			
		} finally {
			httpPost.abort();
		}
		
		return result;
	}
//连接超时时间，默认10秒
	private static int socketTimeout = 10000;

	// 传输超时时间，默认30秒
	private static int connectTimeout = 30000;

	private static boolean hasInit;

	// 请求器的配置
	private static RequestConfig requestConfig;

	// HTTP请求器
	private static CloseableHttpClient httpClient;
}
