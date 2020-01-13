package org.sakaiproject.studymaterial.utils;

import java.util.Calendar;
import java.util.Date;
public class DateUtil{
	
  public Date  getDateFromStr(String dateStr){
		Calendar cal = Calendar.getInstance();
		String yearStr=dateStr.substring(0,4);
		String monthStr=dateStr.substring(5,7);
		String dayStr=dateStr.substring(8);
		int year=Integer.parseInt(yearStr);
		int month=Integer.parseInt(monthStr);
		month--;
		int day=Integer.parseInt(dayStr);
		cal.clear();
		cal.set(year,month,day);
		return  cal.getTime();
	}
	public Date  getDateFromStr(String dateStr,int endIndex){
		Calendar cal = Calendar.getInstance();
		String yearStr=dateStr.substring(0,4);
		String monthStr=dateStr.substring(5,7);
		String dayStr=dateStr.substring(8,endIndex);
		int year=Integer.parseInt(yearStr);
		int month=Integer.parseInt(monthStr);
		month--;
		int day=Integer.parseInt(dayStr);
		cal.clear();
		cal.set(year,month,day);
		return  cal.getTime();
	}
	public long  getTimeInMillisFromDateStr(String dateStr){
		Calendar cal = Calendar.getInstance();
		String yearStr=dateStr.substring(0,4);
		String monthStr=dateStr.substring(5,7);
		String dayStr=dateStr.substring(8);
		int year=Integer.parseInt(yearStr);
		int month=Integer.parseInt(monthStr);
		month--;
		int day=Integer.parseInt(dayStr);
		cal.clear();
		cal.set(year,month,day);
		return  cal.getTimeInMillis();
	}
	public static  int  getCurrYear(){
	               Calendar now = Calendar.getInstance();
 	               return  now.get(Calendar.YEAR);
    }
    public static  String getCurrMonth(){
                	Calendar now = Calendar.getInstance();
                	int month=now.get(Calendar.MONTH) + 1;
                	if (month < 10) {
                		return  "0"+month;
                	}
    	           return ""+month;
    }
    public static String  getCurrDay(){
    	         Calendar now = Calendar.getInstance();
	             int day=now.get(Calendar.DAY_OF_MONTH);
	             if (day < 10) {
	            	 return "0"+day;
	             }
	             return ""+day;
    }
    public static String todayStr(){
            return ""+getCurrYear()+"-"+getCurrMonth()+"-"+ getCurrDay();
    }
	public Date today(){
		           Calendar cal = Calendar.getInstance();
                   return cal.getTime();
	}
	public boolean  isDateBeforeSysDate(String dateStr){//yyyy-mm-dd
		                Date today=today();
		                Date availableDate=getDateFromStr(dateStr);
		                if(availableDate.after(today)){
		                	  return false;
		                }else{
		                	   return true;
		                }
	}

}