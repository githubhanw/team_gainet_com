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
		<title>添加代码审查</title>
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
						<span class="label label-id">${task.id}</span>
						${task.taskName}
						<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 添加代码审查</small>
						</h2>
					</div>
					<div class="table-responsive">
						<table class="table table-form">
							<thead>
								<tr>
									<th class="w-30px col-id">ID</th>
									<th class="w-200px col-plan required">界面</th>
									<th class="w-200px col-plan required">入口点</th>
									<th class="col-name has-btn required">在线URL</th>
									<th class="col-name has-btn required">源文件</th>
									<th class="col-name has-btn required">备注</th>
									<th class="w-50px"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" begin="0" end="4" varStatus="status">
									<form id="addForm${status.index}">
										<tr data-row="${status.index}" class="">
											<td class="col-id">${status.index+1}</td>
											<td style="overflow: visible">
												<input type="text" name="report_interface" class="form-control title-import">
											</td>
											<td style="overflow: visible">
												<input type="text" name="entry_point" class="form-control title-import">
											</td>
											<td style="overflow: visible">
												<input type="text" name="online_url" class="form-control title-import">
											</td>
											<td style="overflow: visible">
												<textarea name="source_file" class="form-control title-import"></textarea>
											</td>
											<td style="overflow: visible">
												<textarea name="report_remark" class="form-control title-import"></textarea>
											</td>
											<input type="hidden" name="task_id" value="${task.id}">
											<input type="hidden" name="report_type" value="0">
											<td id="msg${status.index}"></td>
										</tr>
									</form>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="8" class="text-center form-actions">
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
					
					<div class="table-responsive">
						<table class="table table-form">
							<thead>
								<tr>
								    <th class="w-30px col-id">ID</th>
									<th class="w-200px col-plan required">流程</th>
									<th class="w-200px col-plan required">对应界面入口点</th>
									<th class="col-name has-btn required">函数入口点</th>
									<th class="col-name has-btn required">文件名</th>
									<th class="col-name has-btn required">备注</th>
									<th class="w-50px"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="i" begin="5" end="9" varStatus="status">
									<form id="addForm${status.index}">
										<tr data-row="${status.index}" class="">
											<td class="col-id">${status.index+1}</td>
											<td style="overflow: visible">
												<input type="text" name="report_interface" class="form-control title-import">
											</td>
											<td style="overflow: visible">
												<input type="text" name="entry_point" class="form-control title-import">
											</td>
											<td style="overflow: visible">
												<input type="text" name="online_url" class="form-control title-import">
											</td>
											<td style="overflow: visible">
												<textarea name="source_file" class="form-control title-import"></textarea>
											</td>
											<td style="overflow: visible">
												<textarea name="report_remark" class="form-control title-import"></textarea>
											</td>
											<input type="hidden" name="task_id" value="${task.id}">
											<input type="hidden" name="report_type" value="1">
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
$("input[name='report_interface']").blur(function(){
	var leave = true;
	$("input[name='report_interface']").each(function() {
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
$("input[name='report_interface']").focus(function(){
	$("td[id^='msg']").text('')
})
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	var flag = true;
	for(var i=0;i<10;i++){
		if(!!$($("input[name='report_interface']").get(i)).val()){
			if(!!$("#id").val()){
				$($("select[name='need_id']").get(i)).removeAttr("disabled");
				$($("select[name='task_type']").get(i)).removeAttr("disabled");
			}
			if(!!$("#needId").val()){
				$($("select[name='need_id']").get(i)).removeAttr("disabled");
			}
			$.ajax({type:"POST",url:"code/report/add?r=" + Math.random(),data:$("#addForm" + i).serialize(),dataType:"json",success:function(data){
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
		window.location.href="my/task";
	}
	$.ajaxSettings.async = true;
});
</script>