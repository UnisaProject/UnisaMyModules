package za.ac.unisa.lms.tools.tutorprebooks.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.tools.tutorprebooks.dao.CourseDetailsBean;
import za.ac.unisa.lms.tools.tutorprebooks.dao.TutorPreBooksDAO;
import za.ac.unisa.lms.tools.tutorprebooks.dao.TutorPreBooksSakaiDAO;
import za.ac.unisa.lms.tools.tutorprebooks.forms.MainForm;



/**
 * @author lebelsg
 *
 */
public class TutorMainAction extends LookupDispatchAction {

	private SessionManager sessionManager;
	protected Map getKeyMethodMap() {
	      Map map = new HashMap();
	      
	      map.put("mainDisplay","mainDisplay");
	      map.put("button.search","search");
	      map.put("button.submit","saveTurors");
	      map.put("button.report","viewReports");
	      map.put("button.back","mainDisplay");
	      map.put("button.completed","showCompletedReport");
	      map.put("button.outstanding","showOutstandingReport");
	      map.put("button.clear","clear");
	      map.put("button.clear2","clearReset");
	      
	 
	      	   
	      return map;

}
	public ActionForward mainDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		 MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");				
		 request.setAttribute("yearOptions", mainForm.getYearOptions()); 					
		 request.setAttribute("periodOptions", mainForm.getPeriodOptions());
		 
		/* mainForm.setAcadPeriod("");
	     mainForm.setYear("");*/
		 
		 
	
