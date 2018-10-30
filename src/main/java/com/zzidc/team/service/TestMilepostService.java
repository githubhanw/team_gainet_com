package com.zzidc.team.service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.MilepostManage;
import com.zzidc.team.entity.MilepostTaskneed;
import com.zzidc.team.entity.TaskProject;
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
			String milepostState="";//里程碑状态
			if (!StringUtils.isEmpty(milepostState = conditionPage.getQueryCondition().get("milepostState"))) {
				sql += "AND milepost_state=:milepostState ";
				countSql += "AND milepost_state=:milepostState ";
				conditionMap.put("milepostState", milepostState);
			}
			
			String startTime="";//开始时间
			if (!StringUtils.isEmpty(startTime = conditionPage.getQueryCondition().get("startTime"))) {
				sql += "AND start_time=:startTime ";
				countSql += "AND start_time=:startTime ";
				conditionMap.put("startTime", startTime);
			}
			
			String endTime="";//结束时间
			if (!StringUtils.isEmpty(endTime = conditionPage.getQueryCondition().get("endTime"))) {
				sql += "AND end_time=:endTime ";
				countSql += "AND end_time=:endTime ";
				conditionMap.put("endTime", endTime);
			}
			
			String authorId="";//创建者id
			if (!StringUtils.isEmpty(authorId = conditionPage.getQueryCondition().get("authorId"))) {
				sql += "AND author_id=:authorId ";
				countSql += "AND author_id=:authorId ";
				conditionMap.put("authorId", authorId);
			}
		}
		String temp="";
		if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
			String projectId="";//关联项目id
			//待验收的里程碑
			if(temp.equals("3")){
				if (!StringUtils.isEmpty(projectId = conditionPage.getQueryCondition().get("project_id"))) {
					sql += "AND project_id=:projectId AND milepost_state= 3 ";
					countSql += "AND project_id=:projectId AND milepost_state= 3 ";
					conditionMap.put("projectId", projectId);
				}
			//待编写里程碑报告的里程碑
			}else if(temp.equals("4")){
				if (!StringUtils.isEmpty(projectId = conditionPage.getQueryCondition().get("project_id"))) {
					sql += "AND project_id=:projectId AND milepost_state= 4 ";
					countSql += "AND project_id=:projectId AND milepost_state= 4 ";
					conditionMap.put("projectId", projectId);
				}
			//待验收报告的里程碑
			}else if(temp.equals("5")){
				if (!StringUtils.isEmpty(projectId = conditionPage.getQueryCondition().get("project_id"))) {
					sql += "AND project_id=:projectId AND milepost_state= 5 ";
					countSql += "AND project_id=:projectId AND milepost_state= 5 ";
					conditionMap.put("projectId", projectId);
				}
			//验收完报告的里程碑
			}else if(temp.equals("10")){
				
			}else if(temp.equals("0")){
				
			}else{
				if (!StringUtils.isEmpty(projectId = conditionPage.getQueryCondition().get("project_id"))) {
					sql += "AND project_id=:projectId AND milepost_state= 6 ";
					countSql += "AND project_id=:projectId AND milepost_state= 6 ";
					conditionMap.put("projectId", projectId);
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
	 * 里程碑详情页面
	 */
	public boolean detail(Map<String, String> mvm) {
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
		boolean b =  super.dao.saveUpdateOrDelete(mm, null);
		return b;
	}
	/**
	 * 编辑里程碑列表
	 */
	public boolean edit(Map<String, String> mvm) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String edittime = sdf.format(date);
		MilepostManage mm=new MilepostManage();
		if(GiantUtil.intOf(mvm.get("mi_id"),0) != 0){
		//获取对象
	    mm = (MilepostManage) super.dao.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("mi_id"), 0));
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
		}
		boolean b =  super.dao.saveUpdateOrDelete(mm, null);
		return b;
	}
	/**
	 * 创建里程碑列表
	 */
	public boolean add(Map<String, String> mvm,int id,String name,HttpServletRequest request) {
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
		mm.setProjectId(Integer.parseInt(mvm.get("taskProject_id")));
		String querySql = "select 1 from milepost_manage where project_id = " + Integer.parseInt(mvm.get("taskProject_id"));
		List<Map<String, Object>> list = getMapListBySQL(querySql, null);
		boolean b =  super.dao.saveUpdateOrDelete(mm, null);
		if(b){
			boolean c = false;
			//修改项目状态
			TaskProject tp=(TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("taskProject_id"), 0));
			tp.setState((short) 6);
			c=super.dao.saveUpdateOrDelete(tp, null);
			String[] taskNeedid =request.getParameterValues("task_needid");
			for (String string : taskNeedid) {
				MilepostTaskneed mt=new MilepostTaskneed();
				mt.setMilepostId(mm.getId());
				mt.setTaskneedId(Integer.parseInt(string));
				mt.setAssociationTime(createTime);
			    c=super.dao.saveUpdateOrDelete(mt, null);
			}
			if (c && (list == null || list.size() == 0)) {
				// 调用OA待办接口
				SimpleDateFormat sdfor = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//				OAToDo(mm.getMilepostName(), mm.getStartTime(), mm.getEndTime(),
//						mm.getAuthorId() + "", mm.getAuthorId() + "", "",
//						"", mm.getMilepostRemarks(), "preSales/goClueDetails.do?cluesID=" + mm.getId(),
//						getMemberId() + "");				
			}
			return c;
		}else{
			return false;
		}
	}
	/**
	 * 确认里程碑列表
	 */
	public boolean sure(Map<String, String> mvm) {
		boolean b=false;
		boolean c=false;
		MilepostManage m=new MilepostManage();
		//修改里程碑状态
		List<Map<String, Object>> map=new ArrayList<Map<String, Object>>();
		if(GiantUtil.intOf(mvm.get("project_id"),0) != 0){
			String sql="select id from milepost_manage where project_id = "+mvm.get("project_id");//查询该项目的所有里程碑
			map=super.dao.getMapListBySQL(sql, null);
			if(map!=null){
				for (int i = 0; i < map.size(); i++) {
					map.get(i).get("id");
					//获取对象
				    m = (MilepostManage) super.dao.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(map.get(i).get("id"), 0));
					m.setMilepostState("2");//已确认
					b =  super.dao.saveUpdateOrDelete(m, null);
				}
			}else{
				return false;
			}
		}
		if(b){
		//修改项目状态
		TaskProject tp=(TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("project_id"), 0));
		tp.setState((short) 7);
		tp.setRemark(mvm.get("need_remark"));
		c=super.dao.saveUpdateOrDelete(tp, null);
		if (c) {
			// 调用OA待办接口
			SimpleDateFormat sdfor = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			OAToDo(m.getMilepostName(), m.getStartTime(), m.getEndTime(),
//					m.getAuthorId() + "", m.getAuthorId() + "", "",
//					"", m.getMilepostRemarks(), "preSales/goClueDetails.do?cluesID=" + m.getId(),
//					getMemberId() + "");
		}
		}
		return c;
	}
	/**
	 * 添加一条里程碑关联模块的数据
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
	/**
	 * 添加里程碑关联模块的数据
	 */
	public boolean assTaskneed(int milid,int taskneed) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = sdf.format(date);
		MilepostTaskneed milepostTaskneed =new MilepostTaskneed();
		milepostTaskneed.setTaskneedId(taskneed);
		milepostTaskneed.setMilepostId(milid);
		milepostTaskneed.setAssociationTime(dateTime);
		boolean b =  super.dao.saveUpdateOrDelete(milepostTaskneed, null);
		return b;
	}
	/**
	 * 查询相关模块（相关模块）
	 * @return
	 */
	public List<Map<String, Object>> getMilNeedList(Map<String, String> mvm){
		List<Map<String, Object>> subNeedList = new ArrayList<Map<String, Object>>();
		String sql = "SELECT tn.*,mt.taskneed_id FROM milepost_taskneed mt left join task_need tn on mt.taskneed_id = tn.id WHERE mt.milepost_id=" + mvm.get("id");
		subNeedList = super.dao.getMapListBySQL(sql, null);
		return subNeedList;
	}
	/**
	 * 确认里程碑和概要设计
	 */
	public boolean sureui(Map<String, String> mvm) {
		
		boolean b=false;
		boolean c=false;
		MilepostManage m=new MilepostManage();
		//修改里程碑状态
		List<Map<String, Object>> map=new ArrayList<Map<String, Object>>();
		if(GiantUtil.intOf(mvm.get("project_id"),0) != 0){
			String sql="select id from milepost_manage where project_id = "+mvm.get("project_id");//查询该项目的所有里程碑
			map=super.dao.getMapListBySQL(sql, null);
			if(map!=null){
				for (int i = 0; i < map.size(); i++) {
					map.get(i).get("id");
					//获取对象
				    m = (MilepostManage) super.dao.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(map.get(i).get("id"), 0));
					m.setMilepostState("3");//已确认
					b =  super.dao.saveUpdateOrDelete(m, null);
				}
			}else{
				return false;
			}
		}
		if(b){
		//修改项目状态
		TaskProject tp=(TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(mvm.get("project_id"), 0));
		tp.setState((short) 8);
		tp.setRemark(mvm.get("need_remark"));
		c=super.dao.saveUpdateOrDelete(tp, null);
		if (c) {
			// 调用OA待办接口
			SimpleDateFormat sdfor = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			OAToDo(m.getMilepostName(), m.getStartTime(), m.getEndTime(),
//					m.getAuthorId() + "", m.getAuthorId() + "", "",
//					"", m.getMilepostRemarks(), "preSales/goClueDetails.do?cluesID=" + m.getId(),
//					getMemberId() + "");
		}
		}
		return c;
	}
	/**
	 * 验收
	 */
	public boolean vali(Map<String, String> mvm,int id,String name) {
		MilepostManage t =null;
		 boolean c=false;
		//添加模块页面的项目列表
        if(GiantUtil.intOf(mvm.get("mi_id"), 0) != 0){
        	Date date =new Date();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String vailTime = sdf.format(date);
			//获取对象
		    t = (MilepostManage) super.dao.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("mi_id"), 0));
		    t.setMilepostRemarks(mvm.get("need_remark"));
			t.setCheckName(name);
		    t.setCheckId(id);
		    t.setMilepostState("4");
		    t.setFinishTime(mvm.get("finishtime"));
		    t.setCheckTime(vailTime);
        }
        boolean b =  super.dao.saveUpdateOrDelete(t, null);
        //修改项目状态
        TaskProject tp=(TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(t.getProjectId(), 0));
      	tp.setState((short) 11);
      	if(b){
      	c=super.dao.saveUpdateOrDelete(tp, null);
      	}
		return c;
	}
	/**
	 * 编写里程碑报告
	 */
	public boolean report(Map<String, String> mvm) {
		MilepostManage m=null;
		boolean c=false;
		if(GiantUtil.intOf(mvm.get("mi_id"), 0) != 0){
			//获取对象
		     m = (MilepostManage) super.dao.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("mi_id"), 0));
			 m.setMilepostState("5");//已确认
		}
		boolean b =  super.dao.saveUpdateOrDelete(m, null);
		//修改项目状态
        TaskProject tp=(TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(m.getProjectId(), 0));
      	tp.setState((short) 12);
      	if(b){
      	c=super.dao.saveUpdateOrDelete(tp, null);
      	}
		return c;
	}
	/**
	 * 验收里程碑报告
	 */
	public boolean vailreport(Map<String, String> mvm) {
		MilepostManage m=new MilepostManage();
		boolean c=false;
		Integer count=0;
		if(GiantUtil.intOf(mvm.get("mi_id"), 0) != 0){
			//获取对象
		     m = (MilepostManage) super.dao.getEntityByPrimaryKey(new MilepostManage(), GiantUtil.intOf(mvm.get("mi_id"), 0));
			 m.setMilepostState("6");//已确认
		}
		boolean b =  super.dao.saveUpdateOrDelete(m, null);
		
		if(GiantUtil.intOf(m.getProjectId(),0) != 0){
			String sql="select COUNT(0) from milepost_manage where milepost_state = 3 AND project_id = "+m.getProjectId();//查询该项目的所有里程碑
			count=super.dao.getGiantCounts(sql, null);//该项目下待验收的里程碑的个数
		}
		//修改项目状态
        TaskProject tp=(TaskProject) super.dao.getEntityByPrimaryKey(new TaskProject(), GiantUtil.intOf(m.getProjectId(), 0));
        if(count>0){
        tp.setState((short) 8);
		}else{
        tp.setState((short) 13);
		}
      	if(b){
      	c=super.dao.saveUpdateOrDelete(tp, null);
      	}
		return c;
	}
	/**
	 * 获取子模块【获取已验收模块】
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getSubNeedByNeed(int needId){
		String sql = "SELECT id,need_name,state FROM task_need WHERE state=4 AND parent_id=" + needId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取子模块下的任务【获取已完成任务】
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getSubNeedTaskByNeed(int needId){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img FROM task t, task_need n WHERE t.need_id=n.id AND deleted=0 AND t.state=4 AND n.state=4 AND n.parent_id=" + needId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取模块下的任务【获取已完成任务】
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getTaskByNeed(int needId){
		String sql = "SELECT id,need_id,task_name,interface_img,flow_img FROM task WHERE state=4 AND need_id=" + needId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有任务下的所有测试用例列表
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getTestCaseByNeed(int needId){
		String sql = "SELECT c.id,c.task_id,c.case_name,c.case_type,c.precondition FROM task t, task_need n, test_case c WHERE c.task_id=t.id AND t.need_id=n.id "
				+ "AND c.state=1 AND t.deleted=0 AND t.state=4 AND n.state=4 AND (n.parent_id=" + needId + " OR n.id=" + needId + ")";
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有测试用例下的所有步骤
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getTestCaseStepByNeed(int needId){
		String sql = "SELECT s.case_id,s.step,s.expect FROM task t, task_need n, test_case c, test_case_step s WHERE s.case_id=c.id AND s.version=c.version " + 
				"AND c.task_id=t.id AND t.need_id=n.id AND c.state=1 AND t.deleted=0 AND t.state=4 AND n.state=4 AND (n.parent_id=" + needId + " OR n.id=" + needId + ")";
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取项目下所有模块【获取已验收模块】
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getNeedByProject(int projectId){
		String sql = "SELECT id,need_name,state FROM task_need WHERE state=4 AND parent_id=0 AND project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取项目下所有子模块【获取已验收模块】
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getSubNeedByProject(int projectId){
		String sql = "SELECT id,need_name,state,parent_id FROM task_need WHERE state=4 AND parent_id>0 AND project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有模块下任务【获取已完成任务】
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getNeedTaskByProject(int projectId){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img FROM task t, task_need n "
				+ "WHERE t.need_id=n.id AND deleted=0 AND t.state=4 AND n.state=4 AND n.parent_id=0 AND n.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有子模块下任务【获取已完成任务】
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getSubNeedTaskByProject(int projectId){
		String sql = "SELECT t.id,t.need_id,t.task_name,t.interface_img,t.flow_img FROM task t, task_need n "
				+ "WHERE t.need_id=n.id AND deleted=0 AND t.state=4 AND n.state=4 AND n.parent_id>0 AND n.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有任务下的所有测试用例列表
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getTestCaseByProject(int projectId){
		String sql = "SELECT c.id,c.task_id,c.case_name,c.case_type,c.precondition FROM task t, task_need n, test_case c WHERE c.task_id=t.id AND t.need_id=n.id "
				+ "AND c.state=1 AND t.deleted=0 AND t.state=4 AND n.state=4 AND n.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}

	/**
	 * 获取所有测试用例下的所有步骤
	 * @param needId
	 * @return
	 */
	public List<Map<String, Object>> getTestCaseStepByProject(int projectId){
		String sql = "SELECT s.case_id,s.step,s.expect FROM task t, task_need n, test_case c, test_case_step s WHERE s.case_id=c.id AND s.version=c.version " + 
				"AND c.task_id=t.id AND t.need_id=n.id AND c.state=1 AND t.deleted=0 AND t.state=4 AND n.state=4 AND n.project_id=" + projectId;
		return super.getMapListBySQL(sql, null);
	}
}
