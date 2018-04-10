//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.payments.forms;

import java.util.Vector;

import org.apache.struts.validator.ValidatorForm;

/** 
 * MyEclipse Struts
 * Creation date: 10-24-2005
 * 
 * XDoclet definition:
 * @struts:form name="displayFinancialDetailsForm"
 */
public class DisplayFinancialDetailsForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Student Property **/
	private Student student;
	
	/** Statement Details **/
	private Vector statementRecords;
	
	/** intructional_text property */
	private String intructional_text;

	/** due_may property */
	private double due_may;

	/** due_imm property */
	private double due_imm;

	/** due_next_march property */
	private double due_next_march;

	/** student_user property */
	private RuntimeException student_user;

	/** due_march property */
	private double due_march;

	/** due_aug property */
	private double due_aug;

	/** account_classifications property */
	private String account_classifications;

	/** due_nov property */
	private double due_nov;
	
	/** Current Year */
	private int year;
	
	/** Next Year */
	private int next_year;
	
	/** Balance */
	private double balance;
	
	/** studentUser */
	private boolean studentUser;
	
	/** inputYear */
	private short inputYear;
	
	

	// --------------------------------------------------------- Methods

	



	/** 
	 * Returns the intructional_text.
	 * @return String
	 */
	public String getIntructional_text() {
		return intructional_text;
	}

	/** 
	 * Set the intructional_text.
	 * @param intructional_text The intructional_text to set
	 */
	public void setIntructional_text(String intructional_text) {
		this.intructional_text = intructional_text;
	}

	/** 
	 * Returns the student_user.
	 * @return RuntimeException
	 */
	public RuntimeException getStudent_user() {
		return student_user;
	}

	/** 
	 * Set the student_user.
	 * @param student_user The student_user to set
	 */
	public void setStudent_user(RuntimeException student_user) {
		this.student_user = student_user;
	}


	/** 
	 * Returns the account_classifications.
	 * @return String
	 */
	public String getAccount_classifications() {
		return account_classifications;
	}

	/** 
	 * Set the account_classifications.
	 * @param account_classifications The account_classifications to set
	 */
	public void setAccount_classifications(String account_classifications) {
		this.account_classifications = account_classifications;
	}


	/**
	 * @return Returns the due_aug.
	 */
	public double getDue_aug() {
		return due_aug;
	}

	/**
	 * @param due_aug The due_aug to set.
	 */
	public void setDue_aug(double due_aug) {
		this.due_aug = due_aug;
	}

	/**
	 * @return Returns the due_imm.
	 */
	public double getDue_imm() {
		return due_imm;
	}

	/**
	 * @param due_imm The due_imm to set.
	 */
	public void setDue_imm(double due_imm) {
		this.due_imm = due_imm;
	}

	/**
	 * @return Returns the due_march.
	 */
	public double getDue_march() {
		return due_march;
	}

	/**
	 * @param due_march The due_march to set.
	 */
	public void setDue_march(double due_march) {
		this.due_march = due_march;
	}

	/**
	 * @return Returns the due_may.
	 */
	public double getDue_may() {
		return due_may;
	}

	/**
	 * @param due_may The due_may to set.
	 */
	public void setDue_may(double due_may) {
		this.due_may = due_may;
	}


	/**
	 * @return Returns the due_nov.
	 */
	public double getDue_nov() {
		return due_nov;
	}

	/**
	 * @param due_nov The due_nov to set.
	 */
	public void setDue_nov(double due_nov) {
		this.due_nov = due_nov;
	}

	/**
	 * @return Returns the statementRecords.
	 */
	public Vector getStatementRecords() {
		return statementRecords;
	}

	/**
	 * @param statementRecords The statementRecords to set.
	 */
	public void setStatementRecords(Vector statementRecords) {
		this.statementRecords = statementRecords;
	}

	/**
	 * @return Returns the student.
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student The student to set.
	 */
	public void setStudent(Student student) {
	   this.student = student;
	}

	/**
	 * @return Returns the studentUser.
	 */
	public boolean isStudentUser() {
		return studentUser;
	}

	/**
	 * @param studentUser The studentUser to set.
	 */
	public void setStudentUser(boolean studentUser) {
		this.studentUser = studentUser;
	}

	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return Returns the next_year.
	 */
	public int getNext_year() {
		return next_year;
	}

	/**
	 * @param next_year The next_year to set.
	 */
	public void setNext_year(int next_year) {
		this.next_year = next_year;
	}

	/**
	 * @return Returns the due_next_march.
	 */
	public double getDue_next_march() {
		return due_next_march;
	}

	/**
	 * @param due_next_march The due_next_march to set.
	 */
	public void setDue_next_march(double due_next_march) {
		this.due_next_march = due_next_march;
	}

	/**
	 * @return Returns the balance.
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance The balance to set.
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return Returns the inputYear.
	 */
	public short getInputYear() {
		return inputYear;
	}

	/**
	 * @param s The inputYear to set.
	 */
	public void setInputYear(short inputYear) {
		this.inputYear = inputYear;
	}

}

