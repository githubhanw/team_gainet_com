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
		<title>验收：${n.needName }</title>
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
								<form class="main-table table-task skip-iframe-modal" id="createForm" method="post">
								<tr>
									<th>需求描述</th>
									<td>${needM.need_remark}</td>
									<td></td>
								</tr>
								<tr>
									<th>验收标准</th>
									<td class="required">${needM.check_remark}</td>
									<td></td>
								</tr>
								<tr>
									<th>状态</th>
									<td class="required">
										<label class="radio-inline"><input type="radio" name="stage" value="y" checked="checked" id="passy"> 通过</label>
										<label class="radio-inline"><input type="radio" name="stage" value="n" id="passn"> 不通过</label>
									</td>
								</tr>
								<tr>
									<th>备注</th>
									<td class="required">
										<input type="hidden" name="comment">
										<textarea id="comment" name="details" placeholder="" style="width:100%;"></textarea>
										<div id="comment" value=""></div>
										<input type="hidden" name="id" value="${n.id}"/>
										<input type="hidden" name="needname" value="${n.needName}"/>
									</td>
								</tr>
								<tr>
									<th>上传文档</th>
									<td class="required">
										<input type="file" name="file" id="file">
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
						<div style="margin: 0 auto">
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="my/need" class="btn">返回需求列表</a>
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
	UE.getEditor('comment');
	$("#submit").click(function(){

			
		
		$("input[name='comment']").val(UE.getEditor('comment').getContent());
		var form = new FormData(document.getElementById("createForm"));
		var filesize=$("#file").val();
		
		if(filesize==''){
			alert("请选择文件");
		}else{
		$.ajaxSettings.async = false;
		$.ajax({
	         url:"my/need/check?r=" + Math.random(),
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
		}
	});	
//	UMEditor("comment");
//	$("#submit").click(function(){
//		$.ajaxSettings.async = false;
//		$("input[name='comment']").val(UM.getEditor('comment').getContent());
//		$.ajax({type:"POST",url:"my/need/check?r=" + Math.random(),data:$("form").serialize(),
//				dataType:"json",success:function(data){
//			if(data.code == 0){
//				$("#msg").text(data.message);
//				$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
//			}else{
//				$("#errMsg").text(data.message);
//				$('#errModal').modal({keyboard: false,show: true, moveable: true});
//			}
//	 	}})
//		$.ajaxSettings.async = true;
//	 });
	</script>
</html>