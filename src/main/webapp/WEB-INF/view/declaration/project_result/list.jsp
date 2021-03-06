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
						<a href="declaration/result/index?type=11" class="btn btn-link ${prm.type == 11 ? 'btn-active-text':''}">
							<span class="text">待撰写</span>
							<c:if test="${prm.type == 11}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=12" class="btn btn-link ${prm.type == 12 ? 'btn-active-text':''}">
							<span class="text">撰写中</span>
							<c:if test="${prm.type == 12}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=18" class="btn btn-link ${prm.type == 18 ? 'btn-active-text':''}">
							<span class="text">受理通知书</span>
							<c:if test="${prm.type == 18}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<a href="declaration/result/index?type=19" class="btn btn-link ${prm.type == 19 ? 'btn-active-text':''}">
							<span class="text">已下证</span>
							<c:if test="${prm.type == 19}">
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
						<div class="btn-group">
							<a href="javascript:;" data-toggle="dropdown" class="btn btn-link btn-active-text" style="border-radius: 2px;">
								<c:if test="${prm.type > 12 && prm.type < 18}">
									<span class="text">${prm.type == 13 ? '已撰写': prm.type == 14 ? '已提综管': prm.type == 15 ? '已提代理': prm.type == 16 ? '代理受理': prm.type == 17 ? '代理完成': ''}</span>
									<span class="label label-light label-badge">${pageList.totalCounts}</span>
								</c:if>
								<c:if test="${prm.type < 13 || prm.type > 17}">
									更多
								</c:if>
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
								<li ${prm.type == 13 ? 'class="active"':''}><a href="declaration/result/index?type=13">已撰写</a></li>
								<li ${prm.type == 14 ? 'class="active"':''}><a href="declaration/result/index?type=14">已提综管</a></li>
								<li ${prm.type == 15 ? 'class="active"':''}><a href="declaration/result/index?type=15">已提代理</a></li>
								<li ${prm.type == 16 ? 'class="active"':''}><a href="declaration/result/index?type=16">代理受理</a></li>
								<li ${prm.type == 17 ? 'class="active"':''}><a href="declaration/result/index?type=17">代理完成</a></li>
							</ul>
						</div>
						<a class="btn btn-link querybox-toggle ${prm.type == 10 ? 'querybox-opened':''}" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索
							<c:if test="${prm.type == 10}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
						<!-- <input type="text"/> --> 
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="declaration/result/toAdd" class="btn btn-primary"><i class="icon icon-plus"></i> 添加成果</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator ${prm.type == 10 ? 'show':''}" id="queryBox">
							<form method="post" action="declaration/result/index?type=10" id="searchForm" name="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td class="w-180px">
												<select class="form-control chosen chosen-select" name="nametype" id="nametype">
													<option ${prm.nametype=='1'?'selected="selected"':'' } value="1">成果名称/撰写人</option>
													<option ${prm.nametype=='2'?'selected="selected"':'' } value="2">申请/专利/登记号</option>
													<option ${prm.nametype=='3'?'selected="selected"':'' } value="3">证书号</option>
												</select>
											</td>
											<td colspan="3">
												<input type="text" name="search" id="search" value="${prm.search}" class="form-control  searchInput" placeholder="请输入要查询的成果名称/撰写人 或 申请号/登记号/专利号 或 证书号">
											</td>
											<td class="w-180px">
												<select class="form-control chosen chosen-select" name="datetype" id="datetype">
													<option value="">请选择日期</option>
													<option ${prm.datetype=='1'?'selected="selected"':'' } value="1">申请日期</option>
													<option ${prm.datetype=='2'?'selected="selected"':'' } value="2">受理日期</option>
													<option ${prm.datetype=='3'?'selected="selected"':'' } value="3">下证日期</option>
												</select>
											</td>
											<td class="w-180px">
												<input type="text" name="start_date" id="start_date" value="${prm.start_date}" class="form-control form-date" placeholder="开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
											<td class="w-180px">
												<input type="text" name="end_date" id="end_date" value="${prm.end_date}" class="form-control form-date" placeholder="结束时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
											</td>
										</tr>
										<tr>
											<td>
												<select class="form-control chosen chosen-select" name="company" id="company">
													<option value="">请选择公司</option>
													<option ${prm.company=='景安'?'selected="selected"':'' } value="景安">景安</option>
													<option ${prm.company=='快云'?'selected="selected"':'' } value="快云">快云</option>
													<option ${prm.company=='绿林客'?'selected="selected"':'' } value="绿林客">绿林客</option>
													<option ${prm.company=='大数据'?'selected="selected"':'' } value="大数据">大数据</option>
												</select>
											</td>
											<td>
												<select class="form-control chosen chosen-select" name="result_type" id="result_type">
													<option value="">请选择类型</option>
													<option ${prm.result_type=='1'?'selected="selected"':'' } value="1">软著</option>
													<option ${prm.result_type=='2'?'selected="selected"':'' } value="2">发明专利</option>
													<option ${prm.result_type=='3'?'selected="selected"':'' } value="3">实用新型专利</option>
													<option ${prm.result_type=='4'?'selected="selected"':'' } value="4">外观专利</option>
													<option ${prm.result_type=='5'?'selected="selected"':'' } value="5">商标</option>
												</select>
											</td>
											<td>
												<select class="form-control chosen chosen-select" name="state" id="state">
													<option value="">请选择状态</option>
													<option ${prm.state=='1'?'selected="selected"':'' } value="1">待撰写</option>
													<option ${prm.state=='2'?'selected="selected"':'' } value="2">撰写中</option>
													<option ${prm.state=='3'?'selected="selected"':'' } value="3">已撰写</option>
													<option ${prm.state=='4'?'selected="selected"':'' } value="4">已提综管</option>
													<option ${prm.state=='5'?'selected="selected"':'' } value="5">已提代理</option>
													<option ${prm.state=='6'?'selected="selected"':'' } value="6">代理受理</option>
													<option ${prm.state=='7'?'selected="selected"':'' } value="7">代理完成</option>
													<option ${prm.state=='8'?'selected="selected"':'' } value="8">受理通知书</option>
													<option ${prm.state=='9'?'selected="selected"':'' } value="9">已下证</option>
													<option ${prm.state=='0'?'selected="selected"':'' } value="0">已删除</option>
												</select>
											</td>
											<td>
												<select class="form-control chosen chosen-select" name="payment" id="payment">
													<option value="">请选择付款状态</option>
													<option ${prm.payment=='1'?'selected="selected"':'' } value="1">未付款：未向财务提交付款申请</option>
													<option ${prm.payment=='2'?'selected="selected"':'' } value="2">待付款：已向财务提交付款申请</option>
													<option ${prm.payment=='3'?'selected="selected"':'' } value="3">已付款：财务已向代理商付款</option>
												</select>
											</td>
											<td>
												<select class="form-control chosen chosen-select" name="invoice" id="invoice">
													<option value="">请选择发票状态</option>
													<option ${prm.invoice=='1'?'selected="selected"':'' } value="1">未开发票</option>
													<option ${prm.invoice=='2'?'selected="selected"':'' } value="2">已开发票</option>
												</select>
											</td>
											<td>
												<select class="form-control chosen chosen-select" name="receipt" id="receipt">
													<option value="">请选择收据状态</option>
													<option ${prm.receipt=='1'?'selected="selected"':'' } value="1">已开收据</option>
													<option ${prm.receipt=='2'?'selected="selected"':'' } value="2">未开收据</option>
												</select>
											</td>
											<td>
												<select class="form-control chosen chosen-select" name="is_all_doc" id="is_all_doc">
													<option value="">请选择是否全文档</option>
													<option ${prm.is_all_doc=='1'?'selected="selected"':'' } value="1">否</option>
													<option ${prm.is_all_doc=='2'?'selected="selected"':'' } value="2">是</option>
												</select>
											</td>
										</tr>
										<tr>
											<td colspan="7" class="text-center form-actions">
												<button id="submit" class="btn btn-wide btn-primary" 
														data-loading="稍候..." onclick="javascript:document.searchForm.action='declaration/result/index?type=10';document.searchForm.submit();">搜索</button>
												<button id="exportResult" class="btn btn-wide btn-primary" 
														data-loading="稍候..." onclick="javascript:document.searchForm.action='declaration/result/exportResult?type=10';document.searchForm.submit();">导出</button>
											</td>
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
												<a class="${prm.orderColumn=='pr.id'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('pr.id');">ID</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name" title="登记号">
												<a class="${prm.orderColumn=='registration_number'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('registration_number');">登记号</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:auto" class="c-name " title="项目成果名称">
												<a class="${prm.orderColumn=='pr.project_result_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('pr.project_result_name');">成果名称 </a>
											</th>
											<th data-flex="false" data-width="50px" style="width:70px" class="c-pri " title="所属项目">
												<a class="${prm.orderColumn=='p.project_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('p.project_name');">项目ID</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" title="成果类型">
												<a class="${prm.orderColumn=='pr.type'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('pr.type');">成果类型</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:80px" class="c-name text-center" title="撰写人">
												<a class="${prm.orderColumn=='member_name'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('member_name');">撰写人</a>
											</th>
											<th data-flex="false" data-width="100px" style="width:90px" class="c-name text-center" title="申请日期">
												<a class="${prm.orderColumn=='apply_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('apply_date');">申请日期</a>
											</th>
											<th data-flex="false" data-width="100px" style="width:90px" class="c-name text-center" title="受理日期">
												<a class="${prm.orderColumn=='accept_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('accept_date');">受理日期</a>
											</th>
											<th data-flex="false" data-width="100px" style="width:90px" class="c-name text-center" title="下证日期">
												<a class="${prm.orderColumn=='down_date'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('down_date');">下证日期</a>
											</th>
											<th data-flex="false" data-width="auto" style="width:60px" class="c-name text-center" title="所属公司">
												<a class="${prm.orderColumn=='company'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('company');">公司</a>
											</th>
											<th data-flex="false" data-width="100px" style="width:95px" class="c-name text-center" title="状态">
												<a class="${prm.orderColumn=='pr.state'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('pr.state');">状态</a>
											</th>
											<th data-flex="false" data-width="100px" style="width:60px" class="c-name text-center" title="代理公司开具的收据">
												<a class="${prm.orderColumn=='pr.invoice'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('pr.invoice');">发票</a>
											</th>
											<th data-flex="false" data-width="100px" style="width:60px" class="c-name text-center" title="国家知识产权局开具的收据">
												<a class="${prm.orderColumn=='pr.receipt'?(prm.orderByValue=='DESC'?'sort-down':'sort-up'):'header'}"
													href="javascript:void(0)" onclick="pageOrder('pr.receipt');">收据</a>
											</th>
											<th data-flex="false" data-width="150px" style="width:110px"class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="result" varStatus="sta">
										<tr>
											<td class="c-id cell-id">${result.id}</td>
											<td class="c-assignedTo has-btn">${result.registration_number}</td>
											<td class="text-left" title="${result.project_result_name}">
												<c:if test="${result.payment == 1}">
													<span class="label label-danger" data-toggle="tooltip" data-placement="top" title="付款状态：未向财务提交付款申请">未付款</span>
												</c:if>
												<c:if test="${result.payment == 2}">
													<span class="label label-info" data-toggle="tooltip" data-placement="top" title="付款状态：已向财务提交付款申请">待付款</span>
												</c:if>
												<c:if test="${result.payment == 3}">
													<span class="label label-success" data-toggle="tooltip" data-placement="top" title="付款状态：财务已向代理商付款">已付款</span>
												</c:if>
												<a href="declaration/result/detail?id=${result.id}">${result.project_result_name}</a>
												<c:if test="${result.is_all_doc == 2}">
													<span class="label label-badge label-success" data-toggle="tooltip" data-placement="top" title="相关文档已全部提供">全</span>
												</c:if>
											</td>
											<td class="text-center">
												<c:if test="${result.project_name == null}">
													无
												</c:if>
												<c:if test="${result.project_name != null}">
													<a href="declaration/project/detail?id=${result.project_id}" data-toggle="tooltip" data-placement="top" title="${result.project_name}">${result.project_id}</a>
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
											<td class="c-assignedTo has-btn text-center">
												<span class="${(result.state == 1 || result.state == 2 || result.state == 3) ? 'status-wait' : 
														(result.state == 4 || result.state == 5) ? 'status-doing' : 
														(result.state == 6 || result.state == 7) ? 'status-pause' : 
														result.state == 8 ? 'status-postpone' : 
														result.state == 9 ? 'status-done' : 'status-cancel'}">
													<span class="label label-dot"></span>
													${result.state == 1 ? '待撰写' : result.state == 2 ? '撰写中' : result.state == 3 ? '已撰写' : result.state == 4 ? '已提综管' : result.state == 5 ? '已提代理' : 
													result.state == 6 ? '代理受理' : result.state == 7 ? '代理完成' : result.state == 8 ? '受理通知书' : result.state == 9 ? '已下证' : result.state == 0 ? '已删除' : '未知'}
												</span>
											</td>
											<td class="c-assignedTo has-btn text-center">
												${result.invoice == 1 ? '<span class="label label-dot label-danger"></span> 未开' : result.invoice == 2 ? '<span class="label label-dot label-success"></span> 已开' : '未知'}
											</td>
											<td class="c-assignedTo has-btn text-center">
												${result.receipt == 1 ? '<span class="label label-dot label-danger"></span> 未开' : result.receipt == 2 ? '<span class="label label-dot label-success"></span> 已开' : '未知'}
											</td>
											<td class="c-actions text-center">
												<c:if test="${result.state>0 && result.state<10}">
													<a href="declaration/result/toAdd?id=${result.id}" class="btn" title="修改">修改</a>
													<a href="javascript:void(0)" onclick="del(${result.id})" class="btn" title="删除">删除</a>
												</c:if>
												<c:if test="${result.state<1 || result.state>9}">--</c:if>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!--table-responsive end-->
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