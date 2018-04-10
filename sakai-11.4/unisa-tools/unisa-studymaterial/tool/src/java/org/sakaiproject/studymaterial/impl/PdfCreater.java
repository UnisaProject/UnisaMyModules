package org.sakaiproject.studymaterial.impl;

import org.sakaiproject.studymaterial.utils.*;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PdfCreater {

	static Log  log = LogFactory.getLog(PdfCreater.class);
	public static byte[] readBinaryData(InputStream stream) throws Exception
	{
	    byte[] buffer = new byte[8192];
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();

	    int bytesRead;
	    while ((bytesRead = stream.read(buffer)) != -1)
	    {
	        baos.write(buffer, 0, bytesRead);
	    }
	    return baos.toByteArray();
	}
	public static  long getTotBytes( DataInputStream in)
			throws Exception {

		int w = in.read();
		long totBytes=0;
		if(w != -1){
		   totBytes++;
		}
		while (w != -1) {
			w = in.read();
			if(w != -1){
				   totBytes++;
				}
		}
		return  totBytes;
	}
	public static long getFileSize(String filename,String module,String docType){
		    long fileSize=10;
	        try
		     {
	        	
			    DataInputStream in=getStreamOfFileInWeb(filename, module,docType);
			    if( in!=null){
			           long totBytes=getTotBytes(in);
  	                   fileSize=totBytes/1000;
	        	}
  	      	}catch(Exception ex){
  	      	           	return 0;
  	      	}
	        log.info("***** SONETTE - in PdfCreater.java - getFileSize");
		return fileSize;
	}
	
	public static   DataInputStream  getStreamOfFileInWeb(String filename,String module,String docType){
		                                   DataInputStream    in=null;
		            	                   try{
		            		                      String filepath=Utilities.getFilepath(filename, module,docType);
		            		                      File pdfFile = new File(filepath);
		   			                              if(pdfFile.exists()){
		   			                                    in = new DataInputStream(new FileInputStream(pdfFile));
		   			                              }
		   			          		   		}catch(Exception ex){}
		   		                        return in;
	}
	public static   DataInputStream  getStreamOfFileInWeb(String filepath){
        DataInputStream    in=null;
        try{
               File pdfFile = new File(filepath);
                  if(pdfFile.exists()){
                        in = new DataInputStream(new FileInputStream(pdfFile));
                  }
		   		}catch(Exception ex){}
        return in;
}

}
