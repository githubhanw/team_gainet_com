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
		<title>产品列表</title>
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
						<a href="team/product/index?type=5" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 5}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/product/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">正常</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/product/index?type=2" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">待验收</span>
							<c:if test="${prm.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/product/index?type=3" class="btn btn-link ${prm.type == 3 ? 'btn-active-text':''}">
							<span class="text">已验收</span>
							<c:if test="${prm.type == 3}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/product/index?type=4" class="btn btn-link ${prm.type == 4 ? 'btn-active-text':''}">
							<span class="text">已完成</span>
							<c:if test="${prm.type == 4}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="team/product/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">无效</span>
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
						<a href="team/product/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i> 新建产品</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator ${prm.type == 10 ? 'show':''}" id="queryBox">
							<form method="post" action="team/product/index?type=10" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td style="width:150px">
												<select class="form-control chosen chosen-select" name="searchType" id="searchType">
													<option ${prm.searchType=='1'?'selected="selected"':'' } value="1">产品名称/ID</option>
													<option ${prm.searchType=='2'?'selected="selected"':'' } value="2">产品备注</option>
												</select>
											</td>
											<td style="width:500px">
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control  searchInput" placeholder="选择后请输入要查询的产品名称/ID 或 备注">
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
												<select data-placeholder="请选择产品负责人" class="form-control chosen-select" name="member_id" id="member_id">
													<option value=""></option>
													<c:forEach items="${members}" var="member" varStatus="sta">
														<option value="${member.id}">${member.name}(${member.number})</option>
													</c:forEach>
												</select>
											</td>
											<td style="width:200px">
												<select class="form-control chosen chosen-select" name="state" id="state">
													<option value="">状态</option>
													<option ${prm.state=='0'?'selected="selected"':'' } value="0">无效</option>
													<option ${prm.state=='1'?'selected="selected"':'' } value="1">正常</option>
													<option ${prm.state=='2'?'selected="selected"':'' } value="2">待验收</option>
													<option ${prm.state=='3'?'selected="selected"':'' } value="3">产品已验收</option>
													<option ${prm.state=='4'?'selected="selected"':'' } value="4">产品已完成</option>
													<option ${prm.state=='5'?'selected="selected"':'' } value="5">已拆分模块</option>
													<option ${prm.state=='6'?'selected="selected"':'' } value="6">待测试</option>
													<option ${prm.state=='7'?'selected="selected"':'' } value="7">已完成测试</option>
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
							id="productTaskForm" data-ride="table">
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
											<th data-flex="false" data-width="50px" style="width: auto" class="c-pri " title="产品名称">
												<a class="${prm.orderColumn=='product_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('product_name');">产品名称</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name " title="所属公司">
												<a class="${prm.orderColumn=='company'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('company');">所属公司</a>
											</th>
											<th data-flex="false" data-width="50px" style="width:120px" class="c-pri " title="产品负责人">
												<a class="${prm.orderColumn=='member_id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('member_id');">产品负责人</a>
											</th>
											<th data-flex="false" data-width="50px" style="width: auto" class="c-pri " title="备注">
												<a class="${prm.orderColumn=='remark'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('remark');">备注</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="状态">
												<a class="${prm.orderColumn=='state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('state');">状态</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:150px" class="c-name text-center" title="记录创建时间">
												<a class="${prm.orderColumn=='create_time'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('create_time');">记录创建时间</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:70px" class="c-name text-center" title="模块数">
												<a class="${prm.orderColumn=='needCount'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('needCount');">模块数</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:70px" class="c-name text-center" title="任务数">
												<a class="${prm.orderColumn=='taskCount'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('taskCount');">任务数</a>
											</th>
											<th data-flex="false" data-width="160px" style="width:230px" class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="product" varStatus="sta">
										<tr>
											<td class="c-id cell-id">${product.id}</td>
											<td class="text-left">
												<a href="team/product/detail?id=${product.id}" data-toggle="tooltip" data-placement="top" title="${product.product_name}">${product.product_name}</a>
											</td>
											<td class="c-name text-left">${product.company}</td>
											<td class="c-pri text-left">${product.member_name}</td>
											<td class="c-name text-left">${product.remark}</td>
											<td class="c-assignedTo has-btn text-center">
												${product.state == 0 ? '已删除' : product.state == 1 ? '正常' :
												  product.state == 2 ? '产品待验收' : product.state == 3 ? '产品已验收' :
												  product.state == 4 ? '产品已完成' : product.state == 5 ? '已拆分模块' : 
												  product.state == 6 ? '待测试' : product.state == 7 ? '已完成测试' : '未知'}
											</td>
											<td class="c-pri text-center"><fmt:formatDate value="${product.create_time}" pattern="yyyy-MM-dd" /></td>
											<td class="c-pri text-center"><a href="team/need/index?type=96&product_id=${product.id}">${product.needCount}</a></td>
											<td class="c-pri text-center"><a href="team/task/index?type=96&product_id=${product.id}">${product.taskCount}</a></td>
											<td class="c-actions text-center">
												<c:if test="${product.state == '5' }">
													<a href="test/apply/toAdd?product_id=${product.id}" class="btn" title="提交测试"><i class="icon-story-change icon-fork"></i></a>
												</c:if>
												<c:if test="${product.state == '7' }">
													<a href="team/product/toEditReport?id=${product.id}" class="btn" title="编写产品验收报告"><i class="icon-testreport-browse icon-flag"></i></a>
												</c:if>
												<c:if test="${product.state == '2' }">
													<a href="team/product/toCheck?id=${product.id}" class="btn" title="验收产品"><i class="icon-story-review icon-glasses"></i></a>
												</c:if>
												<c:if test="${product.state == '3' }">
													<a href="team/product/toFinish?id=${product.id}" class="btn" title="确认产品完成"><i class="icon-task-finish icon-checked"></i></a>
												</c:if>
												<a href="team/need/toaddproduct?product_id=${product.id}" class="btn" title="提需求"><i class="icon icon-plus"></i> 提需求</a>
												<a href="team/product/toedit?id=${product.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
												<c:if test="${product.state == '1'}"> 
													<a href="javascript:void(0)" onclick="del(${product.id})" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>
												</c:if>	
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</form>
						<!--table-responsive end-->
						<!--table-footer start-->
						<div class="table-footer" style="left: 0px; bottom: 0px;">
							<!--pager srtart-->
							<jsp:include page="/WEB-INF/view/comm/pagebar_conut.jsp"></jsp:include>
							<!-- <ul class="pager"></ul> -->
							<!--pager end-->
						</div>
						<!--table-footer end-->
					</div>
				</div>
				<!--main-col end-->
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
	function del(id){
		if(confirm("确认删除？")){
			$.ajaxSettings.async = false;
			$.getJSON("team/product/del?id=" + id + "&r=" + Math.random(), function(data) {
				alert(data.message);
				if(data.code == 0){
					window.location.reload();
				}
			});
			$.ajaxSettings.async = true;
		}
	}
</script>