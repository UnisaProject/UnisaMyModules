//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.satbook.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.satbook.dao.SatbookBookingDAO;
import za.ac.unisa.lms.tools.satbook.dao.SatbookDAO;
import za.ac.unisa.lms.tools.satbook.dao.SatbookOracleDAO;
import za.ac.unisa.lms.tools.satbook.forms.AdminForm;
import za.ac.unisa.lms.tools.satbook.forms.AdminLinkForm;
import za.ac.unisa.lms.tools.satbook.forms.AssistantRecord;
import za.ac.unisa.lms.tools.satbook.forms.AssistantTypeRecord;
import za.ac.unisa.lms.tools.satbook.forms.BookingForm;
import za.ac.unisa.lms.tools.satbook.forms.ClassroomForm;
import za.ac.unisa.lms.tools.satbook.forms.InstitutionRecord;
import za.ac.unisa.lms.tools.satbook.forms.LecturerForm;
import za.ac.unisa.lms.tools.satbook.forms.MaterialRecord;
import za.ac.unisa.lms.tools.satbook.forms.SatbookDailyForm;
import za.ac.unisa.lms.tools.satbook.forms.SatbookMonthlyForm;
import za.ac.unisa.lms.tools.satbook.forms.VBookingTypeForm;

/**
 * MyEclipse Struts
 * Creation date: 09-12-2006
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class SatbookAdminAction extends LookupDispatchAction {
	
	

	// --------------------------------------------------------- Instance Variables
	private UsageSessionService usageSessionService;	
	private EventTrackingService eventTrackingService;
	private EmailService emailService;
	
	// --------------------------------------------------------- Methods

	
	protected Map getKeyMethodMap() {
		Map map = new HashMap();

		map.put("button.add.institution","goAddInstitution");
	    map.put("adminmenu","adminMenu");
	    map.put("adminlecturers","adminLecturers");
	    map.put("adminLink","adminLink");
	   	map.put("admininstitutions","adminInstitutions");
	    map.put("admininstitutiondays","adminInstitutionDays");
	    map.put("adminclassrooms","adminClassrooms");
	    map.put("adminVenues","adminVenues");
	    map.put("adminmaterial","adminMaterial");
	    map.put("adminassistanttype","adminAssistantType");
	    map.put("adminassistants","adminAssistants");
	    map.put("adminBookingType","adminBookingType");
	    map.put("adminbookingtype.button.add","addBookingType");
	    map.put("adminbookingtype.button.save", "saveBookingType");
	    map.put("adminbookingtype.button.cancel","cancelBkngType");
	    map.put("adminbookingtype.button.edit", "editBookingType");
	    map.put("adminbookingtype.button.btypeconfirmdelete","deleteBkngTypeConfirm");
	    map.put("adminbookingtype.button.delete", "deleteBkngType");
	    map.put("sendgroupemail","sendGroupEmail");
	    map.put("emailschedule","emailSchedule");
	    map.put("button.add.lecturer","goAddLecturer");
	    map.put("button.to.monthlyview","satbookMonthly");
	    map.put("adminsystemlink.button.save","saveAdministrator");
	    map.put("adminlecturersedit.button.save","saveLecturer");
	    map.put("adminlecturersedit.button.back","adminLecturers");
	    map.put("admininstitutionsedit.button.save","saveInstitution");
	    map.put("admininstitutionsedit.button.back","adminInstitutions");
	    map.put("button.monthlyview.adminview","adminMenu");
	    map.put("adminlecturersedit.button.delete","deleteLecturer");
	    map.put("adminlecturersedit.button.edit","editLecturer");
	    map.put("admininstitutionsedit.button.delete","deleteInstitution");
	    map.put("admininstitutionsedit.button.edit","editInstitution");
	    map.put("admininstitutiondays.button.view","viewInstitutionDays");
	    map.put("admininstitutiondays.button.edit","editInstitutionDays");
	    map.put("admininstitutiondays.button.save","saveInstitutionDays");
	    map.put("adminclassrooms.button.add","addClassroom");
	    map.put("adminclassrooms.button.save","saveClassroom");
	    map.put("adminclassrooms.button.edit","editClassroom");
	    map.put("adminclassrooms.button.delete","deleteClassroom");
	   	map.put("adminvenues.button.add","addVenue");
	    map.put("adminvenues.button.save","saveVenue");
	    map.put("adminvenues.button.edit","editVenue");
	    map.put("adminvenues.button.venueconfirmdelete","deleteVenueConfirm");
	    map.put("adminvenues.button.delete","deleteVenueConfirm");
	    map.put("adminvenues.button.cancel","cancelVenue");
	    map.put("adminmaterial.button.add","addMaterial");
	    map.put("adminmaterial.button.save","saveMaterial");
	    map.put("adminmaterial.button.cancel","cancelMaterial");
	    map.put("adminmaterial.button.delete","deleteMaterial");
	    map.put("adminmaterial.button.edit","editMaterial");
	    map.put("adminassistanttype.button.add","addAssistantType");
	    map.put("adminassistanttype.button.save","saveAssistantType");
	    map.put("adminassistanttype.button.edit","editAssistantType");
	    map.put("adminassistanttype.button.delete","deleteAssistantType");
	    map.put("adminassistants.button.add","addAssistant");
	    map.put("adminassistants.button.save","saveAssistant");
	    map.put("adminassistants.button.edit","editAssistant");
	    map.put("adminassistants.button.delete","deleteAssistant");
	    map.put("adminassistants.button.report","adminAssistants");
	    map.put("email.button.send","sendEmails");
	    map.put("email.button.cancel","cancelForward");

	    return map;
	}
	
	public ActionForward cancelForward(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {

			String gotoStepValidate;
			if ("venue".equalsIgnoreCase(request.getParameter("adminstep"))){
				// if at step 1 go to validations of step1
				gotoStepValidate = "adminclassrooms";
			} else {
				gotoStepValidate = "adminmenu";
			}
			return mapping.findForward(gotoStepValidate);
		}

	public ActionForward adminLink(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		    AdminForm adminForm = (AdminForm) form;
			AdminLinkForm adminLinkForm = new AdminLinkForm();
			//Read session monthlyForm bean
			SatbookMonthlyForm monthlyForm = (SatbookMonthlyForm)request.getSession().getAttribute("monthlyForm");
			adminForm.setSystemID(monthlyForm.getSystemID());
			SatbookDAO db = new SatbookDAO();
			
			// set up system options list
			//adminForm.setS
			
			try {
				adminForm.setAdminList(db.selectAdmin());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("systemList", adminForm.getSystemList());
			
			return mapping.findForward("adminsystemlink");
		}
	public ActionForward saveAdministrator(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

			AdminForm adminForm = (AdminForm) form;
			ActionMessages messages = new ActionMessages();
			SatbookDAO db = new SatbookDAO();
			
			String aExist="5"; 
			int userNotLinked = 0;
			String usersNotLinked = "";
			for(int i=0 ;i<adminForm.getAdminList().size(); i++) {
				AdminLinkForm adminLinkForm = (AdminLinkForm) adminForm.getAdminList().get(i);
				String userId =adminLinkForm.getAdministrator();
				String systemId=adminLinkForm.getSystem();
				
				try {
					db.insertAdministrator(systemId, userId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
				// system selection is mandatory
				
				if (systemId.equals("5")) {
					userNotLinked++;
					usersNotLinked = usersNotLinked+", "+userId;
					
				}
			}
			
			if (userNotLinked >= 1) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The following administrator(s) ("+usersNotLinked+")were not linked to a system , please link them."));
				addErrors(request, messages);
			}
			
			request.setAttribute("systemList", adminForm.getSystemList());
			return mapping.findForward("adminsystemlink");
	  }	
			

	/**
	 * Method adminMenu
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward adminMenu(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		return mapping.findForward("adminmenu");
	}

	/**
	 * Method adminLecturers
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward adminLecturers(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		try {
			adminForm.setLecturerList(db.selectLecturers());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getLecturerList());

		return mapping.findForward("adminlecturers");
	}

	/**
	 * Method adminInstitutions
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward adminInstitutions(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		try {
			adminForm.setInstitutionList(db.selectInstitutions(true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getInstitutionList());

		return mapping.findForward("admininstitutions");
	}

	/**
	 * Method adminInstitutionDays
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward adminInstitutionDays(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		request.setAttribute("yearList", adminForm.getYearList());
		request.setAttribute("monthList", adminForm.getMonthList());

		ArrayList instDaysList = new ArrayList();
		try {
		//	instDaysList = db.selectInstitutionDays(adminForm.getSelectedYear(),adminForm.getSelectedMonth());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//selectInstitutionDays
		adminForm.setInstDaysList(instDaysList);
		request.setAttribute("instDaysList", adminForm.getInstDaysList());

		return mapping.findForward("admininstitutiondays");
	}

	/**
	 * Method adminClassrooms
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward adminClassrooms(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		
		SatbookDAO db = new SatbookDAO();
		try {
			adminForm.setClassroomList(db.selectClassrooms(true,adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch blocka
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getClassroomList());

		return mapping.findForward("adminclassrooms");
	}

	public ActionForward adminVenues(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

			AdminForm adminForm = (AdminForm) form;
			adminForm.setActionStatus("CONFIRM");//confirm delete button in adminclassrooms.jsp
			SatbookDAO db = new SatbookDAO();
			try {
				adminForm.setClassroomList(db.selectVenues(adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//request.setAttribute("regionList", adminForm.getRegionList());
			request.setAttribute("records", adminForm.getClassroomList());

			return mapping.findForward("adminclassrooms");
		}

	/**
	 * Method adminMaterial
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward adminMaterial(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		try {
			adminForm.setMaterialList(db.selectMaterialList(true,adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//request.setAttribute("records", adminForm.getClassroomList());


		return mapping.findForward("adminmaterial");
	}

	/**
	 * Method adminAssistantType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward adminAssistantType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		try {
			adminForm.setAssistantTypeList(db.selectAssistantTypeList(true,adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getAssistantTypeList());

		return mapping.findForward("adminassistanttype");
	}

	/**
	 * Method adminAssistants
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward adminAssistants(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();

		// set assistant types drop down list list
		try {
			adminForm.setAssistantTypeList(db.selectAssistantTypeList(false, adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("assistantTypeList", adminForm.getAssistantTypeList());

		// set assistants list
		try {
			adminForm.setAssistantList(db.selectAssistantList(adminForm.getSelectedAssTypeForReport(),true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getAssistantList());

		return mapping.findForward("adminassistants");
	}
	/**
	 * Method adminBookingType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward adminBookingType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		try {
			adminForm.setBkngTypeList(db.selectBkngTypeList(adminForm.getSystemID(),"object"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//request.setAttribute("record", adminForm.getBkngTypeList());


		return mapping.findForward("bookingtype");
	}


	/**
	 * Method sendGroupEmail
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward sendGroupEmail(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();

		ArrayList tmpList = new ArrayList();

		// set assistant types drop down list list
		try {
			tmpList = db.selectAssistantTypeList(false, adminForm.getSystemID());
			/*if (adminForm.getSystemID().equals(adminForm.getSatbook())) {
				tmpList.add(new org.apache.struts.util.LabelValueBean("LECTURERS", "LECTURERS"));
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adminForm.setAssistantTypeList(tmpList);
		request.setAttribute("assistantTypeList", adminForm.getAssistantTypeList());

		return mapping.findForward("sendgroupemail");
	}

	/**
	 * Method emailSchedule
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward emailSchedule(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();

		ArrayList tmpList = new ArrayList();

		// set assistant types drop down list list
		try {
			tmpList = db.selectAssistantTypeList(false, adminForm.getSystemID());
			/*tmpList.add(new org.apache.struts.util.LabelValueBean("LECTURERS", "LECTURERS"));
			tmpList.add(new org.apache.struts.util.LabelValueBean("STUDENTS", "STUDENTS"));*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adminForm.setAssistantTypeList(tmpList);
		request.setAttribute("assistantTypeList", adminForm.getAssistantTypeList());


		return mapping.findForward("emailschedule");
	}

	/**
	 * Method goAddLecturer
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward goAddLecturer(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("ADD");

		LecturerForm lecturer = new LecturerForm();
		request.setAttribute("lecturer", lecturer);
		//adminForm.getLecturerList();

		return mapping.findForward("adminlecturersedit");
	}

	/**
	 * Method saveLecturer
	 *    Save new lecturer of update existing lecturer detail
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveLecturer(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		AdminForm adminForm = (AdminForm) form;
		ActionMessages messages = new ActionMessages();
		SatbookDAO db = new SatbookDAO();

		try {
			adminForm.setLecturerList(db.selectLecturers());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getLecturerList());

		String tmpNovellId = request.getParameter("novellId").replace('\'',' ');

		if ((null == tmpNovellId)||(tmpNovellId.length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter lecturer novell id."));
			addErrors(request, messages);


			adminForm.setActionStatus("ADD");

			LecturerForm lecturer = new LecturerForm();
			request.setAttribute("lecturer", lecturer);

			return mapping.findForward("adminlecturersedit");
		}

		// validate lecturer against database
		SatbookBookingAction satbookAction = new SatbookBookingAction();
		String lecturerDetail = satbookAction.validateLecturer(tmpNovellId);
		if ((lecturerDetail==null)||(lecturerDetail.length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid lecturer novell id."));
			addErrors(request, messages);

			adminForm.setActionStatus("ADD");
			LecturerForm lecturer = new LecturerForm();
			request.setAttribute("lecturer", lecturer);

			return mapping.findForward("adminlecturersedit");
		}

		if (adminForm.getActionStatus().equals("ADD")) {
			// validate that lecturer does not already exist
			boolean lecturerExist = false;
			try {
				lecturerExist = db.lecturerExist(tmpNovellId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (lecturerExist == true) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Lecturer ("+tmpNovellId+") already exists."));
				addErrors(request, messages);

				try {
					adminForm.setLecturerList(db.selectLecturers());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				request.setAttribute("records", adminForm.getLecturerList());

				return mapping.findForward("adminlecturers");
			}
		}

		// validate novell id
		try {
			db.insertLecturer(request.getParameter("novellId"),request.getParameter("telNumber"),request.getParameter("cellNumber"),adminForm.getActionStatus());
			if (adminForm.getActionStatus().equals("ADD")) {
				// 	save log
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_LECTURER_ADD, " Lecturer: "+request.getParameter("novellId"), false), usageSession);
			} else {
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_LECTURER_EDIT, " Lecturer: "+request.getParameter("novellId"), false), usageSession);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// email administrator that lecturer was added.
		String emailHeading = "Lecturer was added to Satbook";
		String emailMsg = "Lecturer ("+request.getParameter("novellId")+") was added to the satellite booking system.";
		try {
			sendEmail(emailHeading,emailMsg,"syzelle@unisa.ac.za");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			adminForm.setLecturerList(db.selectLecturers());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getLecturerList());

		return mapping.findForward("adminlecturers");
	}

	/**
	 * Method deleteLecturer
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteLecturer(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("DELETE");
		ActionMessages messages = new ActionMessages(); // class that encapsulates messages.

		if ((adminForm.getSelectedLecturer() == null)||
				(adminForm.getSelectedLecturer().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a lecturer to delete."));
			addErrors(request, messages);

			SatbookDAO db = new SatbookDAO();
			try {
				adminForm.setLecturerList(db.selectLecturers());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getLecturerList());

			return mapping.findForward("adminlecturers");
		}

		SatbookDAO db = new SatbookDAO();

		try {
			UsageSession usageSession = usageSessionService.getSession();
			eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_LECTURER_DELETE, " Lecturer: "+adminForm.getSelectedLecturer(), false), usageSession);
			db.deleteLecturer(adminForm.getSelectedLecturer());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	messages.add(ActionMessages.GLOBAL_MESSAGE,
		        new ActionMessage("message.generalmessage", "Lecturer was deleted."));
		addMessages(request, messages);

		try {
			adminForm.setLecturerList(db.selectLecturers());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		request.setAttribute("records", adminForm.getLecturerList());

		return mapping.findForward("adminlecturers");
	}

	/**
	 * Method editLecturer
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editLecturer(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		ActionMessages messages = new ActionMessages();
		adminForm.setActionStatus("EDIT");

		if ((adminForm.getSelectedLecturer() == null)||
				(adminForm.getSelectedLecturer().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a lecturer to edit."));
			addErrors(request, messages);

			SatbookDAO db = new SatbookDAO();
			try {
				adminForm.setLecturerList(db.selectLecturers());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getLecturerList());

			return mapping.findForward("adminlecturers");
		}

		LecturerForm lecturer = new LecturerForm();

		// get the lecturer detail
		SatbookDAO db = new SatbookDAO();
		lecturer = db.selectLecturer(adminForm.getSelectedLecturer());

		request.setAttribute("lecturer",lecturer);

		return mapping.findForward("adminlecturersedit");
	}

	/**
	 * Method goAddInstitution
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward goAddInstitution(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("ADD");

		InstitutionRecord institution = new InstitutionRecord();
		request.setAttribute("institution", institution);

		return mapping.findForward("admininstitutionsedit");
	}

	/**
	 * Method saveInstitution
	 *    Save new institution of update existing lecturer detail
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveInstitution(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		AdminForm adminForm = (AdminForm) form;
		ActionMessages messages = new ActionMessages();
		SatbookDAO db = new SatbookDAO();

		String tmpInstId =adminForm.getSelectedInstitution();
		String tmpInstName = request.getParameter("instName").replace('\'',' ');

		if (adminForm.getActionStatus().equals("ADD")) {
			// validate that institution does not already exist
			boolean instExist = false;
			try {
				instExist = db.institutionExist(tmpInstName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (instExist == true) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Institution ("+tmpInstName+") already exists."));
				addErrors(request, messages);

				try {
					adminForm.setInstitutionList(db.selectInstitutions(true));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				request.setAttribute("records", adminForm.getInstitutionList());
				return mapping.findForward("admininstitutions");
			}
		}

		try {
			db.insertInstitution(tmpInstId,tmpInstName,adminForm.getActionStatus());
			UsageSession usageSession = usageSessionService.getSession();
			if (adminForm.getActionStatus().equals("ADD")) { 
				// 	save log
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_INST_ADD, " Inst: "+tmpInstName+" "+tmpInstId, false), usageSession);

			} else {
				// 	save log
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_INST_EDIT, " Inst: "+tmpInstName+" "+tmpInstId, false), usageSession);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			adminForm.setInstitutionList(db.selectInstitutions(true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getInstitutionList());

		return mapping.findForward("admininstitutions");
	}

	/**
	 * Method satbookMonthly
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward satbookMonthly(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		return mapping.findForward("satbookmonthlyfw");
	}

	/**
	 * Method deleteInstitution
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteInstitution(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("DELETE");
		ActionMessages messages = new ActionMessages(); // class that encapsulates messages.

		if ((adminForm.getSelectedInstitution() == null)||
				(adminForm.getSelectedInstitution().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose an institution to delete."));
			addErrors(request, messages);

			SatbookDAO db = new SatbookDAO();
			try {
				adminForm.setInstitutionList(db.selectInstitutions(true));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getInstitutionList());

			return mapping.findForward("admininstitutions");
		}

		SatbookDAO db = new SatbookDAO();


		try {
			UsageSession usageSession = usageSessionService.getSession();
			eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_INST_DELETE, " Institution: "+adminForm.getSelectedInstitution(), false), usageSession);
			db.deleteInstitution(adminForm.getSelectedInstitution());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	messages.add(ActionMessages.GLOBAL_MESSAGE,
		        new ActionMessage("message.generalmessage", "Institution was deleted."));
		addMessages(request, messages);

		try {
			adminForm.setInstitutionList(db.selectInstitutions(true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		request.setAttribute("records", adminForm.getInstitutionList());

		return mapping.findForward("admininstitutions");
	}

	/**
	 * Method editInstitution
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editInstitution(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("EDIT");
		ActionMessages messages = new ActionMessages();

		if ((adminForm.getSelectedInstitution() == null)||
				(adminForm.getSelectedInstitution().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose an institution to edit."));
			addErrors(request, messages);

			SatbookDAO db = new SatbookDAO();
			try {
				adminForm.setInstitutionList(db.selectInstitutions(true));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getInstitutionList());

			return mapping.findForward("admininstitutions");
		}


		InstitutionRecord institution = new InstitutionRecord();

		// get the lecturer detail
		SatbookDAO db = new SatbookDAO();
		try {
			institution = db.selectInstitution(adminForm.getSelectedInstitution());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("institution",institution);

		return mapping.findForward("admininstitutionsedit");
	}

	/**
	 * Method viewInstitutionDays
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward viewInstitutionDays(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		request.setAttribute("yearList", adminForm.getYearList());
		request.setAttribute("monthList", adminForm.getMonthList());

		ArrayList instDaysList = new ArrayList();
		try {
			instDaysList = db.selectInstitutionDays(adminForm.getSelectedYear(),adminForm.getSelectedMonth(),adminForm.getSystemID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//selectInstitutionDays
		adminForm.setInstDaysList(instDaysList);
		request.setAttribute("instDaysList", adminForm.getInstDaysList());

		return mapping.findForward("admininstitutiondays");
	}

	/**
	 * Method editInstitutionDays
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editInstitutionDays(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		request.setAttribute("yearList", adminForm.getYearList());
		request.setAttribute("monthList", adminForm.getMonthList());

		ArrayList instDaysList = new ArrayList();
		try {
			instDaysList = db.selectInstitutionDays(adminForm.getSelectedYear(),adminForm.getSelectedMonth(),adminForm.getSystemID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adminForm.setInstDaysList(instDaysList);
		request.setAttribute("instDaysList", adminForm.getInstDaysList());

		// set list with institution names
		try {
			adminForm.setInstitutionList(db.selectInstitutions(false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("institutionList", adminForm.getInstitutionList());

		return mapping.findForward("admininstitutiondaysedit");
	}

	/**
	 * Method saveInstitutionDays
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveInstitutionDays(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		request.setAttribute("yearList", adminForm.getYearList());
		request.setAttribute("monthList", adminForm.getMonthList());

		// save changes
		try {
			db.insertInstitutionDays(adminForm.getInstDaysList());
			UsageSession usageSession = usageSessionService.getSession();
			/*if (adminForm.getActionStatus().equals("ADD")) { 
				// 	save log
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_INSTDAYS_ADD, "", false), usageSession);

			} else {
				// 	save log
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_INSTDAYS_EDIT, "", false), usageSession);
				
			}*/
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ArrayList instDaysList = new ArrayList();
		try {
			instDaysList = db.selectInstitutionDays(adminForm.getSelectedYear(),adminForm.getSelectedMonth(),adminForm.getSystemID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adminForm.setInstDaysList(instDaysList);
		request.setAttribute("instDaysList", adminForm.getInstDaysList());

		return mapping.findForward("admininstitutiondays");
	}

	/**
	 * Method AddClassroom
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addClassroom(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		request.setAttribute("regionList", adminForm.getRegionList());
		adminForm.setActionStatus("ADD");

		ClassroomForm classroom = new ClassroomForm();
		request.setAttribute("classroom", classroom);

		return mapping.findForward("adminclassroomsadd");
	}

	/**
	 * Method saveClassroom
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveClassroom(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		ActionMessages messages = new ActionMessages();
		AdminForm adminForm = (AdminForm) form;

		// get var from jsp and set formbean
		ClassroomForm classroom = new ClassroomForm();

		classroom.setRegionCode(adminForm.getSelectedClassroom());
		classroom.setRegionDesc(request.getParameter("regionDesc").replace('\'',' '));
		classroom.setContactPerson(request.getParameter("contactPerson").replace('\'',' '));
		classroom.setContactNum1(request.getParameter("contactNum1"));
		classroom.setContactNum2(request.getParameter("contactNum2"));
		classroom.setRegionAddress1(request.getParameter("regionAddress1").replace('\'',' '));
		classroom.setRegionAddress2(request.getParameter("regionAddress2").replace('\'',' '));
		classroom.setRegionAddress3(request.getParameter("regionAddress3").replace('\'',' '));
		classroom.setRegionAddress4(request.getParameter("regionAddress4").replace('\'',' '));
		classroom.setRegionAddressPcode(request.getParameter("regionAddressPcode").replace('\'',' '));
		if (request.getParameter("regionActive").equalsIgnoreCase("true")) {
			classroom.setRegionActive(true);
		} else {
			classroom.setRegionActive(false);
		}

		// region desc is mandatory
		if ((classroom.getRegionDesc() == null)
				||(classroom.getRegionDesc().length()==0)
				||(classroom.getRegionDesc().equals("0"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter Classroom name."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			return mapping.findForward("adminclassroomsadd");
		}

		// contact name is mandatory
		if ((classroom.getContactPerson() == null)
				||(classroom.getContactPerson().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter a contact person for the classroom."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}

		// contact number 1 is mandatory
		if ((classroom.getContactNum1() == null)||(classroom.getContactNum1().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter a contact number for the contact person (Contact Num 1)."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}

		// physical address validations
		// address line 1 is mandatory
		if ((classroom.getRegionAddress1() == null)||(classroom.getRegionAddress1().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Address line 1 of the region address is mandatory."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}

		// postal code is mandatory
		if ((classroom.getRegionAddressPcode() == null)||(classroom.getRegionAddressPcode().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Postal code of the region address is mandatory."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}

		// save the classroom
		SatbookDAO db = new SatbookDAO();
		SatbookOracleDAO db2 = new SatbookOracleDAO();

		boolean classroomExist = false;
		try {
			classroomExist = db.classroomExist(request.getParameter("regionDesc"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if ((classroomExist == false)||(adminForm.getActionStatus().equals("EDIT"))) {
			// save classroom detail
			try {
				db.insertClassroom(classroom,adminForm.getActionStatus(),adminForm.getSystemID());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Classroom detail was updated successfully"));
				addMessages(request, messages);
				
				UsageSession usageSession = usageSessionService.getSession();
				if (adminForm.getActionStatus().equals("ADD")) { 
					// 	save log
					eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_CLASSROOM_ADD, "classroom ", false), usageSession);

				} else {
					// 	save log
					eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_CLASSROOM_EDIT, "classroom ", false), usageSession);
					
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Classroom already exists"));
			addErrors(request, messages);

			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}

		// get new classroom list with updated/added information.
		try {
			adminForm.setClassroomList(db.selectClassrooms(true,adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getClassroomList());

		return mapping.findForward("adminclassrooms");
	}

	/**
	 * Method EditClassroom
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editClassroom(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("EDIT");
		ActionMessages messages = new ActionMessages();

		if ((adminForm.getSelectedClassroom() == null)||
				(adminForm.getSelectedClassroom().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a classroom to edit."));
			addErrors(request, messages);

			SatbookDAO db = new SatbookDAO();
			try {
				adminForm.setClassroomList(db.selectClassrooms(true,adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getClassroomList());

			return mapping.findForward("adminclassrooms");
		}


		ClassroomForm classroom = new ClassroomForm();

		// get the classroom detail
		SatbookDAO db = new SatbookDAO();
		SatbookOracleDAO db1 = new SatbookOracleDAO();
		try {
			classroom = db.selectClassroom(adminForm.getSelectedClassroom());
			classroom.setRegionCode(adminForm.getSelectedClassroom());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("classroom",classroom);

		return mapping.findForward("adminclassroomsedit");
	}

	/**
	 * Method DeleteClassroom
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteClassroom(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		AdminForm adminForm = (AdminForm) form;
		ActionMessages messages = new ActionMessages();
		SatbookDAO db = new SatbookDAO();

		if ((adminForm.getSelectedClassroom() == null)||
				(adminForm.getSelectedClassroom().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a classroom to delete."));
			addErrors(request, messages);

			try {
				adminForm.setClassroomList(db.selectClassrooms(true,adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getClassroomList());

			return mapping.findForward("adminclassrooms");
		}

		// was classroom linked to a broadcast (SATBOOK_BKNG_CLASSROOM)
		boolean classroomUsed = false;
		try {
			classroomUsed = db.classroomUsed(adminForm.getSelectedClassroom());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		if (classroomUsed == true) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Classroom can not be deleted, it was already linked to a broadcast booking."));
			addErrors(request, messages);

			// get new classroom list with updated/added information.
			try {
				adminForm.setClassroomList(db.selectClassrooms(true,adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mapping.findForward("adminclassrooms");
		}

		try {
			UsageSession usageSession = usageSessionService.getSession();
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_CLASSROOM_DELETE, "classroom "+adminForm.getSelectedClassroom(), false), usageSession);

			db.deleteClassroom(adminForm.getSelectedClassroom());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Classroom was deleted successfully"));
			addMessages(request, messages);
			

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// get new classroom list with updated/added information.
		try {
			adminForm.setClassroomList(db.selectClassrooms(true,adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getClassroomList());

		return mapping.findForward("adminclassrooms");
	}
	/**
	 * Method AddVenues
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addVenue(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		
		AdminForm adminForm = (AdminForm) form;
		request.setAttribute("regionList", adminForm.getRegionList());
		adminForm.setActionStatus("ADD");
     
		ClassroomForm classroom = new ClassroomForm();
		request.setAttribute("classroom", classroom);
     
		return mapping.findForward("adminclassroomsadd");
	}
	
	/**
	 * Method saveVenue
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveVenue(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		ActionMessages messages = new ActionMessages();
		AdminForm adminForm = (AdminForm) form;

		// get var from jsp and set formbean
		ClassroomForm classroom = new ClassroomForm();

		classroom.setRegionCode(adminForm.getSelectedClassroom());
		classroom.setRegionDesc(request.getParameter("regionDesc").replace('\'',' '));
		classroom.setContactPerson(request.getParameter("contactPerson").replace('\'',' '));
		classroom.setContactNum1(request.getParameter("contactNum1"));
		classroom.setContactNum2(request.getParameter("contactNum2"));
		classroom.setRegionAddress1(request.getParameter("regionAddress1").replace('\'',' '));
		classroom.setRegionAddress2(request.getParameter("regionAddress2").replace('\'',' '));
		classroom.setRegionAddress3(request.getParameter("regionAddress3").replace('\'',' '));
		classroom.setRegionAddress4(request.getParameter("regionAddress4").replace('\'',' '));
		classroom.setRegionAddressPcode(request.getParameter("regionAddressPcode").replace('\'',' '));
		if (request.getParameter("regionActive").equalsIgnoreCase("true")) {
			classroom.setRegionActive(true);
		} else {
			classroom.setRegionActive(false);
		}

		// region desc is mandatory
		if ((classroom.getRegionDesc() == null)
				||(classroom.getRegionDesc().length()==0)
				||(classroom.getRegionDesc().equals("0"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter Venue name missing."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			return mapping.findForward("adminclassroomsadd");
		}

		// contact name is mandatory
		if ((classroom.getContactPerson() == null)
				||(classroom.getContactPerson().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter a contact person for the classroom."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}

		// contact number 1 is mandatory
		if ((classroom.getContactNum1() == null)||(classroom.getContactNum1().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter a contact number for the contact person (Contact Num 1)."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}
		
		// email address manadatory for venbook
		if (adminForm.getSystemID().equals(adminForm.getVenbook())) {
			boolean validEmail = ValidateEmail(classroom.getContactNum2());
			if (validEmail == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid e-mail address, please enter a valid e-mail address."));
				addErrors(request, messages);
				request.setAttribute("classroom", classroom);
				request.setAttribute("regionList", adminForm.getRegionList());
				return mapping.findForward("adminclassroomsadd");
			}
		}

		// physical address validations
		// address line 1 is mandatory
		if ((classroom.getRegionAddress1() == null)||(classroom.getRegionAddress1().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Address line 1 of the region address is mandatory."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}

		// postal code is mandatory
		if ((classroom.getRegionAddressPcode() == null)||(classroom.getRegionAddressPcode().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Postal code of the region address is mandatory."));
			addErrors(request, messages);
			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}

		// check duplicate of venues
		SatbookDAO db = new SatbookDAO();
		SatbookOracleDAO db2 = new SatbookOracleDAO();

		boolean venueExist = false;
		try {
			venueExist = db.venueExist(adminForm.getSystemID(),request.getParameter("regionDesc"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// if no duplicates save venue detail
		if ((venueExist == false)&&(adminForm.getActionStatus().equals("ADD"))) {
			try {
				db.insertClassroom(classroom,adminForm.getActionStatus(),adminForm.getSystemID());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Venue detail was updated successfully"));
				addMessages(request, messages);
			
				UsageSession usageSession = usageSessionService.getSession();
				if (adminForm.getActionStatus().equals("ADD")) { 
					// 	save log
					eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_CLASSROOM_ADD, "classroom ", false), usageSession);

				}else {
						// 	save log
						eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_CLASSROOM_EDIT, "classroom ", false), usageSession);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else
		if (adminForm.getActionStatus().equals("EDIT")) {
			
			try {
				db.insertClassroom(classroom,adminForm.getActionStatus(),adminForm.getSystemID());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Venue detail was updated successfully"));
				addMessages(request, messages);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Venue already exists"));
			addErrors(request, messages);

			request.setAttribute("classroom", classroom);
			request.setAttribute("regionList", adminForm.getRegionList());
			return mapping.findForward("adminclassroomsadd");
		}
		// get new venue list with updated/added information.
		try {
			adminForm.setClassroomList(db.selectVenues(adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getClassroomList());

		return mapping.findForward("adminclassrooms");
	}
	
	/**
	 * Method EditVenue
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editVenue(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("EDIT");
		ActionMessages messages = new ActionMessages();

		if ((adminForm.getSelectedClassroom() == null)||
				(adminForm.getSelectedClassroom().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a venue to edit."));
			addErrors(request, messages);
			
		// get new venue list with updated/added information.
		SatbookDAO db = new SatbookDAO();
		try {
			adminForm.setClassroomList(db.selectVenues(adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getClassroomList());

		return mapping.findForward("adminclassrooms");
		}	
		ClassroomForm classroom = new ClassroomForm();

		// get the venue detail
		SatbookDAO db = new SatbookDAO();
	
		try {
			classroom = db.selectClassroom(adminForm.getSelectedClassroom());
			
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		request.setAttribute("classroom",classroom);

		return mapping.findForward("adminclassroomsadd");
	}
	
	/**
	 * Method deleteVenueConfirm
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	*/
	public ActionForward deleteVenueConfirm(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		
		AdminForm adminForm = (AdminForm) form; // instance/reference of adminForm
		ActionMessages messages = new ActionMessages();
		SatbookDAO db1 = new SatbookDAO();
		ClassroomForm classroom = new ClassroomForm(); 
		
		if (adminForm.getActionStatus().equals("DELETE VENUE")) {
			// FOR DELETE (AFTER CONFIRM DELETE)
			//check if venue is used
		
			boolean venueUsed = false;
			try {
				venueUsed = db1.venueUsed(adminForm.getSelectedClassroom());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// assign instance of a form to specific venue detail
			try {
				classroom = db1.selectClassroom(adminForm.getSelectedClassroom());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				db1.deleteVenue(adminForm.getSelectedClassroom(),venueUsed,adminForm.getSystemID());
				
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_CLASSROOM_DELETE, "classroom "+adminForm.getSelectedClassroom(), false), usageSession);
	
				if (venueUsed == true) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The following venue "+classroom.getRegionDesc()+" is in use and will only be set to inactive."));
					addMessages(request, messages);								
				} else {
				
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Venue "+classroom.getRegionDesc()+" was deleted successfully"));
					addMessages(request, messages);
				}
	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// get new classroom list with updated/added information.
			try {
				adminForm.setClassroomList(db1.selectVenues(adminForm.getSystemID()));
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			request.setAttribute("records", adminForm.getClassroomList());
	
			adminForm.setActionStatus("CONFIRM DELETE");
			return mapping.findForward("adminclassrooms");
		} else {
			if ((adminForm.getSelectedClassroom() == null)||
				(adminForm.getSelectedClassroom().length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose venue(s) to delete."));
				addErrors(request, messages);
				
				
				request.setAttribute("records", adminForm.getClassroomList());
			
				return mapping.findForward("adminclassrooms");
			} 
			// get venue detail 
				
				try {
				classroom = db1.selectClassroom(adminForm.getSelectedClassroom());
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
	
				adminForm.setRegionDesc(classroom.getRegionDesc());
				adminForm.setActionStatus("DELETE VENUE");
					
				return mapping.findForward("confirmvenuedelete");
		} 
	}
	

	/**
	 * Method cancelVenue
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
  */	
	public ActionForward cancelVenue(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		AdminForm adminForm = (AdminForm) form;
		ClassroomForm classroom = new ClassroomForm();
		
		//classroom.resetFormbean(mapping,request);
		request.setAttribute("records", adminForm.getClassroomList());
				
		return mapping.findForward("adminclassrooms");
	}

	/**
	 * Method AddBookingType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addBookingType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("ADD");

		VBookingTypeForm vbookingTypeForm = new VBookingTypeForm();
		request.setAttribute("vbookingTypeForm", vbookingTypeForm);
		adminForm.setFormToUse("bkngtype");
		
		//adminmaterialadd.jsp returns a jsp to add either booking type or material  
		
		return mapping.findForward("adminmaterialadd");
	}
	
	/**
	 * Method saveBookingType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	*/ 
	public ActionForward saveBookingType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		ActionMessages messages = new ActionMessages();
		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		boolean bookingTypeExist = false;

		// get var from jsp and set formbean
		VBookingTypeForm vbookingTypeForm = new VBookingTypeForm();

		if (adminForm.getActionStatus().equals("EDIT")) {
			vbookingTypeForm.setBookingTypeId(adminForm.getSelectedBkngType());
		}
		vbookingTypeForm.setBookingTypeDesc(request.getParameter("bookingTypeDesc"));
		
		if (request.getParameter("bookingTypeAct").equalsIgnoreCase("true")) {
			vbookingTypeForm.setBookingTypeAct(true);
		} else {
			vbookingTypeForm.setBookingTypeAct(false);
		}
		
		// material description is mandatory
		if ((vbookingTypeForm.getBookingTypeDesc()==null)||(vbookingTypeForm.getBookingTypeDesc().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the booking type description."));
			addErrors(request, messages);

			request.setAttribute("vbookingTypeForm", vbookingTypeForm);
			return mapping.findForward("adminmaterialadd");
		} else {
			if (adminForm.equals("ADD")) {
				// does booking type already exist
				try {
					bookingTypeExist = db.bookingTypeExist(adminForm.getSystemID(),vbookingTypeForm.getBookingTypeDesc());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				bookingTypeExist = false;
			}

			if (bookingTypeExist == false) {
				//save material
				try {
					db.insertBookingType(vbookingTypeForm,adminForm.getActionStatus(),adminForm.getSystemID());
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Booking type detail was updated successfully"));
					addMessages(request, messages);
					
					UsageSession usageSession = usageSessionService.getSession();
					if (adminForm.getActionStatus().equals("ADD")) { 
						// 	save log
						eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_BKNGTYPE_ADD, "vbookingTypeForm "+vbookingTypeForm.getBookingTypeId(), false), usageSession);

					} else {
						// 	save log
						eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_BKNGTYPE_EDIT,"vbookingTypeForm "+vbookingTypeForm.getBookingTypeId(), false), usageSession);
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// give error message when booking type exists
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The booking type description already exist."));
				addErrors(request, messages);

				request.setAttribute("vbookingTypeForm", vbookingTypeForm);
				return mapping.findForward("adminmaterialadd");//returns booking type add jsp
			}
		}
		// get booking type list with updated/added information.
		try {
			adminForm.setBkngTypeList(db.selectBkngTypeList(adminForm.getSystemID(),"object"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getBkngTypeList());

		return mapping.findForward("bookingtype");
	}
	
	/**
	 * Method cancelBkngType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
  */	
	public ActionForward cancelBkngType(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

			AdminForm adminForm = (AdminForm) form;
		
			request.setAttribute("records", adminForm.getBkngTypeList());
			return mapping.findForward("bookingtype");
	}
	
	/**
	 * Method EditBookingType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
 */	
	public ActionForward editBookingType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		
		adminForm.setActionStatus("EDIT");
		adminForm.setFormToUse("bkngtype");
		ActionMessages messages = new ActionMessages();

		if ((adminForm.getSelectedBkngType() == null)||
				(adminForm.getSelectedBkngType().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a booking type to edit."));
			addErrors(request, messages);
			
			//set the booking type list
			try {
				adminForm.setBkngTypeList(db.selectBkngTypeList((adminForm.getSystemID()),"object"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getBkngTypeList());


			return mapping.findForward("bookingtype");
		}

		// select booking type detail from database
		VBookingTypeForm vbookingTypeForm = new VBookingTypeForm();

		try {
			vbookingTypeForm = db.selectBookingType(adminForm.getSelectedBkngType(),adminForm.getSystemID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("vbookingTypeForm", vbookingTypeForm);

		return mapping.findForward("adminmaterialadd");
	}

	/**
	 * Method deleteBkngTypeConfirm
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	*/
	public ActionForward deleteBkngTypeConfirm(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		AdminForm adminForm = (AdminForm) form; // instance/reference of adminForm
		VBookingTypeForm vbookingTypeForm = new VBookingTypeForm();
		SatbookDAO db1 = new SatbookDAO();
		ActionMessages messages = new ActionMessages();
		
		//if no booking type selected		
		if ((adminForm.getSelectedBkngType() == null)||
				(adminForm.getSelectedBkngType().length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please choose booking type to delete."));
				addErrors(request, messages);
				
				//set the booking type list
                try {
                        adminForm.setBkngTypeList(db1.selectBkngTypeList((adminForm.getSystemID()),"vbookingTypeForm"));
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                request.setAttribute("records", adminForm.getBkngTypeList());

                return mapping.findForward("bookingtype");
        }
			
		// select selected booking type detail from database
		vbookingTypeForm = null;
		try {
			vbookingTypeForm = db1.selectBookingType(adminForm.getSelectedBkngType(), adminForm.getSystemID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			adminForm.setBookingTypeDesc(vbookingTypeForm.getBookingTypeDesc());
	
			return mapping.findForward("confirmbooktypedelete");
		}
	
		
	/**
	 * Method deleteBkngType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
  */
	 public ActionForward deleteBkngType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		 
		 usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		 eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		
		AdminForm adminForm = (AdminForm) form; // instance/reference of adminForm
		ActionMessages messages = new ActionMessages();
		SatbookDAO db1 = new SatbookDAO();
		VBookingTypeForm vbookingTypeForm = new VBookingTypeForm(); 
		
		//check if booking type is used
		boolean bkngTypeUsed = false;
		try {
			bkngTypeUsed = db1.bkngTypeUsed(adminForm.getSelectedBkngType());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// select selected-booking type details from database
			try {
				vbookingTypeForm = db1.selectBookingType(adminForm.getSelectedBkngType(),adminForm.getSystemID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				db1.deleteBkngType(adminForm.getSelectedBkngType(),bkngTypeUsed,adminForm.getSystemID());
				
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_BKNGTYPE_DELETE, "vbookingTypeForm "+adminForm.getSelectedBkngType(), false), usageSession);
	
				if (bkngTypeUsed == true) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The following booking type "+vbookingTypeForm.getBookingTypeDesc()+" is in use and will only be set to inactive."));
					addMessages(request, messages);								
				} else {
				
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "booking type "+vbookingTypeForm.getBookingTypeDesc()+" was deleted successfully"));
					addMessages(request, messages);
				}
	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// get new booking type list with updated/added information.
			try {
				adminForm.setBkngTypeList(db1.selectBkngTypeList((adminForm.getSystemID()),"vbookingTypeForm"));;
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			request.setAttribute("records", adminForm.getBkngTypeList());
	
			return mapping.findForward("bookingtype");
		} 
	
	/**
	 * Method AddMaterial
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addMaterial(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("ADD");

		
		MaterialRecord material = new MaterialRecord();
		request.setAttribute("material", material);
		adminForm.setFormToUse("material");
		
		
		//VBookingTypeForm vbookingTypeForm = new VBookingTypeForm();
		
	
		return mapping.findForward("adminmaterialadd");
	}

	/**
	 * Method EditMaterial
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editMaterial(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		MaterialRecord material = new MaterialRecord();
		adminForm.setActionStatus("EDIT");
		adminForm.setFormToUse("material");
		ActionMessages messages = new ActionMessages();

		if ((adminForm.getSelectedMaterial() == null)||
				(adminForm.getSelectedMaterial().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a material to edit."));
			addErrors(request, messages);

			try {
				adminForm.setMaterialList(db.selectMaterialList(true,adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getClassroomList());


			return mapping.findForward("adminmaterial");
		}

		// select material detail from database
		try {
			material = db.selectMaterial(adminForm.getSelectedMaterial());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("material", material);
		adminForm.setFormToUse("material");
		
		return mapping.findForward("adminmaterialadd");
	}

	/**
	 * Method saveMaterial
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveMaterial(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);

		ActionMessages messages = new ActionMessages();
		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		boolean materialExist = false;

		// get var from jsp and set formbean
		MaterialRecord material = new MaterialRecord();

		material.setMaterialDesc(request.getParameter("materialDesc").replace('\'',' '));
		material.setMaterialId(adminForm.getSelectedMaterial());
		String matAct = request.getParameter("materialAct");
		if (matAct.equalsIgnoreCase("true")) {
			material.setMaterialAct(true);
		} else {
			material.setMaterialAct(false);
		}

		// material description is mandatory
		if ((material.getMaterialDesc()==null)||(material.getMaterialDesc().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the material description."));
			addErrors(request, messages);

			request.setAttribute("material", material);
			return mapping.findForward("adminmaterialadd");
		} else {
			if (adminForm.equals("ADD")) {
				// does material already exist
				try {
					materialExist = db.materialExist(material.getMaterialDesc());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				materialExist = false;
			}

			if (materialExist == false) {
				//save material
				try {
					db.insertMaterial(material,adminForm.getActionStatus(),adminForm.getSystemID());
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Material detail was updated successfully"));
					addMessages(request, messages);
					
					UsageSession usageSession = usageSessionService.getSession();
					if (adminForm.getActionStatus().equals("ADD")) { 
						// 	save log
						eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_MATERIAL_ADD, "material "+material.getMaterialId(), false), usageSession);

					} else {
						// 	save log
						eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_MATERIAL_EDIT, "material "+material.getMaterialId(), false), usageSession);
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// material exist give error message
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The material description already exist."));
				addErrors(request, messages);

				request.setAttribute("material", material);
				return mapping.findForward("adminmaterialadd");
			}
		}

		try {
			adminForm.setMaterialList(db.selectMaterialList(true,adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getMaterialList());

		return mapping.findForward("adminmaterial");
	}
	
	/**
	 * Method cancelMaterial
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
  */	
	public ActionForward cancelMaterial(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

			return mapping.findForward("adminmaterial");
	}

	/**
	 * Method deleteMaterial
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteMaterial(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		ActionMessages messages = new ActionMessages();
		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		boolean materialUsed = false;

		if ((adminForm.getSelectedMaterial() == null)||
				(adminForm.getSelectedMaterial().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose a material to delete."));
			addErrors(request, messages);

			try {
				adminForm.setMaterialList(db.selectMaterialList(true,adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getClassroomList());


			return mapping.findForward("adminmaterial");
		}

		// validate that material was not used then may not be deleted
		try {
			materialUsed = db.materialUsed(adminForm.getSelectedMaterial());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// delete material
		if (materialUsed == false) {
			// delete
			try {
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_MATERIAL_DELETE, "material "+adminForm.getSelectedMaterial(), false), usageSession);

				db.deleteMaterial(adminForm.getSelectedMaterial());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Material was deleted successfully"));
				addMessages(request, messages);


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// give error message
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The material may not be deleted, already in use."));
			addErrors(request, messages);
		}

		// reload material list after delete
		try {
			adminForm.setMaterialList(db.selectMaterialList(true,adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getClassroomList());

		return mapping.findForward("adminmaterial");
	}

	/**
	 * Method AddAssistantType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addAssistantType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		adminForm.setActionStatus("ADD");

		AssistantTypeRecord assType = new AssistantTypeRecord();
		request.setAttribute("assType", assType);

		return mapping.findForward("adminasstypeadd");
	}

	/**
	 * Method saveAssistantType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveAssistantType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		ActionMessages messages = new ActionMessages();
		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		boolean assTypeExist = false;

		AssistantTypeRecord assType = new AssistantTypeRecord();
		assType.setAssTypeName(request.getParameter("assTypeName").replace('\'',' '));
		String aTypeAct = request.getParameter("assTypeAct");
		if (aTypeAct.equalsIgnoreCase("TRUE")) {
			assType.setAssTypeAct(true);
		} else {
			assType.setAssTypeAct(false);
		}

		if ((assType.getAssTypeName().length() == 0)||(assType.getAssTypeName()==null)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the assistant type name."));
			addErrors(request, messages);

			request.setAttribute("assType", assType);

			return mapping.findForward("adminasstypeadd");
		}

		if (adminForm.getActionStatus().equals("ADD")) {
			// 	does assistant type exist?
			try {
				assTypeExist = db.assTypeExist(assType.getAssTypeName());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (assTypeExist == true) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The assistant type already exists."));
				addErrors(request, messages);

				request.setAttribute("assType", assType);

				return mapping.findForward("adminasstypeadd");
			}

		} else {
			assType.setAssTypeId(adminForm.getSelectedAssType());
			assTypeExist = false;
		}

		// add/update assistant type
		if (assTypeExist == false) {
			try {
				db.insertAssistantType(assType,adminForm.getActionStatus());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Assistant Type detail was updated successfully"));
				addMessages(request, messages);
				
				UsageSession usageSession = usageSessionService.getSession();
				if (adminForm.getActionStatus().equals("ADD")) { 
					// 	save log
					eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_ASSTYPE_ADD, "asstype "+assType.getAssTypeId(), false), usageSession);

				} else {
					// 	save log
					eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_ASSTYPE_EDIT, "asstype "+assType.getAssTypeId(), false), usageSession);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// reload assistant type list
		try {
			adminForm.setAssistantTypeList(db.selectAssistantTypeList(true, adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getAssistantTypeList());

		return mapping.findForward("adminassistanttype");
	}

	/**
	 * Method EditAssistantType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editAssistantType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		adminForm.setActionStatus("EDIT");
		ActionMessages messages = new ActionMessages();

		if ((adminForm.getSelectedAssType() == null)||
				(adminForm.getSelectedAssType().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose an assistant type to edit."));
			addErrors(request, messages);

			try {
				adminForm.setAssistantTypeList(db.selectAssistantTypeList(true, adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getAssistantTypeList());

			return mapping.findForward("adminassistanttype");
		}


		AssistantTypeRecord assType = new AssistantTypeRecord();
		// select ass type information
		try {
			assType = db.selectAssistantType(adminForm.getSelectedAssType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("assType", assType);

		return mapping.findForward("adminasstypeadd");
	}

	/**
	 * Method deleteAssistantType
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteAssistantType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		ActionMessages messages = new ActionMessages();
		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		boolean assTypeUsed = false;

		if ((adminForm.getSelectedAssType() == null)||
				(adminForm.getSelectedAssType().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose an assistant type to delete."));
			addErrors(request, messages);

			try {
				adminForm.setAssistantTypeList(db.selectAssistantTypeList(true, adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getAssistantTypeList());

			return mapping.findForward("adminassistanttype");
		}

		// validate that assistant type was not used then may not be deleted
		try {
			assTypeUsed = db.assTypeUsed(adminForm.getSelectedAssType());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// delete material
		if (assTypeUsed == false) {
			// delete
			try {
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_ASSTYPE_DELETE, "assistant type"+adminForm.getSelectedAssType(), false), usageSession);

				db.deleteAssistantType(adminForm.getSelectedAssType());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Assistant Type was deleted successfully"));
				addMessages(request, messages);
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// give error message
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The assistant type may not be deleted, already linked to an assistant."));
			addErrors(request, messages);
		}

		// reload assistant type list
		try {
			adminForm.setAssistantTypeList(db.selectAssistantTypeList(true, adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", adminForm.getAssistantTypeList());

		return mapping.findForward("adminassistanttype");
	}

	/**
	 * Method AddAssistant
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addAssistant(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		adminForm.setActionStatus("ADD");

		AssistantRecord assistant = new AssistantRecord();
		request.setAttribute("assistant", assistant);

		try {
			adminForm.setAssistantTypeList(db.selectAssistantTypeList(false, adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("assistantTypeList", adminForm.getAssistantTypeList());

		return mapping.findForward("adminassistantadd");
	}

	/**
	 * Method saveAssistant
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward saveAssistant(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		ActionMessages messages = new ActionMessages();
		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		boolean assistantExist = false;

		AssistantRecord assistant = new AssistantRecord();
		assistant.setAssistantTypeId(request.getParameter("assistantTypeId"));
		assistant.setAssistantName(request.getParameter("assistantName").replace('\'',' '));
		assistant.setAssistantEmail(request.getParameter("assistantEmail").replace('\'',' '));

		if (adminForm.getActionStatus().equals("EDIT")) {
			assistant.setAssistantId(adminForm.getSelectedAssistant());
		}

		// set assTypeList in case is has to be displayed again if errors occured.
		try {
			adminForm.setAssistantTypeList(db.selectAssistantTypeList(false, adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("assTypeList", adminForm.getAssistantTypeList());

		// set assistant types drop down list list
		try {
			adminForm.setAssistantTypeList(db.selectAssistantTypeList(false, adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("assistantTypeList", adminForm.getAssistantTypeList());


		// assistant type is mandatory
		if ((assistant.getAssistantTypeId().equals("0"))||(assistant.getAssistantTypeId()==null)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose the assistant type."));
			addErrors(request, messages);

			request.setAttribute("assistant", assistant);

			return mapping.findForward("adminassistantadd");
		}

		// assistant name is mandatory
		if ((assistant.getAssistantName().length()==0)||(assistant.getAssistantName()==null)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the assistant name."));
			addErrors(request, messages);

			request.setAttribute("assistant", assistant);

			return mapping.findForward("adminassistantadd");
		}

		// assistant email is mandatory
		if ((assistant.getAssistantEmail().length()==0)||(assistant.getAssistantEmail()==null)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the assistant email address."));
			addErrors(request, messages);

			request.setAttribute("assistant", assistant);

			return mapping.findForward("adminassistantadd");
		}


		// validate the assistant email address
		// PERL: $msg_to =~ /^\w+(\.\w+)*@\w+(\.\w+)*\.\w{2,4}$/
		if (!Pattern.matches(".+@.+\\.[a-z]+",assistant.getAssistantEmail())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Invalid e-mail address, please enter a valid email address."));
			addErrors(request, messages);

			request.setAttribute("assistant", assistant);

			return mapping.findForward("adminassistantadd");
		}

		// does assistant + ass type combination exist
		if (adminForm.getActionStatus().equals("ADD")) {

			try {
				assistantExist = db.assistantExist(assistant.getAssistantTypeId(),assistant.getAssistantName());

				if (assistantExist == true) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The assistant already exists for that assistant type."));
					addErrors(request, messages);

					request.setAttribute("assistant", assistant);

					return mapping.findForward("adminassistantadd");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			assistantExist = false;
		}

		//insert or update assistant details
		if (assistantExist == false) {
			try {
				db.insertAssistant(assistant,adminForm.getActionStatus());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Assistant detail was updated successfully"));
				addMessages(request, messages);
				
				UsageSession usageSession = usageSessionService.getSession();
				if (adminForm.getActionStatus().equals("ADD")) { 
					// 	save log
					eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_ASSISTANT_ADD, "assistant "+assistant.getAssistantId(), false), usageSession);

				} else {
					// 	save log
					eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_ASSISTANT_EDIT, "assistant "+assistant.getAssistantId(), false), usageSession);
					
				}

				// email administrator that administrator was added.
				if (assistant.getAssistantTypeDesc().equals("ADMINISTRATORS")) {
					String emailHeading = "Administrator was added to Satbook";
					String emailMsg = "Administrator ("+request.getParameter("novellId")+") was added to the satellite booking system.";
					try {
						sendEmail(emailHeading,emailMsg,"syzelle@unisa.ac.za");
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// reload assistant list
		try {
			adminForm.setAssistantList(db.selectAssistantList(adminForm.getSelectedAssTypeForReport(),true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("records", adminForm.getAssistantList());

		return mapping.findForward("adminassistants");
	}

	/**
	 * Method EditAssistant
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editAssistant(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		adminForm.setActionStatus("EDIT");
		AssistantRecord assistant = new AssistantRecord();
		ActionMessages messages = new ActionMessages();

		if ((adminForm.getSelectedAssistant() == null)||
				(adminForm.getSelectedAssistant().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose an assistant to edit."));
			addErrors(request, messages);

			// set assistant types drop down list list
			try {
				adminForm.setAssistantTypeList(db.selectAssistantTypeList(false, adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("assistantTypeList", adminForm.getAssistantTypeList());

			// set assistants list
			try {
				adminForm.setAssistantList(db.selectAssistantList(adminForm.getSelectedAssTypeForReport(),true));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getAssistantList());

			return mapping.findForward("adminassistants");
		}

		// select assistant detail
		try {
			assistant = db.selectAssistant(adminForm.getSelectedAssistant());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			adminForm.setAssistantTypeList(db.selectAssistantTypeList(false, adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("assistantTypeList", adminForm.getAssistantTypeList());


		request.setAttribute("assistant", assistant);

		return mapping.findForward("adminassistantadd");
	}

	/**
	 * Method deleteAssistant
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward deleteAssistant(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);

		ActionMessages messages = new ActionMessages();
		AdminForm adminForm = (AdminForm) form;
		SatbookDAO db = new SatbookDAO();
		boolean assistantUsed = false;

		if ((adminForm.getSelectedAssistant() == null)||
				(adminForm.getSelectedAssistant().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose an assistant to delete."));
			addErrors(request, messages);

			// set assistant types drop down list list
			try {
				adminForm.setAssistantTypeList(db.selectAssistantTypeList(false, adminForm.getSystemID()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("assistantTypeList", adminForm.getAssistantTypeList());

			// set assistants list
			try {
				adminForm.setAssistantList(db.selectAssistantList(adminForm.getSelectedAssTypeForReport(),true));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("records", adminForm.getAssistantList());

			return mapping.findForward("adminassistants");
		}

		// validate that assistant type was not used then may not be deleted
		try {
			assistantUsed = db.assistantUsed(adminForm.getSelectedAssistant());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// delete material
		if (assistantUsed == false) {
			// delete
			try {
				UsageSession usageSession = usageSessionService.getSession();
				eventTrackingService.post(
						eventTrackingService.newEvent(EventTrackingTypes.EVENT_SATBOOK_ASSISTANT_DELETE, "assistant "+adminForm.getSelectedAssistant(), false), usageSession);

				db.deleteAssistant(adminForm.getSelectedAssistant());
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Assistant was deleted successfully"));
				addMessages(request, messages);
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// give error message
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The assistant may not be deleted, linked to a booking in the future."));
			addErrors(request, messages);
		}

		// set assistant types drop down list list
		try {
			adminForm.setAssistantTypeList(db.selectAssistantTypeList(false, adminForm.getSystemID()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("assistantTypeList", adminForm.getAssistantTypeList());


		// reload assistant list
		try {
			adminForm.setAssistantList(db.selectAssistantList(adminForm.getSelectedAssTypeForReport(),true));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("records", adminForm.getAssistantList());

		return mapping.findForward("adminassistants");
	}

	/**
	 * Method sendGroupEmail
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward sendEmails(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {

		AdminForm adminForm = (AdminForm) form;
		ActionMessages messages = new ActionMessages();
		String atstep = request.getParameter("atstep").toString();
		ArrayList emailToStudentList = new ArrayList();
		ArrayList emailToLecturerList = new ArrayList();
		ArrayList emailToOtherList = new ArrayList();
		String emailToOtherListStr = "";
		String subjectListStr = "";
		SatbookBookingDAO db1 = new SatbookBookingDAO();
		SatbookOracleDAO db2 = new SatbookOracleDAO();
		SatbookDAO db3 = new SatbookDAO();
		ArrayList bookingsList = new ArrayList();
		String emailConfirmMessage = null;

		/** =========== was a group selected to send email to */
		String[] selectedlist = adminForm.getSelectedAssistantTypes();

		if ((selectedlist == null)||(selectedlist.length==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select 1 or more groups to recieve the email"));
			addErrors(request, messages);

			if (atstep.equalsIgnoreCase("scheduleemail")) {
				return mapping.findForward("emailschedule");
			} else {
				return mapping.findForward("sendgroupemail");
			}
		}

		/** =========== was an email heading entered */
		if ((adminForm.getEmailHeading() == null)||(adminForm.getEmailHeading().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter a heading for the email"));
			addErrors(request, messages);

			if (atstep.equalsIgnoreCase("scheduleemail")) {
				return mapping.findForward("emailschedule");
			} else {
				return mapping.findForward("sendgroupemail");
			}
		}

		/** =========== was an email body entered */
		if ((adminForm.getEmailMessage() == null)||(adminForm.getEmailMessage().length() == 0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter a body/message for the email"));
			addErrors(request, messages);

			if (atstep.equalsIgnoreCase("scheduleemail")) {
				return mapping.findForward("emailschedule");
			} else {
				return mapping.findForward("sendgroupemail");
			}
		}

		/** If schedule email validate dates */
		if (atstep.equalsIgnoreCase("scheduleemail")) {
			// validate schedule from date
			// from date is mandatory
			if ((adminForm.getEmailFromDateDD() == null)||(adminForm.getEmailFromDateDD().length() == 0)||
					(adminForm.getEmailFromDateMM() == null)||(adminForm.getEmailFromDateMM().length() == 0)||
					(adminForm.getEmailFromDateYYYY() == null)||(adminForm.getEmailFromDateYYYY().length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter the email schedule from date."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			int tmp;
			try {
				tmp = Integer.parseInt(adminForm.getEmailFromDateYYYY());
			} catch (NumberFormatException e) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "From year must be number."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			// validate start year
			if ((adminForm.getEmailFromDateYYYY() == null)||(adminForm.getEmailFromDateYYYY().length() < 4)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid year entered for email schedule from date (must be 4 characters)."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			try {
				tmp = Integer.parseInt(adminForm.getEmailFromDateMM());
			} catch (NumberFormatException e) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "From date month must be number."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			try {
				tmp = Integer.parseInt(adminForm.getEmailFromDateDD());
			} catch (NumberFormatException e) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "From date day must be number."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			// validate start date (valid month, year, day)
			int startDayInt = Integer.parseInt(adminForm.getEmailFromDateDD());
			int startYearInt = Integer.parseInt(adminForm.getEmailFromDateYYYY());
			int startMonthInt = Integer.parseInt(adminForm.getEmailFromDateMM());



			// validate to month
			if (startMonthInt > 12) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid month entered for email schedule from date."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			if ((startMonthInt == 1)||(startMonthInt == 3)
					||(startMonthInt == 5)||(startMonthInt == 7)
					||(startMonthInt == 8)||(startMonthInt == 10)
					||(startMonthInt == 12)) {
				if (startDayInt > 31) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for email schedule from date."));
					addErrors(request, messages);

					if (atstep.equalsIgnoreCase("scheduleemail")) {
						return mapping.findForward("emailschedule");
					} else {
						return mapping.findForward("sendgroupemail");
					}
				}
			} else if ((startMonthInt == 4)||(startMonthInt == 6)
					||(startMonthInt == 9)||(startMonthInt == 11)) {
				if (startDayInt > 30) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for email schedule from date."));
					addErrors(request, messages);

					if (atstep.equalsIgnoreCase("scheduleemail")) {
						return mapping.findForward("emailschedule");
					} else {
						return mapping.findForward("sendgroupemail");
					}
				}
			} else if ((startMonthInt == 2)) {
				if (startYearInt % 4 == 0) {
					if (startDayInt > 29) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for email schedule from date."));
						addErrors(request, messages);

						if (atstep.equalsIgnoreCase("scheduleemail")) {
							return mapping.findForward("emailschedule");
						} else {
							return mapping.findForward("sendgroupemail");
						}
					}
				} else {
					if (startDayInt > 28) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for start date."));
						addErrors(request, messages);

						if (atstep.equalsIgnoreCase("scheduleemail")) {
							return mapping.findForward("emailschedule");
						} else {
							return mapping.findForward("sendgroupemail");
						}
					}
				}
			}

			// set from date:
			if (startDayInt <= 9) {
				adminForm.setEmailFromDateDD("0"+startDayInt);
			}
			if (startMonthInt <= 9) {
				adminForm.setEmailFromDateMM("0"+startMonthInt);
			}
			adminForm.setEmailFromDate(adminForm.getEmailFromDateYYYY()+"-"+adminForm.getEmailFromDateMM()+"-"+adminForm.getEmailFromDateDD());

			// validate schedule to  date
			// to date is mandatory
			if ((adminForm.getEmailToDateDD() == null)||(adminForm.getEmailToDateDD().length() == 0)||
					(adminForm.getEmailToDateMM() == null)||(adminForm.getEmailToDateMM().length() == 0)||
					(adminForm.getEmailToDateYYYY() == null)||(adminForm.getEmailToDateYYYY().length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Please enter the email schedule to date."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			// validate to year
			if ((adminForm.getEmailToDateYYYY() == null)||(adminForm.getEmailToDateYYYY().length() < 4)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid year entered for email schedule to date (must be 4 characters)."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			try {
				tmp = Integer.parseInt(adminForm.getEmailToDateYYYY());
			} catch (NumberFormatException e) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "To date year must be number."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			try {
				tmp = Integer.parseInt(adminForm.getEmailToDateMM());
			} catch (NumberFormatException e) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "To date month must be number."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			try {
				tmp = Integer.parseInt(adminForm.getEmailToDateDD());
			} catch (NumberFormatException e) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "To date day must be number."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}

			// validate to date (valid month, year, day)
			int toDayInt = Integer.parseInt(adminForm.getEmailToDateDD());
			int toYearInt = Integer.parseInt(adminForm.getEmailToDateYYYY());
			int toMonthInt = Integer.parseInt(adminForm.getEmailToDateMM());

			// validate to month
			if (toMonthInt > 12) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid month entered for email schedule to date."));
				addErrors(request, messages);

				if (atstep.equalsIgnoreCase("scheduleemail")) {
					return mapping.findForward("emailschedule");
				} else {
					return mapping.findForward("sendgroupemail");
				}
			}


			if ((toMonthInt == 1)||(toMonthInt == 3)
					||(toMonthInt == 5)||(toMonthInt == 7)
					||(toMonthInt == 8)||(toMonthInt == 10)
					||(toMonthInt == 12)) {
				if (toDayInt > 31) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for email schedule to date."));
					addErrors(request, messages);

					if (atstep.equalsIgnoreCase("scheduleemail")) {
						return mapping.findForward("emailschedule");
					} else {
						return mapping.findForward("sendgroupemail");
					}
				}
			} else if ((toMonthInt == 4)||(toMonthInt == 6)
					||(toMonthInt == 9)||(toMonthInt == 11)) {
				if (toDayInt > 30) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid day entered for email schedule to date."));
					addErrors(request, messages);

					if (atstep.equalsIgnoreCase("scheduleemail")) {
						return mapping.findForward("emailschedule");
					} else {
						return mapping.findForward("sendgroupemail");
					}
				}
			} else if ((toMonthInt == 2)) {
				if (toYearInt % 4 == 0) {
					if (toDayInt > 29) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for email schedule to date."));
						addErrors(request, messages);

						if (atstep.equalsIgnoreCase("scheduleemail")) {
							return mapping.findForward("emailschedule");
						} else {
							return mapping.findForward("sendgroupemail");
						}
					}
				} else {
					if (toDayInt > 28) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid day entered for email schedule to date."));
						addErrors(request, messages);

						if (atstep.equalsIgnoreCase("scheduleemail")) {
							return mapping.findForward("emailschedule");
						} else {
							return mapping.findForward("sendgroupemail");
						}
					}
				}
			}

			// set to date:
			if (toDayInt <= 9) {
				adminForm.setEmailToDateDD("0"+toDayInt);
			}
			if (toMonthInt <= 9) {
				adminForm.setEmailToDateMM("0"+toMonthInt);
			}
			adminForm.setEmailToDate(adminForm.getEmailToDateYYYY()+"-"+adminForm.getEmailToDateMM()+"-"+adminForm.getEmailToDateDD());

			/** ===========  select bookings between from and to dates */
			try {
				String[] temp = null;
				String tmpDate;
				temp = adminForm.getEmailFromDate().split("-");
				tmpDate = temp[2]+"-"+temp[1]+"-"+temp[0];
				bookingsList = db1.selectBookingList(adminForm.getEmailFromDate(),adminForm.getEmailToDate(),"",adminForm.getSystemID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/** ===========  add bookings to email message/body */
			String emailMsg = "<html> <body> ";
			if (atstep.equalsIgnoreCase("scheduleemail")) {
				emailMsg = emailMsg +"<b>Schedule Email <br>"+
							"Bookings between "+adminForm.getEmailFromDate()+" and "+adminForm.getEmailToDate()+" </b><br>";
			}
			emailMsg = emailMsg + adminForm.getEmailMessage()+" <p> ";
			for (int i=0; i < bookingsList.size(); i++) {
				BookingForm booking = (BookingForm) bookingsList.get(i);

				// get subjects linked to the booking
				try {
					db1.selectBkngSubjectDetial(booking);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/** ===========  select lecturer detail from table */
				String lecturerDetail = null;
				try {
					lecturerDetail = db2.lecturerExistStaff(booking.getLecturerNovellId());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//if not in STAFF does lecturer exist in table USR
				if ((lecturerDetail == null)||(lecturerDetail.length() == 0)) {
					try {
						lecturerDetail = db2.lecturerExistUsr(booking.getLecturerNovellId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// set lecturer detail in formbean
				String[] temp = null;
				temp = lecturerDetail.split("#");
				//booking.setLecturerName(temp[0]);
				//booking.setLecturerNum1(temp[1]);

				emailMsg = emailMsg + "<p> ========================== </p> <p>"+
  			  				"<b>Heading: "+booking.getHeading()+" </b> <br>"+
  			  				"by lecturer: "+booking.getLecturerName()+": "+booking.getLecturerNum1()+" <br>"+
  			  				"Start date: "+booking.getStartDate()+" "+booking.getStartHH()+":"+booking.getStartMM()+" <br>"+
  			  				"End date: "+booking.getEndDate()+" <br>"+
  			  				"Description: "+booking.getDescription()+" <br>";
  			  		
				/** Student e-mail address */
				if (adminForm.getSystemID().equals(adminForm.getSatbook())) {
  			  		emailMsg = emailMsg + "Registration period: "+booking.getRegistrationPeriodDesc()+" <br>"+
		  				"Registration year: "+booking.getRegistrationYear()+" <br>"+ 
		  				"SUBJECTS: ";

	  			  	ArrayList subjectList = booking.getSelectedSubjectsAL();
	  			  	for (int j=0; j < subjectList.size(); j++) {
	  			  		LabelValueBean tmpSubjCode = (LabelValueBean) subjectList.get(j);
	  			  		// add subject to email message/body
	  			  		emailMsg = emailMsg+tmpSubjCode.getValue()+", ";
	  			  		/*
	  			  		subjectListStr = subjectListStr+"'"+tmpSubjCode.getValue()+"'";
	  			  		if (j < subjectList.size()-1) {
	  			  			subjectListStr = subjectListStr+", ";
	  			  		}
	  			  		*/
	
	  	  			  /** ===========  select all student email addresses that must receive the email address. */
	  				  	String[] tmpList = null;
	  				  	try {
	  				  		tmpList = db2.selectStudentEmailList(tmpSubjCode.getValue(),booking.getRegistrationPeriod(),booking.getRegistrationYear());
	  				  	} catch (Exception e) {
	  				  		// TODO Auto-generated catch block
	  				  		e.printStackTrace();
	  				  	}
	  				  	if (tmpList != null) {
	  				  		for (int k=0; k< tmpList.length; k++) {
	  				  			emailToStudentList.add(tmpList[k]);
	  				  		}
	  				  	}
	
	  			  	} // for (int j=0; j < subjectList.size(); j++) {
				} // if (adminForm.getSystemID().equals(adminForm.getSatbook())) {
  			  	emailMsg = emailMsg+"<br>";
			}
			emailMsg = emailMsg+" </html> </body>";

			adminForm.setEmailMessage(emailMsg);
		} // end if (atstep.equalsIgnoreCase("scheduleemail")) {


		
		/** EMAIL TO LISTS */
		// select email address for persons who must receive the email.
		for (int j=0; j< selectedlist.length; j++) {
			LabelValueBean tmpType = (LabelValueBean) adminForm.getAssistantTypeList().get(Integer.parseInt(selectedlist[j]));
			System.out.println("********* Type: "+tmpType+" value: "+tmpType.getValue() + " label: "+tmpType.getLabel());

			/** ==========  LECTURERS EMAIL ========== */
			if (adminForm.getSystemID().equals(adminForm.getSatbook())) {
				if (tmpType.getValue().equalsIgnoreCase("LECTURERS")) {
					// get lecturers
					ArrayList lectList = new ArrayList();
					try {
						lectList = db3.selectLecturers();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
					// get lecturer email address
					for (int i=0; i<lectList.size(); i++) {
						LecturerForm lecturer = (LecturerForm) lectList.get(i);
						try {
							String tmpEmail = db2.getLecturerEmailAddress(lecturer.getNovellId());
							if ((tmpEmail != null)||(tmpEmail.length()>0)) {
								emailToLecturerList.add(tmpEmail);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} // for (int i=0; i<lectList.size(); i++) {
				} // if (tmpType.getValue().equalsIgnoreCase("LECTURERS"))
			} // if (adminForm.getSystemID().equals(adminForm.getSatbook())) {
			
			/** ==========  e-mail to relevant USERS (14) of VENBOOK ========== */
			if (adminForm.getSystemID().equals(adminForm.getVenbook())) {
				if (atstep.equalsIgnoreCase("scheduleemail")) {
					if (tmpType.getValue().equalsIgnoreCase("14")) {
						// SELECT USERS THAT IS LINKED TO BOOKINGS
						for (int i=0; i < bookingsList.size(); i++) {
							BookingForm booking = (BookingForm) bookingsList.get(i);
							try {
								String userEmail = db2.getLecturerEmailAddress(booking.getLecturerNovellId());
								if ((userEmail != null)||(userEmail.length()>0)) {
									if (emailToOtherList.contains(userEmail) == false) {
										emailToOtherList.add(userEmail);
									}
								}
								System.out.println("Relevant users: "+userEmail);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}

			/** ========== e-mail to relevant FACILITATORS (15) of VENBOOK ==========*/
			if (adminForm.getSystemID().equals(adminForm.getVenbook())) {
				if (tmpType.getValue().equalsIgnoreCase("15")) {
					// select facilitators e-mail addresses

				}
			}
			
			/** ========== e-mail to relevant ADMINISTRATORS of VENBOOK ==========*/
			if (adminForm.getSystemID().equals(adminForm.getVenbook())) {
				if (tmpType.getValue().equalsIgnoreCase("13")) {
					// select administrators e-mail addresses
					ArrayList adminList = null;
						try {
							adminList = db3.selectAdmin();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  for(int i=0 ; i<adminList.size(); i++) {
							AdminLinkForm adminLinkForm = (AdminLinkForm) adminList.get(i);
							String userId =adminLinkForm.getAdministrator();
							String systemId=adminLinkForm.getSystem();
							if (systemId.equals(adminForm.getVenbook())) {
								try {
									String userEmail = db2.getLecturerEmailAddress(userId);
									if ((userEmail != null)||(userEmail.length()>0)) {
										if (emailToOtherList.contains(userEmail) == false) {
											emailToOtherList.add(userEmail);
										}
									}
									System.out.println("admin: "+userId+" "+userEmail);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
					  } //for(int i=0 ; i<adminList.size(); i++) {
				} //if (tmpType.getLabel().equalsIgnoreCase("13")) {
			} //if (adminForm.getSystemID().equals(adminForm.getVenbook())) {

			
			/** ==========  SATBOOK: OTHER EMAILS ==========*/
			if (adminForm.getSystemID().equals(adminForm.getSatbook())) {
				if ((!tmpType.getValue().equalsIgnoreCase("LECTURERS"))&&(!tmpType.getValue().equalsIgnoreCase("STUDENTS"))&&(!tmpType.getValue().equalsIgnoreCase("USERS"))) {
					ArrayList tmpList = new ArrayList();
					try {
						tmpList = db3.selectAssistantList(tmpType.getValue(),true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (int k=0; k< tmpList.size(); k++) {
						AssistantRecord assistant = (AssistantRecord) tmpList.get(k);
						if (assistant.getAssistantEmail() != null) {
							emailToOtherList.add(assistant.getAssistantEmail());
							if (emailToOtherListStr.equals("")) {
								emailToOtherListStr = assistant.getAssistantEmail();
							} else {
								emailToOtherListStr=emailToOtherListStr+";"+assistant.getAssistantEmail();
							}
						}
					}
				} // /** ===========  OTHER EMAILS */
	
			} // for (int j=0; j< selectedlist.length; j++) {
		} //if (adminForm.getSystem().equals(adminForm.getSatbook())) {
			
		// send the emails: ASSISTANTS
		System.out.println("EMAIL+++++++++++++++++++++++++++++++++++++++++++++");
		if (emailToOtherList != null) {
			for (int i=0; i < emailToOtherList.size(); i++) {
				String tmpAddress = emailToOtherList.get(i).toString();
				if (tmpAddress != null) {
					System.out.println(tmpAddress);
					/*
					try {
						sendEmail(adminForm.getEmailHeading(),adminForm.getEmailMessage(),tmpAddress);
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
			}
		}

		// send the emails: LECTURERS (emailToLecturerList)
		if (emailToLecturerList != null) {
			for (int i=0; i < emailToLecturerList.size(); i++) {
				String tmpAddress = emailToLecturerList.get(i).toString();
				/*
				if (tmpAddress != null) {
					try {
						sendEmail(adminForm.getEmailHeading(),adminForm.getEmailMessage(),tmpAddress);
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				*/
			}
		}

		// send the emails: STUDENTS (emailToStudentList)
		if (emailToStudentList != null) {
			for (int i=0; i < emailToStudentList.size(); i++) {
				String tmpAddress = emailToStudentList.get(i).toString();
				if (tmpAddress != null) {
/*
					try {
						sendEmail(adminForm.getEmailHeading(),adminForm.getEmailMessage(),tmpAddress);
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
*/
				}
			}
		}

		return mapping.findForward("emailconfirm");
	}
	

	public boolean ValidateEmail(String email) {
		
		//Input the string for validation
	    //email = "xyz@hotmail.com";
	
	    //Set the email pattern string
	    Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	
	    //Match the given string with the pattern
	    Matcher m = p.matcher(email);
	
	    //check whether match is found 
	
	    boolean matchFound = m.matches();
	
	    if (matchFound) {
	      //System.out.println("Valid Email Id.");
	    	return true;
	    } else {
	      //System.out.println("Invalid Email Id.");
	    	return false;
		}
	}

	
	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {
		
		emailService = (EmailService) ComponentManager.get(EmailService.class);

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");

		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");
		//contentList.add("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");

		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} // end of sendEmail

}

