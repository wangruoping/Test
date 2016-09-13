var autoCommon;

function initCzTable(userId){
	$("#userCzTable").datagrid({
		url:'getServerUserRechargeList',
		pagination:false,
		rownumbers:true,
		singleSelect:true,
		selectOnCheck:false,
		queryParams: {
			userid: userId
		},
		fit:true,
		columns:[[
            {field:'rechargeAmount',title:'充值金额',width:60},
            {field:'rechargeTime',title:'充值时间',width:100}
        ]]
	});
}

function czList(userId){
	
	$("#popWindow").window("open");
	$('userCzTable').datagrid('load',{
		userid: userId
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
		
		$("#userTable").datagrid({
			url:'userList',
			pagination:true,
			rownumbers:true,
			singleSelect:true,
			selectOnCheck:false,		
			fit:true,
			columns:[[
                {field:'username',title:'用户名',width:200,align:'right'},
                {field:'amount',title:'用户余额',width:60},
                {field:'userThridId',title:'第三方用户ID',width:100},
                {field:'userId',title:'充值记录',width:60,align:'center',
                    formatter:function(value,rec){

                        var btn = '<a class="editcls" onclick="czList(\''+rec.userId+'\')" href="javascript:void(0)">查看</a>';
                        return btn;  
                    }
                }
            ]],
            pagination: true,
            onLoadSuccess:function(data){
                $('.editcls').linkbutton({text:'查看',plain:true,iconCls:'icon-edit'});
            }	
		});
	};
	
	//初始化表格
	initTable();
	initCzTable("");
	
	
});
