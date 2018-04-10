//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.regdetails.actions;

import java.util.ArrayList;
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
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.component.cover.ComponentManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.regdetails.dao.RegQueryDAO;
import za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm;
import za.ac.unisa.lms.tools.regdetails.forms.StudyUnit;
import za.ac.unisa.utils.WorkflowFile;

/**
 * MyEclipse Struts
 * Creation date: 11-29-2005
 *
 * XDoclet definition:
 * @struts:action path="/exchange" name="regDetailsForm" parameter="action"
 */
public class ExchangeAction extends LookupDispatchAction {

	// --------------------------------------------------------- Instance
	// Variables
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
		map.put("step2", " ");
		map.put("step3", "step3");
		map.put("step4", "step4");
		map.put("nextStep", "nextStep");
		map.put("button.regdetails", "cancel");

		return map;
	}

	public ActionForward step1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		boolean semestersFound = false;
		try {
			String[] selectedItems = {};
			regDetailsForm.setSelectedItems(selectedItems);
			regDetailsForm.setExchangeStudyUnits(new ArrayList());
			/* check that semster study units are present in studyunit list */
			for (int i = 0; i < regDetailsForm.getStudyUnits().size(); i++) {
				StudyUnit su = new StudyUnit();
				su = (StudyUnit) regDetailsForm.getStudyUnits().get(i);
				//log.debug("ExhangeAction - step1 - Study Unit="+su+", RegPeriod="+su.getRegPeriod());
				if ("1".equalsIgnoreCase(su.getRegPeriod())
						|| "2".equalsIgnoreCase(su.getRegPeriod())) {
					semestersFound = true;
					break;
				}
			}
			if (!semestersFound) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"No semester study units registered."));
				addErrors(request, messages);
				return mapping.findForward("step1");
			}

			/* do tests for valid period on regdat */
			RegQueryDAO db = new RegQueryDAO();
			if (db.isRegPeriodValid("EX", "1")) {
				regDetailsForm.setExSem1("Y");
			} else {
				regDetailsForm.setExSem1("N");
			}
			if (db.isRegPeriodValid("EX", "2")) {
				regDetailsForm.setExSem2("Y");
			} else {
				regDetailsForm.setExSem2("N");
			}
		} catch (Exception e) {
			throw e;
		}

		//log.debug("ExhangeAction - step1 - ExSem1="+regDetailsForm.getExSem1()+", ExSem2="+regDetailsForm.getExSem2());

		
		return mapping.findForward("step1");
	}

	public String confirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		ArrayList exchangeList = new ArrayList();
		ActionMessages messages = new ActionMessages();

		try {
			String[] list = regDetailsForm.getSelectedItems();
			/* Validate input */
			if (regDetailsForm.getSelectedItems().length == 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"You have not selected any study units."));
				addErrors(request, messages);
				return "step1";
			}
			/* read currently registered study units into echange list */
			for (int i = 0; i < list.length; i++) {
				StudyUnit su = new StudyUnit();
				StudyUnit suExchange = new StudyUnit();
				su = (StudyUnit) regDetailsForm.getPickList().get(
						Integer.parseInt(list[i]));
				suExchange.setCode(su.getCode());
				suExchange.setDesc(su.getDesc());
				if ("1".equalsIgnoreCase(su.getRegPeriod())) {
					suExchange.setRegPeriod("2");
				} else if ("2".equalsIgnoreCase(su.getRegPeriod())) {
					suExchange.setRegPeriod("1");
				}
				exchangeList.add(suExchange);
			}
			regDetailsForm.setExchangeStudyUnits(exchangeList);
		} catch (Exception e) {
			throw e;
		}

		return "confirm";
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		writeWorkflow(regDetailsForm, request);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		// UsageSessionService.startSession(regDetailsForm.getStudentNr(),request.getRemoteAddr(),request.getHeader("user-agent"));

		eventTrackingService.post(eventTrackingService.newEvent(
				EventTrackingTypes.EVENT_REGDETAILS_SEMESTEREXCHANGE,
				toolManager.getCurrentPlacement().getContext() + "/"
						+ regDetailsForm.getStudentNr(), false), usageSession);

		RegQueryDAO db = new RegQueryDAO();
		db.writeAuditTrail(regDetailsForm.getStudentNr(), "IX");
		reset(regDetailsForm);

		return mapping.findForward("saved");
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String nextPage = "";
		if ("1".equalsIgnoreCase(request.getParameter("goto"))) {
			return mapping.findForward("step1");
		} else if ("2".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = confirm(mapping, form, request, response);
		}

		return mapping.findForward(nextPage);
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		reset(regDetailsForm);
		String[] selectedItems = {};
		regDetailsForm.setSelectedItems(selectedItems);
		regDetailsForm.setExchangeStudyUnits(new ArrayList());

		return mapping.findForward("cancel");
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String prevPage = "";
		RegDetailsForm regDetailsForm = (RegDetailsForm) form;

		if ("2".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "step1";
		} else if ("3".equalsIgnoreCase(request.getParameter("goto"))) {
			String[] selectedItems = {};
			regDetailsForm.setSelectedItems(selectedItems);
			regDetailsForm.setExchangeStudyUnits(new ArrayList());
			prevPage = "step1";
		} else if ("home".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "cancel";
		}

		return mapping.findForward(prevPage);
	}

	private void reset(RegDetailsForm regDetailsForm) {
		String[] selectedItems = {};
		regDetailsForm.setSelectedItems(selectedItems);
		regDetailsForm.setExchangeStudyUnits(new ArrayList());

	}

	/**
	 * This method writes the cancellation workflow file to the path specified
	 * in the following file: sakai/jakarta-tomcat-5.5.9/sakai/sakai.properties
	 *
	 * @param regDetailsForm
	 *            The form associated with the action
	 */
	private void writeWorkflow(RegDetailsForm regDetailsForm,
			HttpServletRequest request) throws Exception {

		String date = (new java.text.SimpleDateFormat(
				"EEEEE dd MMMMM yyyy hh:mm:ss").format(new java.util.Date())
				.toString());
		/* add date and time to request to display on page */
		request.setAttribute("stamp", date);

		/* write to file */
		WorkflowFile file = new WorkflowFile("exchange_semesters_"
				+ regDetailsForm.getStudentNr());

		file.add(" The following request was received through myUnisa. \r\n\r\n");
		file.add(" Type of request: Application for Exchange of semester(s) \r\n\r\n");
		file.add(" Request received on: " + date + "\r\n\r\n");
		file.add(" Student number          = " + regDetailsForm.getStudentNr()
				+ "\r\n");
		file.add(" Student name            = "
				+ regDetailsForm.getStudentName() + "\r\n");
		file.add(" Qualification code      = "
				+ regDetailsForm.getQual().getQualCode() + " "
				+ regDetailsForm.getQual().getQualDesc() + "\r\n");
		file.add(" -------------------------------------------------------------------- \r\n");
		file.add(" Submitted Details: \r\n\r\n");
		file.add(" Study unit(s) and semester(s) for which the student wishes to exchange: \r\n\r\n");
		for (int i = 0; i < regDetailsForm.getExchangeStudyUnits().size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) regDetailsForm.getExchangeStudyUnits().get(i);
			file.add(" " + su.getCode() + " exchange to semester "
					+ su.getRegPeriod() + "\r\n");
		}
		file.add(" -------------------------------------------------------------------- \r\n");

		file.close();

	}
}