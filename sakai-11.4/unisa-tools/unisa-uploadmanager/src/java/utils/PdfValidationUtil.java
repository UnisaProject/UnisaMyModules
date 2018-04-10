package utils;


import java.util.List;

import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;

public class PdfValidationUtil extends MetaDataValidator{
	
	public  String  validate(UploadManagerForm uploadManagerForm){
                        String validationErrorMsg=validateSelectedVariables(uploadManagerForm);
                        if(!validationErrorMsg.equals("")){
                                return  validationErrorMsg;
                        }
                        validationErrorMsg=canUserUpload(uploadManagerForm);
                        return  validationErrorMsg;
    }
	private String  canUserUpload(UploadManagerForm uploadManagerForm){
                       if (uploadManagerForm.getFromSearch().equals("nonlecturer")){
                             if (uploadManagerForm.getFrom().equals("plan")||uploadManagerForm.getFrom().equals("normal")){
      	                           return "You do not have the permission to upload or reload a file";
                             }
                       }
                       return "";
    } 
    public String validateMaterialListFromScm(List materialList){
                     if(materialList==null){
 	                         return "Oracle webservice is down";
                     }
                     if(materialList.isEmpty()){
 	                        return "There is no data for your selection.";
                     }
                     return "";
   }
}
