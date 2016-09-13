var autoCommon;

/**
 * 编辑用户信息
 * */
function editUserInfo(userId){
	//获取用户信息
	
}

$(function(){
	//共同方法对象
	autoCommon = new $.common;

	/**
	 * 初始化表格
	 * 
	 * */
	function initTable(){
		
		$("#userTable").datagrid({
			url:'userList',
			pagination:true,
			rownumbers:true,
			singleSelect:true,
			selectOnCheck:false,		
			fit:true,
			columns:[[
                {field:'username',title:'用户名',width:200,align:'right'},
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
	
});
