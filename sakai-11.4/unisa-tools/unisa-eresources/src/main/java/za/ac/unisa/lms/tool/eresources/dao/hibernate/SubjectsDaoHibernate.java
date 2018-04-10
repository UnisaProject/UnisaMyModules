package za.ac.unisa.lms.tool.eresources.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import za.ac.unisa.lms.tool.eresources.dao.SubjectsDao;
import za.ac.unisa.lms.tool.eresources.model.Subject;

/**
 * @author lebelsg
 * @author TMasibm
 */
@Repository("subjectDao")

public class SubjectsDaoHibernate extends GenericDaoHibernate<Subject, Long> implements SubjectsDao{

	/**
	 * 
	 */
	public SubjectsDaoHibernate() {
		super(Subject.class);
	}
	
	/**
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public SubjectsDaoHibernate(Class<Subject> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
	
	}

	private Logger log;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		log.debug("Session Factory Failed to load");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> findAll(){
		
		List<Subject> listAllSubjects = new ArrayList<Subject>();
		 try{
			 listAllSubjects = getSessionFactory()
			 .getCurrentSession()
			 .createCriteria(Subject.class)
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllSubjects;
 
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> selectSpecificSubject(String subjectName) {
		List<Subject> listSpecificSubjects = new ArrayList<Subject>();
		 try{
			 listSpecificSubjects = getSessionFactory().getCurrentSession()
				.createCriteria(Subject.class)
				.add(Restrictions.eq("subjectName", subjectName))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listSpecificSubjects;
 
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> selectSubjectById(Long subjectId) {
		List<Subject> listSubjectsById = new ArrayList<Subject>();
		 try{
			 listSubjectsById = getSession().createCriteria(Subject.class)
				.add(Restrictions.eq("subjectId", subjectId))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listSubjectsById;
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> findSubjectLinked(Long eresourceSubject, Long subjectId) {
		List<Subject> listLinkedSubjects = new ArrayList<Subject>();
		 try{
			 listLinkedSubjects =  getSession().createCriteria(Subject.class)
				.add(Restrictions.eq("subjectId", subjectId))
				.add(Restrictions.eq("eresourceSubject", eresourceSubject))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listLinkedSubjects; 
	}

	
	@Override
	public boolean subjectInUse(Long subjectId, boolean isEnabled) {
		if (isEnabled = false)
		 try{
	         getSession()
	         .createCriteria(Subject.class)
	         .add(Restrictions.eq("subjectId", subjectId))
	         .add(Restrictions.eq("isEnabled", true))
	         .list();

	        }catch (Exception e) {

	            log.debug("The following subject is in use "+e);
	        }finally{
	        	 
	        }
		 	
		 return true;
	}

	@Override
	public void clearSubjectId(Long subjectId, boolean isEnabled) {
		if (isEnabled != false);
		 try{
	         getSession()
	         .createCriteria(Subject.class)
	         .add(Restrictions.eq("subjectId", subjectId))
	         .add(Restrictions.eq("isEnabled", isEnabled))
	         .list();
	         isEnabled = false;
	        }catch (Exception e) {

	            log.debug("The following subject is in use "+e);
	        }finally{
	        	 
	        }
		 	
		 return;
		
	}


	@Override
	public void addSubject(Subject subject) {
		 try{
	        	subject.getSubjectId();
	        	getSession().save(subject);
	        }catch (Exception e) {
	            log.debug("Unable to add fields to the database, please check the code and correct the errors "+e);
	        }finally{
	        	
	        }
		
		
	}


	@Override
	public void updateSubject(Subject subject) {
		try{
			subject.getSubjectId();
			getSession().update(subject);
        }catch (Exception e) {
            log.debug("Unable to update the Subject table "+e);
        }finally{
        	
        }
		
	}

	@Override
	public void deleteSubject(Long subjectId, boolean inUse) {
		if (inUse = false);
		 try{
			 getSession().createCriteria(Subject.class);
	         getSession().delete(subjectId);

	        }catch (Exception e) {

	            log.debug("The program is unable to delete the records from the detabase "+e);
	        }finally{
	        	 
	        }
		 	
		 inUse = true;
		
	}

	
}
