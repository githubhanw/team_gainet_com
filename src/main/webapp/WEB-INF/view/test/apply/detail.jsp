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
		<title>测试单详情</title>
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
				<div id="mainMenu" class="clearfix">
					<div class="btn-toolbar pull-left">
						<a href="javascript:history.go(-1);" class="btn btn-link"><i class="icon icon-back icon-sm"></i> 返回</a>
						<div class="divider"></div>
						<div class="page-title">
							<span class="label label-id">${entity.id}</span> <span class="text">${task.taskName != null ? task.taskName : entity.testName}</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">测试内容</div>
								<div class="detail-content article-content">${entity.testContent}</div>
							</div>
							<div class="detail">
								<div class="detail-title">要执行的SQL</div>
								<div class="detail-content article-content">${entity.executeSql}</div>
							</div>
							<c:if test="${applyType != 1}">
							<div class="detail">
								<div class="detail-title">模块详情</div>
								<div class="detail-content article-content">
									<c:if test="${applyType == 2}">
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
									<c:if test="${applyType == 3 || applyType == 4}">
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
								</div>
							</div>
							</c:if>
							<c:if test="${entity.state == 4}">
								<div class="detail">
									<div class="detail-title">驳回原因</div>
									<div class="detail-content article-content">${entity.dismissal}</div>
								</div>
							</c:if>
						</div>
					</div>
					<div class="side-col col-4">
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">基本信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr class="nofixed">
												<c:if test="${applyType == 1}">
													<th>所属任务</th>
													<td>
														<a href="team/task/detail?id=${task.id }">${task.taskName}</a>
													</td>
												</c:if>
												<c:if test="${applyType == 2}">
													<th>所属模块</th>
													<td>
														<a href="team/need/detail?id=${n.id}">${n.needName}</a>
													</td>
												</c:if>
												<c:if test="${applyType == 3}">
													<th>所属项目</th>
													<td>
														<a href="team/project/pro_detail?id=${p.id }">${p.projectName}</a>
													</td>
												</c:if>
												<c:if test="${applyType == 4}">
													<th>所属产品</th>
													<td>
														<a href="team/product/pro_detail?id=${product.id }">${product.productName}</a>
													</td>
												</c:if>
											</tr>
											<tr class="nofixed">
												<th>申请人</th>
												<td>${entity.applyName}</td>
											</tr>
											<tr class="nofixed">
												<th>申请时间</th>
												<td><fmt:formatDate value="${entity.applyTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											</tr>
											<tr class="nofixed">
												<th>状态</th>
												<td>
													<c:if test="${entity.state == 1}">
														待测试
													</c:if>
													<c:if test="${entity.state == 2}">
														测试中
													</c:if>
													<c:if test="${entity.state == 3}">
														已测试
													</c:if>
													<c:if test="${entity.state == 4}">
														已驳回
													</c:if>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
					</div>
				</div>
				<!--mainActions start-->
				<div id="mainActions">
					<nav class="container">
					</nav>
					<div class="btn-toolbar">
						<a href="javascript:history.go(-1);" id="back" class="btn" title="返回[快捷键:Alt+↑]">
							<i class="icon-goback icon-back"></i> 返回
						</a>
						<div class="divider"></div>
						<a href="test/apply/toEdit?id=${entity.id}" class="btn btn-link " title="编辑">
							<i class="icon-common-edit icon-edit"></i>
						</a>
					</div>
				</div>
				<!--mainActions end-->
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>