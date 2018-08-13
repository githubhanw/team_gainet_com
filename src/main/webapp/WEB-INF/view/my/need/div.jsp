<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="modal fade" id="closeModal">
	<div class="modal-dialog" style="width:900px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">关闭</span>
				</button>
				<h4 class="modal-title">
					<span class="label label-id">${n.id}</span>
					<a href="my/need">${n.needName}</a>
					<small>&nbsp;<i class="icon-angle-right"></i>&nbsp; 关闭</small>
				</h4>
			</div>
			<div class="modal-body">
				<form method="post" target="hiddenwin">
					<table class="table table-form">
						<tbody>
							<tr>
								<th class="w-80px">关闭原因</th>
								<td class="w-p25-f required"><select name="closedReason"
									id="closedReason" class="form-control"
									onchange="setStory(this.value)">
										<option value="" selected="selected" title="" data-keys=" "></option>
										<option value="done" title="已完成" data-keys="yiwancheng ywc">已完成</option>
										<option value="subdivided" title="已细分" data-keys="yixifen yxf">已细分</option>
										<option value="duplicate" title="重复" data-keys="zhongfu zf">重复</option>
										<option value="postponed" title="延期" data-keys="yanqi yq">延期</option>
										<option value="willnotdo" title="不做" data-keys="buzuo bz">不做</option>
										<option value="cancel" title="已取消" data-keys="yiquxiao yqx">已取消</option>
										<option value="bydesign" title="设计如此"
											data-keys="shejiruci sjrc">设计如此</option>
								</select></td>
								<td></td>
							</tr>
							<tr id="duplicateStoryBox" style="display: none">
								<th>重复需求</th>
								<td><input type="text" name="duplicateStory"
									id="duplicateStory" value="" class="form-control"
									autocomplete="off"></td>
								<td></td>
							</tr>
							<tr id="childStoriesBox" style="display: none">
								<th>细分需求</th>
								<td><input type="text" name="childStories" id="childStories"
									value="" class="form-control" autocomplete="off"></td>
								<td></td>
							</tr>
							<tr>
								<th>备注</th>
								<td colspan="2">
									<textarea name="comment" id="comment" rows="8" style="width:100%"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary">保存</button>
			</div>
		</div>
	</div>
</div>
