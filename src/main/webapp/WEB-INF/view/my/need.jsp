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
		<title>我的地盘::我的需求</title>
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
						<a href="my/need?type=7" class="btn btn-link ${prm.type == 7 ? 'btn-active-text':''}">
							<span class="text">指派给我</span>
							<c:if test="${prm.type == 7}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="my/need?type=6" class="btn btn-link ${prm.type == 6 ? 'btn-active-text':''}">
							<span class="text">由我创建</span>
							<c:if test="${prm.type == 6}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="my/need?type=10" class="btn btn-link ${prm.type == 10 ? 'btn-active-text':''}">
							<span class="text">由我验收</span>
							<c:if test="${prm.type == 10}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="my/need?type=11" class="btn btn-link ${prm.type == 11 ? 'btn-active-text':''}">
							<span class="text">需求方是我</span>
							<c:if test="${prm.type == 11}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="my/need?type=8" class="btn btn-link ${prm.type == 8 ? 'btn-active-text':''}">
							<span class="text">由我关闭</span>
							<c:if test="${prm.type == 8}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a class="btn btn-link querybox-toggle ${prm.type == 12 ? 'querybox-opened':''}" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索
							<c:if test="${prm.type == 12}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
					</div>
					<div class="btn-toolbar pull-right">
						<a href="my/need/toBatchAdd" class="btn btn-secondary" style="text-shadow:0 -1px 0 rgba(0,0,0,.2);"><i class="icon icon-plus"></i> 批量创建</a>
						<a href="my/need/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i> 提需求</a>
					</div>
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator ${prm.type == 12 ? 'show':''}" id="queryBox">
							<form method="post" action="my/need?type=12" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="needType" id="needType">
													<option ${prm.needType=='1'?'selected="selected"':'' } value="1">需求名称</option>
													<option ${prm.needType=='2'?'selected="selected"':'' } value="2">需求描述</option>
												</select>
											</td>
											<td>
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control  searchInput" placeholder="选择后请输入要查询的需求名称 或 需求描述">
											</td>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="state" id="state">
													<option value="">请选择状态</option>
													<option ${prm.state=='1'?'selected="selected"':'' } value="1">激活</option>
													<option ${prm.state=='3'?'selected="selected"':'' } value="3">已关闭</option>
													<option ${prm.state=='2'?'selected="selected"':'' } value="2">已变更</option>
													<option ${prm.state=='0'?'selected="selected"':'' } value="0">已删除</option>
												</select>
											</td>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="srcId" id="srcId">
													<option value="">请选择需求来源</option>
													<option ${prm.srcId=='1'?'selected="selected"':'' } value="1">产品经理</option>
													<option ${prm.srcId=='2'?'selected="selected"':'' } value="2">市场</option>
													<option ${prm.srcId=='3'?'selected="selected"':'' } value="3">客户</option>
													<option ${prm.srcId=='4'?'selected="selected"':'' } value="4">客服</option>
													<option ${prm.srcId=='5'?'selected="selected"':'' } value="5">技术支持</option>
													<option ${prm.srcId=='6'?'selected="selected"':'' } value="6">开发人员</option>
													<option ${prm.srcId=='7'?'selected="selected"':'' } value="7">测试人员</option>
													<option ${prm.srcId=='8'?'selected="selected"':'' } value="8">Bug</option>
													<option ${prm.srcId=='9'?'selected="selected"':'' } value="9">其他</option>
												</select>
											</td>
											<td class="w-200px">
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
													<option ${prm.memberType=='2'?'selected="selected"':'' } value="2">关闭人</option>
													<option ${prm.memberType=='3'?'selected="selected"':'' } value="3">需求方</option>
												</select>
											</td>
											<td>
												<input type="text" name="memberSearch" id="memberSearch" value="${prm.memberSearch}" class="form-control  searchInput" placeholder="选择后请输入要查询的指派人 或 关闭人 或 需求方">
											</td>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="dateType" id="dateType">
													<option value="">请选择日期</option>
													<option ${prm.dateType=='1'?'selected="selected"':'' } value="1">开始日期</option>
													<option ${prm.dateType=='2'?'selected="selected"':'' } value="2">结束日期</option>
												</select>
											</td>
											<td class="w-200px">
												<input type="text" name="start_date" id="start_date" valve="${prm.start_date}" class="form-control form-date" placeholder="开始" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
											<td class="w-200px">
												<input type="text" name="end_date" id="end_date" valve="${prm.end_date}" class="form-control form-date" placeholder="结束" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td colspan="5" class="text-center form-actions">
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
											<th data-flex="false" data-width="90px" style="width:90px" class="c-id text-center" title="ID">
												<a href="${pageList.desAction}&orderColumn=id&orderByValue=${prm.orderColumn=='id'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">ID</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:110px" class="c-name " title="优先级">
												<a  href="${pageList.desAction}&orderColumn=level&orderByValue=${prm.orderColumn=='level'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='level'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">优先级</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri " title="需求名称">
												<a  href="${pageList.desAction}&orderColumn=need_name&orderByValue=${prm.orderColumn=='need_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='need_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">需求名称</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:150px" class="c-pri " title="所属项目">
												<a href="${pageList.desAction}&orderColumn=tp.project_name&orderByValue=${prm.orderColumn=='tp.project_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='tp.project_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">所属项目</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri " title="需求方">
												<a  href="${pageList.desAction}&orderColumn=member_id&orderByValue=${prm.orderColumn=='member_id'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='member_id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">需求方</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri " title="指派给">
												<a  href="${pageList.desAction}&orderColumn=assigned_name&orderByValue=${prm.orderColumn=='assigned_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='assigned_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">指派给</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="开始时间">
												<a  href="${pageList.desAction}&orderColumn=start_date&orderByValue=${prm.orderColumn=='start_date'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='start_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">开始时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="结束时间">
												<a  href="${pageList.desAction}&orderColumn=end_date&orderByValue=${prm.orderColumn=='end_date'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='end_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">结束时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:90px" class="c-name text-center" title="状态">
												<a  href="${pageList.desAction}&orderColumn=state&orderByValue=${prm.orderColumn=='state'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">状态</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:80px" class="c-name text-center" title="阶段">
												<a  href="${pageList.desAction}&orderColumn=stage&orderByValue=${prm.orderColumn=='stage'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='stage'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">阶段</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="创建时间">
												<a  href="${pageList.desAction}&orderColumn=create_time&orderByValue=${prm.orderColumn=='create_time'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">创建时间</a>
											</th>
											<th data-flex="false" data-width="300px" style="width:200px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="need" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${need.id}</td>
											<td class="c-pri text-left">${need.level == 1 ? '紧急重要' : need.level == 2 ? '紧急不重要' : need.level == 3 ? '不紧急重要' : '不紧急不重要'}</td>
											<td class="text-left">
												<c:if test="${need.resolved == 1 && (prm.type == 1 || prm.type == 2)}">
													<a class="task-toggle" data-id="${need.id}"><i class="icon icon-caret-down"></i></a>
												</c:if>
												<c:if test="${need.parent_id > 0 && prm.type != 1 && prm.type != 2}">
													<span class="label label-badge label-light">子</span>
												</c:if>
												<a href="my/need/detail?id=${need.id}" data-toggle="tooltip" data-placement="top" title="${need.need_name}">
													${need.need_name}
												</a>
												<c:if test="${need.full == 0}">
													<span class="label label-warning" title="不能创建任务，不能分解">不完整需求</span>
												</c:if>
											</td>
											<td class="text-left">
												<a href="team/project/detail?id=${need.project_id}" data-toggle="tooltip" data-placement="top" title="${need.project_name}">${need.project_name}</a>
											</td>
											<td class="c-name text-left">${need.member_name}</td>
											<td class="c-name text-left">
												<c:if test="${need.assigned_name == '' || need.assigned_name == null}">
													<a href="my/need/toAssign?id=${need.id}" class="btn btn-icon-left btn-sm">
														<i class="icon icon-hand-right"></i>
														<span class="text-primary">未指派</span>
													</a>
												</c:if>
												<c:if test="${need.assigned_name != ''}">
													<span class="text-red">${need.assigned_name}</span>
												</c:if>
											</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.start_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.end_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center">
												<span class="${need.state == 1 ? 'status-active' : need.state == 2 ? 'status-changed' : need.state == 3 ? 'status-closed' : ''}">
													<span class="label label-dot"></span>
													${need.state == 1 ? '激活' : need.state == 2 ? '已变更' : need.state == 3 ? '已关闭' : need.state == 0 ? '已删除' : '未知'}
												</span>
											</td>
											<td class="c-assignedTo has-btn text-center">
													${need.stage == 1 ? '待验收' : need.stage == 2 ? '验收完成' : need.stage == 3 ? '验收不通过' : '未知'}
											</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.create_time}" pattern="yyyy-MM-dd" /></td>
											<td class="c-actions text-right">
												<c:if test="${need.state > 0}">
													<c:if test="${need.state == 1 || need.state == 2}">
														<a href="my/need/toChange?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="${need.full == 0?'完善需求':'需求变更'}"><i class="icon-story-change icon-fork"></i></a>
														<a href="my/need/toClose?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关闭需求"><i class='icon-task-close icon-off'></i></a>
														<c:if test="${need.full == 1}">
															<a href="my/need/toCheck?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="验收需求"><i class="icon-story-review icon-glasses"></i></a>
														</c:if>
														<c:if test="${need.full == 1}">
															<a href="my/task/toBatchAdd?need_id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="批量建任务"><i class="icon icon-plus"></i></a>
														</c:if>
														<%-- <c:if test="${need.parent_id>0 || need.full == 0}">
															<a class="disabled btn" title="批量建任务"><i class="icon icon-plus"></i></a>
														</c:if> --%>
														<c:if test="${(need.parent_id == null || need.parent_id == '') && need.full == 1}">
															<a href="my/need/toBatchAdd?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="分解需求"><i class='icon-task-batchCreate icon-branch'></i></a>
														</c:if>
														<%-- <c:if test="${need.parent_id>0 || need.full == 0}">
															<a class="disabled btn" title="分解需求"><i class='icon-task-batchCreate icon-branch'></i></a>
														</c:if> --%>
													</c:if>
													<%-- <c:if test="${need.state != 1 && need.state != 2}">
														<a class="disabled btn" title="变更"><i class="icon-story-change icon-fork"></i></a>
														<a class="disabled btn" title="关闭"><i class='icon-task-close icon-off'></i></a>
														<a class="disabled btn" title="验收"><i class="icon-story-review icon-glasses"></i></a>
														<a class="disabled btn" title="批量建任务"><i class="icon icon-plus"></i></a>
														<a class="disabled btn" title="分解需求"><i class='icon-task-batchCreate icon-branch'></i></a>
													</c:if> --%>
												</c:if>
												<c:if test="${need.state == 0}">--</c:if>
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
	    linkCreator: "my/need?type=${prm.type}&currentPage={page}&pageSize={recPerPage}&search=${prm.search}&orderColumn=${prm.orderColumn}&orderByValue=${prm.orderByValue}"
	});
</script>