package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.ProductDao;
import cn.pxl.app.ms.entity.CompanyProductAuxEntity;

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl<CompanyProductAuxEntity> implements ProductDao {

	Logger log = Logger.getLogger(ProductDaoImpl.class);

	@Override
	public List<CompanyProductAuxEntity> findAll() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<CompanyProductAuxEntity> list = session.createCriteria(CompanyProductAuxEntity.class).list();
		session.close();
		return list;
	}

	@Override
	public Object findById(String id) {
		return null;
	}

	@Override
	public boolean addProductField(CompanyProductAuxEntity companyProductAuxEntity) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		//添加 表字段的更新语句
		String addFieldSql = "ALTER TABLE company_product ADD " +companyProductAuxEntity.getName()+ " varchar;";
		int updateCount = session.createSQLQuery(addFieldSql).executeUpdate();
		transaction.commit();
		session.close();
		return updateCount > 0 ? true : false;
	}

	@Override
	public CompanyProductAuxEntity getProductTableByName(String name) {
		Session session = sessionFactory.openSession();
		log.info("查询用户信息开始");
		Query query = session.createQuery("from CompanyProductAuxEntity companyProductAuxEntity where "
				+ "name = :name");
		log.info("查询准备");
		log.info(query);
		query.setParameter("name", name);
		@SuppressWarnings("unchecked")
		List<CompanyProductAuxEntity> result = query.list();
		log.info("查询结果");
		log.info(result);
		session.close();
		if(result.size() > 0 ){
			return result.get(0);
		}
		return null;
	}
	
	
}
