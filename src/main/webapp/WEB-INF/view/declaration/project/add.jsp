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
			<c:if test="${p.id==null}">添加项目</c:if>
			<c:if test="${p.id > 0}">修改项目</c:if>
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
								<c:if test="${p.id==null}">添加项目</c:if>
								<c:if test="${p.id > 0}">修改项目</c:if>
							</h2>
						</div>
						<form class="load-indicator main-form form-ajax" id="createForm" method="post">
							<table class="table table-form">
								<tbody>
									<tr>
										<th>项目申报号</th>
										<td class="required">
											<input type="text" name="declaration_number" id="declaration_number" value="${p.declarationNumber == null ? '无' : pr.declarationNumber}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>项目名称</th>
										<td class="required">
											<input type="text" name="project_name" id="project_name" value="${p.projectName}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>所属公司</th>
										<td class="required">
											<select class="form-control input-product-code" name="company" id="company">
												<option ${p.company=='景安'?'selected="selected"':'' } value="景安">景安</option>
												<option ${p.company=='快云'?'selected="selected"':'' } value="快云">快云</option>
												<option ${p.company=='绿林客'?'selected="selected"':'' } value="绿林客">绿林客</option>
												<option ${p.company=='大数据'?'selected="selected"':'' } value="大数据">大数据</option>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>阶段</th>
										<td class="required">
											<select class="form-control input-product-code" name="stage" id="stage">
												<option ${p.stage=='研究'?'selected="selected"':'' } value="研究">研究</option>
												<option ${p.stage=='开发'?'selected="selected"':'' } value="开发">开发</option>
												<option ${p.stage=='完成'?'selected="selected"':'' } value="完成">完成</option>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>项目开始时间</th>
										<td class="required">
											<input type="text" name="start_date" id="startDate" value="<fmt:formatDate value="${p.startDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control form-date" placeholder="项目开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>项目结束时间</th>
										<td class="required">
											<input type="text" name="end_date" id="endDate" value="<fmt:formatDate value="${p.endDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control form-date" placeholder="项目结束时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
										</td>
										<td></td>
									</tr>
									<tr>
										<td colspan="3" class="text-center form-actions">
											<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
											<input type="hidden" name="id" value="${p.id}"/>
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
	$.ajaxSettings.async = false;
	$.ajax({type:"POST",url:"declaration/project/addOrUpd?r=" + Math.random(),data:$("form").serialize(),dataType:"json",success:function(data){
		alert(data.message);
	}})
	$.ajaxSettings.async = true;
});
</script>