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

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.regdetails.dao.RegQueryDAO;
import za.ac.unisa.lms.tools.regdetails.forms.RegDetailsForm;
import za.ac.unisa.lms.tools.regdetails.forms.StudyUnit;
import za.ac.unisa.utils.WorkflowFile;
import org.sakaiproject.component.cover.ComponentManager;

/**
 * MyEclipse Struts
 * Creation date: 11-25-2005
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class CancelAction extends LookupDispatchAction {
	
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
			/* clear list */
			String[] selectedItems = {};
			regDetailsForm.setSelectedItems(selectedItems);
			regDetailsForm.setCancelStudyUnits(new ArrayList());
			regDetailsForm.setDisclaimer("");

		} catch (Exception e) {
			throw e;
		}

		return mapping.findForward("step1");
	}

	public String confirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		String[] list = regDetailsForm.getSelectedItems();
		ArrayList cancelList = new ArrayList();
		ActionMessages messages = new ActionMessages();

		try {

			/* Validate input */
			if (regDetailsForm.getSelectedItems().length == 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"You have not selected any study units."));
				addErrors(request, messages);
				return "step1";
			}
			if (regDetailsForm == null
					|| !"1".equals(regDetailsForm.getDisclaimer())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
						"You have not clicked in the checkbox - please do so now."));
				addErrors(request, messages);
				return "step1";
			}

			/* read currently registered study units into cancellation list */
			for (int i = 0; i < list.length; i++) {
				cancelList.add(regDetailsForm.getPickList().get(
						Integer.parseInt(list[i])));
			}
			regDetailsForm.setCancelStudyUnits(cancelList);
		} catch (Exception e) {
			throw e;
		}

		return "confirm";
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
	
		writeWorkflow(regDetailsForm, request);

		eventTrackingService.post(eventTrackingService.newEvent(
				EventTrackingTypes.EVENT_REGDETAILS_CANCELLATION, toolManager
						.getCurrentPlacement().getContext()
						+ "/" + regDetailsForm.getStudentNr(), false),
				usageSession);

		RegQueryDAO db = new RegQueryDAO();
		db.writeAuditTrail(regDetailsForm.getStudentNr(), "IL");
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

		return mapping.findForward("cancel");
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		RegDetailsForm regDetailsForm = (RegDetailsForm) form;
		String prevPage = "";

		if ("2".equalsIgnoreCase(request.getParameter("goto"))) {
			/* clear list */
			String[] selectedItems = {};
			regDetailsForm.setSelectedItems(selectedItems);
			regDetailsForm.setCancelStudyUnits(new ArrayList());
			prevPage = "step1";
		} else if ("3".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "cancel";
		} else if ("home".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "cancel";
		}

		return mapping.findForward(prevPage);
	}

	private void reset(RegDetailsForm regDetailsForm) {
		String[] selectedItems = {};
		regDetailsForm.setSelectedItems(selectedItems);
		regDetailsForm.setCancelStudyUnits(new ArrayList());
		regDetailsForm.setDisclaimer("");
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
		WorkflowFile file = new WorkflowFile("cancel_courses_"
				+ regDetailsForm.getStudentNr());

		file.add(" The following request was received through myUnisa. \r\n\r\n");
		file.add(" Type of request: Application for Cancellation \r\n\r\n");
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
		file.add(" Study unit(s) for which the student wishes to cancel: \r\n\r\n");
		for (int i = 0; i < regDetailsForm.getCancelStudyUnits().size(); i++) {
			StudyUnit su = new StudyUnit();
			su = (StudyUnit) regDetailsForm.getCancelStudyUnits().get(i);
			file.add(" " + su.getCode() + "\r\n");
		}
		file.add(" -------------------------------------------------------------------- \r\n");

		file.close();

	}

}

