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
		<title>我的地盘</title>
		<link rel="stylesheet" href="static/index/css/index.css">
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
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="panel block-welcome">
							<div class="panel-heading" style="background-color:white;border-color:white">
								<div class="panel-title">统计</div>
							</div>
							<div class="panel-body conatiner-fluid">
								<div class="table-row">
									<div class="col col-right">
										<div class="row tiles" >
											<div class="col tile">
												<a href="my/task">
													<div class="tile-title">我的任务</div>
													<div class="tile-amount">${taskCount.count}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/task?type=18">
													<div class="tile-title">待接收任务</div>
													<div class="tile-amount">${taskCount.wait > 0 ? taskCount.wait : 0}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/task?type=20">
													<div class="tile-title">进行中任务</div>
													<div class="tile-amount">${taskCount.doing > 0 ? taskCount.doing : 0}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/task?type=23">
													<div class="tile-title">审核中任务</div>
													<div class="tile-amount">${taskCount.checking > 0 ? taskCount.checking : 0}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/task?type=10">
													<div class="tile-title">已完成任务</div>
													<div class="tile-amount">${taskCount.done > 0 ? taskCount.done : 0}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/task?type=15">
													<div class="tile-title">延期任务</div>
													<div class="tile-amount">
														<span class="label label-warning" style="padding:.2em .6em;font-size:30px;">
															${taskCount.delay > 0 ? taskCount.delay : 0}
														</span>
													</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/task?type=14">
													<div class="tile-title">逾期任务</div>
													<div class="tile-amount">
														<span class="label label-danger" style="padding:.2em .6em;font-size:30px;">
															${taskCount.overdue > 0 ? taskCount.overdue : 0}
														</span>
													</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/task?type=13">
													<div class="tile-title">待我审核</div>
													<div class="tile-amount">${taskCount.wait_check > 0 ? taskCount.wait_check : 0}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/bug">
													<div class="tile-title">我的Bug</div>
													<div class="tile-amount">${bugCount.count > 0 ? bugCount.count : 0}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/test">
													<div class="tile-title">我的测试</div>
													<div class="tile-amount">${testCount.count > 0 ? testCount.count : 0}</div>
												</a>
											</div> 
											<div class="col tile">
												<a href="my/need">
													<div class="tile-title">我的模块</div>
													<div class="tile-amount">${needCount.count > 0 ? needCount.count : 0}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/need?type=13">
													<div class="tile-title">待验收模块</div>
													<div class="tile-amount">${needCount.checking > 0 ? needCount.checking : 0}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/need?type=14">
													<div class="tile-title">待我验收</div>
													<div class="tile-amount">${needCount.wait_check > 0 ? needCount.wait_check : 0}</div>
												</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="panel block-flowchart">
							<div class="panel-heading" style="background-color:white;border-color:white">
								<div class="panel-title">流程图</div>
							</div>
							<div class="panel-body has-table scrollbar-hover">
								<table class="table table-borderless">
									<thead>
										<tr class="text-middle">
											<th style="width: 120px;"><span class="flowchart-title">角色</span></th>
											<th><div>
													<span class="flowchart-step bg-secondary">1</span>
												</div></th>
											<th><div>
													<span class="flowchart-step bg-secondary">2</span>
												</div></th>
											<th><div>
													<span class="flowchart-step bg-secondary">3</span>
												</div></th>
											<th><div>
													<span class="flowchart-step bg-secondary">4</span>
												</div></th>
											<th><div>
													<span class="flowchart-step bg-secondary">5</span>
												</div></th>
										</tr>
									</thead>
									<tbody>
										<tr class="text-middle">
											<th>管理员</th>
											<td>维护组织</td>
											<td>维护权限</td>
											<td>维护项目</td>
											<td>维护模块</td>
											<td></td>
										</tr>
										<tr class="text-middle">
											<th>产品经理 / 需求方</th>
											<td>创建模块</td>
											<td>分配模块</td>
											<td>验收模块</td>
											<td></td>
											<td></td>
										</tr>
										<tr class="text-middle">
											<th>项目负责人</th>
											<td>创建任务</td>
											<td>分配任务</td>
											<td>分解任务</td>
											<td>审核任务</td>
											<td></td>
										</tr>
										<tr class="text-middle">
											<th>DevOps</th>
											<td>撰写用例</td>
											<td>执行用例</td>
											<td>领取任务和Bug</td>
											<td>更新状态</td>
											<td>完成任务和Bug</td>
										</tr>
										<tr class="text-middle">
											<th>测试人员</th>
											<td>撰写用例</td>
											<td>执行用例</td>
											<td>提交Bug</td>
											<td>验证Bug</td>
											<td>关闭Bug</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>