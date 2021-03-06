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
<title><c:if test="${p.id==null}">添加表字段</c:if> <c:if
		test="${p.id > 0}">修改表字段</c:if></title>
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
		<div id="mainContent" class="main-content">
			<div class="center-block">
				<div class="main-header">
					<h2>
						<c:if test="${p.id==null}">添加表字段</c:if>
						<c:if test="${p.id > 0}">修改表字段</c:if>
					</h2>
				</div>
				<table class="table table-form">
					<tbody>
						<form class="load-indicator main-form form-ajax" id="createForm"
							method="post">
							<%-- <tr>
								<th>表名称</th>
								<td class="required"><select
									class="form-control chosen-select" name="parent_id"
									id="parent_id" onchange="selectParent()">
										<option value="0">请选择表名称</option>
										<c:forEach items="${parentList}" var="parent" varStatus="sta">
											<option value="${parent.id}"
												${parent.id==p.parentId?'selected="selected"':''}>${parent.name}</option>
										</c:forEach>
								</select></td>
								<td></td>
							</tr> --%>
						<tr>
							<th>表名称</th>
							<td class="required"><input type="text" name="tableName"
								id="tableName" value="${p.tableName}"
								class="form-control input-product-title" autocomplete="off">
							</td>
							<td></td>
						</tr>
						<tr>
							<th>字段名称</th>
							<td class="required"><input type="text" name="fieldName"
								id="fieldName" value="${p.fieldName}"
								class="form-control input-product-title" autocomplete="off">
							</td>
							<td></td>
						</tr>
						<tr>
							<th>字段描述</th>
							<td>
								<input type="text" name="fieldDesc"
								id="fieldDesc" value="${p.fieldDesc}"
								class="form-control input-product-title" autocomplete="off"> <input type="hidden"
								name="id" value="${p.id}" /></td>
							<td></td>
						</tr>
						</form>
						<tr>
							<td colspan="3" class="text-center form-actions">
								<button id="submit" class="btn btn-wide btn-primary"
									data-loading="稍候...">保存</button> <a
								href="javascript:history.go(-1);"
								class="btn btn-back btn btn-wide">返回</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	</main>
	<div class="modal fade" id="myModal">
		<div class="modal-dialog" style="width: 600px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">提示结果</h4>
				</div>
				<div class="modal-body">
					<div style="margin: 0 auto">
						<p>
							<strong><span id="msg" style="font-size: 18px">成功</span></strong>
						</p>
						<br />
						<br />
						<hr class="small" />
						<p>
							<strong>您现在可以进行以下操作：</strong>
						</p>
						<div>
							<a href="organization/tablefield/toAdd" class="btn">继续新建表字段</a> <a
								href="organization/tablefield/index" class="btn">返回表字段列表</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="errModal">
		<div class="modal-dialog" style="width: 300px">
			<div class="modal-content">
				<div class="modal-body">
					<div style="margin: 0 auto;">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">关闭</span>
						</button>
						<p>
							<span id="errMsg"></span>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/view/comm/footer.jsp"%>
	<!-- <script type="text/javascript">
		function selectParent() {
			$("#url").val("");
			$("#rank").val("");
			var param = $('#parent_id option:selected').val();
			if (param == 0) {
				$("#privilegeUrl").hide();
				$("#privilegeRank").hide();
			}
			if (param != 0) {
				$("#privilegeUrl").show();
				$("#privilegeRank").show();
			}
		}
	</script> -->
</body>
</html>
<script>
	/* UMEditor("fieldDesc"); */
	$("#submit").click(function() {
		$.ajaxSettings.async = false;
		/* $("input[name='fieldDesc']").val(UM.getEditor('fieldDesc').getContent()); */
		$.ajax({
			type : "POST",
			url : "organization/tablefield/addOrUpd?r=" + Math.random(),
			data : $("form").serialize(),
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					$("#msg").text(data.message);
					$('#myModal').modal({
						backdrop : 'static',
						keyboard : false,
						show : true,
						moveable : true
					});
				} else {
					$("#errMsg").text(data.message);
					$('#errModal').modal({
						keyboard : false,
						show : true,
						moveable : true
					});
				}
			}
		})
		$.ajaxSettings.async = true;
	});
</script>