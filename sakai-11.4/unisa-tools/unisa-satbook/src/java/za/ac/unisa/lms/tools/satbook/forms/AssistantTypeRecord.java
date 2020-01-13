package za.ac.unisa.lms.tools.satbook.forms;

import java.util.ArrayList;

import org.apache.struts.validator.ValidatorForm;

public class AssistantTypeRecord extends ValidatorForm {

	// code structure variables
	private String assTypeId;
	private String assTypeName;
	private boolean assTypeAct;

	// booking variables (name linked to a type
	private String selectedAssistant;
	private String selectedAssName;
    private ArrayList assistantsList;

	/**
	 * Returns the assTypeId.
	 * @return String
	 */
	public String getAssTypeId() {
		return assTypeId;
	}

	/**
	 * Set the assTypeId.
	 * @param assTypeId The assTypeId to set
	 */
	public void setAssTypeId(String assTypeId) {
		this.assTypeId = assTypeId;
	}

	/**
	 * Returns the assTypeName.
	 * @return String
	 */
	public String getAssTypeName() {
		return assTypeName;
	}

	/**
	 * Set the assTypeName.
	 * @param assTypeName The assTypeName to set
	 */
	public void setAssTypeName(String assTypeName) {
		this.assTypeName = assTypeName;
	}

	/**
	 * Returns the assTypeAct.
	 * @return boolean
	 */
	public boolean getAssTypeAct() {
		return assTypeAct;
	}

	/**
	 * Set the assTypeAct.
	 * @param assTypeAct The assTypeAct to set
	 */
	public void setAssTypeAct(boolean assTypeAct) {
		this.assTypeAct = assTypeAct;
	}

	public String getSelectedAssistant() {
		return selectedAssistant;
	}

	public void setSelectedAssistant(String selectedAssistant) {
		this.selectedAssistant = selectedAssistant;
	}

	public String getSelectedAssName() {
		return selectedAssName;
	}

	public void setSelectedAssName(String selectedAssName) {
		this.selectedAssName = selectedAssName;
	}

	public ArrayList getAssistantsList() {
		return assistantsList;
	}

	public void setAssistantsList(ArrayList assistantsList) {
		this.assistantsList = assistantsList;
	}

}
