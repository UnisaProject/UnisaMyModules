package za.ac.unisa.lms.tool.eresources.webapp.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import za.ac.unisa.lms.tool.eresources.model.Eresource;
import za.ac.unisa.lms.tool.eresources.model.HighlightNote;
import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.model.Vendor;
import za.ac.unisa.lms.tool.eresources.service.EresourceManager;
import za.ac.unisa.lms.tool.eresources.service.HighlightNotesManager;
import za.ac.unisa.lms.tool.eresources.service.VendorManager;
import za.ac.unisa.lms.tool.eresources.service.impl.VendorManagerImpl;

@Controller  
//@SessionAttributes({"resource"})
//@RequestMapping("/eresourceformPage1*")
public class EresourcesFormController extends BaseFormController{

	private EresourceManager eresourceManager;
	private VendorManager vendorManager;
	private HighlightNotesManager highlightNotesManager;

	@Autowired    
	public void setEresourceManager(@Qualifier("eresourceManager") EresourceManager eresourceManager) {
		this.eresourceManager = eresourceManager;    

	} 
	
	@Autowired
	public void setVendorManager(@Qualifier("vendorManager") VendorManager vendorManager) {
		this.vendorManager = vendorManager;
	}
	
	
	@Autowired
	public void setHighlightNotesManager(@Qualifier("highlightNotesManager")HighlightNotesManager highlightNotesManager) {
		this.highlightNotesManager = highlightNotesManager;
	}
	public EresourcesFormController() {
		setCancelView("redirect:eresources");
		setSuccessView("redirect:eresources");
	}
	
	@ModelAttribute
	@RequestMapping(value = "/eresourceformPage1*", method = {RequestMethod.GET,RequestMethod.POST})
	protected ModelAndView showEResourceFormPage1(HttpServletRequest request) throws Exception {

		ModelAndView modelAndView = new ModelAndView("/eresourceformPage1");
		modelAndView.addObject("eresource", new Eresource());

		return modelAndView;
	}

	

