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

import za.ac.unisa.lms.tool.eresources.model.HighlightNote;

/**
 * @author TMasibm
 *
 */
public class HighlightNotesDaoTest extends BaseDaoTestCase{

	@Autowired
    private HighlightNotesDao highlightNotesDao;
	private HighlightNote notes = new HighlightNote();  
	
	
	/**
	 * @param notes
	 */
	 @Before
     public void init() throws SQLException {
			
		    notes.setHighlightNotesID(-31L);
		    notes.setHighlightNote("Quality Improvement");
		    notes.setEnabled(true);
    }
	
	@Test
	public void testFindAll() throws Exception {    
		List<HighlightNote> notesList = highlightNotesDao.findAll();    
		
		assertTrue(notesList.size() > 0);
		assertNotNull(notesList.containsAll(notesList));
		
	}
	
	@Test
	public void testFindHighLightNoteById() throws Exception {    
		List<HighlightNote> notes = highlightNotesDao.findHighLightNoteById(-3L);    
		
		assertTrue(notes.size() > 0);
		assertNotNull(notes.containsAll(notes));
		
	}

	@Test
	public void testFindtHighLightNoteByDescr() throws Exception {    
		List<HighlightNote> notes = highlightNotesDao.findHighLightNoteByDescr("Quality Improvement");    
		
		assertTrue(notes.size() > 0);
		assertNotNull(notes.containsAll(notes));
		
	}
	/*@Test
	public void testFindHighlightNotesLinked() throws Exception {    
		List<HighlightNote>  notes = highlightNotesDao.findHighlightNotesLinked(1L, 0L);    
		
		assertTrue(notes.size() > 0);
		assertNotNull(notes.containsAll(notes));
	}*/
	
	@Test
	public void testHighlightInUse() throws Exception {    
		boolean  inUse = highlightNotesDao.highlightInUse(0L, true);    
	
		assertTrue("Highlight Note in use", true);
	 	assertNotNull(inUse);
	 	assertNotNull(highlightNotesDao.highlightInUse(1L, true));
	}
		
	@Test(expected = DataAccessException.class)
	public void testAddHighlightNote() throws Exception {    
			notes.setHighlightNotesID(-100L);
			notes.setHighlightNote("Research Reviews");
			
			notes = highlightNotesDao.save(notes); 
			flush(); 
			
			notes = highlightNotesDao.get((long) notes.getHighlightNotesID());     
			assertEquals("Research Reviews", notes.getHighlightNote());    
			assertNotNull(notes.getHighlightNotesID());     
			log.debug("saving notes...");     
	}
	
	@Test(expected = DataAccessException.class)
	public void testUpdateHighlightNote() throws Exception {    
			notes.setHighlightNotesID(-100L);
			notes.setHighlightNote("Research Reviews");
		
			notes = highlightNotesDao.save(notes); 
			flush(); 
			
			notes = highlightNotesDao.get((long) notes.getHighlightNotesID());     
			assertEquals("Research Reviews", notes.getHighlightNote());    
			assertNotNull(notes.getHighlightNotesID());     
			
			log.debug("updating notes...");     	
	}
	
	@Test(expected = DataAccessException.class)
	public void testDeleteHighlightNote() throws Exception {    

		    flush();
		    
			notes = highlightNotesDao.get((long) notes.getHighlightNotesID());  
			
			assertEquals("Quality Improvement", notes.getHighlightNote());    
			assertNotNull(notes.getHighlightNotesID());     
			
			log.debug("removing notes...");     
			
			highlightNotesDao.deleteHighlightNote(notes.getHighlightNotesID(), false);    
			flush();     
			
			// should throw DataAccessException    
			highlightNotesDao.get((long) notes.getHighlightNotesID());
	}

}

