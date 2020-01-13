package za.ac.unisa.lms.tools.liberesource.forms;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.validator.ValidatorForm;

public class ResourceForm extends ValidatorForm {

	private int subjectId;
	private String subject;
	private String enabled;
	private int vendorId;
	private String vendorName;
	private String newsUrl;
	private String newsTitle;
	private String resName;
	private String resdescr;
	private String oncampusUrl;
	private String offcampusUrl;
	private String campusUrl;
	private String addInfo;
	private String ipaddr;
	private String placement;
	private String viewPassword;	
	private BufferedImage logo;
	private String url;
	private String training;
	private String subjectcover;
    private String substat;
    private String vresname;
    private String vresDescr;
    private String vurl;
    private String vplacement;
    private String vaddInfo;
    private String vcampusUrl;
    private String vtraining;
    private String dlogo;
    private String dresName;
    private String dplacement;
    private String daddInfo;
    private String dresDescr;
    private String dcampusUrl;
    private String dtraining;
    private String highlight;
    private String highlightDesc;
    private String contentType;
    private String alert;
    private String accessNote;
    private String rfManagement;
    private String trialExpiryDate;
    

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
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEnabled() {
		return enabled;
	}
	
	public void setExpiryDate(String trialExpiryDate){
		this.trialExpiryDate = trialExpiryDate;
	}
	
	public String getExpiryDate(){
		return trialExpiryDate;
	}
	
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	
	public String getContentType()
	{
		return contentType;
	}
	
	public void setAlert(String alert)
	{
		this.alert = alert;
	}
	
	public String getAlert()
	{
		return alert;
	}
	
	public void setAccessNote(String accessNote)
	{
		this.accessNote = accessNote;
	}
	
	public String getAccessNote()
	{
		return accessNote;
	}
	
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	public void setRfManagement(String rfManagement)
	{
		this.rfManagement = rfManagement; 
	}
	
	public String getRfManagement()
	{
		return rfManagement;
	}
	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResdescr() {
		return resdescr;
	}
	public void setResdescr(String resdescr) {
		this.resdescr = resdescr;
	}
	public String getOncampusUrl() {
		return oncampusUrl;
	}
	public void setOncampusUrl(String oncampusUrl) {
		this.oncampusUrl = oncampusUrl;
	}
	public String getOffcampusUrl() {
		return offcampusUrl;
	}
	public void setOffcampusUrl(String offcampusUrl) {
		this.offcampusUrl = offcampusUrl;
	}
	public BufferedImage getLogo() {
		return logo;
	}
	public void setLogo(BufferedImage logo) {
		this.logo = logo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddInfo() {
		return addInfo;
	}
	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}
	public String getCampusUrl() {
		return campusUrl;
	}
	public void setCampusUrl(String campusUrl) {
		this.campusUrl = campusUrl;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	
	
	public String getTraining() {
		return training;
	}
	public void setTraining(String training) {
		this.training = training;
	}
	public String getSubstat() {
		return substat;
	}
	public void setSubstat(String substat) {
		this.substat = substat;
	}
	public String getVresname() {
		return vresname;
	}
	public void setVresname(String vresname) {
		this.vresname = vresname;
	}
	
	public String getVurl() {
		return vurl;
	}
	public void setVurl(String vurl) {
		this.vurl = vurl;
	}
	public String getVplacement() {
		return vplacement;
	}
	public void setVplacement(String vplacement) {
		this.vplacement = vplacement;
	}
	public String getVaddInfo() {
		return vaddInfo;
	}
	public void setVaddInfo(String vaddInfo) {
		this.vaddInfo = vaddInfo;
	}
	public String getVresDescr() {
		return vresDescr;
	}
	public void setVresDescr(String vresDescr) {
		this.vresDescr = vresDescr;
	}
	
	public String getVtraining() {
		return vtraining;
	}
	public void setVtraining(String vtraining) {
		this.vtraining = vtraining;
	}
	public String getVcampusUrl() {
		return vcampusUrl;
	}
	public void setVcampusUrl(String vcampusUrl) {
		this.vcampusUrl = vcampusUrl;
	}
	public String getDlogo() {
		return dlogo;
	}
	public void setDlogo(String dlogo) {
		this.dlogo = dlogo;
	}
	public String getDresName() {
		return dresName;
	}
	public void setDresName(String dresName) {
		this.dresName = dresName;
	}
	public String getDplacement() {
		return dplacement;
	}
	public void setDplacement(String dplacement) {
		this.dplacement = dplacement;
	}
	public String getDaddInfo() {
		return daddInfo;
	}
	public void setDaddInfo(String daddInfo) {
		this.daddInfo = daddInfo;
	}
	public String getDresDescr() {
		return dresDescr;
	}
	public void setDresDescr(String dresDescr) {
		this.dresDescr = dresDescr;
	}
	public String getDcampusUrl() {
		return dcampusUrl;
	}
	public void setDcampusUrl(String dcampusUrl) {
		this.dcampusUrl = dcampusUrl;
	}
	public String getDtraining() {
		return dtraining;
	}
	public void setDtraining(String dtraining) {
		this.dtraining = dtraining;
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
	public void setSubjectcover(String subjectcover) {
		this.subjectcover = subjectcover;
	}
	public String getSubjectcover() {
		return subjectcover;
	}
	public String getPlacement() {
		return placement;
	}
	public void setPlacement(String placement) {
		this.placement = placement;
	}
	public String getViewPassword() {
		return viewPassword;
	}
	public void setViewPassword(String viewPassword) {
		this.viewPassword = viewPassword;
	}


	
	
	
	
	
}
