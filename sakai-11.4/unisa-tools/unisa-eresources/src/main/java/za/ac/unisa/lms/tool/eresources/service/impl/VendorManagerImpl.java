package za.ac.unisa.lms.tool.eresources.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.unisa.lms.tool.eresources.dao.VendorDao;
import za.ac.unisa.lms.tool.eresources.model.Vendor;
import za.ac.unisa.lms.tool.eresources.service.VendorManager;


/**
 * @author Lebelsg
 *
 */

@Service("vendorManager")
public class VendorManagerImpl extends GenericManagerImpl<Vendor, Long> implements VendorManager{

	VendorDao vendorDao;
	
	@Autowired
	public VendorManagerImpl(VendorDao vendorDao) {
		super(vendorDao);
		this.vendorDao = vendorDao;
	}

	@Override
	public List<Vendor> selectSpecificVendor(Long venId, String logoUrl) {
		return this.vendorDao.selectSpecificVendor(venId , logoUrl);
	}

	@Override
	public List<Vendor> findVendorById(Long vendorId) {
		return this.vendorDao.findVendorById(vendorId);
	}

	@Override
	public List<Vendor> findVendorByName(String vendorName) {
		return this.vendorDao.findVendorByName(vendorName);
		
	}

	@Override
	public List<Vendor> findAll() {
		return this.vendorDao.findAll();
		
	}

	@Override
	public List<Vendor> findLinkedVendor(Long resourceId, Long vendorId) {
		return this.vendorDao.findLinkedVendor(resourceId, vendorId);
	}

	@Override
	public boolean vendorInUse(Long vendorId, boolean isEnabled) {
		return this.vendorDao.vendorInUse(vendorId, isEnabled);
	}

	@Override
	public Vendor getLogo(String logoFile) {
		return this.vendorDao.getLogo(logoFile);
	}

	@Override
	public void addVendor(Vendor vendor) {
		this.vendorDao.addVendor(vendor);
		
	}

	@Override
	public void updateVendor(Vendor vendor) {
		this.vendorDao.updateVendor(vendor);
		
	}

	@Override
	public void deleteVendor(Long vendorId, boolean inUse) {
		this.vendorDao.deleteVendor(vendorId, inUse);
		
	}

	@Override
	public void clearVendorId(Long vendorId, boolean isEnabled) {
		this.vendorDao.clearVendorId(vendorId, isEnabled);
		
	}

}
