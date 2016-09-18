<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="styles/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/pages/html2canvas.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/pages/common.js"></script>
<script type="text/javascript" src="js/pages/layout.js"></script>
<script type="text/javascript" src="js/pages/util.js"></script>
<script type="text/javascript" src="js/pages/templateInfo.js"></script>
<style>
.activeClass{
	
}
.elementClass{
	float:left;
	position:absolute;
	background-color:blanchedalmond;
}
</style>
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
						<td style="width:120px;">模板名称：</td>
						<td><input id="templateName" class="easyui-validatebox"
							name="name" data-options="required:true" maxlength=10/></td>
					</tr>
					<tr>
						<td style="width:120px;">宽度（像素）：</td>
						<td><input id="templateWidth" class="easyui-numberbox"
							name="width" data-options="required:true" maxlength=5/></td>
					</tr>
					<tr>
						<td style="width:120px;">高度（像素）：</td>
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
	<div id="detailPopWindow" class="easyui-window" title="模板详细设计" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:900px;height:500px;;padding:10px;">
		<div class="easyui-layout" data-options="fit:true,border:false">
	        <div data-options="region:'center',split:true,minWidth:140,maxWidth:140" style="background-color: aliceblue;">
	        	<div class="easyui-panel" style="padding:10px;height:50px;">
	        		<div>
	        		<a id="saveTemplateBtn" href="javascript:void(0)" class="easyui-linkbutton">保存</a>
	        		<a id="clearAllBtn" href="javascript:void(0)" class="easyui-linkbutton">清空</a>
	        		<a id="deleteElementBtn" href="javascript:void(0)" class="easyui-linkbutton">删除</a>
	        		</div>
	        		<div style="float:right;">模板名称：<span id="templateName"></span></div>
	        		<input id="templateId" type="hidden"/>
	        	</div>
	        	<div style="height:380px;width:100%;">
	        		<div style="background-color: white;width:285px;
               				height:198px; margin:auto auto;" id="templateImage">
               		</div>
	        	</div>
	        </div> 
	        <div data-options="region:'east',split:true,minWidth:250,maxWidth:250">
	        	<table style="width:100%;">
	        		<tr>
	        			<td>属性</td>
	        			<td>值</td>
	        		</tr>
	        		<tr>
	        			<td>x</td>
	        			<td><input id="marginX" name="marginX" type="text" class="easyui-numberbox"/></td>
	        		</tr>
	        		<tr>
	        			<td>y</td>
	        			<td><input id="marginY" name="marginY" type="text" class="easyui-numberbox"/></td>
	        		</tr>
	        		<tr class="url trClass">
	        			<td>url</td> 
	        			<td><input id="url" name="url" type="file"/></td>
	        		</tr>
	        		<tr class="content trClass">
	        			<td>content</td>
	        			<td><input id="content" name="content"/></td>
	        		</tr>
	        		<tr class="column trClass">
	        			<td>column</td>
	        			<td><select></select></td>
	        		</tr>
	        		<tr class="typeThree trClass">
	        			<td>width</td>
	        			<td><input id="width" name="width" type="text" class="easyui-numberbox"/></td>
	        		</tr>
	        		<tr class="typeThree trClass">
	        			<td>height</td>
	        			<td><input id="height" name="height" type="text" class="easyui-numberbox"/></td>
	        		</tr>
	        		<tr class="typeOne trClass">
	        			<td>font</td>
	        			<td><select></select></td>
	        		</tr>
	        		<tr class="typeOne trClass">
	        			<td>size</td>
	        			<td><input id="size" name="size" class="easyui-numberbox"/></td>
	        		</tr>
	        		<tr class="typeOne trClass">
	        			<td>style</td>
	        			<td><select></select></td>
	        		</tr>
	        	</table>
	        	<div id="tt" class="easyui-tabs" data-options="tabPosition:'left',tabWidth:80,tabHeight:20,border:true,headerWidth:100" style="width:240px;height:100px;margin-top:150px">
					<div title="<span class='tt-inner'>静态文本</span>" style="height:1px;">
						<a id="addStatisticTextBtn" href="javascript:void(0)" iconCls="icon-add" class="easyui-linkbutton">添加静态文本</a>
					</div>
					<div title="<span class='tt-inner'>动态文本</span>" style="padding:1px">
						<a id="addDymTextBtn" href="javascript:void(0)" iconCls="icon-add" class="easyui-linkbutton">添加动态文本</a>
					</div>
					<div title="<span class='tt-inner'>二维码</span>" style="height:1px;">
						<a id="addTxBtn" href="javascript:void(0)" iconCls="icon-add" class="easyui-linkbutton">添加条形码</a>
						<a id="addEwBtn" href="javascript:void(0)" iconCls="icon-add" class="easyui-linkbutton">添加二维码</a>
					</div>
					<div title="<span class='tt-inner'>图片</span>" style="height:1px;">
						<a id="addImageBtn" href="javascript:void(0)" iconCls="icon-add" class="easyui-linkbutton">添加图片</a>
					</div>
				</div>
	        </div> 
	    </div>
	</div>
</body>
</html>