package za.ac.unisa.lms.tool.eresources.service;

import java.util.List;

import org.appfuse.service.GenericManager;

import za.ac.unisa.lms.tool.eresources.model.Subject;

/**
 * @author TMasibm
 *
 */
public interface SubjectManager extends GenericManager<Subject, Long> {

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
