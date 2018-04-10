package UILayer;

public class ScreenFinderUtil {
	
	public String getNextScreenGivenErrorMsg(String  errorMsg){
		              if(errorMsg.equals("You do not have the permission to upload or reload a file")||
		            		  errorMsg.equals("You do not have the permission to upload or reload a file")){
		            	  return   "mainfilter";
		              }else if(errorMsg.equals("Oracle webservice is down")||
		            		  errorMsg.equals("There is no data for your selection.")){
		            	  return "mainfilter";
		            	  
		              }else  if(errorMsg.equals("You have not attached the file.")||
		            		  errorMsg.equals("You can only upload the pdf files.")){
		            	          	  return "fileupload";
		              }else if (errorMsg.equals("You have not selected a file")) {
		            	  return "fileupload";
		              }else if(errorMsg.equals("Please choose the Year")||
		            		  errorMsg.equals("Please choose the course code.")||
		            		  errorMsg.equals("Please choose the period")||
		            		  errorMsg.equals("Please choose the language")){
		            	           return   "mainfilter";
		              }else{
		            	  return  "uploadscreen";
		              }
	}
}
