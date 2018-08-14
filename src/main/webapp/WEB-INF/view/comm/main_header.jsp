<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--mainHeader start-->
<div id="mainHeader">
	<div class="container">
		<hrgroup id="heading" style="top:10px">
			<h1 id="companyname" title="景安任务管理系统">
				<a href=""><img src="static/jingan.png"></a>
			</h1>
		</hrgroup>
		<!--navbar start-->
		<nav id="navbar">
			<ul class="nav nav-default">
				<li ${m=='my'?'class="active"':'' } data-id="my"><a href="my" ${m=='my'?'class="active"':'' }><span>我的地盘</span></a></li>
				<li ${m=='project'?'class="active"':'' } data-id="doc"><a href="team/project/index" ${m=='project'?'class="active"':'' }>项目</a></li>
				<li ${m=='need'?'class="active"':'' } data-id="doc"><a href="team/need/index" ${m=='need'?'class="active"':'' }>需求</a></li>
				<li ${m=='task'?'class="active"':'' } data-id="doc"><a href="team/task/index" ${m=='task'?'class="active"':'' }>任务</a></li>
				<li ${m=='apply'?'class="active"':'' } data-id="doc"><a href="test/apply/index" ${m=='apply'?'class="active"':'' }>测试</a></li>
				<li class="divider"></li>
				<li ${m=='pd'?'class="active"':'' } data-id="doc"><a href="declaration/project/index" ${m=='pd'?'class="active"':'' }>科技申报</a></li>
				<!-- <li data-id="doc"><a href="#">文档</a></li>
				<li data-id="report"><a href="#">统计</a></li> -->
				<li ${m=='organization'?'class="active"':'' } data-id="doc"><a href="organization/user/index" ${m=='organization'?'class="active"':'' }>组织</a></li>
			</ul>
		</nav>
		<!--navbar end-->
		<div id="toolbar">
			<!--userMenu srart-->
			<div id="userMenu">
				<!-- <div id="searchbox">
					<div class="input-group">
						<div class="input-group-btn">
							<a data-toggle="dropdown" class="btn btn-link"
								style="border-radius: 2px 0px 0px 2px;"> <span
								id="searchTypeName">任务</span> <span class="caret"></span>
							</a> <input type="hidden" name="searchType" id="searchType"
								value="task">
							<ul id="searchTypeMenu" class="dropdown-menu">
								<li><a href="#" data-value="bug">Bug</a></li>
								<li><a href="#" data-value="story">需求</a></li>
								<li class="selected"><a href="#" data-value="task">任务</a></li>
								<li><a href="#" data-value="testcase">用例</a></li>
								<li><a href="#" data-value="project">项目</a></li>
								<li><a href="#" data-value="product">产品</a></li>
								<li><a href="#" data-value="user">用户</a></li>
								<li><a href="#" data-value="build">版本</a></li>
								<li><a href="#" data-value="release">发布</a></li>
								<li><a href="#" data-value="productplan">产品计划</a></li>
								<li><a href="#" data-value="testtask">测试单</a></li>
								<li><a href="#" data-value="doc">文档</a></li>
								<li><a href="#" data-value="testsuite">用例库</a></li>
								<li><a href="#" data-value="testreport">测试报告</a></li>
							</ul>
						</div>
						<input id="searchInput" class="form-control search-input"
							type="search" onclick="this.value=&quot;&quot;" onkeydown=""
							placeholder="编号(ctrl+g)" style="border-radius: 0px 2px 2px 0px;">
					</div>
					<a href="javascript:$.gotoObject();" class="btn btn-link"
						id="searchGo">GO!</a>
				</div> -->
				<ul id="userNav" class="nav nav-default">
					<li>
						<a class="dropdown-toggle" data-toggle="dropdown">
							<span class="user-name">${memberName}</span> <span class="caret"></span>
						</a>
						<ul class="dropdown-menu pull-right">
							<li><a href="my/task" data-width="600">我的任务</a></li>
							<li><a href="my/bug" data-width="600">我的Bug</a></li>
							<li><a href="my/test" data-width="600">我的测试</a></li>
							<li><a href="my/need" data-width="600">我的需求</a></li>
						</ul>
					</li>
					<li><a href="logout">退出</a></li>
				</ul>
			</div>
			<!--userMenu end-->
		</div>
	</div>
</div>
<!--mainHeader end-->