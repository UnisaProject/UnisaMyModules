package za.ac.unisa.lms.tools.publicprescribedbooks.forms;


import java.util.List;

import org.apache.struts.action.ActionForm;


public class PublicPrescribedBooksForm extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	
	private String listDate;
	private String unitCode1;
	private String unitCode2;
	private String unitCode3;
	private String unitCode4;
	private String unitCode5;
	private String unitCode6;
	private String unitCode7;
	private String unitCode8;
	private String unitCode9;
	private String unitCode10;
	private String unitCode11;
	private String unitCode12;

	private String academicYear;
	private List years;
	private List prescribedBooksList;
	
	public String getUnitCode10() {
		return unitCode10;
	}

	public void setUnitCode10(String unitCode10) {
		this.unitCode10 = unitCode10;
	}

	public String getUnitCode11() {
		return unitCode11;
	}

	public void setUnitCode11(String unitCode11) {
		this.unitCode11 = unitCode11;
	}

	public String getUnitCode12() {
		return unitCode12;
	}

	public void setUnitCode12(String unitCode12) {
		this.unitCode12 = unitCode12;
	}

	

	public List getYears() {
		return years;
	}

	public void setYears(List years) {
		this.years = years;
	}

	public String getListDate() {
		return listDate;
	}

	public void setListDate(String listDate) {
		this.listDate = listDate;
	}

	public String getUnitCode1() {
		return unitCode1;
	}

	public void setUnitCode1(String unitCode1) {
		this.unitCode1 = unitCode1;
	}

	public String getUnitCode2() {
		return unitCode2;
	}

	public void setUnitCode2(String unitCode2) {
		this.unitCode2 = unitCode2;
	}

	public String getUnitCode3() {
		return unitCode3;
	}

	public void setUnitCode3(String unitCode3) {
		this.unitCode3 = unitCode3;
	}

	public String getUnitCode4() {
		return unitCode4;
	}

	public void setUnitCode4(String unitCode4) {
		this.unitCode4 = unitCode4;
	}

	public String getUnitCode5() {
		return unitCode5;
	}
	
	public void setUnitCode5(String unitCode5) {
		this.unitCode5 = unitCode5;
	}
	public String getUnitCode6() {
		return unitCode6;
	}

	public void setUnitCode6(String unitCode6) {
		this.unitCode6 = unitCode6;
	}

	public String getUnitCode7() {
		return unitCode7;
	}

	public void setUnitCode7(String unitCode7) {
		this.unitCode7 = unitCode7;
	}

	public String getUnitCode8() {
		return unitCode8;
	}

	public void setUnitCode8(String unitCode8) {
		this.unitCode8 = unitCode8;
	}

	public String getUnitCode9() {
		return unitCode9;
	}

	public void setUnitCode9(String unitCode9) {
		this.unitCode9 = unitCode9;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	public List getPrescribedBooksList() {
		return prescribedBooksList;
	}

	public void setPrescribedBooksList(List prescribedBooksList) {
		this.prescribedBooksList = prescribedBooksList;
	}
	
}
