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
			<c:if test="${entity.id==null}">创建测试单</c:if>
			<c:if test="${entity.id > 0}">修改测试单</c:if>
		</title>
    	<%@ include file="/WEB-INF/view/comm/cssjs.jsp" %>
	</head>
	<body style="overflow-y:scroll">
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
								<c:if test="${applyType == 1}">
									<span class="label label-id">${task.id}</span>
									<a href="team/task/detail?id=${task.id}">${task.taskName}</a>
								</c:if>
								<c:if test="${applyType == 2}">
									<span class="label label-id">${n.id}</span>
									<a href="team/need/detail?id=${n.id}">${n.needName}</a>
								</c:if>
								<c:if test="${applyType == 3}">
									<span class="label label-id">${p.id}</span>
									<a href="team/project/detail?id=${p.id}">${p.projectName}</a>
								</c:if>
								<c:if test="${applyType == 4}">
									<span class="label label-id">${product.id}</span>
									<a href="team/product/detail?id=${product.id}">${product.productName}</a>
								</c:if>
								&nbsp;<i class="icon-angle-right"></i>&nbsp;
								<c:if test="${entity.id==null}">创建测试单</c:if>
								<c:if test="${entity.id > 0}">修改测试单</c:if>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<input type="hidden" name="applyType" value="${applyType}"/>
								<c:if test="${applyType == 1}">
									<tr>
										<th>任务名称</th>
										<td class="required" style="width:70%">
											<select data-placeholder="请选择任务名称" class="form-control chosen chosen-select" name="task_id" id="task_id">
												<option value=""></option>
												<c:forEach items="${tasks}" var="task" varStatus="sta">
													<option value="${task.id}" ${task.id==task_id?'selected="selected"':''}>${task.task_name}（任务编号:${task.id}）</option>
												</c:forEach>
											</select>
											<input type="hidden" name="id" value="${entity.id}"/>
										</td>
										<td></td>
									</tr>
								</c:if>
								<c:if test="${applyType != 1}">
									<tr>
										<th>测试名称</th>
										<td class="required" style="width:70%">
											<input type="text" name="test_name" id="test_name" value="${entity.testName }" class="form-control input-product-title" autocomplete="off"/>
											<input type="hidden" name="need_id" value="${n.id}"/>
											<input type="hidden" name="project_id" value="${p.id}"/>
											<input type="hidden" name="product_id" value="${product.id}"/>
											<input type="hidden" name="id" value="${entity.id}"/>
										</td>
										<td></td>
									</tr>
								</c:if>
								<c:if test="${applyType == 2}">
								<tr>
									<th>模块详情</th>
									<td style="width:70%">
										<ul class="tree tree-lines" data-ride="tree">
											<li class="has-list open in">&nbsp;<a target="_blank" href="team/need/detail?id=${n.id}">${n.needName }【模块】</a>
												<ul>
												<c:forEach items="${subNeed}" var="subNeed" varStatus="sta">
													<li class="has-list open in"><a target="_blank" href="team/need/detail?id=${subNeed.id}">&nbsp;${subNeed.need_name }【子模块】</a>
														<ul>
														<c:forEach items="${subNeedTask}" var="task" varStatus="sta">
														<c:if test="${subNeed.id == task.need_id }">
															<li><a target="_blank" href="team/task/detail?id=${task.id}">&nbsp;${task.task_name}【任务】</a>
																<ul>
																	<li><a href="#">&nbsp;界面原型图</a>
																		<ul>
																			<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																				<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																			</c:forEach>
																		</ul>
																	</li>
																	<li><a href="#">&nbsp;流程图</a>
																		<ul>
																			<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																				<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																			</c:forEach>
																		</ul>
																	</li>
																	<li><a href="#">&nbsp;测试用例</a>
																		<ul>
																		<c:forEach items="${testCase}" var="case" varStatus="sta">
																		<c:if test="${task.id == case.task_id }">
																			<li><a href="#">&nbsp;${case.case_name}
