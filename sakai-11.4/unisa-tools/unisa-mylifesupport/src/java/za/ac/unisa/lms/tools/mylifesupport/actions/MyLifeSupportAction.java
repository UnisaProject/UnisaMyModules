package za.ac.unisa.lms.tools.mylifesupport.actions;

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

import za.ac.unisa.lms.tools.mylifesupport.forms.MyLifeSupportForm;
import za.ac.unisa.lms.tools.mylifesupport.DAO.MyLifeSupportQueryDAO;
import za.ac.unisa.lms.tools.mylifesupport.DAO.MyLifeSupportSakaiDAO;
/**
 * @author udcsweb
 *
 */
public class MyLifeSupportAction extends LookupDispatchAction {
	//code for removing the white space problem 
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("action") == null) return confirm(mapping, form, request, response);
	return super.execute(mapping, form, request, response);
	}
	
	
	

	protected Map getKeyMethodMap() {
	      Map map = new HashMap();
	      
	      map.put("input","input");
	      map.put("button.display", "confirm");
	      map.put("button.clearstno","clearInput");	      
	      return map;

}
	public ActionForward input(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			
		
		return mapping.findForward("myLifeSupportInput");
		
	}

	
	
	public ActionForward confirm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		
		ActionMessages messages = new ActionMessages(); // class that encapsulates messages.
		
		MyLifeSupportForm myLifeSupportForm = (MyLifeSupportForm) form;
		String sNumber =ltrim(myLifeSupportForm.getStudentNr());
		sNumber = rtrim(sNumber);
				
		//myLifeSupportForm.setStudentNr("");	
		myLifeSupportForm.setSurName("");
		myLifeSupportForm.setStudentID("");
		myLifeSupportForm.setBirthDate("");
		myLifeSupportForm.setFirstName("");
		myLifeSupportForm.setContactNr("");
		myLifeSupportForm.setMylifEmail("");
		myLifeSupportForm.setMylifePwd("");
		myLifeSupportForm.setStudentAddress("");
		myLifeSupportForm.setRegStatus("");
		myLifeSupportForm.setRegDate("");
		myLifeSupportForm.setJoinDate("");
		myLifeSupportForm.setStatusFlag("");
		myLifeSupportForm.setCellNumber("");
		myLifeSupportForm.setHomeNumber("");
		myLifeSupportForm.setPassportNr("");
		myLifeSupportForm.setMylifeStatus("");
		
		
	
		MyLifeSupportSakaiDAO dao = new MyLifeSupportSakaiDAO();		
		MyLifeSupportQueryDAO db2 = new MyLifeSupportQueryDAO();
		
    	try {
    		int x = Integer.parseInt(sNumber);
	    }catch(NumberFormatException nFE) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Invalid student number"));
			addErrors(request, messages);	
			return mapping.findForward("myLifeSupportInput");
	    }
	    
	    boolean studentExist = db2.getStudentExist(sNumber);
	    if (studentExist == false){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
			        new ActionMessage("message.generalmessage", "Invalid student number"));
			addErrors(request, messages);	
			return mapping.findForward("myLifeSupportInput");
	    }
	    
	    
	    db2.getStudentEmail(myLifeSupportForm);
	    db2.getPersonnelDetails(myLifeSupportForm);
	    db2.getPhonenumber(myLifeSupportForm);
		db2.getStudentAddr(myLifeSupportForm);
		db2.getStuJoinInfo(myLifeSupportForm);
        dao.getMyLifeJoinDetails(myLifeSupportForm);
		
		return mapping.findForward("displayforward");
		
	}
	
	public ActionForward clearInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	
		
		MyLifeSupportForm myLifeSupportForm = (MyLifeSupportForm) form;

		myLifeSupportForm.setStudentNr("");	
		
		return mapping.findForward("myLifeSupportInput");
	}
	/* remove leading whitespace */
	 public static String ltrim(String source) {
	        return source.replaceAll("^\\s+", "");
	    }

	    /* remove trailing whitespace */
	    public static String rtrim(String source) {
	        return source.replaceAll("\\s+$", "");
	    }
}
