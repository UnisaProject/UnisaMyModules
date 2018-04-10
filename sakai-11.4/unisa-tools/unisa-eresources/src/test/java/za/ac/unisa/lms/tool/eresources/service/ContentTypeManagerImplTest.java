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

import za.ac.unisa.lms.tool.eresources.dao.ContentTypeDao;
import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.service.impl.ContentTypeManagerImpl;

public class ContentTypeManagerImplTest extends BaseManagerMockTestCase {   
	
    private final Log log = LogFactory.getLog(ContentTypeManagerImplTest.class);
	private ContentTypeManagerImpl manager = null;    
	private ContentTypeDao dao = null;     
	
	@Before    
	public void setUp() {       
		dao = context.mock(ContentTypeDao.class);       
		manager = new ContentTypeManagerImpl(dao);    
	}    
	
	@After    
	public void tearDown() {        
		manager = null;    
	}     
	
	@Test    
	public void testFindContentType() {        
		log.debug("testing find content type by Id");        
		final Long id = 7L;        
		final ContentType contentType = new ContentType();         
		
		// set expected behavior on DAO 
		
		context.checking(new Expectations() {
			{            
				one(dao).get(with(equal(id)));           
				will(returnValue(contentType));       
			}
		});         
		ContentType result = manager.get(id);        
		assertSame(contentType, result);    
	}    
	
	@Test    
	public void testFindAll() {        
		log.debug("testing getAll...");         
		final ArrayList<ContentType> contentTypes = new ArrayList<ContentType>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).getAll();            
			will(returnValue(contentTypes));        
			}});        
		List<ContentType> result = manager.getAll();         
		assertSame(contentTypes, result);   
	}     
	@Test    
	public void testSelectContentTypeDesc() {        
		log.debug("testing select content type by description field...");         
		final List<ContentType> contentTypes = new ArrayList<ContentType>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).selectContentTypeDesc("Text");            
			will(returnValue(contentTypes));        
			}});        
		List<ContentType> result = manager.selectContentTypeDesc("Text");         
		assertSame(contentTypes, result);   
	}     
	@Test    
	public void testContentTypeInUse() {        
		log.debug("testing to check whether boolean contentTypeInUse is true...");         
		final boolean contentTypes = true;         
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{           
			one(dao).contentTypeInUse(0L, true);            
			will(returnValue(contentTypes));        
			}});        
		boolean result = manager.contentTypeInUse(0L, true);         
		assertSame(contentTypes, result);   
	}     
	@Test    
	public void testAddContentType() {       
		log.debug("testing save...");         
		final ContentType contentType = new ContentType();        
		// enter all required fields                 
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{            
			one(dao).save(with(same(contentType)));       
			
		}});         
		manager.save(contentType);    
	}     
	@Test    
	public void testUpdateContentType() {       
		log.debug("testing save...");         
		final ContentType contentType = new ContentType();        
		// enter all required fields                 
		
		// set expected behavior on DAO      
		context.checking(new Expectations() {{            
			one(dao).updateContentType(with(same(contentType)));       
			
		}});         
		manager.updateContentType(contentType);    
	}     
	@Test    
	public void testDeleteContentType() {        
		log.debug("testing remove...");         
		final Long id = -11L;         
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).remove(with(equal(id)));        
			}});         
		manager.remove(id);    
	}
}