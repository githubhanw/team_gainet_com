package com.zzidc.team.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.TaskProduct;

import net.sf.json.JSONObject;
/**
 * [产品]
 */
@Service("teamProductsService")
public class TeamProductService extends GiantBaseService{
	@Autowired
	private FilemanageService filemanageService;
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "SELECT p.*,m.`name` 'member_name',(SELECT count(0) FROM task_need tn WHERE tn.product_id=p.id AND state!=0) needCount, " + 
				"(SELECT count(0) FROM task t WHERE t.product_id=p.id AND deleted=0) taskCount FROM task_product p LEFT JOIN member m ON p.member_id=m.id WHERE 1=1 ";
		String countSql = "SELECT count(0) FROM task_product p LEFT JOIN member m ON p.member_id=m.id WHERE 1=1 ";
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
							sql += "AND p.product_name LIKE :search ";
							countSql += "AND p.product_name LIKE :search ";
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
				if ("0".equals(temp)) {//无效
					sql += "AND p.state=0";
					countSql += "AND p.state=0";
				} else if ("1".equals(temp)) {//正常
					sql += "AND p.state=1";
					countSql += "AND p.state=1";
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
	public boolean addOrUpd(Map<String, String> mvm,int id,String name,MultipartFile[] file2,MultipartFile[] file3,MultipartFile[] file4) {
		TaskProduct p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (TaskProduct) super.dao.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			p = new TaskProduct();
			p.setCreateTime(new Timestamp(System.currentTimeMillis()));
			p.setState((short)1);
		}
		p.setProductName(GiantUtil.stringOf(mvm.get("product_name")));
		p.setCompany(GiantUtil.stringOf(mvm.get("company")));
		p.setMemberId(GiantUtil.intOf(mvm.get("member_id"), 0));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean a=super.dao.saveUpdateOrDelete(p, null);
		boolean flags;
		//创建文档
		   JSONObject jsonupload=new JSONObject();
		   jsonupload=filemanageService.uploadfiles(file2);
		   if(jsonupload!=null){
		   flags = filemanageService.addcp(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"2");
		   }
		   jsonupload=filemanageService.uploadfiles(file3);
		   if(jsonupload!=null){
	       flags = filemanageService.addcp(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"3");
		   }
		   jsonupload=filemanageService.uploadfiles(file4);
		   if(jsonupload!=null){
		   flags = filemanageService.addcp(mvm,id,name,jsonupload.getString("gs"),jsonupload.getString("url"),jsonupload.getString("fileName"),p.getId(),"4");
		   }
		return a;
	}
	/**
	 * 修改项目信息
	 */
	public boolean edit(Map<String, String> mvm) {
		TaskProduct p = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			p = (TaskProduct) super.dao.getEntityByPrimaryKey(new TaskProduct(), GiantUtil.intOf(mvm.get("id"), 0));
		} else {
			p = new TaskProduct();
			p.setCreateTime(new Timestamp(System.currentTimeMillis()));
			p.setState((short)1);
		}
		p.setProductName(GiantUtil.stringOf(mvm.get("product_name")));
		p.setCompany(GiantUtil.stringOf(mvm.get("company")));
		p.setMemberId(GiantUtil.intOf(mvm.get("member_id"), 0));
		p.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		p.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return super.dao.saveUpdateOrDelete(p, null);
	}

	/**
	 * 项目详情页面获取同项目模块。
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getRelationTaskList(Integer needId) {
		List<Map<String, Object>> relationTaskList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM `task_need` WHERE 1=1 AND product_id=" + needId + " ORDER BY create_time";
		relationTaskList = super.getMapListBySQL(sql, null);
		return relationTaskList;
	}
}
