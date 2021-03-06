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
					<!--btn-toolbar start-->
					<div class="btn-toolbar pull-right">
						<a href="team/need/toaddproject?project_id=${project_id}" class="btn btn-primary"><i class="icon icon-plus"></i> 新建模块</a>
					</div>
					<!--btn-toolbar end-->
				</div>
				<!--mainMenu end-->
				<div id="mainContent" class="main-row fade in">
					<!--main-col start-->
					<div class="main-col">
						<div class="cell load-indicator">
							<span  class="btn btn-wide btn-primary"  style="background-color: #00da88;" data-toggle="tooltip" data-placement="top">列表模块所属项目：</span>
							${project.project_name}
						</div>
						<form class="main-table table-task skip-iframe-modal" method="post"
							id="projectTaskForm" data-ride="table">
							<!--table-responsive start-->
							<div class="table-responsive">
								<table class="table has-sort-head" id="taskList"
									data-fixed-left-width="550" data-fixed-right-width="160">
									<thead>
										<tr>
											<th data-flex="false" data-width="90px" style="width:90px" class="c-id text-center" >ID</th>
											<th data-flex="false" data-width="50px" style="width:250px" class="c-pri" >模块名称</th>
											<th data-flex="false" data-width="50px" style="width:80px" class="c-pri" >需求方</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" >开始时间</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" >结束时间</th>
											<th data-flex="false" data-width="auto" style="width:120px" class="c-name text-center" >验收时间</th>
											<th data-flex="false" data-width="auto" style="width:90px" class="c-name text-center" >状态</th>
											<th data-flex="false" data-width="auto" style="width:100px" class="c-name text-center" >创建时间</th>
											<th data-flex="false" data-width="300px" style="width:200px" class="c-name text-center" >原型图</th>
											<th data-flex="false" data-width="300px" style="width:200px" class="c-name text-center" >流程图</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${List}" var="need" varStatus="sta">
										<tr>
											<td class="c-id cell-id text-center">${need.id}</td>
											<td class="text-left">${need.need_name}</td>
											<td class="c-name text-left">${need.member_name}</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.start_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.end_date}" pattern="yyyy-MM-dd" /></td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.checked_time}" pattern="yyyy-MM-dd HH:mm" /></td>
											<td class="c-assignedTo has-btn text-center">
													${need.state == 0 ? '已删除' : need.state == 1 ? '未开始' : need.state == 2 ? '进行中'
													 : need.state == 3 ? '待验收' : need.state == 4 ? '已验收' : need.state == 5 ? '已关闭' : '未知'}
											</td>
											<td class="c-assignedTo has-btn text-center"><fmt:formatDate value="${need.create_time}" pattern="yyyy-MM-dd" /></td>
											<td class="c-actions text-right">
											<c:forEach items="${fn:split(need.interface_img, ',')}" var="inter" varStatus="sta">
												<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
																fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
															  fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
																fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
													<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${need.need_name}【界面原型图】">
												</c:if>
												<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
															  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true && inter!=null && inter!='' }">
													<a href="${inter}">下载非图片文件</a>
												</c:if>
											</c:forEach>
											</td>
											<td class="c-actions text-right">
											<c:forEach items="${fn:split(need.flow_img, ',')}" var="flow" varStatus="sta">
												<c:if test="${fn:contains(flow,'.BMP')==true || fn:contains(flow,'.JPEG')==true || fn:contains(flow,'.GIF')==true || 
																fn:contains(flow,'.PNG')==true || fn:contains(flow,'.JPG')==true ||
															  fn:contains(flow,'.bmp')==true || fn:contains(flow,'.jpeg')==true || fn:contains(flow,'.gif')==true || 
																fn:contains(flow,'.png')==true || fn:contains(flow,'.jpg')==true }">
													<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${need.need_name}【流程图】">
												</c:if>
												<c:if test="${fn:contains(flow,'.BMP')!=true && fn:contains(flow,'.JPEG')!=true && fn:contains(flow,'.GIF')!=true && 
																fn:contains(flow,'.PNG')!=true && fn:contains(flow,'.JPG')!=true &&
															  fn:contains(flow,'.bmp')!=true && fn:contains(flow,'.jpeg')!=true && fn:contains(flow,'.gif')!=true && 
																fn:contains(flow,'.png')!=true && fn:contains(flow,'.jpg')!=true && flow!=null && flow!='' }">
													<a href="${flow}">下载非图片文件</a>
												</c:if>
											</c:forEach>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
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
	$("#bysearchTab").click(function(){
		if($(this).hasClass("querybox-opened")){
			$(this).removeClass("querybox-opened")
			$("#queryBox").removeClass("show")
		}else{
			$(this).addClass("querybox-opened")
			$("#queryBox").addClass("show")
		}
	});
</script>