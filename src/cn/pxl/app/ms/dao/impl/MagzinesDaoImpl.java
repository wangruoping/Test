package cn.pxl.app.ms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.MagzinesDao;
import cn.pxl.app.ms.entity.MagzinesEntity;

@Repository("magzinesDao")
public class MagzinesDaoImpl extends BaseDaoImpl<MagzinesEntity> implements
		MagzinesDao {

	@Override
	public List<MagzinesEntity> findAll() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<MagzinesEntity> list = session
				.createCriteria(MagzinesEntity.class)
				.add(Restrictions.eq("deleteFlag", 0))
				.list();
		session.close();
		return list;
	}

	@Override
	public List<MagzinesEntity> pagingList(int offset, int limit ,int typeSet) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<MagzinesEntity> list = (List<MagzinesEntity>) session
				.createCriteria(MagzinesEntity.class)
				.add(Restrictions.isNull("zfMagId"))
				.add(Restrictions.eq("publicTypeSet", typeSet))
				.add(Restrictions.eq("deleteFlag", 0))
				.addOrder(Order.desc("uploadTime"))
				.setFirstResult((offset - 1) * limit).setMaxResults(limit).list();
		session.close();
		return list;
	}

	@Override
	public long countForPagingList(int typeSet) {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from MagzinesEntity as magzinesEntity where magzinesEntity.zfMagId IS NULL and magzinesEntity.deleteFlag = 0 and magzinesEntity.publicTypeSet = ?";
		Query query = session.createQuery(hql).setParameter(0, typeSet);
		long result = (Long) (query.uniqueResult());
		session.close();
		return result;
	}

	@Override
	public MagzinesEntity findById(String id) {
		Session session = sessionFactory.openSession();
		MagzinesEntity object =  (MagzinesEntity) session.get(MagzinesEntity.class, id);
		if(object.getDeleteFlag() == 1){
			object = null;
		}
		session.close();
		return object;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MagzinesEntity> getFriendMagList(String userid) {

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from MagzinesEntity as m where m.userEntity.id != ? and m.publicTypeSet = 0 and m.deleteFlag = 0 order by m.uploadTime desc")
				.setParameter(0, userid);
		query.setFirstResult(0);
		query.setMaxResults(5);
		List<MagzinesEntity> result = query.list();
		session.close();
		
		return result;
	}

	@Override
	public Integer getMagZfListCount(String magid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from MagzinesEntity as m where m.zfMagId = ? and m.deleteFlag = 0")
				.setParameter(0, magid);
		@SuppressWarnings("unchecked")
		List<MagzinesEntity> result = query.list();
		session.close();
		
		return result.size();
	}

	@Override
	public List<MagzinesEntity> getUserMagList(String userid,Integer pageIndex) {
		Session session = sessionFactory.openSession();
		Integer pageStart = (pageIndex - 1) * 5;
		Query query = session.createQuery("from MagzinesEntity as m where m.userEntity.id = ? and m.publicTypeSet = 0 and m.deleteFlag = 0 order by m.uploadTime desc")
				.setParameter(0, userid).setFirstResult(pageStart).setMaxResults(5);
		@SuppressWarnings("unchecked")
		List<MagzinesEntity> result = query.list();
		session.close();
		
		return result;
	}
	
	@Override
	public List<MagzinesEntity> getShopMagDetailList(String userid,
			String searchText, int parseInt, int pageSize) {
		Session session = sessionFactory.openSession();
		Query query = null;
		Integer pageStart = (parseInt - 1) * pageSize;
		
		query = session.createQuery("from MagzinesEntity as m left join m.userEntity u "
				+ "where "
				+ "m.publicTypeSet = 1 and "
				+ "m.deleteFlag = 0 and "
				+ "(u.username like ? or "
				+ "m.fileComment like ? ) "
				+ "order by m.uploadTime desc ")
				.setParameter(0, "%" + searchText + "%")
				.setParameter(1, "%" + searchText + "%")
				.setFirstResult(pageStart).setMaxResults(pageSize);

		List<MagzinesEntity> result = new ArrayList<MagzinesEntity>();
		@SuppressWarnings("unchecked")
		List<Object> list1 = query.list();
		for (int i = 0; i < list1.size(); i++) {
			Object[] objArray = (Object[]) list1.get(i);
			if(objArray != null){
				MagzinesEntity magzinesEntity = (MagzinesEntity) objArray[0];
				result.add(magzinesEntity);
			}
		}
		session.close();
		return result;
	}

	@Override
	public List<MagzinesEntity> getFriendMagDetailList(String userid,Integer pageIndex,Integer pageSize) {
		Session session = sessionFactory.openSession();
		Integer pageStart = (pageIndex - 1) * pageSize;
		
		Query query = session.createQuery("from MagzinesEntity as m where "
				+ "m.userEntity.id != ? and "
				+ "m.publicTypeSet = 0 and "
				+ "m.deleteFlag = 0 "
				+ "order by m.uploadTime desc ")
				.setParameter(0, userid)
				.setFirstResult(pageStart).setMaxResults(pageSize);
		@SuppressWarnings("unchecked")
		List<MagzinesEntity> result = query.list();
		session.close();
		return result;
		
	}

	@Override
	public void deleteMagProductList(String[] productIdsStrings) {
		Session session = sessionFactory.openSession();
		String hql = "update tbl_magzines set deleteFlag = 1 where id in (:ids) and deleteFlag = 0";
		Query query = session.createSQLQuery(hql);
		query.setParameterList("ids", productIdsStrings).executeUpdate();
		session.close();
	}

	@Override
	public List<MagzinesEntity> getMagProductList(String categoryId, int top) {
		Session session = sessionFactory.openSession();
		String hql = "from MagzinesEntity as m where " ;
		Query query;
		if(categoryId == null || "".equals(categoryId)){
			hql += "m.publicTypeSet = 1 and m.deleteFlag = 0 order by m.uploadTime desc";
			query = session.createQuery(hql).setMaxResults(top);
		}else{
			hql += "m.magzinesCategoryEntity.id = ? and m.publicTypeSet = 1 and m.deleteFlag = 0 order by m.uploadTime desc";
			query = session.createQuery(hql)
					.setParameter(0, categoryId).setMaxResults(top);
		}

		@SuppressWarnings("unchecked")
		List<MagzinesEntity> result = query.list();
		session.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MagzinesEntity> getAllMagProductList(int orderType,
			String pageIndex) {
		
		Integer pageStart = (Integer.parseInt(pageIndex) - 1) * 10;
		
		Session session = sessionFactory.openSession();
		Query query = null;
		List<MagzinesEntity> result = new ArrayList<MagzinesEntity>();
		Date now = new Date();
		String querySql = "";
		switch (orderType) {
		case 1:
			querySql = "from MagzinesEntity " +
					"where startTime IS NOT NULL " +
					"and deleteFlag = 0 and publicTypeSet = 1 " + 
					"and endTime IS NOT NULL " +
					"and ? > startTime " +
					"and ? < endTime " +
					"order by startTime desc";
			query = session.createQuery(querySql);
			query.setDate(0, now);
			query.setDate(1, now);
			query.setFirstResult(pageStart).setMaxResults(10);
			result = query.list();
			break;
		case 2:
			querySql = "from MagzinesEntity " +
					"where startTime IS NOT NULL " +
					"and deleteFlag = 0 and publicTypeSet = 1 " + 
					"and endTime IS NOT NULL " +
					"and ? > startTime " +
					"and ? < endTime " +
					"order by endTime desc";
			query = session.createQuery(querySql);
			query.setDate(0, now);
			query.setDate(1, now);
			query.setFirstResult(pageStart).setMaxResults(10);
			result = query.list();
			break;
		case 3:
			querySql = "select m,(select count(*) from DownloadEntity d where d.magzinesEntity.id = m.id) as downCount from MagzinesEntity m " +
					"where m.startTime IS NOT NULL " +
					"and m.deleteFlag = 0 and m.publicTypeSet = 1 " + 
					"and m.endTime IS NOT NULL " +
					"and ? > m.startTime " +
					"and ? < m.endTime " +
					"order by downCount desc";
			query = session.createQuery(querySql);
			query.setDate(0, now);
			query.setDate(1, now);
			query.setFirstResult(pageStart).setMaxResults(10);
			List<Object> list = query.list();
			for (int i = 0; i < list.size(); i++) {
				Object[] objArray = (Object[]) list.get(i);
				if(objArray != null){
					MagzinesEntity magzinesEntity = (MagzinesEntity) objArray[0];
					result.add(magzinesEntity);
				}
			}
			break;
		case 4:
			querySql = "select m, " +
					"(select count(*) from AgreeEntity a where a.magzinesEntity.id = m.id) as amCount, "+
					"(select count(*) from CommentsEntity c where c.magzinesEntity.id = m.id) as cmCount "+
					"from MagzinesEntity m " +
					"where m.startTime IS NOT NULL " +
					"and m.deleteFlag = 0 and m.publicTypeSet = 1 " + 
					"and m.endTime IS NOT NULL " +
					"and  ? > m.startTime " +
					"and  ? < m.endTime " +
					"order by amCount desc,cmCount desc";
			query = session.createQuery(querySql);
			query.setDate(0, now);
			query.setDate(1, now);
			query.setFirstResult(pageStart).setMaxResults(10);
			List<Object> list1 = query.list();
			for (int i = 0; i < list1.size(); i++) {
				Object[] objArray = (Object[]) list1.get(i);
				if(objArray != null){
					MagzinesEntity magzinesEntity = (MagzinesEntity) objArray[0];
					result.add(magzinesEntity);
				}
			}
			break;
		case 5:
			querySql = "from MagzinesEntity " +
						"where startTime IS NOT NULL " +
						"and deleteFlag = 0 and publicTypeSet = 1 " + 
						"and endTime IS NOT NULL " +
						"and  ? > startTime " +
						"and  ? < endTime " +
						"order by uploadTime desc";
			query = session.createQuery(querySql);
			query.setDate(0, now);
			query.setDate(1, now);
			query.setFirstResult(pageStart).setMaxResults(10);
		    result = query.list();
			
			break;
		default:
			break;
		}
		
		session.close();
		return result;
		
	}


	@Override
	public List<String> getHotMagProductList(int top) {
		
		Session session = sessionFactory.openSession();
		//根据暂时(评论数，点赞数总和的大小)进行排序
		String sql = "select v.id from (select m.id, "
			+"(select count(*) from tbl_agree a where a.agreeMagzinesId = m.id) amCount, "
			+"(select count(*) from tbl_comments c where c.commentMagzinesId = m.id) cmCount "
			+"from tbl_magzines m "
			+"where m.deleteFlag = 0 "
			+"and m.publicTypeSet = 1 "
			+"order by amCount desc,cmCount desc) v";		
		Query query = session.createSQLQuery(sql);
		query.setMaxResults(top);
		@SuppressWarnings("unchecked")
		List<String> result	= query.list();
		
		session.close();
		return result;
	}

	@Override
	public String getUserMagCount(String userid) {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from MagzinesEntity as m where m.userEntity.id = ? and m.publicTypeSet = 0 and m.deleteFlag = 0";
		Query query = session.createQuery(hql).setParameter(0, userid);
		long result = (Long) (query.uniqueResult());
		session.close();
		return String.valueOf(result);
	}

	@Override
	public List<MagzinesEntity> getUserMagAllList(String userid) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from MagzinesEntity as m where m.userEntity.id = ? and m.publicTypeSet = 0 and m.deleteFlag = 0 order by m.uploadTime desc")
				.setParameter(0, userid).setFirstResult(0).setMaxResults(5);
		@SuppressWarnings("unchecked")
		List<MagzinesEntity> result = query.list();
		session.close();
		
		return result;
	}

	@Override
	public List<MagzinesEntity> getMagDetailProductList(String categoryId,
			String pageIndex) {
		Integer pageStart = (Integer.parseInt(pageIndex) - 1) * 20;
		
		Session session = sessionFactory.openSession();
		String hql = "from MagzinesEntity as m where " ;
		Query query;
		
		hql += "m.magzinesCategoryEntity.id = ? and m.publicTypeSet = 1 and m.deleteFlag = 0 order by m.uploadTime desc";
		query = session.createQuery(hql)
					.setParameter(0, categoryId).setFirstResult(pageStart).setMaxResults(20);
		

		@SuppressWarnings("unchecked")
		List<MagzinesEntity> result = query.list();
		session.close();
		return result;
	}

}
