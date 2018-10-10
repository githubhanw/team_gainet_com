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
		<meta http-equiv="Content-Type" content="multipart/form-data;charset=utf-8" />
		<title>添加原型图和流程图</title>
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
							<h2>添加原型图和流程图</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post" enctype="multipart/form-data">
								<input type="hidden" id="task_id" name="task_id" value="${t.id}">
								<tr>
									<th>任务名称</th>
									<td>
										${t.taskName}
									</td>
									<td></td>
								</tr>
								<tr>
								    <th>界面原型图</th>
								    <td class="required">
									<input type="file" name="filePrototype" multiple="multiple" accept="image/*"/>
								    </td>
								    <td></td>
								</tr>
								<tr>
								    <th>流程图</th>
								    <td class="required">
								    <input type="file" name="filetree" multiple="multiple" accept="image/*"/>
									</td>
								    <td></td>
								</tr>
								</form>
								<tr>
								    <th>现有界面原型图</th>
								    <td>
									    <c:if test="${t.interfaceImg !=null }">
											<c:forEach items="${fn:split(t.interfaceImg, ',')}" var="flow" varStatus="sta">
												<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="【原型图】">&nbsp;&nbsp;
											</c:forEach> 
										</c:if>
										<c:if test="${t.interfaceImg ==null }">
											无图片
										</c:if>
								    </td>
								    <td></td>
								</tr>
								<tr>
								    <th>现有流程图</th>
								    <td>
									    <c:if test="${t.flowImg != null}">
											<c:forEach items="${fn:split(t.flowImg, ',')}" var="flow" varStatus="sta">
												<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="【流程图】">&nbsp;&nbsp;
											</c:forEach>
										</c:if>
										<c:if test="${t.flowImg == null}">
											无图片
										</c:if>
									</td>
								    <td></td>
								</tr>
								<tr>
									<td colspan="3" class="text-center form-actions">
										<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">添加</button>
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
								<a href="team/task/index" class="btn">返回任务列表</a>
								<a href="team/need/index" class="btn">返回模块列表</a>
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
	<script>
	$("#submit").click(function(){
		$.ajaxSettings.async = false;
		var form = new FormData(document.getElementById("createForm"));
		$.ajax({
	         url:"team/task/addPicture?r=" + Math.random(),
	         type:"post",
	         data:form,
	         dataType:"json",
	         processData:false,
	         contentType:false,
	         success:function(data){
	        	if(data.code == 0){
	        		window.location.href="team/task/detail?id="+document.getElementById("task_id").value;
	 			}else{
	 				$("#errMsg").text(data.message);
	 				$('#errModal').modal({keyboard: false,show: true, moveable: true});
	 			}
	         }
	     });
		$.ajaxSettings.async = true;
	});
	</script>
</html>