package za.ac.unisa.lms.tool.eresources.webapp.controller;

import java.util.ArrayList;
import java.util.LinkedList;
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
import za.ac.unisa.lms.tool.eresources.model.HighlightNote;
import za.ac.unisa.lms.tool.eresources.model.Newsletter;
import za.ac.unisa.lms.tool.eresources.service.HighlightNotesManager;


@Controller

public class HighlightNotesController {
	private HighlightNotesManager highlightNotesManager;
	
	@Autowired    
	public void setHighlightNotesManager(@Qualifier("highlightNotesManager")
	HighlightNotesManager highlightNotesManager) {
				this.highlightNotesManager = highlightNotesManager;    
	} 
	
	// JSP page to display the list of HighlightNote
	@RequestMapping(value = "/highlightNotes*",method = RequestMethod.GET)
	public ModelAndView handleRequest()
			throws Exception {        
		return new ModelAndView().addObject("highlightNotesList",highlightNotesManager.getAll());    
		}

	/*Method to display New HighlightNote Form*/
	/*@RequestMapping(value = "/editHighlightNotes", method = RequestMethod.GET)
    public ModelAndView executeNew() {
        ModelAndView mav = new ModelAndView("editHighlightNotes");
        HighlightNote highlightnote = new HighlightNote();
        mav.getModelMap().put("editHighlightNotes", highlightnote);
        return mav;
    }
	*/
	//Method to display Edit ContentType Form
	@RequestMapping(value = "/editHighlightNotes", method = RequestMethod.GET)
       public ModelAndView updateHighlightNote(HttpServletRequest request) {
         ModelAndView mav = new ModelAndView("editHighlightNotes");
         
         String highlightNotesID = request.getParameter("highlightNotesID");
         
         // Fetching the content type from the database using the id from the previous page
         HighlightNote highlightnote = highlightNotesManager.get(new Long(highlightNotesID));
         
         mav.getModelMap().put("editHighlightNotes", highlightnote);
         return mav;
       }
	@RequestMapping(value = "/getAllHighlightNoteList", method = RequestMethod.GET)
	public @ResponseBody ArrayList<HighlightNote> getAllHighlightNotesRecords() throws Exception{
		
		 ArrayList<HighlightNote> highlightNote = (ArrayList<HighlightNote>) highlightNotesManager.getAll();
      	 return highlightNote;
		}


	@RequestMapping(value = "/deleteHighLighttNotes*", method = RequestMethod.GET)  
    public @ResponseBody ArrayList<HighlightNote> deleteHighlightNote(@RequestParam String selectedHighlightNotesIDs) throws Exception {  
                
         String selectedHighlightNote[] = selectedHighlightNotesIDs.split(",");
                
         for(int i = 0; i < selectedHighlightNote.length; ++i)
         {
        	 Long id = Long.parseLong(selectedHighlightNote[i].substring(0, selectedHighlightNote[i].lastIndexOf('-')));
        	 //boolean inUse = Boolean.parseBoolean(selectedHighlightNote[i].substring(selectedHighlightNote[i].lastIndexOf('-')+1));
        	 
        	 highlightNotesManager.remove(id);
         }
         ArrayList<HighlightNote> highlightNote = (ArrayList<HighlightNote>) highlightNotesManager.getAll();
      	 return highlightNote;
    }  
	@RequestMapping(value = "/deleteHighLighttNote", method = RequestMethod.GET)  
    public ModelAndView deleteHighLighttNotes(HttpServletRequest request) throws Exception {  
                
		ModelAndView mav = new ModelAndView("highlightNotes");
		
		String selectedID[] = request.getParameter("selectedHighlightNotesIDs").split(",");
		
		for(int i = 0; i < selectedID.length; ++i)
		{
			highlightNotesManager.remove(new Long(selectedID[i]));
		}
	
		
		List<HighlightNote> highlightNotesList = highlightNotesManager.getAll();
		
		mav.getModelMap().put("highlightNotesList", highlightNotesList);
	      return mav;
    }
}
