package com.zzidc.team.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantUtil;
import com.zzidc.team.entity.CodeReport;
import com.zzidc.team.entity.Task;
/**
 * 代码审查
 * @author chenmenghao
 *
 */
@Service("codeReportService")
public class CodeReportService extends GiantBaseService{
	
	/**
	 * 创建代码审查
	 */
	public boolean add(Map<String, String> mvm,int id) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = sdf.format(date);
		CodeReport cr =new CodeReport();
		cr.setTaskId(Integer.parseInt(mvm.get("task_id")));
		cr.setReportInterface(mvm.get("report_interface"));
		cr.setEntryPoint(mvm.get("entry_point"));
		cr.setOnlineUrl(mvm.get("online_url"));
		cr.setSourceFile(mvm.get("source_file"));
		cr.setReportType(mvm.get("report_type"));
		cr.setExamination("0");
		cr.setAuthorId(id);
		cr.setAuthorTime(createTime);
		cr.setReportRemark(mvm.get("report_remark"));
		boolean b =  super.dao.saveUpdateOrDelete(cr, null);
		return b;
	}
	/**
	 * 根据主键id获取任务表的该条数据
	 */
	public Task getTask(Map<String, String> mvm) {
		//获取对象
		Task f = (Task) super.dao.getEntityByPrimaryKey(new Task(), GiantUtil.intOf(mvm.get("id"), 0));
		return f;
	}

}
