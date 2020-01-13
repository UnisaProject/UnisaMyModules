package za.ac.unisa.lms.tools.tpstudymaterial.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.tpstudymaterial.dao.StudyMaterialDetails;

public class TpStudyMaterialForm extends ValidatorForm{
	private String studentNr;
	private List<StudyMaterialDetails> studyMaterialList;
	private ArrayList courseList;
	private String lastAcademicYear;
	private String course;
	private String courseCode;
	private String listdate;
	private String backOption;
	private String semister; 
	private String dateOfBirth;
	private boolean blockedStatus;
	private String path;
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullNames() {
		return fullNames;
	}

	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	private String academicYear;
	private String filename;
	private String filesize;
	private String surname;
	private String fullNames;
	private String birthYear;
	private String birthMonth;
	private String birthDay;

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getSemister() {
		return semister;
	}

	public void setSemister(String semister) {
		this.semister = semister;
	}

	public String getBackOption() {
		return backOption;
	}

	public void setBackOption(String backOption) {
		this.backOption = backOption;
	}

	public String getListdate() {
		return listdate;
	}

	public void setListdate(String listdate) {
		this.listdate = listdate;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getLastAcademicYear() {
		return lastAcademicYear;
	}

	public void setLastAcademicYear(String lastAcademicYear) {
		this.lastAcademicYear = lastAcademicYear;
	}

	public ArrayList getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList courseList) {
		this.courseList = courseList;
	}

	public List<StudyMaterialDetails> getStudyMaterialList() {
		return studyMaterialList;
	}

	public void setStudyMaterialList(List<StudyMaterialDetails> studyMaterialList) {
		this.studyMaterialList = studyMaterialList;
	}

	public String getStudentNr() {
		return studentNr;
	}

	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}
	
	public boolean isBlockedStatus(){
		return blockedStatus;
	}
	
	public void setBlockedStatus(boolean blockedStatus){
		this.blockedStatus = blockedStatus;
	}

}
