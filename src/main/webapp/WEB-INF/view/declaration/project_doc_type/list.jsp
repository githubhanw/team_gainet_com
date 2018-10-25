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
		<title>项目文档类型列表</title>
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
						<a href="declaration/doctype/index?type=2" class="btn btn-link ${pageList.queryCondition.type == 2 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${pageList.queryCondition.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/doctype/index?type=1" class="btn btn-link ${pageList.queryCondition.type == 1 ? 'btn-active-text':''}">
							<span class="text">正常</span>
							<c:if test="${pageList.queryCondition.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/doctype/index?type=0" class="btn btn-link ${pageList.queryCondition.type == 0 ? 'btn-active-text':''}">
							<span class="text">已删除</span>
							<c:if test="${pageList.queryCondition.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
					    <a href="declaration/doctype/toModify" class="btn btn-primary"><i class="icon"></i>文档类型与成果类型关系</a>
						<a href="declaration/doctype/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i>新建文档类型</a>
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
												<a class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('id');">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width: auto" class="c-pri " title="文档类型名称">
											
												<a class="${prm.orderColumn=='project_doc_type'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('project_doc_type');">文档类型名称</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="状态">
												<a class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('state');">状态</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="项目创建时间">
												<a class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('create_time');">创建时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="项目结束时间">
												<a class="${prm.orderColumn=='update_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('update_time');">修改时间</a>
											</th>
											<th data-flex="false" data-width="160px" style="width: 260px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="item" varStatus="sta">
										<tr>
											<td class="c-id cell-id">${item.id}</td>
											<td class="c-pri text-left" title="${item.project_doc_type}">${item.project_doc_type}</td>
											<td class="c-assignedTo has-btn text-center">${item.state == 1 ? '正常' : '已删除'}</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${item.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${item.update_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="c-actions text-center">
												<c:if test="${item.state == 1}">
													<a href="declaration/doctype/toAdd?id=${item.id}" class="btn" title="修改">修改</a>
													<a href="javascript:void(0)" onclick="del(${item.id})" class="btn" title="删除">删除</a>
												</c:if>
												<c:if test="${item.state != 1}">--</c:if>
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
	function del(id){
		$.ajaxSettings.async = false;
		$.getJSON("declaration/doctype/del?id=" + id + "&r=" + Math.random(), function(data) {
			alert(data.message);
			if(data.code == 0){
				window.location.reload();
			}
		});
		$.ajaxSettings.async = true;
	}
</script>
</html>