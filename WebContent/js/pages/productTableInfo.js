var autoCommon;

/**
 * 编辑表字段信息
 * */
function editProductTableInfo(id){
	//获取表字段信息
	autoCommon.click("getProductTableInfo", {"id":id}, function(data){
		if(data.status == 1){
			var productTableInfo = data.content;		
			$("#editId").val(productTableInfo.id);
			$("#editName").val(productTableInfo.name);
			$("#editOthername").val(productTableInfo.disname);
			$("#editLength").numberbox("setValue", productTableInfo.length);
			$("#editDisIndex").numberbox("setValue", productTableInfo.disIndex);
			if(productTableInfo.disen == 1){
				$("#editDisEn").attr("checked",true);
			}else{
				$("#editDisEn").attr("checked",false);
			}
			$("#popWindow").window("open");
		}else{
			$.messager.alert("提示","表字段信息不存在！","info",function(){					
				$("#productTable").datagrid("load");
			});
		}					
	});	
}

$(function(){
	//共同方法对象
	autoCommon = new $.common;

	/**
	 * 初始化表格
	 * 
	 * */
	function initTable(){
		
		$("#productTable").datagrid({
			url:'productTableList',
			pagination:false,
			rownumbers:true,
			singleSelect:true,
			selectOnCheck:false,
			toolbar:'#toolbar',			
			fit:true,
			columns:[[
			    {field:"ck",title:"",checkbox:true},
                {field:'name',title:'字段名',width:200,align:'center'},
                {field:'disname',title:'显示名称',width:200,align:'center'},
                {field:'disen',title:'是否显示',width:200,align:'center',
                    formatter:function(value,rec){
                       if(value == 0){
                    	   return "显示";
                       }else{
                    	   return "不显示";
                       }
                    }
                },
                {field:'disindex',title:'显示顺序',width:200,align:'center'},
                {field:'userId',title:'操作',width:60,align:'center',
                    formatter:function(value,rec){
                        var btn = '<a class="editcls" onclick="editProductTableInfo(\''+rec.id+'\')" href="javascript:void(0)">编辑</a>';
                        return btn;  
                    }
                }
            ]],
            pagination: true,
            onLoadSuccess:function(data){
                $('.editcls').linkbutton({text:'编辑',plain:true,iconCls:'icon-edit'});
            }	
		});
	};
	
	//初始化表格
	initTable();
	
	//绑定添加按钮
	$("#productTableInfoAdd").bind('click', function(){
		$("#editId").val("");
		$("#editName").val("");
		$("#editOthername").val("");
		$("#editLength").numberbox("setValue", "");
		$("#editDisIndex").numberbox("setValue", "");
		$("#editDisEn").attr("checked",false);
    	//打开用户信息添加窗口
    	$("#popWindow").window("open");	
	});
	
	//绑定删除按钮
	$("#productTableInfoDelete").bind('click', function(){
		var selectedrows = $("#productTable").datagrid('getChecked');
    	if(selectedrows && selectedrows.length > 0){
			$.messager.confirm("确认", "确认要删除选中的表字段吗？", function(r){
	    		if(r){
	        		var productTableIds = "";
	        		for(var i = 0; i < selectedrows.length ; i++){
	        			productTableIds += selectedrows[i].userId + "@";
	        		}
	        		//执行删除操作
	        		autoCommon.click("deleteProductTableList", {"productTableIds":productTableIds}, function(data){
            			if(data.status == 1){
            				$.messager.alert("提示","删除成功！","info",function(){        						
        						$("#productTable").datagrid("load");
        					});
                		}else{
                			$.messager.alert("提示","删除失败！","error",function(){});
                		}
	            	}, true);
	    		}
	    	});
    	}else{
    		$.messager.alert("提示","请选择要删除的表字段信息！","error",function(){});
    	}
	});
	
	//绑定提交按钮
	$("#editBtn").bind('click', function(){
		autoCommon.formClick("productTableInfoHanlde","productTableInfoForm", function(data){
			var obj = eval('('+data+')');
			if(obj.status == 2){
				$.messager.alert("提示","表字段不存在！","error",function(){
					$("#popWindow").window("close");
					$("#productTable").datagrid("load");
				});
				
			}else if(obj.status == 3){
				$.messager.alert("提示","表字段已存在！","error",function(){
					$("#popWindow").window("close");
					$("#productTable").datagrid("load");
				});
				
			}else if(obj.status == 1){
				$.messager.alert("提示","表字段操作成功！","info",function(){
					$("#popWindow").window("close");
					$("#productTable").datagrid("load");
				});
			}else{
				$.messager.alert("提示","表字段操作失败！","error",function(){});
			}
		});	
	});
	
});
