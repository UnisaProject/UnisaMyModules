package za.ac.unisa.lms.tool.eresources.webapp.controller;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*import me.m1key.springshowcase.editors.GenderEditor;
import me.m1key.springshowcase.to.Gender;
import me.m1key.springshowcase.to.PersonTo;
import me.m1key.springshowcase.validators.PersonRegistrationValidator;*/

import org.apache.cxf.annotations.Policy.Placement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.model.Eresource;
import za.ac.unisa.lms.tool.eresources.model.Subject;
import za.ac.unisa.lms.tool.eresources.model.Vendor;
import za.ac.unisa.lms.tool.eresources.service.EresourceManager;
import za.ac.unisa.lms.tool.eresources.service.impl.EresourceManagerImpl;

@Controller
@RequestMapping("/addEresourcePage1")
@SessionAttributes("resource")
public class EresourceFormController {

       /* private static final String REDIRECT_TO_FORM = "redirect:../personRegistration";
        private static final String SUCCESS_PAGE = "addEresourcePage4";
        private static final String PERSON_TO = "eresource";
        private static final String REDIRECT_TO_SUCCESS_PAGE = "redirect:personRegistration/registrationSuccess";
        private static final String REGISTRATION_GENDER_FORM = "registrationGenderForm";
        private static final String REGISTRATION_NAME_FORM = "registrationNameForm";
        private static final String REDIRECT_TO_HOMEPAGE = "redirect:/";

        private EresourceValidator validator;

        private Logger log = LoggerFactory
                        .getLogger(EresourceFormController.class);

        @Autowired
        public EresourceFormController(EresourceValidator validator) {
                super();
                this.validator = validator;
        }

        @InitBinder
        public void initBinder(WebDataBinder binder) {
                binder.registerCustomEditor(Subject.class, (PropertyEditor) new Eresource());
        }

        @RequestMapping(method = RequestMethod.GET)
        public String setupForm(Model model) {
                EresourceManagerImpl eresource = new EresourceManagerImpl(null);
                model.addAttribute(PERSON_TO, eresource);
                return REGISTRATION_NAME_FORM;
        }

        @RequestMapping(method = RequestMethod.POST)
        public String submitForm(HttpServletRequest request,
                        HttpServletResponse response,
                        @ModelAttribute(PERSON_TO) EresourceManagerImpl eresource, BindingResult result,
                        SessionStatus status, @RequestParam("_page") int currentPage,
                        Model model) {

                Map<Integer, String> pageForms = new HashMap<Integer, String>();
                pageForms.put(0, REGISTRATION_NAME_FORM);
                pageForms.put(1, REGISTRATION_GENDER_FORM);

                if (userClickedCancel(request)) {
                        status.setComplete();
                        return REDIRECT_TO_HOMEPAGE;
                } else if (userIsFinished(request)) {
                        validator.validate(eresource, result);
                        if (result.hasErrors()) {
                                return pageForms.get(currentPage);
                        } else {
                                log.info("Registration finished for person [{}: {}].",
                                                eresource.getGender(), eresource.getName());
                                eresource.setRegistrationComplete(true);
                                return REDIRECT_TO_SUCCESS_PAGE;
                        }
                } else {
                        int targetPage = WebUtils.getTargetPage(request, "_target",
                                        currentPage);
                        if (userClickedPrevious(currentPage, targetPage)) {
                                return pageForms.get(targetPage);
                        } else {
                                switch (currentPage) {
                                case 0:
                                        validator.validateName(eresource, result);
                                        break;
                                }

                                if (result.hasErrors()) {
                                        return pageForms.get(currentPage);
                                } else {
                                        return pageForms.get(targetPage);
                                }
                        }
                }
        }

        private boolean userClickedPrevious(int currentPage, int targetPage) {
                return targetPage < currentPage;
        }

        @ModelAttribute("genders")
        public Map<String, String> getGenders() {
                Map<String, String> genders = new LinkedHashMap<String, String>();
                genders.put("", "-- pick one --");
                genders.put("m", "Male");
                genders.put("f", "Female");
                genders.put("o", "Other");
                return genders;
        }

        @RequestMapping(method = RequestMethod.GET, value = "/registrationSuccess")
        public String displaySuccess(Model model, HttpSession session,
                        SessionStatus status) {
                if (registrationHasBeenCompleted(session)) {
                        model.addAttribute(getRegistrationFromSession(session));
                        status.setComplete();
                        return SUCCESS_PAGE;
                } else {
                        return REDIRECT_TO_FORM;
                }
        }

        private boolean userIsFinished(HttpServletRequest request) {
                return request.getParameter("_finish") != null;
        }

        private boolean userClickedCancel(HttpServletRequest request) {
                return request.getParameter("_cancel") != null;
        }

        private boolean registrationHasBeenCompleted(HttpSession session) {
                PersonTo eresource = getRegistrationFromSession(session);
                return eresource != null && eresource.isRegistrationComplete();
        }

        private PersonTo getRegistrationFromSession(HttpSession session) {
                return (PersonTo) session.getAttribute(PERSON_TO);
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
	}*/
}

