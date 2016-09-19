<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css"
	href="styles/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery.edatagrid.js"></script>
<script type="text/javascript" src="js/pages/common.js"></script>
<script type="text/javascript" src="js/pages/layout.js"></script>
<script type="text/javascript" src="js/pages/util.js"></script>
<script type="text/javascript" src="js/pages/productDataInfo.js"></script>
<title></title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<table id="productDataTable" class="easyui-datagrid"></table>
	</div>

	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true"
			onclick="javascript:$('#productDataTable').edatagrid('addRow')">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true"
			onclick="javascript:$('#productDataTable').edatagrid('destroyRow')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" plain="true"
			onclick="javascript:$('#productDataTable').edatagrid('saveRow')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-undo" plain="true"
			onclick="javascript:$('#productDataTable').edatagrid('cancelRow')">取消</a>
	</div>
</body>
</html>