【${case.case_type==1?'功能测试':case.case_type==2?'性能测试':case.case_type==3?'配置相关':case.case_type==4?'安装部署':case.case_type==5?'安全相关':case.case_type==6?'接口测试':'其他'}】</a>
																				<ul>
																					<table>
																						<tr>
																							<td style="border:1px solid #cbd0db" colspan="3">前提条件：${case.precondition}</td>
																						</tr>
																						<tr>
																							<td style="border:1px solid #cbd0db">编号</td>
																							<td style="border:1px solid #cbd0db">步骤</td>
																							<td style="border:1px solid #cbd0db">预期</td>
																						</tr>
																						<c:set var="index" value="1"/>
																						<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																						<c:if test="${case.id == step.case_id }">
																						<tr>
																							<td style="border:1px solid #cbd0db">${index}</td>
																							<td style="border:1px solid #cbd0db">${step.step }</td>
																							<td style="border:1px solid #cbd0db">${step.expect }</td>
																						</tr>
																						<c:set var="index" value="${index+1}"/>
																						</c:if>
																						</c:forEach>
																					</table>
																				</ul>
																			</li>
																		</c:if>
																		</c:forEach>
																		</ul>
																	</li>
																</ul>
															</li>
														</c:if>
														</c:forEach>
														</ul>
													</li>
												</c:forEach>
												<c:forEach items="${needTask}" var="task" varStatus="sta">
													<li><a target="_blank" href="team/task/detail?id=${task.id}">&nbsp;${task.task_name}【任务】</a>
														<ul>
															<li><a href="#">&nbsp;界面原型图</a>
																<ul>
																	<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																		<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																	</c:forEach>
																</ul>
															</li>
															<li><a href="#">&nbsp;流程图</a>
																<ul>
																	<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																		<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																	</c:forEach>
																</ul>
															</li>
															<li><a href="#">&nbsp;测试用例</a>
																<ul>
																<c:forEach items="${testCase}" var="case" varStatus="sta">
																<c:if test="${task.id == case.task_id }">
																	<li><a href="#">&nbsp;${case.case_name}
【${case.case_type==1?'功能测试':case.case_type==2?'性能测试':case.case_type==3?'配置相关':case.case_type==4?'安装部署':case.case_type==5?'安全相关':case.case_type==6?'接口测试':'其他'}】</a>
																		<ul>
																			<table>
																				<tr>
																					<td style="border:1px solid #cbd0db" colspan="3">前提条件：${case.precondition}</td>
																				</tr>
																				<tr>
																					<td style="border:1px solid #cbd0db">编号</td>
																					<td style="border:1px solid #cbd0db">步骤</td>
																					<td style="border:1px solid #cbd0db">预期</td>
																				</tr>
																				<c:set var="index" value="1"/>
																				<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																				<c:if test="${case.id == step.case_id }">
																				<tr>
																					<td style="border:1px solid #cbd0db">${index}</td>
																					<td style="border:1px solid #cbd0db">${step.step }</td>
																					<td style="border:1px solid #cbd0db">${step.expect }</td>
																				</tr>
																				<c:set var="index" value="${index+1}"/>
																				</c:if>
																				</c:forEach>
																			</table>
																		</ul>
																	</li>
																</c:if>
																</c:forEach>
																</ul>
															</li>
														</ul>
													</li>
												</c:forEach>
												</ul>
											</li>
										</ul>
									</td>
									<td></td>
								</tr>
								</c:if>
								<c:if test="${applyType == 3 || applyType == 4}">
								<tr>
									<th>模块详情</th>
									<td>
										<ul class="tree tree-lines" data-ride="tree">
											<c:forEach items="${need}" var="need" varStatus="sta">
												<li class="has-list open in">&nbsp;<a target="_blank" href="team/need/detail?id=${n.id}">${need.need_name }【模块】</a>
													<ul>
													<c:forEach items="${subNeed}" var="subNeed" varStatus="sta">
													<c:if test="${need.id == subNeed.parent_id }">
														<li class="has-list open in"><a target="_blank" href="team/need/detail?id=${subNeed.id}">&nbsp;${subNeed.need_name }【子模块】</a>
															<ul>
															<c:forEach items="${subNeedTask}" var="task" varStatus="sta">
															<c:if test="${subNeed.id == task.need_id }">
																<li><a target="_blank" href="team/task/detail?id=${task.id}">&nbsp;${task.task_name}【任务】</a>
																	<ul>
																		<li><a href="#">&nbsp;界面原型图</a>
																			<ul>
																				<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																					<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																				</c:forEach>
																			</ul>
																		</li>
																		<li><a href="#">&nbsp;流程图</a>
																			<ul>
																				<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																					<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																				</c:forEach>
																			</ul>
																		</li>
																		<li><a href="#">&nbsp;测试用例</a>
																			<ul>
																			<c:forEach items="${testCase}" var="case" varStatus="sta">
																			<c:if test="${task.id == case.task_id }">
																				<li><a href="#">&nbsp;${case.case_name}
