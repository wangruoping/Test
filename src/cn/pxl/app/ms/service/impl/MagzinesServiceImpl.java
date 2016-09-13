package cn.pxl.app.ms.service.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.pxl.app.ms.dao.AgreeDao;
import cn.pxl.app.ms.dao.CommentsDao;
import cn.pxl.app.ms.dao.ConsumeDao;
import cn.pxl.app.ms.dao.DownloadDao;
import cn.pxl.app.ms.dao.MagzinesCategoryDao;
import cn.pxl.app.ms.dao.MagzinesDao;
import cn.pxl.app.ms.dao.PrintAmountDao;
import cn.pxl.app.ms.dao.PrintDao;
import cn.pxl.app.ms.dao.UserDao;
import cn.pxl.app.ms.dto.CommentsDto;
import cn.pxl.app.ms.dto.MagzinesCategoryDto;
import cn.pxl.app.ms.dto.MagzinesDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.PrintDto;
import cn.pxl.app.ms.entity.AgreeEntity;
import cn.pxl.app.ms.entity.CommentsEntity;
import cn.pxl.app.ms.entity.ConsumeEntity;
import cn.pxl.app.ms.entity.DownloadEntity;
import cn.pxl.app.ms.entity.MagzinesCategoryEntity;
import cn.pxl.app.ms.entity.MagzinesEntity;
import cn.pxl.app.ms.entity.PrintAmountEntity;
import cn.pxl.app.ms.entity.PrintEntity;
import cn.pxl.app.ms.entity.CompanyUserEntity;
import cn.pxl.app.ms.form.PrintAmountFrom;
import cn.pxl.app.ms.service.MagzinesService;
import cn.pxl.app.ms.util.CommonUtils;
import cn.pxl.app.ms.util.ZipCompresser;
import cn.pxl.app.ms.util.ZipUtils;

@Service("magzinesService")
public class MagzinesServiceImpl implements MagzinesService {

    private static Logger log = Logger.getLogger(MagzinesServiceImpl.class);

    @Resource(name="magzinesCategoryDao")
    private MagzinesCategoryDao magzinesCategoryDao;
    
    @Resource(name="magzinesDao")
    private MagzinesDao magzinesDao;
    
    @Resource(name="printAmountDao")
    private PrintAmountDao printAmountDao;

    @Resource(name="commentsDao")
    private CommentsDao commentsDao;

    @Resource(name="agreeDao")
    private AgreeDao agreeDao;

    @Resource(name="userDao")
    private UserDao userDao;
    
    @Resource(name="consumeDao")
    private ConsumeDao consumeDao;
    
    @Resource(name="downloadDao")
    private DownloadDao downloadDao;
    
    @Resource(name="printDao")
    private PrintDao printDao;


    @Override
    public void save(MagzinesEntity magzinesEntity) {
        magzinesDao.save(magzinesEntity);
    }

    public MagzinesEntity findById(String magId){
        MagzinesEntity magzinesEntity = (MagzinesEntity) magzinesDao.findById(magId);
        return magzinesEntity;
    }

    @Override
    public PagingDto<MagzinesDto> pagingList(int offset, int limit ,int setType) {

        PagingDto<MagzinesDto> result = new PagingDto<MagzinesDto>();

        List<MagzinesDto> resultList = new ArrayList<MagzinesDto>();

        result.setTotal(magzinesDao.countForPagingList(setType));

        if (result.getTotal() > 0) {
            List<MagzinesEntity> list = magzinesDao.pagingList(offset, limit,setType);
            for (MagzinesEntity me : list) {
                MagzinesDto dto = new MagzinesDto();
                if(me.getMagzinesCategoryEntity() != null){
                	 dto.setCategoryName(me.getMagzinesCategoryEntity().getCategoryName());
                }
               
                dto.setFileComment(me.getFileComment());
                dto.setFileName(me.getFileName());
                dto.setFileSize(me.getFileSize());
                dto.setId(me.getId());
                dto.setName(me.getName());
                dto.setTplName(me.getTplName());
                if(me.getFileAmount() == null){
                	dto.setFileAmount("0");
                }else{
                	dto.setFileAmount(me.getFileAmount().toString());
                }
                
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
                SimpleDateFormat dfFree = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
                String str = df.format(me.getUploadTime());
                dto.setUploadTime(str);
                if(me.getStartTime() != null && me.getEndTime() != null){
                	dto.setStartTime(dfFree.format(me.getStartTime()));
                	dto.setEndTime(dfFree.format(me.getEndTime()));
                }
                if (me.getUserEntity() != null) {
                    dto.setUserId(me.getUserEntity().getId());
                    dto.setUserName(me.getUserEntity().getUsername());
                }
                
                dto.setCfFlag(me.getCfFlag());
                dto.setCfStatus(me.getCfStatus());
                resultList.add(dto);
            }
        }
        result.setRows(resultList);
        return result;
    }

