package za.ac.unisa.lms.tools.broadbandrequest.forms;

import za.ac.unisa.lms.dao.Gencod;

public class ServiceProviderCost {
	private String spCode;
	private String spDescription;
	private double modemFeeDbl;
	private String modemFeeStr;
	private double simFeeDbl;
	private String simFeeStr;
	
	public String getSpCode() {
		return spCode;
	}
	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}
	public String getSpDescription() {
		return spDescription;
	}
	public void setSpDescription(String spDescription) {
		this.spDescription = spDescription;
	}
	public double getModemFeeDbl() {
		return modemFeeDbl;
	}
	public void setModemFeeDbl(double modemFeeDbl) {
		this.modemFeeDbl = modemFeeDbl;
	}
	public String getModemFeeStr() {
		return modemFeeStr;
	}
	public void setModemFeeStr(String modemFeeStr) {
		this.modemFeeStr = modemFeeStr;
	}
	public double getSimFeeDbl() {
		return simFeeDbl;
	}
	public void setSimFeeDbl(double simFeeDbl) {
		this.simFeeDbl = simFeeDbl;
	}
	public String getSimFeeStr() {
		return simFeeStr;
	}
	public void setSimFeeStr(String simFeeStr) {
		this.simFeeStr = simFeeStr;
	}
	
	
}
