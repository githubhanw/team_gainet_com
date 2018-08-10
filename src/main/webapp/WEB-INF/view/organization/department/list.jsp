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
		<title>团队列表</title>
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
						<span class="btn btn-link btn-active-text"><span class="text">部门结构</span></span>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="side-col col-4">
						<div class="panel">
							<!-- <div class="panel-heading">
								<div class="panel-title">景安网络</div>
							</div> -->
							<div class="panel-body">
								<ul class="tree tree-lines tree-angles" data-ride="tree">
									<c:if test="${departmentTree != null}">
										<c:forEach items="${departmentTree}" var="item">
											<li class="has-list">
												<c:if test="${item.childrens != null}">
													<i class="list-toggle icon"></i>
													<a href="organization/department/index?id=${item.id}">${item.name}</a>
													<ul data-idx="1">
														<c:forEach items="${item.childrens}" var="chirden">
															<li><a href="organization/department/index?id=${chirden.id}">${chirden.name}</a></li>
														</c:forEach>
													</ul>
												</c:if>
												<c:if test="${item.childrens == null}">
													<a href="organization/department/index?id=${item.id}">${item.name}</a>
												</c:if>
											</li>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</div>
					</div>
					<div class="main-col col-8">
					    <div class="panel">
					      <!-- <div class="panel-heading">
					        <div class="panel-title">下级部门</div>
					      </div> -->
					      <div class="panel-body">
					       <!--  <div class="main-header">
								<h2>团队</h2>
							</div> -->
							<table class="table table-form">
								<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
									<tr>
										<th>名称</th>
										<td class="required">
											<input type="text" name="name" id="name" value="${entity.name}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>负责人</th>
										<td class="required">
											<select data-placeholder="选择负责人" class="form-control chosen-select"  name="leader_id" id="leader_id">
												<option value=""></option>
												<c:forEach items="${members}" var="p" varStatus="sta">
													<option value="${p.id}" ${p.id==entity.leaderId?'selected="selected"':''}>${p.name}</option>
												</c:forEach>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>部门</th>
										<td class="required">
											<select data-placeholder="选择部门" class="form-control chosen-select" name="parent_id" id="parent_id">
												<option value=""></option>
												<c:forEach items="${departments}" var="p" varStatus="sta">
													<option value="${p.id}" ${p.id==entity.parentId?'selected="selected"':''}>${p.name}</option>
												</c:forEach>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>排序</th>
										<td class="required">
											<input type="text" name="sort" id="sort" value="${entity.sort}" class="form-control" autocomplete="off">
											<input type="hidden" name="id" id="id" value="${entity.id}">
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
				</div>
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
	<script>
	$('select.chosen-select').chosen({
	    no_results_text: '没有找到',    // 当检索时没有找到匹配项时显示的提示文本
	    disable_search_threshold: 10, // 10 个以下的选择项则不显示检索框
	    search_contains: true         // 从任意位置开始检索
	});
	$("#submit").click(function(){
		$.ajaxSettings.async = false;
		$.ajax({type:"POST",url:"organization/department/addOrUpdate?r=" + Math.random(),data:$("form").serialize(),dataType:"json",success:function(data){
			alert(data.message);
			if(data.code == 0){
				window.location.href = "organization/department/index";
			}
		}})
		$.ajaxSettings.async = true;
	});
	</script>
</html>