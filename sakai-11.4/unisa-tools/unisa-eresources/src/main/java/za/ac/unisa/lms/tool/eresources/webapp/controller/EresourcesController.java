package za.ac.unisa.lms.tool.eresources.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import za.ac.unisa.lms.tool.eresources.model.Eresource;
import za.ac.unisa.lms.tool.eresources.model.HighlightNote;
import za.ac.unisa.lms.tool.eresources.service.EresourceManager;

@Controller
public class EresourcesController {
	private EresourceManager eresourceManager;

	@Autowired
	public void setEresourceManager(@Qualifier("eresourceManager") EresourceManager eresourceManager) {
		this.eresourceManager = eresourceManager;

	}

	@RequestMapping(value = "/eresources*", method = RequestMethod.GET)
	public ModelAndView handleRequest() throws Exception {

		// Test Data availability by printing only data from the first record
		List<Eresource> eresourcesList = eresourceManager.getAll();
		
		return new ModelAndView().addObject("eresourcesList", eresourcesList);
	}
	@RequestMapping(value = "/getAllEresourcesList", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Eresource> getAllEresourceRecords() throws Exception{
		
		 ArrayList<Eresource> eresource = (ArrayList<Eresource>) eresourceManager.getAll();
		 
		 System.out.println("Eresources :::"+eresource);
      	 return eresource;
		}


	@RequestMapping(value = "/deleteEresources", method = RequestMethod.GET)  
    public ModelAndView deleteEresource(HttpServletRequest request) throws Exception {  
                
		ModelAndView mav = new ModelAndView("eresources");
		
		String selectedID[] = request.getParameter("selectedSubjectIDs").split(",");
		
		for(int i = 0; i < selectedID.length; ++i)
		{
			eresourceManager.remove(new Long(selectedID[i]));
		}
	
		
		List<Eresource> eresourcesList = eresourceManager.getAll();
		
		mav.getModelMap().put("eresourcesList", eresourcesList);
	      return mav;
    }
	@RequestMapping(value = "/editEresources", method = RequestMethod.GET)
	public ModelAndView updateEresources(HttpServletRequest request) {
	      ModelAndView mav = new ModelAndView("editEresources");	         
	      String eresourceId = request.getParameter("eresourceId");
	      
	      //Fetching the eresource from the database using the id from the previous page
	      Eresource eresource = eresourceManager.get(new Long(eresourceId));
	       
	      mav.getModelMap().put("editEresources", eresource);
	      return mav;
	   }
}
