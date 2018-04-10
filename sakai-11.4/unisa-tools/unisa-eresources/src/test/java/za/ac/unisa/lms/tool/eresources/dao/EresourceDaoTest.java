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

import za.ac.unisa.lms.tool.eresources.model.Eresource;

/**
 * @author TMasibm
 *
 */
public class EresourceDaoTest extends BaseDaoTestCase{

	

	@Autowired
    private EresourceDao eresourceDao;
	private Eresource eresource = new Eresource();  
//	private ContentType contentType = new ContentType()
	/**
	 * @param eresourceDao
	 * @param eresource
	 * Initialise All Eresources Entity Variables Test Data
	 */
	@Before
    public void init() throws SQLException {
		
		eresource.setEresourceId(-21L);    
		eresource.setDescription("Test2");
		/*eresource.setContentType(contentType)
		eresource
		eresource
		eresource
		eresource
		eresource*/
   }
 
	/*@Test
	public void testSelectAllVendorAndPlacementList() throws Exception {    
		List<Eresource> eresource = eresourceDao.selectAllVendorAndPlacementList(0, 0, 0);    
		assertTrue(eresource.size() > 0);
		assertNotNull(eresource.containsAll(eresource));
		
	}*/
	@Test
	public void testSelectAllVendorsLinkedToResourceList() throws Exception {    
		List<Eresource> eresource = eresourceDao.selectAllVendorsLinkedToResourceList(0, 0);       
		assertTrue(eresource.size() > 0);
		assertNotNull(eresource.containsAll(eresource));
		
	}
	@Test
	public void testSelectAllPlacementsLinkedToResourceList() throws Exception {    
		List<Eresource> eresource = eresourceDao.selectAllPlacementsLinkedToResourceList(0, 0);     
		assertTrue(eresource.size() > 0);
		assertNotNull(eresource.containsAll(eresource));
		
	}
	@Test
	public void testSelectSpecificDb() throws Exception {    
		List<Eresource> eresource = eresourceDao.selectSpecificDb(0, "A", "17.0.0.1");       
		assertTrue(eresource.size() > 0);
		assertNotNull(eresource.containsAll(eresource));
		
	}
	@Test
	public void testFindAll() throws Exception {    
		List<Eresource> eresource = eresourceDao.findAll();      
		
		assertTrue(eresource.size() > 0);
		//assertNotNull(eresource.containsAll(eresource));
		
	}
	@Test
	public void testSelectResourceById() throws Exception {    
		List<Eresource> eresource = eresourceDao.selectResourceById(-2);       
		
		assertTrue(eresource.size() > 0);
		assertNotNull(eresource.containsAll(eresource));
		
	}
	@Test
	public void testSelectResourceByName() throws Exception {    
		List<Eresource> eresource = eresourceDao.selectResourceByName("Dynamics of Industrial Law");       
		
		assertTrue(eresource.size() > 0);
		assertNotNull(eresource.containsAll(eresource));
		
	}
	@Test
	public void testGetFeaturedDatabase() throws Exception {    
		List<Eresource> eresource = eresourceDao.getFeaturedDatabase("Dynamics of Family Law", true);        
		
		assertTrue(eresource.size() > 0);
		assertNotNull(eresource.containsAll(eresource));
		
	}
	@Test
	public void testGetPassword() throws Exception {    
		List<Eresource> eresource = eresourceDao.getPassword("87remz");       
		
		assertTrue(eresource.size() > 0);
		assertNotNull(eresource.containsAll(eresource));
		
	}
	@Test
	public void testGetResourcePassword() throws Exception {    
		eresource =  eresourceDao.getResourcePassword("");    
		/*assertTrue(eresource.size() > 0);
		assertNotNull(eresource.containsAll(eresource));*/
		
	}
	

	@Test(expected = DataAccessException.class)
	public void testAddResource() throws Exception {    
		
			eresource = eresourceDao.save(eresource); 
			flush(); 
			
			eresource = eresourceDao.get((long) eresource.getEresourceId());     
			assertEquals("Country", eresource.getResourceName());    
			assertNotNull(eresource.getEresourceId());     
			
			log.debug("updating eresource...");     	
	}
	
	@Test(expected = DataAccessException.class)
	public void testUpdateResource() throws Exception {    
				
			eresource = eresourceDao.save(eresource); 
			flush(); 
			
			eresource = eresourceDao.get((long) eresource.getEresourceId());     
			assertEquals("Country", eresource.getDescription());    
			assertNotNull(eresource.getEresourceId());     
			
			log.debug("updating eresource...");     	
	}
	
	@Test(expected = DataAccessException.class)
	public void testDeleteResource() throws Exception {    

			eresource = eresourceDao.save(eresource); 
			flush(); 
			
			eresource = eresourceDao.get((long) eresource.getEresourceId());     
			assertEquals("Test2", eresource.getDescription());    
			assertNotNull(eresource.getEresourceId());     
			
			log.debug("removing eresource...");     
			
			eresourceDao.remove(eresource.getEresourceId());    
			flush();     
			
			// should throw DataAccessException    
			eresourceDao.get((long) eresource.getEresourceId());
	}
	

}

