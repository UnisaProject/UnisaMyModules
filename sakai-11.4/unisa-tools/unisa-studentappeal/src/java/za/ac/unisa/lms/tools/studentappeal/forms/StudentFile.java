package za.ac.unisa.lms.tools.studentappeal.forms;

public class StudentFile {
	
	/* fileName: 
	 * --------
	 * name of student's file from his/her computer, 
	 * for display purposes only
	 */
	private String fileName = ""; 
	/* newFileName;
	 * -----------
	 * New name given to student's file. It will be used on the
	 * web server and should be specific to the work flow system
	 * specifications for routing purposes. This name will get a 
	 * time stamp added to it, as the student can upload more than
	 * one of the same files.
	 */
	private String newFileName = "";
	/* fileType;
	 * -----------
	 * This identifies the file as a certain type. Used to setup
	 * the new file name for the work flow system.
	 */
	private String fileType = "";
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	
	
	
}
