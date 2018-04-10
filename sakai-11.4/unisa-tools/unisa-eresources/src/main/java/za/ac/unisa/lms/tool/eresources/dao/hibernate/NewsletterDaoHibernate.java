package za.ac.unisa.lms.tool.eresources.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import za.ac.unisa.lms.tool.eresources.dao.NewsletterDao;
import za.ac.unisa.lms.tool.eresources.model.Newsletter;

/**
 * @author lebelsg
 * @author tmasibm
 */
@Repository("newsletterDao")
public class NewsletterDaoHibernate extends GenericDaoHibernate<Newsletter, Long> implements NewsletterDao {

	/**
	 * @param persistentClass
	 */
	public NewsletterDaoHibernate() {
		super(Newsletter.class);
	}

	
	/**
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public NewsletterDaoHibernate(Class<Newsletter> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
	}


	private Logger log;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		log.debug("Session Factory Failed to load");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Newsletter> findNewsById(Long newsletterId) {
		List<Newsletter> listNewsById = new ArrayList<Newsletter>();
		 
		 try{
			 listNewsById = getSession()
				.createCriteria(Newsletter.class)
				.add(Restrictions.eq("newsTitleID", newsletterId))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listNewsById;
		 		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Newsletter> findNewsTitleByDesc(String newsDesc) {
		List<Newsletter> listNewsByDesc = new ArrayList<Newsletter>();
		 
		 try{
			 listNewsByDesc = getSession()
				.createCriteria(Newsletter.class)
				.add(Restrictions.eq("newsTitle", newsDesc))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listNewsByDesc;
 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Newsletter> findAll() {
		List<Newsletter> listAllNewsletters = new ArrayList<Newsletter>();
		 
		 try{
			 listAllNewsletters = getSession()
				.createCriteria(Newsletter.class)
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllNewsletters;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Newsletter> selectNewsTitleLinked(Long resourceId, Long newsletterId) {
		List<Newsletter> listLinkedNewsletters = new ArrayList<Newsletter>();
		 
		 try{
			 listLinkedNewsletters = getSession()
				.createCriteria(Newsletter.class)
				.add(Restrictions.eq("eresource", resourceId))
				.add(Restrictions.eq("newsTitleID", newsletterId))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listLinkedNewsletters; 
	}

	
	@Override
	public boolean newsTitleInUse(Long newsletterId, boolean isEnabled) {
		if(isEnabled = false)		
			try{
				getSession()
				.createCriteria(Newsletter.class)
				.add(Restrictions.eq("newsTitleID", newsletterId))
				.add(Restrictions.eq("isEnabled", true))
				.list();
			
	        }catch (Exception e) {
	        	log.debug("This highlightInUse is already linked to the Eresource "+e);
	        }finally{
	        	
	        }
			return true;
	}

	@Override
	public void addNewsTitle(Newsletter newsTitle) {
		 try{
	        	newsTitle.getNewsTitleID();
	        	getSession().save(newsTitle);
	        }catch (Exception e) {
	            log.debug("Unable to add fields to the database, please check the code and correct the errors "+e);
	        }finally{
	        	
	        }
		
	}


	@Override
	public void updateNewsTitle(Newsletter newsTitle) {
		try{
			newsTitle.getNewsTitleID();
			getSession().update(newsTitle);
        }catch (Exception e) {
            log.debug("Unable to update the Placement ranking table "+e);
        }finally{
        	
        }
		
	}

	@Override
	public void deleteNewsTitle(Long newsletterId, boolean inUse) {
		if (inUse = false);
		 try{
			 getSession().createCriteria(Newsletter.class);
	         getSession().delete(newsletterId);

	        }catch (Exception e) {

	            log.debug("The program is unable to delete the records from the detabase "+e);
	        }finally{
	        	 
	        }
		 	
		if( inUse = true);
		log.debug("The newsletter you are trying to delete is currently linked to a database resource");
		
	}

}
