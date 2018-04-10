package za.ac.unisa.lms.tools.tpustudentplacement.actions;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.*;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

public class PlacementLogMaintenanceAction extends LookupDispatchAction{
	    
	
	    protected Map getKeyMethodMap() {
			// TODO Auto-generated method stub
			Map map = new HashMap();
			map.put("initial", "initial");	
			map.put("button.display", "displayLogs");
			map.put("button.back","prevPage");
			map.put("openCorrespondenceInput","openCorrespondenceInput");
			map.put("button.prevPage","prevPage");
			map.put("getLogEntryDetails","getLogEntryDetails");
			return map;
		}
		
		public ActionForward getLogEntryDetails(//invoked when Details is pressed
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			       StudentPlacementLog spl=new StudentPlacementLog();
			       Student student=new Student(); 
			       String stuNum=request.getParameter("stuNum");
			       student.getStudent(Integer.parseInt(stuNum));
			       String name=student.getName();
			       String action=request.getParameter("action");
			       spl.setAction(action);
				   String updatedOn=request.getParameter("updatedOn");
				   spl.setUpdatedOn(updatedOn);
    			   String updatedBy=request.getParameter("updatedBy");
    			   spl.setUpdatedBy(updatedBy);
    			   spl.setStuNum(stuNum);
    			   spl.setName(name);
    			   spl.setModule(request.getParameter("module"));
    			   spl.setSchoolDesc(request.getParameter("schoolDesc"));
    			   String afterImage=request.getParameter("afterImage");
    			   spl.disassemblePlacementImage(afterImage);
    			   Supervisor superv=new Supervisor();
    			   String supervisorName=superv.getSurpervisorName(spl.getSupervisorCode());
    			   spl.setSupervisorName(supervisorName);
    			   studentPlacementForm.setPlacementLogDetails(spl);
    			   studentPlacementForm.setCurrentPage("displayLogEntry");
			    return mapping.findForward("displayLogEntry");
		}
		public ActionForward prevPage(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			      StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			      String nextPage="";
			      if(studentPlacementForm.getCurrentPage().equals("listLogs")){
						if(studentPlacementForm.getLogButtonTracker().equals("FromPlacementLogBtn")){
							nextPage="listStudentPlacement";
						}else{
							nextPage="inputStudentPlacement";	
							studentPlacementForm.setStudentNr("");
							studentPlacementForm.setSchoolFilterCountry("");
							studentPlacementForm.setLogButtonTracker("");
							studentPlacementForm.setJustEnteredPlacementLogs("no");
						}
						studentPlacementForm.setLogButtonTracker("");
				 }else{
					     StudentPlacementLog spl=studentPlacementForm.getStudentPlacementLog();
					     List list=spl.getListOfSelectedLogs();
					     setLogList(studentPlacementForm,list);
					      nextPage="listLogs";
				 }
			     studentPlacementForm.setCurrentPage(nextPage);
			 	 return  mapping.findForward(studentPlacementForm.getCurrentPage());
		}
		public ActionForward initial(// it lists all the logs
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			       StudentPlacementLog spl=new StudentPlacementLog();
			       initializeStuPlacementLog(spl);
			       spl.setUpdatedBy(studentPlacementForm.getUserId().trim());
			       DateUtil dateUtil=new DateUtil();
			       //spl.setStartDate(dateUtil.getDateYYYYMMDD());
			       studentPlacementForm.setStudentPlacementLog(spl);
			       List list=null;//spl.getListOfSelectedLogs();
			       setLogList(studentPlacementForm,list);
			       studentPlacementForm.setJustEnteredPlacementLogs("yes");
			       studentPlacementForm.setLogButtonTracker("FromPlacementLogMaintenanceLink");
			    return mapping.findForward("listLogs");	
		}
		private void setLogList(StudentPlacementForm studentPlacementForm,List list) throws Exception{
			             studentPlacementForm.setCurrentPage("listLogs");
			             if(list==null){
				        	  list=new ArrayList();
				          }
			             studentPlacementForm.setListLogs(list);
		}
		public ActionForward displayLogs(// it lists all the logs, meeting a selected criteria
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			       ActionMessages messages = new ActionMessages();
			       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			       StudentPlacementLog spl = studentPlacementForm.getStudentPlacementLog();
			       String yearStr=spl.getYear();
			       String semester=spl.getSemester();
			       String networkCode=spl.getUpdatedBy();
			       String studentNum=spl.getStuNum();
			       studentPlacementForm.setJustEnteredPlacementLogs("yes");
			       PlacementUtilities  pu=new PlacementUtilities();
			       if((yearStr==null)||(yearStr.equals(""))){
			    	      messages.add(ActionMessages.GLOBAL_MESSAGE,
					                    new ActionMessage("message.generalmessage",
								           "Please enter Academic Year"));
			    	      addErrors(request,messages);
			    	      return mapping.findForward(studentPlacementForm.getCurrentPage());
			       }
			       if((semester==null)||(semester.equals(""))){
			    	      messages.add(ActionMessages.GLOBAL_MESSAGE,
					                    new ActionMessage("message.generalmessage",
								           "Please enter Semester"));
			    	      addErrors(request,messages);
			    	      return mapping.findForward(studentPlacementForm.getCurrentPage());
			       }
			       if(!pu.isInteger(yearStr)){
			    	      messages.add(ActionMessages.GLOBAL_MESSAGE,
					                    new ActionMessage("message.generalmessage",
								           "Academic year must be an integer"));
			    	      addErrors(request,messages);
			    	      return mapping.findForward(studentPlacementForm.getCurrentPage());
			       }
			       if(((networkCode==null)||(networkCode.equals("")))&&
			    		   ((studentNum==null)||(studentNum.equals("")))){
			    	      messages.add(ActionMessages.GLOBAL_MESSAGE,
					                    new ActionMessage("message.generalmessage",
								           "You must either  search on  Student Number or NetworkCode"));
			    	      addErrors(request,messages);
			    	      return mapping.findForward(studentPlacementForm.getCurrentPage());
			       }
			       if((studentNum!=null)&&(!studentNum.equals(""))){
			           if(!pu.isInteger(studentNum)){
			    	      messages.add(ActionMessages.GLOBAL_MESSAGE,
					                    new ActionMessage("message.generalmessage",
								           "Student Number must be an integer"));
			    	      addErrors(request,messages);
			    	      return mapping.findForward(studentPlacementForm.getCurrentPage());
			           }
			       }
			       DateUtil   dateUtil=new  DateUtil();
			       if((spl.getStartDate()!=null)&&(!spl.getStartDate().equals(""))){
			            if(!dateUtil.validateDateYYYYMMDD(spl.getStartDate())){
			           
			    	      messages.add(ActionMessages.GLOBAL_MESSAGE,
					                    new ActionMessage("message.generalmessage",
								           "Please Enter valid Begin Date"));
			    	      addErrors(request,messages);
			    	      return mapping.findForward(studentPlacementForm.getCurrentPage());
			           }
			       }else{
			    	   if((studentNum==null)||(studentNum.equals(""))){
			    	         messages.add(ActionMessages.GLOBAL_MESSAGE,
			                    new ActionMessage("message.generalmessage",
						           "You must enter a Begin Date"));
	    	                  addErrors(request,messages);
	    	                  return mapping.findForward(studentPlacementForm.getCurrentPage());
			    	   }
			       }
			       if((spl.getEndDate()!=null)&&(!spl.getEndDate().equals(""))){
			            if(!dateUtil.validateDateYYYYMMDD(spl.getEndDate())){
			           
			    	      messages.add(ActionMessages.GLOBAL_MESSAGE,
					                    new ActionMessage("message.generalmessage",
								           "Please Enter valid End Date"));
			    	      addErrors(request,messages);
			    	      return mapping.findForward(studentPlacementForm.getCurrentPage());
			           }
			       }
			       studentPlacementForm.setJustEnteredPlacementLogs("no");
			      studentPlacementForm.setStudentPlacementLog(spl);
		          List list=spl.getListOfSelectedLogs();
			      setLogList(studentPlacementForm,list);
			      return mapping.findForward("listLogs");	
		}
		 private void initializeStuPlacementLog(StudentPlacementLog spl){
			               DateUtil  dateutil=new DateUtil();
	    	   			   spl.setYear(""+dateutil.getYearInt());
			               spl.setSemester("0");
			               spl.setSortOrder("first");
			               spl.setAction("All");
       }

}
