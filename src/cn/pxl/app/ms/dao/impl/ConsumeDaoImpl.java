package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.ConsumeDao;
import cn.pxl.app.ms.entity.ConsumeEntity;

@Repository("consumeDao")
public class ConsumeDaoImpl extends BaseDaoImpl<ConsumeEntity> implements ConsumeDao {

	@Override
	public List<ConsumeEntity> findAll() {
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findUserBuyPro(String userid, String magId) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ConsumeEntity c where c.userEntity.id = ? and c.magzinesEntity.id = ?")
				.setParameter(0, userid).setParameter(1, magId);
		@SuppressWarnings("unchecked")
		List<ConsumeEntity> result = query.list();
		session.close();
		if(result.size() > 0){
			return true;
		}else{
			return false;
		}
	}

}
