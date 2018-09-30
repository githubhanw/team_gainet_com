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
		<title>${task.taskName}::建用例</title>
		<style>
		.table-form > tbody > tr > td .btn-group-vertical > .btn {margin-left: 0!important; padding: 2px 8px}
#steps .step-id {text-align: right}
#steps .step-item-id {background-color: transparent; border: none; display: none; width: 30px; padding-left: 0; padding-right: 0; text-align: right; padding-right: 8px}
#steps .checkbox-inline input[type="checkbox"] {top: -2px}
#steps .btn-group .btn {padding-left: 0; padding-right: 0; min-width: 30px;}
.step-actions {width: 105px;}
#steps .active td {transition: background-color .5s;}
#steps .step-group .step-steps {resize: none; max-height: 30px;}
#steps .step-group .step-expects {display: none;}
#steps .step-item .step-item-id {display: table-cell; min-width: 40px;}
#steps .step-item .step-id {color: transparent}
#steps .step-group .step-id {font-weight: bold}
#steps .step-actions {width: 105px; text-align: center;}

#steps .btn-step-move {cursor: move}
#steps.sortable > tr.drag-shadow {display: none}
#steps.sortable-sorting > tr {transition: all .2s; position: relative; z-index: 5; opacity: .3;}
#steps.sortable-sorting {cursor: move;}
#steps.sortable-sorting > tr.drag-row {opacity: 1; z-index: 10; box-shadow: 0 2px 4px red}
#steps.sortable-sorting > tr.drag-row + tr > td {box-shadow: inset 0 4px 2px rgba(0,0,0,.2)}
#steps.sortable-sorting > tr.drag-row > td {background-color: #edf3fe!important}
#steps.sortable > tr.drop-success > td {background-color: #cfe0ff; transition: background-color 2s;}
#steps .step-type-toggle {padding: 5px 11px 5px 7px;}
#steps .step textarea.autosize{resize:none;}

.table-bordered .step-actions {width: 105px;}
.table-bordered > #steps > tr > td {padding: 0; background-color: #fafafa; border: 1px solid #ddd!important; border-left-style: dotted!important; border-right-style: dotted!important;}
.table-bordered > #steps > tr > td.step-id { padding-right: 8px;}
.table-bordered > #steps > tr > td .btn {border-color: transparent; background-color: transparent}
.table-bordered > #steps > tr > td .btn:hover,
.table-bordered > #steps > tr > td .btn:focus {background-color: #ddd}
.table-bordered > #steps .input-group-addon {border: none; background-color: transparent; border-left: 1px dotted #e5e5e5}
.table-bordered > #steps > tr > td textarea {border-color: #fff; box-shadow: none}
.table-bordered > #steps > tr > td textarea:hover {border-color: #808080;}
.table-bordered > #steps > tr > td textarea:focus {border-color: #4d90fe;}
.table-bordered > #steps .step-child .step-child-id {border-right: 1px dotted #ddd; border-left: none}
#searchStories .searchInput {position: relative;}
#searchStories .searchInput .icon {position: absolute; display: block; left: 9px; top: 9px; z-index: 5; color: #808080;}
#storySearchInput {padding-left: 30px;}

#searchStories .modal-body {height: 300px; overflow-y: auto; padding: 0;}
#searchResult {padding-left: 0; list-style: none; width: 100%}
#searchResult > li {display: block}
#searchResult > li.tip {padding: 6px 15px; color: #808080}
#searchResult > li.loading {text-align: center; padding: 50px}
#searchResult > li.loading > .icon-spinner:before {font-size: 28px;}
#searchResult > li > a {display: block; padding: 6px 15px; color: #333; border-bottom: 1px solid #e5e5e5}
#searchResult > li > a:hover, #searchResult > li > a.selected {color: #1a4f85; background-color: #ddd;}

#story_chosen .chosen-results > li.no-results {cursor: pointer;}
#story_chosen .chosen-results > li.no-results:hover {color: #1a4f85; background-color: #ddd;}
#story_chosen .chosen-results > li.no-results > span {font-weight: bold;}
#module_chosen .chosen-drop {min-width: 400px; border-top: 1px solid #ddd!important}

