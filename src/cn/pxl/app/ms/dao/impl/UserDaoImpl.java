package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.UserDao;
import cn.pxl.app.ms.entity.CommentsEntity;
import cn.pxl.app.ms.entity.ConsumeEntity;
import cn.pxl.app.ms.entity.RechargeEntity;
import cn.pxl.app.ms.entity.UserEntity;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<UserEntity> implements UserDao {

	Logger log = Logger.getLogger(UserDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> findAll() {
		Session session = sessionFactory.openSession();
		List<UserEntity> list = session.createCriteria(UserEntity.class).list();
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
	public UserEntity findById(String id) {
		Session session = sessionFactory.openSession();
		UserEntity object =  (UserEntity) session.get(UserEntity.class, id);
		session.close();
		return object;
	}

	@Override
	public UserEntity findUserByThirdUserId(String thirdUserId,String loginType) {
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
		List<UserEntity> result = query.list();
		log.info("查询结果");
		log.info(result);
		session.close();
		if(result.size() > 0 ){
			return result.get(0);
		}
		return null;
	}

	@Override
	public List<UserEntity> getUserGzList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select ufe.attentionUserEntity from UserFansEntity ufe where ufe.userEntity.id = ?");
		query.setParameter(0, userid);
		@SuppressWarnings("unchecked")
		List<UserEntity> result = query.list();
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
		List<UserEntity> result = query.list();
		session.close();
		if(result.size() > 0 ){
			return 1;
		}
		return 0;
	}

	@Override
	public List<UserEntity> getUserFsList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select ufe.userEntity from UserFansEntity ufe where ufe.attentionUserEntity.id = ?");
		query.setParameter(0, userid);
		@SuppressWarnings("unchecked")
		List<UserEntity> result = query.list();
		session.close();
		return result;
	}

	@Override
	public List<CommentsEntity> getUserCommentList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from CommentsEntity c where c.userEntity.id = ?");
		query.setParameter(0, userid);
		@SuppressWarnings("unchecked")
		List<CommentsEntity> result = query.list();
		session.close();
		return result;

	}
	
	@Override
	public List<CommentsEntity> getMagCommentList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from CommentsEntity c where c.magzinesEntity.id in (select m.id from MagzinesEntity m where m.userEntity.id = ?)");
		query.setParameter(0, userid);
		query.setParameter(1, userid);
		@SuppressWarnings("unchecked")
		List<CommentsEntity> result = query.list();
		session.close();
		return result;
	}
	
	@Override
	public List<ConsumeEntity> getUserConsumeList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from ConsumeEntity c where c.userEntity.id = ? and c.consumeFLag = 1");
		query.setParameter(0, userid);
		@SuppressWarnings("unchecked")
		List<ConsumeEntity> result = query.list();
		session.close();
		return result;
	}

	@Override
	public List<RechargeEntity> getUserRechargeList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from RechargeEntity r where r.userEntity.id = ? and r.rechargeFlag = 1");
		query.setParameter(0, userid);
		@SuppressWarnings("unchecked")
		List<RechargeEntity> result = query.list();
		session.close();
		return result;
	}

	@Override
	public long countForPagingList() {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from UserEntity as userEntity";
		Query query = session.createQuery(hql);
		long result = (Long) (query.uniqueResult());
		session.close();
		return result;
	}

	@Override
	public List<UserEntity> pagingList(int offset, int limit) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<UserEntity> list = (List<UserEntity>) session
				.createCriteria(UserEntity.class)
				.addOrder(Order.desc("createDate"))
				.setFirstResult((offset - 1) * limit).setMaxResults(limit).list();
		session.close();
		return list;
	}

	@Override
	public List<RechargeEntity> getServerUserRechargeList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from RechargeEntity r where r.userEntity.id = ?");
		query.setParameter(0, userid);
		@SuppressWarnings("unchecked")
		List<RechargeEntity> result = query.list();
		session.close();
		return result;
	}

	@Override
	public UserEntity findUserByAccountAndPassword(String acount,
			String password) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from UserEntity userEntity where "
				+ "acount = :acount"
				+ " and password = :password order by createDate desc");
		query.setParameter("acount", acount);
		query.setParameter("password", password);
		@SuppressWarnings("unchecked")
		List<UserEntity> result = query.list();
		session.close();
		if(result.size() > 0 ){
			return result.get(0);
		}
		return null;
	}

	@Override
	public UserEntity findUserByAccount(String acount) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from UserEntity userEntity where "
				+ "acount = :acount order by createDate desc");
		query.setParameter("acount", acount);
		@SuppressWarnings("unchecked")
		List<UserEntity> result = query.list();
		session.close();
		if(result.size() > 0 ){
			return result.get(0);
		}
		return null;
	}


}
