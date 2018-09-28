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
		<title>验收里程碑</title>
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
							<h2>验收里程碑</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<input type="hidden" id="mi_id" name="mi_id" value="${mi.id}"></input>
								<input type="hidden" id="mi_name" name="mi_name" value="${mi.authorName}"></input>
								<input type="hidden" id="finishtime" name="finishtime" value="${finishtime}"></input>
								<tr>
									<th>名称:</th>
									<td class="required">${mi.milepostName}</td>
									<td></td>
								</tr>
								<tr>
									<th>完成时间:</th>
									<td class="required">${finishtime}</td>
									<td></td>
								</tr>
								<tr>
									<th>描述:</th>
									<td class="required">${mi.milepostDescribe}</td>
									<td></td>
								</tr>
								<tr>
									<th>模块列表:</th>
									<td class="required">
									<table width="600" border="1">
									   <tr bgcolor=#D3D3D3> 
									     <td width="300">模块名称</td>
									     <td width="300">完成情况</td>
									   </tr>
									   <c:forEach items="${milNeedList}" var="milNeedList">
									   <tr>
									     <td>${milNeedList.need_name}</td>
									     <td>
									     <c:if test="${milNeedList.state == 0}">已删除</c:if>
									     <c:if test="${milNeedList.state == 1}">未开始</c:if>      
									     <c:if test="${milNeedList.state == 2}">进行中</c:if>
									     <c:if test="${milNeedList.state == 3}">待验收</c:if>
									     <c:if test="${milNeedList.state == 4}">已验收</c:if>
									     <c:if test="${milNeedList.state == 5}">已关闭</c:if>
									     </td>
									   </tr>
									   </c:forEach>
									</table> 
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注:</th>
									<td class="required">
										<input type="hidden" name="need_remark">
										<textarea id="need_remark" name="details" placeholder="" style="width:100%;">${mi.milepostRemarks}</textarea>
										<div id="need_remark" value=""></div>
									</td>
									<td></td>
								</tr>
								</form>
								<tr>
									<td colspan="3" class="text-center form-actions">
										<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">确认</button>
										<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">驳回</a>
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
						<div style="margin: 0 auto">
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="test/milepost/manage" class="btn">返回里程碑列表</a>
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
editor.render("need_remark");
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
UE.Editor.prototype.getActionUrl = function(action){  
	if(action == 'uploadimage' || action == 'uploadscrawl'){  
		return '<%=basePath%>ueditor/upload';  
	}else{  
		return this._bkGetActionUrl.call(this, action);  
	}  
};  
UE.getEditor('need_remark');

$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$("input[name='need_remark']").val(UE.getEditor('need_remark').getContent());
	$.ajax({type:"POST",url:"test/milepost/vali?r=" + Math.random(),data:$("form").serialize(),dataType:"json",success:function(data){
		console.log(data);
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