package za.ac.unisa.lms.tools.uploadmanager.forms;

public class MaterialDetails {

private String itembarcode;
private String itemshortdesc;
private String itemfulldesc;
private String module;
private String academicyear;
private String academicperiod;
private String unitno;
private String language;
private String documenttype;
private String documentname;
private String dateavailable;
private String filesize;
private String lecturer;
private boolean filestatus;
private boolean docAvailability;
public boolean isDocAvailability() {
	return docAvailability;
}
public void setDocAvailability(boolean docAvailability) {
	this.docAvailability = docAvailability;
}
public boolean isFilestatus() {
	return filestatus;
}
public void setFilestatus(boolean filestatus) {
	this.filestatus = filestatus;
}
public String getItembarcode() {
	return itembarcode;
}
public void setItembarcode(String itembarcode) {
	this.itembarcode = itembarcode;
}
public String getItemshortdesc() {
	return itemshortdesc;
}
public void setItemshortdesc(String itemshortdesc) {
	this.itemshortdesc = itemshortdesc;
}
public String getItemfulldesc() {
	return itemfulldesc;
}
public void setItemfulldesc(String itemfulldesc) {
	this.itemfulldesc = itemfulldesc;
}
public String getModule() {
	return module;
}
public void setModule(String module) {
	this.module = module;
}
public String getAcademicyear() {
	return academicyear;
}
public void setAcademicyear(String academicyear) {
	this.academicyear = academicyear;
}
public String getAcademicperiod() {
	return academicperiod;
}
public void setAcademicperiod(String academicperiod) {
	this.academicperiod = academicperiod;
}
public String getUnitno() {
	return unitno;
}
public void setUnitno(String unitno) {
	this.unitno = unitno;
}
public String getLanguage() {
	return language;
}
public void setLanguage(String language) {
	this.language = language;
}
public String getDocumenttype() {
	return documenttype;
}
public void setDocumenttype(String documenttype) {
	this.documenttype = documenttype;
}
public String getDocumentname() {
	return documentname;
}
public void setDocumentname(String documentname) {
	this.documentname = documentname;
}
public String getDateavailable() {
	return dateavailable;
}
public void setDateavailable(String dateavailable) {
	this.dateavailable = dateavailable;
}
public String getFilesize() {
	return filesize;
}
public void setFilesize(String filesize) {
	this.filesize = filesize;
}
public String getLecturer() {
	return lecturer;
}
public void setLecturer(String lecturer) {
	this.lecturer = lecturer;
}

}
