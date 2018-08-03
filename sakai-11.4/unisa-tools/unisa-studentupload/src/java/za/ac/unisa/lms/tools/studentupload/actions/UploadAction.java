package za.ac.unisa.lms.tools.studentupload.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.CodeSource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.tools.studentupload.bo.SavedDoc;
import za.ac.unisa.lms.tools.studentupload.dao.StudentUploadDAO;
import za.ac.unisa.lms.tools.studentupload.dao.SavedDocDao;
import za.ac.unisa.lms.tools.studentupload.forms.FileBean;
import za.ac.unisa.lms.tools.studentupload.forms.StudentFile;
import za.ac.unisa.lms.tools.studentupload.forms.StudentUploadForm;
import Staae05h.Abean.Staae05sAppAdmissionEvaluator;

public class UploadAction extends DispatchAction {

	/** Remember that Application files go to /data/sakai/applications/tmp on the Sakai servers, not /var/spool as used in registrations **/

    /** Our log (commons). */
	public static Log log = LogFactory.getLog(UploadAction.class);
	
	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;

		operListener() {
			exception = null;
		}

		public Exception getException() {
			return exception;
		}

		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
		}
	}
	
	public ActionForward uploadInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		StudentUploadForm stuRegForm = (StudentUploadForm) form;
		SavedDocDao dao = new SavedDocDao();
		ActionMessages messages = new ActionMessages();
		
		//log.debug("UploadAction - uploadInput - Start");
		try{
		// if student number is null, block application
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			log.info("UploadAction - uploadInput " + request.getSession().getId() + " Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("applyLogin");
		}
		
		CodeSource codeSource = FileUpload.class.getProtectionDomain().getCodeSource();
		//log.debug("UploadAction uploadInput " + request.getSession().getId() + " " + stuRegForm.getStudent().getNumber() + " " + ((codeSource != null)? codeSource.getLocation():"no code source for upload" ));
		
		boolean isCGMatrix = false;
		isCGMatrix = dao.getMatricCert(stuRegForm.getStudent().getNumber());
		if (isCGMatrix){
			stuRegForm.getStudent().setMatrix("CG");
		}
		
		stuRegForm.loadData();
		}catch (Exception ex){
			//log.debug("UploadAction - uploadInput An error occurred. Please try again.");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "An error occurred. Please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("applyLogin");
		}
		return mapping.findForward("input");
	}
	
	public ActionForward upload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//log.debug("UploadAction - Upload - Start");
		
		ActionMessages messages = new ActionMessages();
		StudentUploadForm stuRegForm = (StudentUploadForm) form;
		SavedDocDao dao = new SavedDocDao();
		boolean isSelectedRequiredFile = false;
		boolean isSelectedOptionalFile = false;
		//boolean isSelectedOptionalType = false;
		
		 //Reset web messages
		 stuRegForm.setWebUploadMsg("");
		 stuRegForm.setWebLoginMsg("");
		
		/** Edmund Enumerate through all parameters received
	  	 * @return */
	   // getAllRequestParamaters(request, response);
		
		//response.setHeader("cache-control", "no-cache");
		
		// if student number is null, block application
		if(stuRegForm.getStudent().getNumber() == null || "".equals(stuRegForm.getStudent().getNumber())){
			//log.debug("UploadAction - Upload - " + request.getSession().getId() + " Student Nr is null or empty when saving qualifications");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			new ActionMessage("message.generalmessage", "You performed and invalid action or an Error occurred. Please log on again to retry."));
			addErrors(request, messages);
			return mapping.findForward("applyLogin");
		}
		
		if(request.getParameter("Logout") != null) {
			//log.debug("UploadAction - Upload - Logout and return later");
			resetForm((StudentUploadForm) stuRegForm, "Upload1");
			stuRegForm.setSelectReset("");
			//return mapping.findForward("loginSelect");
			return mapping.findForward("applyLogin");
		}		 

		try{

			//log.debug("UploadAction - Upload - Entering upload");
			
			String method = request.getMethod();
			//log.debug("UploadAction - Upload - Method GET Check " + method);
					
			if ("GET".equalsIgnoreCase(method)) {
				//log.debug("UploadAction - Upload - Validate - Method GET Check - Returning Null");
				return null;
			}
			
			if (stuRegForm.getFileBeans() != null){
				for (FileBean fb : stuRegForm.getFileBeans()) {
					//log.debug("UploadAction - Upload - Validate - Required File ========================"+fb.getFile());
					if (fb.getFile() != null && fb.getFile().getFileName().trim().length() > 0) {
						//log.debug("UploadAction - Upload - Validate - Required File - True................");
						isSelectedRequiredFile = true;
						break;
					}else{
						//File is empty
						//log.debug("UploadAction - Upload - Validate - No file or file lenght 0, might be continue, not upload");
					}
				}
		
				if (!isSelectedRequiredFile && stuRegForm.isHiddenButton()) {
					//log.debug("UploadAction - Upload - Validate - Required File ============"+  stuRegForm.getOptionFileBean().getFile());
					//log.debug("UploadAction - Upload - isSelectedRequiredFile - isSelectedRequiredFile="+isSelectedRequiredFile);
					if (stuRegForm.getOptionFileBean().getFile() != null && stuRegForm.getOptionFileBean().getFile().getFileName().trim().length() > 0) {
						isSelectedRequiredFile = true;
						//log.debug("UploadAction - Upload - Validate - Required File - True................");
					}else{
						//log.debug("UploadAction - Upload - Validate - Required File - False................");
					}
				}
			}else{
				//log.debug("UploadAction - Upload - Required File - Empty................");
				//log.debug("UploadAction - Upload - Required File - isSelectedRequiredFile="+isSelectedRequiredFile);
			}
			
			// check the selected files one by one
			for (FileBean fb : stuRegForm.getFileBeans()) {
				FormFile file = fb.getFile();

				if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
					int size = file.getFileSize();
					
					String readFileSize = readableFileSize(size);
					//log.debug("UploadAction - Upload - Validate - Upload Required: " + file.getFileName() + " size = " + readFileSize);
					if (readFileSize.contains("Error"))	{
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", readFileSize));
						addErrors(request, messages);
						return mapping.findForward("input");
					}else {
						if (size > 2097152) {// check file size,  not greater than 2 MB (2097152) measured in BYTES!
							//log.debug("UploadAction - Upload - Validate - Upload Required: " + file.getFileName() + " size = " + readFileSize + " File size too large");
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "File cannot be larger than 2MB (2048K). Your file is "+readFileSize));
							addErrors(request, messages);
							//log.debug("UploadAction - Upload - UploadFile - File larger than 2MB");
							return mapping.findForward("input");
						}else {
							//log.debug("UploadAction - Upload - UploadFile - Required File size OK...............");
						}
					}
					String name = file.getFileName().toLowerCase();
					//log.debug("UploadAction - Upload - UploadFile - File Name: " + name + " ...............");
					if (!name.endsWith(".doc") && !name.endsWith(".docx") && !name.endsWith(".pdf") && !name.endsWith(".tif") && !name.endsWith(".tiff")) {
						//log.debug("Set Error Message: Required File wrong type...............");
						//log.debug("UploadAction - Upload - Validate - Upload Required: " + file.getFileName() + " Required File wrong type ");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Only doc, docx, pdf, tif and tiff files are allowed"));
						addErrors(request, messages);
						//log.debug("UploadAction - Upload - UploadFile - Required File wrong type");
						return mapping.findForward("input");
					}
					//log.debug("UploadAction - Upload - Required File Name: " + name + " ..............."); 
					if (fb.getUploaded().size() == 5) {


						//log.debug("Set Error Message: Required File more than 5...............");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "You have reached the maximum of 5 files"));
						addErrors(request, messages);
						//log.debug("UploadAction - Upload - UploadFile - Required File more than 5");
						return mapping.findForward("input");
					}
				}
			}
			
			// validate optional
			//log.debug("UploadAction - Upload - UploadFile - Optional File ===============================================================");

			if (stuRegForm.getOptionFileBean().getFile() != null){
				//log.debug("UploadAction - Upload - UploadFile - Optional File - Validate Optional File");
				FormFile file = stuRegForm.getOptionFileBean().getFile();
				if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
					isSelectedOptionalFile = true;
					//Enable this if you wish to validate for optional documents
					if( "-1".equals(stuRegForm.getOptionFileBean().getDoc().getDocCode())){
						//log.debug("UploadAction - Upload - UploadFile - Optional File - Set Error Message: Optional File Required...............");
						stuRegForm.setFileSelected(stuRegForm.getOptionFileBean().getDoc().getDocCode());
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please select a document type from list for the optional file"));
						addErrors(request, messages);
						//log.debug("UploadAction - Upload - UploadFile - Optional File - Required Document type missing");
						return mapping.findForward("input");
					}
					int size = file.getFileSize();
					//log.debug("UploadAction - Upload - UploadFile - Optional File - - File: " + file.getFileName() + " & size = " + size);
					
					String readFileSize = readableFileSize(size);
					//log.debug("UploadAction - Upload - UploadFile - Optional File - Validate - Upload Optional: " + file.getFileName() + " size = " + readFileSize);
					if (readFileSize.contains("Error"))	{
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", readFileSize));
						addErrors(request, messages);
						return mapping.findForward("input");
					}else {
						if (size > 2097152) {// check file size,  not greater than 2 MB (2097152) measured in BYTES!
							//log.debug("UploadAction - Upload - UploadFile - Optional File - Validate - Upload Optional: " + file.getFileName() + " size = " + readFileSize + " File size too large");
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "File cannot be larger than 2MB (2048K). Your file is "+readFileSize));
							addErrors(request, messages);
							//log.debug("UploadAction - Upload - UploadFile - Optional File - Optional File larger than 2MB");
							return mapping.findForward("input");
						}else {
							//log.debug("UploadAction - Upload - UploadFile - Optional File - Set Error Message: Required File size OK...............");
						}
					}
					String name = file.getFileName().toLowerCase();
					if (!name.endsWith(".doc") && !name.endsWith(".docx") && !name.endsWith(".pdf") && !name.endsWith(".tif") && !name.endsWith(".tiff")) {
						//log.debug("UploadAction - Upload - UploadFile - Optional File - Validate - Upload Optional: " + file.getFileName() + " File wrong type ");
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Only doc, docx, pdf, tif and tiff files are allowed"));
						addErrors(request, messages);
						//log.debug("UploadAction - Upload - UploadFile - Optional File - Optional File File wrong type");
						return mapping.findForward("input");
					}
		
				}
			}else{
				//log.debug("UploadAction - Upload - UploadFile - Optional File - Empty................");
			}
			//log.debug("UploadAction - Upload - UploadFile - Optional File - ================================================================");
			//log.debug("UploadAction - Upload - UploadFile - Optional File - Checking File Names and Type");
			
			//log.debug("UploadAction - Upload - UploadFile - Optional File - Checking Type - ================================================================");
			if( !"-1".equals(stuRegForm.getOptionFileBean().getDoc().getDocCode())){ 
				stuRegForm.setFileSelected(stuRegForm.getOptionFileBean().getDoc().getDocCode());
				//log.debug("UploadAction - Upload - UploadFile - Optional File - Checking Type = FileSelected - getDocCode Form="+stuRegForm.getOptionFileBean().getDoc().getDocCode());
				//log.debug("UploadAction - Upload - UploadFile - Optional File - Checking Type = FileSelected - getDocCode getParameter="+request.getParameter("optionFileBean.doc.docCode"));
			}else{
				//log.debug("UploadAction - Upload - UploadFile - Optional File - Checking Type = FileSelected - None");
				stuRegForm.setFileSelected("");
			}
			//log.debug("UploadAction - Upload - UploadFile - Optional File - Checking Type - ================================================================");
			
			
			if(request.getParameter("Continue") == null){
				if ("Upload".equalsIgnoreCase(request.getParameter("UploadReq"))){
				if (stuRegForm.getRequiredDocs().size() > 0){
					if (!isSelectedRequiredFile && !isSelectedOptionalFile && (stuRegForm.getOptionFileBean().getDoc().getDocCode() == null || "-1".equals(stuRegForm.getOptionFileBean().getDoc().getDocCode()))){
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please select a required document or optional document type and file from the optional file list"));
						addErrors(request, messages);
							//log.debug("UploadAction - Upload - UploadFile - No Files or type selected for upload - isSelectedRequiredFile="+isSelectedRequiredFile+", isSelectedOptionalFile="+isSelectedOptionalFile);
							return mapping.findForward("input");
						}else if (!isSelectedRequiredFile && !isSelectedOptionalFile){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Please select a required or optional document to upload"));
							addErrors(request, messages);
							//log.debug("UploadAction - Upload - UploadFile - No Files selected for upload - isSelectedRequiredFile="+isSelectedRequiredFile+", isSelectedOptionalFile="+isSelectedOptionalFile);
							return mapping.findForward("input");
						}
					}else{
						if (!isSelectedOptionalFile && (stuRegForm.getOptionFileBean().getDoc().getDocCode() == null || "-1".equals(stuRegForm.getOptionFileBean().getDoc().getDocCode()))){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Please select a document type and file to upload"));
							addErrors(request, messages);
							//log.debug("UploadAction - Upload - UploadFile - No Optional Files or type selected for upload - isSelectedOptionalFile="+isSelectedOptionalFile+", Type="+stuRegForm.getOptionFileBean().getDoc().getDocCode());
							return mapping.findForward("input");
						}else if (isSelectedOptionalFile && "-1".equals(stuRegForm.getOptionFileBean().getDoc().getDocCode())){
							//log.debug("Set Error Message: Optional File Type Required...............");
							stuRegForm.setFileSelected(stuRegForm.getOptionFileBean().getDoc().getDocCode());
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Please select a document type from list for the optional files and re-select your file"));
							addErrors(request, messages);
							//log.debug("UploadAction - Upload - UploadFile - Optional File Type missing");
						return mapping.findForward("input");
					}
					if (!isSelectedOptionalFile && !"-1".equals(stuRegForm.getOptionFileBean().getDoc().getDocCode())){
							//log.debug("Set Error Message: Optional File Required...............");
							stuRegForm.setFileSelected(stuRegForm.getOptionFileBean().getDoc().getDocCode());
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Please select an optional document to upload"));
							addErrors(request, messages);
							//log.debug("UploadAction - Upload - UploadFile - Optional Files Empty");
							return mapping.findForward("input");
						}
					}
				}
			}
			//log.debug("UploadAction - Upload - ================================================================");
			//log.debug("UploadAction - Upload - ================================================================");
			//log.debug("UploadAction - Upload - Continuing Upload");
			//log.debug("UploadAction - Upload - ================================================================");

			//log.debug("UploadAction - Upload - Getting to Formfile (1) Required Files - No Files="+stuRegForm.getFileBeans().length);
			//Changed to upload only one file at a time - Although it should be max 5, we check for 10
			List<Integer> doFileNo = new ArrayList<>();
			boolean isRequiredSelected = false;
			for (int fileNo = 0; fileNo < stuRegForm.getFileBeans().length; fileNo++){
				if (request.getParameter("UploadReq["+fileNo+"]") != null && "Upload".equalsIgnoreCase(request.getParameter("UploadReq["+fileNo+"]"))){
					doFileNo.add(fileNo);
					isRequiredSelected = true;
				}
			}
			//debug
			if (isRequiredSelected && doFileNo.size() > 0){
				int doReqFileNo = 0;
				for (int f=0; f < doFileNo.size(); f++){
					//log.debug("UploadAction - Upload - Required File Index Selected="+f);
					for (FileBean fb : stuRegForm.getFileBeans()) {
						//log.debug("UploadAction - Upload - Required File - Uploading into for loop............");
						if (doReqFileNo == doFileNo.get(f)){
							FormFile file = fb.getFile();
							//log.debug("UploadAction - Upload - Required File - Uploading file("+doReqFileNo+")=" +file+"............");
					
							if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
								//log.debug("UploadAction - Upload - Required File - Uploading filename not null in if - Number of Files="+file.getFileName().trim().length()+", Uploading File="+file.getFileName()+"............");
									
								int size = file.getFileSize();
								//log.debug("UploadAction - Upload - Required File - Uploading file size: " +size+"............");
										
									//save file
									//String dir = servlet.getServletContext().getRealPath("/upload");
									String appDir = ServerConfigurationService.getString("application.path");
									//log.debug("UploadAction - Upload - Required File - Uploading (RequiredFileBean) - application.path:" + appDir + " ............");
										
									String stuGroupDir = stuRegForm.getStudent().getNumber().toString().substring(0, 1);
								   	String stuDir = stuRegForm.getStudent().getNumber().toString();
								   	
								   	String filePath = appDir + "/tmp/" + stuGroupDir + "/" + stuDir;
							    	//log.debug("UploadAction - Upload - Required File - Uploading (RequiredFileBean) -  upload dest path:" + filePath + " ............");
				
										
									File folder = new File(filePath);
									if(!folder.exists()){
										folder.mkdirs();
									}
									String writeFile = "";
									writeFile = file.getFileName();
									//log.debug("UploadAction - Upload - Required File - Uploading - writeFile1:" + writeFile + "............");
										
									StudentFile stuFile = new StudentFile();
									stuFile.setFileName(writeFile);
									String fileDesc = dao.getDocType(stripXSS(fb.getDoc().getDocCode()));
									//log.debug("UploadAction - Upload - Required File - Uploading - fileDesc Before Rename:" + stuFile.getFileName() + "............");
										
									fileDesc = fileDesc.replace("/","-");
									fileDesc = fileDesc.replace(" ","_");
									
									String fileExtension = writeFile.substring(writeFile.lastIndexOf(".")+1);
									//log.debug("UploadAction - Upload - Required File - Uploading - fileExtension:" + fileExtension + "............");
									
									String time = (new java.text.SimpleDateFormat("hhmmssss").format(new java.util.Date()).toString());
									//log.debug("UploadAction - Upload - Required File - Uploading - time:" + time + "............");
									
									String newFileName = stuRegForm.getStudent().getNumber()+"_"+ fileDesc + "_"+ time +"." + fileExtension;
									stuFile.setNewFileName(newFileName);
									// set filename to changed name for workflow purposes
									writeFile = newFileName;
									//log.debug("UploadAction - Upload - Required File - Uploading - writeFile:" + writeFile + "............");
									
									//log.debug("UploadAction - Upload - Required File - Uploading - fileDesc After Rename:" + writeFile + "............");
									String fullFileName = filePath+System.getProperty("file.separator")+writeFile;
									String success = "None";
									success = uploadFile(fullFileName, file.getInputStream());
				
									File testFile = new File(fullFileName);
									//log.debug("UploadAction - Upload - Required File - Upload Status="+success);
									
									if(testFile.exists() && success.equalsIgnoreCase("Success")){
										//log.debug("UploadAction - Upload - Required File - Required File uploaded and exists on server");
										SavedDoc requiredDoc = new SavedDoc();
										requiredDoc.setDocCode(fb.getDoc().getDocCode());
										requiredDoc.setDocName(file.getFileName());
										//log.debug("UploadAction - Upload - Required File - Calling Required File addSavedDoc 1 - Doc:" + requiredDoc + "............");
										dao.addSavedDocSTUAPD(requiredDoc,"Y",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),"1");
										dao.addSavedDocSTUXML(requiredDoc,"Y",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(),"1");
									}else {
										//Uploaded file not found on Filesystem
										messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage", "File upload failed (RNF), was interrupted or could not be completed. Please try again."));
										addErrors(request, messages);
										//log.debug("UploadAction - Upload - Required File - UploadAction - UploadFile - Uploaded file not found on filesystem "+ success);
										return mapping.findForward("input");
									}
								}else{
									//log.debug("UploadAction - Upload - Required File - Uploading required filename is null............");
									if(request.getParameter("Continue") == null){ //Only log null if doing upload, not continue
										//log.debug("UploadAction - Upload - Required File - UploadAction required file - Uploading required filename is null" );
										messages.add(ActionMessages.GLOBAL_MESSAGE,
											new ActionMessage("message.generalmessage", "File type to upload was selected, but no Required file selected. Please try again."));
										addErrors(request, messages);
										return mapping.findForward("input");
				
									}
								}
							}
						doReqFileNo++;
						//log.debug("UploadAction - Upload - Required File - Uploading required filename Done. Testing for next file="+doReqFileNo);
					}
				}
			}

			//log.debug("UploadAction - Upload - Required File - Uploading All Required Files Done............");

			
			//log.debug("UploadAction - Upload - =======================================================================================");
			
			//log.debug("UploadAction - Upload - Getting to Formfile (2) Optional Files............");

			if ("Upload".equalsIgnoreCase(request.getParameter("UploadOpt"))){
				//log.debug("UploadAction - Upload - Optional File Button Clicked");
				FormFile file = stuRegForm.getOptionFileBean().getFile();
				if (file != null && file.getFileName() != null && file.getFileName().trim().length() > 0) {
					int size = file.getFileSize();
					//log.debug("UploadAction - Upload - Optional File - Uploading Optional File size: " +size+"............");
						
					//save file
					//String dir = servlet.getServletContext().getRealPath("/upload");
					String appDir = ServerConfigurationService.getString("application.path");
					//log.debug("UploadAction - Upload - Optional File - Uploading (OptionFileBean) - application.path:" + appDir + " ............");
						
					String stuGroupDir = stuRegForm.getStudent().getNumber().toString().substring(0, 1);
					String stuDir = stuRegForm.getStudent().getNumber().toString();
				    	
					String filePath = appDir + "/tmp/" + stuGroupDir + "/" + stuDir;
		
					//log.debug("UploadAction - Upload - Optional File - Uploading (OptionFileBean) -  upload dest path:" + filePath + " ............");
						
					File folder = new File(filePath);
					if(!folder.exists()){
						folder.mkdirs();
					}
					
					String writeFile = "";
					writeFile = file.getFileName();
					//log.debug("UploadAction - Upload - Optional File - Uploading - writeFile2:" + writeFile + "............");
						
					StudentFile stuFile = new StudentFile();
					stuFile.setFileName(writeFile);
					String fileDesc = dao.getDocType(stripXSS(stuRegForm.getOptionFileBean().getDoc().getDocCode()));
					//log.debug("UploadAction - Upload - Optional File - Uploading - fileDesc Before Rename:" + stuFile.getFileName() + "............");
						
					fileDesc = fileDesc.replace("/","-");
					fileDesc = fileDesc.replace(" ","_");
						
					String fileExtension = writeFile.substring(writeFile.lastIndexOf(".")+1);
					//log.debug("UploadAction - Upload - Optional File - Uploading - fileExtension:" + fileExtension + "............");
						
					String time = (new java.text.SimpleDateFormat("hhmmssss").format(new java.util.Date()).toString());
					//log.debug("UploadAction - Upload - Optional File - Uploading - time:" + time + "............");
						
					String newFileName = stuRegForm.getStudent().getNumber()+"_"+ fileDesc + "_"+ time +"." + fileExtension;
					stuFile.setNewFileName(newFileName);
					
					// set filename to changed name for workflow purposes
					writeFile = newFileName;
					//log.debug("UploadAction - Upload - Optional File - Uploading - writeFile:" + writeFile + "............");
						
					//log.debug("UploadAction - Upload - Optional File - Uploading - fileDesc After Rename:" + writeFile + "............");
					
					String fullFileName = filePath+System.getProperty("file.separator")+writeFile;
					String success = "None";
					success = uploadFile(fullFileName, file.getInputStream());
					//uploadFile(dir+System.getProperty("file.separator")+file.getFileName(), file.getInputStream());
					
					File testFile = new File(fullFileName);
					if(testFile.exists() && success.equalsIgnoreCase("Success")){
						//log.debug("UploadAction - Upload - Optional File - Optional File exists - Saving to Database");
						SavedDoc optionDoc = new SavedDoc();
						optionDoc.setDocCode(stuRegForm.getOptionFileBean().getDoc().getDocCode());
						optionDoc.setDocName(file.getFileName());
						//log.debug("UploadAction - Upload - Optional File - Calling addSavedDoc 2 - optionDoc:" + optionDoc + "............");
						dao.addSavedDocSTUAPD(optionDoc,"N",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),"2");
						dao.addSavedDocSTUXML(optionDoc,"N",stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(),"2");
					}else {
						//Uploaded file not found on Filesystem
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "File upload failed (ONF), was interrupted or could not be completed. Please try again."));
						addErrors(request, messages);
						//log.debug("UploadAction - Upload - Optional File - UploadAction - UploadFile - Uploaded file not found on filesystem "+ success);
						return mapping.findForward("input");
					}
				}else{
					//log.debug("UploadAction - Upload - Optional File - UploadAction optional file - Uploading optional filename is null" );
					if(request.getParameter("Continue") == null && !isSelectedRequiredFile && !isSelectedOptionalFile){ //Only log null if doing upload, not continue
						//log.debug("UploadAction - Upload - Optional File - UploadAction optional file - Uploading optional filename is null" );
						//No File Selected for Upload
						if( "-1".equals(stuRegForm.getOptionFileBean().getDoc().getDocCode())){ 
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "No File type or file was selected for upload. Please try again or Continue if you don't wish to upload any documents"));
							addErrors(request, messages);
							//log.debug("UploadAction - Upload - Optional File - No File was selected for upload. Please try again or Continue if you don't wish to upload any documents");
							return mapping.findForward("input");
						}else{
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "No File was selected for upload. Please try again or Continue if you don't wish to upload any documents"));
							addErrors(request, messages);
							//log.debug("UploadAction - Upload - Optional File - No File was selected for upload. Please try again or Continue if you don't wish to upload any documents");
							return mapping.findForward("input");
						}
					}
				}
				//log.debug("UploadAction - Upload - Optional File - Done");
				
				//log.debug("UploadAction - Upload - Validate - OK - ================================================================");
			}		
			
			//Get Matric Certificate for second login
			if (stuRegForm.getStudent().getMatrix() == null || "".equals(stuRegForm.getStudent().getMatrix().trim())){
				boolean isCGMatrix = false;
				isCGMatrix = dao.getMatricCert(stuRegForm.getStudent().getNumber());
				if (isCGMatrix){
					stuRegForm.getStudent().setMatrix("CG");
				}
			}
			//log.debug("UploadAction - Upload - Optional File - UploadAction - Upload before Form Reload");
			stuRegForm.reLoad();
	
			//log.debug("UploadAction - Upload - Optional File - UploadAction - Upload - Checking Continue="+request.getParameter("Continue"));

			//log.debug("UploadAction - Upload - ================================================================");

			
			if(request.getParameter("Continue") != null) {
				//log.debug("UploadAction - Upload - ================================================================");
				//log.debug("UploadAction - Upload - Save and Continue");
				//log.debug("UploadAction - Upload - ================================================================");

				 
				//log.debug("UploadAction - Upload - Checking Email and CellNr=");
				StudentUploadDAO applyDAO = new StudentUploadDAO();
				 
				if (stuRegForm.getStudent().getEmailAddress() == null || stuRegForm.getStudent().getCellNr() == null){
					stuRegForm.getStudent().setEmailAddress(applyDAO.personalDetails(stuRegForm.getStudent().getNumber(),"email_address").trim());
					stuRegForm.getStudent().setCellNr(applyDAO.personalDetails(stuRegForm.getStudent().getNumber(),"cell_number").trim());
				}
				 
				//Update STUAPQ status for Student to NP if ND
				//log.debug("Setting STUAPQ NP for Student");
				//log.debug("UploadAction - Checking if MD, if so do not write STUAPQ record for Student");
				if(!"MD".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
					//log.debug("UploadAction - Not MD, so write STUAPQ record for Student");
					dao.setSTUAPQ(stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear());
				}
			 
				//log.debug("UploadAction - Upload - STUAPQ Done");
				
				//log.debug("UploadAction - Upload - Do Staae05sAppAdmissionEvaluator Letter");
				/**2018 Edmund Start of Send Letter**/
				/**2018 July - Johanet enable code that was commented out to email application received letter - BRD SR198094 5.1**/
				/* studentUpload tool*/
				try{
					Staae05sAppAdmissionEvaluator op = new Staae05sAppAdmissionEvaluator();
					operListener opl = new operListener();
					op.addExceptionListener(opl);
					op.clear();
	
					op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
					op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
					op.setInCsfClientServerCommunicationsAction("PR");
					op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
					op.setInWsUserNumber(99998);
					//log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Academic Year=" + stuRegForm.getStudent().getAcademicYear());
					op.setInWsAcademicYearYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
					op.setInWebStuApplicationQualAcademicYear((short) Integer.parseInt(stuRegForm.getStudent().getAcademicYear()));
					//log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Academic Period=" + stuRegForm.getStudent().getAcademicPeriod());
					op.setInWebStuApplicationQualApplicationPeriod((short) Integer.parseInt(stuRegForm.getStudent().getAcademicPeriod()));
					//log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Student Number=" + stuRegForm.getStudent().getNumber());
					op.setInWebStuApplicationQualMkStudentNr(Integer.parseInt(stuRegForm.getStudent().getNumber()));					
					////log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - RetQualOneFinal=" + stuRegForm.getStudent().getRetQualOneFinal());
					//op.setInWebStuApplicationQualNewQual(stuRegForm.getStudent().getRetQualOneFinal());
					////log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Choice Nr= 1");
					//op.setInWebStuApplicationQualChoiceNr((short) 1);
					//Get Current Status
					////log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Get Basic Status");
					//String status = applyDAO.getBasicStatus(stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear(),stuRegForm.getStudent().getAcademicPeriod(), "1");
					////log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Basic Status="+status);
					//op.setInWebStuApplicationQualStatusCode(status);
					
					//log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) - Execute");
		
					op.execute();
		
					if (opl.getException() != null) throw opl.getException();
					if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		
					//log.debug("UploadAction - Upload - Staae05sAppAdmissionEvaluator - After Execute");
					String opResult = op.getOutCsfStringsString500();
					//log.debug("UploadAction - Upload - (Staae05sAppAdmissionEvaluator) opResult: " + opResult);
				}catch(Exception e){
					log.debug("Unisa-StudentUpload - UploadAction - Upload - Staae05sAppAdmissionEvaluator - After Execute / sessionID=" + request.getSession().getId() + " / Error=" + e );
					log.warn("Unisa-StudentUpload - UploadAction - Upload - Staae05sAppAdmissionEvaluator - After Execute / sessionID=" + request.getSession().getId() + " / Error=" + e );
				}
				/**End of Send Letter**/
				/*Johanet 2018July - end of enabling code*/
				
				//log.debug("UploadAction moveDocuments if any: " + stuRegForm.getStudent().getNumber());
				 
				String result = moveDocuments(stuRegForm);
				//log.debug("UploadAction - Upload "+result);
				if (result.contains("Error")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "File upload failed (MV), was interrupted or could not be completed. Please try again."));
					addErrors(request, messages);
					return mapping.findForward("input");
				}
				 		 
				/* Set submission time stamp */
				Date date = new java.util.Date();
				String displayDate = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy HH:mm").format(date).toString());
				//log.debug("UploadAction ActionForward AppTime: " + stuRegForm.getStudent().getAppTime());
				//log.debug("UploadAction stuRegForm stuExist: " + stuRegForm.getStudent().isStuExist());
				//log.debug("UploadAction request Parameter stuExist: " + request.getParameter("stuExist"));
				
				if (stuRegForm.getStudent().isStuExist()){
					//log.debug("UploadAction - stuExist Get saved qualifications for student from STUAPQ");
					//Previous Qualification
					String retQualPrevFinal =  stuRegForm.getStudent().getRetQualPrevFinal();
					//log.debug("UploadAction - stuExist Get saved qualifications for student from STUANN - retQualPrevFinal="+retQualPrevFinal);
					String retSpecPrevFinal = stuRegForm.getStudent().getRetSpecPrevFinal();
					//log.debug("UploadAction - stuExist Get saved qualifications for student from STUANN - retSpecPrevFinal="+retSpecPrevFinal);
					//New Qualifications
					//Get saved qualifications for student from STUAPQ (Getting them individually to simplify moving result between forms)
					String retQualOneFinal =  stuRegForm.getStudent().getRetQualOneFinal();
					//log.debug("UploadAction - stuExist Get saved qualifications for student from STUAPQ - retQualOneFinal="+retQualOneFinal);
					String retSpecOneFinal = stuRegForm.getStudent().getRetSpecOneFinal();
					//log.debug("UploadAction - stuExist Get saved qualifications for student from STUAPQ - retSpecOneFinal="+retSpecOneFinal);
					String retQualTwoFinal = stuRegForm.getStudent().getRetQualTwoFinal();
					//log.debug("UploadAction - stuExist Get saved qualifications for student from STUAPQ - retQualTwoFinal="+retQualTwoFinal);
					String retSpecTwoFinal = stuRegForm.getStudent().getRetSpecTwoFinal();
					//log.debug("UploadAction - stuExist Get saved qualifications for student from STUAPQ - retSpecTwoFinal="+retSpecTwoFinal);
					String emailAddress = stuRegForm.getStudent().getEmailAddress();
					boolean isAdmin = stuRegForm.getAdminStaff().isAdmin();
					//Reset All Form Variables
					resetForm((StudentUploadForm) stuRegForm, "Upload Return");
					//Set Necessary variables for Final page
					stuRegForm.getStudent().setAppTime(displayDate);
					stuRegForm.getStudent().setRetQualPrevFinal(retQualPrevFinal);
					stuRegForm.getStudent().setRetSpecPrevFinal(retSpecPrevFinal);
					stuRegForm.getStudent().setRetQualOneFinal(retQualOneFinal);
					stuRegForm.getStudent().setRetSpecOneFinal(retSpecOneFinal);
					stuRegForm.getStudent().setRetQualTwoFinal(retQualTwoFinal);
					stuRegForm.getStudent().setRetSpecTwoFinal(retSpecTwoFinal);
					stuRegForm.getStudent().setEmailAddress(emailAddress);
					stuRegForm.getAdminStaff().setAdmin(isAdmin);
					//log.debug("UploadAction - stuExist Redirect: Return");
					return mapping.findForward("submitRet");
				}else{
					String checkMD = dao.getFinalMD(stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear());
					
					//log.debug("UploadAction - checkMD : " + checkMD);
					//log.debug("UploadAction - LoginSelectMain : " + stuRegForm.getLoginSelectMain());
					
					if ("MD".equalsIgnoreCase(stuRegForm.getLoginSelectMain())){
						//Get saved qualifications for student from STUAPQ (Getting them individually to simplify moving result between forms)
						String studentNr = stuRegForm.getStudent().getNumber();
						String newMDQualFinal = stuRegForm.getStudent().getNewMDQualFinal();
						String newMDSpecFinal = stuRegForm.getStudent().getNewMDSpecFinal();
						String emailAddress = stuRegForm.getStudent().getEmailAddress();
						String cellNr = stuRegForm.getStudent().getCellNr();
						//Reset All Form Variables
						resetForm((StudentUploadForm) stuRegForm, "Upload MD");
						//Set Necessary variables for Final page
						stuRegForm.getStudent().setAppTime(displayDate);
						stuRegForm.getStudent().setNumber(studentNr);
						stuRegForm.getStudent().setNewMDQualFinal(newMDQualFinal);
						stuRegForm.getStudent().setNewMDSpecFinal(newMDSpecFinal);
						stuRegForm.getStudent().setEmailAddress(emailAddress);
						stuRegForm.getStudent().setCellNr(cellNr);
						//log.debug("stuExist Redirect: NewMD");
						return mapping.findForward("submitNewMD");
					}else {
						
						//Get saved qualifications for student from STUAPQ (Getting them individually to simplify moving result between forms)
						String studentNr = stuRegForm.getStudent().getNumber();
						String newQualOneFinal = stuRegForm.getStudent().getNewQualOneFinal();
						String newSpecOneFinal = stuRegForm.getStudent().getNewSpecOneFinal();
						String newQualTwoFinal = stuRegForm.getStudent().getNewQualTwoFinal();
						String newSpecTwoFinal = stuRegForm.getStudent().getNewSpecTwoFinal();
						String emailAddress = stuRegForm.getStudent().getEmailAddress();
						String cellNr = stuRegForm.getStudent().getCellNr();
						//Reset All Form Variables
						resetForm((StudentUploadForm) stuRegForm, "Upload New");
						//Set Necessary variables for Final page
						stuRegForm.getStudent().setAppTime(displayDate);
						stuRegForm.getStudent().setNumber(studentNr);
						stuRegForm.getStudent().setNewQualOneFinal(newQualOneFinal);
						stuRegForm.getStudent().setNewSpecOneFinal(newSpecOneFinal);
						stuRegForm.getStudent().setNewQualTwoFinal(newQualTwoFinal);
						stuRegForm.getStudent().setNewSpecTwoFinal(newSpecTwoFinal);
						stuRegForm.getStudent().setEmailAddress(emailAddress);
						stuRegForm.getStudent().setCellNr(cellNr);
						//log.debug("UploadAction - stuExist Redirect: New");
						return mapping.findForward("submitNew");
					}
				}
			 }
		} catch(Exception ex ){
			//log.debug("Upload failed - Returning to Input");
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "File upload failed (CR), was interrupted or could not be completed. Please try again."));
			addErrors(request, messages);
			return mapping.findForward("input");
		}
		//log.debug("UploadAction - Upload - ================================================================");
		//log.debug("UploadAction - Upload - Done - Going to input");
		//log.debug("UploadAction - Upload - ================================================================");

		 return mapping.findForward("input");
	}


	 private String uploadFile(String filePath,InputStream is) {
		 String result = "Error";
		  try {
			  
			  //log.debug("UploadAction - Entering uploadFile, Filepath:"+filePath);
			  
	        OutputStream os = new FileOutputStream(filePath);

	        byte[] buffer = new byte[1024 * 16];

	        int len;
	        //Add half second delay to enable upload waiting message to initialise
	        
	        while((len=is.read(buffer))!=-1){

	            os.write(buffer,0,len);
	        }
	        
	        Thread.sleep(1000);
	        os.close();
	        os.flush();
	        is.close();
	        result = "Success";
		  } catch(Exception ex ){
			  log.info("UploadAction - Entering uploadFile - Exception="+ex);
			  result = "Error - File upload failed, please try again";
		  }
		  return result;
	    }
	 
		/** Copy documents from temp dir: /data/sakai/applications
		*   to workflow dir: /data/sakai/applications
		*   path in sakai.properties
		*
		*   This method is only called if all required documents have been uploaded
		*   @author Edmund Wilschewski 2013
		**/
		public String moveDocuments(StudentUploadForm stuRegForm) {

			String result = "Error";
			//log.debug("UploadAction - Entering moveDocuments, studentNr:"+stuRegForm.getStudent().getNumber());

			StudentUploadDAO applyDAO = new StudentUploadDAO();
			
	        try {
	        	String qual1 = applyDAO.vrfyNewQualShort("QUAL", "1", stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear());
	        	String qual2 = applyDAO.vrfyNewQualShort("QUAL", "2", stuRegForm.getStudent().getNumber(),stuRegForm.getStudent().getAcademicYear());
	        	
	        	//log.debug("UploadAction - moveDocuments - Qual1="+qual1);
	        	//log.debug("UploadAction - moveDocuments - Qual2="+qual2);

	        	//Get Qualification information to route to correct subfolder - UG/HONS/PG/SLP/SBL/MD
	        	String qualType1 = applyDAO.getQualType(qual1);
	        	String qualType2 = applyDAO.getQualType(qual2);
	        	//log.debug("UploadAction - moveDocuments - QualOne="+qual1+", qualType1="+qualType1);
	        	//log.debug("UploadAction - moveDocuments - QualTwo="+qual2+", qualType2="+qualType2);
	        	boolean isAPP 	= false;
	        	boolean isHON 	= false;
	        	boolean isPG 	= false;
	        	boolean isSBL 	= false;
	        	boolean isSLP 	= false;
	        	boolean isMD 	= false;
	        	
	        	if ("APP".equalsIgnoreCase(qualType1) || "APP".equalsIgnoreCase(qualType2)){
	        		isAPP = true;
	        	}
	        	if ("HON".equalsIgnoreCase(qualType1) || "HON".equalsIgnoreCase(qualType2)){
	        		isHON = true;
	        	}
	        	if ("PG".equalsIgnoreCase(qualType1) || "PG".equalsIgnoreCase(qualType2)){
	        		isPG = true;
	        	}
	        	if ("SBL".equalsIgnoreCase(qualType1) || "SBL".equalsIgnoreCase(qualType2)){
	        		isSBL = true;
	        	}
	        	if ("SLP".equalsIgnoreCase(qualType1) || "SLP".equalsIgnoreCase(qualType2)){
	        		isSLP = true;
	        	}
	        	if ("MD".equalsIgnoreCase(qualType1) || "MD".equalsIgnoreCase(qualType2)){
	        		isMD = true;
	        	}
	        	if (!isAPP && !isHON && !isPG && !isSBL && !isSLP && !isMD){ //Catch All
	        		isAPP = true;
	        	}
	        	
	        	String dirAPP = ServerConfigurationService.getString("application.path");
				//log.debug("UploadAction - moveDocuments - application.path:" + dirAPP + " ............");
				
				String dirHON = ServerConfigurationService.getString("honapply.path");
				//log.debug("UploadAction - moveDocuments - honapply.path:" + dirHON + " ............");
				
				String dirPG = ServerConfigurationService.getString("pgapply.path");
				//log.debug("UploadAction - moveDocuments - pgapply.path:" + dirPG + " ............");
				
				String dirSBL = ServerConfigurationService.getString("sblapply.path");
				//log.debug("UploadAction - moveDocuments - sblapply.path:" + dirSBL + " ............");
				
				String dirSLP = ServerConfigurationService.getString("slpapply.path");
				//log.debug("UploadAction - moveDocuments - slpapply.path:" + dirSLP + " ............");
				
				String dirMD = ServerConfigurationService.getString("mdapply.path");
				//log.debug("UploadAction - moveDocuments - mdapply.path:" + dirMD + " ............");
				
				String stuGroupDir = stuRegForm.getStudent().getNumber().substring(0, 1);
		    	String stuDir = stuRegForm.getStudent().getNumber();
	        	String groupDir = dirAPP + "/tmp/" + stuGroupDir;
	        	String sourceDir =  groupDir + "/" + stuDir;

				//log.debug("UploadAction - moveDocuments - move source path:" + sourceDir + " ............");

	        	//File f = new File(sourceDir); // Source directory
	        	File root = new File(sourceDir); // Source directory
	            
	        	//log.debug("UploadAction - moveDocuments - sourceDir: " + sourceDir);
	        	
				if (!root.exists()) {
					//log.debug("UploadAction - moveDocuments - move source path doesn't exist - Exiting");
					result = "File destination path not found. Please try again.";
				} else {
					//log.debug("UploadAction - moveDocuments - move source path exists");
					 try {
				            boolean recursive = false;
				            boolean isDoRemoveFiles = false;

				            Collection<File> files = FileUtils.listFiles(root, null, recursive);

							 for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
								 boolean copySuccess = true;
					                File sourceFile = iterator.next();
					                if (sourceFile.getName().contains(stuRegForm.getStudent().getNumber().toString())){
			        	        		//log.debug("UploadAction - moveDocuments - file: " + sourceFile);
				        	        	File fileToBeCopied = new File(sourceDir + "/" + sourceFile.getName());
				        	        	//log.debug("UploadAction - moveDocuments - fileToBeCopied: " + fileToBeCopied);
				        	        	//log.debug("UploadAction - moveDocuments - fileToBeCopied name: " + fileToBeCopied.getName());
				        	        	
				        	        	if (isAPP){
				        	        		File dest = new File(dirAPP);
					        	        	//log.debug("UploadAction - moveDocuments - isApp - Copy file to path: " + dirAPP + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("UploadAction - moveDocuments - isApp - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("UploadAction - moveDocuments - isApp - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("UploadAction - moveDocuments - isApp - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("UploadAction - moveDocuments - isApp - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (isHON){
				        	        		File dest = new File(dirHON);
					        	        	//log.debug("UploadAction - moveDocuments - isHON - Copy file to path: " + dirHON + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("UploadAction - moveDocuments - isHON - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("UploadAction - moveDocuments - isHON - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("UploadAction - moveDocuments - isHON - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("UploadAction - moveDocuments - isHON - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (isPG){
				        	        		File dest = new File(dirPG);
					        	        	//log.debug("UploadAction - moveDocuments - isPG - Copy file to path: " + dirPG + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("UploadAction - moveDocuments - isPG - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("UploadAction - moveDocuments - isPG - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("UploadAction - moveDocuments - isPG - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("UploadAction - moveDocuments - isPG - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (isSBL){
				        	        		File dest = new File(dirSBL);
					        	        	//log.debug("UploadAction - moveDocuments - isSBL - Copy file to path: " + dirSBL + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("UploadAction - moveDocuments - isSBL - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("UploadAction - moveDocuments - isSBL - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("UploadAction - moveDocuments - isSBL - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("UploadAction - moveDocuments - isSBL - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (isSLP){
				        	        		File dest = new File(dirSLP);
					        	        	//log.debug("UploadAction - moveDocuments - isSLP - Copy file to path: " + dirSLP + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("UploadAction - moveDocuments - isSLP - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("UploadAction - moveDocuments - isSLP - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("UploadAction - moveDocuments - isSLP - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("UploadAction - moveDocuments - isSLP - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (isMD){
				        	        		File dest = new File(dirMD);
					        	        	//log.debug("UploadAction - moveDocuments - isMD - Copy file to path: " + dirMD + "/ ............");

					        	        	File destFile = new File(dest + "/" + fileToBeCopied.getName());
					        	        	//log.debug("UploadAction - moveDocuments - isMD - destFile: " + destFile);
					        	        	FileUtils.copyFile(sourceFile, destFile);
					        	        	//boolean success = sourceFile.renameTo(destFile);
					        	        	
					        	        	if (destFile.exists()) {
					        	        		//log.debug("UploadAction - moveDocuments - isMD - File " + destFile + " was copied.\n");
					        	        	    if (FileUtils.contentEquals(sourceFile, destFile)){
					        	        			//log.debug("UploadAction - moveDocuments - isMD - File " + sourceFile + " is same as - "+destFile+" - was successfully copied.\n");
					        	        	    }else{
					        	        	    	copySuccess = false;
					        	        	    }
					        	        	} else {
					        	       			//log.debug("UploadAction - moveDocuments - isMD - File " + sourceFile + " was not successfully copied. It can be because file with file name already exists in destination\n");
					        	       			copySuccess = false;
					        	       		}
				        	        	}
				        	        	if (copySuccess){
				        	        		isDoRemoveFiles = true;
				        	        		result = "Success";
				        	        	}else{
				        	        		result = "Error - Copying Temporaru Files to Uniflow failed, please try again";
				        	        	}
				        	       	}
							 }
							 if (isDoRemoveFiles){
								//Check if student and group folders are empty. 
								 //Delete them if they are as we don't want to clutter the Unix filesystem unnecessary
								 //First do Student specific folder if it is empty (/tmp/group/studentnr)
								 File testStuDir = new File (sourceDir);
								 if (testStuDir.isDirectory()) {
									 String[] children1 = testStuDir.list(); //Create list of files in folder
									 //log.debug("UploadAction - MoveDocuments - StuDir Before Delete, thus Deleted=" + children1.length);
									 
									 //Delete all files and sub-folders in Student Temp folder
									 FileUtils.cleanDirectory(testStuDir);
									 
									 String[] children2 = testStuDir.list(); //Create list of files in folder
									 //log.debug("UploadAction - MoveDocuments - StuDir After Delete, thus Deleted=" + children2.length);
									 if (children2.length == 0){ //folder empty
										 testStuDir.delete();
										 //log.debug("UploadAction - MoveDocuments - StuDir Empty, thus Deleted: " + testStuDir);
									 }else{
										 //log.debug("UploadAction - MoveDocuments - StuDir not empty: " + testStuDir + ":" + children2.length);
									 }
								 }
				                    
								 //Now do Group Folder if it is empty (/tmp/group)
								 File testGroupDir = new File (groupDir);
								 if (testGroupDir.isDirectory()) {
									 String[] children = testGroupDir.list(); //Create list of files in folder
									 /**Use if you wish to delete files in folder
									  * At this time, we however don't want to delete the folder if it is not empty
				                     */
				                    if (children.length == 0){ //folder empty
				                    	testGroupDir.delete();
				                    	//log.debug("UploadAction - MoveDocuments - GroupDir Empty, thus Deleted: " + testGroupDir);
				                    	log.info("UploadAction MoveDocuments " + stuRegForm.getStudent().getNumber() + " - GroupDir Empty, thus Deleted: " + testGroupDir);
				                    }else{
				                    	//log.debug("UploadAction - MoveDocuments - GroupDir not empty: " + testGroupDir + ":" + children.length);
				                    }
								 }
							 }
					   	} catch (Exception e) {
					   		log.info("UploadAction - MoveDocuments - Exception="+e);
							result = "Error - Moving File to Uniflow failed, please try again";
					   	}
				}
	        } catch(Exception ex ){
	        	log.info("UploadAction - MoveDocuments - Exception="+ex);
	        	result = "Error - Moving File to Uniflow failed, please try again";
		  	}
	        return result;
		}
	
		
	public void resetForm(StudentUploadForm form, String callAction)	throws Exception {

		//log.debug("UploadAction - Entering resetForm - Called By="+callAction);

		String currentYear = form.getStudent().getAcademicYear();
		String currentPeriod = form.getStudent().getAcademicPeriod();
		
		// Clear fields
		StudentUploadForm stuRegForm = new StudentUploadForm();
		stuRegForm.getStudent().setAcademicYear(currentYear);
		stuRegForm.getStudent().setAcademicPeriod(currentPeriod);
		//log.debug("UploadAction - All StudentRegistrationForm Variables Cleared - Year="+stuRegForm.getStudent().getAcademicYear()+". Period="+stuRegForm.getStudent().getAcademicPeriod());
	}
		
		
	@SuppressWarnings("unchecked")
	public void getAllRequestParamaters(HttpServletRequest req, HttpServletResponse res) throws Exception { 

	    Enumeration<String> parameterNames = req.getParameterNames(); 

	    while (parameterNames.hasMoreElements()) { 
	    	String paramName = parameterNames.nextElement(); 
	    	//log.debug("param: " + paramName); 

			String[] paramValues = req.getParameterValues(paramName); 
			for (int i = 0; i < paramValues.length; i++) { 
				String paramValue = paramValues[i]; 
				//log.debug("value: " + paramValue); 
			} 
		} 
	} 
	
	public String readableFileSize(long size) {
		String result = "Error";
		try{
		    if(size <= 0) return "0";
		    final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
		    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
		    result = new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
		} catch (Exception e) {
			log.info("UploadAction - readableFileSize failed - Exception="+e);
			result = "Error - UploadAction - readableFileSize failed";
		}
		return result;
	}
	
	//Include some cross site scripting checks (XSS)
    private String stripXSS(String value) { 

    	String result = "Error";
    	try{
	        if (value != null) { 
	
	            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to 
	            // avoid encoded attacks. 
	            //result = ESAPI.encoder().canonicalize(value); 
	
	            // Avoid null characters 
	        	result = value.replaceAll("", ""); 
	
	            // Avoid anything between script tags 
	            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE); 
	            result = scriptPattern.matcher(value).replaceAll(""); 
	
	            // Avoid anything in a src='...' type of expression 
	            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
	
	            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
	            result = scriptPattern.matcher(value).replaceAll(""); 
	
	            // Remove any lonesome </script> tag 
	            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE); 
	            result = scriptPattern.matcher(value).replaceAll(""); 
	
	            // Remove any lonesome <script ...> tag 
	            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
	            result = scriptPattern.matcher(value).replaceAll(""); 
	
	            // Avoid eval(...) expressions 
	            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
	            result = scriptPattern.matcher(value).replaceAll(""); 
	
	            // Avoid expression(...) expressions 
	            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
	            result = scriptPattern.matcher(value).replaceAll(""); 
	
	            // Avoid javascript:... expressions 
	            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE); 
	            result = scriptPattern.matcher(value).replaceAll(""); 
	
				// Avoid vbscript:... expressions 
	            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE); 
	            result = scriptPattern.matcher(value).replaceAll(""); 
	
	            // Avoid onload= expressions 
	            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL); 
	            result = scriptPattern.matcher(value).replaceAll(""); 
	        } 
    	} catch (Exception e) {
			log.info("UploadAction - stripXSS failed - Value="+value+", Exception="+e);
			result = "Error - UploadAction - stripXSS failed. Value="+value;
		}
        return result; 
    } 
    
	public void reLoad(StudentUploadForm form) throws Exception{
		form.getDesc().clear();
		form.getMap().clear();
		SavedDocDao savedDocDao = new SavedDocDao();
		//log.debug("StudentRegistrationForm - reLoad - Reloading uploaded docs..");
		savedDocDao.getAllNonRequiredDocInfo(form.getDesc(), form.getMap(), form.getStudent().getNumber(),form.getStudent().getAcademicYear(),form.getStudent().isStuExist(),form.getStudent().getMatrix());
		for(FileBean fb : form.getRequiredFileBeans()){
			//log.debug("StudentRegistrationForm - reLoad - Settigng FileBean Uploaded..");
			fb.setUploaded(savedDocDao.getSavedDocByDoc(fb.getDoc().getDocCode(),form.getStudent().getNumber(),form.getStudent().getAcademicYear()));
		}
		//log.debug("StudentRegistrationForm - reLoad - Done");
	}
	
}


