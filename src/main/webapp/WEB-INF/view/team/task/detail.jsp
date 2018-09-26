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
		<title>任务：${taskM.task_name}</title>
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
						<a href="javascript:history.go(-1);" class="btn btn-link"><i class="icon icon-back icon-sm"></i> 返回</a>
						<div class="divider"></div>
						<div class="page-title">
							<span class="label label-id">${taskM.id}</span> <span class="text">${taskM.task_name}</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">任务描述</div>
								<div class="detail-content article-content">
									<c:if test="${taskM.remark != null && taskM.remark != ''}">
										${taskM.remark}
									</c:if>
									<c:if test="${taskM.remark == null || taskM.remark == ''}">
										<div class="text-center text-muted">暂无</div>
									</c:if>
								</div>
							</div>
							<div class="detail">
								<div class="detail-title">模块描述</div>
								<div class="detail-content article-content">
									<c:if test="${taskM.need_remark != null && taskM.need_remark != ''}">
										${taskM.need_remark}
									</c:if>
									<c:if test="${taskM.need_remark == null || taskM.need_remark == ''}">
										<div class="text-center text-muted">暂无</div>
									</c:if>
								</div>
							</div>
							<div class="detail">
								<div class="detail-title">验收标准</div>
								<div class="detail-content article-content">
									<c:if test="${taskM.check_remark != null && taskM.check_remark != ''}">
										${taskM.check_remark}
									</c:if>
									<c:if test="${taskM.check_remark == null || taskM.check_remark == ''}">
										<div class="text-center text-muted">暂无</div>
									</c:if>
								</div>
							</div>
							<c:if test="${subTask != null}">
								<div class="detail">
									<div class="detail-title">子任务</div>
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
												<c:forEach items="${subTask}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="team/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name }">${item.task_name }</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>${item.task_type==1?'开发':item.task_type==2?'测试':item.task_type==3?'设计':item.task_type==4?'前端':item.task_type==5?'维护':item.task_type==6?'模块':item.task_type==7?'研究':item.task_type==8?'讨论':item.task_type==9?'运维':item.task_type==10?'事务':'其他'}</td>
													<td>${item.state == 1 ? '待接收' : item.state == 2 ? '进行中' : item.state == 3 ? '审核中' : item.state == 4 ? '已完成' : item.state == 5 ? '已暂停' : item.state == 6 ? '已取消' : item.state == 7 ? '已关闭' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${needTask != null}">
								<div class="detail">
									<div class="detail-title">同模块任务</div>
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
												<c:forEach items="${needTask}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="team/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name }">${item.task_name }</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>${item.task_type==1?'开发':item.task_type==2?'测试':item.task_type==3?'设计':item.task_type==4?'前端':item.task_type==5?'维护':item.task_type==6?'模块':item.task_type==7?'研究':item.task_type==8?'讨论':item.task_type==9?'运维':item.task_type==10?'事务':'其他'}</td>
													<td>${item.state == 1 ? '待接收' : item.state == 2 ? '进行中' : item.state == 3 ? '审核中' : item.state == 4 ? '已完成' : item.state == 5 ? '已暂停' : item.state == 6 ? '已取消' : item.state == 7 ? '已关闭' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${linkTask != null}">
								<div class="detail">
									<div class="detail-title">关联任务</div>
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
												<c:forEach items="${linkTask}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="team/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name }">${item.task_name }</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>${item.task_type==1?'开发':item.task_type==2?'测试':item.task_type==3?'设计':item.task_type==4?'前端':item.task_type==5?'维护':item.task_type==6?'模块':item.task_type==7?'研究':item.task_type==8?'讨论':item.task_type==9?'运维':item.task_type==10?'事务':'其他'}</td>
													<td>${item.state == 1 ? '待接收' : item.state == 2 ? '进行中' : item.state == 3 ? '审核中' : item.state == 4 ? '已完成' : item.state == 5 ? '已暂停' : item.state == 6 ? '已取消' : item.state == 7 ? '已关闭' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${testTask != null}">
								<div class="detail">
									<div class="detail-title">测试任务</div>
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
												<c:forEach items="${testTask}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="team/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name }">${item.task_name }</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>${item.task_type==1?'开发':item.task_type==2?'测试':item.task_type==3?'设计':item.task_type==4?'前端':item.task_type==5?'维护':item.task_type==6?'模块':item.task_type==7?'研究':item.task_type==8?'讨论':item.task_type==9?'运维':item.task_type==10?'事务':'其他'}</td>
													<td>${item.state == 1 ? '待接收' : item.state == 2 ? '进行中' : item.state == 3 ? '审核中' : item.state == 4 ? '已完成' : item.state == 5 ? '已暂停' : item.state == 6 ? '已取消' : item.state == 7 ? '已关闭' : '未知'}</td>
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
												<th>所属模块</th>
												<td title="${taskM.need_name}"><a href="team/need/detail?id=${taskM.need_id}" target="_blank">${taskM.need_name}</a></td>
											</tr>
											<tr>
												<th>指派给</th>
												<td>${taskM.assigned_name }</td>
											</tr>
											<tr>
												<th>任务类型</th>
												<td>
													${taskM.task_type==1?'开发':taskM.task_type==2?'测试':taskM.task_type==3?'设计':taskM.task_type==4?'前端':taskM.task_type==5?'维护':taskM.task_type==6?'模块':taskM.task_type==7?'研究':taskM.task_type==8?'讨论':taskM.task_type==9?'运维':taskM.task_type==10?'事务':'其他'}
												</td>
											</tr>
											<tr>
												<th>当前状态</th>
												<td>
													${taskM.state == 1 ? '待接收' : taskM.state == 2 ? '进行中' : taskM.state == 3 ? '审核中' : taskM.state == 4 ? '已完成' : taskM.state == 5 ? '已暂停' : taskM.state == 6 ? '已取消' : taskM.state == 7 ? '已关闭' : '未知'}
												</td>
											</tr>
											<tr>
												<th>删除状态</th>
												<td>
													${taskM.deleted == 0 ? '未删除' : taskM.deleted == 1 ? '<span style="color: red;">已删除</span>' : '未知' }
												</td>
											</tr>
											<tr>
												<th>优先级</th>
												<td>
													<c:if test="${taskM.level=='1'}">紧急重要</c:if>
													<c:if test="${taskM.level=='2'}">紧急不重要</c:if>
													<c:if test="${taskM.level=='3'}">不紧急重要</c:if>
													<c:if test="${taskM.level=='4'}">不紧急不重要</c:if>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">工时信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>初始开始</th>
												<td><fmt:formatDate value="${taskM.start_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>实际开始</th>
												<td><fmt:formatDate value="${taskM.real_start_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>初始结束</th>
												<td><fmt:formatDate value="${taskM.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>计划结束</th>
												<td>
													<fmt:formatDate value="${taskM.plan_end_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
													<c:if test="${taskM.delay==2}">
														<span style="color:red">已延期</span>
														原结束时间：<fmt:formatDate value="${taskM.delayed_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>实际结束</th>
												<td><fmt:formatDate value="${taskM.real_end_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">项目的一生</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>由谁创建</th>
												<td>${taskM.member_name } 于 <fmt:formatDate value="${taskM.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>由谁完成</th>
												<td>
													<c:if test="${taskM.assigned_name != null && taskM.assigned_name != '' && taskM.state == 4}">
														${taskM.assigned_name } 于 <fmt:formatDate value="${taskM.assigned_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>由谁审核</th>
												<td>
													<c:if test="${taskM.checked_name != null && taskM.checked_name != ''}">
														${taskM.checked_name }
														<c:if test="${taskM.checked_time != null}">
															于 <fmt:formatDate value="${taskM.checked_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
														</c:if>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>审核结果</th>
												<td>${taskM.checked_reason }</td>
											</tr>
											<tr>
												<th>由谁关闭</th>
												<td>
													<c:if test="${taskM.closed_name != null && taskM.closed_name != ''}">
														${taskM.closed_name } 于 <fmt:formatDate value="${taskM.closed_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>关闭原因</th>
												<td>${taskM.closed_reason }</td>
											</tr>
											<tr>
												<th>最后修改</th>
												<td>${taskM.update_time }</td>
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
						<a href="javascript:history.go(-1);" id="back" class="btn btn-link"><i class="icon-goback icon-back"></i> 返回</a>
						<c:if test="${taskM.state == 1 && taskM.resolved == 0}">
							<a href='team/task/toOpen?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-start icon-play'></i> 接收</a>
						</c:if>
						<c:if test="${taskM.assigned_name == '' || taskM.assigned_name == null}">
							<a href='team/task/toAssign?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-assignTo icon-hand-right'></i> 指派</a>
						</c:if>
						<c:if test="${taskM.state == 1 || taskM.state == 2}">
							<a href='team/task/toClose?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-close icon-off'></i> 关闭</a>
						</c:if>
						<c:if test="${taskM.state == 7}">
							<a href='team/task/toActive?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-activate icon-magic'></i> 激活</a>
						</c:if>
						<c:if test="${taskM.state == 2 && taskM.delay == 0 && taskM.resolved == 0}">
							<a href='team/task/toDelay?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-recordEstimate icon-time'></i> 延期</a>
						</c:if>
						<c:if test="${taskM.delay == 1}">
							<a href="team/task/toDelayCheck?id=${taskM.id}" class="btn btn-link"><i class="icon-story-review icon-glasses"></i> 延期审核</a>
						</c:if>
						<c:if test="${taskM.state == 3}">
							<a href="team/task/toFinishCheck?id=${taskM.id}" class="btn btn-link"><i class="icon-story-review icon-glasses"></i> 完成审核</a>
						</c:if>
						<c:if test="${taskM.state == 2 && taskM.delay != 1 && taskM.resolved == 0}">
							<a href='team/task/toFinish?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-finish icon-checked'></i> 完成</a>
						</c:if>
						<c:if test="${taskM.state < 3 && taskM.delay != 1 && (taskM.parent_id == null || taskM.parent_id == '')}">
							<a href='team/task/toRelevance?id=${taskM.id}' class='btn btn-link ' ><i class='icon icon-sitemap'></i> 关联</a>
						</c:if>
						<div class="divider"></div>
						<c:if test="${taskM.state < 3 && taskM.delay != 1 && (taskM.parent_id == null || taskM.parent_id == '')}">
							<a href='team/task/toBatchAdd?id=${taskM.id}' class='btn btn-link ' ><i class='icon-task-batchCreate icon-branch'></i> 分解</a>
						</c:if>
						<a href="team/task/toEdit?id=${taskM.id}" class="btn btn-link"><i class="icon-common-edit icon-edit"></i> 编辑</a>
						<a href="javascript:void(0)" onclick="del(${taskM.id})" class="btn btn-link"><i class="icon-common-delete icon-trash"></i> 删除</a>
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
			$.getJSON("team/task/del?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
</script>