//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.ebookshop.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.cover.EventTrackingService;
import org.sakaiproject.event.cover.UsageSessionService;
import org.sakaiproject.tool.cover.ToolManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.ebookshop.dao.Advert;
import za.ac.unisa.lms.tools.ebookshop.dao.EshopSakaiDAO;
import za.ac.unisa.lms.tools.ebookshop.dao.EshopStudentSystemDAO;
import za.ac.unisa.lms.tools.ebookshop.dao.Student;
import za.ac.unisa.lms.tools.ebookshop.forms.EshopForm;
import za.ac.unisa.utils.CodeProfiler;
/**
 * MyEclipse Struts
 * Creation date: 02-07-2006
 *
 * XDoclet definition:
 * @struts:action parameter="action"
 */
public class EshopAction extends DispatchAction {

	public ActionForward showAdverts(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			EshopForm eShopForm = (EshopForm)form;
			EshopSakaiDAO sakaiDAO = new EshopSakaiDAO();
			CodeProfiler profiler = new CodeProfiler();
			profiler.start(request);
			ArrayList list = sakaiDAO.getBooksAdvertised(eShopForm.getOrderBy());
			profiler.stop(request,"unisa.ebookshop EshopAction: getBooksAdvertised:List of advertisement");
			request.setAttribute("advertlist", list);

			//Session currentSession = SessionManager.getCurrentSession();
			//String userId = currentSession.getUserId();
			//if (userId != null) {
			//	User user = UserDirectoryService.getUser(userId);
			//}

			return mapping.findForward("showadverts");
	}

	public ActionForward viewAdvert(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			return mapping.findForward("viewadvert");
	}

	public ActionForward addAdvert(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			EshopForm eShopForm = (EshopForm)form;
			clear(eShopForm);

			return mapping.findForward("addadvert");
	}

	public ActionForward processNewAdd(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			EshopForm eShopForm = (EshopForm)form;
			String buttonClicked = eShopForm.getButtonClicked();
			ActionMessages messages = new ActionMessages();
			EshopSakaiDAO sakaiDAO = new EshopSakaiDAO();
			EshopStudentSystemDAO studentDAO = new EshopStudentSystemDAO();
			eShopForm.getAdvert().setDateAdded(dateAdded());

			//Session currentSession = SessionManager.getCurrentSession();
			//String userId = currentSession.getUserId();
			//User user = null;
			//UsageSession usageSession = null;
				//if (userId != null) {
				//	user = UserDirectoryService.getUser(userId);
					//usageSession = UsageSessionService.startSession(user.getEid(), request.getRemoteAddr(), request.getHeader("user-agent"));
				//	usageSession = UsageSessionService.getSession();
				//}else {

					//user = UserDirectoryService.getUserByEid(eShopForm.getStudent().getStudentNumber());
					//usageSession = UsageSessionService.startSession(eShopForm.getStudent().getStudentNumber(), request.getRemoteAddr(), request.getHeader("user-agent"));
					//usageSession = UsageSessionService.getSession();
				//}

			if(buttonClicked.equalsIgnoreCase("Submit")) {
				if(validateAddAdvertPage(eShopForm)) {

					//insert new add
					try {

//						int i = Integer.parseInt(eShopForm.getStudent().getStudentNumber());
					}catch(NumberFormatException nfe) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Only numerical values should be used for the student number field."));
						addErrors(request,messages);
						return mapping.findForward("revise");
					}
					sakaiDAO.insertAdd(eShopForm.getAdvert(), eShopForm.getStudent());
					UsageSession usageSession = UsageSessionService.startSession(eShopForm.getStudent().getStudentNumber(), request.getRemoteAddr(), request.getHeader("user-agent"));
					EventTrackingService.post(
							EventTrackingService.newEvent(EventTrackingTypes.EVENT_EBOOKSHOP_ADD, ToolManager.getCurrentPlacement().getContext(), false),usageSession);

					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Advertisement information has been updated."));
					addMessages(request,messages);
					ArrayList list = sakaiDAO.getBooksAdvertised("");
					request.setAttribute("advertlist", list);
					clear(eShopForm);
					return mapping.findForward("showadverts");

				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", eShopForm.getErrorMessage()));
					addErrors(request,messages);
					return mapping.findForward("addadvert");
				}
			}
			if(buttonClicked.equalsIgnoreCase("Preview")) {
				if(validateAddAdvertPage(eShopForm)) {
					eShopForm.getAdvert().setCourseDescription(studentDAO.studyUnitDescription(eShopForm.getAdvert().getCourseCode()));
					return mapping.findForward("preview");
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", eShopForm.getErrorMessage()));
					addErrors(request,messages);
					return mapping.findForward("addadvert");
				}

			}
			if(buttonClicked.equalsIgnoreCase("Clear")) {
				clear(eShopForm);
				return mapping.findForward("addadvert");
			}
			if(buttonClicked.equalsIgnoreCase("Cancel")) {
				clear(eShopForm);
				ArrayList list = sakaiDAO.getBooksAdvertised("");
				request.setAttribute("advertlist", list);

				return mapping.findForward("showadverts");
			}

