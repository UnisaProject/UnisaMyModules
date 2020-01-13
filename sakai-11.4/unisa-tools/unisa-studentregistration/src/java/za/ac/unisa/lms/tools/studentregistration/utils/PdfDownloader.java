package za.ac.unisa.lms.tools.studentregistration.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.sakaiproject.component.cover.ServerConfigurationService;

public class PdfDownloader {
	
	public void processPDFRequest(String pdfPath, String pdfFileName, HttpServletResponse response) throws IOException{


                File pdfFile = new File(pdfPath+"/"+pdfFileName);
                
                if (!pdfFile.exists()) {
                	//log.debug("PdfDownloader - processPDFRequest - File not Found - pdfFile="+pdfPath+"/"+pdfFileName);
        			response.sendError(HttpServletResponse.SC_NOT_FOUND);
        			return;
        		}
                //log.debug("PdfDownloader - processPDFRequest - File Found - pdfFile="+pdfPath+"/"+pdfFileName);
                // We need to get a byte count. Internet Explorer has problems
				// if we don't make the setContentLength() call.
			    response.setHeader("Cache-Control", "private"); // HTTP 1.1.
		        response.setHeader("Pragma", "cache");
				response.setContentType("Application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename="+pdfFileName);
				response.setContentLength((int) pdfFile.length());
				saveToClient(response, pdfFile);
	}

	public static void saveToClient(HttpServletResponse response, File file) {
		//log.debug("PdfDownloader - saveToClient - Start");
		
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		int DEFAULT_BUFFER_SIZE = 1024;
		try {
			input = new BufferedInputStream(new FileInputStream(file),
					DEFAULT_BUFFER_SIZE);
			output = new BufferedOutputStream(response.getOutputStream(),
					DEFAULT_BUFFER_SIZE);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
				output.flush();
			}
		} catch (Exception e) {

		} finally {
			closeBuffers(input, output);
		}
		//log.debug("PdfDownloader - saveToClient - End");
	}
	
	public static void closeBuffers(BufferedInputStream input,BufferedOutputStream output){
		 try{
		        input.close();  
		        output.close();
		 } catch (Exception e) {}
		}
}
