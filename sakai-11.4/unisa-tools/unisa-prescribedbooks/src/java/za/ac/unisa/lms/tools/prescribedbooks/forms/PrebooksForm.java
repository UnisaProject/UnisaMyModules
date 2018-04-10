package za.ac.unisa.lms.tools.prescribedbooks.forms;

import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.prescribedbooks.dao.PrebooksDetails;

/**
* MyEclipse Struts
* Creation date: 30-01-2007
* By PJ Ngoepe
* XDoclet definition:
* @struts:form name="prebooksForm"
*/
public class PrebooksForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;

	private  PrebooksDetails prebooksDetails;
	private List prebooksList,recommList,ereserveList;
	private String courseCode;
	private int year;
	private String confirm,confirmRecommendedBook,confirmEreserveBook;
    public void setYear(int year){
    	     this.year=year;
    }
    public int getYear(){
    	return year;
    }
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public PrebooksForm() {
		super();
		prebooksDetails = new PrebooksDetails();
	}

	public PrebooksDetails getPrebooksDetails() {
		return prebooksDetails;
	}

	public void setPrebooksDetails(PrebooksDetails prebooksDetails) {
		this.prebooksDetails = prebooksDetails;
	}

	public List getPrebooksList() {
		return prebooksList;
	}

	public void setPrebooksList(List prebooksList) {
		this.prebooksList = prebooksList;
	}
	public void setRecommList(List recommList) {
		this.recommList = recommList;
	}
	public List getRecommList() {
		return recommList;
	}
	public void setEreserveList(List ereserveList) {
		  this.ereserveList=ereserveList;
	}
	public List getEreserveList() {
		     return ereserveList;
	}
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getConfirmRecommendedBook() {
		return confirmRecommendedBook;
	}

	public void setConfirmRecommendedBook(String confirm) {
		this.confirmRecommendedBook= confirm;
	}

	public String getConfirmEreserveBook() {
		return confirmEreserveBook;
	}

	public void setConfirmEreserveBook(String confirm) {
		this.confirmEreserveBook = confirm;
	}

}

