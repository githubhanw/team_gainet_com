package com.zzidc.team.service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.MilepostManage;
import com.zzidc.team.entity.MilepostTaskneed;
/**
 * 里程碑
 * @author chenmenghao
 *
 */
@Service("testMilepostService")
public class TestMilepostService extends GiantBaseService{
	/**
	 * 里程碑列表
	 */
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "select id, milepost_name, milepost_describe, start_time, end_time, edit_time,"
				+ " create_time, author_id, author_name, milepost_state from milepost_manage where 1=1 ";
		String countSql = "SELECT COUNT(0) from milepost_manage where 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			//数据：{type=10, bugfen=1, createtime=, endtime=, member=}
			String milepostState="";//里程碑状态
			if (!StringUtils.isEmpty(milepostState = conditionPage.getQueryCondition().get("milepostState"))) {
				sql += "AND milepost_state=:milepostState ";
				countSql += "AND milepost_state=:milepostState ";
				conditionMap.put("milepostState", milepostState);
				System.out.println("里程碑状态："+milepostState);
			}
			
			String startTime="";//开始时间
			if (!StringUtils.isEmpty(startTime = conditionPage.getQueryCondition().get("startTime"))) {
				sql += "AND start_time=:startTime ";
				countSql += "AND start_time=:startTime ";
				conditionMap.put("startTime", startTime);
				System.out.println("开始时间："+startTime);
			}
			
			String endTime="";//结束时间
			if (!StringUtils.isEmpty(endTime = conditionPage.getQueryCondition().get("endTime"))) {
				sql += "AND end_time=:endTime ";
				countSql += "AND end_time=:endTime ";
				conditionMap.put("endTime", endTime);
				System.out.println("结束时间："+endTime);
			}
			
			String authorId="";//创建者id
			if (!StringUtils.isEmpty(authorId = conditionPage.getQueryCondition().get("authorId"))) {
				sql += "AND author_id=:authorId ";
				countSql += "AND author_id=:authorId ";
				conditionMap.put("authorId", authorId);
				System.out.println("创建者id："+authorId);
			}
		}
		GiantPager resultPage = super.dao.getPage(sql, conditionPage.getCurrentPage(), conditionPage.getPageSize(), conditionMap);
		resultPage.setQueryCondition(GiantUtils.filterSQLMap(conditionPage.getQueryCondition()));
		resultPage.setTotalCounts(super.dao.getGiantCounts(countSql, conditionMap));
		return resultPage;
	}
	/**
	 * 编辑里程碑列表
	 */
	public boolean edit(Map<String, String> mvm) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String edittime = sdf.format(date);
		MilepostManage mm=new MilepostManage();
		mm.setId(Integer.parseInt(mvm.get("mi_id")));
		mm.setMilepostName(mvm.get("milepost_name"));
		mm.setMilepostDescribe(mvm.get("mark"));
		mm.setStartTime(mvm.get("starttime"));
		mm.setEndTime(mvm.get("endtime"));
		mm.setEditTime(edittime);
		mm.setCreateTime(mvm.get("createtime"));
		mm.setAuthorName(mvm.get("author_name"));
		mm.setAuthorId(mvm.get("authorId"));
		mm.setMilepostState(mvm.get("milepostState"));
		System.out.println(mm.getCreateTime());
		boolean b =  super.dao.saveUpdateOrDelete(mm, null);
		return b;
	}
	/**
	 * 创建里程碑列表
	 */
	public boolean add(Map<String, String> mvm,int id,String name) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		MilepostManage mm=new MilepostManage();
		mm.setMilepostName(mvm.get("need_name"));
		mm.setMilepostDescribe(mvm.get("need_remark"));
		mm.setStartTime(mvm.get("start_date"));
		mm.setEndTime(mvm.get("end_date"));
		mm.setCreateTime(createTime);
		mm.setAuthorId(id+"");
		mm.setAuthorName(name);
		mm.setMilepostState("1");
		boolean b =  super.dao.saveUpdateOrDelete(mm, null);
		return b;
	}
	/**
	 * 添加一条里程碑关联需求的数据
	 */
	public boolean ass(Map<String, String> mvm) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = sdf.format(date);
		MilepostTaskneed milepostTaskneed =new MilepostTaskneed();
		milepostTaskneed.setTaskneedId(Integer.parseInt(mvm.get("handover_id")));
		milepostTaskneed.setMilepostId(Integer.parseInt(mvm.get("mi_id")));
		milepostTaskneed.setAssociationTime(dateTime);
		boolean b =  super.dao.saveUpdateOrDelete(milepostTaskneed, null);
		return b;
	}
}
