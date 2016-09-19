package cn.pxl.app.ms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.pxl.app.ms.dao.ProductDao;
import cn.pxl.app.ms.dao.impl.UserDaoImpl;
import cn.pxl.app.ms.dto.CompanyProductAuxDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.entity.CompanyProductAuxEntity;
import cn.pxl.app.ms.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	Logger log = Logger.getLogger(UserDaoImpl.class);
	
	@Resource(name="productDao")
	private ProductDao productDao;

	@Override
	public PagingDto<CompanyProductAuxDto> getAllList() {
		
		PagingDto<CompanyProductAuxDto> result = new PagingDto<CompanyProductAuxDto>();

        List<CompanyProductAuxDto> resultList = new ArrayList<CompanyProductAuxDto>();

        if (result.getTotal() > 0) {
            List<CompanyProductAuxEntity> list = productDao.findAll();
            for (CompanyProductAuxEntity ue : list) {
            	CompanyProductAuxDto companyProductAuxDto = new CompanyProductAuxDto();
            	companyProductAuxDto.setId(ue.getId());
            	companyProductAuxDto.setName(ue.getName());
            	companyProductAuxDto.setDisname(ue.getDisname());
            	companyProductAuxDto.setDisen(ue.getDisen());
            	companyProductAuxDto.setDisindex(ue.getDisindex());
                resultList.add(companyProductAuxDto);
            }
        }
        result.setRows(resultList);
        result.setTotal(resultList.size());
        return result;
	}

	@Override
	public CompanyProductAuxEntity getProductTableByName(String name) {
		return productDao.getProductTableByName(name);
	}

	@Override
	public boolean addProductAux(CompanyProductAuxEntity companyProductAuxEntity) {
		productDao.save(companyProductAuxEntity);
		return true;
	}

	@Override
	public CompanyProductAuxEntity getProductTableInfo(String id) {
		return (CompanyProductAuxEntity) productDao.findById(id);
	}

	@Override
	public boolean updateProductTable(CompanyProductAuxEntity companyProductAuxEntity) {
		productDao.update(companyProductAuxEntity);
		return true;
	}

	@Override
	public boolean addProductField(CompanyProductAuxEntity companyProductAuxEntity) {
		return productDao.addProductField(companyProductAuxEntity);
	}

}
