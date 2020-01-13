//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.auditview.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.tool.api.ToolManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.auditview.dao.AuditViewQueryDAO;
import za.ac.unisa.lms.tools.auditview.forms.AuditViewForm;

/**
 * MyEclipse Struts
 * Creation date: 01-06-2006
 *
 * XDoclet definition:
 * @struts:action validate="true"
 */
public class AuditViewAction extends DispatchAction {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	
	private static int LIMIT = 100;

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward input(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();

		AuditViewForm auditViewForm = (AuditViewForm) form;
		AuditViewQueryDAO dao = new AuditViewQueryDAO();


		if (auditViewForm.getUserId() == null || auditViewForm.getUserId().equals(""))
			{
		     messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("auditview.nousername"));
		     addErrors(request, messages);
		     return mapping.findForward("start");
			}

		if (auditViewForm.getOldUserId() == null) auditViewForm.setOldUserId("");

		if (!auditViewForm.getOldUserId().equals(auditViewForm.getUserId())) {
			auditViewForm.setPage(0);
			auditViewForm.setStart(0);
			auditViewForm.setPageButton(null);
			auditViewForm.setOldUserId(auditViewForm.getUserId());

		}
       System.out.println("Oldevent =" +auditViewForm.getOldevent()+ " and Eventset = "+auditViewForm.getEventset());
	//	if (auditViewForm.getOldevent()!=null)	{
			if (!auditViewForm.getOldevent().equals(auditViewForm.getEventset())){

			auditViewForm.setPage(0);
			auditViewForm.setStart(0);
			auditViewForm.setPageButton(null);
			auditViewForm.setOldevent(auditViewForm.getEventset());
		 //  }
		}

//	if (auditViewForm.getStart()==-1){
		if (auditViewForm.getPageButton() != null) {
			if (auditViewForm.getPageButton().equals("Next")) {
				int start =Math.max(0,auditViewForm.getStart())+100;
				List eventlist = auditViewForm.getEventList();

				if(eventlist.size()-start>99){
			 //auditViewForm.setPage(auditViewForm.getPage()+1);

		//	 System.out.println("People the initial lis is "+eventlist.size());
	   //	 System.out.println(" long and the sublist is "+auditViewForm.getSubeventList()+" long ");

			 //System.out.println(" Start from the next method starts off as : "+start );
			// System.out.println(" The size of entlist is : "+eventlist.size());
			 auditViewForm.setPage(auditViewForm.getPage()+1);
			 auditViewForm.setSubeventList(dao.pager(eventlist, start));
			 auditViewForm.setStart(start);
			 return mapping.findForward("display");
				}
				else
				{
				//auditViewForm.setPage(auditViewForm.getPage()+1);
				//System.out.println(" Start from the next method starts off as : "+start );
			    //System.out.println(" The size of entlist is : "+eventlist.size());
			    auditViewForm.setSubeventList(dao.pager(eventlist, start));
			    auditViewForm.setPage(auditViewForm.getPage()+1);
				auditViewForm.setStart(start);
				auditViewForm.setHasNext(false);
				return mapping.findForward("display");
				}

			}
		else if (auditViewForm.getPageButton().equals("Previous")) {
				//if(auditViewForm.getPage()>=0){auditViewForm.setPage(auditViewForm.getPage()-1);}

				 List eventlist = auditViewForm.getEventList();
				 int start =auditViewForm.getStart()-100;
				 if (start<100)
				 {start=0;
				 auditViewForm.setPage(1);
				 }
				// System.out.println(" Start from the next method starts off as : "+start );
				 auditViewForm.setPage(auditViewForm.getPage()-1);
				 //System.out.println(" the page is : "+auditViewForm.getPage());
 				 auditViewForm.setSubeventList(dao.pager(eventlist, start));
				 auditViewForm.setStart(start);
				 auditViewForm.setHasNext(true);

				 return mapping.findForward("display");
			}
		}
		if (auditViewForm.getPage() < 0) auditViewForm.setPage(0);

	//}

