<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="utf-8" http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="Xenon Boostrap Admin Panel" />
	<meta name="author" content="" />
	<title>GM-${vName}</title>

	<link rel="stylesheet" href="${GmBase}/assets/css/fonts/linecons/css/linecons.css">
	<link rel="stylesheet" href="${GmBase}/assets/css/fonts/fontawesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="${GmBase}/assets/css/bootstrap.css">
	<link rel="stylesheet" href="${GmBase}/assets/css/xenon-core.css">
	<link rel="stylesheet" href="${GmBase}/assets/css/xenon-forms.css">
	<link rel="stylesheet" href="${GmBase}/assets/css/xenon-components.css">
	<link rel="stylesheet" href="${GmBase}/assets/css/xenon-skins.css">
	<link rel="stylesheet" href="${GmBase}/assets/js/datatables/dataTables.bootstrap.css">
	<link rel="stylesheet" href="${GmBase}/assets/js/select2/select2.css">
	<link rel="stylesheet" href="${GmBase}/assets/js/select2/select2-bootstrap.css">
	<link rel="stylesheet" href="${GmBase}/assets/js/multiselect/css/multi-select.css">
	<link rel="stylesheet" href="${GmBase}/assets/css/custom.css">
	<script src="${GmBase}/assets/js/jquery-1.11.1.min.js"></script>
	<script src="${GmBase}/assets/js/jquery-form.js"></script>
	<script src="${GmBase}/assets/js/laydate/laydate.js"></script>
	<link rel="stylesheet" href="${GmBase}/assets/js/laydate/theme/default/laydate.css">
</head>
<div class="breadcrumb-env">
	<ol class="breadcrumb bc-1">
		<li>
			<a href="#" onclick="moveMain();">
				<i class="fa-home"></i>回到主页
			</a>
		</li>
	</ol>
</div>
<div style="width: 100%;">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">上传文件</h3>

			<div class="panel-options">
				<a href="#" data-toggle="panel">
					<span class="collapse-icon">&ndash;</span>
					<span class="expand-icon">+</span>
				</a>
			</div>
		</div>
		<div class="panel-body">
			<form action="doUpload" role="form" class="form-horizontal" method="post" id="configUp" enctype='multipart/form-data'>
				<div class="form-group">
					<label class="col-sm-1 control-label" for="fileType">类型</label>
					<div class="col-sm-3">
						<select class="form-control" name="fType" id="fileType" >
							<option value="activity-properties" selected="selected">活动配置</option>
							<%--<option value="sign-xlsx">签到配置</option>--%>
							<%--<option value="validate-txt">验证配置</option>--%>
							<option value="activityBatch-zip">批量活动</option>
						</select>
					</div>
				</div>
				<div class="form-group" id="serverDIV">
					<label class="col-sm-1 control-label" for="multi-select">服务器</label>
					<div class="col-sm-3">
						<select class="form-control" multiple="multiple" id="multi-select" name="server">
							<c:forEach items="${serverConfig}" var="config">
								<option value="${config.serverID}">${config.serverName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label" for="field-4">配置文件</label>
					<div class="input-group col-sm-3">
						<input type="file" name="gameConfig" class="form-control" id="field-4">
						<span class="input-group-btn">
							<button class="btn btn-blue" type="button" onclick="upFile();">上传</button>
						</span>
					</div>
				</div>
			</form>
			<br/>
			<div class="form-group">
				<textarea class="col-sm-4" id="upReslut" rows="10" readonly="readonly" style="resize: none;"></textarea>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
//	function changeUploadType() {
//		if($("#fileType").val()=='validate-txt'){
//			$("#serverDIV").hide(140);
//		}else{
//            $("#serverDIV").show(140);
//		}
//    }

	function upFile(){
		$("#configUp").ajaxSubmit({
			dataType:'json',
			success:function(data){
				$("#upReslut").val(data);
                var dataObj =eval('(' + data + ')');
                var serverId = $("#multi-select").val();
                var code = eval('(' + dataObj[serverId] + ')').code;
				if (code == 0){
					alert("上传成功");
				}else {
                    alert("上传失败");
				}
			}
		});
	}
	jQuery(document).ready(function($)
	{
		$("#multi-select").multiSelect({
			afterInit: function()
			{
				// Add alternative scrollbar to list
				this.$selectableContainer.add(this.$selectionContainer).find('.ms-list').perfectScrollbar();
			},
			afterSelect: function()
			{
				// Update scrollbar size
				this.$selectableContainer.add(this.$selectionContainer).find('.ms-list').perfectScrollbar('update');
			}
		});
	});
</script>
<script src="${GmBase}/assets/js/datatables/js/jquery.dataTables.min.js"></script>
<script src="${GmBase}/assets/js/datatables/dataTables.bootstrap.js"></script>
<script src="${GmBase}/assets/js/datatables/yadcf/jquery.dataTables.yadcf.js"></script>
<script src="${GmBase}/assets/js/datatables/tabletools/dataTables.tableTools.min.js"></script>
<script src="${GmBase}/assets/js/tagsinput/bootstrap-tagsinput.min.js"></script>
<script src="${GmBase}/assets/js/inputmask/jquery.inputmask.bundle.js"></script>
<script src="${GmBase}/assets/js/select2/select2.min.js"></script>
<script src="${GmBase}/assets/js/multiselect/js/jquery.multi-select.js"></script>
<script src="${GmBase}/assets/js/jquery-ui/jquery-ui.min.js"></script>

<script src="${GmBase}/assets/js/bootstrap.min.js"></script>
<script src="${GmBase}/assets/js/TweenMax.min.js"></script>
<script src="${GmBase}/assets/js/resizeable.js"></script>
<script src="${GmBase}/assets/js/joinable.js"></script>
<script src="${GmBase}/assets/js/xenon-api.js"></script>
<script src="${GmBase}/assets/js/xenon-toggles.js"></script>
<script src="${GmBase}/assets/js/devexpress-web-14.1/js/globalize.min.js"></script>
<script src="${GmBase}/assets/js/devexpress-web-14.1/js/dx.chartjs.js"></script>
<script src="${GmBase}/assets/js/xenon-custom.js"></script>
</body>
</html>