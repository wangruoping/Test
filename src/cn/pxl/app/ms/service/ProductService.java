package cn.pxl.app.ms.service;

import cn.pxl.app.ms.dto.CompanyProductAuxDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.entity.CompanyProductAuxEntity;

public interface ProductService {

	PagingDto<CompanyProductAuxDto> getAllList();

	CompanyProductAuxEntity getProductTableByName(String name);

	boolean addProductAux(CompanyProductAuxEntity companyProductAuxEntity);

	CompanyProductAuxEntity getProductTableInfo(String id);

	boolean updateProductTable(CompanyProductAuxEntity companyProductAuxEntity);

	boolean addProductField(CompanyProductAuxEntity companyProductAuxEntity);

}
