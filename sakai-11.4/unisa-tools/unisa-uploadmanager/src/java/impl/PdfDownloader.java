package impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
public class PdfDownloader {
	
	
	public void sendPdfDataToClient(HttpServletResponse response,String pdffullPath,String filename ) throws Exception{
		           File pdfFile = new File(pdffullPath);
		           DataInputStream in = new DataInputStream(new FileInputStream(pdfFile));
		           ServletOutputStream out = response.getOutputStream();
		           response.setHeader("Cache-Control", "private"); // HTTP 1.1.
		           response.setHeader("Pragma", "cache");
		           response.setContentType("APPLICATION/OCTET-STREAM");   
		           response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
		       /*    response.setContentType("application/pdf"); */
		           saveToClient(in, out);
	
	}
	public static void saveToClient(DataInputStream in, ServletOutputStream out) throws Exception {

		//int w = in.read();
		
		byte[] buffer = new byte[4096];
        int bytesRead = -1;
         
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
         

/*		while (w != -1) {
			out.write(w);
			w = in.read();
		}*/

		//out.flush();
		//out.close();
		in.close();
	}

}
