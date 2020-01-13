package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;

import org.apache.struts.util.LabelValueBean;

public class DateUtil{
	public int getMonthIndex(String month){
		String[] monthArr= {"January", "February","March", "April", "May", "June", "July",
				"August", "September", "October", "November","December"};
		int index=-1;
		for(int x=0;x<12;x++){
			if(monthArr[x].equals(month))
				   index=x;
				break;
		}
		return index;
	}
	
	public String getMonthIntStr(){
		//return the number of the current month as a string
	      return getMonthIntStr(getMonthInt());
}
	public String getMonthIntStr(String month){
		     return getMonthIntStr(getMonthIndex(month));
		
	}
	public String getMonthIntStr(int fromMonthInt){
		if(fromMonthInt<10){
		     return "0"+fromMonthInt;
	   }else{
		   return ""+fromMonthInt;
	   }
	}
	public String getDayIntStr(int fromDayInt){
		if(fromDayInt<10){
		     return "0"+fromDayInt;
	   }else{
		   return ""+fromDayInt;
	   }
	}
	
	public String getToDateStr(int toYearInt,int monthInt){
		java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
		String suffix="";
		if((monthInt==1)||(monthInt==3)||(monthInt==5)||(monthInt==7)||(monthInt==8)||(monthInt==10)||(monthInt==12)){
			suffix="31";
		}
		if((monthInt==4)||(monthInt==6)||(monthInt==9)||(monthInt==11)){
			suffix="30";
		}
		if(monthInt==2){
			if(cal.isLeapYear(toYearInt)){
   			    suffix="29";
			}else{
				suffix="28";
			}
		}
		return   toYearInt+"-"+getMonthIntStr(monthInt)+"-"+suffix;
	}
	public ArrayList generateDayList(){
		String[] dayArr={"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
				          "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		ArrayList<LabelValueBean>  arrList=new ArrayList <LabelValueBean>();
    	  for(int x=0;x<31;x++){
    		   arrList.add(new org.apache.struts.util.LabelValueBean(dayArr[x],dayArr[x]));
    	  }//for
    	  return arrList;
 }
  public ArrayList generateMonthList(){
		String[] monthArr={"January","February","March","April","May","June",
        		"July","August","September","October","November","December"};
		ArrayList<LabelValueBean>  arrList=new ArrayList <LabelValueBean>();
    	  for(int x=0;x<12;x++){
    	      arrList.add(new org.apache.struts.util.LabelValueBean(monthArr[x].toString(),monthArr[x].toString()));
    	  }//for
    	  return arrList;
	}
  public ArrayList generateMonthsList(){
		String[] monthArr={"January","February","March","April","May","June",
      		"July","August","September","October","November","December"};
		ArrayList<LabelValueBean>  arrList=new ArrayList <LabelValueBean>();
		String tempStr="";
  	  for(int x=1;x<13;x++){
  		    tempStr=""+x;
  		    if(x<10){
  		    	tempStr="0"+x;
  		    }
  		 arrList.add(new org.apache.struts.util.LabelValueBean(monthArr[x-1].toString(),tempStr));
  	  }//for
  	  return arrList;
	}
  public ArrayList generateMonthList2(){
		String[] monthArr={"January","February","March","April","May","June",
      		"July","August","September","October","November","December"};
		 DateFormat dateFormat2 = new SimpleDateFormat("MM");
		 Calendar cal=Calendar.getInstance();
         String monthStr=dateFormat2.format(cal.getTime());
         int  month=Integer.parseInt(monthStr);
 	     ArrayList<LabelValueBean>  arrList=new ArrayList <LabelValueBean>();
  	  for(int x=(month-1);x<12;x++){
  	      arrList.add(new org.apache.struts.util.LabelValueBean(monthArr[x],monthArr[x]));
  	  }//for
  	  return arrList;
	}
  public String[] monthsArr(){
		      String[] monthArr={"January","February","March","April","May","June",
    		                  "July","August","September","October","November","December"};
			 return monthArr;
	}
	public ArrayList generatePeriodList(){
		     String [] datesArr={"Last 7 Days","Last 30 Days","All","Custom"};
		     ArrayList<LabelValueBean>  arrList=new ArrayList <LabelValueBean>();
	    	 for(int x=0;x<datesArr.length;x++){
	    	      arrList.add(new org.apache.struts.util.LabelValueBean(datesArr[x].toString(),datesArr[x].toString()));
	    	 }//for
	    	 return arrList;
	}
	public ArrayList generatePeriodList2(){
	     String [] datesArr={"Last 30 Days","All","Custom"};
	     ArrayList<LabelValueBean>  arrList=new ArrayList <LabelValueBean>();
   	 for(int x=0;x<datesArr.length;x++){
   	      arrList.add(new org.apache.struts.util.LabelValueBean(datesArr[x].toString(),datesArr[x].toString()));
   	 }//for
   	 return arrList;
}
	public Date today(){
		Calendar cal = Calendar.getInstance();
        return cal.getTime();
	}
	
	public  boolean validateDateStr(String dateStr){
	  if((dateStr==null)||(dateStr.length()!=10)){
		   return false;
	  }
	   if((dateStr.charAt(4)!='-')&&(dateStr.charAt(7)!='-')){
		     return false;
	   }
	   if((checkYearStr(dateStr.substring(0,4)))&&(checkMonthStr(dateStr.substring(5,7)))&&(checkDayStr(dateStr.substring(8)))){
		    return  true;   
	   }else{
		   return false;
	   }
  }
	public  boolean validateDateStr(String dateStr,char delimiter){
		  if((dateStr==null)||(dateStr.length()!=10)){
			   return false;
		  }
		   if((dateStr.charAt(4)!=delimiter)&&(dateStr.charAt(7)!=delimiter)){
			     return false;
		   }
		   if((checkYearStr(dateStr.substring(0,4)))&&(checkMonthStr(dateStr.substring(5,7)))&&(checkDayStr(dateStr.substring(8)))){
			    return  true;   
		   }else{
			   return false;
		   }
	  }
	public  boolean validateCurrYearDateStr(String dateStr,char delimiter){
		                 
		                  if(!dateStrLength(dateStr)){
		                	  return false;
		                  }
		                  if(!delimitersValid(dateStr,'/')){
		                	  return false;
		                  }
		                  String monthStr=dateStr.substring(5,7);
		                  String dayStr=dateStr.substring(8);
		                  if(validateCurrYear(dateStr) && 
		                		  checkMonthStr(monthStr) && checkDayStr(dayStr)){
			                     return  true;   
		                  }else{
			                     return false;
		                  }
	  }
	 public  boolean validateDateStrGivenYear(String dateStr,char delimiter,int compYear){
                             if(!dateStrLength(dateStr)){
      	                          return false;
                             }
                             if(!delimitersValid(dateStr,'/')){
      	                          return false;
                             }
                             String monthStr=dateStr.substring(5,7);
                             String dayStr=dateStr.substring(8);
                             if(validateYear(dateStr,compYear) && 
      		                        checkMonthStr(monthStr) && checkDayStr(dayStr)){
                                     return  true;   
                             }else{
                                     return false;
                             }
    }
	private boolean dateStrLength(String dateStr){
		               if((dateStr==null)||(dateStr.length()!=10)){
                              return false;
                        }else{
                	          return true;
                      }
	}
    private boolean validateCurrYear(String dateStr){
    	                 String currYearStr=""+yearInt();
                         boolean yearValid=(currYearStr.equals(dateStr.substring(0,4)));
                         if(yearValid){
                                return true;
                         }else{
   	                            return false;
                         }
	}
    private boolean validateYear(String dateStr,int compYear){
    	                   int yearInDate=Integer.parseInt(dateStr.substring(0,4));
                           if(yearInDate>=compYear){
                                  return true;
                           }else{
                                  return false;
                           }
    }
	private boolean delimitersValid(String dateStr,char delimiter){
		               if((dateStr.charAt(4)==delimiter)&&(dateStr.charAt(7)==delimiter)){
                              return true;
		               }else{
		            	      return false;
                     }
	}
    private boolean checkYearStr(String yearStr){
	        if((yearStr.charAt(0)=='2')&&(yearStr.charAt(1)=='0')&&
	            (Character.isDigit(yearStr.charAt(2)))&&(Character.isDigit(yearStr.charAt(3)))){
	            	return true;
	        }else{
	        	return false;
	        }
  }
  private boolean checkDayStr(String dayStr){
	  String [] strArr={"0","1","2","3","4","5","6","7","8","9",};
	     if(Character.isDigit(dayStr.charAt(0))&& Character.isDigit(dayStr.charAt(0))){
	    	 if((dayStr.charAt(0)=='3')&&((dayStr.charAt(1)=='0')||(dayStr.charAt(1)=='1'))){
	    		 return  true;
		     }
	    	 if((dayStr.charAt(0)=='0')&&(dayStr.charAt(1)=='0')){
	    		 return false;
	    	 }
	    	 boolean secDateCharValid=inStrArr(strArr,""+dayStr.charAt(1));
	    	 boolean firstDateCharValid=((dayStr.charAt(0)=='0')||(dayStr.charAt(0)=='1')||(dayStr.charAt(0)=='2'));
	    	 if(secDateCharValid && firstDateCharValid){
	    		    return  true;
	    	 }
	    	 return false;
	     }else{
	    	 return false;
	    }
  }
  private boolean checkMonthStr(String monthStr){
	  String [] charArr={"01","02","03","04","05","06","07","08","09","10","11","12"};
	    
	    	 if(inStrArr(charArr,monthStr)){
	    			     return  true;
	    	 }else{
	    	    return false;
	    }
  }
  private boolean inStrArr(String[] strArr,String str){
	                boolean found =false;
	                for(int x=0;x<strArr.length;x++){
      	                    if(strArr[x].equals(str)){
      		                     found =true;
      		                     break;
      	                    }//if
                    }//for
	               return found;
  }
  public Date  getDateFromString(String dateStr){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if(dateStr.indexOf("7")!=-1){
			cal.add(Calendar.DATE, -7);
  	}else{
  		cal.add(Calendar.DATE, -30);
  	}
		return  cal.getTime();
  }
  public String getStartDateFromString(String dateStr){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if(dateStr.indexOf("7")!=-1){
			cal.add(Calendar.DATE, -7);
	}else{
		cal.add(Calendar.DATE, -30);
	}
		return  dateFormat.format(cal.getTime());
}
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
	public boolean  isDateGreaterThanSysDate(String dateStr){//yyyymmdd
		                Calendar cal = Calendar.getInstance();
		                long sysTimeInMillis=cal.getTimeInMillis();
		                long enteredDateInMillis=getTimeInMillisFromDateStr(dateStr);
		                if(enteredDateInMillis>sysTimeInMillis){
		                	return true;
		                }else{
		                	return false;
		                }
	}
	public String getNextDate(int daysToAdd, String delimiter){
		//gets the date after adding 'daysToAdd' days to the current system time 
		               Calendar cal = Calendar.getInstance();
		               cal.add(Calendar.DAY_OF_MONTH,daysToAdd);
		               int monthInt=cal.get(Calendar.MONTH);
		               monthInt++;
		               int dayInt=cal.get(Calendar.DAY_OF_MONTH);
		               int year=cal.get(Calendar.YEAR);
		               String monthStr=""+monthInt;
		               String dayStr=""+dayInt;
		               if(monthInt<10){
		            	   monthStr="0"+monthInt;
		               }
		               if(dayInt<10){
		            	   dayStr="0"+dayInt;
		               }
		               return ""+year+delimiter+monthStr+delimiter+dayStr;
	}
	public boolean  isDateGreaterThanSecDate(String firstDateStr,String secDatedateStr){//yyyy/mm/dd  or yyyy-mm-dd
                        Calendar cal = Calendar.getInstance();
                        long firstDateInMillis=getTimeInMillisFromDateStr(firstDateStr);
                        long secDateInMillis=getTimeInMillisFromDateStr(secDatedateStr);
                        if(firstDateInMillis>secDateInMillis){
        	                 return true;
                        }else{
        	                 return false;
                       }
   }
	public boolean cmpYear(int year,String cmpDateStr){
		          String firstSub=cmpDateStr.trim().substring(0,4);
		          if(firstSub.equals(""+year)){
		        	   return true;
		          }else{
		        	   return false;
		          }
	}
	public boolean cmpMonth(int month,String cmpDateStr){
        String firstSub=cmpDateStr.trim().substring(5,7);
        if(Integer.parseInt(firstSub)==month){
      	     return true;
        }else{
      	     return false;
        }
    }

public int  monthToInt(String month){
    String  monthIntStr="-1";	
	 if(month.equals("January")){
	   	    monthIntStr = "01";
    }else if(month.equals("February")){
	          monthIntStr = "02";
    }else if(month.equals("March")){
   	 monthIntStr = "03";
  }else if(month.equals("April")){
	 monthIntStr = "04";
  }else if(month.equals("May")){
	 monthIntStr = "05";
  }else if(month.equals("June")){
	 monthIntStr = "06";
  }else if(month.equals("July")){
	 monthIntStr = "07";
  }else if(month.equals("August")){
	 monthIntStr = "08";
  }else if(month.equals("September")){
	 monthIntStr = "09";
  }else if(month.equals("October")){
	 monthIntStr = "10";
  }else if(month.equals("November")){
	 monthIntStr = "11";
  }else if(month.equals("December")){
	 monthIntStr = "12";
  }
	Integer intVal=new Integer(monthIntStr);
	return  intVal.intValue();
}
public String  monthToIntStr(String month){
    	String monthIntStr="";
	if(month.equals("January")){
		 monthIntStr = "01";
  }else if(month.equals("February")){
	   monthIntStr = "02";
   }else if(month.equals("March")){
   	 monthIntStr = "03";
  }else if(month.equals("April")){
	 monthIntStr = "04";
  }else if(month.equals("May")){
	 monthIntStr = "05";
  }else if(month.equals("June")){
	 monthIntStr = "06";
  }else if(month.equals("July")){
	 monthIntStr = "07";
  }else if(month.equals("August")){
	 monthIntStr = "08";
  }else if(month.equals("September")){
	 monthIntStr = "09";
  }else if(month.equals("October")){
	 monthIntStr = "10";
  }else if(month.equals("November")){
	 monthIntStr = "11";
  }else if(month.equals("December")){
	 monthIntStr = "12";
  }
	return monthIntStr;
}
public String dateDDMMMMYYYY(){
	   Calendar currentDate = Calendar.getInstance();	
	   DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
	   String date= formatter.format(currentDate.getTime());
	   return date;	
}
 public String getTimeStampOfCurrTime(){
        DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    return dateFormat3.format(new Date());
 }
 public int getCurrHourInt(){
	 	 DateFormat dateFormat4= new SimpleDateFormat("HH");
	     String hourStr = dateFormat4.format(new Date());
	     int currHour=Integer.parseInt(hourStr);
	     return currHour;
 }
 public String dateOnly(){
	     DateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd");
         Date date = new Date();
         return dateFormat4.format(date);
 }
 public String todayDateOnly(){
     DateFormat dateFormat4 = new SimpleDateFormat("yyyy/MM/dd");
     Date date = new Date();
     return dateFormat4.format(date);
}
 public String timeOnly(){
          DateFormat dateFormat = new SimpleDateFormat("HH:mm");
          Date date = new Date();
          return  dateFormat.format(date);
 }
 public int getDayOfMonthInt(){
            GregorianCalendar calCurrent = new GregorianCalendar();
            return calCurrent.get(Calendar.DATE);
 }
 public int  getCurrMin(){
     GregorianCalendar calCurrent = new GregorianCalendar();
     return   calCurrent.get(Calendar.MINUTE);
}
 public int getMonthInt(){
	        GregorianCalendar calCurrent = new GregorianCalendar();
	        return calCurrent.get(Calendar.MONTH)+1;
 }
 public String todayDateStr(){
	            return makeDateStr(yearInt(),getMonthInt(),getDayOfMonthInt());
}
 public String getPrevDate(){
	         GregorianCalendar calCurrent = new GregorianCalendar();
	         int monthInt=calCurrent.get(Calendar.MONTH)+1;
	         int dayInt=calCurrent.get(Calendar.DATE);
	         int yearInt=calCurrent.get(Calendar.YEAR);
	         dayInt--;
	         if(dayInt==0){
	        	 monthInt--;
	        	 if(monthInt==0){
	        		 yearInt--;
	        		 monthInt=12;
	        	 }
	        	 dayInt=getEndDayForMonth(monthInt,yearInt);
	         }
	         return makeDateStr(yearInt,monthInt,dayInt);
	 
 }
 private String makeDateStr(int yearInt,int monthInt,int dayInt){
	          String monthIntStr=""+monthInt;
	          if(monthInt<10){
	        	  monthIntStr="0"+monthInt;
	          }
	          String dayIntStr=""+dayInt;
	          if(dayInt<10){
	        	  dayIntStr="0"+dayInt;
	          }
	          return ""+yearInt+"-"+monthIntStr+"-"+dayIntStr;
 }
 private String getMonth(int monthInt){
         String[] monthArr={"January","February","March","April","May","June",
 		 "July","August","September","October","November","December"};
     return monthArr[monthInt];
}
 public  String getCurrMonth(){
	       return getMonth(getMonthInt()-1);
 }
 public int  getYearInt(){
     GregorianCalendar calCurrent = new GregorianCalendar();
     return   calCurrent.get(Calendar.YEAR);
}
 public int  yearInt(){
     GregorianCalendar calCurrent = new GregorianCalendar();
     return   calCurrent.get(Calendar.YEAR);
}
 public String  getYearIntStr(){
        return   ""+yearInt();
}
 public int getEndDayForAmonth(){//return  the end day for the current month of the year 
           int month=getMonthInt(),year=yearInt();
           return getEndDayForMonth(month,year);
 }
 public int getEndDayForMonth(int month,int year){
	  //return  the end day for a given  month of a given year
	 int endday=31;
	 if(month==2){
         GregorianCalendar cal2 = new GregorianCalendar();
         if(cal2.isLeapYear(year)){
	            endday=29;
         }else{
	            endday=28;
         }
      }else{
           if((month==1)||(month==3)||(month==5)||(month==7)||(month==8)||(month==10)||(month==12)){
	              endday=31;
           }else{
	            endday=30;	
           }
      }
	 return endday;
 }
 public ArrayList <LabelValueBean>  getYearsList(){
	  int prevYear=yearInt()-4;
      String[] arr=new String[5];
      for(int x=0;x<arr.length;x++){ 
        arr[x]=""+prevYear;
        prevYear++; 
     }
     ArrayList <LabelValueBean> arrList3=new ArrayList <LabelValueBean>();
     for(int x=0;x< arr.length;x++){
          arrList3.add(new org.apache.struts.util.LabelValueBean(arr[x],arr[x]));
    }//for
     return  arrList3;
   }
   public int calcHours(String endTime,String startTime){
    	//it calulates hours and if there is remaining minutes it adds an hour to the total hours
	   //if the hours are in the same day
               int time2Hr=Integer.parseInt(endTime.substring(0,2));
               int time2Min=Integer.parseInt(endTime.substring(3));
               int time1Hr=Integer.parseInt(startTime.substring(0,2));
               int time1Min=Integer.parseInt(startTime.substring(3));
               int ret=1;
               if(time2Hr>time1Hr){
 	 	    	        ret=time2Hr-time1Hr;
 	 	    	    	if(time2Min>time1Min){
 	 	    	    		ret++;
 		    	    	}
 	 	       }
 	 	       return ret;
   }
   public int calcHours(String startTime){
   	           //it calulates hours and if there is remaining minutes it adds an hour to the total hours
  	          //if calculates the hours that have passed between the given time and current system time
	   
              int time2Hr=getCurrHourInt();
              int time2Min=getCurrMin();
              int time1Hr=Integer.parseInt(startTime.substring(0,2));
              int time1Min=Integer.parseInt(startTime.substring(3));
              int ret=1;
              if(time2Hr>time1Hr){
	 	    	        ret=time2Hr-time1Hr;
	 	    	    	if(time2Min>time1Min){
	 	    	    		ret++;
		    	    	}
	 	       }
	 	       return ret;
  }
 
	 public boolean validateDateYYYYMMDD(String dateStr){
         PlacementUtilities   placementUtilities=new  PlacementUtilities();
         if((dateStr.length()==8)&&( placementUtilities.isInteger(dateStr))){
       	  return true; 
         }else{
       	  return false;
         }
}      
public String changeDateFormatFromDateYYYYMMDD(String dateStr,String delimiter){
       return dateStr.substring(0,4)+delimiter+dateStr.substring(4,6)+delimiter+dateStr.substring(6);    
}
public boolean isValidDate(String inDate, String format) {

if (inDate == null)
return false;

//set the format to use as a constructor argument
SimpleDateFormat dateFormat = new SimpleDateFormat(format);

if (inDate.trim().length() != dateFormat.toPattern().length())
return false;

dateFormat.setLenient(false);

try {
//parse the inDate parameter
dateFormat.parse(inDate.trim());
}
catch (ParseException pe) {
return false;
}
inDate.indexOf("/");
if (inDate.indexOf("/")!=4 || inDate.length()!=10){
return false;
}
inDate = inDate.substring(inDate.indexOf("/")+1);
if (inDate.indexOf("/")!=2 || inDate.length()!=5){
return false;
}
return true;
}
public String getDateYYYYMMDD(){
    DateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String tempDate= dateFormat4.format(date);
    return tempDate.substring(0,4)+tempDate.substring(5,7)+tempDate.substring(8);
}
}