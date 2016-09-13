package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.CommentsEntity;
import cn.pxl.app.ms.entity.ConsumeEntity;
import cn.pxl.app.ms.entity.RechargeEntity;
import cn.pxl.app.ms.entity.CompanyUserEntity;

public interface UserDao extends BaseDao<CompanyUserEntity> {
	long hasEntity(String id);
	CompanyUserEntity findUserByThirdUserId(String thirdUserId,String loginType);
	List<CompanyUserEntity> getUserGzList(String userid);
	int isUserGzAndFs(String id, String userid);
	List<CompanyUserEntity> getUserFsList(String userid);
	List<CommentsEntity> getUserCommentList(String userid);
	List<CommentsEntity> getMagCommentList(String userid);
	public List<ConsumeEntity> getUserConsumeList(String userid);
	public List<RechargeEntity> getUserRechargeList(String userid);
	long countForPagingList();
	List<CompanyUserEntity> pagingList(int offset, int limit);
	List<RechargeEntity> getServerUserRechargeList(String userid);
	CompanyUserEntity findUserByAccountAndPassword(String acount, String password);
	CompanyUserEntity findUserByAccount(String acount);
}
