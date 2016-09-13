
function IsURL(str_url){ 
	var strRegex = /^((https|http|ftp|rtsp|mms)?:\/\/)?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\.[a-z]{2,6})(:[0-9]{1,4})?((\/?)|(\/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+\/?)$/;
    var re=new RegExp(strRegex);  
    if (re.test(str_url.trim())){ 
        return true;  
    }else{  
        return false;  
    } 
} 

function isSoild(solidFunctionArray,functionId){
		
	
	for(var i = 0 ;i < solidFunctionArray.length;i++){
		if(solidFunctionArray[i].id == functionId){
			return true;
		}
	}
	return false;
}
