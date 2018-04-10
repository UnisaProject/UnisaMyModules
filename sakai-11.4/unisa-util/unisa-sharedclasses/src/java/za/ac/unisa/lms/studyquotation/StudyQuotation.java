//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.studyquotation;

import java.util.Calendar;
import java.util.Vector;

public class StudyQuotation {

	private static final long serialVersionUID = 1L;

	/** registrationFee property */
	private String registrationFee;

	/** libraryCard property */
	private String libraryCard;
	
	/** libraryCard property */
	private double libraryCardCost;
	
	/** matricExemption */
	private String matricExemption;
	
	/** matricExemption */
	private double matricExemptionCost;
	
	/** prescribedBooks property */
	private double prescribedBooks;

	/** year property */
	private short academicYear;

	/** qualificationCode property */
	private String qualificationCode;

	/** foreignLevy property */
	private double foreignLevy;

	/** countryCode property */
	private String countryCode;

	/** totalFee property */
	private double totalFee;
	
	/** paymentDue property */
	private double paymentDue;
	
	/** qualification property */
	private String qualification;
		
	/** studyUnits */
	private Vector studyUnits;
	
	/** studyCodes */
	
	private String studyCode1;
	private String studyCode2;
	private String studyCode3;
	private String studyCode4;
	private String studyCode5;
	private String studyCode6;
	private String studyCode7;
	private String studyCode8;
	private String studyCode9;
	private String studyCode10;
	private String studyCode11;
	private String studyCode12;
	private String studyCode13;
	private String studyCode14;
	private String studyCode15;
	private String studyCode16;
	private String studyCode17;
	private String studyCode18;
	
	public void reset() {
		//int academicYear = new Integer(new SimpleDateFormat("yyyy").format(new Date())).intValue();
		//this.setAcademicYear(new Integer(academicYear+1).shortValue());
		if (Calendar.getInstance().get(Calendar.MONTH) < 11) {
			academicYear = (short) Calendar.getInstance().get(Calendar.YEAR);
		} else {
			/*Removed for test purposes +1*/
			academicYear = (short) (Calendar.getInstance().get(Calendar.YEAR) + 1 );
		}
		this.setCountryCode("1015");
		this.setQualification("99999");
		this.setLibraryCard("N");		
		this.setMatricExemption("N");
	}
	
	/** 
	 * Returns the registrationFee.
	 * @return String
	 */
	public String getRegistrationFee() {
		return registrationFee;
	}

	/** 
	 * Set the registrationFee.
	 * @param registrationFee The registrationFee to set
	 */
	public void setRegistrationFee(String registrationFee) {
		this.registrationFee = registrationFee;
	}

	/** 
	 * Returns the prescribedBooks.
	 * @return String
	 */
	public double getPrescribedBooks() {
		return prescribedBooks;
	}

	/** 
	 * Set the prescribedBooks.
	 * @param prescribedBooks The prescribedBooks to set
	 */
	public void setPrescribedBooks(double prescribedBooks) {
		this.prescribedBooks = prescribedBooks;
	}
	/** 
	 * Returns the qualificationCode.
	 * @return String
	 */
	public String getQualificationCode() {
		return qualificationCode;
	}

	/** 
	 * Set the qualificationCode.
	 * @param qualificationCode The qualificationCode to set
	 */
	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	/** 
	 * Returns the foreignLevy.
	 * @return String
	 */
	public double getForeignLevy() {
		return foreignLevy;
	}

	/** 
	 * Set the foreignLevy.
	 * @param d The foreignLevy to set
	 */
	public void setForeignLevy(double d) {
		this.foreignLevy = d;
	}

	/** 
	 * Returns the countryCode.
	 * @return String
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/** 
	 * Set the countryCode.
	 * @param countryCode The countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/** 
	 * Returns the totalFee.
	 * @return String
	 */
	public double getTotalFee() {
		return totalFee;
	}

	/** 
	 * Set the totalFee.
	 * @param totalFee The totalFee to set
	 */
	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public Vector getStudyUnits() {
		return studyUnits;
	}

