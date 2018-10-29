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
		<title>授权</title>
		<style>
			.table-bymodule select.form-control {height:250px}
			.group-item {display:block; width:220px; float:left; font-size: 14px}
			.group-item .checkbox-inline label{padding-left:8px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;}
			.table.table-form tbody > tr:last-child td {border-top: 1px solid #ddd}
			@-moz-document url-prefix(){.table.table-form tbody > tr:last-child td, .table.table-form tbody > tr:last-child th {border-bottom: 1px solid #ddd}}
		</style>
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
								<input type="hidden" value="${entity.id}" id="id" name="id">
								<span class="label label-id">${entity.id}</span>
								<a href="organization/role/index">${entity.name}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 授权</small>
							</h2>
						</div>
						<table class="table table-hover table-striped table-bordered" id="privList">
							<thead>
								<tr>
									<th class="text-center w-230px">模块</th>
									<td class="text-center">方法</td>
								</tr>
							</thead>
							<tbody>
								<!-- <form class="main-table table-task skip-iframe-modal" method="post"> -->
								<c:forEach items="${allPrivileges}" var="p" varStatus="sta">
									<c:if test="${p.parent_id == 0}">
										<tr class="even">
											<th class="text-middle text-right w-150px">
												<div class="checkbox-primary checkbox-inline checkbox-right check-all">
													<input type="checkbox" id="allChecker${p.id}">
													<label class="text-right" for="allChecker${p.id}">${p.name}</label>
												</div>
											</th>
											<td id="${p.id}" class="pv-10px">
												<c:forEach items="${allPrivileges}" var="sub" varStatus="sta">
													<c:if test="${sub.parent_id == p.id}">
														<div class="group-item">
															<div class="checkbox-primary checkbox-inline">
																<c:set var="checked" value="false"></c:set>
																<c:forEach items="${authorized}" var="ep">
																	<c:if test="${ep.privilege_id == sub.id}">
																		<c:set var="checked" value="true"></c:set>
																	</c:if>
																</c:forEach>
																<input type="checkbox" name="actions[${p.id}][]" ${checked ? 'checked="checked"' : "" } value="${sub.id}" id="actions[${p.id}]${sub.id}"> 
																<label for="actions[${p.id}]${sub.id}">${sub.name}</label>
															</div>
														</div>
													</c:if>
												</c:forEach>
											</td>
										</tr>
									</c:if>
								</c:forEach>
								<!-- </form> -->
								<tr>
									<th class="text-right">
										<div class="checkbox-primary checkbox-inline checkbox-right check-all">
											<input type="checkbox" id="allChecker">
											<label class="text-right" for="allChecker">全选</label>
										</div>
									</th>
									<td class="form-actions">
										<button type="submit" id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
										<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">返回</a>
									</td>
								</tr>
							</tbody>
						</table>
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
						<div style="margin: 0 auto">
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="organization/role/index" class="btn">返回角色列表</a>
								<a href="organization/role/toAdd" class="btn">新建角色</a>
								<a href="organization/role/toEdit?id=${entity.id}" class="btn">修改角色</a>
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
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
	<script>
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
	    $('#privList > tbody > tr > th input[type=checkbox]').change(function() {
	        var id      = $(this).attr('id');
	        var checked = $(this).prop('checked');

	        if(id == 'allChecker') {
	            $('input[type=checkbox]').prop('checked', checked);
	        } else {
	            $(this).parents('tr').find('input[type=checkbox]').prop('checked', checked);
	        }
	    });
	});
	$("#submit").click(function(){
		$.ajaxSettings.async = false;
		var privileges = getChecked();
		var id = $("#id").val();
		var params = [];
		params.push("id=" + id);
		params.push("privileges=" + privileges);
		$.ajax({type:"POST",url:"organization/role/auth?r=" + Math.random(),data:params.join("&"),dataType:"json",success:function(data){
			if(data.code == 0){
				$("#msg").text(data.message);
				$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
			}else{
				$("#errMsg").text(data.message);
				$('#errModal').modal({keyboard: false,show: true, moveable: true});
			}
		}})
		$.ajaxSettings.async = true;
	});
	</script>
</html>