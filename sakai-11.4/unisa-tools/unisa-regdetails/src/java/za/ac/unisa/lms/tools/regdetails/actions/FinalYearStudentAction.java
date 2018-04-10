//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.regdetails.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.regdetails.dao.FinalYearStudentQueryDAO;
import za.ac.unisa.lms.tools.regdetails.dao.RegQueryDAO;
import za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm;
import za.ac.unisa.lms.tools.regdetails.forms.StudyUnit;
import za.ac.unisa.utils.WorkflowFile;

/**
 * MyEclipse Struts
 * Creation date: 11-25-2005
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class FinalYearStudentAction extends LookupDispatchAction {

	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;
	
	// --------------------------------------------------------- Methods

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("button.back", "back");
		map.put("button.cancel", "cancel");
		map.put("button.continue", "nextStep");
		map.put("button.save", "save");
		map.put("step1", "step1");
		map.put("step2", "step2");
		map.put("step3", "step3");
		map.put("nextStep", "nextStep");
		map.put("button.regdetails", "cancel");

		return map;
	}

	public ActionForward step1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;

		try {
			
			FinalYearStudentQueryDAO db = new FinalYearStudentQueryDAO();

			// test application requirement: average >= 55%
			regDetailsForm.setModuleCount(db.getNumberOfModulesPassed(regDetailsForm.getStudentNr(), regDetailsForm.getQual().getQualCode()));
			double average = db.getAverageModulePercentage(regDetailsForm.getStudentNr(), regDetailsForm.getQual().getQualCode());
			if(average< Double.valueOf("55")){
				return mapping.findForward("requirementerror");
			}else{
				DecimalFormat df = new DecimalFormat("#.##");
				regDetailsForm.setModuleAverage(df.format(average));
			}
			
			/* clear list */
			ArrayList<StudyUnit> suList = new ArrayList<StudyUnit>();
			// add two study units
			StudyUnit su = new StudyUnit();
			su.setCode("");
			suList.add(su);
			su= new StudyUnit();
			su.setCode("");
			suList.add(su);
			regDetailsForm.setFinalYearStudyUnits(suList);

		} catch (Exception e) {
			throw e;
		}

		return mapping.findForward("step1");
	}
	
	public String step2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		StudyUnit su = new StudyUnit();
		
		// validate input
		String inputSu1 = request.getParameter("su1");
		String inputSu2 = request.getParameter("su2");

		// If both study units empty, give error
		if( inputSu1== null ||  "".equals(inputSu1.trim()) ){
			// 1st study unit empty, check second
			if( inputSu2== null ||  "".equals(inputSu2.trim()) ){
				// 2nd study unit empty, give error
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
				"Please enter at least one study unit code"));
				addErrors(request, messages);
				return "step1";
			}
		}else{
			// test length: study unit 1
			if(inputSu1.length() != 7){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"Invalid study unit code "+ inputSu1));
				addErrors(request, messages);
				return "step1"; 
			}
		}	
		// test length: study unit 2
		if( inputSu2!= null &&  !"".equals(inputSu2.trim()) ){
			if(inputSu2.length() != 7){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"Invalid study unit code "+ inputSu2));
				addErrors(request, messages);
				return "step1"; 
			}
		}
		// test for duplicates if applicable
		if( inputSu2!= null && !"".equals(inputSu2.trim())) {
			if ((inputSu1!= null && !"".equals(inputSu1)) && inputSu2.equalsIgnoreCase(inputSu1) ){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
				"Study unit codes cannot be identical"));
				addErrors(request, messages);
				return "step1";
			}
		}

		//Get study unit details
		ArrayList<StudyUnit> list = new ArrayList<StudyUnit>();
		su = new StudyUnit();
		if(inputSu1!= null && ! "".equals(inputSu1.trim()) ){
			// get study unit details
			su.setCode(inputSu1);
			String result = getStudyUnitDetails(su);
			if (!"".equals(result)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						result));
				addErrors(request, messages);
				return "step1";
			}else{
				list.add(su);
			}
		}
		su = new StudyUnit();
		if(inputSu2!= null && ! "".equals(inputSu2.trim()) ){
			// get study unit details
			su.setCode(inputSu2);
			String result = getStudyUnitDetails(su);
			if (!"".equals(result)){
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						result));
				addErrors(request, messages);
				return "step1";
			}else{
				list.add(su);
			}
		}	

		// add study units to form list
		regDetailsForm.setFinalYearStudyUnits(list);

		return "step2";
	}
	
	private String getStudyUnitDetails(StudyUnit su) throws Exception{
		
		
		FinalYearStudentQueryDAO db = new FinalYearStudentQueryDAO();
		
		// upper case study unit code
		su.setCode(su.getCode().toUpperCase());
		//get study unit description
		su.setDesc(db.getStudyDesc(su.getCode()));
		if ("".equals(su.getDesc())){
			return "Invalid study unit code "+ su.getCode();
		}
		String lang = "";// get language
		lang = db.getStudyUnitLanguage(su.getCode());
		if("E".equals(lang) || "A".equals(lang)){

		}else {
			// language can be E or A
			su.setLanguage("");
		}
		// get registration period
		db.getStudyUnitRegistrationPeriod(su);
		
		return "";
		
	}

	/*public String confirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		String[] list = regDetailsForm.getSelectedItems();
		ArrayList cancelList = new ArrayList();
		ActionMessages messages = new ActionMessages();

		

		return "confirm";
	}*/

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);

		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		ActionMessages messages = new ActionMessages();
		
		if (regDetailsForm.getStudentNr()==null || "".equals(regDetailsForm.getStudentNr())){
			/* not suppose to happen, except if session expired */
			return (mapping.findForward("step1"));
		}
		if ((regDetailsForm.getSu1()==null && regDetailsForm.getSu2()==null) || ("".equals(regDetailsForm.getSu1()) && "".equals(regDetailsForm.getSu2()) )){
			/* not suppose to happen, except if session expired */
			return (mapping.findForward("step1"));
		}
		
		// study unit list can't be empty
		if (regDetailsForm.getFinalYearStudyUnits().size() == 0){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"You have to specify at least one study unit."));
			addErrors(request, messages);
			return mapping.findForward("step2");
			
		}
		
		writeWorkflow(regDetailsForm, request);

		eventTrackingService.post(eventTrackingService.newEvent(
				EventTrackingTypes.EVENT_REGDETAILS_FINALYEAR, toolManager
						.getCurrentPlacement().getContext()
						+ "/" + regDetailsForm.getStudentNr(), false),
				usageSession);

		RegQueryDAO db = new RegQueryDAO();
		db.writeAuditTrail(regDetailsForm.getStudentNr(), "FA");
		reset(regDetailsForm);

		return mapping.findForward("saved");
	}
	
	public ActionForward remove (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response, int index) throws Exception {
		
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		regDetailsForm.getFinalYearStudyUnits().remove(index);
		
		return mapping.findForward("step2");
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String nextPage = "";
		if ("1".equalsIgnoreCase(request.getParameter("goto"))) {
			return mapping.findForward("step1");
		} else if ("2".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = step2(mapping, form, request, response);
		} else if ("3".equalsIgnoreCase(request.getParameter("goto"))) {
			return save(mapping, form, request, response);
		} else if ("home".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = "home";
		}


		return mapping.findForward(nextPage);
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		reset(regDetailsForm);

		return mapping.findForward("cancel");
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String prevPage = "";

		if ("3".equalsIgnoreCase(request.getParameter("goto"))) {
			/* clear list */
			prevPage = "step1";
		} else if ("4".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "cancel";
		} else if ("home".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "home";
		}

		return mapping.findForward(prevPage);
	}

	private void reset(RegDetailsForm regDetailsForm) {
		regDetailsForm.setSu1("");
		regDetailsForm.setSu2("");
		//regDetailsForm.setFinalYearStudyUnits(new ArrayList<StudyUnit>());
		regDetailsForm.setSubmissionTimeStamp("");
	}

	/**
	 * This method writes the workflow file to the path specified
	 * in the following file: sakai/jakarta-tomcat-5.5.9/sakai/sakai.properties
	 *
	 * @param regDetailsForm
	 *            The form associated with the action
	 * @param request
	 * @param moduleCount The total number of modules passed
	 * @param average     The average percentage for all modules passed    
	 */
	private void writeWorkflow(RegDetailsForm regDetailsForm,
			HttpServletRequest request) throws Exception {

		String date = (new java.text.SimpleDateFormat(
				"EEEEE dd MMMMM yyyy hh:mm:ss").format(new java.util.Date())
				.toString());
		/* add date and time to request to display on page */
		request.setAttribute("stamp", date);

		/* write to file */
		WorkflowFile file = new WorkflowFile("final_year_courses_"
				+ regDetailsForm.getStudentNr());

		file.add(" The following request was received through myUnisa. \r\n\r\n");
		file.add(" Type of request: Application for final year student requesting registration for 12 modules \r\n\r\n");
		file.add(" Request received on: " + date + "\r\n\r\n");
		file.add(" Student number                              = " + regDetailsForm.getStudentNr()
				+ "\r\n");
		file.add(" Student name                                = "
				+ regDetailsForm.getStudentName() + "\r\n");
		file.add(" Qualification code                          = "
				+ regDetailsForm.getQual().getQualCode() + " "
				+ regDetailsForm.getQual().getQualDesc() + "\r\n");
		file.add(" Specialisation code                          = "
				+ regDetailsForm.getQual().getSpecCode() + " "
				+ regDetailsForm.getQual().getSpecDesc() + "\r\n");
		file.add(" Number of modules passed                   = "
				+ regDetailsForm.getModuleCount() + "\r\n");
		file.add(" Average percentage for all previous modules = "
				+ regDetailsForm.getModuleAverage() + "\r\n");
		file.add(" -------------------------------------------------------------------- \r\n");
		file.add(" Submitted Details: \r\n\r\n");
		file.add(" Study unit\t Period\t Language\r\n");
		for (int i = 0; i < regDetailsForm.getFinalYearStudyUnits().size(); i++) {
			StudyUnit su = new StudyUnit();
			String lang = "";
			su = (StudyUnit) regDetailsForm.getFinalYearStudyUnits(i);
			if (su.getLanguage()== null || "".equals(su.getLanguage())){
				log.error("ADDITION lang empty " +su.toString());
				su.setLanguage("E");
			}

			if ("A".equalsIgnoreCase(su.getLanguage().substring(0,1))){
				lang = "Afr";
			}else {
				lang = "Eng";
			}
			file.add(" "+su.getCode() +"\t " + su.getRegPeriod()+"\t " + lang +"\r\n");
		}
		file.add(" -------------------------------------------------------------------- \r\n");

		file.close();

	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Iterator i = request.getParameterMap().keySet().iterator();

		while (i.hasNext()) {
			String name = (String) i.next();
			if (name.startsWith("action.remove[")) {
				int startpos = name.indexOf("[");
				int endpos = name.indexOf("]");
				String number = name.substring(startpos + 1, endpos);
				int index = Integer.parseInt(number);

				return remove(mapping, form, request, response, index);
			}
		}

		return super.execute(mapping, form, request, response);
	}
	
	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("FinalYearStudent: unspecified method call -no value for parameter in request");
		return mapping.findForward("step1");

	}

}

