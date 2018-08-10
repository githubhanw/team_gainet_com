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
			<c:if test="${p.id==null}">添加项目</c:if>
			<c:if test="${p.id > 0}">修改项目</c:if>
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
								<c:if test="${p.id==null}">添加项目</c:if>
								<c:if test="${p.id > 0}">修改项目</c:if>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>项目名称</th>
									<td class="required">
										<input type="text" name="project_name" id="project_name" value="${p.projectName}" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>所属公司</th>
									<td class="required">
										<select class="form-control chosen chosen-select" name="company" id="company">
											<option ${p.company=='景安'?'selected="selected"':'' } value="景安">景安</option>
											<option ${p.company=='快云'?'selected="selected"':'' } value="快云">快云</option>
											<option ${p.company=='绿林客'?'selected="selected"':'' } value="绿林客">绿林客</option>
											<option ${p.company=='大数据'?'selected="selected"':'' } value="大数据">大数据</option>
										</select>
									<td></td>
								</tr>
								<tr>
									<th>项目负责人</th>
									<td class="required">
										<select data-placeholder="选择项目负责人" class="form-control chosen-select" name="member_id" id="member_id">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<option value="${member.id}" ${member.id==p.memberId?'selected="selected"':''}>${member.name}</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td>
										<div id="remark" name="remark"></div>
										<input type="hidden" name="id" value="${p.id}"/>
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
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="team/project/toAdd" class="btn">继续创建项目</a> <a
									href="team/need/toAdd" class="btn">提需求</a> <a
									href="team/need/toAdd" class="btn">批量提需求</a> <a
									href="team/project/index" class="btn">返回项目列表</a>
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
</html>
<script>
UMEditor("remark");
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$.ajax({type:"POST",url:"team/project/addOrUpd?r=" + Math.random(),data:$("form").serialize() + "&remark=" + UM.getEditor('remark').getContent(),dataType:"json",success:function(data){
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