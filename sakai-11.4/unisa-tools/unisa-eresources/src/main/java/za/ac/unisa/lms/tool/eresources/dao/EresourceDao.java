package za.ac.unisa.lms.tool.eresources.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import za.ac.unisa.lms.tool.eresources.model.Alphabet;
import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.model.Eresource;
import za.ac.unisa.lms.tool.eresources.model.HighlightNote;
import za.ac.unisa.lms.tool.eresources.model.Newsletter;
import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.model.Subject;
import za.ac.unisa.lms.tool.eresources.model.Vendor;

/**
 * @author TMasibm
 *
 */
public interface EresourceDao extends GenericDao <Eresource , Long> {
	
	/***************************************************************************************
	 * 
	 * These methods for Eresource class Maintain
	 * 
	 ***************************************************************************************/


	//All vendors and Placements
 		public List<Eresource> selectAllVendorAndPlacementList(int vendorId, int placementId, int eresourceId);
 		
 	 	public List<Eresource> selectAllVendorsLinkedToResourceList(int vendorId, int eresourceId);
 	 	
 	 	public List<Eresource> selectAllPlacementsLinkedToResourceList(int placementId,int eresourceId);
	
		public List<Eresource> selectSpecificDb(int placementId,String startChar, String ipAddress);
		
	   	public List<Eresource> findAll();
	   	
	   	public List<Eresource> selectResourceById(long resourceId);
	   	
	   	public List<Eresource> selectResourceByName(String resourceName);
	   	
	   	public List<Eresource> getFeaturedDatabase(String ipAddress,boolean isTrialDatabase);
	   	
	   	public List<Eresource> getPassword(String password);
	 
	   	public Eresource getResourcePassword(String resourceId);
	   	
	   	public void addResource(Eresource eresource);
		
		public void updateResource(Eresource eresource);
		
		public void deleteResource(int resourceId, boolean inUse);

		public List<Alphabet> getAlphabetsList(char alphabet);

		public Eresource getEresource(int eresourceId);

		public List<Subject> getAllSubjects(String allSubjects);

		public List<Vendor> getAllVendors(String anyVendor);

		public List<Placement> getAllPlacements();

		public List<Placement> getPlacementNames(String placementName);

		public List<ContentType> getContentTypes(String contentType);

		public List<Newsletter> getNewsletters(String newsletter);

		public List<HighlightNote> getAccessNote(String accessNote);

		
		
	
}
