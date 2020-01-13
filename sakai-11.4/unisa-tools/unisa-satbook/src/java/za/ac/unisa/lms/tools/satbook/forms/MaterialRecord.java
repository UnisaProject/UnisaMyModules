package za.ac.unisa.lms.tools.satbook.forms;

import org.apache.struts.validator.ValidatorForm;

public class MaterialRecord extends ValidatorForm{

	private String materialId;
	private String materialDesc;
	private boolean materialAct;
	private String cassette;

	/**
	 * Returns the materialId.
	 * @return String
	 */
	public String getMaterialId() {
		return materialId;
	}

	/**
	 * Set the materialId.
	 * @param materialId The materialId to set
	 */
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	/**
	 * Returns the materialDesc.
	 * @return String
	 */
	public String getMaterialDesc() {
		return materialDesc;
	}

	/**
	 * Set the materialDesc
	 * @param materialDesc The materialDesc to set
	 */
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	/**
	 * Returns the materialAct.
	 * @return boolean
	 */
	public boolean getMaterialAct() {

		if ((materialAct!= true)&&(materialAct!=false)) {
			materialAct = true;
		}

		return materialAct;
	}

	/**
	 * Set the materialAct.
	 * @param materialAct The materialAct to set
	 */
	public void setMaterialAct(boolean materialAct) {
		this.materialAct = materialAct;
	}

	public String getCassette() {
		return cassette;
	}

	public void setCassette(String cassette) {
		this.cassette = cassette;
	}

}
