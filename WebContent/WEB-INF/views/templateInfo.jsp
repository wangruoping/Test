<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="styles/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/pages/common.js"></script>
<script type="text/javascript" src="js/pages/layout.js"></script>
<script type="text/javascript" src="js/pages/util.js"></script>
<script type="text/javascript" src="js/pages/templateInfo.js"></script>
<title></title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<table id="templateTable" class="easyui-datagrid"></table>
	</div>
	 <div id="toolbar">        
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="templateInfoAdd">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="templateInfoDelete">删除</a>
    </div>        
	<div id="popWindow" class="easyui-window" title="模板基础信息" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:435px;height:300px;padding:10px;">
		<div class="easyui-panel" style="padding: 10px;">
			<form id="templateInfoForm" method="post">
				<table>
					<tr>
						<td style="width:50px;">模板名称：</td>
						<td><input id="templateName" class="easyui-validatebox"
							name="name" data-options="required:true" maxlength=10/></td>
					</tr>
					<tr>
						<td style="width:50px;">宽度（像素）：</td>
						<td><input id="templateWidth" class="easyui-numberbox"
							name="width" data-options="required:true" maxlength=5/></td>
					</tr>
					<tr>
						<td style="width:50px;">高度（像素）：</td>
						<td><input id="templateHeight" class="easyui-numberbox"
							name="height" data-options="required:true" maxlength=5/></td>
					</tr>
					<tr>
						<td><a id="editBtn" href="javascript:void(0)"
							class="easyui-linkbutton">提交</a></td>
						<td></td>
					</tr>
				</table>
				<input type="hidden" id="editTemplateId" name="id">
			</form>
		</div>
	</div>
	<div id="detailPopWindow" class="easyui-window" title="模板详细设计" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:100%;height:100%;padding:10px;">
		<div class="easyui-panel" style="padding: 10px;">
			在此进行模板内容的相关修改
		</div>
	</div>
</body>
</html>