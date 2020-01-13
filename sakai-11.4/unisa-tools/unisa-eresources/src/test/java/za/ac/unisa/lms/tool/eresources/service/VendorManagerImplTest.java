package za.ac.unisa.lms.tool.eresources.service;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.service.impl.BaseManagerMockTestCase;
import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import za.ac.unisa.lms.tool.eresources.dao.VendorDao;
import za.ac.unisa.lms.tool.eresources.model.Vendor;
import za.ac.unisa.lms.tool.eresources.service.impl.VendorManagerImpl;

public class VendorManagerImplTest extends BaseManagerMockTestCase {

	private final Log log = LogFactory.getLog(VendorManagerImplTest.class);
	private VendorManagerImpl manager = null;    
	private VendorDao dao = null;     
	
	@Before    
	public void setUp() {       
		dao = context.mock(VendorDao.class);       
		manager = new VendorManagerImpl(dao);    
	}    
	
	@After    
	public void tearDown() {        
		manager = null;    
	}   

	@Test    
	public void testFindVendorById() {        
		log.debug("testing find Vendor note by Id");        
		final Long vendorId = 7L;        
		final Vendor vendor = new Vendor();         
		
		// set expected behavior on DAO 
		
		context.checking(new Expectations() {
			{            
				one(dao).get(with(equal(vendorId)));           
				will(returnValue(vendor));       
			}
		});         
		Vendor result = manager.get(vendorId);        
		assertSame(vendor, result);    
	}   
	
	@Test    
	public void testGetLogo() {        
		log.debug("testing find Vendor logo");        
		final Vendor vendors = new Vendor();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).getLogo("unisa.jpg");            
			will(returnValue(vendors));        
			}});        
		Vendor result = manager.getLogo("unisa.jpg");         
		assertSame(vendors, result);     
	}    
	
	@Test    
	public void testFindAll() {        
		log.debug("testing getAll Vendors...");         
		final ArrayList<Vendor> vendors = new ArrayList<Vendor>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).getAll();            
			will(returnValue(vendors));        
			}});        
		List<Vendor> result = manager.getAll();         
		assertSame(vendors, result);   
	}     
	
	@Test    
	public void testFindVendorByName() {        
		log.debug("testing find vendor by name field...");         
		final List<Vendor> vendors = new ArrayList<Vendor>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).findVendorByName("Text");            
			will(returnValue(vendors));        
			}});        
		List<Vendor> result = manager.findVendorByName("Text");         
		assertSame(vendors, result);   
	}  
	
	@Test    
	public void testSelectSpecificVendor() {        
		log.debug("testing find logo url location...");         
		final List<Vendor> vendors = new ArrayList<Vendor>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).selectSpecificVendor(0L, "media/logos/eresources/img013.jpg");            
			will(returnValue(vendors));        
			}});        
		List<Vendor> result = manager.selectSpecificVendor(0L, "media/logos/eresources/img013.jpg");         
		assertSame(vendors, result);   
	}  
	
	
	@Test    
	public void testFindLinkedVendor() {        
		log.debug("testing select vendors linked to an eresource...");         
		final List<Vendor> vendors = new ArrayList<Vendor>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).findLinkedVendor(0L, 0L);            
			will(returnValue(vendors));        
			}});        
		List<Vendor> result = manager.findLinkedVendor(0L, 0L);         
		assertSame(vendors, result);   
	}    
	
	@Test    
	public void testVendorInUse() {        
		log.debug("testing to check whether boolean vendorInUse is true...");         
		final boolean vendors = true;         
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{           
			one(dao).vendorInUse(0L, true);            
			will(returnValue(vendors));        
			}});        
		boolean result = manager.vendorInUse(0L, true);         
		assertSame(vendors, result);   
	}     
	
	@Test    
	public void testClearVendorId() {        
		log.debug("testing to clear vendor id ...");      
		// enter all required fields                 
		/*final boolean isEnabled = false;
		final int vendorId = 0;*/
		
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{            
			one(dao).clearVendorId(0L, true);     
			
		}});         
	 manager.clearVendorId(0L, true);     
	}     
	
	
	@Test    
	public void testAddVendor() {       
		log.debug("testing save...");         
		final Vendor vendor = new Vendor();        
		// enter all required fields                 
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{            
			one(dao).save(with(same(vendor)));       
			
		}});         
		manager.save(vendor);    
	}     
	
	@Test    
	public void testUpdateVendor() {       
		log.debug("testing save...");         
		final Vendor vendor = new Vendor();        
		// enter all required fields                 
		
		// set expected behavior on DAO      
		context.checking(new Expectations() {{            
			one(dao).updateVendor(with(same(vendor)));       
			
		}});         
		manager.updateVendor(vendor);    
	}     
	
	@Test    
	public void testDeleteVendor() {        
		log.debug("testing remove...");         
		final Long vendorId = -11L;         
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).remove(with(equal(vendorId)));        
			}});         
		manager.remove(vendorId);    
	}
}
