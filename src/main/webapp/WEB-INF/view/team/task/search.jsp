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
		<title>人员工作安排情况</title>
		<%@ include file="/WEB-INF/view/comm/cssjs.jsp" %>
		<script src="static/calendar/moment.min.js"></script>
		<script src="static/calendar/fullcalendar.min.js"></script>
		<link rel="stylesheet" href="static/calendar/fullcalendar.min.css">
		<link rel="stylesheet" href="static/calendar/ace.min.css">
		<style type="text/css">
			.fc-toolbar h2{float:left;height: 26px;line-height: 26px;width: 130px;font-size: 20px;}
			.fc-toolbar button{float:left;height:26px;padding:0 .3em;}
			.fc-toolbar{margin-bottom:0px;background: #cfe7ff;border:1px solid #cfe7ff;padding:8px 10px;}
			.fc-unthemed td,.fc-unthemed th{border-color:#cfe7ff;}
			.fc-unthemed th{padding:8px;}
			.fc-day-grid-event > .fc-content{text-align: center;}
			.fc-state-default{background: #8cc6e8;}
			.fc-ltr .fc-basic-view .fc-day-number:nth-child(1),.fc-ltr .fc-basic-view .fc-day-number:nth-child(7){color:red;}
			
			.label.arrowed{height:30px;line-height:30px;padding:0 20px 0 15px;margin-left:10px;}
			.label.arrowed:before{left:-20px;border-width:15px 10px;}
			#calendar{width:100%;}
			.modal-header{padding:6px 15px;background: #6eafd9;color:#fff;}
			.modal-header .close{color:#fff;font-size:28px;margin-top:-4px;opacity:1;}
			.btn-danger{padding:1px 20px;}
			.modal-footer{text-align: center;background-color:none!important;border-top:none;}
			.mr span{padding:0 5px 5px 5px;}
			.mr{padding-bottom:5px;}
			.mrs span:nth-child(1){font-weight:bold;}
			.mrs span:nth-child(2){width:80px;text-align:left;display: inline-block;}
			[status='1']{color:red;}
			a.fc-start[class*='apply']{cursor:zoom-in;cursor:-webkit-zoom-in;}
			a.fc-start[class*='apply']:hover{box-shadow:0 0 10px #428bca}
		</style>
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
						<a href="team/task/search?type=2">
							<span class="btn btn-link ${prm.type == 2 ? 'btn-active-text':''}"><span class="text">人员实际动态</span></span>
						</a>
						<a href="team/task/search?type=1">
							<span class="btn btn-link ${prm.type == 1 ? 'btn-active-text':''}""><span class="text">人员安排</span></span>
						</a>
					</div>
					<div class="btn-toolbar pull-left" style="margin-left:30px;width:300px">
						<form action="team/task/search">
							<input type="hidden" name="type" value="${prm.type}"/>
							<input type="hidden" name="date" value="${prm.date}"/>
							<table style="width:100%">
								<tr>
									<td>
										<select class="form-control chosen chosen-select" name="depId" id="depId">
											<option value="">请选择部门或团队</option>
											<c:forEach items="${levelA}" var="a">
												<option ${prm.depId==a.depId?'selected="selected"':'' } value="${a.depId}">${a.depName}</option>
												<c:forEach items="${levelB}" var="b" varStatus="sta">
													<c:if test="${b.parentId == a.depId}">
														<option ${prm.depId==b.depId?'selected="selected"':'' } value="${b.depId}">| -- ${b.depName}</option>
														<c:forEach items="${levelC}" var="c" varStatus="sta">
															<c:if test="${c.parentId == b.depId}">
																<option ${prm.depId==c.depId?'selected="selected"':'' } value="${c.depId}">&nbsp; &nbsp; &nbsp; | -- ${c.depName}</option>
															</c:if>
														</c:forEach>
													</c:if>
												</c:forEach>
											</c:forEach>
										</select>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="panel">
							<div class="panel-heading">
								<div class="panel-title">
									无任务人员统计
								</div>
							</div>
							<div class="panel-body" style="margin-top:10px">
								<div id="calendar"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script type="text/javascript">
	jQuery(function($) {
		var noTime =  $.fullCalendar.moment("${prm.date}");
		var calendar = $('#calendar').fullCalendar({
			buttonText:{
				prev:"上月",
				next:"下月"
			},
		    timeFormat: {
		        '': 'H:mm{-H:mm}'
		    },
		    weekMode: "variable",
		    columnFormat: {
		        month: 'dddd',
		        week: 'dddd M-d',
		        day: 'dddd M-d'
		    },
		    titleFormat: {
		        month: "YYYY年MMMM月",
		        week: "[yyyy年] MMMM月d日 { '&#8212;' [yyyy年] MMMM月d日}",
		        day: 'yyyy年 MMMM月d日 dddd'
		    },
		    monthNames: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
		    dayNames: ["日", "一", "二", "三", "四", "五", "六"],
			header: {
				left: '',
				center: 'prev,title,next',
				right: ''
			}
		    ,events:${noTaskMemberByMonth}
		    ,eventLimit:false
		    ,eventLimitText : function(more){return "剩" + more + ",查看全部记录";}
			,dayPopoverFormat:"YYYY年MMMM月D日 周dddd"
		    ,views:{
		    	agenda:{
		    		eventLimit:5
		    	}
		    }
		});

		$('#calendar').fullCalendar('gotoDate',noTime);
		var url=document.baseURI+"/team/task/search?type=${prm.type}&depId=${prm.depId}&date=";
		$("#calendar").on("click",".fc-button",function(){
			var date=$.trim($(this).siblings("h2").text().replace("年","-").replace("月",""));
			if(date.length==6)date=date.replace("-","-0");
			location.replace(url+date + "-01");
		});
		$("#depId").on("change",function(){
			$("form").submit();
		})
	})
</script>