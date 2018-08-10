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
		<title>项目成果列表</title>
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
						<a href="declaration/result/index?type=2" class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}">
							<span class="text">所有</span>
							<c:if test="${prm.type == 2}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=1" class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}">
							<span class="text">正常</span>
							<c:if test="${prm.type == 1}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=0" class="btn btn-link ${prm.type == 0 ? 'btn-active-text':''}">
							<span class="text">已删除</span>
							<c:if test="${prm.type == 0}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=3" class="btn btn-link ${prm.type == 3 ? 'btn-active-text':''}">
							<span class="text">已下证</span>
							<c:if test="${prm.type == 3}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=4" class="btn btn-link ${prm.type == 4 ? 'btn-active-text':''}">
							<span class="text">待撰写</span>
							<c:if test="${prm.type == 4}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=5" class="btn btn-link ${prm.type == 5 ? 'btn-active-text':''}">
							<span class="text">撰写中</span>
							<c:if test="${prm.type == 5}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=6" class="btn btn-link ${prm.type == 6 ? 'btn-active-text':''}">
							<span class="text">已提交</span>
							<c:if test="${prm.type == 6}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=7" class="btn btn-link ${prm.type == 7 ? 'btn-active-text':''}">
							<span class="text">已受理</span>
							<c:if test="${prm.type == 7}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=8" class="btn btn-link ${prm.type == 8 ? 'btn-active-text':''}">
							<span class="text">软著</span>
							<c:if test="${prm.type == 8}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=9" class="btn btn-link ${prm.type == 9 ? 'btn-active-text':''}">
							<span class="text">专利</span>
							<c:if test="${prm.type == 9}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a class="btn btn-link querybox-toggle ${prm.type == 10 ? 'querybox-opened':''}" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索</a>
						<!-- <input type="text"/> --> 
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="declaration/result/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i>添加成果</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator ${prm.type == 10 ? 'show':''}" id="queryBox">
							<form method="post" action="declaration/result/index?type=10" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td class="w-180px">
												<select class="form-control chosen chosen-select" name="nametype" id="nametype">
													<option ${prm.nametype=='1'?'selected="selected"':'' } value="1">成果名称</option>
													<option ${prm.nametype=='2'?'selected="selected"':'' } value="2">申请/专利/登记号</option>
													<option ${prm.nametype=='3'?'selected="selected"':'' } value="3">证书号</option>
												</select>
											</td>
											<td>
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control  searchInput" placeholder="请输入要查询的成果名称 或 申请号/登记号/专利号 或 证书号">
											</td>
											<td class="w-150px">
												<select class="form-control chosen chosen-select" name="company" id="company">
													<option value="">请选择公司</option>
													<option ${prm.company=='景安'?'selected="selected"':'' } value="景安">景安</option>
													<option ${prm.company=='快云'?'selected="selected"':'' } value="快云">快云</option>
													<option ${prm.company=='绿林客'?'selected="selected"':'' } value="绿林客">绿林客</option>
													<option ${prm.company=='大数据'?'selected="selected"':'' } value="大数据">大数据</option>
												</select>
											</td>
											<td class="w-150px">
												<select class="form-control chosen chosen-select" name="result_type" id="result_type">
													<option value="">请选择类型</option>
													<option ${prm.result_type=='1'?'selected="selected"':'' } value="1">软著</option>
													<option ${prm.result_type=='2'?'selected="selected"':'' } value="2">发明专利</option>
													<option ${prm.result_type=='3'?'selected="selected"':'' } value="3">实用新型专利</option>
													<option ${prm.result_type=='4'?'selected="selected"':'' } value="4">外观专利</option>
													<option ${prm.result_type=='5'?'selected="selected"':'' } value="5">商标</option>
												</select>
											</td>
											<td class="w-150px">
												<select class="form-control chosen chosen-select" name="state" id="state">
													<option value="">请选择状态</option>
													<option ${prm.state=='1'?'selected="selected"':'' } value="1">待撰写</option>
													<option ${prm.state=='2'?'selected="selected"':'' } value="2">撰写中</option>
													<option ${prm.state=='3'?'selected="selected"':'' } value="3">已提交</option>
													<option ${prm.state=='4'?'selected="selected"':'' } value="4">已受理</option>
													<option ${prm.state=='5'?'selected="selected"':'' } value="5">已下证</option>
													<option ${prm.state=='0'?'selected="selected"':'' } value="0">已删除</option>
												</select>
											</td>
											<td class="w-140px">
												<select class="form-control chosen chosen-select" name="datetype" id="datetype">
													<option value="">请选择日期</option>
													<option ${prm.datetype=='1'?'selected="selected"':'' } value="1">申请日期</option>
													<option ${prm.datetype=='2'?'selected="selected"':'' } value="2">受理日期</option>
													<option ${prm.datetype=='3'?'selected="selected"':'' } value="3">下证日期</option>
												</select>
											</td>
											<td class="w-150px">
												<input type="text" name="start_date" id="start_date" valve="${prm.start_date}" class="form-control form-date" placeholder="开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
											<td class="w-150px">
												<input type="text" name="end_date" id="end_date" valve="${prm.end_date}" class="form-control form-date" placeholder="结束时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
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
						<form class="main-table table-task skip-iframe-modal" method="post" id="projectTaskForm" data-ride="table">
							<!--table-responsive start-->
							<div class="table-responsive">
								<table class="table has-sort-head" id="taskList"
									data-fixed-left-width="550" data-fixed-right-width="160">
									<thead>
										<tr>
											<th data-flex="false" data-width="70px" style="width: 70px" class="c-id " title="ID">
												<a href="${pageList.desAction}&orderColumn=pr.id&orderByValue=${prm.orderColumn=='pr.id'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='pr.id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">ID</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:150px" class="c-name" title="登记号">
												<a href="${pageList.desAction}&orderColumn=registration_number&orderByValue=${prm.orderColumn=='registration_number'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='registration_number'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">登记号</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:auto" class="c-name " title="项目成果名称">
												<a href="${pageList.desAction}&orderColumn=pr.project_result_name&orderByValue=${prm.orderColumn=='pr.project_result_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='pr.project_result_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"> 成果名称 </a>
											</th>
											<th data-flex="false" data-width="50px" style="width:auto" class="c-pri " title="所属项目">
												<a href="${pageList.desAction}&orderColumn=p.project_name&orderByValue=${prm.orderColumn=='p.project_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}"
														class="${prm.orderColumn=='p.project_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">所属项目</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="成果类型">
												<a href="${pageList.desAction}&orderColumn=pr.type&orderByValue=${prm.orderColumn=='pr.type'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='pr.type'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">成果类型</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:80px" class="c-name text-center" title="撰写人">
												<a href="${pageList.desAction}&orderColumn=member_name&orderByValue=${prm.orderColumn=='member_name'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='member_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">撰写人</a>
											</th>
											<th data-flex="false" data-width="100px" style="width:90px" class="c-name text-center" title="申请日期">
												<a href="${pageList.desAction}&orderColumn=apply_date&orderByValue=${prm.orderColumn=='apply_date'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='apply_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">申请日期</a>
											</th>
											<th data-flex="false" data-width="100px" style="width:90px" class="c-name text-center" title="受理日期">
												<a href="${pageList.desAction}&orderColumn=accept_date&orderByValue=${prm.orderColumn=='accept_date'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='accept_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">受理日期</a>
											</th>
											<th data-flex="false" data-width="100px" style="width:90px" class="c-name text-center" title="下证日期">
												<a href="${pageList.desAction}&orderColumn=down_date&orderByValue=${prm.orderColumn=='down_date'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='down_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">下证日期</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:90px" class="c-name text-center" title="所属公司">
												<a href="${pageList.desAction}&orderColumn=company&orderByValue=${prm.orderColumn=='company'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='company'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">所属公司</a>
											</th>
											<%-- <th data-flex="false" data-width="auto" style="width:80px" class="c-name text-center" title="知识产权代理商">
												<a href="${pageList.desAction}&orderColumn=agent&orderByValue=${prm.orderColumn=='agent'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='agent'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">代理商</a>
											</th> --%>
											<th data-flex="false" data-width="100px" style="width:80px" class="c-name text-center" title="状态">
												<a href="${pageList.desAction}&orderColumn=pr.state&orderByValue=${prm.orderColumn=='pr.state'&&prm.orderByValue=='DESC'?'ASC':'DESC'}" 
														class="${prm.orderColumn=='pr.state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}">状态</a>
											</th>
											<th data-flex="false" data-width="150px" style="width: 150px"
												class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="result" varStatus="sta">
										<tr>
											<td class="c-id cell-id">${result.id}</td>
											<td class="c-assignedTo has-btn">${result.registration_number}</td>
											<td class="c-pri text-left" title="${result.project_result_name}">
												<a href="declaration/result/detail?id=${result.id}">${result.project_result_name}</a>
											</td>
											<td class="c-pri text-left">
												<c:if test="${result.project_name == null}">
													暂无
												</c:if>
												<c:if test="${result.project_name != null}">
													<a href="declaration/project/detail?id=${result.project_id}" title="${result.project_name}">${result.project_name}</a>
												</c:if>
											</td>
											<td class="c-assignedTo has-btn text-center">${result.type == 1 ? '软著' : result.type == 2 ? '发明专利' : result.type == 3 ? '实用新型专利' : result.type == 4 ? '外观专利' : result.type == 5 ? '商标' : '未知'}</td>
											<td class="c-assignedTo has-btn text-center">${result.member_name}</td>
											<td class="c-assignedTo has-btn text-center">
												<c:if test="${result.apply_date == null}">
													暂无
												</c:if>
												<c:if test="${result.apply_date != null}">
													${result.apply_date}
												</c:if>
											</td>
											<td class="c-assignedTo has-btn text-center">
												<c:if test="${result.accept_date == null}">
													暂无
												</c:if>
												<c:if test="${result.accept_date != null}">
													${result.accept_date}
												</c:if>
											</td>
											<td class="c-assignedTo has-btn text-center">
												<c:if test="${result.down_date == null}">
													暂无
												</c:if>
												<c:if test="${result.down_date != null}">
													${result.down_date}
												</c:if>
											</td>
											<td class="c-assignedTo has-btn text-center">${result.company}</td>
											<%-- <td class="c-assignedTo has-btn text-center">${result.agent}</td> --%>
											<td class="c-assignedTo has-btn text-center">${result.state == 5 ? '已下证' : result.state == 1 ? '待撰写' : result.state == 2 ? '撰写中' : result.state == 3 ? '已提交' : result.state == 4 ? '已受理' : result.state == 0 ? '已删除' : '未知'}</td>
											<td class="c-actions text-center">
												<c:if test="${result.state>0 && result.state<6}">
													<a href="declaration/result/toAdd?id=${result.id}" class="btn" title="修改">修改</a>
													<a href="javascript:void(0)" onclick="del(${result.id})" class="btn" title="删除">删除</a>
												</c:if>
												<c:if test="${result.state<1 || result.state>5}">--</c:if>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!--table-responsive end-->
							<!--table-footer start-->
							<div class="table-footer" style="left: 0px; bottom: 0px;">
								<!--pager srtart-->
								<ul class="pager">
								</ul>
								<!--pager end-->
							</div>
							<!--table-footer end-->
						</form>
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
	$('.pager').pager({ 
	    page: ${pageList.currentPage},
	    recTotal: ${pageList.totalCounts},
	    recPerPage: ${pageList.pageSize},
	    pageSizeOptions: [10, 20, 30, 50, 100],
	    lang: 'zh_cn',
	    linkCreator: "declaration/result/index?type=${prm.type}&currentPage={page}&pageSize={recPerPage}&search=${prm.search}&orderColumn=${prm.orderColumn}&orderByValue=${prm.orderByValue}"
	});
	function del(id){
		$.ajaxSettings.async = false;
		$.getJSON("declaration/result/del?id=" + id + "&r=" + Math.random(), function(data) {
			alert(data.message);
			if(data.code == 0){
				window.location.reload();
			}
		});
		$.ajaxSettings.async = true;
	}
	$("#bysearchTab").click(function(){
		if($(this).hasClass("querybox-opened")){
			$(this).removeClass("querybox-opened")
			$("#queryBox").removeClass("show")
		}else{
			$(this).addClass("querybox-opened")
			$("#queryBox").addClass("show")
		}
	});
	$("a").bind("click", function () {
		var _this = $(this);
		if(!!_this.attr("method") && _this.attr("method") == "post") {
			var href = _this.attr("href");
			console.log(href);
			// TODO 发post请求
			
			return false; // 拦截原来的href请求
		}
	});
</script>