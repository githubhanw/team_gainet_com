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
		<title>项目详情：${p.projectName}</title>
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
							<span class="label label-id">${p.id}</span> <span class="text">${p.projectName}</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-12">
					<div class="cell">
						<div class="detail">
							<div class="detail-title">项目详情</div>
						</div>
						<table class="table table-form">
							<tbody>
								<tr>
									<th>项目名称</th>
									<td>${p.projectName}</td>
									<td></td>
								</tr>
								<tr>
									<th>所属公司</th>
									<td>${p.company}</td>
									<td></td>
								</tr>
								<tr>
									<th>项目负责人</th>
									<td>${member_name}</td>
									<td></td>
								</tr>
								<tr>
									<th>创建时间</th>
									<td>${p.createTime}</td>
									<td></td>
								</tr>
								<tr>
									<th>状态</th>
									<td>
										<c:if test="${p.state=='0'}">无效</c:if>
										<c:if test="${p.state=='1'}">正常</c:if>
										<c:if test="${p.state=='2'}">项目待验收</c:if>
										<c:if test="${p.state=='3'}">项目已验收</c:if>
										<c:if test="${p.state=='4'}">项目已完成</c:if>
										<c:if test="${p.state=='5'}">已拆分模块</c:if>
										<c:if test="${p.state=='6'}">里程碑待确认</c:if>
										<c:if test="${p.state=='7'}">里程碑待验收</c:if>
										<c:if test="${p.state=='8'}">里程碑及概要设计已确认</c:if>
										<c:if test="${p.state=='9'}">待测试</c:if>
										<c:if test="${p.state=='10'}">已完成测试</c:if>
										<c:if test="${p.state=='11'}">里程碑已验收</c:if>
										<c:if test="${p.state=='12'}">里程碑报告待验收</c:if>
										<c:if test="${p.state=='13'}">里程碑报告已验收</c:if>
										
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td>${p.remark}</td>
									<td></td>
								</tr>
							</tbody>
						</table>
					
				<c:if test="${needTask != null}">
					<div class="detail">
						<div class="detail-title">同项目模块</div>
						<div class="detail-content article-content">
							<table class="table table-hover table-fixed">
								<thead>
									<tr class="text-center">
										<th class="w-80px">ID</th>
										<th class="w-300px">模块名称</th>
										<th>模块方</th>
										<th>指派给</th>
										<th>创建时间</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>验收时间</th>
										<th>阶段</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${needTask}" var="item" varStatus="sta">
									<tr class="text-center">
										<td>${item.id}</td>
										<td><a href="team/need/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.need_name }">${item.need_name }</a></td>
										<td>${item.member_name}</td>
										<td>${item.assigned_name}</td>
										
										<td>${item.create_time}</td>
										<td>${item.start_date}</td>
										<td>${item.end_date}</td>
										<td>${item.checked_time}</td>
										<td>${item.stage==1?'待验收':item.stage==2?'验收完成':item.stage==3?'验收不通过':'其他'}</td>
										<td>${item.state == 1 ? '未开始' : item.state == 2 ? '进行中' : item.state == 3 ? '待验收' : item.state == 4 ? '已验收' : item.state == 5 ? '已关闭' : '未知'}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
				</div>
				</div>
				<div id="mainActions">
					<nav class="container">
					</nav>
					<div class="btn-toolbar">
						<a href="team/project/index" class="btn btn-link"><i class="icon-goback icon-back"></i> 返回</a>
						<div class="divider"></div>
						<a href="team/project/toedit?id=${p.id}" class='btn btn-link '><i class="icon-common-edit icon-edit"></i> 编辑</a>
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