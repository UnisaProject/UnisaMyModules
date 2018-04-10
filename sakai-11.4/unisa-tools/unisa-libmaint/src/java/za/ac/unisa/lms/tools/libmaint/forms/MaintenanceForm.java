package za.ac.unisa.lms.tools.libmaint.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class MaintenanceForm extends ValidatorForm {
	
	private final String database = "libresource";
	
	private String alpha;
	
	// arraylists
	private ArrayList enabledOptionList;
	private ArrayList dataList;
	private ArrayList removalList;
	
	//general
	private int id;
	private String enabled;
	private String performAction;
	private String onCampusURL;
	private String offCampusURL;
	
	// txt maintenance
	private String txtDesc; // text coverage description
	
	// Placements
	private String placement;
	
	// Vendors
	private String vendor;
	private String logoFileName;
	private String logoURL;
	
	// Subjects
	private String subject;
	
	// Highlight note
	private String highlight;
	private String highlightDesc;

	// News title
	private String newsTitleDesc;
	
	// e-resources/subject databases
	private String resourceName;
	private String resourceDesc;
	private int vendorId = -1;
	private String vendorDesc;
	private String textId;
	private String textDesc;
	private String cdRom;
	private String passwordExist;
	private String login;
	private String password;
	private String trainingURL;
	private String newsTitle;
	private String newsURL;
	private String accessNote;
	private String alert;
	private String refManagementURL;

	private ArrayList vendorOptions;
	private ArrayList txtOptions;
	private ArrayList newsTitleOptions;
	private ArrayList highlightOptions;
	private ArrayList placementList;
	private String[] selectedPlacements;
	private String selectedPlacementsStr;
	private ArrayList subjectList;
	private String[] selectedSubjects;
	private String selectedSubjectsStr;
	private String resourcesPerPlacement ="0";
	private String resourcesPerVendor="0";
	
	public String getAlert() {
		return alert;
	}


	public void setAlert(String alert) {
		this.alert = alert;
	}
	
	public String getHighlightDesc() {
		return highlightDesc;
	}


	public void setHighlightDesc(String highlightDesc) {
		this.highlightDesc = highlightDesc;
	}

	
	public ArrayList getHighlightOptions() {
		return highlightOptions;
	}


	public void setHighlightOptions(ArrayList highlightOptions) {
		this.highlightOptions = highlightOptions;
	}
	
	public String getHighlight() {
		return highlight;
	}


	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	
	public String getSelectedSubjectsStr() {
		return selectedSubjectsStr;
	}


	public void setSelectedSubjectsStr(String selectedSubjectsStr) {
		this.selectedSubjectsStr = selectedSubjectsStr;
	}


	public ArrayList getPlacementList() {
		return placementList;
	}


	public void setPlacementList(ArrayList placementList) {
		this.placementList = placementList;
	}


	public ArrayList getSubjectList() {
		return subjectList;
	}


	public void setSubjectList(ArrayList subjectList) {
		this.subjectList = subjectList;
	}


	public ArrayList getTxtOptions() {
		return txtOptions;
	}


	public void setTxtOptions(ArrayList txtOptions) {
		this.txtOptions = txtOptions;
	}


	public ArrayList getVendorOptions() {
		return vendorOptions;
	}


	public void setVendorOptions(ArrayList vendorOptions) {
		this.vendorOptions = vendorOptions;
	}


	public String getOnCampusURL() {
		return onCampusURL;
	}


	public void setOnCampusURL(String onCampusURL) {
		this.onCampusURL = onCampusURL;
	}


	public String getOffCampusURL() {
		return offCampusURL;
	}


	public void setOffCampusURL(String offCampusURL) {
		this.offCampusURL = offCampusURL;
	}


	public String getVendor() {
		return vendor;
	}


	public void setVendor(String vendor) {
		this.vendor = vendor;
	}


	public String getLogoFileName() {
		return logoFileName;
	}


	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}


	public String getLogoURL() {
		return logoURL;
	}


	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}


	/**
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void resetFormbean(ActionMapping mapping, HttpServletRequest request) {
		this.id = 0;
		this.txtDesc = "";
		this.enabled = "";
		this.performAction = "";
		this.removalList = null;
		this.placement = "";
		
		this.resourceName = "";
		this.resourceDesc = "";
		this.onCampusURL = "";
		this.offCampusURL = "";
		this.vendorId = 0;
		this.textId = "";
		this.cdRom = "";
		this.trainingURL = "";
		this.newsTitle = "";
		this.newsURL = "";
		this.accessNote = "";
		this.passwordExist = "";
		this.login = "";
		this.password = "";
		this.enabled = "";
		this.selectedPlacements = null;
		this.selectedSubjects = null;
		this.selectedPlacementsStr = "";
		this.selectedSubjectsStr = "";
		this.highlight = "";
		this.alert="";
		this.refManagementURL = "";
		
		this.vendor="";
		this.vendorDesc="";
		this.logoFileName="";
		this.logoURL="";
	
		
	}
	
	
	public Object getRecordIndexed(int index) {
		return dataList.get(index);
	}

	public Object getRecordIndexed2(int index) {
		return placementList.get(index);
	}
	
	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getTxtDesc() {
		return txtDesc;
	}

	public void setTxtDesc(String txtDesc) {
		this.txtDesc = txtDesc;
	}

	public ArrayList getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList dataList) {
		this.dataList = dataList;
	}

	public String getDatabase() {
		return database;
	}

	public ArrayList getEnabledOptionList() {
		enabledOptionList = new ArrayList();
		enabledOptionList.add(new org.apache.struts.util.LabelValueBean("..","0"));
		enabledOptionList.add(new org.apache.struts.util.LabelValueBean("Yes","Y"));
		enabledOptionList.add(new org.apache.struts.util.LabelValueBean("No","N"));
		return enabledOptionList;
	}

	public void setEnabledOptionList(ArrayList enabledOptionList) {
		this.enabledOptionList = enabledOptionList;
	}

	public ArrayList getRemovalList() {
		return removalList;
	}

	public void setRemovalList(ArrayList removalList) {
		this.removalList = removalList;
	}

	public String getPerformAction() {
		return performAction;
	}

	public void setPerformAction(String performAction) {
		this.performAction = performAction;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public String getResourceDesc() {
		return resourceDesc;
	}


	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}


	public int getVendorId() {
		return vendorId;
	}


	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}


	public String getVendorDesc() {
		return vendorDesc;
	}


	public void setVendorDesc(String vendorDesc) {
		this.vendorDesc = vendorDesc;
	}


	public String getTextId() {
		return textId;
	}


	public void setTextId(String textId) {
		this.textId = textId;
	}


	public String getTextDesc() {
		return textDesc;
	}


	public void setTextDesc(String textDesc) {
		this.textDesc = textDesc;
	}


	public String getCdRom() {
		return cdRom;
	}


	public void setCdRom(String cdRom) {
		this.cdRom = cdRom;
	}


	public String getPasswordExist() {
		return passwordExist;
	}


	public void setPasswordExist(String passwordExist) {
		this.passwordExist = passwordExist;
	}


	public String getTrainingURL() {
		return trainingURL;
	}


	public void setTrainingURL(String trainingURL) {
		this.trainingURL = trainingURL;
	}


	public String getNewsTitleDesc() {
		return newsTitleDesc;
	}


	public void setNewsTitleDesc(String newsTitleDesc) {
		this.newsTitleDesc = newsTitleDesc;
	}


	public String getNewsTitle() {
		return newsTitle;
	}


	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}


	public String getNewsURL() {
		return newsURL;
	}


	public void setNewsURL(String newsURL) {
		this.newsURL = newsURL;
	}


	public String getAccessNote() {
		return accessNote;
	}


	public void setAccessNote(String accessNote) {
		this.accessNote = accessNote;
	}


	public ArrayList getNewsTitleOptions() {
		return newsTitleOptions;
	}


	public void setNewsTitleOptions(ArrayList newsTitleOptions) {
		this.newsTitleOptions = newsTitleOptions;
	}


	public String[] getSelectedPlacements() {
		return selectedPlacements;
	}


	public void setSelectedPlacements(String[] selectedPlacements) {
		this.selectedPlacements = selectedPlacements;
	}


	public String getSelectedPlacementsStr() {
		return selectedPlacementsStr;
	}


	public void setSelectedPlacementsStr(String selectedPlacementsStr) {
		this.selectedPlacementsStr = selectedPlacementsStr;
	}


	public String[] getSelectedSubjects() {
		if (selectedSubjects == null) {
			System.out.println("subject NULL");
		}

		return selectedSubjects;
	}


	public void setSelectedSubjects(String[] selectedSubjects) {
		if (selectedSubjects == null) {
			System.out.println("subject NULL");
		}
		this.selectedSubjects = selectedSubjects;
	}


	public String getAlpha() {
		return alpha;
	}


	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}


	public String getResourcesPerPlacement() {
		return resourcesPerPlacement;
	}


	public void setResourcesPerPlacement(String resourcesPerPlacement) {
		this.resourcesPerPlacement = resourcesPerPlacement;
	}


	public String getResourcesPerVendor() {
		return resourcesPerVendor;
	}


	public void setResourcesPerVendor(String resourcesPerVendor) {
		this.resourcesPerVendor = resourcesPerVendor;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getRefManagementURL() {
		return refManagementURL;
	}


	public void setRefManagementURL(String refManagementURL) {
		this.refManagementURL = refManagementURL;
	}
}
