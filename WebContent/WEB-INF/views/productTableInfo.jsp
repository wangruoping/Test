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
<script type="text/javascript" src="js/pages/productTableInfo.js"></script>
<title></title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<table id="productTable" class="easyui-datagrid"></table>
	</div>
	 <div id="toolbar">        
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="productTableInfoAdd">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="productTableInfoDelete">删除</a>
    </div>        
	<div id="popWindow" class="easyui-window" title="商品表字段操作" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:435px;height:300px;padding:10px;">
		<div class="easyui-panel" style="padding: 10px;">
			<form id="productTableInfoForm" method="post">
				<table>
					<tr>
						<td style="width:80px;">字段名：</td>
						<td><input id="editName" class="easyui-validatebox"
							name="name" data-options="required:true" /></td>
					</tr>
					<tr>
						<td style="width:80px;">显示名称：</td>
						<td><input id="editOthername" class="easyui-validatebox"
							name="othername" data-options="required:true" /></td>
					</tr>
					<tr>
						<td style="width:80px;">长度：</td>
						<td><input id="editLength" class="easyui-numberbox"
							name="length" data-options="required:true" /></td>
					</tr>
					<tr>
						<td style="width:80px;">显示顺序：</td>
						<td><input id="editDisIndex" class="easyui-numberbox"
							name="disIndex" data-options="required:true" /></td>
					</tr>
					<tr>
						<td style="width:80px;">是否显示：</td>
						<td><input id="editDisEn" type="checkbox" name="disEn" value="1"/></td>
					</tr>
					<tr>
						<td><a id="editBtn" href="javascript:void(0)"
							class="easyui-linkbutton">提交</a></td>
						<td></td>
					</tr>
				</table>
				<input type="hidden" id="editId" name="id">
			</form>
		</div>
	</div>
</body>
</html>