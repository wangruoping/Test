package cn.pxl.app.ms.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.portlet.ModelAndView;

import cn.pxl.app.ms.dao.MagzinesDao;
import cn.pxl.app.ms.dto.CommentsDto;
import cn.pxl.app.ms.dto.MagzinesCategoryDto;
import cn.pxl.app.ms.dto.MagzinesDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.PrintDto;
import cn.pxl.app.ms.dto.ResultDto;
import cn.pxl.app.ms.entity.MagzinesCategoryEntity;
import cn.pxl.app.ms.entity.MagzinesEntity;
import cn.pxl.app.ms.form.MagzinesCategoryForm;
import cn.pxl.app.ms.form.MagzinesForm;
import cn.pxl.app.ms.form.PrintAmountFrom;
import cn.pxl.app.ms.service.MagzinesService;
import cn.pxl.app.ms.service.UserService;
import cn.pxl.app.ms.util.CommonUtils;


@Controller
public class MagzinesController {

    @Resource(name="magzinesService")
    private MagzinesService magzinesService;
  
    
    @Resource(name="magzinesDao")
    private MagzinesDao magzinesDao;

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping(value = "uploadMagzines")
    public @ResponseBody Map<String, String> uploadMagzines(
            @RequestParam("uploadFile") MultipartFile file,
            @RequestParam("prevImg1") MultipartFile prev1,
            @RequestParam("prevImg2") MultipartFile prev2,
            @RequestParam("prevImg3") MultipartFile prev3,
            @RequestParam("tplName") String tplName,
            @RequestParam("fileComment") String fileComment,
            @RequestParam("address") String address,
            @RequestParam("phone") String phone,
            @RequestParam("youbian") String youbian,
            @RequestParam("payAmount") String payAmount,
            @RequestParam("magAmount") String magAmount,
            @RequestParam("flag") String flag,
            @RequestParam("magCateId") String magCateId,
            @RequestParam("userid") String userid) {
        Map<String, String> result = new HashMap<String, String>();
        if (!file.isEmpty() && userService.hasUser(userid)) {
            String magServerId = magzinesService.uploadMagzine(file,prev1,prev2,prev3, tplName, 
            		fileComment, userid,address,phone,youbian,payAmount,magAmount,flag,magCateId);
            result.put("s", magServerId);
        } else {
            result.put("s", "");
        }
        return result;
    }

    

