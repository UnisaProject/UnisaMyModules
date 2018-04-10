package impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import utils.UploadManagerFileUtils;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;

public class PdfViewer {

	public void outPutDataToClient(HttpServletResponse response,
			String pdffullPath) throws IOException {
		File file = new File(pdffullPath);
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
	    response.setHeader("Cache-Control", "private"); // HTTP 1.1.
        response.setHeader("Pragma", "cache");
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "filename=\"mydoc.pdf\";");
		response.setContentLength((int) file.length());
		saveToClient(response, file);
	}

	public static void saveToClient(HttpServletResponse response, File file) {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		int DEFAULT_BUFFER_SIZE = 1024;
		try {
			input = new BufferedInputStream(new FileInputStream(file),
					DEFAULT_BUFFER_SIZE);
			output = new BufferedOutputStream(response.getOutputStream(),
					DEFAULT_BUFFER_SIZE);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} catch (Exception e) {

		} finally {
			UploadManagerFileUtils.closeBuffers(input, output);
		}
	}

	public List getStudyMaterialList(UploadManagerForm uploadManagerForm) throws Exception{
		String modCode = uploadManagerForm.getCourseCode();
		String year = uploadManagerForm.getAcadYear();
		String period = uploadManagerForm.getPeriod();
		String lang = uploadManagerForm.getLanguage();
		String type = uploadManagerForm.getType();
		StudyMaterial studyMaterial = new StudyMaterial();
		return studyMaterial.getStudyMaterialList(modCode, year, period, lang,
				type);
	}

}
