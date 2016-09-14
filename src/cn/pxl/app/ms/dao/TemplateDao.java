package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.CompanyTemplateEntity;

public interface TemplateDao extends BaseDao<CompanyTemplateEntity> {
	
	/**
	 * 分页用的两个方法
	 * 
	 * */
	long countForPagingList();
	List<CompanyTemplateEntity> pagingList(int offset, int limit);
	
	/**
	 * 根据模板名获取模板信息
	 * 
	 * @param username 模板名
	 * @return 模板信息
	 * */
	CompanyTemplateEntity getTemplateInfoByName(String templateName);
}
