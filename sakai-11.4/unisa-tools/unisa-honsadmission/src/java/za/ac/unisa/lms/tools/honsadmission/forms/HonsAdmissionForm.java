package za.ac.unisa.lms.tools.honsadmission.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.domain.general.Person;

public class HonsAdmissionForm extends ValidatorForm {
	
	private static final long serialVersionUID = 1L;

	private String userId;
	private Person user;
	private List<SignOffAccess> listUserSignOffAccess; 
	private ApplicationPeriod appPeriod;
	private ApplicationPeriod appPeriodView;
	private List<LabelValueBean> listPeriod;
	private List<Referral> listReview;	
	private List<Referral> listStudentReferrals;	
	private Referral selectedApplication;
	private Application application;
	private String signOffComment;
	private String recommendation;
	private String recommendationAWRComment;
	private String recommendationANRComment;
	private String recommendationDCLComment;
	private String currentPage;
	private ArrayList<UniflowFile> documentList = new ArrayList<UniflowFile>();
	private boolean oldUniflowInUse = true;
	private String access;
	private String selectedSignOffIndex;
	private String studentNr;
	private String studentName;
	private boolean prevUnisaQuals = false;
	
	public boolean isPrevUnisaQuals() {
		return prevUnisaQuals;
	}
	public void setPrevUnisaQuals(boolean prevUnisaQuals) {
		this.prevUnisaQuals = prevUnisaQuals;
	}
	public ApplicationPeriod getAppPeriodView() {
		return appPeriodView;
	}
	public void setAppPeriodView(ApplicationPeriod appPeriodView) {
		this.appPeriodView = appPeriodView;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public List<Referral> getListStudentReferrals() {
		return listStudentReferrals;
	}
	public void setListStudentReferrals(List<Referral> listStudentReferrals) {
		this.listStudentReferrals = listStudentReferrals;
	}
	public String getStudentNr() {
		return studentNr;
	}
	public void setStudentNr(String studentNr) {
		this.studentNr = studentNr;
	}
	public String getSelectedSignOffIndex() {
		return selectedSignOffIndex;
	}
	public void setSelectedSignOffIndex(String selectSignOffIndex) {
		this.selectedSignOffIndex = selectSignOffIndex;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public ArrayList<UniflowFile> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(ArrayList<UniflowFile> documentList) {
		this.documentList = documentList;
	}
	public boolean isOldUniflowInUse() {
		return oldUniflowInUse;
	}
	public void setOldUniflowInUse(boolean oldUniflowInUse) {
		this.oldUniflowInUse = oldUniflowInUse;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public Referral getSelectedApplication() {
		return selectedApplication;
	}
	public void setSelectedApplication(Referral selectedApplication) {
		this.selectedApplication = selectedApplication;
	}
	public ApplicationPeriod getAppPeriod() {
		return appPeriod;
	}
	public void setAppPeriod(ApplicationPeriod appPeriod) {
		this.appPeriod = appPeriod;
	}
	public List<Referral> getListReview() {
		return listReview;
	}
	public void setListReview(List<Referral> listReview) {
		this.listReview = listReview;
	}
	public List<LabelValueBean> getListPeriod() {
		return listPeriod;
	}
	public void setListPeriod(List<LabelValueBean> listPeriod) {
		this.listPeriod = listPeriod;
	}
	public List<SignOffAccess> getListUserSignOffAccess() {
		return listUserSignOffAccess;
	}
	public void setListUserSignOffAccess(List<SignOffAccess> listUserSignOffAccess) {
		this.listUserSignOffAccess = listUserSignOffAccess;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Person getUser() {
		return user;
	}
	public void setUser(Person user) {
		this.user = user;
	}
	public String getSignOffComment() {
		return signOffComment;
	}
	public void setSignOffComment(String signOffComment) {
		this.signOffComment = signOffComment;
	}
	public String getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	public String getRecommendationAWRComment() {
		return recommendationAWRComment;
	}
	public void setRecommendationAWRComment(String recommendationAWRComment) {
		this.recommendationAWRComment = recommendationAWRComment;
	}
	public String getRecommendationANRComment() {
		return recommendationANRComment;
	}
	public void setRecommendationANRComment(String recommendationANRComment) {
		this.recommendationANRComment = recommendationANRComment;
	}
	public String getRecommendationDCLComment() {
		return recommendationDCLComment;
	}
	public void setRecommendationDCLComment(String recommendationDCLComment) {
		this.recommendationDCLComment = recommendationDCLComment;
	}
	
}
