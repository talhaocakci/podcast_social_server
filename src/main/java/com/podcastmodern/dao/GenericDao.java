package com.podcastmodern.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.hibernate.Session;

@Stateless
public class GenericDao {
	
	@PersistenceUnit(unitName = "podcastmodern")
	EntityManagerFactory entityManagerFactory;
	
	
	public void save(Object o){
		
		Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
		session.beginTransaction();
		session.save(o);
		session.getTransaction().commit();
		session.close();
		
	}
	
	public Object get(Class c, Serializable key){
		
		Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
		
		Object o =  session.get(c, key);
		session.close();getClass();
		return o;
		
		
	}

	
	
}
