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
							<span class="label label-id">${entity.id}</span> <span class="text">测试单详情</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">申请人</div>
								<div class="detail-content article-content">${entity.applyName}</div>
							</div>
							<div class="detail">
								<div class="detail-title">申请时间</div>
								<div class="detail-content article-content"><fmt:formatDate value="${entity.applyTime}" pattern="yyyy-MM-dd" /></div>
							</div>
							<div class="detail">
								<div class="detail-title">测试内容</div>
								<div class="detail-content article-content">${entity.testContent}</div>
							</div>
							<div class="detail">
								<div class="detail-title">状态</div>
								<div class="detail-content article-content">
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
								</div>
							</div>
							<c:if test="${entity.state == 4}">
								<div class="detail">
									<div class="detail-title">驳回原因</div>
									<div class="detail-content article-content">${entity.dismissal}</div>
								</div>
							</c:if>
						</div>
					</div>
					<div class="side-col col-4">
						<c:if test="${task != null}">
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">任务信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr class="nofixed">
												<th>任务名称</th>
												<td title="/">${task.taskName}</td>
											</tr>
											<tr class="nofixed">
												<th>初始开始</th>
												<td title="/">${task.startDate}</td>
											</tr>
											<tr class="nofixed">
												<th>初始结束</th>
												<td title="/">${task.endDate}</td>
											</tr>
											<tr class="nofixed">
												<th>计划结束</th>
												<td title="/">${task.planEndDate}</td>
											</tr>
											<tr class="nofixed">
												<th>实际开始</th>
												<td title="/">${task.realStartDate}</td>
											</tr>
											<tr class="nofixed">
												<th>实际结束</th>
												<td title="/">${task.realEndDate}</td>
											</tr>
											<tr>
												<th>指派给</th>
												<td>${task.assignedName}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						</c:if>
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">基本信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr class="nofixed">
												<th>所属项目</th>
												<td title="/">${project.projectName}</td>
											</tr>
											<tr class="nofixed">
												<th>所属需求</th>
												<td title="/">${need.needName}</td>
											</tr>
											<tr>
												<th>需求来源</th>
												<td>
													<c:if test="${need.srcId=='1'}">产品经理</c:if>
													<c:if test="${need.srcId=='2'}">市场</c:if>
													<c:if test="${need.srcId=='3'}">客户</c:if>
													<c:if test="${need.srcId=='4'}">客服</c:if>
													<c:if test="${need.srcId=='5'}">技术支持</c:if>
													<c:if test="${need.srcId=='6'}">开发人员</c:if>
													<c:if test="${need.srcId=='7'}">测试人员</c:if>
													<c:if test="${need.srcId=='8'}">Bug</c:if>
													<c:if test="${need.srcId=='9'}">其他</c:if>
												</td>
											</tr>
											<tr>
												<th>来源备注</th>
												<td>${need.srcRemark}</td>
											</tr>
											<tr>
												<th>需求方</th>
												<td>${need.memberName}</td>
											</tr>
											<tr>
												<th>优先级</th>
												<td>
													<c:if test="${need.level=='1'}">紧急重要</c:if>
													<c:if test="${need.level=='2'}">紧急不重要</c:if>
													<c:if test="${need.level=='3'}">不紧急重要</c:if>
													<c:if test="${need.level=='4'}">不紧急不重要</c:if>
												</td>
											</tr>
											<tr>
												<th>当前状态</th>
												<td>
													${need.state == 1 ? '激活' : need.state == 2 ? '已变更' : need.state == 3 ? '已关闭' : need.state == 0 ? '已删除' : '未知'}
												</td>
											</tr>
											<tr>
												<th>所处阶段</th>
												<td>
													${need.stage == 1 ? '待验收' : need.stage == 2 ? '验收完成' : need.stage == 3 ? '验收不通过' : '未知'}
												</td>
											</tr>
											<tr>
												<th>开始日期</th>
												<td>${need.startDate}</td>
											</tr>
											<tr>
												<th>结束日期</th>
												<td>${need.endDate}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">项目的一生</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>由谁创建</th>
												<td>${need.memberName } 于 <fmt:formatDate value="${need.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>指派给</th>
												<td>
													<c:if test="${need.assignedName != null && need.assignedName != ''}">
														${need.assignedName } 于 <fmt:formatDate value="${need.assignedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>由谁关闭</th>
												<td>
													<c:if test="${need.closedName != null && need.closedName != ''}">
														${need.closedName } 于 <fmt:formatDate value="${need.closedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>关闭原因</th>
												<td>${need.closedReason }</td>
											</tr>
											<tr>
												<th>由谁验收</th>
												<td>
													<c:if test="${need.checkedName != null && need.checkedTime != null}"> <!-- need.stage < 6 -->
														${need.checkedName } 于 <fmt:formatDate value="${need.checkedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
													</c:if>
												</td>
											</tr>
											<tr>
												<th>最后修改</th>
												<td>${need.updateTime }</td>
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