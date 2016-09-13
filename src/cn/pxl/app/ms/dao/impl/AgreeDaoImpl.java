package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.AgreeDao;
import cn.pxl.app.ms.entity.AgreeEntity;

@Repository("agreeDao")
public class AgreeDaoImpl extends BaseDaoImpl<AgreeEntity> implements AgreeDao {

	@Override
	public List<AgreeEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AgreeEntity findAgreeEntity(String magId, String userId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from AgreeEntity where agreeMagzinesId = ? and agreeUserId = ?")
				.setParameter(0, magId).setParameter(1, userId);
		@SuppressWarnings("unchecked")
		List<AgreeEntity> result = query.list();
		session.close();
		if(result.size() > 0){
			return result.get(0);
		}else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AgreeEntity> findAgreeEntity(String magId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from AgreeEntity where agreeMagzinesId = ?")
				.setParameter(0, magId);
		List<AgreeEntity> resultAgreeEntities = (List<AgreeEntity>)query.list();
		session.close();
		return resultAgreeEntities;
	}
	
	

}
