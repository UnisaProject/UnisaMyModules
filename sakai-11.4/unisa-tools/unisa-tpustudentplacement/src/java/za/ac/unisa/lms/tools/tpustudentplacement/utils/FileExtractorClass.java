package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FileExtractorClass{
	
	public void  extractFile(HttpServletRequest request,
			            HttpServletResponse response,String fileToRead,
			            String outputFileName){
	//tell browser program going to return an application file 
        //instead of html page
        response.setContentType("application/octet-stream");
        response.setHeader("Pragma", "private");
        response.setHeader("Cache-Control", "private, must-revalidate");
        String secParam="attachment;filename="+outputFileName;
        response.setHeader("Content-Disposition",secParam);
        
 
	    try{
		      ServletOutputStream out = response.getOutputStream();	
		      String fileName = "";
		      fileName = fileToRead;
		      File file = new File(fileName);
		      BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
		      byte[] buf = new byte[4 * 1024]; // 4K buffer
		      int bytesRead;
		      while ((bytesRead = is.read(buf)) != -1) {
		            out.write(buf, 0, bytesRead);
		      }
		      is.close();
		      out.close(); 			 
	   }catch (Exception e) {e.printStackTrace();}	
	}

}
