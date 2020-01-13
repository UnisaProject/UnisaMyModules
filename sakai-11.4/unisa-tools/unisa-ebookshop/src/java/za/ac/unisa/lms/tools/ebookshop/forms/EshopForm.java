//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.ebookshop.forms;

import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.ebookshop.dao.Advert;
import za.ac.unisa.lms.tools.ebookshop.dao.Student;

public class EshopForm extends ValidatorForm {
	private static final long serialVersionUID = 1L;

	private Advert advert = new Advert();
	private Student student = new Student();
	private String buttonClicked;
	private String editButton;
	private String errorMessage;
	//testing the bookId method
	private String bookID;
	private String orderBy="CATCHPHRASE";

	public Advert getAdvert() {
		return advert;
	}

	public void setAdvert(Advert advert) {
		this.advert = advert;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getButtonClicked() {
		return buttonClicked;
	}

	public void setButtonClicked(String buttonClicked) {
		this.buttonClicked = buttonClicked;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getEditButton() {
		return editButton;
	}

	public void setEditButton(String editButton) {
		this.editButton = editButton;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}



