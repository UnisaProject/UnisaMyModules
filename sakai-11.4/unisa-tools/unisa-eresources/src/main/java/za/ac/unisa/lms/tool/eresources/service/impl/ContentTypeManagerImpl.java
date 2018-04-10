package za.ac.unisa.lms.tool.eresources.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.unisa.lms.tool.eresources.dao.ContentTypeDao;
import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.service.ContentTypeManager;


/**
 * @author lebelsg
 *
 */
@Service("contentTypeManager")
public class ContentTypeManagerImpl extends GenericManagerImpl<ContentType, Long> implements ContentTypeManager{

	ContentTypeDao contentTypeDao;
	
	@Autowired
	public ContentTypeManagerImpl(ContentTypeDao contentTypeDao) {
		super(contentTypeDao);
		this.contentTypeDao = contentTypeDao;
	}

	@Override
	public List<ContentType> findAll() {
		return contentTypeDao.findAll();
		
	}
	
	@Override
	public List<ContentType> selectContentTypeDesc(String textDesc) {
		return this.contentTypeDao.selectContentTypeDesc(textDesc) ;
		
	}

	@Override
	public boolean contentTypeInUse(Long txtId, boolean isEnabled) {
		return this.contentTypeDao.contentTypeInUse(txtId, true);
		
	}

	@Override
	public void addContentType(ContentType contentType) {
		this.contentTypeDao.addContentType(contentType);
		
	}

	@Override
	public void deleteContentType(Long libTxtId) {
		this.contentTypeDao.deleteContentType(libTxtId);
		
	}

	@Override
	public void updateContentType(ContentType textId) {
		this.contentTypeDao.updateContentType(textId);
		
	}

	@Override
	public List<ContentType> findContentTypeById(Long libTxtId) {
		return this.contentTypeDao.findContentTypeById(libTxtId);
		
	}

}
