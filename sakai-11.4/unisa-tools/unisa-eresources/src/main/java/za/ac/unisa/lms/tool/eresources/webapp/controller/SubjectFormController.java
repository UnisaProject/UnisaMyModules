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
import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.model.Subject;
import za.ac.unisa.lms.tool.eresources.service.SubjectManager;

@Controller
@RequestMapping("/subjectform*")
public class SubjectFormController extends BaseFormController {
private SubjectManager SubjectManager =null;

	
	@Autowired    
	public void setSubjectManager(@Qualifier("SubjectManager")
	SubjectManager SubjectManager) {
				this.SubjectManager = SubjectManager;    
	} 
	
	public SubjectFormController() {
		setCancelView("redirect:subjects");
		setSuccessView("redirect:subjects");
	}
	@ModelAttribute
	@RequestMapping(value ="/subjectform*", method = RequestMethod.GET)
	protected ModelAndView showForm(HttpServletRequest request)    throws Exception { 
		String subjectid = request.getParameter("id");
		ModelAndView modelAndView = new ModelAndView("/subjectform");
		
		if (!StringUtils.isBlank(subjectid)) {
			Subject subject = SubjectManager.get(new Long(subjectid));
			modelAndView.addObject("subject",subject);
            return modelAndView;
		} 
		modelAndView.addObject("subject",new Subject());
        return modelAndView;
	}
	
	@RequestMapping(value ="/subjectform*",method = RequestMethod.POST)
	public ModelAndView onSubmit(Subject subject, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		if (request.getParameter("cancel") != null) { 
			return new ModelAndView(getCancelView());
		} 
		
			if (validator != null) { // validator is null during testing 
				validator.validate(subject, errors); 
				
				if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting 
					return new ModelAndView("/subjectform");
				}
			}
			
			log.debug("entering 'onSubmit' method...");
			
			boolean isNew = (subject.getSubjectId() == null);
			String success = getSuccessView();
			Locale locale = request.getLocale();
			
			if (request.getParameter("delete") != null) {
				SubjectManager.deleteSubject(subject.getSubjectId(), subject.isEnabled());
				saveMessage(request, getText("subjects.deleted", locale));
			}
			else if  (isNew = (subject.getSubjectId() != null)){
				SubjectManager.updateSubject(subject);
				String updateKey =  "subjects.updated";
				saveMessage(request, getText(updateKey, locale));
				
				if (!isNew) { 
					success = "redirect:subjects?id=" + subject.getSubjectId();
				
				}
				
			}  else {
				SubjectManager.addSubject(subject); 
				String key = "subjects.added";
				saveMessage(request, getText(key, locale));
				
				if (!isNew) { 
					success = "redirect:subjects?id=" + subject.getSubjectId();
				}
				
			}
			
			return new ModelAndView("/subjects").addObject("subjectsList",SubjectManager.findAll());	
	}
}

/*@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(ContentType contentType, BindingResult errors, HttpServletRequest request, 
					HttpServletResponse response)
	throws Exception {
			
		//Get cancel view if Cancel parameter is selected
			if (request.getParameter("cancel") != null) { 
				return getCancelView(); 
			} 
			
		//Action to take if validator is not null	
			if (validator != null) { // validator is null during testing 
				validator.validate(contentType, errors); 
				
				if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting 
					return "contentTypeForm";
				}
			}
			
			log.debug("entering 'onSubmit' method...");
			
			boolean isNew = (contentType.getLibTxtID() == null);
			
			String success = getSuccessView();
			Locale locale = request.getLocale();
			
			if (request.getParameter("delete") != null) {
				contentTypeManager.deleteContentType(contentType.getLibTxtID(), contentType.isEnabled());
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
			return success;		
	}
	*/
