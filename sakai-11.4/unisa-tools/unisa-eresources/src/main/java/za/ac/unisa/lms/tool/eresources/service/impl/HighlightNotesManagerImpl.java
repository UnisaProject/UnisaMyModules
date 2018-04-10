package za.ac.unisa.lms.tool.eresources.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.unisa.lms.tool.eresources.dao.HighlightNotesDao;
import za.ac.unisa.lms.tool.eresources.model.HighlightNote;
import za.ac.unisa.lms.tool.eresources.service.HighlightNotesManager;

@Service("highlightNotesManager")
public class HighlightNotesManagerImpl extends GenericManagerImpl<HighlightNote, Long> implements HighlightNotesManager{

	HighlightNotesDao highlightNotesDao;
	
	@Autowired
	public HighlightNotesManagerImpl(HighlightNotesDao highlightNotesDao) {
		super(highlightNotesDao);
		this.highlightNotesDao = highlightNotesDao;
	}

	@Override
	public List<HighlightNote> findAll() {
		return this.highlightNotesDao.findAll();
		
	}

	@Override
	public List<HighlightNote> findHighLightNoteById(Long highlightNoteId) {
		return this.highlightNotesDao.findHighLightNoteById(highlightNoteId);
		
	}

	@Override
	public List<HighlightNote> findHighLightNoteByDescr(String descr ) {
		return this.highlightNotesDao.findHighLightNoteByDescr(descr);
		
	}

	@Override
	public List<HighlightNote> findHighlightNotesLinked(Long resourceId,Long highlightNoteId) {
		return this.highlightNotesDao.findHighlightNotesLinked(resourceId, highlightNoteId);
		
	}

	@Override
	public boolean highlightInUse(Long highlightNoteId, boolean isEnabled) {
		return this.highlightNotesDao.highlightInUse(highlightNoteId, isEnabled);
		
	}

	@Override
	public void addHighlightNote(HighlightNote highlightNote) {
		this.highlightNotesDao.addHighlightNote(highlightNote);
		
	}

	@Override
	public void updateHighlightNote(HighlightNote highlightNote) {
		this.highlightNotesDao.updateHighlightNote(highlightNote);
		
	}

	@Override
	public void deleteHighlightNote(Long highlightNoteId,  boolean inUse) {
		this.highlightNotesDao.deleteHighlightNote(highlightNoteId, inUse);
		
	}
	
}
