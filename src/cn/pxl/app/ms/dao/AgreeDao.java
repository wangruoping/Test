package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.AgreeEntity;

public interface AgreeDao extends BaseDao<AgreeEntity>{
	public AgreeEntity findAgreeEntity(String magId,String userId);
	public List<AgreeEntity> findAgreeEntity(String magId);
}
