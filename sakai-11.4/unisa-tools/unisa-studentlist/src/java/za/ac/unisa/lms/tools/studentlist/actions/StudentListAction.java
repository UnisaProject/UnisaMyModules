//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentlist.actions;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
//import org.apache.struts.action.InvalidCancelException;//InvalidCancelException
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.studentlist.dao.StudentDetail;
import za.ac.unisa.lms.tools.studentlist.dao.StudentListDAO;
import za.ac.unisa.lms.tools.studentlist.dao.StudentListSites;
import za.ac.unisa.lms.tools.studentlist.forms.StudentListForm;

/**
 * MyEclipse Struts
 * Creation date: 11-16-2005
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class StudentListAction extends DispatchAction {

	 private SessionManager sessionManager;
	 private UserDirectoryService userDirectoryService;
	 private ToolManager toolManager;
	 private EventTrackingService eventTrackingService;
	 private UsageSessionService usageSessionService;
	 private User user;
	public ActionForward step1(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception{
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		user=(User) ComponentManager.get(User.class);
		userDirectoryService=(UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		usageSessionService=(UsageSessionService) ComponentManager.get(UsageSessionService.class);
		toolManager=(ToolManager) ComponentManager.get(ToolManager.class);
		
		StudentListForm studentlistform = (StudentListForm)form;
		studentlistform.setUserName(request.getAttribute("user") + "");
		request.setAttribute("formlist", studentlistform.getFormat());

		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		//User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}
		String hod;
		StudentListDAO studentlistDAO = new StudentListDAO();
		String persnr =studentlistDAO.getPersonalNR(studentlistform.getUserName().toUpperCase());
		hod = studentlistDAO.verifyHodOrNot(persnr);
		studentlistform.setHod(hod);
		if(!(hod.equals(""))){
			studentlistform.setHodLoggedIn("hod");
		}else{
			studentlistform.setHodLoggedIn("");
		}
	
		
		UsageSession usageSession = usageSessionService.getSession();
		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDENTLIST_VIEW, toolManager.getCurrentPlacement().getContext(), false), usageSession);

		return mapping.findForward("step1");
	}
	
	public ActionForward displayOption(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentListForm studentlistform = (StudentListForm)form;
		studentlistform.setDisplayOption("Select display option");		
		if(studentlistform.getDisplayOption().equals("Select display option")){
			
			return mapping.findForward("displayOptions");
		}
		return mapping.findForward("displayOptions");
	}
	
	public ActionForward step2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentListDAO studentlistDAO = new StudentListDAO();
		StudentListForm studentlistform = (StudentListForm)form;
		studentlistform.setNoSitesExists(false);
		ArrayList listofsites = new ArrayList();
		ActionMessages messages = new ActionMessages();
		String hod=studentlistform.getHod();
		String currentYear="";
		String semester="";
		Calendar calendar = Calendar.getInstance();
		Integer year = new Integer(calendar.get(Calendar.YEAR));
       if(studentlistform.getDisplayOption().equals("")){
    	   messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select display option"));
			addErrors(request, messages);
			return mapping.findForward("displayOptions");
		}
		if(studentlistform.getDisplayOption().equals("curYear")){
			currentYear=year.toString();
			semester="";
		}else if(studentlistform.getDisplayOption().equals("curYearSem1")){
			currentYear=year.toString();
			semester="1";
		}else if(studentlistform.getDisplayOption().equals("curYearSem2")){
			currentYear=year.toString();
			semester="2";
		}else if(studentlistform.getDisplayOption().equals("curYearYear")){
			currentYear=year.toString();
			semester="0";
		}else if(studentlistform.getDisplayOption().equals("all")){
			currentYear="";
			semester="";
		}
		if(!(hod).equals("")){
			if(studentlistform.getSelectView().equals("d")){
				String dptCode=studentlistDAO.getDepartmentCode(hod);
				listofsites = studentlistDAO.getSitesPerHod(dptCode,currentYear,semester);
				if(!listofsites.isEmpty()) {
					studentlistform.setSites(listofsites);
				}
				request.setAttribute("sitesPerLecturer", studentlistform.getSites());
				return mapping.findForward("step2");				
			}else if(studentlistform.getSelectView().equals("l")){
				listofsites = studentlistDAO.getSitesPerLecturer(studentlistform.getUserName(),currentYear,semester);
				if(!listofsites.isEmpty()) {
					studentlistform.setSites(listofsites);
				}
				request.setAttribute("sitesPerLecturer", studentlistform.getSites());

				return mapping.findForward("step2");
				
			}
		}else
			try {
			
				listofsites = studentlistDAO.getSitesPerLecturer(studentlistform.getUserName(),currentYear,semester);
				if(!listofsites.isEmpty()) {
					studentlistform.setSites(listofsites);
				}else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Only lecturer roles can use this tool."));
					addErrors(request, messages);
					studentlistform.setNoSitesExists(true);
					return mapping.findForward("step2");
				}
			}catch(Exception ex) {
				throw ex;
			}

			request.setAttribute("sitesPerLecturer", studentlistform.getSites());

			return mapping.findForward("step2");
		}
		
	

	public ActionForward step3(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		StudentListForm studentlistform = (StudentListForm)form;
		ActionMessages messages = new ActionMessages();
		StudentListDAO studentListDAO = new StudentListDAO();
		studentlistform.setGroupOption("");
		if(isCancelled(request)) {
					studentlistform.setIndexNrOfSelectedSite(null);
					studentlistform.setSelectView("l");
					uncheckStudentList(studentlistform);
					return mapping.findForward("step1");
				}
		 if(studentlistform.getIndexNrOfSelectedSite() == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have not selected any subjects."));
			addErrors(request, messages);
			request.setAttribute("sitesPerLecturer", studentlistform.getSites());
			return mapping.findForward("step2");
		}
		 	 
		 if(studentlistform.getIndexNrOfSelectedSite().length==1){
			 List list = (List)studentlistform.getSites();			
				Vector sites = new Vector();
				String array[] = studentlistform.getIndexNrOfSelectedSite();			
				Integer index = null;
				for(int j = 0; j < array.length; j++ ) {
					index = new Integer(array[j]);  
					sites.add(((StudentListSites)(list.get(index.intValue()))).getLecturerSites());				
					String selectedCourse = (sites.get(j)).toString().substring(0,7);
					String selectedYear = "20" +(sites.get(j)).toString().substring(8,10);
					String selectedSemester = (sites.get(j)).toString().substring(11,13);
					boolean checkCurrentCourse=studentListDAO.checkCurrentModule(selectedCourse,selectedYear,selectedSemester);
					if(checkCurrentCourse){
						studentlistform.setGroupOption("groupOption");
					}else{
						studentlistform.setGroupOption("");
					}
				}
		 }else{
			 studentlistform.setGroupOption(""); 
		 }
		return mapping.findForward("step3");
	}


	public ActionForward step4(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		StudentListForm studentlistform = (StudentListForm)form;
		ActionMessages messages = new ActionMessages();
		addConfinements2RequestObject(request);

		if(request.getParameter("button") != null) {
			if((request.getParameter("button")).equals("Back")) {
				request.setAttribute("sitesPerLecturer", studentlistform.getSites());
				//revised 30-12-2005
				studentlistform.setIndexNrOfSelectedSite(null);
				uncheckStudentList(studentlistform);
				return mapping.findForward("step2");
			}
		}

		if(isCancelled(request)) {
			//revised 30-12-2005
			studentlistform.setIndexNrOfSelectedSite(null);
			uncheckStudentList(studentlistform);
			uncheckConfinements(studentlistform);
			studentlistform.setSelectView("l");

			return mapping.findForward("step1");
		}

		if(nothingChecked(studentlistform)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have not selected any details to be included"));
			addErrors(request, messages);
			request.setAttribute("sitesPerLecturer", studentlistform.getSites());
			uncheckStudentList(studentlistform);

			return mapping.findForward("step3");
		}

		StudentListDAO studentListDAO = new StudentListDAO();
		

		try {
			studentlistform.setHomeLanguages(studentListDAO.getHomeLanguages());
			studentlistform.setCountries(studentListDAO.getCountries());
			studentlistform.setProvinces(studentListDAO.getProvinces());
			studentlistform.setRaces(studentListDAO.getRaces());
			studentlistform.setRegions(studentListDAO.getRegions());
			studentlistform.setDistricts(studentListDAO.getDistricts());
			//registration and residential regions are using the same lookup table
			studentlistform.setResRegions(studentListDAO.getRegions());
			studentlistform.setDisabilityList(studentListDAO.getDisabilityTypes());
		}catch(Exception ex) {
			throw ex;
		}

		request.setAttribute("homeLanguages", studentlistform.getHomeLanguages());
		request.setAttribute("countries", studentlistform.getCountries());
		request.setAttribute("provinces", studentlistform.getProvinces());
		request.setAttribute("races", studentlistform.getRaces());
		request.setAttribute("regions", studentlistform.getRegions());
		request.setAttribute("resRegions", studentlistform.getResRegions());
		request.setAttribute("districts", studentlistform.getDistricts());
		request.setAttribute("disabilityTypeList", studentlistform.getDisabilityList());
		
		
		//request.setAttribute("registered", studentListDAO.getAllRegisteredStudents());
		return mapping.findForward("step4");
	}


	public ActionForward generatelist (
			
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		
		StudentListForm studentlistform = (StudentListForm)form;
		StudentListDAO studentListDAO = new StudentListDAO();
		ActionMessages messages = new ActionMessages();
		
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		userDirectoryService=(UserDirectoryService) ComponentManager.get(UserDirectoryService.class);
		usageSessionService=(UsageSessionService) ComponentManager.get(UsageSessionService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userId = currentSession.getUserId();
		User user = null;
		if (userId != null) {
			user = userDirectoryService.getUser(userId);
		}
		UsageSession usageSession = usageSessionService.getSession();

		String selectedCourseList = "";

		if(request.getParameter("button") != null) {
			if((request.getParameter("button")).equals("Back")) {
				request.setAttribute("sitesPerLecturer", studentlistform.getSites());
				//revised 30-12-2005
				uncheckStudentList(studentlistform);
				return mapping.findForward("step3");
			}
		}

		if(isCancelled(request)) {
			//revised the 30-12-2005
			studentlistform.setIndexNrOfSelectedSite(null);
			uncheckStudentList(studentlistform);
			uncheckConfinements(studentlistform);
			studentlistform.setSelectView("l");
			return mapping.findForward("step1");
		}

		List list = (List)studentlistform.getSites();
		
		
		
		Vector sites = new Vector();
		String array[] = studentlistform.getIndexNrOfSelectedSite();
		
		
		Integer index = null;

		for(int j = 0; j < array.length; j++ ) {
			index = new Integer(array[j]);  //initially the lect selected course sites in step2
		
			sites.add(((StudentListSites)(list.get(index.intValue()))).getLecturerSites());  //adding the lect selected site names to sites  
			//System.out.println("sites "+sites);
			
			//selectedCourseList += (list.get(index.intValue())).toString();
			selectedCourseList += (sites.get(j)).toString().substring(0,7) + "-" +(sites.get(j)).toString().substring(8,10) + "-"+(sites.get(j)).toString().substring(11,13);
			//System.out.println("selected Course List "+selectedCourseList);
			if(j == (array.length -1)) {
				selectedCourseList += "";
			}else {
				selectedCourseList += ", ";
			}
		}


		try {
			
			if (studentlistform.getConfineStudentList().equals("C")) {
				
                    studentlistform.setCompletedStudents(studentListDAO.getAllCompletedStudents(sites)); 
                    studentlistform.setStudents(studentListDAO.getCStudents(studentlistform.getConfineGender(),
                		//	studentlistform.getConfineEmailAddress(),
                			studentlistform.getConfineHomeLanguage(),
                			studentlistform.getConfineCorrespondenceLanguage(),
                			studentlistform.getConfineCountry(),
                			studentlistform.getConfineProvince(),
                			studentlistform.getConfineStudentsregion(),
                			studentlistform.getConfineResidentialRegion(),
                			studentlistform.getConfineRace(),
                			studentlistform.getConfineOnlineUser(),
                			studentlistform.isRegion(),
                			studentlistform.isResRegion(),
                			studentlistform.getDisability(),
                			sites

                	        ));	
                    
			

			        
		} else {

	        studentlistform.setRegistedStudents(studentListDAO.getAllRegisteredStudents(sites));
	        studentlistform.setStudents(studentListDAO.getStudents(studentlistform.getConfineGender(),
		//	studentlistform.getConfineEmailAddress(),
			studentlistform.getConfineHomeLanguage(),
			studentlistform.getConfineCorrespondenceLanguage(),
			studentlistform.getConfineCountry(),
			studentlistform.getConfineProvince(),
			studentlistform.getConfineStudentsregion(),
			studentlistform.getConfineResidentialRegion(),
			studentlistform.getConfineRace(),
			studentlistform.getConfineOnlineUser(),
			studentlistform.isRegion(),
			studentlistform.isResRegion(),
			studentlistform.getConfineGroupedStudent(),
			studentlistform.getDisability(),
			sites
	        ));			
		}




		}catch(Exception ex) {
			throw ex;
		}

		//if no students returned from query
		if(studentlistform.getStudents() == null | (studentlistform.getStudents()).isEmpty()) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No students exist for your specific query"));
			addErrors(request, messages);
			return mapping.findForward("step1");
		}

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		request.setAttribute("studentCount", studentlistform.getStudents().size() + "");
		request.setAttribute("listDate", sdf.format(d));
		request.setAttribute("selectedCourseList", selectedCourseList);
		request.setAttribute("studentDetails", studentlistform.getStudents());
		toolManager=(ToolManager) ComponentManager.get(ToolManager.class);
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		if(studentlistform.getListOutput().equals("Display")) {
		
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDENTLIST_DISPLAY, toolManager.getCurrentPlacement().getContext(), false),usageSession);

			return mapping.findForward("generatelist");
		}else {

			String path = getServlet().getServletContext().getInitParameter("mypath");
			//String path = "C:\\var\\";
			String filename = "";
          // System.out.println("path is  :"+path);
			if(studentlistform.getUserName() != null) {
				filename = studentlistform.getUserName() + ".txt";
				//System.out.println("filename is : "+filename);
			}else {
				filename =  "student_list.txt";
			}

			saveToServer(path,filename,request,studentlistform);
			File file = new File(path + filename);
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setDateHeader("Expires", 0);
			response.setHeader( "Pragma", "public" );
			response.setContentType("application/octet-stream");
			response.setContentLength((int)file.length());
			response.addHeader("Content-Disposition", "attachment;filename=" + filename );

			saveToClient(in, out);
				eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_STUDENTLIST_DOWNLOAD, toolManager.getCurrentPlacement().getContext(), false),usageSession);

			return null;
		}
	}

	public ActionForward afterGeneration (
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StudentListForm studentlistform = (StudentListForm)form;

		if(isCancelled(request)) {
			studentlistform.setIndexNrOfSelectedSite(null);
			uncheckStudentList(studentlistform);
			uncheckConfinements(studentlistform);
			studentlistform.setSelectView("l");
			return mapping.findForward("step1");
		}

		return null;
	}

	private void addConfinements2RequestObject(HttpServletRequest request) {

		request.setAttribute("confineStudentList", getStudentList());
		
		request.setAttribute("confineGenderList", getGenderList());
		request.setAttribute("confineHasEmailList", hasEmailAddressList());
		request.setAttribute("confineOnlineUserList", isOnlineUserList());
		request.setAttribute("confineCorrespondencLanguage",getConfineCorrespondenceLanguage());
		request.setAttribute("confineGroupedList", hasBeenGrouped());
		//Correspondenceanguage
	}

	private ArrayList getConfineCorrespondenceLanguage(){
		//Retrieve a  -- NOT using a look up table
		ArrayList corrlang = new ArrayList();
		corrlang.add(new org.apache.struts.util.LabelValueBean("Not specified", "notspecified"));
		corrlang.add(new org.apache.struts.util.LabelValueBean("ENGLISH", "E"));
		corrlang.add(new org.apache.struts.util.LabelValueBean("AFRIKAANS", "A"));

		return corrlang;
	}

	private ArrayList getGenderList(){
		//Construct value list for gender -- NOT using a look up table
		ArrayList gender = new ArrayList();
		gender.add(new org.apache.struts.util.LabelValueBean("Not specified", "notspecified"));
		gender.add(new org.apache.struts.util.LabelValueBean("Male", "M"));
		gender.add(new org.apache.struts.util.LabelValueBean("Female", "F"));

		return gender;
	}
	
	private ArrayList getStudentList(){
		ArrayList studentList=new ArrayList();
		//studentList.add(new org.apache.struts.util.LabelValueBean("Not specified", "notspecified"));
		studentList.add(new org.apache.struts.util.LabelValueBean("Current", "R"));//current-registered ; expired-completed
		studentList.add(new org.apache.struts.util.LabelValueBean("Expired", "C"));
		
		return studentList;
	}

	private ArrayList hasEmailAddressList(){
		//Construct value list for 'has email address' -- NOT using a look up table
		ArrayList hasemail = new ArrayList();
		hasemail.add(new org.apache.struts.util.LabelValueBean("Not specified", "notspecified"));
		hasemail.add(new org.apache.struts.util.LabelValueBean("Yes", "Yes"));
		//only allow for two options since not specified and no will return the same result.
		//hasemail.add(new org.apache.struts.util.LabelValueBean("No", "No"));

		return hasemail;
	}

	private ArrayList isOnlineUserList(){
		//Construct value list for 'has email address' -- NOT using a look up table
		ArrayList onlineuser = new ArrayList();
		onlineuser.add(new org.apache.struts.util.LabelValueBean("Not specified", "notspecified"));
		onlineuser.add(new org.apache.struts.util.LabelValueBean("Yes", "Yes"));
		onlineuser.add(new org.apache.struts.util.LabelValueBean("No", "No"));

		return onlineuser;
	}

	private ArrayList hasBeenGrouped(){

		ArrayList studentGrouped = new ArrayList();
		studentGrouped.add(new org.apache.struts.util.LabelValueBean("Not specified", "notspecified"));
		studentGrouped.add(new org.apache.struts.util.LabelValueBean("Yes", "Yes"));
		studentGrouped.add(new org.apache.struts.util.LabelValueBean("No", "No"));

		return studentGrouped;
	}

	public void saveToClient(DataInputStream in, ServletOutputStream out) throws Exception {

		int w = in.read();

		while (w != -1) {
			out.write(w);
			w = in.read();
		}

		out.flush();
		out.close();
		in.close();
	}


	public void saveToServer(String path, String filename, HttpServletRequest request, StudentListForm studentlistform) throws IOException {

		File fileobject = new File(path + filename);
		FileWriter fw = new FileWriter(fileobject);
		//sucking out the studentlist from the request object
		List studentlist = (List) request.getAttribute("studentDetails");
		String seperatewith = "";
		//depending on the users descision a comma OR tab seperated file will be created consisting of only selected fields
		if(studentlistform.getFormat().equals("commalist")) {
			seperatewith = ", ";
		}

		if(studentlistform.getFormat().equals("tablist")) {
			seperatewith = "\t";
		}

		//write the TOTAL students included in the list
		fw.write("Total number of students selected: " + request.getAttribute("studentCount"));
		fw.write(" "+((char)13)+((char)10));
		//do them checks to ensure the user will have only relevant data
		for(int i=0; i < studentlist.size(); i++) {

			if(studentlistform.isStudentNumber()) {
				fw.write(((StudentDetail)studentlist.get(i)).getStudentNumber() + seperatewith);
			}

			if(studentlistform.isFirstNames()) {
				fw.write(((StudentDetail)studentlist.get(i)).getFirstNames() + seperatewith);
			}

			if(studentlistform.isLastName()) {
				fw.write(((StudentDetail)studentlist.get(i)).getLastName() + seperatewith);
			}

			if(studentlistform.isInitials()) {
				fw.write(((StudentDetail)studentlist.get(i)).getInitials() + seperatewith);
			}

			if(studentlistform.isTitle()) {
				fw.write(((StudentDetail)studentlist.get(i)).getTitle() + seperatewith);
			}

			if(studentlistform.isGender()) {
				fw.write(((StudentDetail)studentlist.get(i)).getGender() + seperatewith);
			}

			if(studentlistform.isPostalAddress()) {
				fw.write(((StudentDetail)studentlist.get(i)).getPostalAddress() + seperatewith);
			}

			if(studentlistform.isPostalCode()) {
				fw.write(((StudentDetail)studentlist.get(i)).getPostalCode() + seperatewith);
			}

			if(studentlistform.isEmailAddress()) {
				fw.write(((StudentDetail)studentlist.get(i)).getEmailAddress() + seperatewith);
			}

			if(studentlistform.isHomePhoneNumber()) {
				fw.write(((StudentDetail)studentlist.get(i)).getHomePhoneNumber() + seperatewith);
			}

			if(studentlistform.isWorkPhoneNumber()) {
				fw.write(((StudentDetail)studentlist.get(i)).getWorkPhoneNumber() + seperatewith);
			}

			if(studentlistform.isCellularNumber()) {
				fw.write(((StudentDetail)studentlist.get(i)).getCellularNumber() + seperatewith);
			}

			if(studentlistform.isFaxNumber()) {
				fw.write(((StudentDetail)studentlist.get(i)).getFaxNumber() + seperatewith);
			}

			if(studentlistform.isCorrespondenceLanguage()) {
				fw.write(((StudentDetail)studentlist.get(i)).getCorrespondenceLanguage() + seperatewith);
			}

			if(studentlistform.isRegion()) {
				fw.write(((StudentDetail)studentlist.get(i)).getRegion() + seperatewith);
			}

			if(studentlistform.isRegistrationStatus()) {
				fw.write(((StudentDetail)studentlist.get(i)).getRegistrationStatus() + seperatewith);
			}

			if(studentlistform.isResRegion()) {
				fw.write(((StudentDetail)studentlist.get(i)).getResRegion() + seperatewith);
			}
			if(studentlistform.isModuleRegDate()) {
				fw.write(((StudentDetail)studentlist.get(i)).getRegistrationDate()+ seperatewith);
			}
			if(studentlistform.isTutGroupNr()) {
				fw.write(((StudentDetail)studentlist.get(i)).getGroupNumber() + seperatewith);
			}

			fw.write(" "+((char)13)+((char)10));
		}

		fw.flush();
		fw.close();
	}


	private void uncheckStudentList(StudentListForm studentlistform) {
		studentlistform.setStudentNumber(false);
		studentlistform.setTitle(false);
		studentlistform.setInitials(false);
		studentlistform.setFirstNames(false);
		studentlistform.setLastName(false);
		studentlistform.setPostalAddress(false);
		studentlistform.setPostalCode(false);
		studentlistform.setRegion(false);
		studentlistform.setResRegion(false);
		studentlistform.setHomePhoneNumber(false);
		studentlistform.setWorkPhoneNumber(false);
		studentlistform.setCellularNumber(false);
		studentlistform.setFaxNumber(false);
		studentlistform.setEmailAddress(false);
		studentlistform.setGender(false);
		studentlistform.setCorrespondenceLanguage(false);
		studentlistform.setHomeLanguage(false);
		studentlistform.setRegistrationStatus(false);
		studentlistform.setModuleRegDate(false);
		studentlistform.setTutGroupNr(false);
	}


	private void uncheckConfinements(StudentListForm studentlistform) {
		studentlistform.setConfineCountry("");
		studentlistform.setConfineDistrict("");
		studentlistform.setConfineHomeLanguage("");
		studentlistform.setConfineGender("");
		studentlistform.setConfineEmailAddress("");
		studentlistform.setConfineOnlineUser("");
		studentlistform.setConfineRace("");
		studentlistform.setConfineStudentsregion("");
		studentlistform.setConfineResidentialRegion("");
		studentlistform.setConfineProvince("");
		studentlistform.setConfineCorrespondenceLanguage("");
		studentlistform.setConfineGroupedStudent("");
	}


	private boolean nothingChecked(StudentListForm studentlistform) {
		boolean notChecked = false;

		if(studentlistform.isStudentNumber()== false && studentlistform.isTitle() == false &&
		   studentlistform.isInitials() == false &&  studentlistform.isFirstNames() == false &&
		   studentlistform.isLastName() == false && studentlistform.isPostalAddress() == false &&
		   studentlistform.isPostalCode() == false && studentlistform.isRegion() == false &&
		   studentlistform.isHomePhoneNumber() == false && studentlistform.isWorkPhoneNumber() == false &&
		   studentlistform.isCellularNumber() == false && studentlistform.isFaxNumber() == false &&
		   studentlistform.isEmailAddress() == false && studentlistform.isGender() == false &&
		   studentlistform.isCorrespondenceLanguage() == false && studentlistform.isRegistrationStatus() == false &&
		   studentlistform.isResRegion() == false  && studentlistform.isModuleRegDate() == false
		   && studentlistform.isTutGroupNr() == false) {
			notChecked = true;
		}else {
			notChecked = false;
		}

		return notChecked;
	}
}

