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
						<form class="load-indicator main-form form-ajax" id="createForm" method="post">
							<table class="table table-form">
								<tbody>
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
											<input type="text" name="member_name" id="member_name" value="${pr.memberName}" class="form-control input-product-title" autocomplete="off">
										</td>
										<td></td>
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
										<td class="required">
											<input type="text" name="accept_date" id="accept_date" value="<fmt:formatDate value="${pr.acceptDate}" pattern="yyyy-MM-dd"/>" 
													class="form-control form-date" placeholder="受理时间" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
										</td>
										<td></td>
									</tr>
									<tr>
										<th>下证日期</th>
										<td class="required">
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
												<option ${pr.state=='3'?'selected="selected"':'' } value="3">已提交</option>
												<option ${pr.state=='4'?'selected="selected"':'' } value="4">已受理</option>
												<option ${pr.state=='5'?'selected="selected"':'' } value="5">已下证</option>
												<option ${pr.state=='0'?'selected="selected"':'' } value="0">已删除</option>
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
										<td colspan="3" class="text-center form-actions">
											<button id="submit" class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
											<input type="hidden" name="result_id" value="${pr.id}"/>
											<a href="javascript:history.go(-1);" class="btn btn-back btn btn-wide">返回</a>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			</div>
		</main>
    	<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
$("#submit").click(function(){
	var projectResultName = encodeURI(encodeURI($("#name").val()));
	var projectId = $("#projectId").val();
	$.ajaxSettings.async = false;
	$.ajax({type:"POST",url:"declaration/result/addOrUpd?r=" + Math.random(),data:$("form").serialize(),dataType:"json",success:function(data){
		alert(data.message);
	}})
	/* $.getJSON("declaration/result/addOrUpd?r=" + Math.random(), $("form").serialize(), function(data) {
		alert(data.message);
	}); */
	$.ajaxSettings.async = true;
});
</script>