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
		<title>延期：${t.taskName}</title>
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
								<span class="label label-id">${t.id}</span>
								<a href="team/need/index">${t.taskName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 延期</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<tr>
									<th>延期至日期</th>
									<td class="required">
										<input type="text" name="delayed_date" id="delayed_date" value="<fmt:formatDate value="${t.planEndDate}" pattern="yyyy-MM-dd"/>" 
												class="form-control form-date-limit" placeholder="延期至日期" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>延期审批人</th>
									<td class="required">
										<select data-placeholder="请选择延期审批人" class="form-control chosen-select" name="delayed_review_id" id="delayed_review_id">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<option value="${member.id}">${member.name}(${member.number})</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td>
										<div id="comment" name="comment"></div>
										<input type="hidden" name="id" id="id" value="${t.id}"/>
									</td>
								</tr>
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
</html>
<script>
UMEditor("comment");
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$.ajax({type:"POST",url:"team/task/delay?r=" + Math.random(),data:"id=" + $("#id").val() + "&delayed_date=" + $("#delayed_date").val() + "&delayed_review_id=" + $("#delayed_review_id").val() + "&comment=" + $("#comment").val(),dataType:"json",success:function(data){
		if(data.code == 0){
			window.location.href = "team/task/index";
		}else{
			alert(data.message);
		}
	}})
	$.ajaxSettings.async = true;
});
</script>