		return mapping.findForward("mainDisplay");
		
	}

	//retrieves all the modules linked to a user
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MainForm mainForm = (MainForm) form;
		
		TutorPreBooksDAO showLinkedCoursesdao = new TutorPreBooksDAO();
		sessionManager = (SessionManager)ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
   		String userEid = currentSession.getUserEid();
   		mainForm.setUserId(userEid.toUpperCase());
		
		mainForm.setCourseList(null);
		
		
		ActionMessages messages = new ActionMessages();
		
		if(mainForm.getYear().equals("-1")){
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Academic Year is not selected. Please select and search again. "));
				addErrors(request, messages);
				
				 request.setAttribute("yearOptions", mainForm.getYearOptions());
				 request.setAttribute("periodOptions", mainForm.getPeriodOptions());
				
				return mapping.findForward("mainDisplay");
		}
		 
			if(mainForm.getAcadPeriod().equals("-1")){
			
			    messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Semester Period  is not selected. Please select and search again. "));
				addErrors(request, messages);
				
				 request.setAttribute("yearOptions", mainForm.getYearOptions());
				 request.setAttribute("periodOptions", mainForm.getPeriodOptions());
				
				return mapping.findForward("mainDisplay");
		}else{
			
			ArrayList courseList=null;
			
			request.setAttribute("yearOptions", mainForm.getYearOptions());
		    request.setAttribute("periodOptions", mainForm.getPeriodOptions());
		    
			courseList=showLinkedCoursesdao.selectLinkedCourses(userEid.toUpperCase(), mainForm.getYear(), mainForm.getAcadPeriod());
			
			
			 if(courseList.size() == 0){
				 
			    	messages.add(ActionMessages.GLOBAL_MESSAGE,
					        new ActionMessage("message.generalmessage", "You are not linked to any module for "+mainForm.getYear()+"/"+mainForm.getAcadPeriod()+", Please contact department linker for further details."));
					addErrors(request, messages);	
					
					return mapping.findForward("mainDisplay");
			    }

			mainForm.setCourseList(courseList);
			
			
			return mapping.findForward("mainDisplay");

		}

	}
	
	public ActionForward saveTurors(ActionMapping mapping, ActionForm form,	
			HttpServletRequest request, HttpServletResponse response) throws Exception			
	{
		MainForm mainForm = (MainForm) form;
		ActionMessages messages = new ActionMessages();
		
		request.setAttribute("yearOptions", mainForm.getYearOptions());
	    request.setAttribute("periodOptions", mainForm.getPeriodOptions());
	    TutorPreBooksSakaiDAO tutorPreBooksSakaiDAO = new TutorPreBooksSakaiDAO();
	    TutorPreBooksDAO tutorPreBooksDAO = new TutorPreBooksDAO();
	     
	   	
	    Iterator tutors = mainForm.getCourseList().iterator();
	    
			
	      while(tutors.hasNext()){

	          CourseDetailsBean courseDetailsBean = (CourseDetailsBean) tutors.next();
	          String courseCode = courseDetailsBean.getCoursecode();
	          String tutorNr = courseDetailsBean.getNumberOfTutors().trim();
	          String modifiedBy = courseDetailsBean.getLastModifiedBy();
	         // String department =  courseDetailsBean.getDepartment();
	          String department = tutorPreBooksDAO.getDepartment(courseCode);
	          
	          if(null!=tutorNr || !tutorNr.equals("")){
	             if (tutorNr.matches(".*[a-zA-Z].*")) {   
	                   messages.add(ActionMessages.GLOBAL_MESSAGE,
	                                       new ActionMessage("message.generalmessage", "Please enter a numeric number for tutors "));
	                               addErrors(request, messages);    
	                               
	                               return mapping.findForward("mainDisplay");
	             }
	          }

        
    	  
    	  //insert the records into DB
          
          tutorPreBooksSakaiDAO.insertOrUpdateTutorRecords(mainForm.getUserId(),mainForm.getAcadPeriod(),mainForm.getYear(),courseCode,tutorNr,department);
         
      
       
      }


    	
    	messages.add(ActionMessages.GLOBAL_MESSAGE,
		        new ActionMessage("message.generalmessage", "The information has been successfully saved for  "+mainForm.getYear()+"/"+mainForm.getAcadPeriod()+" "));
		addErrors(request, messages);	
		
    	mainForm.setCourseList(null);
    	mainForm.setAcadPeriod("");
    	mainForm.setYear("");
    	
    	return mapping.findForward("mainDisplay");
    	
	}

	     
	
	public ActionForward viewReports(ActionMapping mapping, ActionForm form,	
			HttpServletRequest request, HttpServletResponse response)
	{
		MainForm mainForm = (MainForm) form;
				
		mainForm.setCompletedPrescribedBooksList(null);
		mainForm.setOutstandingPrescribedBooksList(null);
		mainForm.setCompletedButtonClick(false);
		mainForm.setOutstandingButtonClick(false);
		
		ActionMessages messages = new ActionMessages();
		
		if(mainForm.getYear().equals("-1") || mainForm.getAcadPeriod().equals("-1")){
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Please select academic year and period"));
				addErrors(request, messages);
				
				 request.setAttribute("yearOptions", mainForm.getYearOptions());
				 request.setAttribute("periodOptions", mainForm.getPeriodOptions());
				
				return mapping.findForward("mainDisplay");
		}
		else
		{
			return mapping.findForward("viewreportsforward");
		}
		
	}
	
	public ActionForward showCompletedReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
	{
			MainForm mainForm = (MainForm) form;
			
			TutorPreBooksDAO showPreBooksModulesDao = new TutorPreBooksDAO();
			
			mainForm.setCompletedPrescribedBooksList(null);
			mainForm.setOutstandingPrescribedBooksList(null);
			mainForm.setCompletedButtonClick(true);
			mainForm.setOutstandingButtonClick(false);
			
			ArrayList completedPrescribedBooksList = null;
									
			completedPrescribedBooksList = showPreBooksModulesDao.getAllPreBooksModules(mainForm.getYear(), mainForm.getAcadPeriod(), "true");
			
			mainForm.setCompletedPrescribedBooksList(completedPrescribedBooksList);
							
			return mapping.findForward("showreportforward");
			
	}
	
	public ActionForward showOutstandingReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
	{
			MainForm mainForm = (MainForm) form;
			
			TutorPreBooksDAO showPreBooksModulesDao = new TutorPreBooksDAO();
			
			mainForm.setCompletedPrescribedBooksList(null);
			mainForm.setOutstandingPrescribedBooksList(null);
			mainForm.setOutstandingButtonClick(true);
			mainForm.setCompletedButtonClick(false);
			
			ArrayList outstandingPrescribedBooksList = null;
						
			outstandingPrescribedBooksList = showPreBooksModulesDao.getAllPreBooksModules(mainForm.getYear(), mainForm.getAcadPeriod(), "false");
			
			mainForm.setOutstandingPrescribedBooksList(outstandingPrescribedBooksList);
						
			return mapping.findForward("showreportforward");
			
	}
	
	/*public ActionForward clear(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

	 	MainForm mainForm = (MainForm) form;	
		CourseDetailsBean courseDetails = new CourseDetailsBean();
	 	request.setAttribute("yearOptions", mainForm.getYearOptions());
	    request.setAttribute("periodOptions", mainForm.getPeriodOptions());
	    
	 	    
	    if(mainForm.getClearOption().equals("clearTurorNr")){
	    	 return search(mapping, mainForm, request, response);
	    	
	    }else{
	    	mainForm.setAcadPeriod("");
	    	mainForm.setYear("");
	    	 return mapping.findForward("mainDisplay");
	    }
		
	}*/
	
	//clear for upper part jsp
	public ActionForward clear(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		 //MainForm mainForm = (MainForm) form;	
		 MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		CourseDetailsBean courseDetails = new CourseDetailsBean();
	 	request.setAttribute("yearOptions", mainForm.getYearOptions());
	    request.setAttribute("periodOptions", mainForm.getPeriodOptions());
	    
	    mainForm.setAcadPeriod("");
    	mainForm.setYear("");
    	
    	 
    	return mapping.findForward("mainDisplay");	
	    
		
	}
	
	//clear for down part jsp clearReset
	public ActionForward clearReset(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		 //MainForm mainForm = (MainForm) form;	
		 MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
		CourseDetailsBean courseDetails = new CourseDetailsBean();
	 	request.setAttribute("yearOptions", mainForm.getYearOptions());
	    request.setAttribute("periodOptions", mainForm.getPeriodOptions());
	    
	       
	    	 return search(mapping, mainForm, request, response);
    	
    	
    	 
    	//return mapping.findForward("mainDisplay");	
	    
		
	}
	
}
