package za.ac.unisa.lms.tools.tpustudentplacement.actions;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.*;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SupervisorAreaRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SupervisorListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import  za.ac.unisa.lms.tools.tpustudentplacement.model.*;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.*;
import za.ac.unisa.utils.CellPhoneVerification;
import za.ac.unisa.lms.tools.tpustudentplacement.model.SupervisorEmailClass;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Email.EmailLogUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.SupervisorUI;
public class SupervisorMaintenanceAction extends LookupDispatchAction{
	      private static final String saCode="1015";
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
		map.put("adjustStudentAllocationAllowed","adjustStudentAllocationAllowed");
		map.put("extractFile", "extractFile");
		map.put("button.display", "display");
		map.put("button.back", "prevPage");
		map.put("button.add", "addSupervisor");
		map.put("button.link", "linkSupervisorArea");
		map.put("button.edit", "editSupervisor");
		map.put("saveSupervisor", "saveSupervisor");
		map.put("button.saveSupervisor", "saveSupervisor");
		map.put("button.addArea", "addSupervisorArea");
		map.put("button.removeArea", "removeSupervisorArea");
		map.put("button.continue", "nextStep");
		map.put("button.searchPostalCode","displayPostalCodeSearch");
		map.put("button.searchDistrict","searchDistrict");
		map.put("button.select","selectSupervisor");
		map.put("supervCountryOnchangeAction","supervCountryOnchangeAction");
		map.put("button.emaillog","displayEmailLogs");
		map.put("button.sendemail","sendEmail");
		return map;
	}
	
	
	 private String  saveAllowedAlloc(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
                                 throws Exception {
		                            StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                            SupervisorDAO dao = new SupervisorDAO();
		                            PlacementUtilities placementUtilities=new PlacementUtilities();
		                            ActionMessages messages = new ActionMessages();	
		                            if (placementUtilities.isStringEmpty(studentPlacementForm.getStudentsAllowed())||
		                            		(!placementUtilities.isInteger(studentPlacementForm.getStudentsAllowed()))){
					 				             messages.add(ActionMessages.GLOBAL_MESSAGE,
					 						                new ActionMessage("message.generalmessage",
					 									                      "Students Allocation Total Allowed  must be numeric"));
					 				            
					 		        }else{
					 		        	Supervisor  supervisor=studentPlacementForm.getSupervisor();
			                            int studentsAllowed=Integer.parseInt(studentPlacementForm.getStudentsAllowed());
			                            int semester=Integer.parseInt(studentPlacementForm.getSemester());
			                            int year=Integer.parseInt(studentPlacementForm.getAcadYear());
			                            String actionCompleted=dao.saveAllocationAllowed(supervisor.getCode(),studentsAllowed,year,semester);
			                            changeSupervTotStuAllowed(studentPlacementForm);
			                            messages.add(ActionMessages.GLOBAL_MESSAGE,
		 						                new ActionMessage("message.generalmessage",
		 						                		actionCompleted));
					 		        }
		                            addErrors(request,messages);
		                   return "adjustStudentAllowed";
    }
	public ActionForward  adjustStudentAllocationAllowed(ActionMapping mapping, ActionForm form,
	                                       HttpServletRequest request, HttpServletResponse response)
	                                       throws Exception {
		                         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                         GregorianCalendar calCurrent = new GregorianCalendar();
		                         int acadYear= calCurrent.get(Calendar.YEAR);
		                         studentPlacementForm.setAcadYear(""+acadYear);
		                         studentPlacementForm.setSemester("0");
		                         Supervisor supervisor=studentPlacementForm.getSupervisor();
		                         int totStuAllowed=Integer.parseInt(supervisor.getStudentsAllowed());
		                         studentPlacementForm.setStudentsAllowed(""+totStuAllowed);
		                         studentPlacementForm.setCurrentPage("adjustStudentAllowed");
		                     return mapping.findForward("adjustStudentAllowed");	
		                        
	}
	public ActionForward extractFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		        String fileToRead=studentPlacementForm.getDownloadFile();
		        String outputFileName="supervisorList.txt";
		        SupervisorDAO dao=new SupervisorDAO();
		        dao.extractFile(request,response,fileToRead,outputFileName);
			  return null;
		}
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		       studentPlacementForm.setDaysToExpiry("");
		       DateUtil dateutil=new DateUtil();  
               studentPlacementForm.setAcadYear(""+dateutil.yearInt());
		studentPlacementForm.setSupervisorCalledFrom(studentPlacementForm.getCurrentPage());
		if (studentPlacementForm.getSupervisorCalledFrom()==null){
			studentPlacementForm.setSupervisorCalledFrom("inputStudentPlacement");
		}
		
		if (studentPlacementForm.getSupervisorFilterCountry()==null || studentPlacementForm.getSupervisorFilterCountry().trim().equalsIgnoreCase("")){
			//default country to south africa
			studentPlacementForm.setSupervisorFilterCountry(saCode);
		}
		studentPlacementForm.setIndexNrSelected(new String[0]);
		studentPlacementForm.setCurrentPage("inputSupervisor");
		studentPlacementForm.setSupervisorFilterProvince(Short.parseShort("0"));
		studentPlacementForm.setSupervisorFilterDistrict(Short.parseShort("0"));
		studentPlacementForm.setSupervisorFilterDistrictValue("");
		studentPlacementForm.setDistrictFilter("");
				
		
		if (studentPlacementForm.getSupervisorCalledFrom().equalsIgnoreCase("editStudentPlacement")){			
			if (studentPlacementForm.getStudentPlacement().getSupervisorCode()!=null){
				List listDistrict = new ArrayList();
				studentPlacementForm.setListFilterSupervisorDistrict(listDistrict);
				studentPlacementForm.setSupervisorFilter(studentPlacementForm.getStudentPlacement().getSupervisorName() + "%");
				return display(mapping, studentPlacementForm, request, response);
			}			
		}
		if (studentPlacementForm.getSupervisorCalledFrom().equalsIgnoreCase("inputPlacement") || studentPlacementForm.getSupervisorCalledFrom().equalsIgnoreCase("listPlacement")){
			studentPlacementForm.setListFilterSupervisorDistrict(studentPlacementForm.getListFilterPlacementDistrict());
			studentPlacementForm.setSupervisorFilterProvince(studentPlacementForm.getPlacementFilterProvince());
			studentPlacementForm.setSupervisorFilterDistrictValue(studentPlacementForm.getPlacementFilterDistrictValue());
			if (studentPlacementForm.getPlacementFilterDistrict()!=null && !studentPlacementForm.getPlacementFilterDistrictValue().equalsIgnoreCase("")){
				return display(mapping, studentPlacementForm, request, response);
			}
		}else{
			studentPlacementForm.setListFilterSupervisorDistrict(new ArrayList());
		}
		studentPlacementForm.setContractStatus("All");
		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		studentPlacementForm.setCurrentPage("inputSupervisor");
		return mapping.findForward("inputSupervisor");	
	}
	
	public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
		
		if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("editSupervisor")){
			if (studentPlacementForm.getSupervisorAction()!=null && studentPlacementForm.getSupervisorAction().equalsIgnoreCase("Add")){
				studentPlacementForm.setCurrentPage("listSupervisor");
				return mapping.findForward("listSupervisor");
			}else{
				return display(mapping,form,request,response);
			}
		}else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("addSupervisorArea")||
				  studentPlacementForm.getCurrentPage().equalsIgnoreCase("adjustStudentAllowed")){
			      studentPlacementForm.setCurrentPage("editSupervisor");
			      return mapping.findForward("editSupervisor");
		}else if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("emaillog")){
			       studentPlacementForm.setCurrentPage("listSupervisor");
          		   return mapping.findForward("listSupervisor");
		}else{
			studentPlacementForm.setCurrentPage(studentPlacementForm.getSupervisorCalledFrom());
			return mapping.findForward(studentPlacementForm.getSupervisorCalledFrom());
		}
			
	}

	public ActionForward display(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		ActionMessages messages = new ActionMessages();	
		if(!studentPlacementForm.getSupervisorFilterCountry().equals(saCode)){
		       studentPlacementForm.setSupervisorFilterDistrictValue(null);
		       studentPlacementForm.setSupervisorFilterProvince(Short.parseShort("0"));
		}
		Short province = studentPlacementForm.getSupervisorFilterProvince();
		if (studentPlacementForm.getSupervisorFilterDistrictValue()!=null && 
				!studentPlacementForm.getSupervisorFilterDistrictValue().equalsIgnoreCase("")){
		  		int index = studentPlacementForm.getSupervisorFilterDistrictValue().indexOf("-");
		  		String district = studentPlacementForm.getSupervisorFilterDistrictValue().trim().substring(0, index);
		  		province = Short.parseShort(studentPlacementForm.getSupervisorFilterDistrictValue().trim().substring(index+1));
		  		if (studentPlacementForm.getSupervisorFilterProvince().compareTo(province)!=0 &&
		  				studentPlacementForm.getSupervisorFilterProvince().compareTo(Short.parseShort("0"))!=0){
		  			studentPlacementForm.setSupervisorFilterDistrict(Short.parseShort("0"));
		  			studentPlacementForm.setSupervisorFilterDistrictValue("");
		  			studentPlacementForm.setListFilterSupervisorDistrict(new ArrayList());
		   		}else{
		  			studentPlacementForm.setSupervisorFilterDistrict(Short.parseShort(district));
		  			List listDistrict = new ArrayList();
		  			DistrictDAO districtDAO = new DistrictDAO();
		  			listDistrict = districtDAO.getDistrictList2("Y",province,studentPlacementForm.getDistrictFilter());
					String label="ALL";
					String value="0-" + studentPlacementForm.getSupervisorFilterProvince().toString();
					listDistrict.add(0,new LabelValueBean(label, value));
					studentPlacementForm.setListFilterSupervisorDistrict(listDistrict);
			  		//studentPlacementForm.setSupervisorFilterProvince(Short.parseShort(province));
		  		}		  		
		}
		if(studentPlacementForm.getCurrentPage().equals("inputSupervisor")||
				studentPlacementForm.getCurrentPage().equals("listSupervisor")){
		              PlacementUtilities placementUtilities=new PlacementUtilities();
		              if(studentPlacementForm.getContractStatus().trim().equals("Expires within")){
		                   if (placementUtilities.isStringEmpty(studentPlacementForm.getDaysToExpiry())||
        		                  (!placementUtilities.isInteger(studentPlacementForm.getDaysToExpiry()))){
			                             messages.add(ActionMessages.GLOBAL_MESSAGE,
					                             new ActionMessage("message.generalmessage",
								                 "Expiry date must be numeric"));
			            
	                       }
		               }else{
			                    studentPlacementForm.setDaysToExpiry("0");
		               }
		}else{
            studentPlacementForm.setDaysToExpiry("0");
        }
		if (!messages.isEmpty()) {
			addErrors(request,messages);
				studentPlacementForm.setCurrentPage(studentPlacementForm.getCurrentPage());
			    return mapping.findForward(studentPlacementForm.getCurrentPage());				
		}
		List list = new ArrayList<SupervisorListRecord>();
		Supervisor  supervisor= new Supervisor();
		list = supervisor.getSupervisorList(studentPlacementForm.getSupervisorFilterCountry(),
				province,
				studentPlacementForm.getSupervisorFilterDistrict(),
				studentPlacementForm.getSupervisorFilter(),
				studentPlacementForm.getContractStatus(),
				Integer.parseInt(studentPlacementForm.getDaysToExpiry()));
		studentPlacementForm.setListSupervisor(list);
		writeDelimitedFile(list,studentPlacementForm);
		//re-check check boxes after edit
		if (studentPlacementForm.getIndexNrSelected()!=null && studentPlacementForm.getIndexNrSelected().length>0){
			studentPlacementForm.setIndexNrSelected(new String[0]);
			for (int i=0; i < studentPlacementForm.getListSupervisor().size(); i++){
				SupervisorListRecord temp = (SupervisorListRecord)studentPlacementForm.getListSupervisor().get(i);
				if (studentPlacementForm.getSupervisor() != null && 
						studentPlacementForm.getSupervisor().getCode()!= null && 
						temp.getCode()==studentPlacementForm.getSupervisor().getCode()){
					         String[] array = new String[1];
					         array[0]=String.valueOf(i);
					         studentPlacementForm.setIndexNrSelected(array);
					         i = studentPlacementForm.getListSupervisor().size();
				}
			}
		}		
		
		//initialise values
		//studentPlacementForm.setSupervisor(new Supervisor());	
		//studentPlacementForm.setSupervisorFilter("");
		if( studentPlacementForm.getDaysToExpiry().equals("0")){
			   studentPlacementForm.setDaysToExpiry("");
		}
		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		studentPlacementForm.setCurrentPage("listSupervisor");
		return mapping.findForward("listSupervisor");
	}
	private void writeDelimitedFile(List list,StudentPlacementForm studentPlacementForm ){
        //write ~ delimited file
		      SupervisorDAO   dao=new SupervisorDAO();
               String fileName="";
               if (list.size()>0){
	                 String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
	                 String fileDir = path +"/";
	                 String time = (new java.text.SimpleDateFormat("yyyyMMddhhmmssss").format(new java.util.Date()).toString());
	                 fileName = fileDir + studentPlacementForm.getUserId() +"_tpuSupevisorList_"+ time +".txt";			
	                 dao.writeFile(list, fileName,studentPlacementForm.getSupervisorFilterCountry());
	                 studentPlacementForm.setDownloadFile(fileName);
              }
    }	
	public ActionForward addSupervisor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
				Supervisor supervisor = new Supervisor();	
		        Province province = new Province();
		        List listArea = new ArrayList<SupervisorAreaRecord>();
 				
         		supervisor.setCountryCode(studentPlacementForm.getSupervisorFilterCountry());
		        if(studentPlacementForm.getSupervisorFilterCountry().equals("1015")){
		              supervisor.setProvinceCode(studentPlacementForm.getSupervisorFilterProvince());
		              supervisor.setListArea(listArea);
		        }
		        studentPlacementForm.setSupervisor(supervisor);
 		        studentPlacementForm.setSupervisorAction("Add");
 		        studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		        studentPlacementForm.setCurrentPage("editSupervisor");
		        studentPlacementForm.getSupervisor().setContract("Y");
		        studentPlacementForm.setContractStatus("All");
		        return mapping.findForward("editSupervisor");	
	}
	public ActionForward addSupervisorArea(
			                 ActionMapping mapping,
			                 ActionForm form,
			                 HttpServletRequest request,
			                 HttpServletResponse response) throws Exception {
		
                               StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
		                   	   SupervisorAreaRecord supervisorArea = new SupervisorAreaRecord();	
		                       Province province = new Province();
		                       District district = new District();			
		                       supervisorArea.setDistrict(district);
		                       studentPlacementForm.setDistrictSelection(new String[0]);
		                       studentPlacementForm.setListDistrict(new ArrayList());
        		               for(int i=0; i < studentPlacementForm.getSupervisor().getListArea().size();i++){
			                        province.setCode(((SupervisorAreaRecord)(studentPlacementForm.getSupervisor().getListArea().get(i))).getProvince().getCode());
		                       }
		                       supervisorArea.setProvince(province);
		                       studentPlacementForm.setSupervisorArea(supervisorArea);
		                       studentPlacementForm.setDistrictFilter("");
		       		           studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		                       studentPlacementForm.setCurrentPage("addSupervisorArea");
		                       return mapping.findForward("addSupervisorArea");	
	}
	
	public ActionForward removeSupervisorArea(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		ActionMessages messages = new ActionMessages();	
		
		SupervisorDAO dao = new SupervisorDAO();
		
		if (studentPlacementForm.getIndexNrSupAreaSelected()==null ||
				studentPlacementForm.getIndexNrSupAreaSelected().length==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Select at least one are to remove"));
			}else{
				if (studentPlacementForm.getSupervisor().getListArea().size()<=studentPlacementForm.getIndexNrSupAreaSelected().length){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
										"Supervisor must be linked to at least one area. First add an correct area before removing the last one."));
				}
			}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			studentPlacementForm.setCurrentPage("editSupervisor");
			return mapping.findForward("editSupervisor");				
		}
		
		String array[] = studentPlacementForm.getIndexNrSupAreaSelected();
		
		for (int i=0; i <studentPlacementForm.getIndexNrSupAreaSelected().length; i++) {
			SupervisorAreaRecord supervisorArea = new SupervisorAreaRecord();				
			supervisorArea = (SupervisorAreaRecord)studentPlacementForm.getSupervisor().getListArea().get(Integer.parseInt(array[i]));
			if (studentPlacementForm.getSupervisorAction().equalsIgnoreCase("edit")){
				dao.removeSupervisorArea(studentPlacementForm.getSupervisor(), supervisorArea);
			}	
		}
		
		List listArea = new ArrayList<SupervisorAreaRecord>(); 
		for (int i=0; i < studentPlacementForm.getSupervisor().getListArea().size(); i++){
			boolean deleteRecord = false;
			for (int j=0; j <studentPlacementForm.getIndexNrSupAreaSelected().length; j++){
				if(Integer.parseInt(array[j])==i){
					deleteRecord = true;
				}
			}
			if (deleteRecord == false){
				SupervisorAreaRecord supervisorArea = new SupervisorAreaRecord();	
				supervisorArea = (SupervisorAreaRecord)studentPlacementForm.getSupervisor().getListArea().get(i);
				listArea.add(supervisorArea);
			}
		}
			
		studentPlacementForm.getSupervisor().setListArea(listArea);
		studentPlacementForm.setIndexNrSupAreaSelected(new String[0]);
		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		studentPlacementForm.setCurrentPage("editSupervisor");
		return mapping.findForward("editSupervisor");	
	}
	
	
	public ActionForward displayEmailLogs(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		       
		                 StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                 SupervisorUI supervisorUI=new SupervisorUI();
		                 ActionMessages messages = new ActionMessages();
		                 List emailLogList;
		                 String[] indexArr=studentPlacementForm.getIndexNrSelected();
		                 if(studentPlacementForm.getCurrentPage().equals("emaillog")){
		                	    emailLogList=setEmailLogList(studentPlacementForm.getSupervisor().getCode(),Integer.parseInt(studentPlacementForm.getAcadYear()));
			   	                studentPlacementForm.setSupervisorEmailLogList(emailLogList);
		                	    return mapping.findForward("emaillog");	
		                 }else{
		                	   studentPlacementForm.setCurrentPage("emaillog");
		                	   supervisorUI.validateIndexArrForSupervSelection(indexArr,messages);
		                       if (!messages.isEmpty()) {
		                            addErrors(request,messages);
		                            studentPlacementForm.setCurrentPage("listSupervisor");
		                            return mapping.findForward("listSupervisor");				
		                       }
		                       supervisorUI.setSelectedSupervisor(studentPlacementForm);
		                 }
		                 emailLogList=setEmailLogList(studentPlacementForm.getSupervisor().getCode(),Integer.parseInt(studentPlacementForm.getAcadYear()));
		   	             studentPlacementForm.setSupervisorEmailLogList(emailLogList);
		            return mapping.findForward("emaillog");	
	}
	public ActionForward sendEmail(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		       
		                StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                Supervisor supervisor= studentPlacementForm.getSupervisor();
		                SupervisorEmailClass sec=new SupervisorEmailClass();
		                ActionMessages messages = new ActionMessages();
		                if((supervisor.getEmailAddress()==null)||(supervisor.getEmailAddress().trim().equals(""))){
		                	   messages.add(ActionMessages.GLOBAL_MESSAGE,
			                           new ActionMessage("message.generalmessage",
						               "Email has not been sent, the supervisor's email is not valid"));
		                	   addErrors(request,messages);
		                }else{
		                          sec.sendEmailToSupervisor(supervisor);
	                              messages.add(ActionMessages.GLOBAL_MESSAGE,
					                           new ActionMessage("message.generalmessage",
								               "Email has been sent"));
   	                             List emailLogList=setEmailLogList(studentPlacementForm.getSupervisor().getCode(),
   	                            		                            Integer.parseInt(studentPlacementForm.getAcadYear()));
   	                             studentPlacementForm.setSupervisorEmailLogList(emailLogList);
   	                          addMessages(request,messages);
		                }
		               
		               return mapping.findForward(studentPlacementForm.getCurrentPage());				
    }
	private List setEmailLogList(int supervisorCode ,int acadYear)throws Exception {
		           EmailLogUI mailLogRecord=new EmailLogUI();
                    return mailLogRecord.getEmailLog(supervisorCode,acadYear);
                  
	}
	public ActionForward editSupervisor(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

            StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
            ActionMessages messages = new ActionMessages();	
            SupervisorUI supervisorUI=new SupervisorUI();
            String[] indexArr=studentPlacementForm.getIndexNrSelected();
            supervisorUI.validateIndexArrForSupervSelection(indexArr, messages);
            if (!messages.isEmpty()) {
                addErrors(request,messages);
                studentPlacementForm.setCurrentPage("listSupervisor");
                return mapping.findForward("listSupervisor");				
           }
           supervisorUI.setSelectedSupervisor(studentPlacementForm);
           studentPlacementForm.setSupervisorAction("Edit"); 
           studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
           studentPlacementForm.setCurrentPage("editSupervisor");
           String networkCode=studentPlacementForm.getUserId();
           Coordinator coordinator=new Coordinator();
           studentPlacementForm.setCoordinatorActive(coordinator.isCoordinator(networkCode));
           int provCode= getSupervisorProvince(studentPlacementForm);
           String country=studentPlacementForm.getSupervisorFilterCountry();
           String showLink="N";
           if(studentPlacementForm.getCoordinatorActive().equals("Y")){
                    coordinator=new Coordinator();
                    coordinator.setNetworkCode(networkCode);
                    showLink=coordinator.checkCoordinatorForProv(country,provCode);
          }
          if(showLink.equals("Y")){
                  studentPlacementForm.setCoordinatorForProv("Y");
          }else{
                 studentPlacementForm.setCoordinatorForProv("N");
          }
          studentPlacementForm.setContractStatus("All");
      return mapping.findForward("editSupervisor");	
}
	private int getSupervisorProvince(StudentPlacementForm studentPlacementForm){
		                  Supervisor supervisor= studentPlacementForm.getSupervisor();
		                  if(supervisor.getProvinceCode()==null){
                               return 21;	
                           }else{
                        	   return  supervisor.getProvinceCode();
                          }
	}
	public ActionForward saveSupervisor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		         ActionMessages messages = new ActionMessages();
		         if(studentPlacementForm.getCurrentPage().equals("adjustStudentAllowed")){
		                 return mapping.findForward(saveAllowedAlloc(mapping,form,request,response));
	             }
		         SupervisorUI supervisorUI=new SupervisorUI();
		         supervisorUI.validateSupervData(studentPlacementForm.getSupervisor(), messages);
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			System.out.println("here2");
			studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			studentPlacementForm.setCurrentPage("editSupervisor");
			return mapping.findForward("editSupervisor");				
		}
		SupervisorDAO dao = new SupervisorDAO();
		if (studentPlacementForm.getSupervisorAction().equalsIgnoreCase("Add")){
			dao.insertSupervisor(studentPlacementForm.getSupervisor());
		}
		if (studentPlacementForm.getSupervisorAction().equalsIgnoreCase("Edit")){
			dao.updateSupervisor(studentPlacementForm.getSupervisor());
		}
		
		return display(mapping,form,request,response);	
	}
	
	public ActionForward linkSupervisorArea(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		ActionMessages messages = new ActionMessages();	
		
		Province province = new Province();
		province.setCode(studentPlacementForm.getSupervisorArea().getProvince().getCode());
		for (int i=0; i < studentPlacementForm.getListProvince().size();i++){
			if (province.getCode().compareTo(((Province)studentPlacementForm.getListProvince().get(i)).getCode())==0){
				province.setDescription(((Province)studentPlacementForm.getListProvince().get(i)).getDescription());
				i=studentPlacementForm.getListProvince().size();
			}
		}
		//Add districts selected not already on area list
		for (int i=0;i < studentPlacementForm.getDistrictSelection().length; i++){
			String area = "";
			area = studentPlacementForm.getDistrictSelection()[i];		
			int index = area.indexOf("-");			
	  		Short districtCode = Short.parseShort(area.trim().substring(0, index));	  	
	  		boolean areaRecordFound = false;
	  		for (int j=0; j < studentPlacementForm.getSupervisor().getListArea().size(); j++){
	  			SupervisorAreaRecord supervisorArea = new SupervisorAreaRecord();
				supervisorArea = (SupervisorAreaRecord)studentPlacementForm.getSupervisor().getListArea().get(j);
				if (supervisorArea.getDistrict().getCode()!=null){
					if (province.getCode().compareTo(supervisorArea.getProvince().getCode())==0
							&& districtCode.compareTo(supervisorArea.getDistrict().getCode())==0){
								areaRecordFound=true;
								j = studentPlacementForm.getSupervisor().getListArea().size();
					}
				}				
	  		}
	  		if (areaRecordFound==false){
				SupervisorAreaRecord newArea = new SupervisorAreaRecord();	
				District district = new District();
				DistrictDAO districtDAO = new DistrictDAO();
				district = districtDAO.getDistrict(districtCode, null);
	  			newArea.setDistrict(district);
	  			newArea.setProvince(province);
	  			SupervisorDAO supervisorDAO = new SupervisorDAO();
	  			if (studentPlacementForm.getSupervisorAction().equalsIgnoreCase("Add")){
					studentPlacementForm.getSupervisor().getListArea().add(newArea);			
				}else if (studentPlacementForm.getSupervisorAction().equalsIgnoreCase("Edit")){
					       studentPlacementForm.getSupervisor().getListArea().add(newArea);	
				  	       supervisorDAO.addSupervisorArea(studentPlacementForm.getSupervisor(),newArea);
				}
	  		}	  		
		}
		//Add only province if no district selected
		if (studentPlacementForm.getDistrictSelection().length==0){			
  			//add province only if not already in area list
  			boolean areaRecordFound = false;
	  		for (int i=0; i < studentPlacementForm.getSupervisor().getListArea().size(); i++){
	  			SupervisorAreaRecord supervisorArea = new SupervisorAreaRecord();
				supervisorArea = (SupervisorAreaRecord)studentPlacementForm.getSupervisor().getListArea().get(i);
				if (province.getCode().compareTo(supervisorArea.getProvince().getCode())==0
						&& supervisorArea.getDistrict().getCode()==null){
							areaRecordFound=true;
							i = studentPlacementForm.getSupervisor().getListArea().size();
					}
	  		}
	  		if (areaRecordFound==false){
	  			   SupervisorDAO supervisorDAO = new SupervisorDAO();
	  			   SupervisorAreaRecord newArea = new SupervisorAreaRecord();	
				   District district = new District();
				newArea.setDistrict(district);
	  			newArea.setProvince(province);
	  			if (studentPlacementForm.getSupervisorAction().equalsIgnoreCase("Add")){
					studentPlacementForm.getSupervisor().getListArea().add(newArea);			
				}else if (studentPlacementForm.getSupervisorAction().equalsIgnoreCase("Edit")){
					studentPlacementForm.getSupervisor().getListArea().add(newArea);	
					supervisorDAO.addSupervisorArea(studentPlacementForm.getSupervisor(),newArea);
				}
	  		}  			
		}		
		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		studentPlacementForm.setCurrentPage("editSupervisor");
		return mapping.findForward("editSupervisor");	
	}
	
	public ActionForward xxlinkSupervisorArea(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		ActionMessages messages = new ActionMessages();	
		
		boolean areaRecordFound = false;
		
		for (int i=0;i < studentPlacementForm.getSupervisor().getListArea().size(); i++){
			SupervisorAreaRecord supervisorArea = new SupervisorAreaRecord();
			supervisorArea = (SupervisorAreaRecord)studentPlacementForm.getSupervisor().getListArea().get(i);
			if (studentPlacementForm.getSupervisorArea().getProvince().getCode().compareTo(supervisorArea.getProvince().getCode())==0
				&& studentPlacementForm.getSupervisorArea().getDistrict().getCode()==null
				&& supervisorArea.getDistrict().getCode()==null){
					areaRecordFound=true;
					i = studentPlacementForm.getSupervisor().getListArea().size();
			}
			if (studentPlacementForm.getSupervisorArea().getProvince().getCode().compareTo(supervisorArea.getProvince().getCode())==0
					&& studentPlacementForm.getSupervisorArea().getDistrict().getCode().compareTo(supervisorArea.getDistrict().getCode())==0){
						areaRecordFound=true;
						i = studentPlacementForm.getSupervisor().getListArea().size();
				}
		}
		
		
		
		if (areaRecordFound==true){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Supervisor already linked to this area."));
			}		
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			studentPlacementForm.setCurrentPage("addSupervisorArea");
			return mapping.findForward("addSupervisorArea");					
		}
		
		if (studentPlacementForm.getSupervisorAction().equalsIgnoreCase("Add")){
			studentPlacementForm.getSupervisor().getListArea().add(studentPlacementForm.getSupervisorArea());			
		}else if (studentPlacementForm.getSupervisorAction().equalsIgnoreCase("Edit")){
			studentPlacementForm.getSupervisor().getListArea().add(studentPlacementForm.getSupervisorArea());	
			SupervisorDAO dao = new SupervisorDAO();
			dao.addSupervisorArea(studentPlacementForm.getSupervisor(),studentPlacementForm.getSupervisorArea());
		}
		
		studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		studentPlacementForm.setCurrentPage("editSupervisor");
		return mapping.findForward("editSupervisor");	
	}
	
	public ActionForward xxsearchDistrict(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		
		return mapping.findForward("searchDistrict");	
	}
	
	public ActionForward searchDistrict(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		List listDistrict = new ArrayList();
		DistrictDAO dao = new DistrictDAO();
		if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("inputSupervisor") 
				|| studentPlacementForm.getCurrentPage().equalsIgnoreCase("listSupervisor")){
			listDistrict = dao.getDistrictList2("Y", studentPlacementForm.getSupervisorFilterProvince(),studentPlacementForm.getDistrictFilter());
			String label="ALL";
			String value="0-" + studentPlacementForm.getSupervisorFilterProvince().toString();
			listDistrict.add(0,new LabelValueBean(label, value));
			studentPlacementForm.setListFilterSupervisorDistrict(listDistrict);	
			
		}else{
			listDistrict = dao.getDistrictList2("Y", studentPlacementForm.getSupervisorArea().getProvince().getCode(),null);
			studentPlacementForm.setListDistrict(listDistrict);	
		}
				
		return mapping.findForward(studentPlacementForm.getCurrentPage());	
	}	
	
	public ActionForward displayPostalCodeSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		studentPlacementForm.setSearchType("");
		studentPlacementForm.setSearchSuburb("");
		studentPlacementForm.setSearchPostalCode("");
		studentPlacementForm.setSearchResult("");		
		return mapping.findForward("searchPostalCode");
	}
	
	public ActionForward selectSupervisor(
			                ActionMapping mapping,
			                ActionForm form,
			                HttpServletRequest request,
			                HttpServletResponse response) throws Exception {
		                        
		                        SupervisorUI supervisorUI=new SupervisorUI();
		                        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
		                        ActionMessages messages = new ActionMessages();	
		                        String[] indexArr=studentPlacementForm.getIndexNrSelected();
		                        supervisorUI.validateIndexArrForSupervSelection(indexArr,messages);
		                        String nextPage="listSupervisor";
		                        if (messages.isEmpty()) {
			   	                       SupervisorListRecord supervisor = new SupervisorListRecord();
			   	                       supervisor=supervisorUI.getSelectedSupervisor(studentPlacementForm);
		                               nextPage=supervisorUI.validateSupervForPlacement(request, studentPlacementForm, messages, supervisor);
		                               if(messages.isEmpty()){
		                                   String fromPage=studentPlacementForm.getSupervisorCalledFrom();
		                                   nextPage=supervisorUI.setSupForAddOrViewPlacement(studentPlacementForm,supervisor,fromPage,nextPage);
		                                   nextPage=supervisorUI.setSupForEditPlacement(studentPlacementForm,supervisor,fromPage,nextPage);
		                               }
		                        }
		                        StudentPlacement stuPlacement=studentPlacementForm.getStudentPlacement();
		                        if(stuPlacement!=null){
		                        	stuPlacement.setDatesToRequest(request);
                                }
		                        addErrors(request,messages);
		                        studentPlacementForm.setCurrentPage(nextPage);
			                   return mapping.findForward(nextPage);
	}
	 
    
     private void changeSupervTotStuAllowed(StudentPlacementForm studentPlacementForm){
		//change the totalStudentAllowed field for a supervisor in the supervisors List
		            SupervisorListRecord supervisor = new SupervisorListRecord();
		            int index=0;
		            for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {			
			                     String array[] = studentPlacementForm.getIndexNrSelected();
			                     index=Integer.parseInt(array[i]);
			                     supervisor = (SupervisorListRecord)studentPlacementForm.getListSupervisor().get(index);			
			                     i=studentPlacementForm.getIndexNrSelected().length;
		            }
		            supervisor.setStudentsAllowed(studentPlacementForm.getStudentsAllowed());
		            studentPlacementForm.getSupervisor().setStudentsAllowed(studentPlacementForm.getStudentsAllowed());
	}
	public ActionForward supervCountryOnchangeAction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		        studentPlacementForm.setSupervisorFilterCountry(studentPlacementForm.getSupervisorFilterCountry().trim());
		        if(!studentPlacementForm.getSupervisorFilterCountry().trim().equals("1015")){
		               studentPlacementForm.setSchoolFilterDistrictValue("All");
		               studentPlacementForm.setSchoolFilterProvince(Short.parseShort("0"));
		               studentPlacementForm.setSchoolFilterDistrict(Short.parseShort("0"));
			            studentPlacementForm.setSchoolFilterDistrictDesc("All");
		        }
		        studentPlacementForm.setSchoolFilter("");
		         studentPlacementForm.setSupervisorFilter("");
		    	return mapping.findForward("inputSupervisor");
	}
	
}
