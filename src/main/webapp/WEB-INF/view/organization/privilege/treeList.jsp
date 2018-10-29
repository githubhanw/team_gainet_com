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
<title>权限管理</title>
<%@ include file="/WEB-INF/view/comm/cssjs.jsp"%>
<style>
.table-bymodule select.form-control {
	height: 250px
}

.group-item {
	display: block;
	width: 220px;
	float: left;
	font-size: 14px
}

.group-item .checkbox-inline label {
	padding-left: 8px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.table.table-form tbody>tr:last-child td {
	border-top: 1px solid #ddd
}

@
-moz-document url-prefix (){ .table .table-form tbody>tr:last-childtd,
	.table.table-formtbody>tr :last-childth {
	border-bottom: 1pxsolid #ddd
}
}
</style>
</head>
<body>
	<!--header start-->
	<header id="header">
		<%@ include file="/WEB-INF/view/comm/main_header.jsp"%>
		<%@ include file="/WEB-INF/view/comm/sub_header.jsp"%>
	</header>
	<!--header end-->
	<main id="main">
	<div class="container">
		<div id="mainMenu" class="clearfix">
			<div class="btn-toolbar pull-left">
				<span class="btn btn-link btn-active-text"><span class="text">权限结构</span></span>
			</div>
		</div>
		<div id="mainContent" class="main-row">
			<div class="side-col col-4">
				<div class="panel" style="height:700px; overflow-y:auto">
					<div class="panel-body">
						<ul class="tree tree-lines tree-angles" data-ride="tree">
							<c:if test="${privilegeTree != null}">
								<c:forEach items="${privilegeTree}" var="item">
									<li class="has-list ${entity.parentId == item.id ? ' open in':''}">
										<c:if test="${item.childrens != null}">
											<i class="list-toggle icon"></i>
											<a href="organization/privilege/treeList?id=${item.id}">[${item.rank}] ${item.name}</a>
											<ul data-idx="1">
												<c:forEach items="${item.childrens}" var="chirden">
													<li>
														<a ${chirden.id==entity.id?'style="font-weight:bold;color:red"':''} 
																href="organization/privilege/treeList?id=${chirden.id}">
															[${chirden.rank}] ${chirden.name} (${chirden.url}) 
														</a>
													</li>
												</c:forEach>
											</ul>
										</c:if>
										<c:if test="${item.childrens == null}">
											<a href="organization/privilege/treeList?id=${item.id}">[${item.rank}] ${item.name}</a>
										</c:if>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
			<div class="main-col col-12" style="display: block;">
				<div class="panel">
					<div class="panel-body">
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="updateForm" method="post">
									<tr>
										<th>权限名称</th>
										<td class="required"><input type="text" name="name"
											id="name" value="${entity.name}"
											class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>父权限名称</th>
										<td class="required"><select
											class="form-control chosen-select" name="parent_id"
											id="parent_id" onchange="selectParent()">
												<option value="0">父级权限</option>
												<c:forEach items="${parentList}" var="parent"
													varStatus="sta">
													<option value="${parent.id}"
														${parent.id==entity.parentId?'selected="selected"':''}>${parent.name}</option>
												</c:forEach>
										</select></td>
										<td></td>
									</tr>
									<tr id="privilegeUrl"
										${entity.id==null || entity.parentId==0?'style="display:none"':''}>
										<th>权限URL</th>
										<td class="required"><input type="text" name="url"
											id="url" value="${entity.url}"
											class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>顺序</th>
										<td class="required"><input type="text" name="rank"
											id="rank" value="${entity.rank}"
											class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
										<input type="hidden" name="id" value="${entity.id}" />
									</tr>
								</form>
								<tr>
									<td colspan="3" class="form-actions">
										<button id="submit" class="btn btn-wide btn-primary"
											data-loading="稍候...">保存</button> <a
										href="javascript:history.go(-1);"
										class="btn btn-back btn btn-wide">返回</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<c:if test="${allRole != null }">
				<div class="main-col col-12" style="display: block;">
					<div class="panel">
						<div class="panel-body">
							<table class="table table-hover table-striped table-bordered"
								id="privList">
								<tbody>
									<form id="saveRoles" class="main-table table-task skip-iframe-modal" method="post">
										<tr class="even">
											<td class="pv-10px">
												<c:forEach items="${allRole}" var="sub" varStatus="sta">
													<div class="group-item">
														<div class="checkbox-primary checkbox-inline">
															<c:set var="checked" value="false"></c:set>
															<c:forEach items="${roleList}" var="roless">
																<c:if test="${roless.id==sub.id }">
																	<c:set var="checked" value="true"></c:set>
																</c:if>
															</c:forEach>
															<input type="checkbox" name="roles" ${checked ? 'checked="checked"' : "" } value="${sub.id}" id="${sub.id}">
															<label>${sub.name}</label>
														</div>
													</div>
												</c:forEach>
											</td>
											<input type="hidden" name="allRole" id="allRole"/>
											<input type="hidden" name="id" value="${entity.id}" />
										</tr>
									</form>
									<tr>
										<td class="form-actions">
											<div class="group-item" style="width:80px">
												<div class="checkbox-primary checkbox-inline">
													<input type="checkbox" id="allChecker">
													<label>全选</label>
												</div>
											</div>
											<button id="saveRole" class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
											<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">返回</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</c:if>
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
						<br /> <br />
						<hr class="small" />
						<p>
							<strong>您现在可以进行以下操作：</strong>
						</p>
						<div>
							<a href="organization/privilege/treeList?id=${entity.id}" class="btn">刷新</a>
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
	<script type="text/javascript">
		function selectParent() {
			$("#url").val("");
			$("#rank").val("");
			var param = $('#parent_id option:selected').val();

			if (param == 0) {
				$("#privilegeUrl").hide();
			}
			if (param != 0) {
				$("#privilegeUrl").show();
			}
		}
	</script>
</body>
<script>
	$("#submit").click(function() {
		$.ajaxSettings.async = false;
		$.ajax({
			type : "POST",
			url : "organization/privilege/addOrUpd?r=" + Math.random(),
			data : $("#updateForm").serialize(),
			dataType : "json",
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
		})
		$.ajaxSettings.async = true;
	});

	$("#saveRole").click(function() {
		$.ajaxSettings.async = false;
		$("#allRole").val(getChecked());
		$.ajax({
			type : "POST",
			url : "organization/privilege/saveRole?r=" + Math.random(),
			data :$("#saveRoles").serialize(),
			dataType : "json",
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
		})
		$.ajaxSettings.async = true;
	});
	function getChecked() {
		var checkValue = [];
		$(':checkbox').each(function(){
			if(!!$(this).prop('checked') && !!$(this).prop('name')) {
				checkValue.push($(this).val());
			}
		});
		return checkValue.join(",");
	}
	$(function() {
	    $('#allChecker').change(function() {
            $('input[type=checkbox]').prop('checked', $(this).prop('checked'));
	    });
	});
</script>
</html>