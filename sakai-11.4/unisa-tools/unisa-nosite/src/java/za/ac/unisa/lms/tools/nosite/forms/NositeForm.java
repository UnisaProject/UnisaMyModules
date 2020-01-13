//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.nosite.forms;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import org.apache.struts.action.ActionForm;
import za.ac.unisa.lms.tools.nosite.dao.College;
import za.ac.unisa.lms.tools.nosite.dao.NositeDetails;

/**
 * MyEclipse Struts
 * Creation date: 09-08-2006
 *
 * XDoclet definition:
 * @struts.form name="nositeForm"
 */
public class NositeForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** college properties */
	private List collegeList;
	private Set collegeDetails;
	private String college;
	private String college_code;
	private List collegeinfo;
	private Set colleges;
	private Map collegeMap;
	private Map collegecodeMap;



	/** Inactive sites properties */
	private String siteid;
	private String total;
	private String period;
	private String studyunit;

	/** school properties */
	private String school;
	private Set schools;
	private Map schcodeMap;
	private Map schMap;
	private List schoollist;
	private String school_code;
	private Map schoolMap;

	/** department property */
	private String department;
	private Set departments;
	private Map deptMap;
	private String department_code;
	private Map deptcodeMap;


	private String lastYear;
	private String currentYear;
	private String nextYear;
	private String year;


	/** Inactive sites page page properties*/
	private List inactivesites;
	private List Activesites;
	private int active;
	private int inactive;
	private List someinactives;

	private int start;
	private int end;
	private int records=20;
	private int numitems;
	private int someitems;
	private int goback;
	private int goforth;


	/**
	 * Adjusts the start of the page to point to the prev page.
	 * @return int
	 */

	public int getGoback()
	{goback =start;
	return goback;
	}

	/**
	 * Adjusts the start of the page  to point to the next page.
	 * @return int
	 */

	public int getGoforth()
	{goforth=start;
	return goforth;
	}



	/**
	 * Returns the length of the records displayable in a page
	 * @return int
	 */
	public int getSomeitems()
	{return someinactives.size();}

	/**
	 * Returns the # records for a given selection of school & department in a college
	 * @return int
	 */
	public int getNumitems()
	{return  inactivesites.size();

	}
	/**
	 * get the college.
	 * @returns the selected college
	 */

	public String getCollege() {
		return college;
	}

	/**
	 * Set the college.
	 * @param college The college to set
	 */
	public void setCollege(String college) {
		this.college = college;
	}

	/**
	 * Returns the total # of student in for a specific course.
	 * @return String
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * Set the no of students.
	 * @param noofstudent The no of student to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * Returns the school.
	 * @return String
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * Set the school.
	 * @param school The school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * Returns the department.
	 * @return String
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Set the department.
	 * @param department The department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public String getLastYear() {
		return lastYear;
	}

	public void setLastYear(String lastYear) {
		this.lastYear = lastYear;
	}

	public String getNextYear() {
		return nextYear;
	}

	public void setNextYear(String nextYear) {
		this.nextYear = nextYear;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	public void setCollegeDetails(Set<String> collegeDetails )
	{this.collegeDetails=collegeDetails;}


	public Set getCollegeDetails()
	{return collegeDetails;}

	public Set getDepartments() {
		return departments;
	}
	public Set getSchools() {
		return schools;
	}
	public void setSchools(Set schools) {
		this.schools = schools;
	}
	public List getCollegeList() {
		return collegeList;
	}
	public void setCollegeList(List collegeList) {
		this.collegeList = collegeList;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getInactive() {
		return inactive;
	}
	public void setInactive(int inactive) {
		this.inactive = inactive;
	}

	public List getActivesites() {
		return Activesites;
	}
	public void setActivesites(List activesites,String year) {
		Activesites = activesites;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getStudyunit() {
		return studyunit;
	}
	public void setStudyunit(String studyunit) {
		this.studyunit = studyunit;
	}
	public List getInactivesites() {
		return inactivesites;
	}
	public void setInactivesites(List inactivesites) {
		this.inactivesites = inactivesites;
	}
	public List getSomeinactives() {
		return someinactives;
	}
	public void setSomeinactives(List someinactives) {
		this.someinactives = someinactives;
	}
	public int getStart() {
		return start;
	}
	public  void setStart(int start) {
		this.start = start;
	}
	public  int getRecords() {
		return records;
	}
	public  void  setRecords(int records) {
		 this.records = records;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
	public List getCollegeinfo() {
		return collegeinfo;
	}
	public void setCollegeinfo(List collegeinfo) {
		this.collegeinfo = collegeinfo;
	}
	public void setNumitems(int numitems) {
		this.numitems = numitems;
	}

	public void setDepartments(Set departments) {
		this.departments = departments;
	}

	public Set getColleges() {
		return colleges;
	}

	public void setColleges(Set colleges) {
		this.colleges = colleges;
	}

	public Map getCollegeMap() {
		return collegeMap;
	}

	public void setCollegeMap(Map collegeMap) {
		this.collegeMap = collegeMap;
	}

	public Map getDeptMap() {
		return deptMap;
	}

	public void setDeptMap(Map deptMap) {
		this.deptMap = deptMap;
	}

	public Map getSchoolMap() {
		return schoolMap;
	}

	public void setSchoolMap(Map schoolMap) {
		this.schoolMap = schoolMap;
	}

	public Map getCollegecodeMap() {
		return collegecodeMap;
	}

	public void setCollegecodeMap(Map collegecodeMap) {
		this.collegecodeMap = collegecodeMap;
	}


	public Map getSchMap() {
		return schMap;
	}

	public void setSchMap(Map schMap) {
		this.schMap = schMap;
	}

	public Map getSchcodeMap() {
		return schcodeMap;
	}

	public void setSchcodeMap(Map schcodeMap) {
		this.schcodeMap = schcodeMap;
	}

	public Map getDeptcodeMap() {
		return deptcodeMap;
	}

	public void setDeptcodeMap(Map deptcodeMap) {
		this.deptcodeMap = deptcodeMap;
	}

	public List getSchoollist() {
		return schoollist;
	}

	public void setSchoollist(List schoollist) {
		this.schoollist = schoollist;
	}

}

