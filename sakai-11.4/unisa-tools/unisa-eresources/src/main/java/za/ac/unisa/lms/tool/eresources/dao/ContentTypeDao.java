/**
 * 
 */
package za.ac.unisa.lms.tool.eresources.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import za.ac.unisa.lms.tool.eresources.model.ContentType;


/**
 * @author TMasibm
 *
 */
public interface ContentTypeDao extends GenericDao<ContentType, Long> {

	public List<ContentType> findAll(); 
	
	public List<ContentType> findContentTypeById(Long libTxtId);
 	
	public List<ContentType> selectContentTypeDesc(String textDesc);
	
 	public boolean contentTypeInUse(Long txtId, boolean isEnabled);
 	
 	public void addContentType(ContentType contentType);
 		
 	public void updateContentType(ContentType contentTyped);
	
	public void deleteContentType(Long libTxtId);
 	
}
