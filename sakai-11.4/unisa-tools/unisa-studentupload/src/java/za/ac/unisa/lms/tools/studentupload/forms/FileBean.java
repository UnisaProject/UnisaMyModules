package za.ac.unisa.lms.tools.studentupload.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import za.ac.unisa.lms.tools.studentupload.bo.Doc;
import za.ac.unisa.lms.tools.studentupload.bo.SavedDoc;

public class FileBean {
    private Doc doc = new Doc();
    private FormFile file;
    private List<SavedDoc> uploaded = new ArrayList<SavedDoc>();
    
	@SuppressWarnings("unused")
	private boolean moreThanFive = false;
 
	public Doc getDoc() {
		return doc;
	}
	public void setDoc(Doc doc) {
		this.doc = doc;
	}
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		//log.debug("###$$$"+(file==null));
		this.file = file;
		
	}
	public List<SavedDoc> getUploaded() {
		return uploaded;
	}
	public void setUploaded(List<SavedDoc> uploaded) {
		this.uploaded = uploaded;
	}
	public boolean isMoreThanFive() {
		return uploaded.size()>=5;
	}
	public void setMoreThanFive(boolean moreThanFive) {
		this.moreThanFive = moreThanFive;
	}
}
