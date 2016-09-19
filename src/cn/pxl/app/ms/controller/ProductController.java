package cn.pxl.app.ms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.pxl.app.ms.dto.CompanyProductAuxDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.ResultDto;
import cn.pxl.app.ms.entity.CompanyProductAuxEntity;
import cn.pxl.app.ms.form.CompanyProductAuxForm;
import cn.pxl.app.ms.service.ProductService;
import cn.pxl.app.ms.util.CommonUtils;

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
	
	/**
	 * 商品表字段添加/修改
	 * 
	 */
	@RequestMapping(value = "productTableInfoHanlde", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String productTableInfoHanlde(HttpSession session,
			@ModelAttribute("CompanyProductAuxForm") CompanyProductAuxForm companyProductAuxForm) {
		
		ResultDto rd = new ResultDto();
		if (companyProductAuxForm.getId() != null && !"".equals(companyProductAuxForm.getId())) {
			// 更新
			CompanyProductAuxEntity getCompanyProductAuxEntity = productService
					.getProductTableInfo(companyProductAuxForm.getId());
			if (getCompanyProductAuxEntity != null) {
				//判断字段名是否存在
				CompanyProductAuxEntity companyProductAuxEntity = productService.getProductTableByName(companyProductAuxForm.getName());
				if(companyProductAuxEntity != null){
					rd.setStatus("3");
					rd.setContent("");
					return CommonUtils.convertResult(rd, session);
				}
				getCompanyProductAuxEntity.setName(companyProductAuxForm.getName());
				getCompanyProductAuxEntity.setDisname(companyProductAuxForm.getDisname());
				getCompanyProductAuxEntity.setDisindex(companyProductAuxForm.getDisindex());
				getCompanyProductAuxEntity.setDisen(companyProductAuxForm.getDisen());
				if (productService.updateProductTable(getCompanyProductAuxEntity)) {
					
					//更新商品表字段
					
					rd.setStatus("1");
					rd.setContent("");
				} else {
					rd.setStatus("0");
					rd.setContent("");
				}
			} else {
				rd.setStatus("2");
				rd.setContent("");
			}
		} else {
			//判断字段名是否存在
			CompanyProductAuxEntity getCompanyProductAuxEntity = productService.getProductTableByName(companyProductAuxForm.getName());
			if(getCompanyProductAuxEntity != null){
				rd.setStatus("3");
				rd.setContent("");
				return CommonUtils.convertResult(rd, session);
			}
			// 添加
			CompanyProductAuxEntity companyProductAuxEntity = new CompanyProductAuxEntity();
			companyProductAuxEntity.setName(companyProductAuxForm.getName());
			companyProductAuxEntity.setDisname(companyProductAuxForm.getDisname());
			companyProductAuxEntity.setDisindex(companyProductAuxForm.getDisindex());
			companyProductAuxEntity.setDisen(companyProductAuxForm.getDisen());
			if (productService.addProductAux(companyProductAuxEntity)) {
				
				//添加商品表字段
				boolean addFlag = productService.addProductField(companyProductAuxEntity);
				if(addFlag){
					rd.setStatus("1");
					rd.setContent("");
				}else{
					//删除之前添加的数据 TODO
				}
				
			} else {
				rd.setStatus("0");
				rd.setContent("");
			}
		}
		return CommonUtils.convertResult(rd, session);
	}

}
