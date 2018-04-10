package za.ac.unisa.lms.tools.assmarkerreallocation.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.ModuleDAO;
//import za.ac.unisa.lms.dao.assessmentCriteria.AssessmentCriteriaDAO;
import za.ac.unisa.lms.domain.assessmentCriteria.Assignment;
//import za.ac.unisa.lms.domain.assessmentCriteria.Marker;
import za.ac.unisa.lms.domain.general.StudyUnit;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.*;
import za.ac.unisa.lms.tools.assmarkerreallocation.dao.*;
import za.ac.unisa.utils.CoursePeriodLookup;
public class AssMarkerReallocationAction extends LookupDispatchAction{
	
	private SessionManager sessionManager;
	private UserDirectoryService userDirectoryService;
	
	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;
	
		operListener() {
			exception = null;
		}
	
		public Exception getException() {
			return exception;
		}
	
		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			            exception = new Exception(aEvent.getActionCommand());
		}
	}
	
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");
		map.put("button.continue","displayAssessmentList");
		map.put("viewAssessmentStats","viewAssessmentStats");
		map.put("editAssMarkerScreen","editAssMarkerScreen");
		map.put("button.update", "updateAssMarkerDetail");
		map.put("button.cancel", "cancel");
		map.put("back", "back");
		map.put("button.back", "back");
		return map;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
           ActionMessages messages = new ActionMessages();	
		AssMarkerReallocationForm assMarkerForm = (AssMarkerReallocationForm) form;		
		
		//initialise input values
		assMarkerForm.setAcadYear("");
		assMarkerForm.setAssignmentNr("");
		assMarkerForm.setStudyUnit("");
		assMarkerForm.setSemester("");
		assMarkerForm.setUserId("");
		assMarkerForm.setUpdateAllowed(false);
		
		//Get user details		
		User user = null;
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
	    userDirectoryService = (UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID  = currentSession.getUserId();
				
		if (userID != null) {
			user = userDirectoryService.getUser(userID);
			assMarkerForm.setUserId(user.getEid().toUpperCase());
		}else{
			   return mapping.findForward("userUnknown");
		}
		String networkcode=assMarkerForm.getUserId();
		if (networkcode.equalsIgnoreCase("")){
			return mapping.findForward("userUnknown");
		}
		LoginDAO  assMarkerdao=new LoginDAO();
		/*if (!assMarkerdao.isNetworkCodeValid(networkcode.trim().toUpperCase())){
			   return mapping.findForward("userUnknown");
		}*/
		//Get semester list
		List list = new ArrayList();
		StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
		for (int i=0; i < dao.getGenCodes((short)54,1).size(); i++) {
			list.add(i, (Gencod)(dao.getGenCodes((short)54,1).get(i)));
		}
		assMarkerForm.setListSemester(list);
		int   currYear= yearInt();
		AcademicPeriodDetails  apd1=new AcademicPeriodDetails();
		apd1.setYear(""+(currYear-1));
		AcademicPeriodDetails  apd2=new AcademicPeriodDetails();
		apd2.setYear(""+currYear);
		AcademicPeriodDetails  apd3=new AcademicPeriodDetails();
		apd3.setYear(""+(currYear+1));
		List listYear=new ArrayList();
		listYear.add(apd1);
		listYear.add(apd2);
		listYear.add(apd3);
		assMarkerForm.setListYear(listYear);
		return mapping.findForward("input");	
	}
	 public int  yearInt(){
	     GregorianCalendar calCurrent = new GregorianCalendar();
	     return   calCurrent.get(Calendar.YEAR);
	}
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		AssMarkerReallocationForm assMarkerForm = (AssMarkerReallocationForm) form;		
						
		return mapping.findForward("input");	
	}
	private StudyUnit getStudyUnitRec(String studyUnit) throws Exception{
		            ModuleDAO dao = new ModuleDAO();	
		            StudyUnit  studyUnitRec=new StudyUnit();
		            studyUnitRec = dao.getStudyUnit(studyUnit);
		          return studyUnitRec;
	}
	public ActionForward displayAssessmentList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
		         AssMarkerReallocationForm assMarkerForm = (AssMarkerReallocationForm) form;	
		         ActionMessages messages = new ActionMessages();	
		         String academicYear=assMarkerForm.getAcadYear();
		         String semester=assMarkerForm.getSemester();
		         assMarkerForm.setStudyUnit(assMarkerForm.getStudyUnit().toUpperCase());
		         String  studyUnit=assMarkerForm.getStudyUnit();
		         String userId=assMarkerForm.getUserId();
		         StudyUnit studyUnitRec=getStudyUnitRec(studyUnit);
		         assMarkerForm.setStudyUnitRecord(studyUnitRec);
		         Assessment assessment= new Assessment();
			     List  validationErrorsList=assessment.validateInputData(academicYear, semester, studyUnit);
		 		 if (!validationErrorsList.isEmpty()) {
			                 Utilities.addErrorMessages(messages,validationErrorsList);
			                 addErrors(request,messages);
			                 return mapping.findForward("input");				
		         }
		         CoursePeriodLookup periodLookup = new CoursePeriodLookup();
				 assMarkerForm.setSemesterType(periodLookup.getCourseTypeAsString(assMarkerForm.getSemester()));
				 List assessmentList=assessment.getAssessmentList(academicYear, semester, studyUnit);
				 Marker marker=new Marker(academicYear,semester,studyUnit);
			     assMarkerForm.setUpdateAllowed(marker.isAuthorised(userId));
				 assMarkerForm.setAssessmentList(assessmentList);
				 return mapping.findForward("assessmentlist");	
	}
	public ActionForward back(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
		         return mapping.findForward("assessmentlist");	
	}
	public ActionForward editAssMarkerScreen(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)  throws Exception{
		          AssMarkerReallocationForm assMarkerForm = (AssMarkerReallocationForm) form;	
		          makeAssessmentStatsScreen(assMarkerForm);
		              return mapping.findForward("assMarkerDetail");
	}
	public ActionForward viewAssessmentStats(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)  throws Exception {
		               AssMarkerReallocationForm assMarkerForm = (AssMarkerReallocationForm) form;	
	  	               makeAssessmentStatsScreen(assMarkerForm);
		               return mapping.findForward("assMarkerDetail");
		        
   }
	private void makeAssessmentStatsScreen(AssMarkerReallocationForm assMarkerForm) throws Exception {
		                      List assmentList=assMarkerForm.getAssessmentList();
		                      String assNumber=assMarkerForm.getAssignmentNr();
	                          int uniqueNum=Assessment.getUniqueAssignmentNum(assmentList,Integer.parseInt(assNumber));
	                          assMarkerForm.setUniqueNr(""+uniqueNum);
                              String academicYear=assMarkerForm.getAcadYear();
                              String semester=assMarkerForm.getSemester();
                              String studyUnit=assMarkerForm.getStudyUnit();
                              if((assMarkerForm.getStudyUnit()!=null)&&(assMarkerForm.getStudyUnit().trim().equals(""))){
                            	     assMarkerForm.setStudyUnit(assMarkerForm.getStudyUnit());
                              }
                              Assessment assessment= new Assessment(academicYear,semester,studyUnit,(""+uniqueNum),assNumber);
		                      String extentions = assessment.getAssessmentFileExt();
		                      assMarkerForm.setFileFormats(extentions);
		                      int studentRegistered=assessment.getNumOfStudentsRegistered();
		                      assMarkerForm.setStudentsRegistered(studentRegistered);
		                      MarkingLanguage markingLanguage=new MarkingLanguage();
		                      String assessmentFormat=assessment.getAssessmentFormat(uniqueNum);
		                      //if(assessmentFormat.equalsIgnoreCase("myUnisa File Submission Only")){
		                    	       List  list=markingLanguage.getLangLinkedWithUniqueAssNum(uniqueNum);
		 		                       assMarkerForm.setMarkingLanguageList(list);
		 		                       assMarkerForm.setMarkingLanguageListAsString(list);
		 		                     
		                      /*}else{
		                    	      assMarkerForm.setMarkingLanguageList(new java.util.ArrayList());
		                    	      assMarkerForm.setMarkingLanguageListAsString(new java.util.ArrayList());
		                    	      assMarkerForm.setDisplayLang(false);
		                      }*/
		                      List listMarkerDetail=assessment.getMarkingDetailList(assMarkerForm.isUpdateAllowed(), assMarkerForm.isDisplayLang());
		                      assMarkerForm.setListMarkerDetail(listMarkerDetail);
	}
	public ActionForward updateAssMarkerDetail (
			                ActionMapping mapping,
			                ActionForm form,
			                HttpServletRequest request,
			                HttpServletResponse response) throws Exception{
		                           ActionMessages messages = new ActionMessages();
		                           AssMarkerReallocationForm assMarkerForm = (AssMarkerReallocationForm) form;
		                           MarkingDetailValidator markingDetailValidator=new MarkingDetailValidator(); boolean markingLangExist=true;
	                                  if((assMarkerForm.getMarkingLanguageList()==null)||assMarkerForm.getMarkingLanguageList().isEmpty()){
                           	           markingLangExist=false;  
                                       }
		                           List  markingDetailsList=assMarkerForm.getListMarkerDetail();
                                   String errorMsg=markingDetailValidator.validate(markingDetailsList,markingLangExist);
		                           if (!errorMsg.equals("")) {
                                             Utilities.addErrorMessage(messages,errorMsg);
                                             addErrors(request,messages);
                                                   return mapping.findForward("assMarkerDetail");				
                                  }
		                          String academicYear=assMarkerForm.getAcadYear();
                                  String semester=assMarkerForm.getSemester();
                                  String studyUnit=assMarkerForm.getStudyUnit();
                                  String  uniqueNum=assMarkerForm.getUniqueNr();
                                  MarkingDetail markingDetail=new MarkingDetail(academicYear,semester,studyUnit);
                                 
		                          markingDetail.updateMarkingRecords(markingDetailsList,uniqueNum,markingLangExist);
		                   messages.add(ActionMessages.GLOBAL_MESSAGE,
				                            new ActionMessage("message.generalmessage",
						                     "Mark allocation percentages have been successfully updated."));		
		                   addMessages(request, messages);
		                   return mapping.findForward("assessmentlist");
		}

}
