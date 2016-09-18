var autoCommon;

/**
 * 编辑用户信息
 * */
function editUserInfo(userId){
	//获取用户信息
	autoCommon.click("getUserInfo", {"userid":userId}, function(data){
		if(data.status == 1){
			var userInfo = data.content;				
			$("#editUserId").val(userInfo.userId);
			$("#editUsername").val(userInfo.username);
			$("#popWindow").window("open");
		}else{
			$.messager.alert("提示","用户信息不存在！","info",function(){					
				$("#userTable").datagrid("load");
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
                {field:'username',title:'字段名',width:200,align:'center'},
                {field:'username',title:'字段名',width:200,align:'center'},
                {field:'username',title:'字段名',width:200,align:'center'},
                {field:'username',title:'字段名',width:200,align:'center'},
                {field:'username',title:'字段名',width:200,align:'center'},
                {field:'username',title:'字段名',width:200,align:'center'},
                {field:'userId',title:'操作',width:60,align:'center',
                    formatter:function(value,rec){
                        var btn = '<a class="editcls" onclick="editUserInfo(\''+rec.userId+'\')" href="javascript:void(0)">编辑</a>';
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
	$("#userInfoAdd").bind('click', function(){
		$("#editUserId").val("");
		$("#editUsername").val("");
		
    	//打开用户信息添加窗口
    	$("#popWindow").window("open");	
	});
	
	//绑定删除按钮
	$("#userInfoDelete").bind('click', function(){
		var selectedrows = $("#userTable").datagrid('getChecked');
    	if(selectedrows && selectedrows.length > 0){
			$.messager.confirm("确认", "确认要删除选中的用户吗？", function(r){
	    		if(r){
	        		var userIds = "";
	        		for(var i = 0; i < selectedrows.length ; i++){
	        			userIds += selectedrows[i].userId + "@";
	        		}
	        		//执行删除操作
	        		autoCommon.click("deleteUserList", {"userIds":userIds}, function(data){
            			if(data.status == 1){
            				$.messager.alert("提示","删除成功！","info",function(){        						
        						$("#userTable").datagrid("load");
        					});
                		}else{
                			$.messager.alert("提示","删除失败！","error",function(){});
                		}
	            	}, true);
	    		}
	    	});
    	}else{
    		$.messager.alert("提示","请选择要删除的用户信息！","error",function(){});
    	}
	});
	
	//绑定提交按钮
	$("#editBtn").bind('click', function(){
		autoCommon.formClick("userInfoHanlde","userInfoForm", function(data){
			var obj = eval('('+data+')');
			if(obj.status == 2){
				$.messager.alert("提示","用户不存在！","error",function(){
					$("#popWindow").window("close");
					$("#userTable").datagrid("load");
				});
				
			}else if(obj.status == 3){
				$.messager.alert("提示","用户名已存在！","error",function(){
					$("#popWindow").window("close");
					$("#userTable").datagrid("load");
				});
				
			}else if(obj.status == 1){
				$.messager.alert("提示","用户操作成功！","info",function(){
					$("#popWindow").window("close");
					$("#userTable").datagrid("load");
				});
			}else{
				$.messager.alert("提示","用户操作失败！","error",function(){});
			}
		});	
	});
	
});
