//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.welcome.forms;

import org.apache.struts.action.ActionForm;

/**
 * MyEclipse Struts
 * Creation date: 11-16-2005
 *
 * XDoclet definition:
 * @struts:form name="displayWelcomeForm"
 */
public class WelcomeDisplayForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** title property */
	private String title;

	/** content property */
	//private String content;		// Unisa Changes:2018/09/27:Rename variable to eliminate conflict with ckeditor v4.5.7
	private String welcomeContent;	// Unisa Changes:2018/09/27:Rename variable to eliminate conflict with ckeditor v4.5.7
	
	/** siteid property */
	private String siteid;

	/** studentUser */
	private boolean maintainUser;


	// --------------------------------------------------------- Methods


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
	 * Returns the content.
	 * @return String
	 */
	//public String getContent() {			// Unisa Changes:2018/09/27:Rename getter to eliminate conflict with ckeditor v4.5.7 on 'content' variable
	public String getWelcomeContent() {
		//return content;					// Unisa Changes:2018/09/27
		return welcomeContent;				
	}

	/**
	 * Set the content.
	 * @param content The content to set
	 */
	//public void setContent(String content) {				// Unisa Changes:2018/09/27:Rename setter to eliminate conflict with ckeditor v4.5.7 on 'content' variable
	public void setWelcomeContent(String welcomeContent) {	
		//this.content = content;							// Unisa Changes:2018/09/27
		this.welcomeContent = welcomeContent;
	}

	/**
	 * Returns the siteid.
	 * @return String
	 */
	public String getSiteid() {
		return siteid;
	}

	/**
	 * Set the siteid.
	 * @param siteid The siteid to set
	 */
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public boolean isMaintainUser() {
		return maintainUser;
	}

	public void setMaintainUser(boolean maintainUser) {
		this.maintainUser = maintainUser;
	}

}

