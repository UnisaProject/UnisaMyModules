package za.ac.unisa.lms.tool.eresources.service;

import java.util.List;

import org.appfuse.service.GenericManager;

import za.ac.unisa.lms.tool.eresources.model.Newsletter;

/**
 * @author TMasibm
 *
 */
public interface NewsletterManager  extends GenericManager<Newsletter, Long>  {

	public List<Newsletter> findNewsById(Long newsletterId); 
	  
 	public List<Newsletter>  findNewsTitleByDesc(String newsDesc); 
 	
	public List<Newsletter> findAll(); 
	
	public List<Newsletter> selectNewsTitleLinked(Long resourceId, Long newsletterId);
			
	public boolean newsTitleInUse(Long newsletterId, boolean isEnabled);

	public void addNewsTitle(Newsletter newsTitle);
	
	public void updateNewsTitle(Newsletter newsTitle);

	public void deleteNewsTitle(Long newsletterId , boolean inUse);
}

