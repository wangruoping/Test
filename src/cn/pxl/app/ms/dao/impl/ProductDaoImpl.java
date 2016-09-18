package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.ProductDao;
import cn.pxl.app.ms.entity.CompanyProductAuxEntity;

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl<CompanyProductAuxEntity> implements ProductDao {

	Logger log = Logger.getLogger(ProductDaoImpl.class);

	@Override
	public List<CompanyProductAuxEntity> findAll() {
		return null;
	}

	@Override
	public Object findById(String id) {
		return null;
	}
	
	
}
