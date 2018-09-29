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
		<title>任务完成审核：${t.taskName }</title>
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
								<a href="team/task/index">${t.taskName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 任务完成审核</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>审核人</th>
									<td class="required">${t.checkedName}</td>
									<input type="hidden" name="id" value="${t.id}"/>
									<td></td>
								</tr>
								<tr>
									<th>状态</th>
									<td class="required">
										<label class="radio-inline"><input type="radio" name="stage" value="y" checked="checked" id="passy"> 通过</label>
										<label class="radio-inline"><input type="radio" name="stage" value="n" id="passn"> 不通过</label>
									</td>
								</tr>
								<tr>
									<th>审核备注</th>
									<td colspan="2">
										<input type="hidden" name="checked_reason">
										<textarea id="checked_reason" name="details" placeholder="" style="width:100%;"></textarea>
										<div id="checked_reason" value=""></div>
									</td>
								</tr>
								</form>
							</tbody>
						</table>
						<br>
						<br>
						<br>
						<div class="table-responsive">
							<table class="table has-sort-head" id="taskList"
								data-fixed-left-width="550" data-fixed-right-width="160">
								<thead>
								<tr>
									<td style="font-size:15px;font-weight:bold;text-align:left;">代码审查-界面审核</td>
								</tr>
									<tr>
										<th data-flex="false" data-width="90px" style="width:90px" class="c-id text-center" >界面ID</th>
										<th data-flex="false" data-width="50px" style="width:250px" class="c-pri">入口点</th>
										<th data-flex="false" data-width="50px" style="width:80px" class="c-pri">在线URL</th>
										<th data-flex="false" data-width="auto" style="width:100px" class="c-pri">源文件</th>
										<th data-flex="false" data-width="auto" style="width:100px" class="c-pri">审查</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${codeReport}" var="cr" varStatus="sta">
									<tr>
										<td class="c-id cell-id text-center">${cr.id}</td>
										<td class="text-left">${cr.entry_point}</td>
										<td class="c-name text-center">${cr.online_url}</td>
										<td class="c-name text-center">${cr.source_file}</td>
										<td class="c-name text-center">
											<c:if test="${cr.examination == '0'}">
												<span class="label">未审查</span>
											</c:if>
											<c:if test="${cr.examination == '1'}">
												<span class="label label-danger">未通过</span>
											</c:if>
											<c:if test="${cr.examination == '2'}">
												<span class="label label-success">通过</span>
											</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<a href="javascript:void(0)" onclick="exam('${cr.id}','y')" class="btn btn-sm " type="button" title="审核通过">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
											<a href="javascript:void(0)" onclick="exam('${cr.id}','n')" class="btn btn-sm " type="button" title="审核不通过">不通过</a>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						
						<div class="table-responsive">
							<table class="table has-sort-head" id="taskList"
								data-fixed-left-width="550" data-fixed-right-width="160">
								<thead>
								<tr>
									<td style="font-size:15px;font-weight:bold;text-align:left;">代码审查-流程审核</td>
								</tr>
									<tr>
										<th data-flex="false" data-width="90px" style="width:90px" class="c-id text-center" >流程ID</th>
										<th data-flex="false" data-width="50px" style="width:250px" class="c-pri">对应界面入口点</th>
										<th data-flex="false" data-width="50px" style="width:80px" class="c-pri">函数入口点</th>
										<th data-flex="false" data-width="auto" style="width:100px" class="c-pri">文件名</th>
										<th data-flex="false" data-width="auto" style="width:100px" class="c-pri">审查</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${codeInterface}" var="ci" varStatus="sta">
									<tr>
										<td class="c-id cell-id text-center">${ci.id}</td>
										<td class="text-left">${ci.entry_point}</td>
										<td class="c-name text-center">${ci.online_url}</td>
										<td class="c-name text-center">${ci.source_file}</td>
										<td class="c-name text-center">
											<c:if test="${ci.examination == '0'}">
												<span class="label">未审查</span>
											</c:if>
											<c:if test="${ci.examination == '1'}">
												<span class="label label-danger">未通过</span>
											</c:if>
											<c:if test="${ci.examination == '2'}">
												<span class="label label-success">通过</span>
											</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<a href="javascript:void(0)" onclick="exam('${ci.id}','y')" class="btn btn-sm " type="button" title="审核通过">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
											<a href="javascript:void(0)" onclick="exam('${ci.id}','n')" class="btn btn-sm " type="button" title="审核不通过">不通过</a>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div colspan="3" class="text-center form-actions">
							<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">提交</button>
							<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">返回</a>
						</div>
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
						<div style="margin:0 auto">
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="team/task/index" class="btn">返回任务列表</a>
								<a href="team/task/index" class="btn">任务详情</a>
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

UE.getEditor('checked_reason');
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$("input[name='checked_reason']").val(UE.getEditor('checked_reason').getContent());
	$.ajax({type:"POST",url:"team/task/finishCheck?r=" + Math.random(),data:$("form").serialize(),
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

function setStory(reason) {
	if (reason == '重复') {
		$('#duplicateStoryBox').show();
		$('#childStoriesBox').hide();
	} else if (reason == '已细分') {
		$('#duplicateStoryBox').hide();
		$('#childStoriesBox').show();
	} else {
		$('#duplicateStoryBox').hide();
		$('#childStoriesBox').hide();
	}
}

function exam(id,isPass){
	var sname = isPass=='y'?"通过":"不通过";
	if(confirm("确认"+sname+"?")){
		$.ajaxSettings.async = false;
		$.getJSON("team/task/exam?id="+id+"&isPass="+isPass+"&r=" + Math.random(), function(data) {
			alert(data.message);
			if(data.code == 0){
				window.location.reload();
			}
		});
		$.ajaxSettings.async = true;
	}
}
</script>