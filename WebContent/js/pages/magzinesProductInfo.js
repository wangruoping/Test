var autoCommon;

function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
	+ (d < 10 ? ('0' + d) : d);
}
function myparser(s) {
	if (!s)
		return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}
	
function checkWorkFile(){
	var obj = document.getElementById('uploadFile'); 
    if(obj.value==''){ 
    	 $.messager.alert("提示","请选择要上传的文件！","info",function(){});
         return false; 
    } 
    
    if(obj.value.length > 5){
    	 var typeVar = obj.value.substring(obj.value.length - 3,obj.value.length);
    	 if(typeVar != 'zip') { 
	    	$.messager.alert("提示","文件类型不正确，请选择.zip文件！","error",function(){});
	        return false; 
	     } 
    	 return true; 
    }else{
    	$.messager.alert("提示","文件类型不正确，请选择.zip文件！","error",function(){});
    	return false; 
    }
}

function editRow(id){
	
	$("#txtStartTime").datebox('setValue','');
	$("#txtEndTime").datebox('setValue','');
	
	//获取该杂志的免费开始时间和免费结束时间
	autoCommon.click("getMagzinesProductInfo", {"id":id}, function(data){
		if(data.status == 1){
			var magProductInfo = data.content;				
			$("#txtStartTime").datebox('setValue',magProductInfo.startTime);
			$("#txtEndTime").datebox('setValue',magProductInfo.endTime);
		    $("#magzinesId").val(magProductInfo.id); 	
			$("#popFreeWindow").window("open");
		}else{
			$.messager.alert("提示","杂志信息不存在！","info",function(){					
				$("#shopProductTable").datagrid("load");
			});
		}					
	});	
}

function cancelFree(id){
	//获取该杂志的免费开始时间和免费结束时间
	autoCommon.click("cancelFreeMagTime", {"id":id}, function(data){
		if(data.status == 1){
			$.messager.alert("提示","取消成功","info",function(){					
				$("#shopProductTable").datagrid("load");
			});
			
		}else{
			$.messager.alert("提示","杂志信息不存在！","info",function(){					
				$("#shopProductTable").datagrid("load");
			});
		}					
	});	
}

