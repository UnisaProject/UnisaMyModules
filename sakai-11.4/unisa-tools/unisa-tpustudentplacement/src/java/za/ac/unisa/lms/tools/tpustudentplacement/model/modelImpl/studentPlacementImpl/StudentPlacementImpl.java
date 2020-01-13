package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Coordinator;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.CommunicationUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;

public class StudentPlacementImpl extends StuPlacementCRUDHelperClass{
	
	           StudentPlacementDAO dao;
	           public StudentPlacementImpl(){
		                    dao=new StudentPlacementDAO();
		      }
	   	      public List getPlacementListForSupervEmail(int supervisorCode) throws Exception {
		                       return dao.getPlacementListForSupervEmail(supervisorCode);
	          }
	          public List getPlacementListForSuperv(int supervisorCode) throws Exception {
		                       return dao.getPlacementListForSuperv(supervisorCode);	
              }
	          public List getPlacementList(Short acadYear, Short semester, Short province,
			                        Short district,Integer supervisor, Integer school,
			                        String module,String sortOn,String country) throws Exception {
		                                  return dao.getPlacementList(acadYear, semester, province, 
		                        		              district, supervisor,school, module, sortOn, country);
	         }
	         public StudentPlacement getStudentPlacement(Short acadYear,Short semester,Integer studentNr,
			                       String module, Integer school) throws Exception {
		                                   return dao.getStudentPlacement(acadYear,semester,studentNr,module,school);
	        }
	         public void updateStudentPlacement(Short acadYear, Short semester, Integer studentNr, 
			              String originalModule, Integer originalSchool, StudentPlacement placement,
			               int originalSupCode) throws Exception {
		                                dao.updateStudentPlacement(acadYear, semester, studentNr, originalModule, originalSchool, placement, originalSupCode);
			   
	       }
		   public void insertStudentPlacement(Short acadYear, Short semester, Integer studentNr,
			             StudentPlacement placement) throws Exception {
		                              dao.insertStudentPlacement(acadYear, semester, studentNr, placement);
	       }
	       public void updateEmailToSupField(int supervCode) throws Exception{
		                    dao.updateEmailToSupField(supervCode);
           }
	       public StudentPlacement getPlacementFromPlacementListRecord(StudentPlacementListRecord splr){
	    	                          StudentPlacement placement=new StudentPlacement();
	    	                          placement.setSchoolCode(splr.getSchoolCode());
	    				              placement.setModule(splr.getModule());
	    				              placement.setSupervisorCode(splr.getSupervisorCode());
	    				              placement.setStartDate(splr.getStartDate());
	    				              placement.setEndDate(splr.getEndDate());
	    				              placement.setNumberOfWeeks(splr.getNumberOfWeeks());
	    				              placement.setEvaluationMark(splr.getEvaluationMark());	
	    				            return placement;
	       }
	       public boolean isPlacementExisting(Short acadYear, Short semester, 
	                              Integer studentNr, StudentPlacement placement)throws Exception{
	    	                      return  dao.isPlacementExisting(acadYear, semester, studentNr, placement);
	       }
	       public  void replaceDummySupervWithCoordForProv(StudentPlacementForm studentPlacementForm)throws Exception{
	    	                      CommunicationUI communicationUI=new CommunicationUI();
	    	                      List  placementList=communicationUI.getStuPlacementsForSchool(studentPlacementForm);
                                  Iterator i=placementList.iterator();
	    	                      while(i.hasNext()){
	    	                	          StudentPlacementListRecord  placement=(StudentPlacementListRecord)i.next();
	    	                	          replaceDummySupervWithCoordForProv(placement);
     	                         }
	       }
           public  void replaceDummySupervWithCoordForProv(StudentPlacementListRecord placement)throws Exception{
        	                     if(placement.getSupervisorCode()==283){
           		                          Coordinator coordinator=new Coordinator();
        	                              coordinator=coordinator.getCoordForProvGivenSchoolCode(placement.getSchoolCode());
        	                              placement.setSupervisorName(coordinator.getName());
        	                              placement.setSupervisorContactNumber(coordinator.getContactNumber());
        	                      }
          }
           public void setDatesToRequest(HttpServletRequest request,StudentPlacement stuPlacement){
       		     DateUtil dateUtil=new DateUtil();
               String startDate=stuPlacement.getStartDate();
               String endDate=stuPlacement.getEndDate();
               if(startDate==null||(startDate.trim().equals(""))){
                            stuPlacement.setStartDate(dateUtil.todayDateOnly());
                            startDate=dateUtil.todayDateOnly();
               }
               if(endDate==null||endDate.trim().equals("")){
                        stuPlacement.setEndDate(dateUtil.todayDateOnly());
                        endDate=dateUtil.todayDateOnly();
               }
               request.setAttribute("startDate",startDate);
               request.setAttribute("endDate",endDate);
}

}
