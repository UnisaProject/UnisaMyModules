package za.ac.unisa.lms.tool.eresources.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import za.ac.unisa.lms.tool.eresources.model.Vendor;


/**
 * @author lebelsg
 *
 */
public interface VendorDao  extends GenericDao<Vendor, Long> {

	/***************************************************************************************
	 * 
	 * These methods for ContentType class Maintain
	 * 		
	 ***************************************************************************************/

	  	public List<Vendor> selectSpecificVendor(Long venId, String logoUrl);
	 	
	  	public List<Vendor> findVendorById(Long vendorId);
		
	  	public List<Vendor> findVendorByName(String vendorName);
						
		public List<Vendor> findAll();
		
		public List<Vendor> findLinkedVendor(Long resourceId, Long vendorId);

	   	public boolean vendorInUse(Long vendorId, boolean isEnabled);
		
		public Vendor getLogo(String logoFile);
		
		public void addVendor(Vendor vendor);
		
		public void updateVendor(Vendor vendor);
		
		public void deleteVendor(Long vendorId, boolean inUse);
			
		public void clearVendorId(Long vendorId, boolean isEnabled);
}
