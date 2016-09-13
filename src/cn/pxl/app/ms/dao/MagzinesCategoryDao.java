package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.MagzinesCategoryEntity;

public interface MagzinesCategoryDao extends BaseDao<MagzinesCategoryEntity> {
	public List<MagzinesCategoryEntity> pagingList(int offset, int limit);

	public long countForPagingList();

	public void deleteMagCategoryList(String[] categoryIdStrings);
}
