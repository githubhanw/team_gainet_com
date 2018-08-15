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
		<title>授权</title>
		<style>
			.table-bymodule select.form-control {height:250px}
			.group-item {display:block; width:220px; float:left; font-size: 14px}
			.group-item .checkbox-inline label{padding-left:8px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;}
			.table.table-form tbody > tr:last-child td {border-top: 1px solid #ddd}
			@-moz-document url-prefix(){.table.table-form tbody > tr:last-child td, .table.table-form tbody > tr:last-child th {border-bottom: 1px solid #ddd}}
		</style>
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
				<div id="mainContent" class="main-content">
					<div class="center-block">
						<div class="main-header">
							<h2>
								<input type="hidden" value="${entity.id}" id="id" name="id">
								<span class="label label-id">${entity.id}</span>
								<a href="organization/role/index">${entity.name}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 授权</small>
							</h2>
						</div>
						<table class="table table-hover table-striped table-bordered" id="privList">
							<thead>
								<tr>
									<th class="text-center w-230px">模块</th>
									<td class="text-center">方法</td>
								</tr>
							</thead>
							<tbody>
								<!-- <form class="main-table table-task skip-iframe-modal" method="post"> -->
								<c:forEach items="${allPrivileges}" var="p" varStatus="sta">
									<c:if test="${p.parent_id == 0}">
										<tr class="even">
											<th class="text-middle text-right w-150px">
												<div class="checkbox-primary checkbox-inline checkbox-right check-all">
													${p.name}
												</div>
											</th>
											<td id="${p.id}" class="pv-10px">
												<c:forEach items="${allPrivileges}" var="sub" varStatus="sta">
													<c:if test="${sub.parent_id == p.id}">
														<div class="group-item">
															<div class="checkbox-primary checkbox-inline">
																<c:set var="checked" value="false"></c:set>
																<c:forEach items="${entity.privileges}" var="ep">
																	<c:if test="${ep.id == sub.id}">
																		<c:set var="checked" value="true"></c:set>
																	</c:if>
																</c:forEach>
																<input type="checkbox" name="actions[${p.id}][]" ${checked ? 'checked="checked"' : "" } value="${sub.id}" id="actions[${p.id}]${sub.id}"> 
																<label for="actions[${p.id}]${sub.id}">${sub.name}</label>
															</div>
														</div>
													</c:if>
												</c:forEach>
											</td>
										</tr>
									</c:if>
								</c:forEach>
								<!-- </form> -->
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</main>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>