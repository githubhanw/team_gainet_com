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
		<title>
			<c:if test="${obj.id==null}">添加文档类型</c:if>
			<c:if test="${obj.id > 0}">修改文档类型</c:if>
		</title>
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
								<c:if test="${obj.id==null}">添加文档类型</c:if>
								<c:if test="${obj.id > 0}">修改文档类型</c:if>
							</h2>
						</div>
						<form class="load-indicator main-form form-ajax" id="createForm" method="post">
							<table class="table table-form">
								<tbody>
									<tr>
										<th>文档类型名称</th>
										<td class="required">
											<input type="text" id="name" value="${obj.projectDocType}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<td colspan="3" class="text-center form-actions">
											<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
											<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">返回</a>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			</div>
		</main>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
$("#submit").click(function(){
	var projectDocType = encodeURI(encodeURI($("#name").val(),"utf-8"));
	$.ajaxSettings.async = false;
	$.getJSON("declaration/doctype/addOrUpd?id=${obj.id}&projectDocType=" + projectDocType + "&r=" + Math.random(), 
			function(data) {
		alert(data.message);
	});
	$.ajaxSettings.async = true;
});
</script>