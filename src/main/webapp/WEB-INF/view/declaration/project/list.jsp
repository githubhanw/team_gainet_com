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
		<title>项目列表</title>
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
						<a href="declaration/project/index?type=2" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/project/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">正常</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/project/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">已删除</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/project/index?type=3" class="btn btn-link ${prm.type == 3 ? 'btn-active-text':''}">
							<span class="text">研究</span>
							<c:if test="${prm.type == 3}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/project/index?type=4" class="btn btn-link ${prm.type == 4 ? 'btn-active-text':''}">
							<span class="text">开发</span>
							<c:if test="${prm.type == 4}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/project/index?type=5" class="btn btn-link ${prm.type == 5 ? 'btn-active-text':''}">
							<span class="text">完成</span>
							<c:if test="${prm.type == 5}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="declaration/project/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i>新建项目</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<form class="main-table table-task skip-iframe-modal" method="post"
							id="projectTaskForm" data-ride="table">
							<!--table-responsive start-->
							<div class="table-responsive">
								<table class="table has-sort-head" id="taskList"
									data-fixed-left-width="550" data-fixed-right-width="160">
									<thead>
										<tr>
											<th data-flex="false" data-width="90px" style="width: 90px" class="c-id " title="ID">
												<a href="${pageList.desAction}&orderColumn=id&orderByValue=${prm.orderColumn=='id'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width: auto" class="c-pri " title="项目申报号">
												<a  href="${pageList.desAction}&orderColumn=declaration_number&orderByValue=${prm.orderColumn=='declaration_number'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='declaration_number'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">项目申报号</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:300px" class="c-pri " title="项目名称">
												<a  href="${pageList.desAction}&orderColumn=project_name&orderByValue=${prm.orderColumn=='project_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='project_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">项目名称</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name " title="成果个数">
												<a  href="${pageList.desAction}&orderColumn=resultCount&orderByValue=${prm.orderColumn=='resultCount'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='resultCount'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">成果个数</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name " title="所属公司">
												<a  href="${pageList.desAction}&orderColumn=company&orderByValue=${prm.orderColumn=='company'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='company'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">所属公司</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="阶段">
												<a  href="${pageList.desAction}&orderColumn=stage&orderByValue=${prm.orderColumn=='stage'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='stage'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">阶段</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="状态">
												<a  href="${pageList.desAction}&orderColumn=state&orderByValue=${prm.orderColumn=='state'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">状态</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="项目开始时间">
												<a  href="${pageList.desAction}&orderColumn=start_date&orderByValue=${prm.orderColumn=='start_date'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='start_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">项目开始时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="项目结束时间">
												<a  href="${pageList.desAction}&orderColumn=end_date&orderByValue=${prm.orderColumn=='end_date'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='end_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">项目结束时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="项目创建时间">
												<a  href="${pageList.desAction}&orderColumn=create_time&orderByValue=${prm.orderColumn=='create_time'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">项目创建时间</a>
											</th>
											<th data-flex="false" data-width="160px" style="width: 260px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="project" varStatus="sta">
										<tr>
											<td class="c-id cell-id">${project.id}</td>
											<td class="c-pri text-left">${project.declaration_number}</td>
											<td class="c-pri text-left"><a href="declaration/project/detail?id=${project.id}">${project.project_name}</a></td>
											<td class="c-name text-left">${project.resultCount}</td>
											<td class="c-name text-left">${project.company}</td>
											<td class="c-assignedTo has-btn text-center">${project.stage}</td>
											<td class="c-assignedTo has-btn text-center">${project.state == 1 ? '正常' : '已删除'}</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${project.start_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${project.end_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${project.create_time}" pattern="yyyy-MM-dd" /></td>
											<td class="c-actions text-center">
												<c:if test="${project.state == 1}">
													<a href="declaration/project/toAdd?id=${project.id}" class="btn" title="修改">修改</a>
													<a href="javascript:void(0)" onclick="del(${project.id})" class="btn" title="删除">删除</a>
												</c:if>
												<c:if test="${project.state != 1}">--</c:if>
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
	    linkCreator: "declaration/project/index?type=${prm.type}&currentPage={page}&pageSize={recPerPage}&search=${prm.search}&orderColumn=${prm.orderColumn}&orderByValue=${prm.orderByValue}"
	});
	function del(id){
		$.ajaxSettings.async = false;
		$.getJSON("declaration/project/del?id=" + id + "&r=" + Math.random(), function(data) {
			alert(data.message);
			if(data.code == 0){
				window.location.reload();
			}
		});
		$.ajaxSettings.async = true;
	}
</script>