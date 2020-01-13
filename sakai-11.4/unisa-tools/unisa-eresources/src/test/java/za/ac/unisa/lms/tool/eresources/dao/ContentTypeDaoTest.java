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

import za.ac.unisa.lms.tool.eresources.model.ContentType;

/**
 * @author TMasibm
 *
 */
public class ContentTypeDaoTest extends BaseDaoTestCase{

	@Autowired
    private ContentTypeDao contentTypeDao;
	private ContentType contentType = new ContentType();  

	/**
	 * @param notes
	 */
	 @Before
     public void init() throws SQLException {	
		contentType.setLibTxtID(-22L);    
		contentType.setFullTxtDescr("Bibliographic");
		contentType.setEnabled(true);
    }
	
	@Test
	public void testFindContentTypeById() throws Exception {    
		List<ContentType> textlist = contentTypeDao.findContentTypeById(-2L);    
		
		assertTrue(textlist.size() > 0);
		assertNotNull(textlist);
		assertNotNull(textlist.containsAll(textlist));

	}
	
	@Test
	public void testFindAll() throws Exception {    
		List<ContentType> textlist = contentTypeDao.getAll();    
		
		assertTrue(textlist.size() > 0);
		assertNotNull(textlist);
		assertNotNull(textlist.containsAll(textlist));
		
	}
	
	@Test
	public void testContentTypeInUse() throws Exception {
	 	 boolean contentTypeInUse= contentTypeDao.contentTypeInUse(0L, true);
	
	 	assertTrue("Content in use", true);
	 	assertNotNull(contentTypeInUse);
	 	assertNotNull(contentTypeDao.contentTypeInUse(1L, true));
	}
	@Test
	public void testSelectContentTypeDesc() throws Exception {    
		List<ContentType> textlist = contentTypeDao.selectContentTypeDesc("Bibliography");    
		assertTrue(textlist.size() > 0);
		assertNotNull(textlist.containsAll(textlist));
		assertTrue(!textlist.isEmpty());
	
		
	}
	
	@Test(expected = DataAccessException.class)
	public void testAddContentType() throws Exception {    
		
			contentType = contentTypeDao.get((long) contentType.getLibTxtID());     
			assertEquals("Bibliographic", contentType.getFullTxtDescr());    
			assertNotNull(contentType.getLibTxtID());     
			log.debug("saving contentType...");     
	}
	
	@Test(expected = DataAccessException.class)
	public void testUpdateContentType() throws Exception {    
		
			
			contentType = contentTypeDao.get((long) contentType.getLibTxtID());     
			assertEquals("Country", contentType.getFullTxtDescr());    
			assertNotNull(contentType.getLibTxtID());     
			
			log.debug("updating contentType...");     	
	}
	
	@Test(expected = DataAccessException.class)
	public void testDeleteContentType() throws Exception {    
		
			contentType = contentTypeDao.get((long) contentType.getLibTxtID());     
			assertEquals("Country", contentType.getFullTxtDescr());    
			assertNotNull(contentType.getLibTxtID());     
		
			log.debug("removing contentType...");     
			
			contentTypeDao.deleteContentType(contentType.getLibTxtID()); 
			flush();     
			   
			contentTypeDao.get((long) contentType.getLibTxtID());
	}
	

}

