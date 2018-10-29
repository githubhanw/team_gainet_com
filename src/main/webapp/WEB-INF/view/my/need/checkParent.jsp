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
		<meta http-equiv="Content-Type" content="multipart/form-data;charset=utf-8" />
		<title>验收项目</title>
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
				<div id="mainContent" class="main-content">
					<div class="center-block">
						<div class="main-header">
							<h2>
								<span class="label label-id">${n.id}</span>
								<a href="my/need/detail?id=${n.id}">${n.needName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 验收</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post" enctype="multipart/form-data">
								<tr>
									<th>模块名称</th>
									<td>${n.needName}</td>
									<td></td>
								</tr>
								<tr>
									<th>模块描述</th>
									<td bgcolor="#EFEFEF">
									${n.needRemark}
									</td>
								</tr>
								<tr>
									<th>验收标准</th>
									<td bgcolor="#EFEFEF">
									${n.checkRemark}
									</td>
								</tr>
								<tr>
									<th>完成时间</th>
									<td>${n.finishedTime}
									</td>
									<td></td>
								</tr>
								<input type="hidden" name="id" value="${n.id}"/>
								</form>
								<tr>
									<th>模块详情</th>
									<td>
										<ul class="tree tree-lines" data-ride="tree">
												<li>&nbsp;<a href="#">${n.needName }【模块】</a>
													<ul>
													<c:forEach items="${subNeed}" var="subNeed" varStatus="sta">
														<li><a href="#">&nbsp;${subNeed.need_name }【子模块】</a>
															<ul>
															<c:forEach items="${subNeedTask}" var="task" varStatus="sta">
															<c:if test="${subNeed.id == task.need_id }">
																<li><a href="#">&nbsp;${task.task_name}【任务】</a>
																	<ul>
																		<li><a href="#">&nbsp;界面原型图</a>
																			<ul>
																				<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																					<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
																						fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
																					    fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
																						fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
																			            <img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																					</c:if>
																					<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																									fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
																								  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																									fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true && inter!=null && inter!='' }">
																						<a href="${inter}">下载非图片文件</a>
																					</c:if>
																				</c:forEach>
																			</ul>
																		</li>
																		<li><a href="#">&nbsp;流程图</a>
																			<ul>
																				<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																					<c:if test="${fn:contains(flow,'.BMP')==true || fn:contains(flow,'.JPEG')==true || fn:contains(flow,'.GIF')==true || 
																						fn:contains(flow,'.PNG')==true || fn:contains(flow,'.JPG')==true ||
																					    fn:contains(flow,'.bmp')==true || fn:contains(flow,'.jpeg')==true || fn:contains(flow,'.gif')==true || 
																						fn:contains(flow,'.png')==true || fn:contains(flow,'.jpg')==true }">
																			            <img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																					</c:if>
																					<c:if test="${fn:contains(flow,'.BMP')!=true && fn:contains(flow,'.JPEG')!=true && fn:contains(flow,'.GIF')!=true && 
																									fn:contains(flow,'.PNG')!=true && fn:contains(flow,'.JPG')!=true &&
																								  fn:contains(flow,'.bmp')!=true && fn:contains(flow,'.jpeg')!=true && fn:contains(flow,'.gif')!=true && 
																									fn:contains(flow,'.png')!=true && fn:contains(flow,'.jpg')!=true && flow!=null && flow!='' }">
																						<a href="${flow}">下载非图片文件</a>
																					</c:if>
																				</c:forEach>
																			</ul>
																		</li>
																		<li><a href="#">&nbsp;测试用例</a>
																			<ul>
																			<c:forEach items="${testCase}" var="casess" varStatus="sta">
																			<c:if test="${task.id == casess.task_id }">
																				<li><a href="#">&nbsp;${casess.case_name}
