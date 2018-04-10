package UILayer;

import impl.MetaData;
import impl.PdfUploader;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import module.StudyMaterialModule;
import org.apache.struts.action.ActionMessages;
import utils.DateUtil;
import utils.FileUploadUtils;
import utils.InfoMessagesUtil;
import utils.UploadManagerFileUtils;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;

public class PdfUploaderUI {

	private String updateTheStudyMaterialList(List studyMaterilaList,
			String barcode, String userId) throws Exception {

		int index = FileUploadUtils.getIndexOfItemInList(studyMaterilaList,
				barcode);
		StudyMaterialModule studyMaterial = (StudyMaterialModule) studyMaterilaList
				.get(index);
		studyMaterial.setFilestatus(true);
		//studyMaterial.setDateavailable(DateUtil.todayDate());
		if (userId != null) {
		  studyMaterial.setLecturer(userId.toUpperCase());
		}
		return "uploadscreen";

	}

	private String createUploadScreen(UploadManagerForm uploadManagerForm,
			ActionMessages messages) throws Exception {
		MetaData metaData = new MetaData();
		String errorMsg = "";
		errorMsg = metaData.setMetaDataToFormBean(uploadManagerForm);
		if (!errorMsg.equals("")) {
			InfoMessagesUtil.addMessages(messages, errorMsg);
			return "mainfilter";
		}
		return "uploadscreen";
	}

	private String uploadingHander(HttpServletRequest request,UploadManagerForm uploadManagerForm, ActionMessages messages)
			 throws Exception {
		PdfUploader pdfUploader = new PdfUploader();
		String errorMsg = pdfUploader.uploadFile(uploadManagerForm);
		updateTheStudyMaterialList(uploadManagerForm.getMaterialList(),uploadManagerForm.getBarcode(), uploadManagerForm.getUserId());
		ScreenFinderUtil screenFinderUtil = new ScreenFinderUtil();
		
		if (!errorMsg.equals("")) {
			InfoMessagesUtil.addMessages(messages, errorMsg);
			return screenFinderUtil.getNextScreenGivenErrorMsg(errorMsg);
		} else {
			InfoMessagesUtil.addMessages(messages,"File has been uploaded successfully");
		}
		
		
		
		return "uploadscreen";
	}

	private String reloadingHander(HttpServletRequest request,
			UploadManagerForm uploadManagerForm, ActionMessages messages)
			throws Exception {
		
		PdfUploader pdfUploader = new PdfUploader();
		
		String errorMsg = pdfUploader.reloadFile(uploadManagerForm);
		
		updateTheStudyMaterialList(uploadManagerForm.getMaterialList(),
				uploadManagerForm.getBarcode(), uploadManagerForm.getUserId());
		
		UploadManagerFileUtils fileUtils = new UploadManagerFileUtils();
		fileUtils.setInputFileToRequest(request);
		ScreenFinderUtil screenFinderUtil = new ScreenFinderUtil();
		
		if (!errorMsg.equals("")) {
			InfoMessagesUtil.addMessages(messages, errorMsg);
			return screenFinderUtil.getNextScreenGivenErrorMsg(errorMsg);
		} else {
			InfoMessagesUtil.addMessages(messages,
					"File has been reloaded successfully");
		}
		return "uploadscreen";
	}

	public String processUploadRequest(HttpServletRequest request,
			UploadManagerForm uploadManagerForm, ActionMessages messages)
			throws Exception {
		String nextScreen = "";
		if ("1".equalsIgnoreCase(request.getParameter("atStep"))) {
			nextScreen = createUploadScreen(uploadManagerForm, messages);
		} else if ("upload".equalsIgnoreCase(request.getParameter("atStep"))) {
			nextScreen = uploadingHander(request, uploadManagerForm, messages);
		} else if ("reload".equalsIgnoreCase(request.getParameter("atStep"))) {
			nextScreen = reloadingHander(request, uploadManagerForm, messages);
		}
		return nextScreen;
	}
}
