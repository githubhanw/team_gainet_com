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
		<title>变更：${t.taskName}</title>
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
								<a href="team/need/detail?id=${n.id}">${t.taskName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 变更</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>任务名称</th>
									<td class="required">
										<input type="text" name="task_name" id="task_name" value="${t.taskName}" class="form-control input-product-title" autocomplete="off">
									<input type="hidden" name="id" value="${t.id}"/>
									</td>
								</tr>
								<tr>
									<th>任务描述</th>
									<td class="required">
										<input type="hidden" name="remark">
										<textarea id="remark" name="details" placeholder="" style="width:100%;">${t.remark}</textarea>
										<div id="remark" value=""></div>
										<span class="help-block">建议参考的模板：作为一名&lt;某种类型的用户&gt;，我希望&lt;达成某些目的&gt;，这样可以&lt;开发的价值&gt;。</span>
									</td>
								</tr>
								<tr>
									<th>备注</th>
									<td>
										<input type="hidden" name="comment">
										<textarea id="comment" name="details" placeholder="" style="width:100%;"></textarea>
										<div id="comment" value=""></div>
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
UE.getEditor('remark');
UE.getEditor('comment');

$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$("input[name='remark']").val(UE.getEditor('remark').getContent());
	$("input[name='comment']").val(UE.getEditor('comment').getContent());
	$.ajax({type:"POST",url:"team/task/change?r=" + Math.random(),data:$("form").serialize(),
			dataType:"json",success:function(data){
		if(data.code == 0){
			window.location.href = "team/task/index";
		}else{
			alert(data.message);
		}
	}})
	$.ajaxSettings.async = true;
});
</script>