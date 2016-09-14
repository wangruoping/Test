/**
 * 刷新tab
 * 
 * */
function refreshTab(cfg){
	var refresh_tab = cfg.tabTitle?$('#manageTab').tabs('getTab',cfg.tabTitle):$('#manageTab').tabs('getSelected');
	if(refresh_tab && refresh_tab.find('iframe').length > 0){
		var _refresh_ifram = refresh_tab.find('iframe')[0];
		var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;
		_refresh_ifram.contentWindow.location.href=refresh_url;
	}
}

$(function() {

	 var link = $('#content').find('link:first');
	 link.attr('href', 'styles/themes/Default/easyui.css');

    //用户列表
    $("#userList").on('click', function() {
    	var tab = $('.easyui-tabs').tabs('getTab', '用户列表');
    	
    	if (tab) {
            $('.easyui-tabs').tabs('select', '用户列表');
            refreshTab({tabTitle:'用户列表',url:"usersInfo"});
        } else {
            $('.easyui-tabs').tabs('add', {
                title : '用户列表',
                selected: true,
                closable: true,
                content:'<iframe scrolling="no" frameborder="0"  src="usersInfo" style="width:100%;height:100%;"></iframe>'
            });
        }
    });
    
    //模板列表
    $("#templateList").on('click', function() {
    	var tab = $('.easyui-tabs').tabs('getTab', '模板列表');
    	
    	if (tab) {
            $('.easyui-tabs').tabs('select', '模板列表');
            refreshTab({tabTitle:'模板列表',url:"templatesInfo"});
        } else {
            $('.easyui-tabs').tabs('add', {
                title : '模板列表',
                selected: true,
                closable: true,
                content:'<iframe scrolling="no" frameborder="0"  src="templatesInfo" style="width:100%;height:100%;"></iframe>'
            });
        }
    });

});


