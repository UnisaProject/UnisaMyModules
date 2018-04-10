package za.ac.unisa.lms.tools.prescribedbooks.actions;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.sakaiproject.component.cover.ComponentManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.User;

import za.ac.unisa.exceptions.NotACourseSiteException;
import za.ac.unisa.lms.tools.prescribedbooks.dao.PrebooksDAO;
import za.ac.unisa.lms.tools.prescribedbooks.dao.PrebooksDetails;
import za.ac.unisa.lms.tools.prescribedbooks.dao.ereserveBooksDetails;
import za.ac.unisa.lms.tools.prescribedbooks.dao.recommendedBooksDetails;
import za.ac.unisa.lms.tools.prescribedbooks.forms.PrebooksForm;
import za.ac.unisa.utils.CoursePeriod;
import za.ac.unisa.utils.CoursePeriodLookup;
public class PrebooksAction extends DispatchAction {
	private SessionManager sessionManager;
	private ToolManager toolManager;
	private Object Iterator;
	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward showPrebooks(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		PrebooksDAO prebooksDAO = new PrebooksDAO();
		PrebooksForm prebooksForm = (PrebooksForm) form;
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		toolManager=(ToolManager)ComponentManager.get(ToolManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		ActionMessages messages = new ActionMessages();
		String userID = currentSession.getUserId();
		CoursePeriod coursePeriod;
		String siteId = null;
		String subject = null;
		Integer year = null;		
		try{
			coursePeriod = CoursePeriodLookup.getCoursePeriod();
			System.out.println("coursePeriod: "+coursePeriod);
			year = new Integer(coursePeriod.getYear());
			//siteId = coursePeriod.getCourseCode()+"-"+year.toString().substring(2,4)+"-"+CoursePeriodLookup.getCourseTypeAsString("0"+coursePeriod.getSemesterPeriod());
			siteId = toolManager.getCurrentPlacement().getContext();
			subject = coursePeriod.getCourseCode();

		}catch(NotACourseSiteException e){
			e.getStackTrace();
		}
		prebooksForm.setCourseCode(siteId);
		prebooksForm.setConfirm(prebooksDAO.getConfirmStatus(year.toString(),subject));
		prebooksForm.setConfirmRecommendedBook(prebooksDAO.getConfirmRStatus(year.toString(),subject));
		prebooksForm.setConfirmEreserveBook(prebooksDAO.getConfirmEStatus(year.toString(),subject));
		prebooksForm.setYear(year.intValue());
		if(year<=2010){
		      if (prebooksForm.getConfirm().equals("1") || prebooksForm.getConfirm().equals("2")){
			           prebooksForm.setConfirm("1");
		      } else if (prebooksForm.getConfirm().equals("3")){
			            prebooksForm.setConfirm("3");
		      } else {
			          prebooksForm.setConfirm("0");
		      }
		 }else{
			   setRecommConfirm(prebooksForm);
			   seteRConfirm(prebooksForm);
			   setConfirm(prebooksForm);
		 }
 
	    
	    List temp = prebooksDAO.getPrebooksList(year.toString(),subject);// get PrescribedBook list
	    if (userID != null) {
			UserDirectoryService userDirectoryService=(UserDirectoryService)ComponentManager.get(UserDirectoryService.class);
			User user = userDirectoryService.getUser(userID);
			request.setAttribute("user", user);
		}
		Vector booklist = new Vector();
		if(temp!=null){
			Iterator i = temp.iterator();
			int count = 0;
		       while(i.hasNext()){
			        PrebooksDetails c = (PrebooksDetails) i.next();
			        int bookPos=checkIfPreBookExist(c,booklist);
			        if(bookPos==-1){
			        	if (count % 2 == 0){
					          c.setColoured("1");
				        }else {
					          c.setColoured("0");
				        }//else
			        	booklist.addElement(c);
			            count++;   
			        }else{
			        	 PrebooksDetails preb=(PrebooksDetails)booklist.get(bookPos);
			        	 if(c.getCourseNotes().length()>preb.getCourseNotes().length()){
			        		 c.setColoured(preb.getColoured());
			        		 booklist.remove(bookPos);
			        		 booklist.add(c);
			        	 }
			       }
		       }//while
		}//if
		prebooksForm.setPrebooksList(booklist);
		Vector booklist2 = new Vector();
		List temp2=prebooksDAO.getRecommendedBooksList(year.toString(),subject);//getting recommended Book List
		if(temp2!=null){
		    Iterator i2  = temp2.iterator();
		    int count = 0;
		    while(i2.hasNext()){
			   recommendedBooksDetails c = (recommendedBooksDetails) i2.next();
			   if (count % 2 == 0){
				   c.setColoured("1");
			    } else {
  				   c.setColoured("0");
			    }//else
			    booklist2.addElement(c);
			    count++;
		    }//while
		}//if
		prebooksForm.setRecommList(booklist2);
		List temp3 = prebooksDAO.getEreserveBooksList(year.toString(),subject);//getting  EreserveBook List
		Vector booklist3 = new Vector();
		if(temp3!=null){
			Iterator i3 = temp3.iterator();
			int count = 0;
		       while(i3.hasNext()){
			        ereserveBooksDetails c = (ereserveBooksDetails) i3.next();
			        if (count % 2 == 0){
				        c.setColoured("1");
			        }else {
				          c.setColoured("0");
			       }//else
			       booklist3.addElement(c);
			       count++;
		       }//while
		}//if
		prebooksForm.setEreserveList(booklist3);
		return mapping.findForward("showprebooks");
	}
	
	private void setRecommConfirm(PrebooksForm pbooksForm){
	     if (pbooksForm.getConfirmRecommendedBook().equals("5") || pbooksForm.getConfirmRecommendedBook().equals("6")){
   	            pbooksForm.setConfirmRecommendedBook("1");
        }else if( pbooksForm.getConfirmRecommendedBook().equals("3")){
   	            pbooksForm.setConfirmRecommendedBook("3");
        }else{
   	            pbooksForm.setConfirmRecommendedBook("0");
        }
   }
   private void seteRConfirm(PrebooksForm pbooksForm){
   	if (pbooksForm.getConfirmEreserveBook().equals("5") || pbooksForm.getConfirmEreserveBook().equals("6")){
	    	 pbooksForm.setConfirmEreserveBook("1");
	     }else if( pbooksForm.getConfirmEreserveBook().equals("3")){
	    	        pbooksForm.setConfirmEreserveBook("3");
	     }else{
	    	   pbooksForm.setConfirmEreserveBook("0");
	     }
   }
   private void setConfirm(PrebooksForm pbooksForm){
   	 if (pbooksForm.getConfirm().equals("5") || pbooksForm.getConfirm().equals("6")){
		        pbooksForm.setConfirm("1");
        } else if (pbooksForm.getConfirm().equals("3")){
		              pbooksForm.setConfirm("3");
	     } else {
		       pbooksForm.setConfirm("0");
	     }
   }
   private int checkIfPreBookExist(PrebooksDetails pb,Vector bookList){
        int bookAlreadyExist=-1;
       for(int x=0;x<bookList.size();x++){
	        PrebooksDetails preb=(PrebooksDetails)bookList.get(x);
	        if(pb.getTitle().equals(preb.getTitle())){
	    	    bookAlreadyExist=x;
	    	    break;
	        }//if
       }//for
       return  bookAlreadyExist;
  }//  
}
