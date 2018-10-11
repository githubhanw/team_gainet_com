package com.giant.zzidc.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

/**
 * [文件上传工具类]
 * @author LiYatao
 * @date 2018年9月5日 下午9:02:57
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
public class FileUploadUtil {

	private static Logger logger = Logger.getLogger(FileUploadUtil.class);
	
//	private final static String UPLOADURL = "http://lanapi.storagesdk.com/";
	private final static String UPLOADURL = "http://api.storagesdk.com/";//测试使用

	// 每次调用是赋值
	private static String ACCESSKEY = null;
	private static String SECRETKEY = null;
	private static String RESOURCE = null;
	
//	private static String ACCESSKEY = "W7O2M3KY4G5TLPN2URI1";//正式
//	private static String SECRETKEY = "/rWk5zUcLyHSEobRz5ARM1zaVxqbt4ZLXVBBDHi7";//正式
//	private static String RESOURCE = "VG3OQ81wtrNjl7GkAWD9LSNETSqlzykT";//正式RESOURCE值
	
//	private final static String SECRETKEY = "3rIpwa/5HNJpWkxtfsmaGeUVNX5kqQ04hJDBu5ya";//测试
//	private final static String ACCESSKEY = "3M3JYO5HFN6RNO3Y5BGO";//测试
//	private final static String RESOURCE = "VG3OQ81wtrP6xM+q9Ak6G2DLWdw5WNqb";//测试RESOURCE值
	
	private final static String BUCKETNAME = "team-gainet-com";
	private final static String UPLOADIMAGEPATH = "images";
	private final static String UPLOADFILEPATH = "files";
	
	private final static Map<String, String> allowedMimeTypes = new HashMap<String, String>();
	static {
		allowedMimeTypes.put("image/bmp", "image/bmp");
		allowedMimeTypes.put("image/jpeg", "image/jpeg");
		allowedMimeTypes.put("image/pjpeg", "image/pjpeg");
		allowedMimeTypes.put("image/gif", "image/gif");
		allowedMimeTypes.put("image/x-png", "image/x-png");
		allowedMimeTypes.put("image/png", "image/png");
		allowedMimeTypes.put("image/jpg", "image/jpg");	
		allowedMimeTypes.put("application/octet-stream", "application/octet-stream");
	}
	
	public static void SetParam(String accesskey, String secreteky, String resource) {
		ACCESSKEY = accesskey;
		SECRETKEY = secreteky;
		RESOURCE = resource;
	}
	
	/**
	 * [上传百度编辑器中的图片，返回ImageUploadResult] <br>
	 * @author LiYatao <br>
	 * @date 2018年9月5日 下午9:12:15 <br>
	 * @param request
	 * @return <br>
	 */
	public static Object uploadImage(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("upfile");
		String url = "";
		String fileName = "";
		if (file != null && file.getSize() != 0) {
			try {
				fileName = file.getOriginalFilename();
				if (fileName != null && !"".equals(fileName)) {
					int n = fileName.indexOf(".");
					if (n != -1) {
						fileName = UUID.randomUUID().toString()
								+ fileName.substring(n);// 重新生成文件名
						System.out.println(" 文件名   " + fileName);
						url = "http://"+BUCKETNAME+".kuaiyunds.com/"+BUCKETNAME+"/"+UPLOADIMAGEPATH+"/" + fileName;
					}
				}
				MultipartFile[] files = new MultipartFile[1];
				String[] filenames = new String[1];
				files[0] = file;
				filenames[0] = fileName;
				Result result = uploadFiles(files, filenames, UPLOADIMAGEPATH);
				if (!result.isSuccess()) {
					return null;
				}
			} catch (Exception e) {
				System.out.println(" 文件名   " + fileName);
				e.printStackTrace();
				return null;

			}
		}
		// 这里获取到文件 处理上传后返回路径
		ImageUploadResult result = new ImageUploadResult();
		result.setState("SUCCESS");
		result.setUrl(url);
		result.setOriginal(fileName);
		result.setTitle(fileName);

		return result;
	}
	
	/**
	 * [上传文件,并对图片进行验证] <br>
	 * @author LiYatao <br>
	 * @date 2018年9月5日 下午9:12:36 <br>
	 * @param files
	 * @param fileNames
	 * @param dirPath
	 * @return <br>
	 */
	private static Result uploadFiles(MultipartFile[] files, String[] fileNames,
			String dirPath) {
		int size=0;
		try {
			logger.info("开始验证文件类型");
			for (int i = 0; i < files.length; i++) {
				int fileUploadMaxBytes = 1024 * 1024 * 3;// 3MB
				String mimeType = files[i].getContentType();
				boolean rightType = allowedMimeTypes.containsKey(mimeType);
				String fileName = fileNames[i];// 文件名
				String fileNamesTU = fileName.toUpperCase();
				if (!fileNamesTU.endsWith("BMP") && !fileNamesTU.endsWith("JPEG") && !fileNamesTU.endsWith("GIF") && !fileNamesTU.endsWith("PNG")&&!fileNamesTU.endsWith("JPG")) {
					return new Result(false, "操作失败,只能上传BMP、JPEG、GIF、PNG格式的附件");
				}

				if (!rightType) {
					return new Result(false, "操作失败,请点击上传图片，重新添加图片！");
				}

				int available = files[i].getInputStream().available();
				if (available > fileUploadMaxBytes) {
					return new Result(false, "操作失败,上传的附件尺寸不能超过3MB");
				}
				try {
					InputStream inputStream=files[i].getInputStream();
					logger.info("跳转上传方法");
					boolean result = uploadFileToCloud(fileName, inputStream, dirPath.substring(0,dirPath.length()));
					if(result){
						size+=1;
					}
				} catch (Exception e) {
					e.printStackTrace(); 	
				}
			}
			if(size==fileNames.length){
				return new Result(true, "附件上传成功");
			}else{
				return new Result(false, "附件上传失败");
			}
		} catch (IOException ioe) {
			logger.error("上传附件过程中发生错误", ioe);
			return new Result(false, "附件上传失败");
		} 
	}

	/**
	 * [上传文件到云存储] <br>
	 * @author LiYatao <br>
	 * @date 2018年9月5日 下午9:13:04 <br>
	 * @param fileName
	 * @param input
	 * @param folder
	 * @return <br>
	 */
	@SuppressWarnings("all")
	private static boolean uploadFileToCloud(String fileName, InputStream input,
			String folder) {
		try {
			if(GiantUtil.isEmpty(folder)){
				folder = UPLOADIMAGEPATH;
			}
//			String url = "http://lanapi.storagesdk.com/";
//			String url = "http://api.storagesdk.com/";  //测试使用
			if(BUCKETNAME != null && !"".equals(BUCKETNAME) && fileName !=null && !"".equals(fileName) && input != null){
				JSONObject json = new JSONObject();
				
				URL uurl = new URL(UPLOADURL + "restful/storageapi/file/uploadFile"); //接口路径
				logger.info("--上传文件接口的URL: " + UPLOADURL + "restful/storageapi/file/uploadFile");
				URLConnection rulConnection = uurl.openConnection();//?此处的urlConnection对象实际上是根据URL的????
				//?请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection????
				HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
				httpUrlConnection.setRequestMethod("POST");
				//?设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,?默认情况下是false;????
				httpUrlConnection.setDoOutput(true);
				httpUrlConnection.setDoInput(true);
				//?Post?请求不能使用缓存????
				//httpUrlConnection.setUseCaches(false);
				String filen = folder+"/"+fileName;
				filen = filen.trim();
				BASE64Encoder encoder = new BASE64Encoder();
				String filename = encoder.encode(filen.getBytes());//对文件名加密
				filename = filename.replaceAll("\r|\n", "");
				httpUrlConnection.setRequestProperty("resource", RESOURCE);
				httpUrlConnection.setRequestProperty("bucketName", BUCKETNAME);
				httpUrlConnection.setRequestProperty("fileName", filename);
				httpUrlConnection.setRequestProperty("length", input.available()+"");//length为文件长度，即流的长度，可通过InputStream.available()来获取
				httpUrlConnection.setRequestProperty("accessKey", ACCESSKEY);
				httpUrlConnection.setRequestProperty("secretKey", SECRETKEY);
				httpUrlConnection.setRequestProperty("Content-type", "application/json");
				httpUrlConnection.connect();
				OutputStream out = httpUrlConnection.getOutputStream();// 向对象输出流写出数据，这些数据将存到内存缓冲区中
				byte[] buffers=new byte[1024];
				int len = -1;  
				while ((len = input.read(buffers)) != -1) {
				    out.write(buffers, 0, len);
				}
				input.close();
				out.flush();//刷新对象输出流，将任何字节都写入潜在的流中
				out.close();// 关闭流对象,此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中
				
				 //获得响应状态
				int resultCode = httpUrlConnection.getResponseCode();
				logger.info("code:"+resultCode+",info:"+httpUrlConnection.getResponseMessage());
				String result = "";
				if(HttpURLConnection.HTTP_OK == resultCode){
					StringBuffer sb = new StringBuffer();
					String readLine = new String();
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(),"UTF-8"));
					while((readLine = responseReader.readLine()) != null){
						sb.append(readLine).append("\n");
					}
					responseReader.close();
					System.out.println(sb.toString());
					result = sb.toString();
					logger.info("--上传文件doPost的result: " + result);
					JSONObject jsonObject = JSONObject.fromObject(result);
					Map<String, Object> jsonMap = (Map) JSONObject.fromObject(jsonObject);
					int code = Integer.valueOf(jsonMap.get("code").toString());
					String message = jsonMap.get("message").toString();
					if(code == 0){
						return true;
					}else{
						logger.error("--上传文件失败，云存储接口返回信息为：code:"+ code + "message:" + message);
					}
				}else{
					logger.error("上传失败，连接云存储接口失败：code:"+resultCode+",info:"+httpUrlConnection.getResponseMessage());
				}
			}else{
				logger.error("--上传文件出错:文件夹名为空");
			}
		} catch (Exception e) {
			logger.error("--上传文件出错:" + e);
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * [上传input文件到云存储，返回文件路径] <br>
	 * @author LiYatao <br>
	 * @date 2018年9月5日 下午9:13:50 <br>
	 * @param file
	 * @return <br>
	 */
	public static Object uploadFile(MultipartFile[] file) {
		if(file!=null && file.length>0){
			String fileName = file[0].getOriginalFilename();
			MultipartFile f =file[0];
			String hz = fileName.substring(fileName.lastIndexOf("."));
			fileName = System.currentTimeMillis() + hz;
			try {
				if (!fileName.endsWith("BMP") && !fileName.endsWith("JPEG") && !fileName.endsWith("GIF") && !fileName.endsWith("PNG")&&!fileName.endsWith("JPG")) {
					if(uploadFileToCloud(fileName,f.getInputStream(),UPLOADFILEPATH)){
						return "http://"+BUCKETNAME+".kuaiyunds.com/"+BUCKETNAME+"/"+UPLOADFILEPATH+"/" + fileName;
					}else{
						return null;
					}
				}else{
					if(uploadFileToCloud(fileName,f.getInputStream(),UPLOADIMAGEPATH)){
						return "http://"+BUCKETNAME+".kuaiyunds.com/"+BUCKETNAME+"/"+UPLOADIMAGEPATH+"/" + fileName;
					}else{
						return null;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	/**
	 * [上传input文件到云存储，返回文件路径] <br>
	 * @author chenmenghao <br>
	 * @date 2018年9月5日 下午9:13:50 <br>
	 * @param file
	 * @return <br>
	 */
	public static Object uploadFiles(MultipartFile file) {
			String fileName = file.getOriginalFilename();
			MultipartFile f =file;
			String hz = fileName.substring(fileName.lastIndexOf("."));
			fileName = System.currentTimeMillis() + hz;
			try {
				if (!fileName.endsWith("BMP") && !fileName.endsWith("JPEG") && !fileName.endsWith("GIF") && !fileName.endsWith("PNG")&&!fileName.endsWith("JPG")) {
					if(uploadFileToCloud(fileName,f.getInputStream(),UPLOADFILEPATH)){
						return "http://"+BUCKETNAME+".kuaiyunds.com/"+BUCKETNAME+"/"+UPLOADFILEPATH+"/" + fileName;
					}else{
						return null;
					}
				}else{
					if(uploadFileToCloud(fileName,f.getInputStream(),UPLOADIMAGEPATH)){
						return "http://"+BUCKETNAME+".kuaiyunds.com/"+BUCKETNAME+"/"+UPLOADIMAGEPATH+"/" + fileName;
					}else{
						return null;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
}