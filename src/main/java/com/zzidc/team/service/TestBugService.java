package com.zzidc.team.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.giant.zzidc.base.utils.HttpUtils;
import com.zzidc.log.LogMethod;
import com.zzidc.log.LogModule;
import com.zzidc.log.PMLog;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.Task;
import com.zzidc.team.entity.TestBug;

import net.sf.json.JSONObject;

/**
 *  Bug模块
 * @author Qyb
 *
 */
@Service("testBugService")
public class TestBugService extends GiantBaseService {
	
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
		String sql = "select tb.*,t.task_name "
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
	 * 添加Bug时候模块选择
	 * @return
	 */
	public List<Map<String, Object>> getNeed(){
		String sql = "SELECT id, need_name FROM `task_need` WHERE state>0;";
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 添加Bug
	 */
	public boolean add(Map<String, String> mvm) {
		TestBug t = new TestBug();
		Task task = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("taskid"), 0));
		t.setTaskid(Integer.valueOf(mvm.get("taskid")));
		t.setTasktype(task.getTaskType().toString());
		t.setProjectId(task.getProjectId());
		t.setNeedId(task.getNeedId());
		t.setBugproject(mvm.get("bugproject"));
		t.setBugrank(Integer.valueOf(mvm.get("bugrank")));
		t.setBugfen(Integer.valueOf(mvm.get("bugfen")));
		t.setBugtype(Integer.valueOf(mvm.get("bugtype")));
		t.setBugdes(mvm.get("bugdes"));
		Member creater = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("creater_id"), 0));
		t.setCreaterId(creater == null ? 0 : creater.getId());
		t.setCreater(creater == null ? "" : creater.getName());
		Member developer = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("developer_id"), 0));
		t.setDeveloperId(developer == null ? 0 : developer.getId());
		t.setDeveloper(developer == null ? "" : developer.getName());
		t.setSolvestatus(0);
		t.setMark(mvm.get("mark"));
		t.setCreatetime(new Timestamp(System.currentTimeMillis()));
		boolean b =  super.dao.saveUpdateOrDelete(t, null);
		PMLog pmLog = new PMLog(LogModule.BUG, LogMethod.ADD, t.getId(), t.toString(), null);
		if(b) {
			this.log(pmLog);
			//微信提醒
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd E HH:mm");
			Date date = new Date();
			String time = form.format(date);
			if (GiantUtils.isEmpty(developer) || GiantUtils.isEmpty(developer.getNewOpenid())) {
				
			}else {
				String bugrank = "0".equals(t.getBugrank().toString()) ? "A" : "1".equals(t.getBugrank().toString()) ? "B" : "2".equals(t.getBugrank().toString()) ? "C" :"" ;
				String bugfen = "0".equals(t.getBugrank().toString()) ? "正常" : "1".equals(t.getBugrank().toString()) ? "线上BUG" : "2".equals(t.getBugrank().toString()) ? "线下BUG" :"" ;
				String bugtype = "0".equals(t.getBugrank().toString()) ? "功能类" :
								 "1".equals(t.getBugrank().toString()) ? "安全类" : 
								 "2".equals(t.getBugrank().toString()) ? "界面类" : 
								 "3".equals(t.getBugrank().toString()) ? "信息类" : 
								 "4".equals(t.getBugrank().toString()) ? "数据类" : 
								 "5".equals(t.getBugrank().toString()) ? "流程类" : 
								 "6".equals(t.getBugrank().toString()) ? "模块问题" :"" ;
				
				String openid = developer.getNewOpenid();//"o-GQDj8vVvfH2715yROC1aqY4YM0";
				String first = "你好,收到一个【BUG处理】提醒";
				String keyword1 = "处理BUG";
				String keyword2 = this.getMemberName();
				String keyword3 = time;
				String remark = "总任务标题："+task.getTaskName()+
						"\\n总任务领取ID："+task.getId()+
						"\\nBUG创建人："+t.getCreater()+
						"\\nBUG等级："+bugrank+
						"\\nBUG分类："+bugfen+
						"\\nBUG类型："+bugtype;//自定义通知，以换行符隔开 \n
				String str=sendWeChatUtil(openid,first,keyword1,keyword2,keyword3,remark);
				String a1 = JSONObject.fromObject(str).toString();
				HttpUtils.weiXinSendPost(a1);
			}
			
			//调用OA待办接口
			//测试人员提bug后通知开发者
			String Title = "BUG待解决";
			OAToDo(Title, t.getDeveloperId(), t.getCreaterId(), t.getBugdes());
		}
		return b;
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
		if ("2".equals(mvm.get("solution")) && !GiantUtil.isEmpty(mvm.get("check_id"))) {
			if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
				//获取对象 
				t = (TestBug) super.dao.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
				Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("solver_id"), 0));
				t.setSolverId(member == null ? 0 : member.getId());
				t.setSolver(member == null ? "" : member.getName());
				t.setSolution(Integer.valueOf(mvm.get("solution")));
				t.setSolvestatus(4);
				t.setKaifamark(mvm.get("kaifamark"));
				t.setSolvetime(new Timestamp(System.currentTimeMillis()));
				Member check = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("check_id"), 0));
				t.setCheckId(check == null ? 0 : check.getId());
				t.setCheckName(check == null ? "" : check.getName());
				boolean flag = super.dao.saveUpdateOrDelete(t, null);
				if (flag) {
					//调用OA待办接口
					//不是问题的bug通知审核人去审核
					String Title = "BUG待审核";
					OAToDo(Title, t.getCheckId(), t.getDeveloperId(), t.getBugdes());
				}
				return flag;
			}
			return false;
		}else {
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
				boolean flag = super.dao.saveUpdateOrDelete(t, null);
				if (flag) {
					//调用OA待办接口
					//修复bug后通知测试人员去验证
					String Title = "BUG待验证";
					OAToDo(Title, t.getCreaterId(), t.getDeveloperId(), t.getBugdes());
				}
				return flag;
			}
			return false;
		}
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

	/**
	 * 审核不是问题的BUG
	 */
	public boolean checkbug(Map<String, String> mvm) {
		TestBug t = null;
		if(GiantUtil.intOf(mvm.get("id"), 0) != 0){
			//获取对象
			t = (TestBug) super.dao.getEntityByPrimaryKey(new TestBug(), GiantUtil.intOf(mvm.get("id"), 0));
			t.setSolution(Integer.parseInt(mvm.get("solution")));
			t.setSolvestatus(1);
			t.setCheckmark(mvm.get("checkmark"));
			return super.dao.saveUpdateOrDelete(t, null);
		}
		return false;
	}

}
