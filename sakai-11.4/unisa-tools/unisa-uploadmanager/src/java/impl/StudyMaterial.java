package impl;

import java.util.ArrayList;
import utils.MetaDataUtils;
import utils.Utilities;
import module.*;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;

public class StudyMaterial extends StudyMaterialModule {

	MetaData metaData;
	Pdf pdf;

	public StudyMaterial() {

		metaData = new MetaData();
		pdf = new Pdf();
	}

	public StudyMaterial(String studyMaterialBarcode,
		String studyMaterialShortDesc) {
		
		metaData = new MetaData();
		setItemshortdesc(studyMaterialShortDesc);
		setItembarcode(studyMaterialBarcode);
		setFilename();
		setLanguage();
		setType();
		setModule();
	}

	public void setFilename() {
		String filename = MetaDataUtils.getfileName(getItemshortdesc());
		setFilename(filename);
	}

	public void setLanguage() {
		int lastIndexOff_ = getItemshortdesc().lastIndexOf("_") + 1;
		String lang = getItemshortdesc().substring(lastIndexOff_).trim();
		setLanguage(lang);
	}

	public ArrayList getStudyMaterialList(String modCode, String year,
			String period, String lang, String type) throws Exception {

		return metaData.getStudyMaterialList(modCode, year, period, lang, type);
	}

	public void setModule() {
		setModule(MetaDataUtils.getModule(getItemshortdesc()));
	}

	public void setType() {
		
		String StudyMaterialType = MetaDataUtils.getType(getItemshortdesc());
		setType(Utilities.getStudyMaterialTypeDirectoryName(StudyMaterialType));
		System.out.println("study material "+StudyMaterialType);
	}

	public String setMetaDataToFormBean(UploadManagerForm uploadManagerForm)
			throws Exception {
		return metaData.setMetaDataToFormBean(uploadManagerForm);
	}

}
