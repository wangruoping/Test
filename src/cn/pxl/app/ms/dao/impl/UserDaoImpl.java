package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.UserDao;
import cn.pxl.app.ms.entity.CompanyUserEntity;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<CompanyUserEntity> implements UserDao {

	Logger log = Logger.getLogger(UserDaoImpl.class);
	
	@Override
	public long countForPagingList() {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from CompanyUserEntity as userEntity";
		Query query = session.createQuery(hql);
		long result = (Long) (query.uniqueResult());
		session.close();
		return result;
	}

	@Override
	public List<CompanyUserEntity> pagingList(int offset, int limit) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<CompanyUserEntity> list = (List<CompanyUserEntity>) session
				.createCriteria(CompanyUserEntity.class)
				.addOrder(Order.desc("username"))
				.setFirstResult((offset - 1) * limit).setMaxResults(limit).list();
		session.close();
		return list;
	}

	@Override
	public CompanyUserEntity getUserInfoByNameAndPass(String username, String password) {
		Session session = sessionFactory.openSession();
		log.info("查询用户信息开始");
		Query query = session.createQuery("from CompanyUserEntity userEntity where "
				+ "username = :username"
				+ " and password = :password");
		log.info("查询准备");
		log.info(query);
		query.setParameter("username", username);
		query.setParameter("password", password);
		@SuppressWarnings("unchecked")
		List<CompanyUserEntity> result = query.list();
		log.info("查询结果");
		log.info(result);
		session.close();
		if(result.size() > 0 ){
			return result.get(0);
		}
		return null;
	}

	@Override
	public List<CompanyUserEntity> findAll() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<CompanyUserEntity> list = session.createCriteria(CompanyUserEntity.class).list();
		session.close();
		return list;
	}

	@Override
	public Object findById(String id) {
		Session session = sessionFactory.openSession();
		CompanyUserEntity object =  (CompanyUserEntity) session.get(CompanyUserEntity.class, id);
		session.close();
		return object;
	}
}
