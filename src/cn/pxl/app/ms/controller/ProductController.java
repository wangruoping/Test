package cn.pxl.app.ms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.pxl.app.ms.dto.CompanyProductAuxDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.service.ProductService;

@Controller
public class ProductController {

	private Logger logger = Logger.getLogger(ProductController.class);

	@Resource(name = "productService")
	private ProductService productService;

	/** 跳转到商品表管理页 */
	@RequestMapping("/productTableInfo")
	public ModelAndView productTableInfo(HttpSession session) {
		logger.info("跳转到商品表管理页面");
		Map<String, String> map = new HashMap<String, String>();
		return new ModelAndView("productTableInfo", map);
	}
	
	/**
	 * 商品表字段列表
	 */
	@RequestMapping(value = "productTableList")
	public @ResponseBody PagingDto<CompanyProductAuxDto> productTableList() {
		logger.info("获取商品表字段列表");
		return productService.getAllList();
	}

}
