package za.ac.unisa.lms.tool.eresources.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.unisa.lms.tool.eresources.dao.SubjectsDao;
import za.ac.unisa.lms.tool.eresources.model.Subject;
import za.ac.unisa.lms.tool.eresources.service.SubjectsManager;

/**
 * @author tmasibm
 *
 */
@Service("subjectsManager")
public class SubjectsManagerImpl extends GenericManagerImpl<Subject, Long> implements SubjectsManager{

	SubjectsDao subjectsDao;
	
	@Autowired
	public SubjectsManagerImpl(SubjectsDao subjectsDao) {
		super(subjectsDao);
		this.subjectsDao = subjectsDao;
	}


	@Override
	public List<Subject> findAll() {
		
		return this.subjectsDao.findAll();
	}


	@Override
	public List<Subject> selectSpecificSubject( String subjectName) {
		return this.subjectsDao.selectSpecificSubject(subjectName);
	}

	@Override
	public List<Subject> selectSubjectById(Long subjectId) {
		return this.subjectsDao.selectSubjectById( subjectId);
		
	}

	@Override
	public List<Subject> findSubjectLinked(Long resourceId, Long subjectId) {
		return this.subjectsDao.findSubjectLinked( resourceId,  subjectId);
		
	}

	@Override
	public boolean subjectInUse(Long subjectId, boolean isEnabled) {		
		return this.subjectsDao.subjectInUse(subjectId, isEnabled) ;
		
	}

	@Override
	public void clearSubjectId(Long subjectId, boolean isEnabled) {
		
		this.subjectsDao.clearSubjectId(subjectId, isEnabled);	
		
	}

	@Override
	public void addSubject(Subject subject) {
		this.subjectsDao.addSubject( subject);
		
	}

	@Override
	public void updateSubject(Subject subject) {
		this.subjectsDao.updateSubject(subject);

	}

	@Override
	public void deleteSubject(Long subjectId, boolean inUse) {
		this.subjectsDao.deleteSubject( subjectId,  inUse);

	}

}
