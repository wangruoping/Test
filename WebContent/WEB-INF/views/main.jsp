<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="styles/themes/black/easyui.css">
<link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/pages/main.js"></script>
<title></title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north'" style="height: 50px;text-align: right;padding-top: 13px;padding-right: 13px;">
			
		</div>
		<div data-options="region:'south',split:true" style="height: 40px;"></div>
		<div data-options="region:'west',split:true,minWidth:140,maxWidth:140" title="菜单" style="width: 140px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="册子管理" style="padding: 10px;text-align: center;">
					<a id="manageUploadList" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">册 子 列 表</a>
				</div>
				<div title="商城管理" style="padding: 10px;text-align: center;">
					<a id="shopProductCategory" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">产 品 类 别</a>
					<div style="height:5px;"></div>
					<a id="productManage" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">产 品 管 理</a>
					<div style="height:5px;"></div>
					<a id="printManage" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">打 印 管 理</a>
				</div>
				<div title="用户管理" style="padding: 10px;text-align: center;">
					<a id="userList" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">用 户 列 表</a>
				</div>
			</div>
		</div>
		<div data-options="region:'center',title:'主窗口',iconCls:'icon-ok'">
			<div id="manageTab" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
				<div title="关于" style="padding:10px">
					关于系统的介绍
				</div>
			</div>
		</div>
	</div>
</body>
</html>