$(function(){
	//共同方法对象
	autoCommon = new $.common;
	
	$("#productAdd").bind('click',function(){
		//清空杂志心情内容
		$("#fileComment").val('');
		$("#productAmount").val('');
		//清空文件
		var file = $("#uploadFile");
		file.after(file.clone().val(""));
		file.remove();
		//加载类别列表
    	autoCommon.click("getAllServerCategoryList", {}, function(data){
			if(data.status == 1){
    			$('#categoryList').combobox({            	    
            	    data:data.content,
            		valueField:'id',
            	    textField:'categoryName', 
            	    editable:false                     	    
            	});
    		}
    	}, true);
    	//打开用户信息添加窗口
    	$("#popWindow").window("open");	
	});
	
	$("#productDelete").bind('click',function(){
		$.messager.confirm("确认", "确认要删除选中的产品吗？", function(r){
    		if(r){
    			var selectedrows = $("#shopProductTable").datagrid('getChecked');
	        	if(selectedrows && selectedrows.length > 0){
	        		var productIds = "";
	        		for(var i = 0; i < selectedrows.length ; i++){
	        			productIds += selectedrows[i].id + "@";
	        		}
	        		//执行删除操作
	        		autoCommon.click("deleteProductList", {"productIds":productIds}, function(data){
            			if(data.status == 1){
            				$.messager.alert("提示","删除成功！","info",function(){        						
        						$("#shopProductTable").datagrid("load");
        					});
                		}else{
                			$.messager.alert("提示","删除失败！","error",function(){});
                		}
	            	}, true);
	        	}else{
	        		$.messager.alert("提示","请选择要删除的产品信息！","error",function(){});
	        	}
    		}
    	});
	});
	
	/**
	 * 初始化表格
	 * 
	 * */
	function initTable(){
		
		$("#shopProductTable").datagrid({
			url:'magzinesProductList',
			pagination:true,
			rownumbers:true,
			singleSelect:true,
			selectOnCheck:false,
			toolbar:'#toolbar',			
			fit:true,
		    columns:[[
		        {field:"ck",title:"",checkbox:true},
		        {field:'id',title:"",width:150,hidden:true},
		        {field:'categoryName',title:'产品类别',width:200},
                {field:'fileComment',title:'文件描述',width:200,align:'right'},
                {field:'fileAmount',title:'产品价格',width:200,align:'right'},
                {field:'fileSize',title:'文件大小',width:60, 
		        	formatter:function(value,rec){  
		        		var size = Math.round(value/(1024*1024));
		        		return size + "M";
		        	}
		        },
                {field:'uploadUser',title:'上传者',width:100},
                {field:'uploadTime',title:'上传时间',width:150},
                {field:'startTime',title:'免费开始时间',width:100},
                {field:'endTime',title:'免费结束时间',width:100},
                {field:'opt',title:"操作",width:200,align:'center', 
		        	formatter:function(value,rec){  
		        		editBtn = '<a href="javascript:void(0)" class="l-btn l-btn-plain" onclick="editRow(\''+rec.id+'\')" group="" id=""><span class="l-btn-left"><span class="l-btn-text icon-edit l-btn-icon-left">免费时间设置</span></span></a>';
		        		
		        		if(rec.startTime != null && rec.startTime != ""){
		        			editBtn += '<a href="javascript:void(0)" class="l-btn l-btn-plain" onclick="cancelFree(\''+rec.id+'\')" group="" id=""><span class="l-btn-left"><span class="l-btn-text icon-edit l-btn-icon-left">取消免费</span></span></a>';
		        		}
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
		
		if($('#categoryList').combobox('getValue') == ""){
			$.messager.alert("提示","请选择类别!","error",function(){});
			return ;
		}
		if($('#fileComment').val().trim() == ""){
			$.messager.alert("提示","请输入产品信息!","error",function(){});
			return ;
		}
		if(!checkWorkFile()){
			return;
		}
		autoCommon.formClick("uploadProductMagzines","magzinesProductForm", function(data){
			var obj = eval('('+data+')');
			if(obj.status == 2){
				$.messager.alert("提示","产品不存在！","error",function(){
					$("#popWindow").window("close");
					$("#shopProductTable").datagrid("load");
				});
				
			}else if(obj.status == 1){
				$.messager.alert("提示","产品添加成功！","info",function(){
					$("#popWindow").window("close");
					$("#shopProductTable").datagrid("load");
				});
			}else{
				$.messager.alert("提示","产品添加失败！","error",function(){});
			}
		});	
	});
	
	//免费时间修改按钮事件
	$("#changeBtn").bind('click',function(){
		var startTime = $('#txtStartTime').datebox('getValue');
		var endTime = $('#txtEndTime').datebox('getValue');
		console.log(startTime);
		if(startTime != "" && endTime!= ""){
			//提交
			if(endTime > startTime){
				//提交
				autoCommon.formClick("changeProductMagzines","magzinesForm", function(data){
					var obj = eval('('+data+')');
					if(obj.status == 2){
						$.messager.alert("提示","产品不存在！","error",function(){
							$("#popFreeWindow").window("close");
							$("#shopProductTable").datagrid("load");
						});
					}else if(obj.status == 1){
						$.messager.alert("提示","时间修改成功！","info",function(){
							$("#popFreeWindow").window("close");
							$("#shopProductTable").datagrid("load");
						});
					}else{
						$.messager.alert("提示","时间修改失败！","error",function(){});
					}
				});
			}else{
				$.messager.alert("提示","开始时间不能大于等于结束时间！","error",function(){});
			}
		}else{
			$.messager.alert("提示","请合理选择时间！","error",function(){});
		}
	});
});
