package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.TemplateDao;
import cn.pxl.app.ms.entity.CompanyTemplateEntity;

@Repository("templateDao")
public class TemplateDaoImpl extends BaseDaoImpl<CompanyTemplateEntity> implements TemplateDao {

	Logger log = Logger.getLogger(TemplateDaoImpl.class);
	
	@Override
	public long countForPagingList() {
		Session session = sessionFactory.openSession();
		String hql = "select count(*) from CompanyTemplateEntity as companyTemplateEntity";
		Query query = session.createQuery(hql);
		long result = (Long) (query.uniqueResult());
		session.close();
		return result;
	}

	@Override
	public List<CompanyTemplateEntity> pagingList(int offset, int limit) {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<CompanyTemplateEntity> list = (List<CompanyTemplateEntity>) session
				.createCriteria(CompanyTemplateEntity.class)
				.addOrder(Order.desc("name"))
				.setFirstResult((offset - 1) * limit).setMaxResults(limit).list();
		session.close();
		return list;
	}

	@Override
	public List<CompanyTemplateEntity> findAll() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<CompanyTemplateEntity> list = session.createCriteria(CompanyTemplateEntity.class).list();
		session.close();
		return list;
	}

	@Override
	public Object findById(String id) {
		Session session = sessionFactory.openSession();
		CompanyTemplateEntity object =  (CompanyTemplateEntity) session.get(CompanyTemplateEntity.class, id);
		session.close();
		return object;
	}

	@Override
	public CompanyTemplateEntity getTemplateInfoByName(String templateName) {
		Session session = sessionFactory.openSession();
		log.info("查询模板信息开始");
		Query query = session.createQuery("from CompanyTemplateEntity companyTemplateEntity where "
				+ "name = :templateName");
		log.info("查询准备");
		log.info(query);
		query.setParameter("templateName", templateName);
		@SuppressWarnings("unchecked")
		List<CompanyTemplateEntity> result = query.list();
		log.info("查询结果");
		log.info(result);
		session.close();
		if(result.size() > 0 ){
			return result.get(0);
		}
		return null;
	}
}
