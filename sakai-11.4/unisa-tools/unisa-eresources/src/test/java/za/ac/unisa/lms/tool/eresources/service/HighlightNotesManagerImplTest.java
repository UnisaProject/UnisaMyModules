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

import za.ac.unisa.lms.tool.eresources.dao.HighlightNotesDao;
import za.ac.unisa.lms.tool.eresources.model.HighlightNote;
import za.ac.unisa.lms.tool.eresources.service.impl.HighlightNotesManagerImpl;

public class HighlightNotesManagerImplTest extends BaseManagerMockTestCase {

	private final Log log = LogFactory.getLog(HighlightNotesManagerImplTest.class);
	private HighlightNotesManagerImpl manager = null;    
	private HighlightNotesDao dao = null;     
	
	@Before    
	public void setUp() {       
		dao = context.mock(HighlightNotesDao.class);       
		manager = new HighlightNotesManagerImpl(dao);    
	}    
	
	@After    
	public void tearDown() {        
		manager = null;    
	} 
	
	
	@Test    
	public void testFindHighlightNoteById() {        
		log.debug("testing find highlight note by Id");        
		final Long id = 7L;        
		final HighlightNote highlightNote = new HighlightNote();         
		
		// set expected behavior on DAO 
		
		context.checking(new Expectations() {
			{            
				one(dao).get(with(equal(id)));           
				will(returnValue(highlightNote));       
			}
		});         
		HighlightNote result = manager.get(id);        
		assertSame(highlightNote, result);    
	}    
	
	@Test    
	public void testFindAll() {        
		log.debug("testing getAll highlight notes...");         
		final ArrayList<HighlightNote> highlightNote = new ArrayList<HighlightNote>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).getAll();            
			will(returnValue(highlightNote));        
			}});        
		List<HighlightNote> result = manager.getAll();         
		assertSame(highlightNote, result);   
	}     
	
	@Test    
	public void testFindHighlightNoteByDesc() {        
		log.debug("testing select highlight notes by description field...");         
		final List<HighlightNote> highlightNote = new ArrayList<HighlightNote>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).findHighLightNoteByDescr("Text");            
			will(returnValue(highlightNote));        
			}});        
		List<HighlightNote> result = manager.findHighLightNoteByDescr("Text");         
		assertSame(highlightNote, result);   
	}  
	
	@Test    
	public void testFindHighlightNotesLinked() {        
		log.debug("testing select highlight notes by description field...");         
		final List<HighlightNote> highlightNote = new ArrayList<HighlightNote>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).findHighlightNotesLinked(0L, 0L);            
			will(returnValue(highlightNote));        
			}});        
		List<HighlightNote> result = manager.findHighlightNotesLinked(0L, 0L);         
		assertSame(highlightNote, result);   
	}    
	
	@Test    
	public void testFindHighlightNotesInUse() {        
		log.debug("testing to check whether boolean highlightNoteInUse is true...");         
		final boolean highlightNotes = true;         
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{           
			one(dao).highlightInUse(0L, true);            
			will(returnValue(highlightNotes));        
			}});        
		boolean result = manager.highlightInUse(0L, true);         
		assertSame(highlightNotes, result);   
	}     
	
	@Test    
	public void testAddHighlightNotes() {       
		log.debug("testing save...");         
		final HighlightNote highlightNote = new HighlightNote();        
		// enter all required fields                 
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{            
			one(dao).save(with(same(highlightNote)));       
			
		}});         
		manager.save(highlightNote);    
	}     
	
	@Test    
	public void testUpdateHighlightNotes() {       
		log.debug("testing save...");         
		final HighlightNote highlightNote = new HighlightNote();        
		// enter all required fields                 
		
		// set expected behavior on DAO      
		context.checking(new Expectations() {{            
			one(dao).updateHighlightNote(with(same(highlightNote)));       
			
		}});         
		manager.updateHighlightNote(highlightNote);    
	}     
	
	@Test    
	public void testDeleteHighlightNotes() {        
		log.debug("testing remove...");         
		final Long id = -11L;         
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).remove(with(equal(id)));        
			}});         
		manager.remove(id);    
	}
}
