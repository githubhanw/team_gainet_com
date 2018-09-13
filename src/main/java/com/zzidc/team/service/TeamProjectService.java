package com.zzidc.team.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.TaskProject;

import net.sf.json.JSONObject;
/**
 * [说明/描述]
 */
@Service("teamProjectService")
public class TeamProjectService extends GiantBaseService{
	@Autowired
	private FilemanageService filemanageService;
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "SELECT p.*,m.`name` 'member_name',(SELECT count(0) FROM task_need tn WHERE tn.project_id=p.id AND state!=0) needCount, " + 
				"(SELECT count(0) FROM task t WHERE t.project_id=p.id AND deleted=0) taskCount FROM task_project p LEFT JOIN member m ON p.member_id=m.id WHERE 1=1 ";
		String countSql = "SELECT count(0) FROM task_project p LEFT JOIN member m ON p.member_id=m.id WHERE 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
				temp = temp.trim();
				String searchType = "";
				if (!StringUtils.isEmpty(searchType = conditionPage.getQueryCondition().get("searchType"))) {
					if ("1".equals(searchType)) {
						try {
							// 如果成功则为编号
							Integer.parseInt(temp);	
							sql += "AND p.id =:search ";
							countSql += "AND p.id =:search ";
							conditionMap.put("search", temp);
						} catch (Exception e) {
							// 名称条件
							sql += "AND p.project_name LIKE :search ";
							countSql += "AND p.project_name LIKE :search ";
							conditionMap.put("search", temp + "%");
						}
					} else if("2".equals(searchType)) {//项目备注
						sql += "AND p.remark LIKE :search ";
						countSql += "AND p.remark LIKE :search ";
						conditionMap.put("search", "%" + temp + "%");
					}
				}
			}

			String Company="";//公司
			if (!StringUtils.isEmpty(Company = conditionPage.getQueryCondition().get("company"))) {
				sql += "AND p.company=:company ";
				countSql += "AND p.company=:company ";
				conditionMap.put("company", Company);
			}
			
			String MemberId="";//项目负责人
			if (!StringUtils.isEmpty(MemberId = conditionPage.getQueryCondition().get("member_id"))) {
				sql += "AND p.member_id=:member_id ";
				countSql += "AND p.member_id=:member_id ";
				conditionMap.put("member_id", MemberId);
			}
			
			String State="";//状态
			if (!StringUtils.isEmpty(State = conditionPage.getQueryCondition().get("state"))) {
				sql += "AND p.state=:state ";
				countSql += "AND p.state=:state ";
				conditionMap.put("state", State);
			}
			
			String Createtime="";//开始时间
			if (!StringUtils.isEmpty(Createtime = conditionPage.getQueryCondition().get("createtime"))) {
				sql += "AND p.create_time>:createtime ";
				countSql += "AND p.create_time>:createtime ";
				conditionMap.put("createtime", Createtime);
			}
			
			String Endtime="";//结束时间
			if (!StringUtils.isEmpty(Endtime = conditionPage.getQueryCondition().get("endtime"))) {
				sql += "AND p.create_time<:endtime ";
				countSql += "AND p.create_time<:endtime ";
				conditionMap.put("endtime", Endtime);
			}
			
			
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {//已删除
					sql += "AND p.state=0";
					countSql += "AND p.state=0";
				} else if ("1".equals(temp)) {//正常
					sql += "AND p.state=1";
					countSql += "AND p.state=1";
//				} else if ("3".equals(temp)) {//阶段：研究
//					sql += "AND p.stage='研究'";
//					countSql += "AND p.stage='研究'";
//				} else if ("4".equals(temp)) {//阶段：开发
//					sql += "AND p.stage='开发'";
//					countSql += "AND p.stage='开发'";
//				} else if ("5".equals(temp)) {//阶段：完成
//					sql += "AND p.stage='完成'";
//					countSql += "AND p.stage='完成'";
				}
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

	/**
	 * 添加项目信息
	 */
	public boolean addOrUpd(Map<String, String> mvm,int id,String name,MultipartFile[] file1,MultipartFile[] file2,MultipartFile[] file3,MultipartFile[] file4) {
		TaskProject p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			p = new TaskProject();
			p.setCreateTime(new Timestamp(System.currentTimeMillis()));
			p.setState((short)1);
		}
		p.setProjectName(GiantUtil.stringOf(mvm.get("project_name")));
		p.setStartTime(mvm.get("start_date"));
		p.setEndTime(mvm.get("end_date"));
		p.setCompany(GiantUtil.stringOf(mvm.get("company")));
		p.setMemberId(GiantUtil.intOf(mvm.get("member_id"), 0));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		p.setProjectType(mvm.get("type"));
		boolean a=super.dao.saveUpdateOrDelete(p, null);
		boolean flags;
		//创建文档
		if(mvm.get("type").equals("0")){//如果等于0，就是内部文件
		   JSONObject jsonupload=new JSONObject();
		   jsonupload=filemanageService.uploadfiles(file2);
		   if(jsonupload!=null){
		   flags = filemanageService.addxm(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"2");
		   }
		   jsonupload=filemanageService.uploadfiles(file3);
		   if(jsonupload!=null){
	       flags = filemanageService.addxm(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"3");
		   }
		   jsonupload=filemanageService.uploadfiles(file4);
		   if(jsonupload!=null){
		   flags = filemanageService.addxm(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"4");
		   }
		}else{
		   JSONObject jsonupload=new JSONObject();
		   jsonupload=filemanageService.uploadfiles(file1);
		   if(jsonupload!=null){
		   flags = filemanageService.addxm(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"1");
		   }
		   jsonupload=filemanageService.uploadfiles(file2);
		   if(jsonupload!=null){
		   flags = filemanageService.addxm(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"2");
		   }
		   jsonupload=filemanageService.uploadfiles(file3);
		   if(jsonupload!=null){
		   flags = filemanageService.addxm(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"3");
		   }
		   jsonupload=filemanageService.uploadfiles(file4);
		   if(jsonupload!=null){
		   flags = filemanageService.addxm(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"4");
		}
      }
		return a;
	}
	/**
	 * 修改项目信息
	 */
	public boolean edit(Map<String, String> mvm) {
		TaskProject p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			p = new TaskProject();
			p.setCreateTime(new Timestamp(System.currentTimeMillis()));
			p.setState((short)1);
		}
		p.setProjectName(GiantUtil.stringOf(mvm.get("project_name")));
		p.setStartTime(mvm.get("start_date"));
		p.setEndTime(mvm.get("end_date"));
		p.setCompany(GiantUtil.stringOf(mvm.get("company")));
		p.setMemberId(GiantUtil.intOf(mvm.get("member_id"), 0));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		p.setProjectType(mvm.get("type"));
		return super.dao.saveUpdateOrDelete(p, null);
	}
	/**
	 * 获取项目信息
	 * @param projectId
	 * @return
	 */
	public Map<String, Object> getProjectDetail(int projectId){
		String sql = "SELECT * FROM declaration_project WHERE id=" + projectId;
		List<Map<String, Object>> list = super.getMapListBySQL(sql, null);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取项目成果
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getProjectResult(int projectId){
		String sql = "SELECT pr.* FROM declaration_project_result pr WHERE pr.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 获取项目文档
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getProjectDoc(int projectId){
		String sql = "SELECT pd.*,pdt.project_doc_type FROM declaration_project_doc pd, declaration_project_doc_type pdt "
				+ "WHERE pd.type_id=pdt.id AND pd.state>0 AND project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
}
