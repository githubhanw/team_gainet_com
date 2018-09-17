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
		<title>批量创建任务</title>
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
							<c:if test="${t.id == null}">批量创建</c:if>
							<c:if test="${t.id != null}">
								<span class="label label-id">${t.id}</span>
								<a href="team/need/index">${t.taskName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 批量建子任务</small>
							</c:if>
						</h2>
					</div>
					<div class="table-responsive">
						<table class="table table-form">
							<thead>
								<tr>
									<th class="w-30px col-id">ID</th>
									<th class="w-150px col-plan">所属需求</th>
									<th class="w-100px required">任务类型</th>
									<th class="col-name has-btn required">任务名称</th>
									<th class="w-180px">指派给</th>
									<th class="w-120px col-estimate">开始时间</th>
									<th class="w-120px col-review">结束时间</th>
									<th style="width:14%">任务描述</th>
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
												<select class="form-control chosen chosen-select" name="need_id" ${(t.id == null && need_id == null) ? '' : 'disabled="disabled"'}>
													<c:forEach items="${need}" var="n" varStatus="sta">
														<option value="${n.id}" ${(n.id==t.needId || need_id==n.id)?'selected="selected"':''}>${n.need_name }</option>
													</c:forEach>
												</select>
												<input type="hidden" name="id" id="id" value="${t.id}"/>
												<%-- 用于下方js判断 --%>
												<input type="hidden" id="needId" value="${need_id}"/>
											</td>
											<td style="overflow: visible">
												<select class="form-control chosen chosen-select" name="task_type" ${t.id == null ? '' : 'disabled="disabled"'}>
													<option ${t.taskType=='1'?'selected="selected"':'' } value="1">开发</option>
													<option ${t.taskType=='2'?'selected="selected"':'' } value="2">测试</option>
													<option ${t.taskType=='3'?'selected="selected"':'' } value="3">设计</option>
													<option ${t.taskType=='4'?'selected="selected"':'' } value="4">前端</option>
													<option ${t.taskType=='5'?'selected="selected"':'' } value="5">维护</option>
													<option ${t.taskType=='6'?'selected="selected"':'' } value="6">需求</option>
													<option ${t.taskType=='7'?'selected="selected"':'' } value="7">研究</option>
													<option ${t.taskType=='8'?'selected="selected"':'' } value="8">讨论</option>
													<option ${t.taskType=='9'?'selected="selected"':'' } value="9">运维</option>
													<option ${t.taskType=='10'?'selected="selected"':'' } value="10">事务</option>
													<option ${t.taskType=='0'?'selected="selected"':'' } value="0">其他</option>
												</select>
											</td>
											<td style="overflow: visible">
												<input type="text" name="task_name" class="form-control title-import">
											</td>
											<td style="overflow: visible">
												<select data-placeholder="指派给" class="form-control chosen-select" name="assigned_id" id="assigned_id">
													<option value=""></option>
													<c:forEach items="${members}" var="member" varStatus="sta">
														<option value="${member.id}">${member.name}(${member.number})</option>
													</c:forEach>
												</select>
											</td>
											<td class="overflow: visible">
												<input type="text" name="start_date" id="start_date" class="form-control form-date-limit" placeholder="默认今天"/>
											</td>
											<td class="overflow: visible">
												<input type="text" name="end_date" id="end_date" class="form-control form-date-limit" placeholder="默认明天"/>
											</td>
											<td style="overflow: visible">
												<textarea name="remark" class="form-control title-import"></textarea>
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
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
$("input[name='task_name']").blur(function(){
	var leave = true;
	$("input[name='task_name']").each(function() {
		if(!!$(this).val()){
			leave = false;
		}
	})
	if(leave){
		$(window).unbind('beforeunload');
	}else{
		$(window).bind('beforeunload',function(){return '您输入的内容尚未保存，确定离开此页面吗？';});
	}
})
$("input[name='task_name']").focus(function(){
	$("td[id^='msg']").text('')
})
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	var flag = true;
	for(var i=0;i<10;i++){
		if(!!$($("input[name='task_name']").get(i)).val()){
			if(!!$("#id").val()){
				$($("select[name='need_id']").get(i)).removeAttr("disabled");
				$($("select[name='task_type']").get(i)).removeAttr("disabled");
			}
			if(!!$("#needId").val()){
				$($("select[name='need_id']").get(i)).removeAttr("disabled");
			}
			$.ajax({type:"POST",url:"team/task/add?r=" + Math.random(),data:$("#addForm" + i).serialize(),dataType:"json",success:function(data){
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
				$($("select[name='need_id']").get(i)).attr("disabled", "disabled");
				$($("select[name='task_type']").get(i)).attr("disabled", "disabled");
			}
			if(!!$("#needId").val()){
				$($("select[name='need_id']").get(i)).attr("disabled", "disabled");
			}
		}
	}
	if(flag){
		//卸载离开拦截事件
		$(window).unbind('beforeunload');
		window.location.href="team/task/index";
	}
	$.ajaxSettings.async = true;
});
</script>