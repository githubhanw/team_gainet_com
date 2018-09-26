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
		<title>编辑文档</title>
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
							<h2>编辑文档</h2>
						</div>
						<table class="table table-form">
							<tbody>
							<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<input id="fi_id" name="fi_id" type="hidden" value="${FileManage.id}"></input>
								<tr>
									<th>标题</th>
									<td class="required" style="width:60%">
										<input type="text" name="file_name" id="file_name" class="form-control input-product-title" autocomplete="off" value="${FileManage.fileName}">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>关联类型</th>
									<td class="required" style="width:60%">
									<select data-placeholder="请选择类型" class="form-control chosen-select" name="file_type" id="file_type" onchange="show_sub(this.options[this.options.selectedIndex].value)">
												<c:if test="${FileManage.fileClassification ==0}">
												<option value="${FileManage.fileClassification}">项目</option>
												<option value="1">模块</option>
												</c:if>
												<c:if test="${FileManage.fileClassification ==1}">
												<option value="${FileManage.fileClassification}">模块</option>
                                                <option value="0">项目</option>
												</c:if>
									</select>
									</td>
									<td></td>
								</tr>
								<tr id="tr1">
									<th>关联项目</th>
									<td class="required" style="width:60%">
									<select data-placeholder="请选择类型" class="form-control chosen-select" name="xm" id="xm">
									            <c:if test="${FileManage.fileClassification ==0}">
												<option value="${FileManage.glId}">${glname}</option>
												</c:if>
												<c:if test="${FileManage.fileClassification ==1}">
									            <option value=""></option>
									            </c:if>
									            <c:forEach items="${tp}" var="tp" varStatus="sta">
												<option value="${tp.id}">${tp.project_name}(${tp.id})</option>
											    </c:forEach>
									</select>
									</td>
									
								</tr>
								<tr id="tr2">
									<th>关联模块</th>
									<td class="required" style="width:60%">
									<select data-placeholder="请选择类型" class="form-control chosen-select" name="xq" id="xq">
									           <c:if test="${FileManage.fileClassification ==0}">
												<option value=""></option>
												</c:if>
												<c:if test="${FileManage.fileClassification ==1}">
									            <option value="${FileManage.glId}">${glname}</option>
									            </c:if>
									           <c:forEach items="${tn}" var="tn" varStatus="sta">
												<option value="${tn.id}">${tn.need_name}(${tn.id})</input></option>
											   </c:forEach>
									</select>
									</td>
								</tr>
								<tr>
									<th>正文</th>
									<td class="required">
										<input type="hidden" name="file_text">
										<textarea id="file_text" name="details" placeholder="" style="width:100%;">${FileManage.fileText}</textarea>
										<div id="file_text" value=""></div>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td class="required" style="width:60%">
										<input type="text" name="file_remarks" id="file_remarks" class="form-control input-product-title" autocomplete="off" value="${FileManage.fileRemarks}">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>修改文件</th>
									<td class="required">
										<input type="file" name="file" id="file" value="">
										<div><span>${FileManage.fileRealname}</span></div>
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
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
function run (){
	var type = $("#file_type").val();
	if(type==0){
	   	$("#tr1").show();
	   	$("#tr2").hide();
	   }else{
		$("#tr2").show();
		$("#tr1").hide();
	   }
}
run();

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
	$.ajaxSettings.async = false;
	$.ajax({
         url:"filemanage/manage/myedit?r=" + Math.random(),
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
//UMEditor("file_text");
//$("#submit").click(function(){
//	$.ajaxSettings.async = false;
//	$("input[name='file_text']").val(UM.getEditor('file_text').getContent());
//	$.ajax({type:"POST",url:"filemanage/manage/myedit?r=" + Math.random(),data:$("form").serialize(),
//			dataType:"json",success:function(data){
//		if(data.code == 0){
//			$("#msg").text(data.message);
//			$('#myModal').modal({backdrop: 'static', keyboard: false,show: true, moveable: true});
//		}else{
//			$("#errMsg").text(data.message);
//			$('#errModal').modal({keyboard: false,show: true, moveable: true});
//		}
//	}})
//	$.ajaxSettings.async = true;
//});
//$("#file_type").on("change",function(){
//    if($(this).val() == "0"){
//    	$("#bbb").attr("style","display:none;");//隐藏div
//    	$("#ddd").attr("style","display:none;");//隐藏div
//    	$("#aaa").attr("style","display:block;");//显示div
//    	$("#ccc").attr("style","display:block;");//显示div
//    }else{
//   	$("#aaa").attr("style","display:none;");//隐藏div
//    	$("#ccc").attr("style","display:none;");//隐藏div
//    	$("#bbb").attr("style","display:block;");//显示div
//    	$("#ddd").attr("style","display:block;");//显示div
//    }
//})
//function run(){
//	var fille=$("#fille").val();//获取文档类型的值
//	if(fille==0){
//		$("#bbb").attr("style","display:none;");//隐藏div
//   	$("#ddd").attr("style","display:none;");//隐藏div
//    	$("#aaa").attr("style","display:block;");//显示div
//    	$("#ccc").attr("style","display:block;");//显示div
//	}else{
//		$("#aaa").attr("style","display:none;");//隐藏div
//    	$("#ccc").attr("style","display:none;");//隐藏div
//   	$("#bbb").attr("style","display:block;");//显示div
//    	$("#ddd").attr("style","display:block;");//显示div
//	}
//}
//run();//进页面就执行方法
</script>