package cn.pxl.app.ms.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.pxl.app.ms.dao.TemplateDao;
import cn.pxl.app.ms.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Resource(name="templateDao")
	private TemplateDao templateDao;

}
