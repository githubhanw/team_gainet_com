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
		<title>验收里程碑报告</title>
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
							<h2>验收里程碑报告</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<input type="hidden" id="mi_id" name="mi_id" value="${mi.id}"></input>
								<input type="hidden" id="mi_name" name="mi_name" value="${mi.authorName}"></input>
								<tr>
									<th>名称:</th>
									<td class="required">${mi.milepostName}</td>
									<td></td>
								</tr>
								<tr>
									<th>描述:</th>
									<td class="required">${mi.milepostDescribe}</td>
									<td></td>
								</tr>
								<tr>
									<th>完成时间:</th>
									<td class="required">${mi.finishTime}</td>
									<td></td>
								</tr>
								<tr>
								<th>模块详情</th>
									<td>
										<ul class="tree tree-lines" data-ride="tree">
											<c:forEach items="${need}" var="need" varStatus="sta">
												<li>&nbsp;<a href="#">${need.need_name }【模块】</a>
													<ul>
													<c:forEach items="${subNeed}" var="subNeed" varStatus="sta">
													<c:if test="${need.id == subNeed.parent_id }">
														<li><a href="#">&nbsp;${subNeed.need_name }【子模块】</a>
															<ul>
															<c:forEach items="${subNeedTask}" var="task" varStatus="sta">
															<c:if test="${subNeed.id == task.need_id }">
																<li><a href="#">&nbsp;${task.task_name}【任务】</a>
																	<ul>
																		<li><a href="#">&nbsp;界面原型图</a>
																			<ul>
																				<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																					<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																				</c:forEach>
																			</ul>
																		</li>
																		<li><a href="#">&nbsp;流程图</a>
																			<ul>
																				<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																					<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																				</c:forEach>
																			</ul>
																		</li>
																	</ul>
																</li>
															</c:if>
															</c:forEach>
															</ul>
														</li>
													</c:if>
													</c:forEach>
													<c:forEach items="${needTask}" var="task" varStatus="sta">
													<c:if test="${need.id == task.need_id }">
														<li><a href="#">&nbsp;${task.task_name}【任务】</a>
															<ul>
																<li><a href="#">&nbsp;界面原型图</a>
																	<ul>
																		<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																			<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																		</c:forEach>
																	</ul>
																</li>
																<li><a href="#">&nbsp;流程图</a>
																	<ul>
																		<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																			<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																		</c:forEach>
																	</ul>
																</li>
															</ul>
														</li>
													</c:if>
													</c:forEach>
													</ul>
												</li>
											</c:forEach>
										</ul>
									</td>
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
								<a href="test/milepost/manage" class="btn">返回里程碑列表</a>
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
	$.ajax({type:"POST",url:"test/milepost/vailreport?r=" + Math.random(),data:$("form").serialize(),dataType:"json",success:function(data){
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