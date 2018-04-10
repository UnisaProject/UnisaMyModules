package za.ac.unisa.lms.tool.eresources.dao;

import java.util.List;

import org.appfuse.dao.GenericDao;

import za.ac.unisa.lms.tool.eresources.model.Newsletter;



/**
 * @author TMasibm
 *
 */
public interface NewsletterDao  extends GenericDao<Newsletter, Long> {
	
	/***************************************************************************************
	 * 
	 * These methods for Newsletter class Maintain
	 * 
	 ***************************************************************************************/
	
	 	public List<Newsletter> findNewsById(Long newsletterId);
	  
	 	public List<Newsletter>  findNewsTitleByDesc(String newsDesc);
	 	
		public List<Newsletter> findAll(); 
		
		public List<Newsletter> selectNewsTitleLinked(Long resourceId, Long newsletterId);
				
		public boolean newsTitleInUse(Long newsletterId, boolean isEnabled);

		public void addNewsTitle(Newsletter newsTitle);
		
		public void updateNewsTitle(Newsletter newsTitle);

		public void deleteNewsTitle(Long newsletterId , boolean inUse);
	   
}




