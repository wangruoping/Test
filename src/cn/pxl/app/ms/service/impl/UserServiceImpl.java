package cn.pxl.app.ms.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.pxl.app.ms.dao.UserDao;
import cn.pxl.app.ms.dto.CommentsDto;
import cn.pxl.app.ms.dto.ConsumeDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.RechargeDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.entity.CompanyUserEntity;
import cn.pxl.app.ms.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDao")
	private UserDao userDao;
	
	@Override
	public void addUser(CompanyUserEntity userEntity) {
		userDao.save(userEntity);
	}

	@Override
	public List<CompanyUserEntity> findAllUser() {
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
	public CompanyUserEntity findUserEntityByUserId(String userId) {
		
		CompanyUserEntity userEntity = (CompanyUserEntity) userDao.findById(userId);
		return userEntity;
	}

	@Override
	public void updateUser(CompanyUserEntity userEntity) {
		userDao.update(userEntity);
	}

	@Override
	public CompanyUserEntity findUserEntityByThirdUserId(String thirdUserId,String loginType) {
		return userDao.findUserByThirdUserId(thirdUserId,loginType);
	}

	@Override
	public List<UserDto> getUserGzList(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDto> getUserFsList(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertGzInfo(String gzUserid, String bgzUserid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDto getUserInfo(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentsDto> getUserCommentList(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentsDto> getMagCommentList(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConsumeDto> getUserConsumeList(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RechargeDto> getUserRechargeList(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String insertRecharge(String userid, String rechargeAmount, String rechargeFlag, int rechargeType,
			String rechargeDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagingDto<UserDto> pagingList(int offset, int limit, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RechargeDto> getServerUserRechargeList(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompanyUserEntity findUserByAccountAndPassword(String acount, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompanyUserEntity findUserByAccount(String acount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadUserImage(MultipartFile file, String userid, String ipString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadUserTIImage(MultipartFile file, String userid, String usertype, String ipString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getUserImage(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validUserAmount(String userid, String amount) {
		// TODO Auto-generated method stub
		return null;
	}



}
