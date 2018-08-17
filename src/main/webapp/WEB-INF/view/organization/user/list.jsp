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
		<title>用户列表</title>
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
						<a href="organization/user/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="organization/user/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">正常</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a class="btn btn-link querybox-toggle querybox-opened" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索</a>
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="organization/user/sync" class="btn btn-secondary" style="text-shadow:0 -1px 0 rgba(0,0,0,.2);"><!-- <i class="icon icon-plus"></i> -->同步用户信息</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator show" id="queryBox">
							<form method="post" action="organization/user/index?type=10" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td style="width:360px">
												<select data-placeholder="请选择员工状态" class="form-control chosen-select" name="status" id="status">
													<option value=""></option>
													<option ${prm.status=='1'?'selected="selected"':'' } value="1">正常</option>
													<option ${prm.status=='2'?'selected="selected"':'' } value="2">已离职</option>
												</select>
											</td>
											<td style="width:360px">
												<select class="form-control chosen chosen-select" name="search_type" id="search_type">
													<option ${prm.search_type=='1'?'selected="selected"':'' } value="1">姓名</option>
													<option ${prm.search_type=='2'?'selected="selected"':'' } value="2">工号</option>
													<option ${prm.search_type=='3'?'selected="selected"':'' } value="3">电话</option>
													<option ${prm.search_type=='4'?'selected="selected"':'' } value="4">邮箱</option>
												</select>
											</td>
											<td style="width:500px">
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control searchInput" placeholder="请输入要查询的内容">
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
												<a href="${pageList.desAction}&orderColumn=id&orderByValue=${prm.orderColumn=='id'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri text-center" title="名称">
												<a  href="${pageList.desAction}&orderColumn=name&orderByValue=${prm.orderColumn=='name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">名称</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri text-center" title="工号">
												<a  href="${pageList.desAction}&orderColumn=number&orderByValue=${prm.orderColumn=='number'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='number'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">工号</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri text-center" title="电话">
												<a  href="${pageList.desAction}&orderColumn=phone&orderByValue=${prm.orderColumn=='phone'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='phone'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">电话</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri text-center" title="邮箱">
												<a  href="${pageList.desAction}&orderColumn=email&orderByValue=${prm.orderColumn=='email'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='email'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">邮箱</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:auto" class="c-pri text-center" title="性别">
												<a  href="${pageList.desAction}&orderColumn=sex&orderByValue=${prm.orderColumn=='sex'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='sex'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">性别</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:auto" class="c-pri text-center" title="状态">
												<a  href="${pageList.desAction}&orderColumn=status&orderByValue=${prm.orderColumn=='status'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='status'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">状态</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:auto" class="c-pri text-center" title="状态">
												<a  href="${pageList.desAction}&orderColumn=oad.NAME&orderByValue=${prm.orderColumn=='oad.NAME'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='oad.NAME'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">所属OA部门</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:auto" class="c-pri text-center" title="状态">
												<a  href="${pageList.desAction}&orderColumn=d.name&orderByValue=${prm.orderColumn=='d.name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='d.name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">所属团队</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:auto" class="c-pri text-center" title="状态">
												<a  href="${pageList.desAction}&orderColumn=r.name&orderByValue=${prm.orderColumn=='r.name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='r.name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">角色</a>
											</th>
											<th data-flex="false" data-width="360px" style="width:200px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="user" varStatus="sta">
										<tr>
											<td class="c-id text-center">${user.id}</td>
											<td class="c-pri text-center">${user.name}</td>
											<td class="c-pri text-center">${user.number}</td>
											<td class="c-pri text-center">${user.phone}</td>
											<td class="c-pri text-center">${user.email}</td>
											<td class="c-pri text-center">${user.sex}</td>
											<td class="c-pri text-center">${user.status==0?'正常':'<span class="text-red">已离职</span>'}</td>
											<td class="c-pri text-center">${user.oldDptName}</td>
											<td class="c-pri text-center">${user.dptName}</td>
											<td class="c-pri text-center">${user.roleName}</td>
											<td class="c-actions text-center">
												<c:if test="${user.status==0}">
													<a href="organization/user/toConfig?id=${user.id}" class="btn" title="设置部门">设置</a>
												</c:if>
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
	$('.pager').pager({
	    page: ${pageList.currentPage},
	    recTotal: ${pageList.totalCounts},
	    recPerPage: ${pageList.pageSize},
	    pageSizeOptions: [10, 20, 30, 50, 100],
	    lang: 'zh_cn',
	    linkCreator: "organization/user/index?type=${prm.type}&currentPage={page}&pageSize={recPerPage}&search=${prm.search}&orderColumn=${prm.orderColumn}&orderByValue=${prm.orderByValue}"
	});

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