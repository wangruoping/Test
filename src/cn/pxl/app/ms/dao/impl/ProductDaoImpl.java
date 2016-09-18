package cn.pxl.app.ms.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.ProductDao;
import cn.pxl.app.ms.entity.CompanyPoductEntity;

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl<CompanyPoductEntity> implements ProductDao {

	Logger log = Logger.getLogger(ProductDaoImpl.class);

	@Override
	public List<CompanyPoductEntity> findAll() {
		return null;
	}

	@Override
	public Object findById(String id) {
		return null;
	}
	
	
}
