package za.ac.unisa.lms.tool.eresources.service.impl;

import java.util.List;

import org.appfuse.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.ac.unisa.lms.tool.eresources.dao.NewsletterDao;
import za.ac.unisa.lms.tool.eresources.model.Newsletter;
import za.ac.unisa.lms.tool.eresources.service.NewsletterManager;

/**
 * @author Lebelsg
 *
 */

@Service("newsletterManager")
public class NewsletterManagerImpl extends GenericManagerImpl<Newsletter, Long> implements NewsletterManager{

	NewsletterDao newsletterDao;
	
	@Autowired
	public NewsletterManagerImpl(NewsletterDao newsletterDao) {
		super(newsletterDao);
		this.newsletterDao = newsletterDao;
	}

	@Override
	public List<Newsletter> findNewsById(Long newsletterId) {
		return this.newsletterDao.findNewsById(newsletterId);
		
	}

	@Override
	public List<Newsletter> findNewsTitleByDesc(String newsDesc) {
		return this.newsletterDao.findNewsTitleByDesc(newsDesc );
		
	}

	@Override
	public List<Newsletter> findAll() {
		return this.newsletterDao.findAll();
		
	}

	@Override
	public List<Newsletter> selectNewsTitleLinked(Long resourceId,Long newsletterId) {
		return this.newsletterDao.selectNewsTitleLinked(resourceId , newsletterId);
		
	}

	@Override
	public boolean newsTitleInUse(Long newsletterId, boolean isEnabled) {
		return this.newsletterDao.newsTitleInUse(newsletterId , isEnabled);
	}

	@Override
	public void addNewsTitle(Newsletter newsTitle) {	
		this.newsletterDao.addNewsTitle(newsTitle);
	}

	@Override
	public void updateNewsTitle(Newsletter newsTitle) {
		this.newsletterDao.updateNewsTitle(newsTitle);
	}


	@Override
	public void deleteNewsTitle(Long newsletterId , boolean inUse) {	
		this.newsletterDao.deleteNewsTitle(newsletterId, inUse);
		
	}
		
}
