package za.ac.unisa.lms.tools.tutoringplan.impl;
import java.util.ArrayList;
import java.util.List;
import za.ac.unisa.lms.tools.tutoringplan.dao.TutoringPlanDao;
import za.ac.unisa.lms.tools.tutoringplan.forms.CollegeRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringMode;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringPlanDetails;
public class TutoringPlan {
	
	public List<TutoringPlanDetails> getTutorPlanDetailList(Short acadYear, Short semester, 
			                       String searchCritria, Short school, Short department, CollegeRecord college) throws Exception {
		                                     TutoringPlanDao dao = new TutoringPlanDao();
                                              return dao.getTutorPlanDetailList(acadYear, semester, searchCritria, school, department, college);
	}
	public  TutoringPlan  getTutoringPlan(String studyUnitCode, short acadYear, short semester) throws Exception {
	                        TutoringPlanDao dao = new TutoringPlanDao();
	                        return dao.getTutoringPlan(studyUnitCode,acadYear,semester);
	}
	public List validateInput(String studyUnitCode, String acadYear, String semester) throws Exception {
		                      List validationErrsList=new ArrayList();
		                      validateAcademicYear(acadYear,validationErrsList);
		                      validateSemester(semester,validationErrsList);
		                      validateStudyUnit(studyUnitCode,acadYear,semester,validationErrsList);
		                      
		             return 	validationErrsList;
	}
	public List validateAcademicPeriod(String acadYear, String semester) throws Exception {
                    List validationErrsList=new ArrayList();
                    validateAcademicYear(acadYear,validationErrsList);
                    validateSemester(semester,validationErrsList);
                   return 	validationErrsList;
    }
	public void validateAcademicYear(String acadYear,List validationErrsList){
		             if (acadYear==null || acadYear.trim().equalsIgnoreCase("")){
			                validationErrsList.add("Academic Year is required.");
		             } else {
			                  if (!TutoringplanUtils.isInteger(acadYear)){
				                   validationErrsList.add("Academic Year must be numeric.");
			                 }
		            }
	}
	public void validateSemester(String semester,List validationErrsList){
			            if (semester==null || semester.trim().equalsIgnoreCase("")){
	                                validationErrsList.add("Semester is required.");
                        }else {
			                  if (!TutoringplanUtils.isInteger(semester)){
				                   validationErrsList.add("Semester  must be numeric.");
			                 }
		            }
	}
	public void validateStudyUnit(String studyUnitCode,String acadYear,String semester,List validationErrsList)throws Exception {
		             if (studyUnitCode==null || studyUnitCode.trim().equalsIgnoreCase("")){
                             validationErrsList.add("Study Unit is required.");
                     }else{
                               short academicYear=Short.parseShort(acadYear);
                               short semester_period=Short.parseShort(semester);
                               StudyUnitImpl studyUnitImpl=new StudyUnitImpl();
                               String errMsg=studyUnitImpl.validateStudyUnit(studyUnitCode,academicYear, semester_period);
                               if(!errMsg.equals("")){
                                     validationErrsList.add(errMsg);
                              }
                     }
   }
	List<TutoringMode> listTutoringMode;

	public List<TutoringMode> getListTutoringMode() {
		return listTutoringMode;
	}

	public void setListTutoringMode(List<TutoringMode> listTutoringMode) {
		this.listTutoringMode = listTutoringMode;
	}

}
