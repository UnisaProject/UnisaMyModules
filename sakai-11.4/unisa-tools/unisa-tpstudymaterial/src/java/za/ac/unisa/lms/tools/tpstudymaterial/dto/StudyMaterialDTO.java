package za.ac.unisa.lms.tools.tpstudymaterial.dto;

import java.util.Date;

public class StudyMaterialDTO {
	
	
	private String description;
	private long filesize;
	private String dateofAvailable;
	private String moduleCode;
	
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the filesize
	 */
	public long getFilesize() {
		return filesize;
	}
	/**
	 * @param filesize the filesize to set
	 */
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	/**
	 * @return the dateofAvailable
	 */
	public String getDateofAvailable() {
		return dateofAvailable;
	}
	/**
	 * @param dateofAvailable the dateofAvailable to set
	 */
	public void setDateofAvailable(String dateofAvailable) {
		this.dateofAvailable = dateofAvailable;
	}
	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}
	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
    
	
	
	

}
