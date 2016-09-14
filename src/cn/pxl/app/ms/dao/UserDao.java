package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.CompanyUserEntity;

public interface UserDao extends BaseDao<CompanyUserEntity> {
	
	/**
	 * 分页用的两个方法
	 * 
	 * */
	long countForPagingList();
	List<CompanyUserEntity> pagingList(int offset, int limit);
	
	/**
	 * 根据用户名密码获取用户信息
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * 
	 * @return 用户信息
	 * */
	CompanyUserEntity getUserInfoByNameAndPass(String username, String password);
	/**
	 * 根据用户名获取用户信息
	 * 
	 * @param username 用户名
	 * @return 用户信息
	 * */
	CompanyUserEntity getUserInfoByName(String username);
}
