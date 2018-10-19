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
		<title>接收：${n.needName }</title>
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
								<a href="team/need/detail?id=${n.id}">${n.needName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 确认</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="main-table table-task skip-iframe-modal" method="post">
								<tr>
									<th>项目名称</th>
									<td style="width: 70%;">${tp.projectName}</td>
									<input type="hidden" name="id" id="id" value="${n.id}">
									<td></td>
								</tr>
								<tr>
									<th>需求名称</th>
									<td>${n.needName}</td>
									<td></td>
								</tr>
								<tr>
									<th>需求方</th>
									<td>${n.memberName}</td>
									<td></td>
								</tr>
								<tr>
									<th>需求描述</th>
									<td>${n.needRemark}</td>
									<td></td>
								</tr>
								<tr>
									<th>模块列表</th>
									<td>
										<c:if test="${needList!=null && needList!=''}">
										<table style="width: 100%;">
											<tr>
												<td style="border:1px solid #cbd0db">模块名称</td>
												<td style="border:1px solid #cbd0db">原型图</td>
												<td style="border:1px solid #cbd0db">流程图</td>
											</tr>
											<c:set var="index" value="1"/>
											<c:forEach items="${needList}" var="step" varStatus="sta">
											<tr>
												<td style="border:1px solid #cbd0db">
													<c:if test="${n.id==step.id}">【本需求】${step.need_name}</c:if>
													<c:if test="${n.id!=step.id}">【子模块】${step.need_name}</c:if>
												</td>
												<td style="border:1px solid #cbd0db">
												<c:forEach items="${fn:split(step.interface_img, ',')}" var="inter" varStatus="sta">
													<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
																	fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
																  fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
																	fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
														<img src="${inter}" data-toggle="lightbox" height="50px" data-caption="${n.needName}【界面原型图】">
													</c:if>
													<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																	fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
																  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																	fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true && inter!=null && inter!='' }">
														<a href="${inter}">下载非图片文件</a>
													</c:if>
												</c:forEach>
												</td>
												<td  style="border:1px solid #cbd0db">
												<c:forEach items="${fn:split(step.flow_img, ',')}" var="flow" varStatus="sta">
													<c:if test="${fn:contains(flow,'.BMP')==true || fn:contains(flow,'.JPEG')==true || fn:contains(flow,'.GIF')==true || 
																	fn:contains(flow,'.PNG')==true || fn:contains(flow,'.JPG')==true ||
																  fn:contains(flow,'.bmp')==true || fn:contains(flow,'.jpeg')==true || fn:contains(flow,'.gif')==true || 
																	fn:contains(flow,'.png')==true || fn:contains(flow,'.jpg')==true }">
														<img src="${flow}" data-toggle="lightbox" height="50px" data-caption="${n.needName}【流程图】">
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
											<c:set var="index" value="${index+1}"/>
											</c:forEach>
										</table>
										</c:if>
										<c:if test="${needList==null || needList==''}">
											无模块
										</c:if>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>交付日期</th>
									<td>
										<fmt:formatDate value="${n.endDate}" pattern="yyyy-MM-dd"/>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>预计工期</th>
									<td class="required">
										<input type="text" name="period" id="period" value="<fmt:formatDate value="${n.endDate}" pattern="yyyy-MM-dd"/>" 
											class="form-control form-date-limit" placeholder="预计工期" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
								</tr>
								<tr>
									<th>成本</th>
									<td class="required">
										<input type="text" name="cost" id="cost" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>报价</th>
									<td class="required">
										<input type="text" name="offer" id="offer" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td>
										<div id="comment" style="width:100%;">
											<input type="hidden" name="comment">
										</div>
									</td>
									<td></td>
								</tr>
								</form>
								<tr>
									<td colspan="3" class="text-center form-actions">
										<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
										<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">返回</a>
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
								<a href="team/need/index" class="btn">返回模块列表</a>
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
var editor = new UE.ui.Editor();
editor.render("comment");
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
UE.Editor.prototype.getActionUrl = function(action){  
	if(action == 'uploadimage' || action == 'uploadscrawl'){  
		return '<%=basePath%>ueditor/upload';  
	}else{  
		return this._bkGetActionUrl.call(this, action);  
	}  
};  
UE.getEditor('comment');

$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$("input[name='comment']").val(UE.getEditor('comment').getContent());
	$.ajax({type:"POST",url:"team/need/sure?r=" + Math.random(),data:$("form").serialize(),
			dataType:"json",success:function(data){
		if(data.code == 0){
			$("#msg").text(data.message);
			$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
		}else{
			$("#errMsg").text(data.message);
			$('#errModal').modal({keyboard: false,show: true, moveable: true});
		}
	}})
	$.ajaxSettings.async = true;
});
</script>