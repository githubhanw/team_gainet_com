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
		<title>发布列表</title>
    	<%@ include file="/WEB-INF/view/comm/cssjs.jsp" %>
	</head>
	<body>
	    <header id="header">
	    	<%@ include file="/WEB-INF/view/comm/main_header.jsp" %>
	    	<%@ include file="/WEB-INF/view/comm/sub_header.jsp" %>
	    </header>
		<main id="main" class="in">
			<div class="container">
				<div id="mainMenu" class="clearfix">
					<div class="btn-toolbar pull-left">
						<a href="release/index?type=4" class="btn btn-link ${prm.type == 4 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 4}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="release/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">未发布</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="release/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">待发布</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="release/index?type=2" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">已发布</span>
							<c:if test="${prm.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<%-- <a href="release/index?type=3" class="btn btn-link ${prm.type == 3 ? 'btn-active-text':''}">
							<span class="text">延期发布</span>
							<c:if test="${prm.type == 3}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a> --%>
					</div>
					<div class="btn-toolbar pull-right" style="maigin-right: 30px">
						<a href="release/toSubmit" class="btn btn-primary"><i class="icon"></i> 提交更新</a>
						<a href="release/toReceive" class="btn btn-primary"><i class="icon"></i> 接收更新</a>
						<a href="release/toConfirm" class="btn btn-primary"><i class="icon"></i> 确认更新</a>
					</div>
					    
				</div>
				<div id="mainContent" class="main-row fade in">
					<div class="main-col">
						<form class="main-table table-task skip-iframe-modal" method="post"
							id="projectTaskForm" data-ride="table">
							<div class="table-responsive">
								<table class="table has-sort-head" id="taskList" data-fixed-left-width="550" data-fixed-right-width="160" style="border:2px solid #fff">
									<thead>
										<tr>
											<th data-flex="false" data-width="90px" style="width:90px" class="c-id text-center" title="ID">
												<a href="javascript:void(0)" onclick="pageOrder('id');" 
														class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:250px" class="c-pri " title="发布名称">
												<a  href="javascript:void(0)" onclick="pageOrder('name');" 
														class="${prm.orderColumn=='name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">发布名称</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:150px" class="c-pri " title="版本号">
												<a  href="javascript:void(0)" onclick="pageOrder('version');" 
														class="${prm.orderColumn=='version'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">版本号</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="创建时间">
												<a  href="javascript:void(0)" onclick="pageOrder('create_time');" 
														class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">创建时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:90px" class="c-name text-center" title="状态">
												<a  href="javascript:void(0)" onclick="pageOrder('state');" 
														class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">状态</a>
											</th>
											<th data-flex="false" data-width="300px" style="width:200px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="release" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${release.id}</td>
											<td class="c-name text-left">${release.name}
											  <%-- <a href="release/details?id=${release.id}" data-toggle="tooltip" data-placement="top" title="${need.name}">
													${release.name}
											  </a> --%>
											</td>
											<td class="c-name text-left">${release.version}</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${release.create_time}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center">
													${release.state == 0 ? '未发布' : release.state == 1 ? '待发布' : release.state == 2 ? '已发布' : release.state == 3 ? '延期发布' : '未知'}
											</td>
											<td class="c-actions text-center">
											   <c:if test="${release.state == 0 }">
												<a href="release/toEdit?id=${release.id}" class="btn" title="编辑更新"><i class="icon-common-edit icon-edit"></i></a>	
											   </c:if>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</form>
						<div class="table-footer" style="left: 0px; bottom: 0px;">
							<jsp:include page="/WEB-INF/view/comm/pagebar_conut.jsp"></jsp:include>
						</div>
					</div>
				</div>
			</div>
		</main>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<!-- <script>
	$("#bysearchTab").click(function(){
		if($(this).hasClass("querybox-opened")){
			$(this).removeClass("querybox-opened")
			$("#queryBox").removeClass("show")
		}else{
			$(this).addClass("querybox-opened")
			$("#queryBox").addClass("show")
		}
	});
</script> -->