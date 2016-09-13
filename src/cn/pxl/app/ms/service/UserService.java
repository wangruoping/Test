package cn.pxl.app.ms.service;

import cn.pxl.app.ms.entity.CompanyUserEntity;

public interface UserService {

	CompanyUserEntity getUserInfoByNameAndPass(String username, String password);
}
