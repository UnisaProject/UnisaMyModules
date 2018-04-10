package za.ac.unisa.lms.tools.libmaint.dao;

public class LibResourceDetails {
	
	private int resId;
	private String resDesr;
	private String logoFile;
	private String logoUrl;
	private String ResName;
	private String vendorName;
	private String addInfo;
	private String logIn;
	private String password;
	private String dTraining;
	private String tTtraining;
	private String newsUrl;
	private String newsTitle;
	private String alert;
	private String accessNote;
	private String refManagement;
	private String highlight;
	private String highlightDesc;
	private String contentType;
	private int placementID;
	private String trialExpiryDate;
	private String onCampusUrl;
	private String offCampusUrl;
	private String venOffUrl;
	private String venOnUrl;
	private String vLogo;
	private String viewPassword;

	
	public String getOnCampusUrl() {
		return onCampusUrl;
	}
	public void setOnCampusUrl(String onCampusUrl) {
		this.onCampusUrl = onCampusUrl;
	}
	public String getOffCampusUrl() {
		return offCampusUrl;
	}
	public void setOffCampusUrl(String offCampusUrl) {
		this.offCampusUrl = offCampusUrl;
	}
	public String getVenOffUrl() {
		return venOffUrl;
	}
	public void setVenOffUrl(String venOffUrl) {
		this.venOffUrl = venOffUrl;
	}
	public String getVenOnUrl() {
		return venOnUrl;
	}
	public void setVenOnUrl(String venOnUrl) {
		this.venOnUrl = venOnUrl;
	}
	public String getvLogo() {
		return vLogo;
	}
	public void setvLogo(String vLogo) {
		this.vLogo = vLogo;
	}
	public String getViewPassword() {
		return viewPassword;
	}
	public void setViewPassword(String viewPassword) {
		this.viewPassword = viewPassword;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public String getResDesr() {
		return resDesr;
	}
	public void setResDesr(String resDesr) {
		this.resDesr = resDesr;
	}
	public String getLogoFile() {
		return logoFile;
	}
	public void setLogoFile(String logoFile) {
		this.logoFile = logoFile;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getResName() {
		return ResName;
	}
	public void setResName(String resName) {
		ResName = resName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	public String getLogIn() {
		return logIn;
	}
	public void setLogIn(String logIn) {
		this.logIn = logIn;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getdTraining() {
		return dTraining;
	}
	public void setdTraining(String dTraining) {
		this.dTraining = dTraining;
	}
	public String gettTtraining() {
		return tTtraining;
	}
	public void settTtraining(String tTtraining) {
		this.tTtraining = tTtraining;
	}
	public String getNewsUrl() {
		return newsUrl;
	}
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getAccessNote() {
		return accessNote;
	}
	public void setAccessNote(String accessNote) {
		this.accessNote = accessNote;
	}
	public String getRefManagement() {
		return refManagement;
	}
	public void setRefManagement(String refManagement) {
		this.refManagement = refManagement;
	}
	public String getHighlight() {
		return highlight;
	}
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	public String getHighlightDesc() {
		return highlightDesc;
	}
	public void setHighlightDesc(String highlightDesc) {
		this.highlightDesc = highlightDesc;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public int getPlacementID() {
		return placementID;
	}
	public void setPlacementID(int placementID) {
		this.placementID = placementID;
	}

	public void setExpiryDate(String trialExpiryDate){
		this.trialExpiryDate = trialExpiryDate;
	}
	
	public String getExpiryDate(){
		return trialExpiryDate;
	}

}
