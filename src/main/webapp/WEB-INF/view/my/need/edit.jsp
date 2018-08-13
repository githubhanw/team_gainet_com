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
		<title>编辑需求：${n.needName}</title>
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
				<div class="main-content" id="mainContent">
					<form method="post" enctype="multipart/form-data" target="hiddenwin"
						id="dataform">
						<div class="main-header">
							<h2>
								<span class="label label-id">${n.id}</span>
								<a href="my/need/detail?id=${n.id}">${n.needName}</a>
								<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 编辑</small>
							</h2>
						</div>
						<div class="main-row">
							<div class="main-col col-8">
								<div class="cell">
									<div class="detail">
										<div class="detail-title">需求描述</div>
										<div class="detail-content article-content">${n.needRemark}</div>
									</div>
									<div class="detail">
										<div class="detail-title">验收标准</div>
										<div class="detail-content article-content">${n.checkRemark}</div>
									</div>
									<div class="detail">
										<div class="detail-title">备注</div>
										<div class="form-group">
											<div id="comment" name="comment"></div>
										</div>
									</div>
									<div class="actions form-actions text-center">
										<input type="hidden" name="id" value="${n.id}"/>
										<button type="button" id="submit"
											class="btn btn-wide btn-primary" data-loading="稍候...">保存</button>
										<button type="button" class="btn btn-wide"
											onclick="javascript:history.go(-1);">返回</button>
									</div>
									<hr class="small">
									<div class="detail histories" id="actionbox">
										<div class="detail-title">
											历史记录 &nbsp;
											<button type="button"
												class="btn btn-mini btn-icon btn-expand-all" title="切换显示">
												<i class="icon icon-plus icon-sm"></i>
											</button>
										</div>
			                            <div class="detail-content">
			                                <ol class="histories-list">
												<c:forEach items="${logList}" var="log" varStatus="sta">
													<li value="${sta.index+1}" class="">
														<fmt:formatDate value="${log.start_time}" pattern="yyyy-MM-dd HH:mm:ss"/>,
														由 <strong>${log.member_name}</strong> ${log.method}。
														<c:if test="${log.history != null}">
															<button type="button" class="btn btn-mini switch-btn btn-icon btn-expand" title="切换显示"><i class="change-show icon icon-plus icon-sm"></i></button>
															<div class="history-changes" id="changeBox3">
																<c:forEach items="${log.history}" var="his" varStatus="sta">
																	<c:if test="${his.diff == 0}">
																		修改了 <strong><i>${his.field_desc}</i></strong>，旧值为 "${his.old_data}"，新值为 "${his.new_data}"。<br>
																	</c:if>
																	<c:if test="${his.diff == 1}">
																		修改了 <strong><i>${his.field_desc}</i></strong>，区别为：
																		<blockquote class="textdiff">
																			- <del>${his.old_data}</del><br/>+ <ins>${his.new_data}</ins>
																		</blockquote>
																	</c:if>
																</c:forEach>
															</div>
														</c:if>
														<c:if test="${log.comment != null && log.comment != ''}">
															<div class="article-content comment">${log.comment}</div>
														</c:if>
													</li>
												</c:forEach>
			                                </ol>
			                            </div>
									</div>
								</div>
							</div>
							<div class="side-col col-4">
								<div class="cell">
									<div class="detail">
										<div class="detail-title">基本信息</div>
										<table class="table table-form">
											<tbody>
												<tr>
													<th class="w-80px">所属项目</th>
													<td>
														<div class="input-group">
															<select class="form-control chosen chosen-select"  name="project_id" id="project_id">
																<c:forEach items="${project}" var="p" varStatus="sta">
																<option value="${p.id}" ${p.id==n.projectId?'selected="selected"':''}>${p.project_name }</option>
																</c:forEach>
															</select>
														</div>
													</td>
												</tr>
												<tr>
													<th>需求来源</th>
													<td>
														<select class="form-control chosen-select" name="src_id" id="src_id">
															<option value=""></option>
															<c:forEach items="${needSrc}" var="src" varStatus="sta">
																<option ${n.srcId==src.id?'selected="selected"':'' } value="${src.id}">${src.need_src}</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<th>来源备注</th>
													<td>
														<input type="text" name="src_remark" id="src_remark" value="${n.srcRemark}" class="form-control input-product-title" autocomplete="off">
													</td>
												</tr>
												<tr>
													<th>需求方</th>
													<td>
														<select data-placeholder="请选择需求方" class="form-control chosen-select" name="member_id" id="member_id">
															<option value=""></option>
															<c:forEach items="${members}" var="member" varStatus="sta">
																<option value="${member.id}" ${member.id==n.memberId?'selected="selected"':''}>${member.name}</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<th>指派给</th>
													<td>
														<select data-placeholder="请选择被指派人员" class="form-control chosen-select" name="assigned_id" id="assigned_id">
															<option value=""></option>
															<c:forEach items="${members}" var="member" varStatus="sta">
																<option value="${member.id}" ${member.id==n.assignedId?'selected="selected"':''}>${member.name}</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<th>优先级</th>
													<td>
														<select class="form-control chosen chosen-select"  name="level" id="level">
															<option ${n.level=='1'?'selected="selected"':'' } value="1">紧急重要</option>
															<option ${n.level=='2'?'selected="selected"':'' } value="2">紧急不重要</option>
															<option ${n.level=='3'?'selected="selected"':'' } value="3">不紧急重要</option>
															<option ${n.level=='4'?'selected="selected"':'' } value="4">不紧急不重要</option>
														</select>
													</td>
												</tr>
												<tr>
													<th>开始日期</th>
													<td>
														<input type="text" name="start_date" id="start_date" value="<fmt:formatDate value="${n.startDate}" pattern="yyyy-MM-dd"/>" 
															class="form-control form-date-limit" placeholder="需求开始日期" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
													</td>
												</tr>
												<tr>
													<th>结束日期</th>
													<td>
														<input type="text" name="end_date" id="end_date" value="<fmt:formatDate value="${n.endDate}" pattern="yyyy-MM-dd"/>" 
															class="form-control form-date-limit" placeholder="需求结束日期" autocomplete="off" style="border-radius: 2px 0px 0px 2px;" readonly="readonly">
													</td>
												</tr>
											</tbody>
										</table>
									</div>
									<div class="detail">
										<div class="detail-title">需求的一生</div>
										<table class="table table-form">
											<tbody>
												<tr>
													<th class="w-70px">由谁创建</th>
													<td>A:admin</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</form>
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
								<a href="my/need/toEdit?id=${n.id}" class="btn">继续编辑需求</a>
								<a href="team/task/toAdd" class="btn">建任务</a>
								<a href="team/task/toBatchAdd" class="btn">批量建任务</a>
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
</html>
<script>
UMEditor("comment");
$("#submit").click(function(){
	$.ajaxSettings.async = false;
	$.ajax({type:"POST",url:"my/need/edit?r=" + Math.random(),data:$("form").serialize() + "&comment=" + UM.getEditor('comment').getContent(),dataType:"json",success:function(data){
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