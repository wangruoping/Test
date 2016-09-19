var autoCommon;

//模板编辑中选中的元素
var selectedElement;
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
			$("#templateHeight").numberbox("setValue", tempalteInfo.height);
			$("#templateWidth").numberbox("setValue", tempalteInfo.width);
			$("#popWindow").window("open");
		}else{
			$.messager.alert("提示","模板信息不存在！","info",function(){					
				$("#templateTable").datagrid("load");
			});
		}					
	});	
}

/**
 * 初始化模板详细
 * */
function initTemplateDetail(tempalteInfo){
	$("#templateImage").html("");
	$("#templateImage").css("width",Number(tempalteInfo.width));
	$("#templateImage").css("height",Number(tempalteInfo.height));
	$("#templateName").html(tempalteInfo.name);
	$("#templateId").val(tempalteInfo.id);
	//隐藏所有
	$(".trClass").hide();
	//显示colume
	$(".column").show();
	$(".typeOne").show();
}

/**
 * 设计模板信息
 * */
function designerTemplateInfo(templateId){
	//TODO 
	//获取模板信息
	autoCommon.click("getTemplateDetailInfo", {"templateid":templateId}, function(data){
		if(data.status == 1){
			var tempalteInfo = data.content;		
			
			//TODO 初始化模板宽高，及模板中对应的元素
			initTemplateDetail(tempalteInfo);
//			$("#editTemplateId").val(tempalteInfo.id);
//			$("#templateName").val(tempalteInfo.name);
//			$("#templateHeight").numberbox("setValue", tempalteInfo.height);
//			$("#templateWidth").numberbox("setValue", tempalteInfo.width);
			$("#detailPopWindow").window("open");
		}else{
			$.messager.alert("提示","模板信息不存在！","info",function(){					
				$("#templateTable").datagrid("load");
			});
		}					
	});	
}

/**
 * 元素点击事件
 * */
function elementClick(element){
	//选中元素
	$(".elementClass").each(function(){
		$(this).removeClass("activeClass");
	});
	$(element).addClass("activeClass");

	//取标签中存放的属性值
	var marginX = $(element).css("marginLeft").replace('px', '');
	$("#marginX").numberbox("setValue", marginX);
	var marginY = $(element).css("marginTop").replace('px', '');
	$("#marginY").numberbox("setValue", marginY);
	
	//判断元素类型，右侧表格显示不同的行
	var type = $(element).data("type");
	if(type == 1){
		//静态文本输入框
		//隐藏所有
		$(".trClass").hide();
		//显示content
		$(".content").show();
		$(".typeOne").show();
	}else if(type == 2){
		//动态文本输入框
		//隐藏所有
		$(".trClass").hide();
		//显示column
		$(".column").show();
		$(".typeOne").show();
	}else if(type == 3){
		//二维码或者条形码
		//隐藏所有
		$(".trClass").hide();
		//显示column
		$(".column").show();
		$(".typeThree").show();
	}else{
		//图片
		//隐藏所有
		$(".trClass").hide();
		//显示column
		$(".url").show();
		$(".typeThree").show();
	}
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
                {field:'id',title:'操作',width:200,align:'center',
                    formatter:function(value,rec){
                        var editbtn = '<a class="editcls" onclick="editTemplateInfo(\''+rec.id+'\')" href="javascript:void(0)">编辑</a>';
                        var designerbtn = '<a class="designercls" onclick="designerTemplateInfo(\''+rec.id+'\')" href="javascript:void(0)">设计模板</a>';
                        return editbtn + " " + designerbtn;  
                    }
                }
            ]],
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
		$("#templateHeight").numberbox("setValue", "");
		$("#templateWidth").numberbox("setValue", "");
		
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
	
	//绑定静态文字事件
	$("#addStatisticTextBtn").bind('click', function(){
		var text = "nil";
		var inputElement = $("<input class='elementClass' readonly value='"+text+"' type='text' onclick='elementClick(this)'></input>");
		var inputEle = $($(inputElement)[0]);
		inputEle.width(adaptTextInput(text));
		inputEle.data("type", 1);
		inputElement.appendTo("#templateImage");
	});
	
	//绑定动态文字事件
	$("#addDymTextBtn").bind('click', function(){
		var text = "ref=>nil";
		var inputElement = $("<input class='elementClass' readonly value='"+text+"' type='text' onclick='elementClick(this)'></input>");
		var inputEle = $($(inputElement)[0]);
		inputEle.data("type", 2);
		inputEle.width(adaptTextInput(text));
		inputElement.appendTo("#templateImage");
	});
	
	//绑定条形码事件
	$("#addTxBtn").bind('click', function(){
		//TODO 动态生成一个条形码
		var imageElement = $("<img style='width:120px;height:50px;' src='' class='elementClass onclick='elementClick(this)'></img>");
		var imageEle = $($(imageElement)[0]);
		imageEle.data("type", 3);
		imageEle.appendTo("#templateImage");
	});
	
	//绑定二维码事件
	$("#addEwBtn").bind('click', function(){
		//TODO 动态生成一个二维码
		var imageElement = $("<img style='width:50px;height:50px;' src='' class='elementClass onclick='elementClick(this)'></img>");
		var imageEle = $($(imageElement)[0]);
		imageEle.data("type", 3);
		imageEle.appendTo("#templateImage");
	});
	
	//绑定图片事件
	$("#addImageBtn").bind('click', function(){
		var imageElement = $("<img style='width:50px;height:50px;' src='' class='elementClass onclick='elementClick(this)'></img>");
		var imageEle = $($(imageElement)[0]);
		imageEle.data("type", 4);
		imageEle.appendTo("#templateImage");
	});
	
	//marginX input 鼠标离开事件
	$('#marginX').numberbox({
	    min:0,
	    onChange:function(newValue,oldValue){
	    	$(".activeClass").css("marginLeft",Number(newValue));
	    }
	});
	
	//marginY input 鼠标离开事件
	$('#marginY').numberbox({
	    min:0,
	    onChange:function(newValue,oldValue){
	    	$(".activeClass").css("marginTop",Number(newValue));
	    }
	});
	
	//绑定保存模板详细事件
	$("#saveTemplateBtn").bind('click', function(){
		html2canvas($("#templateImage"),{
			allowTaint: true, 
			taintTest: false,
			onrendered: function(canvas) { 
				//生成base64图片数据 
				var dataUrl = canvas.toDataURL(); 
				console.log(dataUrl);
				if(dataUrl != ""){
					$("#imgId").attr("src",dataUrl);
				}
			}
		});
	});
});
