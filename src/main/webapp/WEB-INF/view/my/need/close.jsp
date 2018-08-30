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
		<title>关闭：${n.needName }</title>
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
								<span class="label label-id">${n.id}</span>
								<a href="my/need">${n.needName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 关闭</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th class="w-80px">关闭原因</th>
									<td class="w-p25-f required">
										<select name="closedReason" id="closedReason" class="form-control chosen-select" onchange="setStory(this.value)">
											<option value="已完成">已完成</option>
											<option value="已细分">已细分</option>
											<option value="重复">重复</option>
											<option value="延期">延期</option>
											<option value="不做">不做</option>
											<option value="已取消">已取消</option>
											<option value="设计如此">设计如此</option>
										</select>
										<input type="hidden" name="id" value="${n.id}"/>
									</td>
									<td></td>
								</tr>
								<tr id="duplicateStoryBox" style="display: none">
									<th>重复需求</th>
									<td><input type="text" name="duplicateStory"
										id="duplicateStory" value="" class="form-control"
										autocomplete="off"></td>
									<td></td>
								</tr>
								<tr id="childStoriesBox" style="display: none">
									<th>细分需求</th>
									<td><input type="text" name="childStories" id="childStories"
										value="" class="form-control" autocomplete="off"></td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td colspan="2">
										<input type="hidden" name="comment">
										<div id="comment"></div>
									</td>
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
								<a href="my/need/toAdd" class="btn">继续创建需求</a> <a
									href="team/task/toAdd" class="btn">建任务</a> <a
									href="team/task/toAdd" class="btn">批量建任务</a> <a
									href="my/need/index" class="btn">返回需求列表</a>
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
UMEditor("comment");
function setStory(reason) {
	if (reason == '重复') {
		$('#duplicateStoryBox').show();
		$('#childStoriesBox').hide();
	} else if (reason == '已细分') {
		$('#duplicateStoryBox').hide();
		$('#childStoriesBox').show();
	} else {
		$('#duplicateStoryBox').hide();
		$('#childStoriesBox').hide();
	}
}
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$("input[name='comment']").val(UM.getEditor('comment').getContent());
	$.ajax({type:"POST",url:"my/need/close?r=" + Math.random(),data:$("form").serialize(),
			dataType:"json",success:function(data){
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