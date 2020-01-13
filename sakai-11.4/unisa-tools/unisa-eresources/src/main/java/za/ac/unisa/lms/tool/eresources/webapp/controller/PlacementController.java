package za.ac.unisa.lms.tool.eresources.webapp.controller;

import java.util.ArrayList;
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
import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.service.PlacementManager;


@Controller

public class PlacementController {
	private PlacementManager placementManager;

	@Autowired    
	public void setPlacementManager(@Qualifier("placementManager")
	PlacementManager placementManager) {
				this.placementManager = placementManager;    
		
	} 
	// JSP page to display the list of placements
	@RequestMapping(value ="/placements*", method = RequestMethod.GET)
	public ModelAndView handleRequest()
			throws Exception {        
		return new ModelAndView().addObject("placementList",placementManager.getAll());    
	}
	
	/*Method to display New Placement Form*/
	@RequestMapping(value = "/addPlacement", method = RequestMethod.GET)
    public ModelAndView executeNew() {
        ModelAndView mav = new ModelAndView("addPlacement");
        Placement placement = new Placement();
        mav.getModelMap().put("addPlacement", placement);
        return mav;
    }
	
	@RequestMapping(value = "/getAllPlacementList", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Placement> getAllPlacementRecords() throws Exception{
				
		ArrayList<Placement> placement = (ArrayList<Placement>) placementManager.getAll(); 
		
		return placement;
		}
	
	@RequestMapping(value = "/deletePlacements", method = RequestMethod.GET)  
    public ModelAndView deleteContentType(HttpServletRequest request) throws Exception {  
                
		ModelAndView mav = new ModelAndView("placements");
		
		String selectedID[] = request.getParameter("selectedPlacementsIDs").split(",");
		
		for(int i = 0; i < selectedID.length; ++i)
		{
			placementManager.remove(new Long(selectedID[i]));
		}
	
		
		List<Placement> placementList = placementManager.getAll();
		
		mav.getModelMap().put("placementList", placementList);
	      return mav;
    }
	@RequestMapping(value = "/deletePlacements*", method = RequestMethod.GET)  
    public @ResponseBody ArrayList<Placement> deletePlacements(@RequestParam String selectedPlacementIDs) throws Exception {  
                
         String selectedPlacement[] = selectedPlacementIDs.split(",");
                
         for(int i = 0; i < selectedPlacement.length; ++i)
         {
        	 Long id = Long.parseLong(selectedPlacement[i].substring(0, selectedPlacement[i].lastIndexOf('-')));
        	 //boolean inUse = Boolean.parseBoolean(selectedPlacement[i].substring(selectedPlacement[i].lastIndexOf('-')+1));
        	 
        	 placementManager.remove(id);
         }
         ArrayList<Placement> placementList = (ArrayList<Placement>) placementManager.getAll();
      	 return placementList;
    }  
	//Method to display Edit ContentType Form
			@RequestMapping(value = "/editPlacement", method = RequestMethod.GET)
		       public ModelAndView updatePlacement(HttpServletRequest request) {
		         ModelAndView mav = new ModelAndView("editPlacement");
		         
		         String placementId = request.getParameter("placementId");
		         
		         // Fetching the content type from the database using the id from the previus page
		         Placement placement = placementManager.get(new Long(placementId));
		         
		         mav.getModelMap().put("editPlacement", placement);
		         return mav;
		       }

}
