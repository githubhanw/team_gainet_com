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
<title>文档类型与成果类型关系</title>
<style>
.table-bymodule select.form-control {
	height: 250px
}

.group-item {
	display: block;
	width: 220px;
	float: left;
	font-size: 14px
}

.group-item .checkbox-inline label {
	padding-left: 8px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.table.table-form tbody>tr:last-child td {
	border-top: 1px solid #ddd
}

@
-moz-document url-prefix (){ .table .table-form tbody>tr:last-childtd,
	.table.table-formtbody>tr :last-childth {
	border-bottom: 1pxsolid #ddd
}
}
</style>
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
						<a href="declaration/doctype/index">成果文档类型列表</a> <small>&nbsp;<i
							class="icon-angle-right"></i>&nbsp; 文档类型与成果类型关系
						</small>
					</h2>
				</div>
				<table class="table table-hover table-striped table-bordered"
					id="privList">
					<thead>
						<tr>
							<th class="text-center w-230px">成果类型</th>
							<td class="text-center">文档类型(必选)</td>
							<td class="text-center">文档类型(可选)</td>
							<td class="text-center">操作</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${resultTypeList}" var="resultType"
							varStatus="sta">
							<form id="createForm${resultType }" method="post">
							<tr class="even">
								<th class="text-middle text-right w-150px">${resultType == 1 ? '软著' : resultType == 2 ? '发明专利' : 
													resultType == 3 ? '实用新型专利' : resultType == 4 ? '外观专利' : resultType == 5 ? '商标' : ''}
								</th>
								<td><c:forEach items="${docTypeList}"
										var="docType" varStatus="status">
										<div class="group-item">
											<div class="checkbox-primary checkbox-inline">
												<c:set var="checked" value="false"></c:set>
												<c:forEach items="${requiredList}" var="required">
													<c:if
														test="${required.result_type == resultType && required.doc_type_id == docType.id}">
														<c:set var="checked" value="true"></c:set>
													</c:if>
												</c:forEach>
												<input type="checkbox" name="required" id="${sta.index}${docType.id}required"
													${checked ? 'checked="checked"' : "" }
													value="${docType.id}" onchange="change('${sta.index}${docType.id}','required')"> <label>${docType.project_doc_type}</label>
											</div>
										</div>
									</c:forEach></td>
								<td><c:forEach items="${docTypeList}"
										var="docType" varStatus="status">
										<div class="group-item">
											<div class="checkbox-primary checkbox-inline">
												<c:set var="checked" value="false"></c:set>
												<c:forEach items="${optionalList}" var="optional">
													<c:if
														test="${optional.result_type == resultType && optional.doc_type_id == docType.id}">
														<c:set var="checked" value="true"></c:set>
													</c:if>
												</c:forEach>
												<input type="checkbox" name="optional" id="${sta.index}${docType.id}optional"
													${checked ? 'checked="checked"' : "" }
													value="${docType.id}" onchange="change('${sta.index}${docType.id}','optional')"> <label>${docType.project_doc_type}</label>
											</div>
										</div>
									</c:forEach></td>
								<td class="text-center form-actions">
									<button id="submit" class="btn btn-wide btn-primary"
										data-loading="稍候..." onclick="modify(${resultType})">保存</button>
								</td>
							</tr>
							</form>
						</c:forEach>
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
						<br /> <br />
						<hr class="small" />
						<p>
							<strong>您现在可以进行以下操作：</strong>
						</p>
						<div>
							<a href="organization/role/index" class="btn">返回角色列表</a> <a
								href="organization/role/toAdd" class="btn">新建角色</a> <a
								href="organization/role/toEdit?id=${entity.id}" class="btn">修改角色</a>
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
	<script>
	function change(id, type) {
		if (type == 'required') {
			if ($('#' + id + type).is(':checked')) {
			   $('#' + id + 'optional').attr('checked', false)
			}
		}
		if (type == 'optional') {
			if ($('#' + id + type).is(':checked')) {
				   $('#' + id + 'required').attr('checked', false)
				}
		}
	}
	function modify(resultType) {
		$.ajaxSettings.async = false;
		$.ajax({
			type : "POST",
			url : "declaration/doctype/modify?r=" + Math.random() + "&resultType=" + resultType,
			data : $("#createForm" + resultType).serialize(),
			dataType : "json",
			success : function(data) {
				if(data.code == 0){
					$("#msg").text(data.message);
	 				$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
	 			}else{
	 				$("#errMsg").text(data.message);
	 				$('#errModal').modal({keyboard: false,show: true, moveable: true});
	  			}
			}
		})
		$.ajaxSettings.async = true;
	}
		
</script>
</body>
</html>