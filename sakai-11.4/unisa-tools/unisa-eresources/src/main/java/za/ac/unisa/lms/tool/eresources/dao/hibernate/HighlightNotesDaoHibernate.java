package za.ac.unisa.lms.tool.eresources.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import za.ac.unisa.lms.tool.eresources.dao.HighlightNotesDao;
//import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.model.HighlightNote;

/**
 * @author lebelsg
 * @author TMasibm
 */
@Repository("highlightNotesDao")
public class HighlightNotesDaoHibernate extends GenericDaoHibernate<HighlightNote, Long> implements HighlightNotesDao {
	
	public HighlightNotesDaoHibernate() {
		super(HighlightNote.class);
	}
	

	/**
	 * @param persistentClass
	 * @param sessionFactory
	 */
	public HighlightNotesDaoHibernate(Class<HighlightNote> persistentClass,
			SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
	
	}

	private Logger log;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		log.debug("Session Factory Failed to load");
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<HighlightNote> findAll() {

		List<HighlightNote> listAllHighlightNotes = new ArrayList<HighlightNote>();
		 
		 try{
			 listAllHighlightNotes = getSession().createCriteria(HighlightNote.class).list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllHighlightNotes; 
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<HighlightNote> findHighLightNoteById(Long highlightNoteId) {
		List<HighlightNote> listAllHighlightNotesById = new ArrayList<HighlightNote>();
		 
		 try{
			 listAllHighlightNotesById = getSession().createCriteria(HighlightNote.class).add(Restrictions.eq("highlightNotesID", highlightNoteId)).list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllHighlightNotesById;  
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<HighlightNote> findHighLightNoteByDescr(String descr) {
		List<HighlightNote> listAllHighlightNotesByDescr = new ArrayList<HighlightNote>();
		 
		 try{
			 listAllHighlightNotesByDescr = getSession()
				.createCriteria(HighlightNote.class)
				.add(Restrictions.eq("highlightNote", descr))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listAllHighlightNotesByDescr;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HighlightNote> findHighlightNotesLinked(Long resourceId, Long highlightNoteId) {
		List<HighlightNote> listLinkedHighlightNotes = new ArrayList<HighlightNote>();
		 
		 try{
			 listLinkedHighlightNotes =  getSession()
				.createCriteria(HighlightNote.class)
				.add(Restrictions.eq("eresource", resourceId))
				.add(Restrictions.eq("highlightNotesID", highlightNoteId))
				.list();
	        }catch (Exception e) {

	            log.debug("Unable to search database "+e);
	        }finally{
	        	 
	        	 
	        }
		 return listLinkedHighlightNotes;
		
	}


	@Override
	public boolean highlightInUse(Long highlightNoteId, boolean isEnabled) {
		if(isEnabled = false)		
		try{
			getSession()
			.createCriteria(HighlightNote.class)
			.add(Restrictions.eq("highlightNotesID", highlightNoteId))
			.add(Restrictions.eq("isEnabled", true))
			.list();
		
        }catch (Exception e) {
            log.debug("This highlightInUse is already linked to the Eresource " +e);
        }finally{
        	
        }
		return true;
	}

	@Override
	public void addHighlightNote(HighlightNote highlightNote) {
		 
		try{
	        	highlightNote.getHighlightNotesID();
	        	getSession().save(highlightNote);
	        }catch (Exception e) {
	            log.debug("Unable to add fields to the database, please check the code and correct the errors "+e);
	        }finally{
	        	
	        }
		
		
	}
	@Override
	public void updateHighlightNote(HighlightNote highlightNote) {
		
		try{
			highlightNote.getHighlightNotesID();
			getSession().update(highlightNote);
       }catch (Exception e) {
          
        	log.debug("Unable to update the Placement ranking table "+e);
        }finally{
        	
        }
	}
    @Override
	public void deleteHighlightNote(Long highlightNoteId, boolean inUse) {
		if (inUse = false);
		 try{
			 getSession().createCriteria(HighlightNote.class);
	         getSession().delete(highlightNoteId);

	        }catch (Exception e) {

	            log.debug("The program is unable to delete the records from the detabase "+e);
	        }finally{
	        	 
	        }
		 	
		if( inUse = true);
		log.debug("The highlight note you are trying to delete is currently linked to a database resource");
	}

}
