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
		<title>任务完成审核：${t.taskName }</title>
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
								<span class="label label-id">${t.id}</span>
								<a href="team/task/index">${t.taskName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 任务完成审核</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>审核人</th>
									<td style="width:70%">${t.checkedName}</td>
									<input type="hidden" name="id" value="${t.id}"/>
									<td></td>
								</tr>
								<c:if test="${t.taskType==2 && t.parentId==0}">
									<tr>
										<th>模块详情</th>
										<td>
											<c:if test="${apply.applyType == 2}">
												<ul class="tree tree-lines" data-ride="tree">
													<li class="has-list open in">&nbsp;<a target="_blank" href="team/need/detail?id=${n.id}">${n.needName }【模块】</a>
														<ul>
														<c:forEach items="${subNeed}" var="subNeed" varStatus="sta">
															<li class="has-list open in"><a target="_blank" href="team/need/detail?id=${subNeed.id}">&nbsp;${subNeed.need_name }【子模块】</a>
																<ul>
																<c:forEach items="${subNeedTask}" var="task" varStatus="sta">
																<c:if test="${subNeed.id == task.need_id }">
																	<li><a target="_blank" href="team/task/detail?id=${task.id}">&nbsp;${task.task_name}【任务】</a>
																		<c:if test="${task.test_state==3}">
																			<span class="label label-info">测试中</span>
																		</c:if>
																		<c:if test="${task.test_state==4}">
																			<span class="label label-success">已测试</span>
																		</c:if>
																		<c:if test="${task.test_state==5}">
																			<span class="label label-warning">已驳回</span>
																		</c:if>
																		<ul>
																			<li><a href="#">&nbsp;界面原型图</a>
																				<ul>
																					<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																						<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
																										fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
																									  fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
																										fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
																							<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																						</c:if>
																						<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																										fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
																									  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																										fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true }">
																							<a href="${inter}">下载非图片文件</a>
																						</c:if>
																					</c:forEach>
																				</ul>
																			</li>
																			<li><a href="#">&nbsp;流程图</a>
																				<ul>
																					<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																						<c:if test="${fn:contains(flow,'.BMP')==true || fn:contains(flow,'.JPEG')==true || fn:contains(flow,'.GIF')==true || 
																										fn:contains(flow,'.PNG')==true || fn:contains(flow,'.JPG')==true ||
																									  fn:contains(flow,'.bmp')==true || fn:contains(flow,'.jpeg')==true || fn:contains(flow,'.gif')==true || 
																										fn:contains(flow,'.png')==true || fn:contains(flow,'.jpg')==true }">
																							<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																						</c:if>
																						<c:if test="${fn:contains(flow,'.BMP')!=true && fn:contains(flow,'.JPEG')!=true && fn:contains(flow,'.GIF')!=true && 
																										fn:contains(flow,'.PNG')!=true && fn:contains(flow,'.JPG')!=true &&
																									  fn:contains(flow,'.bmp')!=true && fn:contains(flow,'.jpeg')!=true && fn:contains(flow,'.gif')!=true && 
																										fn:contains(flow,'.png')!=true && fn:contains(flow,'.jpg')!=true }">
																							<a href="${flow}">下载非图片文件</a>
																						</c:if>
																					</c:forEach>
																				</ul>
																			</li>
																			<li><a href="#">&nbsp;测试用例</a>
																				<ul>
																				<c:forEach items="${testCase}" var="casess" varStatus="sta">
																				<c:if test="${task.id == casess.task_id }">
																					<li><a href="#">&nbsp;${casess.case_name}
		【${casess.case_type==1?'功能测试':casess.case_type==2?'性能测试':casess.case_type==3?'配置相关':casess.case_type==4?'安装部署':casess.case_type==5?'安全相关':casess.case_type==6?'接口测试':'其他'}】</a>
																						<ul>
																							<table>
																								<tr>
																									<td style="border:1px solid #cbd0db" colspan="3">前提条件：${casess.precondition}</td>
																								</tr>
																								<tr>
																									<td style="border:1px solid #cbd0db">编号</td>
																									<td style="border:1px solid #cbd0db">步骤</td>
																									<td style="border:1px solid #cbd0db">预期</td>
																								</tr>
																								<c:set var="index" value="1"/>
																								<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																								<c:if test="${casess.id == step.case_id }">
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
																<c:if test="${task.test_state==3}">
																	<span class="label label-info">测试中</span>
																</c:if>
																<c:if test="${task.test_state==4}">
																	<span class="label label-success">已测试</span>
																</c:if>
																<c:if test="${task.test_state==5}">
																	<span class="label label-warning">已驳回</span>
																</c:if>
																<ul>
																	<li><a href="#">&nbsp;界面原型图</a>
																		<ul>
																			<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																				<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
																								fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
																							  fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
																								fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
																					<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																				</c:if>
																				<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																								fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
																							  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																								fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true }">
																					<a href="${inter}">下载非图片文件</a>
																				</c:if>
																			</c:forEach>
																		</ul>
																	</li>
																	<li><a href="#">&nbsp;流程图</a>
																		<ul>
																			<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																				<c:if test="${fn:contains(flow,'.BMP')==true || fn:contains(flow,'.JPEG')==true || fn:contains(flow,'.GIF')==true || 
																								fn:contains(flow,'.PNG')==true || fn:contains(flow,'.JPG')==true ||
																							  fn:contains(flow,'.bmp')==true || fn:contains(flow,'.jpeg')==true || fn:contains(flow,'.gif')==true || 
																								fn:contains(flow,'.png')==true || fn:contains(flow,'.jpg')==true }">
																					<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																				</c:if>
																				<c:if test="${fn:contains(flow,'.BMP')!=true && fn:contains(flow,'.JPEG')!=true && fn:contains(flow,'.GIF')!=true && 
																								fn:contains(flow,'.PNG')!=true && fn:contains(flow,'.JPG')!=true &&
																							  fn:contains(flow,'.bmp')!=true && fn:contains(flow,'.jpeg')!=true && fn:contains(flow,'.gif')!=true && 
																								fn:contains(flow,'.png')!=true && fn:contains(flow,'.jpg')!=true }">
																					<a href="${flow}">下载非图片文件</a>
																				</c:if>
																			</c:forEach>
																		</ul>
																	</li>
																	<li><a href="#">&nbsp;测试用例</a>
																		<ul>
																		<c:forEach items="${testCase}" var="casess" varStatus="sta">
																		<c:if test="${task.id == casess.task_id }">
																			<li><a href="#">&nbsp;${casess.case_name}
		【${casess.case_type==1?'功能测试':casess.case_type==2?'性能测试':casess.case_type==3?'配置相关':casess.case_type==4?'安装部署':casess.case_type==5?'安全相关':casess.case_type==6?'接口测试':'其他'}】</a>
																				<ul>
																					<table>
																						<tr>
																							<td style="border:1px solid #cbd0db" colspan="3">前提条件：${casess.precondition}</td>
																						</tr>
																						<tr>
																							<td style="border:1px solid #cbd0db">编号</td>
																							<td style="border:1px solid #cbd0db">步骤</td>
																							<td style="border:1px solid #cbd0db">预期</td>
																						</tr>
																						<c:set var="index" value="1"/>
																						<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																						<c:if test="${casess.id == step.case_id }">
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
											</c:if>
											<c:if test="${apply.applyType == 3 || apply.applyType == 4}">
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
																			<c:if test="${task.test_state==3}">
																				<span class="label label-info">测试中</span>
																			</c:if>
																			<c:if test="${task.test_state==4}">
																				<span class="label label-success">已测试</span>
																			</c:if>
																			<c:if test="${task.test_state==5}">
																				<span class="label label-warning">已驳回</span>
																			</c:if>
																			<ul>
																				<li><a href="#">&nbsp;界面原型图</a>
																					<ul>
																						<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																							<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
																											fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
																										  fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
																											fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
																								<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																							</c:if>
																							<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																											fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
																										  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																											fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true }">
																								<a href="${inter}">下载非图片文件</a>
																							</c:if>
																						</c:forEach>
																					</ul>
																				</li>
																				<li><a href="#">&nbsp;流程图</a>
																					<ul>
																						<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																							<c:if test="${fn:contains(flow,'.BMP')==true || fn:contains(flow,'.JPEG')==true || fn:contains(flow,'.GIF')==true || 
																											fn:contains(flow,'.PNG')==true || fn:contains(flow,'.JPG')==true ||
																										  fn:contains(flow,'.bmp')==true || fn:contains(flow,'.jpeg')==true || fn:contains(flow,'.gif')==true || 
																											fn:contains(flow,'.png')==true || fn:contains(flow,'.jpg')==true }">
																								<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																							</c:if>
																							<c:if test="${fn:contains(flow,'.BMP')!=true && fn:contains(flow,'.JPEG')!=true && fn:contains(flow,'.GIF')!=true && 
																											fn:contains(flow,'.PNG')!=true && fn:contains(flow,'.JPG')!=true &&
																										  fn:contains(flow,'.bmp')!=true && fn:contains(flow,'.jpeg')!=true && fn:contains(flow,'.gif')!=true && 
																											fn:contains(flow,'.png')!=true && fn:contains(flow,'.jpg')!=true }">
																								<a href="${flow}">下载非图片文件</a>
																							</c:if>
																						</c:forEach>
																					</ul>
																				</li>
																				<li><a href="#">&nbsp;测试用例</a>
																					<ul>
																					<c:forEach items="${testCase}" var="casess" varStatus="sta">
																					<c:if test="${task.id == casess.task_id }">
																						<li><a href="#">&nbsp;${casess.case_name}
		【${casess.case_type==1?'功能测试':casess.case_type==2?'性能测试':casess.case_type==3?'配置相关':casess.case_type==4?'安装部署':casess.case_type==5?'安全相关':casess.case_type==6?'接口测试':'其他'}】</a>
																							<ul>
																								<table>
																									<tr>
																										<td style="border:1px solid #cbd0db" colspan="3">前提条件：${casess.precondition}</td>
																									</tr>
																									<tr>
																										<td style="border:1px solid #cbd0db">编号</td>
																										<td style="border:1px solid #cbd0db">步骤</td>
																										<td style="border:1px solid #cbd0db">预期</td>
																									</tr>
																									<c:set var="index" value="1"/>
																									<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																									<c:if test="${casess.id == step.case_id }">
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
																	<c:if test="${task.test_state==3}">
																		<span class="label label-info">测试中</span>
																	</c:if>
																	<c:if test="${task.test_state==4}">
																		<span class="label label-success">已测试</span>
																	</c:if>
																	<c:if test="${task.test_state==5}">
																		<span class="label label-warning">已驳回</span>
																	</c:if>
																	<ul>
																		<li><a href="#">&nbsp;界面原型图</a>
																			<ul>
																				<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																					<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
																									fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
																								  fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
																									fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
																						<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																					</c:if>
																					<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																									fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
																								  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																									fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true }">
																						<a href="${inter}">下载非图片文件</a>
																					</c:if>
																				</c:forEach>
																			</ul>
																		</li>
																		<li><a href="#">&nbsp;流程图</a>
																			<ul>
																				<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																					<c:if test="${fn:contains(flow,'.BMP')==true || fn:contains(flow,'.JPEG')==true || fn:contains(flow,'.GIF')==true || 
																									fn:contains(flow,'.PNG')==true || fn:contains(flow,'.JPG')==true ||
																								  fn:contains(flow,'.bmp')==true || fn:contains(flow,'.jpeg')==true || fn:contains(flow,'.gif')==true || 
																									fn:contains(flow,'.png')==true || fn:contains(flow,'.jpg')==true }">
																						<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																					</c:if>
																					<c:if test="${fn:contains(flow,'.BMP')!=true && fn:contains(flow,'.JPEG')!=true && fn:contains(flow,'.GIF')!=true && 
																									fn:contains(flow,'.PNG')!=true && fn:contains(flow,'.JPG')!=true &&
																								  fn:contains(flow,'.bmp')!=true && fn:contains(flow,'.jpeg')!=true && fn:contains(flow,'.gif')!=true && 
																									fn:contains(flow,'.png')!=true && fn:contains(flow,'.jpg')!=true }">
																						<a href="${flow}">下载非图片文件</a>
																					</c:if>
																				</c:forEach>
																			</ul>
																		</li>
																		<li><a href="#">&nbsp;测试用例</a>
																			<ul>
																			<c:forEach items="${testCase}" var="casess" varStatus="sta">
																			<c:if test="${task.id == casess.task_id }">
																				<li><a href="#">&nbsp;${casess.case_name}
			【${casess.case_type==1?'功能测试':casess.case_type==2?'性能测试':casess.case_type==3?'配置相关':casess.case_type==4?'安装部署':casess.case_type==5?'安全相关':casess.case_type==6?'接口测试':'其他'}】</a>
																					<ul>
																						<table>
																							<tr>
																								<td style="border:1px solid #cbd0db" colspan="3">前提条件：${casess.precondition}</td>
																							</tr>
																							<tr>
																								<td style="border:1px solid #cbd0db">编号</td>
																								<td style="border:1px solid #cbd0db">步骤</td>
																								<td style="border:1px solid #cbd0db">预期</td>
																							</tr>
																							<c:set var="index" value="1"/>
																							<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																							<c:if test="${casess.id == step.case_id }">
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
											</c:if>
										</td>
										<td></td>
									</tr>
								</c:if>
								<tr>
									<th>状态</th>
									<td class="required">
										<label class="radio-inline"><input type="radio" name="stage" value="y" checked="checked" id="passy"> 通过</label>
										<label class="radio-inline"><input type="radio" name="stage" value="n" id="passn"> 不通过</label>
									</td>
								</tr>
								<tr>
									<th>审核备注</th>
									<td>
										<input type="hidden" name="checked_reason">
										<textarea id="checked_reason" name="details" placeholder="" style="width:100%;"></textarea>
										<div id="checked_reason" value=""></div>
									</td>
									<td></td>
								</tr>
								</form>
							</tbody>
						</table>
						<c:if test="${t.taskType == 1}">
							<br>
							<br>
							<br>
							<div class="table-responsive">
								<table class="table has-sort-head" id="taskList"
									data-fixed-left-width="550" data-fixed-right-width="160">
									<thead>
									<tr>
										<td style="font-size:15px;font-weight:bold;text-align:left;">代码审查-界面审核</td>
									</tr>
										<tr>
											<th data-flex="false" data-width="90px" style="width:90px" class="c-id text-center" >界面ID</th>
											<th data-flex="false" data-width="50px" style="width:250px" class="c-pri">入口点</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-pri">在线URL</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-pri">源文件</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-pri">审查</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${codeReport}" var="cr" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${cr.id}</td>
											<td class="text-left">${cr.entry_point}</td>
											<td class="c-name text-center">${cr.online_url}</td>
											<td class="c-name text-center">${cr.source_file}</td>
											<td class="c-name text-center">
												<c:if test="${cr.examination == '0'}">
													<span class="label">未审查</span>
												</c:if>
												<c:if test="${cr.examination == '1'}">
													<span class="label label-danger">未通过</span>
												</c:if>
												<c:if test="${cr.examination == '2'}">
													<span class="label label-success">通过</span>
												</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="javascript:void(0)" onclick="exam('${cr.id}','y')" class="btn btn-sm " type="button" title="审核通过">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="javascript:void(0)" onclick="exam('${cr.id}','n')" class="btn btn-sm " type="button" title="审核不通过">不通过</a>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							
							<div class="table-responsive">
								<table class="table has-sort-head" id="taskList"
									data-fixed-left-width="550" data-fixed-right-width="160">
									<thead>
									<tr>
										<td style="font-size:15px;font-weight:bold;text-align:left;">代码审查-流程审核</td>
									</tr>
										<tr>
											<th data-flex="false" data-width="90px" style="width:90px" class="c-id text-center" >流程ID</th>
											<th data-flex="false" data-width="50px" style="width:250px" class="c-pri">对应界面入口点</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-pri">函数入口点</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-pri">文件名</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-pri">审查</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${codeInterface}" var="ci" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${ci.id}</td>
											<td class="text-left">${ci.entry_point}</td>
											<td class="c-name text-center">${ci.online_url}</td>
											<td class="c-name text-center">${ci.source_file}</td>
											<td class="c-name text-center">
												<c:if test="${ci.examination == '0'}">
													<span class="label">未审查</span>
												</c:if>
												<c:if test="${ci.examination == '1'}">
													<span class="label label-danger">未通过</span>
												</c:if>
												<c:if test="${ci.examination == '2'}">
													<span class="label label-success">通过</span>
												</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="javascript:void(0)" onclick="exam('${ci.id}','y')" class="btn btn-sm " type="button" title="审核通过">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="javascript:void(0)" onclick="exam('${ci.id}','n')" class="btn btn-sm " type="button" title="审核不通过">不通过</a>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:if>
						<div colspan="3" class="text-center form-actions">
							<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">提交</button>
							<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">返回</a>
						</div>
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
								<a href="team/task/index" class="btn">返回任务列表</a>
								<a href="team/task/detail?id=${t.id}" class="btn">任务详情</a>
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
UE.getEditor('checked_reason');

$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$("input[name='checked_reason']").val(UE.getEditor('checked_reason').getContent());
	$.ajax({type:"POST",url:"team/task/finishCheck?r=" + Math.random(),data:$("form").serialize(),
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

function exam(id,isPass){
	var sname = isPass=='y'?"通过":"不通过";
	if(confirm("确认"+sname+"?")){
		$.ajaxSettings.async = false;
		$.getJSON("team/task/exam?id="+id+"&isPass="+isPass+"&r=" + Math.random(), function(data) {
			alert(data.message);
			if(data.code == 0){
				window.location.reload();
			}
		});
		$.ajaxSettings.async = true;
	}
}
</script>