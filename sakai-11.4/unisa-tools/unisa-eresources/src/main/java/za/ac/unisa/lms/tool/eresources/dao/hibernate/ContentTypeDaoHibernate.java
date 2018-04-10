/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.dao.hibernate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import za.ac.unisa.lms.tool.eresources.dao.ContentTypeDao;
import za.ac.unisa.lms.tool.eresources.model.ContentType;
/**
 * @author TMasibm
 *
 */
@Repository("contentTypeDao")
public class ContentTypeDaoHibernate  extends GenericDaoHibernate<ContentType, Long> implements ContentTypeDao  {
	
	public ContentTypeDaoHibernate() {
		super(ContentType.class);
	}
	
	/**
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public ContentTypeDaoHibernate(Class<ContentType> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
	
	}

	private Logger log;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		log.debug("Session Factory Failed to load");
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<ContentType> findAll(){
	
		List<ContentType> listAllContentTypes = new ArrayList<ContentType>();
		 
		 try{
			 listAllContentTypes = getSession()
			  .createCriteria(ContentType.class)
	          .list(); 
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllContentTypes;       
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContentType> findContentTypeById(Long libTxtId) {

		List<ContentType> listAllContentTypesById = new ArrayList<ContentType>();
		 try{
		
			 listAllContentTypesById = getSession()
				.createCriteria(ContentType.class)
				.add(Restrictions.eq("libTxtID", libTxtId))
				.list(); 
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllContentTypesById;
	}
	// Getting a single value
	public ContentType getContentTypeById(Long libTxtId){
		
			String query = "from ContentType where id=:libTxtId";
			
			Query q = getSession().createQuery(query)
			.setLong("libTxtId", 1);
			ContentType ct = (ContentType) q.uniqueResult();
			
			return ct;
			
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ContentType> selectContentTypeDesc(String textDescr){
		
		List<ContentType> listAllContentTypesByDescr = new ArrayList<ContentType>();
		 
		 try{
			 listAllContentTypesByDescr = getSession()
				.createCriteria(ContentType.class)
				.add(Restrictions.eq("fullTxtDescr", textDescr))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		 return listAllContentTypesByDescr;
			
	}


	@Override
	public boolean contentTypeInUse(Long txtId, boolean isEnabled) {
		if(isEnabled = false)		
		try{
			getSession()
			.createCriteria(ContentType.class)
			.add(Restrictions.eq("libTxtID", txtId))
			.add(Restrictions.eq("isEnabled", true))
			.list();
		
        }catch (Exception e) {
            log.debug("Unable to update the contentType");
        }finally{
        	
        }
		return true;
	}


	@Override
	public void addContentType(ContentType contentType){

	        try{
	        	contentType.getLibTxtID();
	        	getSession().save(contentType);
	        	
	        }catch (Exception e) {
	            log.debug("Unable to add fields to the database, please check the code and correct the errors "+e);
	        }finally{
	        	
	        }
	}



	@Override
	public void updateContentType(ContentType contentType) {
		try{
			contentType.getLibTxtID();
			getSession().update(contentType);
        }catch (Exception e) {
            log.debug("Unable to update the contentType "+e);
        }finally{
        	
        }
		
	}

	@Override
	public void deleteContentType(Long libTxtId){
		
		 try{
	         getSession().delete(libTxtId);

	        }catch (Exception e) {

	            log.debug("The program is unable to delete the records from the detabase "+e);
	        }finally{
	        	 
	        }
		 	
		
		 
	}
	
}


 
 
