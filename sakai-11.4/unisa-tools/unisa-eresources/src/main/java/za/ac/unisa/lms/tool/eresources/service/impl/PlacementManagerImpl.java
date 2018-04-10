package za.ac.unisa.lms.tool.eresources.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.unisa.lms.tool.eresources.dao.PlacementDao;
import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.service.PlacementManager;

@Service("placementManager")
public class PlacementManagerImpl extends GenericManagerImpl<Placement, Long>
		implements PlacementManager {

	PlacementDao placementDao;

	@Autowired
	public PlacementManagerImpl(PlacementDao placementDao) {
		super(placementDao);
		this.placementDao = placementDao;
	}


	@Override
	public List<Placement> getTabs(String placementName) {
		return this.placementDao.getTabs(placementName);
	}

	@Override
	public List<Placement> findAll() {
		return this.placementDao.findAll();
		
	}

	@Override
	public List<Placement> findPlacementById(Long placementId) {
		return this.placementDao.findPlacementById(placementId);
		
	}

	@Override
	public List<Placement> findPlacementByDisplayOrder(int displayOrder) {
		return this.placementDao.findPlacementByDisplayOrder(displayOrder);
		
	}

	@Override
	public List<Placement> findLinkedPlacement(String resourcesPerPlacement) {
		return this.placementDao.findLinkedPlacement(resourcesPerPlacement);
		
	}

	@Override
	public boolean placementInUse(Long resId, boolean isEnabled) {
		return this.placementDao.placementInUse(resId, isEnabled);
		
	}

	@Override
	public void addPlacement(Placement placement) {
		this.placementDao.addPlacement(placement);
		
	}

	@Override
	public void updatePlacement(Placement placement) {
		this.placementDao.updatePlacement(placement);
		
	}

	@Override
	public void updatePlacementRanking(Placement placement, int plcRanking) {
		this.placementDao.updatePlacementRanking(placement, plcRanking);
		
	}

	@Override
	public void deletePlacement(Long placementId, boolean inUse) {
		this.placementDao.deletePlacement(placementId, inUse);
		
	}

}