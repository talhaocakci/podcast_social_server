package com.podcastmodern.genericclass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.podcastmodern.dao.DBQueryObject;



/**
 * 
 * @author Burak Talha OCAKCI
 * @version 0.1 <BR>
 *          REGULAR EXPRESSIONS MUST BE OPTIMIZED
 */


public class GenericDao extends HibernateDaoSupport {

	private Pattern paremeterPattern = Pattern.compile("\\s*:(\\S*)\\s*?");
	private Pattern clausePattern = Pattern.compile("\\S*\\s*\\S\\s*:(.*)\\s*");


	public GenericDao() {
	}

	public Serializable saveOrUpdate(Object ge) {
		Session s = null;
		try {

			s = getSessionFactory().openSession();

			s.beginTransaction();

			s.saveOrUpdate(ge); // or
		
			s.getTransaction().commit();		
		
			
			return s.getIdentifier(ge);
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return -1;
		}finally{
			s.close();
		}
	}
	
	public Object queryByHQL(String hqlQuery,DBQueryObject qObject) {
		ArrayList<Object> list = null;
		Object returnValue = null;
		try {
			Session session = getSessionFactory().openSession();
			
			Matcher matcher = paremeterPattern.matcher(hqlQuery);
			Matcher matcher2 = clausePattern.matcher(hqlQuery);

			String parameterName = "";
			String parameterValue = null;
			hqlQuery = hqlQuery.replaceFirst(" where ", " where 1=1 and ");
			while (matcher.find()) {
				parameterName = matcher.group(1);				
				parameterValue = qObject.getParameterValue(parameterName);
				
				// if the named parameter is assigned, it will be put into
				// the required place in the query
				if (parameterValue != null) {
					hqlQuery = hqlQuery.replaceFirst(":" + parameterName, qObject.getParameterValue(parameterName));
					
				}
				// otherwise whole relation will be canceled out
				else {

					hqlQuery = hqlQuery.replaceAll("(and)*\\s*\\S*\\s*\\S\\s*:"
							+ parameterName + "\\s*", " ");

				}

			}
			
			Query query = session.createQuery(hqlQuery);
			session.beginTransaction();
			list = (ArrayList<Object>) query.list();
			session.getTransaction().commit();
			if(list.size() > 0)
				returnValue = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnValue;

	}
	
	public <T extends Object> ArrayList<T> queryListByHQL(String hqlQuery,
			DBQueryObject qObject) {
		ArrayList<T> list = null;
		try {
			Session session = getSessionFactory().openSession();

			Matcher matcher = paremeterPattern.matcher(hqlQuery);
			Matcher matcher2 = clausePattern.matcher(hqlQuery);

			String parameterName = "";
			String parameterValue = null;
			hqlQuery = hqlQuery.replaceFirst(" where ", " where 1=1 and ");
			while (matcher.find()) {
				parameterName = matcher.group(1);				
				parameterValue = qObject.getParameterValue(parameterName);
				
				// if the named parameter is assigned, it will be put into
				// the required place in the query
				if (parameterValue != null) {
					hqlQuery = hqlQuery.replaceFirst(":" + parameterName, qObject.getParameterValue(parameterName));
					
				}
				// otherwise whole relation will be canceled out
				else {

					hqlQuery = hqlQuery.replaceAll("(and)*\\s*\\S*\\s*\\S\\s*:"
							+ parameterName + "\\s*", " ");

				}

			}

			Query query = session.createQuery(hqlQuery);
			session.beginTransaction();
			list = (ArrayList<T>) query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}
	

	public boolean executeByHQL(String hqlQuery, DBQueryObject qObject) {
		
		try {
			Session session = getSessionFactory().openSession();

			Query query = session.createQuery(hqlQuery);
			
			session.beginTransaction();
			for(Entry<String, Object> entry :qObject.getParameters())
			{
				query.setParameter(entry.getKey(), entry.getValue());
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;

	}
	
	public void deleteByHQL(String hqlQuery, DBQueryObject qObject){
		Session session = getSessionFactory().openSession();
	try {
			
			
			Matcher matcher = paremeterPattern.matcher(hqlQuery);
			Matcher matcher2 = clausePattern.matcher(hqlQuery);

			String parameterName = "";
			String parameterValue = null;
			hqlQuery = hqlQuery.replaceFirst(" where ", " where 1=1 and ");
			while (matcher.find()) {
				parameterName = matcher.group(1);				
				parameterValue = qObject.getParameterValue(parameterName);
				
				// if the named parameter is assigned, it will be put into
				// the required place in the query
				if (parameterValue != null) {
					hqlQuery = hqlQuery.replaceFirst(":" + parameterName, qObject.getParameterValue(parameterName));
					
				}
				// otherwise whole relation will be canceled out
				else {

					hqlQuery = hqlQuery.replaceAll("(and)*\\s*\\S*\\s*\\S\\s*:"
							+ parameterName + "\\s*", " ");

				}

			}
			
			Query query = session.createQuery(hqlQuery);			
			query.executeUpdate();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Object queryByHQL(Session session, String hqlQuery,DBQueryObject qObject) {
		ArrayList<Object> list = null;
		Object returnValue = null;
		try {
						
			Matcher matcher = paremeterPattern.matcher(hqlQuery);
			Matcher matcher2 = clausePattern.matcher(hqlQuery);

			String parameterName = "";
			String parameterValue = null;
			hqlQuery = hqlQuery.replaceFirst(" where ", " where 1=1 and ");
			while (matcher.find()) {
				parameterName = matcher.group(1);				
				parameterValue = qObject.getParameterValue(parameterName);
				
				// if the named parameter is assigned, it will be put into
				// the required place in the query
				if (parameterValue != null) {
					hqlQuery = hqlQuery.replaceFirst(":" + parameterName, qObject.getParameterValue(parameterName));
					
				}
				// otherwise whole relation will be canceled out
				else {

					hqlQuery = hqlQuery.replaceAll("(and)*\\s*\\S*\\s*\\S\\s*:"
							+ parameterName + "\\s*", " ");

				}

			}
			
			Query query = session.createQuery(hqlQuery);
			session.beginTransaction();
			list = (ArrayList<Object>) query.list();
			session.getTransaction().commit();
			returnValue = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnValue;

	}

	public void queryBySample(Object ge) {

		try {
			Session session = getSessionFactory().openSession();
			Criteria prdCrit = session.createCriteria(ge.getClass());
			session.beginTransaction();
			Example entityExample = Example.create(ge);
			prdCrit.add(entityExample);
			ArrayList<Object> list = (ArrayList<Object>) prdCrit
					.list();

			System.out.println(list);

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public Object queryById(Class className, Serializable id) {

		Object entity = null;
		try {
			Session session = getSessionFactory().openSession();


			entity = session.get(className,id);		


		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	public void queryListById() {

		try {
			Session session = getSessionFactory().openSession();
			session.beginTransaction();

			// ArrayList<GenericEntity> list =
			// (ArrayList<GenericEntity>)session.createCriteria(UserResponsibility.class).cre.add(Restrictions.eq("id",
			// 19)).list();
			// System.out.println(list.size());
			// Criteria criteria = session.createCriteria(className).ad;
			// ArrayList<GenericEntity> list =
			// (ArrayList<GenericEntity>)criteria.list();

			// System.out.println(list.toString());
			// System.out.println(sqlObjectHolder.getSqlByName("getY"));
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
