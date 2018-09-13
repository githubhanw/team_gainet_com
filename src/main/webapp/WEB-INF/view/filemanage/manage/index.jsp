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
		<title>文档列表</title>
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
				<div id="mainMenu" class="cleaallfilerfix">
					<div class="btn-toolbar pull-left">
						<a class="btn btn-link querybox-toggle ${prm.type == 10 ? 'querybox-opened':''}" id="bysearchTab"><i class="icon icon-search muted"></i> 搜索
							<c:if test="${prm.type == 10}">
								<span class="label label-light label-badge">${pageList.totalCounts}</span>
							</c:if>
						</a>
					</div>
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="filemanage/manage/toaddwd" class="btn btn-primary"><i class="icon icon-plus"></i>创建文档</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
					<div class="cell load-indicator ${prm.type == 10 ? 'show':''}" id="queryBox">
							<form method="post" action="filemanage/manage/index?type=10" id="searchForm" class="search-form">
								<table class="table table-condensed table-form" id="task-search">
									<tbody>
										<tr>
											<td class="w-150px">
												<select class="form-control chosen chosen-select" name="accessControl" id="accessControl">
													<option value="">状态</option>
													<option value="0">删除</option>
													<option value="1">正常</option>
												</select>
											</td>
											<td class="w-200px">
												<input type="text" name="startTime" id="startTime" value="" class="form-control form-date" placeholder="开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;">
											</td>
											<td class="w-150px">
												<select data-placeholder="请选择人员" class="form-control chosen-select" name="authorId" id="authorId">
													<option value=""></option>
													<c:forEach items="${members}" var="member" varStatus="sta">
														<option value="${member.id}">${member.name}(${member.number})</option>
													</c:forEach>
												</select>
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
											<th data-flex="false" data-width="90px" style="width: 50px" class="c-id text-center" title="ID">
											ID
											</th>
											<th data-flex="false" data-width="150px" style="width: 123px" class="c-pri text-center" title="名称">
											标题
											</th>
										    <th data-flex="false" data-width="50px" style="width: 65px" class="c-pri" title="名称">
											关联类型
											</th>
											<!--  <th data-flex="false" data-width="50px" style="width:120px" class="c-name text-center" title="描述">
											关联名称
											</th>-->
											<th data-flex="false" data-width="auto" style="width:92px" class="c-name" title="创建时间">
											创建时间
											</th>
											<th data-flex="false" data-width="50px" style="width:69px" class="c-name" title="修改时间">
											修改时间
											</th>
											<th data-flex="false" data-width="50px" style="width:90px" class="c-pri text-center" title="文件原名">
											文件原名
											</th>
											<th data-flex="false" data-width="50px" style="width:120px" class="c-pri text-center" title="创建者">
											创建者
											</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-pri text-center" title="状态">
											状态
											</th>
											<th data-flex="false" data-width="300px" style="width:160px" class="c-actions text-center" title="操作">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pageList.pageResult}" var="fil" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${fil.id}</td>
											<td class="c-pri text-center">
										    <a href="filemanage/manage/mydetail?id=${fil.id}" title="${fil.file_name}">
											         <c:if test="${fn:length(fil.file_name)>10 }">
										                         ${fn:substring(fil.file_name, 0, 10)}...
										             </c:if>
										             <c:if test="${fn:length(fil.file_name)<=10}">
										                         ${fil.file_name}
										             </c:if> 
										    </a>
											
											</td>
											<td class="c-name text-left">
											<c:if test="${fil.file_classification == 0}">项目</c:if>
											<c:if test="${fil.file_classification == 1}">需求</c:if>
											</td>
											<!--<td class="c-id cell-id text-center">
											     <a href="filemanage/manage/mydetail?id=${fil.id}" title="${fil.gl_name}">
											         <c:if test="${fn:length(fil.gl_name)>7 }">
										                         ${fn:substring(fil.gl_name, 0, 7)}...
										             </c:if>
										             <c:if test="${fn:length(fil.gl_name)<=7}">
										                         ${fil.gl_name}
										             </c:if> 
										         </a>
											</td> -->
											<td class="c-name text-center">${fil.create_time}</td>
											<td class="c-name text-center">${fil.edit_time}</td>
											<td class="c-pri text-center" title="${fil.file_realname}">
											<c:if test="${fn:length(fil.file_realname)>7 }">
										                         ${fn:substring(fil.file_realname, 0, 7)}...
										    </c:if>
										    <c:if test="${fn:length(fil.file_realname)<=7}">
										                         ${fil.file_realname}
										    </c:if> 
											</td>
											<td class="c-pri text-center">${fil.add_name}</td>
											<td class="c-pri text-center">
											<c:if test="${fil.access_control == 0}">已删除
							                </c:if>
							                <c:if test="${fil.access_control == 1}">正常
							                </c:if>
											</td>
											<td class="c-actions text-center">
											<c:if test="${fil.access_control == 1 }">
												<a href="filemanage/manage/tomyedit?id=${fil.id}" class="btn" title="编辑"><i class="icon-common-edit icon-edit"></i></a>
												<a href="javascript:void(0)" onclick="del(${fil.id})" class="btn" title="删除"><i class="icon-common-delete icon-trash"></i></a>
											</c:if>
											<c:if test="${fil.access_control == 0}">
											<strong>无法操作</strong>
											</c:if>
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
    	<%@ include file="/WEB-INF/view/team/need/div.jsp" %>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
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
	$('.pager').pager({
	    page: ${pageList.currentPage},
	    recTotal: ${pageList.totalCounts},
	    recPerPage: ${pageList.pageSize},
	    pageSizeOptions: [10, 20, 30, 50, 100],
	    lang: 'zh_cn',
	    linkCreator: "filemanage/manage/index?type=${prm.type}&currentPage={page}&pageSize={recPerPage}&search=${prm.search}&orderColumn=${prm.orderColumn}&orderByValue=${prm.orderByValue}"
	});
	function del(id){
			if(confirm("确认删除？")){
				$.ajaxSettings.async = false;
				$.getJSON("filemanage/manage/delete?id=" + id + "&r=" + Math.random(), function(data) {
					alert(data.message);
					if(data.code == 0){
						window.location.reload();
					}
				});
				$.ajaxSettings.async = true;
			}
	}
</script>
</html>