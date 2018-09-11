<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="static/zui-1.8.1-dist/dist/css/zui.css">
<link rel="stylesheet" href="static/zui-1.8.1-dist/dist/css/zh-cn.default.css" type="text/css" media="screen">
<script src="static/zui-1.8.1-dist/dist/lib/jquery/jquery.js"></script>
<script src="static/zui-1.8.1-dist/dist/js/zui.min.js"></script>
<script src="static/zui-1.8.1-dist/dist/js/datetimepicker.js"></script>
<!-- <link rel="stylesheet" href="static/umeditor1.2.3/themes/default/css/umeditor.css" type="text/css">
<script type="text/javascript" charset="utf-8" src="static/umeditor1.2.3/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="static/umeditor1.2.3/umeditor.min.js"></script>
<script type="text/javascript" src="static/umeditor1.2.3/lang/zh-cn/zh-cn.js"></script> -->

<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="static/ueditor/ueditor.all.js"></script>

<style type="text/css" media="screen">
    @font-face {
    font-family:ZentaoIcon;
    font-style:normal;
    font-weight:400;
    src:url(static/zui-1.8.1-dist/dist/fonts/ZentaoIcon.eot);
    src:url(static/zui-1.8.1-dist/dist/fonts/ZentaoIcon.eot) format('embedded-opentype'),
    url(static/zui-1.8.1-dist/dist/fonts/ZentaoIcon.woff) format('woff'),
    url(static/zui-1.8.1-dist/dist/fonts/ZentaoIcon.ttf) format('truetype'),
    url(static/zui-1.8.1-dist/dist/fonts/zenicon.woff) format('woff'),
    url(static/zui-1.8.1-dist/dist/fonts/zenicon.svg) format('svg')
}

.table-children {border-left:2px solid #cbd0db; border-right:2px solid #cbd0db;background:#fafafa}
.table-children.table-child-top {border-top: 2px solid #cbd0db;}
.table-children.table-child-bottom {border-bottom: 2px solid #cbd0db;}
.table td.has-child > .task-toggle {color: #838a9d; position: relative; top: -1px;}
.table td.has-child > .task-toggle:hover {color: #006af1; cursor: pointer;}
.table td.has-child > .task-toggle > .icon {font-size: 16px; display: inline-block; transition: transform .2s;}
.table td.has-child > .task-toggle > .icon:before {text-align: left;}
.table td.has-child > .task-toggle.collapsed > .icon {transform: rotate(-90deg);}
.main-table tbody > tr.table-children > td:first-child::before {width: 3px;}

</style>
<style>
#selectPeriod {padding: 4px 0; height: 197px; min-width: 120px}
#selectPeriod > .dropdown-header {background: #f1f1f1; display: block; text-align: center; padding: 4px 0; line-height: 20px; margin: 5px 10px; font-size: 14px; border-radius: 2px; color: #333; font-size: 12px}
#groupAndOr {display: inline-block;}
#searchForm > table {margin: 0 auto;}
#searchForm > table > tbody > tr > td {padding: 10px 15px; color: #838A9D;}
#searchForm .form-actions {padding-bottom: 20px; padding-top: 0;}
#searchForm .chosen-container[id^="field"] .chosen-drop {min-width: 140px;}
#searchForm [id^="valueBox"] .chosen-container .chosen-single {min-width: 100px;}
#searchForm [id^="valueBox"] .chosen-container .chosen-drop {min-width: 300px;}
#searchForm .chosen-container .chosen-drop ul.chosen-results li {white-space:normal}
#searchForm input.date::-webkit-input-placeholder {color: #000000; opacity: 1;}
#searchForm input.date::-moz-placeholder {color: #000000; opacity: 1;}
#searchForm input.date:-ms-input-placeholder {color: #000000; opacity: 1;}
#searchForm .btn-expand-form {background: transparent;}
#searchForm .btn-expand-form:hover {background: #e9f2fb;}
.showmore .btn-expand-form .icon-chevron-double-down:before {content: '\e959';}

#userQueries {border-left: 1px solid #eee; vertical-align: top;}
#userQueries > h4 {margin: 0 0 6px;}
#userQueries ul {list-style: none; padding-left: 0; margin: 0;}
#userQueries ul li + li {margin-top: 5px;}
#userQueries .label {line-height: 24px; padding: 0 20px 0 8px; display: inline-block; background-color: #EEEEEE; color: #A6AAB8; border-radius: 12px; max-width: 100%; white-space: nowrap; text-overflow: ellipsis; overflow: hidden; position: relative;}
#userQueries .label:hover {background-color: #aaa; color: #fff;}
#userQueries .label > .icon-close {position: absolute; top: 2px; right: 2px; border-radius: 9px; font-size: 12px; line-height: 18px; width: 18px; display: inline-block;}
#userQueries .label > .icon-close:hover {background-color: #ff5d5d; color: #fff;}
@media (max-width: 1150px) {#userQueries {display: none}} 
</style>
<link href="static/zui-1.8.1-dist/dist/lib/chosen/chosen.min.css" rel="stylesheet">
<script src="static/zui-1.8.1-dist/dist/lib/chosen/chosen.min.js"></script>

<script>
    $(document).on('click', '.task-toggle', function(e)
    {
        var $toggle = $(this);
        var id = $(this).data('id');
        var isCollapsed = $toggle.toggleClass('collapsed').hasClass('collapsed');
        $toggle.closest('[data-ride="table"]').find('tr.parent-' + id).toggle(!isCollapsed);

        e.stopPropagation();
        e.preventDefault();
    });
</script>