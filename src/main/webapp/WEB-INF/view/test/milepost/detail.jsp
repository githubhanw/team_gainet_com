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
		<title>里程碑详情</title>
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
							<span class="label label-id">${mi.id}</span> <span class="text">里程碑详情</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">名称</div>
								<div class="detail-content article-content">${mi.milepostName}</div>
							</div>
							<div class="detail">
								<div class="detail-title">描述</div>
								<div class="detail-content article-content">${mi.milepostDescribe}</div>
							</div>
							<div class="detail">
								<div class="detail-title">开始时间</div>
								<div class="detail-content article-content">${mi.startTime}</div>
							</div>
							<div class="detail">
								<div class="detail-title">结束时间</div>
								<div class="detail-content article-content">${mi.endTime}</div>
							</div>
							<div class="detail">
								<div class="detail-title">关联项目</div>
								<div class="detail-content article-content">
									 ${projrckname}
								</div>
							</div>
							<div class="detail">
								<div class="detail-title">关联模块</div>
								<c:forEach items="${needNameList}" var="needNameList">
								<div class="detail-content article-content">
									 ${needNameList.need_name}
								</div>
								</c:forEach>
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
												<th>创建者</th>
												<td title="/">
												${mi.authorName}
												</td>
											</tr>
											<tr class="nofixed">
												<th>创建时间</th>
												<td title="/">
												${mi.createTime}
												</td>
											</tr>
											<tr class="nofixed">
												<th>修改时间</th>
												<td title="/">
												${mi.editTime}
												</td>
											</tr>
											<tr class="nofixed">
												<th>验收者</th>
												<td title="/">
												${mi.checkName}
												</td>
											</tr>
											<tr class="nofixed">
												<th>验收时间</th>
												<td title="/">
												${mi.checkTime}
												</td>
											</tr>
											<tr class="nofixed">
												<th>状态</th>
												<td title="/">
												<c:if test="${mi.milepostState == 0}">
														已删除
												</c:if>
												<c:if test="${mi.milepostState == 1}">
														正常
												</c:if>
												<c:if test="${mi.milepostState == 2}">
														正常
												</c:if>
												</td>
											</tr>
											<tr class="nofixed">
												<th>备注</th>
												<td title="/">
												${mi.milepostRemarks}
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
					</div>
				</div>
				<!--mainActions end-->
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>