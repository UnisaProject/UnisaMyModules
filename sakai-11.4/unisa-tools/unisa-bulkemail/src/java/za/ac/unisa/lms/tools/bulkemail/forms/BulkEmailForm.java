//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.bulkemail.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * MyEclipse Struts
 * Creation date: 12-21-2005
 *
 * XDoclet definition:
 * @struts:form name="studentEmailForm"
 */
public class BulkEmailForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	//The selection criteria
	private String confineGender;
	private String confineHomeLanguage;
	private String confineCountry;
	private String confineProvince;
	private String confineRace;
	private String confineStudentsregion;
	private String confineStudentsResidentialregion;
	private String confineDistrict;

	private String button;

	private String userName;
	private ArrayList sites;
	private String[] indexNrOfSelectedSite;

	//variables relevant to the email
	private String messageSubject;
	private String messageText;
	private String reply="dont";
	private String replyAddress="";
	private String from;
	private String sendFrom;
	private String date;
	private String to;
	private String parameters;
	private ArrayList mysites;

	private boolean studentExists;
	private boolean noSitesExists;

	private static final long serialVersionUID = 1L;
	// --------------------------------------------------------- Methods

	/**
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getConfineCountry() {
		return confineCountry;
	}

	public void setConfineCountry(String confineCountry) {
		this.confineCountry = confineCountry;
	}

	public String getConfineDistrict() {
		return confineDistrict;
	}

	public void setConfineDistrict(String confineDistrict) {
		this.confineDistrict = confineDistrict;
	}

	public String getConfineGender() {
		return confineGender;
	}

	public void setConfineGender(String confineGender) {
		this.confineGender = confineGender;
	}

	public String getConfineHomeLanguage() {
		return confineHomeLanguage;
	}

	public void setConfineHomeLanguage(String confineHomeLanguage) {
		this.confineHomeLanguage = confineHomeLanguage;
	}

	public String getConfineProvince() {
		return confineProvince;
	}

	public void setConfineProvince(String confineProvince) {
		this.confineProvince = confineProvince;
	}

	public String getConfineRace() {
		return confineRace;
	}

	public void setConfineRace(String confineRace) {
		this.confineRace = confineRace;
	}

	public String getConfineStudentsregion() {
		return confineStudentsregion;
	}

	public void setConfineStudentsregion(String confineStudentsregion) {
		this.confineStudentsregion = confineStudentsregion;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList getSites() {
		return sites;
	}

	public void setSites(ArrayList sites) {
		this.sites = sites;
	}

	public String[] getIndexNrOfSelectedSite() {
		return indexNrOfSelectedSite;
	}

	public void setIndexNrOfSelectedSite(String[] indexNrOfSelectedSite) {
		this.indexNrOfSelectedSite = indexNrOfSelectedSite;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public boolean isStudentExists() {
		return studentExists;
	}

	public void setStudentExists(boolean studentExists) {
		this.studentExists = studentExists;
	}

	public boolean isNoSitesExists() {
		return noSitesExists;
	}

	public void setNoSitesExists(boolean noSitesExists) {
		this.noSitesExists = noSitesExists;
	}

	public String getConfineStudentsResidentialregion() {
		return confineStudentsResidentialregion;
	}

	public void setConfineStudentsResidentialregion(
			String confineStudentsResidentialregion) {
		this.confineStudentsResidentialregion = confineStudentsResidentialregion;
	}



	public String getReplyAddress() {
		return replyAddress;
	}

	public void setReplyAddress(String replyAddress) {
		this.replyAddress = replyAddress;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public ArrayList getMysites() {
		return mysites;
	}

	public void setMysites(ArrayList mysites) {
		this.mysites = mysites;
	}



}

