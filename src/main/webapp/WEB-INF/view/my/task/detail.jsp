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
		<title>任务：${taskM.task_name}</title>
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
							<span class="label label-id">${taskM.id}</span> <span class="text">${taskM.task_name}</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">任务描述</div>
								<div class="detail-content article-content">
									<c:if test="${taskM.remark != null && taskM.remark != ''}">
										${taskM.remark}
									</c:if>
									<c:if test="${taskM.remark == null || taskM.remark == ''}">
										<div class="text-center text-muted">暂无</div>
									</c:if>
								</div>
							</div>
							<c:if test="${taskM.task_type == 2 && taskM.execute_sql != null && taskM.execute_sql != ''}">
								<div class="detail">
									<div class="detail-title">要执行的sql</div>
									<div class="detail-content article-content">
										${taskM.execute_sql}
									</div>
								</div>
							</c:if>
							<c:if test="${taskM.need_id != null && taskM.need_id != ''}">
								<div class="detail">
									<div class="detail-title">模块描述</div>
									<div class="detail-content article-content">
										<c:if test="${taskM.need_remark != null && taskM.need_remark != ''}">
											${taskM.need_remark}
										</c:if>
										<c:if test="${taskM.need_remark == null || taskM.need_remark == ''}">
											<div class="text-center text-muted">暂无</div>
										</c:if>
									</div>
								</div>
								<div class="detail">
									<div class="detail-title">验收标准</div>
									<div class="detail-content article-content">
										<c:if test="${taskM.check_remark != null && taskM.check_remark != ''}">
											${taskM.check_remark}
										</c:if>
										<c:if test="${taskM.check_remark == null || taskM.check_remark == ''}">
											<div class="text-center text-muted">暂无</div>
										</c:if>
									</div>
								</div>
							</c:if>
							<c:if test="${taskM.task_type!=2 }">
								<div class="detail">
									<div class="detail-title">原型图和流程图&nbsp;&nbsp;
	                                <a href="my/task/toaddPicture?id=${taskM.id }">添加</a>&nbsp;&nbsp;
									<a href="my/task/todelPicture?id=${taskM.id }">删除</a>
									</div>  
									<div class="detail-content article-content">
									            原型图&nbsp;&nbsp;  
									    <c:if test="${taskM.interface_img !=null }">
										<c:forEach items="${fn:split(taskM.interface_img, ',')}" var="flow" varStatus="sta">
											<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="【原型图】">&nbsp;&nbsp;
										</c:forEach> 
										</c:if>
										<c:if test="${taskM.interface_img ==null }">
										无图片
										</c:if>
									</div>
									<div class="detail-content article-content">
									            流程图&nbsp;&nbsp;
									    <c:if test="${taskM.flow_img != null}">
										<c:forEach items="${fn:split(taskM.flow_img, ',')}" var="flow" varStatus="sta">
											<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="【流程图】">&nbsp;&nbsp;
										</c:forEach>
										</c:if>
										<c:if test="${taskM.flow_img == null}">
										无图片
										</c:if>
									</div>
								</div>
							</c:if>
							<c:if test="${taskM.task_type==1 && testCase != null}">
								<div class="detail">
									<div class="detail-title">测试用例</div>  
									<div class="detail-content article-content">
										<ul class="tree tree-lines" data-ride="tree">
											<c:forEach items="${testCase}" var="casess" varStatus="sta">
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
											</c:forEach>
										</ul>
									</div>
								</div>
							</c:if>
							<c:if test="${taskM.task_type==1 && codeReport != null}">
								<div class="detail">
									<div class="detail-title">代码审查</div>  
									<div class="detail-content article-content">
										<table>
											<tr>
												<th style="border:1px solid #cbd0db">类型</th>
												<th style="border:1px solid #cbd0db">界面/流程名称</th>
												<th style="border:1px solid #cbd0db">入口点/对应界面入口点</th>
												<th style="border:1px solid #cbd0db">在线url/函数入口点</th>
												<th style="border:1px solid #cbd0db">源文件/文件名</th>
											</tr>
											<c:forEach items="${codeReport}" var="code" varStatus="sta">
												<tr>
													<td style="border:1px solid #cbd0db">${code.report_type == 0 ? '界面' : '流程'}</td>
													<td style="border:1px solid #cbd0db">${code.report_interface }</td>
													<td style="border:1px solid #cbd0db">${code.entry_point }</td>
													<td style="border:1px solid #cbd0db">${code.online_url }</td>
													<td style="border:1px solid #cbd0db">${code.source_file }</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${taskM.task_type==2 && taskM.parent_id==0}">
								<div class="detail">
									<div class="detail-title">模块详情
										<c:if test="${taskM.state == 2}">
											<a href="javascript:void(0)" onclick="adoptAll(${taskM.id})" class="btn btn-secondary" style="text-shadow:0 -1px 0 rgba(0, 0, 0, 0);">一键通过</a>
										</c:if>
									</div>
									<div class="detail-content article-content">
										<c:if test="${apply.applyType == 2}">
											<ul class="tree tree-lines" data-ride="tree">
												<li class="has-list open in">&nbsp;<a target="_blank" href="my/need/detail?id=${n.id}">${n.needName }【模块】</a>
													<ul>
													<c:forEach items="${subNeed}" var="subNeed" varStatus="sta">
														<li class="has-list open in"><a target="_blank" href="my/need/detail?id=${subNeed.id}">&nbsp;${subNeed.need_name }【子模块】</a>
															<ul>
															<c:forEach items="${subNeedTask}" var="task" varStatus="sta">
															<c:if test="${subNeed.id == task.need_id }">
																<li><a target="_blank" href="my/task/detail?id=${task.id}">&nbsp;${task.task_name}【任务】</a>
																	<c:if test="${task.test_state==3}">
																		<span class="label label-info">测试中</span>
																	</c:if>
																	<c:if test="${task.test_state==4}">
																		<span class="label label-success">已测试</span>
																	</c:if>
																	<c:if test="${task.test_state==5}">
																		<span class="label label-warning">已驳回</span>
																	</c:if>
																	<c:if test="${taskM.state == 2}">
																		<a href="javascript:void(0)" onclick="adopt(${task.id})" style="padding-left:12px">通过</a>
																		<a href="javascript:void(0)" onclick="reject(${task.id})" style="padding-left:12px">驳回</a>
																	</c:if>
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
														<li><a target="_blank" href="my/task/detail?id=${task.id}">&nbsp;${task.task_name}【任务】</a>
															<c:if test="${task.test_state==3}">
																<span class="label label-info">测试中</span>
															</c:if>
															<c:if test="${task.test_state==4}">
																<span class="label label-success">已测试</span>
															</c:if>
															<c:if test="${task.test_state==5}">
																<span class="label label-warning">已驳回</span>
															</c:if>
															<c:if test="${taskM.state == 2}">
																<a href="javascript:void(0)" onclick="adopt(${task.id})" style="padding-left:12px">通过</a>
																<a href="javascript:void(0)" onclick="reject(${task.id})" style="padding-left:12px">驳回</a>
															</c:if>
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
													<li class="has-list open in">&nbsp;<a target="_blank" href="my/need/detail?id=${n.id}">${need.need_name }【模块】</a>
														<ul>
														<c:forEach items="${subNeed}" var="subNeed" varStatus="sta">
														<c:if test="${need.id == subNeed.parent_id }">
															<li class="has-list open in"><a target="_blank" href="my/need/detail?id=${subNeed.id}">&nbsp;${subNeed.need_name }【子模块】</a>
																<ul>
																<c:forEach items="${subNeedTask}" var="task" varStatus="sta">
																<c:if test="${subNeed.id == task.need_id }">
																	<li><a target="_blank" href="my/task/detail?id=${task.id}">&nbsp;${task.task_name}【任务】</a>
																		<c:if test="${task.test_state==3}">
																			<span class="label label-info">测试中</span>
																		</c:if>
																		<c:if test="${task.test_state==4}">
																			<span class="label label-success">已测试</span>
																		</c:if>
																		<c:if test="${task.test_state==5}">
																			<span class="label label-warning">已驳回</span>
																		</c:if>
																		<c:if test="${taskM.state == 2}">
																			<a href="javascript:void(0)" onclick="adopt(${task.id})" style="padding-left:12px">通过</a>
																			<a href="javascript:void(0)" onclick="reject(${task.id})" style="padding-left:12px">驳回</a>
																		</c:if>
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
																				<c:forEach items="${testCase}" var="case1" varStatus="sta">
																				<c:if test="${task.id == case1.task_id }">
																					<li><a href="#">&nbsp;${case1.case_name}
	【${case1.case_type==1?'功能测试':case1.case_type==2?'性能测试':case1.case_type==3?'配置相关':case1.case_type==4?'安装部署':case1.case_type==5?'安全相关':case1.case_type==6?'接口测试':'其他'}】</a>
																						<ul>
																							<table>
																								<tr>
																									<td style="border:1px solid #cbd0db" colspan="3">前提条件：${case1.precondition}</td>
																								</tr>
																								<tr>
																									<td style="border:1px solid #cbd0db">编号</td>
																									<td style="border:1px solid #cbd0db">步骤</td>
																									<td style="border:1px solid #cbd0db">预期</td>
																								</tr>
																								<c:set var="index" value="1"/>
																								<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																								<c:if test="${case1.id == step.case_id }">
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
															<li><a target="_blank" href="my/task/detail?id=${task.id}">&nbsp;${task.task_name}【任务】</a>
																<c:if test="${task.test_state==3}">
																	<span class="label label-info">测试中</span>
																</c:if>
																<c:if test="${task.test_state==4}">
																	<span class="label label-success">已测试</span>
																</c:if>
																<c:if test="${task.test_state==5}">
																	<span class="label label-warning">已驳回</span>
																</c:if>
																<c:if test="${taskM.state == 2}">
																	<a href="javascript:void(0)" onclick="adopt(${task.id})" style="padding-left:12px">通过</a>
																	<a href="javascript:void(0)" onclick="reject(${task.id})" style="padding-left:12px">驳回</a>
																</c:if>
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
																		<c:forEach items="${testCase}" var="case1" varStatus="sta">
																		<c:if test="${task.id == case1.task_id }">
																			<li><a href="#">&nbsp;${case1.case_name}
		【${case1.case_type==1?'功能测试':case1.case_type==2?'性能测试':case1.case_type==3?'配置相关':case1.case_type==4?'安装部署':case1.case_type==5?'安全相关':case1.case_type==6?'接口测试':'其他'}】</a>
																				<ul>
																					<table>
																						<tr>
																							<td style="border:1px solid #cbd0db" colspan="3">前提条件：${case1.precondition}</td>
																						</tr>
																						<tr>
																							<td style="border:1px solid #cbd0db">编号</td>
																							<td style="border:1px solid #cbd0db">步骤</td>
																							<td style="border:1px solid #cbd0db">预期</td>
																						</tr>
																						<c:set var="index" value="1"/>
																						<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																						<c:if test="${case1.id == step.case_id }">
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
							<c:if test="${subTask != null}">
								<div class="detail">
									<div class="detail-title">子任务</div>
									<div class="detail-content article-content">
										<table class="table table-hover table-fixed">
											<thead>
												<tr class="text-center">
													<th class="w-80px">ID</th>
													<th class="w-300px">任务名称</th>
													<th>优先级</th>
													<th>结束时间</th>
													<th>指派给</th>
													<th>任务类型</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${subTask}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="my/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name }">${item.task_name }</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>${item.task_type==1?'开发':item.task_type==2?'测试':item.task_type==3?'设计':item.task_type==4?'前端':item.task_type==5?'维护':item.task_type==6?'模块':item.task_type==7?'研究':item.task_type==8?'讨论':item.task_type==9?'运维':item.task_type==10?'事务':'其他'}</td>
													<td>${item.state == 1 ? '待接收' : item.state == 2 ? '进行中' : item.state == 3 ? '审核中' : item.state == 4 ? '已完成' : item.state == 5 ? '已暂停' : item.state == 6 ? '已取消' : item.state == 7 ? '已关闭' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${needTask1 != null}">
								<div class="detail">
									<div class="detail-title">同模块任务</div>
									<div class="detail-content article-content">
										<table class="table table-hover table-fixed">
											<thead>
												<tr class="text-center">
													<th class="w-80px">ID</th>
													<th class="w-300px">任务名称</th>
													<th>优先级</th>
													<th>结束时间</th>
													<th>指派给</th>
													<th>任务类型</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${needTask1}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="my/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name }">${item.task_name }</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>${item.task_type==1?'开发':item.task_type==2?'测试':item.task_type==3?'设计':item.task_type==4?'前端':item.task_type==5?'维护':item.task_type==6?'模块':item.task_type==7?'研究':item.task_type==8?'讨论':item.task_type==9?'运维':item.task_type==10?'事务':'其他'}</td>
													<td>${item.state == 1 ? '待接收' : item.state == 2 ? '进行中' : item.state == 3 ? '审核中' : item.state == 4 ? '已完成' : item.state == 5 ? '已暂停' : item.state == 6 ? '已取消' : item.state == 7 ? '已关闭' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${linkTask != null}">
								<div class="detail">
									<div class="detail-title">关联任务</div>
									<div class="detail-content article-content">
										<table class="table table-hover table-fixed">
											<thead>
												<tr class="text-center">
													<th class="w-80px">ID</th>
													<th class="w-300px">任务名称</th>
													<th>优先级</th>
													<th>结束时间</th>
													<th>指派给</th>
													<th>任务类型</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${linkTask}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="my/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name }">${item.task_name }</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>${item.task_type==1?'开发':item.task_type==2?'测试':item.task_type==3?'设计':item.task_type==4?'前端':item.task_type==5?'维护':item.task_type==6?'模块':item.task_type==7?'研究':item.task_type==8?'讨论':item.task_type==9?'运维':item.task_type==10?'事务':'其他'}</td>
													<td>${item.state == 1 ? '待接收' : item.state == 2 ? '进行中' : item.state == 3 ? '审核中' : item.state == 4 ? '已完成' : item.state == 5 ? '已暂停' : item.state == 6 ? '已取消' : item.state == 7 ? '已关闭' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${testTask != null}">
								<div class="detail">
									<div class="detail-title">测试任务</div>
									<div class="detail-content article-content">
										<table class="table table-hover table-fixed">
											<thead>
												<tr class="text-center">
													<th class="w-80px">ID</th>
													<th class="w-300px">任务名称</th>
													<th>优先级</th>
													<th>结束时间</th>
													<th>指派给</th>
													<th>任务类型</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${testTask}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="my/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name }">${item.task_name }</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>${item.task_type==1?'开发':item.task_type==2?'测试':item.task_type==3?'设计':item.task_type==4?'前端':item.task_type==5?'维护':item.task_type==6?'模块':item.task_type==7?'研究':item.task_type==8?'讨论':item.task_type==9?'运维':item.task_type==10?'事务':'其他'}</td>
													<td>${item.state == 1 ? '待接收' : item.state == 2 ? '进行中' : item.state == 3 ? '审核中' : item.state == 4 ? '已完成' : item.state == 5 ? '已暂停' : item.state == 6 ? '已取消' : item.state == 7 ? '已关闭' : '未知'}</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${logList != null}">
	                        <div class="detail histories" id="actionbox" data-textdiff="文本格式" data-original="原始格式">
	                            <div class="detail-title">
									历史记录
	                                <button type="button" class="btn btn-mini btn-icon btn-expand-all" title="切换显示">
	                                    <i class="icon icon-plus icon-sm"></i>
	                                </button>
	                            </div>
	                            <div class="detail-content">
	                                <ol class="histories-list">
										<c:forEach items="${logList}" var="log" varStatus="sta">
											<li value="${sta.index+1}" class="">
												<fmt:formatDate value="${log.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/>,
												由 <strong>${log.member_name}</strong> ${log.method}。
												<c:if test="${log.history != null}">
													<button type="button" class="btn btn-mini switch-btn btn-icon btn-expand" title="切换显示"><i class="change-show icon icon-plus icon-sm"></i></button>
													<div class="history-changes" id="changeBox3">
														<c:forEach items="${log.history}" var="his" varStatus="sta">
															<c:if test="${his.diff == 0}">
																修改了 <strong><i>${his.field_desc}</i></strong>，旧值为 "${his.old_data}"，新值为 "${his.new_data}"。<br>
															</c:if>
															<c:if test="${his.diff == 1}">
																修改了 <strong><i>${his.field_desc}</i></strong>，区别为：
																<blockquote class="textdiff">
																	- <del>${his.old_data}</del><br/>+ <ins>${his.new_data}</ins>
																</blockquote>
															</c:if>
														</c:forEach>
													</div>
												</c:if>
												<c:if test="${log.comment != null && log.comment != ''}">
													<div class="article-content comment">${log.comment}</div>
												</c:if>
											</li>
										</c:forEach>
	                                </ol>
	                            </div>
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
											<c:if test="${taskM.need_id != null && taskM.need_id != ''}">
												<tr class="nofixed">
													<th>所属模块</th>
													<td title="${taskM.need_name}"><a href="my/need/detail?id=${taskM.need_id}" target="_blank">${taskM.need_name}</a></td>
												</tr>
											</c:if>
											<tr>
												<th>指派给</th>
												<td>${taskM.assigned_name }</td>
											</tr>
											<tr>
												<th>任务类型</th>
												<td>
													${taskM.task_type==1?'开发':taskM.task_type==2?'测试':taskM.task_type==3?'设计':taskM.task_type==4?'前端':taskM.task_type==5?'维护':taskM.task_type==6?'模块':taskM.task_type==7?'研究':taskM.task_type==8?'讨论':taskM.task_type==9?'运维':taskM.task_type==10?'事务':'其他'}
												</td>
											</tr>
											<tr>
												<th>当前状态</th>
												<td>
													${taskM.state == 1 ? '待接收' : taskM.state == 2 ? '进行中' : taskM.state == 3 ? '审核中' : taskM.state == 4 ? '已完成' : taskM.state == 5 ? '已暂停' : taskM.state == 6 ? '已取消' : taskM.state == 7 ? '已关闭' : '未知'}
												</td>
											</tr>
											<tr>
												<th>优先级</th>
												<td>
													<c:if test="${taskM.level=='1'}">紧急重要</c:if>
													<c:if test="${taskM.level=='2'}">紧急不重要</c:if>
													<c:if test="${taskM.level=='3'}">不紧急重要</c:if>
													<c:if test="${taskM.level=='4'}">不紧急不重要</c:if>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">任务时间</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>初始开始</th>
												<td><fmt:formatDate value="${taskM.start_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>实际开始</th>
												<td><fmt:formatDate value="${taskM.real_start_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>初始结束</th>
												<td><fmt:formatDate value="${taskM.end_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>计划结束</th>
												<td>
													<fmt:formatDate value="${taskM.plan_end_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
													<c:if test="${taskM.delay==2}">
														<span style="color:red">已延期</span>
														原结束时间：<fmt:formatDate value="${taskM.delayed_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>实际结束</th>
												<td><fmt:formatDate value="${taskM.real_end_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>是否延期</th>
												<td>
													${taskM.delay == 0 ? '未延期' : taskM.delay == 1 ? '<span style="color: red;">已延期</span>' : '未知' }
												</td>
											</tr>
											<tr>
												<th>是否逾期</th>
												<td>
													${taskM.overdue == 0 ? '未逾期' : taskM.overdue == 1 ? '<span style="color: red;">已逾期</span>' : '未知' }
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">任务的一生</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>由谁创建</th>
												<td>${taskM.member_name } 于 <fmt:formatDate value="${taskM.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>由谁完成</th>
												<td>
													<c:if test="${taskM.assigned_name != null && taskM.assigned_name != '' && taskM.state == 4}">
														${taskM.assigned_name } 于 <fmt:formatDate value="${taskM.assigned_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>由谁审核</th>
												<td>
													<c:if test="${taskM.checked_name != null && taskM.checked_name != ''}">
														${taskM.checked_name }
														<c:if test="${taskM.checked_time != null}">
															于 <fmt:formatDate value="${taskM.checked_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
														</c:if>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>审核结果</th>
												<td>${taskM.checked_reason }</td>
											</tr>
											<tr>
												<th>由谁关闭</th>
												<td>
													<c:if test="${taskM.closed_name != null && taskM.closed_name != ''}">
														${taskM.closed_name } 于 <fmt:formatDate value="${taskM.closed_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>关闭原因</th>
												<td>${taskM.closed_reason }</td>
											</tr>
											<tr>
												<th>最后修改</th>
												<td>${taskM.update_time }</td>
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
						<a href="javascript:history.go(-1);" id="back" class="btn btn-link"><i class="icon-goback icon-back"></i> 返回</a>
						<div class="divider"></div>
						<c:if test="${taskM.state == 1 && taskM.resolved == 0}">
						    <c:if test="${taskM.task_type == 2 || taskM.interface_img != null && taskM.interface_img != '' || taskM.flow_img != null && taskM.flow_img != ''}">
								<a href='my/task/toOpen?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-start icon-play'></i> 接收</a>
							</c:if>
						</c:if>
						<c:if test="${taskM.assigned_name == '' || taskM.assigned_name == null}">
							<a href='my/task/toAssign?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-assignTo icon-hand-right'></i> 指派</a>
						</c:if>
						<c:if test="${taskM.state == 1 || taskM.state == 2}">
							<a href='my/task/toClose?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-close icon-off'></i> 关闭</a>
						</c:if>
						<c:if test="${taskM.state == 7}">
							<a href='my/task/toActive?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-activate icon-magic'></i> 激活</a>
						</c:if>
						<c:if test="${taskM.state == 2 && taskM.delay == 0 && taskM.resolved == 0}">
							<a href='my/task/toDelay?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-recordEstimate icon-time'></i> 延期</a>
						</c:if>
						<c:if test="${taskM.delay == 1}">
							<a href="my/task/toDelayCheck?id=${taskM.id}" class="btn btn-link"><i class="icon-story-review icon-glasses"></i> 延期审核</a>
						</c:if>
						<c:if test="${taskM.state == 3}">
							<a href="my/task/toFinishCheck?id=${taskM.id}" class="btn btn-link"><i class="icon-story-review icon-glasses"></i> 完成审核</a>
						</c:if>
						<c:if test="${taskM.state == 2 && taskM.delay != 1 && taskM.resolved == 0}">
							<a href='my/task/toFinish?id=${taskM.id}' class='btn btn-link' ><i class='icon-task-finish icon-checked'></i> 完成</a>
						</c:if>
						<c:if test="${taskM.state < 3 && taskM.delay != 1 && (taskM.parent_id == null || taskM.parent_id == '')}">
							<a href='my/task/toRelevance?id=${taskM.id}' class='btn btn-link ' ><i class='icon icon-sitemap'></i> 关联</a>
						</c:if>
						<c:if test="${taskM.state < 3 && taskM.delay != 1 && (taskM.parent_id == null || taskM.parent_id == '')}">
							<a href='my/task/toBatchAdd?id=${taskM.id}' class='btn btn-link ' ><i class='icon-task-batchCreate icon-branch'></i> 分解</a>
						</c:if>
					</div>
				</div>
				<!--mainActions end-->
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
	function del(id){
		if(confirm("确认删除？")){
			$.ajaxSettings.async = false;
			$.getJSON("my/task/del?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
	function adoptAll(id){
		if(confirm("确认通过所有测试？")){
			$.ajaxSettings.async = false;
			$.getJSON("my/task/adoptAll?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
	function adopt(id){
		if(confirm("确认通过测试？")){
			$.ajaxSettings.async = false;
			$.getJSON("my/task/adopt?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
	function reject(id){
		if(confirm("确认驳回测试？")){
			$.ajaxSettings.async = false;
			$.getJSON("my/task/reject?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
</script>