		try {
			if(auditViewForm.getEventset().equals(auditViewForm.getCurrent())|(auditViewForm.getEventset().equals("")))
				{
				auditViewForm.setEventList(dao.getCurrentEvents(auditViewForm.getUserId()));
				//System.out.println(" The start of this list should be "+(auditViewForm.getPage()*LIMIT));
				//auditViewForm.setSubeventList(dao.pager(auditViewForm.getEventList(), auditViewForm.getPage()*LIMIT, LIMIT));
				auditViewForm.setSubeventList(dao.pager(auditViewForm.getEventList(),  auditViewForm.getPage()*LIMIT));
				//System.out.println(" and the size of the array is "+auditViewForm.getSubeventList().size());
				//System.out.println(" The size of the initial array is "+auditViewForm.getEventList().size());
				//auditViewForm.setStart(LIMIT+1);
				//auditViewForm.setPage(auditViewForm.getPage()+1);
				 auditViewForm.setStart(1);
				auditViewForm.setHasNext(auditViewForm.getEventList().size() >= LIMIT);
		     if (auditViewForm.getEventList().size() == 0) {
			  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("auditview.norecords"));
		      }
			 }else if(auditViewForm.getEventset().equals(auditViewForm.getPrevYear())){
				 Calendar calendar = Calendar.getInstance();
				 Integer year = new Integer(calendar.get(Calendar.YEAR)-1);
				 String prevYear=year.toString();
				 auditViewForm.setEventList(dao.getAuditArchivedEvents_PrevYear(auditViewForm.getUserId(),prevYear));
			     auditViewForm.setSubeventList(dao.pager(auditViewForm.getEventList(),  auditViewForm.getPage()*LIMIT));
			     auditViewForm.setStart(1);
			     auditViewForm.setHasNext(auditViewForm.getEventList().size() >= LIMIT);
				 if (auditViewForm.getEventList().size() == 0) {
				 messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("auditview.norecords"));
		       }
			 }else if(auditViewForm.getEventset().equals(auditViewForm.getPrevYearLessOne())){
				 Calendar calendar = Calendar.getInstance();
					Integer year = new Integer(calendar.get(Calendar.YEAR)-2);
					String prevYear=year.toString();
				 auditViewForm.setEventList(dao.getAuditArchivedEvents_PrevYear(auditViewForm.getUserId(),prevYear));
			      auditViewForm.setSubeventList(dao.pager(auditViewForm.getEventList(),  auditViewForm.getPage()*LIMIT));
			      auditViewForm.setStart(1);
			      auditViewForm.setHasNext(auditViewForm.getEventList().size() >= LIMIT);
				     if (auditViewForm.getEventList().size() == 0) {
					  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("auditview.norecords"));
				     }
			 }
			else //if(auditViewForm.getEventset().equals(auditViewForm.getMostrecent())|(auditViewForm.getEventset().equals("")))
		      {auditViewForm.setEventList(dao.getAuditArchivedEvents(auditViewForm.getUserId()));
		      auditViewForm.setSubeventList(dao.pager(auditViewForm.getEventList(),  auditViewForm.getPage()*LIMIT));
		      auditViewForm.setStart(1);
		      auditViewForm.setHasNext(auditViewForm.getEventList().size() >= LIMIT);
			     if (auditViewForm.getEventList().size() == 0) {
				  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("auditview.norecords"));
			     }
		      }


/*
		try {
			if(auditViewForm.getEventset().equals(auditViewForm.getCurrent())|(auditViewForm.getEventset().equals("")))
				{auditViewForm.setEventList(dao.getCurrentEvents(auditViewForm.getUserId(), auditViewForm.getPage()*LIMIT, LIMIT));
				 auditViewForm.setHasNext(auditViewForm.getEventList().size() >= LIMIT);
		     if (auditViewForm.getEventList().size() == 0) {
			  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("auditview.norecords"));
		      }
			 }

		   else //if(auditViewForm.getEventset().equals(auditViewForm.getMostrecent())|(auditViewForm.getEventset().equals("")))
		      {auditViewForm.setEventList(dao.getAuditrecentEvents(auditViewForm.getUserId(), auditViewForm.getPage()*LIMIT, LIMIT));
		      auditViewForm.setSubeventList(dao.pager(auditViewForm.getEventList(),  auditViewForm.getPage()*LIMIT));
		      auditViewForm.setStart(1);
		      auditViewForm.setHasNext(auditViewForm.getEventList().size() >= LIMIT);
			     if (auditViewForm.getEventList().size() == 0) {
				  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("auditview.norecords"));
			     }
		      }

			 else if(auditViewForm.getEventset().equals(auditViewForm.getRecent()))
			     {  auditViewForm.setEventList(dao.getAuditrecentEvents(auditViewForm.getUserId(), auditViewForm.getPage()*LIMIT, LIMIT));
			     auditViewForm.setSubeventList(dao.pager(auditViewForm.getEventList(),  auditViewForm.getPage()*LIMIT));
			     auditViewForm.setStart(1);
			     auditViewForm.setHasNext(auditViewForm.getEventList().size() >= LIMIT);
				     if (auditViewForm.getEventList().size() == 0) {
					  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("auditview.norecords"));

			     }
			  }
			 else
			 {  auditViewForm.setEventList(dao.getAuditArchivedEvents(auditViewForm.getUserId(), auditViewForm.getPage()*LIMIT, LIMIT));
			    auditViewForm.setHasNext(auditViewForm.getEventList().size() >= LIMIT);
			    auditViewForm.setSubeventList(dao.pager(auditViewForm.getEventList(),  auditViewForm.getPage()*LIMIT));
			    auditViewForm.setStart(1);
			    if (auditViewForm.getEventList().size() == 0) {
				  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("auditview.norecords"));

		     }
		  }

*/
		} catch (Exception e) {
			throw e;
		}
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager=(ToolManager) ComponentManager.get(ToolManager.class);

		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_AUDIT_VIEW, toolManager.getCurrentPlacement().getContext(), false));

		//saveMessages(request, messages);

		return mapping.findForward("display");
	}



	public ActionForward start(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

			return mapping.findForward("start");
		}
}

