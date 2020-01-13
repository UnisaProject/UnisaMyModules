package za.ac.unisa.lms.tools.telecentre.forms;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.Cursor;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import za.ac.unisa.lms.tools.telecentre.dao.TelecentreDAO;
import za.ac.unisa.lms.tools.telecentre.dao.TelecentreDetails;
import za.ac.unisa.lms.tools.telecentre.dao.extendedHourDetails;
public class excelReport extends TelecentreVisits{
	  public List generateExcelWorkBookData(TelecentreForm telecentreForm){
 		          dateUtil du=new dateUtil();
 		          telecentreForm.setCode("All");
		          telecentreForm.setStudentNr("");
		          return  getWorkBookData(telecentreForm,du);
	  }
	  public List getExtendeHoursData(TelecentreForm telecentreForm){
	          dateUtil du=new dateUtil();
	          String fromDate=getStartDateForVisits(telecentreForm);
	          String toDate=getEndDateForVisits(telecentreForm);
	          TelecentreDAO dao=new TelecentreDAO();
	          return dao.getHoursExtensionAuditList(fromDate,toDate);
	  }
	  public void saveToClient(DataInputStream in, ServletOutputStream out) throws Exception {
   		        int w = in.read();
         		while (w != -1) {
		          	 out.write(w);
			         w = in.read();
		        }
		        out.flush();
		        out.close();
		        in.close();
	 }
     public boolean saveWorkBook(String filePath,String filename,List telecentreDetails) {
    	   	boolean saved=true;
    	    try{
    		        java.io.File fileobject = new java.io.File(filePath+filename);
                    java.io.FileWriter  fw= new java.io.FileWriter(fileobject);
                    String seperatewith = "\t";
                    fw.write("PROVINCE" + seperatewith);
    	            fw.write("NAME"+ seperatewith);
    	            fw.write("TELE_ID" + seperatewith);
    	            fw.write("DATE" + seperatewith);
    	            fw.write("STARTTIME"+ seperatewith);
    	            fw.write("ENDTIME"+ seperatewith);
    	            fw.write("TOTAL_DURATION"+ seperatewith);
    	            fw.write("TOTAL_HRS"+ seperatewith);
    	            fw.write("STUDENT_NUMBER" + seperatewith);
    	            fw.write(" "+((char)13)+((char)10));
                    for(int i=0; i < telecentreDetails.size(); i++) {
        	             fw.write(((TelecentreDetails)telecentreDetails.get(i)).getProvince() + seperatewith);
        	             fw.write(((TelecentreDetails)telecentreDetails.get(i)).getCentre() + seperatewith);
        	             fw.write(((TelecentreDetails)telecentreDetails.get(i)).getTeleId()+ seperatewith);
        	             fw.write(((TelecentreDetails)telecentreDetails.get(i)).getTodate() + seperatewith);
        	             fw.write(((TelecentreDetails)telecentreDetails.get(i)).getTimeIn() + seperatewith);
        	             fw.write(((TelecentreDetails)telecentreDetails.get(i)).getTimeOut() + seperatewith);
        	             fw.write(((TelecentreDetails)telecentreDetails.get(i)).getDuration() + seperatewith);
        	             fw.write(((TelecentreDetails)telecentreDetails.get(i)).getDurationReport() + seperatewith);
        	             fw.write(((TelecentreDetails)telecentreDetails.get(i)).getStudentNr() + seperatewith);
                         fw.write(" "+((char)13)+((char)10));
                    }//for
                    fw.flush();
                    fw.close();
             
    }catch (java.io.IOException  iox){}
    return  saved;
   }
    public boolean saveHoursExtensionRecords(String filePath,String filename,List extendedHoursDetails) {
	   	boolean saved=true;
	    try{
		        java.io.File fileobject = new java.io.File(filePath+filename);
                java.io.FileWriter  fw= new java.io.FileWriter(fileobject);
                String seperatewith = "\t";
                fw.write("USER" + seperatewith);
	            fw.write("DATE"+ seperatewith);
	            fw.write("ALLOCATION TYPE" + seperatewith);
	            fw.write("MONTH" + seperatewith);
	            fw.write("TELE_ID" + seperatewith);
	            fw.write("STUDENT_NUMBER" + seperatewith);
	            fw.write("HOURS"+ seperatewith);
	            fw.write(" "+((char)13)+((char)10));
                for(int i=0; i < extendedHoursDetails.size(); i++) {
    	             fw.write(((extendedHourDetails)extendedHoursDetails.get(i)).getAuditUser() + seperatewith);
    	             fw.write(((extendedHourDetails)extendedHoursDetails.get(i)).getAuditDate()+ seperatewith);
    	             fw.write(((extendedHourDetails)extendedHoursDetails.get(i)).getAllocType()+ seperatewith);
    	             fw.write(((extendedHourDetails)extendedHoursDetails.get(i)).getMonth() + seperatewith);
    	             fw.write(((extendedHourDetails)extendedHoursDetails.get(i)).getTeleId() + seperatewith);
    	             fw.write(((extendedHourDetails)extendedHoursDetails.get(i)).getStudentNr()+ seperatewith);
    	             fw.write(((extendedHourDetails)extendedHoursDetails.get(i)).getHours() + seperatewith);
    	             fw.write(" "+((char)13)+((char)10));
                }//for
                fw.flush();
                fw.close();
          }catch (java.io.IOException  iox){}
          return  saved;
   }
   
}


