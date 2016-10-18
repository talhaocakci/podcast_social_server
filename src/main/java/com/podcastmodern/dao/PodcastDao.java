package com.podcastmodern.dao;

import com.podcastmodern.entity.Podcast;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.io.Serializable;
import java.util.List;

@Stateless
public class PodcastDao implements Serializable {


    @PersistenceUnit(unitName = "podcastmodern")
    EntityManagerFactory entityManagerFactory;


    public void save(Podcast o) {

        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();

    }

    public List<Podcast> getAllPodcasts() {

        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
        Criteria criteria = session.createCriteria(Podcast.class);
        return criteria.list();

    }


}
