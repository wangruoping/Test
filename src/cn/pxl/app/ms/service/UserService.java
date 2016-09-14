package cn.pxl.app.ms.service;

import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.entity.CompanyUserEntity;

public interface UserService {

	CompanyUserEntity getUserInfoByNameAndPass(String username, String password);

	PagingDto<UserDto> pagingList(int offset, int limit, int i);

	CompanyUserEntity getUserInfo(String userid);
	
	boolean addUser(CompanyUserEntity userEntity);

	boolean updateUser(CompanyUserEntity userEntity);

	boolean deleteUserList(String[] userIdStrings);

	CompanyUserEntity getUserInfoByName(String username);
}
