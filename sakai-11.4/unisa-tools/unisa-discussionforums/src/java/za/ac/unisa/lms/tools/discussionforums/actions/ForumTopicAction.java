//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.discussionforums.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.discussionforums.dao.Forum;
import za.ac.unisa.lms.tools.discussionforums.dao.ForumDao;
import za.ac.unisa.lms.tools.discussionforums.dao.ForumTopicDetails;
import za.ac.unisa.lms.tools.discussionforums.dao.TopicDao;
import za.ac.unisa.lms.tools.discussionforums.forms.ForumTopicsForm;
import za.ac.unisa.lms.tools.discussionforums.service.ForumDaoService;
import za.ac.unisa.lms.tools.discussionforums.service.TopicDaoService;
import za.ac.unisa.security.SecurityServices;
import za.ac.unisa.lms.tools.discussionforums.dao.ForumMessage; //Added by mphahsm
import za.ac.unisa.lms.tools.discussionforums.dao.MessageDao; //Added by mphahsm
import za.ac.unisa.lms.tools.discussionforums.service.MessageDaoService; //Added by mphahsm
/**
 * MyEclipse Struts
 * Creation date: 06-21-2006
 *
 * XDoclet definition:
 * @struts:action parameter="action"
 */
public class ForumTopicAction extends LookupDispatchAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		if (request.getParameter("act") == null){
			return showTopics(mapping, form, request, response);
		}
		return super.execute(mapping, form, request, response);
	}
	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
    private ToolManager toolManager;
    private SessionManager sessionManager;
	protected static Logger logger = Logger.getLogger(ForumTopicAction.class);
	private int start;//start of the page to be viewed;
	protected Map getKeyMethodMap() {
	
		Map map = new HashMap();
		map.put("showTopics", "showTopics");
		map.put("returnToForum", "returnToForum");
		map.put("createTopic", "createTopic");	
		map.put("Save", "saveTopic");
		//map.put("button.back", "returnToForum");
		map.put("button.back", "showTopics"); //Added by mphahsm on 11/2015 to fix the redirection issue
		map.put("confirmTopicDelete","confirmTopicDelete");
		map.put("editTopic","editTopic");
		map.put("button.delete","deleteTopic");
		map.put("button.edit","editTopicName");
		map.put("button.backToForums", "returnToForum");
		map.put("button.first", "firstTopics");
		map.put("button.previous", "previousTopics");
		map.put("button.next", "nextTopics");
		map.put("button.last", "lastTopics");
		return map;
	}

	public ActionForward showTopics(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
		    TopicDao topicDAO = TopicDaoService.getTopicDao();
			ForumDao forumDao = ForumDaoService.getForumDao();//Called Service Locator
			ForumTopicsForm forumTopicsForm = (ForumTopicsForm) form;
			SecurityServices ss = new SecurityServices();
			//Forum forum = new Forum();
			String siteReference = "";
			Integer numberOfRecords;
			siteReference = ss.getSiteReference();
			forumTopicsForm.setTopicAddable(ss.checkPermission(siteReference, "discuss.addtopic"));
			forumTopicsForm.setTopicDeletable(ss.checkPermission(siteReference, "discuss.deletetopic.any"));
			forumTopicsForm.getForumTopicDetails().setForumId(forumTopicsForm.getForumId());
			Forum forum  = forumDao.getForumContent(forumTopicsForm.getForumId());
			forumTopicsForm.setTopicForumName(forum.getForumName());
			forumTopicsForm.setTopicForumDesciption(forum.getForumDescription());
			if (request.getParameter("records") == null){
				numberOfRecords = new Integer("10");
			} else {
				  numberOfRecords = Integer.parseInt(forumTopicsForm.getRecords());
				//numberOfRecords = new Integer(request.getParameter("records"));
			}
			forumTopicsForm.setRecords(numberOfRecords.toString());
			if (forumTopicsForm.getSortBy() == null && forumTopicsForm.getSortOrder() == null){
				forumTopicsForm.setSortBy("Modification_Date");
				forumTopicsForm.setSortOrder("Asc");
			}
			if (forumTopicsForm.getSortIcon() == null){
				forumTopicsForm.setSortIcon("0");
			} else if (forumTopicsForm.getSortIcon().equalsIgnoreCase("1")){
				forumTopicsForm.setSortOrder("Asc");
			} else if (forumTopicsForm.getSortIcon().equalsIgnoreCase("2")){
				forumTopicsForm.setSortOrder("Desc");
			} else if (forumTopicsForm.getSortIcon().equalsIgnoreCase("3")){
				forumTopicsForm.setSortOrder("Asc");
			} else if (forumTopicsForm.getSortIcon().equalsIgnoreCase("4")){
				forumTopicsForm.setSortOrder("Desc");
			}

			List forumTopics = topicDAO.getTopics(forumTopicsForm.getForumTopicDetails().getForumId(),forumTopicsForm.getSortBy(),forumTopicsForm.getSortOrder());			
			if(forumTopics!=null){
			if (forumTopics.size() < 1 && forum.getForumName().equalsIgnoreCase("General Subject Related Discussions")){
				ForumTopicDetails forumTopicDetails = new ForumTopicDetails();
				forumTopicDetails.setSiteId((String) request.getSession().getAttribute("siteId"));
				forumTopicDetails.setForumId(forumTopicsForm.getForumId());
				forumTopicDetails.setTopicAuthor("admin");
				forumTopicDetails.setTopicTitle("General Discussions");
				forumTopicDetails.setViewCounter(new Integer(1));
				forumTopicDetails.setHideStatus("N");
				forumTopicDetails.setTopicMessage("Welcome to the General Discussions topic. In this topic you can correspond with your fellow class members on any issues regarding this course. Use the Your Message box below to add your message to the list. If you want to start a totally new topic of discussion, use the Add New Topic link which you will find in the Topics List.");
				topicDAO.insertTopic(forumTopicDetails);
				forumTopics = topicDAO.getTopics(forumTopicsForm.getForumTopicDetails().getForumId(),forumTopicsForm.getSortBy(),forumTopicsForm.getSortOrder());
			}
			}else{
				return mapping.findForward("showtopicforward");
			}
			forumTopicsForm.setAllTopics(forumTopics);
			forumTopicsForm.setTopics(pager(0,numberOfRecords,forumTopics));
			forumTopicsForm.setNumberOfItems(forumTopics.size());
			forumTopicsForm.setStart(1);
			forumTopicsForm.setEnd(Math.min(numberOfRecords,forumTopicsForm.getNumberOfItems()));
			if (forumTopicsForm.getNumberOfItems() < 1){
				forumTopicsForm.setStart(0);
				forumTopicsForm.setEnd(0);
			}
			
            request.setAttribute("forumTopicsForm",forumTopicsForm);
			return mapping.findForward("showtopicforward");
	}

	public ActionForward firstTopics(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
			ForumTopicsForm forumTopicsForm = (ForumTopicsForm)form;
			
			int numberOfRecords = new Integer(forumTopicsForm.getRecords()).intValue();
			List forumTopics = forumTopicsForm.getAllTopics();
			
			forumTopicsForm.setStart(1);
			forumTopicsForm.setTopics(pager(0,numberOfRecords,forumTopics));
			forumTopicsForm.setEnd(Math.min(numberOfRecords, forumTopicsForm.getNumberOfItems()));
			if (forumTopicsForm.getNumberOfItems() < 1){
				forumTopicsForm.setStart(0);
			}
			
			return mapping.findForward("showtopicforward");
	}

	public ActionForward previousTopics(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
			ForumTopicsForm forumTopicsForm = (ForumTopicsForm)form;
		
			int start = forumTopicsForm.getStart();
			int numberOfRecords = new Integer(forumTopicsForm.getRecords()).intValue();
			List forumTopics = forumTopicsForm.getAllTopics();
			if ((start - numberOfRecords) <= 0){
				forumTopicsForm.setStart(1);
				forumTopicsForm.setTopics(pager(0,numberOfRecords,forumTopics));
				forumTopicsForm.setEnd(Math.min(numberOfRecords, forumTopicsForm.getNumberOfItems()));
			} else if ((start + numberOfRecords) < forumTopicsForm.getNumberOfItems()){
				int end = forumTopicsForm.getStart() - 1;
				start = forumTopicsForm.getStart() - forumTopicsForm.getNumberOfItems();
				forumTopicsForm.setTopics(pager(start,numberOfRecords,forumTopics));
				forumTopicsForm.setStart(start);
				forumTopicsForm.setEnd(Math.min(end , forumTopicsForm.getNumberOfItems()));
			} else {
				int end = forumTopicsForm.getStart() - 1;
				start = forumTopicsForm.getStart()- numberOfRecords;
				forumTopicsForm.setTopics(pager(start,numberOfRecords,forumTopics));
				forumTopicsForm.setStart(start);
				forumTopicsForm.setEnd(Math.min(end , forumTopicsForm.getNumberOfItems()));
            }
			
			if (forumTopicsForm.getNumberOfItems() < 1){
				forumTopicsForm.setStart(0);
			}
	
			return mapping.findForward("showtopicforward");
	}	
	
	public ActionForward nextTopics(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
			ForumTopicsForm forumTopicsForm = (ForumTopicsForm)form;
			
			int start = forumTopicsForm.getStart();
			int numberOfRecords = new Integer(forumTopicsForm.getRecords()).intValue();
			List forumTopics = forumTopicsForm.getAllTopics();
			
			if (start+numberOfRecords > forumTopicsForm.getNumberOfItems()){
				forumTopicsForm.setStart(start);
				forumTopicsForm.setTopics(pager(start,numberOfRecords,forumTopics));
				forumTopicsForm.setEnd(forumTopicsForm.getNumberOfItems());
			} else if ((start+numberOfRecords - 1) <= forumTopicsForm.getNumberOfItems()){  
				start = forumTopicsForm.getEnd()+1;
				int end = (start + numberOfRecords - 1);
				forumTopicsForm.setStart(start);
				forumTopicsForm.setTopics(pager(start, numberOfRecords,forumTopics));
				end=Math.min(end,forumTopicsForm.getNumberOfItems());
				forumTopicsForm.setEnd(end);
			} else {
				start = forumTopicsForm.getEnd();
				int end = start + numberOfRecords - 1;
				forumTopicsForm.setStart(start);
				forumTopicsForm.setTopics(pager(start, numberOfRecords,forumTopics));
				end = Math.min(end,forumTopicsForm.getNumberOfItems());
				forumTopicsForm.setEnd(end);
	    	 }
			
			if ((forumTopicsForm.getStart() + numberOfRecords - 1) == forumTopicsForm.getNumberOfItems()){
				start=forumTopicsForm.getStart();
				int end = forumTopicsForm.getNumberOfItems();
				end=Math.min(end,forumTopicsForm.getNumberOfItems());
				forumTopicsForm.setEnd(end);
				forumTopicsForm.setTopics(forumTopics.subList(start-1, end));
			}
			
			if (forumTopicsForm.getNumberOfItems() < 1){
				forumTopicsForm.setStart(0);
			}
			
			return mapping.findForward("showtopicforward");
	}	
	
	public ActionForward lastTopics(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
			ForumTopicsForm forumTopicsForm = (ForumTopicsForm)form;
			
			int start = forumTopicsForm.getStart();
			int numberOfRecords = new Integer(forumTopicsForm.getRecords()).intValue();
			List forumTopics = forumTopicsForm.getAllTopics();
			
			if (forumTopicsForm.getNumberOfItems() == 0){
				return mapping.findForward("showtopicforward");
			}
			
			if ((forumTopicsForm.getStart() + numberOfRecords-1) == forumTopicsForm.getNumberOfItems()){
				start = forumTopicsForm.getStart();
				int end = forumTopicsForm.getNumberOfItems();
				end = Math.min(end , forumTopicsForm.getNumberOfItems());
				forumTopicsForm.setEnd(end);
				forumTopicsForm.setTopics(forumTopics.subList(start, end));
			} else {
				start = (1 + forumTopicsForm.getNumberOfItems()) - (forumTopicsForm.getNumberOfItems() % numberOfRecords);
				forumTopicsForm.setTopics(pager(start, numberOfRecords,forumTopics));
				forumTopicsForm.setStart(start);
				forumTopicsForm.setEnd(forumTopicsForm.getNumberOfItems());
			}
			
			if ((forumTopicsForm.getStart()-1) == forumTopicsForm.getNumberOfItems()){
				start = forumTopicsForm.getNumberOfItems() - numberOfRecords + 1;
				forumTopicsForm.setTopics(forumTopics.subList(start, forumTopicsForm.getNumberOfItems()));
				forumTopicsForm.setStart(forumTopicsForm.getNumberOfItems() - numberOfRecords + 1);
	        }
			
			return mapping.findForward("showtopicforward");
	}	
		
	public ActionForward createTopic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse reponse)
	{
			ForumTopicsForm forumTopicsForm = (ForumTopicsForm)form;
			forumTopicsForm.getForumTopicDetails().setTopicTitle("");
			forumTopicsForm.getForumTopicDetails().setTopicMessage("");
			
			//return mapping.findForward("createtopicforward"); //Commented out by mphahsm on 2016/10
						
			//Added by mphahsm on 2016/10 to check also external URLs if user executing it has the permission to perform action						
			ActionMessages messages = new ActionMessages();
			if (forumTopicsForm.isTopicAddable()) {
				return mapping.findForward("createtopicforward");
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.message","You are forbidden to perform this action. Please contact your system administrator."));
				addErrors(request, messages);
				return mapping.findForward("showtopicforward");
			} //End of mphahsm add
	}

	public ActionForward saveTopic(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
			ActionMessages messages = new ActionMessages();
			ForumTopicsForm forumTopicForm = (ForumTopicsForm)form;
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
			//UsageSession usageSession = UsageSessionService.startSession((String)request.getSession().getAttribute("userID"),request.getRemoteAddr(),request.getHeader("user-agent"));
			UsageSession usageSession = usageSessionService.getSession();
			boolean status=false;
			TopicDao topicDAO = TopicDaoService.getTopicDao();
			forumTopicForm.getForumTopicDetails().setTopicTitle(ltrim(forumTopicForm.getForumTopicDetails().getTopicTitle()));
			forumTopicForm.getForumTopicDetails().setTopicTitle(rtrim(forumTopicForm.getForumTopicDetails().getTopicTitle()));
			forumTopicForm.getForumTopicDetails().setTopicTitle(forumTopicForm.getForumTopicDetails().getTopicTitle().trim());
			if(forumTopicForm.getForumTopicDetails().getTopicTitle().length() < 1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Topic Title field cannot be empty.Please enter a title or click Cancel to return to the Topics List View."));
				addErrors(request,messages);
				return mapping.findForward("createtopicforward");
			} else if (forumTopicForm.getForumTopicDetails().getTopicMessage().length() < 1 || forumTopicForm.getForumTopicDetails().getTopicMessage().equalsIgnoreCase("&nbsp;")){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Topic Message field cannot be empty.Please enter a message or click Cancel to return to the Topics List View."));
				addErrors(request,messages);
				return mapping.findForward("createtopicforward");
			}
		
	        String messagereply = forumTopicForm.getForumTopicDetails().getTopicMessage();
			
			//StringBuffer sb =new StringBuffer("textarea style=width:100%");
			boolean test =false;
			boolean test1 =false;
			boolean test2 =false;
			boolean test3 =false;
			boolean test4 =false;
			boolean test5 =false;
			CharSequence s ="width:100%;height:100%";
			CharSequence s1="width: 100%; bottom: 0px; height: 100%";
			CharSequence s2 ="height:100%";
			CharSequence s3 ="height: 100%";
			CharSequence s4 ="width:100%";
			CharSequence s5 ="width: 100%";
			test = messagereply.contains(s);
			test1=messagereply.contains(s1);
			test2=messagereply.contains(s2);
			test3=messagereply.contains(s3);
			test4=messagereply.contains(s4);
			test5=messagereply.contains(s5);
			if(test==true||test1==true||test2==true||test3==true||test4==true||test5==true){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Message cannot be saved. A textarea of 100% width or height was inserted into your message.  Please delete or resize the texarea."));
				addErrors(request,messages);
				return mapping.findForward("createtopicforward");
			}
			forumTopicForm.getForumTopicDetails().setTopicAuthor((String) request.getSession().getAttribute("userID"));
			
			forumTopicForm.getForumTopicDetails().setSiteId((String) request.getSession().getAttribute("siteId"));
			status = topicDAO.insertTopic(forumTopicForm.getForumTopicDetails());
			if (status==true){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Topic was not created try again later"));
				addErrors(request,messages);
				return showTopics(mapping,form,request,reponse);
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","You have successfully created a topic title and topic message"));
			saveMessages(request, messages);
			
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_DISCUSSIONFORUMS_ADD_TOPIC, toolManager.getCurrentPlacement().getContext()+" Topic id: "+forumTopicForm.getForumTopicDetails().getTopicId()+" was created", false),usageSession);
			return showTopics(mapping,form,request,reponse);
	}

	public ActionForward confirmTopicDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse reponse)
	{
		    ForumDao forumDao = ForumDaoService.getForumDao();//Called Service Locator
		    TopicDao topicDAO = TopicDaoService.getTopicDao();
			ForumTopicsForm forumTopicsForm = (ForumTopicsForm) form;
			Forum forum = new Forum();
			ActionMessages messages = new ActionMessages();
			forumTopicsForm.getForumTopicDetails().setForumId(forumTopicsForm.getForumId());
			forum  = forumDao.getForumContent(forumTopicsForm.getForumId());
			forumTopicsForm.setTopicForumName(forum.getForumName());
			
			//Added by mphahsm on 2016/10 to check also external URLs if user executing it has the permission to perform action						
			MessageDao messageDAO = MessageDaoService.getMessageDao();
			ForumMessage forumMessage = new ForumMessage();
			forumMessage = messageDAO.getTopicPosting(forumTopicsForm.getTopicId());
			String loginUser = request.getSession().getAttribute("userID").toString();
			String userCreatedTopic = forumMessage.getUserId();
						
			if (forumTopicsForm.isTopicDeletable()) { //Added by mphahsm on 2016/10 to check also external URLs if user executing it has the permission to perform action
				try{
					forumTopicsForm.getForumTopicDetails().setTopicTitle(topicDAO.getTopicName(forumTopicsForm.getTopicId()));
				} catch (Exception ex){
					logger.debug("DISCUSSIONFORUMS::EXCEPTION_OCCURED - "+ex.getMessage());
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Are you sure you want to delete the following topic and its messages?"));
				addErrors(request, messages);
				return mapping.findForward("topicdeleteforward");
			} else { //Added by mphahsm on 2016/10 to check also external URLs if user executing it has the permission to perform action
					
				if (loginUser.equals(userCreatedTopic))
				{
					try{
						forumTopicsForm.getForumTopicDetails().setTopicTitle(topicDAO.getTopicName(forumTopicsForm.getTopicId()));
					} catch (Exception ex){
						logger.debug("DISCUSSIONFORUMS::EXCEPTION_OCCURED - "+ex.getMessage());
					}
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Are you sure you want to delete the following topic and its messages?"));
					addErrors(request, messages);
					return mapping.findForward("topicdeleteforward");
					
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.message","You are forbidden to perform this action. Please contact your system administrator."));
					addErrors(request, messages);
					return mapping.findForward("showtopicforward");
				}
			} //End of mphahsm add
	}
	
	public ActionForward editTopic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse reponse)
	{
		    ForumDao forumDao = ForumDaoService.getForumDao();//Called Service Locator
		    TopicDao topicDAO = TopicDaoService.getTopicDao();
			ForumTopicsForm forumTopicsForm = (ForumTopicsForm) form;
			Forum forum = new Forum();
			forumTopicsForm.getForumTopicDetails().setForumId(forumTopicsForm.getForumId());
			forum  = forumDao.getForumContent(forumTopicsForm.getForumId());
			forumTopicsForm.setTopicForumName(forum.getForumName());
			
			int topicid = forumTopicsForm.getTopicId();
			forumTopicsForm.setEditTopic(topicid);
			String topicname="";
			
			//Added by mphahsm on 2016/10 to check external URLs if has the permission to perform action						
			ActionMessages messages = new ActionMessages();
			MessageDao messageDAO = MessageDaoService.getMessageDao();
			ForumMessage forumMessage = new ForumMessage();
			forumMessage = messageDAO.getTopicPosting(forumTopicsForm.getTopicId());
			String loginUser = request.getSession().getAttribute("userID").toString();
			String userCreatedTopic = forumMessage.getUserId();
			
			if (forumTopicsForm.isTopicDeletable()) {
				
				try{
					 topicname = topicDAO.getTopicName(forumTopicsForm.getTopicId());
					 forumTopicsForm.setTopicname(topicname);
				} catch (Exception ex){
					logger.debug("ERROR occured in  confirmTopicDelete method in ForumTopicAction "+ex);
				}
				
				return mapping.findForward("edittopic");
			} else {
				
				if (loginUser.equals(userCreatedTopic))
				{
					try{
						 topicname = topicDAO.getTopicName(forumTopicsForm.getTopicId());
						 forumTopicsForm.setTopicname(topicname);
					} catch (Exception ex){
						logger.debug("ERROR occured in  confirmTopicDelete method in ForumTopicAction "+ex);
					}
					
					return mapping.findForward("edittopic");
					
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.message","You are forbidden to perform this action. Please contact your system administrator."));
					addErrors(request, messages);
					return mapping.findForward("showtopicforward");
				}
				
			} //End of mphahsm add
	}
	
	public ActionForward deleteTopic(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse reponse)
	{
			ActionMessages messages = new ActionMessages();
			ForumTopicsForm forumTopicForm = (ForumTopicsForm)form;
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			Session currentSession = sessionManager.getCurrentSession();
			
			String userID = currentSession.getUserEid();
			String ipAddress =  request.getRemoteAddr();
			boolean status=false;
			//UsageSession usageSession = UsageSessionService.startSession((String)request.getSession().getAttribute("userID"),request.getRemoteAddr(),request.getHeader("user-agent"));
			UsageSession usageSession = usageSessionService.getSession();
			TopicDao topicDAO = TopicDaoService.getTopicDao();
			forumTopicForm.getForumTopicDetails().setHideStatus("Y");
			forumTopicForm.getForumTopicDetails().setTopicId(forumTopicForm.getTopicId());
			
			status = topicDAO.deleteTopic(forumTopicForm.getForumTopicDetails(),userID, ipAddress );
			
			if(status==true)
			{
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Topic deletion was unsuccessful, Please try again"));
				addErrors(request, messages);
				return showTopics(mapping,form,request,reponse);
			}
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","You have successfully deleted a topic."));
			addErrors(request, messages);
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_DISCUSSIONFORUMS_DELETE_TOPIC, toolManager.getCurrentPlacement().getContext()+" Topic id: "+forumTopicForm.getForumTopicDetails().getTopicId()+" was deleted", false),usageSession);
			return showTopics(mapping,form,request,reponse);
	}
	
	public ActionForward editTopicName(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
			ActionMessages messages = new ActionMessages();
			ForumTopicsForm forumTopicForm = (ForumTopicsForm)form;
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
			//UsageSession usageSession = UsageSessionService.startSession((String)request.getSession().getAttribute("userID"),request.getRemoteAddr(),request.getHeader("user-agent"));
			UsageSession usageSession = usageSessionService.getSession();
			TopicDao topicDAO = TopicDaoService.getTopicDao();
			topicDAO.editTopicName(forumTopicForm.getTopicname(), forumTopicForm.getTopicId());
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","You have successfully edited the topic title."));
			addErrors(request, messages);
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_DISCUSSIONFORUMS_EDIT, toolManager.getCurrentPlacement().getContext()+" Topic id: "+forumTopicForm.getForumTopicDetails().getTopicId()+" was edited", false),usageSession);
			return showTopics(mapping,form,request,reponse);
	}
	
	
	public ActionForward returnToForum(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse reponse)
	{
		return mapping.findForward("returntoforumforward");
	}
	
	private List pager(int start,int records,List listofItems){
		int end = start+records;
		int listSize = listofItems.size();
		if (start<0){
			start=1;
		}
		if (listSize == 0){
			start=0;
			return listofItems;
		} else if (listSize <=records){
			start = 0;
			listofItems = listofItems.subList(start,listSize);
			end=listSize;
		} else {
			if(listSize > records){ 
				if (start+records <= listSize){
					if (start==0)
					{
						start=1;
					}
			        listofItems=listofItems.subList(start-1,start+records-1);
			        end =start+records-1;
				} else if( start+records > listSize){
					start =listSize - (listSize%records);
					listofItems=listofItems.subList(start,listSize);
					end=listSize;
				}
			}
		}
		this.start = start;
		return listofItems;
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

