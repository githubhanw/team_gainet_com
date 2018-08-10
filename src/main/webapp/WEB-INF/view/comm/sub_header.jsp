<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--subHeader srart-->
<div id="subHeader">
	<div class="container">
		<!-- <div id="pageNav" class="btn-toolbar">
			<div class="btn-group angle-btn">
				<div class="btn-group">
					<button data-toggle="dropdown" type="button" class="btn"style="border-radius: 2px;">
						项目主页 <span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li><a href="#"><i class="icon icon-home"></i> 项目主页</a></li>
						<li><a href="#"><i class="icon icon-cards-view"></i> 所有项目</a></li>
						<li><a href="#"><i class="icon icon-plus"></i> 添加项目</a></li>
					</ul>
				</div>
			</div>
			<div class="btn-group angle-btn">
				<div class="btn-group">
					<button data-toggle="dropdown" type="button" class="btn btn-limit"
						id="currentItem" title="www.kuaiyun.cn"
						style="border-radius: 2px;">
						www.kuaiyun.cn <span class="caret"></span>
					</button>
					<div id="dropMenu" class="dropdown-menu search-list"
						data-ride="searchList" data-url="">
						<div
							class="input-control search-box has-icon-left has-icon-right search-example">
							<input type="search" class="form-control search-input empty">
							<label class="input-control-icon-left search-icon"><i
								class="icon icon-search"></i></label> <a
								class="input-control-icon-right search-clear-btn"><i
								class="icon icon-close icon-sm"></i></a>
						</div>
					</div>
				</div>
			</div>
		</div> -->
		<nav id="subNavbar" ${m=='my'?'':'style="display:none"'}>
			<ul class="nav nav-default" style="max-width: 1491px; left: 0px; position: relative;">
				<li class="dropdown dropdown-hover ${m=='my' && s==''?'active':'' }"><a href="my">首页</a></li>
				<li class="dropdown dropdown-hover ${m=='my' && s=='task'?'active':'' }"><a href="my/task">任务</a></li>
				<li class="dropdown dropdown-hover ${m=='my' && s=='bug'?'active':'' }"><a href="my/bug">Bug</a></li>
				<li class="dropdown dropdown-hover ${m=='my' && s=='test'?'active':'' }"><a href="my/test">测试</a></li>
				<li class="dropdown dropdown-hover ${m=='my' && s=='need'?'active':'' }"><a href="my/need">需求</a></li>
			</ul>
		</nav>
		<nav id="subNavbar" ${m=='project' || m=='need' || m=='task' || m=='apply' || m=='bug'?'':'style="display:none"'}>
			<ul class="nav nav-default"
				style="max-width: 1491px; left: 0px; position: relative;">
				<li class="dropdown dropdown-hover ${m=='project' && s=='project'?'active':'' }"><a href="team/project/index">项目列表</a></li>
				<%-- <li class="dropdown dropdown-hover ${m=='project' && s=='add'?'active':'' }"><a href="team/project/toAdd">添加项目</a></li> --%>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='need' && s=='need'?'active':'' }"><a href="team/need/index">需求列表</a></li>
				<%-- <li class="dropdown dropdown-hover ${m=='need' && s=='add'?'active':'' }"><a href="team/need/toAdd">提需求</a></li> --%>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='task' && s=='task'?'active':'' }"><a href="team/task/index">任务列表</a></li>
				<%-- <li class="dropdown dropdown-hover ${m=='task' && s=='add'?'active':'' }"><a href="team/task/toAdd">建任务</a></li> --%>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='apply' && s=='apply'?'active':'' }"><a href="test/apply/index">测试单列表</a></li>
				<%-- <li class="dropdown dropdown-hover ${m=='apply' && s=='add'?'active':'' }"><a href="test/apply/toAdd">添加测试单</a></li> --%>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='bug' && s=='bug'?'active':'' }"><a href="test/bug/index">Bug列表</a></li>
				<li class="dropdown dropdown-hover ${m=='bug' && s=='add'?'active':'' }"><a href="test/bug/toAdd">提Bug</a></li>
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
		<%-- <nav id="subNavbar" ${m=='apply' || m=='bug'?'':'style="display:none"'}>
			<ul class="nav nav-default"
				style="max-width: 1491px; left: 0px; position: relative;">
				<li class="dropdown dropdown-hover ${m=='apply' && s=='apply'?'active':'' }"><a href="test/apply/index">测试单列表</a></li>
				<li class="dropdown dropdown-hover ${m=='apply' && s=='add'?'active':'' }"><a href="test/apply/toAdd">添加测试单</a></li>
				<li class="divider"></li>
				<li class="dropdown dropdown-hover ${m=='bug' && s=='bug'?'active':'' }"><a href="test/bug/index">Bug列表</a></li>
				<li class="dropdown dropdown-hover ${m=='bug' && s=='add'?'active':'' }"><a href="test/bug/toAdd">提Bug</a></li>
			</ul>
		</nav> --%>
		<nav id="subNavbar" ${m=='organization' ?'':'style="display:none"'}>
			<ul class="nav nav-default" style="max-width: 1491px; left: 0px; position: relative;">
				<li class="dropdown dropdown-hover ${s=='user'?'active':'' }"><a href="organization/user/index">用户</a></li>
				<li class="dropdown dropdown-hover ${s=='department'?'active':'' }"><a href="organization/department/index">部门</a></li>
				<li class="dropdown dropdown-hover ${s=='role'?'active':'' }"><a href="organization/role/index">角色</a></li>
				<%-- <li class="dropdown dropdown-hover ${s=='privilege'?'active':'' }"><a href="organization/privilege/index">权限</a></li> --%>
			</ul>
		</nav>
		<div id="pageActions">
			<div class="btn-toolbar"></div>
		</div>
	</div>
</div>
<!--subHeader end-->