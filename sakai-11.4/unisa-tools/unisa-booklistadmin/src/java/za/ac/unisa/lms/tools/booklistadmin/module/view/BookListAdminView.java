package za.ac.unisa.lms.tools.booklistadmin.module.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.sakaiproject.tool.api.Session;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import javax.servlet.http.HttpServletRequest;
import org.sakaiproject.tool.api.SessionManager;
import za.ac.unisa.lms.tools.booklistadmin.module.College;

public class BookListAdminView {
	
	public void createFirstScreen(HttpServletRequest request,SessionManager sessionManager,
			BooklistAdminForm booklistAdminForm)throws Exception{
                      
                       booklistAdminForm.setDateRemovalScrnEntered(false);
                       HttpSession session = request.getSession(true);		
                       setYearInScreen(booklistAdminForm);
                       Session currentSession = sessionManager.getCurrentSession();
	                   String userID = currentSession.getUserEid();			
	                   booklistAdminForm.setStatusOption("");
                       booklistAdminForm.setCourseCount("");
                       setCollegeListInScreen(booklistAdminForm,session);
                       String typeofbooklist=request.getParameter("TYPE");
                       String networkId = request.getParameter("UserID");
                       if((typeofbooklist!=null) && (networkId!=null)){
                                booklistAdminForm.setTypeOfBookList(typeofbooklist);
                                booklistAdminForm.setNetworkId(networkId);
                       }else{
	                          booklistAdminForm.setTypeOfBookList(booklistAdminForm.getTypeOfBookList());
	                          booklistAdminForm.setNetworkId(booklistAdminForm.getNetworkId());
                       }
    }
	private  void setCollegeListInScreen(BooklistAdminForm booklistAdminForm,HttpSession session) throws Exception{
		                 List collegeList=new ArrayList();
		                 booklistAdminForm.setColleg("");
		                 College college=new College();
		                 session.setAttribute("colleges",college.getCollegeList());
		
	}
    private  void setYearInScreen(BooklistAdminForm booklistAdminForm){
    	Calendar calendar = Calendar.getInstance();
    	Integer thisYear;
        Integer lastYear;
        Integer nextYear;
        Integer year;		
        thisYear = new Integer(calendar.get(Calendar.YEAR));
        lastYear = new Integer(calendar.get(Calendar.YEAR)-1);
        nextYear = new Integer(calendar.get(Calendar.YEAR)+1);
        if (booklistAdminForm.getYear() != null){
    	       if(booklistAdminForm.getYear().equalsIgnoreCase(nextYear.toString())){
    	              year = nextYear;
    	       } else if (booklistAdminForm.getYear().equalsIgnoreCase(lastYear.toString())){
    	                    year = lastYear;
    	       } else {
    	                year = thisYear;
    	       }
    	 } else {
    		       booklistAdminForm.setYear(nextYear.toString());  
    		       year = nextYear;				  
    	  }	
        booklistAdminForm.setLastYear(lastYear.toString());
        booklistAdminForm.setCurrentYear(thisYear.toString());
        booklistAdminForm.setNextYear(nextYear.toString());
        
    }


}