    @Override
    public String uploadMagzine(MultipartFile file,MultipartFile prev1,MultipartFile prev2,
            MultipartFile prev3, String tplName,
            String fileComment,String userid,String address,
            String phone,
            String youbian,
            String payAmount,
            String magAmount,
            String flag,
            String magCateId) {
    	
        String path = CommonUtils.getConfig("upload.file.path");
        String fileId = UUID.randomUUID().toString();
        File savePath = new File(path + fileId + ".zip");
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), savePath);
        } catch (IOException e) {
        	e.printStackTrace();
            return "";
        }

        MagzinesEntity magzinesEntity = new MagzinesEntity();
        magzinesEntity.setUploadTime(new Date());
        magzinesEntity.setFileName(fileId);
        magzinesEntity.setFileSize(file.getSize());
        magzinesEntity.setFileComment(fileComment);
        magzinesEntity.setTplName(tplName);
        magzinesEntity.setCfFlag(0);
        magzinesEntity.setCfStatus(0);
        if("2".equals(flag) || "3".equals(flag)){
        	//商店杂志
            magzinesEntity.setPublicTypeSet(1);
            magzinesEntity.setFileAmount(new BigDecimal(payAmount));
            MagzinesCategoryEntity magzinesCategoryEntity = (MagzinesCategoryEntity) magzinesCategoryDao.findById(magCateId);
            magzinesEntity.setMagzinesCategoryEntity(magzinesCategoryEntity);
        }else {
        	//个人杂志
        	magzinesEntity.setPublicTypeSet(0);
        	magzinesEntity.setFileAmount(new BigDecimal("0"));
		}
        
        magzinesEntity.setDeleteFlag(0);
        CompanyUserEntity userEntity = (CompanyUserEntity) userDao.findById(userid);
    	if(userEntity != null){
    		 magzinesEntity.setUserEntity(userEntity);
    	}else{
    		return "";
    	}
       
        this.save(magzinesEntity);
        

        try {
            String dirPath = path + magzinesEntity.getId() + "_preview";
            File dirPathFile = new File(dirPath);
            dirPathFile.mkdirs();
            FileUtils.copyInputStreamToFile(prev1.getInputStream(), new File(dirPath + File.separator + "1.png"));
            FileUtils.copyInputStreamToFile(prev2.getInputStream(), new File(dirPath + File.separator + "2.png"));
            FileUtils.copyInputStreamToFile(prev3.getInputStream(), new File(dirPath + File.separator + "3.png"));
        } catch (IOException e) {
            return "";
        }
        
        if("2".equals(flag) || "3".equals(flag)){
        	//保存用户上传到商店杂志的自消费记录
			 ConsumeEntity consumeEntity = new ConsumeEntity();
			 consumeEntity.setConsumeAmount(new BigDecimal("0"));
			 consumeEntity.setConsumeFLag("1");
			 consumeEntity.setConsumeTime(new Date());
			 consumeEntity.setMagzinesEntity(magzinesEntity);
			 consumeEntity.setUserEntity(userEntity);
			 consumeEntity.setConsumeStatus("2");
			 consumeDao.save(consumeEntity);
		 }
        
        //插入打印记录
        if("1".equals(flag) || "3".equals(flag)){
        	//插入打印信息
        	PrintEntity printEntity = new PrintEntity();
        	printEntity.setAddress(address);
        	printEntity.setMagId(magzinesEntity.getId());
        	printEntity.setFileName(fileId);
        	printEntity.setPhone(phone);
        	printEntity.setPrintTime(new Date());
        	printEntity.setRechargeAmount(new BigDecimal(magAmount));
        	printEntity.setStatus("0");
        	printEntity.setDeleteFlag(0);
        	printEntity.setYoubian(youbian);
        	printEntity.setUserEntity(userEntity);
        	printDao.save(printEntity);
        	
        	//更新用户余额
        	userEntity.setAmount(userEntity.getAmount().subtract(new BigDecimal(magAmount)));
        	userDao.saveOrUpdate(userEntity);
        	
        	//
        	//保存用户上传打印的消费记录
			ConsumeEntity consumeEntity = new ConsumeEntity();
			consumeEntity.setConsumeAmount(new BigDecimal(magAmount));
			consumeEntity.setConsumeFLag("1");
			consumeEntity.setConsumeTime(new Date());
			consumeEntity.setMagzinesEntity(magzinesEntity);
			consumeEntity.setUserEntity(userEntity);
			consumeEntity.setConsumeStatus("2");
			consumeDao.save(consumeEntity);
        	
        }
        return magzinesEntity.getId();
    }

    public File download(String fileId) {
    	String uploadPath = CommonUtils.getConfig("upload.file.path");
        if (new File(uploadPath + fileId + "_split.zip").exists()) {
            return new File(uploadPath + fileId + "_split.zip");
        }else{
            return null;
        }
    }
    

    public File downloadZoomMagzines(String fileId) {
    	String uploadPath = CommonUtils.getConfig("upload.file.path");
        if (new File(uploadPath + fileId + "_JPEG.zip").exists()) {
            return new File(uploadPath + fileId + "_JPEG.zip");
        }else{
            return null;
        }
    }

    public File downloadOrigin(String fileId) {
        String uploadPath = CommonUtils.getConfig("upload.file.path");
        if (new File(uploadPath + fileId + ".zip").exists()) {
            return new File(uploadPath + fileId + ".zip");
        }else{
            return null;
        }
    }

    public static void cutImage(String src,String dest,int x,int y,int w,int h) throws IOException {
    	File destFile = null;
        BufferedImage srcImg = null;
        BufferedImage bi = null;
        InputStream in = null;
        ImageInputStream iis = null;
        try{
//            Iterator<?> iterator = ImageIO.getImageReadersByFormatName("png");
//            ImageReader reader = (ImageReader) iterator.next();
//
//            in = new FileInputStream(src);
//            iis = ImageIO.createImageInputStream(in);
//            reader.setInput(iis, true);
//            ImageReadParam param = reader.getDefaultReadParam();
//            Rectangle rect = new Rectangle(x, y, w, h);
//            param.setSourceRegion(rect);
//
//            bi = reader.read(0, param);
        	
        	srcImg = ImageIO.read(new File(src));
        	bi = new BufferedImage(w * 2, h * 2, BufferedImage.TYPE_4BYTE_ABGR);
        	Graphics g = bi.getGraphics();
        	g.drawImage(srcImg, 0, 0, w * 2, h * 2, x, y, x + w, y + h, null);

        	
            destFile = new File(dest);
            ImageIO.write(bi, "png", destFile);
//            rect = null;
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {

                }
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException ex) {

                }
            }
            destFile = null;
            bi = null;
            srcImg = null;
            in = null;
            iis = null;
        }
    }
    private boolean splitMagiznes(String fileId) {
        //unzip upload images
        String uploadPath = CommonUtils.getConfig("upload.file.path");
        File savePath = new File(uploadPath + fileId + ".zip");
        File unzipPath = new File(uploadPath + fileId + "_split");
        
        File upzipZoomPath = new File(uploadPath + fileId + "_JPEG");
        
        try {
            ZipUtils.unzipArchive(savePath, unzipPath);
            ZipUtils.unzipArchive(savePath, upzipZoomPath);
        } catch (ZipException e1) {
            return false;
        }

        try {
            //确定文件
            Vector<File> vector = new Vector<File>();
            for (File file : unzipPath.listFiles()) {
                vector.add(file);
            }
            //操作文件
            for (File file : vector) {
            	//第一张图不切割
            	if ("1.png".equals(file.getName())) {
            		
                    BufferedImage bi = ImageIO.read(file);
                    
                    BufferedImage newImage = new BufferedImage(bi.getWidth() * 2, bi.getHeight() * 2, bi.getType());
                    Graphics g = newImage.getGraphics();
                    g.drawImage(bi, 0 ,0 , bi.getWidth() * 2, bi.getHeight() * 2, null);
                    g.dispose();
                    bi = null;
                    
                    ImageIO.write(newImage, "png", file);
            		newImage = null;
            		continue;
            	}
            	
                BufferedImage bi = ImageIO.read(file);

                String baseName = file.getAbsolutePath();
                baseName = baseName.substring(0, baseName.lastIndexOf("."));

                cutImage(file.getAbsolutePath(), baseName + "-A.png", 0, 0, bi.getWidth() / 2 + 17, bi.getHeight());
                cutImage(file.getAbsolutePath(), baseName + "-B.png", bi.getWidth() / 2 - 17, 0, bi.getWidth() / 2 + 17, bi.getHeight());
                bi = null;
                file.delete();
                file = null;
            }
            
            //确定文件
            Vector<File> vector1 = new Vector<File>();
            for (File file : upzipZoomPath.listFiles()) {
            	vector1.add(file);
            }
            //操作文件
            for (File file : vector1) {
            	//第一张图不切割
            	if ("1.png".equals(file.getName())) {
            		
                    BufferedImage bi = ImageIO.read(file);
                    BufferedImage newImage = new BufferedImage(3402, 3038, BufferedImage.TYPE_INT_RGB);
                    Graphics g = newImage.getGraphics();
                    g.drawImage(bi, 0 ,0 , newImage.getWidth(), newImage.getHeight(), Color.WHITE, null);
                    g.dispose();
                    bi = null;
                    
                    String path = file.getAbsolutePath();
                    
            		FileOutputStream fos = new FileOutputStream(new File(path.substring(0, path.lastIndexOf(".")) + ".jpg"));  
            		com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);  
            		com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(newImage);  
                    jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);  
                    jpegEncodeParam.setXDensity(288);  
                    jpegEncodeParam.setYDensity(288);  
                    jpegEncoder.encode(newImage, jpegEncodeParam);  
                    
                    
            		newImage = null;
            		file.delete();
            		continue;
            	}
            	
                BufferedImage bi = ImageIO.read(file);
                BufferedImage newImage = new BufferedImage(3175, 2820, BufferedImage.TYPE_INT_RGB);
                Graphics g = newImage.getGraphics();
                g.drawImage(bi, 0 ,0 , newImage.getWidth(), newImage.getHeight(), Color.WHITE, null);
                g.dispose();
                bi = null;

                String path = file.getAbsolutePath();
                
        		FileOutputStream fos = new FileOutputStream(new File(path.substring(0, path.lastIndexOf(".")) + ".jpg"));  
        		com.sun.image.codec.jpeg.JPEGImageEncoder jpegEncoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(fos);  
        		com.sun.image.codec.jpeg.JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(newImage);  
                jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);  
                jpegEncodeParam.setXDensity(288);  
                jpegEncodeParam.setYDensity(288);  
                jpegEncoder.encode(newImage, jpegEncodeParam);  
                

                newImage = null;
                file.delete();
                file = null;
            }
        } catch (IOException e) {
            log.error(e);
            return false;
        }

        //zip dir
        ZipCompresser zz = new ZipCompresser(unzipPath);
        zz.archive();
        
        zz = new ZipCompresser(upzipZoomPath);
        zz.archive();
        //delete tmps
        try {
            FileUtils.deleteDirectory(unzipPath);
            FileUtils.deleteDirectory(upzipZoomPath);
        } catch (IOException e) {
            log.error(e);
        }
        return true;
    }

    public List<CommentsDto> getCommentsList(String magId,String commentDate) {

        List<CommentsDto> reCommentsDtos = new ArrayList<CommentsDto>();

        List<CommentsEntity> list = commentsDao.getCommentsList(magId,commentDate);
        for (int i = 0; i < list.size(); i++) {
            CommentsEntity commentsEntity = list.get(i);

            CommentsDto commentsDto = new CommentsDto();
            commentsDto.setCommentContent(commentsEntity.getCommentContent());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
            String str = df.format(commentsEntity.getCommentTime());
            commentsDto.setCommentTime(str);
            commentsDto.setCommentUserId(commentsEntity.getUserEntity().getId());
            commentsDto.setCommentUserImgPath(commentsEntity.getUserEntity().getPicPath());
            commentsDto.setCommentUserName(commentsEntity.getUserEntity().getUsername());
            reCommentsDtos.add(commentsDto);
        }

        return reCommentsDtos;
    }

    @Override
    public String insertComment(String magId, String commentContent,
            String userid) {
        MagzinesEntity magzinesEntity = (MagzinesEntity) magzinesDao.findById(magId);
        CompanyUserEntity userEntity = (CompanyUserEntity) userDao.findById(userid);
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setCommentContent(commentContent);
        commentsEntity.setMagzinesEntity(magzinesEntity);
        commentsEntity.setUserEntity(userEntity);
        Timestamp commentTime = new Timestamp(System.currentTimeMillis());
        commentsEntity.setCommentTime(commentTime);
        commentsDao.save(commentsEntity);
        return null;
    }

    /**
     * 添加杂志点赞数据
     *
     * liux add
     * */
    @Override
    public String insertAgree(String magId,String userId) {

        AgreeEntity agreeEntity = agreeDao.findAgreeEntity(magId, userId);
        if(agreeEntity != null){
            agreeDao.delete(agreeEntity);
            return "2";
        }else{
            MagzinesEntity magzinesEntity = (MagzinesEntity) magzinesDao.findById(magId);
            CompanyUserEntity userEntity = (CompanyUserEntity) userDao.findById(userId);
            AgreeEntity newAgreeEntity = new AgreeEntity();

            newAgreeEntity.setMagzinesEntity(magzinesEntity);
            newAgreeEntity.setUserEntity(userEntity);
            agreeDao.save(newAgreeEntity);
            return "1";
        }

    }

    public Map<String, String> getAgreesCount(String magId, String userid){
        Map<String, String> map = new HashMap<String, String>();

        List<AgreeEntity> result = agreeDao.findAgreeEntity(magId);
        map.put("agreeCount", String.valueOf(result.size()));
        boolean flag = false;
        for(int i = 0 ;i < result.size();i++){
            AgreeEntity agreeEntity = result.get(i);
            if(userid.equals(agreeEntity.getUserEntity().getId())){
                flag = true;
            }
        }
        if(flag){
            map.put("userAgreeFlag", "1");
        }else{
            map.put("userAgreeFlag", "0");
        }
        return map;
    }

    @Override
    public Map<String, List<MagzinesEntity>> getFriendMagList(String userid) {
        Map<String,List<MagzinesEntity>> magMap = new HashMap<String,List<MagzinesEntity>>();
        //List<MagzinesEntity> list = magzinesDao.getFriendMagList(userid);
        List<MagzinesEntity> list = new ArrayList<MagzinesEntity>();
        magMap.put("magList", list);
        return magMap;
    }

    @Override
    public String insertMagZf(String magId, String zfCommentContent,
            String userid) {
    	
    	MagzinesEntity magzinesEntity = new MagzinesEntity();
    	
    	//获取被转发的杂志
    	MagzinesEntity getMagzinesEntity = (MagzinesEntity)magzinesDao.findById(magId);
    	
    	//判断被转发的杂志是否是原始杂志
    	if(getMagzinesEntity != null){
    		if(getMagzinesEntity.getFirstMagId() != null && !"".equals(getMagzinesEntity.getFirstMagId())){
    			magzinesEntity.setFirstMagId(getMagzinesEntity.getFirstMagId());
    		}else{
    			magzinesEntity.setFirstMagId(getMagzinesEntity.getId());
    		}
    		magzinesEntity.setZfMagId(magId);
            magzinesEntity.setFileComment(zfCommentContent);
            CompanyUserEntity userEntity = (CompanyUserEntity) userDao.findById(userid);
            magzinesEntity.setUserEntity(userEntity);
            magzinesEntity.setUploadTime(new Timestamp(System.currentTimeMillis()));
            magzinesEntity.setPublicTypeSet(0);
            magzinesDao.save(magzinesEntity);
            return "1";
    	}else{
    		return "0";
    	}
        
        
        
    }

    @Override
    public Map<String, String> getMagZfListCount(String magId) {
        Map<String, String> map = new HashMap<String, String>();
        Integer zfCount = magzinesDao.getMagZfListCount(magId);
        map.put("zfCount", zfCount.toString());
        return map;
    }

    @Override
    public List<MagzinesDto> getUserMagList(String userid,Integer pageIndex) {
        List<MagzinesEntity> list = magzinesDao.getUserMagList(userid,pageIndex);
        List<MagzinesDto> listDtos = new ArrayList<MagzinesDto>();
        for(int i =0;i< list.size();i++){

            MagzinesDto magzinesDto = new MagzinesDto();
            MagzinesEntity magzinesEntity = list.get(i);
            //判断是否是转发的杂志
            if(magzinesEntity.getFirstMagId() != null && !"".equals(magzinesDto.getFirstMagId())){
            	magzinesDto.setFirstMagId(magzinesEntity.getFirstMagId());
            }else{
            	magzinesDto.setFirstMagId("");
            }
            String magId = magzinesEntity.getId();
            magzinesDto.setId(magId);
            //评论数
            List<CommentsEntity> comList = commentsDao.getCommentsList(magId, "");
            magzinesDto.setCommentCount(String.valueOf(comList.size()));
            //点赞数
            List<AgreeEntity> agreeList = agreeDao.findAgreeEntity(magId);
            magzinesDto.setAgreeCount(String.valueOf(agreeList.size()));
            //转发数
            Integer zfCount = magzinesDao.getMagZfListCount(magId);
            magzinesDto.setZfCount(zfCount.toString());
            magzinesDto.setTplName(magzinesEntity.getTplName());
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uploadTime = df.format(magzinesEntity.getUploadTime());
            magzinesDto.setUploadTime(uploadTime);
            listDtos.add(magzinesDto);
        }
        return listDtos;
    }

	@Override
	public File getMagImageList(String magid,
			String imageName) {
		
		MagzinesEntity magzinesEntity = (MagzinesEntity) magzinesDao.findById(magid);
		
		
		if(magzinesEntity != null){
			
			if(magzinesEntity.getFirstMagId() != null && !"".equals(magzinesEntity.getFirstMagId())){
				magid = magzinesEntity.getFirstMagId();
			}
			
			String uploadPath = CommonUtils.getConfig("upload.file.path");
	        File previewPath = new File(uploadPath + magid + "_preview");
	        if(previewPath.isDirectory() && previewPath.exists()){

        		for(File file:previewPath.listFiles()){
	        		if(file.getName().equals(imageName+".png")){
	        			return file;
	        		}
	        	}  	
	        }else{
	        	return null;
	        }
		}
		return null;
	}
	
	@Override
	public Map<String, List<MagzinesDto>> getShopMagDetailList(String userid,
			String searchText, int parseInt,int pageSize) {
		Map<String, List<MagzinesDto>> map = new HashMap<String, List<MagzinesDto>>();
		List<MagzinesEntity> list = magzinesDao.getShopMagDetailList(userid,searchText,parseInt,pageSize);
        List<MagzinesDto> listDtos = new ArrayList<MagzinesDto>();
        for(int i =0;i< list.size();i++){

            MagzinesDto magzinesDto = new MagzinesDto();
            MagzinesEntity magzinesEntity = list.get(i);
            String magId = magzinesEntity.getId();
            //杂志Id
            magzinesDto.setId(magId);
            //评论数
            List<CommentsEntity> comList = commentsDao.getCommentsList(magId, "");
            magzinesDto.setCommentCount(String.valueOf(comList.size()));
            //点赞数
            List<AgreeEntity> agreeList = agreeDao.findAgreeEntity(magId);
            boolean flag = false;
            for(int w = 0; w<agreeList.size();w++){
            	AgreeEntity agreeEntity = agreeList.get(w);
            	if(agreeEntity.getUserEntity()!=null && agreeEntity.getUserEntity().getId().equals(userid)){
            		flag = true;
            	}
            }
            if(flag){
            	magzinesDto.setZanFlag("1");
            }else{
            	magzinesDto.setZanFlag("0");
            }
            magzinesDto.setAgreeCount(String.valueOf(agreeList.size()));
            //转发数
            Integer zfCount = magzinesDao.getMagZfListCount(magId);
            magzinesDto.setZfCount(zfCount.toString());

            //用户名
            magzinesDto.setUserName(magzinesEntity.getUserEntity().getUsername());
            //用户头像
            magzinesDto.setUserImgPath(magzinesEntity.getUserEntity().getPicPath());
            magzinesDto.setSex(magzinesEntity.getUserEntity().getSex());
            magzinesDto.setUserId(magzinesEntity.getUserEntity().getId());
            listDtos.add(magzinesDto);
            //杂志心情
            magzinesDto.setFileComment(magzinesEntity.getFileComment());
            //发布时间
            SimpleDateFormat df = new SimpleDateFormat("MM-dd");
            String uploadTime = df.format(magzinesEntity.getUploadTime());
            //商店杂志管理员标示
            if("9".equals(magzinesEntity.getUserEntity().getLoginType())){
            	magzinesDto.setShopFlag("1");
        	}else{
        		magzinesDto.setShopFlag("0");
        	}
            magzinesDto.setUploadTime(uploadTime);
            
        }
        map.put("listDtos", listDtos);
        return map;
	}

	@Override
	public Map<String, List<MagzinesDto>> getFriendMagDetailList(String userid,Integer pageIndex,Integer pageSize) {
		
		Map<String, List<MagzinesDto>> map = new HashMap<String, List<MagzinesDto>>();
		//List<MagzinesEntity> list = magzinesDao.getFriendMagDetailList(userid,pageIndex,pageSize);
		List<MagzinesEntity> list = new ArrayList<MagzinesEntity>();
		List<MagzinesDto> listDtos = new ArrayList<MagzinesDto>();
        
        
        
        for(int i =0;i< list.size();i++){

            MagzinesDto magzinesDto = new MagzinesDto();
            MagzinesEntity magzinesEntity = list.get(i);
            String magId = magzinesEntity.getId();
            //杂志Id
            magzinesDto.setId(magId);
            //评论数
            List<CommentsEntity> comList = commentsDao.getCommentsList(magId, "");
            magzinesDto.setCommentCount(String.valueOf(comList.size()));
            //点赞数
            List<AgreeEntity> agreeList = agreeDao.findAgreeEntity(magId);
            boolean flag = false;
            for(int w = 0; w<agreeList.size();w++){
            	AgreeEntity agreeEntity = agreeList.get(w);
            	if(agreeEntity.getUserEntity()!=null && agreeEntity.getUserEntity().getId().equals(userid)){
            		flag = true;
            	}
            }
            if(flag){
            	magzinesDto.setZanFlag("1");
            }else{
            	magzinesDto.setZanFlag("0");
            }
            magzinesDto.setAgreeCount(String.valueOf(agreeList.size()));
            //转发数
            Integer zfCount = magzinesDao.getMagZfListCount(magId);
            magzinesDto.setZfCount(zfCount.toString());

            //用户名
            magzinesDto.setUserName(magzinesEntity.getUserEntity().getUsername());
            //用户头像
            magzinesDto.setUserImgPath(magzinesEntity.getUserEntity().getPicPath());
            magzinesDto.setSex(magzinesEntity.getUserEntity().getSex());
            magzinesDto.setUserId(magzinesEntity.getUserEntity().getId());
            magzinesDto.setShopFlag("0");
 
            listDtos.add(magzinesDto);
            //杂志心情
            magzinesDto.setFileComment(magzinesEntity.getFileComment());
            //发布时间
            SimpleDateFormat df = new SimpleDateFormat("MM-dd");
            String uploadTime = df.format(magzinesEntity.getUploadTime());
            magzinesDto.setUploadTime(uploadTime);
            
            //是否是转发刊物信息
            if(magzinesEntity.getZfMagId() != null && !"".equals(magzinesEntity.getZfMagId())){
            	MagzinesEntity zfMagzinesEntity = (MagzinesEntity) magzinesDao.findById(magzinesEntity.getZfMagId());
            	MagzinesDto zfMagzinesDto = new MagzinesDto();
            	zfMagzinesDto.setFileComment(zfMagzinesEntity.getFileComment());
            	zfMagzinesDto.setUserId(zfMagzinesEntity.getUserEntity().getId());
            	zfMagzinesDto.setUserName(zfMagzinesEntity.getUserEntity().getUsername());
            	zfMagzinesDto.setUserImgPath(zfMagzinesEntity.getUserEntity().getPicPath());
            	zfMagzinesDto.setId(magzinesEntity.getZfMagId());
            	zfMagzinesDto.setFirstMagId(magzinesEntity.getFirstMagId());
            	zfMagzinesDto.setSex(zfMagzinesEntity.getUserEntity().getSex());
            	if("9".equals(magzinesEntity.getUserEntity().getLoginType())){
            		zfMagzinesDto.setShopFlag(zfMagzinesEntity.getPublicTypeSet().toString());
            	}else{
            		zfMagzinesDto.setShopFlag("0");
            	}
            	
            	magzinesDto.setMagzinesDto(zfMagzinesDto);
            	magzinesDto.setZfMagId(magzinesEntity.getZfMagId());
            }
        }
        map.put("listDtos", listDtos);
        return map;
	}

	@Override
	public PagingDto<MagzinesCategoryDto> magzinesCategoryList(int offset,
			int limit) {
		PagingDto<MagzinesCategoryDto> result = new PagingDto<MagzinesCategoryDto>();

        List<MagzinesCategoryDto> resultList = new ArrayList<MagzinesCategoryDto>();

        result.setTotal(magzinesCategoryDao.countForPagingList());

        if (result.getTotal() > 0) {
            List<MagzinesCategoryEntity> list = magzinesCategoryDao.pagingList(offset, limit);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (MagzinesCategoryEntity me : list) {
            	MagzinesCategoryDto dto = new MagzinesCategoryDto();
            	dto.setId(me.getId());
            	dto.setCategoryName(me.getCategoryName());
                //定义格式，不显示毫秒
                String str = df.format(me.getCreateDate());
                dto.setCreateDate(str);               
                resultList.add(dto);
            }
            result.setRows(resultList);
        }else{
        	result.setRows(resultList);
        }

        return result;
	}

	@Override
	public MagzinesCategoryEntity getmMagzinesCategoryInfo(
			String categoryId) {
		
		return (MagzinesCategoryEntity)magzinesCategoryDao.findById(categoryId);
	}

	@Override
	public boolean updateMagzinesCategory(
			MagzinesCategoryEntity magzinesCategoryEntity) {
		magzinesCategoryDao.saveOrUpdate(magzinesCategoryEntity);
		return true;
	}

	@Override
	public boolean addMagzinesCategory(
			MagzinesCategoryEntity magzinesCategoryEntity) {
		magzinesCategoryDao.saveOrUpdate(magzinesCategoryEntity);
		return true;
	}

	@Override
	public boolean deleteMagCategoryList(String[] categoryIdStrings) {
		magzinesCategoryDao.deleteMagCategoryList(categoryIdStrings);
		return true;
	}

	@Override
	public Integer magzinesCategoryCount() {
		return Integer.valueOf((int)magzinesCategoryDao.countForPagingList());
	}

	@Override
	public List<MagzinesCategoryEntity> getAllCategoryList() {
		
		return magzinesCategoryDao.findAll();
	}

	@Override
	public int uploadProductMagzines(CommonsMultipartFile file, String categoryId,String fileComment,String productAmount) {
		String path = CommonUtils.getConfig("upload.file.path");
        String fileId = UUID.randomUUID().toString();
        File savePath = new File(path + fileId + ".zip");
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), savePath);
        } catch (IOException e) {
        	e.printStackTrace();
            return 0;
        }
        
        MagzinesEntity magzinesEntity = new MagzinesEntity();
        MagzinesCategoryEntity magzinesCategoryEntity = (MagzinesCategoryEntity) magzinesCategoryDao.findById(categoryId);
        if(magzinesCategoryEntity != null){
            magzinesEntity.setMagzinesCategoryEntity(magzinesCategoryEntity);
        }
        
        magzinesEntity.setUploadTime(new Date());
        magzinesEntity.setFileName(fileId);
        magzinesEntity.setFileSize(file.getSize());
        magzinesEntity.setDeleteFlag(0);
        magzinesEntity.setFileComment(fileComment);
        if(productAmount != null && !"".equals(productAmount)){
        	 magzinesEntity.setFileAmount(new BigDecimal(productAmount));
        }else{
        	magzinesEntity.setFileAmount(new BigDecimal("0"));
        }
       
        //店铺杂志
        magzinesEntity.setPublicTypeSet(1);
        String userId = "FFFF";
        CompanyUserEntity ue = (CompanyUserEntity) userDao.findById(userId);
        magzinesEntity.setUserEntity(ue);
        this.save(magzinesEntity);
        
        try {
            String dirPath = path + magzinesEntity.getId() + "_preview";
            File dirPathFile = new File(dirPath);
            dirPathFile.mkdirs();
            for (int i = 1; i < 4; i++) {
            	int w = i == 1 ? 640 : 320;
            	ZipFile zf =  new ZipFile(savePath);
                ZipEntry ze = zf.getEntry((i + 1) + ".png");
                BufferedImage bi = new BufferedImage(w, w, BufferedImage.TYPE_4BYTE_ABGR);
                BufferedImage orgimg = ImageIO.read(zf.getInputStream(ze));
                Graphics g = bi.createGraphics();
                
                int ww = orgimg.getWidth();
                int wh = orgimg.getHeight();
                
                if (ww > wh) {
                	g.drawImage(orgimg, 0, 0, w, w, (ww - wh) / 2, 0, ww - (ww - wh) / 2, wh, null);	
                } else {
                	g.drawImage(orgimg, 0, 0, w, w, 0, (wh - ww) / 2, ww, wh - (wh - ww) / 2, null);
                }
                ImageIO.write(bi, "png", new File(dirPath + File.separator + i + ".png"));
                bi = null;
                orgimg = null;
                zf.close();
			}
            
            
        } catch (IOException e) {
            return 0;
        }
		return 1;
	}

	@Override
	public boolean deleteMagProductList(String[] productIdsStrings) {
		magzinesDao.deleteMagProductList(productIdsStrings);
		return true;
	}

	@Override
	public List<MagzinesEntity> getMagProductList(String categoryId,
			int top) {
		return magzinesDao.getMagProductList(categoryId,top);

	}

	@Override
	public List<MagzinesDto> getAllMagProductList(int orderType,
			String pageIndex) {
		List<MagzinesDto> result = new ArrayList<MagzinesDto>();
		
		List<MagzinesEntity> magList = magzinesDao.getAllMagProductList(orderType,pageIndex);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat freeDf = new SimpleDateFormat("yyyy-MM-dd");
		for(MagzinesEntity entity : magList){
			MagzinesDto magzinesDto = new MagzinesDto();
			magzinesDto.setId(entity.getId());
			magzinesDto.setUserName(entity.getUserEntity().getUsername());
			magzinesDto.setFileComment(entity.getFileComment());
			magzinesDto.setUploadTime(df.format(entity.getUploadTime()));
			magzinesDto.setStartTime(freeDf.format(entity.getStartTime()));
			magzinesDto.setEndTime(freeDf.format(entity.getEndTime()));
			magzinesDto.setCategoryName(entity.getMagzinesCategoryEntity().getCategoryName());
			result.add(magzinesDto);
		}
		
		return result;
	}

	@Override
	public List<String> getHotMagProductList(int top) {
		return magzinesDao.getHotMagProductList(top);
	}

	@Override
	public void updateMagzines(MagzinesEntity magzinesEntity) {
		magzinesDao.update(magzinesEntity);
	}

	@Override
	public boolean findUserBuyPro(String userid, String magId) {
		
		return consumeDao.findUserBuyPro(userid,magId);
	}

	@Override
	public String insertConsumeInfo(String magId, String userid,String consumeFLag) {

		ConsumeEntity consumeEntity = new ConsumeEntity();
		MagzinesEntity magzinesEntity = (MagzinesEntity) magzinesDao.findById(magId);
		consumeEntity.setMagzinesEntity(magzinesEntity);
		CompanyUserEntity userEntity = (CompanyUserEntity) userDao.findById(userid);
		
		if(magzinesEntity != null && userEntity != null){
			if(magzinesEntity.getFileAmount().compareTo(userEntity.getAmount()) > 0){
				return "0";
			}
		}
		//更新购买用户余额
		userEntity.setAmount(userEntity.getAmount().subtract(magzinesEntity.getFileAmount()));
		userDao.update(userEntity);
		consumeEntity.setConsumeStatus("2");
		consumeEntity.setUserEntity(userEntity);
		consumeEntity.setConsumeTime(new Date());
		consumeEntity.setConsumeAmount(magzinesEntity.getFileAmount());
		consumeEntity.setConsumeFLag(consumeFLag);
		consumeDao.save(consumeEntity);
		
		//更新上传杂志用户余额
		CompanyUserEntity sellUserEntity = magzinesEntity.getUserEntity();
		sellUserEntity.setAmount(sellUserEntity.getAmount().add(magzinesEntity.getFileAmount()));
		userDao.update(sellUserEntity);
		
		//添加上传杂志用户的消费记录
		ConsumeEntity consumeSellEntity = new ConsumeEntity();
		consumeSellEntity.setUserEntity(sellUserEntity);
		consumeSellEntity.setMagzinesEntity(magzinesEntity);
		consumeSellEntity.setConsumeTime(new Date());
		consumeSellEntity.setConsumeAmount(magzinesEntity.getFileAmount());
		consumeSellEntity.setConsumeFLag(consumeFLag);
		consumeSellEntity.setConsumeStatus("1");
		consumeDao.save(consumeEntity);		
		
		//插入用户杂志
		MagzinesEntity newMagzinesEntity = new MagzinesEntity();
		newMagzinesEntity.setDeleteFlag(0);
		newMagzinesEntity.setPublicTypeSet(0);
		newMagzinesEntity.setUploadTime(new Date());
		newMagzinesEntity.setBuyMagId(magId);
		newMagzinesEntity.setFirstMagId(magId);
		newMagzinesEntity.setZfMagId(magId);
		newMagzinesEntity.setFileComment(magzinesEntity.getFileComment());
		newMagzinesEntity.setUserEntity(userEntity);
		magzinesDao.save(newMagzinesEntity);
		
		
		
		return "1";
	}

	@Override
	public void insertDownloadEntity(String magId, String userid) {
		boolean flag = downloadDao.findByMagIdAndUserId(magId,userid);
		if(!flag){
			DownloadEntity downloadEntity = new DownloadEntity();
			MagzinesEntity magzinesEntity = (MagzinesEntity) magzinesDao.findById(magId);
			downloadEntity.setMagzinesEntity(magzinesEntity);
			CompanyUserEntity userEntity = (CompanyUserEntity)userDao.findById(userid);
			downloadEntity.setUserEntity(userEntity);
			downloadDao.save(downloadEntity);
		}
		
		
	}

	@Override
	public List<MagzinesDto> getUserMagAllList(String userid) {
		List<MagzinesEntity> list = magzinesDao.getUserMagAllList(userid);
        List<MagzinesDto> listDtos = new ArrayList<MagzinesDto>();
        for(int i =0;i< list.size();i++){

            MagzinesDto magzinesDto = new MagzinesDto();
            MagzinesEntity magzinesEntity = list.get(i);
            //判断是否是转发的杂志
            if(magzinesEntity.getFirstMagId() != null && !"".equals(magzinesDto.getFirstMagId())){
            	magzinesDto.setFirstMagId(magzinesEntity.getFirstMagId());
            }else{
            	magzinesDto.setFirstMagId("");
            }
            String magId = magzinesEntity.getId();
            magzinesDto.setId(magId);
            //评论数
            List<CommentsEntity> comList = commentsDao.getCommentsList(magId, "");
            magzinesDto.setCommentCount(String.valueOf(comList.size()));
            //点赞数
            List<AgreeEntity> agreeList = agreeDao.findAgreeEntity(magId);
            magzinesDto.setAgreeCount(String.valueOf(agreeList.size()));
            //转发数
            Integer zfCount = magzinesDao.getMagZfListCount(magId);
            magzinesDto.setZfCount(zfCount.toString());
            magzinesDto.setTplName(magzinesEntity.getTplName());
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            magzinesDto.setUploadTime(sdFormat.format(magzinesEntity.getUploadTime()));
            listDtos.add(magzinesDto);
        }
        return listDtos;
	}

	@Override
	public List<MagzinesCategoryDto> getCategorysList() {
		List<MagzinesCategoryEntity> list = magzinesCategoryDao.findAll();
		List<MagzinesCategoryDto> listDaos = new ArrayList<MagzinesCategoryDto>();
		for (int i = 0; i < list.size(); i++) {
			MagzinesCategoryDto magzinesCategoryDto = new MagzinesCategoryDto();
			magzinesCategoryDto.setCategoryName(list.get(i).getCategoryName());
			magzinesCategoryDto.setId(list.get(i).getId());
			listDaos.add(magzinesCategoryDto);
		}
		return listDaos;
	}

	@Override
	public PagingDto<PrintDto> printList(int offset, int limit, int i) {
		
		PagingDto<PrintDto> result = new PagingDto<PrintDto>();

        List<PrintDto> resultList = new ArrayList<PrintDto>();

        result.setTotal(printDao.countForPagingList());

        if (result.getTotal() > 0) {
            List<PrintEntity> list = printDao.pagingList(offset, limit);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (PrintEntity me : list) {
            	PrintDto printDto = new PrintDto();
            	printDto.setId(me.getId());
            	printDto.setCfFlag(me.getCfFlag());
    			printDto.setAddress(me.getAddress());
    			printDto.setPhone(me.getPhone());
    			printDto.setPrintTime(df.format(me.getPrintTime()));
    			printDto.setStatus(me.getStatus());
    			printDto.setUsername(me.getUserEntity().getUsername());
    			printDto.setYoubian(me.getYoubian());
    			printDto.setMagId(me.getMagId());
    			printDto.setFileName(me.getFileName());
    			resultList.add(printDto);
            }
            result.setRows(resultList);
        }else{
        	result.setRows(resultList);
        }

        return result;
	}

	@Override
	public void setPrintAmount(PrintAmountFrom printAmountFrom) {
		
		List<PrintAmountEntity> resultAmountEntities = printAmountDao.findAll();
		if(resultAmountEntities.size() > 0){
			PrintAmountEntity printAmountEntity = resultAmountEntities.get(0);
			printAmountEntity.setAmount(new BigDecimal(printAmountFrom.getAmount().toString()));
			printAmountDao.saveOrUpdate(printAmountEntity);
		}else{
			PrintAmountEntity printAmountEntity = new PrintAmountEntity();
			printAmountEntity.setAmount(new BigDecimal(printAmountFrom.getAmount()));
			printAmountDao.save(printAmountEntity);
		}
		
	}

	@Override
	public String getPrintAmount() {
		List<PrintAmountEntity> resultAmountEntities = printAmountDao.findAll();
		if(resultAmountEntities.size() > 0){
			PrintAmountEntity printAmountEntity = resultAmountEntities.get(0);
			return printAmountEntity.getAmount().toString();
		}else{
			return "0";
		}
	}

	@Override
	public List<MagzinesEntity> getMagDetailProductList(String categoryId,
			String pageIndex) {
		
		return magzinesDao.getMagDetailProductList(categoryId,pageIndex);
	}

	@Override
	public int cfMagzines(String magId, String id) {
		int flag = 1;
		PrintEntity getprintEntity = (PrintEntity) printDao.findById(id);
		if(getprintEntity != null && getprintEntity.getCfStatus() != 1){
			//更新拆分状态
			getprintEntity.setCfStatus(1);
			printDao.saveOrUpdate(getprintEntity);
			
			boolean isBool = splitMagiznes(magId);
			if(isBool){
				getprintEntity.setCfFlag(1);
				printDao.saveOrUpdate(getprintEntity);
			}else{
				flag = 2;
			}
			
			//更新拆分状态
			getprintEntity.setCfStatus(0);
			printDao.saveOrUpdate(getprintEntity);
		}else{
			flag = 3;
		}
        
		return flag;
	}

	@Override
	public int magCfMagzines(String magId, String id) {
		int flag = 1;
		MagzinesEntity getMagEntity = (MagzinesEntity) magzinesDao.findById(id);
		if(getMagEntity != null && getMagEntity.getCfStatus() != 1){
			//更新拆分状态
			getMagEntity.setCfStatus(1);
			magzinesDao.saveOrUpdate(getMagEntity);
			
			boolean isBool = splitMagiznes(magId);
			if(isBool){
				getMagEntity.setCfFlag(1);
				magzinesDao.saveOrUpdate(getMagEntity);
			}else{
				flag = 2;
			}
			
			//更新拆分状态
			getMagEntity.setCfStatus(0);
			magzinesDao.saveOrUpdate(getMagEntity);
		}else{
			flag = 3;
		}
        
		return flag;
	}

}
