package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateUtil{
	
	public static  Date  getDateFromStr(String dateStr){
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
  public static Date  getDateFromStr(String dateStr,int endIndex){
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
	 public static String todayDate(){
	           DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
             return dateFormat.format(date);
    }
}