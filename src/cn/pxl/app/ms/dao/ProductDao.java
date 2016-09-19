package cn.pxl.app.ms.dao;

import cn.pxl.app.ms.entity.CompanyProductAuxEntity;

public interface ProductDao extends BaseDao<CompanyProductAuxEntity> {

	boolean addProductField(CompanyProductAuxEntity companyProductAuxEntity);

	CompanyProductAuxEntity getProductTableByName(String name);

	boolean deleteProductField(String name);

	boolean updateProductField(String oldField, CompanyProductAuxEntity getCompanyProductAuxEntity);
	
	
}
