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
		<title>文档详情</title>
    	<%@ include file="/WEB-INF/view/comm/cssjs.jsp" %>
	</head>
	<body>
	    <!--header start-->
	    <header id="header">
	    	<%@ include file="/WEB-INF/view/comm/main_header.jsp" %>
	    	<%@ include file="/WEB-INF/view/comm/sub_header.jsp" %>
	    </header>
	    <!--header end-->
		<main id="main">
			<div class="container">
				<div id="mainMenu" class="clearfix">
					<div class="btn-toolbar pull-left">
						<a href="javascript:history.go(-1);" class="btn btn-link"><i class="icon icon-back icon-sm"></i> 返回</a>
						<div class="divider"></div>
						<div class="page-title">
							<span class="label label-id">${FileManage.id}</span> <span class="text">文档详情</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">创建人</div>
								<div class="detail-content article-content">${FileManage.addName}</div>
							</div>
							<div class="detail">
								<div class="detail-title">创建时间</div>
								<div class="detail-content article-content">${FileManage.createTime}</div>
							</div>
							<div class="detail">
								<div class="detail-title">备注</div>
								<div class="detail-content article-content">${FileManage.fileRemarks}</div>
							</div>
							<div class="detail">
								<div class="detail-title">状态</div>
								<div class="detail-content article-content">
									<c:if test="${FileManage.accessControl == 0}">
										已删除
									</c:if>
									<c:if test="${FileManage.accessControl == 1}">
										正常
									</c:if>
								</div>
							</div>
							<input type="hidden" id="accessControl" name="accessControl" value="${FileManage.accessControl}">
							<div class="detail">
								<div class="detail-title">正文</div>
								<div class="detail-content article-content">
								${FileManage.fileText}
								</div>
							</div>
							<div class="detail">
								<div class="detail-title">文档路径</div>
								<div class="detail-content article-content">
								<a href="${FileManage.fileUrl}">${FileManage.fileUrl}</a>
								</div>
							</div>
						</div>
					</div>
					<div class="side-col col-4">
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">基本信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr class="nofixed">
												<th>关联类型</th>
												<td title="/">
												<c:if test="${FileManage.fileClassification == 0}">
													项目
												</c:if>
												<c:if test="${FileManage.fileClassification == 1}">
													模块
												</c:if>
												</td>
											</tr>
											<tr class="nofixed">
												<th>关联名称</th>
												<td title="/">${glname}</td>
											</tr>
											<tr>
												<th>文档标题</th>
												<td>${FileManage.fileName}</td>
											</tr>
											<tr>
												<th>文件格式</th>
												<td>
												${FileManage.fileFormat}
												</td>
											</tr>
											<tr>
												<th>文件原名</th>
												<td>
												${FileManage.fileRealname}
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
				<!--mainActions start-->
				<div id="mainActions">
					<nav class="container">
					</nav>
					<div class="btn-toolbar">
						<a href="javascript:history.go(-1);" id="back" class="btn" title="返回[快捷键:Alt+↑]">
							<i class="icon-goback icon-back"></i> 返回
						</a>
						<div class="divider"></div>
						<a href="javascript:void(0)" onclick="del(${FileManage.id})" class="btn btn-link"><i class="icon-common-delete icon-trash"></i> 删除</a>
					    <input id="f_id" name="f_id" type="hidden" value="${FileManage.accessControl}"></input>
					</div>
				</div>
				<!--mainActions end-->
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
<script>
function run (){
	var accessControl =$("#accessControl").val(); //获取文档状态
	if(accessControl==0){
		$("#ccc").hide();
	}else{
		$("#ccc").show();
	}
}
run();
	function del(id){
		var a=document.getElementById("f_id").value;
		if(a=="0"){
			alert("该文档已经被删除");
		}else{
			if(confirm("确认删除？")){
				$.ajaxSettings.async = false;
				$.getJSON("filemanage/manage/delete?id=" + id + "&r=" + Math.random(), function(data) {
					alert(data.message);
					if(data.code == 0){
						window.location.reload();
					}
				});
				$.ajaxSettings.async = true;
			}
		}
	}
</script>
</html>