package za.ac.unisa.lms.tool.eresources.service;

import java.util.List;

import org.appfuse.service.GenericManager;

import za.ac.unisa.lms.tool.eresources.model.Vendor;

/**
 * @author TMasibm
 *
 */
public interface VendorManager  extends GenericManager<Vendor, Long>  {

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

