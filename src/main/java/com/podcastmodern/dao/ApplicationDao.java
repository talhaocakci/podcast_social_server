package com.podcastmodern.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.podcastmodern.entity.Application;
import com.podcastmodern.entity.User;

@Stateless
@LocalBean
public class ApplicationDao {
	
	@PersistenceUnit(unitName = "podcastmodern")
	EntityManagerFactory entityManagerFactory;
	
	
	public void save(Application o){
		
		Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
		session.beginTransaction();
		session.save(o);
		session.getTransaction().commit();
		session.close();
		
	}
	
	public Application findApplicationById(int id){
		Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
		Criteria criteria = session.createCriteria(Application.class);
		criteria.add(Restrictions.eq("applicationId", id));
		Application app =  (Application) criteria.uniqueResult();
		
		return app;
		
	}
	
	public List<User> findusersOfApp(int appId){
		Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
		return session.createQuery("select u from User as u inner join u.applicationUsers as au where au.id.applicationId = " + appId).list();
		
	}
	

	public List<Application> findAll(){
		Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
		Criteria criteria = session.createCriteria(Application.class);
	
		return criteria.list();
		
	
		
	}
	
	
	
}
