package za.ac.unisa.lms.tool.eresources.webapp.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import za.ac.unisa.lms.tool.eresources.model.Newsletter;
import za.ac.unisa.lms.tool.eresources.service.NewsletterManager;

@Controller
@RequestMapping("/newsletterform*") //form to implement the CRUD functionality of the newsLetter
public class NewsletterFormController extends BaseFormController {
	
	private NewsletterManager newsletterManager;
	
	@Autowired    
	public void setNewsletterManager(@Qualifier("newsletterManager")
	NewsletterManager newsletterManager) {
				this.newsletterManager = newsletterManager;    
	} 
	
	public NewsletterFormController() {
		setCancelView("redirect:newsLetter");
		setSuccessView("redirect:newsLetter");
	}
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	protected Newsletter showForm(HttpServletRequest request)    throws Exception { 
		String id = request.getParameter("id");
		
		if (!StringUtils.isBlank(id)) {
			return newsletterManager.get(new Long(id));
		}         
		
		return new Newsletter();
	}
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(Newsletter newsletter, BindingResult errors, HttpServletRequest request, 
					HttpServletResponse response)
	throws Exception {
			if (request.getParameter("cancel") != null) { 
				return getCancelView(); 
			} 
			if (validator != null) { // validator is null during testing 
				validator.validate(newsletter, errors); 
				
				if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting 
					return "newsletterform";
				}
			}
			
			log.debug("entering 'onSubmit' method...");
			
			boolean isNew = ( newsletter.getNewsTitleID() == null);
			String success = getSuccessView();
			Locale locale = request.getLocale();
			
			if (request.getParameter("delete") != null) {
				newsletterManager.deleteNewsTitle(newsletter.getNewsTitleID(), newsletter.isEnabled());
				saveMessage(request, getText("newsLetter.deleted", locale));
			} 
			else if  (isNew = (newsletter.getNewsTitleID() != null)){
				newsletterManager.updateNewsTitle(newsletter);
				String updateKey =  "newsLetter.updated";
				saveMessage(request, getText(updateKey, locale));
				
				if (!isNew) { 
					success = "redirect:newsLetter?id=" + newsletter.getNewsTitleID();
				
				}
				
			}   else {
				newsletterManager.addNewsTitle(newsletter); 
				String key = "newsLetter.added" ;
				saveMessage(request, getText(key, locale));
				
				if (!isNew) { 
					success = "redirect:newsLetter?id=" + newsletter.getNewsTitleID();
				}
			}
			
			return success;		
	}

}
