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

import za.ac.unisa.lms.tool.eresources.dao.EresourceDao;
import za.ac.unisa.lms.tool.eresources.model.Eresource;
import za.ac.unisa.lms.tool.eresources.model.Vendor;
import za.ac.unisa.lms.tool.eresources.service.impl.EresourceManagerImpl;

public class EresourceManagerImplTest extends BaseManagerMockTestCase {

	 	private final Log log = LogFactory.getLog(EresourceManagerImplTest.class);
		private EresourceManagerImpl manager = null;    
		private EresourceDao dao = null;     
		
		@Before    
		public void setUp() {       
			dao = context.mock(EresourceDao.class);       
			manager = new EresourceManagerImpl(dao);    
		}    
		
		@After    
		public void tearDown() {        
			manager = null;    
		}   
		

		@Test    
		public void testSelectAllVendorAndPlacementList() {        
			log.debug("testing get featured database...");         
			final List<Eresource> eresources = new ArrayList<Eresource>();         
			
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).selectAllVendorAndPlacementList(0, 0, 0);            
				will(returnValue(eresources));        
				}});        
			List<Eresource> result = manager.selectAllVendorAndPlacementList(0, 0, 0);        
			assertSame(eresources, result);   
		}   
		
		@Test    
		public void testSelectAllVendorsLinkedToResourceList() {        
			log.debug("testing get featured database...");         
			
			final List<Vendor> vendor = new ArrayList<Vendor>(); 
			
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).selectAllVendorAndPlacementList(0, 0, 0);            
				will(returnValue(vendor));        
				}});        
			List<Eresource> result = manager.selectAllVendorAndPlacementList(0, 0, 0);         
			assertSame(vendor, result);   
		} 
		
		@Test    
		public void testselectAllPlacementsLinkedToResourceList() {        
			log.debug("testing get featured database...");         
			final List<Eresource> eresources = new ArrayList<Eresource>();         
			
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).selectAllPlacementsLinkedToResourceList(0, 0);            
				will(returnValue(eresources));        
				}});        
			List<Eresource> result = manager.selectAllPlacementsLinkedToResourceList(0, 0);         
			assertSame(eresources, result);   
		}    
		@Test    
		public void testSelectEresourceById() {        
			log.debug("testing find Eresource note by Id");        
			final Long eresourceId = 7L;        
			final Eresource eresource = new Eresource();         
			
			// set expected behavior on DAO 
			
			context.checking(new Expectations() {
				{            
					one(dao).get(with(equal(eresourceId)));           
					will(returnValue(eresource));       
				}
			});         
			Eresource result = manager.get(eresourceId);        
			assertSame(eresource, result);    
		}   
		
		@Test    
		public void testGetResourcePassword() {        
			log.debug("testing find Eresource logo");        
			final Eresource eresources = new Eresource();         
			
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).getResourcePassword("eresource101");            
				will(returnValue(eresources));        
				}});        
			Eresource result = manager.getResourcePassword("eresource101");         
			assertSame(eresources, result);     
		}    
		
		@Test    
		public void testFindAll() {        
			log.debug("testing getAll Eresources...");         
			final ArrayList<Eresource> eresources = new ArrayList<Eresource>();         
			
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).getAll();            
				will(returnValue(eresources));        
				}});        
			List<Eresource> result = manager.getAll();         
			assertSame(eresources, result);   
		}     
		
		@Test    
		public void testSelectEresourceByName() {        
			log.debug("testing find eresource by name field...");         
			final List<Eresource> eresources = new ArrayList<Eresource>();         
			
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).selectResourceByName("Text");            
				will(returnValue(eresources));        
				}});        
			List<Eresource> result = manager.selectResourceByName("Text");         
			assertSame(eresources, result);   
		}  
		
		@Test    
		public void testSelectSpecificDB() {        
			log.debug("testing find logo url location...");         
			final List<Eresource> eresources = new ArrayList<Eresource>();         
			
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).selectSpecificDb(0,"A" ,"media/logos/eresources/img013.jpg");            
				will(returnValue(eresources));        
				}});        
			List<Eresource> result = manager.selectSpecificDb(0 ,"A" ,"media/logos/eresources/img013.jpg");         
			assertSame(eresources, result);   
		}  
		
		@Test    
		public void testGetPassword() {        
			log.debug("testing get password...");         
			final List<Eresource> eresources = new ArrayList<Eresource>();         
			
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).getPassword("");            
				will(returnValue(eresources));        
				}});        
			List<Eresource> result = manager.getPassword("");         
			assertSame(eresources, result);   
		}    
		
		@Test    
		public void testGetFeaturedDatabase() {        
			log.debug("testing get featured database...");         
			final List<Eresource> eresources = new ArrayList<Eresource>();         
			
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).getFeaturedDatabase("", true);            
				will(returnValue(eresources));        
				}});        
			List<Eresource> result = manager.getFeaturedDatabase("", true);         
			assertSame(eresources, result);   
		}    
		
		@Test    
		public void testAddEresource() {       
			log.debug("testing save...");         
			final Eresource eresource = new Eresource();        
			// enter all required fields                 
			
			// set expected behavior on DAO       
			context.checking(new Expectations() {{            
				one(dao).save(with(same(eresource)));       
				
			}});         
			manager.save(eresource);    
		}     
		
		@Test    
		public void testUpdateEresource() {       
			log.debug("testing update...");         
			final Eresource eresource = new Eresource();        
			// enter all required fields                 
			
			// set expected behavior on DAO      
			context.checking(new Expectations() {{            
				one(dao).updateResource(with(same(eresource)));       
				
			}});         
			manager.updateResource(eresource);    
		}     
		
		@Test    
		public void testDeleteEresource() {        
			log.debug("testing remove...");         
			final Long eresourceId = -11L;         
			// set expected behavior on DAO        
			context.checking(new Expectations() {{           
				one(dao).remove(with(equal(eresourceId)));        
				}});         
			manager.remove(eresourceId);    
		}
}
