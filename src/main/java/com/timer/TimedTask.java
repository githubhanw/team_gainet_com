package com.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.giant.zzidc.base.service.GiantBaseService;
import com.giant.zzidc.base.utils.GiantUtils;
import com.giant.zzidc.base.utils.HttpUtils;
import com.zzidc.team.entity.Member;
import com.zzidc.team.entity.Task;

import net.sf.json.JSONObject;

/**
 * 任务逾期提醒
 * 待接收而未接收；进行中而未完成；待审核而未审核
 * 
 * @author hanw
 *
 */
@Component
public class TimedTask extends GiantBaseService {
 
	@Scheduled(cron = "0 00 10 ? * *") // 每天17:50执行一次
	@Scheduled(cron = "0 03 10 ? * *") // 每天18:20执行一次
	public void overdueWarn() {
		
		//待接收任务在初始结束时间的当天提醒
		//进行中任务在计划结束时间的当天提醒
		//待审核任务在计划结束时间的三天后提醒
		String querySql = "select id from ( "
						+ "select assigned_id id from task where DATE_FORMAT(end_date, '%Y-%m-%d') = CURDATE() and state=1 and overdue=0 "
						+ "union all "
						+ "select assigned_id id from task where DATE_FORMAT(plan_end_date, '%Y-%m-%d') = CURDATE() and state=2 and overdue=0 "
						+ "union all "
						+ "select checked_id id from task where DATE_ADD(DATE_FORMAT(plan_end_date, '%Y-%m-%d'), INTERVAL 3 DAY) = CURDATE() and state=3 and overdue=0 "
						+ ") late_remind group by id";
		
		List<Map<String, Object>> list = super.dao.getListMapBySql(querySql, null);
		if (list != null && list.size() > 0) { 
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				Member member = (Member) super.dao.getEntityByPrimaryKey(new Member(), map.get("id"));
				SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd E HH:mm");
				Date date = new Date();
				String time = form.format(date);
				
				//微信推送消息
				if (!GiantUtils.isEmpty(member) && !GiantUtils.isEmpty(member.getNewOpenid())) {
					String openid = member.getNewOpenid();
					String first = "您好！您收到一个【任务即将逾期】提醒";
					String keyword1 = "任务即将逾期";
					String keyword2 = member.getName();
					String keyword3 = time;
					String remark = "";
					String str = super.sendWeChatUtil(openid, first, keyword1, keyword2, keyword3, remark);
					String a1 = JSONObject.fromObject(str).toString();
					HttpUtils.weiXinSendPost(a1);//推送
				}
			}
		}
	}

}