.row .col-sm-10{width:89%;}
.row .col-sm-2{padding-left:0px; width:11%;}

#module + .chosen-container-single .chosen-single,
#stage + .chosen-container-multi .chosen-choices {border-top-left-radius: 0; border-bottom-left-radius: 0; margin-left: -1px;}
#module + .chosen-container-single .chosen-single {border-top-right-radius: 0; border-bottom-right-radius: 0; margin-left: -1px;}

.dropdown-pris > .btn {background-color: #fff; text-shadow: none}

#moduleIdBox .input-group-btn > .btn {margin-left: -1px!important;}
#moduleIdBox .input-group-btn > .btn:first-child {border-left: none}

.chosen-container {max-width: 1000px;}

.minw-80px {min-width: 80px;}

.colorpicker.input-group-btn > .btn {border-right: none}

#branch {border-left-width: 0px;}

.input-group-addon.w-80px{min-width:80px;}</style>
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
								<span class="label label-id">${task.id}</span>
								<a href="team/task/detail?id=${task.id}">${task.taskName}</a>
								&nbsp;<i class="icon-angle-right"></i>&nbsp; 建用例
							</h2>
						</div>
						<table class="table table-form">
							<tbody>
								<form class="load-indicator main-form form-ajax" id="createForm" method="post">
								<tr>
									<th>用例名称</th>
									<td class="required" width="1000px">
										<input type="text" name="case_name" id="case_name" value="" class="form-control input-product-title" autocomplete="off"/>
										<input type="hidden" name="task_id" value="${task.id}"/>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>用例类型</th>
									<td class="required">
										<select class="form-control chosen chosen-select" name="case_type" id="case_type">
											<option value="1">功能测试</option>
											<option value="2">性能测试</option>
											<option value="3">配置相关</option>
											<option value="4">安装部署</option>
											<option value="5">安全相关</option>
											<option value="6">接口测试</option>
											<option value="7">其他</option>
										</select>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>前置条件</th>
									<td class="required">
										<input type="text" name="condition" id="condition" value="" class="form-control input-product-title" autocomplete="off">
									</td>
									<td></td>
								</tr>
								<tr>
									<th>用例步骤</th>
									<td class="required">
										<table class="table table-form mg-0 table-bordered" style="border: 1px solid #ddd">
											<thead>
												<tr>
													<th class="w-50px text-right">编号</th>
													<th width="45%">步骤</th>
													<th>预期</th>
													<th class="step-actions">操作</th>
												</tr>
											</thead>
											<tbody id='steps' class='sortable' data-group-name='分组名称'>
												<tr class='template step' id='stepTemplate'>
													<td class='step-id'></td>
													<td>
														<div class='input-group'>
															<span class='input-group-addon step-item-id'></span>
															<textarea rows='1' class='form-control autosize step-steps' name='steps[]'></textarea>
														</div>
													</td>
													<td><textarea rows='1'
															class='form-control autosize step-expects'
															name='expects[]'></textarea></td>
													<td class='step-actions'>
														<div class='btn-group'>
															<button type='button' class='btn btn-step-add'
																tabindex='-1'>
																<i class='icon icon-plus'></i>
															</button>
															<button type='button' class='btn btn-step-move'
																tabindex='-1'>
																<i class='icon icon-move'></i>
															</button>
															<button type='button' class='btn btn-step-delete'
																tabindex='-1'>
																<i class='icon icon-trash'></i>
															</button>
														</div>
													</td>
												</tr>
												<tr class='step'>
													<td class='step-id'></td>
													<td>
														<div class='input-group'>
															<span class='input-group-addon step-item-id'></span>
															<textarea name='steps[]' id='steps[]' rows='1'
																class='form-control autosize step-steps'></textarea>
														</div>
													</td>
													<td><textarea name='expects[]' id='expects[]' rows='1'
															class='form-control autosize step-expects'></textarea></td>
													<td class='step-actions'>
														<div class='btn-group'>
															<button type='button' class='btn btn-step-add'
																tabindex='-1'>
																<i class='icon icon-plus'></i>
															</button>
															<button type='button' class='btn btn-step-move'
																tabindex='-1'>
																<i class='icon icon-move'></i>
															</button>
															<button type='button' class='btn btn-step-delete'
																tabindex='-1'>
																<i class='icon icon-trash'></i>
															</button>
														</div>
													</td>
												</tr>
												<tr class='step'>
													<td class='step-id'></td>
													<td>
														<div class='input-group'>
															<span class='input-group-addon step-item-id'></span>
															<textarea name='steps[]' id='steps[]' rows='1'
																class='form-control autosize step-steps'></textarea>
														</div>
													</td>
													<td><textarea name='expects[]' id='expects[]' rows='1'
															class='form-control autosize step-expects'></textarea></td>
													<td class='step-actions'>
														<div class='btn-group'>
															<button type='button' class='btn btn-step-add'
																tabindex='-1'>
																<i class='icon icon-plus'></i>
															</button>
															<button type='button' class='btn btn-step-move'
																tabindex='-1'>
																<i class='icon icon-move'></i>
															</button>
															<button type='button' class='btn btn-step-delete'
																tabindex='-1'>
																<i class='icon icon-trash'></i>
															</button>
														</div>
													</td>
												</tr>
												<tr class='step'>
													<td class='step-id'></td>
													<td>
														<div class='input-group'>
															<span class='input-group-addon step-item-id'></span>
															<textarea name='steps[]' id='steps[]' rows='1'
																class='form-control autosize step-steps'></textarea>
														</div>
													</td>
													<td><textarea name='expects[]' id='expects[]' rows='1'
															class='form-control autosize step-expects'></textarea></td>
													<td class='step-actions'>
														<div class='btn-group'>
															<button type='button' class='btn btn-step-add'
																tabindex='-1'>
																<i class='icon icon-plus'></i>
															</button>
															<button type='button' class='btn btn-step-move'
																tabindex='-1'>
																<i class='icon icon-move'></i>
															</button>
															<button type='button' class='btn btn-step-delete'
																tabindex='-1'>
																<i class='icon icon-trash'></i>
															</button>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>备注</th>
									<td>
										<div id="remark" style="width:100%;">
											<input type="hidden" name="remark">
										</div>
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
						<div style="margin: 0 auto">
							<p><strong><span id="msg" style="font-size:18px">成功</span></strong></p><br/><br/>
							<hr class="small"/>
							<p><strong>您现在可以进行以下操作：</strong></p>
							<div>
								<a href="javascript:history.go(-1);" class="btn">返回</a>
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
	$.ajax({type:"POST",url:"test/case/add?r=" + Math.random(),data:$("form").serialize(),
			dataType:"json",success:function(data){
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
/**
 * Init testcase steps in form
 * 
 * @param  string selector
 * @access public
 * @return void
 */
function initSteps(selector)
{
    $(document).on('input keyup paste change', 'textarea.autosize', function()
    {
        var height = (this.scrollHeight + 2) + "px";
        this.style.height = 'auto';
        this.style.height = height; 
        $(this).closest('tr').find('textarea').each(function()
        {
            this.style.height = height; 
        });
    });

    var $steps = $(selector || '#steps');
    var $stepTemplate = $('#stepTemplate').detach().removeClass('template').attr('id', null);
    var initSortableCallTask = null;
    var groupNameText = $steps.data('groupName');
    var insertStepRow = function($row, count, type, notFocus)
    {
        if(count === undefined) count = 1;
        var $step;
        for(var i = 0; i < count; ++i)
        {
            $step = $stepTemplate.clone();
            if($row) $row.after($step);
            else $steps.append($step);
            $step.addClass('step-new');
            if(type) $step.find('.step-type').val(type);
        }
        if(!notFocus && $step) setTimeout(function(){$step.find('.step-steps').focus();}, 10);
    };
    var updateStepType = function($step, type)
    {
        var targetIsGroup = type =='group';
        $step.attr('data-type', type).find('.step-steps').toggleClass('autosize', !targetIsGroup).attr('placeholder', targetIsGroup ? groupNameText : null);
    };
    var getStepsElements = function()
    {
        return $steps.children('.step:not(.drag-shadow)');
    };
    var refreshSteps = function()
    {
        var parentId = 1, childId = 0;
        getStepsElements().each(function(idx)
        {
            var $step = $(this).attr('data-index', idx + 1);
            var type = $step.find('.step-type').val();
            var stepID;
            if(type == 'group')
            {
                $step.removeClass('step-item step-step').addClass('step-group');
                stepID = parentId++;
                $step.find('.step-id').text(stepID);
                childId = 1;
            }
            else if(type == 'step')
            {
                $step.removeClass('step-item step-group').addClass('step-step');
                stepID = parentId++;
                $step.find('.step-id').text(stepID);
                childId = 0;
            }
            else // step type is not set
            {
                if(childId) // type as child
                {
                    stepID = (parentId - 1) + '.' + (childId++);
                    $step.removeClass('step-step step-group').addClass('step-item').find('.step-item-id').text(stepID);
                }
                else // type as step
                {
                    $step.removeClass('step-item step-group').addClass('step-step');
                    stepID = parentId++;
                    $step.find('.step-id').text(stepID);
                }
            }
            $step.find('[name^="steps["]').attr('name', "steps[" +stepID + ']');
            $step.find('[name^="stepType["]').attr('name', "stepType[" +stepID + ']');
            $step.find('[name^="expects["]').attr('name', "expects[" +stepID + ']');

            updateStepType($step, type);
        });
    };
    var initSortable = function()
    {
        var isMouseDown = false;
        var $moveStep = null, moveOrder = 0;
        $steps.on('mousedown', '.btn-step-move', function()
        {
            isMouseDown = true;
            $moveStep = $(this).closest('.step').addClass('drag-row');
            
            $(document).off('.sortable').one('mouseup.sortable', function()
            {
                isMouseDown = false;
                $moveStep.removeClass('drag-row');
                $steps.removeClass('sortable-sorting');
                $moveStep = null;
                refreshSteps();
            });
            $steps.addClass('sortable-sorting');
        }).on('mouseenter', '.step:not(.drag-row)', function()
        {
            if(!isMouseDown) return;
            var $targetStep = $(this);
            getStepsElements().each(function(idx)
            {
                $(this).data('order', idx);
            });
            moveOrder = $moveStep.data('order');
            var targetOrder = $targetStep.data('order');
            if(moveOrder === targetOrder) return;
            else if(targetOrder > moveOrder)
            {
                $targetStep.after($moveStep);
            }
            else if(targetOrder < moveOrder)
            {
                $targetStep.before($moveStep);
            }
        });
    }
    $steps.on('click', '.btn-step-add', function()
    {
        insertStepRow($(this).closest('.step'));
        refreshSteps();
    }).on('click', '.btn-step-delete', function()
    {
        if($steps.children('.step').length == 1) return;
        $(this).closest('.step').remove();
        refreshSteps();
    }).on('change', '.step-group-toggle', function()
    {
        var $checkbox = $(this);
        var $step = $checkbox.closest('.step');
        var isChecked = $checkbox.is(':checked');
        var suggestType = isChecked ? 'group' : 'item';
        if(!isChecked) 
        {
            var $prevStep = $step.prev('.step:not(.drag-shadow)');
            var suggestChild = $prevStep.length && $prevStep.is('.step-group') && $step.next('.step:not(.drag-shadow)').length;
            suggestType = suggestChild ? 'item' : 'step';
        }
        $step.find('.step-type').val(suggestType);
        refreshSteps();
    }).on('change', '.form-control', function()
    {
        var $control = $(this);
        if($control.val())
        {
            var $step = $control.closest('.step');
            if($step.data('index') === getStepsElements().length)
            {
                insertStepRow($step, 1, 'step', true);
                if($step.is('.step-item,.step-group')) insertStepRow($step, 1, 'item', true);
                refreshSteps();
            }
        }
    });
    initSortable();
    refreshSteps();
}
initSteps();
</script>