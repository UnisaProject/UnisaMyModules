package utils;
import java.util.List;
import  za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;

public class Validator {
	public String  validateSelectedVariables(UploadManagerForm uploadManagerForm){
		                if (uploadManagerForm.getCourseCode()==null||uploadManagerForm.getCourseCode().equals("-1")){					
		            	        return "Please choose the course code.";
	    	            }
		                if (uploadManagerForm.getAcadYear()==null||uploadManagerForm.getAcadYear().equals("-1")){					
		            	        return "Please choose the Year";
	    	    	    }
		                if (uploadManagerForm.getPeriod()==null||uploadManagerForm.getPeriod().equals("-1")){					
			                   return "Please choose the period";
	    			    }
		                if (uploadManagerForm.getLanguage()==null||uploadManagerForm.getLanguage().equals("-1")){					
			                  return  "Please choose the language";
	    			    }
		                return "";
	}
	public String validateMaterialListFromScm(List materialList){
        if(materialList.contains("Exception")){
                 return "Oracle webservice is down";
        }
        if(materialList.isEmpty()){
                return "There is no data for your selection.";
        }
        return "";
    }
	public String validateInputFile(long  filesize,String fileExtension){
		              if( filesize==0){
		                	  return "You have not attached the file.";
	    	     	  } else if (!fileExtension.equals("pdf")){					
						          return "You can only upload the pdf files.";
	    	     	  }
		              return "";
	}
	

}
