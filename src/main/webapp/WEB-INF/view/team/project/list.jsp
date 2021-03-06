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
		<title>项目列表</title>
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
						<a href="team/project/index?type=5" class="btn btn-link ${prm.type == 5 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 5}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/project/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">正常</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/project/index?type=2" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">待验收</span>
							<c:if test="${prm.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/project/index?type=3" class="btn btn-link ${prm.type == 3 ? 'btn-active-text':''}">
							<span class="text">已验收</span>
							<c:if test="${prm.type == 3}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/project/index?type=4" class="btn btn-link ${prm.type == 4 ? 'btn-active-text':''}">
							<span class="text">已完成</span>
							<c:if test="${prm.type == 4}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/project/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">已删除</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a class="btn btn-link querybox-toggle ${prm.type == 10 ? 'querybox-opened':''}" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索
							<c:if test="${prm.type == 10}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>	
						</a>
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="team/project/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i> 新建项目</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator ${prm.type == 10 ? 'show':''}" id="queryBox">
							<form method="post" action="team/project/index?type=10" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td style="width:150px">
												<select class="form-control chosen chosen-select" name="searchType" id="searchType">
													<option ${prm.searchType=='1'?'selected="selected"':'' } value="1">项目名称/ID</option>
													<option ${prm.searchType=='2'?'selected="selected"':'' } value="2">项目备注</option>
												</select>
											</td>
											<td style="width:500px">
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control  searchInput" placeholder="选择后请输入要查询的项目名称/ID 或 备注">
											</td>
											<td style="width:150px">
												<select class="form-control chosen chosen-select" name="company" id="company">
													<option value="">公司</option>
													<option ${prm.company=='景安'?'selected="selected"':'' } value="景安">景安</option>
													<option ${prm.company=='快云'?'selected="selected"':'' } value="快云">快云</option>
													<option ${prm.company=='大数据'?'selected="selected"':'' } value="大数据">大数据</option>
												</select>
											</td>
											<td style="width:250px">
												<select data-placeholder="请选择项目负责人" class="form-control chosen-select" name="member_id" id="member_id">
													<option value=""></option>
													<c:forEach items="${members}" var="member" varStatus="sta">
														<option value="${member.id}">${member.name}(${member.number})</option>
													</c:forEach>
												</select>
											</td>
											<td style="width:100px">
												<select class="form-control chosen chosen-select" name="state" id="state">
													<option value="">状态</option>
													<option ${prm.bugrank=='0'?'selected="selected"':'' } value="0">无效</option>
													<option ${prm.bugrank=='1'?'selected="selected"':'' } value="1">正常</option>
													<option ${prm.bugrank=='0'?'selected="selected"':'' } value="2">待验收</option>
													<option ${prm.bugrank=='1'?'selected="selected"':'' } value="3">已验收</option>
													<option ${prm.bugrank=='0'?'selected="selected"':'' } value="4">已完成</option> 
												</select>
											</td>
											
											<td class="w-160px">
												<input type="text" name="createtime" id="createtime" value="${prm.createtime}" class="form-control form-date" placeholder="开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;">
											</td>
											<td class="w-160px">
												<input type="text" name="endtime" id="endtime" value="${prm.endtime}" class="form-control form-date" placeholder="结束时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;">
											</td>
										</tr>
										<tr>
											<td colspan="8" class="text-center form-actions">
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
								<table class="table has-sort-head" id="taskList"
									data-fixed-left-width="550" data-fixed-right-width="160">
									<thead>
										<tr>
											<th data-flex="false" data-width="90px" style="width: 90px" class="c-id " title="ID">
												<a class="${prm.orderColumn=='id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('id');">ID</a>
											</th>
											<th data-flex="false" data-width="50px" style="width: 130px" class="c-pri " title="项目名称">
												<a class="${prm.orderColumn=='project_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('project_name');">项目名称</a>
											</th>
											<th data-flex="false" data-width="90px" style="width: 90px" class="c-id " title="类型">
												<a class="${prm.orderColumn=='project_type'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('project_type');">类型</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name " title="所属公司">
												<a class="${prm.orderColumn=='company'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('company');">所属公司</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:120px" class="c-pri " title="项目负责人">
												<a class="${prm.orderColumn=='member_id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('member_id');">项目负责人</a>
											</th>
											<th data-flex="false" data-width="50px" style="width: 200px" class="c-pri " title="备注">
												<a class="${prm.orderColumn=='remark'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('remark');">备注</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:160px" class="c-name text-center" title="状态">
												<a class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('state');">状态</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:150px" class="c-name text-center" title="开始时间">
												<a class="${prm.orderColumn=='start_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('start_time');">开始时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:150px" class="c-name text-center" title="结束时间">
												<a class="${prm.orderColumn=='end_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('end_time');">结束时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:150px" class="c-name text-center" title="项目创建时间">
												<a class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('create_time');">项目创建时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:70px" class="c-name text-center" title="需求数">
												<a class="${prm.orderColumn=='needCount'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('needCount');">需求数</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:70px" class="c-name text-center" title="任务数">
												<a class="${prm.orderColumn=='taskCount'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('taskCount');">任务数</a>
											</th>
											<th data-flex="false" data-width="160px" style="width:200px" class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="project" varStatus="sta">
										<tr>
											<td class="c-id cell-id">${project.id}</td>
											<td class="text-left">
												<a href="team/project/detail?id=${project.id}" data-toggle="tooltip" data-placement="top" title="${project.project_name}">${project.project_name}</a>
											</td>
											<td class="c-pri text-left">
											<c:if test="${project.project_type == 0}">内部项目</c:if>
											<c:if test="${project.project_type == 1}">外部项目</c:if>
											</td>
											<td class="c-name text-left">${project.company}</td>
											<td class="c-pri text-left">${project.member_name}</td>
											<td class="c-pri text-left">${project.remark}</td>
											<td class="c-assignedTo has-btn text-center">
												${project.state == 0 ? '已删除' : project.state == 1 ? '正常' :
												  project.state == 2 ? '项目待验收' : project.state == 3 ? '项目已验收' :
												  project.state == 4 ? '项目已完成' : project.state == 5 ? '已拆分模块' :
												  project.state == 6 ? '里程碑待确认' : project.state == 7 ? '里程碑待验收' :
												  project.state == 8 ? '里程碑及概要设计已确认' : project.state == 9 ? '待测试' :
												  project.state == 10 ? '已完成测试' : project.state == 11 ? '里程碑已验收' :
												  project.state == 12 ? '里程碑报告待验收' : project.state == 13 ? '里程碑报告已验收' :
												  project.state == 14 ? '验收不通过' :
												  '未知'}
											</td>
											<td class="c-pri text-center" />${project.start_time}</td>
											<td class="c-pri text-center" />${project.end_time}</td>
											<td class="c-pri text-center"><fmt:formatDate value="${project.create_time}" pattern="yyyy-MM-dd" /></td>
											<td class="c-pri text-center"><a href="team/need/index?type=96&project_id=${project.id}">${project.needCount}</a></td>
											<td class="c-pri text-center"><a href="team/task/index?type=96&project_id=${project.id}">${project.taskCount}</a></td>
											<td class="c-actions text-center">
											    <c:if test="${project.state == '1'}">
													<a href="team/project/toedit?id=${project.id}" class="btn" title="编辑项目"><i class="icon-common-edit icon-edit"></i></a>
												    <a href="javascript:void(0)" onclick="del(${project.id})" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>	
												</c:if>
												<c:if test="${project.state == '5' }">
													<a href="test/milepost/toBatchAdd?project_id=${project.id}" class="btn" title="填写里程碑"><i class="icon-story-change icon-fork"></i></a>
												</c:if>
												<c:if test="${project.state == '6' }">
													<a href="test/milepost/tosure?project_id=${project.id}" class="btn" title="确认里程碑"><i class="icon-story-change icon-fork"></i></a>
												</c:if>
												<c:if test="${project.state == '7' }">
													<a href="test/milepost/tosureui?project_id=${project.id}" class="btn" title="确认里程碑及概要设计"><i class="icon-story-change icon-fork"></i></a>
												</c:if>
												<c:if test="${project.state == '8' }">
													<a href="test/apply/toAdd?project_id=${project.id}" class="btn" title="提交测试"><i class="icon-story-change icon-fork"></i></a>
												</c:if>
												<c:if test="${project.state == '10' }">
													<a href="test/milepost/manage?type=3&project_id=${project.id}" class="btn" title="验收里程碑"><i class="icon icon-sitemap"></i></a>
												</c:if>
												<c:if test="${project.state == '11' }">
													<a href="test/milepost/manage?type=4&project_id=${project.id}" class="btn" title="编写里程碑报告"><i class="icon-task-close icon-off"></i></a>
												</c:if>
												<c:if test="${project.state == '12' }">
													<a href="test/milepost/manage?type=5&project_id=${project.id}" class="btn" title="验收里程碑报告"><i class="icon icon-sitemap"></i></a>
												</c:if>
												<c:if test="${project.state == '13' }">
													<a href="team/project/toEditReport?id=${project.id}" class="btn" title="编写项目验收报告"><i class="icon-testreport-browse icon-flag"></i></a>
												</c:if>
												<c:if test="${project.state == '2' }">
													<a href="team/project/toCheck?id=${project.id}" class="btn" title="验收项目"><i class="icon-story-review icon-glasses"></i></a>
												</c:if>
												<c:if test="${project.state == '3' }">
													<a href="team/project/toFinish?id=${project.id}" class="btn" title="确认项目完成"><i class="icon-task-finish icon-checked"></i></a>
												</c:if>
												<c:if test="${project.state == '1' || project.state == '14'}">
													<a href="team/need/toaddneed?project_id=${project.id}" class="btn" title="提需求"><i class="icon icon-plus"></i></a>
												</c:if>
												<c:if test="${project.state == '1' || project.state == '14' || project.state == '5' || project.state == '6' || project.state == '7'}">
													<a href="team/need/toEachAdd?project_id=${project.id}" class="btn" title="拆分模块"><i class="icon-task-batchCreate icon-branch"></i></a>
												</c:if>
												
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
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
			<script></script>
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
	function del(id){
		if(confirm("确认删除？")){
			$.ajaxSettings.async = false;
			$.getJSON("team/project/del?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
</script>