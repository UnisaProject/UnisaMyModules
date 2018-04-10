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
import za.ac.unisa.lms.tool.eresources.model.Newsletter;
import za.ac.unisa.lms.tool.eresources.model.Placement;
import za.ac.unisa.lms.tool.eresources.model.VendorList;
import za.ac.unisa.lms.tool.eresources.service.NewsletterManager;


@Controller
public class NewsletterController {
	private NewsletterManager newsletterManager;
		
	@Autowired    
	public void setNewsletterManager(@Qualifier("newsletterManager")
	NewsletterManager newsletterManager) {
				this.newsletterManager = newsletterManager;    
	} 
	
	// JSP page to display the list of content types	
	@RequestMapping(value="/newsLetter*", method = RequestMethod.GET)
	public ModelAndView handleRequest()
			throws Exception {        
		return new ModelAndView().addObject("newsletterList",newsletterManager.findAll());    
		}
	
	/*Method to display New Newsletter Heading Form*/
	@RequestMapping(value = "/createNewsLetterHeading", method = RequestMethod.GET)
    public ModelAndView executeNew() {
        ModelAndView mav = new ModelAndView("createNewsLetterHeading");
        Newsletter newsHeader = new Newsletter();
        mav.getModelMap().put("createNewsLetterHeading", newsHeader);
        return mav;
    }
	
	//Method to display Edit ContentType Form
	@RequestMapping(value = "/editNewsLetterHeading", method = RequestMethod.GET)
       public ModelAndView updateNewsletter(HttpServletRequest request) {
         ModelAndView mav = new ModelAndView("editNewsLetterHeading");
         
         String newsTitleID = request.getParameter("newsTitleID");
         
         // Fetching the content type from the database using the id from the previus page
         Newsletter newsletter = newsletterManager.get(new Long(newsTitleID));
         
         mav.getModelMap().put("editNewsLetterHeading", newsletter);
         return mav;
       }
	@RequestMapping(value = "/getAllNewsLetterList", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Newsletter> getAllNewsLetterRecords() throws Exception{
					
		 ArrayList<Newsletter> newsLetterList = (ArrayList<Newsletter>) newsletterManager.findAll();
      	 return newsLetterList;
		}
	
	@RequestMapping(value = "/deleteNewsLetter*", method = RequestMethod.GET)  
    public @ResponseBody ArrayList<Newsletter> deleteNewsLetter(@RequestParam String selectedNewsLetterIDs) throws Exception {  
                
         String selectedNewsLetter[] = selectedNewsLetterIDs.split(",");
                
         for(int i = 0; i < selectedNewsLetter.length; ++i)
         {
        	 Long id = Long.parseLong(selectedNewsLetter[i].substring(0, selectedNewsLetter[i].lastIndexOf('-')));
        	 //boolean inUse = Boolean.parseBoolean(selectedNewsLetter[i].substring(selectedNewsLetter[i].lastIndexOf('-')+1));
        	 
        	 newsletterManager.remove(id);
         }
         ArrayList<Newsletter> newsLetterList = (ArrayList<Newsletter>) newsletterManager.findAll();
      	 return newsLetterList;
    }  
	@RequestMapping(value = "/deleteNewsLetters", method = RequestMethod.GET)  
    public ModelAndView deleteNewsLetters(HttpServletRequest request) throws Exception {  
                
		ModelAndView mav = new ModelAndView("newsLetter");
		
		String selectedID[] = request.getParameter("selectedNewsLetterIDs").split(",");
		
		for(int i = 0; i < selectedID.length; ++i)
		{
			newsletterManager.remove(new Long(selectedID[i]));
		}
	
		
		List<Newsletter> newsletterList = newsletterManager.getAll();
		
		mav.getModelMap().put("newsletterList", newsletterList);
	      return mav;
    }
}	

