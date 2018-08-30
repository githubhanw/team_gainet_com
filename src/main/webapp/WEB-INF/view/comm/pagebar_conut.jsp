<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--
使用说明:
	此为通用分页的jsp,引用说明
	使用时:
		第一步:只需在原来的jsp页面引用的老pagebar.jsp 替换为pagebar_conut.jsp 
		第二步:到jsp页面所在的service中的查找列表方法中添加相应的SQL即可
			(PS:例子路径 finance/FinanceController中的/list-> GiantPager resultPager = financeService.list(sourcePager, 3); 进入方法
			String conutValue 这个变量就是作为分页查询必须使用的,自己使用的时候看好SQL conut(1)的时候把SQL优化好[例如倒序,不需要的字段就不要再作为查询总量的条件了,
			还有需要作为查询的条件也要统一拼接到SQL后面,否则容易查造成总页数与实际页数不匹配导致查不到结果],不然程序查的列表会很慢)
		第三步:找到returnPage.setQueryCondition(sourcePage.getQueryCondition()); 这行代码,并再之后添加:
			returnPage.setorderColumn(sourcePage.getorderColumn());
			returnPage.setTotalCounts(dao.getGiantCounts(conutValue, conditionMap));//参数:新加的conut(1)的SQL  查询条件的map集合
		建议:	①如果SQL优化完的查询结果依然还是一分钟往上的话,建议还是使用pagebar.jsp 不要使用新的分页.
				②自己新增conut(1)的SQL一定要去掉所有的不必要的条件,不要懒省事使用子查询,搜索的时长结果会让人很崩溃
				③具体的可以看例子代码,自己完善
--%>
<style>
	.share-menu a {
	    color: rgb(3, 108, 180);
	    padding: 5px 12px;
	    border-width: 1px;
	    border-style: solid;
	    border-color: rgb(238, 238, 238);
	    border-image: initial;
	    text-decoration: none;
	}
	.share-menu a:hover {
	    color: rgb(102, 102, 102);
	    border-width: 1px;
	    border-style: solid;
	    border-color: rgb(153, 153, 153);
	    border-image: initial;
	}
	.share-menu .current {
	    font-weight: bold;
	    color: rgb(255, 255, 255);
	    border-width: 1px;
	    border-style: solid;
	    border-color: rgb(3, 108, 180);
	    border-image: initial;
	    padding: 5px 12px;
	    background: rgb(3, 108, 180);
	}
	.share-menu .disabled {
	    padding: 5px 12px;
	    border-width: 1px;
	    border-style: solid;
	    border-color: rgb(238, 238, 238);
	    border-image: initial;
	    text-decoration: none;
	}
