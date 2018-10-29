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
		<title>批量创建模块</title>
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
				<div id="mainContent" class="main-content">
					<div class="main-header">
						<h2>
							<c:if test="${n.id == null}">批量创建</c:if>
							<c:if test="${n.id != null}">
								<span class="label label-id">${n.id}</span>
								<a href="my/need">${n.needName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 批量建子模块</small>
							</c:if>
						</h2>
					</div>
					<div class="table-responsive">
						<table class="table table-form">
							<thead>
								<tr>
									<th class="w-30px col-id">ID</th>
									<th class="w-150px col-plan required">所属项目</th>
									<th class="w-130px required">模块来源</th>
									<th class="w-130px required">需求方</th>
									<th class="col-name has-btn required">模块名称</th>
									<th class="w-150px">指派给</th>
									<th class="w-120px col-estimate">开始时间</th>
									<th class="w-120px col-review">结束时间</th>
									<th class="w-150px col-pri">优先级</th>
									<th class="w-50px"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" begin="0" end="9" varStatus="status">
									<form id="addForm${status.index}">
										<tr data-row="${status.index}" class="">
											<td class="col-id">${status.index+1}</td>
											<td style="overflow: visible">
												<select class="form-control chosen chosen-select" name="project_id" ${n.id == null ? '' : 'disabled="disabled"'}>
													<c:forEach items="${project}" var="p" varStatus="sta">
														<option value="${p.id}" ${p.id==n.projectId?'selected="selected"':''}>${p.project_name }</option>
													</c:forEach>
												</select>
												<input type="hidden" name="id" id="id" value="${n.id}"/>
											</td>
											<td style="overflow: visible">
												<select data-placeholder="请选择模块来源" class="form-control chosen-select" name="src_id" id="src_id">
													<option value=""></option>
													<c:forEach items="${needSrc}" var="src" varStatus="sta">
														<option ${n.srcId==src.id?'selected="selected"':'' } value="${src.id}">${src.need_src}</option>
													</c:forEach>
												</select>
											</td>
											<td class="overflow: visible">
												<select data-placeholder="请选需求方" class="form-control chosen-select" name="member_id" id="member_id">
													<option value=""></option>
													<c:forEach items="${members}" var="member" varStatus="sta">
														<option value="${member.id}" ${member.id==n.memberId?'selected="selected"':''}>${member.name}(${member.number})</option>
													</c:forEach>
												</select>
											</td>
											<td style="overflow: visible">
												<input type="text" name="need_name" class="form-control title-import">
											</td>
											<td class="overflow: visible">
												<select data-placeholder="请选被指派人员" class="form-control chosen-select" name="assigned_id" id="assigned_id">
													<option value=""></option>
													<c:forEach items="${members}" var="member" varStatus="sta">
														<option value="${member.id}">${member.name}(${member.number})</option>
													</c:forEach>
												</select>
											</td>
											<td class="overflow: visible">
												<input type="text" name="start_date" class="form-control form-date-limit" placeholder="默认今天"/>
											</td>
											<td class="overflow: visible">
												<input type="text" name="end_date" class="form-control form-date-limit" placeholder="默认明天"/>
											</td>
											<td style="overflow: visible">
												<select class="form-control chosen chosen-select" name="level">
													<option value="1">紧急重要</option>
													<option value="2">紧急不重要</option>
													<option value="3">不紧急重要</option>
													<option value="4">不紧急不重要</option>
												</select>
											</td>
											<td id="msg${status.index}"></td>
										</tr>
									</form>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="8" class="text-center form-actions">
										<button id="submit"
											class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
										<a href="javascript:history.go(-1);"
										class="btn btn-back btn btn-wide">返回</a>
									</td>
								</tr>
							</tfoot>
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
						<div style="margin: 0 auto; max-width: 430px">
							<p>
								<strong><span id="msg">成功</span> ，您现在可以进行以下操作：</strong>
							</p>
							<div>
								<a href="my/need/toAdd" class="btn">继续创建模块</a> <a
									href="my/task/toAdd" class="btn">创建任务</a> <a
									href="my/need" class="btn">返回我的模块</a>
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
$("input[name='need_name']").focus(function(){
	$("td[id^='msg']").text('')
})
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	var flag = true;
	for(var i=0;i<10;i++){
		if(!!$($("input[name='need_name']").get(i)).val()){
			if(!!$("#id").val()){
				$($("select[name='project_id']").get(i)).removeAttr("disabled");
				$($("select[name='src_id']").get(i)).removeAttr("disabled");
				$($("input[name='member_id']").get(i)).removeAttr("disabled");
			}
			$.ajax({type:"POST",url:"my/need/batchAdd?r=" + Math.random(),data:$("#addForm" + i).serialize(),dataType:"json",success:function(data){
				if(data.code == 0){
					$("#msg" + i).css("color", "blue");
					$("#msg" + i).text('成功');
				}else{
					$("#msg" + i).css("color", "red");
					$("#msg" + i).text('失败');
					flag = false;
				}
			}})
			if(!!$("#id").val()){
				$($("select[name='project_id']").get(i)).attr("disabled", "disabled");
				$($("select[name='src_id']").get(i)).attr("disabled", "disabled");
				$($("input[name='member_id']").get(i)).attr("disabled", "disabled");
			}
		}
	}
	if(flag){
		window.location.href="my/need";
	}
	$.ajaxSettings.async = true;
});
</script>