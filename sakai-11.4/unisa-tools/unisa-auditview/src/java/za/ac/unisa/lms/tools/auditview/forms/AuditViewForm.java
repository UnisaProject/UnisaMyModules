//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.auditview.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * MyEclipse Struts
 * Creation date: 01-06-2006
 *
 * XDoclet definition:
 * @struts:form name="auditViewForm"
 */
public class AuditViewForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables


	private static final long serialVersionUID = 1L;

	/** userId property */
	private String userId;

	private String oldUserId;

	private int page;

	private boolean hasNext;

	

	private String pageButton;
    private String current   = "Current week";
    //private String history= "History";
	//private String recent    ="18 May 2007-09 Jul 2007 ";
    private String archived  ="History";
    private String oldevent = "Current week";
    private String prevYear   = "Previous Year";
    private String prevYearLessOne   = "Previous Year Less one";



    private String eventset= "";
	/** eventList property */
	private List eventList;
	private List subeventList;
	private int start;
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

	/**
	 * Returns the studentNr.
	 * @return String
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Set the studentNr.
	 * @param studentNr The studentNr to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List getEventList() {
		return this.eventList;
	}

	public void setEventList(List eventList) {
		this.eventList = eventList;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}


	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public String getPageButton() {
		return pageButton;
	}

	public void setPageButton(String pageButton) {
		this.pageButton = pageButton;
	}

	public String getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	public String getEventset() {
		return eventset;
	}

	public void setEventset(String eventset) {
		this.eventset = eventset;
	}

	public String getArchived() {
		return archived;
	}

	public void setArchived(String archived) {
		this.archived = archived;
	}
	/*
	public String getRecent() {
		return recent;
	}

	public void setRecent(String recent) {
		this.recent = recent;
	}

	public String getMostrecent() {
		return mostrecent;
	}

	public void setMostrecent(String mostrecent) {
		this.mostrecent = mostrecent;
	}
*/
	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public List getSubeventList() {
		return subeventList;
	}

	public void setSubeventList(List subeventList) {
		this.subeventList = subeventList;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getOldevent() {
		return oldevent;
	}

	public void setOldevent(String oldevent) {
		this.oldevent = oldevent;
	}

	public String getPrevYear() {
		return prevYear;
	}

	public void setPrevYear(String prevYear) {
		this.prevYear = prevYear;
	}

	public String getPrevYearLessOne() {
		return prevYearLessOne;
	}

	public void setPrevYearLessOne(String prevYearLessOne) {
		this.prevYearLessOne = prevYearLessOne;
	}

}

