package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.DownloadDao;
import cn.pxl.app.ms.entity.ConsumeEntity;
import cn.pxl.app.ms.entity.DownloadEntity;

@Repository("downloadDao")
public class DownloadDaoImpl extends BaseDaoImpl<DownloadEntity> implements DownloadDao {

	@Override
	public List<DownloadEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findByMagIdAndUserId(String magId, String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from DownloadEntity d where d.userEntity.id = ? and d.magzinesEntity.id = ?")
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
