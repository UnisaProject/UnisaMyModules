package za.ac.unisa.lms.booklistadmin.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenericUtil {
	public void saveToClient(String filepath ,DataInputStream in, ServletOutputStream out) throws Exception {

		int w = in.read();
		for(int x=0;x<filepath.length();x++){
			 out.write(filepath.charAt(x));
		 }
		while (w != -1) {
			out.write(w);
			w = in.read();
		}

		out.flush();
		out.close();
		in.close();
	}
	public String processInvalidRequest(String realpath,HttpServletRequest request,HttpServletResponse response){
		String filepath="";
        try{
                String pdffullPath=request.getParameter("path");
                String filename=request.getParameter("filename");
                File pdfFile = new File(pdffullPath);
                        DataInputStream in = new DataInputStream(new FileInputStream(pdfFile));
			       //InputStream inputStream = new FileInputStream(pdfFile);
                ServletOutputStream out = response.getOutputStream();
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "filename=" + filename);
		           saveToClient(realpath,in, out);
        }catch(Exception ex){}
      return filepath;
}
	public boolean inValidRequest(String type){
		  if(type.equals("X")){
			    return  true;
		  }else{
			  return  false;
		  }
		
	}
}
