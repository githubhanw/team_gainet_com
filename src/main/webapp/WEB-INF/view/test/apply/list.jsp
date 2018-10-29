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
		<title>测试单列表</title>
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
						<a href="test/apply/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="test/apply/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">待测试</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="test/apply/index?type=2" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">测试中</span>
							<c:if test="${prm.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="test/apply/index?type=3" class="btn btn-link ${prm.type == 3 ? 'btn-active-text':''}">
							<span class="text">已测试</span>
							<c:if test="${prm.type == 3}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="test/apply/index?type=4" class="btn btn-link ${prm.type == 4 ? 'btn-active-text':''}">
							<span class="text">驳回</span>
							<c:if test="${prm.type == 4}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a class="btn btn-link querybox-toggle ${prm.type == 10 ? 'querybox-opened':''}" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索
							<c:if test="${prm.type == 10}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="test/apply/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i> 提测试单</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator ${prm.type == 10 ? 'show':''}" id="queryBox">
							<form method="post" action="test/apply/index?type=10" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td style="width:500px">
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control searchInput" placeholder="请输入要查询的测试申请单内容">
											</td>
											<td style="width:360px">
												<select data-placeholder="请选择状态" class="form-control chosen chosen-select" name="state" id="state">
													<option value="1" ${prm.state == 1 ? 'selected="selected"' : ''}>待测试</option>
													<option value="2" ${prm.state == 2 ? 'selected="selected"' : ''}>测试中</option>
													<option value="3" ${prm.state == 3 ? 'selected="selected"' : ''}>已测试</option>
													<option value="4" ${prm.state == 4 ? 'selected="selected"' : ''}>驳回</option>
												</select>
											</td>
											<td class="w-140px">
												<input type="text" name="createtime" id="createtime" value="${prm.createtime}" class="form-control form-date" placeholder="开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;">
											</td>
											<td class="w-140px">
												<input type="text" name="endtime" id="endtime" value="${prm.endtime}" class="form-control form-date" placeholder="结束时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;">
											</td>
										</tr>
										<tr>
											<td colspan="8" class="text-center form-actions">
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
								<table class="table has-sort-head" id="taskList"
									data-fixed-left-width="550" data-fixed-right-width="160">
									<thead>
										<tr>
											<th data-flex="false" data-width="90px" style="width:150px" class="c-id text-center" title="ID">
												<a class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('id');">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri" title="测试名称">
												<a class="${prm.orderColumn=='t.task_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('t.task_name');">测试名称</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri text-center" title="类型">
												<a class="${prm.orderColumn=='apply_type'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('apply_type');">类型</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri text-center" title="状态">
												<a class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('state');">状态</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri text-center" title="申请人">
												<a class="${prm.orderColumn=='apply_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('apply_name');">申请人</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri text-center" title="测试人">
												<a class="${prm.orderColumn=='tester'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('tester');">测试人</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri text-center" title="申请时间">
												<a class="${prm.orderColumn=='apply_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('apply_time');">申请时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:200px" class="c-pri" title="驳回原因">
												<a class="${prm.orderColumn=='dismissal'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('dismissal');">驳回原因</a>
											</th>
											<th data-flex="false" data-width="200px" style="width:200px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="apply" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${apply.id}</td>
											<td class="c-pri text-left">
												<a href="test/apply/detail?id=${apply.id}">
													${apply.task_name != null ? apply.task_name : apply.test_name}
												</a>
											</td>
											<td class="c-pri text-center">
												${apply.apply_type==1?'任务测试':apply.apply_type==2?'模块测试':apply.apply_type==3?'项目测试':apply.apply_type==4?'产品测试':'未知'}
											</td>
											<td class="c-pri text-center">
												<c:if test="${apply.state == 1}">
													<span class="status-wait"><span class="label label-dot"></span> 待测试</span>
												</c:if>
												<c:if test="${apply.state == 2}">
													<span class="status-doing"><span class="label label-dot"></span> 测试中</span>
												</c:if>
												<c:if test="${apply.state == 3}">
													<span class="status-done"><span class="label label-dot"></span> 已测试</span>
												</c:if>
												<c:if test="${apply.state == 4}">
													<span class="status-delayed"><span class="label label-dot"></span> 已驳回</span>
												</c:if>
											</td>
											<td class="c-pri text-center">${apply.apply_name}</td>
											<td class="c-pri text-center">${apply.state>1 ? apply.tester : ''}</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${apply.apply_time}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-left">${apply.dismissal}</td>
											<td class="c-actions text-center">
												<c:if test="${apply.state > 0}">
													<a href="test/apply/toEdit?id=${apply.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
													<a href="test/apply/toDismissal?id=${apply.id}" class="btn" title="驳回"><i class="icon icon-reply-all"></i></a>
													<c:if test="${apply.state == 1}">
														<a href="test/apply/toReceive?id=${apply.id}" class="btn" title="领取"><i class='icon-task-start icon-play'></i></a>
													</c:if>
													<a href="test/bug/toAdd?id=${apply.task_id}" class="btn" title="提Bug"><i class='icon-task-start icon-bug'></i></a>
												</c:if>
												<c:if test="${apply.state == 0}">--</c:if>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</form>
						<!--table-footer start-->
						<div class="table-footer" style="left: 0px; bottom: 0px;">
							<!--pager srtart-->
							<jsp:include page="/WEB-INF/view/comm/pagebar_conut.jsp"></jsp:include>
							<!-- <ul class="pager"></ul> -->
							<!--pager end-->
						</div>
						<!--table-footer end-->
					</div>
					<!--main-col end-->
				</div>
			</div>
			<script></script>
		</main>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
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
</script>
</html>