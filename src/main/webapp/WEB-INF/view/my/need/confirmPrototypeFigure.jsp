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
		<title>确认界面原型图</title>
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
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 确认界面原型图</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post" enctype="multipart/form-data">
								<tr>
									<th>界面原型图</th>
									<td style="width:70%">
									<table border="1" style="width: 100%;border:1px solid #ccc">
									   <tr> 
									     <td style="width: 25%; text-align: center;">模块名称</td>
									     <td style="text-align: center;">界面原型</td>
									   </tr>
									   <tr>
									     <td>${n.needName }</td>
									     <td>
									     	<c:if test="${n.interfaceImg !=null }">
										 		<c:forEach items="${fn:split(n.interfaceImg, ',')}" var="inter" varStatus="sta">
										 		    <c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
														fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
													    fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
														fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
											            <img src="${inter}" data-toggle="lightbox" height="50px" data-caption="【原型图】">&nbsp;&nbsp;
													</c:if>
													<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																	fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
																  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																	fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true && inter!=null && inter!='' }">
														<a href="${inter}">下载非图片文件</a>
													</c:if>
													
												</c:forEach> 
											</c:if>
											<c:if test="${n.interfaceImg ==null }">
												无图片
											</c:if>
										</td>
									   </tr>
									   <c:forEach items="${subList}" var="sub">
									   <tr>
									     <td>${sub.need_name }</td>
									     <td>
									     	<c:if test="${sub.interface_img !=null }">
										 		<c:forEach items="${fn:split(sub.interface_img, ',')}" var="inter" varStatus="sta">
													<c:if test="${fn:contains(inter,'.BMP')==true || fn:contains(inter,'.JPEG')==true || fn:contains(inter,'.GIF')==true || 
														fn:contains(inter,'.PNG')==true || fn:contains(inter,'.JPG')==true ||
													    fn:contains(inter,'.bmp')==true || fn:contains(inter,'.jpeg')==true || fn:contains(inter,'.gif')==true || 
														fn:contains(inter,'.png')==true || fn:contains(inter,'.jpg')==true }">
											            <img src="${inter}" data-toggle="lightbox" height="50px" data-caption="【原型图】">&nbsp;&nbsp;
													</c:if>
													<c:if test="${fn:contains(inter,'.BMP')!=true && fn:contains(inter,'.JPEG')!=true && fn:contains(inter,'.GIF')!=true && 
																	fn:contains(inter,'.PNG')!=true && fn:contains(inter,'.JPG')!=true &&
																  fn:contains(inter,'.bmp')!=true && fn:contains(inter,'.jpeg')!=true && fn:contains(inter,'.gif')!=true && 
																	fn:contains(inter,'.png')!=true && fn:contains(inter,'.jpg')!=true && inter!=null && inter!='' }">
														<a href="${inter}">下载非图片文件</a>
													</c:if>
													
												</c:forEach> 
											</c:if>
											<c:if test="${sub.interface_img ==null }">
												无图片
											</c:if>
                                         </td>
									   </tr>
									   </c:forEach>
									</table>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td>
										<input type="hidden" name="remark">
										<textarea id="remark" name="details" placeholder="" style="width:100%;"></textarea>
										<div id="remark" value=""></div>
									</td>
									<td></td>
								</tr>
								<input type="hidden" name="id" value="${n.id }">
								</form>
								<tr>
									<td colspan="3" class="text-center form-actions">
										<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">提交</button>
										<button id="rejected" class="btn btn-back btn btn-wide">驳回</button>
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
var editor = new UE.ui.Editor();
editor.render("content");
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
UE.Editor.prototype.getActionUrl = function(action){  
	if(action == 'uploadimage' || action == 'uploadscrawl'){  
		return '<%=basePath%>ueditor/upload';  
	}else{  
		return this._bkGetActionUrl.call(this, action);  
	}  
};  
UE.getEditor('remark');

$("#submit").click(function(){
	$("input[name='remark']").val(UE.getEditor('remark').getContent());
	var form = new FormData(document.getElementById("createForm"));
		$.ajaxSettings.async = false;
		$.ajax({
	         url:"my/need/confirmPrototypeFigure?r=" + Math.random(),
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
		$.ajaxSettings.async = true;
});

$("#rejected").click(function(){
	$("input[name='remark']").val(UE.getEditor('remark').getContent());
	var form = new FormData(document.getElementById("createForm"));
		$.ajaxSettings.async = false;
		$.ajax({
	         url:"my/need/toRejected?r=" + Math.random(),
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
		$.ajaxSettings.async = true;
});
</script>