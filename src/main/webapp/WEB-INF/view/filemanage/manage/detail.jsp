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
								<div class="detail-content article-content">${FileManage.editTime}</div>
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
							<div class="detail">
								<div class="detail-title">正文</div>
								<div class="detail-content article-content">
								${FileManage.fileText}
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
												<td title="/">${FileManage.glName}</td>
											</tr>
											<tr>
												<th>文档标题</th>
												<td>${FileManage.fileName}</td>
											</tr>
											<tr>
												<th>关键字</th>
												<td>${FileManage.fileKeyword}</td>
											</tr>
											<tr>
												<th>文件格式</th>
												<td>
												${FileManage.fileFormat}
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
					</div>
				</div>
				<!--mainActions end-->
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>