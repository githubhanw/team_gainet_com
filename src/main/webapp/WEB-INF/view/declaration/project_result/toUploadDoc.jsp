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
<meta http-equiv="Content-Type" content="multipart/form-data;charset=utf-8" />
<title>上传成果文档</title>
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
					<h2>上传成果文档</h2>
				</div>
				<form class="load-indicator main-form form-ajax" id="createForm" enctype="multipart/form-data"
					method="post">
					<table class="table table-form">
						<tbody>
							<tr>
								<th>成果文档名称</th>
								<td class="required"><input type="text" name="doc_name" readonly="readonly"
									id="doc_name" value="${obj.docName}"
									class="form-control input-product-title" autocomplete="off">
								</td>
								<td></td>
							</tr>
							<tr>
								<th>成果文档路径</th>
								<td><input type="text" name="project_doc_url" readonly="readonly"
									id="project_doc_url" value="${obj.projectDocUrl}"
									class="form-control input-product-title" autocomplete="off">
								</td>
								<td></td>
							</tr>
							<tr>
								<th>文档类型</th>
								<td class="required"><input type="text" name="type_id" readonly="readonly"
									id="type_id" value="${doc.projectDocType}"
									class="form-control input-product-title" autocomplete="off"></td>
								<td></td>
							</tr>
							<tr>
								<th>所属成果</th>
								<td><input type="text" name="result_id" readonly="readonly"
									id="result_id" value="${result.projectResultName}"
									class="form-control input-product-title" autocomplete="off"></td>
								<td></td>
							</tr>
							<tr>
								<th>所属项目</th>
								<td><input type="text" name="project_id" readonly="readonly"
									id="project_id" value="${project.projectName}"
									class="form-control input-product-title" autocomplete="off"></td>
								<td></td>
							</tr>
							<tr>
								<th>是否提供</th>
								<td class="required"><input type="text" name="doc_state" readonly="readonly"
									id="doc_state" value="${obj.docState==1?'已提供':'未提供'}"
									class="form-control input-product-title" autocomplete="off"></td>
								<td></td>
							</tr>
							<tr>
								<th>预计提供时间</th>
								<td class="required"><input type="text" name="provide_date" readonly="readonly"
									id="provide_date" value="${obj.provideDate}"
									class="form-control input-product-title" autocomplete="off"></td>
								<td></td>
							</tr>
							<tr>
								<th>原文档名称</th>
								<td class="required"><input type="text" name="original_name" readonly="readonly"
									id="original_name" value="${obj.originalName}"
									class="form-control input-product-title" autocomplete="off">
								</td>
								<td></td>
							</tr>
							<tr>
								<th>文档上传</th>
								<td class="required"><input type="file" name="file"
									id="file">
									<div><span>${obj.docName}</span></div>
									</td>
								<td></td>
							</tr>
							<tr>
								<td colspan="3" class="text-center form-actions">
									<button id="submit" class="btn btn-wide btn-primary"
										data-loading="稍候...">上传</button> <a
									href="javascript:history.go(-1);"
									class="btn btn-back btn btn-wide">返回</a>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
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
							<strong> <p style="font-size:18px">操作成功</p></strong>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="declaration/result/detail?id=${obj.id }" class="btn">返回成果详情</a>
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
	<%@ include file="/WEB-INF/view/comm/footer.jsp"%>
</body>
<script>
/* 	$("#provide_date").datetimepicker({
		language : "zh-CN",
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		forceParse : 0,
		format : "yyyy-mm-dd"
	}); */
	$("#submit").click(
			function() {
				var form = new FormData(document.getElementById("createForm"));
				var filesize=$("#file").val();
				if(filesize==''){
					alert("请选择文件");
				}else{
				$.ajaxSettings.async = false;
				$.ajax({
			         url:"declaration/result/uploadDoc?r=" + Math.random() + "&id=${obj.id}",
			         type:"post",
			         data:form,
			         dataType:"json",
			         processData:false,
			         contentType:false,
			         success:function(data){
			        	if(data.code == 0){
			 				$("#msg").text(data.message);
			 				$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
			 			}else{
			 				$("#errMsg").text(data.message);
			 				$('#errModal').modal({keyboard: false,show: true, moveable: true});
			 			}
			         }
			     });
				$.ajaxSettings.async = true;
				}
			});
</script>
</html>