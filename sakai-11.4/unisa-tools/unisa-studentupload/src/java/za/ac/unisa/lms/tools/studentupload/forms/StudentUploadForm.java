package za.ac.unisa.lms.tools.studentupload.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

import za.ac.unisa.lms.tools.studentupload.forms.Staff;
import za.ac.unisa.lms.tools.studentupload.bo.Doc;
import za.ac.unisa.lms.tools.studentupload.dao.DocDao;
import za.ac.unisa.lms.tools.studentupload.dao.SavedDocDao;
import za.ac.unisa.lms.tools.studentupload.forms.FileBean;
import za.ac.unisa.lms.tools.studentupload.forms.StudentFile;
import za.ac.unisa.lms.tools.studentupload.actions.StudentUploadAction;

public class StudentUploadForm extends ValidatorActionForm {

	// --------------------------------------------------------- Instance Variables

	private static final long serialVersionUID = 1L;
	private static final String version = "2018003a";
	public static Log log = LogFactory.getLog(StudentUploadAction.class);
	
	private String applyType = "";
	private String registrationType ="";// U = undergrad, P = postgrad, S = Short learning programme
	private boolean allowLogin = true;
	private Student student = new Student();
	private Staff adminStaff = new Staff();
	private Qualification qual = new Qualification();
	private Qualification qualtwo = new Qualification();
	private Application studentApplication = new Application();
	private boolean qualSTAAE01 = false;
	private int Spescount=0;
	private String regqualtype="";

	// Apply for student nr
	private String selectedDisability;
	private String selectedQual;
	private String selectedQualtwo;
	private String selectedSpes;
	private String selectedSpestwo;
	private String selectedCountry;
	private String selectedExamCentre;
	private String selectedExamCenprov;
	private String selectedHomeLanguage;
	private String selectedNationality;
	private String selectedPopulationGroup;
	private String selectedOccupation;
	private String selectedEconomicSector;
	private String selectedOtherUniversity;
	private String SelectedPrevInstitution;
	private String SelectedProvince;

	private String selectedPrevActivity;

	private String agree;
	private String numberBack;
	private String timeBack;
	private String donesubmit;
	private String String500back;

	//variable used to test session throughout process
	private String fromPage;

	// 2014 New Login Select
	private String selected = "1";
	
	private String loginSelectMain = "";
	private String webUploadMsg = "";
	private String webLoginMsg = "";
	private String selectReset = "";
	
	private String selCategoryCode;
	private String selCategoryCode1;
	private String selCategoryCode2;
	
	private String selQualCode;
	private String selQualCode1;
	private String selQualCode2;
	
	private String selQualCodeDesc = "";
	private String selQualCode1Desc = "";
	private String selQualCode2Desc = "";
	
	private String selSpecCode;
	private String selSpecCode1;
	private String selSpecCode2;
	
	private String selSpecCodeDesc = "";
	private String selSpecCode1Desc = "";
	private String selSpecCode2Desc = "";
	
	private String selQualPrevCode;

	ArrayList<String> categoryCodeList = new ArrayList<String>();
	ArrayList<String> categoryCodeList1 = new ArrayList<String>();
	ArrayList<String> categoryCodeList2 = new ArrayList<String>();

	ArrayList<String> categoryDescList = new ArrayList<String>();
	ArrayList<String> categoryDescList1 = new ArrayList<String>();
	ArrayList<String> categoryDescList2 = new ArrayList<String>();
	
	ArrayList<String> qualCodeList = new ArrayList<String>();
	ArrayList<String> qualCodeList1 = new ArrayList<String>();
	ArrayList<String> qualCodeList2 = new ArrayList<String>();
	
	ArrayList<String> qualCodeDescList = new ArrayList<String>();
	ArrayList<String> qualCodeDescList1 = new ArrayList<String>();
	ArrayList<String> qualCodeDescList2 = new ArrayList<String>();
	
