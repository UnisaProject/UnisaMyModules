package za.ac.unisa.lms.tools.booklistadmin.module.view;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.booklistadmin.dao.BookSubmissionDateDAO;
import za.ac.unisa.lms.tools.booklistadmin.dao.DateUtil;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class BookSubmissionDateUI {
	
	BookSubmissionDateDAO dao;
	DateUtil dateutility;
	public BookSubmissionDateUI(BookSubmissionDateDAO dao,DateUtil dateutility){
		         this.dao=dao;
		         this.dateutility=dateutility;
	}
	public void  createDateManagementScrn(BooklistAdminForm booklistAdminForm,HttpServletRequest request){
          ArrayList <LabelValueBean> arrList2=new ArrayList <LabelValueBean>();
        String[] arr2={"All","0","1","2","3","4","5","6","7"};
        for(int x=0;x< arr2.length;x++){
	              arrList2.add(new org.apache.struts.util.LabelValueBean(arr2[x],arr2[x]));
        }//for
        DateUtil dateutility=new DateUtil();
        int prevYear=dateutility.yearInt()-1;
        String[] arr3=new String[4];
        for(int x=0;x<4;x++){
	          arr3[x]=""+prevYear;
	          prevYear++;
        }
        ArrayList <LabelValueBean> arrList=new ArrayList <LabelValueBean>();
        for(int x=0;x< arr3.length;x++){
	            arrList.add(new org.apache.struts.util.LabelValueBean(arr3[x],arr3[x]));
        }//for
        prevYear=dateutility.yearInt()-1;
        String[] arr4=new String[21];
        for(int x=0;x<21;x++){
              arr4[x]=""+prevYear;
              prevYear++; 
        }
        ArrayList <LabelValueBean> arrList3=new ArrayList <LabelValueBean>();
        for(int x=0;x< arr4.length;x++){
                arrList3.add(new org.apache.struts.util.LabelValueBean(arr4[x],arr4[x]));
        }//for
        if(booklistAdminForm.getTypeOfBookList().equals("P")){
	              booklistAdminForm.setTypeOfBookListStr("Prescribed Books");
        }else if(booklistAdminForm.getTypeOfBookList().equals("E")){
	                booklistAdminForm.setTypeOfBookListStr("E-Reserves");
        }else{
	             booklistAdminForm.setTypeOfBookListStr("Recommended Books");
        }
        DateUtil  dateUtil=new DateUtil();
        List currSettingList=dao.getCurrSettingOfSubmissionDates(booklistAdminForm.getTypeOfBookList());
        booklistAdminForm.setBooklist(currSettingList);
        booklistAdminForm.setAcademicYear(""+(dateutility.yearInt()+1));
        booklistAdminForm.setOpeningMonth(""+dateutility.getMonthIntStr());
        booklistAdminForm.setOpeningDay(""+dateutility.getDayIntStr());
        booklistAdminForm.setOpeningYear(""+dateutility.yearInt());
        booklistAdminForm.setYearsList(arrList);
        booklistAdminForm.setClosingYearsList(arrList3);
        booklistAdminForm.setLevelList(arrList2);
        booklistAdminForm.setMonthList(dateUtil.generateMonthList());
        booklistAdminForm.setDaysList(dateUtil.generateDayList());
        booklistAdminForm.setClosingMonth("12");
        HttpSession session = request.getSession(true);
        session.setAttribute("yearsList",booklistAdminForm.getYearsList());
        session.setAttribute("closingYearsList",booklistAdminForm.getClosingYearsList());
        session.setAttribute("levelList",booklistAdminForm.getLevelList());
        session.setAttribute("monthList",booklistAdminForm.getMonthList());
        session.setAttribute("dayList",booklistAdminForm.getDaysList());
        session.setAttribute("booklist",booklistAdminForm.getBooklist());
        booklistAdminForm.setDateRemovalScrnEntered(false);
}

}
