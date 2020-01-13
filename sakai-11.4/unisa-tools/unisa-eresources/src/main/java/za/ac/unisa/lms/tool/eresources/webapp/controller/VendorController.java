package za.ac.unisa.lms.tool.eresources.webapp.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.model.Vendor;
import za.ac.unisa.lms.tool.eresources.model.VendorList;
import za.ac.unisa.lms.tool.eresources.service.VendorManager;


@Controller

public class VendorController {
	private VendorManager vendorManager;

	@Autowired    
	public void setVendorManager(@Qualifier("vendorManager")
	VendorManager vendorManager) {
		this.vendorManager = vendorManager;    
	} 

	// JSP page to display the list of vendors
	@RequestMapping(value = "/vendors*", method = RequestMethod.GET)
	public ModelAndView handleRequest()throws Exception {  
		
		Vendor vendor = vendorManager.get(new Long(0));
		System.out.println("vendor :::::::::::::::::::: "+vendor.getLogoFile());
		
		return new ModelAndView().addObject("vendorList",vendorManager.getAll());    
	}
	@RequestMapping(value = "/editVendors", method = RequestMethod.GET)
    public ModelAndView updateVendor(HttpServletRequest request) {
      ModelAndView mav = new ModelAndView("editVendors");
      
      String vendorId = request.getParameter("vendorId");
      
      // Fetching the content type from the database using the id from the previus page
      Vendor vendor = vendorManager.get(new Long(vendorId));
      
      mav.getModelMap().put("editVendors", vendor);
      return mav;
    }
	@RequestMapping(value = "/getAllVendorList", method = RequestMethod.GET)
	public @ResponseBody VendorList getAllVendorRecords() throws Exception{
					
		VendorList vendorList = new VendorList(vendorManager.getAll()); 
		
		return vendorList;
		}
	@RequestMapping(value = "/deleteVendors", method = RequestMethod.GET)  
    public ModelAndView deleteVendors(HttpServletRequest request) throws Exception {  
                
		ModelAndView mav = new ModelAndView("vendors");
		
		String selectedID[] = request.getParameter("selectedVendorsIDs").split(",");
		
		for(int i = 0; i < selectedID.length; ++i)
		{
			vendorManager.remove(new Long(selectedID[i]));
		}
	
		
		List<Vendor> vendorList = vendorManager.getAll();
		
		mav.getModelMap().put("vendorList", vendorList);
	      return mav;
    }

	@RequestMapping(value = "/deleteVendor*", method = RequestMethod.GET)  
    public @ResponseBody VendorList deleteVendor(@RequestParam String selectedVendorIDs) throws Exception {  
                
         String selectedVendor[] = selectedVendorIDs.split(",");
                
         for(int i = 0; i < selectedVendor.length; ++i)
         {
        	 Long id = Long.parseLong(selectedVendor[i].substring(0, selectedVendor[i].lastIndexOf('-')));
        	 //boolean inUse = Boolean.parseBoolean(selectedVendor[i].substring(selectedVendor[i].lastIndexOf('-')+1));
        	 
        	 vendorManager.remove(id);
         }
         VendorList vendorList = new VendorList(vendorManager.getAll());
      	 return vendorList;
    }  

}
