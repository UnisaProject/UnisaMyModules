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

import za.ac.unisa.lms.tool.eresources.dao.PlacementDao;
import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.service.impl.PlacementManagerImpl;

public class PlacementManagerImplTest extends BaseManagerMockTestCase {

	private final Log log = LogFactory.getLog(PlacementManagerImplTest.class);
	private PlacementManagerImpl manager = null;    
	private PlacementDao dao = null;     
	
	@Before    
	public void setUp() {       
		dao = context.mock(PlacementDao.class);       
		manager = new PlacementManagerImpl(dao);    
	}    
	
	@After    
	public void tearDown() {        
		manager = null;    
	}   

	/*

	

	
	public void updatePlacementRanking(Placement placement, int plcRanking);

*/
	@Test    
	public void testFindPlacementById() {        
		log.debug("testing find content type by Id");        
		final Long id = 7L;        
		final Placement placement = new Placement();         
		
		// set expected behavior on DAO 
		
		context.checking(new Expectations() {
			{            
				one(dao).get(with(equal(id)));           
				will(returnValue(placement));       
			}
		});         
		Placement result = manager.get(id);        
		assertSame(placement, result);    
	}    
	
	@Test    
	public void testFindAllPlacements() {        
		log.debug("testing getting all placements...");         
		final ArrayList<Placement> placement = new ArrayList<Placement>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).getAll();            
			will(returnValue(placement));        
			}});        
		List<Placement> result = manager.getAll();         
		assertSame(placement, result);   
	}     
	@Test    
	public void testFindPlacementDisplayOrder() {        
		log.debug("testing select content type by description field...");         
		final List<Placement> placement = new ArrayList<Placement>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).findPlacementByDisplayOrder(0);            
			will(returnValue(placement));        
			}});        
		List<Placement> result = manager.findPlacementByDisplayOrder(0);         
		assertSame(placement, result);   
	}   
	
	@Test    
	public void testFindLinkedPlacement() {        
		log.debug("testing select content type by description field...");         
		final List<Placement> placement = new ArrayList<Placement>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).findLinkedPlacement("E-Books");            
			will(returnValue(placement));        
			}});        
		List<Placement> result = manager.findLinkedPlacement("E-Books");         
		assertSame(placement, result);   
	}   
	
	@Test    
	public void testGetTabs() {        
		log.debug("testing select content type by description field...");         
		final List<Placement> placement = new ArrayList<Placement>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).getTabs("E-Books");            
			will(returnValue(placement));        
			}});        
		List<Placement> result = manager.getTabs("E-Books");         
		assertSame(placement, result);   
	}     
	
	@Test    
	public void testPlacementInUse() {        
		log.debug("testing to check whether boolean placementInUse is true...");         
		final boolean placement = true;         
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{           
			one(dao).placementInUse(0L, true);            
			will(returnValue(placement));        
			}});        
		boolean result = manager.placementInUse(0L, true);         
		assertSame(placement, result);   
	}     
	@Test    
	public void testAddPlacement() {       
		log.debug("testing save...");         
		final Placement placement = new Placement();        
		// enter all required fields                 
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{            
			one(dao).save(with(same(placement)));       
			
		}});         
		manager.save(placement);    
	}     
	@Test    
	public void testUpdatePlacement() {       
		log.debug("testing save...");         
		final Placement placement = new Placement();        
		// enter all required fields                 
		
		// set expected behavior on DAO      
		context.checking(new Expectations() {{            
			one(dao).updatePlacement(with(same(placement)));       
			
		}});         
		manager.updatePlacement(placement);    
	}  
	@Test    
	public void testUpdatePlacementRanking() {       
		log.debug("testing save...");         
		final Placement placement = new Placement(); 
		final Integer plcRanking = 1;
		// enter all required fields                 
		
		// set expected behavior on DAO      
		context.checking(new Expectations() {{            
			 
			one(dao).updatePlacementRanking(placement, plcRanking);       
			
		}});         
		manager.updatePlacementRanking(placement, plcRanking);    
	}  

	@Test    
	public void testDeletePlacement() {        
		log.debug("testing remove...");         
		final Long id = -11L;         
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).remove(with(equal(id)));        
			}});         
		manager.remove(id);    
	}
}
