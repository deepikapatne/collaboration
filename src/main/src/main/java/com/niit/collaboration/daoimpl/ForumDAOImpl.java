package com.niit.collaboration.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.dao.ForumDAO;
import com.niit.collaboration.model.Forum;

@Repository("ForumDAO")
@EnableTransactionManagement
public class ForumDAOImpl implements ForumDAO{

	@Autowired
	SessionFactory sessionFactory;

	public ForumDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

	@Transactional
	public boolean save(Forum forum) {
		sessionFactory.getCurrentSession().saveOrUpdate(forum);
		return false;
	}

	@Transactional
	public List<Forum> list() {
			String hql = "from Forum";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			return query.list();
		}

	@Transactional
	public boolean delete(int id) {
	 Forum forum = new Forum();
	 forum.setId(id);
	 sessionFactory.getCurrentSession().delete(forum);
	return false;
	}

	@Transactional
	public boolean update(Forum forum) {
		sessionFactory.getCurrentSession().update(forum);
		return false;
	}

}
