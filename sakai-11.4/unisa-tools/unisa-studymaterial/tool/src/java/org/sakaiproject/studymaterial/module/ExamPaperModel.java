package org.sakaiproject.studymaterial.module;

import java.io.File;

import org.sakaiproject.studymaterial.utils.Utilities;

public class ExamPaperModel extends StudyMaterialModel{
	private String  periodDesc;
	private String  fileSizeInBytes;
	private boolean fileExist;
	public boolean isFileExist(){
		     String examPaperFilepath=Utilities.currServer()+ getFilePath().substring(9).replace("\">", "");
		     File file=new File(examPaperFilepath);
		    fileExist=file.exists();
		    return  fileExist;
	}
	public String getFileSizeInBytes(){
		    return fileSizeInBytes;
	}
	public void setFileSizeInBytes(String fileSizeInBytes){
	           this.fileSizeInBytes=fileSizeInBytes;
    }
	public String getPeriodDesc(){
		if (getPeriod().equalsIgnoreCase("1")){
			        periodDesc="January/February";
			}
			if(getPeriod().equalsIgnoreCase("6")){
			       periodDesc="May/June";
			}
			if(getPeriod().equalsIgnoreCase("10")){
			      periodDesc="October/November";
			}
	        return periodDesc;
	}
	public String  getExamPaperLanguage(){
		       if(getLanguage().equalsIgnoreCase("E")){
			         return "English";
		        }
		       return "Afrikaans";
		
	}
	public String getConveFileSize(){
		   int filesize = Integer.parseInt(getFileSizeInBytes())/1024;
	        return Integer.toString(filesize);
	}
	public String getFilepasthInServer(){
		return       Utilities.currServer()+ getFilePath().substring(9).replace("\">", "");
	}

}
