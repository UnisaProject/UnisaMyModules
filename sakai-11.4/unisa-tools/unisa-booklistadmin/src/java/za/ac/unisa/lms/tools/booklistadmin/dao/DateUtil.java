package za.ac.unisa.lms.tools.booklistadmin.dao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.struts.util.LabelValueBean;

public class DateUtil{
	public  static String getTimeStampOfCurrTime(){
	        DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    return dateFormat3.format(new Date());
	 }

	public  static int getCurrHourInt(){
	 	 DateFormat dateFormat4= new SimpleDateFormat("HH");
	     String hourStr = dateFormat4.format(new Date());
	     int currHour=Integer.parseInt(hourStr);
	     return currHour;
    }
 
	public  static Date  getDateFromStr(String dateStr){
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
	
  /*public ArrayList generateMonthList(){
		String[] monthArr={"January","February","March","April","May","June",
        		"July","August","September","October","November","December"};
		ArrayList<LabelValueBean>  arrList=new ArrayList <LabelValueBean>();
    	  for(int x=0;x<12;x++){
    	      arrList.add(new org.apache.struts.util.LabelValueBean(monthArr[x].toString(),monthArr[x].toString()));
    	  }//for
    	  return arrList;
	}*/
  public ArrayList generateMonthList(){
		String[] monthArr={"January","February","March","April","May","June",
      		"July","August","September","October","November","December"};
		ArrayList<LabelValueBean>  arrList=new ArrayList <LabelValueBean>();
  	  for(int x=0;x<12;x++){
  		  String monthVal="";
  		  if(x<9){
  			    monthVal="0"+(x+1); 
  		  }else{
  			   monthVal=""+(x+1);
  		  }
  	      arrList.add(new org.apache.struts.util.LabelValueBean(monthArr[x].toString(),monthVal));
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
  private boolean checkYearStr(String yearStr){
	        if((yearStr.charAt(0)=='2')&&(yearStr.charAt(1)=='0')&&
	            (Character.isDigit(yearStr.charAt(2)))&&(Character.isDigit(yearStr.charAt(3)))){
	            	return true;
	        }else{
	        	return false;
	        }
  }
  private boolean checkDayStr(String dateStr){
	  char[] charArr={'3','4','5','6','7','8','9'};
	     if(Character.isDigit(dateStr.charAt(0))&& Character.isDigit(dateStr.charAt(0))){
	    	 if((dateStr.charAt(0)=='3')&&((dateStr.charAt(1)=='0')||(dateStr.charAt(1)=='1'))){
	    		 return  true;
		     }
	    	 if(!inCharArr(charArr,dateStr.charAt(0))){
	    		 return  true;
	    	 }
	    	 if((dateStr.charAt(0)=='0')&&(dateStr.charAt(1)=='0')){
	    		 return false;
	    	 }
	    	 return false;
	     }else{
	    	 return false;
	    }
  }
  private boolean checkMonthStr(String dateStr){
	  char[] charArr={'2','3','4','5','6','7','8','9'};
	    if(Character.isDigit(dateStr.charAt(0))&& Character.isDigit(dateStr.charAt(0))){
	    	 if((dateStr.charAt(0)=='1')&&((dateStr.charAt(1)=='0')||(dateStr.charAt(1)=='1')||(dateStr.charAt(1)=='1'))){
	    		 return  true;
		     }
	    	 if(!inCharArr(charArr,dateStr.charAt(0))){
	    		 return  true;
	    	 }
	    	 if((dateStr.charAt(0)=='0')&&(dateStr.charAt(1)=='0')){
	    		 return false; 
	    	 }
	    	 return false;
	     }else{
	    	 return false;
	    }
  }
  private boolean inCharArr(char[] charArr,char charac){
	  boolean found =false;
	   for(int x=0;x<charArr.length;x++){
      	if(charArr[x]==charac){
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
	public boolean isDateInRange(Date date,Date firstInRange,Date secInRange){
	    if(firstInRange.equals(date)||secInRange.equals(date)){
	    	return  true;
	    }
	    if(firstInRange.before(date)&& secInRange.after(date)){
	    	return  true;
	    }else{
	    	return false;
	    }
}
public boolean isDateInRange(Date date,Date firstInRange){
    if(firstInRange.equals(date)){
 	  return  true;
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

 public int getDayOfMonthInt(){
            GregorianCalendar calCurrent = new GregorianCalendar();
            return calCurrent.get(Calendar.DATE);
 }
 public int getMonthInt(){
	        GregorianCalendar calCurrent = new GregorianCalendar();
	        return calCurrent.get(Calendar.MONTH)+1;
 }
 public String getMonthIntStr(){
         int	 monthInt=getMonthInt();
    	 if(monthInt<10){
		     return "0"+monthInt;
	     }else{
		     return ""+monthInt;
	   }
 }
 public String getDayIntStr(){
     int	 dayInt=getDayOfMonthInt();
	 if(dayInt<10){
	     return "0"+dayInt;
     }else{
	     return ""+dayInt;
   }
}
 public  String getMonth(int monthInt){
         String[] monthArr={"January","February","March","April","May","June",
 		 "July","August","September","October","November","December"};
     return monthArr[monthInt];
}
 public  String getCurrMonth(){
	       return getMonth(getMonthInt());
 }
 public int  yearInt(){
	     GregorianCalendar calCurrent = new GregorianCalendar();
	     return   calCurrent.get(Calendar.YEAR);
 }
 public int getEndDayForAmonth(){
           int endday=31;
           int month=getMonthInt(),year=yearInt();
           if(getMonthInt()==2){
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
 public  int getMonthEndDay(int month,int year){
	           int endDay=30;
	           if((month==1)||(month==3)||(month==5)||(month==7)||(month==8)||(month==10)||(month==12)){
		              endDay =31;
	           }
	           if((month==4)||(month==6)||(month==9)||(month==11)){
		              endDay= 30;
	           }
	           if(month==2){
		            endDay=28;
		            GregorianCalendar cal=new GregorianCalendar();
		            if(cal.isLeapYear(year)){
			              endDay=29;
		            }
	           }
	           return endDay;
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

}