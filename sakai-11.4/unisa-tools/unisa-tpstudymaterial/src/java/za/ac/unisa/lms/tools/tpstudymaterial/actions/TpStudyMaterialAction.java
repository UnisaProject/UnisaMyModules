package za.ac.unisa.lms.tools.tpstudymaterial.actions;


import java.io.DataInputStream;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.UserDirectoryService;
import org.springframework.jdbc.core.JdbcTemplate;
import Srcds01h.Abean.Srcds01sMntStudContactDetail;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.tpstudymaterial.service.SCMWebService;
import za.ac.unisa.lms.tools.tpstudymaterial.dao.StudyMaterialDetails;
import za.ac.unisa.lms.tools.tpstudymaterial.dao.TpStudyMaterialDAO;
import za.ac.unisa.lms.tools.tpstudymaterial.forms.TpStudyMaterialForm;
import za.ac.unisa.lms.tools.tpstudymaterial.util.MetaDataUtils;
import za.ac.unisa.lms.tools.tpstudymaterial.util.StudyMaterialLocation;




public class TpStudyMaterialAction extends LookupDispatchAction{
	 
	 SessionManager  sessionManager;
	 UserDirectoryService userDirectoryService;
	 private SCMWebService scmWebService;
	 Log log = LogFactory.getLog(TpStudyMaterialAction.class);
	 
