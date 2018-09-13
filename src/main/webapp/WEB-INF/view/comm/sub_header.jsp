<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--subHeader srart-->
<div id="subHeader">
	<div class="container">
		<nav id="subNavbar" ${m=='my'?'':'style="display:none"'}>
			<ul class="nav nav-default" style="max-width: 1491px; left: 0px; position: relative;">
				<li class="dropdown dropdown-hover ${m=='my' && s==''?'active':'' }"><a href="my">首页</a></li>
				<li class="dropdown dropdown-hover ${m=='my' && s=='task'?'active':'' }"><a href="my/task">我的任务</a></li>
				<li class="dropdown dropdown-hover ${m=='my' && s=='bug'?'active':'' }"><a href="my/bug">我的Bug</a></li>
				<li class="dropdown dropdown-hover ${m=='my' && s=='test'?'active':'' }"><a href="my/test">我的测试</a></li>
				<li class="dropdown dropdown-hover ${m=='my' && s=='need'?'active':'' }"><a href="my/need">我的需求</a></li>
				<li class="dropdown dropdown-hover ${m=='my' && s=='auth'?'active':'' }"><a href="my/auth">我的权限</a></li>
			</ul>
		</nav>
		<nav id="subNavbar" ${m=='project' || m=='need' || m=='task' || m=='apply' || m=='meeting'  || m=='milepost' ?'':'style="display:none"'}>
			<ul class="nav nav-default"
				style="max-width: 1491px; left: 0px; position: relative;">
				<li class="dropdown dropdown-hover ${m=='project' && s=='project'?'active':'' }"><a href="team/project/index">项目列表</a></li>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='need' && s=='need'?'active':'' }"><a href="team/need/index">需求列表</a></li>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='task' && s=='task'?'active':'' }"><a href="team/task/index">任务列表</a></li>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='apply' && s=='apply'?'active':'' }"><a href="test/apply/index">测试单列表</a></li>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='apply' && s=='bug'?'active':'' }"><a href="test/bug/index">Bug列表</a></li>
				<li class="dropdown dropdown-hover ${m=='apply' && s=='add'?'active':'' }"><a href="test/bug/toAdd">提Bug</a></li>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='meeting' && s=='meeting'?'active':'' }"><a href="month/meeting/index">月会议</a></li>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='milepost' && s=='manage'?'active':'' }"><a href="test/milepost/manage">里程碑</a></li>
			</ul>
		</nav>
		<nav id="subNavbar" ${m=='pd'?'':'style="display:none"'}>
			<ul class="nav nav-default"
				style="max-width: 1491px; left: 0px; position: relative;">
				<li class="dropdown dropdown-hover ${s=='project'?'active':'' }"><a href="declaration/project/index">项目列表</a></li>
				<li class="dropdown dropdown-hover ${s=='result'?'active':'' }"><a href="declaration/result/index">成果列表</a></li>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${s=='doc'?'active':'' }"><a href="declaration/doc/index">成果文档列表</a></li>
				<li class="dropdown dropdown-hover ${s=='doctype'?'active':'' }"><a href="declaration/doctype/index">成果文档类型</a></li>
			</ul>
		</nav>
		<nav id="subNavbar" ${m=='organization' ?'':'style="display:none"'}>
			<ul class="nav nav-default" style="max-width: 1491px; left: 0px; position: relative;">
				<li class="dropdown dropdown-hover ${s=='user'?'active':'' }"><a href="organization/user/index">用户</a></li>
				<li class="dropdown dropdown-hover ${s=='department'?'active':'' }"><a href="organization/department/index">团队</a></li>
				<li class="dropdown dropdown-hover ${s=='role'?'active':'' }"><a href="organization/role/index">角色</a></li>
				<li class="dropdown dropdown-hover ${s=='privilege'?'active':'' }"><a href="organization/privilege/index">权限管理</a></li>
			</ul>
		</nav>
		<nav id="subNavbar" ${m=='filemanage' ?'':'style="display:none"'}>
		    <ul class="nav nav-default" style="max-width: 1491px; left: 0px; position: relative;">
				<li class="dropdown dropdown-hover ${m=='filemanage' && s=='manage'?'active':'' }"><a href="filemanage/manage/index">全部文档</a></li>
			</ul>
		</nav>
		<div id="pageActions">
			<div class="btn-toolbar"></div>
		</div>
	</div>
</div>
<!--subHeader end-->