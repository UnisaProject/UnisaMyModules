package impl;

import java.util.ArrayList;
import utils.MetaDataValidator;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;

public class MetaData {

	public String setMetaDataToFormBean(UploadManagerForm uploadManagerForm)
			throws Exception {

		MetaDataValidator metaDataValidator = new MetaDataValidator();

		String validationErrorMsg = metaDataValidator
				.validateSelectedVariables(uploadManagerForm);
		
		if (!validationErrorMsg.equals("")) {
			return validationErrorMsg;
		}
		
		String modCode = uploadManagerForm.getCourseCode().trim();
        String year = uploadManagerForm.getAcadYear().trim();
		String period = uploadManagerForm.getPeriod().trim();
		String lang = uploadManagerForm.getLanguage().trim();
		String type = uploadManagerForm.getType().trim();
		
		
		
			
			
			ArrayList materialList = getStudyMaterialList(modCode, year, period,lang, type);
			if(type!="MO")
			{
				metaDataValidator.validateMaterialListFromScm(materialList);
			}
			
		

		if (!validationErrorMsg.equals("")) {
			return validationErrorMsg;
		}
		uploadManagerForm.setMaterialList(materialList);

		return "";

	}

	public ArrayList getStudyMaterialList(String modCode, String year,
			String period, String lang, String type) throws Exception {
		MetaDataGetter metaDataGetter = new MetaDataGetter();
		return metaDataGetter.getStudyMaterialList(modCode, year, period, lang,
				type);
	}

}
