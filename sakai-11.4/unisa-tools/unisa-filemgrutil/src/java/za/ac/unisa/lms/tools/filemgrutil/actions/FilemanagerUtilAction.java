package za.ac.unisa.lms.tools.filemgrutil.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;
import za.ac.unisa.lms.tools.filemgrutil.forms.UtilDetailsForm;
import za.ac.unisa.lms.tools.filemgrutil.dao.FilemanagerDAO;
import org.apache.commons.logging.Log;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.LogFactory;

public class FilemanagerUtilAction extends LookupDispatchAction {
	// the code for removing white space problem when you use ENTER button
	// instead of mouse .
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (request.getParameter("action") == null)
			return allocateTask(mapping, form, request, response);
		return super.execute(mapping, form, request, response);
	}

	private ServerConfigurationService serverConfigurationService;
	private Log log = LogFactory.getLog(this.getClass());

	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("button.submit", "performTask");
		map.put("button.continue", "allocateTask");
		map.put("displayMainPage", "displayMainPage");
		map.put("button.back", "displayMainPage");
		return map;
	}

	public ActionForward displayMainPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UtilDetailsForm utilDetailsForm = (UtilDetailsForm) form;
		utilDetailsForm.setTask("fix");
		utilDetailsForm.setWebId("");
		return mapping.findForward("mainforward");
	}

	public ActionForward allocateTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UtilDetailsForm utilDetailsForm = (UtilDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		FilemanagerDAO filemanagerdao = new FilemanagerDAO();

		if (utilDetailsForm.getSubjectCode().length() > 7) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"errors.message.subjectcode"));

		} else if (utilDetailsForm.getSubjectCode().length() == 0
				|| utilDetailsForm.getSubjectCode().length() > 9) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"errors.message.subjectcode"));

		}

		else if (!filemanagerdao.isSubjectCodeValid(utilDetailsForm
				.getSubjectCode().toUpperCase())) {

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"errors.message.subjectcode1"));
		}

		/*
		 * catch(Exception ex){
		 * 
		 * log.error("ERROR occured in  fileManagerUtil method performTask "+ex);
		 * }
		 */

		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return displayMainPage(mapping, form, request, response);
		}
		if (utilDetailsForm.getTask().equals("fix")) {
			return mapping.findForward("webidforward");
		}
		if (utilDetailsForm.getTask().equals("solve")) {

			String code = utilDetailsForm.getSubjectCode().toUpperCase();
			String record = filemanagerdao.setWebRecord(code);
			// System.out.println("results---final"+ record);
			if (record.equals("sucess")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"sucsessfully.htm.solve"));

				addMessages(request, messages);
				return mapping.findForward("mainforward");
			}
			if (record.equals("")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"alredy.record"));

				addMessages(request, messages);
				return mapping.findForward("mainforward");
			}
			if (record.equals("norecord")) {

				ArrayList idList = filemanagerdao.getAnnstmId(code);
				utilDetailsForm.setWedids(idList);
				try {
					if (idList.isEmpty() == true) {
						// System.out.println("emptyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyynbdsvbn");
					} else {
						Iterator n = idList.iterator();
						while (n.hasNext()) {
							// System.out.println("wwwwwwwwwwwwwwwwwwwwwww"+n.next());
						}

					}
				} catch (NullPointerException e) {
					System.out.println("bbbb");
				}
				/*
				 * messages.add(ActionMessages.GLOBAL_MESSAGE, new
				 * ActionMessage("annstm.norecord"));
				 * 
				 * addMessages(request, messages);
				 */
				return mapping.findForward("mainforward");
			}
			if (record.equals("nosunrecord")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"sunstm.norecord"));

				addMessages(request, messages);
				return mapping.findForward("mainforward");
			}

			return mapping.findForward("mainforward");
		} else {
			return mapping.findForward("mainforward");

		}

	}

	public ActionForward performTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UtilDetailsForm utilDetailsForm = (UtilDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		FilemanagerDAO filemanagerdao = new FilemanagerDAO();
		String sourcePath = "";
		String destPath = "";
		serverConfigurationService = (ServerConfigurationService) ComponentManager
				.get(ServerConfigurationService.class);
		String mainpath = serverConfigurationService.getString("materialPath");//"/var/www/www3dev.unisa.ac.za/sol/data/htdocs/material/";
		new FilePermission(mainpath + "*", "read,write");
		boolean success = false;
		if (utilDetailsForm.getTask().equals("fix")) {
			if (utilDetailsForm.getWebId().length() < 1) {

				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"errors.message.no"));
				addErrors(request, messages);
				return mapping.findForward("webidforward");
			}

			try {
				int x = Integer.parseInt(utilDetailsForm.getWebId());
			} catch (NumberFormatException nFE) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", "Web ID should be a Number"));
				addErrors(request, messages);
				return mapping.findForward("webidforward");

			}

			try {
				if (!filemanagerdao.isWebIdValid(new Integer(utilDetailsForm
						.getWebId()))) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.message.valid"));
					addErrors(request, messages);
					return mapping.findForward("webidforward");
				}
			} catch (Exception ex) {
				log.error("ERROR occured in  fileManagerUtil method performTask isWebIdValid "
						+ ex);
			}

			destPath = filemanagerdao.getDocumentPath(new Integer(
					utilDetailsForm.getWebId()));

			if (destPath.startsWith("collect")) {
				sourcePath = mainpath + destPath.replace("collect/", "");
			} else {
				sourcePath = mainpath + "collect/" + destPath;
			}
			String destPath1 = mainpath + destPath;
			File source = new File(sourcePath);
			File destination = new File(destPath1);
			File destination1 = destination.getParentFile();

			// System.out.println("hdsckjsnclk"+destination.getParentFile());
			File destination2 = destination1.getParentFile();
			int i = destPath1.lastIndexOf("/");
			String pathname = destPath1.substring(0, i);
			String file = destPath1.substring(i + 1);
			utilDetailsForm.setFile_name(file);
			utilDetailsForm.setDest(pathname);
			int j = sourcePath.lastIndexOf("/");
			String source1 = sourcePath.substring(0, j);
			utilDetailsForm.setSource(source1);
			
			log.info("Source path: "+source);
			log.info("Destination path: "+destination);
			
			
			if (destination.exists()) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.file.exists"));
				// System.out.println("already there is a destination file ");
				addMessages(request, messages);
				return mapping.findForward("webidforward");

			} else if (source.isDirectory()) {
				messages.add(
						ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Web id ("
								+ (utilDetailsForm.getWebId())
								+ ")"
								+ " entered for "
								+ (utilDetailsForm.getSubjectCode()
										.toUpperCase()) + " does not exist."));
				// System.out.println("there is no source file ");
				addMessages(request, messages);
				return mapping.findForward("webidforward");

			}
			
			try {
				if (destination1.exists()) {

					boolean readable = source.canRead();
					boolean writable = destination1.canWrite();
					
					
					
					if (readable) {
						if (writable) {
							//success = source.renameTo(destination);
							 FileUtils.moveFile(source, destination);
							 success = true;
							 //System.out.println("statusssssssss-------------------" + success);
						} else {
							 //System.out.println(" not writable");
						}
					} else {
						 //System.out.println("not readable");
					}

				} 
				else 
				{
					if (!destination2.exists()) 
					{
						destination2.mkdir();
					}
					
					destination1.mkdir();
					boolean readable = source.canRead();
					boolean writable = destination1.canWrite();
					if (readable) {
						if (writable) {
							//success = source.renameTo(destination);
							 FileUtils.moveFile(source, destination);
							 success = true;
							 //System.out.println("statusssssssss------------------------------::");
						} else {
							 //System.out.println(" not writable");
						}
					} else {
						//System.out.println("not readable");
					}

				}

			}

			catch (SecurityException ex) {
				System.out.println("Exception while Moving File" + ex);
			}

		}
		if (success == true) {
			return mapping.findForward("success");
		}
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"notmoved.destination", "File does not exist"));

		addMessages(request, messages);
		return mapping.findForward("webidforward");

	}

}