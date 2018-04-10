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

import za.ac.unisa.lms.tool.eresources.model.Vendor;
import za.ac.unisa.lms.tool.eresources.service.VendorManager;

@Controller 
public class VendorFormController extends BaseFormController{
	private VendorManager vendorManager;

	@Autowired    
	public void setVendorManager(@Qualifier("vendorManager")
	VendorManager vendorManager) {
		this.vendorManager = vendorManager;    
	} 

	public VendorFormController() {
		setCancelView("redirect:vendors");
		setSuccessView("redirect:vendors");
	}

	@ModelAttribute
	@RequestMapping(value="/vendorform*", method = RequestMethod.GET)
	protected ModelAndView showForm(HttpServletRequest request)    throws Exception { 
		String vendorId = request.getParameter("id");

		ModelAndView modelAndView = new ModelAndView("/vendorform");
		if (!StringUtils.isBlank(vendorId)) {
			Vendor vendor = vendorManager.get(new Long(vendorId));
			modelAndView.addObject("vendor",vendor);
			return modelAndView;
		}         
		modelAndView.addObject("vendor",new Vendor());
		return modelAndView;
	}

	@RequestMapping(value="/vendorform*", method = RequestMethod.POST)
	public ModelAndView onSubmit(Vendor vendor, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
			
            if (request.getParameter("cancel") != null) { 
			return new ModelAndView(getCancelView()); 
            }
			if (validator != null) { // validator is null during testing 
				validator.validate(vendor, errors); 
				
				if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting 
					return new ModelAndView("/vendorform");
				}
			}
			
			log.debug("entering 'onSubmit' method...");
			
			boolean isNew = (vendor.getVendorId() == null);
			String success = getSuccessView();
			Locale locale = request.getLocale();
			
			if (request.getParameter("delete") != null) {
				vendorManager.deleteVendor(vendor.getVendorId(), vendor.isEnabled());
				saveMessage(request, getText("vendor.deleted", locale));
			}else
				if  (isNew = (vendor.getVendorId() != null)){
					vendorManager.updateVendor(vendor);
					String updateKey =  "vendors.updated";
					saveMessage(request, getText(updateKey, locale));
				
				if (!isNew) { 
					success = "redirect:vendors?id=" + vendor.getVendorId();
				
				}
				
			}   else {
				vendorManager.addVendor(vendor); 
				String key = "vendors.added";
				saveMessage(request, getText(key, locale));
				
				if (!isNew) { 
					success = "redirect:vendors?id=" + vendor.getVendorId();
				}
			}
			
			return new ModelAndView("/vendors").addObject("vendorList",vendorManager.findAll());	
	}

}