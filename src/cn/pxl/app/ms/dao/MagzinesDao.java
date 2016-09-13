package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.MagzinesEntity;

public interface MagzinesDao extends BaseDao<MagzinesEntity> {
	List<MagzinesEntity> pagingList(int offset, int limit, int setType);
	long countForPagingList(int setType);
	List<MagzinesEntity> getFriendMagList(String userid);
	Integer getMagZfListCount(String magId);
	List<MagzinesEntity> getUserMagList(String userid, Integer pageIndex);
	List<MagzinesEntity> getFriendMagDetailList(String userid, Integer pageIndex, Integer pageSize);
	void deleteMagProductList(String[] productIdsStrings);
	List<MagzinesEntity> getMagProductList(String categoryId, int top);
	List<MagzinesEntity> getAllMagProductList(int orderType, String searchText);
	List<String> getHotMagProductList(int top);
	String getUserMagCount(String userid);
	List<MagzinesEntity> getUserMagAllList(String userid);
	List<MagzinesEntity> getShopMagDetailList(String userid, String searchText,
			int parseInt, int pageSize);
	List<MagzinesEntity> getMagDetailProductList(String categoryId,
			String pageIndex);
}
