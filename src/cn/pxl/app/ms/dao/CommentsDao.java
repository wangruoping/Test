package cn.pxl.app.ms.dao;

import java.util.List;

import cn.pxl.app.ms.entity.CommentsEntity;

public interface CommentsDao extends BaseDao<CommentsEntity> {
	List<CommentsEntity> getCommentsList(String magId,String commentDate);
}
