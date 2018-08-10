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
    	<%@ include file="/WEB-INF/view/comm/cssjs.jsp" %>
		<style>
			.block-welcome .col-left {
				width: 25%;
			}
			.block-welcome .col-left>h4 {
				margin: 5px 0;
				line-height: 30px;
			}
			.block-welcome .col-left .timeline {
				margin: 10px 0 0;
				font-size: 12px;
			}
			.block-welcome .col-right>h4 {
				font-weight: normal;
			}
			.block-welcome .col-right>h4 small {
				display: inline-block;
				margin-right: 8px;
				font-size: 14px;
			}
			.block-welcome .col-right .tiles {
				padding: 10px 0 0 16px
			}
			.block-welcome .col-right .tile {
				width: 16%;
			}
			.block-welcome .panel-body {
				padding-top: 15px;
			}
			.block-welcome .user-notification-icon {
				position: relative;
				display: inline-block;
				margin-left: 5px;
			}
			.block-welcome .user-notification-icon .label-badge {
				position: absolute;
				top: 1px;
				right: -8px;
				min-width: 16px;
				padding: 2px;
				font-size: 12px;
				font-weight: normal;
			}
			.block-welcome.block-sm .col-right {
				padding: 0;
			}
			.block-welcome.block-sm .col-right .tiles {
				border-left: none;
				padding-left: 0
			}
			.block-welcome.block-sm .tile-title {
				font-size: 12px;
				margin: 0 -10px;
			}
		</style>
							<style>
		.block-flowchart .panel-body {
			padding-top: 0
		}
		
		.block-flowchart .table {
			margin-bottom: 0;
		}
		
		.block-flowchart .table>thead>tr>th, .block-flowchart .table>tbody>tr>td,
			.block-flowchart .table>tbody>tr>th {
			padding: 10px;
			font-weight: normal;
			text-align: center;
		}
		
		.block-flowchart .table>thead>tr>th:first-child, .block-flowchart .table>tbody>tr>td:first-child,
			.block-flowchart .table>tbody>tr>th:first-child {
			text-align: left;
		}
		
		.block-flowchart .table>thead>tr>th {
			width: 18%;
			padding-top: 0
		}
		
		.block-flowchart .table>thead>tr>th:first-child {
			width: auto;
			padding: 0 10px 0 10px;
			background: none;
		}
		
		.block-flowchart .table>thead>tr>th:first-child+th>div {
			background: none;
		}
		
		.block-flowchart .table>thead>tr>th, .block-flowchart .table>thead>tr>th>div
			{
			padding: 0;
			background:
				url('data:image/gif;base64,R0lGODlhLQAPAJEAAAAAAP///+7u7v///yH5BAEAAAMALAAAAAAtAA8AAAJEnH8imMvKopzKofrg3FRfb2Dc+FmNOYgkqZ4ZunZwm4KxHNH0nds1uuOFfDqf8NcDGoW7IuzYtAWZROmSF1U+oc+qsAAAOw==')
				no-repeat;
			background-position: right -22px top 15px;
		}
		
		.block-flowchart .table>thead>tr>th>div {
			padding: 10px 10px 15px 10px;
			background-position: left -23px top 15px;
		}
		
		.block-flowchart .table>thead>tr>th:last-child {
			background: none;
		}
		
		.block-flowchart .table>tbody>tr>td {
			color: #3c4353;
		}
		
		.block-flowchart .table>tbody>tr:nth-child(even)>td {
			background: #f7f8f9;
		}
		
		.block-flowchart .flowchart-title {
			font-size: 14px;
			font-weight: bold;
			color: #3c4353;
		}
		
		.block-flowchart .flowchart-step {
			display: inline-block;
			width: 24px;
			height: 24px;
			font-size: 14px;
			line-height: 24px;
			color: #fff;
			border-radius: 50%;
		}
		
		.block-flowchart.block-sm .table>tbody>tr>td, .block-flowchart.block-sm .table>tbody>tr>th
			{
			padding: 10px 4px;
			font-size: 12px;
		}
		
		.block-flowchart.block-sm .table>thead>tr>th:first-child {
			padding: 0 4px 0 4px;
			width: 65px
		}
		</style>
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
							<div class="panel-move-handler"></div>
							<div class="panel-body conatiner-fluid">
								<div class="table-row">
									<div class="col col-right">
										<h4>
											<small class="text-muted">2018年08月09日</small> 工作任务统计
										</h4>
										<div class="row tiles">
											<div class="col tile">
												<a href="my/task">
													<div class="tile-title">我的任务</div>
													<div class="tile-amount">${taskCount.count}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="team/task/index?type=16">
													<div class="tile-title">今日任务</div>
													<div class="tile-amount">${taskCount.today}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="team/task/index?type=18">
													<div class="tile-title">未接收任务</div>
													<div class="tile-amount">${taskCount.noopen}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="team/task/index?type=17">
													<div class="tile-title">昨日任务</div>
													<div class="tile-amount">${taskCount.yesteday}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="team/task/index?type=15">
													<div class="tile-title">延期任务</div>
													<div class="tile-amount"><span class="label label-warning" style="padding:.2em .6em;font-size:30px;">${taskCount.delay}</span></div>
												</a>
											</div>
											<div class="col tile">
												<a href="team/task/index?type=14">
													<div class="tile-title">逾期任务</div>
													<div class="tile-amount"><span class="label label-danger" style="padding:.2em .6em;font-size:30px;">${taskCount.overdue}</span></div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/bug">
													<div class="tile-title">我的Bug</div>
													<div class="tile-amount">${bugCount.count}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/test">
													<div class="tile-title">我的测试</div>
													<div class="tile-amount">${testCount.count}</div>
												</a>
											</div>
											<div class="col tile">
												<a href="my/need">
													<div class="tile-title">我的需求</div>
													<div class="tile-amount">${needCount.count}</div>
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
											<th style="width: 110px;"><span class="flowchart-title">角色</span></th>
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
											<td>维护需求</td>
											<td></td>
										</tr>
										<tr class="text-middle">
											<th>产品经理</th>
											<td>创建需求</td>
											<td>验收需求</td>
											<td>分配任务</td>
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
											<th>研发人员</th>
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
<script>
	$('.pager').pager({
	    page: ${pageList.currentPage},
	    recTotal: ${pageList.totalCounts},
	    recPerPage: ${pageList.pageSize},
	    pageSizeOptions: [10, 20, 30, 50, 100],
	    lang: 'zh_cn',
	    linkCreator: "my/bug?type=${prm.type}&currentPage={page}&pageSize={recPerPage}&search=${prm.search}&orderColumn=${prm.orderColumn}&orderByValue=${prm.orderByValue}"
	});
</script>
</html>