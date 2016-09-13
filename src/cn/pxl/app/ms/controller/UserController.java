package cn.pxl.app.ms.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.portlet.ModelAndView;

import cn.pxl.app.ms.dto.CommentsDto;
import cn.pxl.app.ms.dto.ConsumeDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.RechargeDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.entity.CompanyUserEntity;
import cn.pxl.app.ms.service.UserService;
import cn.pxl.app.ms.util.CommonUtils;

@Controller
public class UserController {

	private Logger log = Logger.getLogger(UserController.class);
	
	@Resource(name="userService")
	private UserService userService;
	
	/**
	 * 本地登陆 用户添加
	 * 
	 * */
	@RequestMapping(value = "userLoginOrRegister") 
	public @ResponseBody Map<String, String> userLoginOrRegister(
			@RequestParam("acount") String acount,
			@RequestParam("password") String password,
			@RequestParam("handleType") String handleType
			){
		Map<String, String> result = new HashMap<String, String>();
		
		if("1".equals(handleType)){
			CompanyUserEntity userEntity = userService.findUserByAccountAndPassword(acount,CommonUtils.encodePassword(password));
			if(userEntity != null){
				result.put("flag", "0");
				result.put("userId", userEntity.getId());
				result.put("picPath", userEntity.getPicPath());
				result.put("name", userEntity.getUsername());
				result.put("address", userEntity.getAddress());
				result.put("userAddress", userEntity.getUserAddress());
				result.put("phone", userEntity.getPhone());
				result.put("youbian", userEntity.getYoubian());
				result.put("sex", userEntity.getSex());
			}else{
				result.put("flag", "1");
			}
		}else{
			
			CompanyUserEntity existUserEntity = userService.findUserByAccount(acount);
			if(existUserEntity != null){
				result.put("flag", "1");
			}else{
				CompanyUserEntity userEntity = new CompanyUserEntity();
				userEntity.setAcount(acount);
				userEntity.setAmount(new BigDecimal("0"));
				userEntity.setCreateDate(new Date());
				userEntity.setPassword(CommonUtils.encodePassword(password));
				userEntity.setLoginType("2");
				userEntity.setUsername("新人");
				userEntity.setSex("m");
				userEntity.setPicPath("");
				userEntity.setUserAddress("");
				userEntity.setAddress("");
				userEntity.setYoubian("");
				userService.addUser(userEntity);
				result.put("flag", "0");
				result.put("userId", userEntity.getId());
				result.put("picPath", userEntity.getPicPath());
				result.put("name", userEntity.getUsername());
				result.put("address", userEntity.getAddress());
				result.put("userAddress", userEntity.getUserAddress());
				result.put("phone", userEntity.getPhone());
				result.put("youbian", userEntity.getYoubian());
				result.put("sex", userEntity.getSex());
			}
			
		}
		
		return result;
	}
	
	/**
	 * 本地登陆 用户信息更新
	 * 
	 * */
	@RequestMapping(value = "updateUserInfo") 
	public @ResponseBody Map<String, String> updateUserInfo(
			@RequestParam("userid") String userid,
			@RequestParam("username") String username,
			@RequestParam("sex") String sex,
			@RequestParam("address") String address,
			@RequestParam("phone") String phone,
			@RequestParam("youbian") String youbian
			){
		Map<String, String> result = new HashMap<String, String>();
		CompanyUserEntity userEntity = userService.findUserEntityByUserId(userid);
		if(userEntity != null){
			userEntity.setSex(sex);
			userEntity.setAddress(address);
			userEntity.setUsername(username);
			userEntity.setPhone(phone);
			userEntity.setYoubian(youbian);
			userService.updateUser(userEntity);
			result.put("flag", "0");
		}else{
			result.put("flag", "1");
		}
		return result;
	}
	
	
	/**
	 * 获取用户关注好友列表
	 * 
	 * */
	@RequestMapping(value = "getUserGzList") 
	public @ResponseBody Map<String, List<UserDto>> getUserGzList(
			@RequestParam("userid") String userid
			){
		Map<String, List<UserDto>> map = new HashMap<String, List<UserDto>>();
		List<UserDto> list = userService.getUserGzList(userid);
		map.put("userList", list);
		return map;
	}
	
	/**
	 * 获取用户关注好友列表
	 * 
	 * */
	@RequestMapping(value = "getUserFsList") 
	public @ResponseBody Map<String, List<UserDto>> getUserFsList(
			@RequestParam("userid") String userid
			){
		Map<String, List<UserDto>> map = new HashMap<String, List<UserDto>>();
		List<UserDto> list = userService.getUserFsList(userid);
		map.put("userList", list);
		return map;
	}
	
