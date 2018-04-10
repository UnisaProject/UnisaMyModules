package za.ac.unisa.lms.tools.maintainstaff.forms;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class HodDisplayForm  extends ValidatorForm  {
	private ArrayList departmentList; // list of available departments
	private ArrayList departmentOrderList; // order by options
	private String departmentTypeForReport; // type of department
	private String[] selectedDepartment; // selected department
	private String[] SelectedStandin; // selected standin 
	private boolean  choice; //indicator of choice made

/*

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}*/

	public boolean isChoice() {
		return choice;
	}

	public void setChoice(boolean choice) {
		this.choice = choice;
	}

	public String[] getSelectedStandin() {
		return SelectedStandin;
	}

	public void setSelectedStandin(String[] selectedStandin) {
		SelectedStandin = selectedStandin;
	}

	public String[] getSelectedDepartment() {
		return selectedDepartment;
	}

	public void setSelectedDepartment(String[] selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}

	public String getDepartmentTypeForReport() {
		return departmentTypeForReport;
	}

	public void setDepartmentTypeForReport(String departmentTypeForReport) {
		this.departmentTypeForReport = departmentTypeForReport;
	}

	public ArrayList getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(ArrayList departmentList) {
		this.departmentList = departmentList;
	}

	public ArrayList getDepartmentOrderList() {
		return departmentOrderList;
	}

	public void setDepartmentOrderList(ArrayList departmentOrderList) {
		this.departmentOrderList = departmentOrderList;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		choice=false;
		
		
	}

	
	
}