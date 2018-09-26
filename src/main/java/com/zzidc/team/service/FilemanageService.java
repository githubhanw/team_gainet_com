package com.zzidc.team.service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.FileUploadUtil;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.FileManage;

import net.sf.json.JSONObject;
/**
 * 文档
 * @author chenmenghao
 *
 */
@Service("filemanageService")
public class FilemanageService extends GiantBaseService{
	
	/**
	 * 文档列表
	 */
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "select * from file_manage where 1=1 ";
		String countSql = "SELECT COUNT(0) from file_manage where 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			//数据：{type=10, bugfen=1, createtime=, endtime=, member=}
			String accessControl="";//里程碑状态
			if (!StringUtils.isEmpty(accessControl = conditionPage.getQueryCondition().get("accessControl"))) {
				sql += "AND access_control=:accessControl ";
				countSql += "AND access_control=:accessControl ";
				conditionMap.put("accessControl", accessControl);
			}
			
			String startTime="";//开始时间
			if (!StringUtils.isEmpty(startTime = conditionPage.getQueryCondition().get("startTime"))) {
				sql += "AND create_id=:startTime ";
				countSql += "AND create_id=:startTime ";
				conditionMap.put("startTime", startTime);
			}
			
			String authorId="";//创建者id
			if (!StringUtils.isEmpty(authorId = conditionPage.getQueryCondition().get("authorId"))) {
				sql += "AND create_id=:authorId ";
				countSql += "AND create_id=:authorId ";
				conditionMap.put("authorId", authorId);
			}
		}
		// 字段倒叙或升序排列
		if (conditionPage.getOrderColumn() != null && !"".equals(conditionPage.getOrderColumn())) {
				sql += " ORDER BY " + conditionPage.getOrderColumn()
						+ (conditionPage.get("orderByValue") == null ? " DESC" : " " + conditionPage.get("orderByValue"));
		}
		GiantPager resultPage = super.dao.getPage(sql, conditionPage.getCurrentPage(), conditionPage.getPageSize(), conditionMap);
		resultPage.setQueryCondition(GiantUtils.filterSQLMap(conditionPage.getQueryCondition()));
		resultPage.setTotalCounts(super.dao.getGiantCounts(countSql, conditionMap));
		return resultPage;
	}
	//上传文件返回文件名字和格式和存储路径
	public JSONObject uploadfiles(MultipartFile[] file){
		JSONObject json =new JSONObject();
		Object url=FileUploadUtil.uploadFile(file);
		
		if(url!=null){
			String fileName = file[0].getOriginalFilename();
			String gs=file[0].getOriginalFilename().substring(fileName.lastIndexOf(".")).substring(1);       
			json.put("url", url);
			json.put("fileName",fileName);
			json.put("gs", gs);
			return json;
		}else{
			
			return null;
		}
	} 
	
	/**
	 * 创建文档
	 */
	public boolean add(Map<String, String> mvm,int id,String name,String gs,String url,String fileRealname) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		FileManage fm =new FileManage();
		fm.setFileClassification(mvm.get("file_type"));
		if(mvm.get("file_type").equals("0")){
			fm.setGlId(Integer.parseInt(mvm.get("xm")));
		}else{
			fm.setGlId(Integer.parseInt(mvm.get("xq")));
		}
		fm.setFileName(mvm.get("file_name"));
		fm.setFileText(mvm.get("file_text"));
		fm.setCreateId(id+"");
		fm.setAddName(name);
		fm.setCreateTime(createTime);
		fm.setEditTime(createTime);
		fm.setFileFormat(gs);//
		fm.setFileUrl(url);//
		fm.setFileRealname(fileRealname);
		fm.setFileRemarks(mvm.get("file_remarks"));
		fm.setAccessControl("1");
		boolean b =  super.dao.saveUpdateOrDelete(fm, null);
		return b;
	}
	/**
	 * 创建项目创建文档
	 */
	public boolean addxm(Map<String, String> mvm,int id,String name,String gs,String url,String fileRealname,int pid,String i) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		FileManage fm =new FileManage();
		fm.setGlId(pid);
		fm.setFileClassification("0");
		if(i.equals("1")){
			fm.setFileName("可行性研究报告（"+mvm.get("project_name")+"）");
		}else if(i.equals("2")){
			fm.setFileName("项目立项书（"+mvm.get("project_name")+"）");
		}else if(i.equals("3")){
			fm.setFileName("工作量评估报告（"+mvm.get("project_name")+"）");
		}else{
			fm.setFileName("项目预算费用表（"+mvm.get("project_name")+"）");
		}
		
		fm.setFileText("");
		fm.setCreateId(id+"");
		fm.setAddName(name);
		fm.setCreateTime(createTime);
		fm.setEditTime(createTime);
		fm.setFileFormat(gs);
		fm.setFileUrl(url);
		fm.setFileRealname(fileRealname);
		fm.setFileRemarks("");
		fm.setAccessControl("1");
		boolean b =  super.dao.saveUpdateOrDelete(fm, null);
		return b;
	}
	/**
	 * 创建模块创建文档(我的、管理中心)
	 */
	public boolean addxq(Map<String, String> mvm,int id,String name,String gs,String url,String fileRealname,int needId) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		FileManage fm =new FileManage();
		fm.setGlId(needId);
		fm.setFileClassification("1");
		fm.setFileName("模块相关文档（"+mvm.get("need_name")+"）");
		fm.setFileText("");
		fm.setCreateId(id+"");
		fm.setAddName(name);
		fm.setCreateTime(createTime);
		fm.setEditTime(createTime);
		fm.setFileFormat(gs);
		fm.setFileUrl(url);
		fm.setFileRealname(fileRealname);
		fm.setFileRemarks("");
		fm.setAccessControl("1");
		boolean b =  super.dao.saveUpdateOrDelete(fm, null);
		return b;
	}
	/**
	 * 变更模块创建文档(我的、 管理中心)
	 */
	public boolean changexq(Map<String, String> mvm,int id,String name,String gs,String url,String fileRealname,String needid,String needname) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		FileManage fm =new FileManage();
		fm.setGlId(Integer.parseInt(needid));
		fm.setFileClassification("1");
		fm.setFileName("模块变更文档（"+needname+"）");
		fm.setFileText("");
		fm.setCreateId(id+"");
		fm.setAddName(name);
		fm.setCreateTime(createTime);
		fm.setEditTime(createTime);
		fm.setFileFormat(gs);
		fm.setFileUrl(url);
		fm.setFileRealname(fileRealname);
		fm.setFileRemarks("");
		fm.setAccessControl("1");
		boolean b =  super.dao.saveUpdateOrDelete(fm, null);
		return b;
	}
	/**
	 * 验收模块创建文档(我的、管理中心)
	 */
	public boolean checkxq(Map<String, String> mvm,int id,String name,String gs,String url,String fileRealname,String needid,String needname) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		FileManage fm =new FileManage();
		fm.setGlId(Integer.parseInt(needid));
		fm.setFileClassification("1");
		fm.setFileName("模块验收文档（"+needname+"）");
		fm.setFileText("");
		fm.setCreateId(id+"");
		fm.setAddName(name);
		fm.setCreateTime(createTime);
		fm.setEditTime(createTime);
		fm.setFileFormat(gs);
		fm.setFileUrl(url);
		fm.setFileRealname(fileRealname);
		fm.setFileRemarks("");
		fm.setAccessControl("1");
		boolean b =  super.dao.saveUpdateOrDelete(fm, null);
		return b;
	}
	/**
	 * 编辑文档
	 */
	public boolean edit(Map<String, String> mvm,FileManage fm,MultipartFile[] file) {
		String fileName = file[0].getOriginalFilename();
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = sdf.format(date);
		fm.setFileClassification(mvm.get("file_type"));
		if(mvm.get("file_type").equals("0")){
			fm.setGlId(Integer.parseInt(mvm.get("xm")));
		}else{
			fm.setGlId(Integer.parseInt(mvm.get("xq")));
		}
		if(fileName.equals("")){
			
		}else{
			Object url=FileUploadUtil.uploadFile(file);
			if(url!=null){
			String fileRealname = file[0].getOriginalFilename();
			String gs=file[0].getOriginalFilename().substring(fileName.lastIndexOf(".")).substring(1);       
			fm.setFileFormat(gs);
			fm.setFileUrl(url.toString());
			fm.setFileRealname(fileRealname);
			}else{
		    return false;
			}
		}
		fm.setFileName(mvm.get("file_name"));
		fm.setFileText(mvm.get("file_text"));
		fm.setEditTime(datetime);
		fm.setFileRemarks(mvm.get("file_remarks"));
		boolean b =  super.dao.saveUpdateOrDelete(fm, null);
		return b;
	}
	
	/**
	 * 创建产品创建文档
	 */
	public boolean addcp(Map<String, String> mvm,int id,String name,String gs,String url,String fileRealname,int pid,String i) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		FileManage fm =new FileManage();
		fm.setGlId(pid);
		fm.setFileClassification("2");
		if(i.equals("2")){
			fm.setFileName("产品立项书（"+mvm.get("product_name")+"）");
		}else if(i.equals("3")){
			fm.setFileName("工作量评估报告（"+mvm.get("product_name")+"）");
		}else{
			fm.setFileName("产品预算费用表（"+mvm.get("product_name")+"）");
		}
		
		fm.setFileText("");
		fm.setCreateId(id+"");
		fm.setAddName(name);
		fm.setCreateTime(createTime);
		fm.setEditTime(createTime);
		fm.setFileFormat(gs);
		fm.setFileUrl(url);
		fm.setFileRealname(fileRealname);
		fm.setFileRemarks("");
		fm.setAccessControl("1");
		boolean b =  super.dao.saveUpdateOrDelete(fm, null);
		return b;
	}

}
