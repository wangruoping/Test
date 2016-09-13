package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.PrintDao;
import cn.pxl.app.ms.entity.PrintEntity;

@Repository("printDao")
public class PrintDaoImpl extends BaseDaoImpl<PrintEntity> implements PrintDao {

	@Override
	public List<PrintEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findById(String id) {
		Session session = sessionFactory.openSession();
		PrintEntity p = (PrintEntity) session.get(PrintEntity.class, id);
		session.close();
		return p;
	}

	@Override
	public long countForPagingList() {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from PrintEntity as printEntity where printEntity.deleteFlag = 0";
		Query query = session.createQuery(hql);
		long result = (Long) (query.uniqueResult());
		session.close();
		return result;
	}

	@Override
	public List<PrintEntity> pagingList(int offset, int limit) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<PrintEntity> list = (List<PrintEntity>) session
				.createCriteria(PrintEntity.class)
				.add(Restrictions.eq("deleteFlag", 0))
				.addOrder(Order.desc("printTime"))
				.setFirstResult((offset - 1) * limit).setMaxResults(limit).list();
		session.close();
		return list;
	}

}
