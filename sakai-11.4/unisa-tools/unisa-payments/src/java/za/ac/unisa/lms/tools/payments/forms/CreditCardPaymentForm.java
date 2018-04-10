//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.payments.forms;

import java.util.Vector;
import org.apache.struts.validator.ValidatorForm;

/** 
 * MyEclipse Struts
 * Creation date: 09-16-2005
 * 
 * XDoclet definition:
 * @struts:form name="CreditCardPaymentForm"
 */
public class CreditCardPaymentForm extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** matricExempFees property */
	private double matricExempFees = 0.00;

	/** title property */
	private String title;

	/** cell property */
	private String cell;

	/** studyFees property */
	private double studyFees = 0.00;

	/** homePhone property */
	private String homePhone;

	/** cardholderSurname property */
	private String cardholderSurname;

	/** birthDate property */
	private String birthDate;

	/** cvNo property */
	private String cvNo;

	/** expiryDate property */
	private String expiryDate;

	/** expiryMonth property */
	private String expiryMonth;

	/** expiryYear property */
	private String expiryYear;

	/** total property */
	private double total;

	/** surname property */
	private String surname;

	/** stno property */
	private String stno;

	/** firstNames property */
	private String firstNames;

	/** postalAddress property */
	private String postalAddress;

	/** emailAddress property */
	private String emailAddress;
	private String prevEmailAddress;

	/** initials property */
	private String initials;

	/** budgetOption property */
	private String budgetOption;

	/** creditCardNumber property */
	private String creditCardNumber;
	/** 4 parts */
	private String creditCardNumber1;
	private String creditCardNumber2;
	private String creditCardNumber3;
	private String creditCardNumber4;

	/** degree property */
	private String degree;

	/** cardHolderId property */
	private String cardHolderId;

	/** confCreditCard property */
	private String confCreditCard;
	/** 4 parts */
	private String confCreditCard1;
	private String confCreditCard2;
	private String confCreditCard3;
	private String confCreditCard4;	

	/** confCVno property */
	private String confCVno;
	
	/** postal address property */
	private Vector postal;

	/** postalCode property */
	private String postalCode;
	// --------------------------------------------------------- Methods

	/** 
	 * Returns the matricExempFees.
	 * @return String
	 */
	public double getMatricExempFees() {
		return matricExempFees;
	}

	/** 
	 * Set the matricExempFees.
	 * @param matricExempFees The matricExempFees to set
	 */
	public void setMatricExempFees(double matricExempFees) {
		this.matricExempFees = matricExempFees;
	}

	/** 
	 * Returns the title.
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/** 
	 * Set the title.
	 * @param title The title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 
	 * Returns the cell.
	 * @return String
	 */
	public String getCell() {
		return cell;
	}

	/** 
	 * Set the cell.
	 * @param cell The cell to set
	 */
	public void setCell(String cell) {
		this.cell = cell;
	}

	/** 
	 * Returns the studyFees.
	 * @return String
	 */
	public double getStudyFees() {
		return studyFees;
	}

	/** 
	 * Set the studyFees.
	 * @param studyFees The studyFees to set
	 */
	public void setStudyFees(double studyFees) {
		this.studyFees = studyFees;
	}

	/** 
	 * Returns the homePhone.
	 * @return String
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/** 
	 * Set the homePhone.
	 * @param homePhone The homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/** 
	 * Returns the cardholderSurname.
	 * @return String
	 */
	public String getCardholderSurname() {
		return cardholderSurname;
	}

	/** 
	 * Set the cardholderSurname.
	 * @param cardholderSurname The cardholderSurname to set
	 */
	public void setCardholderSurname(String cardholderSurname) {
		this.cardholderSurname = cardholderSurname;
	}

	/** 
	 * Returns the birthDate.
	 * @return String
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/** 
	 * Set the birthDate.
	 * @param birthDate The birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/** 
	 * Returns the cvNo.
	 * @return String
	 */
	public String getCvNo() {
		return cvNo;
	}

	/** 
	 * Set the cvNo.
	 * @param cvNo The cvNo to set
	 */
	public void setCvNo(String cvNo) {
		this.cvNo = cvNo;
	}

	/** 
	 * Returns the expiryDate.
	 * @return String
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/** 
	 * Set the expiryDate.
	 * @param expiryDate The expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/** 
	 * Returns the expiryMonth.
	 * @return String
	 */
	public String getExpiryMonth() {
		return expiryMonth;
	}

	/** 
	 * Set the expiryMonth.
	 * @param expiryMonth The expiryMonth to set
	 */
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}	
	
	/** 
	 * Set the expiryYear.
	 * @param expiryYear The expiryYear to set
	 */
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	/** 
	 * Returns the expiryYear.
	 * @return String
	 */
	public String getExpiryYear() {
		return expiryYear;
	}



	/** 
	 * Returns the total.
	 * @return String
	 */
	public double getTotal() {
		return total;
	}

	/** 
	 * Set the total.
	 * @param total The total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/** 
	 * Returns the surname.
	 * @return String
	 */
	public String getSurname() {
		return surname;
	}

	/** 
	 * Set the surname.
	 * @param surname The surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/** 
	 * Returns the stno.
	 * @return String
	 */
	public String getStno() {
		return stno;
	}

	/** 
	 * Set the stno.
	 * @param stno The stno to set
	 */
	public void setStno(String stno) {
		this.stno = stno;
	}

	/** 
	 * Returns the firstNames.
	 * @return String
	 */
	public String getFirstNames() {
		return firstNames;
	}

	/** 
	 * Set the firstNames.
	 * @param firstNames The firstNames to set
	 */
	public void setFirstNames(String firstNames) {
		this.firstNames = firstNames;
	}

	/** 
	 * Returns the postalAddress.
	 * @return String
	 */
	public String getPostalAddress() {
		return postalAddress;
	}

	/** 
	 * Set the postalAddress.
	 * @param postalAddress The postalAddress to set
	 */
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	/** 
	 * Returns the emailAddress.
	 * @return String
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/** 
	 * Set the emailAddress.
	 * @param emailAddress The emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/** 
	 * Returns the prevEmailAddress.
	 * @return String
	 */
	public String getPrevEmailAddress() {
		return prevEmailAddress;
	}

	/** 
	 * Set the prevEmailAddress.
	 * @param prevEmailAddress The prevEmailAddress to set
	 */
	public void setPrevEmailAddress(String prevEmailAddress) {
		this.prevEmailAddress = prevEmailAddress;
	}
	
	/** 
	 * Returns the initials.
	 * @return String
	 */
	public String getInitials() {
		return initials;
	}

	/** 
	 * Set the initials.
	 * @param initials The initials to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	/** 
	 * Returns the budgetOption.
	 * @return String
	 */
	public String getBudgetOption() {
		return budgetOption;
	}

	/** 
	 * Set the budgetOption.
	 * @param budgetOption The budgetOption to set
	 */
	public void setBudgetOption(String budgetOption) {
		this.budgetOption = budgetOption;
	}

	/** 
	 * Returns the creditCardNumber.
	 * @return String
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/** 
	 * Set the creditCardNumber.
	 * @param creditCardNumber The creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/** 
	 * Returns the creditCardNumber.
	 * @return String
	 */
	public String getCreditCardNumber1() {
		return creditCardNumber1;
	}

	/** 
	 * Set the creditCardNumber.
	 * @param creditCardNumber The creditCardNumber to set
	 */
	public void setCreditCardNumber1(String creditCardNumber1) {
		this.creditCardNumber1 = creditCardNumber1;
	}
	
	/** 
	 * Returns the creditCardNumber.
	 * @return String
	 */
	public String getCreditCardNumber2() {
		return creditCardNumber2;
	}

	/** 
	 * Set the creditCardNumber2.
	 * @param creditCardNumber2 The creditCardNumber2 to set
	 */
	public void setCreditCardNumber2(String creditCardNumber2) {
		this.creditCardNumber2 = creditCardNumber2;
	}
	
	/** 
	 * Returns the creditCardNumber.
	 * @return String
	 */
	public String getCreditCardNumber3() {
		return creditCardNumber3;
	}

	/** 
	 * Set the creditCardNumber3.
	 * @param creditCardNumber3 The creditCardNumber3 to set
	 */
	public void setCreditCardNumber3(String creditCardNumber3) {
		this.creditCardNumber3 = creditCardNumber3;
	}
	
	/** 
	 * Returns the creditCardNumber4.
	 * @return String
	 */
	public String getCreditCardNumber4() {
		return creditCardNumber4;
	}

	/** 
	 * Set the creditCardNumber4.
	 * @param creditCardNumber4 The creditCardNumber4 to set
	 */
	public void setCreditCardNumber4(String creditCardNumber4) {
		this.creditCardNumber4 = creditCardNumber4;
	}	

	/** 
	 * Returns the degree.
	 * @return String
	 */
	public String getDegree() {
		return degree;
	}

	/** 
	 * Set the degree.
	 * @param degree The degree to set
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}

	/** 
	 * Returns the cardHolderId.
	 * @return String
	 */
	public String getCardHolderId() {
		return cardHolderId;
	}

	/** 
	 * Set the cardHolderId.
	 * @param cardHolderId The cardHolderId to set
	 */
	public void setCardHolderId(String cardHolderId) {
		this.cardHolderId = cardHolderId;
	}

	/** 
	 * Returns the confCreditCard.
	 * @return String
	 */
	public String getConfCreditCard() {
		return confCreditCard;
	}

	/** 
	 * Set the confCreditCard.
	 * @param confCreditCard The confCreditCard to set
	 */
	public void setConfCreditCard(String confCreditCard) {
		this.confCreditCard = confCreditCard;
	}

	/** 
	 * Returns the confCreditCard1.
	 * @return String
	 */
	public String getConfCreditCard1() {
		return confCreditCard1;
	}

	/** 
	 * Set the confCreditCard1.
	 * @param confCreditCard The confCreditCard to set
	 */
	public void setConfCreditCard1(String confCreditCard1) {
		this.confCreditCard1 = confCreditCard1;
	}

	/** 
	 * Returns the confCreditCard2.
	 * @return String
	 */
	public String getConfCreditCard2() {
		return confCreditCard2;
	}

	/** 
	 * Set the confCreditCard2.
	 * @param confCreditCard The confCreditCard to set
	 */
	public void setConfCreditCard2(String confCreditCard2) {
		this.confCreditCard2 = confCreditCard2;
	}	
	
	/** 
	 * Returns the confCreditCard3.
	 * @return String
	 */
	public String getConfCreditCard3() {
		return confCreditCard3;
	}

	/** 
	 * Set the confCreditCard3.
	 * @param confCreditCard The confCreditCard to set
	 */
	public void setConfCreditCard3(String confCreditCard3) {
		this.confCreditCard3 = confCreditCard3;
	}	

	/** 
	 * Returns the confCreditCard4.
	 * @return String
	 */
	public String getConfCreditCard4() {
		return confCreditCard4;
	}

	/** 
	 * Set the confCreditCard4.
	 * @param confCreditCard The confCreditCard to set
	 */
	public void setConfCreditCard4(String confCreditCard4) {
		this.confCreditCard4 = confCreditCard4;
	}	
	
	/** 
	 * Returns the confCVno.
	 * @return String
	 */
	public String getConfCVno() {
		return confCVno;
	}

	/** 
	 * Set the confCVno.
	 * @param confCVno The confCVno to set
	 */
	public void setConfCVno(String confCVno) {
		this.confCVno = confCVno;
	}
	
	public Vector getPostal() {
		return postal;
	}

	public void setPostal(Vector postal) {
		this.postal = postal;
	}
	
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}

