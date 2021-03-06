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
								<form class="load-indicator main-form form-ajax" id="createForm" method="post" enctype="multipart/form-data">
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
								<%-- <tr>
									<th>所属分类</th>
									<td class="required">
										<select class="form-control chosen chosen-select" name="type" id="type">
											<c:if test="${p.projectType == 0}">
											<option value="${p.projectType}">内部项目</option>
											<option value="1">外部项目</option>
											</c:if>
											<c:if test="${p.projectType == 1}">
											<option value="${p.projectType}">外部项目</option>
											<option value="0">内部项目</option>
											</c:if>
										</select>
									<td></td>
								</tr> --%>
								<tr>
									<th>客户名称(公司名称)</th>
									<td class="required">
										<input type="text" name="customer_name" id="customer_name" value="${p.customerName}" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>模块负责人</th>
									<td class="required">
										<select data-placeholder="选择模块负责人" class="form-control chosen-select" name="demand_id" id="demand_id">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<option value="${member.id}" ${member.id==p.demandId?'selected="selected"':''}>${member.name}(${member.number})</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>项目工期</th>
									<td class="required">
										<input type="text" name="time_limit" id="time_limit" value="${p.timeLimit}" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>项目预算</th>
									<td class="required">
										<input type="text" name="budget" id="budget" value="${p.budget}" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>立项评估文件(路径)</th>
									<td class="required">
										<input type="text" name="file_url" id="file_url" value="${p.fileUrl}" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>工作量评估</th>
									<td class="required">
										<input type="text" name="assessment" id="assessment" value="${p.assessment}" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>项目负责人</th>
									<td class="required">
										<select data-placeholder="选择项目负责人" class="form-control chosen-select" name="member_id" id="member_id">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<option value="${member.id}" ${member.id==p.memberId?'selected="selected"':''}>${member.name}(${member.number})</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>项目成员</th>
									<td class="required">
										<select data-placeholder="请选择项目成员" class="form-control chosen chosen-select" multiple name="projectMembers" id="projectMembers">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<c:set var="isSelected" value="0" />
												<c:forEach items="${listProject}" var="projectMember" varStatus="sta">
													<c:if test="${member.id == projectMember.memberId }">
														<option value="${member.id}" selected="selected">${member.name }</option>
														<c:set var="isSelected" value="1" />
													</c:if>
												</c:forEach>
												<c:if test="${isSelected == 0}">
													<option value="${member.id}">${member.name }</option>
												</c:if>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>测试人员</th>
									<td class="required">
										<select data-placeholder="请选择测试人员" class="form-control chosen chosen-select" multiple name="testMembers" id="testMembers">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<c:set var="isSelected" value="0" />
												<c:forEach items="${listTest}" var="testMember" varStatus="sta">
													<c:if test="${member.id == testMember.memberId }">
														<option value="${member.id}" selected="selected">${member.name }</option>
														<c:set var="isSelected" value="1" />
													</c:if>
												</c:forEach>
												<c:if test="${isSelected == 0}">
													<option value="${member.id}">${member.name }</option>
												</c:if>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>是否加班</th>
									<td class="required">
										<input type="radio" name="overtime" value="1" ${p.overtime==1?'checked="checked"':'' }>是
										<input type="radio" name="overtime" value="0" ${p.overtime==0?'checked="checked"':'' } style="margin-left: 30px">否
									</td>
									<td></td>
								</tr>
								<tr>
									<th>开始日期</th>
									<td class="required">
										<input type="text" name="start_date" id="start_date"
												class="form-control form-date-limit" placeholder="${p.startTime}" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly" value="${p.startTime}">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>结束日期</th>
									<td class="required">
										<input type="text" name="end_date" id="end_date"
												class="form-control form-date-limit" placeholder="${p.endTime}" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly" value="${p.endTime}">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>项目内容</th>
									<td class="required">
										<input type="hidden" name="project_content">
										<textarea id="project_content" name="details" placeholder="" style="width:100%;">${p.projectContent}</textarea>
										<div id="project_content" value=""></div>
									</td>
								</tr>
								<tr>
									<th>备注</th>
									<td class="required">
										<input type="hidden" name="remark">
										<textarea id="content" name="details" placeholder="" style="width:100%;">${p.remark}</textarea>
										<div id="remark" value=""></div>
										<input type="hidden" name="id" value="${p.id}"/>
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
								<a href="team/project/toAdd" class="btn">继续创建项目</a> <a
									href="team/need/toaddproject?project_id=${p.id}" class="btn">提需求</a> <a
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
var editor = new UE.ui.Editor();
editor.render("content");

UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
UE.Editor.prototype.getActionUrl = function(action){  
	if(action == 'uploadimage' || action == 'uploadscrawl'){  
		return '<%=basePath%>ueditor/upload';  
	}else{  
		return this._bkGetActionUrl.call(this, action);  
	}  
};
UE.getEditor('project_content');
UE.getEditor('content');

$("#submit").click(function(){
	$("input[name='project_content']").val(UE.getEditor('project_content').getContent());
	$("input[name='remark']").val(UE.getEditor('content').getContent());
	var form = new FormData(document.getElementById("createForm"));
	$.ajaxSettings.async = false;
	$.ajax({
         url:"team/project/edit?r=" + Math.random(),
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
	$.ajaxSettings.async = true;
});
</script>