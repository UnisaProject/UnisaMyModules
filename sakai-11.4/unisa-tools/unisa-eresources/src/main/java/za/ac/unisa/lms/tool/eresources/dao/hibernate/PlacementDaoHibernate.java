package za.ac.unisa.lms.tool.eresources.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import za.ac.unisa.lms.tool.eresources.dao.PlacementDao;
import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.model.Placement;

/**
 * @author lebelsg
 * @author TMasibm
 */
@Repository("placementDao")

public class PlacementDaoHibernate extends  GenericDaoHibernate<Placement, Long> implements PlacementDao  {
	
	public PlacementDaoHibernate() {
		super(Placement.class);
	}
		
	
	/**
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public PlacementDaoHibernate(Class<Placement> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
	
	}

	private Logger log;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		log.debug("Session Factory Failed to load");
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Placement> getTabs(String placementName) {
		List<Placement> listAllTabs = new ArrayList<Placement>();
		 
		 try{
			 listAllTabs = getSession()
			 	.createCriteria(Placement.class)
				.add(Restrictions.eq("placement", placementName))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to to connect to the database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllTabs;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Placement> findAll() {
		List<Placement> listAllPlacements = new ArrayList<Placement>();
		 
		 try{
			 listAllPlacements = getSession()
				.createCriteria(Placement.class)
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to to connect to the database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllPlacements;
		 		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Placement> findPlacementById(Long placementId) {
		List<Placement> listPlacementsById = new ArrayList<Placement>();
		 
		 try{
			 listPlacementsById = getSession()
				.createCriteria(Placement.class)
				.add(Restrictions.eq("placementId", placementId))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to to connect to the database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listPlacementsById;
		 		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Placement> findPlacementByDisplayOrder(int displayOrder) {
		List<Placement> listPlacementsByDisplayOrder = new ArrayList<Placement>();
		 
		 try{
			 listPlacementsByDisplayOrder =
			 	getSession()
				.createCriteria(Placement.class)
				.add(Restrictions.eq("displayOrder", displayOrder))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to to connect to the database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listPlacementsByDisplayOrder;
				
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Placement> findLinkedPlacement(String resourcesPerPlacement) {
		List<Placement> listLinkedPlacements = new ArrayList<Placement>();
		 
		 try{
			 listLinkedPlacements =  getSession()
				.createCriteria(Placement.class)
				.add(Restrictions.eq("eresourcePlacement", resourcesPerPlacement))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to to connect to the database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listLinkedPlacements;		
				
	}


	@Override
	public boolean placementInUse(Long resId, boolean isEnabled) {
		if(isEnabled = false)		
			try{
				getSession()
				.createCriteria(ContentType.class)
				.add(Restrictions.eq("placementId", resId))
				.add(Restrictions.eq("isEnabled", true))
				.list();
			
	        }catch (Exception e) {
	            log.debug("Unable to update the contentType");
	        }finally{
	        	
	        }
			return true;
	}


	@Override
	public void deletePlacement(Long placementId, boolean inUse) {
		if (inUse = false)
		 try{
			 getSession().createCriteria(Placement.class);
	         getSession().delete(placementId);

	        }catch (Exception e) {

	            log.debug("The program is unable to delete the records from the detabase "+e);
	        }finally{
	        	 
	        }
		 	
		 	inUse = true;
			log.debug("The placement you are trying to delete is currently linked to a database resource");

		
	}
	
	
	@Override
	public void addPlacement(Placement placement) {
		 try{
	        	placement.getPlacementId();
	        	getSession().save(placement);
	        }catch (Exception e) {
	            log.debug("Unable to add fields to the database, please check the code and correct the errors "+e);
	        }finally{
	        }
		
	}

	@Override
	public void updatePlacement(Placement placement) {
		try{
			placement.getPlacementId();
			getSession().update(placement);
        }catch (Exception e) {
            log.debug("Unable to update the Placement table "+e);
        }finally{
        }
		
	}

	@Override
	public void updatePlacementRanking(Placement placement, int plcRanking) {
		try{
			placement.getDisplayOrder();
			getSession().update(plcRanking);
        }catch (Exception e) {
            log.debug("Unable to update the Placement ranking table "+e);
        }finally{
        }	
		
	}

}
