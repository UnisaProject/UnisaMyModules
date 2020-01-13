//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.biodetails.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.ToolManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.biodetails.dao.ExamCentreDAO;
import za.ac.unisa.lms.tools.biodetails.forms.BioDetailsForm;
import za.ac.unisa.lms.tools.biodetails.forms.ExamCentreForm;
import za.ac.unisa.utils.WorkflowFile;

/**
 * MyEclipse Struts
 * Creation date: 09-14-2005
 *
 * XDoclet definition:
 * @struts:action path="/ContactDetailsAction" name="bioDetailsForm" parameter="action" validate="true"
 */
@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class ExamCentreAction extends DispatchAction {
	
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;

	public ActionForward display(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		ArrayList listexam = new ArrayList();
		ExamCentreDAO examCentreDao = new ExamCentreDAO();
		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;

		String typeText = "";
		try{
			List ecentres = examCentreDao.getExamCentres();
			Iterator i = ecentres.iterator();
			while (i.hasNext()){
				ExamCentreForm examcentreform = (ExamCentreForm) i.next();
				//String code = examcentreform.getCode();
				String Desc = examcentreform.getCentreName();
				String Type = examcentreform.getCentreType();
				if ("P".equalsIgnoreCase(Type)){
					typeText = "- Correctional Facility";
				}else{
					typeText = "";
				}
				listexam.add(new LabelValueBean(Desc+" "+typeText,Desc+"@"+Type));
			}
			request.setAttribute("listexam",listexam);
		}catch(Exception ex){
			throw new Exception("ExamCentreAction : Error executing ExamCentreAction / "+ ex,ex);
		}
		bioDetailsForm.setSelectedExamCentre(bioDetailsForm.getExamCentre());
		return mapping.findForward("display");
	}

	public ActionForward request(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{

			//go to first screen on cancel
			if (isCancelled(request)) {
				return (mapping.findForward("cancel"));
			}

			ActionMessages messages = new ActionMessages();
			BioDetailsForm biodetailsform = (BioDetailsForm) form;
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			UsageSession usageSession = usageSessionService.getSession();

			
			if ((biodetailsform.getSelectedExamCentre().equalsIgnoreCase("")) || (!wereChangesMade(biodetailsform))){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
			        		new ActionMessage("message.generalmessage", "You did not change your exam centre; we consider it to be correct."));
					addMessages(request, messages);
					return (mapping.findForward("cancel"));
			}
		    //Write workflow
		    writeWorkflow(biodetailsform);

    		eventTrackingService.post(
    				eventTrackingService.newEvent(EventTrackingTypes.EVENT_BIODETAILS_EXAMCENTRECHANGE, toolManager.getCurrentPlacement().getContext()+"/"+biodetailsform.getNumber(), false),usageSession);

		    //No errors occured
		    messages.add(ActionMessages.GLOBAL_MESSAGE,
	        new ActionMessage("message.generalmessage", "Exam centre change request has been successfully submitted. Details below will change once processed."));
				addMessages(request, messages);
			return (mapping.findForward("cancel"));
	}

	// Examination Centre Change Workflow

	private void writeWorkflow(BioDetailsForm biodetailsform) throws Exception{

		String date = (new java.text.SimpleDateFormat("EEEEE dd MMMMM yyyy hh:mm:ss").format(new java.util.Date()).toString());
		//String time = (new java.text.SimpleDateFormat("hhmmss").format(new java.util.Date()).toString());
		String fileName="";
		if (biodetailsform.getNumber().length()==7){
		 	fileName = "examcc_"+biodetailsform.getForeign()+"_0";
		}else{
			fileName = "examcc_"+biodetailsform.getForeign()+"_";
		}
		WorkflowFile file = new WorkflowFile(fileName + biodetailsform.getNumber());
		file.add(" The following request was received through myUnisa:\r\n");
		file.add(" Type of request: Examination centre change \r\n");
		file.add(" Request received on " + date + "\r\n\r\n");
		file.add(" Student Number: "+biodetailsform.getNumber()+"\r\n");
		file.add(" Email address:  " + biodetailsform.getEmail() + "\r\n");
		file.add(" Degree:  "+biodetailsform.getQual().getQualCode()+"  " + biodetailsform.getQual().getQualDesc()+"\r\n");
		file.add(" Country Code:  "+biodetailsform.getCountryCode()+"  " + biodetailsform.getForeign()+ "\r\n\r\n");
		file.add(" Details:\r\n");
		file.add(" ----------------------------------------\r\n");
		file.add(" Old examination centre "+biodetailsform.getExamCentre()+"\r\n");
		
		String exCentreCode = "";
    	String exCentreType = "";
    	
		log.debug("ExamCentreAction - writeWorkflow: Student: "+biodetailsform.getNumber() + " Exam Centre - CentreSelected: "+ biodetailsform.getSelectedExamCentre());
        if(biodetailsform.getSelectedExamCentre()!=null){
        	int posEx = 0;
	        String exCentreSelected = biodetailsform.getSelectedExamCentre().trim();
	        posEx = exCentreSelected.indexOf("@");
	        exCentreCode = exCentreSelected.substring(0,posEx);
	        exCentreType = exCentreSelected.substring(posEx+1,exCentreSelected.length());
         }
        
		file.add(" New examination centre "+exCentreCode+"\r\n");


		file.close();

	}

	private boolean wereChangesMade(BioDetailsForm biodetailsform){

		boolean changed = false;
		String exCentreCode = "";
    	String exCentreType = "";
    	
		log.debug("ExamCentreAction - wereChangesMade: Student: "+biodetailsform.getNumber() + " Exam Centre - CentreSelected: "+ biodetailsform.getSelectedExamCentre());
        if(biodetailsform.getSelectedExamCentre()!=null){
        	int posEx = 0;
	        String exCentreSelected = biodetailsform.getSelectedExamCentre().trim();
	        posEx = exCentreSelected.indexOf("@");
	        exCentreCode = exCentreSelected.substring(0,posEx);
	        exCentreType = exCentreSelected.substring(posEx+1,exCentreSelected.length());
        }
		if (! biodetailsform.getExamCentre().equalsIgnoreCase(exCentreCode)){
			return true;
		}
		return changed;

	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("ExamCentreAction: unspecified method call -no value for parameter in request");

		return mapping.findForward("cancel");

	}
}