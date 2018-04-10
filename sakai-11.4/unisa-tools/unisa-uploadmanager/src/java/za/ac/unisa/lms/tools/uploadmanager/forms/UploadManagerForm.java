package za.ac.unisa.lms.tools.uploadmanager.forms;

import java.util.ArrayList;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.validator.ValidatorActionForm;

public class UploadManagerForm extends ValidatorActionForm {

	
	private String courseCode;
	private String acadYear;
	private String period;
	private String userId;
	private String message;
	private String type;
	private String language;
	private String status;
	private FormFile file;
	private String filename;
	private String method;
	private String searchCode; 
	private String from; 
	private String barcode;
	private String fileSize;
	private String file_name;
	private String doc_type;
	private String mod_code;
	private String lang;
	private String itemfulldesc;
	private String materialPeriod;
	private String documentType;
	private String fromStaff;
	private String itemDesc;
	private String inputFilePath;
	private String  userAction;
	List yearsList,courseCodeList,materialList;
	private List lecturersModuleCodeList = new ArrayList();
	private ArrayList fromYearList;
	private String moduleCode1;
	private String userprivilege;
	
	@Override
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
 
	    ActionErrors errors = new ActionErrors();
	    
	   if (getFile() != null) {
	      
		   if (getFile().getFileSize()== 0) {
	           errors.add(ActionMessages.GLOBAL_MESSAGE,
	    	   new ActionMessage("errors.message","Please select a file to upload"));
	           setFile(null);
	           return errors;
	       }
 
	    //only allow textfile to upload
/*	    if(!"text/plain".equals(getFile().getContentType())){
	        errors.add("common.file.err.ext",
	    	 new ActionMessage("error.common.file.textfile.only"));
	        return errors;
	    }*/
	    
            //file size cant larger than 10kb
	    System.out.println(getFile().getFileSize());
	    if(getFile().getFileSize() > 1024 * 1024 * 200){ //2mb
	       errors.add(ActionMessages.GLOBAL_MESSAGE,
		    new ActionMessage("errors.message","File size exceeds 200MB"));
	       setFile(null);
	       return errors;
	    }
	    
	    
		
		   if (!(getFile().getFileName() != null && getFile().getFileName().substring(getFile().getFileName().lastIndexOf(".")+1).equalsIgnoreCase("pdf"))) {
			   
		        errors.add(ActionMessages.GLOBAL_MESSAGE,
		 	    	   new ActionMessage("errors.message","You can only upload the pdf files."));
		 	           setFile(null);
		 	           return errors;
		   }
		   
		   
	    }
	    return errors;
	}
	
	
	public void setUserAction(String userAction){
		this.userAction=userAction;
	}
	public String getUserAction(){
		return userAction;
	}
	public void setInputFilePath(String inputFilePath){
		    this.inputFilePath=inputFilePath;
	}
	public String getInputFilePath(){
		           return inputFilePath;
	}
	public String getItemDesc(){
		return itemDesc;
	}
	public void setItemDesc(String itemDesc){
	      	this.itemDesc=itemDesc;
	}
	public List getYearsList(){
		return yearsList;
	}
	public void setYearsList(List yearsList){
		this.yearsList=yearsList;
	}
	public List getCourseCodeList(){
		return courseCodeList;
	}
	public void setCourseCodeList(List courseCodeList){
		this.courseCodeList=courseCodeList;
	}
	public List getLecturersModuleCodeList(){
		return lecturersModuleCodeList;
	}
	public void setLecturersModuleCodeList(List lecturersModuleCodeList){
		this.lecturersModuleCodeList=lecturersModuleCodeList;
	}
	
	public String getFromStaff() {
		return fromStaff;
	}
	public void setFromStaff(String fromStaff) {
		this.fromStaff = fromStaff;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getMaterialPeriod() {
		return materialPeriod;
	}
	public void setMaterialPeriod(String materialPeriod) {
		this.materialPeriod = materialPeriod;
	}
	public String getItemfulldesc() {
		return itemfulldesc;
	}
	public void setItemfulldesc(String itemfulldesc) {
		this.itemfulldesc = itemfulldesc;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String fileName) {
		file_name = fileName;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	private String selectedCourseCode;
	private String fromSearch;
	private String search;
	
	
	public String getFromSearch() {
		return fromSearch;
	}
	public void setFromSearch(String fromSearch) {
		this.fromSearch = fromSearch;
	}
	public String getSearchCode() {
		return searchCode;
	}
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	public String getSelectedCourseCode() {
		return selectedCourseCode;
	}
	public void setSelectedCourseCode(String selectedCourseCode) {
		this.selectedCourseCode = selectedCourseCode;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getModuleCode1() {
		return moduleCode1;
	}
	public void setModuleCode1(String moduleCode1) {
		this.moduleCode1 = moduleCode1;
	}
	public List getMaterialList() {
		return materialList;
	}
	public void setMaterialList(List materialList) {
		this.materialList = materialList;
	}
	public String getFilename() {
		return filename;
	}
	public void setFileName(String fileName) {
		this.filename = fileName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList getFromYearList() {
		fromYearList = new ArrayList();
		 Calendar cal=Calendar.getInstance();
		 int max=cal.get(Calendar.YEAR)+6;
		 int min=cal.get(Calendar.YEAR)-1;
		for (int i=min; i <= max; i++) {
			fromYearList.add(new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
		}
		
	
		return fromYearList;
	}
	public void setFromYearList(ArrayList fromYearList) {
		this.fromYearList = fromYearList;
	}
	
	public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	public  String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getAcadYear() {
		return acadYear;
	}
	public void setAcadYear(String acadYear) {
		this.acadYear = acadYear;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the userprivilege
	 */
	public String getUserprivilege() {
		return userprivilege;
	}
	/**
	 * @param userprivilege the userprivilege to set
	 */
	public void setUserprivilege(String userprivilege) {
		this.userprivilege = userprivilege;
	}
	
	
}
