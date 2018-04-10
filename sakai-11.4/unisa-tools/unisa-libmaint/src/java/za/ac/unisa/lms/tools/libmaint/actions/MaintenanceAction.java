package za.ac.unisa.lms.tools.libmaint.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.libmaint.dao.LibraryEResourcesDAO;
import za.ac.unisa.lms.tools.libmaint.forms.MaintenanceForm;
import za.ac.unisa.lms.tools.libmaint.forms.NewsTitleForm;
import za.ac.unisa.lms.tools.libmaint.forms.PlacementForm;
import za.ac.unisa.lms.tools.libmaint.forms.ResourceForm;
import za.ac.unisa.lms.tools.libmaint.forms.SubjectForm;
import za.ac.unisa.lms.tools.libmaint.forms.TextForm;
import za.ac.unisa.lms.tools.libmaint.forms.VendorForm;
import za.ac.unisa.lms.tools.libmaint.forms.highlightNoteForm;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.UserDirectoryService;

public class MaintenanceAction extends LookupDispatchAction {

	
	@Override
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
	    map.put("mainDisplay","mainDisplay");
	    map.put("txtDisplay","txtDisplay");
	    map.put("placementDisplay","placementDisplay");
	    map.put("vendorDisplay","vendorDisplay");
	    map.put("subjectDisplay","subjectDisplay");
	    map.put("resourceDisplay","resourceDisplay");   
	    map.put("highlightDisplay","highlightDisplay");
	    map.put("resourceDisplayPerVendor","resourceDisplayPerVendor");
	    map.put("newsTitleDisplay", "newsTitleDisplay");
	    map.put("button.add","gotoAdd");
	    map.put("button.remove","gotoRemove");
	    map.put("button.edit","gotoEdit");
	    map.put("button.editdates","resourceView");
	    map.put("button.view","resourceView");
	    map.put("button.back","Back");
	    map.put("button.back1","Back");
	    map.put("button.save","gotoSave");
	    map.put("button.continue","gotoContinue");
	    map.put("button.cancel","gotoCancel");
	    map.put("button.ranking","gotoRanking");
	    map.put("resourceLinkPlacements", "resourceLinkPlacements");
	    map.put("resourceLinkSubjects", "resourceLinkSubjects");
	    map.put("button.update","updateResources");
	    
