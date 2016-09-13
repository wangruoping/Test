package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.PrintEntity;

public interface PrintDao extends BaseDao<PrintEntity>{

	long countForPagingList();

	List<PrintEntity> pagingList(int offset, int limit);

}
