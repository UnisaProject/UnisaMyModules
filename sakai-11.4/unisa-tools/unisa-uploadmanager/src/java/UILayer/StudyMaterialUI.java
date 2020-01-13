package UILayer;

import impl.StudyMaterial;
import java.util.List;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import utils.Validator;
import  za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;
public class StudyMaterialUI {
	
	public String createStudyMaterialViewScreen(UploadManagerForm uploadManagerForm){
		     try {
		                List materialList=getStudyMaterialList(uploadManagerForm);
                        Validator validator=new Validator();
                        String validationErrorMsg=validator.validateSelectedVariables(uploadManagerForm);
                        if(!validationErrorMsg.equals("")){
     	                         return  validationErrorMsg;
                        }
                        validationErrorMsg=validator.validateMaterialListFromScm(materialList);
                        if(!validationErrorMsg.equals("")){
        	                   return  validationErrorMsg;
                        }
                        setListOfMaterialInFormBean(uploadManagerForm,materialList);
                        return "";
		     } catch(Exception e) {
		    	 return e.getMessage();
		     }
  }
	
	private List getStudyMaterialList(UploadManagerForm uploadManagerForm) throws Exception{
		                 String modCode = uploadManagerForm.getCourseCode();
		                 String year = uploadManagerForm.getAcadYear();
		                 String period = uploadManagerForm.getPeriod();
		                 String lang = uploadManagerForm.getLanguage();
		                 String type = uploadManagerForm.getType();
		                 StudyMaterial studyMaterial=new StudyMaterial();
		              return  studyMaterial.getStudyMaterialList(modCode, year, period, lang, type);
    }
	private void setListOfMaterialInFormBean(UploadManagerForm uploadManagerForm,List materialList){
		               uploadManagerForm.setMaterialList(materialList);
		               /*if(uploadManagerForm.getFromSearch().equals("nonlecturer")&&
		            		   uploadManagerForm.getFrom().equals("normal")){
		            	             StudyMaterialImpl studyMaterialImpl=new StudyMaterialImpl();
		            	             List materialWithPdfInServer=studyMaterialImpl.removeMaterialWithNoPdfInServer(materialList);
		            	             uploadManagerForm.setMaterialList(materialWithPdfInServer);
                       }*/
	}
	public  String createViewStudyMaterialScreen(UploadManagerForm uploadManagerForm,ActionMessages messages){
                       StudyMaterialUI studyMaterialUI=new StudyMaterialUI();
                       String errorMsg=studyMaterialUI.createStudyMaterialViewScreen(uploadManagerForm);
                       if(!errorMsg.equals("")){
      	                    messages.add(ActionMessages.GLOBAL_MESSAGE,
					               new ActionMessage("errors.message", errorMsg));
      	                                  return "mainfilter";
                      }
                      return "viewstudymaterial";
    }
}
