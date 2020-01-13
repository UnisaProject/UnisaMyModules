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

import za.ac.unisa.lms.tool.eresources.model.Subject;

/**
 * @author TMasibm
 *
 */
public class SubjectsDaoTest extends BaseDaoTestCase{

	@Autowired
    private SubjectsDao subjectsDao;
	private Subject subject = new Subject();  
	
	/**
	 * @param subject
	 */
	  @Before
	     public void init() throws SQLException {
	    
		    subject.setSubjectId(2L);
			subject.setSubjectName("Geography");
			subject.setEnabled(false);
	    }
	
	
	@Test
	public void testFindAll() throws Exception {
		List<Subject> subject =  subjectsDao.findAll();
		assertTrue(subject.size() > 0);
	}
	
	@Test
	public void testSelectSpecificSubject() throws Exception {    
		List<Subject> subject = subjectsDao.selectSpecificSubject("Economics");    
		assertTrue(subject.size() > 0);
		assertNotNull(subject.containsAll(subject));
		
	}
	
	@Test
	public void testSelectSubjectById() throws Exception {
		List<Subject> subjectslist = subjectsDao.selectSubjectById(1L);       
		
		assertTrue(subjectslist.size() > 0);
		assertNotNull(subjectslist.containsAll(subjectslist));
		
	}
	
	/*@Test
	public void testFindSubjectLinked() throws Exception {    
		List<Subject> subject = subjectsDao.findSubjectLinked(0L, 0L);  
		assertTrue(subject.size() > 0);
		assertNotNull(subject.containsAll(subject));
	}*/
	
	@Test
	public void testSubjectInUse() throws Exception {  
		
		 boolean inUse= subjectsDao.subjectInUse(2L, true);
			
		 	assertTrue("Subject in use", true);
		 	assertNotNull(inUse);
		 	assertNotNull(subjectsDao.subjectInUse(0L, true));
	}
	
 
	@Test
	public void testClearSubjectId() throws Exception {   
			// subjectsDao.clearSubjectId(0L, false);
			 
		//	 assertNotNull(subjectsDao.clearSubjectId(0, false));
	}
	
	
	@Test(expected = DataAccessException.class)
	public void testAddSubjects() throws Exception {    
		subject.setSubjectId(3L);
		subject.setSubjectName("Geography1");
		subject = subjectsDao.save(subject);
		flush();
			subject = subjectsDao.get((long) subject.getSubjectId());     
			assertEquals("Geography1", subject.getSubjectName());    
			assertNotNull(subject.getSubjectId());     
			log.debug("saving subject...");     
	}
	
	@Test(expected = DataAccessException.class)
	public void testUpdateSubjects() throws Exception {    
		subject.setSubjectId(3L);
		subject.setSubjectName("Geography");
		subject = subjectsDao.save(subject);
		flush();		
			subject = subjectsDao.get((long) subject.getSubjectId());     
			assertEquals("Geography", subject.getSubjectName());    
			assertNotNull(subject.getSubjectId());     
			
			log.debug("updating subject...");     	
	}
	
	@Test(expected = DataAccessException.class)
	public void testDeleteSubjects() throws Exception {    
		
			subject.setSubjectId(-27L);
		
			subject = subjectsDao.get((long) subject.getSubjectId());     
			assertEquals("Economics", subject.getSubjectName());    
			assertNotNull(subject.getSubjectId());     
			
			log.debug("removing subject...");     
			
			subjectsDao.deleteSubject(subject.getSubjectId(), false);    
			flush();     
			
			// should throw DataAccessException    
			subjectsDao.get((long) subject.getSubjectId());
	}
	

}

