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
		<title>配置</title>
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
								<span class="label label-id">${entity.id}</span>
								<a href="organization/user/index">${entity.name}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 配置</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="main-table table-task skip-iframe-modal" method="post">
								<tr>
									<th>所属团队</th>
									<td class="required">
										<select data-placeholder="选择一个部门" class="form-control chosen chosen-select"  name="department_id" id="department_id">
											<option value=""></option>
											<c:forEach items="${departmentTree}" var="p" varStatus="sta">
												<option value="${p.id}" ${p.id==entity.config.departmentId ? 'selected="selected"':''}>${p.name}</option>
												<c:forEach items="${p.childrens}" var="children" varStatus="sta">
													<option value="${children.id}" ${children.id==entity.config.departmentId ? 'selected="selected"':''}>|——${children.name}</option>
												</c:forEach>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>所属角色</th>
									<td class="required">
										<select data-placeholder="选择一个角色" class="form-control chosen chosen-select"  name="role_ids" id="role_ids">
											<option value=""></option>
											<c:forEach items="${roles}" var="p" varStatus="sta">
												<option value="${p.id}" ${p.id==entity.config.roleIds?'selected="selected"':''}>${p.name }</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>备注</th>
									<td>
										<textarea name="comment" rows="6" style="width:100%"></textarea>
										<input type="hidden" name="id" value="${entity.id}"/>
										<input type="hidden" name="config_id" value="${entity.config.id}"/>
									</td>
									<td></td>
								</tr>
								</form>
								<tr>
									<td colspan="3" class="text-center form-actions">
										<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
										<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">返回</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</main>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
	<script>
	$("#submit").click(function(){
		$.ajaxSettings.async = false;
		$.ajax({type:"POST",url:"organization/user/config?r=" + Math.random(),data:$("form").serialize(),dataType:"json",success:function(data){
			if(data.code == 0){
				window.location.href = "organization/user/index";
			} else {
				alert(data.message);
			}
		}})
		$.ajaxSettings.async = true;
	});
	</script>
</html>