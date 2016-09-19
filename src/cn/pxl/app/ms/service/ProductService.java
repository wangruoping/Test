package cn.pxl.app.ms.service;

import java.util.List;

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

	boolean deleteProductTableList(String[] productTableIdStrings);

	boolean updateProductField(String oldField, CompanyProductAuxEntity getCompanyProductAuxEntity);

	List<CompanyProductAuxEntity> getAllProductTable();

}
