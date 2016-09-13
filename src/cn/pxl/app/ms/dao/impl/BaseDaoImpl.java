package cn.pxl.app.ms.dao.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import cn.pxl.app.ms.dao.BaseDao;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	@Resource(name="sessionFactory")
	protected SessionFactory sessionFactory;

	@Override
	public Serializable save(T o) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Serializable result = session.save(o);
		transaction.commit();
		session.close();
		return result;
	}

	@Override
	public void delete(T o) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(o);
		transaction.commit();
		session.close();
	}

	@Override
	public void update(T o) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(o);
		transaction.commit();
		session.close();
	}

	@Override
	public void saveOrUpdate(T o) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.merge(o);
		transaction.commit();
		session.close();
	}
}
