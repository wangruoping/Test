var autoCommon;

/**
 * 编辑模板信息
 * */
function editTemplateInfo(templateId){
	//获取模板信息
	autoCommon.click("getTemplateInfo", {"templateid":templateId}, function(data){
		if(data.status == 1){
			var tempalteInfo = data.content;				
			$("#editTemplateId").val(tempalteInfo.id);
			$("#templateName").val(tempalteInfo.name);
			$("#templateHeight").val(tempalteInfo.height);
			$("#templateWidth").val(tempalteInfo.width);
			$("#popWindow").window("open");
		}else{
			$.messager.alert("提示","模板信息不存在！","info",function(){					
				$("#templateTable").datagrid("load");
			});
		}					
	});	
}

/**
 * 设计模板信息
 * */
function designerTemplateInfo(templateId){
	//TODO 
	$("#detailPopWindow").window("open");
}
$(function(){
	//共同方法对象
	autoCommon = new $.common;

	/**
	 * 初始化表格
	 * 
	 * */
	function initTable(){
		
		$("#templateTable").datagrid({
			url:'templateList',
			pagination:true,
			rownumbers:true,
			singleSelect:true,
			selectOnCheck:false,
			toolbar:'#toolbar',			
			fit:true,
			columns:[[
			    {field:"ck",title:"",checkbox:true},
                {field:'name',title:'模板名',width:200,align:'center'},
                {field:'width',title:'宽度',width:100,align:'center',
                    formatter:function(value,rec){
                        return value + "像素";  
                    }
                },
                {field:'height',title:'高度',width:100,align:'center',
                    formatter:function(value,rec){
                        return value + "像素";  
                    }
                },
                {field:'id',title:'操作',width:60,align:'center',
                    formatter:function(value,rec){
                        var editbtn = '<a class="editcls" onclick="editTemplateInfo(\''+rec.id+'\')" href="javascript:void(0)">编辑</a>';
                        var designerbtn = '<a class="designercls" onclick="designerTemplateInfo(\''+rec.id+'\')" href="javascript:void(0)">设计模板</a>';
                        return btn + " " + designerbtn;  
                    }
                }
            ]],
            pagination: true,
            onLoadSuccess:function(data){
                $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
                $('.designercls').linkbutton({text:'设计模板',plain:true,iconCls:'icon-edit'});
            }	
		});
	};
	
	//初始化表格
	initTable();
	
	//绑定添加按钮
	$("#templateInfoAdd").bind('click', function(){
		$("#editTemplateId").val("");
		$("#templateName").val("");
		$("#templateHeight").val("");
		$("#templateWidth").val("");
		
    	//打开模板信息添加窗口
    	$("#popWindow").window("open");	
	});
	
	//绑定删除按钮
	$("#templateInfoDelete").bind('click', function(){
		var selectedrows = $("#templateTable").datagrid('getChecked');
    	if(selectedrows && selectedrows.length > 0){
			$.messager.confirm("确认", "确认要删除选中的模板吗？", function(r){
	    		if(r){
	        		var templateIds = "";
	        		for(var i = 0; i < selectedrows.length ; i++){
	        			templateIds += selectedrows[i].id + "@";
	        		}
	        		//执行删除操作
	        		autoCommon.click("deleteTemplateList", {"templateIds":templateIds}, function(data){
            			if(data.status == 1){
            				$.messager.alert("提示","删除成功！","info",function(){        						
        						$("#templateTable").datagrid("load");
        					});
                		}else{
                			$.messager.alert("提示","删除失败！","error",function(){});
                		}
	            	}, true);
	    		}
	    	});
    	}else{
    		$.messager.alert("提示","请选择要删除的模板信息！","error",function(){});
    	}
	});
	
	//绑定提交按钮
	$("#editBtn").bind('click', function(){
		autoCommon.formClick("templateInfoHanlde","templateInfoForm", function(data){
			var obj = eval('('+data+')');
			if(obj.status == 2){
				$.messager.alert("提示","模板不存在！","error",function(){
					$("#popWindow").window("close");
					$("#templateTable").datagrid("load");
				});
				
			}else if(obj.status == 3){
				$.messager.alert("提示","模板名已存在！","error",function(){
					$("#popWindow").window("close");
					$("#templateTable").datagrid("load");
				});
				
			}else if(obj.status == 1){
				$.messager.alert("提示","模板操作成功！","info",function(){
					$("#popWindow").window("close");
					$("#templateTable").datagrid("load");
				});
			}else{
				$.messager.alert("提示","模板操作失败！","error",function(){});
			}
		});	
	});
	
});
