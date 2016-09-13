package cn.pxl.app.ms.service;

import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.entity.CompanyUserEntity;

public interface UserService {

	CompanyUserEntity getUserInfoByNameAndPass(String username, String password);

	PagingDto<UserDto> pagingList(int offset, int limit, int i);
}
