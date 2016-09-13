package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.UserFansDao;
import cn.pxl.app.ms.entity.UserFansEntity;

@Repository("userFansDao")
public class UserFansImpl extends BaseDaoImpl<UserFansEntity> implements UserFansDao {

	@Override
	public List<UserFansEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
