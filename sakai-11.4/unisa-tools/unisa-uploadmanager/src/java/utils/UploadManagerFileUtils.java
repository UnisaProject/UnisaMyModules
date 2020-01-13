package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import org.sakaiproject.component.cover.ServerConfigurationService;

public class UploadManagerFileUtils {
	
	public  static String  checkInputFile(String inputFilePath,String inputFilename){
		
		                 MetaDataValidator metaDataValidator=new MetaDataValidator();
		                 File testSize = new File(inputFilePath);
		                 if((inputFilename==null)||inputFilename.equals("")){
		                	 return  "You have not selected a file";
		                 }
		                 String fileExtension=inputFilename.substring(inputFilename.lastIndexOf(".")+1);
		                 String errorMsg=metaDataValidator.validateInputFile(testSize.length(), fileExtension);
		                 if(!errorMsg.equals("")){
		                	     return errorMsg;
		                  }
	                  return "";
	}
	public  static int getInputFileSize(String inputFilePath){
		             File testSize = new File(inputFilePath);
		             int size = (int)testSize.length();
		          return size;
	}
	public static  boolean  deleteFile(String inputFilePath){
                     File file = new File(inputFilePath);
                     return file.delete();
	}
	public String setInputFileToRequest(HttpServletRequest request){
		               String inputFilepathsStr="";
                	   try{
			                   inputFilepathsStr = request.getAttribute("file").toString();
			                   request.setAttribute("inputFileName", inputFilepathsStr);
		               } catch (NullPointerException ne){
			
		               }
                	   return inputFilepathsStr;
	}
	public static  void closeBuffers(BufferedInputStream input,BufferedOutputStream output){
		 try{
		        input.close();  
		        output.close();
		 } catch (Exception e) {}
		}
	

}