	ArrayList<String> qualDescList = new ArrayList<String>();
	ArrayList<String> qualDescList1 = new ArrayList<String>();
	ArrayList<String> qualDescList2 = new ArrayList<String>();
	
	ArrayList<String> specCodeList = new ArrayList<String>();
	ArrayList<String> specCodeList1 = new ArrayList<String>();
	ArrayList<String> specCodeList2 = new ArrayList<String>();
	
	ArrayList<String> specCodeDescList = new ArrayList<String>();
	ArrayList<String> specCodeDescList1 = new ArrayList<String>();
	ArrayList<String> specCodeDescList2 = new ArrayList<String>();
	
	ArrayList<String> specDescList = new ArrayList<String>();
	ArrayList<String> specDescList1 = new ArrayList<String>();
	ArrayList<String> specDescList2 = new ArrayList<String>();

	ArrayList<String> qualPrevCodeList = new ArrayList<String>();
	ArrayList<String> qualPrevDescList = new ArrayList<String>();
	ArrayList<String> qualPrevCodeDescList = new ArrayList<String>();
	
	private String qualUpload1 = "";
	private String qualUpload2 = "";
	private String qualUploadCode1 = "";
	private String qualUploadCode2 = "";
	private String qualUpload1Reason = "";
	private String qualUpload2Reason = "";
	
	//FileUpload
	//for showing optional docs

	private List<String> desc = new ArrayList<String>();
	private Map<String, List<String>> map = new HashMap<String, List<String>>();
		

	private List<Doc> requiredDocs;
	private List<Doc> optionalDocs;

	private List<String> optionLabels = new ArrayList<String>();
	private List<String> optionCodes = new ArrayList<String>();

	private FileBean[] requiredFileBeans;
	private FileBean optionFileBean;

	private String docMsg = "";

	private String fileName = "";
	private String fileNewName = "";
	private String fileType = "";
	private int fileCount = 0;
	private FormFile file;
	private FormFile theFile;
	private String fileSelected = "";
	private ArrayList<StudentFile> studentFiles = new ArrayList<StudentFile>();
	
	private boolean hiddenButtonUpload = false;
	private boolean hiddenButton = false;

	public String getVersion() {
		return version;
	}
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Staff getAdminStaff() {
		return adminStaff;
	}

	public void setAdminStaff(Staff adminStaff) {
		this.adminStaff = adminStaff;
	}
	
	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public boolean isAllowLogin() {
		return allowLogin;
	}

