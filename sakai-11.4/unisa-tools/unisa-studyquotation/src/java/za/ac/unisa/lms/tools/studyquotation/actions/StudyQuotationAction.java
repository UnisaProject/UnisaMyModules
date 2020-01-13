//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studyquotation.actions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.studyquotation.StudyQuotation;
import za.ac.unisa.lms.studyquotation.StudyQuotationService;
import za.ac.unisa.lms.tools.studyquotation.dao.StudyQuoteQueryDAO;
import za.ac.unisa.lms.tools.studyquotation.forms.StudyQuotationForm;

public class StudyQuotationAction extends LookupDispatchAction {

	private SessionManager sessionManager;
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("studyquote.button.display", "studyQuotationDisplay");
		map.put("studyQuotationInput", "studyQuotationInput");
		map.put("studyquote.button.clear", "studyQuotationInput");
		map.put("studyquote.button.back", "back");
		return map;
	}

	public ActionForward studyQuotationInput(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response
	) throws Exception {
		StudyQuotationForm studyQuotationForm = (StudyQuotationForm)form;
		// Check whether quotations dates are open
		StudyQuoteQueryDAO dao = new StudyQuoteQueryDAO();
		studyQuotationForm.setShowDisclaimer(false);
		studyQuotationForm.setDisclaimerMessage("");
		short validYear = dao.getValidQuotationYear();
		if (validYear==0){
			return mapping.findForward("closed");
		}else{
			//Change 20181114 - Study fees not yet available for the next year
			StudentSystemGeneralDAO daoGen = new StudentSystemGeneralDAO();			
			Gencod gencod = new Gencod();
			gencod = daoGen.getGenCode("316", "QUOTEDISCL");
			if (gencod != null && gencod.getAfrDescription()!= null && gencod.getAfrDescription().trim().equalsIgnoreCase("Y")){
				/*Temporary disable additional links*/
				studyQuotationForm.setShowDisclaimer(true);
				int currentYear= Calendar.getInstance().get(Calendar.YEAR);
				int  nextYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
				String disclaimerMessage = "NB: This fee information is for the " + currentYear + " academic year only.  Unisa's student fee information for " + nextYear + " will only be available on 1 January " + nextYear + ".";
				studyQuotationForm.setDisclaimerMessage(disclaimerMessage);
			}
			
			StudyQuotation studyQuotation = new StudyQuotation();
			studyQuotation.reset();
			// Set year to valid year returned from db: regdat
			studyQuotation.setAcademicYear(validYear);
			studyQuotationForm.setStudyQuotation(studyQuotation);
			return mapping.findForward("study-quotation-input");
		}
	}

	public ActionForward back(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

			return mapping.findForward("study-quotation-input");
}

	public ActionForward studyQuotationDisplay(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response
	              ) {
		ActionMessages messages = new ActionMessages();
		try{

		/* Get user details, set in default action for event log */
		String currentUserID = "";
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		Session currentSession = sessionManager.getCurrentSession();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		
		String userID = currentSession.getUserId();
		if (userID != null) {
			currentUserID = currentSession.getUserEid();
		}

		StudyQuotationService studyQuotationService = new StudyQuotationService();
		StudyQuotationForm studyQuotationForm = (StudyQuotationForm)form;
		StudyQuotation studyQuotation = studyQuotationForm.getStudyQuotation();
		

		//Validate academic year input
		messages = (ActionMessages) studyQuotationForm.validate(mapping, request);
		if (!messages.isEmpty()) {
			addErrors(request, messages);
			//studyQuotation.reset();
			return mapping.findForward("study-quotation-input");
		}
		
		studyQuotation.setQualificationCode(studyQuotation.getQualificationCode().trim());
		if (studyQuotation.getQualificationCode().length()> 5){
			messages.add(
	    			ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.coolgenerror", "Qualification code may only be 5 characters long."));
			    addErrors(request, messages);
			    return mapping.findForward("study-quotation-input");
		}

		/* at least one study unit must be entered */
		if ("".equals(studyQuotation.getStudyCode1().trim()) && "".equals(studyQuotation.getStudyCode2().trim())
				&& "".equals(studyQuotation.getStudyCode3().trim()) && "".equals(studyQuotation.getStudyCode4().trim())
				&& "".equals(studyQuotation.getStudyCode5().trim()) && "".equals(studyQuotation.getStudyCode6().trim())
				&& "".equals(studyQuotation.getStudyCode7().trim()) && "".equals(studyQuotation.getStudyCode8().trim())
				&& "".equals(studyQuotation.getStudyCode9().trim()) && "".equals(studyQuotation.getStudyCode10().trim())){
			if ("".equals(studyQuotation.getStudyCode11().trim()) && "".equals(studyQuotation.getStudyCode12().trim())
					&& "".equals(studyQuotation.getStudyCode13().trim()) && "".equals(studyQuotation.getStudyCode14().trim())
					&& "".equals(studyQuotation.getStudyCode15().trim()) && "".equals(studyQuotation.getStudyCode16().trim())
					&& "".equals(studyQuotation.getStudyCode17().trim()) && "".equals(studyQuotation.getStudyCode18().trim())
			){
				messages.add(
	    			ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.commonerr", "At least one study unit"));
			    addErrors(request, messages);
			    return mapping.findForward("study-quotation-input");
			}
		}		
		studyQuotationService.setStudyQuotation(studyQuotation);
		int result = studyQuotationService.calculateStudyQuotation();

		if (result == StudyQuotationService.QUAL_CODE_MISSING) {
			messages.add(
				ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("errors.commonerr", "Qualification Code")
			);
			addErrors(request, messages);
			return mapping.findForward("study-quotation-input");
		} else if (result == StudyQuotationService.COOLGEN_ERROR) {
			messages.add(
    			ActionMessages.GLOBAL_MESSAGE,
			    new ActionMessage("errors.coolgenerror", studyQuotationService.getErrorMessage())
		    );
		    addErrors(request, messages);
		    return mapping.findForward("study-quotation-input");
		}
		// Write log with usage session if user code is not empty
		if(currentUserID != null && !"".equals(currentUserID)){
			UsageSession usageSession = usageSessionService.startSession(currentUserID,request.getRemoteAddr(),request.getHeader("user-agent"));
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDYQUOTATION_VIEW, toolManager.getCurrentPlacement().getContext(), false),usageSession);
		}else{
			eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDYQUOTATION_VIEW, toolManager.getCurrentPlacement().getContext(), false));
		}

		return mapping.findForward("study-quotation-display");
		}catch(Exception ex){   
			                   String errorMessage="An unexpected error has happened.Error :";
			                   messages.add(ActionMessages.GLOBAL_MESSAGE,
					                        new ActionMessage("errors.error",errorMessage+ex.getMessage()));
				               addErrors(request, messages);
				return mapping.findForward("study-quotation-input");
			
		}
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("StudyQuotationAction: unspecified method call -no value for parameter in request");
		return mapping.findForward("study-quotation-input");

	}
}