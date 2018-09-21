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
		<title>编辑里程碑</title>
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
							<h2>编辑里程碑</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<input type="hidden" name="mi_id" id="mi_id" value="${mi.id}" class="form-control input-product-title" autocomplete="off">
								<input type="hidden" name="createtime" id="createtime" value="${mi.createTime}" class="form-control input-product-title" autocomplete="off">
								<input type="hidden" name="authorId" id="authorId" value="${mi.authorId}" class="form-control input-product-title" autocomplete="off">
								<input type="hidden" name="milepostState" id="milepostState" value="${mi.milepostState}" class="form-control input-product-title" autocomplete="off">
								<tr>
									<th>名称</th>
									<td class="required">
										<input type="text" name="milepost_name" id="milepost_name" value="${mi.milepostName}" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>开始日期</th>
									<td class="required">
										<input type="text" name="starttime" id="starttime"
												class="form-control form-date-limit" placeholder="${mi.startTime}" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly" value="${mi.startTime}">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>结束日期</th>
									<td class="required">
										<input type="text" name="endtime" id="endtime" class="form-control form-date-limit" placeholder="${mi.endTime}" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly" value="${mi.endTime}">
									</td>
									<td></td>
								</tr>
											<input type="hidden" name="author_name" id="author_name" value="${mi.authorName}" class="form-control input-product-title" autocomplete="off">
								<tr>
									<th>描述</th>
									<td class="required">
										<input type="hidden" name="mark">
										<textarea id="mark" name="details" placeholder="" style="width:100%;">${mi.milepostDescribe}</textarea>
										<div id="mark" value=""></div>
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
						   <strong> <p style="font-size:18px">操作成功</p></strong>
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
//alert(document.getElementById("mi_id").value );
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
	if($("input[name='mark']").val(UE.getEditor('mark').getContent())==null){
		alert(4556);
	}
	$.ajax({type:"POST",url:"test/milepost/edit?r=" + Math.random(),data:$("form").serialize(),
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