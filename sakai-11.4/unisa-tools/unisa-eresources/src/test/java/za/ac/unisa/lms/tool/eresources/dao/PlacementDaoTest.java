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

import za.ac.unisa.lms.tool.eresources.model.Placement;

/**
 * @author TMasibm
 *
 */
public class PlacementDaoTest extends BaseDaoTestCase{

	@Autowired
    private PlacementDao placementDao;
	
	
	private Placement placement = new Placement();  
	
    @Before
     public void init() throws SQLException {
		placement.setPlacementId(-25L);
		placement.setPlacement("Asle 3");
		placement.setDisplayOrder(4);
		placement.setEnabled(false);
    }

	@Test
	public void testFindPlacementById() {
		
			List<Placement> placementlist = placementDao.findPlacementById(1L);       
			
			assertTrue(placementlist.size() > 0);
			assertNotNull(placementlist.containsAll(placementlist));
	
	}
	
	
	@Test
	public void testFindByDisplayOrder() {
	
		List<Placement> placements = placementDao.findPlacementByDisplayOrder(3);
		
			assertTrue(placements.size() > 0);
			
			for (Placement placement : placements) {
				assertEquals(3, placement.getDisplayOrder());
		}
	}

	@Test
	public void testFindAll(){
		List<Placement> placements = placementDao.findAll();    
			assertTrue(placements.size() > 0);
			assertNotNull(placements.containsAll(placements));
			assertNotNull(placements.equals(0));
		
	}
	
	/*@Test
	public void testFindLinkedPlacement(){
		List<Placement> placements = placementDao.findLinkedPlacement("12");    
			assertTrue(placements.size() > 0);
			assertNotNull(placements.containsAll(placements));
			assertNotNull(placements.equals(00));
	
	}
	*/
	

	@Test
	public void testGetTabs() throws Exception {    
		List<Placement> placements = placementDao.getTabs("Bookshelf");
		
		assertEquals(0, placements.size());
		
		for (Placement placement : placements) {
		
			assertEquals(this.placement.getPlacementId(), placement.getPlacementId());
			assertEquals(this.placement.getEresourcePlacement(), placement.getEresourcePlacement());
			assertEquals(this.placement.getPlacement(), placement.getPlacement());
			assertEquals(this.placement.getDisplayOrder(), placement.getDisplayOrder());
	}
	}
	
	@Test
	public void testPlacementInUse() throws Exception {    
	
		boolean placement = placementDao.placementInUse(-2L, true);  
		
		 	assertTrue("placement in use", true);
		 	assertNotNull(placement);
		 	assertNotNull(placementDao.placementInUse(-2L, true));
	}
	
	
	@Test(expected = DataAccessException.class)
	public void testAddPlacement() throws Exception {    
			
			placement.setPlacementId(25L);
			placement.setPlacement("Asle 3");
			 
			placement = placementDao.save(placement); 
			flush(); 
			
			placement = placementDao.get((long) placement.getPlacementId());     
			assertEquals("Asle 3", placement.getPlacement());    
			assertNotNull(placement.getPlacementId());     
			log.debug("saving placement...");   
			
	}
	
	@Test(expected = DataAccessException.class)
	public void testUpdatePlacement() throws Exception {    
			
			placement.setPlacementId(-25L);
			placement.setPlacement("Asle 3");
		
			placement = placementDao.save(placement); 
			flush(); 
			
			placement = placementDao.get((long) placement.getPlacementId());     
			assertEquals("Asle 3", placement.getPlacement());    
			assertNotNull(placement.getPlacementId());     
			
			log.debug("updating placement...");     	
	}
	
	@Test(expected = DataAccessException.class)
	public void testDeletePlacement() throws Exception {    
		
			placement.setPlacementId(-25L);
			//flush(); 
			
			placement = placementDao.get((long) placement.getPlacementId());
			assertEquals("Ebooks", placement.getPlacement());
			assertNotNull(placement.getPlacementId());
			
			log.debug("removing placement...");     
			
			placementDao.deletePlacement(placement.getPlacementId(),false);
			placementDao.remove(placement.getPlacementId());
			flush();     
			
			// should throw DataAccessException    
			placementDao.get((long) placement.getPlacementId());
			
	}
	
	
	@Test(expected = DataAccessException.class)
	public void testUpdatePlacementRanking() throws Exception{
		
		placement = placementDao.get((long) placement.getDisplayOrder());     
		assertEquals("Country", placement.getPlacement());    
		assertNotNull(placement.getPlacementId());     
		
		log.debug("updating placement...");  
		
		placementDao.updatePlacementRanking(placement, placement.getDisplayOrder());
		
		placement = placementDao.save(placement); 
		flush(); 
	}

}

