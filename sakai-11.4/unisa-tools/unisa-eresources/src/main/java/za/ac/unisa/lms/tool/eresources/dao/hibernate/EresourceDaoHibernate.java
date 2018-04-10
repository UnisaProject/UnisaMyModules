package za.ac.unisa.lms.tool.eresources.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import za.ac.unisa.lms.tool.eresources.dao.EresourceDao;
import za.ac.unisa.lms.tool.eresources.model.Alphabet;
import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.model.Eresource;
import za.ac.unisa.lms.tool.eresources.model.EresourcePlacement;
import za.ac.unisa.lms.tool.eresources.model.HighlightNote;
import za.ac.unisa.lms.tool.eresources.model.Newsletter;
import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.model.Subject;
import za.ac.unisa.lms.tool.eresources.model.Vendor;

/**
 * @author lebelsg
 * @author tmasibm
 */
@Repository("eresourceDao")
public class EresourceDaoHibernate  extends GenericDaoHibernate<Eresource, Long> implements EresourceDao {

	
	public EresourceDaoHibernate() {
		super(Eresource.class);
	}
	
	
	/**
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public EresourceDaoHibernate(Class<Eresource> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass);
	}
	/**
	 * @param persistentClass
	 * @param sessionFactory
	 */


	private Logger log;
	
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		log.debug("Session Factory Failed to load");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Eresource> selectAllVendorAndPlacementList(int vendorId, int placementId, int eresourceId) {
		
		
		List<Eresource> listAllVendorAndPlacement = new ArrayList<Eresource>();
		 
		 try{
			 listAllVendorAndPlacement = 
			 getSessionFactory()
			 	.getCurrentSession()
			 	.createCriteria(Eresource.class)
				.add(Restrictions.eq("vendorId", vendorId))
				.add(Restrictions.eq("placementId", placementId))
				.add(Restrictions.eq("eresourceId", eresourceId))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		return listAllVendorAndPlacement;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Eresource> selectAllVendorsLinkedToResourceList(int vendorId,
			int eresourceId) {
		List<Eresource> listVendorsLinkedToResource = new ArrayList<Eresource>();
		 
		 try{
			 listVendorsLinkedToResource = getSessionFactory()
			 	.getCurrentSession()
				.createCriteria(Eresource.class)
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		return listVendorsLinkedToResource;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Eresource> selectAllPlacementsLinkedToResourceList(
			int placementId, int eresourceId) {
		List<Eresource> listPlacementsLinkedToResource = new ArrayList<Eresource>();
		 
		 try{
			 listPlacementsLinkedToResource =getSessionFactory()
			 	.getCurrentSession()
				.createCriteria(Eresource.class)
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		return listPlacementsLinkedToResource;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Eresource> selectSpecificDb(int placementId, String startChar,
			String ipAddress) {
		List<Eresource> listAllSpecificDbs = new ArrayList<Eresource>();
		 
		 try{
			 listAllSpecificDbs = 
			 getSessionFactory()
			 	.getCurrentSession()
				.createCriteria(Eresource.class)
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		return listAllSpecificDbs;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Eresource> findAll() {
		List<Eresource> listAllResources = new ArrayList<Eresource>();
		 
		 try{
			 listAllResources = getSessionFactory()
			 	.getCurrentSession()
				.createCriteria(Eresource.class)
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		return listAllResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Eresource> selectResourceById(long resourceId) {
		List<Eresource> listAllResourceById = new ArrayList<Eresource>();
		 
		 try{
			 listAllResourceById = getSessionFactory()
			 	.getCurrentSession()
				.createCriteria(Eresource.class)
				.add(Restrictions.eq("eresourceId", resourceId))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		return listAllResourceById;
				
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Eresource> selectResourceByName(String resourceName) {
		List<Eresource> listAllResourceByNames = new ArrayList<Eresource>();
		 
		 try{
			 listAllResourceByNames = getSessionFactory()
			 	.getCurrentSession()
				.createCriteria(Eresource.class)
				.add(Restrictions.eq("resourceName", resourceName))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		return listAllResourceByNames;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Alphabet> getAlphabetsList(char alphabet) {
		List<Alphabet> listAllAphabets = new ArrayList<Alphabet>();
		 
		 try{
			 listAllAphabets = getSessionFactory()
			 	.getCurrentSession()
				.createCriteria(Alphabet.class)
				.add(Restrictions.eq("Alphabet", alphabet))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		return listAllAphabets;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Eresource> getFeaturedDatabase(String ipAddress, boolean isTrialDatabase) {
		List<Eresource> listAllFeaturedDatabases = new ArrayList<Eresource>();
		if(isTrialDatabase = true)
		 try{
			 listAllFeaturedDatabases = 
			 getSessionFactory()
			 	.getCurrentSession()
			 	.createCriteria(Eresource.class)
			 	.add(Restrictions.eq("trialDatabase", true))
			 	.add(Restrictions.eq("resourceName", ipAddress))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		return listAllFeaturedDatabases;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Eresource> getPassword(String password) {
		List<Eresource> listAllPasswordResources = new ArrayList<Eresource>();
		 
		 try{
			 listAllPasswordResources = getSessionFactory()
			 	.getCurrentSession()
				.createCriteria(Eresource.class)
				.add(Restrictions.eq("password", password))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        }
		return listAllPasswordResources;
	}


	@Override
	public Eresource getResourcePassword(String resourceId) {
		Eresource password = null;
		try{
			getSessionFactory()
			.getCurrentSession()
			.createCriteria(Eresource.class)
			.add(Restrictions.eq("password", resourceId));
		} catch (Exception e){
			log.debug("No password required for this eresource" + e);
		}finally{
			
		}
		return password;
	}



	@Override
	public void addResource(Eresource eresource) {
		getSession().beginTransaction();
		
		try{
			 persistContentType(eresource);
		//	 persistNewsletter(eresource);
			 
	         getSession().save(eresource);
	        }catch (Exception e) {
	            log.debug("Unable to add fields to the database, please check the code and correct the errors "+e);
	        }finally{
	        	getSession().flush();
	        }
		
	}


	@Override
	public void updateResource(Eresource eresource) {
		try{
			
			eresource.getEresourceId();
			getSession().update(eresource);
        }catch (Exception e) {
            log.debug("Unable to update the Subject table "+e);
        }finally{
        	getSession().flush();
        }
		
	}


	@Override
	public void deleteResource(int resourceId, boolean inUse) {
		if (inUse = false);
		 try{
			 getSession().createCriteria(Eresource.class);
	         getSession().delete(resourceId);

	        }catch (Exception e) {

	            log.debug("The program is unable to delete the records from the detabase "+e);
	        }finally{
	        	 
	        }
		 	
		 inUse = true;
	}
	
	protected void persistContentType(Eresource eresource) {
		ContentType contentType = new ContentType();
		try{		
			//contentType.setEresource(eresource);
			eresource.setContentType(contentType);
	 
			getSession().save(contentType);
			} catch (Exception e){
				log.debug("Unable to pesrsist content type to the database, please check the code and correct the errors "+e);
			} finally{
				getSession().flush();
			}
		 
	}
	
/*	private void persistNewsletter(Eresource eresource) {
		Newsletter newsTitle = new Newsletter();
		try{
	
			//newsTitle.setEresource(eresource);
			eresource.setNewsletter(newsTitle);
	 
			getSession().save(newsTitle);
			} catch (Exception e){
			log.debug("Unable to pesrsist content type to the database, please check the code and correct the errors "+e);
		} finally{
			getSession().flush();
		}
		 
	}*/
	
	/*public void persistHighlighNote(Eresource eresource) {
		HighlightNote notes = new HighlightNote();
		try{
	
			//notes.setEresource(eresource);
			eresource.setHighlightNote(notes);
	 
			getSession().save(notes);
			} catch (Exception e){
			log.debug("Unable to pesrsist highlight notes to the database, please check the code and correct the errors "+e);
		} finally{
			getSession().flush();
		}
		 
	}*/
	
	public void persistResourcePlacement(EresourcePlacement resPlacement) {
		
		getSession().beginTransaction();
		 try{
		    Eresource eresource = new Eresource();
		    Placement placement1 = new Placement();
		    
		    //new placement, need save to get the id first
		    getSession().save(placement1);
		 
	
		    resPlacement.setEresource(eresource);
		    resPlacement.setPlacement(placement1);
		    resPlacement.setEndDate(new Date());
		    resPlacement.setStartDate(new Date());
		    
		   eresource.getEresourcePlacement().add(resPlacement);
		 
		 
		    getSession().save(eresource);
		 }
		 catch (Exception e){
			 log.debug("Unable to add fields to the database, please check the code and correct the errors "+e);
	        }finally{
	        	
	        }
		    getSession().getTransaction().commit();
		    getSession().flush();
	}
	
	
	public void persistResourceSubjects(Eresource resSub) {
		
		getSession().beginTransaction();
		 try{
		  
		    Subject subj = new Subject();
		    
		    Set<Subject> subject = new HashSet<Subject>();
		    subject.add(subj);
		    
		    resSub.setSubject(subject);
		    //new placement, need save to get the id first
		    getSession().save(resSub);
		 
		 }
		 catch (Exception e){
			 log.debug("Unable to add fields to the database, please check the code and correct the errors "+e);
	        }finally{
	        	
	        }
		    getSession().getTransaction().commit();
		    getSession().flush();
	}
	
	 
	@Override
	public List<Subject> getAllSubjects(String allSubjects){
		
		SubjectDaoHibernate subj = new SubjectDaoHibernate();
		return subj.selectSpecificSubject(allSubjects);
		
	}
	
	@Override
	public List<Vendor> getAllVendors (String anyVendor){
		VendorDaoHibernate vendor = new VendorDaoHibernate();
		return vendor.findVendorByName(anyVendor);
	}
	
	@Override
	public List<Placement> getAllPlacements(){
		
		PlacementDaoHibernate placement = new PlacementDaoHibernate();
		return placement.findAll();
		
	}
	
	@Override
	public List<Placement> getPlacementNames(String placementName){
		
		PlacementDaoHibernate placement = new PlacementDaoHibernate();
		return placement.getTabs(placementName);
		
	}


	@Override
	public Eresource getEresource(int eresourceId) {
			  return (Eresource) getSessionFactory()
					  .getCurrentSession()
					  .get(Eresource.class, eresourceId);
			 }
	
	@Override
	public List<ContentType> getContentTypes(String contentType){
		
		ContentTypeDaoHibernate content = new ContentTypeDaoHibernate();
		return content.selectContentTypeDesc(contentType);
		
	}

	@Override
	public List<Newsletter> getNewsletters(String newsletter){
		
		NewsletterDaoHibernate news = new NewsletterDaoHibernate();
		return news.findNewsTitleByDesc(newsletter);
		
	}
	
	@Override
	public List<HighlightNote> getAccessNote(String accessNote){
		
		HighlightNotesDaoHibernate access = new HighlightNotesDaoHibernate();
		return access.findHighLightNoteByDescr(accessNote);
		
	}
	
}
