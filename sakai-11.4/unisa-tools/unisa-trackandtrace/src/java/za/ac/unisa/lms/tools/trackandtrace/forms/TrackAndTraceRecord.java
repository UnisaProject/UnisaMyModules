//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.trackandtrace.forms;

import org.apache.struts.validator.ValidatorForm;

/** 
 * MyEclipse Struts
 * Creation date: 11-13-2005
 * 
 * XDoclet definition:
 * @struts:form name="trackAndTraceRecord"
 */
public class TrackAndTraceRecord extends ValidatorForm {

	// --------------------------------------------------------- Instance Variables

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** trackTraceDate property */
	private String trackTraceDate;
	
	private String trackTraceAgent;

	/** trackTraceNumber property */
	private String trackTraceNumber;

	// --------------------------------------------------------- Methods

	/** 
	 * Returns the trackTraceDate.
	 * @return String
	 */
	public String getTrackTraceDate() {
		return trackTraceDate;
	}

	/** 
	 * Set the trackTraceDate.
	 * @param trackTraceDate The trackTraceDate to set
	 */
	public void setTrackTraceDate(String trackTraceDate) {
		this.trackTraceDate = trackTraceDate;
	}

	/** 
	 * Returns the trackTraceNumber.
	 * @return String
	 */
	public String getTrackTraceNumber() {
		return trackTraceNumber;
	}

	/** 
	 * Set the trackTraceNumber.
	 * @param trackTraceNumber The trackTraceNumber to set
	 */
	public void setTrackTraceNumber(String trackTraceNumber) {
		this.trackTraceNumber = trackTraceNumber;
	}

	public String getTrackTraceAgent() {
		return trackTraceAgent;
	}

	public void setTrackTraceAgent(String trackTraceAgent) {
		this.trackTraceAgent = trackTraceAgent;
	}

}

