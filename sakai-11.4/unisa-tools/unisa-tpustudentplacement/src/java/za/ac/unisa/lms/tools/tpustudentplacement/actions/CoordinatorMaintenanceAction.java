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
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.*;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.CoordinatorValidation;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;

public class CoordinatorMaintenanceAction extends LookupDispatchAction{
	    
	
	    public static final int sadecIntCode=21;
		protected Map getKeyMethodMap() {
			// TODO Auto-generated method stub
			Map map = new HashMap();
			map.put("initial", "initial");	
			map.put("button.delete", "selectCoordinator");
			map.put("button.back", "prevPage");
			map.put("button.add", "addCoordinator");
		  	map.put("button.confirmdelete","delete");
			map.put("button.save","save");
			map.put("button.edit","goToEditScreen");
			map.put("extractFile", "extractFile");
			map.put("staffInfoBtnOnClickAction","staffInfoBtnOnClickAction");
			return map;
		}
		public ActionForward  selectCoordinator(//selected coordinator is displayed  for confirmation of delete
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
			  	   ActionMessages messages = new ActionMessages();	
			  	   CoordinatorValidation  coordinatorValidation=new CoordinatorValidation();
			  	   coordinatorValidation.validateSelection(messages,studentPlacementForm.getIndexNrSelected());
			       Coordinator coordinator = new Coordinator();
			       for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {			
				           String array[] = studentPlacementForm.getIndexNrSelected();
				           coordinator = (Coordinator)studentPlacementForm.getListCoordinator().get(Integer.parseInt(array[i]));			
				           i=studentPlacementForm.getIndexNrSelected().length;
			       }
			       //coordinator=coordinator.getCoordinator(Integer.parseInt(coordinator.getPersonnelNumber()));
			       studentPlacementForm.setCoordinator(coordinator);
			       //coordinator.setSadecInt(coordinator.getSadecInt().trim());
			       studentPlacementForm.setCurrentPage("displayCoordinator");
			return mapping.findForward("displayCoordinator");
		}
		public ActionForward goToEditScreen(//selected coordinator is displayed  for purpose of editing
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
			  	   ActionMessages messages = new ActionMessages();	
			  	   CoordinatorValidation  coordinatorValidation=new CoordinatorValidation();
			  	   coordinatorValidation.validateSelection(messages,studentPlacementForm.getIndexNrSelected());
			       if (!messages.isEmpty()) {
				          addErrors(request,messages);
				          studentPlacementForm.setCurrentPage("listCoordinator");
				          return mapping.findForward("listCoordinator");				
			       }
			       Coordinator coordinator = new Coordinator();
			       for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {			
				           String array[] = studentPlacementForm.getIndexNrSelected();
				           coordinator = (Coordinator)studentPlacementForm.getListCoordinator().get(Integer.parseInt(array[i]));			
				           i=studentPlacementForm.getIndexNrSelected().length;
			       }
			       ProvinceDAO dao=new ProvinceDAO();
				   List provinceList=dao.getProvinceLabelValueList(""+sadecIntCode,"NONE");
				   studentPlacementForm.setListProvince(provinceList);
				   studentPlacementForm.setCoordinator(coordinator);
				   studentPlacementForm.setCurrProv(coordinator.getWorkStationCode());
			       studentPlacementForm.setSaveAction("Update");
			       studentPlacementForm.setCurrentPage("editCoordinator");
			return mapping.findForward("editCoordinator");
		}
		public ActionForward delete(//selected coordinator is  deleted 
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
			  	   Coordinator coordinator=studentPlacementForm.getCoordinator();
			  	   CoordinatorDAO cdao = new CoordinatorDAO();
			  	   cdao.deleteCoordinator(coordinator.getPersonnelNumber().trim(),coordinator.getWorkStationCode().trim());
			  	   setCoordinatorsList(studentPlacementForm);
				   studentPlacementForm.setCurrentPage("listCoordinator");
				   studentPlacementForm.reset(mapping,request);
			return mapping.findForward("listCoordinator");
		}
			public ActionForward addCoordinator(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
			  	   Coordinator coordinator = new Coordinator();
			  	   coordinator.setSadecInt("Y");
			  	   coordinator.setWorkStationCode(""+sadecIntCode);
			  	   coordinator.setWorkStationDescr("NONE");
			  	   coordinator.setEmailAddress("");
			  	   coordinator.setContactNumber("");
			       studentPlacementForm.setCoordinator(coordinator);
				   studentPlacementForm.setCurrentPage("inputCoordinator");
				   ProvinceDAO dao=new ProvinceDAO();
				   List provinceList=dao.getProvinceLabelValueList(""+sadecIntCode,"NONE");
				   studentPlacementForm.setListProvince(provinceList);
				   studentPlacementForm.setStaffInfoStatus("");
				   studentPlacementForm.setSaveAction("Save");
			       return mapping.findForward("inputCoordinator");
		}
		public ActionForward initial(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			       setCoordinatorsList(studentPlacementForm);
				   studentPlacementForm.setCurrentPage("listCoordinator");
				   studentPlacementForm.setStaffInfoStatus("");
				   studentPlacementForm.reset(mapping,request);
			return mapping.findForward("listCoordinator");	
		}
		
