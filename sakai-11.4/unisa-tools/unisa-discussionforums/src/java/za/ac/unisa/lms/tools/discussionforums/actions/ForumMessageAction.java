//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.discussionforums.actions;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
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
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.discussionforums.dao.ForumDao;
import za.ac.unisa.lms.tools.discussionforums.dao.ForumMessage;
import za.ac.unisa.lms.tools.discussionforums.dao.Forum;
import za.ac.unisa.lms.tools.discussionforums.dao.MessageDao;
import za.ac.unisa.lms.tools.discussionforums.dao.TopicDao;
import za.ac.unisa.lms.tools.discussionforums.forms.ForumMessageForm;
import za.ac.unisa.lms.tools.discussionforums.forms.ForumTopicsForm;
import za.ac.unisa.lms.tools.discussionforums.service.ForumDaoService;
import za.ac.unisa.lms.tools.discussionforums.service.MessageDaoService;
import za.ac.unisa.lms.tools.discussionforums.service.TopicDaoService;
import za.ac.unisa.security.SecurityServices;

/**
 * MyEclipse Struts
 * Creation date: 06-26-2006
 *
 * XDoclet definition:
 * @struts:action parameter="action"
 */
public class ForumMessageAction extends LookupDispatchAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ForumTopicsForm forumTopicsForm = (ForumTopicsForm) request.getSession().getAttribute("forumTopicsForm");
		if (request.getParameter("action") == null) return showMessages(mapping, form, request, response);
		return super.execute(mapping, form, request, response);
	}

	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
    private ToolManager toolManager;
    private SessionManager sessionManager;
	//private Log log = LogFactory.getLog(this.getClass());
	protected static Logger logger = Logger.getLogger(ForumMessageAction.class);
	private int start;//start of the page to be viewed;
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("showMessages", "showMessages");
		map.put("button.save", "saveMessage");
		map.put("confirmMessageDelete","confirmMessageDelete");
		map.put("button.delete", "deleteMessage");
		map.put("button.cancel", "showMessages");
		map.put("button.back", "showMessages");
		map.put("button.first", "firstMessages");
		map.put("button.previous", "previousMessages");
		map.put("button.next", "nextMessages");
		map.put("button.last", "lastMessages");
		map.put("button.add.attachment", "addAttachment");
		map.put("button.continue","attachFile");
		map.put("readAttachment", "readAttachment");
		map.put("clearAttachment", "clearAttachment");
		return map;
	}

	public ActionForward showMessages(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		
		ForumMessageForm forumMessageForm = (ForumMessageForm)form;
		ForumDao forumDao = ForumDaoService.getForumDao();//Called Service Locator
		TopicDao topicDAO = TopicDaoService.getTopicDao();
		MessageDao messageDAO = MessageDaoService.getMessageDao();
		ForumMessage forumMessage = new ForumMessage();
		SecurityServices ss = new SecurityServices();
		Vector messages = new Vector();
		Integer numberOfRecords;
		String siteReference = "";
		int count = 0;
		if (forumMessageForm.getCancel().equals("topics")){
			return backToTopic(mapping, form, request, response);
		}
		if (forumMessageForm.getUpload().equals("upload")){	
			forumMessageForm.setFilename(forumMessageForm.getFilename());
		} else {
			forumMessageForm.setFilename("");
		}
		siteReference = ss.getSiteReference();
		forumMessageForm.setReplyAddable(ss.checkPermission(siteReference, "discuss.addreply"));
		forumMessageForm.setAnyReplyDeledable(ss.checkPermission(siteReference, "discuss.deletereply.any"));
		if (forumMessageForm.getTopicId() != null){
			forumMessageForm.getForumMessage().setTopicId(forumMessageForm.getTopicId());			
		}
		if (forumMessageForm.getCancel().equals("messages")){
			forumMessageForm.getForumMessage().setTopicId(new Integer(request.getParameter("topicId")));
			forumMessageForm.setTopicId(new Integer(request.getParameter("topicId")));
			forumMessageForm.setForumId(request.getParameter("forumId"));
			forumMessageForm.setHidden("0");
		}
		forumMessage = messageDAO.getTopicPosting(forumMessageForm.getForumMessage().getTopicId());
		if (forumMessage.getAuthor()== null){
			forumMessage.setAuthor("Not Available");
		}
		if (forumMessage.getAuthor().equalsIgnoreCase("admin")){
			forumMessage.setAuthor("MyUnisa Administrator");
		} else if (forumMessage.getAuthor().equalsIgnoreCase("NotAvailable")) {
			forumMessage.setAuthor("Not Available");
		}
		forumMessageForm.setForumMessage(forumMessage);
		try{
			forumMessageForm.setMessageTopic(topicDAO.getTopicName(forumMessageForm.getForumMessage().getTopicId()));
			if (forumMessageForm.isTopicViewed()){
				forumMessageForm.setTopicViewed(false);
			}
		}  catch (Exception ex) {
			logger.debug("DISCUSSIONFORUMS::EXCEPTION_OCCURED - "+ex.getMessage());
			
		}
		if (request.getParameter("msgRecords") == null){
			numberOfRecords = new Integer("10");
		} else {
			numberOfRecords = new Integer(request.getParameter("msgRecords"));
		}
		
		if (forumMessageForm.getForumId() == null || forumMessageForm.getForumId().equals("")){
			if (request.getParameter("forumId") != null && !request.getParameter("forumId").equals("")){
				forumMessageForm.setForumId(request.getParameter("forumId"));
			} 	
			
		}
		if (forumMessageForm.getForumDesc() == null || forumMessageForm.getForumDesc().equals("")){
			if (request.getParameter("forumDesc") != null || !request.getParameter("forumDesc").equals("")){
				forumMessageForm.setForumDesc(request.getParameter("forumDesc"));
			} else {
				if (forumMessageForm.getForumId() != null && !forumMessageForm.getForumId().equals("")){
					Forum forum = new Forum();
					forum  = forumDao.getForumContent(new Integer(forumMessageForm.getForumId()));
					forumMessageForm.setForumDesc(forum.getForumName());
					if (forumMessageForm.getForumDesc().equals("") || forumMessageForm.getForumDesc() == null){
						return mapping.findForward("showtopicforward2");
					}
				} else {
					return mapping.findForward("showtopicforward2");
				}
			}
			
		}
		
		List topicMessages =  messageDAO.getMessageList(forumMessageForm.getForumMessage().getTopicId());
		forumMessageForm.setallMessages(topicMessages);
		forumMessageForm.setNumberOfItems(topicMessages.size());
		forumMessageForm.setStart(1);
		forumMessageForm.setTopicMessages(pager(0,numberOfRecords,topicMessages));
		forumMessageForm.setEnd(Math.min(numberOfRecords.intValue(), new Integer(forumMessageForm.getNumberOfItems()).intValue()));
		Iterator messageList = forumMessageForm.getTopicMessages().iterator();
		while(messageList.hasNext()){
			ForumMessage msg = (ForumMessage) messageList.next();
			if (count % 2 == 0){
				msg.setColoured("1");
			} else {
				msg.setColoured("0");
			}
			messages.addElement(msg);
			count++;
		}
		
		forumMessageForm.setTopicMessages(messages);
		if (forumMessageForm.getUpload().equals("upload")){
			forumMessageForm.getForumMessage().setMessageReply(request.getParameter("forumMessage.messageReply"));
		}
        try{
		if (request.getParameter("hidden").equals("0")){
			forumMessageForm.setHidden("0");
		}
        }catch(NullPointerException e){}
		//else {
			forumMessageForm.setHidden("0");
		//}
		if (forumMessageForm.getNumberOfItems() < 1){
			forumMessageForm.setStart(0);
			forumMessageForm.setEnd(0);
		}
		
		return mapping.findForward("showmessagesforward");
		
	}

	public ActionForward firstMessages(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
			ForumMessageForm forumMessageForm = (ForumMessageForm)form;
			
			forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId()); //Added by mphahsm on 2016/11: To fix the error of message failing to create because the system lost the forumId in the process
			
			int numberOfRecords = new Integer(forumMessageForm.getMsgRecords()).intValue();
			List forumMessages = forumMessageForm.getAllMessages();
			
			forumMessageForm.setStart(1);
			forumMessageForm.setTopicMessages(pager(0,numberOfRecords,forumMessages));
			forumMessageForm.setEnd(Math.min(numberOfRecords, forumMessageForm.getNumberOfItems()));
			if (forumMessageForm.getNumberOfItems() < 1){
				forumMessageForm.setStart(0);
			}
			
			return mapping.findForward("showmessagesforward");
	}

	public ActionForward previousMessages(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
			ForumMessageForm forumMessageForm = (ForumMessageForm)form;
			
			forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId()); //Added by mphahsm on 2016/11: To fix the error of message failing to create because the system lost the forumId in the process
		
			int start = forumMessageForm.getStart();
			int numberOfRecords = new Integer(forumMessageForm.getMsgRecords()).intValue();
			List forumMessages = forumMessageForm.getAllMessages();
			if ((start - numberOfRecords) <= 0){
				forumMessageForm.setStart(1);
				forumMessageForm.setTopicMessages(pager(0,numberOfRecords,forumMessages));
				forumMessageForm.setEnd(Math.min(numberOfRecords, forumMessageForm.getNumberOfItems()));
			} else if ((start + numberOfRecords) < forumMessageForm.getNumberOfItems()){
				int end = forumMessageForm.getStart() - 1;
				start = forumMessageForm.getStart() - numberOfRecords;
				forumMessageForm.setTopicMessages(pager(start,numberOfRecords,forumMessages));
				forumMessageForm.setStart(start);
				forumMessageForm.setEnd(Math.min(end , forumMessageForm.getNumberOfItems()));
			} else {
				int end = forumMessageForm.getStart() - 1;
				start = forumMessageForm.getStart()- numberOfRecords;
				forumMessageForm.setTopicMessages(pager(start,numberOfRecords,forumMessages));
				forumMessageForm.setStart(start);
				forumMessageForm.setEnd(Math.min(end , forumMessageForm.getNumberOfItems()));
            }
			
			if (forumMessageForm.getNumberOfItems() < 1){
				forumMessageForm.setStart(0);
			}
	
			return mapping.findForward("showmessagesforward");
	}	
	
	public ActionForward nextMessages(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse reponse)
	{
			ForumMessageForm forumMessageForm = (ForumMessageForm)form;
						
			forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId()); //Added by mphahsm on 2016/11: To fix the error of message failing to create because the system lost the forumId in the process
									
			int start = forumMessageForm.getStart();
			int numberOfRecords = new Integer(forumMessageForm.getMsgRecords()).intValue();
			List forumMessages = forumMessageForm.getAllMessages();
			
			if (start+numberOfRecords > forumMessageForm.getNumberOfItems()){
				forumMessageForm.setStart(start);
				forumMessageForm.setTopicMessages(pager(start,numberOfRecords,forumMessages));
				forumMessageForm.setEnd(forumMessageForm.getNumberOfItems());
			} else if ((start+numberOfRecords - 1) <= forumMessageForm.getNumberOfItems()){  
				start = forumMessageForm.getEnd()+1;
				int end = (start + numberOfRecords - 1);
				forumMessageForm.setStart(start);
				forumMessageForm.setTopicMessages(pager(start, numberOfRecords,forumMessages));
				end=Math.min(end,forumMessageForm.getNumberOfItems());
				forumMessageForm.setEnd(end);
			} else {
				start = forumMessageForm.getEnd();
				int end = start + numberOfRecords - 1;
				forumMessageForm.setStart(start);
				forumMessageForm.setTopicMessages(pager(start, numberOfRecords,forumMessages));
				end = Math.min(end,forumMessageForm.getNumberOfItems());
				forumMessageForm.setEnd(end);
	    	 }
			
			if ((forumMessageForm.getStart() + numberOfRecords - 1) == forumMessageForm.getNumberOfItems()){
				start=forumMessageForm.getStart();
				int end = forumMessageForm.getNumberOfItems();
				end=Math.min(end,forumMessageForm.getNumberOfItems());
				forumMessageForm.setEnd(end);
				forumMessageForm.setTopicMessages(forumMessages.subList(start, end));
			}
			
			if (forumMessageForm.getNumberOfItems() < 1){
				forumMessageForm.setStart(0);
			}
			
			return mapping.findForward("showmessagesforward");
	}	
	
	public ActionForward lastMessages(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse reponse){
			ForumMessageForm forumMessageForm = (ForumMessageForm)form;
			
			forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId()); //Added by mphahsm on 2016/11: To fix the error of message failing to create because the system lost the forumId in the process
			
			int start = forumMessageForm.getStart();
			int numberOfRecords = new Integer(forumMessageForm.getMsgRecords()).intValue();
			List forumMessages = forumMessageForm.getAllMessages();
			
			if (forumMessageForm.getNumberOfItems() == 0){
				return mapping.findForward("showmessagesforward");
			}
			
			if ((forumMessageForm.getStart() + numberOfRecords-1) == forumMessageForm.getNumberOfItems()){
				start = forumMessageForm.getStart();
				int end = forumMessageForm.getNumberOfItems();
				end = Math.min(end , forumMessageForm.getNumberOfItems());
				forumMessageForm.setEnd(end);
				forumMessageForm.setTopicMessages(forumMessages.subList(start, end));
			} else {
				start = (1 + forumMessageForm.getNumberOfItems()) - (forumMessageForm.getNumberOfItems() % numberOfRecords);
				forumMessageForm.setTopicMessages(pager(start, numberOfRecords,forumMessages));
				forumMessageForm.setStart(start);
				forumMessageForm.setEnd(forumMessageForm.getNumberOfItems());
			}
			
			if ((forumMessageForm.getStart()-1) == forumMessageForm.getNumberOfItems()){
				start = forumMessageForm.getNumberOfItems() - numberOfRecords + 1;
				forumMessageForm.setTopicMessages(forumMessages.subList(start, forumMessageForm.getNumberOfItems()));
				forumMessageForm.setStart(forumMessageForm.getNumberOfItems() - numberOfRecords + 1);
	        }
			
			return mapping.findForward("showmessagesforward");
	}	
	
	public ActionForward addAttachment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		    String forumid = request.getParameter("forumId");
			ForumMessageForm forumMessageForm = (ForumMessageForm)form;
			forumMessageForm.getForumMessage().setForumId(forumid);
			return mapping.findForward("addattachmentforward");
	}	
	

	@SuppressWarnings("deprecation")
	public ActionForward attachFile(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
			ForumMessageForm forumMessageForm = (ForumMessageForm) form;
			ActionMessages messages = new ActionMessages();
			String readFile = "";
			String writeFile = "";
			FileInputStream buffIn = null;
			FileOutputStream buffout = null;
			String fileDir;
			String inputFileName = "";
			try{
				inputFileName = request.getAttribute("theFile").toString();
			} catch (NullPointerException ne){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","You cannot upload file greater than 6 megabytes"));
				addErrors(request, messages);
				return mapping.findForward("addattachmentforward");
			}
			String uploadPath = getServlet().getServletContext().getInitParameter("forumFullPath")+"/";
			String [] input = inputFileName.split(",");
			String[] parseInput; 
			String extensions = "exe,zip,avi";
			String[] extList = extensions.split(",");
			for (int i=0; i < input.length; i++){
				parseInput = input[i].split("=");
				if (parseInput.length > 1){
					if (i == 0){						
						String tmp = parseInput[1];
						if (parseInput[1].lastIndexOf("\\") > 0 ){
							tmp = parseInput[1].substring(parseInput[1].lastIndexOf("\\")+1);
						}
						writeFile = tmp;
					} else if (i == 1){
						readFile = parseInput[1];
					}
				}
			}
			
			if (writeFile.equalsIgnoreCase("") && forumMessageForm.getAddressLink().length() < 1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Click Browse button to select the file or enter the URL link or click Cancel button"));
					addErrors(request,messages);
					return addAttachment(mapping, form, request, response);
			} else if (!(writeFile.equalsIgnoreCase("")) && forumMessageForm.getAddressLink().length() > 1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Click Browse button to select the file or enter the URL link or click Cancel button"));
				addErrors(request,messages);
				return addAttachment(mapping, form, request, response);				
			}
			request.setAttribute("inputFileName", inputFileName);
			if (!writeFile.equalsIgnoreCase("") && writeFile != null){
					boolean notAllowed = false;
					request.setAttribute("filename", writeFile);
					forumMessageForm.setFilename(writeFile);
					File testSize = new File(readFile);
					for(int i=0; i < extList.length; i++){
						if (writeFile.substring(writeFile.lastIndexOf(".")+1).equalsIgnoreCase(extList[i])){
							notAllowed = true;
						}
					}
					if (notAllowed){
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","You are not allowed to attach video,exe or zip files"));
						addErrors(request, messages);
						return addAttachment(mapping, form, request, response);
					}
					if (testSize.length() > 6291456){
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","You cannot upload file greater than 6 megabytes"));
						addErrors(request, messages);
						return addAttachment(mapping, form, request, response);
					} else {
						try {
							buffIn = new FileInputStream(readFile);
							fileDir = uploadPath+request.getSession().getAttribute("siteId")+"/"+forumMessageForm.getTopicId()+"/";
							File file = new File(fileDir);
							if (!file.exists()){
								file.mkdirs();
							}
							buffout = new FileOutputStream(fileDir+writeFile);
							boolean eof = false;
							while(!eof){
								int line = buffIn.read();
								if (line == -1){
									eof  = true;
								} else {
									buffout.write(line);
								}
							}
							buffIn.close();
							buffout.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (NullPointerException ne) {						
							ne.printStackTrace();						
						} catch (IOException ioe){
							ioe.printStackTrace();
						} finally {
							try{
								if (buffIn != null){
									buffIn.close();
									buffout.close();
								}
							} catch(IOException ie){
								ie.printStackTrace();
							}catch(NullPointerException ne){
								ne.printStackTrace();
							}
						}
					}
			} else if(forumMessageForm.getAddressLink().length() > 0) {
				request.setAttribute("filename","");
				if (!forumMessageForm.getAddressLink().startsWith("http://")){	
					forumMessageForm.setAddressLink("");
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Url should start with http://"));
					addErrors(request, messages);
					return addAttachment(mapping, form, request, response);
				}
			} 
			forumMessageForm.setHidden("0");
			return showMessages(mapping, form, request, response);
	}
	
	public void readAttachment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		ForumMessageForm forumMessageForm = (ForumMessageForm)form;
		ServletContext application;
		DataInputStream in = null;
		ServletOutputStream sos = null;
		String type = null;			
		String fileDir = null;
		String fileName = null;
			
		fileDir = getServlet().getServletContext().getInitParameter("forumFullPath")+"/";
		type = request.getParameter("attachment").substring(request.getParameter("attachment").lastIndexOf(".")+1);
		fileName = request.getParameter("attachment").substring(request.getParameter("attachment").lastIndexOf("/")+1);
		
		try 
		{
			in = new DataInputStream(new FileInputStream(new File(fileDir+request.getParameter("attachment"))));
			sos = response.getOutputStream();
		} 
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/* commented out by mphahsm on 2016/05 to avoid being specific of file content type
		if (type.equalsIgnoreCase("pdf")) {
			response.setContentType("application/pdf");
		} else if ((type.equalsIgnoreCase("doc")) || (type.equalsIgnoreCase("docx"))) {
			response.setContentType("application/msword");
		} else if (type.equalsIgnoreCase("wpd")) {
			response.setContentType("application/wordperfect");
		} else if (type.equalsIgnoreCase("txt")) {
			response.setContentType("text/plain");
		} else if (type.equalsIgnoreCase("html")) {
			response.setContentType("text/html");
		} else if (type.equalsIgnoreCase("gif")) {
			response.setContentType("image/gif");
		} else if ((type.equalsIgnoreCase("jpeg")) || (type.equalsIgnoreCase("jpg")) || (type.equalsIgnoreCase("jpe"))) {
			response.setContentType("image/jpeg");
		} else if (type.equalsIgnoreCase("png")) {
			response.setContentType("image/png");
		}else if (type.equalsIgnoreCase("css")) {
			response.setContentType("text/css");
		}*/
		
		//response.addHeader("content-disposition", "filename="+fileName);
		/* Added by mphahsm on 2016/05 to handle any file content type and download file instead of opening it inline*/
		response.setContentType("application/octet-stream");
		response.addHeader("content-disposition", "attachment; filename="+fileName); 
		/* End of mphahsm Add*/
			
		int w;
		try {
			w = in.read();
			while (w != -1) {
				sos.write(w);
				w = in.read();
			}
			sos.flush();
			sos.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public ActionForward clearAttachment(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			ForumMessageForm forumMessageForm = (ForumMessageForm)form;
			String fileName = null;
			if (!(request.getParameter("fname").equalsIgnoreCase("clearUrl"))){
				fileName = getServlet().getServletContext().getInitParameter("forumFullPath")+"/"+request.getSession().getAttribute("siteId")+"/"+forumMessageForm.getTopicId()+"/"+request.getParameter("fname");
				File file = new File(fileName);
				if (file.exists()){
					file.delete();
				}
			} else {
				forumMessageForm.setAddressLink("");
			}
			forumMessageForm.setUpload("no");
			forumMessageForm.setFilename("");
			
			return mapping.findForward("showmessagesforward");
	}

	
	public ActionForward saveMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		    ForumMessageForm forumMessageForm = (ForumMessageForm) form;
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
			UsageSession usageSession = usageSessionService.getSession();
			ActionMessages messages = new ActionMessages();
			ForumDao forumDao = ForumDaoService.getForumDao();//Called Service Locator
			MessageDao messageDAO = MessageDaoService.getMessageDao();
			String uploadPath = getServlet().getServletContext().getInitParameter("forumFullPath")+"/";
			String attachment = "";
			boolean status=false;
			
			if (forumMessageForm.getTopicId() == null){
				forumMessageForm.setTopicId(new Integer(request.getParameter("topicId")));
			}
			forumMessageForm.getForumMessage().setTopicId(forumMessageForm.getTopicId());
			/* Commented out by mphahsm: 2016/11
			if (forumMessageForm.getForumId() == null){
				forumMessageForm.setForumId(request.getParameter("forumId"));
			}*/
			
			//Added by mphahsm: 2016/11 -  To fix the error of message failing to create because the system lost the forumId in the process
			if (forumMessageForm.getForumId().equals("null")){
				forumMessageForm.setForumId(forumMessageForm.getForumMessage().getForumId());
			}//End of mphahsm
			forumMessageForm.getForumMessage().setForumId(forumMessageForm.getForumId());
			if(forumMessageForm.getForumMessage().getMessageReply().length() == 0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Your Message field cannot be empty when Attachment has been added. Please enter a message or click Cancel to return to the Topics List View."));
				addErrors(request,messages);
				return mapping.findForward("showmessagesforward");
			}
			String messagereply = forumMessageForm.getForumMessage().getMessageReply();
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
				return mapping.findForward("showmessagesforward");
			}
			
			if (forumMessageForm.getUpload().equalsIgnoreCase("upload")){
				if (!forumMessageForm.getFilename().equalsIgnoreCase("")){
					attachment = request.getSession().getAttribute("siteId")+"/"+forumMessageForm.getTopicId()+"/"+forumMessageForm.getFilename();
					forumMessageForm.getForumMessage().setAttachment(attachment);
					forumMessageForm.getForumMessage().setFileType("F");
				} else {
					if (!forumMessageForm.getAddressLink().equalsIgnoreCase("")){
						forumMessageForm.getForumMessage().setAttachment(forumMessageForm.getAddressLink().toLowerCase());
						forumMessageForm.getForumMessage().setFileType("L");
					}
				}
			}
			forumMessageForm.getForumMessage().setAuthor((String)request.getSession().getAttribute("userID"));
			if(isInteger(forumMessageForm.getForumMessage().getAuthor())){
				forumMessageForm.getForumMessage().setUserType("S");
			} else {
				forumMessageForm.getForumMessage().setUserType("L");
			}
			//if topic id is zero
			if(forumMessageForm.getTopicId() == null||forumMessageForm.getTopicId().equals(0)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Your message could not be saved. Please go back to your topic and retype the message."));
				return mapping.findForward("showtopicforward2");
			}else{
				status=messageDAO.insertMessage(forumMessageForm.getForumMessage());
			if(status==true){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Message was not created, please try again"));
				addErrors(request,messages);
				forumMessageForm.setHidden("0");
				return showMessages(mapping,form,request,response);
			}
			}
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The reply was successfully recorded."));
			saveMessages(request, messages);
			forumMessageForm.getForumMessage().setMessageReply("");
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_DISCUSSIONFORUMS_ADD_REPLY, toolManager.getCurrentPlacement().getContext()+" Message id: "+forumMessageForm.getForumMessage().getMessageId()+" was added", false),usageSession);
			forumMessageForm.setHidden("0");
			forumMessageForm.setUpload("no");
			forumMessageForm.setAddressLink("");
			forumMessageForm.setTheFile(null);
			forumMessageForm.setCancel("no");
			return showMessages(mapping,form,request,response);
		}

	public ActionForward confirmMessageDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
			ActionMessages messages = new ActionMessages();
			ForumMessageForm forumMessageForm = (ForumMessageForm)form;
			forumMessageForm.getForumMessage().getMessageId();
			MessageDao messageDAO = MessageDaoService.getMessageDao();
						
			//Added by mphahsm on 2016/10 to check also external URLs if user executing it has the permission to perform action
			ForumMessage forumMessage = new ForumMessage();
			forumMessage = messageDAO.getMessageDetail(forumMessageForm.getForumMessage().getMessageId());
			
			String loginUser = request.getSession().getAttribute("userID").toString();
			String userCreatedMessage = forumMessage.getUserId();
			
			if (forumMessageForm.isAnyReplyDeledable()) { //Added by mphahsm on 2016/10 to check also external URLs if user executing it has the permission to perform action
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Are you sure you want to delete following message?"));
				addErrors(request,messages);
				forumMessageForm.setForumMessage(messageDAO.getMessageDetail(forumMessageForm.getForumMessage().getMessageId()));
				return mapping.findForward("messagedeleteforward");
			} else { //Added by mphahsm on 2016/10 to check also external URLs if user executing it has the permission to perform action
				if (loginUser.equals(userCreatedMessage)) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Are you sure you want to delete following message?"));
					addErrors(request,messages);
					forumMessageForm.setForumMessage(messageDAO.getMessageDetail(forumMessageForm.getForumMessage().getMessageId()));
					return mapping.findForward("messagedeleteforward");
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.message","You are forbidden to perform this action. Please contact your system administrator."));
					addErrors(request, messages);
					return mapping.findForward("forumforward");
				}
			}//End of mphahsm
	}

	public ActionForward deleteMessage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) 
	{
			ForumMessageForm forumMessageForm = (ForumMessageForm) form;
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class); //Added by mphahsm on 2015/09
			UsageSession usageSession = usageSessionService.getSession();
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			Session currentSession = sessionManager.getCurrentSession();
						
			String userID = currentSession.getUserEid();
			ActionMessages messages = new ActionMessages();
			String ipAddress =  request.getRemoteAddr();
			MessageDao messageDAO = MessageDaoService.getMessageDao();
			String attach="";
			try
			{
				if (forumMessageForm.getForumMessage() != null)
				{	
					forumMessageForm.setFirstTopicMessage(messageDAO.initailMessage(forumMessageForm.getForumMessage()));
					
					if(forumMessageForm.getFirstTopicMessage().equals("N"))
					{
						boolean status=messageDAO.deleteMessage(forumMessageForm.getForumMessage(), userID, ipAddress);
						
						if(status==true)
						{
							messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Message deletion was unsuccessful, Please try again"));	
							addErrors(request, messages);
							return showMessages(mapping,form,request,response);
						}
											
						if (!(forumMessageForm.getForumMessage().getAttachment().equalsIgnoreCase(" ")) || !(forumMessageForm.getForumMessage().getAttachment() == null ))
						{
							if (forumMessageForm.getForumMessage().getFileType().equalsIgnoreCase("F"))
							{
								String currentDir = forumMessageForm.getForumMessage().getAttachment().substring(0, forumMessageForm.getForumMessage().getAttachment().lastIndexOf("/")-1);
								String uploadPath = getServlet().getServletContext().getInitParameter("forumFullPath")+"/";
								attach = uploadPath+forumMessageForm.getForumMessage().getAttachment();
								File file = new File(attach);
								if (file.exists()){
									file.delete();
								}
								File listDir = new File(uploadPath+currentDir);
								if (listDir.isDirectory()){
									if (listDir.listFiles().length < 1){
										listDir.delete();
									}
								}
							}
						}
						
						//Added by mphahsm on 2015/09 to show this message only when delete is successfully
						if (!(toolManager.getCurrentPlacement().getContext().equals(null)))
						{
							eventTrackingService.post(
									eventTrackingService.newEvent(EventTrackingTypes.EVENT_DISCUSSIONFORUMS_DELETE_REPLY, toolManager.getCurrentPlacement().getContext()+" Message id: "+forumMessageForm.getForumMessage().getMessageId()+" was deleted", false),usageSession);
						}
						else
						{
							eventTrackingService.post(
									eventTrackingService.newEvent(EventTrackingTypes.EVENT_DISCUSSIONFORUMS_DELETE_REPLY, "myUnisa Message id: "+forumMessageForm.getForumMessage().getMessageId()+" was deleted", false),usageSession);
						}
						
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The message was successfully deleted"));
						addErrors(request, messages);
						forumMessageForm.getForumMessage().setMessageReply("");
						forumMessageForm.setHidden("0");
						//End of mphahsm Add
						
					}
					else
					{
						logger.info("DISCUSSIONFORUMS::FIRST_MESSAGE_DELETE_ATTEMPT - [Attempt to delete first message by "+userID + " on TopicID: "+forumMessageForm.getForumMessage().getTopicId()+"]");
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","This is intial message"));
						addErrors(request, messages);
						forumMessageForm.getForumMessage().setMessageReply("");
						forumMessageForm.setHidden("0");
						
						return showMessages(mapping,form,request,response);
					}
				}
				else
				{
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Message deletion was unsuccessful, Please try again"));	
					addErrors(request, messages);
					forumMessageForm.getForumMessage().setMessageReply("");
					forumMessageForm.setHidden("0");
					
					return showMessages(mapping,form,request,response);
				}
			
	        } 
			catch (Exception e) 
			{
				logger.debug("DISCUSSIONFORUMS::EXCEPTION_OCCURED - "+e.getMessage());
				e.printStackTrace();
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Message deletion was unsuccessful, Please try again"));	
				addErrors(request, messages);
				forumMessageForm.getForumMessage().setMessageReply("");
				forumMessageForm.setHidden("0");
				
				return showMessages(mapping,form,request,response);
	        }
			
			return showMessages(mapping,form,request,response);
		}

	public ActionForward backToTopic(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
			if (null == request.getSession().getAttribute("forumTopicsForm")){
				return showMessages(mapping,form,request,response);
			}
			return mapping.findForward("showtopicforward2");
		}

	private List pager(int start,int records,List listofItems){
		int end = start+records;
		int listSize=0;
		try{
		 listSize = listofItems.size();
		} catch(NullPointerException ne){}
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
	
	private boolean isInteger(String i)
	{
		try{
			Integer.parseInt(i);
			return true;
		}catch(NumberFormatException nfe)
		{
			return false;
		}
	}	
	
}

