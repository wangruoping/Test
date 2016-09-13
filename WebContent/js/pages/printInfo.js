var autoCommon;

function downloadMag(id){
    window.open("downloadMagzines?magId="+id+"");
}
function downloadZoomMagzines(id){
    window.open("downloadZoomMagzines?magId="+id+"");
}



function cfMag(magId,id){
	//杂志拆分
	autoCommon.click("cfMagzines", {"magId":magId,"id":id}, function(data){
		if(data.status == 1){
			$.messager.alert("提示","杂志拆分成功！","info",function(){					
				$("#printTable").datagrid("load");
			});
		}else if(data.status == 0){
			$.messager.alert("提示","杂志拆分失败！","error",function(){					
				$("#printTable").datagrid("load");
			});
		}else{
			$.messager.alert("提示","被人正在拆分中。。。","error",function(){					
				$("#printTable").datagrid("load");
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

        $("#printTable").datagrid({
            url:'printList',
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            toolbar:'#toolbar',
            selectOnCheck:false,
            fit:true,
            columns:[[
                {field:'username',title:'打印人',width:80,align:'center'},
                {field:'address',title:'发货地址',width:300},
                {field:'phone',title:'联系电话',width:100},
                {field:'youbian',title:'邮编',width:100},
                {field:'printTime',title:'请求打印时间',width:150},
                {field:'fileName',title:'下载打印杂志',width:100,align:'center',
                    formatter:function(value,rec){

                        var btnVar = '<a class="cfcls" onclick="cfMag(\''+rec.fileName+'\',\''+rec.id+'\')" href="javascript:void(0)">拆分</a>';
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
                {field:'id',title:'下载原始版',width:80,align:'center',
                    formatter:function(value,rec){
                        if(rec && rec.cfFlag && rec.cfFlag == 1){
                        	var btn = '<a class="downloadcls" onclick="downloadZoomMagzines(\''+rec.fileName+'\')" href="javascript:void(0)">下载</a>';
                        	return btn;
                        }
                        return '';
                    }
                }
            ]],
            pagination: true,
            onLoadSuccess:function(data){
                console.log(data);
                $('.cfcls').linkbutton({text:'拆分',plain:true,iconCls:'icon-add'});

                $('.downloadcls').linkbutton({text:'下载',plain:true,iconCls:'icon-edit'});
            }
        });
    };

    //初始化表格
    initTable();

    //打印金按钮事件
    $("#userAmountSet").bind('click',function(){
        //加载类别列表
        autoCommon.click("getPrintAmount", {}, function(data){
            if(data.status == 1){
                $("#amount").val(data.content);
            }
        }, true);
        //打开用打印金设定窗口
        $("#popWindow").window("open");
    });

    //设定按钮事件
    $("#setBtn").bind('click',function(){

        if($('#amount').val().trim() == ""){
            $.messager.alert("提示","请输入打印金额!","error",function(){});
            return ;
        }

        autoCommon.formClick("setPrintAmount","printAmountForm", function(data){
            var obj = eval('('+data+')');
            if(obj.status == 1){
                $.messager.alert("提示","打印金设定成功！","info",function(){
                    $("#popWindow").window("close");
                });
            }else{
                $.messager.alert("提示","打印金设定失败！","error",function(){});
            }
        });
    });

});
