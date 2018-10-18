package com.zzidc.declaration.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantPager;
import com.giant.zzidc.base.utils.GiantUtil;
import com.giant.zzidc.base.utils.GiantUtils;
import com.zzidc.team.entity.DeclarationProjectResult;
import com.zzidc.team.entity.Member;;

/**
 * [说明/描述]
 */
@Service("projectResultService")
public class ProjectResultService extends GiantBaseService{
	
	public GiantPager getPageList(GiantPager conditionPage) {
		if (conditionPage == null) {
			conditionPage = new GiantPager();
		}
		conditionPage = this.filterStr(conditionPage);
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		String sql = "SELECT pr.*,p.project_name,p.start_date,p.end_date FROM declaration_project_result pr "
				+ "LEFT JOIN declaration_project p ON pr.project_id=p.id where pr.id>0 ";
		String countSql = "SELECT count(0) FROM declaration_project_result pr "
				+ "LEFT JOIN declaration_project p ON pr.project_id=p.id where pr.id>0 ";
		if (conditionPage.getQueryCondition() != null) {
			String temp = "";
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("search"))) {
				temp = temp.trim();
				String nameType = "";
				if (!StringUtils.isEmpty(nameType = conditionPage.getQueryCondition().get("nametype"))) {
					if ("1".equals(nameType)) {//成果名称
						sql += "AND (pr.project_result_name LIKE :search OR pr.member_name LIKE :search)";
						countSql += "AND (pr.project_result_name LIKE :search OR pr.member_name LIKE :search)";
						conditionMap.put("search", "%" + temp + "%");
					} else if("2".equals(nameType)) {//申请/专利/登记号
						sql += "AND pr.registration_number=:search ";
						countSql += "AND pr.registration_number=:search ";
						conditionMap.put("search", temp);
					} else if("3".equals(nameType)) {//证书号
						sql += "AND pr.cert_number=:search ";
						countSql += "AND pr.cert_number=:search ";
						conditionMap.put("search", temp);
					}
				}
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("company"))) {
				sql += "AND pr.company=:company ";
				countSql += "AND pr.company=:company ";
				conditionMap.put("company", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("result_type"))) {
				sql += "AND pr.type=:type ";
				countSql += "AND pr.type=:type ";
				conditionMap.put("type", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("state"))) {
				sql += "AND pr.state=:state ";
				countSql += "AND pr.state=:state ";
				conditionMap.put("state", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("payment"))) {
				sql += "AND pr.payment=:payment ";
				countSql += "AND pr.payment=:payment ";
				conditionMap.put("payment", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("invoice"))) {
				sql += "AND pr.invoice=:invoice ";
				countSql += "AND pr.invoice=:invoice ";
				conditionMap.put("invoice", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("receipt"))) {
				sql += "AND pr.receipt=:receipt ";
				countSql += "AND pr.receipt=:receipt ";
				conditionMap.put("receipt", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("is_all_doc"))) {
				sql += "AND pr.is_all_doc=:is_all_doc ";
				countSql += "AND pr.is_all_doc=:is_all_doc ";
				conditionMap.put("is_all_doc", temp);
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("start_date"))) {
				String dateType = "";
				if (!StringUtils.isEmpty(dateType = conditionPage.getQueryCondition().get("datetype"))) {
					if ("1".equals(dateType)) {//申请日期
						sql += "AND pr.apply_date>:start_date ";
						countSql += "AND pr.apply_date>:start_date ";
					} else if("2".equals(dateType)) {//受理日期
						sql += "AND pr.accept_date>:start_date ";
						countSql += "AND pr.accept_date>:start_date ";
					} else if("3".equals(dateType)) {//下证日期
						sql += "AND pr.down_date>:start_date ";
						countSql += "AND pr.down_date>:start_date ";
					}
					conditionMap.put("start_date", temp);
				}
				
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("end_date"))) {
				String dateType = "";
				if (!StringUtils.isEmpty(dateType = conditionPage.getQueryCondition().get("datetype"))) {
					if ("1".equals(dateType)) {//申请日期
						sql += "AND pr.apply_date<:end_date ";
						countSql += "AND pr.apply_date<:end_date ";
					} else if("2".equals(dateType)) {//受理日期
						sql += "AND pr.accept_date<:end_date ";
						countSql += "AND pr.accept_date<:end_date ";
					} else if("3".equals(dateType)) {//下证日期
						sql += "AND pr.down_date<:end_date ";
						countSql += "AND pr.down_date<:end_date ";
					}
					conditionMap.put("end_date", temp);
				}
				
			}
			if (!StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("type"))) {
				if ("0".equals(temp)) {//已删除
					sql += "AND pr.state=0";
					countSql += "AND pr.state=0";
				} else if ("1".equals(temp)) {//正常
					sql += "AND pr.state IN (1,2,3,4,5)";
					countSql += "AND pr.state IN (1,2,3,4,5)";
				} else if ("11".equals(temp)) {//待撰写
					sql += "AND pr.state=1";
					countSql += "AND pr.state=1";
				} else if ("12".equals(temp)) {//撰写中
					sql += "AND pr.state=2";
					countSql += "AND pr.state=2";
				} else if ("13".equals(temp)) {//已撰写
					sql += "AND pr.state=3";
					countSql += "AND pr.state=3";
				} else if ("14".equals(temp)) {//已提综管
					sql += "AND pr.state=4";
					countSql += "AND pr.state=4";
				} else if ("15".equals(temp)) {//已提代理
					sql += "AND pr.state=5";
					countSql += "AND pr.state=5";
				} else if ("16".equals(temp)) {//代理受理
					sql += "AND pr.state=6";
					countSql += "AND pr.state=6";
				} else if ("17".equals(temp)) {//代理完成
					sql += "AND pr.state=7";
					countSql += "AND pr.state=7";
				} else if ("18".equals(temp)) {//下通知书
					sql += "AND pr.state=8";
					countSql += "AND pr.state=8";
				} else if ("19".equals(temp)) {//已下证
					sql += "AND pr.state=9";
					countSql += "AND pr.state=9";
				} else if ("8".equals(temp)) {//软著
					sql += "AND pr.state>0 AND pr.type=1";
					countSql += "AND pr.state>0 AND pr.type=1";
				} else if ("9".equals(temp)) {//专利
					sql += "AND pr.state>0 AND pr.type IN (2,3,4)";
					countSql += "AND pr.state>0 AND pr.type IN (2,3,4)";
				} else {
					if (StringUtils.isEmpty(temp = conditionPage.getQueryCondition().get("state"))) {
						sql += "AND pr.state>0 ";
						countSql += "AND pr.state>0 ";
					}
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

	public List<Map<String, Object>> getDeclarationProject(){
		String sql = "select * from declaration_project where state>0";
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 添加、修改成果信息
	 */
	public boolean addOrUpd(Map<String, String> mvm) {
		DeclarationProjectResult pr = null;
		if(GiantUtil.intOf(mvm.get("result_id"), 0) != 0){
			//获取成果对象
			pr = (DeclarationProjectResult) super.dao.getEntityByPrimaryKey(new DeclarationProjectResult(), GiantUtil.intOf(mvm.get("result_id"), 0));
		} else {
			pr = new DeclarationProjectResult();
			pr.setCreateTime(new Timestamp(System.currentTimeMillis()));
			pr.setState((short) 1);
		}
		pr.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		pr.setCertNumber(GiantUtil.stringOf(mvm.get("cert_number")));
		pr.setRegistrationNumber(GiantUtil.stringOf(mvm.get("registration_number")));
		pr.setProjectResultName(GiantUtil.stringOf(mvm.get("project_result_name")));
		pr.setProjectId(GiantUtil.intOf(mvm.get("project_id"), 0));
		pr.setType((short)GiantUtil.intOf(mvm.get("type"), 0));
		Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), GiantUtil.intOf(mvm.get("member_id"), 0));
		pr.setMemberId(member == null ? 0 : member.getId());
		pr.setMemberName(member == null ? "" : member.getName());
		try {
			pr.setApplyDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("apply_date")));	
		} catch (ParseException e) {
		}
		try {
			pr.setAcceptDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("accept_date")));
		} catch (ParseException e) {
		}
		try {
			pr.setDownDate(new SimpleDateFormat("yyyy-MM-dd").parse(mvm.get("down_date")));
		} catch (ParseException e) {
		}
		pr.setCompany(GiantUtil.stringOf(mvm.get("company")));
		pr.setAgent(GiantUtil.stringOf(mvm.get("agent")));
		pr.setVersion(GiantUtil.stringOf(mvm.get("version")));
		pr.setInventor(GiantUtil.stringOf(mvm.get("inventor")));
		pr.setRemark(GiantUtil.stringOf(mvm.get("remark")));
		pr.setState((short)GiantUtil.intOf(mvm.get("state"), 0));
		pr.setPayment(GiantUtil.intOf(mvm.get("payment"), 0));
		pr.setInvoice(GiantUtil.intOf(mvm.get("invoice"), 0));
		pr.setReceipt(GiantUtil.intOf(mvm.get("receipt"), 0));
		pr.setIsAllDoc(GiantUtil.intOf(mvm.get("is_all_doc"), 0));
		return super.dao.saveUpdateOrDelete(pr, null);
	}

	/**
	 * 获取成果信息
	 * @param resultId
	 * @return
	 */
	public List<Map<String, Object>> getProjectResult(int resultId){
		String sql = "SELECT pr.*,p.project_name FROM declaration_project_result pr "
				+ "LEFT JOIN declaration_project p ON pr.project_id=p.id WHERE pr.id=" + resultId;
		return super.getMapListBySQL(sql, null);
	}
	
	/**
	 * 获取成果文档
	 * @param resultId
	 * @return
	 */
	public List<Map<String, Object>> getResultDoc(int resultId){
		String sql = "SELECT pd.*,pdt.project_doc_type FROM declaration_project_doc pd, declaration_project_doc_type pdt "
				+ "WHERE pd.type_id=pdt.id AND pd.state>0 AND result_id=" + resultId;
		return super.getMapListBySQL(sql, null);
	}
}
