package za.ac.unisa.lms.tools.tpustudentplacement.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Country;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.PlacementUtilities;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Mentor;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.DistrictUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.PrelimStudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;

public class ManagementInfoAction extends LookupDispatchAction{
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
			map.put("button.add", "addSchool");
			map.put("button.searchPostalCode","displayPostalCodeSearch");
			map.put("button.searchDistrict","searchDistrict");
			map.put("button.searchSchool","searchSchool");
			map.put("button.searchSupervisor","searchSupervisor");
			map.put("button.clearSchool","clearSchool");
			map.put("button.clearSupervisor","clearSupervisor");
			map.put("extractFile", "extractFile");
			map.put("placementCountryOnchangeAction","placementCountryOnchangeAction");
			map.put("button.prelimPlacements","displayPrelimPlacements");
			return map;
		}
	   
		public ActionForward extractFile(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
			StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			 
				//tell browser program going to return an application file 
			        //instead of html page
			        response.setContentType("application/octet-stream");
			        response.setHeader("Pragma", "private");
			        response.setHeader("Cache-Control", "private, must-revalidate");
			        response.setHeader("Content-Disposition","attachment;filename=placementList.txt");
			        
			 
				try 
				{
					ServletOutputStream out = response.getOutputStream();	
					String fileName = "";
					fileName = studentPlacementForm.getDownloadFile().trim();;
					File file = new File(fileName);
					BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
					byte[] buf = new byte[4 * 1024]; // 4K buffer
					int bytesRead;
					while ((bytesRead = is.read(buf)) != -1) {
					        out.write(buf, 0, bytesRead);
					}
					is.close();
					out.close(); 			 
				  }catch (Exception e) {e.printStackTrace();}	
				  return null;
			}
		    public ActionForward displayPrelimPlacements(
				                   ActionMapping mapping,
				                   ActionForm form,
				                   HttpServletRequest request,
				                   HttpServletResponse response) throws Exception {
			                           StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			                           PrelimStudentPlacementUI prelimStudentPlacementUI=new PrelimStudentPlacementUI();
			                           ActionMessages messages=prelimStudentPlacementUI.buildPrelimPlacementScreen(studentPlacementForm);
			                           if(messages.isEmpty()){
			                        	   writeDelimitedFile(studentPlacementForm);
			                           }
			                           addErrors(request,messages);
			          		      return mapping.findForward("listPlacement");
		  }
		
		  public ActionForward initial(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			
			studentPlacementForm.setManagementCalledFrom(studentPlacementForm.getCurrentPage());
			if (studentPlacementForm.getManagementCalledFrom()==null){
				studentPlacementForm.setManagementCalledFrom("inputStudentPlacement");
			}
			if (studentPlacementForm.getPlacementFilterCountry()==null || studentPlacementForm.getPlacementFilterCountry().trim().equalsIgnoreCase("")){
				//default country to south africa
				   studentPlacementForm.setSchoolFilterCountry(PlacementUtilities.getSaCode());
				   studentPlacementForm.setPlacementFilterCountry(PlacementUtilities.getSaCode());
			}
			studentPlacementForm.setPlacementFilterProvince(Short.parseShort("0"));
			studentPlacementForm.setPlacementFilterDistrict(Short.parseShort("0"));
			studentPlacementForm.setPlacementFilterSupervisor(Integer.parseInt("0"));
			studentPlacementForm.setPlacementFilterSchool(Integer.parseInt("0"));
			studentPlacementForm.setPlacementFilterDistrictValue("");
			studentPlacementForm.setPlacementFilterModule("");
			studentPlacementForm.setPlacementFilterSchoolDesc("");
			studentPlacementForm.setPlacementFilterSupervisorDesc("");
			studentPlacementForm.setDistrictFilter("");
			studentPlacementForm.setSchoolFilter("");
			studentPlacementForm.setSupervisorFilter("");
			studentPlacementForm.setListFilterPlacementDistrict(new ArrayList());
			PlacementUtilities util = new PlacementUtilities();
			if (studentPlacementForm.getSemester()==null || studentPlacementForm.getSemester().equalsIgnoreCase("")){
				studentPlacementForm.setSemester("0");
			}
			if (studentPlacementForm.getAcadYear()==null || studentPlacementForm.getAcadYear().equalsIgnoreCase("")){
				studentPlacementForm.setAcadYear(util.getDefaultAcadYear().toString());
			}
			
			List list = new ArrayList();
			StudentDAO studentDAO = new StudentDAO();
			list=studentDAO.getPracticalModuleList();
			list.add(0,"ALL");
			studentPlacementForm.setListPracticalModules(list);
			studentPlacementForm.setPlacementSortOn("District");			
			studentPlacementForm.setContractStatus("All");
			studentPlacementForm.setCurrentPage("inputPlacement");
			return mapping.findForward("inputPlacement");	
		}
		
		public ActionForward prevPage(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			       StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
			  	   studentPlacementForm.setCurrentPage(studentPlacementForm.getManagementCalledFrom());
			return mapping.findForward(studentPlacementForm.getManagementCalledFrom());
		}
		
		public ActionForward display(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			    StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			    ActionMessages messages = new ActionMessages();	
			    Short province = studentPlacementForm.getPlacementFilterProvince();
			    if(!studentPlacementForm.getPlacementFilterCountry().equals(PlacementUtilities.getSaCode())){
			    	   province=Short.parseShort("0");
			    	   studentPlacementForm.setPlacementFilterDistrictValue(null);
			    }
			    studentPlacementForm.setListPlacement(new ArrayList());
			    DistrictUI districtUI=new DistrictUI();
			    districtUI.setDistrictValue(studentPlacementForm,province);
		        checkFilterValues(studentPlacementForm,messages,PlacementUtilities.getSaCode());
			    if (!messages.isEmpty()) {
			    	   addErrors(request,messages);
				       studentPlacementForm.setCurrentPage(studentPlacementForm.getCurrentPage());
				       return mapping.findForward(studentPlacementForm.getCurrentPage());				
			    }
			    StudentPlacementUI studentPlacementUI=new StudentPlacementUI();
			    studentPlacementUI.setPlacementList(studentPlacementForm,province);
			    writeDelimitedFile(studentPlacementForm);
			    studentPlacementForm.setCurrentPage("listPlacement");
			    studentPlacementUI.initForIntCountry(studentPlacementForm);
			return mapping.findForward("listPlacement");
		}
		public void writeDelimitedFile(StudentPlacementForm studentPlacementForm){
			              //write ~ delimited file
			              List list=studentPlacementForm.getListPlacement();
			              String fileName="";
			              if (list.size()>0){
				                 String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
				                 String fileDir = path +"/";
				                 String time = (new java.text.SimpleDateFormat("yyyyMMddhhmmssss").format(new java.util.Date()).toString());
				                 fileName = fileDir +studentPlacementForm.getUserId()+ "_tpuPlacementList_"+ time +".txt";		
				                 studentPlacementForm.setDownloadFile(fileName);
				                 writeFile(list, fileName);
				                
			            }
		}
		private void checkFilterValues(StudentPlacementForm studentPlacementForm,ActionMessages messages,String saCode){
			                    if(studentPlacementForm.getPlacementFilterCountry().equals(saCode)){
			                    	    filterValuesForNational(studentPlacementForm,messages);
			                    }else{
			                    	   // filterValuesForInternational(studentPlacementForm,messages);
			                    }
		}
        private void filterValuesForNational(StudentPlacementForm studentPlacementForm,ActionMessages messages){
        	 if ((studentPlacementForm.getPlacementFilterDistrictValue()==null || 
             		studentPlacementForm.getPlacementFilterDistrictValue().trim().equalsIgnoreCase("") || 
             		studentPlacementForm.getPlacementFilterDistrictValue().trim().substring(0,1).equalsIgnoreCase("0")) &&
                (studentPlacementForm.getPlacementFilterSchool()==null ||
             		   studentPlacementForm.getPlacementFilterSchool()==0) &&
                (studentPlacementForm.getPlacementFilterSupervisor()==null ||
             		   studentPlacementForm.getPlacementFilterSupervisor()==0) &&
                (studentPlacementForm.getPlacementFilterModule()==null || 
             		   studentPlacementForm.getPlacementFilterModule().equalsIgnoreCase("ALL"))){
	                  messages.add(ActionMessages.GLOBAL_MESSAGE,
			                        new ActionMessage("message.generalmessage",
						            "Filter on at least one of the following: District, Module, School or Supervisor."));
        	 }
        }
        
		
		public static void writeFile(List records, String fileName){
			 //Create file 
			 try {
				 FileOutputStream out = new FileOutputStream(new File(fileName));
				 PrintStream ps = new PrintStream(out);
				 String headingLine = "Student_Name~Student_Nr~Student_Contact_Nr~Module~Period~Eval_mark~School_Name~School_Contact_Nr~District~Town";				 
				 ps.print(headingLine);
				 ps.println();
				 for (int i=0; i<records.size(); i++){
					 PlacementListRecord record = new PlacementListRecord();
					 record = (PlacementListRecord)records.get(i);
					 String line = record.getStudentName() + '~' + record.getStudentNumber().toString() + '~' + record.getStudentContactNumber() + 
					 '~' + record.getModule() + '~' +	record.getStartDate() + " - " + record.getEndDate() + '~' + record.getEvaluationMark() + '~' + record.getSchoolDesc() + 
					 '~' + record.getSchoolContactNumber() + '~' + record.getDistrictDesc()+ '~' + record.getTown();				 
					 ps.print(line);
					 ps.println(); 		
				 }
				 ps.close();
			 } catch (Exception e) {}		
		}
		
		public ActionForward searchDistrict(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			        List listDistrict = new ArrayList();
			        DistrictDAO dao = new DistrictDAO();
			        listDistrict = dao.getDistrictList2("Y", studentPlacementForm.getPlacementFilterProvince(),studentPlacementForm.getDistrictFilter());
			        String label="ALL";
			        String value="0-" + studentPlacementForm.getPlacementFilterProvince().toString();
			        listDistrict.add(0,new LabelValueBean(label, value));
			        studentPlacementForm.setListFilterPlacementDistrict(listDistrict);	
				return mapping.findForward(studentPlacementForm.getCurrentPage());	
		}
		
		public ActionForward searchSchool(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			         studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getPlacementFilterCountry());
			         studentPlacementForm.setSchoolFilter("");
			   return mapping.findForward("searchSchool");	
		}
		
		public ActionForward searchSupervisor(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			         studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getPlacementFilterCountry());
			         studentPlacementForm.setSchoolFilter("");
			         studentPlacementForm.setSupervisorFilter("");
				return mapping.findForward("searchSupervisor");	
		}
		
		public ActionForward clearSchool(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			           StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			           studentPlacementForm.setPlacementFilterSchool(Integer.parseInt("0"));
			           studentPlacementForm.setPlacementFilterSchoolDesc("");
				return mapping.findForward(studentPlacementForm.getCurrentPage());	
		}
		
		public ActionForward clearSupervisor(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			studentPlacementForm.setPlacementFilterSupervisor(Integer.parseInt("0"));
			studentPlacementForm.setPlacementFilterSupervisorDesc("");
			
			return mapping.findForward(studentPlacementForm.getCurrentPage());	
		}
		public ActionForward placementCountryOnchangeAction(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			                   StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			                   	       studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getPlacementFilterCountry());
			                   	       StudentPlacementUI studentPlacementUI=new StudentPlacementUI();
			                   	       studentPlacementUI.initForIntCountry(studentPlacementForm);
				                       studentPlacementForm.setSchoolFilter("");
				                       studentPlacementForm.setSupervisorFilter("");
			                           if(studentPlacementForm.getCurrentPage().equals("listPrelimPlacement")){
			        	                   return mapping.findForward("listPrelimPlacement");  
			                           }else{
			                                  return mapping.findForward("listPlacement");
			                           }
		}
		
		
}
