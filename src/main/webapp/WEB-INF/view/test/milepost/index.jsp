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
		<title>里程碑列表</title>
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
						<a class="btn btn-link querybox-toggle ${prm.type == 10 ? 'querybox-opened':''}" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索
							<c:if test="${prm.type == 10}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
					</div>
					<!--btn-toolbar 创建里程碑和批量创建里程碑 start-->
					<!-- <div class="btn-toolbar pull-right">
					    <a href="test/milepost/toBatchAdd" class="btn btn-secondary" style="text-shadow:0 -1px 0 rgba(0,0,0,.2);"><i class="icon icon-plus"></i> 批量创建</a>
						<a href="test/milepost/toadd" class="btn btn-primary"><i class="icon icon-plus"></i>创建里程碑</a>
					</div>  -->
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
					<div class="cell load-indicator ${prm.type == 10 ? 'show':''}" id="queryBox">
							<form method="post" action="test/milepost/manage?type=10" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td class="w-150px">
												<select class="form-control chosen chosen-select" name="milepostState" id="milepostState">
													<option ${prm.milepostState==''?'selected="selected"':'' }value="">里程碑状态</option>
													<option ${prm.milepostState=='0'?'selected="selected"':'' } value="0">删除</option>
													<option ${prm.milepostState=='1'?'selected="selected"':'' } value="1">正常</option>
													<option ${prm.milepostState=='2'?'selected="selected"':'' } value="2">已确认</option>
													<option ${prm.milepostState=='3'?'selected="selected"':'' } value="3">已确认原型</option>
													<option ${prm.milepostState=='4'?'selected="selected"':'' } value="4">已验收</option>
													<option ${prm.milepostState=='5'?'selected="selected"':'' } value="5">已编写报告</option>
												    <option ${prm.milepostState=='6'?'selected="selected"':'' } value="6">已验收报告</option>
											</td>
											<td class="w-200px">
												<input type="text" name="startTime" id="startTime" value="${prm.startTime}" class="form-control form-date" placeholder="开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;">
											</td>
											<td class="w-200px">
												<input type="text" name="endTime" id="endTime" value="${prm.endTime}" class="form-control form-date" placeholder="结束时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;">
											</td>
											<td class="w-150px">
												<select data-placeholder="请选择人员" class="form-control chosen-select" name="authorId" id="authorId">
													<option value=""></option>
													<c:forEach items="${members}" var="member" varStatus="sta">
														<option  ${prm.authorId==member.id?'selected="selected"':'' } value="${member.id}">${member.name}(${member.number})</option>
													</c:forEach>
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
											<th data-flex="false" data-width="90px" style="width: 28px" class="c-id text-center" title="ID">
											ID
											</th>
										    <th data-flex="false" data-width="50px" style="width: 34px" class="c-pri" title="名称">
											名称
											</th>
											<th data-flex="false" data-width="50px" style="width:81px" class="c-name text-center" title="描述">
											描述
											</th>
											<th data-flex="false" data-width="auto" style="width:25px" class="c-name" title="开始时间">
											开始时间
											</th>
											<th data-flex="false" data-width="50px" style="width:33px" class="c-pri text-center" title="结束时间">
											结束时间
											</th>
											<th data-flex="false" data-width="50px" style="width:36px" class="c-pri text-center" title="修改时间">
											修改时间
											</th>
											<th data-flex="false" data-width="50px" style="width:35px" class="c-pri text-center" title="创建者名字">
											创建者名字
											</th>
											<th data-flex="false" data-width="50px" style="width:25px" class="c-pri text-center" title="状态">
											状态
											</th>
											<th data-flex="false" data-width="300px" style="width:51px" class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="mil" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${mil.id}</td>
											<td class="c-pri text-left"><a href="test/milepost/detail?id=${mil.id}">${mil.milepost_name}</a></td>
											<td class="c-pri text-center" title="${mil.milepost_describe}">
											${mil.milepost_describe}
											<!--<c:if test="${fn:length(mil.milepost_describe)>50 }">
										                         ${fn:substring(mil.milepost_describe, 0, 50)}...
										             </c:if>
										             <c:if test="${fn:length(mil.milepost_describe)<=50}">
										                         ${mil.milepost_describe}
			                                </c:if> -->
											</td>
											<td class="c-name text-left">
													${mil.start_time}
											</td>
											<td class="c-pri text-center">${mil.end_time}</td>
											<td class="c-pri text-center">
												${mil.edit_time}
											</td>
											<td class="c-pri text-center">${mil.author_name}</td>
											<td class="c-pri text-center">
											<c:if test="${mil.milepost_state == 0}">已删除
							                </c:if>
							                <c:if test="${mil.milepost_state == 1}">正常
							                </c:if>
							                <c:if test="${mil.milepost_state == 2}">已确认
							                </c:if>
							                <c:if test="${mil.milepost_state == 3}">已确认原型
							                </c:if>
							                <c:if test="${mil.milepost_state == 4}">已验收
							                </c:if>
							                <c:if test="${mil.milepost_state == 5}">已编写报告
							                </c:if>
							                <c:if test="${mil.milepost_state == 6}">已验收报告
							                </c:if>
											</td>
											<td class="c-actions text-center">
											<c:if test="${mil.milepost_state == 1}">
												<a href="test/milepost/toass?id=${mil.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关联模块"><i class="icon icon-plus"></i></a>
												<a href="test/milepost/toedit?id=${mil.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
              									<!-- <a href="test/milepost/tosure?id=${mil.id}" class="btn" title="确认"><i class='icon-task-start icon-play'></i></a> -->
											    <a href="test/milepost/todelete?id=${mil.id}" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>
											</c:if>
											<c:if test="${mil.milepost_state == 2}">
       											<a href="test/milepost/toass?id=${mil.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关联模块"><i class="icon icon-plus"></i></a>
											    <a href="test/milepost/toedit?id=${mil.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
												<!-- <a href="test/milepost/tosureui?id=${mil.id}" class="btn" data-toggle="tooltip" data-placement="top" title="确认里程碑和概要设计"><i class="icon-story-review icon-glasses"></i></a> -->
											    <a href="test/milepost/todelete?id=${mil.id}" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>
											</c:if>
											<c:if test="${mil.milepost_state == 3}">
												<a href="test/milepost/toass?id=${mil.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关联模块"><i class="icon icon-plus"></i></a>
											    <a href="test/milepost/toedit?id=${mil.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
												<a href="test/milepost/tovali?id=${mil.id}" class="btn" title="验收"><i class='icon-task-finish icon-checked'></i></a>
											    <a href="test/milepost/todelete?id=${mil.id}" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>
											</c:if>
											<c:if test="${mil.milepost_state == 4}">
												<a href="test/milepost/toass?id=${mil.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关联模块"><i class="icon icon-plus"></i></a>
											    <a href="test/milepost/toedit?id=${mil.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
												<a href="test/milepost/toreport?id=${mil.id}" class="btn" title="编写里程碑报告"><i class="icon-story-review icon-glasses"></i></a>
											    <a href="test/milepost/todelete?id=${mil.id}" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>
											</c:if>
											<c:if test="${mil.milepost_state == 5}">
												<a href="test/milepost/toass?id=${mil.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关联模块"><i class="icon icon-plus"></i></a>
											    <a href="test/milepost/toedit?id=${mil.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
												<a href="test/milepost/tovailreport?id=${mil.id}" class="btn" title="验收里程碑报告"><i class="icon-task-start icon-play"></i></a>
											    <a href="test/milepost/todelete?id=${mil.id}" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>
											</c:if>
											<c:if test="${mil.milepost_state == 6}">
												<a href="test/milepost/toass?id=${mil.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关联模块"><i class="icon icon-plus"></i></a>
											    <a href="test/milepost/toedit?id=${mil.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
											    <a href="test/milepost/todelete?id=${mil.id}" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>
											</c:if>
											<c:if test="${mil.milepost_state == 0}">
											<strong>无法操作</strong>
											</c:if>
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