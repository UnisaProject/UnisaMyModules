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
import org.springframework.web.servlet.ModelAndView;

import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.service.ContentTypeManager;

@Controller
//@RequestMapping() //form to implement the CRUD functionality of the content type
public class ContentTypeFormController extends BaseFormController {
	private ContentTypeManager contentTypeManager = null;
	
	
	@Autowired    
	public void setContentTypeManager(@Qualifier("contentTypeManager")
	ContentTypeManager contentTypeManager) {
				this.contentTypeManager = contentTypeManager;    
		
	} 
	
	public ContentTypeFormController() {
		setCancelView("redirect:contentTypes");
		setSuccessView("redirect:contentTypes");
	}
	@ModelAttribute
	@RequestMapping(value="/contenttypeform*", method = RequestMethod.GET)
	protected ModelAndView showForm(HttpServletRequest request)  throws Exception { 
		String contentTypeId = request.getParameter("id");
		
		ModelAndView modelAndView = new ModelAndView("/contenttypeform");
		
		if (!StringUtils.isBlank(contentTypeId)) {
			ContentType contentType = contentTypeManager.get(new Long(contentTypeId));
			modelAndView.addObject("contentType",contentType);
			return modelAndView;
		}
		modelAndView.addObject("contentType",new ContentType());
		return modelAndView;
	}
	
	@RequestMapping(value="/contenttypeform*", method = RequestMethod.POST)
	public ModelAndView onSubmit(ContentType contentType, BindingResult errors, HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		//Get cancel view if Cancel parameter is selected
			if (request.getParameter("cancel") != null) { 
				return new ModelAndView(getCancelView()); 
			} 
			
		//Action to take if validator is not null	
			if (validator != null) { // validator is null during testing 
				validator.validate(contentType, errors); 
				
				if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting 
					//System.out.
					return new ModelAndView("/contenttypeform");
				}
			}
			
			log.debug("entering 'onSubmit' method...");
			
			boolean isNew = (contentType.getLibTxtID() == null);
			
			String success = getSuccessView();
			Locale locale = request.getLocale();
			
			if (request.getParameter("delete") != null) {
				contentTypeManager.deleteContentType(contentType.getLibTxtID());
				saveMessage(request, getText("contentType.deleted", locale)); 
			} 
			else if  (isNew = (contentType.getLibTxtID() != null)){
				contentTypeManager.updateContentType(contentType);
				String updateKey =  "contentType.updated";
				saveMessage(request, getText(updateKey, locale));
				
				if (!isNew) { 
					success = "redirect:contentTypes?id=" + contentType.getLibTxtID();
				
				}
				
			}  else {
					contentTypeManager.addContentType(contentType); 
					String key = "contentType.added";
					saveMessage(request, getText(key, locale));
						
					if (!isNew) { 
						success = "redirect:contentTypes?id=" + contentType.getLibTxtID();
					}
			}	
			return new ModelAndView("/contentTypes").addObject("contentTypeList",contentTypeManager.findAll());		
	}
	
	
}	
	