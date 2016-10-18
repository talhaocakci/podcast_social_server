package com.podcastmodern.dao;

import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.io.Serializable;

@Stateless
public class GenericDao implements Serializable {

    @PersistenceUnit(unitName = "podcastmodern")
    EntityManagerFactory entityManagerFactory;


    public void save(Object o) {

        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();


    }

    public Object get(Class c, Serializable key) {

        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());

        Object o = session.get(c, key);

        return o;


    }


}
