package za.ac.unisa.lms.tools.signoff.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class SignoffForm extends ActionForm{
	
	private String code="-1";
	private String sysdate;
	private String dean;
	private String dyDean;
	private String schDirector;
	private String cod;
	private String deanStatus;
	private String dyDeanStatus;
	private String schDirectorStatus;
	private String codStatus;
	private String levelCode="-1";
	private String dpt_list;
	private ArrayList standinDydean;
	private ArrayList standinSch;
	private ArrayList standinCod;
	private ArrayList removePersonList;
	private ArrayList staffList;
	private ArrayList fullNamesList;
	private int noPersons;
	private String structure;
	private String orderStructure;
	private String order_list;
	private String errorMessage="";
	private String edit;
	private int noOfRecords =0;
	private String level_list;
	private int fullNameList;
	private String clgCode="0";
	private String schCode="0";
	private String userPermission;
	public String getUserPermission() {
		return userPermission;
	}

	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}

	public String getClgCode() {
		return clgCode;
	}

	public void setClgCode(String clgCode) {
		this.clgCode = clgCode;
	}

	public String getSchCode() {
		return schCode;
	}

	public void setSchCode(String schCode) {
		this.schCode = schCode;
	}

	public int getFullNameList() {
		return fullNameList;
	}

	public void setFullNameList(int fullNameList) {
		this.fullNameList = fullNameList;
	}
	private String [] orderList;
	
	public String getLevelCode() {
		return levelCode;
	}

	public String[] getOrderList() {
		return orderList;
	}

	public void setOrderList(String[] orderList) {
		this.orderList = orderList;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getDpt_list() {
		return dpt_list;
	}

	public void setDpt_list(String dptList) {
		dpt_list = dptList;
	}

	public ArrayList getFullNamesList() {
		return fullNamesList;
	}

	public void setFullNamesList(ArrayList fullNamesList) {
		this.fullNamesList = fullNamesList;
	}
	private String dptCode="-1";
	public String getDptCode() {
		return dptCode;
	}

	public void setDptCode(String dptCode) {
		this.dptCode = dptCode;
	}

	public ArrayList getStaffList() {
		return staffList;
	}

	public void setStaffList(ArrayList staffList) {
		this.staffList = staffList;
	}
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getOrder_list() {
		return order_list;
	}

	public void setOrder_list(String orderList) {
		order_list = orderList;
	}
	private String display="";
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	public String getLevel_list() {
		return level_list;
	}

	public void setLevel_list(String levelList) {
		level_list = levelList;
	}

	public String getOrderStructure() {
		return orderStructure;
	}

	public void setOrderStructure(String orderStructure) {
		this.orderStructure = orderStructure;
	}
	public int getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	private ArrayList noOfRecordsOptions;
	
	public ArrayList getNoOfRecordsOptions() {
		noOfRecordsOptions = new ArrayList();
		for (int i=0; i <= 10; i++) {
			noOfRecordsOptions.add(new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
		}
		return noOfRecordsOptions;
	}
	
	public void setNoOfRecordsOptions(ArrayList noOfRecordsOptions) {
		this.noOfRecordsOptions = noOfRecordsOptions;
	}
	
	
	public String getEdit() {
		return edit;
	}
	public void setEdit(String edit) {
		this.edit = edit;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public int getNoPersons() {
		return noPersons;
	}
	public void setNoPersons(int noPersons) {
		this.noPersons = noPersons;
	}
	public ArrayList getRemovePersonList() {
		return removePersonList;
	}
	public void setRemovePersonList(ArrayList removePersonList) {
		this.removePersonList = removePersonList;
	}
	public ArrayList getStandinCod() {
		return standinCod;
	}
	public void setStandinCod(ArrayList standinCod) {
		this.standinCod = standinCod;
	}
	public ArrayList getStandinSch() {
		return standinSch;
	}
	public void setStandinSch(ArrayList standinSch) {
		this.standinSch = standinSch;
	}
	public ArrayList getStandinDydean() {
		return standinDydean;
	}
	public void setStandinDydean(ArrayList standinDydean) {
		this.standinDydean = standinDydean;
	}
	public String getDeanStatus() {
		return deanStatus;
	}
	public void setDeanStatus(String deanStatus) {
		this.deanStatus = deanStatus;
	}
	public String getDyDeanStatus() {
		return dyDeanStatus;
	}
	public void setDyDeanStatus(String dyDeanStatus) {
		this.dyDeanStatus = dyDeanStatus;
	}
	public String getSchDirectorStatus() {
		return schDirectorStatus;
	}
	public void setSchDirectorStatus(String schDirectorStatus) {
		this.schDirectorStatus = schDirectorStatus;
	}
	public String getCodStatus() {
		return codStatus;
	}
	public void setCodStatus(String codStatus) {
		this.codStatus = codStatus;
	}
	public String getDean() {
		return dean;
	}
	public void setDean(String dean) {
		this.dean = dean;
	}
	public String getDyDean() {
		return dyDean;
	}
	public void setDyDean(String dyDean) {
		this.dyDean = dyDean;
	}
	public String getSchDirector() {
		return schDirector;
	}
	public void setSchDirector(String schDirector) {
		this.schDirector = schDirector;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getSysdate() {
		return sysdate;
	}
	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getRecordIndexed(int index) {
		return standinDydean.get(index);
	}
	public Object getRecordIndexed1(int index) {
		return standinSch.get(index);
	}
	public Object getRecordIndexed2(int index) {
		return standinCod.get(index);
	}
	public Object getRecordIndexed3(int index) {
		return staffList.get(index);
	}
}
