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
		<title>
			<c:if test="${pr.id==null}">添加成果</c:if>
			<c:if test="${pr.id > 0}">修改成果</c:if>
		</title>
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
								<c:if test="${pr.id==null}">添加成果</c:if>
								<c:if test="${pr.id > 0}">修改成果</c:if>
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
									<tr>
										<th>证书号</th>
										<td>
											<input type="text" name="cert_number" id="cert_number" value="${pr.certNumber == null ? '无' : pr.certNumber}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>登记号</th>
										<td class="required">
											<input type="hidden" name="result_id" value="${pr.id}"/>
											<input type="text" name="registration_number" id="number" value="${pr.registrationNumber == null ? '无' : pr.registrationNumber}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>成果名称</th>
										<td class="required">
											<input type="text" name="project_result_name" id="name" value="${pr.projectResultName}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>所属项目</th>
										<td class="required">
											<select class="form-control chosen chosen-select" name="project_id" id="projectId">
												<option value=""></option>
												<option value="0" ${pr.projectId==0?'selected="selected"':''}>无项目</option>
												<c:forEach items="${project}" var="p" varStatus="sta">
													<option value="${p.id}" ${p.id==pr.projectId?'selected="selected"':''}>${p.project_name }</option>
												</c:forEach>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>成果类型</th>
										<td class="required">
											<select class="form-control chosen chosen-select" name="type" id="type">
												<option ${pr.type=='1'?'selected="selected"':'' } value="1">软著</option>
												<option ${pr.type=='2'?'selected="selected"':'' } value="2">发明专利</option>
												<option ${pr.type=='3'?'selected="selected"':'' } value="3">实用新型专利</option>
												<option ${pr.type=='4'?'selected="selected"':'' } value="4">外观专利</option>
												<option ${pr.type=='5'?'selected="selected"':'' } value="5">商标</option>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>撰写人</th>
										<td class="required">
											<select data-placeholder="撰写人" class="form-control chosen-select" name="member_id" id="member_id">
												<option value=""></option>
												<c:forEach items="${members}" var="member" varStatus="sta">
													<option value="${member.id}" ${member.id==pr.memberId?'selected="selected"':''}>${member.name}(${member.number})${member.status==1?'(已离职)':'' }</option>
												</c:forEach>
											</select>
										</td>
										<td><c:if test="${pr.id > 0}">原撰写人：${pr.memberName}</c:if></td>
									</tr>
									<tr>
										<th>申请日期</th>
										<td class="required">
											<input type="text" name="apply_date" id="apply_date" value="<fmt:formatDate value="${pr.applyDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control form-date" placeholder="申请时间 / 预计提供日期" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>受理日期</th>
										<td>
											<input type="text" name="accept_date" id="accept_date" value="<fmt:formatDate value="${pr.acceptDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control form-date" placeholder="受理时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>下证日期</th>
										<td>
											<input type="text" name="down_date" id="down_date" value="<fmt:formatDate value="${pr.downDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control form-date" placeholder="下证时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>所属公司</th>
										<td class="required">
											<select class="form-control chosen chosen-select" name="company" id="company">
												<option ${pr.company=='景安'?'selected="selected"':'' } value="景安">景安</option>
												<option ${pr.company=='快云'?'selected="selected"':'' } value="快云">快云</option>
												<option ${pr.company=='绿林客'?'selected="selected"':'' } value="绿林客">绿林客</option>
												<option ${p.company=='大数据'?'selected="selected"':'' } value="大数据">大数据</option>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>知识产权代理商</th>
										<td class="required">
											<input type="text" name="agent" id="agent" value="${pr.agent == null ? '集佳' : pr.agent}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>状态</th>
										<td class="required">
											<select class="form-control chosen chosen-select" name="state" id="state">
												<option ${pr.state=='1'?'selected="selected"':'' } value="1">待撰写</option>
												<option ${pr.state=='2'?'selected="selected"':'' } value="2">撰写中</option>
												<option ${pr.state=='3'?'selected="selected"':'' } value="3">已撰写</option>
												<option ${pr.state=='4'?'selected="selected"':'' } value="4">已提综管</option>
												<option ${pr.state=='5'?'selected="selected"':'' } value="5">已提代理</option>
												<option ${pr.state=='6'?'selected="selected"':'' } value="6">代理受理</option>
												<option ${pr.state=='7'?'selected="selected"':'' } value="7">代理完成</option>
												<option ${pr.state=='8'?'selected="selected"':'' } value="8">受理通知书</option>
												<option ${pr.state=='9'?'selected="selected"':'' } value="9">已下证</option>
												<option ${pr.state=='0'?'selected="selected"':'' } value="0">已删除</option>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>付款状态</th>
										<td class="required">
											<select class="form-control chosen chosen-select" name="payment" id="payment">
												<option ${pr.payment=='1'?'selected="selected"':'' } value="1">未付款：未向财务提交付款申请</option>
												<option ${pr.payment=='2'?'selected="selected"':'' } value="2">待付款：已向财务提交付款申请</option>
												<option ${pr.payment=='3'?'selected="selected"':'' } value="3">已付款：财务已向代理商付款</option>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>发票状态</th>
										<td class="required">
											<select class="form-control chosen chosen-select" name="invoice" id="invoice">
												<option ${pr.invoice=='1'?'selected="selected"':'' } value="1">未开发票</option>
												<option ${pr.invoice=='2'?'selected="selected"':'' } value="2">已开发票</option>
											</select>
										<td>注：发票为代理公司开具的收据。</td>
									</tr>
									<tr>
										<th>收据状态</th>
										<td class="required">
											<select class="form-control chosen chosen-select" name="receipt" id="receipt">
												<option ${pr.receipt=='1'?'selected="selected"':'' } value="1">未开收据</option>
												<option ${pr.receipt=='2'?'selected="selected"':'' } value="2">已开收据</option>
											</select>
										<td>注：收据为国家知识产权局开具的收据。</td>
									</tr>
									<tr>
										<th>是否有全部文档</th>
										<td class="required">
											<select class="form-control chosen chosen-select" name="is_all_doc" id="is_all_doc">
												<option ${pr.isAllDoc=='1'?'selected="selected"':'' } value="1">否</option>
												<option ${pr.isAllDoc=='2'?'selected="selected"':'' } value="2">是</option>
											</select>
										<td></td>
									</tr>
									<tr>
										<th>版本号</th>
										<td>
											<input type="text" name="version" id="version" value="${pr.version == null ? 'V1.0' : pr.version}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td>软著专用</td>
									</tr>
									<tr>
										<th>发明人</th>
										<td>
											<input type="text" name="inventor" id="inventor" value="${pr.inventor}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td>专利专用</td>
									</tr>
									<tr>
										<th>备注</th>
										<td>
											<input type="hidden" name="remark">
											<textarea id="remark" name="details" placeholder="" style="width:100%;">${t.remark}</textarea>
											<div id="remark" value=""></div>
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
editor.render("remark");
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
	$.ajaxSettings.async = false;
	$("input[name='remark']").val(UE.getEditor('remark').getContent());
	$.ajax({type:"POST",url:"declaration/result/addOrUpd?r=" + Math.random(),data:$("form").serialize(),dataType:"json",success:function(data){
		alert(data.message);
		if(data.code == 0){
			window.location.href="declaration/result/index";
		}
	}})
	$.ajaxSettings.async = true;
});
</script>