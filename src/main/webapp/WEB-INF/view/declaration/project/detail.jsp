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
		<title>项目：${projectM.project_name}</title>
    	<%@ include file="/WEB-INF/view/comm/cssjs.jsp" %>
	</head>
	<body>
	    <!--header start-->
	    <header id="header">
	    	<%@ include file="/WEB-INF/view/comm/main_header.jsp" %>
	    	<%@ include file="/WEB-INF/view/comm/sub_header.jsp" %>
	    </header>
	    <!--header end-->
		<main id="main">
			<div class="container">
				<div id="mainMenu" class="clearfix">
					<div class="btn-toolbar pull-left">
						<a href="javascript:history.go(-1);" class="btn btn-link"><i class="icon icon-back icon-sm"></i> 返回</a>
						<div class="divider"></div>
						<div class="page-title">
							<span class="label label-id">${projectM.id}</span> <span class="text">${projectM.project_name}</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">所有成果</div>
								<div class="detail-content article-content">
									<table class="table table-hover table-fixed">
										<thead>
											<tr class="text-center">
												<th>证书号</th>
												<th>登记号</th>
												<th class="w-300px">成果名称</th>
												<th>成果类型</th>
												<th>撰写人</th>
												<th>受理时间</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${pr}" var="item" varStatus="sta">
											<tr class="text-center">
												<td>${item.cert_number}</td>
												<td>${item.registration_number}</td>
												<td><a href="declaration/result/detail?id=${item.id}" title="${item.project_result_name }">${item.project_result_name }</a></td>
												<td>${item.type == 1 ? '软著' : item.type == 2 ? '发明专利' : item.type == 3 ? '实用新型专利' : item.type == 4 ? '外观专利' : item.type == 5 ? '商标' : '未知'}</td>
												<td>${item.member_name}</td>
												<td>${item.acceptance_date}</td>
												<td>${item.state == 5 ? '已下证' : item.state == 1 ? '待撰写' : item.state == 2 ? '撰写中' : item.state == 3 ? '已提交' : item.state == 4 ? '已受理' : item.state == 0 ? '已删除' : '未知'}</td>
												<td class="c-actions">
													<a
													href="declaration/result/toAdd?id=${item.id}"
													class="btn " title="编辑"><i
														class="icon-common-edit icon-edit"></i></a>
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<div class="detail">
								<div class="detail-title">所需文档</div>
								<div class="detail-content article-content">
									<table class="table table-hover table-fixed">
										<thead>
											<tr class="text-center">
												<th class="w-50px">编号</th>
												<th>文档名称</th>
												<th>文档类型</th>
												<th>文档地址</th>
												<th class="w-80px">文档状态</th>
												<th class="w-120px">预计提供时间</th>
												<th >操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${doc}" var="item" varStatus="sta">
											<tr class="text-center">
												<td>${item.id}</td>
												<td>${item.doc_name }</td>
												<td>${item.project_doc_type }</td>
												<td>${item.project_doc_url}</td>
												<td>${item.doc_state == 1 ? '已提供' : '未提供'}</td>
												<td>${item.provide_date}</td>
												<td class="c-actions">
													<a
													href="declaration/doc/toAdd?id=${item.id}"
													class="btn " title="编辑"><i
														class="icon-common-edit icon-edit"></i></a>
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
	                        <div class="detail histories" id="actionbox" data-textdiff="文本格式" data-original="原始格式">
                            <div class="detail-title">
									历史记录
	                                <button type="button" class="btn btn-mini btn-icon btn-expand-all" title="切换显示">
	                                    <i class="icon icon-plus icon-sm"></i>
	                                </button>
	                            </div>
                            <c:if test="${logList != null}">
	                            <div class="detail-content">
	                                <ol class="histories-list">
										<c:forEach items="${logList}" var="log" varStatus="sta">
											<li value="${sta.index+1}" class="">
												<fmt:formatDate value="${log.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/>,
												由 <strong>${log.member_name}</strong> ${log.method}。
												<c:if test="${log.history != null}">
													<button type="button" class="btn btn-mini switch-btn btn-icon btn-expand" title="切换显示"><i class="change-show icon icon-plus icon-sm"></i></button>
													<div class="history-changes" id="changeBox3">
														<c:forEach items="${log.history}" var="his" varStatus="sta">
															<c:if test="${his.diff == 0}">
																修改了 <strong><i>${his.field_desc}</i></strong>，旧值为 "${his.old_data}"，新值为 "${his.new_data}"。<br>
															</c:if>
															<c:if test="${his.diff == 1}">
																修改了 <strong><i>${his.field_desc}</i></strong>，区别为：
																<blockquote class="textdiff">
																	- <del>${his.old_data}</del><br/>+ <ins>${his.new_data}</ins>
																</blockquote>
															</c:if>
														</c:forEach>
													</div>
												</c:if>
												<c:if test="${log.comment != null && log.comment != ''}">
													<div class="article-content comment">${log.comment}</div>
												</c:if>
											</li>
										</c:forEach>
	                                </ol>
	                            </div>
	                        </c:if>
                        </div>
						</div>
					</div>
					<div class="side-col col-4">
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">基本信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr class="nofixed">
												<th>项目名称</th>
												<td title="/">${projectM.project_name}</td>
											</tr>
											<tr>
												<th>申报号</th>
												<td><a href="#">${projectM.declaration_number}</a></td>
											</tr>
											<tr>
												<th>负责人</th>
												<td>${projectM.leader_name}</td>
											</tr>
											<tr>
												<th>所属公司</th>
												<td>${projectM.company}</td>
											</tr>
											<tr>
												<th>阶段</th>
												<td>${projectM.stage}</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						<!-- <div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">工时信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>预计开始</th>
												<td>0000-00-00</td>
											</tr>
											<tr>
												<th>实际开始</th>
												<td>0000-00-00</td>
											</tr>
											<tr>
												<th>截止日期</th>
												<td>0000-00-00</td>
											</tr>
											<tr>
												<th>最初预计</th>
												<td>0工时</td>
											</tr>
											<tr>
												<th>总消耗</th>
												<td>0工时</td>
											</tr>
											<tr>
												<th>预计剩余</th>
												<td>0工时</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div> -->
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">项目的一生</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>由谁创建</th>
												<td>${projectM.leader_name } 于 <fmt:formatDate value="${projectM.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>开始日期</th>
												<td>${projectM.start_date }</td>
											</tr>
											<tr>
												<th>结束日期</th>
												<td>${projectM.end_date }</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
					</div>
				</div>
				<!--mainActions start-->
				<div id="mainActions">
					<nav class="container">
					</nav>
					<div class="btn-toolbar">
						<a href="javascript:history.go(-1);" id="back" class="btn" title="返回[快捷键:Alt+↑]">
							<i class="icon-goback icon-back"></i> 返回
						</a>
						<div class="divider"></div>
						<a href="declaration/result/toAdd?id=${projectM.id}" class="btn btn-link " title="编辑">
							<i class="icon-common-edit icon-edit"></i>
						</a>
					</div>
				</div>
				<!--mainActions end-->
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
	$("#submit").click(function() {
		var projectResultName = encodeURI(encodeURI($("#name").val()));
		var projectId = $("#projectId").val();
		$.ajaxSettings.async = false;
		$.ajax({
			type : "POST",
			url : "declaration/result/addOrUpd?r=" + Math.random(),
			data : $("form").serialize(),
			dataType : "json",
			success : function(data) {
				alert(data.message);
			}
		})
		/* $.getJSON("declaration/result/addOrUpd?r=" + Math.random(), $("form").serialize(), function(data) {
			alert(data.message);
		}); */
		$.ajaxSettings.async = true;
	});
</script>