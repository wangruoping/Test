package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.UserDao;
import cn.pxl.app.ms.entity.CompanyUserEntity;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<CompanyUserEntity> implements UserDao {

	Logger log = Logger.getLogger(UserDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyUserEntity> findAll() {
		Session session = sessionFactory.openSession();
		List<CompanyUserEntity> list = session.createCriteria(CompanyUserEntity.class).list();
		session.close();
		return list;
	}

	@Override
	public long hasEntity(String id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select count(*) from UserEntity userEntity where id = :id");
		query.setParameter("id", id);
		long result = (Long) (query.uniqueResult());
		session.close();
		return result;
	}

	@Override
	public CompanyUserEntity findById(String id) {
		Session session = sessionFactory.openSession();
		CompanyUserEntity object =  (CompanyUserEntity) session.get(CompanyUserEntity.class, id);
		session.close();
		return object;
	}

	@Override
	public CompanyUserEntity findUserByThirdUserId(String thirdUserId,String loginType) {
		Session session = sessionFactory.openSession();
		log.info("查询第三方用户信息开始");
		Query query = session.createQuery("from UserEntity userEntity where "
				+ "threeUserId = :threeUserId"
				+ " and loginType = :loginType order by createDate desc");
		log.info("查询准备");
		log.info(query);
		query.setParameter("threeUserId", thirdUserId);
		query.setParameter("loginType", loginType.toString());
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
	public List<CompanyUserEntity> getUserGzList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select ufe.attentionUserEntity from UserFansEntity ufe where ufe.userEntity.id = ?");
		query.setParameter(0, userid);
		@SuppressWarnings("unchecked")
		List<CompanyUserEntity> result = query.list();
		session.close();
		return result;
	}

	@Override
	public int isUserGzAndFs(String id, String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from UserFansEntity ufe where ufe.userEntity.id = ? and ufe.attentionUserEntity.id = ?");
		query.setParameter(0, userid);
		query.setParameter(1, id);
		@SuppressWarnings("unchecked")
		List<CompanyUserEntity> result = query.list();
		session.close();
		if(result.size() > 0 ){
			return 1;
		}
		return 0;
	}

	@Override
	public List<CompanyUserEntity> getUserFsList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select ufe.userEntity from UserFansEntity ufe where ufe.attentionUserEntity.id = ?");
		query.setParameter(0, userid);
		@SuppressWarnings("unchecked")
		List<CompanyUserEntity> result = query.list();
		session.close();
		return result;
	}

	@Override
	public long countForPagingList() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CompanyUserEntity> pagingList(int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompanyUserEntity findUserByAccount(String acount) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
