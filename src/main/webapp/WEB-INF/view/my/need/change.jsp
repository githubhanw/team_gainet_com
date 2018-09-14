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
		<title>变更：${n.needName }</title>
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
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 变更</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>需求名称</th>
									<td class="required">
										<input type="text" name="need_name" id="need_name" value="${n.needName}" class="form-control input-product-title" autocomplete="off">
									</td>
								</tr>
								<tr>
									<th>项目名称</th>
									<td class="required">
										<select class="form-control chosen-select" name="projectId" id="projectId" onchange="selectParent()">
											<option value="0">请选择项目名称</option>
											<c:forEach items="${projectList}" var="project" varStatus="sta">
												<option value="${project.id}" ${project.id==n.projectId?'selected="selected"':''}>${project.project_name}</option>
											</c:forEach>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>需求描述</th>
									<td class="required">
										<input type="hidden" name="need_remark">
										<textarea id="need_remark" name="details" placeholder="" style="width:100%;height:500px;">${n.needRemark}</textarea>
										<div id="need_remark" value=""></div>
										<span class="help-block">建议参考的模板：作为一名&lt;某种类型的用户&gt;，我希望&lt;达成某些目的&gt;，这样可以&lt;开发的价值&gt;。</span>
									</td>
								</tr>
								
								<tr>
									<th>验收标准</th>
									<td class="required">
										<input type="hidden" name="check_remark">
										<textarea id="check_remark" name="details" placeholder="" style="width:100%;height:500px;">${n.checkRemark}</textarea>
										<div id="check_remark" value=""></div>
										<input type="hidden" name="id" value="${n.id}"/>
									</td>
									<td></td>
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
UE.getEditor('need_remark');
UE.getEditor('check_remark');
$("#submit").click(function(){

		
	
	$("input[name='need_remark']").val(UE.getEditor('need_remark').getContent());
	$("input[name='check_remark']").val(UE.getEditor('check_remark').getContent());
	var form = new FormData(document.getElementById("createForm"));
	var filesize=$("#file").val();
	
	if(filesize==''){
		alert("请选择文件");
	}else{
	$.ajaxSettings.async = false;
	$.ajax({
         url:"my/need/change?r=" + Math.random(),
         type:"post",
         data:form,
         dataType:"json",
         processData:false,
         contentType:false,
         success:function(data){
        	 if(data.code == 0){
     			window.location.href = "my/need";
     		}else{
     			alert(data.message);
     		}
         }
     });
	$.ajaxSettings.async = true;
	}
});
//UMEditor("need_remark");
//UMEditor("check_remark");
//$("#submit").click(function(){
//	$.ajaxSettings.async = false;
//	$("input[name='need_remark']").val(UM.getEditor('need_remark').getContent());
//	$("input[name='check_remark']").val(UM.getEditor('check_remark').getContent());
//	$.ajax({type:"POST",url:"my/need/change?r=" + Math.random(),data:$("form").serialize(),
//			dataType:"json",success:function(data){
//		if(data.code == 0){
//			window.location.href = "my/need";
//		}else{
//			alert(data.message);
//		}
//	}})
//	$.ajaxSettings.async = true;
//});
</script>