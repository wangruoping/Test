package cn.pxl.app.ms.service;

import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.TemplateDto;
import cn.pxl.app.ms.entity.CompanyTemplateEntity;

public interface TemplateService {

	PagingDto<TemplateDto> pagingList(int offset, int limit, int i);

	CompanyTemplateEntity getTemplateInfo(String templateid);
	
	boolean addTemplate(CompanyTemplateEntity templateEntity);

	boolean updateTemplate(CompanyTemplateEntity templateEntity);

	boolean deleteTemplateList(String[] templateIdStrings);

	CompanyTemplateEntity getTemplateInfoByName(String templatename);
}
