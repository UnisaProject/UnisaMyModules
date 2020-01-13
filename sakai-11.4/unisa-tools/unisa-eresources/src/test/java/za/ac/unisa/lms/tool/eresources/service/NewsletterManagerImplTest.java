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

import za.ac.unisa.lms.tool.eresources.dao.NewsletterDao;
import za.ac.unisa.lms.tool.eresources.model.Newsletter;
import za.ac.unisa.lms.tool.eresources.service.impl.NewsletterManagerImpl;

public class NewsletterManagerImplTest extends BaseManagerMockTestCase {

	private final Log log = LogFactory.getLog(NewsletterManagerImplTest.class);
	private NewsletterManagerImpl manager = null;    
	private NewsletterDao dao = null;     
	
	@Before    
	public void setUp() {       
		dao = context.mock(NewsletterDao.class);       
		manager = new NewsletterManagerImpl(dao);    
	}    
	
	@After    
	public void tearDown() {        
		manager = null;    
	}   

	@Test    
	public void testFindNewsById() {        
		log.debug("testing find highlight note by Id");        
		final Long id = 7L;        
		final Newsletter newsletter = new Newsletter();         
		
		// set expected behavior on DAO 
		
		context.checking(new Expectations() {
			{            
				one(dao).get(with(equal(id)));           
				will(returnValue(newsletter));       
			}
		});         
		Newsletter result = manager.get(id);        
		assertSame(newsletter, result);    
	}    
	
	@Test    
	public void testFindAll() {        
		log.debug("testing getAll highlight notes...");         
		final ArrayList<Newsletter> newsletters = new ArrayList<Newsletter>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).getAll();            
			will(returnValue(newsletters));        
			}});        
		List<Newsletter> result = manager.getAll();         
		assertSame(newsletters, result);   
	}     
	
	@Test    
	public void testFindNewsTitleByDesc() {        
		log.debug("testing select highlight notes by description field...");         
		final List<Newsletter> newsletters = new ArrayList<Newsletter>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).findNewsTitleByDesc("Text");            
			will(returnValue(newsletters));        
			}});        
		List<Newsletter> result = manager.findNewsTitleByDesc("Text");         
		assertSame(newsletters, result);   
	}  
	
	@Test    
	public void testSelectNewsTitleLinkedd() {        
		log.debug("testing select highlight notes by description field...");         
		final List<Newsletter> newsletters = new ArrayList<Newsletter>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).selectNewsTitleLinked(0L, 0L);            
			will(returnValue(newsletters));        
			}});        
		List<Newsletter> result = manager.selectNewsTitleLinked(0L, 0L);         
		assertSame(newsletters, result);   
	}    
	
	@Test    
	public void testNewsTitleInUse() {        
		log.debug("testing to check whether boolean newsletterInUse is true...");         
		final boolean newsletters = true;         
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{           
			one(dao).newsTitleInUse(0L, true);            
			will(returnValue(newsletters));        
			}});        
		boolean result = manager.newsTitleInUse(0L, true);         
		assertSame(newsletters, result);   
	}     
	
	@Test    
	public void testAddNewsletter() {       
		log.debug("testing save...");         
		final Newsletter newsletter = new Newsletter();        
		// enter all required fields                 
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{            
			one(dao).save(with(same(newsletter)));       
			
		}});         
		manager.save(newsletter);    
	}     
	
	@Test    
	public void testUpdateNewsletter() {       
		log.debug("testing save...");         
		final Newsletter newsletter = new Newsletter();        
		// enter all required fields                 
		
		// set expected behavior on DAO      
		context.checking(new Expectations() {{            
			one(dao).updateNewsTitle(with(same(newsletter)));       
			
		}});         
		manager.updateNewsTitle(newsletter);    
	}     
	
	@Test    
	public void testDeleteNewsletter() {        
		log.debug("testing remove...");         
		final Long newsletterId = -11L;         
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).remove(with(equal(newsletterId)));        
			}});         
		manager.remove(newsletterId);    
	}
}
