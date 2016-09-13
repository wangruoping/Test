var autoCommon;

function downloadMag(id){
    window.open("downloadMagzines?magId="+id+"");
}
function downloadZoomMagzines(id){
    window.open("downloadZoomMagzines?magId="+id+"");
}



function cfMag(magId,id){
	//杂志拆分
	autoCommon.click("magCfMagzines", {"magId":magId,"id":id}, function(data){
		if(data.status == 1){
			$.messager.alert("提示","杂志拆分成功！","info",function(){					
				$("#userProductTable").datagrid("load");
			});
		}else if(data.status == 0){
			$.messager.alert("提示","杂志拆分失败！","error",function(){					
				$("#userProductTable").datagrid("load");
			});
		}else{
			$.messager.alert("提示","被人正在拆分中。。。","error",function(){					
				$("#userProductTable").datagrid("load");
			});
		}
	},"拆分中。。。");	
}

$(function(){
	//共同方法对象
	autoCommon = new $.common;

	/**
	 * 初始化表格
	 * 
	 * */
	function initTable(){
		
		$("#userProductTable").datagrid({
			url:'uploadMagzinesList.html',
			pagination:true,
			rownumbers:true,
			singleSelect:true,
			selectOnCheck:false,
			toolbar:'#toolbar',			
			fit:true,
			columns:[[
			    {field:'fileName',title:'文件名',width:240},
                {field:'fileSize',title:'文件大小',width:60},
                {field:'userName',title:'上传者',width:100},
                {field:'uploadTime',title:'上传时间',width:120},
                {field:'tplName',title:'模板',width:40},
                {field:'id',title:'下载打印杂志',width:80,align:'center',
                    formatter:function(value,rec){

                        var btnVar = '<a class="cfcls" onclick="cfMag(\''+rec.fileName+'\',\''+rec.id+'\')" href="javascript:void(0)">拆分</a>';
                        console.log(btnVar);
                        if(rec && rec.cfFlag && rec.cfFlag == 0){
                            return btnVar;
                        }

                        if(rec && rec.cfFlag && rec.cfFlag == 1){
                            var downloadBtn = '<a class="downloadcls" onclick="downloadMag(\''+rec.fileName+'\')" href="javascript:void(0)">下载</a>';
                            return downloadBtn;
                        }
                        return btnVar;
                    }
                },
                {field:'no',title:'下载原始版',width:80,align:'center',
                    formatter:function(value,rec){
                        if(rec && rec.cfFlag && rec.cfFlag == 1){
                        	var btn = '<a class="downloadcls" onclick="downloadZoomMagzines(\''+rec.fileName+'\')" href="javascript:void(0)">下载</a>';
                        	return btn;
                        }
                        return '';
                    }
                },
                {field:'fileComment',title:'用户备注',width:300}
            ]],
            pagination: true,
            onLoadSuccess:function(data){
                $('.downloadcls').linkbutton({text:'下载',plain:true,iconCls:'icon-edit'});
                $('.cfcls').linkbutton({text:'拆分',plain:true,iconCls:'icon-add'});
            }	
		});
	};
	
	//初始化表格
	initTable();;
	
	
});
