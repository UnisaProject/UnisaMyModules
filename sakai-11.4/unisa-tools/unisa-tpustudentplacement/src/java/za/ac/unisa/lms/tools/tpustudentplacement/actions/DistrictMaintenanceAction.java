package za.ac.unisa.lms.tools.tpustudentplacement.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.*;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.*;

public class DistrictMaintenanceAction extends LookupDispatchAction{
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
	
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");
		map.put("button.display", "display");
		map.put("button.back", "prevPage");
		map.put("button.add", "addDistrict");
		map.put("button.edit", "editDistrict");
		map.put("button.save", "saveDistrict");	
		map.put("button.select", "selectDistrict");	
		return map;
	}
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		
		if (studentPlacementForm.getListDistrict()!=null){
			for (int i=0; i < studentPlacementForm.getListDistrict().size(); i++){
				if (request.getParameter("action.select" + String.valueOf(i)) != null) return getSelectedDistrict(mapping, form, request, response,i);			
			}
		}				
			return super.execute(mapping, form, request, response);
		}
	
	public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	

		if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("editDistrict")){
			studentPlacementForm.setCurrentPage("listDistrict");
			return mapping.findForward("listDistrict");
		}else{
			studentPlacementForm.setCurrentPage(studentPlacementForm.getDistrictCalledFrom());
			return mapping.findForward(studentPlacementForm.getDistrictCalledFrom());	
		}
	}
	
	public ActionForward getSelectedDistrict(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			int selectedRec) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
				
		return mapping.findForward("main");	
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		
		studentPlacementForm.setIndexNrSelected(new String[0]);
		studentPlacementForm.setDistrictCalledFrom(studentPlacementForm.getCurrentPage());
		if (studentPlacementForm.getDistrictCalledFrom()==null){
			studentPlacementForm.setDistrictCalledFrom("inputPlacement");
		}
		if (studentPlacementForm.getDistrictCalledFrom().equalsIgnoreCase("inputSupervisor") || 
			studentPlacementForm.getDistrictCalledFrom().equalsIgnoreCase("listSupervisor")){
			studentPlacementForm.setDistrictFilterProvince(studentPlacementForm.getSupervisorFilterProvince());
				return display(mapping,form,request,response);	
		}else if (studentPlacementForm.getDistrictCalledFrom().equalsIgnoreCase("addSupervisorArea")){
			studentPlacementForm.setDistrictFilterProvince(studentPlacementForm.getSupervisorArea().getProvince().getCode());
			return display(mapping,form,request,response);	
		}else if (studentPlacementForm.getDistrictCalledFrom().equalsIgnoreCase("inputSchool") ||
			studentPlacementForm.getDistrictCalledFrom().equalsIgnoreCase("listSchool")){
			studentPlacementForm.setDistrictFilterProvince(studentPlacementForm.getSchoolFilterProvince());
			return display(mapping,form,request,response);	
		}else if (studentPlacementForm.getDistrictCalledFrom().equalsIgnoreCase("editSchool")){
			studentPlacementForm.setDistrictFilterProvince(studentPlacementForm.getSchool().getDistrict().getProvince().getCode());
			return display(mapping,form,request,response);	
		}
		
		studentPlacementForm.setCurrentPage("inputDistrict");
		return mapping.findForward("inputDistrict");	
	}
	
	public ActionForward display(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		ActionMessages messages = new ActionMessages();	
		
		List list = new ArrayList<District>();
		DistrictDAO dao = new DistrictDAO();
		if (studentPlacementForm.getDistrictCalledFrom().equalsIgnoreCase("inputPlacement")){
			list = dao.getDistrictList(null, studentPlacementForm.getDistrictFilterProvince(),studentPlacementForm.getDistrictFilter());
		} else {
			list = dao.getDistrictList2("Y", studentPlacementForm.getDistrictFilterProvince(),studentPlacementForm.getDistrictFilter());
		}
		
		studentPlacementForm.setListDistrict(list);

		//initialise values
		studentPlacementForm.setDistrictCode(null);
		studentPlacementForm.setDistrictName(null);
		studentPlacementForm.setDistrictInUse(null);
		studentPlacementForm.setDistrictProvince(null);
		studentPlacementForm.setDistrictAction(null);		
		
		studentPlacementForm.setCurrentPage("listDistrict");
		return mapping.findForward("listDistrict");
	}
	
	public ActionForward selectDistrict(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
		
		ActionMessages messages = new ActionMessages();	
		
		if (studentPlacementForm.getIndexNrSelected()==null ||
				studentPlacementForm.getIndexNrSelected().length==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select a district"));
			}
		if (studentPlacementForm.getIndexNrSelected()!=null &&
				studentPlacementForm.getIndexNrSelected().length>1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select only one district"));
			}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			studentPlacementForm.setCurrentPage("listDistrict");
			return mapping.findForward("listDistrict");				
		}
		District district = new District();
		Province province = new Province();
		
		for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
			String array[] = studentPlacementForm.getIndexNrSelected();
			district = (District)studentPlacementForm.getListDistrict().get(Integer.parseInt(array[i]));			
			i=studentPlacementForm.getIndexNrSelected().length;
		}
		province = district.getProvince();
		studentPlacementForm.setDistrictCode(district.getCode());
		studentPlacementForm.setDistrictName(district.getDescription());
		studentPlacementForm.setDistrictInUse(district.getInUse());
		studentPlacementForm.setDistrictProvince(district.getProvince().getCode());		
		studentPlacementForm.setSupervisorFilterDistrict(district.getCode());
		studentPlacementForm.setSupervisorFilterDistrictDesc(district.getDescription());
		studentPlacementForm.setSupervisorFilterProvince(district.getProvince().getCode());
		studentPlacementForm.setSchoolFilterDistrict(district.getCode());
		studentPlacementForm.setSchoolFilterDistrictDesc(district.getDescription());
		studentPlacementForm.setSchoolFilterProvince(district.getProvince().getCode());
		if (studentPlacementForm.getDistrictCalledFrom().equalsIgnoreCase("editSchool")){
			studentPlacementForm.getSchool().setDistrict(district);
		}		
		if (studentPlacementForm.getDistrictCalledFrom().equalsIgnoreCase("addSupervisorArea")){
			studentPlacementForm.getSupervisorArea().setDistrict(district);
			studentPlacementForm.getSupervisorArea().setProvince(province);
		}		
		return mapping.findForward(studentPlacementForm.getDistrictCalledFrom());
	}		

	
	public ActionForward addDistrict(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
		
 		studentPlacementForm.setDistrictAction("Add");
		studentPlacementForm.setDistrictInUse("Y");
		studentPlacementForm.setDistrictProvince(studentPlacementForm.getDistrictFilterProvince());
		studentPlacementForm.setCurrentPage("editDistrict");
		return mapping.findForward("editDistrict");	
	}
	
	public ActionForward editDistrict(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		ActionMessages messages = new ActionMessages();	
		
		if (studentPlacementForm.getIndexNrSelected()==null ||
				studentPlacementForm.getIndexNrSelected().length==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select a district to view or edit"));
			}
		if (studentPlacementForm.getIndexNrSelected()!=null &&
				studentPlacementForm.getIndexNrSelected().length>1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select only one district to view or edit"));
			}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			studentPlacementForm.setCurrentPage("listDistrict");
			return mapping.findForward("listDistrict");				
		}
		District district = new District();
		
		for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
			String array[] = studentPlacementForm.getIndexNrSelected();
			district = (District)studentPlacementForm.getListDistrict().get(Integer.parseInt(array[i]));
		}
		studentPlacementForm.setDistrictCode(district.getCode());
		studentPlacementForm.setDistrictName(district.getDescription());
		studentPlacementForm.setDistrictInUse(district.getInUse());
		studentPlacementForm.setDistrictProvince(district.getProvince().getCode());
 		studentPlacementForm.setDistrictAction("Edit"); 
 		studentPlacementForm.setCurrentPage("editDistrict");
		return mapping.findForward("editDistrict");	
	}
	
	public ActionForward saveDistrict(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		ActionMessages messages = new ActionMessages();	
		
		if (studentPlacementForm.getDistrictName()==null || studentPlacementForm.getDistrictName().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"District Name is required"));
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			studentPlacementForm.setCurrentPage("editDistrict");
			return mapping.findForward("editDistrict");				
		}
		
		District district = new District();
		DistrictDAO dao = new DistrictDAO();
		if (studentPlacementForm.getDistrictAction().equalsIgnoreCase("Add")){
			district = dao.getDistrict(null,studentPlacementForm.getDistrictName());
			if (district.getCode()!=null){
				if (district.getProvince()!=null && district.getProvince().getCode()!=null && 
						district.getProvince().getCode().compareTo(studentPlacementForm.getDistrictProvince())==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Another District with this name already exists in the specified province"));
				}	
			}			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				studentPlacementForm.setCurrentPage("editDistrict");
				return mapping.findForward("editDistrict");				
			}
			Province province = new Province();
			district.setDescription(studentPlacementForm.getDistrictName());
			district.setInUse(studentPlacementForm.getDistrictInUse());
			province.setCode(studentPlacementForm.getDistrictProvince());
			district.setProvince(province);
			dao.insertDistrict(district);
		}
		if (studentPlacementForm.getDistrictAction().equalsIgnoreCase("Edit")){
			district = dao.getDistrict(null,studentPlacementForm.getDistrictName());
			if (district.getCode()!=null && district.getCode().compareTo(studentPlacementForm.getDistrictCode())!=0){
				if (district.getProvince()!=null && district.getProvince().getCode()!=null && 
						district.getProvince().getCode().compareTo(studentPlacementForm.getDistrictProvince())==0){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Another District with this name already exists in the specified province"));
				}	
			}			
			if (!messages.isEmpty()) {
				addErrors(request,messages);
				studentPlacementForm.setCurrentPage("editDistrict");
				return mapping.findForward("editDistrict");				
			}
			Province province = new Province();
			district.setCode(studentPlacementForm.getDistrictCode());
			district.setDescription(studentPlacementForm.getDistrictName());
			district.setInUse(studentPlacementForm.getDistrictInUse());
			province.setCode(studentPlacementForm.getDistrictProvince());
			district.setProvince(province);
			dao.updateDistrict(district);			
		}
		return display(mapping,form,request,response);	
	}

}
