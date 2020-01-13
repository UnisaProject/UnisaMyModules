package za.ac.unisa.lms.tool.eresources.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.unisa.lms.tool.eresources.dao.SubjectDao;
import za.ac.unisa.lms.tool.eresources.model.Subject;
import za.ac.unisa.lms.tool.eresources.service.SubjectManager;

/**
 * @author tmasibm
 *
 */
@Service("SubjectManager")
public class SubjectManagerImpl extends GenericManagerImpl<Subject, Long> implements SubjectManager{

	SubjectDao SubjectDao;
	
	@Autowired
	public SubjectManagerImpl(SubjectDao SubjectDao) {
		super(SubjectDao);
		this.SubjectDao = SubjectDao;
	}


	@Override
	public List<Subject> findAll() {
		
		return this.SubjectDao.findAll();
	}


	@Override
	public List<Subject> selectSpecificSubject( String subjectName) {
		return this.SubjectDao.selectSpecificSubject(subjectName);
	}

	@Override
	public List<Subject> selectSubjectById(Long subjectId) {
		return this.SubjectDao.selectSubjectById( subjectId);
		
	}

	@Override
	public List<Subject> findSubjectLinked(Long resourceId, Long subjectId) {
		return this.SubjectDao.findSubjectLinked( resourceId,  subjectId);
		
	}

	@Override
	public boolean subjectInUse(Long subjectId, boolean isEnabled) {		
		return this.SubjectDao.subjectInUse(subjectId, isEnabled) ;
		
	}

	@Override
	public void clearSubjectId(Long subjectId, boolean isEnabled) {
		
		this.SubjectDao.clearSubjectId(subjectId, isEnabled);	
		
	}

	@Override
	public void addSubject(Subject subject) {
		this.SubjectDao.addSubject( subject);
		
	}

	@Override
	public void updateSubject(Subject subject) {
		this.SubjectDao.updateSubject(subject);

	}

	@Override
	public void deleteSubject(Long subjectId, boolean inUse) {
		this.SubjectDao.deleteSubject( subjectId,  inUse);

	}

}
