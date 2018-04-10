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
public class SubjectDaoTest extends BaseDaoTestCase{

	@Autowired
    private SubjectDao SubjectDao;
	private Subject Subject = new Subject();  
	
	/**
	 * @param Subject
	 */
	  @Before
	     public void init() throws SQLException {
	    
		    Subject.setSubjectId(2L);
			Subject.setSubjectName("Geography");
			Subject.setEnabled(false);
	    }
	
	
	@Test
	public void testFindAll() throws Exception {
		List<Subject> Subject =  SubjectDao.findAll();
		assertTrue(Subject.size() > 0);
	}
	
	@Test
	public void testSelectSpecificSubject() throws Exception {    
		List<Subject> Subject = SubjectDao.selectSpecificSubject("Economics");    
		assertTrue(Subject.size() > 0);
		assertNotNull(Subject.containsAll(Subject));
		
	}
	
	@Test
	public void testSelectSubjectById() throws Exception {
		List<Subject> Subjectlist = SubjectDao.selectSubjectById(1L);       
		
		assertTrue(Subjectlist.size() > 0);
		assertNotNull(Subjectlist.containsAll(Subjectlist));
		
	}
	
	/*@Test
	public void testFindSubjectLinked() throws Exception {    
		List<Subject> Subject = SubjectDao.findSubjectLinked(0L, 0L);  
		assertTrue(Subject.size() > 0);
		assertNotNull(Subject.containsAll(Subject));
	}*/
	
	@Test
	public void testSubjectInUse() throws Exception {  
		
		 boolean inUse= SubjectDao.subjectInUse(2L, true);
			
		 	assertTrue("Subject in use", true);
		 	assertNotNull(inUse);
		 	assertNotNull(SubjectDao.subjectInUse(0L, true));
	}
	
 
	@Test
	public void testClearSubjectId() throws Exception {   
			// SubjectDao.clearSubjectId(0L, false);
			 
		//	 assertNotNull(SubjectDao.clearSubjectId(0, false));
	}
	
	
	@Test(expected = DataAccessException.class)
	public void testAddSubject() throws Exception {    
		Subject.setSubjectId(3L);
		Subject.setSubjectName("Geography1");
		Subject = SubjectDao.save(Subject);
		flush();
			Subject = SubjectDao.get((long) Subject.getSubjectId());     
			assertEquals("Geography1", Subject.getSubjectName());    
			assertNotNull(Subject.getSubjectId());     
			log.debug("saving Subject...");     
	}
	
	@Test(expected = DataAccessException.class)
	public void testUpdateSubject() throws Exception {    
		Subject.setSubjectId(3L);
		Subject.setSubjectName("Geography");
		Subject = SubjectDao.save(Subject);
		flush();		
			Subject = SubjectDao.get((long) Subject.getSubjectId());     
			assertEquals("Geography", Subject.getSubjectName());    
			assertNotNull(Subject.getSubjectId());     
			
			log.debug("updating Subject...");     	
	}
	
	@Test(expected = DataAccessException.class)
	public void testDeleteSubject() throws Exception {    
		
			Subject.setSubjectId(-27L);
		
			Subject = SubjectDao.get((long) Subject.getSubjectId());     
			assertEquals("Economics", Subject.getSubjectName());    
			assertNotNull(Subject.getSubjectId());     
			
			log.debug("removing Subject...");     
			
			SubjectDao.deleteSubject(Subject.getSubjectId(), false);    
			flush();     
			
			// should throw DataAccessException    
			SubjectDao.get((long) Subject.getSubjectId());
	}
	

}

