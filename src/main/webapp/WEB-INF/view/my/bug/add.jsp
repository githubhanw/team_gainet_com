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
		<title>提BUG</title>
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
							<h2>提BUG</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>任务名称</th>
									<td class="required">
										<select class="form-control chosen-select" name="taskid" id="taskid">
											<option value="">请选择任务</option>
											<c:forEach items="${task}" var="task" varStatus="sta">
												<option value="${task.id}" ${task.id==taskId?'selected="selected"':''}>【任务ID:${task.id}】${task.task_name }</option>
											</c:forEach>
										</select>
									<td></td>
								</tr>
								<tr>
									<th>bug产品</th>
									<td class="required">
										<input type="text" name="bugproject" id="bugproject" value="" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG等级</th>
									<td class="required">
										<select class="form-control chosen chosen-select" name="bugrank" id="bugrank">
											<option selected="selected" value="0">A</option>
											<option value="1">B</option>
											<option value="2">C</option>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG分类</th>
									<td class="required">
										<select class="form-control chosen chosen-select" name="bugfen" id="bugfen">
											<option selected="selected" value="0">正常</option>
											<option value="1">线上bug</option>
											<option value="2">线上线下bug</option>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG类型</th>
									<td class="required">
										<select class="form-control chosen chosen-select" name="bugtype" id="bugtype">
											<option selected="selected" value="0">功能类</option>
											<option value="1">安全类</option>
											<option value="2">界面类</option>
											<option value="3">信息类</option>
											<option value="4">数据类</option>
											<option value="5">流程类</option>
											<option value="6">需求问题</option>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG标题</th>
									<td class="required">
										<input type="text" name="bugdes" id="bugdes" value="" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>创建者</th>
									<td class="required">
										<select data-placeholder="请选择创建者" class="form-control chosen-select" name="creater_id" id="creater_id">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<option value="${member.id}">${member.name}(${member.number})</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>开发者</th>
									<td class="required">
										<select data-placeholder="请选择开发者" class="form-control chosen-select" name="developer_id" id="developer_id">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<option value="${member.id}">${member.name}(${member.number})</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>BUG描述</th>
									<td>
										<div id="mark" style="width:100%;">
											<input type="hidden" name="mark">
										</div>
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
								<a href="my/bug/toAdd" class="btn">继续提BUG</a>
								<a href="my/bug" class="btn">返回我的Bug</a>
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
editor.render("mark");
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
UE.Editor.prototype.getActionUrl = function(action){  
	if(action == 'uploadimage' || action == 'uploadscrawl'){  
		return '<%=basePath%>ueditor/upload';  
	}else{  
		return this._bkGetActionUrl.call(this, action);  
	}  
};  
UE.getEditor('mark');

$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$("input[name='mark']").val(UE.getEditor('mark').getContent());
	$.ajax({type:"POST",url:"my/bug/add?r=" + Math.random(),data:$("form").serialize(),
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