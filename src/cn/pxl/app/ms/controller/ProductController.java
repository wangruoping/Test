package cn.pxl.app.ms.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import cn.pxl.app.ms.service.ProductService;

@Controller
public class ProductController {

	private Logger logger = Logger.getLogger(ProductController.class);

	@Resource(name = "productService")
	private ProductService productService;

	

}
