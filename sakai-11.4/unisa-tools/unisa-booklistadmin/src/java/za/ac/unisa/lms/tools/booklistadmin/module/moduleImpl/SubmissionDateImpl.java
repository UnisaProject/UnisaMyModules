package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import za.ac.unisa.lms.tools.booklistadmin.dao.DateUtil;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.BookSubmissionDate;
import za.ac.unisa.lms.tools.booklistadmin.module.BookSubmissionDateModule;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.BookHelperClasses.BookSubmissionValidator;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookSubmissionDateDAO;
import org.apache.struts.action.ActionMessages;

public class SubmissionDateImpl {
	
	  BookSubmissionDateDAO submissionDateDao;
	   DateUtil dateUtility;
	  public  SubmissionDateImpl(BookSubmissionDateDAO submissionDateDao,DateUtil dateUtility) {
		           this.submissionDateDao=submissionDateDao;
		           this.dateUtility=dateUtility;
	   }	
	   
	  private void saveData(int level,BooklistAdminForm booklistAdminForm,int academicYear,String networkId) {
				       int openingYear=Integer.parseInt(booklistAdminForm.getOpeningYear());
			           int openingMonth=Integer.parseInt(booklistAdminForm.getOpeningMonth());
				       int endOfMonth=dateUtility.getMonthEndDay(openingMonth,openingYear);
				       int openingDay=Integer.parseInt(booklistAdminForm.getOpeningDay());
				       if(openingDay>endOfMonth){
     	                      booklistAdminForm.setOpeningDay(""+endOfMonth);
                       }
                       endOfMonth=dateUtility.getMonthEndDay(Integer.parseInt(booklistAdminForm.getClosingMonth()),Integer.parseInt(booklistAdminForm.getClosingYear()));
        if(Integer.parseInt(booklistAdminForm.getClosingDay())>endOfMonth){
     	                booklistAdminForm.setClosingDay(""+endOfMonth);
        }
	       boolean recordExists=submissionDateDao.checkAcademicYear(booklistAdminForm.getTypeOfBookList(),academicYear,level);
        String openingDateStr=booklistAdminForm.getOpeningMonth()+"-"+booklistAdminForm.getOpeningDay()+"-"+booklistAdminForm.getOpeningYear();
        String closingDateStr=booklistAdminForm.getClosingMonth()+"-"+booklistAdminForm.getClosingDay()+"-"+booklistAdminForm.getClosingYear();
        if(recordExists){
        	submissionDateDao.updateSubmissionDates(openingDateStr,closingDateStr,booklistAdminForm.getTypeOfBookList(),academicYear,level);
        	submissionDateDao.updateLogsForUpdatingLogs(booklistAdminForm.getNetworkId(),academicYear,level,booklistAdminForm.getTypeOfBookList());
        }else{
        	submissionDateDao.saveSubmissionDates(networkId,academicYear,level,booklistAdminForm.getTypeOfBookList(),openingDateStr,closingDateStr);
        	submissionDateDao.updateLogsForSavingDates(booklistAdminForm.getNetworkId(),academicYear,level,booklistAdminForm.getTypeOfBookList());
        }
		
     }
  public void removeSubmissionDate(BooklistAdminForm booklistAdminForm,HttpServletRequest request){
	  
	                 if( booklistAdminForm.getDateRemovalScrnEntered()){
		                   List dateslist=booklistAdminForm.getBooklist();
                           Iterator dateslistIterator=dateslist.iterator();
                           while(dateslistIterator.hasNext()){
        	                          BookSubmissionDateModule submissionData=(BookSubmissionDateModule)dateslistIterator.next();
        	                          String bookListType=booklistAdminForm.getTypeOfBookList();
        	                          int year=Utilities.toInt(submissionData.getAcademicYear());
            	                      int level=Utilities.toInt(submissionData.getLevel());
        	                          if(submissionData.isRemove()){
        		   		                        submissionDateDao.deleteAcademicYear(bookListType,year,level );
        	                          }
           	                 }
                      }
	                   booklistAdminForm.setDateRemovalScrnEntered(true);
	                   booklistAdminForm.setDateRemoved(true);
	                   List currSettingList=submissionDateDao.getCurrSettingOfSubmissionDates(booklistAdminForm.getTypeOfBookList());
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
		                             booklistAdminForm.setDateRemovalScrnEntered(true);
                                     booklistAdminForm.setDateRemoved(false);
                                    return "confirmscreenreleasedates";
		                    }else{
		                    	     bookSubmissionValidator.noSelection(messages);
		                             bookSubmissionDate.createDateManagementScrn(booklistAdminForm,request);
    	                             return "submissiondates";
	                      }
  

     }
     public void saveBookSubmissionDate(BooklistAdminForm booklistAdminForm,String  networkId,HttpSession session,int academicYear){
    	
                 if(booklistAdminForm.getLevel().equals("All")){
    	             for(int x=0;x<8;x++){
    		                 if(submissionDateDao.checkAcademicYear(booklistAdminForm.getTypeOfBookList(),academicYear,x)){
    			                 submissionDateDao.deleteAcademicYear(booklistAdminForm.getTypeOfBookList(),academicYear,x);
    		                  }
    	              }//for
    	              for(int x=0;x<8;x++){
    	                      saveData(x,booklistAdminForm,academicYear,networkId);
    	               }//for
                 }else{
    	                int level=Integer.parseInt(booklistAdminForm.getLevel());
    	                saveData(level,booklistAdminForm,academicYear,networkId);
                 }
                  List currSettingList=submissionDateDao.getCurrSettingOfSubmissionDates(booklistAdminForm.getTypeOfBookList());
                  booklistAdminForm.setBooklist(currSettingList);
                  session.setAttribute("booklist",booklistAdminForm.getBooklist());
                  session.setAttribute("yearsList",booklistAdminForm.getYearsList());
                  session.setAttribute("closingYearsList",booklistAdminForm.getClosingYearsList());
                  session.setAttribute("levelList",booklistAdminForm.getLevelList());
                  session.setAttribute("monthList",booklistAdminForm.getMonthList());
                  session.setAttribute("dayList",booklistAdminForm.getDaysList());
    	 }

  
}
