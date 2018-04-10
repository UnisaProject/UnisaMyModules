package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessages;

import za.ac.unisa.lms.tools.booklistadmin.dao.DateUtil;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookReleaseDateDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.BookSubmissionDate;
import za.ac.unisa.lms.tools.booklistadmin.module.BookSubmissionDateModule;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookSubmissionValidator;

public class BookReleaseDateImpl {
	
	
	  BookReleaseDateDAO bookReleaseDateDao;
	   DateUtil dateUtility;
	   public  BookReleaseDateImpl(BookReleaseDateDAO bookReleaseDateDao,DateUtil dateUtility) {
		        this.bookReleaseDateDao=bookReleaseDateDao;
		        this.dateUtility=dateUtility;
	   }
	private void saveReleaseData(BooklistAdminForm booklistAdminForm,int academicYear){
		   boolean recordExists=bookReleaseDateDao.checkAcademicYearInRelDates(booklistAdminForm.getTypeOfBookList(),academicYear);
	   int endOfMonth=dateUtility.getMonthEndDay(Integer.parseInt(booklistAdminForm.getOpeningMonth()),Integer.parseInt(booklistAdminForm.getOpeningYear()));
	    if(Integer.parseInt(booklistAdminForm.getOpeningDay())>endOfMonth){
	 	    booklistAdminForm.setOpeningDay(""+endOfMonth);
	    }
	    String releaseDateStr=booklistAdminForm.getOpeningMonth()+"-"+booklistAdminForm.getOpeningDay()+"-"+booklistAdminForm.getOpeningYear();
	   if(recordExists){
		   bookReleaseDateDao.updateReleaseDates(releaseDateStr,booklistAdminForm.getTypeOfBookList(),academicYear);
		   bookReleaseDateDao.updateLogsForReleaseDates(booklistAdminForm.getNetworkId(),academicYear,booklistAdminForm.getTypeOfBookList());
	    }else{
	    	bookReleaseDateDao.saveReleaseDates(academicYear,booklistAdminForm.getTypeOfBookList(),releaseDateStr);
	    	bookReleaseDateDao.updateLogsSaveReleaseDates(booklistAdminForm.getNetworkId(),academicYear,booklistAdminForm.getTypeOfBookList());
	    }
	}
	public void removeReleaseDate(BooklistAdminForm booklistAdminForm,HttpServletRequest request){
		if( booklistAdminForm.isReleaseDateRemovalScrnEntered()){
			List dateslist=booklistAdminForm.getBooklist();
	        Iterator dateslistIterator=dateslist.iterator();
	        while(dateslistIterator.hasNext()){
	        	BookSubmissionDateModule submissionData=(BookSubmissionDateModule)dateslistIterator.next();
	        	if(submissionData.isRemove()){
	        		bookReleaseDateDao.removeAcademicYearInRelDates(booklistAdminForm.getTypeOfBookList(), 
	        				Utilities.toInt(submissionData.getAcademicYear()));
	           	}
	        }
		    booklistAdminForm.setReleaseDateRemovalScrnEntered(true);
		    booklistAdminForm.setReleaseDateRemoved(true);
	      }
 	      List currSettingList=bookReleaseDateDao.getCurrSettingOfReleaseDates(booklistAdminForm.getTypeOfBookList());
	      booklistAdminForm.setBooklist(currSettingList);
	      HttpSession session = request.getSession(true);
	      session.setAttribute("booklist",booklistAdminForm.getBooklist());
	}
	 public  String   confirmDateRemovaL(BooklistAdminForm booklistAdminForm,HttpServletRequest request,ActionMessages  messages){
         boolean deleteStatus = false;
         List dateslist=booklistAdminForm.getBooklist();
         Iterator dateslistIterator=dateslist.iterator();
         BookSubmissionValidator bookSubmissionValidator=new BookSubmissionValidator();
         BookSubmissionDate bookSubmissionDate=new BookSubmissionDate();
         while(dateslistIterator.hasNext()){
 	                BookSubmissionDateModule submissionData=(BookSubmissionDateModule)dateslistIterator.next();
                      if (submissionData.isRemove()){
                            deleteStatus = true;											
                   }
          }	
          if (deleteStatus){
	                 bookSubmissionValidator.confirmSubmissioinDateRemoval(messages, booklistAdminForm);
	                 booklistAdminForm.setReleaseDateRemovalScrnEntered(true);
					 booklistAdminForm.setReleaseDateRemoved(false);
					  return "confirmscreenreleasedates";
           }else{
           	        bookSubmissionValidator.noSelection(messages);
                    bookSubmissionDate.createDateManagementScrn(booklistAdminForm,request);
                    return "releasedates";
         }
   }
	  public void saveBookReleaseDate(BooklistAdminForm booklistAdminForm,HttpSession session,int academicYear){
		             saveReleaseData(booklistAdminForm,academicYear);
		             List currSettingList=bookReleaseDateDao.getCurrSettingOfReleaseDates(booklistAdminForm.getTypeOfBookList());
		             booklistAdminForm.setBooklist(currSettingList);
		             session.setAttribute("booklist",booklistAdminForm.getBooklist());
		             session.setAttribute("yearsList",booklistAdminForm.getYearsList());
		             session.setAttribute("monthList",booklistAdminForm.getMonthList());
		             session.setAttribute("dayList",booklistAdminForm.getDaysList());
	}
     
}
