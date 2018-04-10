//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.acadhistory.forms;


/** 
 * MyEclipse Struts
 * Creation date: 08-18-2005
 * 
 * XDoclet definition:
 */
public class Student {

	// --------------------------------------------------------- Instance Variables

	/** studentNumber property */
	private String number;

	/** studentTitle property */
	private String title;

	/** studentName property */
	private String name;

	/** studentInitials property */
	private String initials;
	
	private int libraryBlackList;
	private String examFeesDebtFlag;
	private int disciplinaryIncid;
	private int nsfasContractBlock;
	private String financialBlockFlag;
	private String numberRestricted;

	// --------------------------------------------------------- Methods

	
	/** 
	 * Returns the studentNumber.
	 * @return String
	 */
	public String getNumber() {
		return number;
	}

	/** 
	 * Set the number.
	 * @param number The number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/** 
	 * Returns the studentTitle.
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
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/** 
	 * Set the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	public int getLibraryBlackList() {
		return libraryBlackList;
	}

	public void setLibraryBlackList(int libraryBlackList) {
		this.libraryBlackList = libraryBlackList;
	}

	public String getExamFeesDebtFlag() {
		return examFeesDebtFlag;
	}

	public void setExamFeesDebtFlag(String examFeesDebtFlag) {
		this.examFeesDebtFlag = examFeesDebtFlag;
	}

	public int getDisciplinaryIncid() {
		return disciplinaryIncid;
	}

	public void setDisciplinaryIncid(int disciplinaryIncid) {
		this.disciplinaryIncid = disciplinaryIncid;
	}

	public int getNsfasContractBlock() {
		return nsfasContractBlock;
	}

	public void setNsfasContractBlock(int nsfasContractBlock) {
		this.nsfasContractBlock = nsfasContractBlock;
	}

	public String getFinancialBlockFlag() {
		return financialBlockFlag;
	}

	public void setFinancialBlockFlag(String financialBlockFlag) {
		this.financialBlockFlag = financialBlockFlag;
	}

	public String getNumberRestricted() {
		return numberRestricted;
	}

	public void setNumberRestricted(String numberRestricted) {
		this.numberRestricted = numberRestricted;
	}

	
}

