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
import za.ac.unisa.lms.tool.eresources.model.Subject;
import za.ac.unisa.lms.tool.eresources.service.SubjectManager;


@Controller
 
public class SubjectController {
	private SubjectManager subjectManager;

	@Autowired    
	public void setSubjectManager(@Qualifier("SubjectManager")
	SubjectManager SubjectManager) {
				this.subjectManager = SubjectManager;    
	} 
		
	
	@RequestMapping(value ="/subjects*",method = RequestMethod.GET)
	public ModelAndView handleRequest()
			throws Exception {        
		return new ModelAndView().addObject("subjectsList",subjectManager.getAll());    
		}
	
	/*Method to display New Subject Form*/
	@RequestMapping(value = "/addSubject", method = RequestMethod.GET)
    public ModelAndView executeNew() {
        ModelAndView mav = new ModelAndView("addSubject");
        Subject subject = new Subject();
        mav.getModelMap().put("addSubject", subject);
        return mav;
    }
	
	//Method to display Edit Subject Form
	@RequestMapping(value = "/editSubject", method = RequestMethod.GET)
       public ModelAndView updateSubject(HttpServletRequest request) {
         ModelAndView mav = new ModelAndView("editSubject");
         
         String subjectId = request.getParameter("subjectId");
         
         // Fetching the subject from the database using the id from the previous page
         Subject subjectName= subjectManager.get(new Long(subjectId));
         
         mav.getModelMap().put("editSubject", subjectName);
         return mav;
       }
	@RequestMapping(value = "/getAllSubjectList", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Subject> getAllSubjectRecords() throws Exception{
						
		ArrayList<Subject> subjectList = (ArrayList<Subject>) subjectManager.getAll();
		return subjectList;
		}

	@RequestMapping(value = "/deleteSubject*", method = RequestMethod.GET)  
    public @ResponseBody ArrayList<Subject> deleteSubject(@RequestParam String selectedSubjectIDs) throws Exception {  
                
         String selectedSubject[] = selectedSubjectIDs.split(",");
                
         for(int i = 0; i < selectedSubject.length; ++i)
         {
        	 Long id = Long.parseLong(selectedSubject[i].substring(0, selectedSubject[i].lastIndexOf('-')));
        	 //boolean inUse = Boolean.parseBoolean(selectedSubject[i].substring(selectedSubject[i].lastIndexOf('-')+1));
        	 
        	 subjectManager.remove(id);
         }
         ArrayList<Subject> subjectList = (ArrayList<Subject>) subjectManager.getAll();
      	 return subjectList;
    }
	@RequestMapping(value = "/deleteSubjects", method = RequestMethod.GET)  
    public ModelAndView deleteSubjects(HttpServletRequest request) throws Exception {  
                
		ModelAndView mav = new ModelAndView("subjects");
		
		String selectedID[] = request.getParameter("selectedSubjectsIDs").split(",");
		
		for(int i = 0; i < selectedID.length; ++i)
		{
			subjectManager.remove(new Long(selectedID[i]));
		}
	
		
		List<Subject> subjectsList = subjectManager.getAll();
		
		mav.getModelMap().put("subjectsList", subjectsList);
	      return mav;
    }
}