【${casess.case_type==1?'功能测试':casess.case_type==2?'性能测试':casess.case_type==3?'配置相关':casess.case_type==4?'安装部署':casess.case_type==5?'安全相关':casess.case_type==6?'接口测试':'其他'}】</a>
																					<ul>
																						<table>
																							<tr>
																								<td style="border:1px solid #cbd0db" colspan="3">前提条件：${casess.precondition}</td>
																							</tr>
																							<tr>
																								<td style="border:1px solid #cbd0db">编号</td>
																								<td style="border:1px solid #cbd0db">步骤</td>
																								<td style="border:1px solid #cbd0db">预期</td>
																							</tr>
																							<c:set var="index" value="1"/>
																							<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																							<c:if test="${casess.id == step.case_id }">
																							<tr>
																								<td style="border:1px solid #cbd0db">${index}</td>
																								<td style="border:1px solid #cbd0db">${step.step }</td>
																								<td style="border:1px solid #cbd0db">${step.expect }</td>
																							</tr>
																							<c:set var="index" value="${index+1}"/>
																							</c:if>
																							</c:forEach>
																						</table>
																					</ul>
																				</li>
																			</c:if>
																			</c:forEach>
																			</ul>
																		</li>
																	</ul>
																</li>
															</c:if>
															</c:forEach>
															</ul>
														</li>
													</c:forEach>
													<c:forEach items="${needTask}" var="task" varStatus="sta">
													<c:if test="${n.id == task.need_id }">
														<li><a href="#">&nbsp;${task.task_name}【任务】</a>
															<ul>
																<li><a href="#">&nbsp;界面原型图</a>
																	<ul>
																		<c:forEach items="${fn:split(task.interface_img, ',')}" var="inter" varStatus="sta">
																			<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
																						fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
																					    fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
																						fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
																			            <img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【界面原型图】">
																					</c:if>
																					<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																									fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
																								  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																									fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true && inter!=null && inter!='' }">
																						<a href="${inter}">下载非图片文件</a>
																					</c:if>
																		</c:forEach>
																	</ul>
																</li>
																<li><a href="#">&nbsp;流程图</a>
																	<ul>
																		<c:forEach items="${fn:split(task.flow_img, ',')}" var="flow" varStatus="sta">
																			<c:if test="${fn:contains(flow,'.BMP')==true || fn:contains(flow,'.JPEG')==true || fn:contains(flow,'.GIF')==true || 
																						fn:contains(flow,'.PNG')==true || fn:contains(flow,'.JPG')==true ||
																					    fn:contains(flow,'.bmp')==true || fn:contains(flow,'.jpeg')==true || fn:contains(flow,'.gif')==true || 
																						fn:contains(flow,'.png')==true || fn:contains(flow,'.jpg')==true }">
																			            <img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${task.task_name}【流程图】">
																					</c:if>
																					<c:if test="${fn:contains(flow,'.BMP')!=true && fn:contains(flow,'.JPEG')!=true && fn:contains(flow,'.GIF')!=true && 
																									fn:contains(flow,'.PNG')!=true && fn:contains(flow,'.JPG')!=true &&
																								  fn:contains(flow,'.bmp')!=true && fn:contains(flow,'.jpeg')!=true && fn:contains(flow,'.gif')!=true && 
																									fn:contains(flow,'.png')!=true && fn:contains(flow,'.jpg')!=true && flow!=null && flow!='' }">
																						<a href="${flow}">下载非图片文件</a>
																					</c:if>
																		</c:forEach>
																	</ul>
																</li>
																<li><a href="#">&nbsp;测试用例</a>
																	<ul>
																	<c:forEach items="${testCase}" var="casess" varStatus="sta">
																	<c:if test="${task.id == casess.task_id }">
																		<li><a href="#">&nbsp;${casess.case_name}
	【${casess.case_type==1?'功能测试':casess.case_type==2?'性能测试':casess.case_type==3?'配置相关':casess.case_type==4?'安装部署':casess.case_type==5?'安全相关':casess.case_type==6?'接口测试':'其他'}】</a>
																			<ul>
																				<table>
																					<tr>
																						<td style="border:1px solid #cbd0db" colspan="3">前提条件：${casess.precondition}</td>
																					</tr>
																					<tr>
																						<td style="border:1px solid #cbd0db">编号</td>
																						<td style="border:1px solid #cbd0db">步骤</td>
																						<td style="border:1px solid #cbd0db">预期</td>
																					</tr>
																					<c:set var="index" value="1"/>
																					<c:forEach items="${testCaseStep}" var="step" varStatus="sta">
																					<c:if test="${casess.id == step.case_id }">
																					<tr>
																						<td style="border:1px solid #cbd0db">${index}</td>
																						<td style="border:1px solid #cbd0db">${step.step }</td>
																						<td style="border:1px solid #cbd0db">${step.expect }</td>
																					</tr>
																					<c:set var="index" value="${index+1}"/>
																					</c:if>
																					</c:forEach>
																				</table>
																			</ul>
																		</li>
																	</c:if>
																	</c:forEach>
																	</ul>
																</li>
															</ul>
														</li>
													</c:if>
													</c:forEach>
													</ul>
												</li>
										</ul>
									</td>
									<td></td>
								</tr>
								<tr>
									<td colspan="3" class="text-center form-actions">
										<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">验收</button>
										<button id="notThrough" class="btn btn-wide" style="background-color: red;">不通过</button>
										<!-- <button id="export" class="btn btn-wide btn-primary">导出</button> -->
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</main>
		<div class="modal fade" id="myModal">
			<div class="modal-dialog" style="width:600px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">提示结果</h4>
					</div>
					<div class="modal-body">
						<div style="margin:0 auto">
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="my/need" class="btn">返回我的模块</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="errModal">
			<div class="modal-dialog" style="width:300px">
				<div class="modal-content">
					<div class="modal-body">
						<div style="margin:0 auto;">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span><span class="sr-only">关闭</span>
							</button>
							<p>
								<span id="errMsg"></span>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>

$("#submit").click(function(){
	var form = new FormData(document.getElementById("createForm"));
	$.ajax({
         url:"my/need/checkParent?r=" + Math.random(),
         type:"post",
         data:form,
         dataType:"json",
         processData:false,
         contentType:false,
         success:function(data){
        	if(data.code == 0){
 				$("#msg").text(data.message);
 				$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
 			}else{
 				$("#errMsg").text(data.message);
 				$('#errModal').modal({keyboard: false,show: true, moveable: true});
 			}
         }
     });
});

$("#notThrough").click(function(){
	var form = new FormData(document.getElementById("createForm"));
	var notThrough = 0;
	$.ajax({
         url:"my/need/checkParent?r=" + Math.random() + "&notThrough=0",
         type:"post",
         data:form,
         dataType:"json",
         processData:false,
         contentType:false,
         success:function(data){
        	if(data.code == 0){
 				$("#msg").text(data.message);
 				$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
 			}else{
 				$("#errMsg").text(data.message);
 				$('#errModal').modal({keyboard: false,show: true, moveable: true});
 			}
         }
     });
});
</script>