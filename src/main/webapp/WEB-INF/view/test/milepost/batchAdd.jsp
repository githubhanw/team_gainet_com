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
		<title>批量创建里程碑</title>
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
							批量创建
								<span class="label label-id"></span>
								<a href="team/need/index"></a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp;</small>
						</h2>
					</div>
					<div class="table-responsive">
						<table class="table table-form">
							<thead>
								<tr>
									<th class="w-50px col-id">ID</th>
									<th class="w-250px col-plan">名称</th>
									<th class="w-200px col-pri">所属项目</th>
									<th class="w-300px col-pri">选择模块</th>
									<th class="w-180px col-estimate">开始时间</th>
									<th class="w-180px col-review">结束时间</th>
									<th style="width:25%">描述</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" begin="0" end="9" varStatus="status">
									<form id="addForm${status.index}">
										<tr data-row="${status.index}" class="">
											<td class="col-id">${status.index+1}</td>
											<td style="overflow: visible">
												<input type="text" name="need_name" class="form-control title-import">
											</td>
											<td class="overflow: visible">
											    <input type="hidden"  id="taskProject_id" name="taskProject_id" value="${tp.id}">
													${tp.projectName}
											</td>
											<td>
												<select data-placeholder="请选择模块" class="form-control chosen chosen-select" 
												multiple name="task_needid" id="task_needid" onchange="getDate('${status.index+1}')">
													<option value=""></option>
													<c:forEach items="${taskNeed}" var="taskNeed" varStatus="sta">
														<c:set var="isSelected" value="0" />
														<c:forEach items="${fn:split(t.link, ',')}" var="needId" varStatus="sta">
															<c:if test="${taskNeed.id == needId }">
																<option value="${taskNeed.id}" selected="selected">${taskNeed.need_name}[${taskNeed.start_date}/${taskNeed.end_date}]</option>
																<c:set var="isSelected" value="1" />
															</c:if>
														</c:forEach>
														<c:if test="${isSelected == 0}">
															<option value="${taskNeed.id}">${taskNeed.need_name}[${taskNeed.start_date}/${taskNeed.end_date}]</option>
														</c:if>
													</c:forEach>
												</select>
											</td>
											<!-- <td class="overflow: visible">
										        <input type="text" name="start_date" id="start_date"
												class="form-control form-date-limit" placeholder="请选择时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
											<td class="overflow: visible">
											    <input type="text" name="end_date" id="end_date"
												class="form-control form-date-limit" placeholder="请选择时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td> -->
											<td class="overflow: visible">
										        <input type="text" name="start_date" id="start_date${status.index+1}" 
												class="form-control" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
											<td class="overflow: visible">
											    <input type="text" name="end_date" id="end_date${status.index+1}"
												class="form-control" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
											<td style="overflow: visible">
												<textarea name="need_remark" class="form-control title-import"></textarea>
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
$("input[name='need_name']").blur(function(){
	var leave = true;
	$("input[name='need_name']").each(function() {
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
$("input[name='need_name']").focus(function(){
	$("td[id^='msg']").text('')
})
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	var flag = true;
	for(var i=0;i<10;i++){
		if(!!$($("input[name='need_name']").get(i)).val()){
			if(!!$("#id").val()){
				$($("select[name='need_id']").get(i)).removeAttr("disabled");
				$($("select[name='task_type']").get(i)).removeAttr("disabled");
			}
			if(!!$("#needId").val()){
				$($("select[name='need_id']").get(i)).removeAttr("disabled");
			}
			$.ajax({type:"POST",url:"test/milepost/add?r=" + Math.random(),data:$("#addForm" + i).serialize(),dataType:"json",success:function(data){
				if(data.code == 0){
					$("#msg" + i).css("color", "blue");
					$("#msg" + i).text('成功');
				}else{
					$("#msg" + i).css("color", "red");
					$("#msg" + i).text(data.message);
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
		window.location.href="test/milepost/manage";
	}
	$.ajaxSettings.async = true;
});


	function getDate(index) {
		$("#start_date" + index).val('');
		$("#end_date" + index).val('');
		$.ajax({
			type : "POST",
			url : "test/milepost/getDate?r=" + Math.random(),
			data:$("#addForm" + (index - 1)).serialize(),
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					$("#start_date" + index).val(data.startDate);
					$("#end_date" + index).val(data.endDate);
				} else {
					
				}
			}
		})
	}
</script>