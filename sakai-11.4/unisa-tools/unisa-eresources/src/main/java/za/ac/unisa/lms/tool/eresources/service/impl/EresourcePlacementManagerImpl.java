package za.ac.unisa.lms.tool.eresources.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.unisa.lms.tool.eresources.dao.EresourcePlacementDao;
import za.ac.unisa.lms.tool.eresources.model.EresourcePlacement;
import za.ac.unisa.lms.tool.eresources.service.EresourcePlacementManager;

@Service("eresourcePlacement")
public class EresourcePlacementManagerImpl extends GenericManagerImpl<EresourcePlacement, Long> implements EresourcePlacementManager{

	EresourcePlacementDao eresourcePlacementDao;
	
	@Autowired
	public EresourcePlacementManagerImpl(EresourcePlacementDao eresourcePlacementDao) {
		super(eresourcePlacementDao);
		this.eresourcePlacementDao = eresourcePlacementDao;
	}

	@Override
	public List<EresourcePlacement> selectResourcePlacementById(int resourceId) {
		return this.eresourcePlacementDao.selectResourcePlacementById( resourceId);
		
	}

	@Override
	public void addResPlacement(EresourcePlacement resPlacement) {
		this.eresourcePlacementDao.addResPlacement( resPlacement);
		
	}

	@Override
	public void updateResPlacement(EresourcePlacement resPlacement) {
		this.eresourcePlacementDao.updateResPlacement( resPlacement);
		
	}

	@Override
	public void deleteResPlacements(int resId) {
		this.eresourcePlacementDao.deleteResPlacements(resId);
		
	}

	@Override
	public List<EresourcePlacement> selectAllResourcePlacements() {
		return this.eresourcePlacementDao.selectAllResourcePlacements();
		
	}
	
}
