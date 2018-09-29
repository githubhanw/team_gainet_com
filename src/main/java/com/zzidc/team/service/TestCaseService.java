package com.zzidc.team.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.TestBug;
import com.zzidc.team.entity.TestCase;
import com.zzidc.team.entity.TestCaseStep;

/**
 *  测试用例管理
 */
@Service("testCaseService")
public class TestCaseService extends GiantBaseService {
	
	/**
	 * Bug列表
	 * @param conditionPage
	 * @return
	 */
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
		String sql = "select tb.id, tb.taskid,tb.bugproject,tb.bugdes,tb.bugrank,tb.creater,"
				+ "tb.developer,tb.solver,tb.solvestatus,tb.createtime,tb.solvetime,t.task_name "
				+ "from test_bug tb left join task t on t.id = tb.taskid where 1=1 ";
		String countSql = "SELECT COUNT(0) from test_bug tb left join task t on t.id = tb.taskid where 1=1 ";
		if (conditionPage.getQueryCondition() != null) {
			String Bugfen="";//BUG分类
			if (!StringUtils.isEmpty(Bugfen = conditionPage.getQueryCondition().get("bugfen"))) {
				sql += "AND tb.bugfen=:bugfen ";
				countSql += "AND tb.bugfen=:bugfen ";
				conditionMap.put("bugfen", Bugfen);
			}
			
			String Bugrank="";//BUG等级
			if (!StringUtils.isEmpty(Bugrank = conditionPage.getQueryCondition().get("bugrank"))) {
				sql += "AND tb.bugrank=:bugrank ";
				countSql += "AND tb.bugrank=:bugrank ";
				conditionMap.put("bugrank", Bugrank);
			}
			
			String Bugtype="";//BUG类型
			if (!StringUtils.isEmpty(Bugtype = conditionPage.getQueryCondition().get("bugtype"))) {
				sql += "AND tb.bugtype=:bugtype ";
				countSql += "AND tb.bugtype=:bugtype ";
				conditionMap.put("bugtype", Bugtype);
			}
			
			String Solution="";//解决方案
			if (!StringUtils.isEmpty(Solution = conditionPage.getQueryCondition().get("solution"))) {
				sql += "AND tb.solution=:solution ";
				countSql += "AND tb.solution=:solution ";
				conditionMap.put("solution", Solution);
			}
			
			String Solvestatus="";//解决状态
			if (!StringUtils.isEmpty(Solvestatus = conditionPage.getQueryCondition().get("solvestatus"))) {
				sql += "AND tb.solvestatus=:solvestatus ";
				countSql += "AND tb.solvestatus=:solvestatus ";
				conditionMap.put("solvestatus", Solvestatus);
			}
			
			String Createtime="";//开始时间
			if (!StringUtils.isEmpty(Createtime = conditionPage.getQueryCondition().get("createtime"))) {
				sql += "AND tb.createtime>:createtime ";
				countSql += "AND tb.createtime>:createtime ";
				conditionMap.put("createtime", Createtime);
			}
			
			String Endtime="";//结束时间
			if (!StringUtils.isEmpty(Endtime = conditionPage.getQueryCondition().get("endtime"))) {
				sql += "AND tb.createtime<:endtime ";
				countSql += "AND tb.createtime<:endtime ";
				conditionMap.put("endtime", Endtime);
			}
			
			String Member="";//人员类型
			if (!StringUtils.isEmpty(Member = conditionPage.getQueryCondition().get("member"))) {//人员是否存在
				String MemberState="";//人员类型条件
				if (!StringUtils.isEmpty(MemberState = conditionPage.getQueryCondition().get("memberState"))) {//人员类型条件
					if ("0".equals(MemberState)) {//创建者
						sql += "AND tb.creater_id=:member ";
						countSql += "AND tb.creater_id=:member ";
						conditionMap.put("member", Member);
					}else if("1".equals(MemberState)) {
						sql += "AND tb.developer_id=:member ";
						countSql += "AND tb.developer_id=:member ";
						conditionMap.put("member", Member);
					}else if("2".equals(MemberState)) {
						sql += "AND tb.solver_id=:member ";
						countSql += "AND tb.solver_id=:member ";
						conditionMap.put("member", Member);
					}
				}
			}
			
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {// 所有
				} else if ("1".equals(temp)) {// 待处理
					sql += "AND tb.solvestatus=0";
					countSql += "AND tb.solvestatus=0";
				} else if ("2".equals(temp)) {// 待验证
					sql += "AND tb.solvestatus=1";
					countSql += "AND tb.solvestatus=1";
				} else if ("3".equals(temp)) {// 已验证
					sql += "AND tb.solvestatus=2";
					countSql += "AND tb.solvestatus=2";
				} else if ("4".equals(temp)) {// 已删除
					sql += "AND tb.solvestatus=3";
					countSql += "AND tb.solvestatus=3";
				} else if ("5".equals(temp)) {// 由我创建
					sql += "AND tb.creater_id=" + memberId;
					countSql += "AND tb.creater_id=" + memberId;
				} else if ("6".equals(temp)) {// 待我处理
					sql += "AND tb.solvestatus=0 AND tb.developer_id=" + memberId;
					countSql += "AND tb.solvestatus=0 AND tb.developer_id=" + memberId;
				} else if ("7".equals(temp)) {// 由我处理
					sql += "AND tb.solvestatus>0 AND tb.solver_id=" + memberId;
					countSql += "AND tb.solvestatus>0 AND tb.solver_id=" + memberId;
				} else if ("8".equals(temp)) {// 待我验证
					sql += "AND tb.solvestatus=1 AND tb.creater_id=" + memberId;
					countSql += "AND tb.solvestatus=1 AND tb.creater_id=" + memberId;
				}
			}
		}
		// 字段倒叙或升序排列 {search=, type=0, orderColumn=id, orderByValue=DESC}
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
	 * 添加Bug时候需求选择
	 * @return
	 */
	public List<Map<String, Object>> getNeed(){
		String sql = "SELECT id, need_name FROM `task_need` WHERE state>0;";
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 添加测试用例
	 */
	public boolean add(Map<String, String> mvm) {
		TestCase tc = new TestCase();
		tc.setTaskId(Integer.valueOf(mvm.get("task_id")));
		tc.setCaseName(mvm.get("case_name"));
		tc.setCreateId(super.getMemberId());
		tc.setCreateName(super.getMemberName());
		tc.setUpdateId(super.getMemberId());
		tc.setUpdateName(super.getMemberName());
		tc.setCaseType(Integer.valueOf(mvm.get("case_type")));
		tc.setPrecondition(mvm.get("condition"));
		tc.setVersion(1);
		tc.setRemark(mvm.get("remark"));
		tc.setCreatetime(new Timestamp(System.currentTimeMillis()));
		tc.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		tc.setState(1);
		boolean flag = super.saveUpdateOrDelete(tc, null);
		if(flag) {
			List<TestCaseStep> list = new ArrayList<TestCaseStep>();
			int i = 1;
			while(true) {
				if(!GiantUtil.isEmpty(mvm.get("steps[" + i + "]"))) {
					TestCaseStep tcs = new TestCaseStep();
					tcs.setCaseId(tc.getId());
					tcs.setStep(mvm.get("steps[" + i + "]"));
					tcs.setExpect(mvm.get("expects[" + i + "]"));
					tcs.setVersion((short) 1);
					list.add(tcs);
				} else {
					break;
				}
				i++;
			}
			super.saveUpdateOrDelete(list, null);
		}
		return flag;
	}

	/**
	 * 获取当前用户下可以提BUG的任务列表
	 * <pre>
	 * 条件：
	 * 1.  测试单状态的:2测试中/3已测试
	 * </pre>
	 * 
	 * @param id 当前测试申请单的ID
	 * @return
	 */
	public List<Map<String, Object>> getFinishedTasksByMember(){
		String sql = "select t.id, t.task_name from task t where t.state != 1 and t.deleted = 0 order by t.id desc ";
		return super.getMapListBySQL(sql, null);
	}
	/**
	 * 修改Bug信息
	 */
	public boolean edit(Map<String, String> mvm) {
		TestBug t = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			t = (TestBug) super.dao.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			t.setTaskid(Integer.valueOf(mvm.get("taskid")));
			t.setTasktype(mvm.get("tasktype"));
			t.setBugproject(mvm.get("bugproject"));
			t.setBugrank(Integer.valueOf(mvm.get("bugrank")));
			t.setBugfen(Integer.valueOf(mvm.get("bugfen")));
			t.setBugtype(Integer.valueOf(mvm.get("bugtype")));
			t.setBugdes(mvm.get("bugdes"));
			Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("creater_id"), 0));
			t.setCreaterId(member == null ? 0 : member.getId());
			t.setCreater(member == null ? "" : member.getName());
			member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("developer_id"), 0));
			t.setDeveloperId(member == null ? 0 : member.getId());
			t.setDeveloper(member == null ? "" : member.getName());
			t.setSolvestatus(0);
			t.setMark(mvm.get("mark"));
			t.setCreatetime(new Timestamp(System.currentTimeMillis()));
			return super.dao.saveUpdateOrDelete(t, null);
		}
		return false;
	}

	/**
	 * BUG指派给
	 */
	public boolean assign(Map<String, String> mvm) {
		TestBug t = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			t = (TestBug) super.dao.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("solver_id"), 0));
			t.setSolverId(member == null ? 0 : member.getId());
			t.setSolver(member == null ? "" : member.getName());
			return super.dao.saveUpdateOrDelete(t, null);
		}
		return false;
	}

	/**
	 * 解决BUG
	 */
	public boolean solve(Map<String, String> mvm) {
		TestBug t = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			t = (TestBug) super.dao.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("solver_id"), 0));
			t.setSolverId(member == null ? 0 : member.getId());
			t.setSolver(member == null ? "" : member.getName());
			t.setSolution(Integer.valueOf(mvm.get("solution")));
			t.setSolvestatus(1);
			t.setKaifamark(mvm.get("kaifamark"));
			t.setSolvetime(new Timestamp(System.currentTimeMillis()));
			return super.dao.saveUpdateOrDelete(t, null);
		}
		return false;
	}

	/**
	 * 验证BUG
	 */
	public boolean vali(Map<String, String> mvm) {
		TestBug t = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			t = (TestBug) super.dao.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			t.setSolvestatus(2);
			t.setValidatetime(new Timestamp(System.currentTimeMillis()));
			return super.dao.saveUpdateOrDelete(t, null);
		}
		return false;
	}

}
