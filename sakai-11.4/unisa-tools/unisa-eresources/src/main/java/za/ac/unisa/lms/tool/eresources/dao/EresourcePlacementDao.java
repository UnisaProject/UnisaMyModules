package za.ac.unisa.lms.tool.eresources.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import za.ac.unisa.lms.tool.eresources.model.EresourcePlacement;



/**
 *@author lebelsg
 *@author TMasibm
 */
public interface EresourcePlacementDao extends GenericDao<EresourcePlacement, Long> {

	/***************************************************************************************
	 * 
	 * These methods for EresourcePlacement class Maintain/Front End to be used by the 
	 * Eresources Screens for all the relationships between Eresources and Placements
	 * 
	 ***************************************************************************************/
	
		public List<EresourcePlacement> selectAllResourcePlacements();
		
		public List<EresourcePlacement> selectResourcePlacementById(int resPlacementId);		
				
		public void addResPlacement(EresourcePlacement resPlacement);

		public void updateResPlacement(EresourcePlacement resPlacement);
		
		public void deleteResPlacements(int resPlacementId);
		
	
}
