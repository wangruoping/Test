package cn.pxl.app.ms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.ResultDto;
import cn.pxl.app.ms.dto.TemplateDto;
import cn.pxl.app.ms.entity.CompanyTemplateEntity;
import cn.pxl.app.ms.form.CompanyTemplateForm;
import cn.pxl.app.ms.service.TemplateService;
import cn.pxl.app.ms.util.CommonUtils;

@Controller
public class TemplateController {

	private Logger logger = Logger.getLogger(TemplateController.class);

	@Resource(name = "templateService")
	private TemplateService templateService;

	/** 跳转到模板管理页 */
	@RequestMapping("/templatesInfo")
	public ModelAndView usersInfo(HttpSession session) {
		logger.info("跳转到模板管理页面");
		Map<String, String> map = new HashMap<String, String>();
		return new ModelAndView("templateInfo", map);
	}

	/**
	 * 模板列表
	 * 
	 */
	@RequestMapping(value = "templateList")
	public @ResponseBody PagingDto<TemplateDto> userList(@RequestParam("page") String page,
			@RequestParam("rows") String rows) {
		logger.info("获取模板分页列表");
		int offset = Integer.parseInt(page);
		int limit = Integer.parseInt(rows);

		return templateService.pagingList(offset, limit, 0);
	}

	/**
	 * 获取模板信息
	 * 
	 */
	@RequestMapping(value = "getTemplateInfo")
	public @ResponseBody String getTemplateInfo(HttpSession session,
			@RequestParam("templateid") String templateid) {
		ResultDto rd = new ResultDto();
		CompanyTemplateEntity companyTemplateEntity = templateService.getTemplateInfo(templateid);
		if (companyTemplateEntity != null) {
			TemplateDto templateDto = new TemplateDto();
			templateDto.setId(companyTemplateEntity.getId());
			templateDto.setName(companyTemplateEntity.getName());
			templateDto.setWidth(companyTemplateEntity.getWidth());
			templateDto.setHeight(companyTemplateEntity.getHeight());
			rd.setStatus("1");
			rd.setContent(templateDto);
		} else {
			rd.setStatus("0");
		}
		return CommonUtils.convertResult(rd, session);
	}
	
	/**
	 * 获取模板信息
	 * 
	 */
	@RequestMapping(value = "getTemplateDetailInfo")
	public @ResponseBody String getTemplateDetailInfo(HttpSession session,
			@RequestParam("templateid") String templateid) {
		ResultDto rd = new ResultDto();
		CompanyTemplateEntity companyTemplateEntity = templateService.getTemplateInfo(templateid);
		if (companyTemplateEntity != null) {
			TemplateDto templateDto = new TemplateDto();
			templateDto.setId(companyTemplateEntity.getId());
			templateDto.setName(companyTemplateEntity.getName());
			templateDto.setWidth(companyTemplateEntity.getWidth());
			templateDto.setHeight(companyTemplateEntity.getHeight());
			rd.setStatus("1");
			rd.setContent(templateDto);
		} else {
			rd.setStatus("0");
		}
		return CommonUtils.convertResult(rd, session);
	}
	
	/**
     * 用户信息删除
     * 
     * */
    @RequestMapping(value="deleteTemplateList")
    public @ResponseBody String deleteTemplateList(HttpSession session,
            @RequestParam("templateIds") String templateIds){
    	
    	ResultDto rd = new ResultDto();	
		String[] templateIdStrings = templateIds.split("@");
		if(templateIdStrings.length > 0 && templateService.deleteTemplateList(templateIdStrings)){				
			rd.setStatus("1");
			rd.setContent("");
		}else{
			rd.setStatus("0");
			rd.setContent("");
		}
		return CommonUtils.convertResult(rd, session);
    }

	/**
	 * 模板信息添加/修改
	 * 
	 */
	@RequestMapping(value = "templateInfoHanlde", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String templateInfoHanlde(HttpSession session,
			@ModelAttribute("companyTemplateForm") CompanyTemplateForm companyTemplateForm) {
		
		ResultDto rd = new ResultDto();
		if (!StringUtils.isEmpty(companyTemplateForm.getId())) {
			// 更新
			CompanyTemplateEntity companyTemplateEntity = templateService
					.getTemplateInfo(companyTemplateForm.getId());
			if (companyTemplateEntity != null) {
				//判断模板名是否存在
				if(!companyTemplateEntity.getName().equals(companyTemplateForm.getName())){
					CompanyTemplateEntity getCompanyTemplateEntity = templateService.getTemplateInfoByName(companyTemplateForm.getName());
					if(getCompanyTemplateEntity != null){
						rd.setStatus("3");
						rd.setContent("");
						return CommonUtils.convertResult(rd, session);
					}
				}
				companyTemplateEntity.setName(companyTemplateForm.getName());
				companyTemplateEntity.setWidth(companyTemplateForm.getWidth());
				companyTemplateEntity.setHeight(companyTemplateForm.getHeight());
				if (templateService.updateTemplate(companyTemplateEntity)) {
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
			//判断模板名是否存在
			CompanyTemplateEntity getCompanyTemplateEntity = templateService.getTemplateInfoByName(companyTemplateForm.getName());
			if(getCompanyTemplateEntity != null){
				rd.setStatus("3");
				rd.setContent("");
				return CommonUtils.convertResult(rd, session);
			}
			// 添加
			CompanyTemplateEntity companyTemplateEntity = new CompanyTemplateEntity();
			companyTemplateEntity.setName(companyTemplateForm.getName());
			companyTemplateEntity.setWidth(companyTemplateForm.getWidth());
			companyTemplateEntity.setHeight(companyTemplateForm.getHeight());
			if (templateService.addTemplate(companyTemplateEntity)) {
				rd.setStatus("1");
				rd.setContent("");
			} else {
				rd.setStatus("0");
				rd.setContent("");
			}
		}
		return CommonUtils.convertResult(rd, session);
	}

}
