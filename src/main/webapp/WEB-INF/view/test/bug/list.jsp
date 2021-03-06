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
		<title>Bug列表</title>
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
						<a href="test/bug/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="test/bug/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">待处理</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="test/bug/index?type=2" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">待验证</span>
							<c:if test="${prm.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="test/bug/index?type=3" class="btn btn-link ${prm.type == 3 ? 'btn-active-text':''}">
							<span class="text">已验证</span>
							<c:if test="${prm.type == 3}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="test/bug/index?type=4" class="btn btn-link ${prm.type == 4 ? 'btn-active-text':''}">
							<span class="text">已删除</span>
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
						<a href="test/bug/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i> 提Bug</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator ${prm.type == 10 ? 'show':''}" id="queryBox">
							<form method="post" action="test/bug/index?type=10" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td class="w-70px">
												<select class="form-control chosen chosen-select" name="bugfen" id="bugfen">
													<option value="">BUG分类</option>
													<option ${prm.bugfen=='0'?'selected="selected"':'' } value="0">正常</option>
													<option ${prm.bugfen=='1'?'selected="selected"':'' } value="1">线上bug</option>
													<option ${prm.bugfen=='2'?'selected="selected"':'' } value="2">线上线下bug</option>
												</select>
											</td>
											<td class="w-70px">
												<select class="form-control chosen chosen-select" name="bugrank" id="bugrank">
													<option value="">BUG等级</option>
													<option ${prm.bugrank=='0'?'selected="selected"':'' } value="0">A</option>
													<option ${prm.bugrank=='1'?'selected="selected"':'' } value="1">B</option>
													<option ${prm.bugrank=='2'?'selected="selected"':'' } value="2">C</option>
												</select>
											</td>
											<td class="w-70px">
												<select class="form-control chosen chosen-select" name="bugtype" id="bugtype">
													<option value="">BUG类型</option>
													<option ${prm.bugtype=='0'?'selected="selected"':'' } value="0">功能类</option>
													<option ${prm.bugtype=='1'?'selected="selected"':'' } value="1">安全类</option>
													<option ${prm.bugtype=='2'?'selected="selected"':'' } value="2">界面类</option>
													<option ${prm.bugtype=='3'?'selected="selected"':'' } value="3">信息类</option>
													<option ${prm.bugtype=='4'?'selected="selected"':'' } value="4">数据类</option>
													<option ${prm.bugtype=='5'?'selected="selected"':'' } value="5">流程类</option>
													<option ${prm.bugtype=='6'?'selected="selected"':'' } value="6">需求问题</option>
												</select>
											</td>
											<td class="w-70px">
												<select class="form-control chosen chosen-select" name="solution" id="solution">
													<option value="">解决方案</option>
													<option ${prm.solution=='0'?'selected="selected"':'' } value="0">问题已修复</option>
													<option ${prm.solution=='1'?'selected="selected"':'' } value="1">重复问题</option>
													<option ${prm.solution=='2'?'selected="selected"':'' } value="2">不是问题</option>
													<option ${prm.solution=='3'?'selected="selected"':'' } value="3">需求如此</option>
													<option ${prm.solution=='4'?'selected="selected"':'' } value="4">延期处理</option>
												</select>
											</td>
											<td class="w-70px">
												<select class="form-control chosen chosen-select" name="solvestatus" id="solvestatus">
													<option value="">解决状态</option>
													<option ${prm.solvestatus=='0'?'selected="selected"':'' } value="0">待处理</option>
													<option ${prm.solvestatus=='1'?'selected="selected"':'' } value="1">待验证</option>
													<option ${prm.solvestatus=='2'?'selected="selected"':'' } value="2">已验证</option>
													<option ${prm.solvestatus=='2'?'selected="selected"':'' } value="3">已删除</option>
												</select>
											</td>
											<td class="w-50px">
												<input type="text" name="createtime" id="createtime" value="${prm.createtime}" class="form-control form-date" placeholder="开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;">
											</td>
											<td class="w-50px">
												<input type="text" name="endtime" id="endtime" value="${prm.endtime}" class="form-control form-date" placeholder="结束时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;">
											</td>
											<td class="w-100px">
												<select data-placeholder="请选择人员" class="form-control chosen-select" name="member" id="member">
													<option value=""></option>
													<c:forEach items="${members}" var="member" varStatus="sta">
														<option value="${member.id}">${member.name}(${member.number})</option>
													</c:forEach>
												</select>
											</td>
											<td class="w-60px">
												<select class="form-control chosen chosen-select" name="memberState" id="memberState">
													<option ${prm.memberState=='0'?'selected="selected"':'' } value="0">类型:创建者</option>
													<option ${prm.memberState=='1'?'selected="selected"':'' } value="1">类型:开发者</option>
													<option ${prm.memberState=='2'?'selected="selected"':'' } value="2">类型:解决者</option>
												</select>
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
											<th data-flex="false" data-width="90px" style="width: 90px" class="c-id text-center" title="ID">
												<a class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('id');">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri" title="bug标题">
												<a class="${prm.orderColumn=='bugdes'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('bugdes');">bug标题</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-name text-center" title="任务ID">
												<a class="${prm.orderColumn=='task_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('task_name');">任务ID</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name" title="bug状态">
												<a class="${prm.orderColumn=='solvestatus'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('solvestatus');">bug状态</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name" title="bug状态">
												<a class="${prm.orderColumn=='solution'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('solution');">解决方案</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:120px" class="c-pri text-center" title="bug产品">
												<a class="${prm.orderColumn=='bugproject'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('bugproject');">bug产品</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:90px" class="c-pri text-center" title="bug等级">
												<a class="${prm.orderColumn=='bugrank'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('bugrank');">bug等级</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:100px" class="c-pri text-center" title="创建时间">
												<a class="${prm.orderColumn=='createtime'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('createtime');">创建时间</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-pri text-center" title="创建者">
												<a class="${prm.orderColumn=='creater'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('creater');">创建者</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-pri text-center" title="开发者">
												<a class="${prm.orderColumn=='developer'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('developer');">开发者</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-pri text-center" title="解决者">
												<a class="${prm.orderColumn=='solver'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('solver');">解决者</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-pri text-center" title="解决时间">
												<a class="${prm.orderColumn=='solvetime'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('solvetime');">解决时间</a>
											</th>
											<th data-flex="false" data-width="300px" style="width:160px" class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="bug" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${bug.id}</td>
											<td class="c-pri text-left">
												<a href="test/bug/toDetail?id=${bug.id}">${bug.bugdes}</a>
											</td>
											<td class="c-pri text-center">
												<a href="team/task/detail?id=${bug.taskid}">${bug.taskid}</a>
											</td>
											<td class="c-name text-left">
												<span class="${bug.solvestatus == 0 ? 'status-wait' : bug.solvestatus == 1 ? 'status-doing' : bug.solvestatus == 2 ? 'status-done' : bug.solvestatus == 3 ? 'status-cancel':''}">
													<span class="label label-dot"></span>
													${bug.solvestatus == 0 ? '待处理' : bug.solvestatus == 1 ? '待验证' 
													: bug.solvestatus == 2 ? '已验证' : bug.solvestatus == 3 ? '已删除'
													: bug.solvestatus == 4 ? '待审核':'未知'}
												</span>
											</td>
											<td class="c-pri text-center">
												<c:if test="${bug.solvestatus > 0}">
													${bug.solution==0?'问题已修复':bug.solution==1?'重复问题':bug.solution==2?'不是问题':bug.solution==3?'需求如此':bug.solution==4?'延期处理':'未知'}
												</c:if>
											</td>
											<td class="c-pri text-center">${bug.bugproject}</td>
											<td class="c-pri text-center">
												<c:if test="${bug.bugrank=='0'}">A</c:if>
												<c:if test="${bug.bugrank=='1'}">B</c:if>
												<c:if test="${bug.bugrank=='2'}">C</c:if>
											</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${bug.createtime}" pattern="yyyy-MM-dd" /></td>
											<td class="c-pri text-center">${bug.creater}</td>
											<td class="c-pri text-center">${bug.developer}</td>
											<td class="c-pri text-center">${bug.solver}</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${bug.solvetime}" pattern="yyyy-MM-dd" /></td>									
											<td class="c-actions text-center">
											<c:if test="${bug.solvestatus==0}">
												<a href="test/bug/toSolve?id=${bug.id}" class="btn" title="解决"><i class='icon-task-finish icon-checked'></i></a>
											</c:if>
											<c:if test="${bug.solvestatus==1}">
												<a href="test/bug/toVali?id=${bug.id}" class="btn" title="验证"><i class="icon-story-review icon-glasses"></i></a>
											</c:if>
											<c:if test="${bug.solvestatus==0 || bug.solvestatus==1}">
												<a href="test/bug/toEdit?id=${bug.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
											</c:if>
											<c:if test="${bug.solvestatus!=3}">
												<a href="test/bug/toDelete?id=${bug.id}" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>
											</c:if>
											<c:if test="${bug.solvestatus==3}">
												无操作
											</c:if>
											<c:if test="${bug.solvestatus==4}">
												<a href="test/bug/toCheckBug?id=${bug.id}" class="btn" title="审核"><i class='icon-task-close'>审</i></a>
											</c:if>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!--table-responsive end-->
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