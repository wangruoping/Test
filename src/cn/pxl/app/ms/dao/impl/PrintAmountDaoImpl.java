package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.PrintAmountDao;
import cn.pxl.app.ms.entity.PrintAmountEntity;

@Repository("printAmountDao")
public class PrintAmountDaoImpl extends BaseDaoImpl<PrintAmountEntity> implements PrintAmountDao {

	@Override
	public List<PrintAmountEntity> findAll() {
		
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<PrintAmountEntity> list = session
				.createCriteria(PrintAmountEntity.class)
				.list();
		session.close();
		return list;
	}

	@Override
	public Object findById(String id) {
		return null;
	}

	
	

}
