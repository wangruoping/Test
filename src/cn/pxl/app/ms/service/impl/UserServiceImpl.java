package cn.pxl.app.ms.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.pxl.app.ms.dao.MagzinesDao;
import cn.pxl.app.ms.dao.RechargeDao;
import cn.pxl.app.ms.dao.UserDao;
import cn.pxl.app.ms.dao.UserFansDao;
import cn.pxl.app.ms.dto.CommentsDto;
import cn.pxl.app.ms.dto.ConsumeDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.RechargeDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.entity.CommentsEntity;
import cn.pxl.app.ms.entity.ConsumeEntity;
import cn.pxl.app.ms.entity.RechargeEntity;
import cn.pxl.app.ms.entity.UserEntity;
import cn.pxl.app.ms.entity.UserFansEntity;
import cn.pxl.app.ms.service.UserService;
import cn.pxl.app.ms.util.CommonUtils;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDao")
	private UserDao userDao;
	
	@Resource(name="userFansDao")
	private UserFansDao userFansDao;
	
	@Resource(name="magzinesDao")
    private MagzinesDao magzinesDao;
	
	@Resource(name="rechargeDao")
    private RechargeDao rechargeDao;
	
	@Override
	public void addUser(UserEntity userEntity) {
		userDao.save(userEntity);
	}

	@Override
	public List<UserEntity> findAllUser() {
		return userDao.findAll();
	}

	@Override
	public boolean hasUser(String userid) {
		if (userDao.hasEntity(userid) > 0) {
			return true;	
		} else {
			return false;
		}
	}

	@Override
	public UserEntity findUserEntityByUserId(String userId) {
		
		UserEntity userEntity = (UserEntity) userDao.findById(userId);
		return userEntity;
	}

	@Override
	public void updateUser(UserEntity userEntity) {
		userDao.update(userEntity);
	}

	@Override
	public UserEntity findUserEntityByThirdUserId(String thirdUserId,String loginType) {
		return userDao.findUserByThirdUserId(thirdUserId,loginType);
	}

	@Override
	public List<UserDto> getUserGzList(String userid) {
		List<UserDto> userDtos = new ArrayList<UserDto>();
		
		List<UserEntity> userEntities = userDao.getUserGzList(userid);
		
		UserDto userDto = new UserDto();
		for(int k = 0; k < userEntities.size();k++){
			UserEntity userEntity = userEntities.get(k);
			userDto.setUserId(userEntity.getId());
			userDto.setUserImgPath(userEntity.getPicPath());
			userDto.setUsername(userEntity.getUsername());
			int flag = userDao.isUserGzAndFs(userEntity.getId(),userid);
			if(flag == 1){
				userDto.setGzFlag("2");
			}else{
				userDto.setGzFlag("0");
			}
			userDtos.add(userDto);
		}
		
		return userDtos;
	}

	@Override
	public List<UserDto> getUserFsList(String userid) {
List<UserDto> userDtos = new ArrayList<UserDto>();
		
		List<UserEntity> userEntities = userDao.getUserFsList(userid);
		
		UserDto userDto = new UserDto();
		for(int k = 0; k < userEntities.size();k++){
			UserEntity userEntity = userEntities.get(k);
			userDto.setUserId(userEntity.getId());
			userDto.setUserImgPath(userEntity.getPicPath());
			userDto.setUsername(userEntity.getUsername());
			int flag = userDao.isUserGzAndFs(userEntity.getId(),userid);
			if(flag == 1){
				userDto.setGzFlag("2");
			}else{
				userDto.setGzFlag("1");
			}
			userDtos.add(userDto);
		}
		
		return userDtos;
	}

	
	@Override
	public void insertGzInfo(String gzUserid, String bgzUserid) {
		UserEntity gzUserEntity = (UserEntity) userDao.findById(gzUserid);
		UserEntity bgzUserEntity = (UserEntity) userDao.findById(bgzUserid);
		UserFansEntity userFansEntity = new UserFansEntity();
		userFansEntity.setUserEntity(gzUserEntity);
		userFansEntity.setAttentionUserEntity(bgzUserEntity);
		
		userFansDao.save(userFansEntity);
	}

	@Override
	public UserDto getUserInfo(String userid) {
		UserDto userDto = new UserDto();
		UserEntity userEntity = (UserEntity) userDao.findById(userid);
		if(userEntity != null){
			userDto.setUserId(userEntity.getId());
			//用户名
			userDto.setUsername(userEntity.getUsername());
			//用户头像
			userDto.setUserImgPath(userEntity.getPicPath());
			//用户余额
			userDto.setAmount(userEntity.getAmount().toString());
			//用户收货地址
			userDto.setAddress(userEntity.getAddress());
			//第三方用户地址
			userDto.setUserAddress(userEntity.getUserAddress());
			//用户联系电话
			userDto.setPhone(userEntity.getPhone());
			//用户邮编
			userDto.setYoubian(userEntity.getYoubian());
			//用户性别
			userDto.setSex(userEntity.getSex());

		    //用户关注数
			List<UserEntity> userGzEntities = userDao.getUserGzList(userid);
			userDto.setLookCount(String.valueOf(userGzEntities.size()));
			//用户粉丝数
			List<UserEntity> userFsEntities = userDao.getUserFsList(userid);
			userDto.setFansCount(String.valueOf(userFsEntities.size()));
			//用户杂志数
			String magCount = magzinesDao.getUserMagCount(userid);
			userDto.setBooksCount(magCount);
		}
		return userDto;
	}

	@Override
	public List<CommentsDto> getUserCommentList(String userid) {
		List<CommentsDto> result = new ArrayList<CommentsDto>();
		List<CommentsEntity> getList = userDao.getUserCommentList(userid);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");//定义格式，不显示毫秒
		for (int i = 0; i < getList.size(); i++) {
			CommentsEntity commentEntity = getList.get(i);
			CommentsDto commentsDto = new CommentsDto();
			commentsDto.setCommentContent(commentEntity.getCommentContent());
			commentsDto.setCommentUserName(commentEntity.getUserEntity().getUsername());
			commentsDto.setCommentTime(sdf.format(commentEntity.getCommentTime()));
			commentsDto.setMagId(commentEntity.getMagzinesEntity().getId());
			commentsDto.setFileComment(commentEntity.getMagzinesEntity().getFileComment());
			result.add(commentsDto);
		}
		return result;
	}
	
	@Override
	public List<CommentsDto> getMagCommentList(String userid) {
		List<CommentsDto> result = new ArrayList<CommentsDto>();
		List<CommentsEntity> getList = userDao.getUserCommentList(userid);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");//定义格式，不显示毫秒
		for (int i = 0; i < getList.size(); i++) {
			CommentsEntity commentEntity = getList.get(i);
			CommentsDto commentsDto = new CommentsDto();
			commentsDto.setCommentContent(commentEntity.getCommentContent());
			commentsDto.setCommentUserName(commentEntity.getUserEntity().getUsername());
			commentsDto.setCommentTime(sdf.format(commentEntity.getCommentTime()));
			commentsDto.setMagId(commentEntity.getMagzinesEntity().getId());
			commentsDto.setFileComment(commentEntity.getMagzinesEntity().getFileComment());
			result.add(commentsDto);
		}
		return result;
	}

	@Override
	public List<ConsumeDto> getUserConsumeList(String userid) {
		List<ConsumeDto> result = new ArrayList<ConsumeDto>();
		List<ConsumeEntity> getList = userDao.getUserConsumeList(userid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
		for (int i = 0; i < getList.size(); i++) {
			ConsumeEntity consumeEntity = getList.get(i);
			ConsumeDto consumeDto = new ConsumeDto();
			consumeDto.setConsumeAmount(consumeEntity.getConsumeAmount().toString());
			consumeDto.setCunsumeTime(sdf.format(consumeEntity.getConsumeTime()));
			consumeDto.setCunsumeStatus(consumeEntity.getConsumeStatus());
			result.add(consumeDto);
		}
		return result;
	}

	@Override
	public List<RechargeDto> getUserRechargeList(String userid) {
		List<RechargeDto> result = new ArrayList<RechargeDto>();
		List<RechargeEntity> getList = userDao.getUserRechargeList(userid);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");//定义格式，不显示毫秒
		for (int i = 0; i < getList.size(); i++) {
			RechargeEntity rechargeEntity = getList.get(i);
			RechargeDto rechargeDto = new RechargeDto();
			rechargeDto.setRechargeAmount(rechargeEntity.getRechargeAmount().toString());
			rechargeDto.setRechargeTime(sdf.format(rechargeEntity.getRechargeTime()));
			result.add(rechargeDto);
		}
		return result;
	}

	@Override
	public String insertRecharge(String userid, String rechargeAmount,String rechargeFlag ,int rechargeType,String rechargeDate) {
		
		RechargeEntity rechargeEntity = new RechargeEntity();
		UserEntity userEntity = (UserEntity) userDao.findById(userid);
		
		rechargeEntity.setRechargeAmount(new BigDecimal(rechargeAmount));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(rechargeDate);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		rechargeEntity.setRechargeTime(date);	
		rechargeEntity.setRechargeFlag(rechargeFlag);
		rechargeEntity.setRechargeType(rechargeType);
		rechargeEntity.setUserEntity(userEntity);
	
		rechargeDao.save(rechargeEntity);
		
		
		BigDecimal bigDecimal = new BigDecimal(rechargeAmount);
		userEntity.setAmount(userEntity.getAmount().add(bigDecimal));
		userDao.update(userEntity);
		
		return "1";
	}

	@Override
	public PagingDto<UserDto> pagingList(int offset, int limit, int i) {
		PagingDto<UserDto> result = new PagingDto<UserDto>();

        List<UserDto> resultList = new ArrayList<UserDto>();

        result.setTotal(userDao.countForPagingList());

        if (result.getTotal() > 0) {
            List<UserEntity> list = userDao.pagingList(offset, limit);
            for (UserEntity ue : list) {
            	UserDto dto = new UserDto();
            	dto.setAmount(ue.getAmount().toString());
            	dto.setUserId(ue.getId());
            	dto.setUsername(ue.getUsername());
            	dto.setUserThridId(ue.getThreeUserId());
                resultList.add(dto);
            }
        }
        result.setRows(resultList);
        return result;
	}

	@Override
	public List<RechargeDto> getServerUserRechargeList(String userid) {
		List<RechargeDto> result = new ArrayList<RechargeDto>();
		List<RechargeEntity> getList = userDao.getServerUserRechargeList(userid);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");//定义格式，不显示毫秒
		for (int i = 0; i < getList.size(); i++) {
			RechargeEntity rechargeEntity = getList.get(i);
			RechargeDto rechargeDto = new RechargeDto();
			rechargeDto.setRechargeAmount(rechargeEntity.getRechargeAmount().toString());
			rechargeDto.setRechargeTime(sdf.format(rechargeEntity.getRechargeTime()));
			result.add(rechargeDto);
		}
		return result;
	}

	@Override
	public UserEntity findUserByAccountAndPassword(String acount,
			String password) {
		return userDao.findUserByAccountAndPassword(acount,password);
	}

	@Override
	public UserEntity findUserByAccount(String acount) {
		return userDao.findUserByAccount(acount);
	}

	@Override
	public String uploadUserImage(MultipartFile file, String userid,String imageUrl) {
		String path = CommonUtils.getConfig("upload.file.path");
        File savePath = new File(path + userid + ".png");
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), savePath);
        } catch (IOException e) {
        	e.printStackTrace();
            return "";
        }
 
        UserEntity ue = (UserEntity) userDao.findById(userid);
        if(ue != null){
             ue.setPicPath(imageUrl);
             userDao.update(ue);  
             return imageUrl;
        }else{
        	return "";
        }
       
	}

	@Override
	public File getUserImage(String userid) {

		UserEntity userEntity = (UserEntity) userDao.findById(userid);
		
		if(userEntity != null){
			
			String uploadPath = CommonUtils.getConfig("upload.file.path");
	        File file = new File(uploadPath + userEntity.getId() + ".png");
	        if(file.exists()){
        		return file;
	        }else{
	        	return null;
	        }
		}
		return null;
	}

	@Override
	public String uploadUserTIImage(MultipartFile file, String userid,
			String usertype, String imageUrl) {

		String path = CommonUtils.getConfig("upload.file.path");
        File savePath = new File(path + userid + ".png");
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), savePath);
        } catch (IOException e) {
        	e.printStackTrace();
            return "";
        }
 
        UserEntity ue = userDao.findUserByThirdUserId(userid, usertype);
        if(ue != null){
             ue.setPicPath(imageUrl);
             userDao.update(ue);  
             return imageUrl;
        }else{
        	return "";
        }
	}
	
	@Override
	public String validUserAmount(String userid, String amount) {
		UserEntity userEntity = (UserEntity) userDao.findById(userid);
		
		if(userEntity != null){
			
			if(userEntity.getAmount() != null){
				if(userEntity.getAmount().compareTo(new BigDecimal(amount)) >= 0){
					return "1";
				}else{
					return "0";
				}
			}else{
	        	return "0";
	        }
		}
		return "0";
	}

}
