//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.examtimetable.forms;

import za.ac.unisa.lms.tools.examtimetable.dao.StudentDAO;

/** 
 * MyEclipse Struts
 * Creation date: 08-18-2005
 * 
 * XDoclet definition:
 */
public class Student {
	
	StudentDAO studentDAO;
	public  Student() {
		     studentDAO=new StudentDAO();
	}

	// --------------------------------------------------------- Instance Variables

	/** studentNumber property */
	private String number;

	/** studentTitle property */
	private String title;

	/** studentName property */
	private String name;

	/** studentInitials property */
	private String initials;

	// --------------------------------------------------------- Methods

	private String emailAddress;
	
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
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void setEmailAddress() throws Exception{
		          setEmailAddress(studentDAO.getStudentEmail(number)); 
	}

}

