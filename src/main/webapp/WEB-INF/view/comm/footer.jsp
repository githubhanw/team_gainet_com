<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--footer start-->
<footer id="footer">
	<div class="container">
		<div style="height:40px;line-height:40px;text-align:center;font-size:16px;">
			<small>© Copyright 2015-2018. Power by <a href="http://www.zzidc.com/" target="_blank">郑州市景安网络科技股份有限公司</a></small>
		</div>
	</div>
</footer>
<script>
$("[data-toggle='tooltip']").tooltip();
// function UMEditor(id) {
// 	UM.getEditor(id, {
// 		/* 传入配置参数,可配参数列表看umeditor.config.js */
// 	    toolbar: [/*'source | ', */'undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat ',
// 	        '| insertorderedlist insertunorderedlist ',
// 	        '| selectall cleardoc paragraph | fontfamily fontsize' ,
// 	        '| justifyleft justifycenter justifyright justifyjustify ',
// 	        /*'| link unlink | emotion image video  | map',*/
// 	        '| horizontal' 
// 	        /*, 'print preview fullscreen', 'drafts', 'formula'*/]
// 		,initialFrameHeight: 200
// 	});
// }
//限制
$(".form-date-limit").datetimepicker({
    language:  "zh-CN",
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 1,
    forceParse: 0,
    showMeridian: 1,
    startDate:new Date(new Date()-1000 * 60 * 60 * 24 * 2),
    format: "yyyy-mm-dd hh:00"
});
//普通
$(".form-date").datetimepicker({
    language:  "zh-CN",
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 1,
    forceParse: 0,
    showMeridian: 1,
    format: "yyyy-mm-dd hh:00"
});
$('.chosen-select').chosen({
	disable_search_threshold: 10,
	no_results_text: '没有找到',
	disable_search: false,
	search_contains: true
});
$(".btn-expand-all").click(function(){
	if($(this).children().hasClass("icon-minus")){
		$(this).children().addClass("icon-plus").removeClass("icon-minus")
		$(".histories-list").children().each(function(){
			$(this).removeClass("show-changes")
		})
	}else{
		$(this).children().addClass("icon-minus").removeClass("icon-plus")
		$(".histories-list").children().each(function(){
			$(this).addClass("show-changes")
		})
	}
});
$(".btn-expand").click(function(){
	if($(this).parent().hasClass("show-changes")){
		$(this).parent().removeClass("show-changes")
	}else{
		$(this).parent().addClass("show-changes")
	}
});
</script>
<!--footer end-->
