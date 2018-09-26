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
		<meta http-equiv="Content-Type" content="multipart/form-data;charset=utf-8" />
		<title>建任务</title>
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
							<h2>建任务</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post" enctype="multipart/form-data">
								<tr>
									<th>任务名称</th>
									<td class="required">
										<input type="text" name="task_name" id="task_name" value="" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>所属需求</th>
									<td class="required">
										<select data-placeholder="请选择需求" class="form-control chosen chosen-select" name="need_id" id="need_id">
											<option value=""></option>
											<c:forEach items="${need}" var="n" varStatus="sta">
												<option value="${n.id}" ${n.id==need_id?'selected="selected"':''}>${n.need_name}</option>
											</c:forEach>
										</select>
									<td></td>
								</tr>
								<tr>
									<th>指派给</th>
									<td class="required">
										<select data-placeholder="指派给" class="form-control chosen-select" name="assigned_id" id="assigned_id">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<option value="${member.id}">${member.name}(${member.number})</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>任务类型</th>
									<td class="required">
										<select class="form-control chosen chosen-select" name="task_type" id="task_type">
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
									<td></td>
								</tr>
								<tr>
									<th>优先级</th>
									<td class="required">
										<select class="form-control chosen chosen-select" name="level" id="level">
											<option value="1">紧急重要</option>
											<option value="2">紧急不重要</option>
											<option value="3">不紧急重要</option>
											<option value="4">不紧急不重要</option>
										</select>
									<td></td>
								</tr>
								<tr>
									<th>任务开始日期</th>
									<td class="required">
										<input type="text" name="start_date" id="start_date" value="<fmt:formatDate value="${t.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
												class="form-control form-date-limit" placeholder="任务开始日期" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>任务结束日期</th>
									<td class="required">
										<input type="text" name="end_date" id="end_date" value="<fmt:formatDate value="${t.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
												class="form-control form-date-limit" placeholder="任务结束日期" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
									<td></td>
								</tr>
								<tr>
								    <th>界面原型</th>
								    <td class="required">
									<input type="file" name="filePrototype" multiple="multiple" accept="image/*"/>
								    </td>
								    <td></td>
								</tr>
								<tr>
								    <th>流程图</th>
								    <td class="required">
								    <input type="file" name="filetree" multiple="multiple" accept="image/*"/>
									</td>
								    <td></td>
								</tr>
								<tr>
									<th>任务描述</th>
									<td>
										<div id="remark" style="width:100%;">
											<input type="hidden" name="remark">
										</div>
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
						<div style="margin: 0 auto">
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="team/task/toAdd" class="btn">继续建任务</a>
								<a href="team/task/toAdd" class="btn">批量建任务</a>
								<a href="team/task/index" class="btn">返回任务列表</a>
								<a href="team/need/index" class="btn">返回需求列表</a>
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
	var editor = new UE.ui.Editor();
    editor.render("remark");
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
	UE.Editor.prototype.getActionUrl = function(action){  
		if(action == 'uploadimage' || action == 'uploadscrawl'){  
			return '<%=basePath%>ueditor/upload';  
		}else{  
			return this._bkGetActionUrl.call(this, action);  
		}  
	};  
    UE.getEditor('remark');
	$("#submit").click(function(){
		$.ajaxSettings.async = false;
		$("input[name='remark']").val(UE.getEditor('remark').getContent());
		var form = new FormData(document.getElementById("createForm"));
		$.ajax({
	         url:"team/task/addTask?r=" + Math.random(),
	         type:"post",
	         data:form,
	         dataType:"json",
	         processData:false,
	         contentType:false,
	         success:function(data){
	        	if(data.code == 0){
	 				$("#msg").text(data.message);
	 				$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
	 			}else{
	 				$("#errMsg").text(data.message);
	 				$('#errModal').modal({keyboard: false,show: true, moveable: true});
	 			}
	         }
	     });
		
//		$.ajax({type:"POST",url:"team/task/add?r=" + Math.random(),data:$("form").serialize(),
//				dataType:"json",success:function(data){
//			if(data.code == 0){
//				$("#msg").text(data.message);
//				$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
//			}else{
//				$("#errMsg").text(data.message);
//				$('#errModal').modal({keyboard: false,show: true, moveable: true});
//			}
//		}})
		$.ajaxSettings.async = true;
	});
	</script>
</html>