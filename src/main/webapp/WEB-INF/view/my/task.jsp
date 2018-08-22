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
		<title>我的地盘::我的任务</title>
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
				<!--mainMenu start-->
				<div id="mainMenu" class="clearfix">
					<div class="btn-toolbar pull-left">
						<a href="my/task?type=8" class="btn btn-link ${prm.type == 8 ? 'btn-active-text':''}">
							<span class="text">指派给我</span>
							<c:if test="${prm.type == 8}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="my/task?type=13" class="btn btn-link ${prm.type == 13 ? 'btn-active-text':''}">
							<span class="text">待我审核</span>
							<c:if test="${prm.type == 13}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="my/task?type=11" class="btn btn-link ${prm.type == 11 ? 'btn-active-text':''}">
							<span class="text">由我创建</span>
							<c:if test="${prm.type == 11}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="my/task?type=10" class="btn btn-link ${prm.type == 10 ? 'btn-active-text':''}">
							<span class="text">由我完成</span>
							<c:if test="${prm.type == 10}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="my/task?type=12" class="btn btn-link ${prm.type == 12 ? 'btn-active-text':''}">
							<span class="text">由我关闭</span>
							<c:if test="${prm.type == 12}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a class="btn btn-link querybox-toggle ${prm.type == 14 ? 'querybox-opened':''}" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索</a>
					</div>
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator ${prm.type == 14 ? 'show':''}" id="queryBox">
							<form method="post" action="my/task?type=14" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="searchType" id="searchType">
													<option ${prm.searchType=='1'?'selected="selected"':'' } value="1">任务名称/ID</option>
													<option ${prm.searchType=='2'?'selected="selected"':'' } value="2">任务描述</option>
												</select>
											</td>
											<td>
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control  searchInput" placeholder="选择后请输入要查询的任务名称/ID 或 任务描述">
											</td>
											<td class="w-180px">
												<select class="form-control chosen chosen-select" name="delay" id="delay">
													<option value="">请选择是否已延期</option>
													<option ${prm.delay=='1'?'selected="selected"':'' } value="1">已延期</option>
												</select>
											</td>
											<td class="w-180px">
												<select class="form-control chosen chosen-select" name="taskType" id="taskType">
													<option value="">请选择任务类型</option>
													<option ${prm.taskType=='1'?'selected="selected"':'' } value="1">开发</option>
													<option ${prm.taskType=='2'?'selected="selected"':'' } value="2">测试</option>
													<option ${prm.taskType=='3'?'selected="selected"':'' } value="3">设计</option>
													<option ${prm.taskType=='4'?'selected="selected"':'' } value="4">前端</option>
													<option ${prm.taskType=='5'?'selected="selected"':'' } value="5">维护</option>
													<option ${prm.taskType=='6'?'selected="selected"':'' } value="6">需求</option>
													<option ${prm.taskType=='7'?'selected="selected"':'' } value="7">研究</option>
													<option ${prm.taskType=='8'?'selected="selected"':'' } value="8">讨论</option>
													<option ${prm.taskType=='9'?'selected="selected"':'' } value="9">运维</option>
													<option ${prm.taskType=='10'?'selected="selected"':'' } value="10">事务</option>
													<option ${prm.taskType=='0'?'selected="selected"':'' } value="0">其他</option>
												</select>
											</td>
											<td class="w-160px">
												<select class="form-control chosen chosen-select" name="state" id="state">
													<option value="">请选择状态</option>
													<option ${prm.state=='1'?'selected="selected"':'' } value="1">待接收</option>
													<option ${prm.state=='2'?'selected="selected"':'' } value="2">进行中</option>
													<option ${prm.state=='3'?'selected="selected"':'' } value="3">审核中</option>
													<option ${prm.state=='4'?'selected="selected"':'' } value="4">已完成</option>
													<option ${prm.state=='6'?'selected="selected"':'' } value="6">已取消</option>
													<option ${prm.state=='7'?'selected="selected"':'' } value="7">已关闭</option>
												</select>
											</td>
											<td class="w-160px">
												<select class="form-control chosen chosen-select" name="level" id="level">
													<option value="">请选择优先级</option>
													<option ${prm.level=='1'?'selected="selected"':'' } value="1">紧急重要</option>
													<option ${prm.level=='2'?'selected="selected"':'' } value="2">紧急不重要</option>
													<option ${prm.level=='3'?'selected="selected"':'' } value="3">不紧急重要</option>
													<option ${prm.level=='4'?'selected="selected"':'' } value="4">不紧急不重要</option>
												</select>
											</td>
										</tr>
										<tr>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="memberType" id="memberType">
													<option ${prm.memberType=='1'?'selected="selected"':'' } value="1">指派人</option>
													<option ${prm.memberType=='2'?'selected="selected"':'' } value="2">完成者</option>
												</select>
											</td>
											<td>
												<input type="text" name="memberSearch" id="memberSearch" value="${prm.memberSearch}" class="form-control  searchInput" placeholder="选择后请输入要查询的指派人 或 完成者">
											</td>
											<td class="w-180px">
												<select class="form-control chosen chosen-select" name="overdue" id="overdue">
													<option value="">请选择是否已逾期</option>
													<option ${prm.overdue=='1'?'selected="selected"':'' } value="1">已逾期</option>
												</select>
											</td>
											<td class="w-180px">
												<select class="form-control chosen chosen-select" name="dateType" id="dateType">
													<option value="">请选择日期</option>
													<option ${prm.dateType=='1'?'selected="selected"':'' } value="1">计划结束日期</option>
													<option ${prm.dateType=='2'?'selected="selected"':'' } value="2">实际结束日期</option>
												</select>
											</td>
											<td class="w-160px">
												<input type="text" name="start_date" id="start_date" valve="${prm.start_date}" class="form-control form-date" placeholder="开始" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
											<td class="w-160px">
												<input type="text" name="end_date" id="end_date" valve="${prm.end_date}" class="form-control form-date" placeholder="结束" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td colspan="6" class="text-center form-actions">
												<button type="submit" id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">搜索</button>
										</tr>
									</tbody>
								</table>
							</form>
						</div>
						<form class="main-table table-task skip-iframe-modal" method="post"
							id="projectTaskForm" data-ride="table">
							<!--table-responsive start-->
							<div class="table-responsive">
								<table class="table has-sort-head" id="taskList" data-fixed-left-width="550" data-fixed-right-width="160" style="border:2px solid #fff">
									<thead>
										<tr>
											<th data-flex="false" data-width="90px" style="width: 70px" class="c-id text-center" title="ID">
												<a href="${pageList.desAction}&orderColumn=t.id&orderByValue=${prm.orderColumn=='t.id'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='t.id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:150px" class="c-pri " title="P">
												<a  href="${pageList.desAction}&orderColumn=t.level&orderByValue=${prm.orderColumn=='t.level'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='t.level'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">优先级</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri " title="任务名称">
												<a  href="${pageList.desAction}&orderColumn=t.task_name&orderByValue=${prm.orderColumn=='t.task_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='t.task_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">任务名称</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:150px" class="c-pri " title="需求名称">
												<a  href="${pageList.desAction}&orderColumn=tn.need_id&orderByValue=${prm.orderColumn=='tn.need_id'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='tn.need_id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">需求ID</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name " title="任务类型">
												<a  href="${pageList.desAction}&orderColumn=t.task_type&orderByValue=${prm.orderColumn=='t.task_type'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='t.task_type'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">任务类型</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:125px" class="c-name text-center" title="状态">
												<a  href="${pageList.desAction}&orderColumn=t.state&orderByValue=${prm.orderColumn=='t.state'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='t.state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">状态</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri " title="创建者">
												<a  href="${pageList.desAction}&orderColumn=t.member_id&orderByValue=${prm.orderColumn=='t.member_id'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='t.member_id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">创建者</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri " title="指派给">
												<a  href="${pageList.desAction}&orderColumn=t.assigned_name&orderByValue=${prm.orderColumn=='t.assigned_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='t.assigned_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">指派给</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="计划结束时间">
												<a  href="${pageList.desAction}&orderColumn=t.plan_end_date&orderByValue=${prm.orderColumn=='t.plan_end_date'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='t.plan_end_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">计划结束</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="实际结束时间">
												<a  href="${pageList.desAction}&orderColumn=t.real_end_date&orderByValue=${prm.orderColumn=='t.real_end_date'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='t.real_end_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">实际结束</a>
											</th>
											<th data-flex="false" data-width="360px" style="width:260px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="task" varStatus="sta">
										<tr>
											<td class="c-id text-center">${task.id}</td>
											<td class="c-pri">${task.level == 1 ? '紧急重要' : task.level == 2 ? '紧急不重要' : task.level == 3 ? '不紧急重要' : '不紧急不重要'}</td>
											<td class="text-left">
												<c:if test="${task.subCount > 0 && (prm.type == 1 || prm.type == 2)}">
													<a class="task-toggle" data-id="${task.id}"><i class="icon icon-caret-down"></i></a>
												</c:if>
												<c:if test="${task.parent_id > 0 && prm.type != 1 && prm.type != 2}">
													<span class="label label-badge label-light">子</span>
												</c:if>
												<a href="my/task/detail?id=${task.id}" data-toggle="tooltip" data-placement="top" title="${task.task_name}">${task.task_name}</a>
											</td>
											<td class="text-left">
												<a href="my/need/detail?id=${task.need_id}" data-toggle="tooltip" data-placement="top" title="${task.need_name}">${task.need_id}</a>
											</td>
											<td class="c-pri text-center">${task.task_type==1?'开发':task.task_type==2?'测试':task.task_type==3?'设计':task.task_type==4?'前端':task.task_type==5?'维护':task.task_type==6?'需求':task.task_type==7?'研究':task.task_type==8?'讨论':task.task_type==9?'运维':task.task_type==10?'事务':'其他'}</td>
											<td class="c-pri text-center">
												<span class="${task.state == 1 ? 'status-wait' : task.state == 2 ? 'status-doing' : task.state == 3 ? 'status-pause' : task.state == 4 ? 'status-done' : task.state == 7 ? 'status-closed' : 'status-cancel'}">
													<span class="label label-dot"></span>
													${task.state == 1 ? '待接收' : task.state == 2 ? '进行中' : task.state == 3 ? '审核中' : task.state == 4 ? '已完成' : task.state == 5 ? '已暂停' : task.state == 6 ? '已取消' : task.state == 7 ? '已关闭' : '未知'}
												</span>
												<c:if test="${task.delay>0}">
													<span class="label label-warning" title="任务已延期">延</span>
												</c:if>
												<c:if test="${task.overdue==1}">
													<span class="label label-danger" title="任务已逾期">逾</span>
												</c:if>
											</td>
											<td class="c-pri text-left">${task.member_name}</td>
											<td class="c-name text-left">
												<c:if test="${task.assigned_name == '' || task.assigned_name == null}">
													<a href="my/task/toAssign?id=${task.id}" class="btn btn-icon-left btn-sm">
														<i class="icon icon-hand-right"></i>
														<span class="text-primary">未指派</span>
													</a>
												</c:if>
												<c:if test="${task.assigned_name != ''}">
													<span class="text-red">${task.assigned_name}</span>
												</c:if>
											</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${task.plan_end_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${task.real_end_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-actions text-right">
												<c:if test="${task.deleted == '0'}">
													<%-- 任务状态为待接收时，且是未分解任务 --%>
													<c:if test="${task.state == 1 && task.resolved == 0}">
														<a href="my/task/toOpen?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="接收任务"><i class='icon-task-start icon-play'></i></a>
													</c:if>
													<%-- 任务状态为待接收和进行中时 --%>
													<c:if test="${task.state == 1 || task.state == 2}">
														<a href="my/task/toClose?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关闭任务"><i class='icon-task-close icon-off'></i></a>
													</c:if>
													<%-- 任务状态为进行中，且不为延期审核中，且是未分解任务 --%>
													<c:if test="${task.state == 2 && task.delay != 1 && task.resolved == 0}">
														<a href="my/task/toFinish?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="完成任务"><i class='icon-task-finish icon-checked'></i></a>
													</c:if>
													<%-- 延期审核中 --%>
													<c:if test="${task.delay == 1}">
														<a href="my/task/toDelayCheck?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="延期审核"><i class="icon-story-review icon-glasses"></i></a>
													</c:if>
													<%-- 完成审核中 --%>
													<c:if test="${task.state == 3}">
														<a href="my/task/toFinishCheck?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="完成审核"><i class="icon-story-review icon-glasses"></i></a>
													</c:if>
													<c:if test="${task.state == 1 || task.state == 2}">
														<a href="my/task/toHandover?id=${task.id}" class="btn btn-primary" data-toggle="tooltip" data-placement="top" title="任务交接"><i class="icon icon-exchange"></i></a>
													</c:if>
													<c:if test="${task.state == 4 && task.task_type != 2}">
														<a href="my/test/toAdd?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="提测试"><i class="icon icon-plus"></i></a>
													</c:if>
													<%-- 任务状态为待接收、进行中时，且不为延期审核中 --%>
													<c:if test="${task.state < 3 && task.delay != 1 && task.resolved == 0}">
														<a href="my/task/toChange?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="变更任务"><i class="icon-story-change icon-fork"></i></a>
													</c:if>
													<%-- 任务状态为进行中，且未进行过延期，且是未分解任务 --%>
													<c:if test="${task.state == 2 && task.delay == 0 && task.resolved == 0}">
														<a href="my/task/toDelay?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="延期任务"><i class='icon-task-recordEstimate icon-time'></i></a>
													</c:if>
													<%-- 状态不为完成审核中，且不为延期审核中 --%>
													<c:if test="${task.state < 3 && task.delay != 1}">
														<c:if test="${task.parent_id == null || task.parent_id == ''}">
															<a href="my/task/toBatchAdd?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="分解任务"><i class='icon-task-batchCreate icon-branch'></i></a>
														</c:if>
													</c:if>
													<c:if test="${task.state == 4 && task.task_type == 2}">
														<a href="my/bug/toAdd?id=${task.id}" class="btn" data-toggle="tooltip" data-placement="top" title="提Bug"><i class='icon-task-start icon-bug'></i></a>
													</c:if>
												</c:if>
												<c:if test="${task.deleted == '1'}">--</c:if>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!--table-responsive end-->
							<!--table-footer start-->
							<div class="table-footer" style="left: 0px; bottom: 0px;">
								<!--pager srtart-->
								<ul class="pager">
								</ul>
								<!--pager end-->
							</div>
							<!--table-footer end-->
						</form>
					</div>
					<!--main-col end-->
				</div>
			</div>
			<script></script>
		</main>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
	$("#bysearchTab").click(function(){
		if($(this).hasClass("querybox-opened")){
			$(this).removeClass("querybox-opened")
			$("#queryBox").removeClass("show")
		}else{
			$(this).addClass("querybox-opened")
			$("#queryBox").addClass("show")
		}
	});
	$('.pager').pager({
	    page: ${pageList.currentPage},
	    recTotal: ${pageList.totalCounts},
	    recPerPage: ${pageList.pageSize},
	    pageSizeOptions: [10, 20, 30, 50, 100],
	    lang: 'zh_cn',
	    linkCreator: "my/task?type=${prm.type}&currentPage={page}&pageSize={recPerPage}&search=${prm.search}&orderColumn=${prm.orderColumn}&orderByValue=${prm.orderByValue}"
	});
</script>