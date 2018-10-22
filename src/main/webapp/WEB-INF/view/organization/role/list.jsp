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
		<title>角色列表</title>
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
						<a href="organization/role/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="organization/role/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">正常</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a class="btn btn-link querybox-toggle querybox-opened" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索</a>
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="organization/role/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i> 新建</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator show" id="queryBox">
							<form method="post" action="organization/role/index?type=10" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td style="width:500px">
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control searchInput" placeholder="请输入要查询的角色名称">
											</td>
											<td>
												<button type="submit" id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">搜索</button>
											</td>
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
											<th data-flex="false" data-width="90px" style="width: 90px" class="c-id text-center" title="ID">
												<a class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('id');">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri text-center" title="角色名称">
												<a class="${prm.orderColumn=='name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('name');">角色名称</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri text-center" title="角色描述">
												<a class="${prm.orderColumn=='remark'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('remark');">角色描述</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri text-center" title="创建时间">
												<a class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('create_time');">创建时间</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri text-center" title="修改时间">
												<a class="${prm.orderColumn=='update_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('update_time');">修改时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:auto" class="c-pri text-center" title="状态">
												<a class="${prm.orderColumn=='status'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('status');">状态</a>
											</th>
											<th data-flex="false" data-width="360px" style="width:360px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="user" varStatus="sta">
										<tr>
											<td class="c-id text-center">${user.id}</td>
											<td class="c-pri text-center">${user.name}</td>
											<td class="c-pri text-center">${user.remark}</td>
											<td class="c-pri text-center"><fmt:formatDate value="${user.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="c-pri text-center"><fmt:formatDate value="${user.update_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="c-pri text-center">
												<c:if test="${user.status == 1}">正常</c:if>
												<c:if test="${user.status == 0}"><span class="text-red">禁用</span></c:if>
											</td>
											<td class="c-actions text-center">
												<a href="organization/role/toEdit?id=${user.id}" class="btn" title="修改">修改</a>
												<a href="organization/role/toAuth?id=${user.id}" class="btn" title="授权">授权</a>
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
</script>