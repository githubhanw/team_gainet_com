<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<base href="<%=basePath%>" />
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>编辑任务：${t.taskName}</title>
    	<%@ include file="/WEB-INF/view/comm/cssjs.jsp" %>
	</head>
	<body>
	    <!--header start-->
	    <header id="header">
	    	<%@ include file="/WEB-INF/view/comm/main_header.jsp" %>
	    	<%@ include file="/WEB-INF/view/comm/sub_header.jsp" %>
	    </header>
	    <!--header end-->
		<main id="main" class="in">
			<div class="container">
				<div class="main-content" id="mainContent">
					<form method="post" enctype="multipart/form-data" target="hiddenwin"
						id="dataform">
						<div class="main-header">
							<h2>
								<span class="label label-id">${t.id}</span>
								<a href="team/task/index">${t.taskName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 编辑</small>
							</h2>
						</div>
						<div class="main-row">
							<div class="main-col col-8">
								<div class="cell">
									<div class="detail">
										<div class="detail-title">任务名称</div>
										<div class="detail-content form-group article-content required">
											<input type="text" name="task_name" id="task_name" value="${t.taskName}" class="form-control input-product-title" autocomplete="off">
										</div>
									</div>
									<div class="detail">
										<div class="detail-title">任务描述</div>
										<div class="detail-content form-group article-content">
											<div id="remark" name="remark">${t.remark }</div>
										</div>
									</div>
									<div class="detail">
										<div class="detail-title">备注</div>
										<div class="detail-content form-group article-content">
											<div id="comment" name="comment"></div>
										</div>
									</div>
									<div class="actions form-actions text-center">
										<input type="hidden" name="id" value="${t.id}"/>
										<button type="button" id="submit"
											class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
										<button type="button" class="btn btn-wide"
											onclick="javascript:history.go(-1);">返回</button>
									</div>
									<hr class="small">
									<div class="detail histories" id="actionbox">
										<div class="detail-title">
											历史记录 &nbsp;
											<button type="button"
												class="btn btn-mini btn-icon btn-expand-all" title="切换显示">
												<i class="icon icon-plus icon-sm"></i>
											</button>
										</div>
			                            <div class="detail-content">
			                                <ol class="histories-list">
												<c:forEach items="${logList}" var="log" varStatus="sta">
													<li value="${sta.index+1}" class="">
														<fmt:formatDate value="${log.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/>,
														由 <strong>${log.member_name}</strong> ${log.method}。
														<c:if test="${log.history != null}">
															<button type="button" class="btn btn-mini switch-btn btn-icon btn-expand" title="切换显示"><i class="change-show icon icon-plus icon-sm"></i></button>
															<div class="history-changes" id="changeBox3">
																<c:forEach items="${log.history}" var="his" varStatus="sta">
																	<c:if test="${his.diff == 0}">
																		修改了 <strong><i>${his.field_desc}</i></strong>，旧值为 "${his.old_data}"，新值为 "${his.new_data}"。<br>
																	</c:if>
																	<c:if test="${his.diff == 1}">
																		修改了 <strong><i>${his.field_desc}</i></strong>，区别为：
																		<blockquote class="textdiff">
																			- <del>${his.old_data}</del><br/>+ <ins>${his.new_data}</ins>
																		</blockquote>
																	</c:if>
																</c:forEach>
															</div>
														</c:if>
														<c:if test="${log.comment != null && log.comment != ''}">
															<div class="article-content comment">${log.comment}</div>
														</c:if>
													</li>
												</c:forEach>
			                                </ol>
			                            </div>
									</div>
								</div>
							</div>
							<div class="side-col col-4">
								<div class="cell">
									<div class="detail">
										<div class="detail-title">基本信息</div>
										<table class="table table-form">
											<tbody>
												<tr>
													<th class="w-80px">所属需求</th>
													<td>
														<div class="input-group">
															<select class="form-control chosen chosen-select"  name="need_id" id="need_id">
																<c:forEach items="${need}" var="n" varStatus="sta">
																<option value="${n.id}" ${n.id==t.needId?'selected="selected"':''}>${n.need_name }</option>
																</c:forEach>
															</select>
														</div>
													</td>
												</tr>
												<tr>
													<th>指派给</th>
													<td>
														<select data-placeholder="指派给" class="form-control chosen-select" name="assigned_id" id="assigned_id">
															<option value=""></option>
															<c:forEach items="${members}" var="member" varStatus="sta">
																<option value="${member.id}" ${member.id==t.assignedId?'selected="selected"':''}>${member.name}(${member.number})</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<th>任务类型</th>
													<td class="required">
														<select class="form-control chosen chosen-select" name="task_type" id="task_type">
															<option ${t.taskType=='1'?'selected="selected"':'' } value="1">开发</option>
															<option ${t.taskType=='2'?'selected="selected"':'' } value="2">测试</option>
															<option ${t.taskType=='3'?'selected="selected"':'' } value="3">设计</option>
															<option ${t.taskType=='4'?'selected="selected"':'' } value="4">前端</option>
															<option ${t.taskType=='5'?'selected="selected"':'' } value="5">维护</option>
															<option ${t.taskType=='6'?'selected="selected"':'' } value="6">需求</option>
															<option ${t.taskType=='7'?'selected="selected"':'' } value="7">研究</option>
															<option ${t.taskType=='8'?'selected="selected"':'' } value="8">讨论</option>
															<option ${t.taskType=='9'?'selected="selected"':'' } value="9">运维</option>
															<option ${t.taskType=='10'?'selected="selected"':'' } value="10">事务</option>
															<option ${t.taskType=='0'?'selected="selected"':'' } value="0">其他</option>
														</select>
													</td>
												</tr>
												<tr>
													<th>任务状态</th>
													<td>
														<select class="form-control chosen chosen-select" name="state" id="state">
															<option ${t.state=='1'?'selected="selected"':'' } value="1">待接收</option>
															<option ${t.state=='2'?'selected="selected"':'' } value="2">进行中</option>
															<option ${t.state=='3'?'selected="selected"':'' } value="3">审核中</option>
															<option ${t.state=='4'?'selected="selected"':'' } value="4">已完成</option>
															<option ${t.state=='6'?'selected="selected"':'' } value="6">已取消</option>
															<option ${t.state=='7'?'selected="selected"':'' } value="7">已关闭</option>
														</select>
													</td>
												</tr>
												<tr>
													<th>删除状态</th>
													<td>
														<select class="form-control chosen chosen-select" name="deleted" id="deleted">
															<option ${t.deleted=='0'?'selected="selected"':'' } value="0">未删除</option>
															<option ${t.deleted=='1'?'selected="selected"':'' } value="1">已删除</option>
														</select>
													</td>
												</tr>
												<tr>
													<th>优先级</th>
													<td>
														<select class="form-control chosen chosen-select"  name="level" id="level">
															<option ${t.level=='1'?'selected="selected"':'' } value="1">紧急重要</option>
															<option ${t.level=='2'?'selected="selected"':'' } value="2">紧急不重要</option>
															<option ${t.level=='3'?'selected="selected"':'' } value="3">不紧急重要</option>
															<option ${t.level=='4'?'selected="selected"':'' } value="4">不紧急不重要</option>
														</select>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
									<div class="detail">
										<div class="detail-title">任务时间</div>
										<table class="table table-form">
											<tbody>
												<tr>
													<th class="w-80px">开始日期</th>
													<td>
														<input type="text" name="start_date" id="start_date" value="<fmt:formatDate value="${t.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
															class="form-control form-date-limit" placeholder="任务开始日期" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
													</td>
												</tr>
												<tr>
													<th>结束日期</th>
													<td>
														<input type="text" name="end_date" id="end_date" value="<fmt:formatDate value="${t.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
															class="form-control form-date" placeholder="任务结束日期" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
													</td>
												</tr>
												<tr>
													<th>是否延期</th>
													<td>
														<select class="form-control chosen chosen-select"  name="delay" id="delay">
															<option ${t.delay=='0'?'selected="selected"':'' } value="0">未延期</option>
															<option ${t.delay=='1'?'selected="selected"':'' } value="1">已延期</option>
														</select>
													</td>
												</tr>
												<tr>
													<th>是否逾期</th>
													<td>
														<select class="form-control chosen chosen-select"  name="overdue" id="overdue">
															<option ${t.overdue=='0'?'selected="selected"':'' } value="0">未逾期</option>
															<option ${t.overdue=='1'?'selected="selected"':'' } value="1">已逾期</option>
														</select>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
									<div class="detail">
										<div class="detail-title">任务的一生</div>
										<table class="table table-form">
											<tbody>
												<tr>
													<th class="w-80px">由谁创建</th>
													<td>${t.memberName}</td>
												</tr>
												<tr>
													<th>由谁完成</th>
													<td>
														同指派给
														<%-- <select data-placeholder="指派给" class="form-control chosen-select" name="assigned_id" id="assigned_id">
															<option value=""></option>
															<c:forEach items="${members}" var="member" varStatus="sta">
																<option value="${member.id}" ${member.id==t.assignedId?'selected="selected"':''}>${member.name}(${member.number})</option>
															</c:forEach>
														</select> --%>
													</td>
												</tr>
												<tr>
													<th>完成时间</th>
													<td>
														<input type="text" name="real_end_date" id="real_end_date" value="<fmt:formatDate value="${t.realEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
															class="form-control form-date" placeholder="完成时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
													</td>
												</tr>
												<tr>
													<th>由谁验收</th>
													<td>
														<select data-placeholder="请选择验收人" class="form-control chosen-select" name="checked_id" id="checked_id">
															<option value=""></option>
															<c:forEach items="${members}" var="member" varStatus="sta">
																<option value="${member.id}" ${member.id==t.checkedId?'selected="selected"':''}>${member.name}(${member.number})</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<th>由谁关闭</th>
													<td>
														<select data-placeholder="请选择关闭人" class="form-control chosen-select" name="closed_id" id="closed_id">
															<option value=""></option>
															<c:forEach items="${members}" var="member" varStatus="sta">
																<option value="${member.id}" ${member.id==t.closedId?'selected="selected"':''}>${member.name}(${member.number})</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<th>关闭原因</th>
													<td>
														<select class="form-control chosen chosen-select"  name="closed_reason" id="closed_reason">
															<option value=""></option>
															<option ${t.closedReason=='已完成'?'selected="selected"':'' } value="已完成">已完成</option>
															<option ${t.closedReason=='已取消'?'selected="selected"':'' } value="已取消">已取消</option>
														</select>
													</td>
												</tr>
												<tr>
													<th>关闭时间</th>
													<td>
														<input type="text" name="closed_time" id="closed_time" value="<fmt:formatDate value="${t.closedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
															class="form-control form-date" placeholder="关闭时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</main>
		<div class="modal fade" id="myModal">
			<div class="modal-dialog" style="width:600px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">提示结果</h4>
					</div>
					<div class="modal-body">
						<div style="margin:0 auto">
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="javascript:history.go(0);" class="btn">继续编辑任务</a> <a
									href="team/task/toAdd" class="btn">建任务</a> <a
									href="team/task/toAdd" class="btn">批量建任务</a> <a
									href="team/task/index" class="btn">返回任务列表</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="errModal">
			<div class="modal-dialog" style="width:300px">
				<div class="modal-content">
					<div class="modal-body">
						<div style="margin:0 auto;">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">关闭</span>
							</button>
							<p>
								<span id="errMsg"></span>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
UMEditor("remark");
UMEditor("comment");
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$.ajax({type:"POST",url:"team/task/edit?r=" + Math.random(),data:$("form").serialize() + "&remark=" + UM.getEditor('remark').getContent() + "&comment=" + UM.getEditor('comment').getContent(),dataType:"json",success:function(data){
		if(data.code == 0){
			$("#msg").text(data.message);
			$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
		}else{
			$("#errMsg").text(data.message);
			$('#errModal').modal({keyboard: false,show: true, moveable: true});
		}
	}})
	$.ajaxSettings.async = true;
});
</script>