    @RequestMapping(value = "downloadOriginMagzines")
    public ModelAndView downloadOriginMagzines(@RequestParam("downFileName") String downFileName,@RequestParam("userid") String userid,@RequestParam("magId") String magId, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;

        File file = magzinesService.downloadOrigin(downFileName);

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
                
                //插入下载信息
                magzinesService.insertDownloadEntity(magId,userid);
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

    @RequestMapping(value = "getUserMagList")
    public @ResponseBody Map<String,Object> getUserMagList(
            @RequestParam("userid") String userid,
            @RequestParam("pageIndex") String pageIndex){
        Map<String,Object> map = new HashMap<String,Object>();
        List<MagzinesDto> list = magzinesService.getUserMagList(userid,Integer.parseInt(pageIndex));
        
        map.put("magList", list);
        map.put("maCount", magzinesDao.getUserMagCount(userid));
        return map;
    }
    
    @RequestMapping(value = "getUserMagAllList")
    public @ResponseBody Map<String,Object> getUserMagAllList(
            @RequestParam("userid") String userid){
        Map<String,Object> map = new HashMap<String,Object>();
        List<MagzinesDto> list = magzinesService.getUserMagAllList(userid);
        
        map.put("magList", list);
        return map;
    }

    /**
     * 获取杂志评论列表
     *
     * @param magId
     * */
    @RequestMapping(value = "getCommentsList")
    public @ResponseBody Map<String, List<CommentsDto>> getCommentsList(@RequestParam("magId") String magId,@RequestParam("commentDate") String commentDate){
        Map<String, List<CommentsDto>> map = new HashMap<String, List<CommentsDto>>();
        List<CommentsDto> resultCommentsDtos = magzinesService.getCommentsList(magId,commentDate);
        map.put("result", resultCommentsDtos);
        return map;
    }
    
    /**
     * 获取杂志分类列表
     *
     * @param magId
     * */
    @RequestMapping(value = "getCategorysList")
    public @ResponseBody Map<String, List<MagzinesCategoryDto>> getCategorysList(){
        Map<String, List<MagzinesCategoryDto>> map = new HashMap<String, List<MagzinesCategoryDto>>();
        List<MagzinesCategoryDto> resultMagzinesCategoryDtos = magzinesService.getCategorysList();
        map.put("result", resultMagzinesCategoryDtos);
        return map;
    }
    
    /**
	 * 获取远程杂志图片
	 * 
	 * */
	@RequestMapping(value = "getMagImageList") 
	public @ResponseBody ModelAndView getMagImageList(
			@RequestParam("magid") String magid,@RequestParam("imageName") String imageName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");  
	    request.setCharacterEncoding("UTF-8");  
	    java.io.BufferedInputStream bis = null;  
	    java.io.BufferedOutputStream bos = null;  
		
		File file = magzinesService.getMagImageList(magid, imageName);
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

    /**
     * 添加杂志转发信息
     *
     * */
    @RequestMapping(value = "insertMagZf")
    public @ResponseBody String insertMagZf(@RequestParam("magId") String magId,
                                            @RequestParam("zfCommentContent") String zfCommentContent,
                                            @RequestParam("userid") String userid){
        return magzinesService.insertMagZf(magId,zfCommentContent,userid);
    }

    /**
     * 添加评论信息
     *
     * */
    @RequestMapping(value = "insertComment")
    public @ResponseBody String insertComment(@RequestParam("magId") String magId,
                                            @RequestParam("commentContent") String commentContent,
                                            @RequestParam("userid") String userid){
        return magzinesService.insertComment(magId,commentContent,userid);
    }

    /**
     * 获取杂志信息
     *
     *
     * */
    @RequestMapping(value = "getMagInfo")
    public @ResponseBody Map<String, String> getMagInfo(
    		@RequestParam("magId") String magId,
    		@RequestParam("userid") String userid){
        Map<String,String> map = new HashMap<String, String>();
        MagzinesEntity magzinesEntity = magzinesService.findById(magId);
        if(magzinesEntity != null){
            map.put("fileComment", magzinesEntity.getFileComment());
            map.put("fbDate", magzinesEntity.getUploadTime().toString());
            map.put("username", magzinesEntity.getUserEntity().getUsername());
            map.put("userImgPath", magzinesEntity.getUserEntity().getPicPath());
            map.put("sex", magzinesEntity.getUserEntity().getSex());
            map.put("userId", magzinesEntity.getUserEntity().getId());
            map.put("shopFlag", magzinesEntity.getPublicTypeSet().toString());
            
            if(magzinesEntity.getFirstMagId() != null && !"".equals(magzinesEntity.getFirstMagId())){
            	//转发的杂志
            	MagzinesEntity firstMagzinesEntity = magzinesService.findById(magzinesEntity.getFirstMagId());
            	if(firstMagzinesEntity != null){
            		//判断是否是好友，商店的产品
            		if(firstMagzinesEntity.getPublicTypeSet() == 1){
            			
            			//判断是否已购买
            			boolean flag = magzinesService.findUserBuyPro(userid, firstMagzinesEntity.getId());
            			if(flag){
            				map.put("buyFlag", "1");
            			}else{
            				//判断是否在免费时间段内
                			if(firstMagzinesEntity.getStartTime() != null && firstMagzinesEntity.getEndTime() != null){
                				Date now = new Date();
                				if(now.after(firstMagzinesEntity.getStartTime()) && now.before(firstMagzinesEntity.getEndTime())){
                					map.put("buyFlag", "1");
                				}
                			}else{
                				map.put("buyFlag", "0");
                			}
            			}
            		}else{
            			map.put("buyFlag", "2");
            		}
            		map.put("downloadFileName", firstMagzinesEntity.getFileName());
            		if(magzinesEntity.getZfMagId() != null && !"".equals(magzinesEntity.getZfMagId())){
            			map.put("zfMagId", magzinesEntity.getZfMagId());
            		}else if(magzinesEntity.getBuyMagId() != null && !"".equals(magzinesEntity.getBuyMagId())){
            			map.put("zfMagId", magzinesEntity.getBuyMagId());
            		}
            		map.put("firstMagId", magzinesEntity.getFirstMagId());
            	}else{
            		map.put("downloadFileName", "");
            		map.put("zfMagId", "");
            		map.put("buyFlag", "2");
            		map.put("firstMagId", "");
            	}
            	map.put("zfFlag","1");
            	
            }else{
            	if(magzinesEntity.getFileName() == null){
                    map.put("downloadFileName", "");
                }else{
                    map.put("downloadFileName", magzinesEntity.getFileName());
                }
            	map.put("zfFlag","0");
            	map.put("zfMagId", "");
            	map.put("magId", magzinesEntity.getId());
            	map.put("buyFlag", "2");
            	map.put("firstMagId", "");
            }
        }else{
            map.put("fileComment", "");
            map.put("fbDate", "");
            map.put("username", "");
            map.put("userImgPath", "");
            map.put("downloadFileName", "");
            map.put("zfFlag","0");
            map.put("zfMagId", "");
        }
        return map;
    }
    
    /**
     * 获取杂志信息
     *
     *
     * */
    @RequestMapping(value = "getMagProductInfo")
    public @ResponseBody Map<String, String> getMagProductInfo(@RequestParam("magId") String magId,@RequestParam("userid") String userid){
        Map<String,String> map = new HashMap<String, String>();
        MagzinesEntity magzinesEntity = magzinesService.findById(magId);	
        if(magzinesEntity != null){
        	map.put("userId", magzinesEntity.getUserEntity().getId());
            map.put("fileComment", magzinesEntity.getFileComment());
            map.put("fbDate", magzinesEntity.getUploadTime().toString());
            map.put("username", magzinesEntity.getUserEntity().getUsername());
            map.put("userImgPath", magzinesEntity.getUserEntity().getPicPath());
            map.put("fileAmount", magzinesEntity.getFileAmount().toString());
            map.put("sex", magzinesEntity.getUserEntity().getSex());
            if("9".equals(magzinesEntity.getUserEntity().getLoginType())){
            	map.put("shopFlag", magzinesEntity.getPublicTypeSet().toString());
            }else{
            	map.put("shopFlag", "0");
            }
            
            //查询用户是否购买改产品
            boolean flag = magzinesService.findUserBuyPro(userid,magId);
            if(flag){
            	map.put("freeFlag", "1");
            }else{
            	if(magzinesEntity.getStartTime() != null && magzinesEntity.getEndTime() != null){
                	Date now = new Date();
                	if(now.after(magzinesEntity.getStartTime()) && now.before(magzinesEntity.getEndTime())){
                		map.put("freeFlag", "1");
                	}else{
                		map.put("freeFlag", "0");
                	}
                }else{
                	map.put("freeFlag", "0");
                }
            }
            
            
            
            if(magzinesEntity.getZfMagId() != null && !"".equals(magzinesEntity.getZfMagId())){
            	//转发的杂志
            	MagzinesEntity firstMagzinesEntity = magzinesService.findById(magzinesEntity.getFirstMagId());
            	if(firstMagzinesEntity != null){
            		map.put("downloadFileName", firstMagzinesEntity.getFileName());
            	}else{
            		map.put("downloadFileName", "");
            		
            	}
            	
            }else{
            	if(magzinesEntity.getFileName() == null){
                    map.put("downloadFileName", "");
                }else{
                    map.put("downloadFileName", magzinesEntity.getFileName());
                }
            }
            
            

        }else{
            map.put("fileComment", "");
            map.put("fbDate", "");
            map.put("username", "");
            map.put("userImgPath", "");
            map.put("downloadFileName", "");
            map.put("freeFlag", "0");
        }
        return map;
    }

    /**
     * 添加点赞信息
     *
     * */
    @RequestMapping(value = "insertOrDeleteAgree")
    public @ResponseBody String insertAgree(@RequestParam("magId") String magId,@RequestParam("userid") String userid){
        return magzinesService.insertAgree(magId,userid);
    }

    /**
     * 获取点赞数及是否当前用户点赞
     *
     * */
    @RequestMapping(value="getAgreesCount")
    public @ResponseBody Map<String, String> getAgreesCount(@RequestParam("magId") String magId,@RequestParam("userid") String userid){
        return magzinesService.getAgreesCount(magId,userid);
    }

    /**
     * 获取好友杂志列表
     *
     * */
    @RequestMapping(value="getFriendMagList")
    public @ResponseBody Map<String, List<MagzinesEntity>> getFriendMagList(@RequestParam("userid") String userid){
        return magzinesService.getFriendMagList(userid);
    }
    
    /**
     * 获取好友杂志列表
     *
     * */
    @RequestMapping(value="getFriendMagDetailList")
    public @ResponseBody Map<String, List<MagzinesDto>> getFriendMagDetailList(
    		@RequestParam("userid") String userid,
    		@RequestParam("pageIndex") String pageIndex,
    		@RequestParam("pageSize") String pageSize){
        return magzinesService.getFriendMagDetailList(userid,Integer.parseInt(pageIndex),Integer.parseInt(pageSize));
    }
    
    /**
     * 获取商店杂志列表
     *
     * */
    @RequestMapping(value="getShopMagDetailList")
    public @ResponseBody Map<String, List<MagzinesDto>> getShopMagDetailList(
    		@RequestParam("userid") String userid,
    		@RequestParam("searchText") String searchText,
    		@RequestParam("pageIndex") String pageIndex,
    		@RequestParam("pageSize") String pageSize){
        return magzinesService.getShopMagDetailList(userid,searchText,Integer.parseInt(pageIndex),Integer.parseInt(pageSize));
    }

    /**
     * 获取杂志的转发数
     *
     * */
    @RequestMapping(value="getMagZfListCount")
    public @ResponseBody Map<String, String> getMagZfListCount(@RequestParam("magId") String magId){
        return magzinesService.getMagZfListCount(magId);
    }
    
    /**
     * 获取相应类别下的杂志列表
     * 
     * */
    @RequestMapping(value="getMagProductList")
    public @ResponseBody List<MagzinesEntity> getMagProductList(@RequestParam("categoryId") String categoryId,
    		@RequestParam("top") int top){
        return magzinesService.getMagProductList(categoryId,top);
    }
    
    /**
     * 获取相应类别下的杂志列表
     * 
     * */
    @RequestMapping(value="getMagDetailProductList")
    public @ResponseBody List<MagzinesEntity> getMagDetailProductList(@RequestParam("categoryId") String categoryId,
    		@RequestParam("pageIndex") String pageIndex){
        return magzinesService.getMagDetailProductList(categoryId,pageIndex);
    }
    
    /**
     * 获取全部杂志类别信息
     * 
     * */
    @RequestMapping(value="getAllCategoryList")
    public @ResponseBody List<MagzinesCategoryEntity> getAllCategoryList(){
    	List<MagzinesCategoryEntity> result = magzinesService.getAllCategoryList();
		return result;
    }
    
    /**
     * 根据条件获取免费杂志产品信息列表
     * (包含排序)
     * int orderType 排序类型
     * 
     * 1，免费开始时间排序
     * 2，免费结束时间排序
     * 3，下载数
     * 4，关注数
     * 5, 按上架时间排序
     * */
    @RequestMapping(value="getAllMagProductList")
    public @ResponseBody List<MagzinesDto> getAllMagProductList(
    		@RequestParam("orderType") int orderType,
    		@RequestParam("pageIndex") String pageIndex){
    	List<MagzinesDto> result = magzinesService.getAllMagProductList(orderType,pageIndex);
		return result;
    }
    
    /**
     * 最热杂志信息列表获取
     * 
     * 
     * */
    @RequestMapping(value="getHotMagProductList")
    public @ResponseBody List<String> getHotMagProductList(@RequestParam("top") int top){
    	List<String> result = magzinesService.getHotMagProductList(top);
		return result;
    }
    
    /**
     * 购买杂志
     *
     * insertConsumeInfo
     * */
    @RequestMapping(value="insertConsumeInfo")
    public @ResponseBody String insertConsumeInfo(
    		@RequestParam("magId") String magId,
    		@RequestParam("userid") String userid,
    		@RequestParam("consumeFlag") String consumeFlag){
    	String flag = magzinesService.insertConsumeInfo(magId,userid,consumeFlag);
		return flag;
    }
    
    /**
     * 打印金获取
     * */
    @RequestMapping(value="getClientPrintAmount")
    public @ResponseBody String getClientPrintAmount(){
    	
    	String amount = magzinesService.getPrintAmount();
		return amount;
	}
    
    
    /**************************服务端管理****************************/
    /**跳转到产品类别管理页*/
    @RequestMapping("/magzinesCategoryInfo")
	public ModelAndView magzinesCategoryInfo(HttpSession session) {
		return new ModelAndView("magzinesCategoryInfo");		
	}
    
    /**跳转到册子产品管理页*/
    @RequestMapping("/printInfo")
	public ModelAndView printInfo(HttpSession session) {
		return new ModelAndView("printInfo");		
	}
    
    /**跳转到打印请求管理页*/
    @RequestMapping("/magzinesProductInfo")
	public ModelAndView magzinesProductInfo(HttpSession session) {
		return new ModelAndView("magzinesProductInfo");		
	}
    
    /**跳转到册子管理页*/
    @RequestMapping("/magzinesInfo")
	public ModelAndView magzinesInfo(HttpSession session) {
		return new ModelAndView("magzinesInfo");		
	}
    
    /**
     * 客户端上传杂志列表
     * 
     * */
    @RequestMapping(value = "uploadMagzinesList.html")
    public @ResponseBody PagingDto<MagzinesDto> uploadMagzinesList(
            @RequestParam("page") String page,
            @RequestParam("rows") String rows) {

        int offset = Integer.parseInt(page);
        int limit = Integer.parseInt(rows);

        return magzinesService.pagingList(offset, limit,0);
    }
    
    /**
     * 册子杂志拆分
     * 
     * */
    @RequestMapping(value = "magCfMagzines")
    public @ResponseBody String magCfMagzines(HttpSession session
    		,@RequestParam("magId") String magId
    		,@RequestParam("id") String id)
            throws Exception {
    	
    	ResultDto rd = new ResultDto();
    	
    	int flag = magzinesService.magCfMagzines(magId,id);
    	if(flag == 1){
    		rd.setStatus("1");
    	}else if(flag == 2){
    		rd.setStatus("0");
    		String uploadPath = CommonUtils.getConfig("upload.file.path");
            File savePath = new File(uploadPath + magId + ".zip");
    		FileUtils.deleteDirectory(savePath);
    	}else{
    		rd.setStatus("2");
    	}
    	
    	return CommonUtils.convertResult(rd, session);
    }
    
    /**
     * 请求打印杂志拆分
     * 
     * */
    @RequestMapping(value = "cfMagzines")
    public @ResponseBody String cfMagzines(HttpSession session
    		,@RequestParam("magId") String magId
    		,@RequestParam("id") String id)
            throws Exception {
    	
    	ResultDto rd = new ResultDto();
    	
    	int flag = magzinesService.cfMagzines(magId,id);
    	if(flag == 1){
    		rd.setStatus("1");
    	}else if(flag == 2){
    		rd.setStatus("0");
    		String uploadPath = CommonUtils.getConfig("upload.file.path");
            File savePath = new File(uploadPath + magId + ".zip");
    		FileUtils.deleteDirectory(savePath);
    	}else{
    		rd.setStatus("2");
    	}
    	
    	return CommonUtils.convertResult(rd, session);
    }
    
    /**
     * 杂志下载
     * 
     * */
    @RequestMapping(value = "downloadMagzines")
    public ModelAndView download(@RequestParam("magId") String magId, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;

        File file = magzinesService.download(magId);

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
    

    /**
     * 杂志下载
     * 
     * */
    @RequestMapping(value = "downloadZoomMagzines")
    public ModelAndView downloadZoomMagzines(@RequestParam("magId") String magId, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;

        File file = magzinesService.downloadZoomMagzines(magId);

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
    
    /**
     * 杂志类别列表
     * 
     * */
    @RequestMapping(value="magzinesCategoryList")
    public @ResponseBody PagingDto<MagzinesCategoryDto> magzinesCategoryList(@RequestParam("page") String page,
            @RequestParam("rows") String rows){
    	 int offset = Integer.parseInt(page);
         int limit = Integer.parseInt(rows);
        return magzinesService.magzinesCategoryList(offset,limit);
    }
    
    /**
     * 杂志类别信息获取
     * 
     * */
    @RequestMapping(value="getMagzinesCategoryInfo")
    public @ResponseBody String getmMagzinesCategoryInfo(HttpSession session,
            @RequestParam("categoryId") String categoryId){
    	ResultDto rd = new ResultDto();		
        MagzinesCategoryEntity magzinesCategoryEntity = magzinesService.getmMagzinesCategoryInfo(categoryId);
        if(magzinesCategoryEntity != null){
        	MagzinesCategoryDto magzinesCategoryDto = new MagzinesCategoryDto();
            magzinesCategoryDto.setId(magzinesCategoryEntity.getId());
            magzinesCategoryDto.setCategoryName(magzinesCategoryEntity.getCategoryName());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
            String str = df.format(magzinesCategoryEntity.getCreateDate());
            magzinesCategoryDto.setCreateDate(str);
            rd.setContent(magzinesCategoryDto);
            rd.setStatus("1");
            
        }else{
        	rd.setStatus("0");
        }
        return CommonUtils.convertResult(rd, session);
    }
    
    /**
     * 类别信息添加/修改
     * 
     * */
    @RequestMapping(value="categoryInfoHanlde", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String index(HttpSession session,@ModelAttribute("MagzinesCategoryForm") MagzinesCategoryForm magzinesCategoryForm) {
		ResultDto rd = new ResultDto();		
		
		if(magzinesCategoryForm.getId() != null && !"".equals(magzinesCategoryForm.getId())){
			//更新
			MagzinesCategoryEntity magzinesCategoryEntity = magzinesService.getmMagzinesCategoryInfo(magzinesCategoryForm.getId());
			if(magzinesCategoryEntity != null){
				magzinesCategoryEntity.setCategoryName(magzinesCategoryForm.getCategoryName());							
				if(magzinesService.updateMagzinesCategory(magzinesCategoryEntity)){
					rd.setStatus("1");
					rd.setContent("");
				}else{
					rd.setStatus("0");
					rd.setContent("");
				}				
			}else{
				rd.setStatus("2");
				rd.setContent("");
			}
		}else{
			//添加
			MagzinesCategoryEntity magzinesCategoryEntity = new MagzinesCategoryEntity();
			magzinesCategoryEntity.setCategoryName(magzinesCategoryForm.getCategoryName());
			magzinesCategoryEntity.setCreateDate(new Date());
			magzinesCategoryEntity.setDeleteFlag(0);
			if(magzinesService.addMagzinesCategory(magzinesCategoryEntity)){
				rd.setStatus("1");
				rd.setContent("");
			}else{
				rd.setStatus("0");
				rd.setContent("");
			}
				
		}
		return CommonUtils.convertResult(rd, session);
	}
    
    /**
     * 杂志类别信息删除
     * 
     * */
    @RequestMapping(value="deleteCategoryList")
    public @ResponseBody String deleteCategoryList(HttpSession session,
            @RequestParam("categoryIds") String categoryIds){
    	
    	ResultDto rd = new ResultDto();	
		String[] categoryIdStrings = categoryIds.split("@");
		if(categoryIdStrings.length > 0 && magzinesService.deleteMagCategoryList(categoryIdStrings)){				
			rd.setStatus("1");
			rd.setContent("");
		}else{
			rd.setStatus("0");
			rd.setContent("");
		}
		return CommonUtils.convertResult(rd, session);
    }

    /**
     * 服务端上传杂志产品列表
     * 
     * */
    @RequestMapping(value = "magzinesProductList")
    public @ResponseBody PagingDto<MagzinesDto> magzinesProductList(
            @RequestParam("page") String page,
            @RequestParam("rows") String rows) {

        int offset = Integer.parseInt(page);
        int limit = Integer.parseInt(rows);

        return magzinesService.pagingList(offset, limit,1);
    }
    
    /**
     * 获取全部杂志类别信息
     * 
     * */
    @RequestMapping(value="getAllServerCategoryList")
    public @ResponseBody String getAllServerCategoryList(HttpSession session){
    	ResultDto dto = new ResultDto();
    	List<MagzinesCategoryEntity> result = magzinesService.getAllCategoryList();
    	dto.setContent(result);
    	dto.setStatus("1");
		return CommonUtils.convertResult(dto, session);
    }
    
    /**
     * 获取打印请求列表信息
     * 
     * */
    @RequestMapping(value="printList")
    public @ResponseBody PagingDto<PrintDto> printList(HttpSession session,@RequestParam("page") String page,
            @RequestParam("rows") String rows) {

     int offset = Integer.parseInt(page);
     int limit = Integer.parseInt(rows);

     return magzinesService.printList(offset, limit,1);
    }
    
    /**
     * 上传杂志zip包
     * 
     * */
    @RequestMapping(value = "uploadProductMagzines")
    public @ResponseBody String uploadProductMagzines(
    		 @RequestParam("file") CommonsMultipartFile file,
            @RequestParam("categoryId") String categoryId,
            @RequestParam("fileComment") String fileComment,
            @RequestParam("productAmount") String productAmount,
            HttpSession session) {
    	ResultDto resultDto = new ResultDto();
    	int flag = magzinesService.uploadProductMagzines(file,categoryId,fileComment,productAmount);
		if(flag == 1){
			 resultDto.setStatus("1");
		}else{
			 resultDto.setStatus("0");
		}
        return CommonUtils.convertResult(resultDto, session);
    }
    
    /**
     * 杂志免费时间信息获取
     * 
     * */
    @RequestMapping(value = "getMagzinesProductInfo")
    public @ResponseBody String getMagzinesProductInfo(
            @RequestParam("id") String id,
            HttpSession session) {
    	ResultDto resultDto = new ResultDto();
    	MagzinesEntity magzinesEntity = magzinesService.findById(id);
    	
    	MagzinesDto magzinesDto = new MagzinesDto();
		if(magzinesEntity != null){
			magzinesDto.setId(magzinesEntity.getId());
			
			if(magzinesEntity.getStartTime() == null || magzinesEntity.getEndTime() == null){
				magzinesDto.setStartTime("");
				magzinesDto.setEndTime("");
			}else{
				magzinesDto.setStartTime(CommonUtils.formatDate(magzinesEntity.getStartTime(), "yyyy-MM-dd"));
				magzinesDto.setEndTime(CommonUtils.formatDate(magzinesEntity.getEndTime(), "yyyy-MM-dd"));
			}
			
			
			resultDto.setStatus("1");
			resultDto.setContent(magzinesDto);
		}else{
			 resultDto.setStatus("0");
		}
        return CommonUtils.convertResult(resultDto, session);
    }
    
    /**
     * 杂志免费时间取消
     * cancelFreeMagTime
     * */
    @RequestMapping(value = "cancelFreeMagTime")
    public @ResponseBody String cancelFreeMagTime(
            @RequestParam("id") String id,
            HttpSession session) {
    	ResultDto resultDto = new ResultDto();
    	MagzinesEntity magzinesEntity = magzinesService.findById(id);
    	if(magzinesEntity != null){
    		magzinesEntity.setStartTime(null);
    		magzinesEntity.setEndTime(null);
    		magzinesService.updateMagzines(magzinesEntity);
        	resultDto.setStatus("1");
    	}else{
    		resultDto.setStatus("0");
    	}
    	
    	return CommonUtils.convertResult(resultDto, session);
    }

    /**
     * 杂志产品信息免费时间修改
     * 
     * */
    @RequestMapping(value="changeProductMagzines")
    public @ResponseBody String changeProductMagzines(HttpSession session
    		,@ModelAttribute("MagzinesForm") MagzinesForm magzinesForm){
    	ResultDto rd = new ResultDto();	
    	MagzinesEntity magzinesEntity = magzinesService.findById(magzinesForm.getId());
    	if(magzinesEntity != null){
    		magzinesEntity.setStartTime(CommonUtils.formatDate(magzinesForm.getStartDate(), "yyyy-MM-dd"));
    		magzinesEntity.setEndTime(CommonUtils.formatDate(magzinesForm.getEndDate(), "yyyy-MM-dd"));
    		magzinesService.updateMagzines(magzinesEntity);
    		rd.setStatus("1");
    	}else{
    		rd.setStatus("2");
    	}
    	
    	return CommonUtils.convertResult(rd, session);
    }
    
    /**
     * 杂志产品信息删除
     * 
     * */
    @RequestMapping(value="deleteProductList")
    public @ResponseBody String deleteProductList(HttpSession session,
            @RequestParam("productIds") String productIds){
    	
    	ResultDto rd = new ResultDto();	
		String[] productIdsStrings = productIds.split("@");
		if(productIdsStrings.length > 0 && magzinesService.deleteMagProductList(productIdsStrings)){				
			rd.setStatus("1");
			rd.setContent("");
		}else{
			rd.setStatus("0");
			rd.setContent("");
		}
		return CommonUtils.convertResult(rd, session);
    }
    
    /**
     * 
     * 优惠信息网页
     * */
    @RequestMapping("/magzinesYhInfo.html")
	public String index() {
		return "magzinesYhInfo";
	}
    
    /**
     * 打印金获取
     * */
    @RequestMapping(value="getPrintAmount")
    public @ResponseBody String getPrintAmount(HttpSession session){
    	ResultDto rd = new ResultDto();	
    	String amount = magzinesService.getPrintAmount();
    	rd.setStatus("1");
		rd.setContent(amount);
		return CommonUtils.convertResult(rd, session);
	}
    
    /**
     * 打印金设定
     * */
    @RequestMapping(value="setPrintAmount")
    public @ResponseBody String setPrintAmount(HttpSession session
    		,@ModelAttribute("printAmountFrom") PrintAmountFrom printAmountFrom){
    	ResultDto rd = new ResultDto();	
    	magzinesService.setPrintAmount(printAmountFrom);
    	rd.setStatus("1");
		rd.setContent("");
		return CommonUtils.convertResult(rd, session);
	}
    
}