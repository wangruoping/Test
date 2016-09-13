package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.MagzinesCategoryDao;
import cn.pxl.app.ms.entity.MagzinesCategoryEntity;

@Repository("magzinesCategoryDao")
public class MagzinesCategoryDaoImpl extends BaseDaoImpl<MagzinesCategoryEntity> implements MagzinesCategoryDao {

	@Override
	public List<MagzinesCategoryEntity> findAll() {
		
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<MagzinesCategoryEntity> list = session
				.createQuery("from MagzinesCategoryEntity where deleteFlag = 0 order by createDate desc ").list();
		session.close();
		return list;
	}
	
	@Override
	public List<MagzinesCategoryEntity> pagingList(int offset, int limit) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<MagzinesCategoryEntity> list = (List<MagzinesCategoryEntity>) session
				.createCriteria(MagzinesCategoryEntity.class)
				.add(Restrictions.eq("deleteFlag", 0))
				.addOrder(Order.desc("createDate"))
				.setFirstResult((offset - 1) * limit).setMaxResults(limit).list();
		session.close();
		return list;
	}

	@Override
	public MagzinesCategoryEntity findById(String id) {
		Session session = sessionFactory.openSession();
		MagzinesCategoryEntity magzinesCategoryEntity = (MagzinesCategoryEntity) session.get(MagzinesCategoryEntity.class, id);
		session.close();
		if(magzinesCategoryEntity.getDeleteFlag() == 1){
			return null;
		}
		return magzinesCategoryEntity;
	}

	@Override
	public long countForPagingList() {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from MagzinesCategoryEntity where deleteFlag = 0";
		Query query = session.createQuery(hql);
		long result = (Long) (query.uniqueResult());
		session.close();
		return result;
	}

	@Override
	public void deleteMagCategoryList(String[] categoryIdStrings) {
		Session session = sessionFactory.openSession();
		String hql = "update tbl_magzines_category set deleteFlag = 1 where id in (:ids) and deleteFlag = 0";
		Query query = session.createSQLQuery(hql);
		query.setParameterList("ids", categoryIdStrings).executeUpdate();
		
		String magHql = "update tbl_magzines set deleteFlag = 1 where magzinesCategoryId in (:catIds) and deleteFlag = 0";
		Query magQuery = session.createSQLQuery(magHql);
		magQuery.setParameterList("catIds", categoryIdStrings).executeUpdate();
		session.close();
		
	}


}
