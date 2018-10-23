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
		<title>人员工作安排情况</title>
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
						<a href="team/task/search?type=2">
							<span class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}"><span class="text">人员历史动态</span></span>
						</a>
						<a href="team/task/search?type=1">
							<span class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}""><span class="text">人员安排</span></span>
						</a>
					</div>
					<div class="btn-toolbar pull-left" style="margin-left:30px;width:600px">
						<form action="team/task/search">
							<table style="width:100%">
								<tr>
									<%-- <td>
										<select class="form-control chosen chosen-select" name="level" id="level">
											<option value="">请选择部门或团队</option>
											<option ${prm.level=='1'?'selected="selected"':'' } value="1">紧急重要</option>
											<option ${prm.level=='2'?'selected="selected"':'' } value="2">紧急不重要</option>
											<option ${prm.level=='3'?'selected="selected"':'' } value="3">不紧急重要</option>
											<option ${prm.level=='4'?'selected="selected"':'' } value="4">不紧急不重要</option>
										</select>
									</td> --%>
									<td>
										<input type="hidden" name="type" value="${prm.type}"/>
										<input type="text" name="start_date" id="start_date" value="${prm.start_date}" class="form-control form-date" placeholder="选择计划开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
									<td>
										<input type="text" name="end_date" id="end_date" value="${prm.end_date}" class="form-control form-date" placeholder="选择计划结束时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
									<td>
										<button type="submit" id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">搜索</button>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="side-col col-4">
						<div class="panel">
							<div class="panel-heading">
								<div class="panel-title">
									无任务人员列表
									<span class="label label-light label-badge">${noTaskMember.size()}</span>
								</div>
							</div>
							<div class="panel-body">
								<table class="table has-sort-head">
									<tr>
										<td style="width:50%">团队</td>
										<td style="width:50%">姓名</td>
									</tr>
									<c:forEach items="${noTaskMember}" var="member" varStatus="sta">
										<tr>
											<td>${member.depName}</td>
											<td>${member.memName}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
					<div class="main-col col-4">
						<div class="panel">
							<div class="panel-heading">
								<div class="panel-title">
									有任务人员列表
									<span class="label label-light label-badge">${taskMember.size()}</span>
								</div>
							</div>
							<div class="panel-body">
								<table class="table has-sort-head">
									<tr>
										<td>团队</td>
										<td>姓名</td>
									</tr>
									<c:forEach items="${taskMember}" var="member" varStatus="sta">
										<tr>
											<td>${member.depName}</td>
											<td>${member.memName}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>