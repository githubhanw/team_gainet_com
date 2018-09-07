package com.zzidc.team.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.log.LogMethod;
import com.zzidc.log.LogModule;
import com.zzidc.log.PMLog;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.TaskNeed;

/**
 * [说明/描述]
 */
@Service("teamNeedService")
public class TeamNeedService extends GiantBaseService{

	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		Integer	memberId = 157;
		try {
			memberId = super.getMemberId();
		} catch (Exception e) {
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "SELECT tn.*,tp.project_name,m.name meeting_name FROM task_need tn LEFT JOIN task_project tp ON tn.project_id=tp.id LEFT JOIN month_meeting m ON tn.meeting_id=m.id WHERE 1=1 ";
		String countSql = "SELECT COUNT(0) FROM task_need tn LEFT JOIN task_project tp ON tn.project_id=tp.id WHERE 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("98".equals(temp) || "97".equals(temp)) {//当前用户
					sql += "AND (tn.member_id=:memberId OR tn.create_id=:memberId OR tn.assigned_id=:memberId OR tn.checked_id=:memberId) ";
					countSql += "AND (tn.member_id=:memberId OR tn.create_id=:memberId OR tn.assigned_id=:memberId OR tn.checked_id=:memberId) ";
					conditionMap.put("memberId", memberId);
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("nameOrId"))) {
				sql += "AND (tn.id=:id OR tn.need_name LIKE :name) ";
				countSql += "AND (tn.id=:id OR tn.need_name LIKE :name) ";
				conditionMap.put("id", temp);
				conditionMap.put("name", "%" + temp + "%");
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
				temp = temp.trim();
				String needType = "";
				if (!StringUtils.isEmpty(needType = conditionPage.getQueryCondition().get("needType"))) {
					if ("1".equals(needType)) {//需求名称 / ID
						sql += "AND (tn.id=:id OR tn.need_name LIKE :name) ";
						countSql += "AND (tn.id=:id OR tn.need_name LIKE :name) ";
						conditionMap.put("id", temp);
						conditionMap.put("name", "%" + temp + "%");
					} else if("2".equals(needType)) {//需求描述
						sql += "AND tn.need_remark LIKE :search ";
						countSql += "AND tn.need_remark LIKE :search ";
						conditionMap.put("search", "%" + temp + "%");
					}
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("memberSearch"))) {
				temp = temp.trim();
				String memberType = "";
				if (!StringUtils.isEmpty(memberType = conditionPage.getQueryCondition().get("memberType"))) {
					if ("1".equals(memberType)) {//指派人
						sql += "AND tn.assigned_name=:memberSearch ";
						countSql += "AND tn.assigned_name=:memberSearch ";
					} else if("2".equals(memberType)) {//关闭人
						sql += "AND tn.closed_name=:memberSearch ";
						countSql += "AND tn.closed_name=:memberSearch ";
					} else if("3".equals(memberType)) {//需求方
						sql += "AND tn.member_name=:memberSearch ";
						countSql += "AND tn.member_name=:memberSearch ";
					}
					conditionMap.put("memberSearch", temp);
				}
			}
			String temp1 = "";
			if (!StringUtils.isEmpty(temp1 = conditionPage.getQueryCondition().get("project_id"))) {
				if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
					if ("96".equals(temp)) {// 所属项目
						sql += "AND tn.project_id=:project_id ";
						countSql += "AND tn.project_id=:project_id ";
						conditionMap.put("project_id", temp1);
					}
				}
			}
			
			//hanwei   start
			String temp2 = "";
			if (!StringUtils.isEmpty(temp2 = conditionPage.getQueryCondition().get("meeting_id"))) {
				if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
					if ("301".equals(temp)) {//月会议对应的需求总数
						sql += "AND tn.meeting_id=" + temp2 + " ";
						countSql += "AND tn.meeting_id=" + temp2 + " ";
					}
					if ("302".equals(temp)) {//月会议对应的已验收需求总数
						sql += "AND tn.meeting_id=" + temp2 + " AND tn.stage = 2 ";
						countSql += "AND tn.meeting_id=" + temp2 + " AND tn.stage = 2 ";
					}
					if ("303".equals(temp)) {//月会议对应的待验收需求总数
						sql += "AND tn.meeting_id=" + temp2 + " AND tn.stage = 1 ";
						countSql += "AND tn.meeting_id=" + temp2 + " AND tn.stage = 1 ";
					}
				}
			}
			//hanwei   end
			
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("state"))) {
				sql += "AND tn.state=:state ";
				countSql += "AND tn.state=:state ";
				conditionMap.put("state", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("srcId"))) {
				sql += "AND tn.src_id=:src_id ";
				countSql += "AND tn.src_id=:src_id ";
				conditionMap.put("src_id", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("meetingId"))) {
				sql += "AND tn.meeting_id=:meeting_id ";
				countSql += "AND tn.meeting_id=:meeting_id ";
				conditionMap.put("meeting_id", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("level"))) {
				sql += "AND tn.level=:level ";
				countSql += "AND tn.level=:level ";
				conditionMap.put("level", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("start_date"))) {
				String dateType = "";
				if (!StringUtils.isEmpty(dateType = conditionPage.getQueryCondition().get("dateType"))) {
					if ("1".equals(dateType)) {//开始时间
						sql += "AND tn.start_date>:start_date ";
						countSql += "AND tn.start_date>:start_date ";
					} else if("2".equals(dateType)) {//结束时间
						sql += "AND tn.start_date>:start_date ";
						countSql += "AND tn.start_date>:start_date ";
					}
					conditionMap.put("start_date", temp);
				}
				
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("end_date"))) {
				String dateType = "";
				if (!StringUtils.isEmpty(dateType = conditionPage.getQueryCondition().get("dateType"))) {
					if ("1".equals(dateType)) {//开始时间
						sql += "AND tn.end_date<:end_date ";
						countSql += "AND tn.end_date<:end_date ";
					} else if("2".equals(dateType)) {//结束时间
						sql += "AND tn.end_date<:end_date ";
						countSql += "AND tn.end_date<:end_date ";
					}
					conditionMap.put("end_date", temp);
				}
				
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {//已删除
					sql += "AND tn.state=0 ";
					countSql += "AND tn.state=0 ";
				} else if (!"12".equals(temp)) {//未删除
					sql += "AND tn.state!=0 ";
					countSql += "AND tn.state!=0 ";
				}
				
				if ("1".equals(temp)) {//正常
					sql += "AND tn.parent_id=0 AND tn.state!=3";
					countSql += "AND tn.parent_id=0 AND tn.state!=3";
				} else if ("2".equals(temp)) {//所有
					sql += "AND tn.parent_id=0";
					countSql += "AND tn.parent_id=0";
				} else if ("3".equals(temp)) {//已验收
					sql += "AND tn.stage=2";
					countSql += "AND tn.stage=2";
				} else if ("4".equals(temp)) {//激活
					sql += "AND tn.state=1";
					countSql += "AND tn.state=1";
				} else if ("5".equals(temp)) {//已变更
					sql += "AND tn.state=2";
					countSql += "AND tn.state=2";
				} else if ("6".equals(temp)) {//由我创建
					sql += "AND tn.create_id=" + memberId;
					countSql += "AND tn.create_id=" + memberId;
				} else if ("7".equals(temp)) {//指派给我
					sql += "AND tn.assigned_id=" + memberId;
					countSql += "AND tn.assigned_id=" + memberId;
				} else if ("8".equals(temp)) {//由我关闭
					sql += "AND tn.closed_id=" + memberId;
					countSql += "AND tn.closed_id=" + memberId;
				} else if ("9".equals(temp)) {//待验收
					sql += "AND tn.stage=1";
					countSql += "AND tn.stage=1";
				} else if ("10".equals(temp)) {//由我验收
					sql += "AND tn.checked_id=" + memberId;
					countSql += "AND tn.checked_id=" + memberId;
				} else if ("11".equals(temp)) {//需求方是我
					sql += "AND tn.member_id=" + memberId;
					countSql += "AND tn.member_id=" + memberId;
				} else if ("13".equals(temp)) {//待我验收
					sql += "AND tn.stage=1 AND tn.member_id=" + memberId;
					countSql += "AND tn.stage=1 AND tn.member_id=" + memberId;
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
	 * 获取子需求
	 * @return
	 */
	public List<Map<String, Object>> getSubNeedList(List<?> needList, String type){
		if(needList == null || needList.size() == 0) {
			return null;
		}
		List<Map<String, Object>> subNeedList = new ArrayList<Map<String, Object>>();
		for(Object obj: needList) {
			@SuppressWarnings("unchecked")
			Map<String, Object> needMap = (Map<String, Object>) obj;
			String sql = "SELECT tn.*,tp.project_name FROM task_need tn LEFT JOIN task_project tp ON tn.project_id=tp.id WHERE tn.parent_id=" + needMap.get("id");
			if("1".equals(type)) {
				sql += " AND  tn.state>0 AND tn.state<3";
			}
			sql += " ORDER BY tn.state";
			List<Map<String, Object>> list = super.getMapListBySQL(sql, null);
			if(list != null) {
				needMap.put("subNeed", list);
				subNeedList.addAll(list);
			}
		}
		return subNeedList;
	}

	/**
	 * 获取需求详情
	 * @return
	 */
	public Map<String, Object> getNeedDetail(Integer needId){
		String sql = "SELECT tn.*,ns.need_src,tp.project_name FROM task_need tn LEFT JOIN task_project tp ON tn.project_id=tp.id LEFT JOIN need_src ns ON tn.src_id=ns.id WHERE tn.id=" + needId;
		List<Map<String, Object>> list = super.getMapListBySQL(sql, null);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 需求详情页面获取子需求
	 * @return
	 */
	public List<Map<String, Object>> getSubNeedList(Integer parentNeedId){
		List<Map<String, Object>> subNeedList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT tn.* FROM task_need tn WHERE tn.parent_id=" + parentNeedId;
		subNeedList = super.getMapListBySQL(sql, null);
		return subNeedList;
	}

	/**
	 * 需求详情页面获取相关任务
	 * @return
	 */
	public List<Map<String, Object>> getTaskList(Integer parentNeedId){
		List<Map<String, Object>> subNeedList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM `task` WHERE deleted=0 AND task_type!=2 AND need_id=" + parentNeedId + " ORDER BY state";
		subNeedList = super.getMapListBySQL(sql, null);
		return subNeedList;
	}

	/**
	 * 需求详情页面获取关联需求
	 * @return
	 */
	public List<Map<String, Object>> getLinkNeed(String linkNeed){
		List<Map<String, Object>> linkNeedList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT * FROM task_need WHERE id in (" + linkNeed + ")";
		linkNeedList = super.getMapListBySQL(sql, null);
		return linkNeedList;
	}

	/**
	 * 获取日志
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getLogList(Integer needId){
		String sql = "SELECT * FROM `action_log` where module='need' and object_id=" + needId;
		List<Map<String, Object>> logList = super.getMapListBySQL(sql, null);
		if(logList != null && logList.size() > 0) {
			for(Map<String, Object> log: logList) {
				sql = "SELECT tfd.field_desc,ah.old_data,ah.new_data,ah.diff FROM action_history ah LEFT JOIN table_field_desc tfd ON ah.field=tfd.field_name WHERE tfd.table_name='need' AND action_id=" + log.get("id");
				List<Map<String, Object>> historyList = super.getMapListBySQL(sql, null);
				if(historyList != null && historyList.size() > 0) {
					log.put("history", historyList);
				}
			}
		}
		return logList;
	}

	/**
	 * 获取项目信息
	 * @return
	 */
	public List<Map<String, Object>> getTeamProject(){
		String sql = "select id,project_name from task_project where state>0 order by id desc";
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取需求来源
	 * @return
	 */
	public List<Map<String, Object>> getNeedSrc(){
		String sql = "select id,need_src from need_src order by sort";
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 添加需求信息
	 */
	public boolean add(Map<String, String> mvm) {
		PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.ADD, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
		TaskNeed need = new TaskNeed();
		need.setNeedName(GiantUtil.stringOf(mvm.get("need_name")));
		need.setProjectId(GiantUtil.intOf(mvm.get("project_id"), 0));
		need.setCreateId(super.getMemberId());
		need.setCreateName(super.getMemberName());
		//需求方
		Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("member_id"), 0));
		need.setMemberId(member == null ? 0 : member.getId());
		need.setMemberName(member == null ? "" : member.getName());
		//指派给
		Member assign = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("assigned_id"), 0));
		need.setAssignedId(assign == null ? 0 : assign.getId());
		need.setAssignedName(assign == null ? "" : assign.getName());
		need.setAssignedTime(new Timestamp(System.currentTimeMillis()));
		//变更人
		need.setChangedId(0);
		need.setChangedName("");
		need.setChangedTime(null);
		need.setChangedCount((short) 0);
		//关闭人
		need.setClosedId(0);
		need.setClosedName("");
		need.setClosedTime(null);
		need.setClosedReason("");
		//验收人
		need.setCheckedId(0);
		need.setCheckedName("");
		need.setCheckedTime(null);
		need.setCreateTime(new Timestamp(System.currentTimeMillis()));
		need.setState((short)1);
		
		need.setSrcId(GiantUtil.intOf(mvm.get("src_id"), 0));
		need.setLevel(GiantUtil.intOf(mvm.get("level"), 0));
		//分解
		need.setResolved((short)0);
		need.setParentId(GiantUtil.intOf(mvm.get("id"), 0));
		need.setStage((short)1);
		try {
			need.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("start_date")));
		} catch (ParseException e) {
			need.setStartDate(new Date());
		}
		try {
			need.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("end_date")));
		} catch (ParseException e) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, 1);
			need.setEndDate(c.getTime());
		}
		need.setSrcRemark(GiantUtil.stringOf(mvm.get("src_remark")));
		need.setNeedRemark(GiantUtil.stringOf(mvm.get("need_remark")));
		need.setCheckRemark(GiantUtil.stringOf(mvm.get("check_remark")));
		need.setFull((short) 1);
		need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean flag = super.dao.saveUpdateOrDelete(need, null);
		pmLog.setObjectId(need.getId());
		this.log(pmLog);
		return flag;
	}

	/**
	 * 批量添加、分解需求信息
	 */
	public Integer batchAdd(Map<String, String> mvm) {
		TaskNeed need = new TaskNeed();
		need.setCreateId(super.getMemberId());
		need.setCreateName(super.getMemberName());
		//分解
		need.setResolved((short)0);
		need.setParentId(GiantUtil.intOf(mvm.get("id"), 0));
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed parentNeed = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(parentNeed.getResolved() == 0) {
				parentNeed.setResolved((short) 1);
				parentNeed.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				super.dao.saveUpdateOrDelete(parentNeed, null);
			}
		}
		need.setStage((short)1);
		//需求方
		Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("member_id"), 0));
		need.setMemberId(member == null ? 0 : member.getId());
		need.setMemberName(member == null ? "" : member.getName());
		//指派给
		Member assign = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("assigned_id"), 0));
		need.setAssignedId(assign == null ? 0 : assign.getId());
		need.setAssignedName(assign == null ? "" : assign.getName());
		need.setAssignedTime(new Timestamp(System.currentTimeMillis()));
		//变更人
		need.setChangedId(0);
		need.setChangedName("");
		need.setChangedTime(null);
		need.setChangedCount((short) 0);
		//关闭人
		need.setClosedId(0);
		need.setClosedName("");
		need.setClosedTime(null);
		need.setClosedReason("");
		//验收人
		need.setCheckedId(0);
		need.setCheckedName("");
		need.setCheckedTime(null);
		need.setCreateTime(new Timestamp(System.currentTimeMillis()));
		need.setState((short)1);
		need.setNeedName(GiantUtil.stringOf(mvm.get("need_name")));
		need.setProjectId(GiantUtil.intOf(mvm.get("project_id"), 0));
		
		need.setSrcId(GiantUtil.intOf(mvm.get("src_id"), 0));
		need.setLevel(GiantUtil.intOf(mvm.get("level"), 0));
		try {
			need.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("start_date")));
		} catch (ParseException e) {
			need.setStartDate(new Date());
		}
		try {
			need.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("end_date")));
		} catch (ParseException e) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, 1);
			need.setEndDate(c.getTime());
		}
		need.setFull((short) 0);
		need.setSrcRemark("");
		need.setCheckRemark("");
		need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		if(super.dao.saveUpdateOrDelete(need, null)) {
			return need.getId();
		}else {
			return 0;
		}
	}

	/**
	 * 编辑项目信息
	 */
	public boolean edit(Map<String, String> mvm) {
		PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.EDIT, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
		//获取对象
		TaskNeed need = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
		TaskNeed oldNeed = new TaskNeed();
		BeanUtils.copyProperties(need, oldNeed);
		
		//需求方
		Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("member_id"), 0));
		need.setMemberId(member == null ? 0 : member.getId());
		need.setMemberName(member == null ? "" : member.getName());
		//指派给
		Member assign = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("assigned_id"), 0));
		need.setAssignedId(assign == null ? 0 : assign.getId());
		need.setAssignedName(assign == null ? "" : assign.getName());
		need.setAssignedTime(new Timestamp(System.currentTimeMillis()));
		
		need.setProjectId(GiantUtil.intOf(mvm.get("project_id"), 0));
		need.setSrcId(GiantUtil.intOf(mvm.get("src_id"), 0));
		need.setLevel(GiantUtil.intOf(mvm.get("level"), 0));
		need.setSrcRemark(GiantUtil.stringOf(mvm.get("src_remark")));
		try {
			need.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("start_date")));
		} catch (ParseException e) {
			need.setStartDate(new Date());
		}
		try {
			need.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("end_date")));
		} catch (ParseException e) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, 1);
			need.setEndDate(c.getTime());
		}
		
		//项目的一生信息修改
		if (!GiantUtils.isEmpty(mvm.get("checked_time"))) {//验收时间
			try {//实际结束
				need.setCheckedTime(super.returnTime(mvm.get("checked_time")));
				need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			} catch (Exception e) {
				need.setCheckedTime(new Timestamp(System.currentTimeMillis()));
				need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			}
		}
		if (!GiantUtils.isEmpty(mvm.get("checked_id"))) {//验收人
			Member check = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("checked_id"), 0));
			need.setCheckedId(check == null ? 0 : check.getId());
			need.setCheckedName(check == null ? "" : check.getName());
		}
		if (!GiantUtils.isEmpty(mvm.get("closed_id"))) {//关闭人
			Member close = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("closed_id"), 0));
			need.setClosedId(close == null ? 0 : close.getId());
			need.setClosedName(close == null ? "" : close.getName());
		}
		if (!GiantUtils.isEmpty(mvm.get("closed_time"))) {//关闭时间
			try {//关闭时间
				need.setClosedTime(super.returnTime(mvm.get("closed_time")));
				need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			} catch (Exception e) {
				need.setClosedTime(new Timestamp(System.currentTimeMillis()));
				need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			}
		}
		if (!GiantUtils.isEmpty(mvm.get("closed_reason"))) {//关闭原因
			need.setClosedReason(mvm.get("closed_reason"));
		}
		
		need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		boolean flag = super.dao.saveUpdateOrDelete(need, null);
		pmLog.add(oldNeed, need, "member_name", "assigned_name", "project_id", "src_id", "level", "start_date", "end_date", "checked_time", "closed_time", "checked_name", "closed_name", "closed_reason");
		pmLog.setObjectId(need.getId());
		this.log(pmLog);
		return flag;
				
	}
	
	/**
	 * [是否能进行指派操作] <br>
	 * <pre>
	 * 只有需求方和创建者能指派
	 * </pre>
	 * @author likai <br>
	 * @date 2018年8月13日 下午5:16:50 <br>
	 * @param mvm
	 * @return <br>
	 */
	public boolean isCanAssign(Map<String, String> mvm) {
		boolean b = false;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed need = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			// 当前用户是否为创建者
			b = this.isCurrentMember(need.getCreateId());
			// 当前用户是否为需求方
			b = b && this.isCurrentMember(need.getMemberId());
			if(b) {
				// 是否已经指派
				Integer assignedId = need.getAssignedId();
				if(assignedId != null && need.getAssignedId() > 0) {
					b = b && false;
				}
			}
			return b;
		}
		return b;
	}

	/**
	 * 指派给
	 */
	public boolean assign(Map<String, String> mvm) {
		TaskNeed need = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			need = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.ASSIGN, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TaskNeed oldT = new TaskNeed();
			BeanUtils.copyProperties(need, oldT);
			
			Member assign = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("assigned_id"), 0));
			need.setAssignedId(assign == null ? 0 : assign.getId());
			need.setAssignedName(assign == null ? "" : assign.getName());
			need.setAssignedTime(new Timestamp(System.currentTimeMillis()));
			need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(need, null);
			if(b) {
				pmLog.add(need.getId(), oldT, need, "assigned_name");
				this.log(pmLog);
			}
			return b;
		}
		return false;
	}
	
	/**
	 * [是否能进行指派操作] <br>
	 * <pre>
	 * 只有需求方和创建者能指派
	 * </pre>
	 * @author likai <br>
	 * @date 2018年8月13日 下午5:16:50 <br>
	 * @param mvm
	 * @return <br>
	 */
	public boolean isCanOperation(Map<String, String> mvm) {
		boolean b = false;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			TaskNeed need = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
//			// 当前用户是否为创建者
//			b = this.isCurrentMember(need.getCreateId());
			
			// 当前用户是否为需求方
			b = this.isCurrentMember(need.getMemberId());
			return b;
		}
		return b;
	}

	/**
	 * 变更需求信息
	 */
	public boolean change(Map<String, String> mvm) {
		TaskNeed need = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			need = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.CHANGE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TaskNeed oldT = new TaskNeed();
			BeanUtils.copyProperties(need, oldT);
			if (need.getFull() == 1) {
				need.setChangedId(super.getMemberId());
				need.setChangedName(super.getMemberName());
				need.setChangedTime(new Timestamp(System.currentTimeMillis()));
				if(need.getChangedCount() == null || need.getChangedCount() < 1) {
					need.setChangedCount((short) 1);
				}else {
					need.setChangedCount((short) (need.getChangedCount() + 1));
				}
				need.setState((short) 2);
			} else {
				need.setFull((short) 1);
				pmLog.setMethod(LogMethod.PERFECT);
			}
			need.setNeedName(GiantUtil.stringOf(mvm.get("need_name")));
			need.setCheckRemark(GiantUtil.stringOf(mvm.get("check_remark")));
			need.setNeedRemark(GiantUtil.stringOf(mvm.get("need_remark")));
			need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(need, null);
			if(b) {
				pmLog.add(need.getId(), oldT, need, new String[]{"check_remark","need_remark"},"changed_name","need_name","state","changed_count","check_remark","need_remark");
				this.log(pmLog);
			}
			return b;
		}
		return false;
	}

	/**
	 * 关闭需求
	 */
	public boolean close(Map<String, String> mvm) {
		TaskNeed need = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			need = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.CLOSE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TaskNeed oldT = new TaskNeed();
			BeanUtils.copyProperties(need, oldT);
			
			need.setClosedId(super.getMemberId());
			need.setClosedName(super.getMemberName());
			need.setClosedReason(GiantUtil.stringOf(mvm.get("closedReason")));
			need.setClosedTime(new Timestamp(System.currentTimeMillis()));
			need.setState((short)3);
			need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(need, null);
			if(b) {
				pmLog.add(need.getId(), oldT, need,"closed_reason","closed_name");
				this.log(pmLog);
			}
			return b;
		}
		return false;
	}

	/**
	 * 激活需求
	 */
	public boolean active(Map<String, String> mvm) {
		TaskNeed need = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			need = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.ACTIVE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TaskNeed oldT = new TaskNeed();
			BeanUtils.copyProperties(need, oldT);
			
			need.setState((short)GiantUtil.intOf(mvm.get("state"), 1));
			need.setClosedId(0);
			need.setClosedName("");
			need.setClosedTime(null);
			need.setClosedReason("");
			need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(need, null);
			if(b) {
				pmLog.add(need.getId(), oldT, need,"update_time");
				this.log(pmLog);
			}
			return b;
		}
		return false;
	}

	
	/**
	 * [验收] <br>
	 * @author likai <br>
	 * @date 2018年7月26日 下午8:27:39 <br>
	 * @param mvm 参数 {r=0.29616789999172366, stage=y, comment=, id=25}
	 * @return 是否成功 <br>
	 */
	public boolean check(Map<String, String> mvm) {
		TaskNeed need = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			need = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(need != null) {
				PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.CHECK, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
				TaskNeed oldT = new TaskNeed();
				BeanUtils.copyProperties(need, oldT);
				
				need.setCheckedId(super.getMemberId());
				need.setCheckedName(super.getMemberName());
				need.setCheckedTime(new Timestamp(System.currentTimeMillis()));
				need.setStage("y".equals(GiantUtil.stringOf(mvm.get("stage"))) ? (short)2 : (short)3); // stage 2表示验收完成，3表示验收未通过
				need.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				boolean b = super.dao.saveUpdateOrDelete(need, null);
				if(b) {
					pmLog.add(need.getId(), oldT, need,"checked_name");
					this.log(pmLog);
				}
				return b;
			}
		}
		return false;
	}
	
	/**
	 * [判断需求是否能进行关联操作] <br>
	 * 
	 * @author likai <br>
	 * @date 2018年7月26日 下午9:10:04 <br>
	 * @param id 需求ID
	 * @return <br>
	 */
	public boolean isCanRelevance(int id) {
		TaskNeed need = (TaskNeed) super.getEntityByPrimaryKey(new TaskNeed(), id);
		if(need != null) {
			if(need.getParentId() > 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * [获取当前需求可关联的需求列表] <br>
	 * <pre>
	 *  符合条件：
	 *  1. 不是子需求的， 即parent_id 为 0；
	 *  2. 没有子需求的需求，即id 不在 parent_id中的，除了当前需求id；
	 *  3. state不为删除的，及 state != 0的；
	 * </pre>
	 * 
	 * @author likai <br>
	 * @date 2018年7月26日 下午9:10:04 <br>
	 * @param id 需求ID
	 * @return <br>
	 */
	public List<Map<String, Object>> getCanRelevanceNeeds(int id) {
		if(isCanRelevance(id)) {
			String sql = "select id, need_name, parent_id from task_need where (parent_id = 0 or parent_id = :id) and state <> 0 and id not in (select parent_id from task_need where parent_id > 0)";
			Map<String, Object> conditionMap = new HashMap<String, Object>();
			conditionMap.put("id", id);
			return super.getMapListBySQL(sql, conditionMap);
		} else {
			return new ArrayList<Map<String, Object>>();
		}
	}

	/**
	 * 获取可以关联的月会议记录
	 * @return
	 */
	public List<Map<String, Object>> getCanRelateMeetings() {
		String sql = "SELECT * FROM month_meeting WHERE state=1";
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取月会议记录
	 * @return
	 */
	public List<Map<String, Object>> getRelateMeetings() {
		String sql = "SELECT * FROM month_meeting";
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * [关联] <br>
	 * @author likai <br>
	 * @date 2018年7月26日 下午8:27:39 <br>
	 * @param mvm 参数{r=0.4018686015158184, id=25, needs=1,3,5, comment=dsdsdsd}
	 * @return 是否成功 <br>
	 */
	public boolean relevance(Map<String, String> mvm) {
		if(!isCanRelevance(GiantUtil.intOf(mvm.get("id"), 0))) { 
			return false;
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			TaskNeed taskNeed = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(taskNeed != null) {
				PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.RELEVANCE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
				TaskNeed oldT = new TaskNeed();
				BeanUtils.copyProperties(taskNeed, oldT);
				
				taskNeed.setLink(GiantUtil.stringOf(mvm.get("needs")));
				taskNeed.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				boolean b = super.dao.saveOrUpdateOne(taskNeed);
				if (b) {
					pmLog.add(taskNeed.getId(), oldT, taskNeed,"link");
					this.log(pmLog);
				}
				return b; 
			}
		}
		return false;
	}

	/**
	 * 关联月会议
	 */
	public boolean relate(Map<String, String> mvm) {
		if(!isCanRelevance(GiantUtil.intOf(mvm.get("id"), 0))) { 
			return false;
		}
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			TaskNeed taskNeed = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			if(taskNeed != null) {
				PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.RELATE, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
				TaskNeed oldT = new TaskNeed();
				BeanUtils.copyProperties(taskNeed, oldT);
				
				taskNeed.setMeetingId(GiantUtil.intOf(mvm.get("meeting_id"), 0));
				taskNeed.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				boolean b = super.dao.saveOrUpdateOne(taskNeed);
				if (b) {
					pmLog.add(taskNeed.getId(), oldT, taskNeed, "meeting_id");
					this.log(pmLog);
				}
				return b; 
			}
		}
		return false;
	}

	/**
	 * 开始需求
	 */
	public boolean open(Map<String, String> mvm) {
		TaskNeed n = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			n = (TaskNeed) super.dao.getEntityByPrimaryKey(new TaskNeed(), GiantUtil.intOf(mvm.get("id"), 0));
			PMLog pmLog = new PMLog(LogModule.NEED, LogMethod.OPEN, mvm.toString(), GiantUtil.stringOf(mvm.get("comment")));
			TaskNeed oldT = new TaskNeed();
			BeanUtils.copyProperties(n, oldT);
			
//			n.setOpenedId(0);
//			n.setOpenedName("");
//			n.setOpenedTime(null);
			try {
				n.setPlanEndDate(super.returnTime(mvm.get("plan_end_date")));
			} catch (Exception e) {
				n.setPlanEndDate(new Date());
			}
			n.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			boolean b = super.dao.saveUpdateOrDelete(n, null);
			if (b) {
				pmLog.add(n.getId(), oldT, n,"plan_end_date");
				this.log(pmLog);
			}
			return b; 
		}
		return false;
	}
	
}
