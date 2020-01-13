package za.ac.unisa.lms.tool.eresources.service;

import java.util.List;

import org.appfuse.service.GenericManager;

import za.ac.unisa.lms.tool.eresources.model.Placement;

/**
 * @author TMasibm
 *
 */
public interface PlacementManager  extends GenericManager<Placement, Long>  {

	public List<Placement> getTabs(String placementName);
		
	public List<Placement> findAll(); 
	
	public List<Placement> findPlacementById(Long placementId);
		
	public List<Placement> findLinkedPlacement(String resourcesPerPlacement);
	
 	public boolean placementInUse(Long resId, boolean isEnabled);
	
	public void addPlacement(Placement placement);
	
	public void updatePlacement(Placement placement);
	
	public void updatePlacementRanking(Placement placement, int plcRanking);

	public void deletePlacement(Long placementId, boolean inUse);

	public List<Placement> findPlacementByDisplayOrder(int displayOrder);
}

