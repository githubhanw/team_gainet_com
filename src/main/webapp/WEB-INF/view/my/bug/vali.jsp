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
		<title>验证BUG</title>
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
							<h2>验证BUG</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>任务id:</th>
									<td class="required">${t.taskid}</td>
									<td></td>
								</tr>
								<tr>
									<th>任务名称:</th>
									<td>${t.tasktype}<input type="hidden" name="id" value="${t.id}"/></td>
									<td></td>
								</tr>
								<tr>
									<th>BUG标题:</th>
									<td class="required">${t.bugdes}</td>
									<td></td>
								</tr>
								<tr>
									<th>bug产品:</th>
									<td class="required">${t.bugproject}</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG描述</th>
									<td>${t.mark}</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG等级:</th>
									<td class="required">
										<c:if test="${t.bugrank=='0'}">A</c:if>
										<c:if test="${t.bugrank=='1'}">B</c:if>
										<c:if test="${t.bugrank=='2'}">C</c:if>
										</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG分类:</th>
									<td class="required">
										<c:if test="${t.bugfen=='0'}">正常</c:if>
										<c:if test="${t.bugfen=='1'}">线上bug</c:if>
										<c:if test="${t.bugfen=='2'}">线上线下bug</c:if>
										</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG类型:</th>
									<td class="required">
										<c:if test="${t.bugtype=='0'}">功能类</c:if>
										<c:if test="${t.bugtype=='1'}">安全类</c:if>
										<c:if test="${t.bugtype=='2'}">界面类</c:if>
										<c:if test="${t.bugtype=='3'}">信息类</c:if>
										<c:if test="${t.bugtype=='4'}">数据类</c:if>
										<c:if test="${t.bugtype=='5'}">流程类</c:if>
										<c:if test="${t.bugtype=='6'}">需求问题</c:if>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>创建者:</th>
									<td class="required">${t.creater}</td>
									<td></td>
								</tr>
								<tr>
									<th>开发者:</th>
									<td class="required">${t.developer}</td>
									<td></td>
								</tr>
								<tr>
									<th>解决人:</th>
									<td class="required">${t.solver}</td>
									<td></td>
								</tr>
								<tr>
									<th>解决方案</th>
									<td class="required">
										<c:if test="${t.solution=='0'}">问题已修复</c:if>
										<c:if test="${t.solution=='1'}">重复问题</c:if>
										<c:if test="${t.solution=='2'}">不是问题</c:if>
										<c:if test="${t.solution=='3'}">需求如此</c:if>
										<c:if test="${t.solution=='4'}">延期处理</c:if>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>解决人描述:</th>
									<td class="required">${t.kaifamark}</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG创建时间:</th>
									<td class="required">${t.createtime}</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG解决时间:</th>
									<td class="required">${t.solvetime}</td>
									<td></td>
								</tr>
								</form>
								<tr>
									<td colspan="3" class="text-center form-actions">
										<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">确认</button>
										<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">返回</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</main>
		<div class="modal fade" id="myModal">
			<div class="modal-dialog" style="width:600px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">提示结果</h4>
					</div>
					<div class="modal-body">
						<div style="margin: 0 auto">
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="my/bug" class="btn">返回BUG列表</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="errModal">
			<div class="modal-dialog" style="width:300px">
				<div class="modal-content">
					<div class="modal-body">
						<div style="margin:0 auto;">
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
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$.ajax({type:"POST",url:"my/bug/vali?r=" + Math.random(),data:$("form").serialize(),
			dataType:"json",success:function(data){
		if(data.code == 0){
			$("#msg").text(data.message);
			$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
		}else{
			$("#errMsg").text(data.message);
			$('#errModal').modal({keyboard: false,show: true, moveable: true});
		}
	}})
	$.ajaxSettings.async = true;
});
</script>