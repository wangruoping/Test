;(function($){
	 $.common = function() {
		 
		 var that = this;
		 
		 var _ajax = function(url, param, callback, async,msg) {
	            var realAsync = async ? true : async;
	            var realUrl = url;
	           
	            AUTOLAYOUT.loading(msg);
	            $.ajax({
	                type: "POST",
	                url: realUrl,
	                //contentType: "application/json;charset=utf-8",
	                cache: false,
	                async: realAsync,
	                data: param,
	                dataType: "json",
	                success: function(returnData) {
	                	callback(returnData);
	                	AUTOLAYOUT.loadingOut();
	                },
	                error : function(XMLHttpRequest, textStatus, errorThrown) {
	                	AUTOLAYOUT.loadingOut();
	                    
	                }
	            });
	       }; 
		 
		 /**
		  * ajax请求函数
		  * 
		  * */
		 this.click = function(url, param, callback, async,msg) {
	            _ajax(url, param, callback, async,msg);
	     };
	     
	     /**
	      * form 表单页面跳转函数
	      * 
	      * */
	      var flagForJumpUrlOneTime = false;
	      this.jumpPage = function(url,data,msg){
	    	  AUTOLAYOUT.loading(msg);
	    	  if (flagForJumpUrlOneTime) {
	                return;
	          }
	          flagForJumpUrlOneTime = true;
	          var tempForm = $("#jumpPageFrom");
	          var rtInput = "";
	          rtInput.removeAttr("id");
              $(tempForm).append(rtInput);
              if (data) {
                for (var i in data) {
                    var tmpinput = $("<input>");
                    $(tmpinput).attr("name", i);
                    $(tmpinput).attr("value", data[i]);
                    $(tempForm).append(tmpinput);
                }
              }
              $(tempForm).attr("action", url);
	          $(tempForm).attr("method", "post");
	          $(tempForm).submit();	    	  	        
	    	  return false;
	    	    
	      };
	      
	      /**
	       * form表单提交验证
	       * 
	       * */
	      this.formClick = function(url,formId,callback,msg){

	          var realUrl = url;
	          
	          AUTOLAYOUT.loading(msg);
	    	  $('#'+formId).form('submit',{  
	    	        url:realUrl,  
	    	        onSubmit:function(){  	  
	    	        	if(!$(this).form('validate')){
	    	        		AUTOLAYOUT.loadingOut();
	    	        		return false;
	    	        	}
	    	        	
	    	            return true;  
	    	        },  
	    	        success:function(data){  
	    	        	AUTOLAYOUT.loadingOut();
	    	        	callback(data);  
	    	        }  
	    	    }); 
	      };
	      
	      this.getFunctionIdList = function(){
	    	  
	    	 if(self.frameElement && self.frameElement.tagName=="IFRAME"){	  
	                return $("#functionIdList",parent.document).val();
 			 }else{
 				    return $("#functionIdList").val();
 			 }	
	      };
	      
	     /**
	      * token设置
	      * 
	      * */
	     this.tokenSet = function(data){	
	    	 console.log("======================================================");
	    	 console.log(data);
	    	 console.log("======================================================");
	    	 
	    	 if(data != null){
	    		 
	    		//session超时
	    		if(data.status == 2){
		    		 $.messager.alert(AUTOMESSAGE["label.0029"],AUTOMESSAGE["message.0033"],"error",function(){
		    			 if(self.frameElement && self.frameElement.tagName=="IFRAME"){	  
		    				 parent.iframeLogout();       
		    			 }else{
		    				 that.jumpPage("login.html",{},AUTOMESSAGE["message.0036"]);	
		    			 }
		    		});
		    		return false;
		    	}
	    		 
	    		//系统异常
	    		if(data.status == 3){
	    			 $.messager.alert(AUTOMESSAGE["label.0029"],AUTOMESSAGE["message.0034"],"error",function(){
	    				 if(self.frameElement && self.frameElement.tagName=="IFRAME"){	  
	    					 parent.iframeLogout();       
		    			 }else{
		    				 that.jumpPage("login.html",{},AUTOMESSAGE["message.0036"]);	
		    			 }		    				
		    		});
	    			return false;
	    		 }	    		 	    		 
	    		 
	    		 //token异常
	    		 if(data.status == 4){
		    		 $.messager.alert(AUTOMESSAGE["label.0029"],AUTOMESSAGE["message.0038"],"error",function(){
		    			 if(self.frameElement && self.frameElement.tagName=="IFRAME"){	  
		    				 parent.iframeLogout();       
		    			 }else{
		    				 that.jumpPage("login.html",{},AUTOMESSAGE["message.0036"]);	
		    			 }      
		    		});
		    		return false;
		    	 }
	    		 
	    		 //正常访问服务器情况，否则是loadData访问本地情况（不需要更新token）
	    		 if(data.status){
	    			 var requestToken = data.rt;	
	    			 console.log("_____________________________");
	    			 console.log(requestToken);
	    			 console.log("_____________________________");
	    			 //判断是否是iframe中的操作
	    			 if(self.frameElement && self.frameElement.tagName=="IFRAME"){	   	    				 
	    				 $("#requestToken",parent.document).val(requestToken);
	    			 }else{
	    				 $("#requestToken").val(requestToken);
	    			 }
				    
	    		 }
			    
			     
			     return true;
	    	 }else{
	    		 return false;
	    	 }
	    	 
	    	     	
	     };
	    	 
	 };
})(jQuery);