	 public TpStudyMaterialAction () {
		 scmWebService = new SCMWebService();
	 }
		//the code for removing white space problem when you use ENTER button instead of mouse .
	 public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
			if (request.getParameter("action") == null) {
				return studentnrstep(mapping, form, request, response);
			}
			return super.execute(mapping, form, request, response);		
		}
		
	protected Map getKeyMethodMap() {
		Map map = new HashMap();		
		map.put("studentnrstep","studentnrstep");
		map.put("button.continue","getCourses");
		map.put("viewMaterial", "viewMaterial");
		map.put("getMaterial", "getMaterial");		
		map.put("button.close", "back");
		map.put("button.cancel", "cancel");
		map.put("button.goprevpage", "cancel");
		map.put("download","download");
		map.put("viewStudyMaterial", "viewStudyMaterial");
			
		return map;
	}
	
	public ActionForward studentnrstep( ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		TpStudyMaterialForm tpStudyMaterialForm = (TpStudyMaterialForm) form;
		tpStudyMaterialForm.setBackOption("");
		tpStudyMaterialForm.setStudentNr("");
		tpStudyMaterialForm.setSurname("");
		tpStudyMaterialForm.setFullNames("");
		tpStudyMaterialForm.setDateOfBirth("");
		tpStudyMaterialForm.setBirthDay("");
		tpStudyMaterialForm.setBirthMonth("");
		tpStudyMaterialForm.setBirthYear("");
		return mapping.findForward("studentNrStep");	
	}
	
	public ActionForward getCourses( ActionMapping mapping, ActionForm form, HttpServletRequest request,
			                 HttpServletResponse response)throws Exception{
		
		ActionMessages messages = new ActionMessages();
		TpStudyMaterialForm tpStudyMaterialForm = (TpStudyMaterialForm) form;
		String helpdeskEmail = ServerConfigurationService.getString("helpdeskEmail");
		TpStudyMaterialDAO dao=new TpStudyMaterialDAO();
		String regex = "^0*";
		String surnameinrecords = "";
	    String fullNamesinrecords = "";
	    String dateOfBirthinrecords = "";
		String studentNr=tpStudyMaterialForm.getStudentNr().replaceAll(regex, "");
		
	    if (studentNr.length()<=0) {
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Please enter the student number"));
			addErrors(request, messages);	
			return mapping.findForward("studentNrStep");
	    }
	    
		try {
    		int x = Integer.parseInt(studentNr);
	    } catch(NumberFormatException nFE) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "The student number you have entered is not valid." +
						"Please check and try again."));
			addErrors(request, messages);	
			return mapping.findForward("studentNrStep");
	    }
		
	    if (studentNr.length()<7) {
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "The student number you have entered is not valid." +
						"Please check and try again."));
			addErrors(request, messages);	
			return mapping.findForward("studentNrStep");
	    }
	    
	    boolean studentExist = dao.getStudentExist(studentNr);
	    if (studentExist == false){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "The student number you have entered is not valid." +
						"Please check and try again."));
			addErrors(request, messages);	
			log.debug(this+": "+tpStudyMaterialForm.getStudentNr()+" is not valid.");
			return mapping.findForward("studentNrStep");
	    }
	    Srcds01sMntStudContactDetail op = new Srcds01sMntStudContactDetail();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInSecurityWsUserNumber(9999);
		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("D");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInWsStudentNr(Integer.parseInt(studentNr));
		op.execute();
		String Errormsg = op.getOutCsfStringsString500();
		
		if ((Errormsg != null) && (!Errormsg.equals(""))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.coolgenerror", Errormsg));
			addErrors(request, messages);
			return mapping.findForward("studentNrStep");
		}
		surnameinrecords = ltrim(op.getOutWsStudentSurname());
		surnameinrecords = rtrim(surnameinrecords);
		fullNamesinrecords = ltrim(op.getOutWsStudentFirstNames());
		fullNamesinrecords = rtrim(fullNamesinrecords);
		DateFormat strDate = new SimpleDateFormat( "yyyy-MM-dd" );
		
		 try {
		    dateOfBirthinrecords = strDate.format(op.getOutWsStudentBirthDate().getTime());
		 } catch(NullPointerException e) {
			 
		   }
		tpStudyMaterialForm.setDateOfBirth(tpStudyMaterialForm.getBirthYear()+"-"+tpStudyMaterialForm.getBirthMonth()+"-"+tpStudyMaterialForm.getBirthDay());
		
		String enteredSurname = rtrim(ltrim(tpStudyMaterialForm.getSurname()));
		String enteredFullNames = rtrim(ltrim(tpStudyMaterialForm.getFullNames()));
		
		 if (tpStudyMaterialForm.getSurname().length()<=0){
		    	messages.add(ActionMessages.GLOBAL_MESSAGE,
				        new ActionMessage("message.generalmessage", "Please enter the surname"));
				addErrors(request, messages);	
				return mapping.findForward("studentNrStep");
		 }
		 
	     if (!enteredSurname.equalsIgnoreCase(surnameinrecords)) {
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "The surname you entered does not correspond with our records. Please check and try again." +
			        		" If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"+helpdeskEmail+"\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
			addErrors(request, messages);
			log.debug(this+": "+tpStudyMaterialForm.getStudentNr()+" surname");		
			return mapping.findForward("studentNrStep");			
	    }
	    
	    if(tpStudyMaterialForm.getFullNames().length()<=0){
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Please enter full names"));
			addErrors(request, messages);	
			return mapping.findForward("studentNrStep");
	    }
	    if (!enteredFullNames.equalsIgnoreCase(fullNamesinrecords)) {
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "The full names you entered do not correspond with our records. Please check and try again." +
			        		" If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"+helpdeskEmail+"\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
			addErrors(request, messages);
			log.debug(this+": "+tpStudyMaterialForm.getStudentNr()+" fullnames");	
			return mapping.findForward("studentNrStep");
	    }
	   
	    if(tpStudyMaterialForm.getBirthYear().length()<=0){
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Please enter year for date of birth"));
			addErrors(request, messages);	
			return mapping.findForward("studentNrStep");
	    }
	    if(tpStudyMaterialForm.getBirthMonth().length()<=0){
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Please enter month for date of birth"));
			addErrors(request, messages);	
			return mapping.findForward("studentNrStep");
	    }
	    if(tpStudyMaterialForm.getBirthDay().length()<=0){
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Please enter day for date of birth"));
			addErrors(request, messages);	
			return mapping.findForward("studentNrStep");
	    }
	    if (!tpStudyMaterialForm.getDateOfBirth().equalsIgnoreCase(dateOfBirthinrecords)) {
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "The date of birth you entered does not correspond with our records. Please check and try again." +
			        		" If you are convinced you have entered the correct information, please contact the <A HREF=\"mailto:"+helpdeskEmail+"\">myUnisaHelp@unisa.ac.za</A> to have your student records checked."));
			addErrors(request, messages);
			log.debug(this+": "+tpStudyMaterialForm.getStudentNr()+" dateofbirth");	
			return mapping.findForward("studentNrStep");
	    }
	    
		
	    String lastAcadYear = dao.getStudentLastAcadYear(studentNr);
	    tpStudyMaterialForm.setLastAcademicYear(lastAcadYear);
	     
	    if (lastAcadYear.length()==0) {
	    	
	    	messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Cannot determine current/last academic year for student "+ studentNr + "." +
			"Please check the student number and try again."));	 
	    	addErrors(request, messages);	
	    	log.debug(this+": "+tpStudyMaterialForm.getStudentNr()+" not having last academic year");
	    	return mapping.findForward("studentNrStep");
	    }
	    String tpstatus=dao.getTpRegistrationStatus(studentNr,lastAcadYear);	  
	  if(tpstatus.length()==0){
		  messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "You do not have any modules on your student record for this year"));
			addErrors(request, messages);
		  return mapping.findForward("studentNrStep");
	  }  
	  
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Calendar curDate1 = Calendar.getInstance();
		Date curDate = new Date();
		String listDate = formatter.format(curDate);
		tpStudyMaterialForm.setListdate(listDate);
	   ArrayList courselist = new ArrayList();
	   log.debug(this+": "+tpStudyMaterialForm.getStudentNr()+" get courselist to view TP study material");
	   courselist=dao.getCourseList(studentNr,lastAcadYear);
	   tpStudyMaterialForm.setCourseList(courselist);
	  // System.out.println("do we allow access to study material? "+tpStudyMaterialForm.isBlockedStatus());
	   String fileName = "";
	   

	   return mapping.findForward("courselist");	
	}
	public ActionForward viewMaterial (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		TpStudyMaterialForm tpStudyMaterialForm = (TpStudyMaterialForm) form;
		List<StudyMaterialDetails> studyMaterialList = new ArrayList<StudyMaterialDetails>();
		TpStudyMaterialDAO dao = new TpStudyMaterialDAO();
		Boolean courseMatch=false;
		
		String academicYear = request.getParameter("academicYear");
		String semister = request.getParameter("semister");
		String courseCode = request.getParameter("courseCode");
		
		 try {
			    studyMaterialList = scmWebService.getStudyMaterialList(courseCode, "20" + academicYear,semister);
	            
		 } catch(Exception e) {
	            	 ActionMessages messages = new ActionMessages();
	            	 messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message",e.getMessage()));
		             addErrors(request, messages);	             
	     }
		 
		 tpStudyMaterialForm.setStudyMaterialList(studyMaterialList);
		
		 return mapping.findForward("viewMaterial");	
		
		
		
		/*if (tpStudyMaterialForm.getStudentNr().length()==0) {
			return studentnrstep(mapping, form, request, response);
		}

	    Iterator i = tpStudyMaterialForm.getCourseList().iterator();
		
	    while (i.hasNext()) {
			  StudyMaterialDetails studyMaterialDetails=(StudyMaterialDetails) i.next();
			  String course=studyMaterialDetails.getCourseCode()+"-"+studyMaterialDetails.getAcademicYear()+"-"+studyMaterialDetails.getSemister();
			  String courselink=tpStudyMaterialForm.getCourseCode()+"-"+tpStudyMaterialForm.getAcademicYear()+"-"+tpStudyMaterialForm.getSemister();
			  if(course.equals(courselink)){
				  courseMatch=true;
				  break;
			  }
				  
		}
		if(courseMatch==true){
			//studyMaterialList = dao.getStudyMaterialList(tpStudyMaterialForm.getCourseCode(), "20" + tpStudyMaterialForm.getAcademicYear(),tpStudyMaterialForm.getSemister());
            try {
		    studyMaterialList = scmWebService.getStudyMaterialList(tpStudyMaterialForm.getCourseCode(), "20" + tpStudyMaterialForm.getAcademicYear(), tpStudyMaterialForm.getSemister());
            } catch(Exception e) {
            	 ActionMessages messages = new ActionMessages();

            	  messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message",e.getMessage()));
            
                       addErrors(request, messages);
             
            }
			
						for () {
				
			}
			tpStudyMaterialForm.setStudyMaterialList(studyMaterialList);
			return mapping.findForward("viewMaterial");	
		}else{
			return studentnrstep(mapping, form, request, response);
		}*/

	
	}
	
	public ActionForward download(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		TpStudyMaterialForm tpStudyMaterialForm = (TpStudyMaterialForm) form;
        String itemshortdesc = request.getParameter("itemshortdesc");
        String filename =  MetaDataUtils.getfileName(itemshortdesc);
        String type =  MetaDataUtils.getType(itemshortdesc);
        
        if (type != null) {
        	type = StudyMaterialLocation.getStudyMaterialTypeDirectoryName(type);
        }
        
        String modCode = request.getParameter("courseCode");
        String filePath = StudyMaterialLocation.getFilepath(modCode,type,filename);
        DataInputStream in = new DataInputStream(new FileInputStream(filePath));
        ServletOutputStream out = response.getOutputStream();
       // response.setContentType("APPLICATION/OCTET-STREAM");   
       // response.setContentType("application/pdf");
        //response.setHeader("Content-Disposition","filename=\"" + filename + "\""); 
        response.setHeader("Cache-Control", "private"); // HTTP 1.1.
        response.setHeader("Pragma", "cache");
        
        response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");   
        response.setContentType("APPLICATION/OCTET-STREAM");
	/*	response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "filename=\"mydoc.pdf\";");
		response.setContentLength((int) file.length());*/
        
        IOUtils.copy(in,out);
        in.close();
      //  out.close();

       return null;

  }
	
	public ActionForward viewStudyMaterial(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		TpStudyMaterialForm tpStudyMaterialForm = (TpStudyMaterialForm) form;
        String itemshortdesc = request.getParameter("itemshortdesc");
        String filename =  MetaDataUtils.getfileName(itemshortdesc);
        String type =  MetaDataUtils.getType(itemshortdesc);
        if (type != null) {
        	type = StudyMaterialLocation.getStudyMaterialTypeDirectoryName(type);
        }
        String modCode = request.getParameter("courseCode");
        String filePath = StudyMaterialLocation.getFilepath(modCode,type,filename);
        DataInputStream in = new DataInputStream(new FileInputStream(filePath));
        ServletOutputStream out = response.getOutputStream();
        //response.setContentType("APPLICATION/OCTET-STREAM");   
        response.setHeader("Cache-Control", "private"); // HTTP 1.1.
        response.setHeader("Pragma", "cache");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","filename=\"" + filename + "\"");   
        
	/*	response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "filename=\"mydoc.pdf\";");
		response.setContentLength((int) file.length());*/
        
        IOUtils.copy(in,out);
        in.close();
      //  out.close();

       return null;

  }
	
  
	
	
