package com.podcastmodern.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.podcastmodern.entity.Podcast;

@Stateless
public class PodcastDao {
	
	
	@PersistenceUnit(unitName = "podcastmodern")
	EntityManagerFactory entityManagerFactory;
	
	
	public void save(Podcast o){
		
		Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
		session.beginTransaction();
		session.save(o);
		session.getTransaction().commit();
		session.close();
		
	}
	
	public List<Podcast> getAllPodcasts(){
		
		Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
		Criteria criteria = session.createCriteria(Podcast.class);
		return criteria.list();
		
	}
	
	
}
