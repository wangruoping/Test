package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.RechargeDao;
import cn.pxl.app.ms.entity.RechargeEntity;

@Repository("rechargeDao")
public class RechargeDaoImpl extends BaseDaoImpl<RechargeEntity> implements RechargeDao {

	@Override
	public List<RechargeEntity> findAll() {
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