			return mapping.findForward("addadvert");
	}

	public ActionForward processPreviewAction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EshopForm eShopForm = (EshopForm)form;
		String buttonClicked = eShopForm.getButtonClicked();
		ActionMessages messages = new ActionMessages();
		EshopSakaiDAO sakaiDAO = new EshopSakaiDAO();


		if(buttonClicked.equalsIgnoreCase("Submit")) {
			if(!validateAddAdvertPage(eShopForm)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", eShopForm.getErrorMessage() ));
				addErrors(request,messages);
				return mapping.findForward("editadvert");
			}
			//update the database
			if(!(null == eShopForm.getBookID())){
				sakaiDAO.editAdvert(eShopForm.getAdvert(), eShopForm.getBookID());
			}else {
				sakaiDAO.insertAdd(eShopForm.getAdvert(), eShopForm.getStudent());
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Advertisement information has been updated."));
			addMessages(request,messages);
			ArrayList list = sakaiDAO.getBooksAdvertised("");
			request.setAttribute("advertlist", list);

			clear(eShopForm);
			return mapping.findForward("showadverts");
		}
		if(buttonClicked.equalsIgnoreCase("Revise")) {
			buttonClicked = "";
			return mapping.findForward("editadvert");
		}
		if(buttonClicked.equalsIgnoreCase("Cancel")) {
			clear(eShopForm);
			ArrayList list = sakaiDAO.getBooksAdvertised("");
			request.setAttribute("advertlist", list);
			return mapping.findForward("showadverts");
		}

		return null;
	}

	public ActionForward editAdvert(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EshopForm eShopForm = (EshopForm)form;
		String buttonClicked = eShopForm.getEditButton();
		EshopSakaiDAO sakaiDAO = new EshopSakaiDAO();
		EshopStudentSystemDAO studentDAO = new EshopStudentSystemDAO();
		ArrayList list = sakaiDAO.getBooksAdvertised("");
		request.setAttribute("advertlist", list);
		ActionMessages messages = new ActionMessages();



		//Session currentSession = SessionManager.getCurrentSession();
		//String userId = currentSession.getUserId();
		//User user = null;
		//UsageSession usageSession = null;
			//if (userId != null) {
			//	user = UserDirectoryService.getUser(userId);
				//usageSession = UsageSessionService.startSession(user.getEid(), request.getRemoteAddr(), request.getHeader("user-agent"));
			//	usageSession = UsageSessionService.getSession();

			//}else {
			//	user = UserDirectoryService.getUserByEid(eShopForm.getStudent().getStudentNumber());
				//usageSession = UsageSessionService.startSession(eShopForm.getStudent().getStudentNumber(), request.getRemoteAddr(), request.getHeader("user-agent"));
			//	usageSession = UsageSessionService.getSession();
			//}

		if(buttonClicked.equalsIgnoreCase("Submit")) {
			if(validateEditAdvertPage(eShopForm)) {
				/**do update if bookId is not null else do an insert -- this method is access for two different scenarios**/
				UsageSession usageSession = UsageSessionService.startSession(eShopForm.getStudent().getStudentNumber(), request.getRemoteAddr(), request.getHeader("user-agent"));
				if(!(null == eShopForm.getBookID())) {
					sakaiDAO.editAdvert(eShopForm.getAdvert(),eShopForm.getBookID());
					EventTrackingService.post(
							EventTrackingService.newEvent(EventTrackingTypes.EVENT_EBOOKSHOP_UPDATE, ToolManager.getCurrentPlacement().getContext(), false),usageSession);

				}else {
					sakaiDAO.insertAdd(eShopForm.getAdvert(), eShopForm.getStudent());
					EventTrackingService.post(
							EventTrackingService.newEvent(EventTrackingTypes.EVENT_EBOOKSHOP_ADD, ToolManager.getCurrentPlacement().getContext(), false), usageSession);

				}
				list = sakaiDAO.getBooksAdvertised("");
				request.setAttribute("advertlist", list);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Advertisement information has been updated"));
				addMessages(request,messages);
				clear(eShopForm);
				eShopForm.setOrderBy("COURSE_CODE");
				return mapping.findForward("showadverts");
			}else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", eShopForm.getErrorMessage()));
				addErrors(request,messages);
				return mapping.findForward("editadvert");
			}
		}
		if(buttonClicked.equalsIgnoreCase("Preview")) {
			if(validateAddAdvertPage(eShopForm)) {
				eShopForm.getAdvert().setCourseDescription(studentDAO.studyUnitDescription(eShopForm.getAdvert().getCourseCode()));
			}else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", eShopForm.getErrorMessage() ));
				addErrors(request,messages);
				return mapping.findForward("editadvert");
			}
			return mapping.findForward("preview");
		}
		if(buttonClicked.equalsIgnoreCase("Cancel")) {
			clear(eShopForm);
			return mapping.findForward("showadverts");
		}
		if(buttonClicked.equalsIgnoreCase("Revise")) {
			buttonClicked = "";
			eShopForm.getAdvert().setCourseDescription(studentDAO.studyUnitDescription(eShopForm.getAdvert().getCourseCode()));
			return mapping.findForward("preview");
		}

		return null;
	}

	public ActionForward deleteActionTaken(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EshopForm eShopForm = (EshopForm)form;
		String buttonClicked = eShopForm.getButtonClicked();
		EshopSakaiDAO sakaiDAO = new EshopSakaiDAO();

		//Session currentSession = SessionManager.getCurrentSession();
		//String userId = currentSession.getUserId();
		//User user = null;
		//UsageSession usageSession = null;

		//if (userId != null) {
		//	user = UserDirectoryService.getUser(userId);
			//usageSession = UsageSessionService.startSession(user.getEid(), request.getRemoteAddr(), request.getHeader("user-agent"));
		//	usageSession = UsageSessionService.getSession();

		//}else {
		//	user = UserDirectoryService.getUserByEid(eShopForm.getStudent().getStudentNumber());
			//usageSession = UsageSessionService.startSession(eShopForm.getStudent().getStudentNumber(), request.getRemoteAddr(), request.getHeader("user-agent"));
		//	usageSession = UsageSessionService.getSession();
		//}


		if(buttonClicked.equalsIgnoreCase("Cancel")) {
			ArrayList list = sakaiDAO.getBooksAdvertised("");
			request.setAttribute("advertlist", list);
			clear(eShopForm);
			return mapping.findForward("showadverts");
		}
		if(buttonClicked.equalsIgnoreCase("Delete")) {
			sakaiDAO.deleteAdd(eShopForm.getBookID());
			UsageSession usageSession = UsageSessionService.startSession(eShopForm.getStudent().getStudentNumber(), request.getRemoteAddr(), request.getHeader("user-agent"));
			EventTrackingService.post(
					EventTrackingService.newEvent(EventTrackingTypes.EVENT_EBOOKSHOP_DELETE, ToolManager.getCurrentPlacement().getContext(), false),usageSession);

			ArrayList list = sakaiDAO.getBooksAdvertised("");
			request.setAttribute("advertlist", list);
			clear(eShopForm);
			return mapping.findForward("showadverts");
		}

		return mapping.findForward("showadverts");
	}

	public ActionForward reviseLink(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EshopForm eShopForm = (EshopForm)form;
		eShopForm.setBookID(request.getParameter("bookId"));
		eShopForm.setButtonClicked(null);
		return mapping.findForward("revise");
	}

	public ActionForward reviseActionTaken(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {


		EshopForm eShopForm = (EshopForm)form;
		String buttonClicked = eShopForm.getButtonClicked();
		ActionMessages messages = new ActionMessages();
		EshopSakaiDAO sakaiDAO = new EshopSakaiDAO();
		EshopStudentSystemDAO studentSystemDAO = new EshopStudentSystemDAO();

//		Student student = eShopForm.getStudent();

		ArrayList list = sakaiDAO.getBooksAdvertised("");
		request.setAttribute("advertlist", list);
		eShopForm.setAdvert(sakaiDAO.populateAdvertObject(eShopForm.getBookID(), eShopForm));

		if(buttonClicked.equalsIgnoreCase("Cancel")) {
			clear(eShopForm);
			return mapping.findForward("showadverts");
		}

		try {
//			int i = Integer.parseInt(student.getStudentNumber());
		}catch(NumberFormatException nfe) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Only numerical values should be used for the student number field."));
			addErrors(request,messages);
			return mapping.findForward("revise");
		}

		if(buttonClicked.equalsIgnoreCase("Edit Advertisement")) {
			if(sakaiDAO.myUnisaAccountExist(eShopForm.getStudent().getStudentNumber())) {
				if(sakaiDAO.authenticateMyUnisaUser(eShopForm.getStudent().getStudentNumber(), eShopForm.getStudent().getPassword())) {
						if(sakaiDAO.isAddCreator(eShopForm.getBookID(), eShopForm.getStudent().getStudentNumber())){
							return mapping.findForward("editadvert");
						}else {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "The information you supplied does not match those submitted for this advertisement."));
							addErrors(request,messages);
							clear(eShopForm);
							eShopForm.setOrderBy("COURSE_CODE");
							return mapping.findForward("showadverts");
						}
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Incorrect myUnisa password."));
					addErrors(request,messages);
					return mapping.findForward("revise");
				}
			}else if(studentSystemDAO.regularAccountExist(eShopForm.getStudent().getStudentNumber())) {
				if(sakaiDAO.isAddCreator(eShopForm.getBookID(), eShopForm.getStudent().getStudentNumber(), eShopForm.getStudent().getPassword())) {
					return mapping.findForward("editadvert");
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The information you supplied does not match those submitted for this advertisement."));
					addErrors(request,messages);
					clear(eShopForm);
					return mapping.findForward("showadverts");
				}
			}else {
				if(!(validateReviseAdvertPage(eShopForm))) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", eShopForm.getErrorMessage()));
					addErrors(request,messages);
					return mapping.findForward("revise");
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage", "Error Message: The information supplied does not match those submitted for this advertisement."));
				addErrors(request,messages);
				clear(eShopForm);
				return mapping.findForward("showadverts");
			}
		}

		if(buttonClicked.equalsIgnoreCase("Delete Advertisement")) {
			if(sakaiDAO.myUnisaAccountExist(eShopForm.getStudent().getStudentNumber())) {
				if(sakaiDAO.authenticateMyUnisaUser(eShopForm.getStudent().getStudentNumber(), eShopForm.getStudent().getPassword())) {
						if(sakaiDAO.isAddCreator(eShopForm.getBookID(), eShopForm.getStudent().getStudentNumber())){
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "!Are you sure you you want to delete the following Advertisement?"));
							addErrors(request,messages);
							return mapping.findForward("delete");
						}else {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage", "The information you supplied does not match those submitted for this advertisement."));
							addErrors(request,messages);
							clear(eShopForm);
							return mapping.findForward("showadverts");
						}
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Incorrect myUnisa password."));
					addErrors(request,messages);
					return mapping.findForward("revise");
				}
			}else if(studentSystemDAO.regularAccountExist(eShopForm.getStudent().getStudentNumber())) {
				if(sakaiDAO.isAddCreator(eShopForm.getBookID(), eShopForm.getStudent().getStudentNumber(), eShopForm.getStudent().getPassword())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "!Are you sure you you want to delete the following Advertisement?"));
					addMessages(request,messages);
					return mapping.findForward("delete");
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The information you supplied does not match those submitted for this advertisement."));
					addErrors(request,messages);
					clear(eShopForm);
					return mapping.findForward("showadverts");
				}
			}else {
				if(!(validateReviseAdvertPage(eShopForm))) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", eShopForm.getErrorMessage()));
					addErrors(request,messages);
					return mapping.findForward("revise");
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.generalmessage", "You are not a current or former Unisa student."));
				addErrors(request,messages);
				clear(eShopForm);
				return mapping.findForward("showadverts");
			}
			//return mapping.findForward("delete");
		}

		if(buttonClicked.equalsIgnoreCase("Cancel")) {
			clear(eShopForm);
			return mapping.findForward("showadverts");
		}

		return mapping.findForward("showadverts");
	}

	private String dateAdded() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(d);
	}

	private void clear(EshopForm form) {
		form.setAdvert(new Advert());
		form.setStudent(new Student());
	}

	private boolean validateAddAdvertPage(EshopForm eshopForm) throws Exception{
		boolean valid = true;
		EshopSakaiDAO sakaiDAO = new EshopSakaiDAO();
		EshopStudentSystemDAO studentSystemDAO = new EshopStudentSystemDAO();
		String courseCode = eshopForm.getAdvert().getCourseCode();
		String addHeading = eshopForm.getAdvert().getAddHeading();
		String addText = eshopForm.getAdvert().getAddText();
		String studentNumber = eshopForm.getStudent().getStudentNumber();
		String contactDetails = eshopForm.getAdvert().getContactDetails();
		String password = eshopForm.getStudent().getPassword();
		String confirmPassword = eshopForm.getStudent().getConfirmPassword();

		//no password validation should happen when the user is a myUnisa student

		if((courseCode.indexOf("\'") != -1) | (courseCode.indexOf("\"") != -1)) {
			eshopForm.setErrorMessage("Invalid character input for Course Code field");
			valid = false;
		}else {
			String temp = studentSystemDAO.studyUnitCoursecode(eshopForm.getAdvert().getCourseCode());
			if((null == temp)||(temp.equalsIgnoreCase("no description"))) {
				eshopForm.setErrorMessage("The code " + courseCode + " you entered is not a valid Unisa course code. Please correct the code or click Cancel to exit.");
				valid = false;
			}
			eshopForm.getAdvert().setCourseCode(temp);
		}

		if((studentNumber.indexOf("\'") != -1) | (studentNumber.indexOf("\"") != -1)) {
			eshopForm.setErrorMessage("Invalid character input for Student Number field");
			valid = false;
		}else {
			if(!(sakaiDAO.myUnisaAccountExist(studentNumber))) {
				if(null == confirmPassword | confirmPassword.equalsIgnoreCase("")) {
					eshopForm.setErrorMessage("'Enter Password Again' is a required field");
					valid = false;
				}
				if(null == password | password.equalsIgnoreCase("")) {
					eshopForm.setErrorMessage("'Password' is a required field");
					valid = false;
				}
				if(!password.equals(confirmPassword)) {
					eshopForm.setErrorMessage("Your password entries do not match. Please correct your information.");
					valid = false;
				}
			}

			if(!(studentSystemDAO.regularAccountExist(studentNumber))) {
				eshopForm.setErrorMessage("Your student record was not found.");
				valid = false;
			}
		}

		if(null == studentNumber) {
			eshopForm.setErrorMessage("'Student number' is a required field");
			valid = false;
		}

		if(null == courseCode | courseCode.equalsIgnoreCase("")) {
			eshopForm.setErrorMessage("'Course code' is a required field");
			valid = false;
		}else if(courseCode.length() <= 5) {
				eshopForm.setErrorMessage("At least 6 characters must be entered in the course code field");
				valid = false;
		}

		if(null == addHeading | addHeading.equalsIgnoreCase("")) {
			eshopForm.setErrorMessage("'Advertisement Heading' is a required field");
			valid = false;
		}
		else if(null == addText | addText.equalsIgnoreCase("")) {
			eshopForm.setErrorMessage("'Advertisement Text' is a required field");
			valid = false;
		}
		else if(addText.length() > 500) {
			eshopForm.setErrorMessage("Advertisemt Text: only 500 characters allowed. Please alter your text.");
			valid = false;
		}

		else if(null == studentNumber | studentNumber.equalsIgnoreCase("")) {
			eshopForm.setErrorMessage("'Student number' is a required field");
			valid = false;
		}

		else if(null == contactDetails | contactDetails.equalsIgnoreCase("") ) {
			eshopForm.setErrorMessage("'Contact Details' is a required field");
			valid = false;
		}

		return valid;
	}


	private boolean validateEditAdvertPage(EshopForm eshopForm) throws Exception{
		boolean valid = true;
		String courseCode = eshopForm.getAdvert().getCourseCode();
		String addHeading = eshopForm.getAdvert().getAddHeading();
		String addText = eshopForm.getAdvert().getAddText();
		String contactDetails = eshopForm.getAdvert().getContactDetails();

		EshopStudentSystemDAO studentSystemDAO = new EshopStudentSystemDAO();

		if((studentSystemDAO.studyUnitDescription(eshopForm.getAdvert().getCourseCode()).equalsIgnoreCase("no description"))) {
			eshopForm.setErrorMessage("The code " + courseCode + " you entered is not a valid Unisa study unit code. Please correct the code or click Cancel to exit.");
			valid = false;
		}else if(courseCode.length() <= 5) {
			eshopForm.setErrorMessage("At least 6 characters must be entered in the course code field");
			valid = false;
		}
		else if(null == courseCode | courseCode.equalsIgnoreCase("")) {
			eshopForm.setErrorMessage("'Course code' is a required field");
			valid = false;
		}
		else if(null == addHeading | addHeading.equalsIgnoreCase("")) {
			eshopForm.setErrorMessage("'Advertisement Heading' is a required field");
			valid = false;
		}
		else if(null == addText | addText.equalsIgnoreCase("")) {
			eshopForm.setErrorMessage("'Advertisement Text' is a required field");
			valid = false;
		}
		else if(addText.length() > 500) {
			eshopForm.setErrorMessage("Advertisemt Text: only 500 characters allowed. Please alter your text.");
			valid = false;
		}
		else if(null == contactDetails | contactDetails.equalsIgnoreCase("") ) {
			eshopForm.setErrorMessage("'Contact Details' is a required field");
			valid = false;
		}


		return valid;
	}

	private boolean validateReviseAdvertPage(EshopForm eshopForm) {
		boolean valid = true;
		String studentNumber = eshopForm.getStudent().getStudentNumber();
		String password = eshopForm.getStudent().getPassword();

		if(null == studentNumber | studentNumber.equalsIgnoreCase("")) {
			eshopForm.setErrorMessage("'Student number' is a required field");
			valid = false;
		}
		if(null == password | password.equalsIgnoreCase("")) {
				eshopForm.setErrorMessage("'Password' is a required field");
				valid = false;
		}

		return valid;
	}

}

