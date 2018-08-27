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
		<title>需求：${needM.need_name}</title>
    	<%@ include file="/WEB-INF/view/comm/cssjs.jsp" %>
	</head>
	<body>
	    <!--header start-->
	    <header id="header">
	    	<%@ include file="/WEB-INF/view/comm/main_header.jsp" %>
	    	<%@ include file="/WEB-INF/view/comm/sub_header.jsp" %>
	    </header>
	    <!--header end-->
		<main id="main">
			<div class="container">
				<div id="mainMenu" class="clearfix">
					<div class="btn-toolbar pull-left">
						<a href="${u}" class="btn btn-link"><i class="icon icon-back icon-sm"></i> 返回</a>
						<div class="divider"></div>
						<div class="page-title">
							<span class="label label-id">${needM.id}</span> <span class="text" title="${needM.need_name}">${needM.need_name}</span>
							<c:if test="${needM.full == 0}">
								<span class="label label-warning" title="不能创建任务，不能分解">不完整需求</span>
							</c:if>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">需求描述</div>
								<div class="detail-content article-content">
									<c:if test="${needM.need_remark != null && needM.need_remark != ''}">
										${needM.need_remark}
									</c:if>
									<c:if test="${needM.need_remark == null || needM.need_remark == ''}">
										<div class="text-center text-muted">暂无</div>
									</c:if>
								</div>
							</div>
							<div class="detail">
								<div class="detail-title">验收标准</div>
								<div class="detail-content article-content">
									<c:if test="${needM.check_remark != null && needM.check_remark != ''}">
										${needM.check_remark}
									</c:if>
									<c:if test="${needM.check_remark == null || needM.check_remark == ''}">
										<div class="text-center text-muted">暂无</div>
									</c:if>
								</div>
							</div>
							<c:if test="${subNeed != null}">
								<div class="detail">
									<div class="detail-title">子需求</div>
									<div class="detail-content article-content">
										<table class="table table-hover table-fixed">
											<thead>
												<tr class="text-center">
													<th>ID</th>
													<th class="w-300px">需求名称</th>
													<th>优先级</th>
													<th class="w-100px">结束时间</th>
													<th>指派给</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${subNeed}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="team/need/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.need_name}">${item.need_name}</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.member_name}</td>
													<td>${item.state == 1 ? '激活' : item.state == 2 ? '已变更' : item.state == 3 ? '已关闭' : item.state == 0 ? '已删除' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${task != null}">
								<div class="detail">
									<div class="detail-title">相关任务</div>
									<div class="detail-content article-content">
										<table class="table table-hover table-fixed">
											<thead>
												<tr class="text-center">
													<th class="w-80px">ID</th>
													<th class="w-300px">任务名称</th>
													<th>优先级</th>
													<th>结束时间</th>
													<th>指派给</th>
													<th>任务类型</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${task}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="team/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name}">${item.task_name}</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.member_name}</td>
													<td>${item.task_type==1?'开发':item.task_type==2?'测试':item.task_type==3?'设计':item.task_type==4?'前端':item.task_type==5?'维护':item.task_type==6?'需求':item.task_type==7?'研究':item.task_type==8?'讨论':item.task_type==9?'运维':item.task_type==10?'事务':'其他'}</td>
													<td>${item.state == 1 ? '待接收' : item.state == 2 ? '进行中' : item.state == 3 ? '审核中' : item.state == 4 ? '已完成' : item.state == 5 ? '已暂停' : item.state == 6 ? '已取消' : item.state == 7 ? '已关闭' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${linkNeed != null}">
								<div class="detail">
									<div class="detail-title">关联需求</div>
									<div class="detail-content article-content">
										<table class="table table-hover table-fixed">
											<thead>
												<tr class="text-center">
													<th>ID</th>
													<th class="w-300px">需求名称</th>
													<th>优先级</th>
													<th class="w-100px">结束时间</th>
													<th>指派给</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${linkNeed}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="team/need/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.need_name}">${item.need_name}</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.member_name}</td>
													<td>${item.state == 1 ? '激活' : item.state == 2 ? '已变更' : item.state == 3 ? '已关闭' : item.state == 0 ? '已删除' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${logList != null}">
	                        <div class="detail histories" id="actionbox" data-textdiff="文本格式" data-original="原始格式">
	                            <div class="detail-title">
									历史记录
	                                <button type="button" class="btn btn-mini btn-icon btn-expand-all" title="切换显示">
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
	                        </c:if>
						</div>
					</div>
					<div class="side-col col-4">
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">基本信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr class="nofixed">
												<th>所属项目</th>
												<td title="/">${needM.project_name}</td>
											</tr>
											<tr>
												<th>需求来源</th>
												<td>${needM.need_src}</td>
											</tr>
											<tr>
												<th>来源备注</th>
												<td>${needM.leader_name}</td>
											</tr>
											<tr>
												<th>需求方</th>
												<td>${needM.member_name}</td>
											</tr>
											<tr>
												<th>优先级</th>
												<td>
													<c:if test="${needM.level=='1'}">紧急重要</c:if>
													<c:if test="${needM.level=='2'}">紧急不重要</c:if>
													<c:if test="${needM.level=='3'}">不紧急重要</c:if>
													<c:if test="${needM.level=='4'}">不紧急不重要</c:if>
												</td>
											</tr>
											<tr>
												<th>当前状态</th>
												<td>
													${needM.state == 1 ? '激活' : needM.state == 2 ? '已变更' : needM.state == 3 ? '已关闭' : needM.state == 0 ? '已删除' : '未知'}
												</td>
											</tr>
											<tr>
												<th>当前阶段</th>
												<td>
													${needM.stage == 1 ? '待验收' : needM.stage == 2 ? '验收完成' : needM.stage == 3 ? '验收不通过' : '未知'}
												</td>
											</tr>
											<tr>
												<th>开始日期</th>
												<td>${needM.start_date}</td>
											</tr>
											<tr>
												<th>结束日期</th>
												<td>${needM.end_date}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">需求的一生</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>由谁创建</th>
												<td>${needM.create_name } 于 <fmt:formatDate value="${needM.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>指派给</th>
												<td>
													<c:if test="${needM.assigned_name != null && needM.assigned_name != ''}">
														${needM.assigned_name } 于 <fmt:formatDate value="${needM.assigned_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>由谁关闭</th>
												<td>
													<c:if test="${needM.closed_name != null && needM.closed_name != ''}">
														${needM.closed_name } 于 <fmt:formatDate value="${needM.closed_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>关闭原因</th>
												<td>${needM.closed_reason }</td>
											</tr>
											<tr>
												<th>由谁验收</th>
												<td>
													<c:if test="${needM.checked_name != null && needM.checked_name != ''}">
														${needM.checked_name } 于 <fmt:formatDate value="${needM.checked_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>最后修改</th>
												<td>${needM.update_time }</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
					</div>
				</div>
				<!--mainActions start-->
				<div id="mainActions">
					<nav class="container">
					</nav>
					<div class="btn-toolbar">
						<a href="${u}" id="back" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="返回"><i class="icon-goback icon-back"></i> 返回</a>
						<c:if test="${needM.state == 1 || needM.state == 2}">
							<a href="team/need/toChange?id=${needM.id}" class="btn btn-link" title="${needM.full == 0?'完善':'变更'}"><i class="icon-story-change icon-fork"></i> ${needM.full == 0?'完善':'变更'}</a>
						</c:if>
						<c:if test="${needM.state != 3}">
							<a href="team/need/toClose?id=${needM.id}" class="btn btn-link" title="关闭"><i class='icon-task-close icon-off'></i> 关闭</a>
						</c:if>
						<c:if test="${needM.state == 3}">
							<a href="team/need/toActive?id=${needM.id}" class="btn btn-link" title="激活">激活</a>
						</c:if>
						<c:if test="${needM.state == 1 || needM.state == 2}">
							<c:if test="needM.full == 1">
								<a href="team/need/toCheck?id=${needM.id}" class="btn btn-link" title="验收"><i class="icon-story-review icon-glasses"></i> 验收</a>
								<a href="team/task/toAdd?need_id=${needM.id}" class="btn btn-link" title="批量建任务"><i class="icon icon-plus"></i> 批量建任务</a>
							</c:if>
						</c:if>
						<c:if test="${needM.parent_id == null || needM.parent_id == ''}">
							<a href="team/need/toRelevance?id=${needM.id}" class="btn btn-link" title="关联"><i class='icon icon-sitemap'></i> 关联</a>
						</c:if>
						<div class="divider"></div>
						<c:if test="${needM.parent_id == null || needM.parent_id == ''}">
							<c:if test="needM.full == 1">
								<a href="team/need/toBatchAdd?id=${needM.id}" class="btn btn-link" title="分解需求"><i class='icon-task-batchCreate icon-branch'></i> 分解</a>
							</c:if>
						</c:if>
						<a href="team/need/toEdit?id=${needM.id}" class="btn btn-link" title="编辑"><i class="icon-common-edit icon-edit"></i> 编辑</a>
						<a href="javascript:void(0)" onclick="del(${needM.id})" class="btn btn-link" title="删除"><i class="icon-common-delete icon-trash"></i> 删除</a>
					</div>
				</div>
				<!--mainActions end-->
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
	function del(id){
		if(confirm("确认删除？")){
			$.ajaxSettings.async = false;
			$.getJSON("team/need/del?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
</script>