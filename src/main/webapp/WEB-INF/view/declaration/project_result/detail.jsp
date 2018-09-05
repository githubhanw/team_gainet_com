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
		<title>成果：${pr.project_result_name}</title>
    	<%@ include file="/WEB-INF/view/comm/cssjs.jsp" %>
	</head>
	<body>
	    <!--header start-->
	    <header id="header">
	    	<%@ include file="/WEB-INF/view/comm/main_header.jsp" %>
	    	<%@ include file="/WEB-INF/view/comm/sub_header.jsp" %>
	    </header>
	    <!--header end-->
		<main id="main">
			<div class="container">
				<div id="mainMenu" class="clearfix">
					<div class="btn-toolbar pull-left">
						<a href="javascript:history.go(-1);" class="btn btn-link"><i class="icon icon-back icon-sm"></i> 返回</a>
						<div class="divider"></div>
						<div class="page-title">
							<span class="label label-id">${pr.id}</span> <span class="text" style="color: #ffaf38">${pr.project_result_name}</span>
						</div>
					</div>
				</div>
				<div id="mainContent" class="main-row">
					<div class="main-col col-8">
						<div class="cell">
							<div class="detail">
								<div class="detail-title">所需文档</div>
								<div class="detail-content article-content">
									<table class="table table-hover table-fixed">
										<thead>
											<tr class="text-center">
												<th class="w-50px">编号</th>
												<th>文档名称</th>
												<th>文档类型</th>
												<th>文档地址</th>
												<th class="w-80px">文档状态</th>
												<th class="w-120px">预计提供时间</th>
												<th >操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${doc}" var="item" varStatus="sta">
											<tr class="text-center">
												<td>${item.id}</td>
												<td>${item.doc_name }</td>
												<td>${item.project_doc_type }</td>
												<td>${item.project_doc_url}</td>
												<td>${item.doc_state == 1 ? '已提供' : '未提供'}</td>
												<td>${item.provide_date}</td>
												<td class="c-actions">
													<a
													href="declaration/doc/toAdd?id=${item.id}"
													class="btn " title="编辑"><i
														class="icon-common-edit icon-edit"></i></a>
												</td>
											</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
                        <div class="detail histories" id="actionbox" data-textdiff="文本格式" data-original="原始格式">
                            <div class="detail-title">
								历史记录（暂未实现）
                                <button type="button" class="btn btn-mini btn-icon btn-reverse" title="切换顺序">
                                    <i class="icon icon-arrow-up icon-sm"></i>
                                </button>
                                <button type="button" class="btn btn-mini btn-icon btn-expand-all" title="切换显示">
                                    <i class="icon icon-plus icon-sm"></i>
                                </button>
                                <button type="button" class="btn btn-link pull-right btn-comment"><i class="icon icon-chat-line"></i> 添加备注</button>
                                <div class="modal fade modal-comment">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal"><i class="icon icon-close"></i></button>
                                                <h4 class="modal-title">添加备注</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form action="#" target="hiddenwin" method="post">
                                                    <div class="form-group">
                                                        <textarea id="content" name="content" class="form-control kindeditor" style="height:150px;">Hello, world!</textarea>
                                                    </div>
                                                    <div class="form-group form-actions text-center">
                                                        <button type="submit" class="btn btn-primary btn-wide">保存</button>
                                                        <input type="hidden" id="uid" name="uid" value="5b4c2efc98fe7">
                                                        <button type="button" class="btn btn-wide" data-dismiss="modal">关闭</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="detail-content">
                                <ol class="histories-list">
                                    <li value="1">
                                        2018-07-13 23:41:03, 由 <strong>admin</strong> 创建。
                                    </li>
                                    <li value="2">
                                        2018-07-16 13:36:40, 由 <strong>admin</strong> 添加备注。
                                        <button type="button" class="btn btn-link btn-icon btn-sm btn-edit-comment" title="修改备注">
                                            <i class="icon icon-pencil"></i>
                                        </button>
                                    <div class="article-content comment">
                                    磊
                                    </div>
                                    <form method="post" class="comment-edit-form" action="">
                                    <div class="form-group">

                                    <textarea id="content" name="content" class="form-control kindeditor" style="height:150px;">Hello, world!</textarea>
                                    </div>
                                    <div class="form-group form-actions">
                                    <button type="submit" id="submit" class="btn btn-primary btn-wide" data-loading="稍候...">保存</button><input type="hidden" id="uid" name="uid" value="5b4c2efc98fe7">           <button type="button" class="btn btn-wide btn-hide-form">关闭</button>          </div>
                                    </form>
                                    </li>
                                </ol>
                            </div>
                        </div>
						</div>
					</div>
					<div class="side-col col-4">
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">基本信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>成果类型</th>
												<td>
													${pr.type == 1 ? '软著' : pr.type == 2 ? '发明专利' : pr.type == 3 ? '实用新型专利' : pr.type == 4 ? '外观专利' : pr.type == 5 ? '商标' : '未知'}
												</td>
											</tr>
											<tr class="nofixed">
												<th>所属项目</th>
												<td>${pr.project_name}</td>
											</tr>
											<tr>
												<th>证书号</th>
												<td>${pr.cert_number}</td>
											</tr>
											<tr>
												<th>登记号</th>
												<td>${pr.registration_number}</td>
											</tr>
											<tr>
												<th>撰写人</th>
												<td>${pr.member_name}</td>
											</tr>
											<tr>
												<th>所属公司</th>
												<td>${pr.company}</td>
											</tr>
											<tr>
												<th>代理商</th>
												<td>${pr.agent}</td>
											</tr>
											<tr>
												<th>状态</th>
												<td>${pr.state == 1 ? '已下证' : pr.state == 2 ? '待撰写' : pr.state == 3 ? '撰写中' : pr.state == 4 ? '已提交' : pr.state == 5 ? '已受理' : pr.state == 0 ? '已删除' : '未知'}</td>
											</tr>
											<c:if test="${pr.type == 1}">
												<tr>
													<th>版本号</th>
													<td>${pr.version}</td>
												</tr>
											</c:if>
											<c:if test="${pr.type == 2 || pr.type == 3 || pr.type == 4}">
												<tr>
													<th>发明人</th>
													<td>${pr.inventor}</td>
												</tr>
											</c:if>
										</tbody>
									</table>
								</div>
							</details>
						</div>
						<!-- <div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">工时信息</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>预计开始</th>
												<td>0000-00-00</td>
											</tr>
											<tr>
												<th>实际开始</th>
												<td>0000-00-00</td>
											</tr>
											<tr>
												<th>截止日期</th>
												<td>0000-00-00</td>
											</tr>
											<tr>
												<th>最初预计</th>
												<td>0工时</td>
											</tr>
											<tr>
												<th>总消耗</th>
												<td>0工时</td>
											</tr>
											<tr>
												<th>预计剩余</th>
												<td>0工时</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div> -->
						<div class="cell">
							<details class="detail" open="">
								<summary class="detail-title">成果的一生</summary>
								<div class="detail-content">
									<table class="table table-data">
										<tbody>
											<tr>
												<th>由谁创建</th>
												<td>${pr.member_name } 于 <fmt:formatDate value="${pr.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											</tr>
											<tr>
												<th>申请日期</th>
												<td>${pr.application_date }</td>
											</tr>
											<tr>
												<th>受理日期</th>
												<td>${pr.acceptance_date }</td>
											</tr>
										</tbody>
									</table>
								</div>
							</details>
						</div>
					</div>
				</div>
				<!--mainActions start-->
				<div id="mainActions">
					<nav class="container">
					</nav>
					<div class="btn-toolbar">
						<a href="javascript:history.go(-1);" id="back" class="btn" title="返回[快捷键:Alt+↑]">
							<i class="icon-goback icon-back"></i> 返回
						</a>
						<div class="divider"></div>
						<a href="declaration/result/toAdd?id=${pr.id}" class="btn btn-link " title="编辑">
							<i class="icon-common-edit icon-edit"></i>
						</a>
					</div>
				</div>
				<!--mainActions end-->
			</div>
		</main>
		<%@ include file="/WEB-INF/view/comm/footer.jsp" %>
	</body>
</html>
<script>
	$("#submit").click(function() {
		var projectResultName = encodeURI(encodeURI($("#name").val()));
		var projectId = $("#projectId").val();
		$.ajaxSettings.async = false;
		$.ajax({
			type : "POST",
			url : "declaration/result/addOrUpd?r=" + Math.random(),
			data : $("form").serialize(),
			dataType : "json",
			success : function(data) {
				alert(data.message);
			}
		})
		/* $.getJSON("declaration/result/addOrUpd?r=" + Math.random(), $("form").serialize(), function(data) {
			alert(data.message);
		}); */
		$.ajaxSettings.async = true;
	});
</script>