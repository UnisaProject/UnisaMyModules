//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentemail.forms;


import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.tools.studentemail.Student;

/** 
 * MyEclipse Struts
 * Creation date: 12-27-2005
 * 
 * XDoclet definition:
 * @struts:form name="displayemailForm"
 */
public class DisplayemailForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Student student;
	private boolean studentUser;
	private String course;
	private String emailaddres;
	private String choice;
	private String subject;
	private String message;
	private boolean noemail;
	/**
	 * @return Returns the course.
	 */
	public String getCourse() {
		return course;
	}
	/**
	 * @param course The course to set.
	 */
	public void setCourse(String course) {
		this.course = course;
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
	 * @return Returns the emailaddres.
	 */
	public String getEmailaddres() {
		return emailaddres;
	}
	/**
	 * @param emailaddres The emailaddres to set.
	 */
	public void setEmailaddres(String emailaddres) {
		this.emailaddres = emailaddres;
	}
	/**
	 * @return Returns the choice.
	 */
	public String getChoice() {
		return choice;
	}
	/**
	 * @param choice The choice to set.
	 */
	public void setChoice(String choice) {
		this.choice = choice;
	}
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return Returns the noemail.
	 */
	public boolean isNoemail() {
		return noemail;
	}
	/**
	 * @param noemail The noemail to set.
	 */
	public void setNoemail(boolean noemail) {
		this.noemail = noemail;
	}
	

}

