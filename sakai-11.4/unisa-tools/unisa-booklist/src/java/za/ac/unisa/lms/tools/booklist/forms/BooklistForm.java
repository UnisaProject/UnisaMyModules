//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.booklist.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import org.apache.struts.action.ActionForm;

import za.ac.unisa.lms.tools.booklist.dao.College;
import za.ac.unisa.lms.tools.booklist.dao.BooklistDetails;


/**
 * MyEclipse Struts
 * Creation date: 09-08-2006
 *
 * XDoclet definition:
 * @struts.form name="nositeForm"
 */
public class BooklistForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Set - a set of colleges 
	 */

    private ArrayList colleges;

    
    private ArrayList prescribedBooks;
    private String statusOptions;
  
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String booklistTypes;
	private String booklistStatus;
    private List someprescribedBooks;
    private String [] selectedModules;
    private ArrayList modules;
    private ArrayList firstmodules;
    private String state;
    /**
     * college - property of colleges collection
     */
    private String collegeCode;
    private String college;
    private String school="-1";
    private String department="-1";
    private String olddept="-1";
    public String getBooklistStatus() {
		return booklistStatus;
	}
	public void setBooklistStatus(String booklistStatus) {
		this.booklistStatus = booklistStatus;
	}
	private String oldsch="-1";
    public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	private String studyUnit;
    
    private int  records =20;
    private int  start =1;
    private int  end =records;    
    private int  numitems;
    private String status;
  
    private String viewStudyUnits;
   // private String
   // private String
	
    /**
     * Map - a mapping of colleges to the number prescribed books per college
     */   
    
    private Map collegeTotals;
    private List booklistOpen;
    private Map booklistAuthorized;
    private Map noBooks;
	
    /**
     * String - the year of interest
     */    
    
    private String year;
    private String lastYear;
    private String currentYear;
    private String nextYear;
    private String prevYear;
    private List collegeinfo;
    private Set collegestat;
    private String today;
 
    
    /*private String dept;
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}*/
   
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getBooklistTypes() {
		return booklistTypes;
	}
	public void setBooklistTypes(String booklistTypes) {
		this.booklistTypes = booklistTypes;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getLastYear() {
		return lastYear;
	}
	public void setLastYear(String lastYear) {
		this.lastYear = lastYear;
	}
	public String getCurrentYear() {
		return currentYear;
	}
	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}
	public String getNextYear() {
		return nextYear;
	}
	public void setNextYear(String nextYear) {
		this.nextYear = nextYear;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	
	public Map getCollegeTotals() {
		return collegeTotals;
	}
	public void setCollegeTotals(Map collegeTotals) {
		this.collegeTotals = collegeTotals;
	}
	public List getBooklistOpen() {
		return booklistOpen;
	}
	public void setBooklistOpen(List booklistOpen) {
		this.booklistOpen = booklistOpen;
	}

	public Map getBooklistAuthorized() {
		return booklistAuthorized;
	}
	public void setBooklistAuthorized(Map booklistAuthorized) {
		this.booklistAuthorized = booklistAuthorized;
	}
	public Map getNoBooks() {
		return noBooks;
	}
	public void setNoBooks(Map noBooks) {
		this.noBooks = noBooks;
	}
	public String getStatusOptions() {
		return statusOptions;
	}
	public void setStatusOptions(String statusOptions) {
		this.statusOptions = statusOptions;
	}
	public ArrayList getPrescribedBooks() {
		return prescribedBooks;
	}
	public void setPrescribedBooks(ArrayList prescribedBooks) {
		this.prescribedBooks = prescribedBooks;
	}
	
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCollegeCode() {
		return collegeCode;
	}
	public void setCollegeCode(String collegeCode) {
		this.collegeCode = collegeCode;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {

		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getNumitems() {
		return prescribedBooks.size();
	}
	public void setNumitems(int numitems) {
		this.numitems = numitems;
	}
	public List getSomeprescribedBooks() {
		return someprescribedBooks;
	}
	public void setSomeprescribedBooks(List list) {
		this.someprescribedBooks = list;
	}
	public String getViewStudyUnits() {
		return viewStudyUnits;
	}
	public void setViewStudyUnits(String viewStudyUnits) {
		this.viewStudyUnits = viewStudyUnits;
	}
	public String getStudyUnit() {
		return studyUnit;
	}
	public void setStudyUnit(String studyUnit) {
		this.studyUnit = studyUnit;
	}
	public String[] getSelectedModules() {
		return selectedModules;
	}
	public void setSelectedModules(String[] selectedModules) {
		this.selectedModules = selectedModules;
	}
	public ArrayList getModules() {
		return modules;
	}
	public void setModules(ArrayList modules) {
		this.modules = modules;
	}
	public String getOlddept() {
		return olddept;
	}
	public void setOlddept(String olddept) {
		this.olddept = olddept;
	}
	public String getOldsch() {
		return oldsch;
	}
	public void setOldsch(String oldsch) {
		this.oldsch = oldsch;
	}
	public ArrayList getFirstmodules() {
		return firstmodules;		
	}
	public void setFirstmodules(ArrayList firstmodules) {
		this.firstmodules = firstmodules;
	}
	public String getPrevYear() {
		return prevYear;
	}
	public void setPrevYear(String prevYear) {
		this.prevYear = prevYear;
	}
	public List getCollegeinfo() {
		return collegeinfo;
	}
	public void setCollegeinfo(List collegeinfo) {
		this.collegeinfo = collegeinfo;
	}
	public Set getCollegestat() {
		return collegestat;
	}
	public void setCollegestat(Set collegestat) {
		this.collegestat = collegestat;
	}
	public ArrayList getColleges() {
		return colleges;
	}
	public void setColleges(ArrayList colleges) {
		this.colleges = colleges;
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}


	
}

