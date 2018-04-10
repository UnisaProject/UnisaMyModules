package za.ac.unisa.lms.tools.booklistadmin.module.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;


import za.ac.unisa.lms.tools.booklistadmin.dao.DateUtil;
import za.ac.unisa.lms.tools.booklistadmin.dao.BookReleaseDateDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;

public class BookReleaseDateUI {
	
	BookReleaseDateDAO dao;
	DateUtil dateutility;
	public BookReleaseDateUI(BookReleaseDateDAO dao,DateUtil dateutility){
		         this.dao=dao;
		         this.dateutility=dateutility;
	}
	public void  createReleaseDateManagementScrn(BooklistAdminForm booklistAdminForm,HttpServletRequest request){
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
        if(booklistAdminForm.getTypeOfBookList().equals("P")){
                booklistAdminForm.setTypeOfBookListStr("Prescribed Books");
        }else if(booklistAdminForm.getTypeOfBookList().equals("E")){
                 booklistAdminForm.setTypeOfBookListStr("E-Reserves");
        }else{
            booklistAdminForm.setTypeOfBookListStr("Recommended Books");
        }
        DateUtil dateUtil=new DateUtil();
        List currSettingList=dao.getCurrSettingOfReleaseDates(booklistAdminForm.getTypeOfBookList());
        booklistAdminForm.setBooklist(currSettingList);
        booklistAdminForm.setAcademicYear(""+(dateutility.yearInt()+1));
        booklistAdminForm.setOpeningMonth(""+dateutility.getMonthIntStr());
        booklistAdminForm.setOpeningDay(""+dateutility.getDayIntStr());
        booklistAdminForm.setOpeningYear(""+dateutility.yearInt());
        booklistAdminForm.setYearsList(arrList);
        booklistAdminForm.setMonthList(dateUtil.generateMonthList());
        booklistAdminForm.setDaysList(dateUtil.generateDayList());
        HttpSession session = request.getSession(true);
        session.setAttribute("yearsList",booklistAdminForm.getYearsList());
        session.setAttribute("monthList",booklistAdminForm.getMonthList());
        session.setAttribute("dayList",booklistAdminForm.getDaysList());
        session.setAttribute("booklist",booklistAdminForm.getBooklist());
        booklistAdminForm.setReleaseDateRemovalScrnEntered(false);
}

}
