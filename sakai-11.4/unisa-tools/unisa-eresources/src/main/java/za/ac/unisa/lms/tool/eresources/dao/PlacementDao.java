package za.ac.unisa.lms.tool.eresources.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import za.ac.unisa.lms.tool.eresources.model.Placement;


/**
 * @author lebelsg
 * @author TMasibm
 */
public interface PlacementDao extends GenericDao<Placement, Long> {
	
	/***************************************************************************************
	 * 
	 * These methods for Placement class Maintain
	 * 
	 ***************************************************************************************/
	
		public List<Placement> getTabs(String placementName);
	   				
		public List<Placement> findAll(); 
		
		public List<Placement> findPlacementById(Long placementId);
		
		public List<Placement> findPlacementByDisplayOrder(int displayOrder);
		
		public List<Placement> findLinkedPlacement(String resourcesPerPlacement);
		
	 	public boolean placementInUse(Long resId, boolean isEnabled);
		
		public void addPlacement(Placement placement);
		
		public void updatePlacement(Placement placement);
		
		public void updatePlacementRanking(Placement placement, int plcRanking);

		public void deletePlacement(Long placementId, boolean inUse);
		
}
