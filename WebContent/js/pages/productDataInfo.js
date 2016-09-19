var autoCommon;

$(function(){
	//共同方法对象
	autoCommon = new $.common;

	/**
	 * 初始化表格
	 * 
	 * */
	function initTable(){
		//获取表字段信息
		autoCommon.click("getAllProductTableInfo", {}, function(data){
			if(data.status == 1){
				 console.log(data.content.toString());
				 $('#productDataTable').datagrid({ 
                     url: 'productDataList',
                     rownumbers:true,
         			 singleSelect:true,
         			 selectOnCheck:false,
         			 fit:true,
         			 toolbar:'#toolbar',	
                     pagination:true,
                     fitColumns:true,
                     idField:"id",
                     columns:data.content,
                     queryParams:{},
                     onSelect:function(rowIndex, rowData){
                        
                     }
                 });
			}
		});	
	};
	
	//初始化表格
	initTable();
	
	 $('#productDataTable').edatagrid({
         url: 'getProductInfo',
         saveUrl: 'saveProductInfo',
         updateUrl: 'updateProductInfo',
         destroyUrl: 'deleteProductInfo'
     });
	
});
