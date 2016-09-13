package cn.pxl.app.ms.dao;

import cn.pxl.app.ms.entity.DownloadEntity;

public interface DownloadDao extends BaseDao<DownloadEntity> {

	boolean findByMagIdAndUserId(String magId, String userid);

	

}
