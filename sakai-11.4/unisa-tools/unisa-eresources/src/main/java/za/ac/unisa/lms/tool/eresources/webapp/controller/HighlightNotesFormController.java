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

import za.ac.unisa.lms.tool.eresources.model.HighlightNote;
import za.ac.unisa.lms.tool.eresources.service.HighlightNotesManager;


@Controller
//@RequestMapping("/highlightnoteform*") //form to implement the CRUD functionality of the highlight Notes highlightnotesform

public class HighlightNotesFormController extends BaseFormController{

private HighlightNotesManager highlightNotesManager;
	
	@Autowired    
	public void setHighlightNotesManager(@Qualifier("highlightNotesManager")
	HighlightNotesManager highlightNotesManager) {
				this.highlightNotesManager = highlightNotesManager;    
	} 
	

	public HighlightNotesFormController() {
		setCancelView("redirect:highlightNotes");
		setSuccessView("redirect:highlightNotes");
	}
	
	@ModelAttribute
	@RequestMapping(value="/highlightnotesform*", method = RequestMethod.GET)
	protected ModelAndView showForm(HttpServletRequest request)    throws Exception { 
		String highlightNotesID = request.getParameter("id");
		
    ModelAndView modelAndView = new ModelAndView("/highlightnotesform");
		if (!StringUtils.isBlank(highlightNotesID)) {
            
            HighlightNote highlightnote = highlightNotesManager.get(new Long(highlightNotesID));
            modelAndView.addObject("highlightnote",highlightnote);
			return modelAndView;
		}         
		modelAndView.addObject("highlightnote", new HighlightNote());
		return modelAndView;
	}
	
	@RequestMapping(value="highlightnotesform*", method = RequestMethod.POST)
	public ModelAndView onSubmit(HighlightNote highlightnote, BindingResult errors, HttpServletRequest request,HttpServletResponse response) throws Exception {
			if (request.getParameter("cancel") != null) { 
				return new ModelAndView(getCancelView()); 
			} 
			if (validator != null) { // validator is null during testing 
				validator.validate(highlightnote, errors); 
				
				if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting 
					return new ModelAndView("/highlightnotesform");
					
				}
			}
			
			log.debug("entering 'onSubmit' method...");
			
			boolean isNew = (highlightnote.getHighlightNotesID() == null);
			String success = getSuccessView();
			Locale locale = request.getLocale();
			
			if (request.getParameter("delete") != null) {
				highlightNotesManager.deleteHighlightNote(highlightnote.getHighlightNotesID(), highlightnote.isEnabled());
				saveMessage(request, getText("highlightnote.deleted", locale));
			}
			else if (isNew = (highlightnote.getHighlightNotesID()!= null)){
				
				highlightNotesManager.updateHighlightNote(highlightnote);

				String updateKey =  "highlightnote.updated";
				saveMessage(request, getText(updateKey, locale));
				
				if (!isNew) { 
					success = "redirect:highlightNotes?id=" + highlightnote.getHighlightNotesID();
				}
				
			}  

			else {
				highlightNotesManager.addHighlightNote(highlightnote); 
				String key = "highlightnote.added";
				saveMessage(request, getText(key, locale));
				
				if (!isNew) { 
					success = "redirect:highlightNotes?id=" + highlightnote.getHighlightNotesID();
				}
			}
			
			return new ModelAndView("/highlightNotes").addObject("highlightNotesList",highlightNotesManager.findAll());		
			
	}
}