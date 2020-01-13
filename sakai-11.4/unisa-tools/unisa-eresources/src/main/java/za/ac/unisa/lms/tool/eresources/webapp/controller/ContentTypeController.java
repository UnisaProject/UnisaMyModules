package za.ac.unisa.lms.tool.eresources.webapp.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import za.ac.unisa.lms.tool.eresources.model.ContentType;
import za.ac.unisa.lms.tool.eresources.model.ContentTypeList;
import za.ac.unisa.lms.tool.eresources.model.Eresource;
import za.ac.unisa.lms.tool.eresources.service.ContentTypeManager;

@Controller
public class ContentTypeController {    
	private ContentTypeManager contentTypeManager;
	private static final Logger logger = Logger.getLogger(ContentTypeController.class.getName());
	
	@Autowired    
	public void setContentTypeManager(@Qualifier("contentTypeManager")
	ContentTypeManager contentTypeManager) {
				this.contentTypeManager = contentTypeManager;    
		
	} 

	//@RequestMapping(method = RequestMethod.GET)
	@RequestMapping("/contentTypes*") // JSP page to display the list of content types
	public ModelAndView handleRequest()
			throws Exception {        
		return new ModelAndView().addObject("contentTypeList",contentTypeManager.findAll());    
		}
	
	/*Method to display New ContentType Form
	@RequestMapping(value = "/addContentType", method = RequestMethod.GET)
    public ModelAndView executeNew() {
        ModelAndView mav = new ModelAndView("addContentType");
        ContentType contentType = new ContentType();
        mav.getModelMap().put("addContentType", contentType);
        return mav;
        
    }*/

	@RequestMapping(value = "/getAllContentTypeList", method = RequestMethod.GET)
	public @ResponseBody ContentTypeList getAllContentTypeRecords() throws Exception{
					
		ContentTypeList contentTypeList = new ContentTypeList(contentTypeManager.getAll()); 
		
		return contentTypeList;
		}


	@RequestMapping(value = "/deleteContentTypes*", method = RequestMethod.GET)  
    public @ResponseBody ContentTypeList deleteContentTypes(@RequestParam String selectedContentTypeIDs) throws Exception {  
                
         String selectedContentType[] = selectedContentTypeIDs.split(",");
                
         for(int i = 0; i < selectedContentType.length; ++i)
         {
        	 Long id = Long.parseLong(selectedContentType[i].substring(0, selectedContentType[i].lastIndexOf('-')));
        	 //boolean inUse = Boolean.parseBoolean(selectedContentType[i].substring(selectedContentType[i].lastIndexOf('-')+1));
        	 
        	 contentTypeManager.remove(id);
         }
         ContentTypeList contentTypeList = new ContentTypeList(contentTypeManager.getAll());
      	 return contentTypeList;
    }  

	//Method to display Edit ContentType Form
	@RequestMapping(value = "/editContentType", method = RequestMethod.GET)
	public ModelAndView updateContentType(HttpServletRequest request) {
	      ModelAndView mav = new ModelAndView("editContentType");	         
	      String libTextId = request.getParameter("libTxtID");
	      //Fetching the content type from the database using the id from the previous page
	      ContentType contentType = contentTypeManager.get(new Long(libTextId));
	       
	      mav.getModelMap().put("editContentType", contentType);
	      return mav;
	   }
	@RequestMapping(value = "/deleteContentType", method = RequestMethod.GET)  
    public ModelAndView deleteContentType(HttpServletRequest request) throws Exception {  
                
		ModelAndView mav = new ModelAndView("contentTypes");
		
		String selectedID[] = request.getParameter("selectedContentTypeIDs").split(",");
		
		for(int i = 0; i < selectedID.length; ++i)
		{
			contentTypeManager.remove(new Long(selectedID[i]));
		}
	
		
		List<ContentType> contentTypeList = contentTypeManager.getAll();
		
		mav.getModelMap().put("contentTypeList", contentTypeList);
	      return mav;
    }
	}

