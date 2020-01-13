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

import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.service.PlacementManager;

@Controller
public class PlacementFormController extends BaseFormController{

	private PlacementManager placementManager;
	
	@Autowired    
	public void setPlacementManager(@Qualifier("placementManager")
	PlacementManager placementManager) {
				this.placementManager = placementManager;    
		
	} 
	
	public PlacementFormController() {
		setCancelView("redirect:placements");
		setSuccessView("redirect:placements");
	}
	
	
	@ModelAttribute
	@RequestMapping(value="/placementform*", method = RequestMethod.GET)
	protected ModelAndView showForm(HttpServletRequest request)  throws Exception { 
		String placementId = request.getParameter("id");

		ModelAndView modelAndView = new ModelAndView("/placementform");
		if (!StringUtils.isBlank(placementId)) {

			Placement placement = placementManager.get(new Long(placementId));
			modelAndView.addObject("placement",placement);
			return modelAndView;
		}       
		modelAndView.addObject("placement",new Placement());
		return modelAndView;
	}
	
	@RequestMapping(value="/placementform*", method = RequestMethod.POST)
	public ModelAndView onSubmit(Placement placement, BindingResult errors, HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		if (request.getParameter("cancel") != null) { 
			return new ModelAndView(getCancelView()); 
		} 
			if (validator != null) { // validator is null during testing 
				validator.validate(placement, errors); 

				if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting 
					return new ModelAndView("/placementform");
				}
			}
			
			log.debug("entering 'onSubmit' method...");
			
			boolean isNew = (placement.getPlacementId() == null);
			String success = getSuccessView();
			Locale locale = request.getLocale();
			
			if (request.getParameter("delete") != null) {
				placementManager.deletePlacement(placement.getPlacementId(), placement.isEnabled());
				saveMessage(request, getText("placements.deleted", locale));
			}
            else if  (isNew = (placement.getPlacementId() != null)){
				placementManager.updatePlacement(placement);
				String updateKey =   "placements.updated";
				saveMessage(request, getText(updateKey, locale));
				
				if (!isNew) { 
					success = "redirect:placements?id=" +  placement.getPlacementId();
				
				} 
			}

               else {
				placementManager.addPlacement(placement); 
				String key = "placements.added";
				saveMessage(request, getText(key, locale));
				
				if (!isNew) { 
					success = "redirect:placements?id=" + placement.getPlacementId();
				}
			}
			return new ModelAndView("/placements").addObject("placementList",placementManager.findAll());	
	}
}
	
