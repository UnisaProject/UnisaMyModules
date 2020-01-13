package za.ac.unisa.lms.tool.eresources.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import za.ac.unisa.lms.tool.eresources.model.Subject;


/**
 * @author lebelsg
 *
 */
public interface SubjectDao extends GenericDao<Subject, Long> {

	
	/***************************************************************************************
	 * 
	 * These methods for Subject class Maintain
	 *  
	 ***************************************************************************************/
	
		public List<Subject> findAll(); 
		  
	    public List<Subject> selectSpecificSubject( String subjectName);
	 
	    public List<Subject> selectSubjectById(Long subjectId);
	    
		public List<Subject> findSubjectLinked(Long resourceId, Long subjectId);
				
		public boolean subjectInUse(Long subjectId, boolean isEnabled);
			    
		public void addSubject(Subject subject);
		
		public void updateSubject(Subject subject);
		
		public void deleteSubject(Long subjectId, boolean inUse);

		public void clearSubjectId(Long subjectId, boolean isEnabled);
		

}
