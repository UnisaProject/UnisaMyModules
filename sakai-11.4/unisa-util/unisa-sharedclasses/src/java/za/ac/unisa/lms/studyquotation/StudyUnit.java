package za.ac.unisa.lms.studyquotation;

public class StudyUnit {
	
	private String studyUnitcode;
	private String description;
	private double fee;
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the fee.
	 */
	public double getFee() {
		return fee;
	}
	/**
	 * @param d The fee to set.
	 */
	public void setFee(double d) {
		this.fee = d;
	}
	/**
	 * @return Returns the studyUnitcode.
	 */
	public String getStudyUnitcode() {
		return studyUnitcode;
	}
	/**
	 * @param studyUnitcode The studyUnitcode to set.
	 */
	public void setStudyUnitcode(String studyUnitcode) {
		this.studyUnitcode = studyUnitcode;
	}
	
	

}
