package cn.pxl.app.ms.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.pxl.app.ms.dao.CommentsDao;
import cn.pxl.app.ms.entity.CommentsEntity;

@Repository("commentsDao")
public class CommentsDaoImpl extends BaseDaoImpl<CommentsEntity> implements CommentsDao {

	@Override
	public List<CommentsEntity> findAll() {
		return null;
	}

	@Override
	public Object findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentsEntity> getCommentsList(String magId,String commentDate) {
		
		Date date = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(!"".equals(commentDate)){
				date = sdf.parse(commentDate);
			}else{
				date = sdf.parse("0000-00-00 00:00:00");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from CommentsEntity where commentMagzinesId = ? and commentTime > ? order by commentTime desc")
				.setParameter(0, magId).setParameter(1, date);
		@SuppressWarnings("unchecked")
		List<CommentsEntity> result = query.list();
		session.close();
		return result;
	}

}
