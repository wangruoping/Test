var AUTOLAYOUT = {
	
	/**
	 * loading start
	 * 
	 * @param  msg
	 * */
	loading:function(Msg){
		
		 var message = "加载中";
		 var height = window.screen.height-250;  
		 var width = window.screen.width;  
		 var leftW = 300;  
		 if(width>1200){  
		    leftW = 500;  
		 }else if(width>1000){  
		    leftW = 350;  
		 }else {  
		    leftW = 100;  
		 }
		 
		 if(Msg != undefined){
			 message = Msg;
		 }
		 
		 var mask = "<div id='loading' style='z-index:10000;position:absolute;left:0;width:100%;height:"+height+"px;top:0;background:#E0ECFF;opacity:0.8;filter:alpha(opacity=30);'>"  
		  + "<div style='position:absolute;  cursor1:wait;left:"+leftW+"px;top:200px;width:auto;height:16px;padding:12px 5px 10px 30px;font-family: monospace;"
		  + "background:#fff url(/mmss/styles/themes/default/images/loading.gif) no-repeat scroll 5px 10px;border:2px solid #ccc;color:#000;'>"  
		  + message 
		  + "</div></div>"; 
		 $mask = $(mask);
		 $('body').append($mask);
	},

	/**
	 * loading ends
	 * */
	loadingOut:function(){
	    $("#loading").detach().remove();
	}
};