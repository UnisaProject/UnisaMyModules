package za.ac.unisa.lms.tool.eresources.service;

import java.util.List;

import org.appfuse.service.GenericManager;

import za.ac.unisa.lms.tool.eresources.model.EresourcePlacement;

/**
 * @author TMasibm
 *
 */
public interface EresourcePlacementManager  extends GenericManager<EresourcePlacement, Long>  {
	
	public List<EresourcePlacement> selectAllResourcePlacements();
	
	public List<EresourcePlacement> selectResourcePlacementById(int resPlacementId);		
			
	public void addResPlacement(EresourcePlacement resPlacement);

	public void updateResPlacement(EresourcePlacement resPlacement);
	
	public void deleteResPlacements(int resPlacementId);
}

