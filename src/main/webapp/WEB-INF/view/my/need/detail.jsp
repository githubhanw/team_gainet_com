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
		<title>模块：${needM.need_name}</title>
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
							<span class="label label-id">${needM.id}</span> <span class="text" title="${needM.need_name}">${needM.need_name}</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">模块描述</div>
								<div class="detail-content article-content">
									<c:if test="${needM.need_remark != null && needM.need_remark != ''}">
										${needM.need_remark}
									</c:if>
									<c:if test="${needM.need_remark == null || needM.need_remark == ''}">
										<div class="text-center text-muted">暂无</div>
									</c:if>
								</div>
							</div>
							<div class="detail">
								<div class="detail-title">验收标准</div>
								<div class="detail-content article-content">
									<c:if test="${needM.check_remark != null && needM.check_remark != ''}">
										${needM.check_remark}
									</c:if>
									<c:if test="${needM.check_remark == null || needM.check_remark == ''}">
										<div class="text-center text-muted">暂无</div>
									</c:if>
								</div>
							</div>
							<c:if test="${needM.state!=0 }">
								<div class="detail">
									<div class="detail-title">原型图和流程图&nbsp;&nbsp;
	                                <a href="my/need/toaddPicture?id=${needM.id }">添加</a>&nbsp;&nbsp;
									<a href="my/need/todelPicture?id=${needM.id }">删除</a>
									</div>  
									<div class="detail-content article-content">
									            原型图&nbsp;&nbsp;  
									    <c:if test="${needM.interface_img !=null }">
										<c:forEach items="${fn:split(needM.interface_img, ',')}" var="inter" varStatus="sta">
											<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
															fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
														  fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
															fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
												<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="【原型图】">&nbsp;&nbsp;
											</c:if>
											<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
															fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
														  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
															fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true && inter!=null && inter!='' }">
												<a href="${inter}">下载非图片文件</a>
											</c:if>
										</c:forEach> 
										</c:if>
										<c:if test="${needM.interface_img ==null }">
										无图片
										</c:if>
									</div>
									<div class="detail-content article-content">
									            流程图&nbsp;&nbsp;
									    <c:if test="${needM.flow_img != null}">
										<c:forEach items="${fn:split(needM.flow_img, ',')}" var="flow" varStatus="sta">
											<c:if test="${fn:contains(flow,'.BMP')==true || fn:contains(flow,'.JPEG')==true || fn:contains(flow,'.GIF')==true || 
															fn:contains(flow,'.PNG')==true || fn:contains(flow,'.JPG')==true ||
														  fn:contains(flow,'.bmp')==true || fn:contains(flow,'.jpeg')==true || fn:contains(flow,'.gif')==true || 
															fn:contains(flow,'.png')==true || fn:contains(flow,'.jpg')==true }">
												<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="【流程图】">&nbsp;&nbsp;
											</c:if>
											<c:if test="${fn:contains(flow,'.BMP')!=true && fn:contains(flow,'.JPEG')!=true && fn:contains(flow,'.GIF')!=true && 
															fn:contains(flow,'.PNG')!=true && fn:contains(flow,'.JPG')!=true &&
														  fn:contains(flow,'.bmp')!=true && fn:contains(flow,'.jpeg')!=true && fn:contains(flow,'.gif')!=true && 
															fn:contains(flow,'.png')!=true && fn:contains(flow,'.jpg')!=true && flow!=null && flow!='' }">
												<a href="${flow}">下载非图片文件</a>
											</c:if>
										</c:forEach>
										</c:if>
										<c:if test="${needM.flow_img == null}">
										无图片
										</c:if>
									</div>
								</div>
							</c:if>
							<c:if test="${subNeed != null}">
								<div class="detail">
									<div class="detail-title">子模块</div>
									<div class="detail-content article-content">
										<table class="table table-hover table-fixed">
											<thead>
												<tr class="text-center">
													<th>ID</th>
													<th class="w-300px">模块名称</th>
													<th>优先级</th>
													<th class="w-100px">结束时间</th>
													<th>指派给</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${subNeed}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="my/need/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.need_name}">${item.need_name}</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>
													${item.state == 0 ? '已删除' : item.state == 1 ? '未开始' : item.state == 2 ? '进行中'
													 : item.state == 3 ? '待验收' : item.state == 4 ? '已验收' : item.state == 5 ? '已关闭' : '未知'}
													</td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${task != null}">
								<div class="detail">
									<div class="detail-title">相关任务</div>
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
												<c:forEach items="${task}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="team/task/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.task_name}">${item.task_name}</a></td>
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
							<c:if test="${linkNeed != null}">
								<div class="detail">
									<div class="detail-title">关联模块</div>
									<div class="detail-content article-content">
										<table class="table table-hover table-fixed">
											<thead>
												<tr class="text-center">
													<th>ID</th>
													<th class="w-300px">模块名称</th>
													<th>优先级</th>
													<th class="w-100px">结束时间</th>
													<th>指派给</th>
													<th>状态</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${linkNeed}" var="item" varStatus="sta">
												<tr class="text-center">
													<td>${item.id}</td>
													<td><a href="my/need/detail?id=${item.id}" data-toggle="tooltip" data-placement="top" title="${item.need_name}">${item.need_name}</a></td>
													<td>
														<c:if test="${item.level=='1'}">紧急重要</c:if>
														<c:if test="${item.level=='2'}">紧急不重要</c:if>
														<c:if test="${item.level=='3'}">不紧急重要</c:if>
														<c:if test="${item.level=='4'}">不紧急不重要</c:if>
													</td>
													<td>${item.end_date}</td>
													<td>${item.assigned_name}</td>
													<td>${item.state == 0 ? '已删除' : item.state == 1 ? '未开始' : item.state == 2 ? '进行中'
													 : item.state == 3 ? '待验收' : item.state == 4 ? '已验收' : item.state == 5 ? '已关闭' : '未知'}</td>
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
											<c:if test="${needM.project_id!='0'}">
											<tr class="nofixed">
												<th>所属项目</th>
												<td>
													<a href="team/project/detail?id=${needM.project_id}" data-toggle="tooltip" data-placement="top" title="${needM.project_name}">${needM.project_name}</a>
												</td>
											</tr>
											</c:if>
											<c:if test="${needM.project_id=='0'}">
											<tr class="nofixed">
												<th>所属产品</th>
												<td>
													<a href="team/product/detail?id=${needM.product_id}" data-toggle="tooltip" data-placement="top" title="${needM.product_name}">${needM.product_name}</a>
												</td>
											</tr>
											</c:if>
											<tr>
												<th>模块来源</th>
												<td>${needM.need_src}</td>
											</tr>
											<tr>
												<th>来源备注</th>
												<td>${needM.leader_name}</td>
											</tr>
											<c:if test="${needM.project_id == '0' || needM.project_id == null}">
											<tr>
												<th>部门经理</th>
												<td>${needM.department_name}</td>
											</tr>
											</c:if>
											<tr>
												<th>需求方</th>
												<td>${needM.member_name}</td>
											</tr>
											<tr>
												<th>优先级</th>
												<td>
													<c:if test="${needM.level=='1'}">紧急重要</c:if>
													<c:if test="${needM.level=='2'}">紧急不重要</c:if>
													<c:if test="${needM.level=='3'}">不紧急重要</c:if>
													<c:if test="${needM.level=='4'}">不紧急不重要</c:if>
												</td>
											</tr>
											<tr>
												<th>当前状态</th>
												<td>
													${needM.state == 0 ? '已删除' : needM.state == 1 ? '未开始' : needM.state == 2 ? '进行中'
													 : needM.state == 3 ? '待验收' : needM.state == 4 ? '已验收' : needM.state == 5 ? '已关闭' : needM.state == 6 ? '待安排' : '未知'}
												</td>
											</tr>
											<tr>
												<th>逾期状态</th>
												<td>
												     <c:if test="${needM.overdue==1}">
													          已逾期
												      </c:if>
												      <c:if test="${needM.overdue==0}">
													         未逾期
												      </c:if>
												</td>
											</tr>
											<%-- <tr>
												<th>当前阶段</th>
												<td>
													${needM.stage == 1 ? '待验收' : needM.stage == 2 ? '验收完成' : needM.stage == 3 ? '验收不通过' : '未知'}
												</td>
											</tr> --%>
											<tr>
												<th>代码开始</th>
												<td>${needM.start_date}</td>
											</tr>
											<tr>
												<th>代码结束</th>
												<td>${needM.cend_date}</td>
											</tr>
											<tr>
												<th>测试结束</th>
												<td>${needM.tend_date}</td>
											</tr>
											<tr>
												<th>上线时间</th>
												<td>${needM.end_date}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">模块的一生</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>由谁创建</th>
												<td>${needM.create_name } 于 <fmt:formatDate value="${needM.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>指派给</th>
												<td>
													<c:if test="${needM.assigned_name != null && needM.assigned_name != ''}">
														${needM.assigned_name } 于 <fmt:formatDate value="${needM.assigned_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>由谁关闭</th>
												<td>
													<c:if test="${needM.closed_name != null && needM.closed_name != ''}">
														${needM.closed_name } 于 <fmt:formatDate value="${needM.closed_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>关闭原因</th>
												<td>${needM.closed_reason }</td>
											</tr>
											<tr>
												<th>由谁验收</th>
												<td>
													<c:if test="${needM.checked_name != null && needM.checked_name != ''}">
														${needM.checked_name } 于 <fmt:formatDate value="${needM.checked_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>最后修改</th>
												<td>${needM.update_time }</td>
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
						<a href="javascript:history.go(-1);" id="back" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="返回"><i class="icon-goback icon-back"></i> 返回</a>
						<c:if test="${needM.state == 1 || needM.state == 2 || needM.state == 3 || needM.state == 4}">
								<a href="my/need/toRelate?id=${needM.id}" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="关联月会议"><i class='icon icon-sitemap'></i>关联月会议</a>
						</c:if>
						<c:if test="${needM.state == 1 || needM.state == 2}">
							<a href="my/need/toChange?id=${needM.id}" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="${needM.full == 0?'完善':'变更'}">
								<i class="icon-story-change icon-fork"></i> ${needM.full == 0?'完善':'变更'}
							</a>
							<a href="my/need/toClose?id=${needM.id}" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="关闭模块"><i class='icon-task-close icon-off'></i> 关闭模块</a>
						</c:if>
						<c:if test="${needM.full == 1}">
						  <c:if test="${needM.state == 1}">
						    <a href="my/need/toOpen?id=${needM.id}" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="接收模块"><i class="icon-task-start icon-play"></i>接收模块</a>
						  </c:if>
						  <c:if test="${needM.state == 2}">
						  <a href="my/need/toSubmitCheck?id=${needM.id}" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="提交验收"><i class="icon-task-finish icon-checked"></i>提交验收</a>
						</c:if>
						<c:if test="${needM.state == 3}">
						<a href="my/need/toCheck?id=${needM.id}" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="验收模块"><i class="icon-story-review icon-glasses"></i> 验收模块</a>
						</c:if>
						</c:if>
						<c:if test="${(needM.parent_id == null || needM.parent_id == 0) && needM.full == 1 && needM.state == 2}">	
								<a href="my/task/toAdd?need_id=${needM.id}" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="批量建任务"><i class="icon icon-plus"></i> 批量建任务</a>
						  </c:if>
						
						<c:if test="${needM.parent_id == null || needM.parent_id == ''}">
							<a href="my/need/toRelevance?id=${needM.id}" class="btn btn-link" data-toggle="tooltip" data-placement="top" title="关联模块"><i class='icon icon-sitemap'></i> 关联模块</a>
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
			$.getJSON("my/need/del?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
</script>