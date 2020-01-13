package za.ac.unisa.lms.tools.satbook.forms;

import org.apache.struts.validator.ValidatorForm;

public class AssistantRecord extends ValidatorForm {

	String assistantId;
	String assistantTypeId;
	String assistantTypeDesc;
	String assistantName;
	String assistantEmail;

	/**
	 * Returns the assistantId.
	 * @return String
	 */
	public String getAssistantId() {
		return assistantId;
	}

	/**
	 * Set the assistantId.
	 * @param assistantId The assistantId to set
	 */
	public void setAssistantId(String assistantId) {
		this.assistantId = assistantId;
	}

	/**
	 * Returns the assistantTypeId.
	 * @return String
	 */
	public String getAssistantTypeId() {
		return assistantTypeId;
	}

	/**
	 * Set the assistantTypeId.
	 * @param assistantType The assistantTypeId to set
	 */
	public void setAssistantTypeId(String assistantTypeId) {
		this.assistantTypeId = assistantTypeId;
	}

	/**
	 * Returns the assistantName.
	 * @return String
	 */
	public String getAssistantName() {
		return assistantName;
	}

	/**
	 * Set the assistantName.
	 * @param assistantName The assistantName to set
	 */
	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	/**
	 * Returns the assistantEmail.
	 * @return String
	 */
	public String getAssistantEmail() {
		return assistantEmail;
	}

	/**
	 * Set the assistantEmail.
	 * @param assistantEmail The assistantEmail to set
	 */
	public void setAssistantEmail(String assistantEmail) {
		this.assistantEmail = assistantEmail;
	}

	/**
	 * Returns the assistantTypeDesc.
	 * @return String
	 */
	public String getAssistantTypeDesc() {
		return assistantTypeDesc;
	}

	/**
	 * Set the assistantTypeDesc.
	 * @param assistantTypeDesc The assistantTypeDesc to set
	 */
	public void setAssistantTypeDesc(String assistantTypeDesc) {
		this.assistantTypeDesc = assistantTypeDesc;
	}
}
