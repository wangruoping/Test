var autoCommon;

$(function(){
	//共同方法对象
	autoCommon = new $.common;

	/**
	 * 初始化表格
	 * 
	 * */
	function initTable(){
		
		$("#productDataTable").datagrid({
			url:'productDataList',
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
                       if(value == 1){
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
	
});
