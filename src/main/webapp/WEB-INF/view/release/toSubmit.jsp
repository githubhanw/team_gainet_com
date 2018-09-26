<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=basePath%>" />
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type"
	content="multipart/form-data;charset=utf-8" />
<title>提交更新</title>
<%@ include file="/WEB-INF/view/comm/cssjs.jsp"%>
</head>
<body>
	<!--header start-->
	<header id="header">
		<%@ include file="/WEB-INF/view/comm/main_header.jsp"%>
		<%@ include file="/WEB-INF/view/comm/sub_header.jsp"%>
	</header>
	<!--header end-->
	<main id="main" class="in">
	<div class="container">
		<div id="mainContent" class="main-content">
			<div class="center-block">
				<div class="main-header">
					<h2>提交更新</h2>
				</div>
				<table class="table table-form">
					<tbody>
						<form class="load-indicator main-form form-ajax" id="createForm"
							method="post" enctype="multipart/form-data">
							<tr>
								<th>发布名称</th>
								<td class="required"><input type="text" name="name"
									id="name" value=""
									class="form-control input-product-title" autocomplete="off">
								</td>
								<td></td>
							</tr>
							<tr>
								<th>更新内容</th>
								<td><input type="hidden" name="content">
									<textarea id="content" name="details" placeholder=""
										style="width: 100%;"></textarea>
									<div id="content" value=""></div></td>
							</tr>
							<tr>
								<th>更新站点</th>
								<td><input type="hidden" name="site">
									<textarea id="site" name="details" placeholder=""
										style="width: 100%;"></textarea>
									<div id="site" value=""></div></td>
							</tr>
							<tr>
								<th>执行SQL</th>
								<td><input type="hidden" name="performSql">
									<textarea id="performSql" name="details" placeholder=""
										style="width: 100%;"></textarea>
									<div id="performSql" value=""></div></td>
							</tr>
							<tr>
								<th>特殊操作</th>
								<td><input type="hidden" name="special">
									<textarea id="special" name="details" placeholder=""
										style="width: 100%;"></textarea>
									<div id="special" value=""></div></td>
							</tr>
							<tr>
								<th>备注</th>
								<td><input type="hidden" name="remark">
									<textarea id="remark" name="details" placeholder=""
										style="width: 100%;"></textarea>
									<div id="remark" value=""></div></td>
							</tr>
						</form>
						<tr>
							<td colspan="3" class="text-center form-actions">
								<button id="submit" class="btn btn-wide btn-primary"
									data-loading="稍候...">提交</button>
								<a href="release/index" class="btn btn-back btn btn-wide">返回</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	</main>
	<div class="modal fade" id="myModal">
		<div class="modal-dialog" style="width: 600px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">提示结果</h4>
				</div>
				<div class="modal-body">
					<div style="margin: 0 auto">
						<p>
							<strong><span id="msg" style="font-size: 18px">成功</span></strong>
						</p>
						<br />
						<br />
						<hr class="small" />
						<p>
							<strong>您现在可以进行以下操作：</strong>
						</p>
						<div>
							<a href="release/index" class="btn">返回发布列表</a>
							<a href="release/toSubmit" class="btn">继续提交更新</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="errModal">
		<div class="modal-dialog" style="width: 300px">
			<div class="modal-content">
				<div class="modal-body">
					<div style="margin: 0 auto;">
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
	<%@ include file="/WEB-INF/view/comm/footer.jsp"%>
</body>
</html>
<script>
var editor = new UE.ui.Editor();
editor.render("content");

UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
UE.Editor.prototype.getActionUrl = function(action){  
	if(action == 'uploadimage' || action == 'uploadscrawl'){  
		return '<%=basePath%>ueditor/upload';
		} else {
			return this._bkGetActionUrl.call(this, action);
		}
	};
	UE.getEditor('content');
	UE.getEditor('site');
	UE.getEditor('performSql');
	UE.getEditor('special');
	UE.getEditor('remark');

	$("#submit").click(function() {

		$("input[name='content']").val(UE.getEditor('content').getContent());
		$("input[name='site']").val(UE.getEditor('site').getContent());
		$("input[name='performSql']").val(UE.getEditor('performSql').getContent());
		$("input[name='special']").val(UE.getEditor('special').getContent());
		$("input[name='remark']").val(UE.getEditor('remark').getContent());
		var form = new FormData(document.getElementById("createForm"));
		$.ajaxSettings.async = false;
		$.ajax({
			url : "release/submit?r=" + Math.random(),
			type : "post",
			data : form,
			dataType : "json",
			processData : false,
			contentType : false,
			success : function(data) {
				if (data.code == 0) {
					$("#msg").text(data.message);
					$('#myModal').modal({
						backdrop : 'static',
						keyboard : false,
						show : true,
						moveable : true
					});
				} else {
					$("#errMsg").text(data.message);
					$('#errModal').modal({
						keyboard : false,
						show : true,
						moveable : true
					});
				}
			}
		});
		$.ajaxSettings.async = true;

	});
</script>