	    return map;
	}
	
	/** mainDisplay: Forward to maintenance menu */
	public ActionForward mainDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
				
		return mapping.findForward("mainDisplay");
	}
	
	/** txtDisplay: Forward to maintenance of Text Coverage */
	public ActionForward txtDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		
		maintenanceForm.setDataList(dao.selectTextCoverageList("FORMLIST"));
		
		return mapping.findForward("txtDisplay");
	}
	
	/** placementDisplay: Forward to maintenance of Placements */
	public ActionForward placementDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		maintenanceForm.setDataList(dao.selectPlacementList("FORMLIST",maintenanceForm.getResourcesPerPlacement(),false,false));
				
		return mapping.findForward("placementDisplay");
	}
	
	/** vendorDisplay: Forward to maintenance of Vendors */
	public ActionForward vendorDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		maintenanceForm.setDataList(dao.selectVendorList("FORMLIST",maintenanceForm.getAlpha()));
				
		return mapping.findForward("vendorDisplay");
	}
	
	/** subjectDisplay: Forward to maintenance of Subjects */
	public ActionForward subjectDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		maintenanceForm.setDataList(dao.selectSubjectList("FORMLIST",maintenanceForm.getAlpha()));
				
		return mapping.findForward("subjectDisplay");
	}
	
	
	/** highlightDisplay: Forward to maintenance of Subjects */
	public ActionForward highlightDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		maintenanceForm.setDataList(dao.selectHighLightList("FORMLIST"));
				
		return mapping.findForward("highlightDisplay");
	}
	
	/** resourceDisplay: Forward to maintenance of E-resources/subject databases */
	public ActionForward resourceDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		maintenanceForm.setResourcesPerVendor("0");
		if ((maintenanceForm.getAlpha() == null)||(maintenanceForm.getAlpha().equals(""))) {
			maintenanceForm.setAlpha("a");
		}
		maintenanceForm.setDataList(dao.selectResourceList(maintenanceForm.getAlpha(),maintenanceForm.getResourcesPerPlacement(),maintenanceForm.getResourcesPerVendor()));

		request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));

		return mapping.findForward("resourceDisplay");
	}
	
	/** resourceDisplay: Forward to maintenance of E-resources/subject databases */
	public ActionForward resourceDisplayPerVendor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		maintenanceForm.setResourcesPerPlacement("0");
		maintenanceForm.setDataList(dao.selectResourceList(maintenanceForm.getAlpha(),maintenanceForm.getResourcesPerPlacement(),maintenanceForm.getResourcesPerVendor()));

		
		request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));

		return mapping.findForward("resourceDisplay");
	}
	
	
	/** resourceDisplay: Forward to maintenance of E-resources/subject databases */
	public ActionForward newsTitleDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		maintenanceForm.setDataList(dao.selectNewsTitleList("FORMLIST"));
				
		return mapping.findForward("newsTitleDisplay");
	}
	
	public String vendorSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		ActionMessages messages = new ActionMessages();
		
		// vendor is mandatory
		if ((maintenanceForm.getVendor()==null)||(maintenanceForm.getVendor().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the Vendor name"));
			addErrors(request, messages);
			return "vendorAdd";
		}
		
		//Remove all leading and trailing spaces
		maintenanceForm.setVendor(maintenanceForm.getVendor());
		maintenanceForm.setVendor(ltrim(maintenanceForm.getVendor()));
		maintenanceForm.setVendor(rtrim(maintenanceForm.getVendor()));
		
		//replace ' with spaces
		maintenanceForm.setVendor(maintenanceForm.getVendor().replace('\'',' '));
		
		// vendor must be unique
		ArrayList tmpList = maintenanceForm.getDataList();
		for (int i=0; i < tmpList.size(); i++) {
			VendorForm record = (VendorForm) tmpList.get(i);
			if ((record.getVendor().equals(maintenanceForm.getVendor()))&&
					(record.getVendorId() != maintenanceForm.getId())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Vendor already exist."));
				addErrors(request, messages);
				return "vendorAdd";				
			}
		}
		
		// validate vendor on-campus url
		System.out.println("onCampusURL "+maintenanceForm.getOnCampusURL());
		if (null != maintenanceForm.getOnCampusURL()) {
			maintenanceForm.setOnCampusURL(ltrim(maintenanceForm.getOnCampusURL()));
			maintenanceForm.setOnCampusURL(rtrim(maintenanceForm.getOnCampusURL()));
			//replace ' with spaces
			maintenanceForm.setOnCampusURL(maintenanceForm.getOnCampusURL().replace('\'',' '));	
			if (maintenanceForm.getOnCampusURL().length() >= 1) {
				boolean onURLValid = urlValidator(maintenanceForm.getOnCampusURL());
				if (onURLValid == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid On-campus URL, please check."));
					addErrors(request, messages);
					return "vendorAdd";
				}
			}
		}
		// validate vendor off-campus url
		if (null != maintenanceForm.getOnCampusURL()) {
			maintenanceForm.setOffCampusURL(ltrim(maintenanceForm.getOffCampusURL()));
			maintenanceForm.setOffCampusURL(rtrim(maintenanceForm.getOffCampusURL()));
			//replace ' with spaces
			maintenanceForm.setOffCampusURL(maintenanceForm.getOffCampusURL().replace('\'',' '));	

			if (maintenanceForm.getOffCampusURL().length() >= 1) {
				boolean onURLValid = urlValidator(maintenanceForm.getOffCampusURL());
				if (onURLValid == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid Off-campus URL, please check."));
					addErrors(request, messages);
					return "vendorAdd";
				}
			}
		}
		
		// enabled is mandatory
		if ((maintenanceForm.getEnabled()==null)||(maintenanceForm.getEnabled().length()==0)||(maintenanceForm.getEnabled().equals("0"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose if vendor is enabled."));
			addErrors(request, messages);
			return "vendorAdd";
		}
		
		if (maintenanceForm.getPerformAction().equals("EDIT")) {
			dao.updateVendor(maintenanceForm.getId(),maintenanceForm.getVendor(), maintenanceForm.getOnCampusURL(), maintenanceForm.getOffCampusURL(),
					maintenanceForm.getLogoFileName(), maintenanceForm.getLogoURL(),maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Vendor record successfully updated."));
			addMessages(request, messages);
		} else {
			dao.insertVendor(maintenanceForm.getVendor(), maintenanceForm.getOnCampusURL(), maintenanceForm.getOffCampusURL(),
					maintenanceForm.getLogoFileName(), maintenanceForm.getLogoURL(),maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "New Vendor record successfully added."));
			addMessages(request, messages);
		}
		maintenanceForm.setDataList(dao.selectVendorList("FORMLIST",maintenanceForm.getAlpha()));
		maintenanceForm.resetFormbean(mapping, request);
		
		// reset vendor values
		maintenanceForm.setId(0);
		maintenanceForm.setVendor("");
		maintenanceForm.setLogoFileName("");
		maintenanceForm.setLogoURL("");
		maintenanceForm.setEnabled("");
		maintenanceForm.setOffCampusURL("");
		maintenanceForm.setOnCampusURL("");
		maintenanceForm.setRefManagementURL("");
		
		return "vendorDisplay";
	}
	
	public String vendorEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			VendorForm record = (VendorForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				maintenanceForm.setId(record.getVendorId());
				maintenanceForm.setVendor(record.getVendor());
				maintenanceForm.setOnCampusURL(record.getOnCampusURL());
				maintenanceForm.setOffCampusURL(record.getOffCampusURL());
				maintenanceForm.setLogoFileName(record.getLogoFileName());
				maintenanceForm.setLogoURL(record.getLogoURL());
				maintenanceForm.setEnabled(record.getEnabled());
			}
		}
		
		// Check if records were marked for removal
		if (counter > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Only one record may be checked for edit."));
			addErrors(request, messages);
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectVendorList("FORMLIST",maintenanceForm.getAlpha()));
			return "vendorDisplay";						
		}
		
		// Check if records were marked for edit
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for edit."));
			addErrors(request, messages);
			return "vendorDisplay";						
		}
		
		return "vendorAdd";
	}
	
	public String vendorRemoveConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		ArrayList tmpList2 = new ArrayList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			VendorForm record = (VendorForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				
				// Check if record is in use, then record will be disabled and not removed!
				boolean inUse = dao.vendorInUse(record.getVendorId());
				
				record.setInUse(inUse);
				tmpList2.add(record);
			}
		}
		
		// Check if records were marked for removal
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for delete."));
			addErrors(request, messages);
			return "vendorDisplay";						
		}
		
		maintenanceForm.setRemovalList(tmpList2);
	
		return "vendorRemove";
	}
	
	public String vendorRemove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		String removed = "";
		ArrayList tmpList = maintenanceForm.getRemovalList();
		for (int i=0; i < tmpList.size(); i++) {
			VendorForm record = (VendorForm) tmpList.get(i);
			dao.deleteVendor(record.getVendorId(), record.isInUse());
			if (removed.equals("")) {
				removed = record.getVendor();
			} else {
				removed = removed+", "+record.getVendor();
			}
		}
				
		maintenanceForm.setDataList(dao.selectVendorList("FORMLIST",maintenanceForm.getAlpha()));
		maintenanceForm.resetFormbean(mapping, request);
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", removed+" successfully deleted/disabled"));
		addMessages(request, messages);
		
		return "vendorDisplay";
	}
	
	/**
	 * Method gotoAdd
	 * 		to decide to which add page it must go to
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward gotoAdd(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());

		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		maintenanceForm.setPerformAction("ADD");
		String atStep = request.getParameter("atstep");
		if (atStep.equals("txt")) {
			maintenanceForm.setPerformAction("ADD");
			maintenanceForm.setEnabled("Y");
			return mapping.findForward("txtAdd");
		} else if (atStep.equals("placement")) {
			maintenanceForm.setEnabled("Y");
			return mapping.findForward("placementAdd");
		} else if (atStep.equals("vendor")) {
			return mapping.findForward("vendorAdd");
		} else if (atStep.equals("subject")) {
			return mapping.findForward("subjectAdd");
		} else if (atStep.equals("highlight")) {
			return mapping.findForward("highlightAdd");
		} else if (atStep.equals("newstitle")) {
			return mapping.findForward("newsTitleAdd");
		} else if (atStep.equals("resource")) {
			maintenanceForm.resetFormbean(mapping, request);
		
			// select vendor options
			maintenanceForm.setVendorOptions(dao.selectVendorList("OPTIONLIST",""));
			request.setAttribute("vendorOptions", maintenanceForm.getVendorOptions());
			
			// select text content options
			maintenanceForm.setTxtOptions(dao.selectTextCoverageList("OPTIONLIST"));
			request.setAttribute("txtOptions", maintenanceForm.getTxtOptions());
			
			// select news letter title options
			maintenanceForm.setNewsTitleOptions(dao.selectNewsTitleList("OPTIONLIST"));
			request.setAttribute("newsTitleOptions", maintenanceForm.getNewsTitleOptions());
			
			// select highlight note options
			maintenanceForm.setHighlightOptions(dao.selectHighLightList("OPTIONLIST"));
			request.setAttribute("highlightOptions", maintenanceForm.getHighlightOptions());

			return mapping.findForward("resourceAdd");
		} else {
			return mapping.findForward("mainDisplay");
		}
	}
	
	/**
	 * Method gotoRemove
	 * 		to decide to which remove page it must go to
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward gotoRemove(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		MaintenanceForm maintenanceForm =(MaintenanceForm) form;
		ActionMessages messages = new ActionMessages();
		String gotoMethod = "";

		String atStep = request.getParameter("atstep");
		if (atStep.equals("txt")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = txtRemoveConfirm(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("txtremove")) {
			// method to do actual removal
			gotoMethod = txtRemove(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
			
		} else if (atStep.equals("placement")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = placementRemoveConfirm(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("placementremove")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = placementRemove(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
		
		} else if (atStep.equals("vendor")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = vendorRemoveConfirm(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("vendorremove")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = vendorRemove(mapping,form,request, response);
			return mapping.findForward(gotoMethod);		
			
		} else if (atStep.equals("subject")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = subjectRemoveConfirm(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
			
		} else if (atStep.equals("subjectremove")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = subjectRemove(mapping,form,request, response);
			return mapping.findForward(gotoMethod);		

		} else if (atStep.equals("highlight")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = highlightRemoveConfirm(mapping,form,request, response);
			return mapping.findForward(gotoMethod);

		} else if (atStep.equals("highlightremove")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = highlightRemove(mapping,form,request, response);
			return mapping.findForward(gotoMethod);		

		} else if (atStep.equals("newstitle")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = newsTitleRemoveConfirm(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("newstitleremove")) {
			// method to validate removal detail and display confirm removal page
			gotoMethod = newsTitleRemove(mapping,form,request, response);
			return mapping.findForward(gotoMethod);	
		} else if (atStep.equals("resource")) {
			gotoMethod = resourceRemoveConfirm(mapping,form,request,response);
			return mapping.findForward(gotoMethod);	
		} else if (atStep.equals("resourceremove")) {
			gotoMethod = resourceRemove(mapping,form,request,response);
			return mapping.findForward(gotoMethod);				
		} else {
			return mapping.findForward("mainDisplay");
		}
	}
	
	/**
	 * Method gotoEdit
	 * 		to decide to which edit page it must go to
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward gotoEdit(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		String gotoMethod = "";
		String atStep = request.getParameter("atstep");
		maintenanceForm.setPerformAction("EDIT");
		if (atStep.equals("txt")) {
			// select data to be displayed for edit
			gotoMethod = txtEdit(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
			
		} else if (atStep.equals("placement")) {
			gotoMethod = placementEdit(mapping,form,request, response);
			return mapping.findForward(gotoMethod);
			
		} else if (atStep.equals("vendor")) {
			// select data to be displayed for edit
			gotoMethod = vendorEdit(mapping,form,request, response);
 			return mapping.findForward(gotoMethod);
 			
		} else if (atStep.equals("subject")) {
			// select data to be displayed for edit
			gotoMethod = subjectEdit(mapping,form,request, response);
 			return mapping.findForward(gotoMethod);

		} else if (atStep.equals("highlight")) {
			// select data to be displayed for edit
			gotoMethod = highlightEdit(mapping,form,request, response);
 			return mapping.findForward(gotoMethod);
 			
		} else if (atStep.equals("newstitle")) {
			// select data to be displayed for edit
			gotoMethod = newsTitleEdit(mapping,form,request, response);
 			return mapping.findForward(gotoMethod);
 			
		} else if (atStep.equals("resource")) {
		
			// select data to be displayed for edit
			gotoMethod = resourceEdit(mapping,form,request, response);
 			return mapping.findForward(gotoMethod);
 			

		} else {
			return mapping.findForward("mainDisplay");
		}
	}
	
	/**
	 * Method gotoCancel
	 * 		to decide to which view page it must go to
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward gotoCancel(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());

		String atStep = request.getParameter("atstep");
		if (atStep.equals("txt")) {
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectTextCoverageList("FORMLIST"));
			return mapping.findForward("txtDisplay");
		} else if (atStep.equals("txtremove")) {
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectTextCoverageList("FORMLIST"));
			return mapping.findForward("txtDisplay");
		} else if (atStep.equals("placement")) {
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectPlacementList("FORMLIST",maintenanceForm.getResourcesPerPlacement(),false,false));
			return mapping.findForward("placementDisplay");
		} else if (atStep.equals("placementremove")) {
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectPlacementList("FORMLIST",maintenanceForm.getResourcesPerPlacement(),false,false));
			return mapping.findForward("placementDisplay");
		} else if (atStep.equals("vendor")) {
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectVendorList("FORMLIST",maintenanceForm.getAlpha()));
			return mapping.findForward("vendorDisplay");
		} else if (atStep.equals("vendorremove")) {
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectVendorList("FORMLIST",maintenanceForm.getAlpha()));
			return mapping.findForward("vendorDisplay");			
		} else if (atStep.equals("subject")) {
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectSubjectList("FORMLIST",maintenanceForm.getAlpha()));
			return mapping.findForward("subjectDisplay");
		} else if (atStep.equals("newstitle")) {
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectNewsTitleList("FORMLIST"));
			return mapping.findForward("newsTitleDisplay");
		} else if (atStep.equals("resourcelinkplacements")) {
			request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
			request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
			
			return mapping.findForward("resourceDisplay");
		} else if (atStep.equals("resourcelinksubjects")) {
			request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
			request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
			
			return mapping.findForward("resourceDisplay");
			
		} else if ((atStep.equals("resource"))||(atStep.equals("resourceconfirm"))||
				(atStep.equals("resourcelinksubjects"))||(atStep.equals("resourcelinkplacements"))||(atStep.equals("resourceview"))) {
			if ((null == maintenanceForm.getResourcesPerVendor())||(maintenanceForm.getResourcesPerVendor().equals("null"))) {
				maintenanceForm.setResourcesPerVendor("0");
			}
			if (null == maintenanceForm.getResourcesPerPlacement()) {
				maintenanceForm.setResourcesPerPlacement("0");
			}
			maintenanceForm.setDataList(dao.selectResourceList(maintenanceForm.getAlpha(),maintenanceForm.getResourcesPerPlacement(),maintenanceForm.getResourcesPerVendor()));
			request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
			request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
			return mapping.findForward("resourceDisplay");
		} else {
			return mapping.findForward("mainDisplay");
		}
	}
	
	/**
	 * Method gotoSave
	 * 		to decide which save method must be executed
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward gotoSave(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;

		String gotoMethod;
		String atStep = request.getParameter("atstep");
		String resourceStep = request.getParameter("resourcestep");
		//maintenanceForm.setPerformAction("ADD");
		if (atStep.equals("txt")) {
			gotoMethod = txtSave(mapping,form,request, response);
				
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("placement")) {
			gotoMethod = placementSave(mapping,form,request, response);
					
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("placementRanking")) {
			gotoMethod = placementRankingSave(mapping,form,request, response);
					
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("vendor")) {
			gotoMethod = vendorSave(mapping,form,request, response);
					
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("subject")) {
			gotoMethod = subjectSave(mapping,form,request, response);
					
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("highlight")) {
			gotoMethod = highlightSave(mapping,form,request, response);
					
			return mapping.findForward(gotoMethod);			
		} else if (atStep.equals("newstitle")) {
			gotoMethod = newsTitleSave(mapping,form,request, response);
			
			return mapping.findForward(gotoMethod);
		
		} else if (atStep.equals("resource")) {
			if (resourceStep.equals("step1")){
				gotoMethod = resourceLinkPlacements(mapping,form,request, response);
			} else if (resourceStep.equals("step2")) {
				gotoMethod = linkPlacementsVal(mapping,form,request, response);
			} else {
				gotoMethod = resourceSave(mapping,form,request, response);
				
				// reset vendor values
				/*
				maintenanceForm.setEnabled("");
				maintenanceForm.setId(0);
				maintenanceForm.setResourceDesc("");
				maintenanceForm.setResourceName("");
				maintenanceForm.setAccessNote("");
				maintenanceForm.setCdRom("");
				maintenanceForm.setOffCampusURL("");
				maintenanceForm.setOffCampusURL("");
				maintenanceForm.setVendorId(0);
				maintenanceForm.setTextId("");
				maintenanceForm.setCdRom("");
				maintenanceForm.setTrainingURL("");
				maintenanceForm.setNewsTitle("");
				maintenanceForm.setNewsURL("");
				
				maintenanceForm.setNewsTitleDesc("");
				*/
			}
			
			return mapping.findForward(gotoMethod);
			
		} else if (atStep.equals("resourcelinkplacements")) {
			gotoMethod = linkPlacementsVal(mapping,form,request, response);
			
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("resourcelinksubjects")) {
			gotoMethod = linkSubjectsVal(mapping,form,request, response);
			
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("resourceconfirm")) {
			gotoMethod = resourceSave(mapping,form,request, response);
			
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("resourceview")) {
			gotoMethod = resourceSavePlacementDates(mapping,form,request, response);
			
			return mapping.findForward(gotoMethod);
		} else {
			gotoMethod = "mainDisplay";
			return mapping.findForward(gotoMethod);
		}
	}
	
	/**
	 * Method gotoContinue
	 * 		to decide which save method must be executed
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward gotoContinue(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		ActionMessages messages = new ActionMessages();
		String gotoMethod;
		String atStep = request.getParameter("atstep");
		String resourceStep = request.getParameter("resourcestep");
		//maintenanceForm.setPerformAction("ADD");
		/*if (((maintenanceForm.getNewsTitle()==null)||(maintenanceForm.getNewsTitle().length()==0))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select News Title."));
			addErrors(request, messages);
			return mapping.findForward("resourceAdd");
		
		}*/
		if (atStep.equals("resource")) {
			if (resourceStep.equals("step1")){
				gotoMethod = resourceLinkPlacements(mapping,form,request, response);
			} else if (resourceStep.equals("step2")) {
				gotoMethod = linkPlacementsVal(mapping,form,request, response);
			} else {
				gotoMethod = resourceSave(mapping,form,request, response);
				
				// reset vendor values
				/*
				maintenanceForm.setEnabled("");
				maintenanceForm.setId(0);
				maintenanceForm.setResourceDesc("");
				maintenanceForm.setResourceName("");
				maintenanceForm.setAccessNote("");
				maintenanceForm.setCdRom("");
				maintenanceForm.setOffCampusURL("");
				maintenanceForm.setOffCampusURL("");
				maintenanceForm.setVendorId(0);
				maintenanceForm.setTextId("");
				maintenanceForm.setCdRom("");
				maintenanceForm.setTrainingURL("");
				maintenanceForm.setNewsTitle("");
				maintenanceForm.setNewsURL("");
				
				maintenanceForm.setNewsTitleDesc("");
				*/
			}
			
			return mapping.findForward(gotoMethod);
			
		} else if (atStep.equals("resourcelinkplacements")) {
			gotoMethod = linkPlacementsVal(mapping,form,request, response);
			
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("resourcelinksubjects")) {
			gotoMethod = linkSubjectsVal(mapping,form,request, response);
			
			return mapping.findForward(gotoMethod);
		} else if (atStep.equals("resourceconfirm")) {
			gotoMethod = resourceSave(mapping,form,request, response);
			
			return mapping.findForward(gotoMethod);
		} else {
			gotoMethod = "mainDisplay";
			return mapping.findForward(gotoMethod);
		}
	}
	
	/**
	 * Method Back
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward Back(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		String atStep = request.getParameter("atstep");
		
		if (atStep.equals("resourcelinkplacements")) {
			// select vendor options
			request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
			maintenanceForm.setVendorOptions(dao.selectVendorList("OPTIONLIST",""));
			request.setAttribute("vendorOptions", maintenanceForm.getVendorOptions());
			
			// select text content options
			maintenanceForm.setTxtOptions(dao.selectTextCoverageList("OPTIONLIST"));
			request.setAttribute("txtOptions", maintenanceForm.getTxtOptions());
			
			// select news letter title options
			maintenanceForm.setNewsTitleOptions(dao.selectNewsTitleList("OPTIONLIST"));
			request.setAttribute("newsTitleOptions", maintenanceForm.getNewsTitleOptions());
			
			maintenanceForm.setHighlightOptions(dao.selectHighLightList("OPTIONLIST"));
			request.setAttribute("highlightOptions", maintenanceForm.getHighlightOptions());
			
			return mapping.findForward("resourceAdd");
		} else if (atStep.equals("resourcelinksubjects")) {
			return mapping.findForward("resourceLinkPlacements");
		} else if (atStep.equals("resourceconfirm")) {
			return mapping.findForward("resourceLinkSubjects");
		} else if (atStep.equals("placementRanking")) {
			return mapping.findForward("placementDisplay");
		} else {
			maintenanceForm.setAlpha("");
			return mapping.findForward("mainDisplay");
		}
	}
	
	
	
	public String txtSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		// txt is mandatory
		if ((maintenanceForm.getTxtDesc()==null)||(maintenanceForm.getTxtDesc().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the Text coverage description"));
			addErrors(request, messages);
			return "txtAdd";
		}
		
		//Remove all leading and trailing spaces
		maintenanceForm.setTxtDesc(maintenanceForm.getTxtDesc());
		maintenanceForm.setTxtDesc(ltrim(maintenanceForm.getTxtDesc()));
		maintenanceForm.setTxtDesc(rtrim(maintenanceForm.getTxtDesc()));
		
		//replace ' with spaces
		maintenanceForm.setTxtDesc(maintenanceForm.getTxtDesc().replace('\'',' '));
		
		// txt must be unique
		ArrayList tmpList = maintenanceForm.getDataList();
		for (int i=0; i < tmpList.size(); i++) {
			TextForm record = (TextForm) tmpList.get(i);
			if ((record.getTxtDesc().equals(maintenanceForm.getTxtDesc()))&&
					(record.getTxtId() != maintenanceForm.getId())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Text coverage description already exist."));
				addErrors(request, messages);
				return "txtAdd";				
			}
		}
		
		// enabled is mandatory
		if ((maintenanceForm.getEnabled()==null)||(maintenanceForm.getEnabled().length()==0)||(maintenanceForm.getEnabled().equals("0"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose if text coverage is enabled."));
			addErrors(request, messages);
			return "txtAdd";
		}
		
		if (maintenanceForm.getPerformAction().equals("EDIT")) {
			dao.updateText(maintenanceForm.getId(),maintenanceForm.getTxtDesc(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Text record successfully updated."));
			addMessages(request, messages);
		} else {
			dao.insertText(maintenanceForm.getTxtDesc(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "New text record successfully added."));
			addMessages(request, messages);
		}
		maintenanceForm.setDataList(dao.selectTextCoverageList("FORMLIST"));
		maintenanceForm.resetFormbean(mapping, request);
		
		// reset vendor values
		maintenanceForm.setEnabled("");
		maintenanceForm.setId(0);
		maintenanceForm.setTxtDesc("");
		
		return "txtDisplay";
	}
	
	public String txtRemoveConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		ArrayList tmpList2 = new ArrayList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			TextForm record = (TextForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				
				// Check if record is in use, then record will be disabled and not removed!
				boolean inUse = dao.textCoverageInUse(record.getTxtId());
				
				record.setInUse(inUse);
				tmpList2.add(record);
			}
		}
		
		// Check if records were marked for removal
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for removal."));
			addErrors(request, messages);
			return "txtDisplay";						
		}
		
		maintenanceForm.setRemovalList(tmpList2);
	
		return "txtRemove";
	}
	
	public String txtRemove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		String removed = "";
		ArrayList tmpList = maintenanceForm.getRemovalList();
		for (int i=0; i < tmpList.size(); i++) {
			TextForm record = (TextForm) tmpList.get(i);
			dao.deleteText(record.getTxtId(), record.isInUse());
			if (removed.equals("")) {
				removed = record.getTxtDesc();
			} else {
				removed = removed+", "+record.getTxtDesc();
			}
		}
				
		maintenanceForm.setDataList(dao.selectTextCoverageList("FORMLIST"));
		maintenanceForm.resetFormbean(mapping, request);
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", removed+" successfully deleted/disabled"));
		addMessages(request, messages);
		
		return "txtDisplay";
	}
	
	public String txtEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			TextForm record = (TextForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				maintenanceForm.setId(record.getTxtId());
				maintenanceForm.setTxtDesc(record.getTxtDesc());
				maintenanceForm.setEnabled(record.getEnabled());
			}
		}
		
		// Check if records were marked for removal
		if (counter > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Only one record may be checked for edit."));
			addErrors(request, messages);
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectTextCoverageList("FORMLIST"));
			return "txtDisplay";						
		}
		
		// Check if records were marked for edit
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for edit."));
			addErrors(request, messages);
			return "txtDisplay";						
		}
		
		return "txtAdd";
	}
	
	public String subjectSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		// Subject is mandatory
		if ((maintenanceForm.getSubject()==null)||(maintenanceForm.getSubject().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the Subject"));
			addErrors(request, messages);
			return "subjectAdd";
		}
		
		//Remove all leading and trailing spaces
		maintenanceForm.setSubject(maintenanceForm.getSubject());
		maintenanceForm.setSubject(ltrim(maintenanceForm.getSubject()));
		maintenanceForm.setSubject(rtrim(maintenanceForm.getSubject()));
		
		//replace ' with spaces
		maintenanceForm.setSubject(maintenanceForm.getSubject().replace('\'',' '));
		
		// txt must be unique
		ArrayList tmpList = maintenanceForm.getDataList();
		for (int i=0; i < tmpList.size(); i++) {
			SubjectForm record = (SubjectForm) tmpList.get(i);
			if ((record.getSubject().equals(maintenanceForm.getSubject()))&&
					(record.getSubjectId() != maintenanceForm.getId())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Subject already exist."));
				addErrors(request, messages);
				return "subjectAdd";				
			}
		}
		
		// Enabled is mandatory
		if ((maintenanceForm.getEnabled()==null)||(maintenanceForm.getEnabled().length()==0)||(maintenanceForm.getEnabled().equals("0"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose if subject is enabled."));
			addErrors(request, messages);
			return "subjectAdd";
		}
		
		if (maintenanceForm.getPerformAction().equals("EDIT")) {
			dao.updateSubject(maintenanceForm.getId(),maintenanceForm.getSubject(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Subject record successfully updated."));
			addMessages(request, messages);
		} else {
			dao.insertSubject(maintenanceForm.getSubject(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "New Subject record successfully added."));
			addMessages(request, messages);
		}
		maintenanceForm.setDataList(dao.selectSubjectList("FORMLIST",maintenanceForm.getAlpha()));
		maintenanceForm.resetFormbean(mapping, request);
		
		// reset subject values
		maintenanceForm.setEnabled("");
		maintenanceForm.setId(0);
		maintenanceForm.setSubject("");

		
		return "subjectDisplay";
	}
	
	public String subjectEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			SubjectForm record = (SubjectForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				maintenanceForm.setId(record.getSubjectId());
				maintenanceForm.setSubject(record.getSubject());
				maintenanceForm.setEnabled(record.getEnabled());
			}
		}
		
		// Check if records were marked for removal
		if (counter > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Only one record may be checked for edit."));
			addErrors(request, messages);
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectSubjectList("FORMLIST",maintenanceForm.getAlpha()));
			return "subjectDisplay";						
		}
		
		// Check if records were marked for edit
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for edit."));
			addErrors(request, messages);
			return "subjectDisplay";						
		}
		
		return "subjectAdd";
	}
	
	public String highlightSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		// Highlight note is mandatory
		if ((maintenanceForm.getHighlight()==null)||(maintenanceForm.getHighlight().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the Highlight Note"));
			addErrors(request, messages);
			return "highlightAdd";
		}
		
		//Remove all leading and trailing spaces
		maintenanceForm.setHighlight(maintenanceForm.getHighlight());
		maintenanceForm.setHighlight(ltrim(maintenanceForm.getHighlight()));
		maintenanceForm.setHighlight(rtrim(maintenanceForm.getHighlight()));
		
		//replace ' with spaces
		maintenanceForm.setHighlight(maintenanceForm.getHighlight().replace('\'',' '));
		
		// highlight note must be unique
		ArrayList tmpList = maintenanceForm.getDataList();
		for (int i=0; i < tmpList.size(); i++) {
			highlightNoteForm record = (highlightNoteForm) tmpList.get(i);
			if ((record.getHighlight().equals(maintenanceForm.getHighlight()))&&
					(record.getHighId() != maintenanceForm.getId())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Highlight Note already exist."));
				addErrors(request, messages);
				return "highlightAdd";				
			}
		}
		
		// Enabled is mandatory
		if ((maintenanceForm.getEnabled()==null)||(maintenanceForm.getEnabled().length()==0)||(maintenanceForm.getEnabled().equals("0"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose if highlight note is enabled."));
			addErrors(request, messages);
			return "highlightAdd";
		}
		
		if (maintenanceForm.getPerformAction().equals("EDIT")) {
			dao.updateHighlight(maintenanceForm.getId(),maintenanceForm.getHighlight(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Highlight note record successfully updated."));
			addMessages(request, messages);
		} else {
			dao.insertHighlight(maintenanceForm.getHighlight(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "New Highlight note record successfully added."));
			addMessages(request, messages);
		}
		maintenanceForm.setDataList(dao.selectHighLightList("FORMLIST"));
		maintenanceForm.resetFormbean(mapping, request);
		
		// reset highlight note values
		maintenanceForm.setEnabled("");
		maintenanceForm.setId(0);
		maintenanceForm.setSubject("");

		
		return "highlightDisplay";
	}
	
	public String highlightEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			highlightNoteForm record = (highlightNoteForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				maintenanceForm.setId(record.getHighId());
				maintenanceForm.setHighlight(record.getHighlight());
				maintenanceForm.setEnabled(record.getEnabled());
			}
		}
		
		// Check if records were marked for removal
		if (counter > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Only one record may be checked for edit."));
			addErrors(request, messages);
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectHighLightList("FORMLIST"));
			return "highlightDisplay";						
		}
		
		// Check if records were marked for edit
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for edit."));
			addErrors(request, messages);
			return "highlightDisplay";						
		}
		
		return "highlightAdd";
	}
	
	public String resourceEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
		maintenanceForm.setHighlightOptions(dao.selectHighLightList("OPTIONLIST"));
		request.setAttribute("highlightOptions", maintenanceForm.getHighlightOptions());
	
		ArrayList tmpList = maintenanceForm.getDataList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			ResourceForm record = (ResourceForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				maintenanceForm.setId(record.getResourceId());
				maintenanceForm.setResourceName(record.getResourceName());
			}
		}
		
		// Check if records were marked for removal
		if (counter > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Only one record may be checked for edit."));
			addErrors(request, messages);
			maintenanceForm.resetFormbean(mapping, request);
			if ((null == maintenanceForm.getResourcesPerVendor())||(maintenanceForm.getResourcesPerVendor().equals("null"))) {
				maintenanceForm.setResourcesPerVendor("0");
			}
			if (null == maintenanceForm.getResourcesPerPlacement()) {
				maintenanceForm.setResourcesPerPlacement("0");
			}
			maintenanceForm.setDataList(dao.selectResourceList(maintenanceForm.getAlpha(),maintenanceForm.getResourcesPerPlacement(),maintenanceForm.getResourcesPerVendor()));
			return "resourceDisplay";						
		}
		
		// Check if records were marked for edit
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for edit."));
			addErrors(request, messages);
			return "resourceDisplay";						
		}
		
		// select vendor options
		maintenanceForm.setVendorOptions(dao.selectVendorList("OPTIONLIST",""));
		request.setAttribute("vendorOptions", maintenanceForm.getVendorOptions());
		
		// select text content options
		maintenanceForm.setTxtOptions(dao.selectTextCoverageList("OPTIONLIST"));
		request.setAttribute("txtOptions", maintenanceForm.getTxtOptions());
		
		// select news letter title options
		maintenanceForm.setNewsTitleOptions(dao.selectNewsTitleList("OPTIONLIST"));
		request.setAttribute("newsTitleOptions", maintenanceForm.getNewsTitleOptions());

		
		// select resource information for edit
		dao.selectResource(maintenanceForm);
		
		return "resourceAdd";
	}
	
	public ActionForward resourceView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
	
		ArrayList tmpList = maintenanceForm.getDataList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			ResourceForm record = (ResourceForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				maintenanceForm.setId(record.getResourceId());
				maintenanceForm.setResourceName(record.getResourceName());
			}
		}
		
		// Check if records were marked for view
		if (counter > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Only one record may be checked for edit."));
			addErrors(request, messages);
			return mapping.findForward("resourceDisplay");						
		}
		
		// Check if records were marked for view
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for view."));
			addErrors(request, messages);
			return mapping.findForward("resourceDisplay");						
		}
			
		// select resource information for view
		dao.selectResource(maintenanceForm);

		// select placements linked to e-resource
		maintenanceForm.setPlacementList(dao.selectResourcePlacements(maintenanceForm.getId(),"O"));

		// select subjects linked to e-resource
		maintenanceForm.setSubjectList(dao.selectResourceSubject(maintenanceForm.getId()));
		
		return mapping.findForward("resourceView");
	}
	
	public String subjectRemoveConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		ArrayList tmpList2 = new ArrayList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			SubjectForm record = (SubjectForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				
				// Check if record is in use, then record will be disabled and not removed!
				boolean inUse = dao.subjectInUse(record.getSubjectId());
				
				record.setInUse(inUse);
				tmpList2.add(record);
			}
		}
		
		// Check if records were marked for removal
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for removal."));
			addErrors(request, messages);
			return "subjectDisplay";						
		}
		
		maintenanceForm.setRemovalList(tmpList2);
	
		return "subjectRemove";
	}
	
	public String subjectRemove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		String removed = "";
		ArrayList tmpList = maintenanceForm.getRemovalList();
		for (int i=0; i < tmpList.size(); i++) {
			SubjectForm record = (SubjectForm) tmpList.get(i);
			dao.deleteSubject(record.getSubjectId(), record.isInUse());
			if (removed.equals("")) {
				removed = record.getSubject();
			} else {
				removed = removed+", "+record.getSubject();
			}
		}
				
		maintenanceForm.setDataList(dao.selectSubjectList("FORMLIST",maintenanceForm.getAlpha()));
		maintenanceForm.resetFormbean(mapping, request);
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", removed+" successfully deleted/disabled"));
		addMessages(request, messages);
		
		return "subjectDisplay";
	}
	
	public String highlightRemoveConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		ArrayList tmpList2 = new ArrayList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			highlightNoteForm record = (highlightNoteForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				
				// Check if record is in use, then record will be disabled and not removed!
				boolean inUse = dao.highlightInUse(record.getHighId());
				
				record.setInUse(inUse);
				tmpList2.add(record);
			}
		}
		
		// Check if records were marked for removal
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for delete."));
			addErrors(request, messages);
			return "highlightDisplay";						
		}
		
		maintenanceForm.setRemovalList(tmpList2);
	
		return "highlightRemove";
	}
	
	public String highlightRemove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		String removed = "";
		ArrayList tmpList = maintenanceForm.getRemovalList();
		for (int i=0; i < tmpList.size(); i++) {
			highlightNoteForm record = (highlightNoteForm) tmpList.get(i);
			dao.deleteHighlight(record.getHighId(), record.isInUse());
			if (removed.equals("")) {
				removed = record.getHighlight();
			} else {
				removed = removed+", "+record.getHighlight();
			}
		}
				
		maintenanceForm.setDataList(dao.selectHighLightList("FORMLIST"));
		maintenanceForm.resetFormbean(mapping, request);
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", removed+" successfully deleted/disabled"));
		addMessages(request, messages);
		
		return "highlightDisplay";
	}
	
	public String resourceRemoveConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
		
		ArrayList tmpList = maintenanceForm.getDataList();
		ArrayList tmpList2 = new ArrayList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			ResourceForm record = (ResourceForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;				
				tmpList2.add(record);
			}
		}
		
		// Check if records were marked for removal
		if (counter == 0) {
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for delete."));
			addErrors(request, messages);
			return "resourceDisplay";						
		}
		
		maintenanceForm.setRemovalList(tmpList2);
	
		return "resourceRemove";
	}
	
	public String resourceRemove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		String removed = "";
		ArrayList tmpList = maintenanceForm.getRemovalList();
		for (int i=0; i < tmpList.size(); i++) {
			ResourceForm record = (ResourceForm) tmpList.get(i);
			dao.deleteResource(record.getResourceId());
			if (removed.equals("")) {
				removed = record.getResourceName();
			} else {
				removed = removed+", "+record.getResourceName();;
			}
		}
				
		//maintenanceForm.setDataList(dao.selectSubjectList("FORMLIST",maintenanceForm.getAlpha()));
		maintenanceForm.resetFormbean(mapping, request);
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", removed+" successfully deleted"));
		addMessages(request, messages);
		
		if ((null == maintenanceForm.getResourcesPerVendor())||(maintenanceForm.getResourcesPerVendor().equals("null"))) {
			maintenanceForm.setResourcesPerVendor("0");
		}
		if (null == maintenanceForm.getResourcesPerPlacement()) {
			maintenanceForm.setResourcesPerPlacement("0");
		}
		maintenanceForm.setDataList(dao.selectResourceList(maintenanceForm.getAlpha(),maintenanceForm.getResourcesPerPlacement(),maintenanceForm.getResourcesPerVendor()));

		
		request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
		
		return "resourceDisplay";
	}
	
	public String newsTitleRemove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		String removed = "";
		ArrayList tmpList = maintenanceForm.getRemovalList();
		for (int i=0; i < tmpList.size(); i++) {
			NewsTitleForm record = (NewsTitleForm) tmpList.get(i);
			dao.deleteNewsTitle(record.getNewsTitleId(), record.isInUse());
			if (removed.equals("")) {
				removed = record.getNewsTitleDesc();
			} else {
				removed = removed+", "+record.getNewsTitleDesc();
			}
		}
				
		maintenanceForm.setDataList(dao.selectNewsTitleList("FORMLIST"));
		maintenanceForm.resetFormbean(mapping, request);
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", removed+" successfully deleted/disabled"));
		addMessages(request, messages);
		
		return "newsTitleDisplay";
	}
	
	public String newsTitleSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		// Subject is mandatory
		if ((maintenanceForm.getNewsTitleDesc()==null)||(maintenanceForm.getNewsTitleDesc().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the News Letter Title"));
			addErrors(request, messages);
			return "newsTitleAdd";
		}
		
		//Remove all leading and trailing spaces
		maintenanceForm.setNewsTitleDesc(maintenanceForm.getNewsTitleDesc());
		maintenanceForm.setNewsTitleDesc(ltrim(maintenanceForm.getNewsTitleDesc()));
		maintenanceForm.setNewsTitleDesc(rtrim(maintenanceForm.getNewsTitleDesc()));
		
		//replace ' with spaces
		maintenanceForm.setNewsTitleDesc(maintenanceForm.getNewsTitleDesc().replace('\'',' '));
		
		// txt must be unique
		ArrayList tmpList = maintenanceForm.getDataList();
		for (int i=0; i < tmpList.size(); i++) {
			NewsTitleForm record = (NewsTitleForm) tmpList.get(i);
			if ((record.getNewsTitleDesc().equals(maintenanceForm.getNewsTitleDesc()))&&
					(record.getNewsTitleId() != maintenanceForm.getId())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "News Letter Title already exist."));
				addErrors(request, messages);
				return "newsTitleAdd";				
			}
		}
		
		// enabled is mandatory
		if ((maintenanceForm.getEnabled()==null)||(maintenanceForm.getEnabled().length()==0)||(maintenanceForm.getEnabled().equals("0"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose if news title is enabled."));
			addErrors(request, messages);
			return "newsTitleAdd";
		}
		
		if (maintenanceForm.getPerformAction().equals("EDIT")) {
			dao.updateNewsTitle(maintenanceForm.getId(),maintenanceForm.getNewsTitleDesc(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "News Letter Title record successfully updated."));
			addMessages(request, messages);
		} else {
			dao.insertNewsTitle(maintenanceForm.getNewsTitleDesc(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "New News Letter Title record successfully added."));
			addMessages(request, messages);
		}
		maintenanceForm.setDataList(dao.selectNewsTitleList("FORMLIST"));
		maintenanceForm.resetFormbean(mapping, request);
		
		// reset vendor values
		maintenanceForm.setEnabled("");
		maintenanceForm.setId(0);
		maintenanceForm.setNewsTitleDesc("");
		
		return "newsTitleDisplay";
	}
	
	public String newsTitleEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			NewsTitleForm record = (NewsTitleForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				maintenanceForm.setId(record.getNewsTitleId());
				maintenanceForm.setNewsTitleDesc(record.getNewsTitleDesc());
				maintenanceForm.setEnabled(record.getEnabled());
			}
		}
		
		// Check if records were marked for removal
		if (counter > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Only one record may be checked for edit."));
			addErrors(request, messages);
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectNewsTitleList("FORMLIST"));
			return "newsTitleDisplay";						
		}
		
		// Check if records were marked for edit
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for edit."));
			addErrors(request, messages);
			return "newsTitleDisplay";						
		}
		
		return "newsTitleAdd";
	}
	
	public String newsTitleRemoveConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		ArrayList tmpList2 = new ArrayList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			NewsTitleForm record = (NewsTitleForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				
				// Check if record is in use, then record will be disabled and not removed!
				boolean inUse = dao.newsTitleInUse(record.getNewsTitleId());
				
				record.setInUse(inUse);
				tmpList2.add(record);
			}
		}
		
		// Check if records were marked for removal
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for delete."));
			addErrors(request, messages);
			return "newsTitleDisplay";						
		}
		
		maintenanceForm.setRemovalList(tmpList2);
	
		return "newsTitleRemove";
	}
	
	public String placementSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		// placement desc is mandatory
		if ((maintenanceForm.getPlacement()==null)||(maintenanceForm.getPlacement().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the Placement name/description"));
			addErrors(request, messages);
			return "placementAdd";
		}
		
		//Remove all leading and trailing spaces
		maintenanceForm.setPlacement(maintenanceForm.getPlacement());
		maintenanceForm.setPlacement(ltrim(maintenanceForm.getPlacement()));
		maintenanceForm.setPlacement(rtrim(maintenanceForm.getPlacement()));
		
		//replace ' with spaces
		maintenanceForm.setPlacement(maintenanceForm.getPlacement().replace('\'',' '));
		
		// placement must be unique
		ArrayList tmpList = maintenanceForm.getDataList();
		for (int i=0; i < tmpList.size(); i++) {
			PlacementForm record = (PlacementForm) tmpList.get(i);
			if ((record.getPlacement().equals(maintenanceForm.getPlacement()))&&
					(record.getPlacementId() != maintenanceForm.getId())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Placement already exist."));
				addErrors(request, messages);
				return "placementAdd";				
			}
		}
		
		// enabled is mandatory
		if ((maintenanceForm.getEnabled()==null)||(maintenanceForm.getEnabled().length()==0)||(maintenanceForm.getEnabled().equals("0"))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose if placement is enabled."));
			addErrors(request, messages);
			return "placementAdd";
		}
		
		if (maintenanceForm.getPerformAction().equals("EDIT")) {
			dao.updatePlacement(maintenanceForm.getId(),maintenanceForm.getPlacement(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Placement record successfully updated."));
			addMessages(request, messages);
		} else {
			dao.insertPlacement(maintenanceForm.getPlacement(), maintenanceForm.getEnabled());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "New Placement record successfully added."));
			addMessages(request, messages);
		}
		maintenanceForm.setDataList(dao.selectPlacementList("FORMLIST",maintenanceForm.getResourcesPerPlacement(),false,false));
		maintenanceForm.resetFormbean(mapping, request);
		
		// reset placement values
		maintenanceForm.setEnabled("");
		maintenanceForm.setId(0);
		maintenanceForm.setPlacement("");
		
		return "placementDisplay";
	}

	public String placementRankingSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		for (int i=0; i < tmpList.size(); i++) {
			PlacementForm record = (PlacementForm) tmpList.get(i);
			if (!record.getPlacementRank().equals("0")) {
				dao.updatePlacementRanking(i,record.getPlacementId());
			}
		}
		
		maintenanceForm.setDataList(null);
		if ((null == maintenanceForm.getResourcesPerVendor())||(maintenanceForm.getResourcesPerVendor().equals("null"))) {
			maintenanceForm.setResourcesPerVendor("0");
		}
		if (null == maintenanceForm.getResourcesPerPlacement()) {
			maintenanceForm.setResourcesPerPlacement("0");
		}
		maintenanceForm.setDataList(dao.selectPlacementList("FORMLIST",maintenanceForm.getResourcesPerPlacement(),false,false));
		maintenanceForm.resetFormbean(mapping, request);
		
		// reset placement values
		maintenanceForm.setEnabled("");
		maintenanceForm.setId(0);
		maintenanceForm.setPlacement("");
		
		return "placementDisplay";
	}

	
	public String placementRemoveConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		ArrayList tmpList2 = new ArrayList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			PlacementForm record = (PlacementForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				
				// Check if record is in use, then record will be disabled and not removed!
				boolean inUse = dao.placementInUse(record.getPlacementId());
				
				record.setInUse(inUse);
				tmpList2.add(record);
			}
		}
		
		// Check if records were marked for removal
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for delete."));
			addErrors(request, messages);
			return "placementDisplay";						
		}
		
		maintenanceForm.setRemovalList(tmpList2);
	
		return "placementRemove";
	}
	
	public String placementRemove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		String removed = "";
		ArrayList tmpList = maintenanceForm.getRemovalList();
		for (int i=0; i < tmpList.size(); i++) {
			PlacementForm record = (PlacementForm) tmpList.get(i);
			dao.deletePlacement(record.getPlacementId(), record.isInUse());
			if (removed.equals("")) {
				removed = record.getPlacement();
			} else {
				removed = removed+", "+record.getPlacement();
			}
		}
				
		maintenanceForm.setDataList(dao.selectPlacementList("FORMLIST",maintenanceForm.getResourcesPerPlacement(),false,false));
		maintenanceForm.resetFormbean(mapping, request);
	
		messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", removed+" successfully deleted/disabled"));
		addMessages(request, messages);
		return "placementDisplay";	
	}
	
	public String placementEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		
		ArrayList tmpList = maintenanceForm.getDataList();
		int counter = 0;
		for (int i=0; i < tmpList.size(); i++) {
			PlacementForm record = (PlacementForm) tmpList.get(i);
			if (record.isChecked()) {
				counter++;
				maintenanceForm.setId(record.getPlacementId());
				maintenanceForm.setPlacement(record.getPlacement());
				maintenanceForm.setEnabled(record.getEnabled());
			}
		}
		
		// Check if records were marked for edit
		if (counter > 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Only one record may be checked for edit."));
			addErrors(request, messages);
			maintenanceForm.resetFormbean(mapping, request);
			maintenanceForm.setDataList(dao.selectPlacementList("FORMLIST",maintenanceForm.getResourcesPerPlacement(),false,false));
			return "placementDisplay";						
		}
		
		// Check if records were marked for edit
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "No records were selected for edit."));
			addErrors(request, messages);
			return "placementDisplay";						
		}
		
		return "placementAdd";
	}
	
	public String resourceLinkPlacements(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		request.setAttribute("vendorOptions", maintenanceForm.getVendorOptions());
		request.setAttribute("txtOptions", maintenanceForm.getTxtOptions());
		request.setAttribute("newsTitleOptions", maintenanceForm.getNewsTitleOptions());
		request.setAttribute("highlightOptions",dao.selectHighLightList("OPTIONLIST"));
		
		if (maintenanceForm.getResourceName()!=null) {
			//Remove all leading and trailing spaces
			maintenanceForm.setResourceName(maintenanceForm.getResourceName());
			maintenanceForm.setResourceName(ltrim(maintenanceForm.getResourceName()));
			maintenanceForm.setResourceName(rtrim(maintenanceForm.getResourceName()));
			//replace ' with spaces
			maintenanceForm.setResourceName(maintenanceForm.getResourceName().replace('\'',' '));
		}
		
		// resource name is mandatory
		if ((maintenanceForm.getResourceName()==null)||(maintenanceForm.getResourceName().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the Subject Database name"));
			addErrors(request, messages);
			return "resourceAdd";
		} else if (maintenanceForm.getResourceName().length()> 255) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The Subject Database name may not be more than 255 characters ("+maintenanceForm.getResourceName().substring(0,254)+")"));
			addErrors(request, messages);
			return "resourceAdd";
		}
		
	
		// for add check that resource name must be unique
		if (!maintenanceForm.getPerformAction().equals("EDIT")) {
			if (dao.selectResourceUnique(maintenanceForm.getResourceName()) == false) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Subject Database with this name already exist."));
				addErrors(request, messages);
				return "resourceAdd";	
			}
		}
		
		if (maintenanceForm.getResourceDesc()!=null) {
			//Remove all leading and trailing spaces
			maintenanceForm.setResourceDesc(ltrim(maintenanceForm.getResourceDesc()));
			maintenanceForm.setResourceDesc(rtrim(maintenanceForm.getResourceDesc()));
					//replace ' with spaces
			maintenanceForm.setResourceDesc(maintenanceForm.getResourceDesc().replace('\'',' '));
		}
		
		// resource description is mandatory
		if ((maintenanceForm.getResourceDesc()==null)||(maintenanceForm.getResourceDesc().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the Subject Database Description"));
			addErrors(request, messages);
			return "resourceAdd";
		} else if (maintenanceForm.getResourceDesc().length() > 255) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "The Subject Database Description may not be more than 255 characters. ("+maintenanceForm.getResourceDesc().substring(0, 254)+")"));
			addErrors(request, messages);
			return "resourceAdd";
		}
		
		if (maintenanceForm.getOnCampusURL()!=null) {
			//Remove all leading and trailing spaces
			maintenanceForm.setOnCampusURL(ltrim(maintenanceForm.getOnCampusURL()));
			maintenanceForm.setOnCampusURL(rtrim(maintenanceForm.getOnCampusURL()));
					//replace ' with spaces
			maintenanceForm.setOnCampusURL(maintenanceForm.getOnCampusURL().replace('\'',' '));	
		}
		if (maintenanceForm.getOffCampusURL()!=null) {
			//Remove all leading and trailing spaces
			maintenanceForm.setOffCampusURL(ltrim(maintenanceForm.getOffCampusURL()));
			maintenanceForm.setOffCampusURL(rtrim(maintenanceForm.getOffCampusURL()));
					//replace ' with spaces
			maintenanceForm.setOffCampusURL(maintenanceForm.getOffCampusURL().replace('\'',' '));	
		}
		
		// offcampus OR oncampus URL is mandatory
		if (((maintenanceForm.getOnCampusURL()==null)||(maintenanceForm.getOnCampusURL().length()==0))&&
				((maintenanceForm.getOffCampusURL()==null)||(maintenanceForm.getOffCampusURL().length()==0))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the On-campus URL OR the Off-campus URL"));
			addErrors(request, messages);
			return "resourceAdd";
		}
		
		if (!((maintenanceForm.getOnCampusURL()==null)||(maintenanceForm.getOnCampusURL().length()==0))) {
			//Validate onCampusURL
			if (maintenanceForm.getOnCampusURL().length() < 7) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid On-campus URL, please check."));
				addErrors(request, messages);
				return "resourceAdd";
			} else {
				boolean onURLValid = urlValidator(maintenanceForm.getOnCampusURL());
				if (onURLValid == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid On-campus URL, please check."));
					addErrors(request, messages);
					return "resourceAdd";
				}
				if (maintenanceForm.getOnCampusURL().length() > 255) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The On-campus URL may not be more than 255 characters. ("+maintenanceForm.getOnCampusURL().substring(0, 254)+")"));
					addErrors(request, messages);
					return "resourceAdd";
				}
			}
		} 
		 
		if (!((maintenanceForm.getOffCampusURL()==null)||(maintenanceForm.getOffCampusURL().length()==0))) {		
			//Validate offCampusURL
			if (maintenanceForm.getOffCampusURL().length() < 7) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid Off-campus URL, please check."));
				addErrors(request, messages);
				return "resourceAdd";
			} else {
				boolean offURLValid = urlValidator(maintenanceForm.getOffCampusURL());
				if (offURLValid == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid Off-campus URL, please check."));
					addErrors(request, messages);
					return "resourceAdd";
				}
				if (maintenanceForm.getOffCampusURL().length() > 255) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "The Off-campus URL may not be more than 255 characters. ("+maintenanceForm.getOffCampusURL().substring(0,254)+")"));
					addErrors(request, messages);
					return "resourceAdd";
				}
			}
		}
		
		// text content is mandatory
		if ((maintenanceForm.getTextId() ==null)||(maintenanceForm.getTextId().length()==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please choose the Full Text Content."));
			addErrors(request, messages);
			return "resourceAdd";
		}
		
		if (maintenanceForm.getCdRom() != null) {		
			//Remove all leading and trailing spaces
			maintenanceForm.setCdRom(ltrim(maintenanceForm.getCdRom()));
			maintenanceForm.setCdRom(rtrim(maintenanceForm.getCdRom()));
					//replace ' with spaces
			maintenanceForm.setCdRom(maintenanceForm.getCdRom().replace('\'',' '));
			
			if (maintenanceForm.getCdRom().length() > 255) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "CD Rom contact info may not be more than 255 characters. ("+maintenanceForm.getCdRom().substring(0,254)+")"));
				addErrors(request, messages);
				return "resourceAdd";	
			}
		} 
		
		if (maintenanceForm.getTrainingURL()!=null) {
			//Remove all leading and trailing spaces
			maintenanceForm.setTrainingURL(ltrim(maintenanceForm.getTrainingURL()));
			maintenanceForm.setTrainingURL(rtrim(maintenanceForm.getTrainingURL()));
					//replace ' with spaces
			maintenanceForm.setTrainingURL(maintenanceForm.getTrainingURL().replace('\'',' '));
		}
		
		if (!((maintenanceForm.getTrainingURL()==null)||(maintenanceForm.getTrainingURL().length()==0))) {
			//Validate training url
			if (maintenanceForm.getTrainingURL().length() < 7) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid Training URL, please check."));
				addErrors(request, messages);
				return "resourceAdd";
			} else {
				boolean URLValid = urlValidator(maintenanceForm.getTrainingURL());
				if (URLValid == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid Training URL, please check."));
					addErrors(request, messages);
					return "resourceAdd";
				}
				if (maintenanceForm.getTrainingURL().length() > 255) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Training URL may not be more than 255 characters. ("+maintenanceForm.getTrainingURL().substring(0, 254)+")"));
					addErrors(request, messages);
					return "resourceAdd";
				}
			}
		}
		
		if (maintenanceForm.getRefManagementURL()!=null) {
			//Remove all leading and trailing spaces
			maintenanceForm.setRefManagementURL(ltrim(maintenanceForm.getRefManagementURL()));
			maintenanceForm.setRefManagementURL(rtrim(maintenanceForm.getRefManagementURL()));
					//replace ' with spaces
			maintenanceForm.setRefManagementURL(maintenanceForm.getRefManagementURL().replace('\'',' '));
		}
		if (!((maintenanceForm.getRefManagementURL()==null)||(maintenanceForm.getRefManagementURL().length()==0))) {
			//Validate training url
			if (maintenanceForm.getRefManagementURL().length() < 7) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid Reference Management URL, please check."));
				addErrors(request, messages);
				return "resourceAdd";
			} else {
				boolean URLValid = urlValidator(maintenanceForm.getRefManagementURL());
				if (URLValid == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid Reference Management URL, please check."));
					addErrors(request, messages);
					return "resourceAdd";
				}
				
				if (maintenanceForm.getRefManagementURL().length() > 255) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Reference Management URL may not be more than 255 characters. ("+maintenanceForm.getRefManagementURL().substring(0, 254)+")"));
					addErrors(request, messages);
					return "resourceAdd";
				}
			}
		}

		// if newstitle was selected then news url must be entered
		if (!((maintenanceForm.getNewsTitle()==null)||(maintenanceForm.getNewsTitle().length()==0))) {
			if (maintenanceForm.getNewsURL()!=null) {
				maintenanceForm.setNewsURL(ltrim(maintenanceForm.getNewsURL()));
				maintenanceForm.setNewsURL(rtrim(maintenanceForm.getNewsURL()));
						//replace ' with spaces
				maintenanceForm.setNewsURL(maintenanceForm.getNewsURL().replace('\'',' '));
			}
			if ((maintenanceForm.getNewsURL()==null)||(maintenanceForm.getNewsURL().length()==0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "If a News Title was selected then the News URL must be completed."));
				addErrors(request, messages);
				return "resourceAdd";
			} else {
				//Validate news url
				if (maintenanceForm.getNewsURL().length() < 7) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid News URL, please check."));
					addErrors(request, messages);
					return "resourceAdd";
				} else {
					boolean URLValid = urlValidator(maintenanceForm.getNewsURL());
					if (URLValid == false) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Invalid News URL, please check."));
						addErrors(request, messages);
						return "resourceAdd";
					}
					if (maintenanceForm.getNewsURL().length()>255) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "News URL may not be more than 255 characters. ("+maintenanceForm.getNewsURL().substring(0, 254)+")"));
						addErrors(request, messages);
						return "resourceAdd";
					}
				}
			}
		} else {
			maintenanceForm.setNewsTitleDesc("");
			maintenanceForm.setNewsURL("");
		}
		
		if (maintenanceForm.getAccessNote() != null) {
			maintenanceForm.setAccessNote(ltrim(maintenanceForm.getAccessNote()));
			maintenanceForm.setAccessNote(rtrim(maintenanceForm.getAccessNote()));
					//replace ' with spaces
			maintenanceForm.setAccessNote(maintenanceForm.getAccessNote().replace('\'',' '));
		} 
		if (maintenanceForm.getAccessNote().length() >500) {
			System.out.println("accessNote.length "+maintenanceForm.getAccessNote().length());
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Access note may not be more than 500 characters. ("+maintenanceForm.getAccessNote().substring(0, 254)+")"));
			addErrors(request, messages);
			return "resourceAdd";
		}
		
		// if password required is yes then login and password must be completed
		if (maintenanceForm.getPasswordExist().equals("Y")) {
			if ((null ==maintenanceForm.getLogin())||(maintenanceForm.getLogin().length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Password required field is set to yes, please enter the login"));
				addErrors(request, messages);
				return "resourceAdd";
			}
			if ((null ==maintenanceForm.getPassword())||(maintenanceForm.getPassword().length() == 0)) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Password required field is set to yes, please enter the password"));
				addErrors(request, messages);
				return "resourceAdd";
			}
		}
		
		if (maintenanceForm.getAlert()!=null) {
			//Remove all leading and trailing spaces
			maintenanceForm.setAlert(ltrim(maintenanceForm.getAlert()));
			maintenanceForm.setAlert(rtrim(maintenanceForm.getAlert()));
					//replace ' with spaces
			maintenanceForm.setAlert(maintenanceForm.getAlert().replace('\'',' '));	
		}
		if (!((maintenanceForm.getAlert()==null)||(maintenanceForm.getAlert().length()==0))) {
			//Validate alert url
			if (maintenanceForm.getAlert().length() < 7) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Invalid Alerts URL, please check."));
				addErrors(request, messages);
				return "resourceAdd";
			} else {
				boolean URLValid = urlValidator(maintenanceForm.getAlert());
				if (URLValid == false) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Invalid Alerts URL, please check."));
					addErrors(request, messages);
					return "resourceAdd";
				}
				if (maintenanceForm.getAlert().length() > 255) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Alerts URL may not be more than 255 characters."));
					addErrors(request, messages);
					return "resourceAdd";
				}
			}
		}

		// select placements
		maintenanceForm.setPlacementList(dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("placementList", maintenanceForm.getPlacementList());
		
		return "resourceLinkPlacements";
	}
	
	public String resourceLinkSubjects(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		
		// select subjects
		maintenanceForm.setSubjectList(dao.selectSubjectList("OPTIONLIST",""));
		request.setAttribute("subjectList", maintenanceForm.getSubjectList());
		
		return"resourceLinkSubjects";
	}
	
	public String resourceSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;	
		
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		request.setAttribute("vendorOptions", maintenanceForm.getVendorOptions());
		request.setAttribute("txtOptions", maintenanceForm.getTxtOptions());
		request.setAttribute("newsTitleOptions", maintenanceForm.getNewsTitleOptions());
		request.setAttribute("highlightOptions", maintenanceForm.getHighlightOptions());
	
		// Placements is mandatory
		String[] selectedPlacements = maintenanceForm.getSelectedPlacements();
		int counter = 0;

		if (selectedPlacements != null) {
			for (int i = 0; i < selectedPlacements.length; i++) {
				counter++;
			}
			if (counter <= 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "At least one Placement must be selected, click on Link Placements to add placements."));
				addErrors(request, messages);
				return "resourceAdd";	
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "At least one Placement must be selected, click on Link Placement to add placement."));
			addErrors(request, messages);
			return "resourceAdd";
		}
		
		// Subjects is mandatory
		String[] selectedSubjects = maintenanceForm.getSelectedSubjects();
		counter = 0;


		/* Validate that atleast one subject was selected */
		if (selectedSubjects != null) {
			for (int i = 0; i < selectedSubjects.length; i++) {
				counter++;
			}
			if (counter <= 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "At least one Subject must be selected, click on Link Subjects to add subjects."));
				addErrors(request, messages);
				return "resourceAdd";	
			}
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "At least one Subject must be selected, click on Link Subjects to add subjects."));
			addErrors(request, messages);
			return "resourceAdd";
		}

		if (maintenanceForm.getPerformAction().equals("EDIT")) {
			// update record
			dao.updateResource(maintenanceForm.getId(),maintenanceForm.getResourceName(),maintenanceForm.getResourceDesc(),
					maintenanceForm.getOnCampusURL(),maintenanceForm.getOffCampusURL(),
					maintenanceForm.getVendorId(),maintenanceForm.getTextId(),maintenanceForm.getCdRom(),
					maintenanceForm.getTrainingURL(),maintenanceForm.getNewsTitle(),
					maintenanceForm.getNewsURL(), maintenanceForm.getAccessNote(),
					maintenanceForm.getPasswordExist(),maintenanceForm.getEnabled(),
					maintenanceForm.getSelectedPlacements(),maintenanceForm.getSelectedSubjects(),maintenanceForm.getLogin(),
					maintenanceForm.getPassword(),maintenanceForm.getHighlight(),maintenanceForm.getAlert(),maintenanceForm.getRefManagementURL());
		} else {
			// insert new subject database record
			dao.insertResource(maintenanceForm.getResourceName(),maintenanceForm.getResourceDesc(),
					maintenanceForm.getOnCampusURL(),maintenanceForm.getOffCampusURL(),
					maintenanceForm.getVendorId(),maintenanceForm.getTextId(),maintenanceForm.getCdRom(),
					maintenanceForm.getTrainingURL(),maintenanceForm.getNewsTitle(),
					maintenanceForm.getNewsURL(), maintenanceForm.getAccessNote(),
					maintenanceForm.getPasswordExist(),maintenanceForm.getEnabled(),
					maintenanceForm.getSelectedPlacements(),maintenanceForm.getSelectedSubjects(),
					maintenanceForm.getLogin(),maintenanceForm.getPassword(),maintenanceForm.getHighlight(),maintenanceForm.getAlert(),
					maintenanceForm.getRefManagementURL());
		}
		
		request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
		if ((null == maintenanceForm.getResourcesPerVendor())||(maintenanceForm.getResourcesPerVendor().equals("null"))) {
			maintenanceForm.setResourcesPerVendor("0");
		}
		if (null == maintenanceForm.getResourcesPerPlacement()) {
			maintenanceForm.setResourcesPerPlacement("0");
		}
		maintenanceForm.setDataList(dao.selectResourceList(maintenanceForm.getAlpha(),maintenanceForm.getResourcesPerPlacement(),maintenanceForm.getResourcesPerVendor()));
		maintenanceForm.resetFormbean(mapping, request);

		return "resourceDisplay";
	}
	
	public String resourceSavePlacementDates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;	
		
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		int syear = 0;
		int smonth = 0;
		int sday = 0;
		int eyear = 0;
		int emonth = 0;
		int eday = 0;
	
		ArrayList placementList = maintenanceForm.getPlacementList();
		for (int i=0; i < placementList.size(); i++) {			
			PlacementForm record = (PlacementForm) placementList.get(i);
			if ((null != record.getFromDate())&&(!record.getFromDate().equals(""))) {
               String[] tmpFrom = record.getFromDate().split("/");
               // validate day
               try {
       			 sday = Integer.parseInt(tmpFrom[0]);
       		   } catch (NumberFormatException e) {
       			  messages.add(ActionMessages.GLOBAL_MESSAGE,
       					new ActionMessage("message.generalmessage", record.getPlacement()+": From date day must be number."));
       			  addErrors(request, messages);

         			request.setAttribute("maintenanceForm", maintenanceForm);
         			return "resourceView";
        		}
       		   
       		   // validate month
               try {
       			 smonth = Integer.parseInt(tmpFrom[1]);
       		   } catch (NumberFormatException e) {
       			  messages.add(ActionMessages.GLOBAL_MESSAGE,
       					new ActionMessage("message.generalmessage", record.getPlacement()+": From date month must be number."));
       			  addErrors(request, messages);

         			request.setAttribute("maintenanceForm", maintenanceForm);
         			return "resourceView";
       		   }
       		   if ((smonth > 12)||(smonth <0)) {
        			messages.add(ActionMessages.GLOBAL_MESSAGE,
         					new ActionMessage("message.generalmessage", record.getPlacement()+": From date month must be between 1 and 12."));
        			addErrors(request, messages);

         			request.setAttribute("maintenanceForm", maintenanceForm);
         			return "resourceView";  
       		   }
       		   // validate month
               try {
       			 syear = Integer.parseInt(tmpFrom[2]);
       		   } catch (NumberFormatException e) {
       			  messages.add(ActionMessages.GLOBAL_MESSAGE,
       					new ActionMessage("message.generalmessage", record.getPlacement()+": From date year must be number."));
       			  addErrors(request, messages);

         			request.setAttribute("maintenanceForm", maintenanceForm);
         			return "resourceView";
       		   }
       		   
       		if (smonth == 1||smonth == 3
    				||smonth == 5||smonth == 7||smonth == 8
    				||smonth == 10||smonth == 12) {
    			if (sday > 31) {
    				messages.add(ActionMessages.GLOBAL_MESSAGE,
    						new ActionMessage("message.generalmessage", record.getPlacement()+": Invalid day entered for from date."));
    				addErrors(request, messages);

         			request.setAttribute("maintenanceForm", maintenanceForm);
         			return "resourceView";  
    			}
    		} else if (smonth == 4||smonth == 6
    				||smonth == 9||smonth == 11) {
    			if (sday > 30) {
    				messages.add(ActionMessages.GLOBAL_MESSAGE,
    						new ActionMessage("message.generalmessage", record.getPlacement()+": Invalid day entered for from date."));
    				addErrors(request, messages);

         			request.setAttribute("maintenanceForm", maintenanceForm);
         			return "resourceView";  
    			}
    		} else if (smonth == 2) {
    			if (syear % 4 == 0) {
    				if (sday > 29) {
    					messages.add(ActionMessages.GLOBAL_MESSAGE,
    							new ActionMessage("message.generalmessage", record.getPlacement()+": Invalid day entered for from date."));
    					addErrors(request, messages);

             			request.setAttribute("maintenanceForm", maintenanceForm);
             			return "resourceView"; 
    				}
    			} else {
    				if (sday > 28) {
    					messages.add(ActionMessages.GLOBAL_MESSAGE,
    							new ActionMessage("message.generalmessage", record.getPlacement()+": Invalid day entered for from date."));
    					addErrors(request, messages);

             			request.setAttribute("maintenanceForm", maintenanceForm);
             			return "resourceView"; 
    				}
    			}
    		} // end validate start date
       		   
       		   
       		   if (smonth <= 9) {
       			   record.setFromDate("0"+smonth+"/");
       		   } else {
       			   record.setFromDate(smonth+"/");
       		   }
       		   if (sday <=9) {
       			   record.setFromDate(record.getFromDate()+"0"+sday+"/"+syear);
       		   } else {
       			   record.setFromDate(record.getFromDate()+sday+"/"+syear);
       		   }
			}
		    if (null != record.getEndDate()&&(!record.getEndDate().equals(""))) {
			   String[] tmpEnd = record.getEndDate().split("/");
	               // validate day
	               try {
	       			 eday = Integer.parseInt(tmpEnd[0]);
	       		   } catch (NumberFormatException e) {
	       			  messages.add(ActionMessages.GLOBAL_MESSAGE,
	       					new ActionMessage("message.generalmessage", record.getPlacement()+": End date day must be number."));
	       			  addErrors(request, messages);

	         			request.setAttribute("maintenanceForm", maintenanceForm);
	         			return "resourceView";
	        		}
	       		   
	       		   // validate month
	               try {
	       			 emonth = Integer.parseInt(tmpEnd[1]);
	       		   } catch (NumberFormatException e) {
	       			  messages.add(ActionMessages.GLOBAL_MESSAGE,
	       					new ActionMessage("message.generalmessage", record.getPlacement()+": End date month must be number."));
	       			  addErrors(request, messages);

	         			request.setAttribute("maintenanceForm", maintenanceForm);
	         			return "resourceView";
	       		   }
	       		   if ((emonth > 12)||(emonth <0)) {
	        			messages.add(ActionMessages.GLOBAL_MESSAGE,
	         					new ActionMessage("message.generalmessage", record.getPlacement()+": End date month must be between 1 and 12."));
	        			addErrors(request, messages);

	         			request.setAttribute("maintenanceForm", maintenanceForm);
	         			return "resourceView";  
	       		   }
	       		   // validate month
	               try {
	       			 eyear = Integer.parseInt(tmpEnd[2]);
	       		   } catch (NumberFormatException e) {
	       			  messages.add(ActionMessages.GLOBAL_MESSAGE,
	       					new ActionMessage("message.generalmessage", record.getPlacement()+": End date year must be number."));
	       			  addErrors(request, messages);

	         			request.setAttribute("maintenanceForm", maintenanceForm);
	         			return "resourceView";
	       		   }
	       		   
	       		if (emonth == 1||emonth == 3
	    				||emonth == 5||emonth == 7||emonth == 8
	    				||emonth == 10||emonth == 12) {
	    			if (eday > 31) {
	    				messages.add(ActionMessages.GLOBAL_MESSAGE,
	    						new ActionMessage("message.generalmessage", record.getPlacement()+": Invalid day entered for end date."));
	    				addErrors(request, messages);

	         			request.setAttribute("maintenanceForm", maintenanceForm);
	         			return "resourceView";  
	    			}
	    		} else if (emonth == 4||emonth == 6
	    				||emonth == 9||emonth == 11) {
	    			if (eday > 30) {
	    				messages.add(ActionMessages.GLOBAL_MESSAGE,
	    						new ActionMessage("message.generalmessage", record.getPlacement()+": Invalid day entered for end date."));
	    				addErrors(request, messages);

	         			request.setAttribute("maintenanceForm", maintenanceForm);
	         			return "resourceView";  
	    			}
	    		} else if (emonth == 2) {
	    			if (eyear % 4 == 0) {
	    				if (eday > 29) {
	    					messages.add(ActionMessages.GLOBAL_MESSAGE,
	    							new ActionMessage("message.generalmessage", record.getPlacement()+": Invalid day entered for end date."));
	    					addErrors(request, messages);

	             			request.setAttribute("maintenanceForm", maintenanceForm);
	             			return "resourceView"; 
	    				}
	    			} else {
	    				if (eday > 28) {
	    					messages.add(ActionMessages.GLOBAL_MESSAGE,
	    							new ActionMessage("message.generalmessage", record.getPlacement()+": Invalid day entered for end date."));
	    					addErrors(request, messages);

	             			request.setAttribute("maintenanceForm", maintenanceForm);
	             			return "resourceView"; 
	    				}
	    			}
	    		} // end validate start date
	       		   
	       		Calendar start = Calendar.getInstance();
	       		start.set(syear,smonth,sday);
	       		Calendar end = Calendar.getInstance();
	       		end.set(eyear,emonth,eday);
	       		
	       		if (end.before(start)) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", record.getPlacement()+": The end date cannot be before the from date."));
					addErrors(request, messages);

         			request.setAttribute("maintenanceForm", maintenanceForm);
         			return "resourceView"; 
	       		}
	       		
	       		   if (emonth <= 9) {
	       			   record.setEndDate("0"+emonth+"/");
	       		   } else {
	       			   record.setEndDate(emonth+"/");
	       		   }
	       		   if (eday <=9) {
	       			   record.setEndDate(record.getEndDate()+"0"+eday+"/"+eyear);
	       		   } else {
	       			   record.setEndDate(record.getEndDate()+eday+"/"+eyear);
	       		   }
		    }
		    
			dao.updateResPlacementDates(record);
		}
		
		request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
		
		if ((null == maintenanceForm.getResourcesPerVendor())||(maintenanceForm.getResourcesPerVendor().equals("null"))) {
			maintenanceForm.setResourcesPerVendor("0");
		}
		if (null == maintenanceForm.getResourcesPerPlacement()) {
			maintenanceForm.setResourcesPerPlacement("0");
		}
		maintenanceForm.setDataList(dao.selectResourceList(maintenanceForm.getAlpha(),maintenanceForm.getResourcesPerPlacement(),maintenanceForm.getResourcesPerVendor()));
		maintenanceForm.resetFormbean(mapping, request);
		
		return "resourceDisplay";
	}
	
	public String linkPlacementsVal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		String selPlacements = "";
		String tmp1 = request.getParameter("selectedPlacements");
		if (tmp1 == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "At least one Placement must be selected."));
			addMessages(request, messages);
			
			request.setAttribute("placementList", maintenanceForm.getPlacementList());
			return "resourceLinkPlacements";
		}
		String[] selectedPlacements = maintenanceForm.getSelectedPlacements();
		ArrayList tmp = maintenanceForm.getPlacementList();
		int counter = 0;

		if (selectedPlacements != null) {
			for (int i = 0; i < selectedPlacements.length; i++) {
				counter++;
				for (int j=0; j < tmp.size(); j++) {
					LabelValueBean tmpPlc = (LabelValueBean) tmp.get(j);
					if (tmpPlc.getValue().equals(selectedPlacements[i])) {
						if (selPlacements.length() == 0) {
							selPlacements = tmpPlc.getLabel();
						} else {
							selPlacements = selPlacements +", "+ tmpPlc.getLabel();
						}
					}
				}
			}
		} else {
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "At least one Placement must be selected."));
			addMessages(request, messages);
			
			request.setAttribute("placementList", maintenanceForm.getPlacementList());
			return "resourceLinkPlacements";
		}
		
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "At least one Placement must be selected."));
			addMessages(request, messages);
			
			request.setAttribute("placementList", maintenanceForm.getPlacementList());
			return "resourceLinkPlacements";			
		}
		
		maintenanceForm.setSelectedPlacementsStr(selPlacements);
		
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		request.setAttribute("vendorOptions", maintenanceForm.getVendorOptions());
		request.setAttribute("txtOptions", maintenanceForm.getTxtOptions());
		request.setAttribute("newsTitleOptions", maintenanceForm.getNewsTitleOptions());
		request.setAttribute("highlightOptions",maintenanceForm.getHighlightOptions());
		
		// select subjects
		maintenanceForm.setSubjectList(dao.selectSubjectList("OPTIONLIST",""));
		request.setAttribute("subjectList", maintenanceForm.getSubjectList());
		
		return"resourceLinkSubjects";
	}
	
	public String linkSubjectsVal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		ActionMessages messages = new ActionMessages();
		String selSubjects = "";
		String tmp1 = request.getParameter("selectedSubjects");
		if (tmp1 == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "At least one Subject must be selected."));
			addMessages(request, messages);
			
			request.setAttribute("subjectList", maintenanceForm.getSubjectList());
			return "resourceLinkSubjects";
		}
		String[] selectedSubjects = maintenanceForm.getSelectedSubjects();
		ArrayList tmp = maintenanceForm.getSubjectList();
		int counter = 0;

		/* read currently registered study units into cancellation list */
		if (selectedSubjects != null) {
			for (int i = 0; i < selectedSubjects.length; i++) {
				counter++;
				for (int j=0; j < tmp.size(); j++) {
					LabelValueBean tmpPlc = (LabelValueBean) tmp.get(j);
					if (tmpPlc.getValue().equals(selectedSubjects[i])) {
						if (selSubjects.length() == 0) {
							selSubjects = selSubjects + tmpPlc.getLabel();
						} else {
							selSubjects = selSubjects +", "+ tmpPlc.getLabel();
						}
					}
				}
			}
		} else {
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "At least one Subject must be selected."));
			addMessages(request, messages);
			
			request.setAttribute("subjectList", maintenanceForm.getSubjectList());
			return "resourceLinkSubjects";
		}
		
		if (counter == 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "At least one Subject must be selected."));
			addMessages(request, messages);
			
			request.setAttribute("subjectList", maintenanceForm.getSubjectList());
			return "resourceLinkSubjects";			
		}
		
		maintenanceForm.setSelectedSubjectsStr(selSubjects);
		
		request.setAttribute("enabledOptionList",maintenanceForm.getEnabledOptionList());
		request.setAttribute("vendorOptions", maintenanceForm.getVendorOptions());
		request.setAttribute("txtOptions", maintenanceForm.getTxtOptions());
		request.setAttribute("newsTitleOptions", maintenanceForm.getNewsTitleOptions());
		request.setAttribute("highlightOptions",maintenanceForm.getHighlightOptions());
		
		// select vendor desc
		maintenanceForm.setVendorDesc(dao.selectVendorDesc(maintenanceForm.getVendorId()));
		
		// select text desc
		maintenanceForm.setTextDesc(dao.selectTextCoverageDesc(maintenanceForm.getTextId()));
		
		// select newstitle description
		if (!((maintenanceForm.getNewsTitle()==null)||(maintenanceForm.getNewsTitle().length()==0))) {
			maintenanceForm.setNewsTitleDesc(dao.selectNewsTitleDesc(maintenanceForm.getNewsTitle()));
		}
		
		return "resourceConfirm";
	}
	
	/** txtDisplay: Forward to maintenance of Text Coverage */
	public ActionForward gotoRanking(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MaintenanceForm maintenanceForm = (MaintenanceForm) form;
		LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
		maintenanceForm.setDataList(dao.selectPlacementList("FORMLIST","",true,true));
		maintenanceForm.setPlacementList(dao.selectPlacementList("OPTIONLIST","",true,false));
		request.setAttribute("placementList", maintenanceForm.getPlacementList());
		
		return mapping.findForward("placementRanking");
	}
	
	/* remove leading whitespace */
    public static String ltrim(String source) {
        return source.replaceAll("^\\s+", "");
    }

    /* remove trailing whitespace */
    public static String rtrim(String source) {
        return source.replaceAll("\\s+$", "");
    }
    
    public static boolean urlValidator(String URL) {
    	boolean isValid = false;
    	URL = rtrim(URL);
    	URL = ltrim(URL);
    	
    	if ((Pattern.matches("http://", URL.substring(0, 7)))||(Pattern.matches("https://", URL.substring(0, 8)))) {
    		isValid = true;
    		
    	} else {
    		isValid = false;
    	}
    	
    	return isValid;
    }
    
    public ActionForward updateResources(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

				MaintenanceForm maintenanceForm = (MaintenanceForm) form;          
				ActionMessages messages = new ActionMessages();
                

				
				LibraryEResourcesDAO dao = new LibraryEResourcesDAO(maintenanceForm.getDatabase());
				ArrayList libresourceList =  dao.selectSpecificdb();
				request.setAttribute("placementOptions", dao.selectPlacementList("OPTIONLIST","",true,false));
                request.setAttribute("vendorOptions",dao.selectVendorList("OPTIONLIST", ""));
				dao.atozInsert(libresourceList);
				
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				                                            new ActionMessage("message.generalmessage", "Resources display updated successfully"));
				            addMessages(request, messages);
            
			return mapping.findForward("resourceDisplay");  
}


}
