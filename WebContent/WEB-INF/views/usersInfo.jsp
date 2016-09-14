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
<script type="text/javascript" src="js/pages/usersInfo.js"></script>
<title></title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<table id="userTable" class="easyui-datagrid"></table>
	</div>
	 <div id="toolbar">        
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="userInfoAdd">添加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="userInfoDelete">删除</a>
    </div>        
	<div id="popWindow" class="easyui-window" title="普通用户操作" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:435px;height:300px;padding:10px;">
		<div class="easyui-panel" style="padding: 10px;">
			<form id="userInfoForm" method="post">
				<table>
					<tr>
						<td style="width:50px;">用户名：</td>
						<td><input id="editUsername" class="easyui-validatebox"
							name="username" data-options="required:true" /></td>
					</tr>
					<tr>
						<td><a id="editBtn" href="javascript:void(0)"
							class="easyui-linkbutton">提交</a></td>
						<td></td>
					</tr>
					<tr>
						<td style="color:red;" colspan="2">注：新创建的用户的默认密码是123456</td>
					</tr>
				</table>
				<input type="hidden" id="editUserId" name="id">
			</form>
		</div>
	</div>
</body>
</html>