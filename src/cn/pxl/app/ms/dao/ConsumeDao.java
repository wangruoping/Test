package cn.pxl.app.ms.dao;

import cn.pxl.app.ms.entity.ConsumeEntity;

public interface ConsumeDao extends BaseDao<ConsumeEntity> {

	boolean findUserBuyPro(String userid, String magId);

}
