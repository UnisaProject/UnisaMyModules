//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.booklist.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import za.ac.unisa.lms.tools.booklist.dao.BooklistDAO;
import za.ac.unisa.lms.tools.booklist.forms.BooklistForm;


/**
 * MyEclipse Struts
 * Creation date: 09-08-2006
 *
 * XDoclet definition:
 * @struts.action parameter="action" validate="true"
 */
public class BooklistAction extends LookupDispatchAction  {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/**
	 * Method view
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */

	protected Map getKeyMethodMap() {
	       Map map = new HashMap();
	       map.put("button.back","back");
	      // map.put("modules.back","stepback");
	       map.put("view", "view");
	       map.put("viewbooklist", "viewbooklist");
	       map.put("viewmodules", "viewmodules");
	       map.put("gofoward", "goforth");
	       map.put("goback", "goback");
	       map.put("verystart", "begin");
	       map.put("verylast", "verylast");
	       map.put("button.display", "view");
	       map.put("sites.display", "viewbooklist");
	       map.put("booklist.continue", "viewselectedmodules");
	       map.put("viewselectedmodules", "viewselectedmodules");
	   return map;
	  }
	public ActionForward view(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  
			  HttpServletResponse response) throws Exception {
			  BooklistDAO dao = new BooklistDAO();
			  HttpSession session = request.getSession(true);
			  BooklistForm booklistForm = (BooklistForm) form;
			  Calendar calendar = Calendar.getInstance();
			  String yr =booklistForm.getYear();	
			  ActionMessages messages = new ActionMessages();
		 	  String collegecode = booklistForm.getCollegeCode();
		 	  List selection;
		 	  Map colMap=dao.getCollegesMap();//.get(collegecode);
		 	  booklistForm.setCollege((String)colMap.get(collegecode));
		 	  String dept=booklistForm.getDepartment();//selected department
		 	  String sch =booklistForm.getSchool();//selected school	
		 	  String previouslySelectedYear;
		 	  String statusOptions=booklistForm.getStatusOptions();
		 	  String booklistTypes = booklistForm.getBooklistTypes();
		 	 SortedSet collegeSet = Collections.synchronizedSortedSet(new TreeSet(dao.getColleges2().values()));
			  Integer thisYear;
			  Integer lastYear;
			  Integer nextYear;
			  Integer year;			  
			  String def="-1";
			Date d= new Date();
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			String date=sdf.format(d);
			
			  booklistForm.setToday(date); 
			  thisYear = new Integer(calendar.get(Calendar.YEAR));
			  lastYear = new Integer(calendar.get(Calendar.YEAR)-1);
			  nextYear = new Integer(calendar.get(Calendar.YEAR)+1);
			  if (booklistForm.getYear() != null){
			   if(booklistForm.getYear().equalsIgnoreCase(nextYear.toString())){
			    year = nextYear;
			   } else if (booklistForm.getYear().equalsIgnoreCase(lastYear.toString())){
			    year = lastYear;
			   } else {
			    year = thisYear;
			   }
			  } else {
				  booklistForm.setYear(nextYear.toString());  
				  year = nextYear;				  
			  }	
			 	
			  try{ 
				 	   previouslySelectedYear=booklistForm.getPrevYear();				 
				 	  if (previouslySelectedYear ==null){
				 		  booklistForm.setPrevYear(year.toString());
				 	  }
				 		   
				 	  }
				 	 catch(NullPointerException e)
				 	   {//e.printStackTrace();
				 		previouslySelectedYear =year.toString();
				 	   }
				 	
				
			  if ((booklistForm.getCollege()==null)||(booklistForm.getCollege().equals(def))){ 				
				  	       if ((previouslySelectedYear!=null)&&(previouslySelectedYear.equals(year.toString()))){
				  		        if (previouslySelectedYear.equals(year.toString())){
				  		        	
				  	    	    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("booklist.nocollege"));
				  		        addErrors(request, messages); 
				  		        }
				  		        
				           }
			       }
			  else if(booklistForm.getBooklistTypes()==null||booklistForm.getBooklistTypes().equals("a")){
				  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("booklist.nobooklistoptions"));
	  		        addErrors(request, messages); 
	  		      return  mapping.findForward("viewforward");
			  }
			  else if ((booklistForm.getStatusOptions()==null)||(booklistForm.getStatusOptions().equals(def))){ 	
				  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("booklist.nostatusOptions"));
	  		        addErrors(request, messages); 
			  }
			 
		//new code------------------------------------------	
			     booklistForm.setCollegestat(collegeSet);
			     List collegesites =dao.getCollegeinfo(dao.getBooklistOPen(year.toString()),dao.getBooklistSubmitted(year.toString()),dao.getBooklistAuthorized(year.toString()),dao.getNoBooklist(year.toString())
			    		,year.toString());
			     booklistForm.setCollegeinfo(dao.sortCollegedisplay(booklistForm.getCollegestat(),collegesites));
			 	
			  if ((booklistForm.getViewStudyUnits()==null)){  }
			  else if(booklistForm.getViewStudyUnits().equals("m"))
		       	 {
		          messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("booklist.noschoolDepartment"));
		       	  addErrors(request, messages);	
		        	booklistForm.setPrevYear(previouslySelectedYear); 
		       	  return mapping.findForward("viewmodules");		        
		       	 }
	
			  session.setAttribute("colleges",dao.getColleges());
			  session.setAttribute("departments",dao.getDepts(collegecode));
			  session.setAttribute("schools",dao.getSchools(collegecode));
			  
			  System.out.println("schools"+(dao.getSchools(collegecode)));
			  
			  booklistForm.setLastYear(lastYear.toString());
			  booklistForm.setCurrentYear(thisYear.toString());
			  booklistForm.setNextYear(nextYear.toString());	
			   if (collegecode==null||collegecode.equals("-1")){}
			   
			   else{
				   booklistForm.setPrescribedBooks(dao.getPrescibedBooksStat(statusOptions,yr,Integer.parseInt(collegecode),booklistTypes));	
				    int start =booklistForm.getStart();	
				   int end = booklistForm.getEnd();
				   if (booklistForm.getNumitems()<=booklistForm.getRecords()){
					 start =0;
					 end =Math.min(booklistForm.getEnd(),booklistForm.getNumitems());
				   }
				   booklistForm.setSomeprescribedBooks((booklistForm.getPrescribedBooks()));
		 		   booklistForm.setStart(1);
				   booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
			    if (booklistForm.getNumitems()<1)
			     {booklistForm.setStart(0);
			     booklistForm.setEnd(0);
			     }
			   }			
			  if (collegecode== null ||collegecode.equals("-1")||booklistForm.getStatusOptions()==null||booklistForm.getStatusOptions().equals(def))
			  {
				  booklistForm.setPrevYear(year.toString());
				  return mapping.findForward("viewforward");
			  } 
			  selection = booklistForm.getSomeprescribedBooks();
			 
			  if(selection.isEmpty() == true){
				  messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("booksite.notavailable"));
		    	  addErrors(request, messages);	
		    	  selection = null;
				  return mapping.findForward("viewforward");
				
			  }
			  
			 booklistForm.setPrevYear(year.toString()); 
			 
			  
			if(booklistTypes.equals("P")){
				booklistForm.setBooklistStatus("Prescribed");
			}
			else if(booklistTypes.equals("R")){
				booklistForm.setBooklistStatus("Recommended");
			}else if(booklistTypes.equals("E")){
				booklistForm.setBooklistStatus("Ereserves");
			}
		 	 if(statusOptions.equals("-1")){
		 		 booklistForm.setStatus("Book list with no activity");
		 	 }
		  else if(statusOptions.equals("5")){
		 		 booklistForm.setStatus("Book list authorized by Dean");
		 	 }
		  else if(statusOptions.equals("4")){
		 		 booklistForm.setStatus("Booklist authorized by School Director");
		 	 }
		  else if(statusOptions.equals("2")){
		 		 booklistForm.setStatus("Booklist authorized by COD ");
		 	 }
		  else if(statusOptions.equals("1")){
		 		 booklistForm.setStatus("Booklist submitted but not authorized ");
		 	 }
		  else if(statusOptions.equals("0")){
		 		 booklistForm.setStatus("Booklist started but not submitted ");
		 	 }
		  else if(statusOptions.equals("7")){
		 		 booklistForm.setStatus("Booklist open for editing by administrator ");
		 	 }
		  else {
		 		 booklistForm.setStatus("Book list published by administrator");
		 	 }
  return  mapping.findForward("viewbooklist");
}
	public ActionForward display(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		 	  BooklistForm booklistForm = (BooklistForm) form;
		 	  HttpSession session = request.getSession(true);
		 	  String def= booklistForm.getCollege();
 	  
 	 return mapping.findForward("viewbooklist");	 	  
	}

	public ActionForward back(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
			  BooklistForm booklistForm = (BooklistForm) form;
		      BooklistDAO dao = new BooklistDAO();
		      String booklistTypes = booklistForm.getBooklistTypes();

		 	  int college =Integer.parseInt(booklistForm.getCollegeCode());
		 	  String yr =booklistForm.getYear();		  
			  
		  if (booklistForm.getViewStudyUnits().equals("a")){			  
              booklistForm.setCollege("-1");
              booklistForm.setCollegeCode("-1");
		      booklistForm.setSchool("-1");
		      booklistForm.setDepartment("-1");
		      booklistForm.setPrevYear(booklistForm.getYear());
		      booklistForm.setStart(1);			      
		      booklistForm.setEnd(booklistForm.getRecords());
		  }
		  else 
		  {	
			  
			  
			  String statusOptions=booklistForm.getStatusOptions();
			  booklistForm.setPrescribedBooks(dao.getPrescibedBooksStat(statusOptions,yr,college,booklistTypes));       
 		      booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(),dao.getPrescibedBooksStat(statusOptions,yr,college,booklistTypes)));
			  booklistForm.setViewStudyUnits("a");             
		      booklistForm.setSchool("-1");
		      booklistForm.setDepartment("-1");
		      booklistForm.setPrevYear(booklistForm.getYear());
		      booklistForm.setStart(1);		      
		      booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
		    
		     return mapping.findForward("viewbooklist");   
		  }
		return mapping.findForward("viewforward");
	}
	
	public  ActionForward viewbooklist(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      BooklistDAO dao = new BooklistDAO();
		      HttpSession session = request.getSession(true);
		 	  BooklistForm booklistForm = (BooklistForm) form;
		 	  ActionMessages messages = new ActionMessages();
		 	 
		 	  String def="-1";
		 	  String dept=booklistForm.getDepartment();//selected department
		 	  String sch =booklistForm.getSchool();//selected school
		 	  String col =booklistForm.getCollegeCode();//selected college	 
		 	  String booklistTypes = booklistForm.getBooklistTypes();
		 	 
		 	  int college =Integer.parseInt(booklistForm.getCollegeCode());
		 	  String yr =booklistForm.getYear();
		 	  String statusOptions=booklistForm.getStatusOptions();  
		 	  
		       if (booklistForm.getViewStudyUnits().equals("m"))
		       {  
		    	   if (def.equals(sch)||(def.equals(dept)))
		    	   {messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("booklist.noschoolDepartment"));
	       	 		addErrors(request, messages); 	       	 		
	       	 		booklistForm.setSomeprescribedBooks(null);
	       	 		booklistForm.setPrescribedBooks(null);  
	       	 	
	       	    	return mapping.findForward("viewbooklist");	 
		    	   }
		    	   else{
		 			    booklistForm.setPrescribedBooks(dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions));//---
		 			    booklistForm.setModules(dao.getModules(booklistForm.getPrescribedBooks()));
		 			    booklistForm.setFirstmodules(dao.getFirstModules(booklistForm.getModules()));
		 			    booklistForm.setStart(1);		 	    
		 		 		booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
		 		 		booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(), booklistForm.getPrescribedBooks()));
		 		  		booklistForm.setOlddept(dept);
		 		 		booklistForm.setOldsch(sch);
		 		 		booklistForm.setSelectedModules(null);
		 		 		return mapping.findForward("viewmodules");	 
		    		   		    		  
		    		   }
	       	 
	         }


		 	  if ((def.equals(sch))&(def.equals(dept)))
               {
		 		session.setAttribute("departments",dao.getDepts(col));
	        	booklistForm.setPrescribedBooks(dao.getPrescibedBooksStat(statusOptions,yr,college,booklistTypes)); 	      
			    booklistForm.setStart(1);
			    booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
			    booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(),dao.getPrescibedBooksStat(statusOptions,yr,college,booklistTypes)));
			    }
		 	 else if ((!def.equals(sch))& (def.equals(dept)))
	            {   
		 		booklistForm.setPrescribedBooks(dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions));		 		
		 		session.setAttribute("departments",dao.getDepts(col, sch));
	            booklistForm.setStart(1);
	            booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
	            booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(),dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions)));
	            } 
		 	 else if (def.equals(sch)&(!def.equals(dept)))
        		{
        		booklistForm.setPrescribedBooks(dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions));
        		session.setAttribute("departments",dao.getDepts(col));
                booklistForm.setStart(1);
                booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
                booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(), dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions)));
            	}	  
		 	 else if (!def.equals(sch)&(!def.equals(dept)))
         	{
		 	
		 	 booklistForm.setPrescribedBooks(dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions));
		 	 session.setAttribute("departments",dao.getDepts(col, sch));		 	
             booklistForm.setStart(1);
             booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
             booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(), booklistForm.getPrescribedBooks()));
    		}	  
         if (booklistForm.getNumitems()<1)
          	{booklistForm.setStart(0);
          	 booklistForm.setEnd(0);
          	}         
		 booklistForm.setOlddept(dept);
 		 booklistForm.setOldsch(sch);
         booklistForm.setModules(dao.getModules(booklistForm.getPrescribedBooks()));
         booklistForm.setFirstmodules(dao.getFirstModules(booklistForm.getModules()));
        return mapping.findForward("viewbooklist");
	}	
 
	  
	
	public  ActionForward viewselectedmodules(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      BooklistDAO dao = new BooklistDAO();
		      HttpSession session = request.getSession(true);
		 	  BooklistForm booklistForm = (BooklistForm) form;
		 	  ActionMessages messages = new ActionMessages();		 	  
		
		 	  String def="-1";
		 	  String dept=booklistForm.getDepartment();//selected department
		 	  String olddept=booklistForm.getOlddept();
		 	  String oldsch =booklistForm.getOldsch();
		 	  //olddept 
		 	  String sch =booklistForm.getSchool();//selected school
		 	  String col =booklistForm.getCollegeCode();//selected college	
		 	 String booklistTypes = booklistForm.getBooklistTypes();
		 	 String statusOptions=booklistForm.getStatusOptions(); 
		 	  int college =Integer.parseInt(booklistForm.getCollegeCode());
		 	  String yr =booklistForm.getYear();		 	  
		 	 // System.out.println("dept"+dept);
		 	 //System.out.println("sch"+sch);
			 	 if (booklistForm.getSelectedModules() ==null){
			 	  		 		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("booklist.noselection"));
			 	 		 addErrors(request, messages); 			 
			 	 		return mapping.findForward("viewmodules");	 
				 	 }	 	  
		 	  

			 	 String[] codes =booklistForm.getSelectedModules();		 	  
		 	 if (booklistForm.getViewStudyUnits().equals("a"))
		 	   {	
		 		 if (def.equals(dept))
		 		 {booklistForm.setPrescribedBooks(dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions));}
		 		 else if(def.equals(sch))
		 		 		{
		 			 booklistForm.setPrescribedBooks(dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions));}
		 		 else { 
		 			 
		 			 booklistForm.setPrescribedBooks(dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions));}
		 		 
		 	     booklistForm.setStart(1);		 	    
	             booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
	             booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(), booklistForm.getPrescribedBooks()));
		 	   return mapping.findForward("viewbooklist");
		 	   }
		 	 else if(dept.equals(olddept)&& sch.equals(oldsch)){		 			 
		 			 booklistForm.setPrescribedBooks(dao.getPrescibedBooks1(yr, college, sch, dept,codes,booklistTypes, statusOptions));	 	
		 			 booklistForm.setStart(1);		 	    
		 			 booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
		 			 booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(), booklistForm.getPrescribedBooks()));
		 			 booklistForm.setOlddept(dept);
		 			 booklistForm.setOldsch(sch);		 			 
		 			return mapping.findForward("viewselectedmodules"); 
		 		 	}
		 	 else if (def.equals(sch)||(def.equals(dept)))
		 	 		{
		 		     
		 		     
		 		     messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("booklist.noschoolDepartment"));
		 	 		 addErrors(request, messages);
		 	 		System.out.println("Is department = -1 ? and school not ");
		 	 		// booklistForm.setModules(dao.getModules(booklistForm.getPrescribedBooks()));
		 	 		 return mapping.findForward("viewselectedmodules");
		 	 		}
		 		 else{	
		 			
		 			    booklistForm.setPrescribedBooks(dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions));
		 			    booklistForm.setModules(dao.getModules(booklistForm.getPrescribedBooks()));
		 			    booklistForm.setFirstmodules(dao.getFirstModules(booklistForm.getModules()));
		 			    booklistForm.setStart(1);		 	    
		 		 		booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
		 		 		booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(), booklistForm.getPrescribedBooks()));
		 		 		booklistForm.setOlddept(dept);
		 		 		booklistForm.setOldsch(sch);
		 		 		
		 		 		return mapping.findForward("viewmodules");		 	
		           }		 	 
		 	 


} 


	public  ActionForward viewmodules(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      BooklistDAO dao = new BooklistDAO();
		      HttpSession session = request.getSession(true);
		 	  BooklistForm booklistForm = (BooklistForm) form;
		 	  ActionMessages messages = new ActionMessages();
		 	  
		 	 
		 	  String def="-1";
		 	  String dept=booklistForm.getDepartment();//selected department
		 	  String sch =booklistForm.getSchool();//selected school
		 	  String col =booklistForm.getCollegeCode();//selected college	 	  
		 	  int college =Integer.parseInt(booklistForm.getCollegeCode());
		  	 String booklistTypes = booklistForm.getBooklistTypes();
		 	 String statusOptions=booklistForm.getStatusOptions(); 
		 	  String yr =booklistForm.getYear();
		 	  
		         
			    if (def.equals(sch)||(def.equals(dept)))
		       	 {messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("booklist.noschoolDepartment")); 
		        	addErrors(request, messages);		      
		       	 //booklistForm.setModules(dao.getModules(booklistForm.getPrescribedBooks()));
		        	return mapping.findForward("viewmodules"); 
		       	 } 	  

      		if (!def.equals(sch)&&(!def.equals(dept)))
       	    {
      		
      		 booklistForm.setPrescribedBooks(dao.getPrescibedBooks(yr, college, sch, dept,booklistTypes,statusOptions));
		 	 session.setAttribute("departments",dao.getDepts(col, sch));		 	
		 	 booklistForm.setStart(1);
		 	 booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
		 	 booklistForm.setSomeprescribedBooks(dao.pager(booklistForm.getStart(),booklistForm.getRecords(), booklistForm.getPrescribedBooks()));
  		    }
      		else{messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("booklist.noschoolDepartment"));
      			addErrors(request, messages);      	
      		}
      		booklistForm.setModules(dao.getModules(booklistForm.getPrescribedBooks())); 
      		booklistForm.setFirstmodules(dao.getFirstModules(booklistForm.getModules()));	
      		
      		if (booklistForm.getNumitems()<1)
      		{booklistForm.setStart(0);
      		booklistForm.setEnd(0);
      		}
      		booklistForm.setOlddept(dept);
      		booklistForm.setOldsch(sch);

		return mapping.findForward("viewmodules");      
	}	

	
	public synchronized ActionForward goforth(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      BooklistForm booklistForm = (BooklistForm) form;
		      BooklistDAO dao = new BooklistDAO();
		    
		      int start=booklistForm.getStart();
		      int records=booklistForm.getRecords();
		      int college =Integer.parseInt(booklistForm.getCollegeCode());
		      
		 	  String yr =booklistForm.getYear();
		 	  String collegecode = booklistForm.getCollegeCode();	  


		      if (start+records>(booklistForm.getNumitems()))
              {booklistForm.setStart(start);
               booklistForm.setSomeprescribedBooks(dao.pager(start,records, booklistForm.getPrescribedBooks()));       
               booklistForm.setEnd(booklistForm.getNumitems());
              }
		      else if (start+records-1<=(booklistForm.getNumitems()))
		      {  start=booklistForm.getEnd()+1;
		    	 int end= start+records-1;
		    	 booklistForm.setStart(start);
		    	 booklistForm.setSomeprescribedBooks(dao.pager(start,records, booklistForm.getPrescribedBooks())); 
		         end=Math.min(end,booklistForm.getNumitems());
                 booklistForm.setEnd(end);
  		      }

		      else
		    	 {start=booklistForm.getEnd();
		    	 int end= start+records-1;
		    	  booklistForm.setStart(start);
		    	  booklistForm.setSomeprescribedBooks(dao.pager(start,records, booklistForm.getPrescribedBooks())); 
		      	  end=Math.min(end,booklistForm.getNumitems());
                  booklistForm.setEnd(end);
   		          }
		      if ((booklistForm.getStart()+booklistForm.getRecords()-1)==booklistForm.getNumitems())
		      {
		    	  start=booklistForm.getStart();
		    	 int end=booklistForm.getNumitems();
	              end=Math.min(end,booklistForm.getNumitems());
	              booklistForm.setEnd(end);
	              booklistForm.setSomeprescribedBooks((booklistForm.getPrescribedBooks().subList(start, end)));
	          }
		      if (booklistForm.getNumitems()<1){booklistForm.setStart(0);}
			return mapping.findForward("viewbooklist");
	}


	public synchronized  ActionForward goback(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      BooklistDAO dao = new BooklistDAO();

		      BooklistForm booklistForm = (BooklistForm) form;

		      int start=booklistForm.getStart();
		      int records=booklistForm.getRecords();
		 	  String yr =booklistForm.getYear();
		 	  String collegecode = booklistForm.getCollegeCode();

		      if (start-records<=0)		    	  
              { booklistForm.setSomeprescribedBooks(dao.pager(0,records, booklistForm.getPrescribedBooks()));
		        booklistForm.setStart(1);
                booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
              }
		    else if (start+records<booklistForm.getNumitems())
		     {int end=booklistForm.getStart()-1;
		      start=booklistForm.getStart()-booklistForm.getRecords();
		      booklistForm.setSomeprescribedBooks(dao.pager(start,records, booklistForm.getPrescribedBooks()));
		      booklistForm.setStart(start);
              booklistForm.setEnd(Math.min(end, booklistForm.getNumitems()));
             }
		    else
		    {int end=booklistForm.getStart()-1;
		    start=booklistForm.getStart()-booklistForm.getRecords();
		    booklistForm.setSomeprescribedBooks(dao.pager(start,records, booklistForm.getPrescribedBooks()));
	        booklistForm.setStart(start);
            booklistForm.setEnd(Math.min(end, booklistForm.getNumitems()));
            }
		      if (booklistForm.getNumitems()<1){booklistForm.setStart(0);}		    
return mapping.findForward("viewbooklist");
}


	public synchronized ActionForward begin(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      BooklistDAO dao = new BooklistDAO();
		      BooklistForm booklistForm = (BooklistForm) form;
		      
		      int start=booklistForm.getStart();
		      int records=booklistForm.getRecords();
		      int college =Integer.parseInt(booklistForm.getCollegeCode());		      
		 	  String yr =booklistForm.getYear();
		 	  String collegecode = booklistForm.getCollegeCode();
		 	  booklistForm.setSomeprescribedBooks(dao.pager(0,records, booklistForm.getPrescribedBooks()));
	          booklistForm.setStart(1);
              booklistForm.setEnd(Math.min(booklistForm.getRecords(), booklistForm.getNumitems()));
            
              if (booklistForm.getNumitems()<1){booklistForm.setStart(0);}

		      return mapping.findForward("viewbooklist");
	}

	public synchronized ActionForward verylast(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      BooklistDAO dao = new BooklistDAO();

		      BooklistForm booklistForm = (BooklistForm) form;
		      int numitems=booklistForm.getNumitems();
		      int start;
		  
		      int records=booklistForm.getRecords();
		      int college =Integer.parseInt(booklistForm.getCollegeCode());
		      
		 	  String yr =booklistForm.getYear();
		 	  String collegecode = booklistForm.getCollegeCode();			      

		      if (booklistForm.getNumitems()==0) {return mapping.findForward("viewbooklist");}

		      if ((booklistForm.getStart()+booklistForm.getRecords()-1)==booklistForm.getNumitems())
		      {
		    	  start=booklistForm.getStart();
		    	 int end=booklistForm.getNumitems();
	              end=Math.min(end,booklistForm.getNumitems());
	              booklistForm.setEnd(end);
		    	  booklistForm.setSomeprescribedBooks((booklistForm.getPrescribedBooks().subList(start, end)));
    	      }
		      else
		      {start=1+booklistForm.getNumitems()-(booklistForm.getNumitems()%booklistForm.getRecords());
		       booklistForm.setSomeprescribedBooks(dao.pager(start,records, booklistForm.getPrescribedBooks())); 
		       booklistForm.setStart(start);
		       booklistForm.setEnd(numitems);
		      }

		      if ((booklistForm.getStart()-1)==booklistForm.getNumitems())
		        {start=booklistForm.getNumitems()-booklistForm.getRecords()+1;
		         booklistForm.setSomeprescribedBooks((booklistForm.getPrescribedBooks().subList(start, booklistForm.getNumitems())));
	   	         booklistForm.setStart(booklistForm.getNumitems()-booklistForm.getRecords()+1);
		        }
		      if (booklistForm.getNumitems()<1){booklistForm.setStart(0);}
		      if (booklistForm.getStart()>booklistForm.getNumitems()){booklistForm.setStart(booklistForm.getNumitems());}

		      return mapping.findForward("viewbooklist");
	}

}

