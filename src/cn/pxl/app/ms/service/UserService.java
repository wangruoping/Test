package cn.pxl.app.ms.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.pxl.app.ms.dto.CommentsDto;
import cn.pxl.app.ms.dto.ConsumeDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.RechargeDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.entity.UserEntity;

public interface UserService {
	void addUser(UserEntity userEntity);
	List<UserEntity> findAllUser();
	boolean hasUser(String userid);
	UserEntity findUserEntityByUserId(String userId);
	UserEntity findUserEntityByThirdUserId(String thirdUserId,String loginType);
	void updateUser(UserEntity userEntity);
	List<UserDto> getUserGzList(String userid);
	List<UserDto> getUserFsList(String userid);
	void insertGzInfo(String gzUserid, String bgzUserid);
	UserDto getUserInfo(String userid);
	public List<CommentsDto> getUserCommentList(String userid);
	public List<CommentsDto> getMagCommentList(String userid);
	List<ConsumeDto> getUserConsumeList(String userid);
	List<RechargeDto> getUserRechargeList(String userid);
	String insertRecharge(String userid, String rechargeAmount,String rechargeFlag ,int rechargeType,String rechargeDate);
	PagingDto<UserDto> pagingList(int offset, int limit, int i);
	List<RechargeDto> getServerUserRechargeList(String userid);
	UserEntity findUserByAccountAndPassword(String acount, String password);
	UserEntity findUserByAccount(String acount);
	String uploadUserImage(MultipartFile file, String userid, String ipString);
	String uploadUserTIImage(MultipartFile file, String userid, String usertype, String ipString);
	File getUserImage(String userid);
	String validUserAmount(String userid, String amount);
}
