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

import za.ac.unisa.lms.tool.eresources.model.Newsletter;

/**
 * @author TMasibm
 *
 */
public class NewsletterDaoTest extends BaseDaoTestCase{

	@Autowired
    private NewsletterDao newsletterDao;
	
	private Newsletter newsletter = new Newsletter();  

	/**
	 * @param newsletter
	 */

	  @Before
	     public void init() throws SQLException {
	    
		  /*	newsletter.setNewsTitleID(0);
			newsletter.setNewsTitle("Gupta Gate Scandal Article");
			newsletter.setEresource(null);
			newsletter.setEnabled(true);*/
	    }
	
	  
		@Test
		public void testFindNewsById() throws Exception {    
			List<Newsletter> newsletter = newsletterDao.findNewsById(1L);    
			assertTrue(newsletter.size() > 0);
			assertNotNull(newsletter.containsAll(newsletter));
			
		}
		
		@Test
		public void testFindAll() throws Exception {    
			List<Newsletter> newsletter = newsletterDao.findAll();    
			assertTrue(newsletter.size() > 0);
			assertNotNull(newsletter.containsAll(newsletter));
			
		}
		
		@Test
		public void testFindNewsTitleByDesc() throws Exception {    
			List<Newsletter> newsletter = newsletterDao.findNewsTitleByDesc("Incidents");    
			assertTrue(newsletter.size() > 0);
			assertNotNull(newsletter.containsAll(newsletter));
			
		}
		
		/*@Test
		public void testSelectNewsTitleLinked() throws Exception {    
			List<Newsletter> newsletter = newsletterDao.selectNewsTitleLinked(0L, 0L);    
			assertTrue(newsletter.size() > 0);
			assertNotNull(newsletter.containsAll(newsletter));
			
		}*/
		
		@Test
		public void testNewsletterInUse() throws Exception {    
			boolean newsletterInUse = newsletterDao.newsTitleInUse(0L, true);    
						
			 	assertTrue("Newsletter in use", true);
			 	assertNotNull(newsletterInUse);
			 	assertNotNull(newsletterDao.newsTitleInUse(0L, true));
		}
		
		
		@Test(expected = DataAccessException.class)
		public void testAddNewsTitle() throws Exception {    
			   
			newsletter.setNewsTitle("Country");    
			newsletter.setNewsTitleID(20L);     
			
			newsletter = newsletterDao.save(newsletter);    
			flush(); 
			
			newsletter = newsletterDao.get((long) newsletter.getNewsTitleID());     
			
			assertEquals("Country", newsletter.getNewsTitle());    
			assertNotNull(newsletter.getNewsTitleID());     
			
			// should throw DataAccessException   
			newsletterDao.get((long) newsletter.getNewsTitleID());
		}
	
		@Test(expected = DataAccessException.class)
		public void testUpdateNewsTitle() throws Exception {    
			    
			newsletter.setNewsTitle("Country");    
			newsletter.setNewsTitleID(20L);     
			
			assertEquals("Country", newsletter.getNewsTitle());    
			assertNotNull(newsletter.getNewsTitleID());     
			
			log.debug("removing newsletter...");    
			newsletterDao.updateNewsTitle(newsletter);    
			
		//	flush();    
			
			// should throw DataAccessException   
		//	newsletterDao.getNewsById(newsletter.getNewsTitleID());
			}
	
		
		@Test(expected = DataAccessException.class)
		public void testDeleteNewsTitle() throws Exception {    
	
				newsletter.setNewsTitle("Incidents");    
				newsletter.setNewsTitleID(-20L);   
				flush(); 
				
				newsletter = newsletterDao.get((long) newsletter.getNewsTitleID());     
				assertEquals("Incidents", newsletter.getNewsTitle());    
				assertNotNull(newsletter.getNewsTitleID());     
				
				log.debug("removing newsletter...");     
				
				//newsletterDao.remove(newsletter.getNewsTitleID());
				newsletterDao.deleteNewsTitle(newsletter.getNewsTitleID(), true);
				flush();     
				
				// should throw DataAccessException    
				newsletterDao.get((long) newsletter.getNewsTitleID());
		}
		

}