	/**
	 * 插入用户关注信息
	 * 
	 * */
	 @RequestMapping(value = "insertGzInfo") 
	public @ResponseBody String insertGzInfo(
			@RequestParam("userid") String gzUserid,
			@RequestParam("userid") String bgzUserid
			){
		userService.insertGzInfo(gzUserid,bgzUserid);
		
		return "";
	}
	/**
	 * 获取用户信息
	 * 
	 * */
	 @RequestMapping(value = "getUserInfo") 
	public @ResponseBody UserDto getUserInfo(
			@RequestParam("userid") String userid
			){
		UserDto userDto = userService.getUserInfo(userid);
		
		return userDto;
	}
	 
	 
	 /**
	   * 获取用户杂志评论列表
	   * 
	   * */
	 @RequestMapping(value = "getUserCommentList") 
	 public @ResponseBody List<CommentsDto> getUserCommentList(@RequestParam("userid") String userid){
	 	
	 	List<CommentsDto> result = userService.getUserCommentList(userid);
	 	return result;
	 }
	 
	 /**
      * 获取用户杂志评论列表
      * 
      * */
	 @RequestMapping(value = "getMagCommentList") 
     public @ResponseBody List<CommentsDto> getMagCommentList(@RequestParam("userid") String userid){
   	
   	  List<CommentsDto> result = userService.getMagCommentList(userid);
   	  return result;
     }
     
     /**
      * 获取用户消费记录列表
      * 
      * */
	 @RequestMapping(value = "getUserConsumeList") 
     public @ResponseBody List<ConsumeDto> getUserConsumeList(@RequestParam("userid") String userid){
    	   	
      	  List<ConsumeDto> result = userService.getUserConsumeList(userid);
      	  return result;
     }
     
     /**
      * 获取用户充值记录列表
      * 
      * */
	 @RequestMapping(value = "getUserRechargeList") 
     public @ResponseBody List<RechargeDto> getUserRechargeList(@RequestParam("userid") String userid){
    	   	
      	  List<RechargeDto> result = userService.getUserRechargeList(userid);
      	  return result;
     }
	 
	 /**
	  * 用户充值
	  *
      */
	 @RequestMapping(value = "insertRecharge") 
     public @ResponseBody String insertRecharge(@RequestParam("userid") String userid,
    		 @RequestParam("rechargeAmount") String rechargeAmount,
    		 @RequestParam("rechargeFlag") String rechargeFlag,
    		 @RequestParam("rechargeType") String rechargeType,
    		 @RequestParam("rechargeTime") String rechargeTime){
		
      	  String flagString = userService.insertRecharge(userid,rechargeAmount , rechargeFlag , Integer.parseInt(rechargeType),rechargeTime);
      	  return flagString;
     }
	 
	 /**
	  * 用户余额是否够判断
	  *
      */
	 @RequestMapping(value = "validUserAmount") 
     public @ResponseBody String validUserAmount(@RequestParam("userid") String userid,
    		 @RequestParam("amount") String amount){
		
      	  String flagString = userService.validUserAmount(userid,amount);
      	  return flagString;
     }
	 
	 
	 /**
	  * 上传图片
	  * 
	  * */
	 @RequestMapping(value = "uploadUserImage")
	    public @ResponseBody Map<String, String> uploadUserImage(
	            @RequestParam("userImg") MultipartFile file,
	            @RequestParam("userid") String userid,
	            @RequestParam("usertype") String usertype,
	            @RequestParam("imageUrl") String imageUrl,
	            HttpServletRequest request) {
	        Map<String, String> result = new HashMap<String, String>();
        	log.info("自己注册用户上传头像");
	        if (!file.isEmpty() && userService.hasUser(userid)) {
	        	log.info("有文件，有用户，可以上传");
	            String picPath = userService.uploadUserImage(file, userid,imageUrl);
	            log.info("上传成功");
	            result.put("picPath", picPath);
	        } else {
	        	log.info("上传失败");
	            result.put("picPath", "");
	        }	
	        return result;
	    }
	 	/**
		 * 获取远程用户图片
		 * 
		 * */
		@RequestMapping(value = "getUserImage") 
		public @ResponseBody ModelAndView getUserImage(
				@RequestParam("userid") String userid,@RequestParam(value="r", required=false, defaultValue="1") String r, HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setContentType("text/html;charset=utf-8");  
		    request.setCharacterEncoding("UTF-8");  
		    java.io.BufferedInputStream bis = null;  
		    java.io.BufferedOutputStream bos = null;  
			
			File file = userService.getUserImage(userid);
			if(file != null){
	        	String fileName = file.getName();
	        	try {  
	                long fileLength = file.length();  
	                response.setContentType("application/x-msdownload;");  
	                response.setHeader("Content-disposition", "attachment; filename="  
	                        + new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
	                response.setHeader("Content-Length", String.valueOf(fileLength));  
	                bis = new BufferedInputStream(new FileInputStream(file));  
	                bos = new BufferedOutputStream(response.getOutputStream());  
	                byte[] buff = new byte[2048];  
	                int bytesRead;  
	                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	                    bos.write(buff, 0, bytesRead);  
	                }  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            } finally {  
	                if (bis != null)  
	                    bis.close();  
	                if (bos != null)  
	                    bos.close();  
	            }  
	        }
			return null;
		}
	 
