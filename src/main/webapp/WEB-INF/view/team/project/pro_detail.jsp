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
		<title>
			项目详情
		</title>
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
								项目详情
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>项目名称</th>
									<td>
										${p.projectName}
									</td>
									<td></td>
								</tr>
								<tr>
									<th>所属公司</th>
									<td>
										${p.company}
									</td>
									<td></td>
								</tr>
								<tr>
									<th>项目负责人</th>
									<td>
										${member_name}
									</td>
									<td></td>
								</tr>
								<tr>
									<th>创建时间</th>
									<td>
										${p.createTime}
									</td>
									<td></td>
								</tr>
								<tr>
									<th>状态</th>
									<td>
										<c:if test="${p.state=='0'}">无效</c:if>
										<c:if test="${p.state=='1'}">正常</c:if>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td>
										${p.remark}
									</td>
									<td></td>
								</tr>
								</form>
							</tbody>
						</table>
					</div>
				</div>
				<div id="mainActions">
					<nav class="container">
					</nav>
					<div class="btn-toolbar">
						<a href="team/project/index" class="btn btn-link"><i class="icon-goback icon-back"></i> 返回</a>
						<div class="divider"></div>
						<a href="team/project/toAdd?id=${p.id}" class='btn btn-link '><i class="icon-common-edit icon-edit"></i> 编辑</a>
						<a href="javascript:void(0)" onclick="del(${p.id})" class='btn btn-link '><i class="icon-common-delete icon-trash"></i> 删除</a>
						<a href="team/need/toAdd?project_id=${p.id}" class='btn btn-link '><i class="icon icon-plus"></i> 提需求</a>
					</div>
				</div>
			</div>
		</main>
	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
function del(id){
	if(confirm("确认删除？")){
		$.ajaxSettings.async = false;
		$.getJSON("team/project/del?id=" + id + "&r=" + Math.random(), function(data) {
			alert(data.message);
			if(data.code == 0){
				window.location.reload();
			}
		});
		$.ajaxSettings.async = true;
	}
}
</script>