<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>权限列表</title>
<%@ include file="/WEB-INF/view/comm/cssjs.jsp"%>
</head>
<body>
	<!--header start-->
	<header id="header">
		<%@ include file="/WEB-INF/view/comm/main_header.jsp"%>
		<%@ include file="/WEB-INF/view/comm/sub_header.jsp"%>
	</header>
	<!--header end-->
	<main id="main" class="in">
	<div class="container">
		<!--mainMenu start-->
		<div id="mainMenu" class="clearfix">
			<div class="btn-toolbar pull-left">
				<a href="organization/tablefield/index?type=2"
					class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}"> <span
					class="text">所有</span> <c:if test="${prm.type == 2}">
						<span class="label label-light label-badge">${pageList.totalCounts}</span>
					</c:if>
				</a> <a href="organization/tablefield/index?type=1"
					class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}"> <span
					class="text">正常</span> <c:if test="${prm.type == 1}">
						<span class="label label-light label-badge">${pageList.totalCounts}</span>
					</c:if>
				</a> <a href="organization/tablefield/index?type=0"
					class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}"> <span
					class="text">已删除</span> <c:if test="${prm.type == 0}">
						<span class="label label-light label-badge">${pageList.totalCounts}</span>
					</c:if>
				</a>
				<%-- <a
					class="btn btn-link querybox-toggle ${prm.type == 10 ? 'querybox-opened':''}"
					id="bysearchTab"><i class="icon icon-search muted"></i> 搜索 <c:if
						test="${prm.type == 10}">
						<span class="label label-light label-badge">${pageList.totalCounts}</span>
					</c:if> </a> --%>
			</div>
			<!--btn-toolbar start-->
			<div class="btn-toolbar pull-right">
				<a href="organization/tablefield/toAdd" class="btn btn-primary"><i
					class="icon icon-plus"></i> 新建表字段</a>
			</div>
			<!--btn-toolbar end-->
		</div>
		<!--mainMenu end-->
		<div id="mainContent" class="main-row fade in">
			<!--main-col start-->
			<div class="main-col">
				<div class="cell load-indicator ${prm.type == 10 ? 'show':''}"
					id="queryBox">
					<form method="post" action="organization/tablefield/index?type=10"
						id="searchForm" class="search-form">
						<table class="table table-condensed table-form" id="task-search">
							<tbody>
								<tr>
									<td style="width: 150px"><select
										class="form-control chosen chosen-select" name="searchType"
										id="searchType">
											<option ${prm.searchType=='1'?'selected="selected"':'' }
												value="1">表字段名称/ID</option>
											<option ${prm.searchType=='2'?'selected="selected"':'' }
												value="2">表字段备注</option>
									</select></td>
									<td style="width: 500px"><input type="text" name="search"
										id="search" value="${prm.search}"
										class="form-control  searchInput"
										placeholder="选择后请输入要查询的表字段名称/ID 或 备注"></td>
									<td style="width: 100px"><select
										class="form-control chosen chosen-select" name="state"
										id="state">
											<option value="">状态</option>
											<option ${prm.bugrank=='0'?'selected="selected"':'' }
												value="0">已删除</option>
											<option ${prm.bugrank=='1'?'selected="selected"':'' }
												value="1">正常</option>
									</select></td>
								</tr>
								<tr>
									<td colspan="8" class="text-center form-actions">
										<button type="submit" id="submit"
											class="btn btn-wide btn-primary" data-loading="稍候...">搜索</button>
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
									<th data-flex="false" data-width="200px" style="width: 200px"
										class="c-id " title="ID"><a href="javascript:void(0)"
										onclick="pageOrder('id');"
										class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">ID</a>
									</th>
									<th data-flex="false" data-width="50px" style="width: 250px"
										class="c-pri " title="表名称"><a href="javascript:void(0)"
										onclick="pageOrder('table_name');"
										class="${prm.orderColumn=='table_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">表名称</a>
									</th>
									<th data-flex="false" data-width="50px" style="width: 250px"
										class="c-pri " title="字段名称"><a href="javascript:void(0)"
										onclick="pageOrder('field_name');"
										class="${prm.orderColumn=='field_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">字段名称</a>
									</th>
									<th data-flex="false" data-width="50px" style="width: 300px"
										class="c-pri " title="字段描述"><a href="javascript:void(0)"
										onclick="pageOrder('field_desc');"
										class="${prm.orderColumn=='field_desc'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">字段描述</a>
									</th>
									<th data-flex="false" data-width="50px" style="width: 300px"
										class="c-pri text-center" title="状态"><a
										href="javascript:void(0)" onclick="pageOrder('state');"
										class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">状态</a>
									</th>
									<th data-flex="false" data-width="160px" style="width: 230px"
										class="c-actions text-center" title="操作">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageList.pageResult}" var="project"
									varStatus="sta">
									<tr>
										<td class="c-id cell-id">${project.id}</td>
										<td class="c-pri text-left">${project.table_name}</td>
										<td class="c-name text-left">${project.field_name}</td>
										<td class="c-name text-left">${project.field_desc}</td>
										<td class="c-pri text-center">
												<c:if test="${project.state == 1}">正常</c:if>
												<c:if test="${project.state == 0}"><span class="text-red">已删除</span></c:if>
											</td>
										<td class="c-actions text-center"><c:if
												test="${project.state == '1'}">
												<a href="organization/tablefield/toAdd?id=${project.id}"
													class="btn" title="编辑"><i
													class="icon-common-edit icon-edit"></i> 编辑</a>
												<a href="javascript:void(0)" onclick="del(${project.id})"
													class="btn" title="删除"><i
													class="icon-common-delete icon-trash"></i> 删除</a>
											</c:if> <c:if test="${project.state != '1'}">--</c:if></td>
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
					<!--pager end-->
				</div>
				<!--table-footer end-->
			</div>
			<!--main-col end-->
		</div>
	</div>
	<script></script> </main>
	<%@ include file="/WEB-INF/view/comm/footer.jsp"%>
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
	/* $('.pager').pager({
	    page: ${pageList.currentPage},
	    recTotal: ${pageList.totalCounts},
	    recPerPage: ${pageList.pageSize},
	    pageSizeOptions: [10, 20, 30, 50, 100],
	    lang: 'zh_cn',
	    linkCreator: "organization/privilege/index?type=${prm.type}&currentPage={page}&pageSize={recPerPage}&search=${prm.search}&orderColumn=${prm.orderColumn}&orderByValue=${prm.orderByValue}"
	}); */
	function del(id){
		if(confirm("确认删除？")){
			$.ajaxSettings.async = false;
			$.getJSON("organization/tablefield/del?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
</script>