	 //****************************服务端操作*************//
	 
	 /**跳转到用户管理页*/
     @RequestMapping("/usersInfo")
	 public ModelAndView usersInfo(HttpSession session) {
		
    	 Map<String,String> map = new HashMap<String, String>();
    	 map.put("imageList", "");
    	 return new ModelAndView("usersInfo", map);		
	 }
	 
	 /**
	  * 用户列表
	  * 
	  * */
	 @RequestMapping(value = "userList")
     public @ResponseBody PagingDto<UserDto> userList(
            @RequestParam("page") String page,
            @RequestParam("rows") String rows) {

        int offset = Integer.parseInt(page);
        int limit = Integer.parseInt(rows);

        return userService.pagingList(offset, limit,0);
    }
	
	 /**
      * 获取用户充值记录列表
      * 
      * */
	 @RequestMapping(value = "getServerUserRechargeList") 
     public @ResponseBody List<RechargeDto> getServerUserRechargeList(@RequestParam("userid") String userid){
    	   	
      	  List<RechargeDto> result = userService.getServerUserRechargeList(userid);
      	  return result;
     }
	 
	 /**
	  * 接收日志文件
	  * 
	  * */
	 @RequestMapping(value = "uploadLogFile") 
     public @ResponseBody void uploadLogFile(@RequestParam("logInfo") String logInfo){
    	   	
		 String path = CommonUtils.getConfig("upload.logfile.path");
         File savePath = new File(path + UUID.randomUUID().toString() + ".txt");
         try {
            FileUtils.writeStringToFile(savePath, logInfo);
         } catch (IOException e) {
        	e.printStackTrace();
         }
     }
	 
	 /**
	  * 加载服务器图片显示到页面中
	  * 
	  * */
	 @RequestMapping(value = "loadImage") 
     public @ResponseBody void loadImage(@RequestParam("imgPath") String imgPath,HttpServletResponse response){
    	   	
		 String path = CommonUtils.getConfig("upload.logfile.path");
         FileInputStream in;  
         response.setContentType("application/octet-stream;charset=UTF-8");  
         try {  
             //图片读取路径  
            //in=new FileInputStream("E:\\picture\\"+photoName);  
            in=new FileInputStream(path + imgPath);  
            int i=in.available();  
            byte[] data=new byte[i];  
            in.read(data);  
            in.close();  
              
            //写图片  
            OutputStream outputStream=new BufferedOutputStream(response.getOutputStream());  
            outputStream.write(data);  
            outputStream.flush();  
            outputStream.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
     }
	 
	 /**
	  * 加载服务器音频文件
	  * 
	  * */
	 @RequestMapping(value = "loadVideo") 
     public @ResponseBody void loadVideo(@RequestParam("loadVideoPath") String loadVideoPath,HttpServletResponse response){
    	   	
		 String path = CommonUtils.getConfig("upload.logfile.path");
         FileInputStream in;  
         response.setContentType("application/octet-stream;charset=UTF-8");  
         try {  
             //图片读取路径  
            //in=new FileInputStream("E:\\picture\\"+photoName);  
            in=new FileInputStream(path + loadVideoPath);  
            int i=in.available();  
            byte[] data=new byte[i];  
            in.read(data);  
            in.close();  
              
            //写图片  
            OutputStream outputStream=new BufferedOutputStream(response.getOutputStream());  
            outputStream.write(data);  
            outputStream.flush();  
            outputStream.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
     }
	 
}