	@RequestMapping(value = "/submitEResourceForm1*",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView handleEResourceFormPage1(Eresource eresource, HttpSession session, HttpServletRequest request, ModelAndView modelAndView) 
	{

		session.setAttribute("newEresourceObject", eresource);
		
		modelAndView.setViewName("/eresourceformPage2");
		modelAndView.addObject("eresource", new Eresource());

		return modelAndView;

	}
	
	
	@RequestMapping(value = "/submitEResourceForm2*",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView handleEResourceFormPage2(HttpSession session, HttpServletRequest request, Eresource eresource) 
	{		
		
		String placementIdsCollector = "";
		
		Set<String> placementIds = eresource.getEresourcePlacementIds();
		
		Iterator<String> placementIdIterator = placementIds.iterator();
		
		while(placementIdIterator.hasNext()) {
			String placementId = placementIdIterator.next();
			
			placementIdsCollector += placementId+",";
		}

		session.setAttribute("placementIds", placementIdsCollector);
		
		ModelAndView modelAndView = new ModelAndView("/eresourceformPage3");
		modelAndView.addObject("eresource",eresource);

		return modelAndView;

	}
	
	
	@RequestMapping(value = "/submitEResourceForm3*",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView handleEResourceFormPage3(HttpSession session, HttpServletRequest request, Eresource eresource) 
	{

		String subjectsIdsCollector = "";
		
		Set<String> subjectsIds = eresource.getEresourceSubjectIds();
		
		Iterator<String> subjectsIdIterator = subjectsIds.iterator();
		
		while(subjectsIdIterator.hasNext()) {
			String subjectId = subjectsIdIterator.next();
			
			subjectsIdsCollector += subjectId+",";
		}		

		session.setAttribute("subjectsIds", subjectsIdsCollector);
		
		ModelAndView modelAndView = new ModelAndView("/eresourceformPage4");
		modelAndView.addObject("eresource",eresource);

		return modelAndView;

	}
	@RequestMapping(value="/editEresources*", method = RequestMethod.POST)
	public ModelAndView onSubmit(Eresource eresource, BindingResult errors, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Locale locale = request.getLocale();
	
		if (request.getParameter("cancel") != null) { 
				
			return new ModelAndView(getCancelView()); 
		} 
		else
		{
			eresourceManager.updateResource(eresource);
			String updateKey =  "eresource.updated";
			saveMessage(request, getText(updateKey, locale));
		
			return new ModelAndView("/eresources").addObject("eresourcesList",eresourceManager.findAll());		
		}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*@RequestMapping(method = RequestMethod.GET)
	public ModelAndView populatePlacements() throws Exception {

	
		String placementName = null;
		// Test Data availability by printing only data from the first record
		List<Placement> placementList = eresourceManager.getPlacementNames(placementName);
		return new ModelAndView().addObject("placementList", placementList);
	}*/
	
}

//Display 1st page of the wizard
	/*@ModelAttribute
	//@RequestMapping(value = "/eresourceformPage1*", method = {RequestMethod.GET,RequestMethod.POST})
	public String showFormPage1(HttpRequest request) { 

		System.out.println("inside submitForm))))))))))))))))>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OUT!!!!!!!!!!!!");

		ModelAndView modelAndView = new ModelAndView("/eresourceformPage1");
		modelAndView.addObject("resource", new Eresource());

		System.out.println("Size of the VendorList :: " + eresourceManager.getAll().size());
		modelAndView.addObject("vendorList",eresourceManager.getAll());

		//Eresource resource = new Eresource();                 
		//model.addAttribute("resource", resource);  

		return "eresourceformPage1"; 

	} */  

	/*@RequestMapping(method = RequestMethod.POST)
	public String submitForm(
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("resource") Eresource resource,             
			BindingResult result, SessionStatus status,             
			@RequestParam("_page") int currentPage, Model model) {

		System.out.println("inside submitForm))))))))))))))))>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OUT!!!!!!!!!!!!");

		Map<Integer, String> pageForms = new HashMap<Integer, String>();
		pageForms.put(0,"eresourceformPage1");
		pageForms.put(1,"eresourceformPage2");
		pageForms.put(2,"eresourceformPage3");
		pageForms.put(3,"eresourceformPage4");

		if (request.getParameter("_cancel") != null) {

			// Return to current page view, since resource clicked cancel
			//return (String)pageForms.get(currentPage);
			return "eresourceformPage1"; 

		} else if (request.getParameter("_finish") != null) {

			//new EresourceValidator().validate(resource, result);
			//	resourceValidator.validate(resource, result);
			if (!result.hasErrors()) {
				status.setComplete();
				return "eresourceformPage4";
			} else {
				// Errors 
				return (String)pageForms.get(currentPage);
			}
		} else {
			int targetPage = WebUtils.getTargetPage(request, "_target", currentPage);
			// If targetPage is lesser than current page, resource clicked 'Previous'
			if (targetPage < currentPage) {
				return (String)pageForms.get(targetPage);
			}
			// Eresource clicked next
			// Validate data based on page
			switch (currentPage) {
			case 0:
				//new EresourceValidatorOLD().validatePage1Form(resource, result);
				resourceValidator.validate(resource, result); //.validatePage1Form(resource, result);
				System.out.println("inside submitForm))))))))))))))))>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OUT1111!!!!!!!!!!!!");
				break;
			case 1:
				//new EresourceValidatorOLD().validatePage2Form(resource, result);
				resourceValidator.validate(resource, result);
				System.out.println("inside submitForm))))))))))))))))>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OUT22222!!!!!!!!!!!!");
				break;
			case 2:
				//new EresourceValidatorOLD().validatePage3Form(resource, result);
				resourceValidator.validate(resource, result);
				System.out.println("inside submitForm))))))))))))))))>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OUT333!!!!!!!!!!!!");
				break;
			case 3:
				//new EresourceValidatorOLD().validatePage3Form(resource, result);
				resourceValidator.validate(resource, result);
				System.out.println("inside submitForm))))))))))))))))>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>OUT4444!!!!!!!!!!!!");
				break;
			}
			if (!result.hasErrors()) {
				// No errors, return target page
				return (String)pageForms.get(targetPage);
			} else {
				// Errors, return current page
				return (String)pageForms.get(currentPage);
			}
		}

	}*/

