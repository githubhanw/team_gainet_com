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
		<title>创建文档</title>
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
							<h2>创建文档</h2>
						</div>
						<table class="table table-form">
							<tbody>
							<form class="load-indicator main-form form-ajax" id="createForm" method="post" enctype="multipart/form-data">
								<tr>
									<th>标题</th>
									<td class="required" style="width:60%">
										<input type="text" name="file_name" id="file_name" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>关联类型</th>
									<td class="required" style="width:60%">
									<select data-placeholder="请选择类型" class="form-control chosen-select" name="file_type" id="file_type" onchange="show_sub(this.options[this.options.selectedIndex].value)">
												<option value=""></option>
												<option value="0">项目</option>
												<option value="1">模块</option>
									</select>
									</td>
									<td></td>
								</tr>
								
								<tr id="tr1">
									<th>关联项目</th>
									<td class="required" style="width:60%">
									<select data-placeholder="项目和模块选其中一个即可" class="form-control chosen-select" name="xm" id="xm">
												<option value=""></option>
											<c:forEach items="${tp}" var="tp" varStatus="sta">
												<option value="${tp.id}">${tp.project_name}(${tp.id})</option>
											</c:forEach>
									</select>
									</td>
									<td></td>
								</tr>
								<tr id="tr2">
									<th>关联模块</th>
									<td class="required" style="width:60%">
									<select data-placeholder="项目和模块选其中一个即可" class="form-control chosen-select" name="xq" id="xq">
												<option value=""></option>
											<c:forEach items="${tn}" var="tn" varStatus="sta">
												<option value="${tn.id}">${tn.need_name}(${tn.id})</input></option>
											</c:forEach>
									</select>
									</td>
									<td></td>
								</tr>
								
								<tr>
									<th>正文</th>
									<td class="required">
										<input type="hidden" name="file_text">
										<textarea id="file_text" name="details" placeholder="" style="width:100%;"></textarea>
										<div id="file_text" value=""></div>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td class="required" style="width:60%">
										<input type="text" name="file_remarks" id="file_remarks" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>模块文件</th>
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
						<div style="margin:0 auto">
							<strong> <p style="font-size:18px">操作成功</p></strong>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="filemanage/manage/index" class="btn">返回文档首页</a>
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
		<input id="f_id" name="f_id" type="hidden" value="000"></input>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
function show_sub(v){
	   if(v==0){
	   	$("#tr1").show();
	   	$("#tr2").hide();
	   }else{
		$("#tr2").show();
		$("#tr1").hide();
	   }
}
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
UE.getEditor('file_text');

$("#submit").click(function(){

		
	
	$("input[name='file_text']").val(UE.getEditor('file_text').getContent());
	var form = new FormData(document.getElementById("createForm"));
	var filesize=$("#file").val();
	
	if(filesize==''){
		alert("请选择文件");
	}else{
	$.ajaxSettings.async = false;
	$.ajax({
         url:"filemanage/manage/addwd?r=" + Math.random(),
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
//   $("#file_type").on("change",function(){
//	var v1 = document.getElementById("tr1");
//	var v2 = document.getElementById("tr2");
 //   if($(this).val() == "0"){
  //  	$("#tr2").toggle();
 //   	$("#tr1").toggle();
//      	$("#tr1").attr("style","display:block;");//隐藏选择项目下拉框
//      	$("#tr2").attr("style","display:none;");//显示选择模块下拉框
 //   }else{
 //   	$("#tr2").toggle();
  //  	$("#tr1").toggle();
//   	$("#tr1").attr("style","display:none;");//隐藏选择项目下拉框
//     	$("#tr2").attr("style","display:block;");//显示选择模块下拉框
  //  }

// })
</script>