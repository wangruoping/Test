package cn.pxl.app.ms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.pxl.app.ms.dto.ColumnDto;
import cn.pxl.app.ms.dto.CompanyProductAuxDto;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.ResultDto;
import cn.pxl.app.ms.entity.CompanyProductAuxEntity;
import cn.pxl.app.ms.form.CompanyProductAuxForm;
import cn.pxl.app.ms.form.CompanyProductForm;
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
	 * 获取商品表字段信息
	 * 
	 */
	@RequestMapping(value = "getProductTableInfo")
	public @ResponseBody String getProductTableInfo(HttpSession session,
			@RequestParam("id") String id) {
		ResultDto rd = new ResultDto();
		CompanyProductAuxEntity companyProductAuxEntity = productService.getProductTableInfo(id);
		if (companyProductAuxEntity != null) {
			CompanyProductAuxDto companyProductAuxDto = new CompanyProductAuxDto();
			companyProductAuxDto.setId(companyProductAuxEntity.getId());
			companyProductAuxDto.setName(companyProductAuxEntity.getName());
			companyProductAuxDto.setDisname(companyProductAuxEntity.getDisname());
			companyProductAuxDto.setDisindex(companyProductAuxEntity.getDisindex());
			companyProductAuxDto.setDisen(companyProductAuxEntity.getDisen());
			rd.setStatus("1");
			rd.setContent(companyProductAuxDto);
		} else {
			rd.setStatus("0");
		}
		return CommonUtils.convertResult(rd, session);
	}
	
	/**
     * 商品表字段删除
     * 
     * */
	@RequestMapping(value="deleteProductTableList")
    public @ResponseBody String deleteProductTableList(HttpSession session,
            @RequestParam("productTableIds") String productTableIds){
    	
    	ResultDto rd = new ResultDto();	
		String[] productTableIdStrings = productTableIds.split("@");
		if(productTableIdStrings.length > 0 && productService.deleteProductTableList(productTableIdStrings)){				
			rd.setStatus("1");
			rd.setContent("");
		}else{
			rd.setStatus("0");
			rd.setContent("");
		}
		return CommonUtils.convertResult(rd, session);
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
				boolean changeNameFlag = false;
				String oldField = getCompanyProductAuxEntity.getName();
				if(!getCompanyProductAuxEntity.getName().equals(companyProductAuxForm.getName())){
					CompanyProductAuxEntity companyProductAuxEntity = productService.getProductTableByName(companyProductAuxForm.getName());
					if(companyProductAuxEntity != null){
						rd.setStatus("3");
						rd.setContent("");
						return CommonUtils.convertResult(rd, session);
					}
					changeNameFlag = true;
				}
				getCompanyProductAuxEntity.setName(companyProductAuxForm.getName());
				getCompanyProductAuxEntity.setDisname(companyProductAuxForm.getDisname());
				getCompanyProductAuxEntity.setDisindex(companyProductAuxForm.getDisindex());
				getCompanyProductAuxEntity.setDisen(companyProductAuxForm.getDisen());
				if (productService.updateProductTable(getCompanyProductAuxEntity)) {
					
					//判断字段名称是否修改
					if(changeNameFlag){
						//更新商品表字段
						boolean addFlag = productService.updateProductField(oldField, getCompanyProductAuxEntity);
						if(addFlag){
							rd.setStatus("1");
							rd.setContent("");
						}else{
							//删除之前添加的数据 TODO
							rd.setStatus("0");
							rd.setContent("");
						}
					}else{
						rd.setStatus("1");
						rd.setContent("");
					}
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	/** 跳转到商品列表页 */
	@RequestMapping("/productDataInfo")
	public ModelAndView productDataInfo(HttpSession session) {
		logger.info("跳转到商品列表页面");
		Map<String, String> map = new HashMap<String, String>();
		return new ModelAndView("productDataInfo", map);
	}
	
	/**
	 * 获取商品表字段列表信息
	 * 
	 */
	@RequestMapping(value = "getAllProductTableInfo")
	public @ResponseBody String getAllProductTableInfo(HttpSession session) {
		ResultDto rd = new ResultDto();
		List<List<ColumnDto>> tmList = new ArrayList<List<ColumnDto>>();
		List<ColumnDto> columnDtos = new ArrayList<ColumnDto>();
		List<CompanyProductAuxEntity> companyProductAuxEntities= productService.getAllProductTable();
		for (int i = 0; i < companyProductAuxEntities.size(); i++) {
			CompanyProductAuxEntity gProductAuxEntity = companyProductAuxEntities.get(i);
			ColumnDto columnDto = new ColumnDto();
			columnDto.setField(gProductAuxEntity.getName());
			columnDto.setAlign("center");
			columnDto.setTitle(gProductAuxEntity.getDisname());
			columnDto.setWidth(100);
			columnDto.setEditor("text");
			columnDtos.add(columnDto);
		}
		tmList.add(columnDtos);
		rd.setStatus("1");
		rd.setContent(tmList);
		return CommonUtils.convertResult(rd, session);
	}
	
	/**
	 * 商品信息添加/修改
	 * 
	 */
	@RequestMapping(value = "saveProductInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveProductInfo(HttpSession session,
			@ModelAttribute("CompanyProductForm") CompanyProductForm companyProductForm) {
		ResultDto rd = new ResultDto();
		if(companyProductForm.getIsNewRecord() == null){
			//更新
		}else{
			//添加
		}
		
		rd.setStatus("1");
		rd.setContent("");
		return CommonUtils.convertResult(rd, session);
	}
		

}
