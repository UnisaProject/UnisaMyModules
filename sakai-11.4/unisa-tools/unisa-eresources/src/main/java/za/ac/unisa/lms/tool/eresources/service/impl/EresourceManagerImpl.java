package za.ac.unisa.lms.tool.eresources.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.unisa.lms.tool.eresources.dao.EresourceDao;
import za.ac.unisa.lms.tool.eresources.model.Alphabet;
import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.model.Eresource;
import za.ac.unisa.lms.tool.eresources.model.HighlightNote;
import za.ac.unisa.lms.tool.eresources.model.Newsletter;
import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.model.Subject;
import za.ac.unisa.lms.tool.eresources.model.Vendor;
import za.ac.unisa.lms.tool.eresources.service.EresourceManager;

@Service("eresourceManager")
public class EresourceManagerImpl extends GenericManagerImpl<Eresource, Long> implements EresourceManager{

	EresourceDao eresourceDao;
	
	@Autowired
	public EresourceManagerImpl(EresourceDao eresourceDao) {
		super(eresourceDao);
		this.eresourceDao = eresourceDao;
	}

	@Override
	public List<Eresource> selectAllVendorAndPlacementList(int vendorId,
			int placementId, int eresourceId) {
		
		return this.eresourceDao.selectAllVendorAndPlacementList(vendorId,
				placementId,eresourceId);
	}

	@Override
	public List<Eresource> selectAllVendorsLinkedToResourceList(int vendorId,
			int eresourceId) {
		
		return this.eresourceDao.selectAllVendorsLinkedToResourceList(vendorId,
				eresourceId);
	}

	@Override
	public List<Eresource> selectAllPlacementsLinkedToResourceList(
			int placementId, int eresourceId) {
		
		return this.eresourceDao.selectAllPlacementsLinkedToResourceList(
				placementId,  eresourceId);
	}

	@Override
	public List<Eresource> selectSpecificDb(int placementId, String startChar,
			String ipAddress) {
		
		return this.eresourceDao.selectSpecificDb(placementId,  startChar,
				 ipAddress);
	}

	@Override
	public List<Eresource> findAll() {
		return this.eresourceDao.findAll();
		
	}

	@Override
	public List<Eresource> selectResourceById(int resourceId) {
		return this.eresourceDao.selectResourceById( resourceId);
		
	}

	@Override
	public List<Eresource> selectResourceByName(String resourceName) {
		return this.eresourceDao.selectResourceByName( resourceName);
		
	}

	@Override
	public List<Eresource> getFeaturedDatabase(String ipAddress, boolean isTrialDatabase) {
		return this.eresourceDao.getFeaturedDatabase( ipAddress, isTrialDatabase);
		
	}

	@Override
	public List<Eresource> getPassword(String password) {
		return this.eresourceDao.getPassword( password);
		
	}

	@Override
	public Eresource getResourcePassword(String resourceId) {
		return this.eresourceDao.getResourcePassword( resourceId);
		
	}

	@Override
	public void addResource(Eresource eresource) {
		this.eresourceDao.addResource( eresource);

	}

	@Override
	public void updateResource(Eresource eresource) {
		this.eresourceDao.updateResource( eresource);		

	}

	@Override

	public void deleteResource(int resourceId, boolean inUse){
		this.eresourceDao.deleteResource( resourceId,  inUse);

	}

	@Override
	public List<Alphabet> getAlphabetsList(char alphabet) {
		// TODO Auto-generated method stub
		return this.eresourceDao.getAlphabetsList(alphabet);
	}

	@Override
	public Eresource getEresource(int eresourceId) {
		// TODO Auto-generated method stub
		return this.eresourceDao.getEresource(eresourceId);
	}

	@Override
	public List<Subject> getAllSubjects(String allSubjects) {
		// TODO Auto-generated method stub
		return this.eresourceDao.getAllSubjects(allSubjects);
	}

	@Override
	public List<Vendor> getAllVendors(String anyVendor) {
		// TODO Auto-generated method stub
		return this.eresourceDao.getAllVendors(anyVendor);
	}

	@Override
	public List<Placement> getAllPlacements() {
		// TODO Auto-generated method stub
		return this.eresourceDao.getAllPlacements();
	}

	@Override
	public List<Placement> getPlacementNames(String placementName) {
		// TODO Auto-generated method stub
		return this.eresourceDao.getPlacementNames(placementName);
	}

	@Override
	public List<ContentType> getContentTypes(String contentType) {
		// TODO Auto-generated method stub
		return this.eresourceDao.getContentTypes(contentType);
	}

	@Override
	public List<Newsletter> getNewsletters(String newsletter) {
		// TODO Auto-generated method stub
		return this.eresourceDao.getNewsletters(newsletter);
	}

	@Override
	public List<HighlightNote> getAccessNote(String accessNote) {
		// TODO Auto-generated method stub
		return this.eresourceDao.getAccessNote(accessNote);
	}
	
}
