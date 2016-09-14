package cn.pxl.app.ms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.pxl.app.ms.dao.UserDao;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.entity.CompanyUserEntity;
import cn.pxl.app.ms.service.UserService;
import cn.pxl.app.ms.util.CommonUtils;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name="userDao")
	private UserDao userDao;

	@Override
	public CompanyUserEntity getUserInfoByNameAndPass(String username, String password) {
		return userDao.getUserInfoByNameAndPass(username, CommonUtils.encodePassword(password));
	}

	@Override
	public PagingDto<UserDto> pagingList(int offset, int limit, int i) {
		PagingDto<UserDto> result = new PagingDto<UserDto>();

        List<UserDto> resultList = new ArrayList<UserDto>();

        result.setTotal(userDao.countForPagingList());

        if (result.getTotal() > 0) {
            List<CompanyUserEntity> list = userDao.pagingList(offset, limit);
            for (CompanyUserEntity ue : list) {
            	UserDto dto = new UserDto();
            	dto.setUserId(ue.getId());
            	dto.setUsername(ue.getUsername());
                resultList.add(dto);
            }
        }
        result.setRows(resultList);
        return result;
	}

	@Override
	public CompanyUserEntity getUserInfo(String userid) {
		return (CompanyUserEntity) userDao.findById(userid);
	}
	
	@Override
	public boolean addUser(CompanyUserEntity userEntity) {
		userDao.save(userEntity);
		return true;
	}

	@Override
	public boolean updateUser(CompanyUserEntity userEntity) {
		userDao.update(userEntity);
		return true;
	}

	@Override
	public boolean deleteUserList(String[] userIdStrings) {
		for (int i = 0; i < userIdStrings.length; i++) {
			CompanyUserEntity userEntity = new CompanyUserEntity();
			userEntity.setId(userIdStrings[i]);
			userDao.delete(userEntity);
		}
		return true;
	}

	@Override
	public CompanyUserEntity getUserInfoByName(String username) {
		return userDao.getUserInfoByName(username);
	}
}
