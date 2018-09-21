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
		<title>建任务</title>
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
								<a href="my/task">${t.taskName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 交接</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>任务名称</th>
									<td>${t.taskName}</td>
									<td></td>
								</tr>
								<tr>
									<th>任务类型</th>
									<td>
										<c:if test="${t.taskType=='1'}">开发</c:if>
										<c:if test="${t.taskType=='2'}">测试</c:if>
										<c:if test="${t.taskType=='3'}">设计</c:if>
										<c:if test="${t.taskType=='4'}">前端</c:if>
										<c:if test="${t.taskType=='5'}">维护</c:if>
										<c:if test="${t.taskType=='6'}">需求</c:if>
										<c:if test="${t.taskType=='7'}">研究</c:if>
										<c:if test="${t.taskType=='8'}">讨论</c:if>
										<c:if test="${t.taskType=='9'}">运维</c:if>
										<c:if test="${t.taskType=='10'}">事务</c:if>
										<c:if test="${t.taskType=='0'}">其他</c:if>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>优先级</th>
									<td>
										<c:if test="${t.level=='1'}">紧急重要</c:if>
										<c:if test="${t.level=='2'}">紧急不重要</c:if>
										<c:if test="${t.level=='3'}">不紧急重要</c:if>
										<c:if test="${t.level=='4'}">不紧急不重要</c:if>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>任务开始日期</th>
									<td><fmt:formatDate value="${t.startDate}" pattern="yyyy-MM-dd"/></td>
									<td></td>
								</tr>
								<tr>
									<th>任务结束日期</th>
									<td><fmt:formatDate value="${t.endDate}" pattern="yyyy-MM-dd"/></td>
									<td></td>
								</tr>
								<tr>
									<th>任务描述</th>
									<td>${t.remark}</td>
									<td></td>
								</tr>
								<tr>
									<th>交接给</th>
									<td class="required">
										<select data-placeholder="请选择交接人" class="form-control chosen-select" name="handover_id" id="handover_id">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<option value="${member.id}">${member.name}(${member.number})</option>
											</c:forEach>
										</select>
									</td>
									<input type="hidden" name="id" value="${t.id}"/>
									<td></td>
								</tr>
								<tr>
									<th>已完成内容</th>
									<td>
										<input type="hidden" name="handover_info">
										<textarea id="handover_info" name="details" placeholder="" style="width:100%;"></textarea>
										<div id="handover_info" value=""></div>
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
								<a href="my/task" class="btn">返回任务列表</a>
								<a href="my/need" class="btn">返回需求列表</a>
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
var editor = new UE.ui.Editor();
editor.render("content");
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
UE.Editor.prototype.getActionUrl = function(action){  
	if(action == 'uploadimage' || action == 'uploadscrawl'){  
		return '<%=basePath%>ueditor/upload';  
	}else{  
		return this._bkGetActionUrl.call(this, action);  
	}  
};  
UE.getEditor('handover_info');

$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$("input[name='handover_info']").val(UE.getEditor('handover_info').getContent());
	$.ajax({type:"POST",url:"my/task/handover?r=" + Math.random(),data:$("form").serialize(),
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