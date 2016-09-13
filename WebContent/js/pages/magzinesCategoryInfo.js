 var autoCommon;
$(function(){
	//共同方法对象
	autoCommon = new $.common;
	
	$("#categoryAdd").bind('click',function(){
		$("#categoryId").val("");
		$("#categoryName").val("");
		
    	//打开用户信息添加窗口
    	$("#popWindow").window("open");	
	});
	
	$("#categoryDelete").bind('click',function(){
		$.messager.confirm("确认", "确认要删除选中的类别吗？", function(r){
    		if(r){
    			var selectedrows = $("#shopProductCategoryTable").datagrid('getChecked');
	        	if(selectedrows && selectedrows.length > 0){
	        		var categoryIds = "";
	        		for(var i = 0; i < selectedrows.length ; i++){
	        			categoryIds += selectedrows[i].id + "@";
	        		}
	        		//执行删除操作
	        		autoCommon.click("deleteCategoryList", {"categoryIds":categoryIds}, function(data){
            			if(data.status == 1){
            				$.messager.alert("提示","删除成功！","info",function(){        						
        						$("#shopProductCategoryTable").datagrid("load");
        					});
                		}else{
                			$.messager.alert("提示","删除失败！","error",function(){});
                		}
	            	}, true);
	        	}else{
	        		$.messager.alert("提示","请选择要删除的类别信息！","error",function(){});
	        	}
    		}
    	});
	});
	
	/**
	 * 初始化表格
	 * 
	 * */
	function initTable(){
		
		$("#shopProductCategoryTable").datagrid({
			url:'magzinesCategoryList',
			pagination:true,
			rownumbers:true,
			singleSelect:true,
			selectOnCheck:false,
			toolbar:'#toolbar',			
			fit:true,
		    columns:[[
		        {field:"ck",title:"",checkbox:true},
		        {field:'id',title:"",width:150,hidden:true},
		        {field:'categoryName',title:"类别名称",width:300},
		        {field:'createDate',title:"创建时间",width:150},
		        {field:'opt',title:"操作",width:200,align:'center', 
		        	formatter:function(value,rec){  
		        		editBtn = '<a href="javascript:void(0)" class="l-btn l-btn-plain" onclick="editRow(\''+rec.id+'\')" group="" id=""><span class="l-btn-left"><span class="l-btn-text icon-edit l-btn-icon-left">编辑</span></span></a>';
		        		return editBtn;  
		        	}   
		        }
		    ]]		
		});
	};
	
	//初始化表格
	initTable();;
	
	//添加按钮事件
	$("#addBtn").bind('click',function(){

		autoCommon.formClick("categoryInfoHanlde","magzinesCategoryForm", function(data){
			var obj = eval('('+data+')');
			if(obj.status == 2){
				$.messager.alert("提示","类别不存在！","error",function(){
					$("#popWindow").window("close");
					$("#shopProductCategoryTable").datagrid("load");
				});
				
			}else if(obj.status == 1){
				$.messager.alert("提示","类别添加成功！","info",function(){
					$("#popWindow").window("close");
					$("#shopProductCategoryTable").datagrid("load");
				});
			}else{
				$.messager.alert("提示","类别添加失败！","error",function(){});
			}
		});	
	});
});

//用户信息编辑
function editRow(categoryId){
	autoCommon.click("getMagzinesCategoryInfo", {"categoryId":categoryId}, function(data){
		if(data.status == 1){
			var categoryInfo = data.content;				
			$("#categoryId").val(categoryInfo.id);
			$("#categoryName").val(categoryInfo.categoryName);
		     	
			$("#popWindow").window("open");
		}else{
			$.messager.alert("提示","类别信息不存在！","info",function(){					
				$("#shopProductCategoryTable").datagrid("load");
			});
		}					
	});	
}

