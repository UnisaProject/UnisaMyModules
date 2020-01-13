package za.ac.unisa.lms.tools.tutorappointments.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;

import za.ac.unisa.lms.tools.tutorappointments.forms.TutorAppointmentsForm;

public class TutorAppointmentsAction extends LookupDispatchAction{
	
	private Log log = LogFactory.getLog(TutorAppointmentsAction.class.getName());
	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
	
	protected Map getKeyMethodMap(){
		  Map map = new HashMap();		  
		  map.put("mainview","mainview");
         return map;
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward mainview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		TutorAppointmentsForm tutorAppointmentsForm = (TutorAppointmentsForm) form;
		
		request.setAttribute("academicYearOptions", tutorAppointmentsForm.getAcademicYearOptions());
		request.setAttribute("academicPeriodOptions", tutorAppointmentsForm.getAcademicPeriodOptions());
		log.info("Tutor Appointments mainview");
		
		return mapping.findForward("mainview");
	}
}
