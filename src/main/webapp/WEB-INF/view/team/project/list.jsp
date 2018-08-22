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
						<a href="team/project/index?type=2" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/project/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">正常</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/project/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">已删除</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="team/project/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i> 新建项目</a>
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
											<th data-flex="false" data-width="50px" style="width:300px" class="c-pri " title="项目名称">
												<a  href="${pageList.desAction}&orderColumn=project_name&orderByValue=${prm.orderColumn=='project_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='project_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">项目名称</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name " title="所属公司">
												<a  href="${pageList.desAction}&orderColumn=company&orderByValue=${prm.orderColumn=='company'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='company'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">所属公司</a>
											</th>
											<th data-flex="false" data-width="50px" style="width: auto" class="c-pri " title="项目负责人">
												<a  href="${pageList.desAction}&orderColumn=member_id&orderByValue=${prm.orderColumn=='member_id'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='member_id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">项目负责人</a>
											</th>
											<th data-flex="false" data-width="50px" style="width: auto" class="c-pri " title="备注">
												<a  href="${pageList.desAction}&orderColumn=remark&orderByValue=${prm.orderColumn=='remark'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='remark'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">备注</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="状态">
												<a  href="${pageList.desAction}&orderColumn=state&orderByValue=${prm.orderColumn=='state'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">状态</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="项目创建时间">
												<a  href="${pageList.desAction}&orderColumn=create_time&orderByValue=${prm.orderColumn=='create_time'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">项目创建时间</a>
											</th>
											<%-- <th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="未完成需求">
												<a  href="${pageList.desAction}&orderColumn=create_time&orderByValue=${prm.orderColumn=='create_time'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">未完成需求</a>
											</th>
											<th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="未完成任务">
												<a  href="${pageList.desAction}&orderColumn=create_time&orderByValue=${prm.orderColumn=='create_time'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">未完成任务</a>
											</th> --%>
											<%-- <th data-flex="false" data-width="auto" style="width: auto" class="c-name text-center" title="未解决Bug">
												<a  href="${pageList.desAction}&orderColumn=create_time&orderByValue=${prm.orderColumn=='create_time'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">未解决Bug</a>
											</th> --%>
											<th data-flex="false" data-width="160px" style="width: 330px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="project" varStatus="sta">
										<tr>
											<td class="c-id cell-id">${project.id}</td>
											<%-- <td class="c-pri text-left"><a href="team/project/detail?id=${project.id}">${project.project_name}</a></td> --%>
											<td class="c-pri text-left">${project.project_name}</td>
											<td class="c-name text-left">${project.company}</td>
											<td class="c-pri text-left">${project.member_name}</td>
											<td class="c-pri text-left">${project.remark}</td>
											<td class="c-assignedTo has-btn text-center">
												${project.state == 1 ? '正常' : '已删除'}
											</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${project.create_time}" pattern="yyyy-MM-dd" /></td>
											<!-- <td class="c-assignedTo has-btn text-center">1</td>
											<td class="c-assignedTo has-btn text-center">1</td> -->
											<td class="c-actions text-center">
												<c:if test="${project.state == '1'}">
													<a href="team/project/toAdd?id=${project.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i> 编辑</a>
													<a href="javascript:void(0)" onclick="del(${project.id})" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i> 删除</a>
													<a href="team/need/toAdd?project_id=${project.id}" class="btn" title="提需求"><i class="icon icon-plus"></i> 提需求</a>
													<a href="team/need/toBatchAdd?project_id=${project.id}" class="btn" title="批量提需求"><i class="icon icon-plus"></i> 批量提需求</a>
												</c:if>
												<c:if test="${project.state != '1'}">--</c:if>
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
	    linkCreator: "team/project/index?type=${prm.type}&currentPage={page}&pageSize={recPerPage}&search=${prm.search}&orderColumn=${prm.orderColumn}&orderByValue=${prm.orderByValue}"
	});
	function del(id){
		if(confirm("确认删除？")){
			$.ajaxSettings.async = false;
			$.getJSON("team/project/del?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
</script>