	public void setStudyUnits(Vector studyUnits) {
		this.studyUnits = studyUnits;
	}

	public String getMatricExemption() {
		return matricExemption;
	}

	public void setMatricExemption(String matricExemption) {
		this.matricExemption = matricExemption;
	}

	public short getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(short academicYear) {
		this.academicYear = academicYear;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getStudyCode1() {
		return studyCode1;
	}

	public void setStudyCode1(String studyCode1) {
		this.studyCode1 = studyCode1;
	}

	public String getStudyCode10() {
		return studyCode10;
	}

	public void setStudyCode10(String studyCode10) {
		this.studyCode10 = studyCode10;
	}

	public String getStudyCode11() {
		return studyCode11;
	}

	public void setStudyCode11(String studyCode11) {
		this.studyCode11 = studyCode11;
	}

	public String getStudyCode12() {
		return studyCode12;
	}

	public void setStudyCode12(String studyCode12) {
		this.studyCode12 = studyCode12;
	}

	public String getStudyCode13() {
		return studyCode13;
	}

	public void setStudyCode13(String studyCode13) {
		this.studyCode13 = studyCode13;
	}

	public String getStudyCode14() {
		return studyCode14;
	}

	public void setStudyCode14(String studyCode14) {
		this.studyCode14 = studyCode14;
	}

	public String getStudyCode15() {
		return studyCode15;
	}

	public void setStudyCode15(String studyCode15) {
		this.studyCode15 = studyCode15;
	}

	public String getStudyCode16() {
		return studyCode16;
	}

	public void setStudyCode16(String studyCode16) {
		this.studyCode16 = studyCode16;
	}

	public String getStudyCode17() {
		return studyCode17;
	}

	public void setStudyCode17(String studyCode17) {
		this.studyCode17 = studyCode17;
	}
	
	public String getStudyCode18() {
		return studyCode18;
	}

	public void setStudyCode18(String studyCode18) {
		this.studyCode18 = studyCode18;
	}
	
	
	public String getStudyCode2() {
		return studyCode2;
	}

	public void setStudyCode2(String studyCode2) {
		this.studyCode2 = studyCode2;
	}

	public String getStudyCode3() {
		return studyCode3;
	}

	public void setStudyCode3(String studyCode3) {
		this.studyCode3 = studyCode3;
	}

	public String getStudyCode4() {
		return studyCode4;
	}

	public void setStudyCode4(String studyCode4) {
		this.studyCode4 = studyCode4;
	}

	public String getStudyCode5() {
		return studyCode5;
	}

	public void setStudyCode5(String studyCode5) {
		this.studyCode5 = studyCode5;
	}

	public String getStudyCode6() {
		return studyCode6;
	}

	public void setStudyCode6(String studyCode6) {
		this.studyCode6 = studyCode6;
	}

	public String getStudyCode7() {
		return studyCode7;
	}

	public void setStudyCode7(String studyCode7) {
		this.studyCode7 = studyCode7;
	}

	public String getStudyCode8() {
		return studyCode8;
	}

	public void setStudyCode8(String studyCode8) {
		this.studyCode8 = studyCode8;
	}

	public String getStudyCode9() {
		return studyCode9;
	}

	public void setStudyCode9(String studyCode9) {
		this.studyCode9 = studyCode9;
	}

	public double getLibraryCardCost() {
		return libraryCardCost;
	}

	public void setLibraryCardCost(double libraryCardCost) {
		this.libraryCardCost = libraryCardCost;
	}

	public String getLibraryCard() {
		return libraryCard;
	}

	public void setLibraryCard(String libraryCard) {
		this.libraryCard = libraryCard;
	}

	public double getMatricExemptionCost() {
		return matricExemptionCost;
	}

	public void setMatricExemptionCost(double matricExemptionCost) {
		this.matricExemptionCost = matricExemptionCost;
	}

	public double getPaymentDue() {
		return paymentDue;
	}

	public void setPaymentDue(double paymentDue) {
		this.paymentDue = paymentDue;
	}
}