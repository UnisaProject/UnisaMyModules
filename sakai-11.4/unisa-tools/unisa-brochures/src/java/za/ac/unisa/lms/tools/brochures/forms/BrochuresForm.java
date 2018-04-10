package za.ac.unisa.lms.tools.brochures.forms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class BrochuresForm extends ActionForm {
	
	private String collegeCode;
	private String school;
	private String year;
	private String category;
	private String format;
	private String type;
	private String type1;
	private String userId;
	private List exportList;
	private String qualCode;
	private String spec;
	private String specRepeat;
	private String heqf;
	private String repeatYear;
	private ArrayList fromYearList;
	private String auditType;
	private String schCode;
	private String dptCode;
	private String module;
	private String subCode;
	private String regLevel;
	
	private String researchFlag;
	private String nqfLevel;
	
	public String getNqfLevel() {
		return nqfLevel;
	}
	public void setNqfLevel(String nqfLevel) {
		this.nqfLevel = nqfLevel;
	}
	public String getResearchFlag() {
		return researchFlag;
	}
	public void setResearchFlag(String researchFlag) {
		this.researchFlag = researchFlag;
	}
	public String getRegLevel() {
		return regLevel;
	}
	public void setRegLevel(String regLevel) {
		this.regLevel = regLevel;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getSchCode() {
		return schCode;
	}
	public void setSchCode(String schCode) {
		this.schCode = schCode;
	}
	public String getDptCode() {
		return dptCode;
	}
	public void setDptCode(String dptCode) {
		this.dptCode = dptCode;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getRepeatYear() {
		return repeatYear;
	}
	public void setRepeatYear(String repeatYear) {
		this.repeatYear = repeatYear;
	}
	public ArrayList getFromYearList() {
		
		fromYearList = new ArrayList();
		 Calendar cal=Calendar.getInstance();
		 int max=cal.get(Calendar.YEAR)+2;
		 int min=cal.get(Calendar.YEAR)-3;
		for (int i=min; i <= max; i++) {
			fromYearList.add(new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
		}
		
	
		return fromYearList;
	}
	public void setFromYearList(ArrayList fromYearList) {
		this.fromYearList = fromYearList;
	}
	public String getSpecRepeat() {
		return specRepeat;
	}
	public void setSpecRepeat(String specRepeat) {
		this.specRepeat = specRepeat;
	}
	public String getHeqf() {
		return heqf;
	}
	public void setHeqf(String heqf) {
		this.heqf = heqf;
	}
	public String getQualCode() {
		return qualCode;
	}
	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	public List getExportList() {
		return exportList;
	}
	public void setExportList(List exportList) {
		this.exportList = exportList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private ArrayList yearsList;
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCollegeCode() {
		return collegeCode;
	}
	public void setCollegeCode(String collegeCode) {
		this.collegeCode = collegeCode;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public ArrayList getYearsList() {
		yearsList = new ArrayList();
		 Calendar cal=Calendar.getInstance();
		 int max=cal.get(Calendar.YEAR)+2;
		 int min=cal.get(Calendar.YEAR)-3;
		for (int i=min; i <= max; i++) {
			yearsList.add(new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
		}
		
		return yearsList;
	}
	public void setYearsList(ArrayList yearsList) {
		this.yearsList = yearsList;
	}

}