/*	public ActionForward getMaterial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		TpStudyMaterialForm tpStudyMaterialForm = (TpStudyMaterialForm) form;
		ArrayList studyMaterialList = new ArrayList();
		TpStudyMaterialDAO dao = new TpStudyMaterialDAO();
		if(tpStudyMaterialForm.getStudentNr().length()==0){
			return mapping.findForward("studentNrStep");	
		}
		System.out.println("link "+request.getParameter("path"));
		studyMaterialList = dao.getStudyMaterialList(tpStudyMaterialForm.getCourseCode(), "20" + tpStudyMaterialForm.getAcademicYear(),tpStudyMaterialForm.getSemister());
		tpStudyMaterialForm.setStudyMaterialList(studyMaterialList);
		return mapping.findForward("viewMaterial");	
	}*/
	
	
	public ActionForward back(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		 
		return studentnrstep(mapping, form, request, response);
	}
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		TpStudyMaterialForm tpStudyMaterialForm = (TpStudyMaterialForm) form;
		if(tpStudyMaterialForm.getBackOption().equals("TOVIEWCOURSE")){
			 return mapping.findForward("courselist");
		}else
			return studentnrstep(mapping, form, request, response);
	}
	  public static String ltrim(String source) {
	        return source.replaceAll("^\\s+", "");
	    }

	    /* remove trailing whitespace */
	    public static String rtrim(String source) {
	        return source.replaceAll("\\s+$", "");
	    }
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
}