	public void setAllowLogin(boolean allowLogin) {
		this.allowLogin = allowLogin;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public Qualification getQual() {
		return qual;
	}

	public void setQual(Qualification qual) {
		this.qual = qual;
	}

	public String getSelectedQual() {
		return selectedQual;
	}

	public void setSelectedQual(String selectedQual) {
		this.selectedQual = selectedQual;
	}

	public String getSelectedQualtwo() {
		return selectedQualtwo;
	}

	public void setSelectedQualtwo(String selectedQualtwo) {
		this.selectedQualtwo = selectedQualtwo;
	}

	public Qualification getQualtwo() {
		return qualtwo;
	}

	public void setQualtwo(Qualification qualtwo) {
		this.qualtwo = qualtwo;
	}

	public int getSpescount() {
		return Spescount;
	}

	public String getSelectedDisability() {
		return selectedDisability;
	}

	public void setSelectedDisability(String selectedDisability) {
		this.selectedDisability = selectedDisability;
	}

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public String getSelectedExamCentre() {
		return selectedExamCentre;
	}

	public void setSelectedExamCentre(String selectedExamCentre) {
		this.selectedExamCentre = selectedExamCentre;
	}

	public String getSelectedEconomicSector() {
		return selectedEconomicSector;
	}

	public void setSelectedEconomicSector(String selectedEconomicSector) {
		this.selectedEconomicSector = selectedEconomicSector;
	}

	public String getSelectedHomeLanguage() {
		return selectedHomeLanguage;
	}

	public void setSelectedHomeLanguage(String selectedHomeLanguage) {
		this.selectedHomeLanguage = selectedHomeLanguage;
	}

	public String getSelectedNationality() {
		return selectedNationality;
	}

	public void setSelectedNationality(String selectedNationality) {
		this.selectedNationality = selectedNationality;
	}

	public String getSelectedOccupation() {
		return selectedOccupation;
	}

	public void setSelectedOccupation(String selectedOccupation) {
		this.selectedOccupation = selectedOccupation;
	}

	public String getSelectedOtherUniversity() {
		return selectedOtherUniversity;
	}

	public void setSelectedOtherUniversity(String selectedOtherUniversity) {
		this.selectedOtherUniversity = selectedOtherUniversity;
	}

	public String getSelectedPopulationGroup() {
		return selectedPopulationGroup;
	}

	public void setSelectedPopulationGroup(String selectedPopulationGroup) {
		this.selectedPopulationGroup = selectedPopulationGroup;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public Application getStudentApplication() {
		return studentApplication;
	}

	public void setStudentApplication(Application studentApplication) {
		this.studentApplication = studentApplication;
	}
	
	public String getSelectedPrevInstitution() {
		return SelectedPrevInstitution;
	}

	public void setSelectedPrevInstitution(String selectedPrevInstitution) {
		SelectedPrevInstitution = selectedPrevInstitution;
	}

	public String getSelectedProvince() {
		return SelectedProvince;
	}

	public void setSelectedProvince(String selectedProvince) {
		SelectedProvince = selectedProvince;
	}

	public String getNumberBack() {
		return numberBack;
	}

	public void setNumberBack(String numberBack) {
		this.numberBack = numberBack;
	}

	public String getDonesubmit() {
		return donesubmit;
	}

	public void setDonesubmit(String donesubmit) {
		this.donesubmit = donesubmit;
	}

	public String getTimeBack() {
		return timeBack;
	}

	public void setTimeBack(String timeBack) {
		this.timeBack = timeBack;
	}

	public String getSelectedSpes() {
		return selectedSpes;
	}

	public void setSelectedSpes(String selectedSpes) {
		this.selectedSpes = selectedSpes;
	}

	public String getSelectedSpestwo() {
		return selectedSpestwo;
	}

	public void setSelectedSpestwo(String selectedSpestwo) {
		this.selectedSpestwo = selectedSpestwo;
	}

	public String getSelectedPrevActivity() {
		return selectedPrevActivity;
	}

	public void setSelectedPrevActivity(String selectedPrevActivity) {
		this.selectedPrevActivity = selectedPrevActivity;
	}
	public boolean isQualSTAAE01() {
		return qualSTAAE01;
	}

	public void setQualSTAAE01(boolean qualSTAAE01) {
		this.qualSTAAE01 = qualSTAAE01;
	}

	public void setSpescount(int spescount) {
		Spescount = spescount;
	}

	public String getString500back() {
		return String500back;
	}

	public void setString500back(String string500back) {
		String500back = string500back;
	}

	public String getRegqualtype() {
		return regqualtype;
	}

	public void setRegqualtype(String regqualtype) {
		this.regqualtype = regqualtype;
	}

	public String getSelectedExamCenprov() {
		return selectedExamCenprov;
	}

	public void setSelectedExamCenprov(String selectedExamCenprov) {
		this.selectedExamCenprov = selectedExamCenprov;
	}
	// New 2014 Login Select
	public String getLoginSelectMain() {
		return loginSelectMain;
	}

	public void setLoginSelectMain(String loginSelectMain) {
		this.loginSelectMain = loginSelectMain;
	}
	
	public String getWebLoginMsg() {
		return webLoginMsg;
	}

	public void setWebLoginMsg(String webLoginMsg) {
		this.webLoginMsg = webLoginMsg;
	}

	public String getSelectReset() {
		return selectReset;
	}

	public void setSelectReset(String selectReset) {
		this.selectReset = selectReset;
	}

	public void setSpecDescList2(ArrayList<String> specDescList2) {
		this.specDescList2 = specDescList2;
	}
	public String getSelQualCode() {
		return selQualCode;
	}

	public void setSelQualCode(String selQualCode) {
		this.selQualCode = selQualCode;
	}

	public String getSelQualPrevCode() {
		return selQualPrevCode;
	}

	public void setSelQualPrevCode(String selQualPrevCode) {
		this.selQualPrevCode = selQualPrevCode;
	}
	public void setSpecDescList(ArrayList<String> specDescList) {
		this.specDescList = specDescList;
	}

	public void setCategoryCodeList(ArrayList<String> categoryCodeList) {
		this.categoryCodeList = categoryCodeList;
	}

	public void setCategoryDescList(ArrayList<String> categoryDescList) {
		this.categoryDescList = categoryDescList;
	}

	public void setQualCodeList(ArrayList<String> qualCodeList) {
		this.qualCodeList = qualCodeList;
	}

	public void setQualDescList(ArrayList<String> qualDescList) {
		this.qualDescList = qualDescList;
	}
	
	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getSelCategoryCode() {
		return selCategoryCode;
	}
	public void setSelCategoryCode(String selCategoryCode) {
		this.selCategoryCode = selCategoryCode;
	}
	public String getSelCategoryCode1() {
		return selCategoryCode1;
	}
	public void setSelCategoryCode1(String selCategoryCode1) {
		this.selCategoryCode1 = selCategoryCode1;
	}
	public String getSelQualCode1() {
		return selQualCode1;
	}
	public void setSelQualCode1(String selQualCode1) {
		this.selQualCode1 = selQualCode1;
	}
	public String getSelSpecCode1() {
		return selSpecCode1;
	}
	public void setSelSpecCode1(String selSpecCode1) {
		this.selSpecCode1 = selSpecCode1;
	}
	public String getSelCategoryCode2() {
		return selCategoryCode2;
	}
	public void setSelCategoryCode2(String selCategoryCode2) {
		this.selCategoryCode2 = selCategoryCode2;
	}
	public String getSelQualCode2() {
		return selQualCode2;
	}
	public void setSelQualCode2(String selQualCode2) {
		this.selQualCode2 = selQualCode2;
	}
	public String getSelSpecCode2() {
		return selSpecCode2;
	}
	public void setSelSpecCode2(String selSpecCode2) {
		this.selSpecCode2 = selSpecCode2;
	}

	public void setQualCodeDescList2(ArrayList<String> qualCodeDescList2) throws Exception {
		  this.qualCodeDescList2 = qualCodeDescList2;
	}
	public String getSelSpecCode() {
		return selSpecCode;
	}

	public String getSelQualCodeDesc() {
		return selQualCodeDesc;
	}

	public void setSelQualCodeDesc(String selQualCodeDesc) {
		this.selQualCodeDesc = selQualCodeDesc;
	}

	public String getSelQualCode1Desc() {
		return selQualCode1Desc;
	}

	public void setSelQualCode1Desc(String selQualCode1Desc) {
		this.selQualCode1Desc = selQualCode1Desc;
	}

	public String getSelQualCode2Desc() {
		return selQualCode2Desc;
	}

	public void setSelQualCode2Desc(String selQualCode2Desc) {
		this.selQualCode2Desc = selQualCode2Desc;
	}

	public String getSelSpecCodeDesc() {
		return selSpecCodeDesc;
	}

	public void setSelSpecCodeDesc(String selSpecCodeDesc) {
		this.selSpecCodeDesc = selSpecCodeDesc;
	}

	public String getSelSpecCode1Desc() {
		return selSpecCode1Desc;
	}

	public void setSelSpecCode1Desc(String selSpecCode1Desc) {
		this.selSpecCode1Desc = selSpecCode1Desc;
	}

	public String getSelSpecCode2Desc() {
		return selSpecCode2Desc;
	}

	public void setSelSpecCode2Desc(String selSpecCode2Desc) {
		this.selSpecCode2Desc = selSpecCode2Desc;
	}

	public void setQualPrevCodeDescList(ArrayList<String> qualPrevCodeDescList) {
		this.qualPrevCodeDescList = qualPrevCodeDescList;
	}

	public String getQualUpload1() {
		return qualUpload1;
	}

	public void setQualUpload1(String qualUpload1) {
		this.qualUpload1 = qualUpload1;
	}

	public String getQualUpload2() {
		return qualUpload2;
	}

	public void setQualUpload2(String qualUpload2) {
		this.qualUpload2 = qualUpload2;
	}
	
	public String getQualUploadCode1() {
		return qualUploadCode1;
	}
	
	public void setQualUploadCode1(String qualUploadCode1) {
		this.qualUploadCode1 = qualUploadCode1;
	}
	
	public String getQualUploadCode2() {
		return qualUploadCode2;
	}

	public void setQualUploadCode2(String qualUploadCode2) {
		this.qualUploadCode2 = qualUploadCode2;
	}

	public String getQualUpload1Reason() {
		return qualUpload1Reason;
	}

	public void setQualUpload1Reason(String qualUpload1Reason) {
		this.qualUpload1Reason = qualUpload1Reason;
	}

	public String getQualUpload2Reason() {
		return qualUpload2Reason;
	}

	public void setQualUpload2Reason(String qualUpload2Reason) {
		this.qualUpload2Reason = qualUpload2Reason;
	}

	public static Log getLog() {
		return log;
	}

	public static void setLog(Log log) {
		StudentUploadForm.log = log;
	}

	public ArrayList<String> getQualPrevCodeList() {
		return qualPrevCodeList;
	}

	public void setQualPrevCodeList(ArrayList<String> qualPrevCodeList) {
		this.qualPrevCodeList = qualPrevCodeList;
	}

	public ArrayList<String> getQualPrevDescList() {
		return qualPrevDescList;
	}

	public void setQualPrevDescList(ArrayList<String> qualPrevDescList) {
		this.qualPrevDescList = qualPrevDescList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ArrayList<String> getQualPrevCodeDescList() {
		return qualPrevCodeDescList;
	}

	public void loadData(){
		//log.debug("studentuploadForm - loadData - LoadData");
		try {
			//log.debug("studentuploadForm - loadData - getAllRequiredDocs - Try: " + getStudent().getNumber() + " : " + getStudent().getAcademicYear() + " : " + getStudent().getAcademicPeriod() + " : Y : " + getStudent().getMatrix());
			DocDao docDao = new DocDao();
			requiredDocs = docDao.getAllRequiredDocs(getStudent().getNumber(),getStudent().getAcademicYear(),"Y",getStudent().getMatrix(), getStudent().isStuExist(), getLoginSelectMain());
		} catch (Exception e1) {
			//log.debug("studentuploadForm - loadData - getAllRequiredDocs - Catch: " + getStudent().getNumber() + " : " + getStudent().getAcademicYear() + " : " + getStudent().getAcademicPeriod() + " : Y : " + getStudent().getMatrix());
			e1.printStackTrace();
		}
		try {
			//log.debug("studentuploadForm - loadData - getAllOptionalDocs - Try: " + getStudent().getNumber() + " : " + getStudent().getAcademicYear() + " : " + getStudent().getAcademicPeriod() + " : Y : " + getStudent().getMatrix());
			DocDao docDao = new DocDao();
			optionalDocs = docDao.getAllOptionalDocs(getStudent().getNumber(),getStudent().getAcademicYear(),getStudent().getMatrix(), getStudent().isStuExist());
		} catch (Exception e1) {
			//log.debug("studentuploadForm - loadData - getAllOptionalDocs - Catch: " + getStudent().getNumber() + " : " + getStudent().getAcademicYear() + " : " + getStudent().getAcademicPeriod() + " : " + getStudent());
			e1.printStackTrace();
		}

		//log.debug("studentuploadForm - loadData - Checking RequiredDocs............");
		if (requiredDocs != null) {
			//log.debug("studentuploadForm - loadData - requiredDocs <> null............");
			int fileSize = requiredDocs.size();
			//log.debug("studentuploadForm - loadData - Instantiate Optional File Bean");
			requiredFileBeans = new FileBean[fileSize];
			
			int index = 0;
			for (Doc doc : requiredDocs) {
				FileBean fb = new FileBean();
				fb.setDoc(doc);
				requiredFileBeans[index++] = fb;
				try {
					SavedDocDao savedDocDao = new SavedDocDao();
					fb.setUploaded(savedDocDao.getSavedDocByDoc(doc.getDocCode(),getStudent().getNumber(),getStudent().getAcademicYear()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		//log.debug("studentuploadForm - loadData - Checking RequiredDocs............");
		if (optionalDocs != null) {
			//log.debug("studentuploadForm - loadData - optionalDocs <> null............");

			optionCodes.clear();
			for (Doc d : optionalDocs) {
				//log.debug("studentuploadForm - loadData - Getting optionalDoc Codes");
				optionCodes.add(d.getDocCode());
			}
		}
		if (optionalDocs != null) {
			optionLabels.clear();
			for (Doc d : optionalDocs) {
				//log.debug("studentuploadForm - loadData - Getting optionalDoc Labels");
				optionLabels.add(d.getDocDescription());
			}
		}
		
		//log.debug("studentuploadForm - loadData - Instantiate Optional File Bean");
		optionFileBean = new FileBean();
		//optional docs showing
   		   	desc.clear();
		    map.clear();
		    //log.debug("studentuploadForm - loadData - Map Cleared");
		   try {
			   SavedDocDao savedDocDao = new SavedDocDao();
			   //log.debug("studentuploadForm - loadData - Getting SavedDocDAOInfo");
			   savedDocDao.getAllNonRequiredDocInfo(desc, map, getStudent().getNumber(),getStudent().getAcademicYear(), getStudent().isStuExist(), getStudent().getMatrix());
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
		 //log.debug("studentuploadForm - loadData - Done");
	}

	public void reLoad() throws Exception{
		desc.clear();
		map.clear();
		SavedDocDao savedDocDao = new SavedDocDao();
		//log.debug("studentuploadForm - reLoad - Reloading uploaded docs..");
		savedDocDao.getAllNonRequiredDocInfo(desc, map, getStudent().getNumber(),getStudent().getAcademicYear(), getStudent().isStuExist(), getStudent().getMatrix());
		for(FileBean fb : requiredFileBeans){
			//log.debug("studentuploadForm - reLoad - Setting FileBean Uploaded..");
			fb.setUploaded(savedDocDao.getSavedDocByDoc(fb.getDoc().getDocCode(),getStudent().getNumber(),getStudent().getAcademicYear()));
		}
		//log.debug("studentuploadForm - reLoad - Done");
	}

	public boolean isHiddenButtonUpload() {
		//log.debug("studentuploadForm - isHiddenButtonUpload - Start");
		  try {
			  for(FileBean fb : requiredFileBeans) {
				  if(fb.getUploaded() == null ||fb.getUploaded().size() == 0 || fb.getUploaded().isEmpty()){
					  //log.debug("no file");
					  return true;
				  }
			 }
			  return false;
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  //log.debug("studentuploadForm - isHiddenButtonUpload - Done");
	  return hiddenButtonUpload;
	}

	public boolean isHiddenButton() {
		  try {
			  if(!getStudent().isStuExist()){
				  ////log.debug("no Student Number - New Student - Disable Document button: " + getStudent().getStuExist());
				  return true;
			  }
			  return false;
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	  return hiddenButton;
	}

	public void setHiddenButton(boolean hiddenButton) {
		this.hiddenButton = hiddenButton;
	}
	
	public void setHiddenButtonUpload(boolean hiddenButtonUpload) {
		//log.debug("studentuploadForm - SetHiddenButtonUpload");
		  this.hiddenButtonUpload = hiddenButtonUpload;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		ActionErrors errors = new ActionErrors();
		//log.debug("studentuploadForm - Validate................");
		return errors;
	}

	public void setFileBeans(FileBean[] requiredFileBeans) {
		//log.debug("studentuploadForm - setFileBeans");
		this.requiredFileBeans = requiredFileBeans;
		//log.debug("studentuploadForm - setFileBeans - Done");
	}

	public FileBean[] getFileBeans() {
		//log.debug("studentuploadForm - getFileBeans");
		return requiredFileBeans;
	}
	
	public FileBean[] getRequiredFileBeans() {
		return requiredFileBeans;
	}

	public void setRequiredFileBeans(FileBean[] requiredFileBeans) {
		this.requiredFileBeans = requiredFileBeans;
	}
	

	public List<Doc> getRequiredDocs() {
		//log.debug("studentuploadForm - getRequiredDocs");
		return requiredDocs;
	}

	public void setRequiredDocs(List<Doc> requiredDocs) {
		//log.debug("studentuploadForm - setRequiredDocs");
		this.requiredDocs = requiredDocs;
	}

	public List<Doc> getOptionalDocs() {
		//log.debug("studentuploadForm - getOptionalDocs");
		return optionalDocs;
	}

	public void setOptionalDocs(List<Doc> optionalDocs) {
		//log.debug("studentuploadForm - setOptionalDocs");
		this.optionalDocs = optionalDocs;
	}

	public List<String> getOptionLabels() {
		//log.debug("studentuploadForm - getOptionLabels");
		return optionLabels;
	}

	public void setOptionLabels(List<String> optionLabels) {
		//log.debug("studentuploadForm - setOptionLabels");
		this.optionLabels = optionLabels;
	}

	public List<String> getOptionCodes() {
		//log.debug("studentuploadForm - getOptionCodes");
		return optionCodes;
	}

	public void setOptionCodes(List<String> optionValues) {
		//log.debug("studentuploadForm - setOptionCodes");
		this.optionCodes = optionValues;
	}

	public FileBean getOptionFileBean() {
		//log.debug("studentuploadForm - getOptionFileBean");
		return optionFileBean;
	}

	public void setOptionFileBean(FileBean optionFileBean) {
		//log.debug("studentuploadForm - setOptionFileBean");
		this.optionFileBean = optionFileBean;
	}

	public List<String> getDesc() {
		//log.debug("studentuploadForm - getDesc");
		return desc;
	}

	public void setDesc(List<String> desc) {
		//log.debug("studentuploadForm - setDesc");
		this.desc = desc;
	}

	public Map<String, List<String>> getMap() {
		//log.debug("studentuploadForm - getMap");
		return map;
	}

	public void setMap(Map<String, List<String>> map) {
		//log.debug("studentuploadForm - setMap");
		this.map = map;
	}
	public String getDocMsg() {
		return docMsg;
	}

	public void setDocMsg(String docMsg) {
		this.docMsg = docMsg;
	}
	public String getWebUploadMsg() {
		return webUploadMsg;
	}

	public void setWebUploadMsg(String webUploadMsg) {
		this.webUploadMsg = webUploadMsg;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getNewFileName() {
		return fileNewName;
	}

	public void setNewFileName(String fileNewName) {
		this.fileNewName = fileNewName;
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public FormFile getTheFile() {
		return theFile;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	
	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getFileSelected() {
		return fileSelected;
	}

	public void setFileSelected(String fileSelected) {
		this.fileSelected = fileSelected;
	}

	public ArrayList<StudentFile> getStudentFiles() {
		return studentFiles;
	}
	
	public void setStudentFiles(ArrayList<StudentFile> studentFiles) {
		this.studentFiles = studentFiles;
	}
	
	
	public void resetFormFieldsOLD() {
		applyType = "";
		registrationType = "";
		allowLogin = true;
		student = new Student();
		qual = new Qualification();
		qualtwo = new Qualification();
		studentApplication = new Application();

		// Apply for student nr
		numberBack = null;
		timeBack = null;
		donesubmit = null;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;

		// 2014 New Login Select
		selected = "1";
		
		loginSelectMain = "";
		webLoginMsg = "";
		selectReset = "";
		
		selCategoryCode = null;
		selCategoryCode1 = null;
		selCategoryCode2 = null;
		
		selQualCode = null;
		selQualCode1 = null;
		selQualCode2 = null;
		
		selSpecCode = null;
		selSpecCode1 = null;
		selSpecCode2 = null;
		
		selQualPrevCode  = null;

		categoryCodeList = new ArrayList<String>();
		categoryCodeList1 = new ArrayList<String>();
		categoryCodeList2 = new ArrayList<String>();

		categoryDescList = new ArrayList<String>();
		categoryDescList1 = new ArrayList<String>();
		categoryDescList2 = new ArrayList<String>();
		
		qualCodeList = new ArrayList<String>();
		qualCodeList1 = new ArrayList<String>();
		qualCodeList2 = new ArrayList<String>();
		
		qualCodeDescList = new ArrayList<String>();
		qualCodeDescList1 = new ArrayList<String>();
		qualCodeDescList2 = new ArrayList<String>();
		
		qualDescList = new ArrayList<String>();
		qualDescList1 = new ArrayList<String>();
		qualDescList2 = new ArrayList<String>();
		
		specCodeList = new ArrayList<String>();
		specCodeList1 = new ArrayList<String>();
		specCodeList2 = new ArrayList<String>();
		
		specCodeDescList = new ArrayList<String>();
		specCodeDescList1 = new ArrayList<String>();
		specCodeDescList2 = new ArrayList<String>();
		
		specDescList = new ArrayList<String>();
		specDescList1 = new ArrayList<String>();
		specDescList2 = new ArrayList<String>();

		qualPrevCodeList = new ArrayList<String>();
		qualPrevDescList = new ArrayList<String>();
		qualPrevCodeDescList = new ArrayList<String>();
		
		//FileUpload
		//for showing optional docs

		desc = new ArrayList<String>();
		map = new HashMap<String, List<String>>();
			
		requiredDocs  = null;
		optionalDocs  = null;

		optionLabels = new ArrayList<String>();
		optionCodes = new ArrayList<String>();

		requiredFileBeans = null;
		optionFileBean = null;

		docMsg = "";

		hiddenButtonUpload = false;
		hiddenButton = false;

	}

	public void resetFormFields(){
		applyType = "";
		allowLogin = true;
		student = new Student();
		qual = new Qualification();
		qualtwo = new Qualification();
		studentApplication = new Application();

		donesubmit = null;
		String500back = null;

		//variable used to test session throughout process
		fromPage = null;

		// 2014 New Login Select
		selected = "1";
		
		webLoginMsg = "";
		selectReset = "";
		
		selQualCode1 = null;
		selQualCode2 = null;
		
		selSpecCode1 = null;
		selSpecCode2 = null;
		
		selQualPrevCode  = null;

		docMsg = "";
		
		student.setNumber("");
		student.setSurname("");
		student.setFirstnames("");
		student.setBirthYear("");
		student.setBirthMonth("");
		student.setBirthDay("");

		qualSTAAE01 = false;

		qualUpload1 = "";
		qualUpload2 = "";
		qualUploadCode1 = "";
		qualUploadCode2 = "";
		qualUpload1Reason = "";
		qualUpload2Reason = "";
	
		//FileUpload
		//for showing optional docs

		desc = new ArrayList<String>();
		map = new HashMap<String, List<String>>();
			
		requiredDocs  = null;
		optionalDocs  = null;

		optionLabels = new ArrayList<String>();
		optionCodes = new ArrayList<String>();

		requiredFileBeans = null;
		optionFileBean = null;

		docMsg = "";

		hiddenButtonUpload = false;
		hiddenButton = false;
	}
}