</style>
<div class="share-menu" style="height: 35px; line-height: 32px;text-align: left;width:100%;_width:auto;">
	
	<c:if test="${ pageList.pageResult != null}">
		<form id="pageForm" action="${pageList.desAction}" method="post">
		<input type="hidden" id="pageSize" name="pageSize" value="${pageList.pageSize}" />
		<c:forEach items="${pageList.queryCondition }" var="query">
			<c:if test="${query.value!=null }">
				<input type="hidden" value="${query.value}" name="${query.key}"/>
			</c:if>
		</c:forEach>
			<span>共有${pageList.totalCounts }条记录</span>
			&nbsp;&nbsp;&nbsp;
			<c:if test="${ pageList.currentPage==1 || pageList.totalPages == 0}">
				<span class="disabled" hidefocus>首页</span>
				<span class="disabled" hidefocus>上一页</span>
			</c:if>
			<c:if test="${ pageList.currentPage !=1 && pageList.totalPages != 0}">
				<a href="javascript:gotoPage(${ 1});" hidefocus>首页</a>
				<a href="javascript:gotoPage(${pageList.currentPage-1 });" hidefocus>上一页</a>
			</c:if>
			
			
			<c:if test="${ 9 >= pageList.totalPages}">
				<c:forEach begin="1" end="${ pageList.currentPage - 1}" var="num1">
					<a href="javascript:gotoPage(${num1});" hidefocus>${num1}</a>
				</c:forEach>
					<span class="current" hidefocus><strong>${pageList.currentPage}</strong></span>
				<c:forEach begin="${ pageList.currentPage + 1}" end="${ pageList.totalPages}" var="num2">
					<a href="javascript:gotoPage(${num2 });" hidefocus>${num2}</a>
				</c:forEach>
			</c:if>
			
			<c:if test="${ pageList.totalPages > 9}">
				<c:if test="${ pageList.currentPage == 1}">
					<span class="current" hidefocus><strong>${pageList.currentPage}</strong></span>
					<c:forEach begin="${ pageList.currentPage + 1}" end="${ pageList.currentPage + 3}" var="num3">
						<a href="javascript:gotoPage(${num3 });" hidefocus>${num3}</a>
					</c:forEach>
					...
					<a href="javascript:gotoPage(${pageList.totalPages });" hidefocus>${pageList.totalPages}</a>
				</c:if>
				<c:if test="${ pageList.currentPage > 1 && 5 >= pageList.currentPage}">
					<c:forEach begin="1" end="${ pageList.currentPage - 1}" var="num4">
						<a href="javascript:gotoPage(${num4 });" hidefocus>${num4}</a>
					</c:forEach>
					<span class="current" hidefocus><strong>${pageList.currentPage}</strong></span>
					<c:forEach begin="${ pageList.currentPage + 1}" end="${ pageList.currentPage + 3}" var="num5">
						<a href="javascript:gotoPage(${num5 });" hidefocus>${num5}</a>
					</c:forEach>
					...
					<a href="javascript:gotoPage(${pageList.totalPages });" hidefocus>${pageList.totalPages}</a>
				</c:if>
				<c:if test="${ pageList.currentPage > 5 && (pageList.totalPages - 4) >= pageList.currentPage}">
					<a href="javascript:gotoPage(1);" hidefocus>1</a>
					...
					<c:forEach begin="${ pageList.currentPage - 3}" end="${ pageList.currentPage - 1}" var="num6">
						<a href="javascript:gotoPage(${num6 });" hidefocus>${num6}</a>
					</c:forEach>
					<span class="current" hidefocus><strong>${pageList.currentPage}</strong></span>
					<c:forEach begin="${ pageList.currentPage + 1}" end="${ pageList.currentPage + 3}" var="num7">
						<a href="javascript:gotoPage(${num7 });" hidefocus>${num7}</a>
					</c:forEach>
					...
					<a href="javascript:gotoPage(${pageList.totalPages });" hidefocus>${pageList.totalPages}</a>
				</c:if>
				<c:if test="${ pageList.currentPage > (pageList.totalPages - 4) && pageList.totalPages > pageList.currentPage}">
					<a href="javascript:gotoPage(1);" hidefocus>1</a>
					...
					<c:forEach begin="${ pageList.currentPage - 3}" end="${ pageList.currentPage - 1}" var="num7">
						<a href="javascript:gotoPage(${num7 });" hidefocus>${num7}</a>
					</c:forEach>
					<span class="current" hidefocus><strong>${pageList.currentPage}</strong></span>
					<c:forEach begin="${ pageList.currentPage + 1}" end="${ pageList.totalPages}" var="num8">
						<a href="javascript:gotoPage(${num8 });" hidefocus>${num8}</a>
					</c:forEach>
				</c:if>
				<c:if test="${ pageList.currentPage == pageList.totalPages}">
					<a href="javascript:gotoPage(${1});" hidefocus>1</a>
					...
					<c:forEach begin="${ pageList.currentPage - 3}" end="${ pageList.currentPage - 1}" var="num9">
						<a href="javascript:gotoPage(${num9 });" hidefocus>${num9}</a>
					</c:forEach>
					<span class="current" hidefocus><strong>${pageList.currentPage}</strong></span>
				</c:if>
			</c:if>
			
			
			<c:if test="${ pageList.currentPage==pageList.totalPages || pageList.totalPages == 0}">
				<span class="disabled" hidefocus>下一页</span>
				<span class="disabled" hidefocus>尾页</span>
			</c:if>
			<c:if test="${ !( pageList.currentPage==pageList.totalPages || pageList.totalPages == 0)}">
				<a href="javascript:gotoPage(${ pageList.currentPage + 1});" >下一页</a>
				<a href="javascript:gotoPage(${pageList.totalPages });" >尾页</a>
			</c:if>
			
			&nbsp;&nbsp;&nbsp;
			<td colspan="2"> 每页展示：
				<c:if test="${ pageList.pageSize != null && pageList.pageSize != ''}">
					<select id="pageSize_cite" name="pageSize" data="pageSize" onchange="meiYeSubmit()" style="height:25px">
						<option <c:if test="${pageList.pageSize==10 }">selected="selected"</c:if> value="10" >10条</option>
						<option <c:if test="${pageList.pageSize==15 }">selected="selected"</c:if> value="15" >15条</option>
						<option <c:if test="${pageList.pageSize==30 }">selected="selected"</c:if> value="30" >30条</option>
						<option <c:if test="${pageList.pageSize==100 }">selected="selected"</c:if> value="100" >100条</option>
					</select>
				</c:if>
				<c:if test="${ pageList.pageSize == null || pageList.pageSize == ''}">
					<cite id="pageSize_cite" data="pageSize">每页10条</cite>
				</c:if>
			</td>
			 共<span class="share-ment-txt">${pageList.totalPages}</span>页   
			&nbsp;&nbsp;&nbsp;
			 跳转到
			<input id="jumpPage_id" value="${pageList.currentPage }" type="text" class="share-menu-input" onkeydown="if(event.keyCode==13){goPage2();return false;}" style="height:23px;width:50px; line-height:23px;display:inline-block;vertical-align: top;margin:0px;margin-top:5px;*margin-top:0px;_margin-top:-1px;margin-top:4px\0;"/>
			<input id="currentpage_jumppage" value="${pageList.currentPage }" type="hidden"/>
			<input id="totalpage_jumppage" value="${pageList.totalPages }" type="hidden"/>
			<c:if test="${ pageList.totalPages == 0}">
				<button class="share-menu-button" onclick="javascript:goPage2();" disabled="disabled" style="margin:0px;height:25px;line-height: normal;display:inline-block;vertical-align: top;margin-top: 5px;*margin-top:0px;_margin-top:0px;margin-top:4px\0;" hidefocus ondblclick="javascript:void(0);">确定</button>
			</c:if>
			<c:if test="${ pageList.totalPages != 0}">
				<button class="share-menu-button" onclick="javascript:goPage2();" style="margin:0px;height:25px;line-height: normal;display:inline-block;vertical-align: top;margin-top: 5px;*margin-top:0px;_margin-top:0px;margin-top:4px\0;" hidefocus ondblclick="javascript:void(0);">确定</button>
			</c:if>
