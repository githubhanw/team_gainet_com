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
			<c:if test="${obj.id==null}">添加成果文档</c:if>
			<c:if test="${obj.id > 0}">修改成果文档</c:if>
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
								<c:if test="${obj.id==null}">添加成果文档</c:if>
								<c:if test="${obj.id > 0}">修改成果文档</c:if>
							</h2>
						</div>
						<form class="load-indicator main-form form-ajax" id="createForm" method="post">
							<table class="table table-form">
								<tbody>
									<tr>
										<th>成果文档名称</th>
										<td class="required">
											<input type="text" name="doc_name" id="doc_name" value="${obj.docName}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>成果文档路径</th>
										<td>
											<input ${obj.id > 0 ? 'readonly="readonly"' : ''} type="text" name="project_doc_url" id="project_doc_url" value="${obj.projectDocUrl}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>文档类型</th>
										<td class="required">
											<select class="form-control input-product-code" name="type_id" id="type_id">
												<option value="0">-- 请选择 --</option>
												<c:forEach items="${pdt}" var="pdtItem" varStatus="sta">
												<option value="${pdtItem.id}" ${pdtItem.id==obj.typeId?'selected="selected"':''}>${pdtItem.project_doc_type }</option>
												</c:forEach>
											</select>
										</td>
										<td></td>
									</tr>
									<tr>
										<th>所属成果</th>
										<td>
											<select class="form-control input-product-code" name="result_id" id="result_id">
												<option value="0">-- 请选择 --</option>
												<c:forEach items="${pr}" var="prItem" varStatus="sta">
												<option value="${prItem.id}" ${prItem.id==obj.resultId?'selected="selected"':''}>${prItem.project_result_name }</option>
												</c:forEach>
											</select>
										</td>
										<td></td>
									</tr>
									<tr>
										<th>所属项目</th>
										<td>
											<select class="form-control input-product-code" name="project_id" id="project_id">
												<option value="0">-- 请选择 --</option>
												<c:forEach items="${p}" var="pItem" varStatus="sta">
											<option value="${pItem.id}"
												${pItem.id==obj.projectId?'selected="selected"':''}>${pItem.project_name }</option>
										</c:forEach>
											</select>
										</td>
										<td></td>
									</tr>
									<tr>
										<th>是否提供</th>
										<td class="required">
											<select class="form-control input-product-code" id="doc_state">
												<option ${obj.docState=='1'?'selected="selected"':'' } value="1">已提供</option>
												<option ${obj.docState=='0'?'selected="selected"':'' } value="0">未提供</option>
											</select>
										</td>
										<td></td>
									</tr>
									<tr>
										<th>预计提供时间</th>
										<td class="required">
											<input type="text" id="provide_date" value="<fmt:formatDate value="${obj.provideDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control form-date" placeholder="项目开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
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
	<script>
		$("#provide_date").datetimepicker({
		    language:  "zh-CN",
		    weekStart: 1,
		    todayBtn:  1,
		    autoclose: 1,
		    todayHighlight: 1,
		    startView: 2,
		    minView: 2,
		    forceParse: 0,
		    format: "yyyy-mm-dd"
		});
		$("#submit").click(function(){
			var doc_name = encodeURI(encodeURI($("#doc_name").val()));
			var project_doc_url = encodeURI(encodeURI($("#project_doc_url").val()));
			var type_id = $("#type_id").val();
			var result_id = $("#result_id").val();
			var project_id = $("#project_id").val();
			var doc_state = $("#doc_state").val();
			var provide_date = $("#provide_date").val();
			$.ajaxSettings.async = false;
			$.getJSON("declaration/doc/addOrUpd?id=${obj.id}&doc_name=" + doc_name + "&project_doc_url=" + project_doc_url + "&type_id=" + type_id + "&result_id=" + result_id + "&project_id=" + project_id + "&doc_state=" + doc_state + "&provide_date=" + provide_date + "&r=" + Math.random(), 
					function(data) {
				alert(data.message);
			});
			$.ajaxSettings.async = true;
		});
	</script>
</html>