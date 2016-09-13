package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.CompanyUserEntity;

public interface UserDao extends BaseDao<CompanyUserEntity> {
	long hasEntity(String id);
	CompanyUserEntity findUserByThirdUserId(String thirdUserId,String loginType);
	List<CompanyUserEntity> getUserGzList(String userid);
	int isUserGzAndFs(String id, String userid);
	List<CompanyUserEntity> getUserFsList(String userid);
	long countForPagingList();
	List<CompanyUserEntity> pagingList(int offset, int limit);
	CompanyUserEntity findUserByAccount(String acount);
}
