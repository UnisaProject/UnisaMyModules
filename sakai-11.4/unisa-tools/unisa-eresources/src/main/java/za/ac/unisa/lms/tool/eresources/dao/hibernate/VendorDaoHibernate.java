package za.ac.unisa.lms.tool.eresources.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import za.ac.unisa.lms.tool.eresources.dao.VendorDao;
import za.ac.unisa.lms.tool.eresources.model.Vendor;

/**
 * @author lebelsg
 * @author TMasibm
 */
@Repository("vendorDao")

public class VendorDaoHibernate extends GenericDaoHibernate<Vendor, Long> implements VendorDao{


	public VendorDaoHibernate() {
		super(Vendor.class);
	}
	
	/**
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public VendorDaoHibernate(Class<Vendor> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
	
	}

	private Logger log;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		log.debug("Session Factory Failed to load");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> selectSpecificVendor(Long venId, String logoUrl) {
		
		List<Vendor> listSpecificVendor = new ArrayList<Vendor>();
		 
		 try{
			 listSpecificVendor = getSession()
			 .createCriteria(Vendor.class)
			 .add(Restrictions.eq("vendorId", venId))
			 .add(Restrictions.eq("logoURL", logoUrl))
			 .list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		return listSpecificVendor; 
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> findVendorById(Long vendorId) {
		List<Vendor> listVendorsByID = new ArrayList<Vendor>();
		 
		 try{
			 listVendorsByID = getSessionFactory().getCurrentSession()
				.createCriteria(Vendor.class)
				.add(Restrictions.eq("vendorId", vendorId))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		return listVendorsByID;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> findVendorByName(String vendorName) {
		
		List<Vendor> listVendorsByName = new ArrayList<Vendor>();
		 
		 try{
			 listVendorsByName = getSessionFactory().getCurrentSession()
				.createCriteria(Vendor.class)
				.add(Restrictions.eq("vendorName", vendorName))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listVendorsByName;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> findAll() {
		 List<Vendor> listAllVendors = new ArrayList<Vendor>();
		 
		 try{
			 listAllVendors = getSessionFactory().getCurrentSession()
				.createCriteria(Vendor.class)
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllVendors;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> findLinkedVendor(Long vendorId, Long resourceId) {
		 
		List<Vendor> listLinkedVendors = new ArrayList<Vendor>(0);
		 
		 try{
			 listLinkedVendors = getSession()
				.createCriteria(Vendor.class)
				.add(Restrictions.eq("vendorId", vendorId))
				//.add(Restrictions.eq("eresources", resourceId))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listLinkedVendors;
	}

	@Override
	public Vendor getLogo(String logoFile) {
		Vendor logo = null;
		 try{ 
			 logo = (Vendor) getSession()
			 	.createCriteria(Vendor.class)
				.add(Restrictions.eq("logoFile", logoFile))
				.list().get(0);
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		return logo;
		
	}

	@Override
	public void deleteVendor(Long vendorId, boolean inUse) {
		if (inUse = false)
		 try{
	         getSession().delete(vendorId);

	        }catch (Exception e) {

	            log.debug("The program is unable to delete the records from the detabase "+e);
	        }finally{
	        	 
	        }
		 	
		 inUse = true;
	}


	@Override
	public void addVendor(Vendor vendor) {
		 try{
	        	
	        	vendor.getVendorId();
	        	getSession().save(vendor);
	        }catch (Exception e) {
	            log.debug("Unable to add fields to the database, please check the code and correct the errors "+e);
	        }finally{
	        	
	        }
		
	}

	@Override
	public void updateVendor(Vendor vendor) {
		try{
			vendor.getVendorId();
			getSession().update(vendor);
        }catch (Exception e) {
            log.debug("Unable to update the Vendor table "+e);
        }finally{
        	
        }
		
	}

	@Override
	public void clearVendorId(Long vendorId, boolean isEnabled) {
		if (isEnabled = true)
		try{
	         getSession().createCriteria(Vendor.class)
	         .add(Restrictions.eq("vendorId", vendorId))
	         .add(Restrictions.eq("isEnabled", isEnabled))
	         ;

	        }catch (Exception e) {

	            log.debug("This Vendor is already linked to the Eresource "+e);
	        }finally{
	        	 
	        } 
			isEnabled =false;
		 return;
	}


	@Override
	public boolean vendorInUse(Long vendorId, boolean isEnabled) {
		if (isEnabled = false)
		 try{
	         getSession()
	         .createCriteria(Vendor.class)
	         .add(Restrictions.eq("vendorId", vendorId))
			 .add(Restrictions.eq("isEnabled", true))
			.list();

	        }catch (Exception e) {
	            log.debug("This Vendor is already linked to the Eresource "+e);
	        }finally{
	        	
	        }
		 return true;
	}
	
}
