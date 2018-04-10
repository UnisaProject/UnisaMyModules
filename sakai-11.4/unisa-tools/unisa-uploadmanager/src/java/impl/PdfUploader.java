package impl;

import java.io.File;
import org.sakaiproject.component.cover.ServerConfigurationService;
import utils.FileUploadUtils;
import utils.UploadManagerFileUtils;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;

public class PdfUploader {
	
	public static final String pathToMaterialOnServer = ServerConfigurationService.getString("materialPath");
	
	private String checkInputFile(String inputFilePath, String inputFilename) {
		String uploadErrorMsg = UploadManagerFileUtils.checkInputFile(inputFilePath,
				inputFilename);
		return uploadErrorMsg;
	}

	public String uploadFile(UploadManagerForm uploadManagerForm)
			throws Exception {
		StudyMaterial studyMaterial = new StudyMaterial(
				uploadManagerForm.getBarcode(), uploadManagerForm.getItemDesc());
		studyMaterial.setModule(uploadManagerForm.getMod_code());
		String uploadErrorMsg = checkInputFile(
				uploadManagerForm.getInputFilePath(),
				uploadManagerForm.getFilename());
		if (!uploadErrorMsg.equals("")) {
			return uploadErrorMsg;
		}
		AuditTrail auditTrail = new AuditTrail();
		copyFileForUploadAction(uploadManagerForm.getInputFilePath(),
				studyMaterial, auditTrail);
		
		if (!uploadErrorMsg.equals("")) {
			return uploadErrorMsg;
		}
		return writeAuditLog(studyMaterial, uploadManagerForm, auditTrail,
				studyMaterial.getFilepath());
	}

	public String reloadFile(UploadManagerForm uploadManagerForm)
			throws Exception {
		StudyMaterial studyMaterial = new StudyMaterial(
				uploadManagerForm.getBarcode(), uploadManagerForm.getItemDesc());
		
		studyMaterial.setModule(uploadManagerForm.getMod_code());
		
		String uploadErrorMsg = checkInputFile(
				uploadManagerForm.getInputFilePath(),
				uploadManagerForm.getFilename());
		
		if (!uploadErrorMsg.equals("")) {
			return uploadErrorMsg;
		}
		
		AuditTrail auditTrail = new AuditTrail();
		copyFileForReloadloadAction(uploadManagerForm.getInputFilePath(),
				uploadManagerForm.getFilename(), studyMaterial);
		if (!uploadErrorMsg.equals("")) {
			return uploadErrorMsg;
		}
		return writeAuditLog(studyMaterial, uploadManagerForm, auditTrail,
				studyMaterial.getFilepath());
	}

	private String writeAuditLog(StudyMaterial studymaterial,
			UploadManagerForm uploadManagerForm, AuditTrail auditTrail,
			String uploadpath) throws Exception {
		String uploadErrorMsg = "There was a problem writing audit record for the uploaded file";
		int size = UploadManagerFileUtils.getInputFileSize(uploadManagerForm
				.getInputFilePath());
		auditTrail.writeLogs(studymaterial.getItembarcode(), size, uploadpath,
				uploadManagerForm.getUserId(), studymaterial.getFilename());
		uploadErrorMsg = "";
		return uploadErrorMsg;

	}

	private String copyFileForReloadloadAction(String inputFilePath,
			String inputFilename, StudyMaterial studyMaterial) throws Exception {
		String uploadErrorMsg = "";
		Pdf pdf = new Pdf();
		String uploadpath = null;
/*		String uploadpath = pdf.getReloadDirPath(
				studyMaterial.getItembarcode(), studyMaterial.getModule());*/
		boolean collectionExistFlag = isCollectionStudyMaterial(studyMaterial.getType(),
				studyMaterial.getModule(),studyMaterial.getFilename());
		if (collectionExistFlag) {
			uploadpath = pdf.getUploadDirPathForCollection(studyMaterial.getType(),
					studyMaterial.getModule());
		}else {
			uploadpath = pdf.getUploadDirPathForSingle(studyMaterial.getType(),
					studyMaterial.getModule());
		}
		studyMaterial.setFilepath(uploadpath);
		
/*		boolean oldFileDeleted = UploadManagerFileUtils.deleteFile(uploadpath
				+ inputFilename);*//*
		if (oldFileDeleted) {*/
			uploadErrorMsg = pdf.copyFileToSingle(inputFilePath, uploadpath
					,studyMaterial.getFilename());
	/*	}*/
		return uploadErrorMsg;
	}
	
	public static boolean isCollectionStudyMaterial(String type,String module,String inputFilename) {
		Pdf pdf = new Pdf();
		String collection = pdf.getUploadDirPathForCollection(type,
				module);
		
		File file = new File(collection+inputFilename);
		
		return file.exists()?Boolean.TRUE:Boolean.FALSE;
	
		
	}
	
	

	private String copyFileForUploadAction(String inputFilePath,
			StudyMaterial studyMaterial, AuditTrail auditTrail)
			throws Exception {
		Pdf pdf = new Pdf();
		String uploadpath = "", errorMsg = "";
		String itemBarcode = studyMaterial.getItembarcode();
		//String pathInLog = auditTrail.getPath(studyMaterial.getItembarcode());
        /*		File fileName = new File(newfilename);
		        File origFileUploaded */
		/*		
		if (auditTrail.studyMaterialExistInLogs(itemBarcode)
				&& canBeUploadedAsCollect(pathInLog, studyMaterial.getModule())) {
			pdf.copyFileToCollection(inputFilePath, studyMaterial, auditTrail);

		} else {*/

		/*}*/
		
		uploadpath = pdf.getUploadDirPathForSingle(studyMaterial.getType(),
				studyMaterial.getModule());
		errorMsg = pdf.copyFileToSingle(inputFilePath, uploadpath
				,studyMaterial.getFilename());
		studyMaterial.setFilepath(uploadpath);

		return errorMsg;
	}

	private boolean canBeUploadedAsCollect(String path, String uploadingModule)
			throws Exception {

		boolean uploadInColl = true;
		String moduleInLog = FileUploadUtils.getModuleCodeFromFilePath(path);
		if (uploadingModule.equals(moduleInLog)
				&& (path.indexOf("collect") == -1)) {
			uploadInColl = false;
		}
		return uploadInColl;
	}

}