【${case.case_type==1?'功能测试':case.case_type==2?'性能测试':case.case_type==3?'配置相关':case.case_type==4?'安装部署':case.case_type==5?'安全相关':case.case_type==6?'接口测试':'其他'}】</a>
																					<ul>
																						<table>
																							<tr>
																								<td style="border:1px solid #cbd0db" colspan="3">前提条件：${case.precondition}</td>
																							</tr>
																							<tr>
																								<td style="border:1px solid #cbd0db">编号</td>
																								<td style="border:1px solid #cbd0db">步骤</td>
																								<td style="border:1px solid #cbd0db">预期</td>
																							</tr>
																							<c:set var="index" value="1"/>
																							<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																							<c:if test="${case.id == step.case_id }">
																							<tr>
																								<td style="border:1px solid #cbd0db">${index}</td>
																								<td style="border:1px solid #cbd0db">${step.step }</td>
																								<td style="border:1px solid #cbd0db">${step.expect }</td>
																							</tr>
																							<c:set var="index" value="${index+1}"/>
																							</c:if>
																							</c:forEach>
																						</table>
																					</ul>
																				</li>
																			</c:if>
																			</c:forEach>
																			</ul>
																		</li>
																	</ul>
																</li>
															</c:if>
															</c:forEach>
															</ul>
														</li>
													</c:if>
													</c:forEach>
													<c:forEach items="${needTask}" var="task" varStatus="sta">
													<c:if test="${need.id == task.need_id }">
														<li><a target="_blank" href="team/task/detail?id=${task.id}">&nbsp;${task.task_name}【任务】</a>
															<ul>
																<li><a href="#">&nbsp;界面原型图</a>
																	<ul>
																		<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																			<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																		</c:forEach>
																	</ul>
																</li>
																<li><a href="#">&nbsp;流程图</a>
																	<ul>
																		<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																			<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																		</c:forEach>
																	</ul>
																</li>
																<li><a href="#">&nbsp;测试用例</a>
																	<ul>
																	<c:forEach items="${testCase}" var="case" varStatus="sta">
																	<c:if test="${task.id == case.task_id }">
																		<li><a href="#">&nbsp;${case.case_name}
	【${case.case_type==1?'功能测试':case.case_type==2?'性能测试':case.case_type==3?'配置相关':case.case_type==4?'安装部署':case.case_type==5?'安全相关':case.case_type==6?'接口测试':'其他'}】</a>
																			<ul>
																				<table>
																					<tr>
																						<td style="border:1px solid #cbd0db" colspan="3">前提条件：${case.precondition}</td>
																					</tr>
																					<tr>
																						<td style="border:1px solid #cbd0db">编号</td>
																						<td style="border:1px solid #cbd0db">步骤</td>
																						<td style="border:1px solid #cbd0db">预期</td>
																					</tr>
																					<c:set var="index" value="1"/>
																					<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																					<c:if test="${case.id == step.case_id }">
																					<tr>
																						<td style="border:1px solid #cbd0db">${index}</td>
																						<td style="border:1px solid #cbd0db">${step.step }</td>
																						<td style="border:1px solid #cbd0db">${step.expect }</td>
																					</tr>
																					<c:set var="index" value="${index+1}"/>
																					</c:if>
																					</c:forEach>
																				</table>
																			</ul>
																		</li>
																	</c:if>
																	</c:forEach>
																	</ul>
																</li>
															</ul>
														</li>
													</c:if>
													</c:forEach>
													</ul>
												</li>
											</c:forEach>
										</ul>
									</td>
									<td></td>
								</tr>
								</c:if>
								<tr>
									<th>测试内容</th>
									<td class="required">
										<input type="hidden" name="test_content">
										<textarea id="test_content" name="details" placeholder="" style="width:100%;">${entity.testContent}</textarea>
										<div id="test_content" value=""></div>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>要执行的SQL</th>
									<td>
										<input type="hidden" name="execute_sql">
										<textarea id="execute_sql" name="details" placeholder="" style="width:100%;">${entity.executeSql}</textarea>
										<div id="execute_sql" value=""></div>
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
								<a href="test/apply/index" class="btn">返回测试单列表</a>
								<c:if test="${entity.id==null}"><a href="test/apply/toAdd" class="btn">继续建测试单</a></c:if>
								<%-- <c:if test="${entity.id > 0}"><a href="test/apply/toAdd?id=${entity.id}" class="btn">继续建测试单</a></c:if> --%>
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
editor.render("content");

UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
UE.Editor.prototype.getActionUrl = function(action){  
	if(action == 'uploadimage' || action == 'uploadscrawl'){  
		return '<%=basePath%>ueditor/upload';  
	}else{  
		return this._bkGetActionUrl.call(this, action);  
	}  
};  
UE.getEditor('test_content');
UE.getEditor('execute_sql');
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$("input[name='test_content']").val(UE.getEditor('test_content').getContent());
	$("input[name='execute_sql']").val(UE.getEditor('execute_sql').getContent());
	$.ajax({type:"POST",url:"test/apply/addOrUpdate?r=" + Math.random(),data:$("form").serialize(),
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
</html>