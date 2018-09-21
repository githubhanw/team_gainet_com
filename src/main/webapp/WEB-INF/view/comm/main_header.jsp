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
				<li ${m=='my' && s!='task' && s!='need'?'class="active"':'' } data-id="my"><a href="my"><span>我的地盘</span></a></li>
				<li ${(m=='my' && s=='task')?'class="active"':'' } data-id="my"><a href="my/task"><span>我的任务</span></a></li>
				<li ${(m=='my' && s=='need')?'class="active"':'' } data-id="my"><a href="my/need"><span>我的需求</span></a></li>
				<li class="divider"></li>
				<li ${m=='project' || m=='need' || m=='task' || m=='release' || m=='meeting' || m=='apply'?'class="active"':'' } data-id="my"><a href="team/task/index"><span>管理中心</span></a></li> 
				<li class="divider"></li>
				<li ${m=='pd'?'class="active"':'' } data-id="doc"><a href="declaration/project/index" ${m=='pd'?'class="active"':'' }>科技申报</a></li>
				<li class="divider"></li>
				<li ${m=='filemanage'?'class="active"':'' } data-id="doc"><a href="filemanage/manage/index" ${m=='filemanage'?'class="active"':'' }>文档</a></li>
				<!--<li ${(m=='filemanage' && s=='manage')?'class="active"':'' }data-id="filemanage"><a href="filemanage/manage">文档</a></li>
				<li data-id="report"><a href="#">统计</a></li> -->
				<li ${m=='organization'?'class="active"':'' } data-id="doc"><a href="organization/user/index" ${m=='organization'?'class="active"':'' }>组织</a></li>
				
				<%-- <li ${m=='my'?'class="active"':'' } data-id="my"><a href="my" ${m=='my'?'class="active"':'' }><span>我的地盘</span></a></li>
				<li ${m=='project'?'class="active"':'' } data-id="doc"><a href="team/project/index" ${m=='project'?'class="active"':'' }>项目</a></li>
				<li ${m=='need'?'class="active"':'' } data-id="doc"><a href="team/need/index" ${m=='need'?'class="active"':'' }>需求</a></li>
				<li ${m=='task'?'class="active"':'' } data-id="doc"><a href="team/task/index" ${m=='task'?'class="active"':'' }>任务</a></li>
				<li ${m=='apply'?'class="active"':'' } data-id="doc"><a href="test/apply/index" ${m=='apply'?'class="active"':'' }>测试</a></li>
				<li class="divider"></li>
				<li ${m=='pd'?'class="active"':'' } data-id="doc"><a href="declaration/project/index" ${m=='pd'?'class="active"':'' }>科技申报</a></li>
				<!-- <li data-id="doc"><a href="#">文档</a></li>
				<li data-id="report"><a href="#">统计</a></li> -->
				<li ${m=='organization'?'class="active"':'' } data-id="doc"><a href="organization/user/index" ${m=='organization'?'class="active"':'' }>组织</a></li> --%>
			</ul>
		</nav>
		<!--navbar end-->
		<div id="toolbar">
			<!--userMenu srart-->
			<div id="userMenu">
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
							<li><a href="my/auth" data-width="600">我的权限</a></li>
							<!-- <li><a href="my/weixin" data-width="600">绑定微信</a></li> -->
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