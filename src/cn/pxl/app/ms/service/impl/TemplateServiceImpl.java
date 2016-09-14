package cn.pxl.app.ms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.pxl.app.ms.dao.TemplateDao;
import cn.pxl.app.ms.dto.PagingDto;
import cn.pxl.app.ms.dto.TemplateDto;
import cn.pxl.app.ms.entity.CompanyTemplateEntity;
import cn.pxl.app.ms.service.TemplateService;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

	@Resource(name="templateDao")
	private TemplateDao templateDao;

	@Override
	public PagingDto<TemplateDto> pagingList(int offset, int limit, int i) {
		PagingDto<TemplateDto> result = new PagingDto<TemplateDto>();

        List<TemplateDto> resultList = new ArrayList<TemplateDto>();

        result.setTotal(templateDao.countForPagingList());

        if (result.getTotal() > 0) {
            List<CompanyTemplateEntity> list = templateDao.pagingList(offset, limit);
            for (CompanyTemplateEntity ue : list) {
            	TemplateDto dto = new TemplateDto();
            	dto.setId(ue.getId());
            	dto.setName(ue.getName());
            	dto.setWidth(ue.getWidth());
            	dto.setHeight(ue.getHeight());
                resultList.add(dto);
            }
        }
        result.setRows(resultList);
        return result;
	}

	@Override
	public boolean addTemplate(CompanyTemplateEntity templateEntity) {
		templateDao.save(templateEntity);
		return true;
	}

	@Override
	public boolean updateTemplate(CompanyTemplateEntity templateEntity) {
		templateDao.update(templateEntity);
		return true;
	}

	@Override
	public boolean deleteTemplateList(String[] templateIdStrings) {
		for (int i = 0; i < templateIdStrings.length; i++) {
			CompanyTemplateEntity companyTemplateEntity = new CompanyTemplateEntity();
			companyTemplateEntity.setId(templateIdStrings[i]);
			templateDao.delete(companyTemplateEntity);
			
			//TODO 删除模板的附表数据
		}
		return true;
	}

	@Override
	public CompanyTemplateEntity getTemplateInfoByName(String templatename) {
		return templateDao.getTemplateInfoByName(templatename);
	}

	@Override
	public CompanyTemplateEntity getTemplateInfo(String templateid) {
		return (CompanyTemplateEntity) templateDao.findById(templateid);
	}
}