		public ActionForward prevPage(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			      StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			      if(studentPlacementForm.getCurrentPage().equals("listCoordinator")){
			    	       studentPlacementForm.reset(mapping,request);
			 		       studentPlacementForm.setCurrentPage("inputStudentPlacement");
			 	  }else{
			 		       setCoordinatorsList(studentPlacementForm);
			 		       studentPlacementForm.setCurrentPage("listCoordinator");
			 	  }
			      ProvinceDAO dao=new ProvinceDAO();
			      List provinceList=dao.getProvinceList();
				  studentPlacementForm.setListProvince(provinceList);
				  studentPlacementForm.setSadecInt("N");
			 	  studentPlacementForm.setStaffInfoStatus("");
			return mapping.findForward(studentPlacementForm.getCurrentPage());
		}
		private void setCoordinatorsList(StudentPlacementForm studentPlacementForm) throws Exception{
			             List list = new ArrayList();
		                 CoordinatorDAO cdao = new CoordinatorDAO();
		                 list=cdao.getCoordinatorList();
		                 studentPlacementForm.setListCoordinator(list);
		}
		public ActionForward staffInfoBtnOnClickAction(
					ActionMapping mapping,
					ActionForm form,
					HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				         
				         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
				         ActionMessages messages = new ActionMessages();	
				         Coordinator coordinator = studentPlacementForm.getCoordinator();
				          String persno=coordinator.getPersonnelNumber();
				         PlacementUtilities placementUtilities=new PlacementUtilities();
				         Coordinator prospectiveCoordinator=null;
				         if (!placementUtilities.isStringEmpty(persno)&&placementUtilities.isInteger(persno)){
				        	   String personnelNumStr=coordinator.getPersonnelNumber(Integer.parseInt(persno));
				        	   if(!personnelNumStr.equals(""))
				        	       prospectiveCoordinator=coordinator.getProspectiveCoordinator(Integer.parseInt(persno));
				         }
				 		 if ((prospectiveCoordinator==null)||
				 					 (prospectiveCoordinator.getName().trim().equals(""))){
				 				               messages.add(ActionMessages.GLOBAL_MESSAGE,
				 						                  new ActionMessage("message.generalmessage",
				 									"The Personnel Number you entered is invalid, the person   may have resigned/they are not authorized to use the tool"));
				 				      studentPlacementForm.setStaffInfoStatus("");
				 		 }
				 	  	if (!messages.isEmpty()) {
			 			     addErrors(request,messages);
			 			     return mapping.findForward("inputCoordinator");				
			 		    }
				 	  	studentPlacementForm.setCoordinator( prospectiveCoordinator);
					    studentPlacementForm.setStaffInfoStatus("StaffInfoSet");
					  	 return mapping.findForward("inputCoordinator");
			}
		
			public ActionForward save(
					ActionMapping mapping,
					ActionForm form,
					HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
				         ActionMessages messages = new ActionMessages();	
				         studentPlacementForm.setStaffInfoStatus("");
				         Coordinator coordinator=studentPlacementForm.getCoordinator();
				         CoordinatorValidation cval=new  CoordinatorValidation();
				         cval.validateInput(coordinator, messages);
				 		 if (!messages.isEmpty()) {
			 			           addErrors(request,messages);
			 			           if(studentPlacementForm.getSaveAction().trim().equals("Save")){
			 			                 return mapping.findForward("inputCoordinator");		
			 			           }else{
			 			            	 return mapping.findForward("editCoordinator");	
			 			           }
			 		      }
				 		  String saveStr="";
				 		 if(studentPlacementForm.getSaveAction().trim().equals("Save")){
				 			         saveStr=coordinator.saveCoordinator(coordinator);
 			             }else{
 			            	         saveStr=coordinator.updateCoordinator(coordinator,studentPlacementForm.getCurrProv());
 			             }
				 		 if (!saveStr.equals("")){
		 				        messages.add(ActionMessages.GLOBAL_MESSAGE,
		 						new ActionMessage("message.generalmessage",
		 						   saveStr));
		 				       addErrors(request,messages);
		 				       if(studentPlacementForm.getSaveAction().trim().equals("Save")){
		 			                  return mapping.findForward("inputCoordinator");		
		 			           }else{
		 			            	  return mapping.findForward("editCoordinator");	
		 			           }
		 				 }
				 		  setCoordinatorsList(studentPlacementForm);
				 		  studentPlacementForm.setStaffInfoStatus("");
						  studentPlacementForm.setCurrentPage("listCoordinator");
						  studentPlacementForm.reset(mapping,request);
				 	   return mapping.findForward("listCoordinator");
			}
}
