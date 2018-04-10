package za.ac.unisa.lms.tool.eresources.service;

import java.util.List;

import org.appfuse.service.GenericManager;

import za.ac.unisa.lms.tool.eresources.model.ContentType;

/**
 * @author TMasibm
 *
 */
public interface ContentTypeManager  extends GenericManager<ContentType, Long>  {
	
	public List<ContentType> findAll(); 
	
	public List<ContentType> findContentTypeById(Long libTxtId);
 	
	public List<ContentType> selectContentTypeDesc(String textDesc);
	
 	public boolean contentTypeInUse(Long txtId, boolean isEnabled);
 	
 	public void addContentType(ContentType contentType);
 		
 	public void updateContentType(ContentType contentType);
	
	public void deleteContentType(Long libTxtId);

}