<%--			<input id="pageNum" type='hidden' name='currentPage' value="${currentPage }"/>--%>
		</form>
	</c:if>
</div>
<!--分页信息-->
<script type="text/javascript">
	function pageOrder(orderColumn){
		if(orderColumn == $("input[name='orderColumn']").val()){
			if($("input[name='orderByValue']").val() == 'ASC'){
				$("input[name='orderByValue']").val("DESC");
			}else{
				$("input[name='orderByValue']").val("ASC");
			}
		}else{
			$("input[name='orderColumn']").val(orderColumn);
			$("input[name='orderByValue']").val("DESC");
		}
		$("#pageForm").submit();
	}
	function gotoPage(pageNum){
		$("input[name='currentPage']").val(pageNum);
		$("#pageForm").submit();
	}
	function meiYeSubmit(){
		$("#pageForm").find("#pageSize").val($("#pageSize_cite").val());
		$("#pageForm").submit();
	}
	function goPage2(){
		p=document.getElementById("jumpPage_id").value;
		var ex = /^\d+$/;//整数数字
		if(isNaN(p)||parseInt(p)>${pageList.totalPages }||parseInt(p)<1||!ex.test(p)){
			$("#jumpPage_id").val(${pageList.currentPage });
			gotoPage($("#jumpPage_id").val());
			//return false;
		} else {
			gotoPage(p);
		}
	}
</script>