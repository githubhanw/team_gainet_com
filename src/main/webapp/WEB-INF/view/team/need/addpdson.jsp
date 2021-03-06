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
		<title>添加子模块</title>
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
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 添加子模块</small>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
							<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>所属产品</th>
									<td style="width:60%">
										<c:forEach items="${product}" var="p" varStatus="sta">
											<c:if test="${p.id==product_id}">${p.product_name }</c:if>
										</c:forEach>
										<input type="hidden" name="id" value="${need_id}"/>
										<input type="hidden" name="src_id" value="${n.srcId}"/>
										<input type="hidden" name="member_id" value="${n.memberId}"/>
										<input type="hidden" name="product_id" value="${n.productId}"/>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>子模块名称</th>
									<td class="required">
										<input type="text" name="need_name" id="need_name" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
								    <th>原型图</th>
								    <td class="required">
										<input type="file" name="filePrototype" multiple="multiple" accept="image/*"/>
								    </td>
								    <td></td>
								</tr>
								<tr>
								    <th>流程图</th>
								    <td class="required">
										<input type="file" name="filetree" multiple="multiple" accept="image/*"/>
									</td>
								    <td></td>
								</tr>
								<tr>
									<th>指派给</th>
									<td class="required">
										<select data-placeholder="请选择被指派人员" class="form-control chosen-select" name="assigned_id" id="assigned_id">
											<option value=""></option>
											<c:forEach items="${members}" var="member" varStatus="sta">
												<option value="${member.id}">${member.name}(${member.number})</option>
											</c:forEach>
										</select>
										<input type="hidden" name="department_id" value="0"/>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>优先级</th>
									<td class="required">
										<select class="form-control chosen chosen-select"  name="level" id="level">
											<option value="1">紧急重要</option>
											<option value="2">紧急不重要</option>
											<option value="3">不紧急重要</option>
											<option value="4">不紧急不重要</option>
										</select>
									<td></td>
								</tr>
								<tr>
									<th>代码开始时间</th>
									<td class="required">
										<input type="text" name="start_date" id="start_date"
												class="form-control form-date-limit" placeholder="代码开始时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>代码结束时间</th>
									<td class="required">
										<input type="text" name="cend_date" id="cend_date"
												class="form-control form-date-limit" placeholder="代码结束时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>测试时间</th>
									<td class="required">
										<input type="text" name="tend_date" id="tend_date"
												class="form-control form-date-limit" placeholder="测试时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>上线时间</th>
									<td class="required">
										<input type="text" name="end_date" id="end_date"
												class="form-control form-date-limit" placeholder="上线时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>模块描述</th>
									<td class="required">
										<input type="hidden" name="need_remark">
										<textarea id="need_remark" name="details" placeholder="" style="width:100%;">${n.needRemark}</textarea>
										<div id="need_remark" value=""></div>
										<span class="help-block">建议参考的模板：作为一名&lt;某种类型的用户&gt;，我希望&lt;达成某些目的&gt;，这样可以&lt;开发的价值&gt;。</span>
									</td>
								</tr>
								<tr>
									<th>验收标准</th>
									<td class="required">
										<input type="hidden" name="check_remark">
										<textarea id="check_remark" name="details" placeholder="" style="width:100%;">${n.checkRemark}</textarea>
										<div id="check_remark" value=""></div>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>模块文档</th>
									<td>
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
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="team/task/toAdd" class="btn">建任务</a> 
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
	var product_id = ${product_id};
	
	/* if(filesize==''){
		alert("请选择文件");
	}else{ */
	$.ajaxSettings.async = false;
	$.ajax({
         url:"team/need/addproduct?r=" + Math.random(),
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
	/* } */
});
</script>