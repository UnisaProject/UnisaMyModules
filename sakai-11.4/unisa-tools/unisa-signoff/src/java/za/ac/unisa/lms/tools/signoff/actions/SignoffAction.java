package za.ac.unisa.lms.tools.signoff.actions;

import java.beans.PropertyVetoException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import Gistl01h.Abean.Gistl01sProxyForGistf01m;
import za.ac.unisa.lms.tools.signoff.dao.SignoffDAO;
import za.ac.unisa.lms.tools.signoff.dao.SignoffDetails;
import za.ac.unisa.lms.tools.signoff.dao.SignoffSakaiDAO;
import za.ac.unisa.lms.tools.signoff.forms.SignoffForm;
import za.ac.unisa.lms.tools.signoff.forms.StaffDeatils;
import za.ac.unisa.lms.tools.signoff.forms.StaffRecord;

public class SignoffAction extends LookupDispatchAction {
	
	//the code for removing white space problem when you use ENTER button instead of mouse .
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
          if (request.getParameter("act") == null){ 
        	  return authorisationPage(mapping, form, request, response);
          }
          return super.execute(mapping, form, request, response);           
    }
    private SessionManager sessionManager;
    private EmailService emailService;
    private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
	private Log log = LogFactory.getLog(this.getClass());
	protected Map getKeyMethodMap(){
		Map map = new HashMap();
		map.put("authorisationPage","authorisationPage");
		map.put("button.remove","removeSelectedPerson");
		map.put("button.removeconfirm","removeConfirm");
		map.put("button.back","goToBack");
		map.put("button.cancel","goToCancel");
		map.put("editStandIn","editStandIn");
		map.put("button.submit","goToSubmit");
		map.put("orderSignoffDisplay","orderSignoffDisplay");
		map.put("button.display","displayList");
		map.put("moveUp","moveUp");
		map.put("moveDown","moveDown");

		return map;
	}
	
	
	/**
	 * Method gotoBack
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward goToBack(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {

		 SignoffForm signoffForm = (SignoffForm)form;
		
		String gotoStepValidate = "";
		if ("1".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = back1(mapping,form,request, response);
		} else if ("2".equalsIgnoreCase(request.getParameter("atstep"))){
			//gotoStepValidate = clear2(mapping,form,request, response);
		}
		return mapping.findForward(gotoStepValidate);
	}
	/**
	 * Method gotoBack
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward goToCancel(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {

		 SignoffForm signoffForm = (SignoffForm)form;
		
		String gotoStepValidate = "";
		if ("1".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = cancel1(mapping,form,request, response);
		} else if ("2".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = cancel2(mapping,form,request, response);
		} else if ("3".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = cancel3(mapping,form,request, response);
		}
		return mapping.findForward(gotoStepValidate);
	}
	/**
	 * Method goToSubmit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward goToSubmit(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {

		 SignoffForm signoffForm = (SignoffForm)form;
		
		String gotoStepValidate = "";
		if ("2".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = addPersons(mapping,form,request, response);
		} else if ("3".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = submitOrder(mapping,form,request, response);
		}
		return mapping.findForward(gotoStepValidate);
	}
	
	public ActionForward authorisationPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    SignoffDAO dao = new SignoffDAO();
		    SignoffSakaiDAO daoSak = new SignoffSakaiDAO();
		    SignoffForm signoffForm = (SignoffForm)form;
		    HttpSession session = request.getSession(true);
		    Date d= new Date();
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			String sysdate=sdf.format(d);
			signoffForm.setSysdate(sysdate);
			
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			UsageSession usageSession = usageSessionService.getSession();

			
			//Session currentSession = SessionManager.getCurrentSession();
			String userEID = usageSession.getUserEid();
			String userId = usageSession.getUserId();
			
			// get user permission from dao
			try {
				signoffForm.setUserPermission(daoSak.getUserRights(userId,userEID));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.setAttribute("structureName",dao.getStructureName());
			int code ;
			int col_code;
			String[] codes = signoffForm.getCode().split("\\.");
			
			if(signoffForm.getCode().length()==0||signoffForm.getCode().equals("-1")){
				
				signoffForm.setCode("");
				return mapping.findForward("authstructure");
			}else{
				//code = Integer.parseInt(signoffForm.getCode());
				 signoffForm.setCod("");
				 signoffForm.setDean("");
				 signoffForm.setDyDean("");
				 signoffForm.setSchDirector("");
				code = Integer.parseInt(codes[0]);
				col_code = Integer.parseInt(codes[2]);
			 ArrayList list = dao.getPersonelNumbers(code);
			 if(list.size()!=0){
			 String cod =(String) list.get(0);
			 String dean=(String) list.get(1);
			 String dydean=(String) list.get(2);
			 signoffForm.setClgCode((String) list.get(3));
			 signoffForm.setCod(getfullName(cod));
			 signoffForm.setDean(getfullName(dean));
			 signoffForm.setDyDean(getfullName(dydean));
			 signoffForm.setCodStatus(getStatus(cod));
			 signoffForm.setDeanStatus(getStatus(dean));
			 signoffForm.setDyDeanStatus(getStatus(dydean));
		    }
			     ArrayList personsList = dao.getPersonelNumbers1(code,col_code);
			     
			 if(personsList.size()!=0){
				 String sod=(String) personsList.get(0);
				 signoffForm.setSchCode((String) personsList.get(1));
				 signoffForm.setSchDirector(getfullName(sod));
				 signoffForm.setSchDirectorStatus(getStatus(sod));
				 
			   }
			 
			int sch_code=Integer.parseInt(codes[1]);
			int clg_code=Integer.parseInt(codes[2]);
			 ArrayList listDydean = dao.getStandinDydean(clg_code);
			 ArrayList namesList = new ArrayList();
			 for(int i=0;i<listDydean.size();i++){
				 SignoffDetails signoffDetails = (SignoffDetails)listDydean.get(i);
				 String name = getfullName(signoffDetails.getStandinDydean());
				 String status = getStatus(signoffDetails.getStandinDydean());
				 signoffDetails.setName(name);
				 signoffDetails.setStatus(status);
				 signoffDetails.setStaffNumber(signoffDetails.getStandinDydean());
				 namesList.add(signoffDetails);
			 }
			 signoffForm.setStandinDydean(namesList);
			 
			 ArrayList listSch = dao.getStandinSOD(sch_code,clg_code);
			 ArrayList namesList1 = new ArrayList();
			 for(int i=0;i<listSch.size();i++){
				 SignoffDetails signoffDetails = (SignoffDetails)listSch.get(i);
				 String name = getfullName(signoffDetails.getStandinSch());
				 String status = getStatus(signoffDetails.getStandinSch());
				 signoffDetails.setName1(name);
				 signoffDetails.setStatus1(status);
				 signoffDetails.setStaffNumber1(signoffDetails.getStandinSch());
				 namesList1.add(signoffDetails);
			 }
			 signoffForm.setStandinSch(namesList1);
			 
			 ArrayList listCod = dao.getStandinCOD(code);
			 ArrayList namesList2 = new ArrayList();
			 for(int i=0;i<listCod.size();i++){
				 SignoffDetails signoffDetails = (SignoffDetails)listCod.get(i);
				 String name = getfullName(signoffDetails.getStandinCod());
				 String status = getStatus(signoffDetails.getStandinCod());
				 signoffDetails.setName2(name);
				 signoffDetails.setStatus2(status);
				 signoffDetails.setStaffNumber2(signoffDetails.getStandinCod());
				 namesList2.add(signoffDetails);
			 }
			 signoffForm.setStandinCod(namesList2);
			}
		
			return mapping.findForward("authstructure");
	}
	
	public ActionForward removeSelectedPerson(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    SignoffDAO dao = new SignoffDAO();
		    SignoffForm signoffForm = (SignoffForm)form;
		    HttpSession session = request.getSession(true);
		    int noPersons=0;
		    ArrayList removePersonList = new ArrayList();
		    ArrayList tmpDydeanList = signoffForm.getStandinDydean();
		    if(!signoffForm.getUserPermission().equals("MAINTAIN")){
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("errors.message","You are not authorized to remove person"));
						addErrors(request, messages); 
						
						return mapping.findForward("authstructure");
		    }
		    for (int i=0; i<tmpDydeanList.size(); i++) {
		    	 SignoffDetails signoffDetails = (SignoffDetails)tmpDydeanList.get(i);
		    	  boolean remove = signoffDetails.isRemove();
		    	  if(remove==true){
		    		  StaffDeatils staffDeatils = new StaffDeatils();
		    		  staffDeatils.setManagement_level("Stand-in Dean");
		    		  staffDeatils.setName(signoffDetails.getName());
		    		  staffDeatils.setSno(noPersons+1);
		    		  staffDeatils.setPersonnelNumber(signoffDetails.getStaffNumber());
		    		  staffDeatils.setRemove(remove);
		    		  removePersonList.add(staffDeatils);
		    		  noPersons++;
		    	  } 
		    }
			
		    ArrayList tmpSchList = signoffForm.getStandinSch();
		    for (int i=0; i<tmpSchList.size(); i++) {
		    	 SignoffDetails signoffDetails = (SignoffDetails)tmpSchList.get(i);
		    	  boolean remove = signoffDetails.isRemove1();
		    	  if(remove==true){
		    		  StaffDeatils staffDeatils = new StaffDeatils();
		    		  staffDeatils.setManagement_level("School Director/Stand-in School Director");
		    		  staffDeatils.setName(signoffDetails.getName1());
		    		  staffDeatils.setPersonnelNumber(signoffDetails.getStaffNumber1());
		    		  staffDeatils.setRemove(remove);
		    		  staffDeatils.setSno(noPersons+1);
		    		  removePersonList.add(staffDeatils);
		    		  noPersons++;
		    	  } 
		    }
		    ArrayList tmpCodList = signoffForm.getStandinCod();
		    for (int i=0; i<tmpCodList.size(); i++) {
		    	 SignoffDetails signoffDetails = (SignoffDetails)tmpCodList.get(i);
		    	  boolean remove = signoffDetails.isRemove2();
		    	  if(remove==true){
		    		  StaffDeatils staffDeatils = new StaffDeatils();
		    		  staffDeatils.setManagement_level("COD/Stand-in COD");
		    		  staffDeatils.setName(signoffDetails.getName2());
		    		  staffDeatils.setPersonnelNumber(signoffDetails.getStaffNumber2());
		    		  staffDeatils.setRemove(remove);
		    		  staffDeatils.setSno(noPersons+1);
		    		  removePersonList.add(staffDeatils);
		    		  noPersons++;
		    	  } 
		    }
		    if(noPersons==0){
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("signoff.nopersons"));
						addErrors(request, messages); 
						return mapping.findForward("authstructure");
		    }
		    signoffForm.setNoPersons(noPersons);
		    signoffForm.setRemovePersonList(removePersonList);
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message","Are you sure you want to remove the following person(s)?"));
					addErrors(request, messages); 
		    
			return mapping.findForward("removepersons");
	}
	
	
	public ActionForward removeConfirm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    SignoffDAO dao = new SignoffDAO();
		    SignoffForm signoffForm = (SignoffForm)form;
		    HttpSession session = request.getSession(true);
		    ArrayList removePersonList = new ArrayList();
		    removePersonList = signoffForm.getRemovePersonList();
		    String[] codes = signoffForm.getCode().split("\\.");
		    String dptcode = codes[0];
		    String schCode = codes[1];
		    String clgCode = codes[2];
		    int code = Integer.parseInt(dptcode);
			int sch_code=Integer.parseInt(schCode);
			int clg_code=Integer.parseInt(clgCode);
			int codeRemove=0;
		    for (int i=0; i<removePersonList.size(); i++) {
		    	StaffDeatils staffDeatils = (StaffDeatils)removePersonList.get(i);
		    	String level = staffDeatils.getManagement_level();
		    	staffDeatils.getName();
		    	staffDeatils.getPersonnelNumber();
		    	String type="";
		    	if(level.equals("Stand-in Dean")){
		    		type="COLLEGE";
		    		codeRemove=clg_code;
		    	}else if(level.equals("School Director/Stand-in School Director")){
		    		type="SCHOOL";
		    		codeRemove=sch_code;
		    	}else if(level.equals("COD/Stand-in COD")){
		    		type="DPT";
		    		codeRemove=code;
		    	}
		    	dao.deleteUsrdpt(code, sch_code, clg_code, type, staffDeatils.getPersonnelNumber());
		    }
		    ArrayList listDydean = dao.getStandinDydean(clg_code);
			 ArrayList namesList = new ArrayList();
			 for(int i=0;i<listDydean.size();i++){
				 SignoffDetails signoffDetails = (SignoffDetails)listDydean.get(i);
				 String name = getfullName(signoffDetails.getStandinDydean());
				 String status = getStatus(signoffDetails.getStandinDydean());
				 signoffDetails.setName(name);
				 signoffDetails.setStatus(status);
				 signoffDetails.setStaffNumber(signoffDetails.getStandinDydean());
				 namesList.add(signoffDetails);
			 }
			 signoffForm.setStandinDydean(namesList);
			 
			 ArrayList listSch = dao.getStandinSOD(sch_code,clg_code);
			 ArrayList namesList1 = new ArrayList();
			 for(int i=0;i<listSch.size();i++){
				 SignoffDetails signoffDetails = (SignoffDetails)listSch.get(i);
				 String name = getfullName(signoffDetails.getStandinSch());
				 String status = getStatus(signoffDetails.getStandinSch());
				 signoffDetails.setName1(name);
				 signoffDetails.setStatus1(status);
				 signoffDetails.setStaffNumber1(signoffDetails.getStandinSch());
				 namesList1.add(signoffDetails);
			 }
			 signoffForm.setStandinSch(namesList1);
			 
			 ArrayList listCod = dao.getStandinCOD(code);
			 ArrayList namesList2 = new ArrayList();
			 for(int i=0;i<listCod.size();i++){
				 SignoffDetails signoffDetails = (SignoffDetails)listCod.get(i);
				 String name = getfullName(signoffDetails.getStandinCod());
				 String status = getStatus(signoffDetails.getStandinCod());
				 signoffDetails.setName2(name);
				 signoffDetails.setStatus2(status);
				 signoffDetails.setStaffNumber2(signoffDetails.getStandinCod());
				 namesList2.add(signoffDetails);
			 }
			 signoffForm.setStandinCod(namesList2);
			 messages.add(ActionMessages.GLOBAL_MESSAGE,
			  new ActionMessage("message.generalmessage", signoffForm.getNoPersons()+" records successfully deleted"));
			  addErrors(request, messages);
			
			return mapping.findForward("authstructure");
	}
	
	/**
	 * Method editStandIn
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward editStandIn(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		 HttpSession session = request.getSession(true);
		SignoffForm signoffForm = (SignoffForm)form;
		SignoffDAO dao = new SignoffDAO();
		signoffForm.setEdit(request.getParameter("type"));
		ArrayList staffList = new ArrayList();
		if(signoffForm.getEdit().equals("a")){
			session.setAttribute("dptList",dao.getCod());
			signoffForm.setStructure("Select Department");
		}else if(signoffForm.getEdit().equals("b")){
			session.setAttribute("dptList",dao.getSch());
			signoffForm.setStructure("Select School");
		}else if(signoffForm.getEdit().equals("c")){
			session.setAttribute("dptList",dao.getDyDean());
			signoffForm.setStructure("Select College");
		}
		int n = signoffForm.getNoOfRecords();
    	for (int i=1; i <=n; i++) {
    		StaffRecord record = new StaffRecord();
    		record.setHead("Add Staff Member ("+i+" of "+n+")");
    		staffList.add(record);
	    }
    	signoffForm.setStaffList(staffList);
		session.setAttribute("noOfRecordsOptions",signoffForm.getNoOfRecordsOptions());
		return mapping.findForward("editstandin");
	}
	
	/**
	 * Method addPersons
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public String addPersons(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		 HttpSession session = request.getSession(true);
		 ActionMessages messages = new ActionMessages();
		SignoffForm signoffForm = (SignoffForm)form;
		SignoffDAO dao = new SignoffDAO();
		signoffForm.setEdit(request.getParameter("type"));
		ArrayList staffList = new ArrayList();
		staffList=signoffForm.getStaffList();
		String level="";
		String type="";
		if(signoffForm.getDptCode().equals("-1")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Please "+signoffForm.getStructure()));
					addErrors(request, messages);
					return "editstandin";
		}
		if(signoffForm.getNoOfRecords()==0){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", "Please choose Number of Persons to Add"));
					addErrors(request, messages);
					return "editstandin";
		}
		
		String dptCode="0";
		String schCode="0";
		String colCode="0";
		String commonCode="0";
		String [] codes;
		
		for(int i=0; i<staffList.size();i++){
			StaffRecord record = (StaffRecord)staffList.get(i);
			 if (record.getNetworkCode() != null) {
				 record.setNetworkCode(ltrim(record.getNetworkCode()));
				 record.setNetworkCode(rtrim(record.getNetworkCode()));
              }
			 if (record.getStaffNumber() != null) {
				 record.setStaffNumber(ltrim(record.getStaffNumber()));
				 record.setStaffNumber(rtrim(record.getStaffNumber()));
              }
			 if(record.getNetworkCode().equals("")||record.getNetworkCode()==null){
				 messages.add(ActionMessages.GLOBAL_MESSAGE,
						 new ActionMessage("errors.message", "Please enter networkcode"));
						 addErrors(request, messages);
						 return "editstandin";
			 }
			 if(record.getStaffNumber().equals("")||record.getStaffNumber()==null){
				 messages.add(ActionMessages.GLOBAL_MESSAGE,
						 new ActionMessage("errors.message", "Please enter Staff Number"));
						 addErrors(request, messages);
						 return "editstandin";
			 }
			String perNum= getPersonnelNumber(record.getNetworkCode().toUpperCase());
			if(!signoffForm.getErrorMessage().equals("")){
				 messages.add(ActionMessages.GLOBAL_MESSAGE,
						 new ActionMessage("errors.message", signoffForm.getErrorMessage()));
						 addErrors(request, messages);
						 return "editstandin";
			}
			if(perNum.equals("0")||perNum.equals("")||perNum==null){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
			    new ActionMessage("errors.message", "Staff information is not valid for "+record.getNetworkCode().toUpperCase()+" verify network code was correctly entered"));
				addErrors(request, messages);
			    return "editstandin";
			}
			if(!perNum.equals(record.getStaffNumber())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("errors.message", "Invalid Staff number entered for the network code, correct staff number is  "+perNum));
					 addErrors(request, messages);
					 return "editstandin";
				}
				String fullName=getfullName(perNum);
				if(getStatus(perNum).equals("Inactive")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							 new ActionMessage("errors.message", fullName+" does not have an active contract with Unisa.  Please verify and correct"));
							 addErrors(request, messages);
							 return "editstandin";
				}
			
				if(signoffForm.getEdit().equals("a")){
					type="DPT";
					level="COD/Stand-in COD";
				}else if(signoffForm.getEdit().equals("b")){
					type="SCHOOL";
					level="School Director/Stand-in School Director";
				}else if(signoffForm.getEdit().equals("c")){
					type="COLLEGE";
					level="Stand-in Dean";
				}
				
			
				if(signoffForm.getEdit().equals("a")){
			        codes = signoffForm.getDptCode().split("\\.");
			        int x= codes.length;
			      
			        	dptCode=codes[0];
						schCode=codes[1];
						colCode=codes[2];
						commonCode=dptCode;
				}else if(signoffForm.getEdit().equals("b")){
				   codes = signoffForm.getDptCode().split("\\.");
				   schCode=codes[0];
				   colCode=codes[1];
				   commonCode=schCode;
				}else if(signoffForm.getEdit().equals("c")){
					colCode=signoffForm.getDptCode();
					 commonCode=colCode;
				}
				
				boolean exist=dao.isStaffNumber(record.getNetworkCode().toUpperCase(), perNum, Integer.parseInt(dptCode), Integer.parseInt(schCode), Integer.parseInt(colCode), type );
				if(exist==true){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("errors.message", record.getNetworkCode().toUpperCase()+" already listed as "+level+". Cannot create duplicate. Please correct."));
					addErrors(request, messages);
					return "editstandin";
				}
				
			
				
				dao.setUserDpt(record.getNetworkCode().toUpperCase(), perNum, Integer.parseInt(dptCode), Integer.parseInt(schCode), Integer.parseInt(colCode), type);
				
			
			
		}
		
             String dpt = dao.getDepartmentName(type, Integer.parseInt(commonCode),Integer.parseInt(colCode));
		  messages.add(ActionMessages.GLOBAL_MESSAGE,
  			 new ActionMessage("message.generalmessage", level+" for "+dpt+" successfully saved"));
		        addErrors(request, messages);
		        if(signoffForm.getCode().length()==0||signoffForm.getCode().equals("-1")){
					
					signoffForm.setCode("");
					return "authstructure";
		        }else{
		        String[] codes1=signoffForm.getCode().split("\\.");
		        
		        int code = Integer.parseInt(codes1[0]);
				int clg_code=Integer.parseInt(codes1[2]);
				int sch_code=Integer.parseInt(codes1[1]);
		        ArrayList listDydean = dao.getStandinDydean(clg_code);
				 ArrayList namesList = new ArrayList();
				 for(int i=0;i<listDydean.size();i++){
					 SignoffDetails signoffDetails = (SignoffDetails)listDydean.get(i);
					 String name = getfullName(signoffDetails.getStandinDydean());
					 String status = getStatus(signoffDetails.getStandinDydean());
					 signoffDetails.setName(name);
					 signoffDetails.setStatus(status);
					 signoffDetails.setStaffNumber(signoffDetails.getStandinDydean());
					 namesList.add(signoffDetails);
				 }
				 signoffForm.setStandinDydean(namesList);
				 
				 ArrayList listSch = dao.getStandinSOD(sch_code, clg_code);
				 ArrayList namesList1 = new ArrayList();
				 for(int i=0;i<listSch.size();i++){
					 SignoffDetails signoffDetails = (SignoffDetails)listSch.get(i);
					 String name = getfullName(signoffDetails.getStandinSch());
					 String status = getStatus(signoffDetails.getStandinSch());
					 signoffDetails.setName1(name);
					 signoffDetails.setStatus1(status);
					 signoffDetails.setStaffNumber1(signoffDetails.getStandinSch());
					 namesList1.add(signoffDetails);
				 }
				 signoffForm.setStandinSch(namesList1);
				 
				 ArrayList listCod = dao.getStandinCOD(code);
				 ArrayList namesList2 = new ArrayList();
				 for(int i=0;i<listCod.size();i++){
					 SignoffDetails signoffDetails = (SignoffDetails)listCod.get(i);
					 String name = getfullName(signoffDetails.getStandinCod());
					 String status = getStatus(signoffDetails.getStandinCod());
					 signoffDetails.setName2(name);
					 signoffDetails.setStatus2(status);
					 signoffDetails.setStaffNumber2(signoffDetails.getStandinCod());
					 namesList2.add(signoffDetails);
				 }
				 signoffForm.setStandinCod(namesList2);
		        }
		        signoffForm.setNoOfRecords(0);
			    signoffForm.setDptCode("-1");
		    	return "authstructure";
 
	}
	
	/**
	 * Method orderSignoffDisplay
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward orderSignoffDisplay(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		 HttpSession session = request.getSession(true);
		SignoffForm signoffForm = (SignoffForm)form;
		SignoffDAO dao = new SignoffDAO();
		//String type="";
		//type=signoffForm.getLevelCode();

			session.setAttribute("orderDisplyList",dao.getOderDisplyDptList(signoffForm.getLevelCode()));

		
		
		return mapping.findForward("orderdisplay");
	}
	
	/**
	 * Method displayList
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward displayList(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		 HttpSession session = request.getSession(true);
		SignoffForm signoffForm = (SignoffForm)form;
		ActionMessages messages = new ActionMessages();
		SignoffDAO dao = new SignoffDAO();
		String type="";
		//type=signoffForm.getLevelCode();
		signoffForm.setDisplay("display");
		int dpt_code=Integer.parseInt(signoffForm.getDpt_list());
		session.setAttribute("orderDisplyList",dao.getOderDisplyDptList(signoffForm.getLevelCode()));
		if(signoffForm.getLevelCode().equals("clg")){
			type="COLLEGE";
			signoffForm.setOrderStructure("Order College Structure");
			signoffForm.setLevel_list("Deputy-dean List");
		}else if(signoffForm.getLevelCode().equals("sch")){
			type="SCHOOL";
			signoffForm.setOrderStructure("Order School Structure");
			signoffForm.setLevel_list("School Director List");
		}else if(signoffForm.getLevelCode().equals("dpt")){
			type="DPT";
			signoffForm.setOrderStructure("Order Department Structure");
			signoffForm.setLevel_list("COD List");
		}else if(signoffForm.getLevelCode().equals("-1")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
		    new ActionMessage("errors.message", "Please choose Authorization level"));
		    addErrors(request, messages);
		    return mapping.findForward("orderdisplay");
				}
		if(signoffForm.getDpt_list().equals("-1")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
		    new ActionMessage("errors.message", "Please choose Department"));
						        addErrors(request, messages);
		  return mapping.findForward("orderdisplay");
		}
        ArrayList order_list=new ArrayList();
        ArrayList list=new ArrayList();
        order_list=dao.getOrderList(dpt_code, type);
        for(int i=0;i<order_list.size();i++){
        	StaffRecord staffRecord = (StaffRecord)order_list.get(i);
        	staffRecord.getNovelUsrCode();
        	staffRecord.getPersonelNum();
        	String fullName=getfullName(staffRecord.getPersonelNum());
        	//staffRecord.setFullName(fullName);
        	//list.add(staffRecord);
        	list.add(new org.apache.struts.util.LabelValueBean(fullName,staffRecord.getPersonelNum()));
        }
        signoffForm.setFullNameList(list.size());
        session.setAttribute("fullNamesList",list);
        signoffForm.setOrder_list("");
		return mapping.findForward("orderdisplay");
	}
	
    /**
 	 * Method moveUp
 	 * 		
 	 * @param mapping
 	 * @param form
 	 * @param request
 	 * @param response
 	 * @return ActionForward
 	 */
 	public ActionForward moveUp(
 		ActionMapping mapping,
 		ActionForm form,
 		HttpServletRequest request,
 		HttpServletResponse response)throws Exception {
 		 HttpSession session = request.getSession(true);
 		SignoffForm signoffForm = (SignoffForm)form;
 		SignoffDAO dao = new SignoffDAO();
 		 String tmpMoveValue = signoffForm.getOrder_list();
 		  ArrayList list = (ArrayList) session.getAttribute("fullNamesList");
 		  String[] newOrderList = new String[list.size()];

 		 for(int i=0;i<list.size();i++){
 			 LabelValueBean lb = (LabelValueBean) list.get(i);
 			 LabelValueBean firstLabel= (LabelValueBean) list.get(0);
 		     if(i==0){
  			 if(firstLabel.getValue().equals(tmpMoveValue)){
  				 for(int j=0;j<list.size();j++){
  					 LabelValueBean lb1 = (LabelValueBean) list.get(j); 
  					 newOrderList[j] = lb1.getValue();
  				 }
  				 signoffForm.setOrderList(newOrderList);
  				return mapping.findForward("orderdisplay");
  			 }
  			 }
 			 if (lb.getValue().equals(tmpMoveValue)) {
 			    newOrderList[i] = newOrderList[i-1];
 			    newOrderList[i-1] = lb.getValue();
 				LabelValueBean tmp = (LabelValueBean) list.get(i-1);
 				list.set(i-1, lb); 
 				list.set(i,tmp);
 				 
 			 } else {
 				 newOrderList[i] = lb.getValue();
 			 }
 			
 		 }
 		 signoffForm.setOrderList(newOrderList);
 		 return mapping.findForward("orderdisplay");
 		 
 	} // end of moveUp
	
 	 /**
 	 * Method moveDown
 	 * 		
 	 * @param mapping
 	 * @param form
 	 * @param request
 	 * @param response
 	 * @return ActionForward
 	 */
 	public ActionForward moveDown(
 		ActionMapping mapping,
 		ActionForm form,
 		HttpServletRequest request,
 		HttpServletResponse response)throws Exception {
 		 HttpSession session = request.getSession(true);
 		SignoffForm signoffForm = (SignoffForm)form;
 		SignoffDAO dao = new SignoffDAO();
 		 String tmpMoveValue = signoffForm.getOrder_list();
 		  ArrayList list = (ArrayList) session.getAttribute("fullNamesList");
 		  String[] newOrderList = new String[list.size()];
 		  int count=0;
 
 		 for(int i=0;i<list.size();i++){
 			 LabelValueBean lb = (LabelValueBean) list.get(i);
 			 LabelValueBean lastLb = (LabelValueBean) list.get(list.size()-1);
 			 if(i==(list.size()-1)){
 	  		 if(lastLb.getValue().equals(tmpMoveValue)){
 	  				 for(int j=0;j<list.size();j++){
 	  					 LabelValueBean lb1 = (LabelValueBean) list.get(j); 
 	  					 newOrderList[j] = lb1.getValue();
 	  				 }
 	  				 signoffForm.setOrderList(newOrderList);
 	  				return mapping.findForward("orderdisplay");
 	  		  }
 	  		 }
 			 if (lb.getValue().equals(tmpMoveValue)) {
 				 if(count==0){
 				 newOrderList[i] = newOrderList[i+1];
 				 newOrderList[i+1] = lb.getValue();
 				 LabelValueBean tmp = (LabelValueBean) list.get(i+1);
 				 list.set(i+1, lb); 
 				 list.set(i,tmp);
 				 count++;
 				 }
 			 } else {
 				 newOrderList[i] = lb.getValue();
 			 }

 			
 		 }
 		 
 		 signoffForm.setOrderList(newOrderList);
 		 return mapping.findForward("orderdisplay");
 		 
 	} // end of moveDown
	public String submitOrder(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
			 HttpSession session = request.getSession(true);
			SignoffForm signoffForm = (SignoffForm)form;
			SignoffDAO dao = new SignoffDAO();
			ActionMessages messages = new ActionMessages();
			String type="";
			if(signoffForm.getLevelCode().equals("clg")){
				type="COLLEGE";
			}else if(signoffForm.getLevelCode().equals("sch")){
				type="SCHOOL";
			}else if(signoffForm.getLevelCode().equals("dpt")){
				type="DPT";
			}
			String [] newOrderList=signoffForm.getOrderList();
			for (int i=0; i<newOrderList.length; i++) {
			 dao.updateUserdpt(newOrderList[i],i,type,signoffForm.getDpt_list());
		    }
			
			  messages.add(ActionMessages.GLOBAL_MESSAGE,
			  			 new ActionMessage("message.generalmessage","Newly ordered list successfully saved"));
					        addErrors(request, messages);
			
			signoffForm.setLevelCode("-1");
			signoffForm.setDpt_list("-1");
		
			return "orderdisplay";
		}
	
	public String back1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    SignoffForm signoffForm = (SignoffForm)form;
		   
				return "authstructure";
		
	}
	public String cancel1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    SignoffForm signoffForm = (SignoffForm)form;
		    ArrayList tmpDydeanList = signoffForm.getStandinDydean();
		    for (int i=0; i<tmpDydeanList.size(); i++) {
		    	 SignoffDetails signoffDetails = (SignoffDetails)tmpDydeanList.get(i);
		    	 signoffDetails.setRemove(false);
		    }
		    ArrayList tmpschList = signoffForm.getStandinSch();
		    for (int i=0; i<tmpschList.size(); i++) {
		    	 SignoffDetails signoffDetails = (SignoffDetails)tmpschList.get(i);
		    	 signoffDetails.setRemove1(false);
		    }
		    ArrayList tmpcodList = signoffForm.getStandinCod();
		    for (int i=0; i<tmpcodList.size(); i++) {
		    	 SignoffDetails signoffDetails = (SignoffDetails)tmpcodList.get(i);
		    	 signoffDetails.setRemove2(false);
		    }
		   
				return "authstructure";
		
	}
	public String cancel2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    SignoffForm signoffForm = (SignoffForm)form;
		   
		    signoffForm.setNoOfRecords(0);
		    signoffForm.setDptCode("-1");
				return "authstructure";
		
	}
	public String cancel3(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    SignoffForm signoffForm = (SignoffForm)form;
		   
		    signoffForm.setLevelCode("-1");
		    signoffForm.setDisplay("");
				return "authstructure";
		
	}
	public String getfullName(String personelNumber) throws Exception{
		
		int perno = Integer.parseInt(personelNumber);
		SignoffDAO dao = new SignoffDAO();
		Gistl01sProxyForGistf01m op = new Gistl01sProxyForGistf01m();
		operListener opl = new operListener();
	    op.addExceptionListener(opl);
	    op.clear();

	    op.setInStaffPersno(perno);
	    op.execute();
		
	    if (opl.getException() != null) {
	      throw opl.getException();
	     }
	    if (op.getExitStateType() < 3) {
	   	   throw new Exception(op.getExitStateMsg());
	    }

        //retrieve the error message that proxy might return.
	    String errorMessage = op.getOutCsfStringsString500();
	    	    
	   // get the network code from proxy
       String tmpNovell = op.getOutStaffNovellUserId();
     // get personnel number from proxy
	   int tmpPersnr = op.getOutStaffPersno();
     // get person name
	  String name;
	  name = op.getOutStaffTitle() +" "+op.getOutStaffInitials()+" "+op.getOutStaffSurname();
	  
	  if (name.equals("")||name==null){
		  name = dao.getName(personelNumber);
	  }
	  
	  
     return name;

	  }
	
	
     public String getStatus(String personelNumber)throws Exception{
    		int perno = Integer.parseInt(personelNumber);
    		SignoffDAO dao = new SignoffDAO();
    		Gistl01sProxyForGistf01m op = new Gistl01sProxyForGistf01m();
    		operListener opl = new operListener();
    	    op.addExceptionListener(opl);
    	    op.clear();

    	    op.setInStaffPersno(perno);
    	    op.execute();
    		
    	    if (opl.getException() != null) {
    	      throw opl.getException();
    	     }
    	    if (op.getExitStateType() < 3) {
    	   	   throw new Exception(op.getExitStateMsg());
    	    }

            //retrieve the error message that proxy might return.
    	    String errorMessage = op.getOutCsfStringsString500();
    	    	    
    	    // get the network code from proxy
            String tmpNovell = op.getOutStaffNovellUserId();
           // get personnel number from proxy
    	    int tmpPersnr = op.getOutStaffPersno();
           // get person name
    	   String name;
    	   name = op.getOutStaffTitle() +" "+op.getOutStaffSurname()+" "+op.getOutStaffInitials();
    	   name = name.replace('\"','\'').trim();
    	   String status="";
    	
    if(name.equals("")||name==null){
    	 name = dao.getName(personelNumber);
    	 if(name.equals("")||name==null){
    		 status="User not found";
    	 }else{
    		 status = dao.getStatus(personelNumber);
    	 }
  
    }else{
    	   
		// check resignation date
		Calendar tmpResignDate1 = op.getOutStaffResignDate();
		// get current date
		GregorianCalendar calCurrent = new GregorianCalendar();
		int currentDay = calCurrent.get(Calendar.DAY_OF_MONTH);
		int currentMonth = calCurrent.get(Calendar.MONTH) + 1;
		int currentYear = calCurrent.get(Calendar.YEAR);

		String currentDayStr = "";
		String currentMonthStr = "";
		if (currentDay <= 9) {
			currentDayStr = "0"+currentDay;
		}else {
			currentDayStr = Integer.toString(currentDay);
		}
		if (currentMonth <= 9) {
			currentMonthStr = "0"+currentMonth;
		}else {
			currentMonthStr = Integer.toString(currentMonth);
		}
		String currentDate = currentDayStr+"-"+currentMonthStr+"-"+currentYear;

	     /*compare resignation date and current date.
	      if resignation date is bigger than current date or is null 
	      then user is still active at Unisa (result >= 0)
	      if resignation date is smaller than current date then user 
	      is not active. (result <=-1) */
		int result = 0;
		if (tmpResignDate1 != null) {
			result = tmpResignDate1.compareTo(calCurrent);
		}
		if (result <= -1) {
			status = "Inactive";
		} else {
			status = "Active";
		}
		return status;
    }
	return status;
		
   }
     
     public String getPersonnelNumber(String networkCode) throws Exception{
    	 /* **************************** */
    	 /*  First try and select staff 
    	 detail from table STAFF using this proxy */
    	 String errorMessage="";
         SignoffForm signoffForm = new SignoffForm();
    	 Gistl01sProxyForGistf01m op = new Gistl01sProxyForGistf01m();
    	 operListener opl = new operListener();
    	 op.addExceptionListener(opl);
    	 op.clear();
    	 		
    	 networkCode = networkCode.toUpperCase();
    	 		
    	 op.setInStaffNovellUserId(networkCode);
    	 op.execute();
    	 		
    	 if (opl.getException() != null) {
    	 	throw opl.getException();
    	 }
    	 if (op.getExitStateType() < 3) {
    	 	throw new Exception(op.getExitStateMsg());
    	 }

    	 errorMessage = op.getOutCsfStringsString500();
    	 signoffForm.setErrorMessage(errorMessage);
    	 int staffNr = op.getOutStaffPersno();
    	 String personnelNumber = Integer.toString(staffNr);
    	 String name = op.getOutStaffTitle() +" "+op.getOutStaffSurname()+" "+op.getOutStaffInitials();

    return personnelNumber;
     }
	
     
	// --------------------------------------------------------- Methods
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
	
	/*
	 * remove leading whitespace 
	 */
	 public static String ltrim(String source) {
	        return source.replaceAll("^\\s+", "");
	     }

	    /* remove trailing whitespace */
	 public static String rtrim(String source) {
	        return source.replaceAll("\\s+$", "");
	    }
}
