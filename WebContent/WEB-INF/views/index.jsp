<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	    <title></title>
	    <link rel="stylesheet" type="text/css" href="styles/themes/bootstrap/easyui.css">
	    <link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
	    <script type="text/javascript" src="js/jquery.min.js"></script>
	    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	        <script>
	        function submitForm() {

	            if ($('#ff').form("validate")) {
	            	$('#ff').submit();	
	            }
	        }
	    </script>
	</head>
	<body>
	
	    <div id="w" class="easyui-window" title="登录窗口" data-options="maximizable:false,collapsible:false,modal:true,closable:false,resizable:false,minimizable:false" style="width:300px;height:180px;padding:10px;">
	        <div style="text-align:center;padding:5px">
		        <form id="ff" method="post" style="width: 100%;" action="login.html">
		            <table cellpadding="5" style="width: 100%;">
		                <tr>
		                    <td>账号:</td>
		                    <td><input value="admin888" class="easyui-validatebox" maxlength="16" type="text" name="username" data-options="required:true,validType:'length[6,16]'"></input></td>
		                </tr>
		                <tr>
		                    <td>秘密:</td>
		                    <td><input value="admin888" class="easyui-validatebox" maxlength="16" type="password" name="password" data-options="required:true,validType:'length[6,16]'"></input></td>
		                </tr>
		            </table>
		        </form>
		        <div style="text-align:center;padding:5px">
		            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">登录</a>
		        </div>
	        </div>
	    </div>
	</body>
</html>