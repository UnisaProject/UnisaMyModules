/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.appfuse.dao.BaseDaoTestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import za.ac.unisa.lms.tool.eresources.model.Vendor;

/**
 * @author TMasibm
 *
 */
public class VendorDaoTest extends BaseDaoTestCase{

	@Autowired
    private VendorDao vendorDao;
	private Vendor vendor = new Vendor();  
	
	
	/**
	 * @param vendor
	 */

	  @Before
	     public void init() throws SQLException {
	    
		  	/*vendor.setVendorId(0);    
			vendor.setVendorName("The Book Co");
			vendor.setOnCampusURL("");
			vendor.setOffCampusURL("");
			vendor.setLogoURL("/logos/unisa.jpg");
			vendor.setLogoFile("wits.jpg");
			vendor.setEnabled(true);*/
			
	    }
	  
		@Test
		public void testSelectSpecificVendor() throws Exception {    
			List<Vendor> vendor = vendorDao.selectSpecificVendor(-2L, "wits/logo.jpg");    
			
			assertTrue(vendor.size() > 0);
			assertNotNull(vendor.containsAll(vendor));
		}
		
		
		@Test
		public void testFindVendorByName() throws Exception {    
			List<Vendor> vendorlist = vendorDao.findVendorByName("Wits Multimedia");    
			
			assertTrue(vendorlist.size() > 0);
			assertNotNull(vendorlist.containsAll(vendorlist));
			//assertEquals("Wits Multimedia", vendor.getVendorName());
			
		}
		
		@Test
		public void testFindVendorById() throws Exception {
			List<Vendor> vendorlist = vendorDao.findVendorById((1L));       
			
			assertTrue(vendorlist.size() > 0);
			assertNotNull(vendorlist.containsAll(vendorlist));
			//assertEquals(0L, vendor.getVendorId());
		}
		
		@Test
		public void testFindAll() throws Exception {
			List<Vendor> vendorList = vendorDao.findAll();
			
			assertTrue(vendorList.size() > 0);
		}
		
		/*@Test
		public void testFindLinkedVendor() throws Exception {    
			List<Vendor> vendor = vendorDao.findLinkedVendor(0L, 0L);    
			assertTrue(vendor.size() > 0);
			assertNotNull(vendor.containsAll(vendor));
		}*/
		
		@Test
		public void testVendorInUse() throws Exception {    
			boolean vendor = vendorDao.vendorInUse(0L, true);
			
			assertTrue("Vendor in use", true);
		 	assertNotNull(vendor);
		 	assertNotNull(vendorDao.vendorInUse(0L, true));
			
		}
		
	/*	@Test
		public void testGetLogoFile() throws Exception {   
			  vendor = vendorDao.getLogo("wits.jpg");    
			 
			  assertEquals(vendor, "wits.jpg");
			//assertEquals("wits.jpg", vendor.getLogoFile());
		}*/
		
		@Test
		public void testClearVendorId() throws Exception {   
			
		}
		
		
		@Test(expected = DataAccessException.class)
		public void testAddVendor() throws Exception {    
				vendor.setVendorId(8L);
				vendor.setVendorName("SAHRC");
				vendor.setLogoURL("ftp://logo2.jpg");
				
				vendor = vendorDao.save(vendor); 
				flush(); 
				
				vendor = vendorDao.get((long) vendor.getVendorId());     
				assertEquals("SAHRC", vendor.getVendorName());    
				assertNotNull(vendor.getVendorId());     
				log.debug("saving vendor...");     
				vendorDao.addVendor(vendor);
		}
		
		@Test(expected = DataAccessException.class)
		public void testUpdateVendor() throws Exception {    
				vendor.setVendorId(8L);
				vendor.setVendorName("SAHRC");
				vendor.setLogoURL("ftp://logo2.jpg");
				
				vendor = vendorDao.save(vendor); 
				flush(); 
				
				vendor = vendorDao.get((long) vendor.getVendorId());     
				assertEquals("SAHRC", vendor.getVendorName());    
				assertNotNull(vendor.getVendorName());     
				
				log.debug("updating vendor...");   
				vendorDao.updateVendor(vendor);
		}
		
		@Test(expected = DataAccessException.class)
		public void testDeleteVendor() throws Exception {    
				vendor.setVendorId(8L);
				vendor.setVendorName("SAHRC");
			
				flush();
				vendor = vendorDao.get((long) vendor.getVendorId());     
				assertEquals("SAHRC", vendor.getVendorName());    
				assertNotNull(vendor.getVendorId());     
				
				log.debug("removing vendor...");     
				
				vendorDao.deleteVendor(vendor.getVendorId(), true);    
				flush();     
				
				// should throw DataAccessException    
				vendorDao.get((long) vendor.getVendorId());
		}
		
}

