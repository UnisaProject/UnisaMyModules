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

import za.ac.unisa.lms.tool.eresources.dao.SubjectDao;
import za.ac.unisa.lms.tool.eresources.model.Subject;
import za.ac.unisa.lms.tool.eresources.service.impl.SubjectManagerImpl;

public class SubjectManagerImplTest extends BaseManagerMockTestCase {

	private final Log log = LogFactory.getLog(SubjectManagerImplTest.class);
	private SubjectManagerImpl manager = null;    
	private SubjectDao dao = null;     
	
	@Before    
	public void setUp() {       
		dao = context.mock(SubjectDao.class);       
		manager = new SubjectManagerImpl(dao);    
	}    
	
	@After    
	public void tearDown() {        
		manager = null;    
	}   

	@Test    
	public void testSelectSubjectById() {        
		log.debug("testing find content type by Id");        
		final Long id = 7L;        
		final Subject Subject = new Subject();         
		
		// set expected behavior on DAO 
		
		context.checking(new Expectations() {
			{            
				one(dao).get(with(equal(id)));           
				will(returnValue(Subject));       
			}
		});         
		Subject result = manager.get(id);        
		assertSame(Subject, result);    
	}    
	
	@Test    
	public void testFindAll() {        
		log.debug("testing getAll...");         
		final ArrayList<Subject> Subject = new ArrayList<Subject>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).getAll();            
			will(returnValue(Subject));        
			}});        
		List<Subject> result = manager.getAll();         
		assertSame(Subject, result);   
	}   
	
	@Test    
	public void testFindSubjectLinked() {        
		log.debug("testing getAll...");         
		final ArrayList<Subject> Subject = new ArrayList<Subject>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).findSubjectLinked(0L, 0L);            
			will(returnValue(Subject));        
			}});        
		List<Subject> result = manager.findSubjectLinked(0L, 0L);         
		assertSame(Subject, result);   
	}  
	@Test    
	public void testSelectSelectSpecificSubject() {        
		log.debug("testing find all Subject by description field...");         
		final List<Subject> Subject = new ArrayList<Subject>();         
		
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).selectSpecificSubject("Geography");            
			will(returnValue(Subject));        
			}});        
		List<Subject> result = manager.selectSpecificSubject("Geography");         
		assertSame(Subject, result);   
	}     
	@Test    
	public void testSubjectInUse() {        
		log.debug("testing to check whether boolean subjectInUse is true...");         
		final boolean Subject = true;         
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{           
			one(dao).subjectInUse(0L, true);            
			will(returnValue(Subject));        
			}});        
		boolean result = manager.subjectInUse(0L, true);         
		assertSame(Subject, result);   
	}  
	
	@Test    
	public void testClearSubjectId() {        
		log.debug("testing to check whether ...");      
		// enter all required fields                 
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{            
			one(dao).clearSubjectId(0L, true);     
			
		}});         
		manager.clearSubjectId(0L, true);     
	}     
	
	@Test    
	public void testAddSubject() {       
		log.debug("testing save...");         
		final Subject subject = new Subject();        
		// enter all required fields                 
		
		// set expected behavior on DAO       
		context.checking(new Expectations() {{            
			one(dao).save(with(same(subject)));       
			
		}});         
		manager.save(subject);    
	}     
	@Test    
	public void testUpdateSubject() {       
		log.debug("testing update subject...");         
		final Subject subject = new Subject();        
		// enter all required fields                 
		
		// set expected behavior on DAO      
		context.checking(new Expectations() {{            
			one(dao).updateSubject(with(same(subject)));       
			
		}});         
		manager.updateSubject(subject);    
	}     
	@Test    
	public void testDeleteSubject() {        
		log.debug("testing remove...");         
		final Long id = -11L;         
		// set expected behavior on DAO        
		context.checking(new Expectations() {{           
			one(dao).remove(with(equal(id)));        
			}});         
		manager.remove(id);    
	}
}
