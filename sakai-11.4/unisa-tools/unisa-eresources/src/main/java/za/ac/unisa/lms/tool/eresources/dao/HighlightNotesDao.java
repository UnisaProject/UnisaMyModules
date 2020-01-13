package za.ac.unisa.lms.tool.eresources.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import za.ac.unisa.lms.tool.eresources.model.HighlightNote;

/**
 * @author TMasibm
 *
 */
public interface HighlightNotesDao extends GenericDao <HighlightNote , Long> {


	/***************************************************************************************
	 * 
	 * These methods for HighlightNote class Maintain / Front End
	 * 
	 ***************************************************************************************/

		public List<HighlightNote> findAll();
		
		public List<HighlightNote> findHighLightNoteById(Long highlightNoteId);
		
		public List<HighlightNote> findHighLightNoteByDescr(String descr);
			
		public List<HighlightNote> findHighlightNotesLinked(Long resourceId, Long highlightNoteId);
		
		public boolean highlightInUse(Long highlightNoteId, boolean isEnabled);
	
		public void addHighlightNote(HighlightNote highlightNote);
		
		public void updateHighlightNote(HighlightNote highlightNote) ;
	
		public void deleteHighlightNote(Long highlightNoteId,  boolean inUse);	
	
}
