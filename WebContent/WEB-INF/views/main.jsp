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
<script type="text/javascript" src="js/pages/main.js"></script>
<title></title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north'" style="height: 50px;text-align: right;padding-top: 13px;padding-right: 13px;">
			用户名：admin123  退出
		</div>
		<div data-options="region:'west',split:true,minWidth:140,maxWidth:140" title="菜单" style="width: 140px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="用户管理" style="padding: 10px;text-align: center;">
					<a id="userList" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">用 户 列 表</a>
				</div>
				<div title="模板管理" style="padding: 10px;text-align: center;">
					<a id="templateList" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">模 板 列 表</a>
				</div>
				<div title="商品管理" style="padding: 10px;text-align: center;">
					<a id="productTableManage" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">商品表管理</a>
					<a id="productList" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">商 品 列 表</a>
				</div>
				<div title="在线标签管理" style="padding: 10px;text-align: center;">
					<a id="onlineTagList" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">标 签 列 表</a>
				</div>
				<div title="系统管理" style="padding: 10px;text-align: center;">
					<a id="logList" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">日 志 列 表</a>
					<a id="messageSecretList" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">通讯加密密码</a>
					<a id="secretList" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">通讯加密密钥</a>
					<a id="changePass" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-large-smartart',fit:true">修 改 密 码</a>
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