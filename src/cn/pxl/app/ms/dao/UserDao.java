package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.CommentsEntity;
import cn.pxl.app.ms.entity.ConsumeEntity;
import cn.pxl.app.ms.entity.RechargeEntity;
import cn.pxl.app.ms.entity.UserEntity;

public interface UserDao extends BaseDao<UserEntity> {
	long hasEntity(String id);
	UserEntity findUserByThirdUserId(String thirdUserId,String loginType);
	List<UserEntity> getUserGzList(String userid);
	int isUserGzAndFs(String id, String userid);
	List<UserEntity> getUserFsList(String userid);
	List<CommentsEntity> getUserCommentList(String userid);
	List<CommentsEntity> getMagCommentList(String userid);
	public List<ConsumeEntity> getUserConsumeList(String userid);
	public List<RechargeEntity> getUserRechargeList(String userid);
	long countForPagingList();
	List<UserEntity> pagingList(int offset, int limit);
	List<RechargeEntity> getServerUserRechargeList(String userid);
	UserEntity findUserByAccountAndPassword(String acount, String password);
	UserEntity findUserByAccount(String acount);
}
