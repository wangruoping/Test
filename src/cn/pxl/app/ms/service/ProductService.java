package cn.pxl.app.ms.service;

import cn.pxl.app.ms.dto.CompanyProductAuxDto;
import cn.pxl.app.ms.dto.PagingDto;

public interface ProductService {

	PagingDto<CompanyProductAuxDto> getAllList();

}
