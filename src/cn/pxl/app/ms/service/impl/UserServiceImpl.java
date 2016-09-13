package cn.pxl.app.ms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.pxl.app.ms.dao.UserDao;
import cn.pxl.app.ms.entity.CompanyUserEntity;
import cn.pxl.app.ms.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDao")
	private UserDao userDao;

	@Override
	public CompanyUserEntity getUserInfoByNameAndPass(String username, String password) {
		return userDao.getUserInfoByNameAndPass(username, password);
	}
	
	
}
