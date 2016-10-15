package com.podcastmodern.dao;

import com.podcastmodern.entity.Application;
import com.podcastmodern.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortMeta;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Map;

@Stateless
@LocalBean
public class ApplicationDao {


    @PersistenceUnit(unitName = "podcastmodern")
    EntityManagerFactory entityManagerFactory;


    public void save(Application o) {
        System.out.println("---------------" + o);
        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());

        session.merge(o);
        session.close();

    }

    public Application findApplicationById(int id) {
        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
        Criteria criteria = session.createCriteria(Application.class);
        criteria.add(Restrictions.eq("applicationId", id));
        Application app = (Application) criteria.uniqueResult();

        return app;

    }

    public List<User> findusersOfApp(int appId) {
        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
        return session.createQuery("select u from User as u inner join u.applicationUsers as au where au.id" +
            ".applicationId = " + appId).list();

    }


    public List<Application> findAll() {
        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
        Criteria criteria = session.createCriteria(Application.class);

        List<Application> list = criteria.list();
        session.close();
        return list;

    }

    public List<Application> findAllByCriteria(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String,
        Object> filters) {
        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
        Criteria criteria = session.createCriteria(Application.class);
        criteria.setFirstResult(first);
        criteria.setMaxResults(pageSize);

        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            criteria.add(Restrictions.ilike(entry.getKey(), entry.getValue() + "%"));
        }


        List<Application> list = criteria.list();
        session.close();

        return list;

    }

    public Integer findCountByCriteria(List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        Session session = (Session) (entityManagerFactory.createEntityManager().getDelegate());
        Criteria criteria = session.createCriteria(Application.class);
        return ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();


    }

}
