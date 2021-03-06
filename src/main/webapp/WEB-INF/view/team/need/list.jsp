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
		<title>模块列表</title>
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
				<!--mainMenu start-->
				<div id="mainMenu" class="clearfix">
					<div class="btn-toolbar pull-left">
					    <!-- type值说明：0已删除，1未开始，2进行中，3待验收，4已验收，5已关闭，20所有，21未关闭，
					                  22已变更，23已逾期，12高级搜索    -->
						<a href="team/need/index?type=20" class="btn btn-link ${prm.type == 20 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 20}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=21" class="btn btn-link ${prm.type == 21 ? 'btn-active-text':''}">
							<span class="text">未关闭</span>
							<c:if test="${prm.type == 21}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=24" class="btn btn-link ${prm.type == 24 ? 'btn-active-text':''}">
							<span class="text">待安排</span>
							<c:if test="${prm.type == 24}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">未开始</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=2" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">进行中</span>
							<c:if test="${prm.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=3" class="btn btn-link ${prm.type == 3 ? 'btn-active-text':''}">
							<span class="text">待验收</span>
							<c:if test="${prm.type == 3}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=4" class="btn btn-link ${prm.type == 4 ? 'btn-active-text':''}">
							<span class="text">已验收</span>
							<c:if test="${prm.type == 4}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=22" class="btn btn-link ${prm.type == 22 ? 'btn-active-text':''}">
							<span class="text">已变更</span>
							<c:if test="${prm.type == 22}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=23" class="btn btn-link ${prm.type == 23 ? 'btn-active-text':''}">
							<span class="text">已逾期</span>
							<c:if test="${prm.type == 23}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=5" class="btn btn-link ${prm.type == 5 ? 'btn-active-text':''}">
							<span class="text">已关闭</span>
							<c:if test="${prm.type == 5}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/need/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">已删除</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a class="btn btn-link querybox-toggle ${prm.type == 12 ? 'querybox-opened':''}" id="bysearchTab"><i class="icon icon-search muted"></i> 高级搜索
							<c:if test="${prm.type == 12}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<div style="width:220px;float:right">
							<form method="post" action="team/need/index?type=99" class="search-form">
								<input type="text" name="nameOrId" value="${prm.nameOrId}" class="form-control searchInput" 
									style="border:1px solid #999;height:34px" placeholder="输入 模块名称 或 ID，按 回车 查询">
							</form>
						</div>
					</div>
					<!--btn-toolbar start-->
					<!-- <div class="btn-toolbar pull-right">
						<a href="team/need/toBatchAdd" class="btn btn-secondary" style="text-shadow:0 -1px 0 rgba(0,0,0,.2);"><i class="icon icon-plus"></i> 批量创建</a>
						<a href="team/need/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i> 提需求</a>
					</div> -->
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator ${prm.type == 12 ? 'show':''}" id="queryBox">
							<form method="post" action="team/need/index?type=12" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="needType" id="needType">
													<option ${prm.needType=='1'?'selected="selected"':'' } value="1">模块名称</option>
													<option ${prm.needType=='2'?'selected="selected"':'' } value="2">模块描述</option>
												</select>
											</td>
											<td>
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control  searchInput" placeholder="选择后请输入要查询的模块名称 或 模块描述">
											</td>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="state" id="state">
													<option value="">请选择状态</option>
													<option ${prm.state=='1'?'selected="selected"':'' } value="1">未开始</option>
													<option ${prm.state=='3'?'selected="selected"':'' } value="3">待验收</option>
													<option ${prm.state=='2'?'selected="selected"':'' } value="2">进行中</option>
													<option ${prm.state=='0'?'selected="selected"':'' } value="0">已删除</option>
													<option ${prm.state=='4'?'selected="selected"':'' } value="4">已验收</option>
													<option ${prm.state=='5'?'selected="selected"':'' } value="5">已关闭</option>
												</select>
											</td>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="srcId" id="srcId">
													<option value="">请选择模块来源</option>
													<c:forEach items="${needSrc}" var="src" varStatus="sta">
														<option ${prm.srcId==src.id?'selected="selected"':'' } value="${src.id}">${src.need_src}</option>
													</c:forEach>
												</select>
											</td>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="meetingId" id="meetingId">
													<option value="">请选择月会议</option>
													<c:forEach items="${meetings}" var="src" varStatus="sta">
														<option ${prm.meetingId==src.id?'selected="selected"':'' } value="${src.id}">${src.name}</option>
													</c:forEach>
												</select>
											</td>
										</tr>
										<tr>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="memberType" id="memberType">
													<option ${prm.memberType=='1'?'selected="selected"':'' } value="1">指派人</option>
													<option ${prm.memberType=='2'?'selected="selected"':'' } value="2">关闭人</option>
													<option ${prm.memberType=='3'?'selected="selected"':'' } value="3">需求方</option>
												</select>
											</td>
											<td>
												<input type="text" name="memberSearch" id="memberSearch" value="${prm.memberSearch}" class="form-control  searchInput" placeholder="选择后请输入要查询的指派人 或 关闭人 或 模块方">
											</td>
											<td class="w-200px">
												<select class="form-control chosen chosen-select" name="dateType" id="dateType">
													<option value="">请选择日期</option>
													<option ${prm.dateType=='1'?'selected="selected"':'' } value="1">开始日期</option>
													<option ${prm.dateType=='2'?'selected="selected"':'' } value="2">结束日期</option>
												</select>
											</td>
											<td class="w-200px">
												<input type="text" name="start_date" id="start_date" value="${prm.start_date}" class="form-control form-date" placeholder="开始" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
											<td class="w-200px">
												<input type="text" name="end_date" id="end_date" value="${prm.end_date}" class="form-control form-date" placeholder="结束" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td colspan="5" class="text-center form-actions">
												<button type="submit" id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">搜索</button>
										</tr>
									</tbody>
								</table>
							</form>
						</div>
						<form class="main-table table-task skip-iframe-modal" method="post"
							id="projectTaskForm" data-ride="table">
							<!--table-responsive start-->
							<div class="table-responsive">
								<table class="table has-sort-head" id="taskList" data-fixed-left-width="550" data-fixed-right-width="160" style="border:2px solid #fff">
									<thead>
										<tr>
											<th data-flex="false" data-width="90px" style="width:90px" class="c-id text-center" title="ID">
												<a href="javascript:void(0)" onclick="pageOrder('id');" 
														class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri " title="模块名称">
												<a  href="javascript:void(0)" onclick="pageOrder('need_name');" 
														class="${prm.orderColumn=='need_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">模块名称</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:150px" class="c-pri " title="所属项目">
												<a href="javascript:void(0)" onclick="pageOrder('tp.project_name');" 
														class="${prm.orderColumn=='tp.project_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">所属项目</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-pri " title="需求方">
												<a  href="javascript:void(0)" onclick="pageOrder('member_id');" 
														class="${prm.orderColumn=='member_id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">需求方</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-pri " title="指派给">
												<a  href="javascript:void(0)" onclick="pageOrder('assigned_name');" 
														class="${prm.orderColumn=='assigned_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">指派给</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="开始时间">
												<a  href="javascript:void(0)" onclick="pageOrder('start_date');" 
														class="${prm.orderColumn=='start_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">开始时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="结束时间">
												<a  href="javascript:void(0)" onclick="pageOrder('end_date');" 
														class="${prm.orderColumn=='end_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">结束时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:120px" class="c-name text-center" title="验收时间">
												<a  href="javascript:void(0)" onclick="pageOrder('checked_time');" 
														class="${prm.orderColumn=='checked_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">验收时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:90px" class="c-name text-center" title="状态">
												<a  href="javascript:void(0)" onclick="pageOrder('state');" 
														class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">状态</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="创建时间">
												<a  href="javascript:void(0)" onclick="pageOrder('create_time');" 
														class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">创建时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:110px" class="c-name text-center" title="确认原型图">
												<a  href="javascript:void(0)" onclick="pageOrder('prototype_figure');" 
														class="${prm.orderColumn=='prototype_figure'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">确认原型图</a>
											</th>
											<th data-flex="false" data-width="300px" style="width:200px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="need" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${need.id}</td>
											<td class="text-left">
												<c:if test="${need.resolved == 1 && (prm.type == 21 || prm.type == 20)}">
													<a class="task-toggle" data-id="${need.id}"><i class="icon icon-caret-down"></i></a>
												</c:if>
												<c:if test="${need.parent_id > 0 && prm.type != 21 && prm.type != 20}">
													<span class="label label-badge label-light">子</span>
												</c:if>
												<a href="team/need/detail?id=${need.id}" data-toggle="tooltip" data-placement="top" title="${need.need_name}">
													${need.need_name}
												</a>
												<c:if test="${need.full == 0}">
													<span class="label label-warning" title="不能创建任务，不能分解">不完整模块</span>
												</c:if>
												<c:if test="${need.meeting_id > 0}">
													<span class="label label-info" data-toggle="tooltip" data-placement="top" title="${need.meeting_name}">会</span>
												</c:if>
											</td>
											<td class="text-left">
												<c:if test="${need.project_id!='0'}">
													<span class="label label-info" style="background-color: #00da88;" data-toggle="tooltip" data-placement="top" title="${need.project_name}">项目</span>
												</c:if>
												<c:if test="${need.project_id=='0'}">
													<span class="label label-info" style="background-color: #03b8cf;" data-toggle="tooltip" data-placement="top" title="${need.product_name}">产品</span>
												</c:if>
												${need.project_id!='0'?need.project_name:need.product_name}
											</td>
											<td class="c-name text-left">${need.member_name}</td>
											<td class="c-name text-left">
												<c:if test="${need.assigned_name == '' || need.assigned_name == null}">
													<c:if test="${need.state != 6}">
														<a href="team/need/toAssign?id=${need.id}" class="btn btn-icon-left btn-sm">
															<i class="icon icon-hand-right"></i>
															<span class="text-primary">未指派</span>
														</a>
													</c:if>
													<c:if test="${need.state == 6}">
														<a href="team/need/toArrange?id=${need.id}" class="btn btn-icon-left btn-sm">
															<i class="icon icon-hand-right"></i>
															<span class="text-primary">未安排</span>
														</a>
													</c:if>
												</c:if>
												<c:if test="${need.assigned_name != ''}">
													<span class="text-red">${need.assigned_name}</span>
												</c:if>
											</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.start_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.end_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.checked_time}" pattern="yyyy-MM-dd HH:mm" /></td>
											<td class="c-assignedTo has-btn text-center">
												${need.state == 0 ? '已删除' : need.state == 1 ? '未开始' : need.state == 2 ? '进行中'
												 : need.state == 3 ? '待验收' : need.state == 4 ? '已验收' : need.state == 5 ? '已关闭' 
												 : need.state == 6 ? '待安排' : need.state == 7 ? '待确定' : need.state == 8 ? '待谈判' 
												 : need.state == 9 ? '谈判失败' : '未知'}
										        <c:if test="${need.overdue==1}">
												    <span class="label label-danger" title="任务已逾期">逾</span>
											    </c:if>
											</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.create_time}" pattern="yyyy-MM-dd" /></td>
											<c:if test="${need.project_id=='0'}">
												<td class="c-assignedTo has-btn text-center">
													${need.prototype_figure == 0 ? '未确认' : need.prototype_figure == 1 ? '已确认' : '未知'}
												</td>
											</c:if>
											<c:if test="${need.project_id!='0'}">
												<td class="c-assignedTo has-btn text-center">
													
												</td>
											</c:if>
											<td class="c-actions text-right">
												<c:if test="${need.state == 1 || need.state == 2 || need.state == 3 || need.state == 4}">
													<a href="team/need/toRelate?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关联月会议"><i class='icon icon-sitemap'></i></a>
												</c:if>
												<c:if test="${need.state == 1 || need.state == 2}">
												    <a href="team/need/toClose?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关闭模块"><i class='icon-task-close icon-off'></i></a>
											    </c:if>
												<c:if test="${need.full == 1 && (need.state!=7 && need.state!=8)}">
													<c:if test="${need.state == 1}">
														<a href="team/need/toOpen?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="接收模块"><i class="icon-task-start icon-play"></i></a>
													</c:if>
													<c:if test="${need.resolved == 0}">
														<c:if test="${need.task_sum > 0 && need.not_finish_task == 0}">
													 		<c:if test="${need.project_id=='0' && need.state == 2 && need.test_state == 4}">
																<a href="team/need/toSubmitCheck?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="提交验收"><i class="icon-task-finish icon-checked"></i></a>
															</c:if>
															<c:if test="${need.project_id=='0' && need.state == 3}">
																<a href="team/need/toCheck?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="验收模块"><i class="icon-story-review icon-glasses"></i></a>
															</c:if>
															<c:if test="${need.project_id!='0' && need.state == 2}">
																<a href="team/need/toSubmitCheck?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="提交验收"><i class="icon-task-finish icon-checked"></i></a>
															</c:if>
															<c:if test="${need.project_id!='0' && need.state == 3}">
																<a href="team/need/toCheck?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="验收模块"><i class="icon-story-review icon-glasses"></i></a>
															</c:if>
														</c:if>
													</c:if>
													<c:if test="${need.resolved == 1 && need.sub_need_count > 0}">
														<c:if test="${need.not_check_need == 0 && need.not_finish_task == 0}">
													 		<c:if test="${need.project_id=='0' && need.state == 2 && need.test_state == 4}">
																<a href="team/need/toSubmitCheck?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="提交验收"><i class="icon-task-finish icon-checked"></i></a>
															</c:if>
															<c:if test="${need.project_id=='0' && need.state == 3}">
																<a href="team/need/toCheck?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="验收模块"><i class="icon-story-review icon-glasses"></i></a>
															</c:if>
															<c:if test="${need.project_id!='0' && need.state == 2}">
																<a href="team/need/toSubmitCheck?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="提交验收"><i class="icon-task-finish icon-checked"></i></a>
															</c:if>
															<c:if test="${need.project_id!='0' && need.state == 3}">
																<a href="team/need/toCheck?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="验收模块"><i class="icon-story-review icon-glasses"></i></a>
															</c:if>
														</c:if>
													</c:if>
												</c:if>
												<c:if test="${need.project_id=='0' && need.state==2 && need.prototype_figure==0 && need.resolved == 1 && (need.parent_id == null || need.parent_id == 0)}">
													<a href="team/need/toConfirmPrototypeFigure?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="确认原型图"><i class="icon-task-finish icon-checked"></i></a>
												</c:if>
												<c:if test="${need.project_id=='0' && (need.parent_id == null || need.parent_id == 0) && need.full == 1 && need.state == 2 && need.prototype_figure == 1 && need.resolved == 0}">
													<a href="team/task/toAdd?need_id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="建任务"><i class="icon icon-plus"></i></a>
												</c:if>
												<c:if test="${need.project_id!='0' && (need.parent_id == null || need.parent_id == 0) && need.full == 1 && need.state == 2}">
													<a href="team/task/toAdd?need_id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="建任务"><i class="icon icon-plus"></i></a>
												</c:if>
												<c:if test="${(need.parent_id == null || need.parent_id == 0) && need.full == 1 && (need.state == 2) && need.project_id>0}">
													<a href="team/need/toAddPJSon?need_id=${need.id}&project_id=${need.project_id}" class="btn" data-toggle="tooltip" data-placement="top" title="添加子模块"><i class='icon-task-close'>子</i></a>
												</c:if>
												<c:if test="${(need.parent_id == null || need.parent_id == 0) && need.full == 1 && (need.state == 2) && need.product_id>0 && need.prototype_figure==0}">
													<a href="team/need/toAddPDSon?need_id=${need.id}&product_id=${need.product_id}" class="btn" data-toggle="tooltip" data-placement="top" title="添加子模块"><i class='icon-task-close'>子</i></a>
												</c:if>
												<c:if test="${need.project_id=='0' && need.state == 2 && need.test_state == 1 && need.not_check_need == 0 && need.not_finish_task == 0 && (need.parent_id == null || need.parent_id == 0)}">
													<a href="test/apply/toAdd?need_id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="提交测试">提测</a>
												</c:if>
												<c:if test="${need.project_id!='0' && need.state == 4}">
													<a href="test/apply/toAdd?need_id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="提交测试">提测</a>
												</c:if>
												<c:if test="${need.state == 6}">
													<a href="team/need/toArrange?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="安排模块">安排</i></a>
												</c:if>
												<c:if test="${need.state == 7}">
													<a href="team/need/toSure?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="确认新需求">确认</i></a>
													<a href="team/need/toAddPJSon?need_id=${need.id}&project_id=${need.project_id}" class="btn" data-toggle="tooltip" data-placement="top" title="添加子模块"><i class='icon-task-close'>子</i></a>
												</c:if>
												<c:if test="${need.state == 8}">
													<a href="team/need/toTalk?id=${need.id}" class="btn" data-toggle="tooltip" data-placement="top" title="确认谈判结果">谈判</i></a>
													<a href="team/need/toAddPJSon?need_id=${need.id}&project_id=${need.project_id}" class="btn" data-toggle="tooltip" data-placement="top" title="添加子模块"><i class='icon-task-close'>子</i></a>
												</c:if>
											</td>
										</tr>
										<c:if test="${need.resolved == 1 && (prm.type == 20 || prm.type == 21)}">
											<c:forEach items="${need.subNeed}" var="subNeed" varStatus="subSta">
												<c:if test="${need.id == subNeed.parent_id}">
													<tr class="table-children ${subSta.first?'table-child-top':''} ${subSta.last?'table-child-bottom':''} parent-${need.id}">
														<td class="c-id cell-id text-center">${subNeed.id}</td>
														<td class="text-left">
															<a href="team/need/detail?id=${subNeed.id}" data-toggle="tooltip" data-placement="top" title="${subNeed.need_name}">
																<span class="label label-badge label-light">子</span> ${subNeed.need_name}
															</a>
															<c:if test="${subNeed.full == 0}">
																<span class="label label-warning" title="不能创建任务，不能分解">不完整模块</span>
															</c:if>
														</td>
														<td class="text-left">
															${subNeed.project_name}
														</td>
														<td class="c-name text-left">${subNeed.member_name}</td>
														<td class="c-name text-left">
															<c:if test="${subNeed.assigned_name == '' || subNeed.assigned_name == null}">
																<c:if test="${subNeed.state != 6}">
																	<a href="team/need/toAssign?id=${subNeed.id}" class="btn btn-icon-left btn-sm">
																		<i class="icon icon-hand-right"></i>
																		<span class="text-primary">未指派</span>
																	</a>
																</c:if>
																<c:if test="${subNeed.state == 6}">
																	<a href="team/need/toArrange?id=${subNeed.id}" class="btn btn-icon-left btn-sm">
																		<i class="icon icon-hand-right"></i>
																		<span class="text-primary">未安排</span>
																	</a>
																</c:if>
															</c:if>
															<c:if test="${subNeed.assigned_name != ''}">
																<span class="text-red">${subNeed.assigned_name}</span>
															</c:if>
														</td>
														<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${subNeed.start_date}" pattern="yyyy-MM-dd" /></td>
														<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${subNeed.end_date}" pattern="yyyy-MM-dd" /></td>
														<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${subNeed.checked_time}" pattern="yyyy-MM-dd HH:mm" /></td>
														<td class="c-assignedTo has-btn text-center">
														        ${subNeed.state == 0 ? '已删除' : subNeed.state == 1 ? '未开始' : subNeed.state == 2 ? '进行中'
													            : subNeed.state == 3 ? '待验收' : subNeed.state == 4 ? '已验收' : subNeed.state == 5 ? '已关闭'
													            : subNeed.state == 6 ? '待安排' : subNeed.state == 7 ? '待确定' : subNeed.state == 8 ? '待谈判' 
													            : subNeed.state == 9 ? '谈判失败' : '未知'}
													            <c:if test="${subNeed.overdue==1}">
													               <span class="label label-danger" title="任务已逾期">逾</span>
												                </c:if>
														</td>
														<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${subNeed.create_time}" pattern="yyyy-MM-dd" /></td>
														<c:if test="${subNeed.project_id=='0'}">
															<td class="c-assignedTo has-btn text-center">
																${subNeed.prototype_figure == 0 ? '未确认' : subNeed.prototype_figure == 1 ? '已确认' : '未知'}
															</td>
														</c:if>
														<c:if test="${subNeed.project_id!='0'}">
															<td class="c-assignedTo has-btn text-center">
																
															</td>
														</c:if>
														<td class="c-actions text-right">
															<c:if test="${subNeed.state == 1 || subNeed.state == 2}">
																<a href="team/need/toChange?id=${subNeed.id}" class="btn" data-toggle="tooltip" data-placement="top" title="${subNeed.full == 0?'完善模块':'模块变更'}"><i class="icon-story-change icon-fork"></i></a>
																<a href="team/need/toClose?id=${subNeed.id}" class="btn" data-toggle="tooltip" data-placement="top" title="关闭模块"><i class='icon-task-close icon-off'></i></a>
															</c:if>														  
															<c:if test="${subNeed.full == 1 && (need.state!=7 && need.state!=8)}">
																<c:if test="${subNeed.state == 1}">
																	<a href="team/need/toOpen?id=${subNeed.id}" class="btn" data-toggle="tooltip" data-placement="top" title="接收模块"><i class="icon-task-start icon-play"></i></a>
												                </c:if>
																<c:if test="${subNeed.task_sum > 0 && subNeed.not_finish_task == 0}">
													                <c:if test="${subNeed.state == 2}">
													          			<a href="team/need/toSubmitCheck?id=${subNeed.id}" class="btn" data-toggle="tooltip" data-placement="top" title="提交验收"><i class="icon-task-finish icon-checked"></i></a>
																	</c:if>
														            <c:if test="${subNeed.state == 3}">
																		<a href="team/need/toCheck?id=${subNeed.id}" class="btn" data-toggle="tooltip" data-placement="top" title="验收模块"><i class="icon-story-review icon-glasses"></i></a>
																	</c:if>
													           	</c:if>
															</c:if>
															<c:if test="${subNeed.project_id=='0' && subNeed.full == 1 && subNeed.state == 2 && subNeed.prototype_figure == 1 && (need.state!=7 && need.state!=8)}">
																<a href="team/task/toAdd?need_id=${subNeed.id}" class="btn" data-toggle="tooltip" data-placement="top" title="建任务"><i class="icon icon-plus"></i></a>
															</c:if>
															<c:if test="${subNeed.project_id!='0' && subNeed.full == 1 && subNeed.state == 2 && (need.state!=7 && need.state!=8)}">
																<a href="team/task/toAdd?need_id=${subNeed.id}" class="btn" data-toggle="tooltip" data-placement="top" title="建任务"><i class="icon icon-plus"></i></a>
															</c:if>
															<c:if test="${(subNeed.state == 0 || subNeed.state == 5) && (need.state!=7 && need.state!=8)}"></c:if>
															<c:if test="${subNeed.state == 6 && (need.state!=7 && need.state!=8)}">
																<a href="team/need/toArrange?id=${subNeed.id}" class="btn" data-toggle="tooltip" data-placement="top" title="安排模块"><i class='icon-task-close'>安排</i></a>
															</c:if>
														</td>
													</tr>
												</c:if>
											</c:forEach>
										</c:if>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!--table-responsive end-->
						</form>
						<!--table-footer start-->
						<div class="table-footer" style="left: 0px; bottom: 0px;">
							<!--pager srtart-->
							<jsp:include page="/WEB-INF/view/comm/pagebar_conut.jsp"></jsp:include>
							<!--pager end-->
						</div>
						<!--table-footer end-->
					</div>
					<!--main-col end-->
				</div>
			</div>
		</main>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
	$("#bysearchTab").click(function(){
		if($(this).hasClass("querybox-opened")){
			$(this).removeClass("querybox-opened")
			$("#queryBox").removeClass("show")
		}else{
			$(this).addClass("querybox-opened")
			$("#queryBox").addClass("show")
		}
	});
</script>