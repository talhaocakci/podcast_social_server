package com.podcastmodern.entity;
// Generated Oct 20, 2015 2:32:43 PM by Hibernate Tools 4.0.0

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Map;

/**
 * Home object for domain model class Customer.
 *
 * @author Hibernate Tools
 * @see com.entity.Customer
 */
@Stateless
public class CustomerHome {

    private static final Log log = LogFactory.getLog(CustomerHome.class);

    @PersistenceUnit
    private EntityManagerFactory entityManager;

    public void persist(Customer transientInstance) {
        log.debug("persisting Customer instance");
        try {
            entityManager.createEntityManager().persist(transientInstance);
            log.debug("persist successful");
        } catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }

    public void remove(Customer persistentInstance) {
        log.debug("removing Customer instance");
        try {
            entityManager.createEntityManager().remove(persistentInstance);
            log.debug("remove successful");
        } catch (RuntimeException re) {
            log.error("remove failed", re);
            throw re;
        }
    }

    public Customer merge(Customer detachedInstance) {
        log.debug("merging Customer instance");
        try {
            Customer result = entityManager.createEntityManager().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }


    public Customer findByUsernameAndPassword(String userName, String password) {

        Session session = (Session) entityManager.createEntityManager().getDelegate();
        Criteria criteria = session.createCriteria(Customer.class);
        criteria.add(Restrictions.eq("userName", userName));
        criteria.add(Restrictions.eq("password", password));
        Customer c = (Customer) criteria.uniqueResult();
        return c;

    }

    public Customer findById(Integer id) {
        log.debug("getting Customer instance with id: " + id);
        try {
            Customer instance = entityManager.createEntityManager().find(Customer.class, id);
            log.debug("get successful");
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public List<Customer> findAll(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object>
        filters) {

        try {
            Session session = (Session) (entityManager.createEntityManager().getDelegate());
            Criteria cr = session.createCriteria(Customer.class);
            cr.setFirstResult(first);
            cr.setMaxResults(pageSize);

            //cr.add(Restrictions.eqOrIsNull("name", ""));
            List<Customer> results = cr.list();
            session.close();
            return results;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public Long findCount(Map<String, Object> filters) {

        try {
            Session session = (Session) (entityManager.createEntityManager().getDelegate());
            Criteria cr = session.createCriteria(Customer.class);
            Long count = (Long) cr.setProjection(Projections.rowCount()).uniqueResult();
            session.close();
            return count;

        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}
