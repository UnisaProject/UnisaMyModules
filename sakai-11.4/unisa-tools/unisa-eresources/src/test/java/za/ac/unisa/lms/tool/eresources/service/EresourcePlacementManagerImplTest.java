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

import za.ac.unisa.lms.tool.eresources.dao.EresourcePlacementDao;
import za.ac.unisa.lms.tool.eresources.model.EresourcePlacement;
import za.ac.unisa.lms.tool.eresources.service.impl.EresourcePlacementManagerImpl;

public class EresourcePlacementManagerImplTest extends BaseManagerMockTestCase {

	private final Log log = LogFactory.getLog(EresourcePlacementManagerImplTest.class);
	private EresourcePlacementManagerImpl manager = null;    
	private EresourcePlacementDao dao = null;     
	
	@Before    
	public void setUp() {       
		dao = context.mock(EresourcePlacementDao.class);       
		manager = new EresourcePlacementManagerImpl(dao);    
	}    
	
	@After    
	public void tearDown() {        
		manager = null;    
	}   
	
	@Test    
	public void testFindEresourcePlacementById() {        
		log.debug("testing find EresourcePlacement by Id");        
		final Long resourcePlacementId = 7L;        
		final EresourcePlacement resourcePlacement = new EresourcePlacement();         
		
		// set expected behavior on DAO 
		
		context.checking(new Expectations() {
			{            
				one(dao).get(with(equal(resourcePlacementId)));           
				will(returnValue(resourcePlacement));       
			}
		});         
		EresourcePlacement result = manager.get(resourcePlacementId);        
		assertSame(resourcePlacement, result);    
	}   
	
	@Test    
	public void testFindAll() {        
		log.debug("testing getAll EresourcePlacements...");         
		final ArrayList<EresourcePlacement> resourcePlacements = new ArrayList<EresourcePlacement>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).getAll();            
			will(returnValue(resourcePlacements));        
			}});        
		List<EresourcePlacement> result = manager.getAll();         
		assertSame(resourcePlacements, result);   
	}     
	
	
	
	@Test    
	public void testAddEresourcePlacement() {       
		log.debug("testing save...");         
		final EresourcePlacement resourcePlacement = new EresourcePlacement();        
		// enter all required fields                 
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{            
			one(dao).save(with(same(resourcePlacement)));       
			
		}});         
		manager.save(resourcePlacement);    
	}     
	
	@Test    
	public void testUpdateEresourcePlacement() {       
		log.debug("testing update...");         
		final EresourcePlacement resourcePlacement = new EresourcePlacement();        
		// enter all required fields                 
		
		// set expected behavior on DAO      
		context.checking(new Expectations() {{            
			one(dao).updateResPlacement(with(same(resourcePlacement)));       
			
		}});         
		manager.updateResPlacement(resourcePlacement);    
	}     
	
	@Test    
	public void testDeleteEresourcePlacement() {        
		log.debug("testing remove...");         
		final Long resourcePlacementId = -11L;         
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).remove(with(equal(resourcePlacementId)));        
			}});         
		manager.remove(resourcePlacementId);    
	}
}
