package cn.pxl.app.ms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.pxl.app.ms.dao.ProductDao;
import cn.pxl.app.ms.dao.TemplateDao;
import cn.pxl.app.ms.dto.CompanyProductAuxDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.UserDto;
import cn.pxl.app.ms.entity.CompanyProductAuxEntity;
import cn.pxl.app.ms.entity.CompanyUserEntity;
import cn.pxl.app.ms.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

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

}
