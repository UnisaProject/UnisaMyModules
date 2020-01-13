package za.ac.unisa.lms.tools.libmaint.forms;

import org.apache.struts.validator.ValidatorForm;

public class PlacementForm extends ValidatorForm {
	
	private int placementId;
	private String placement;
	private String placementRank;
	public String getPlacementRank() {
		return placementRank;
	}
	public void setPlacementRank(String placementRank) {
		this.placementRank = placementRank;
	}
	private String enabled;
	private boolean checked;
	private boolean inUse;
	
	// variables if list of placement linked to a resource
	private int resId;
	private String fromDate;
	private String endDate;
	
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public boolean isInUse() {
		return inUse;
	}
	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}
	public int getPlacementId() {
		return placementId;
	}
	public void setPlacementId(int placementId) {
		this.placementId = placementId;
	}
	public String getPlacement() {
		return placement;
	}
	public void setPlacement(String placement) {
		this.placement = placement;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

}
