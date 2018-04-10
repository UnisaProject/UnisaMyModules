package za.ac.unisa.lms.tools.assmarkerreallocation.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.general.ModuleDAO;
import za.ac.unisa.lms.domain.general.StudyUnit;
import za.ac.unisa.lms.tools.assmarkerreallocation.dao.AssessmentDAO;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.AssessmentDetails;
import za.ac.unisa.lms.domain.assessmentCriteria.Assignment;
public class Assessment{
	
	public  Assessment(){
		       dao =new AssessmentDAO ();
	}
	public  Assessment(String academicYear,String  semester,String studyUnit,String uniqueAssNum,String assNum){
		           initialise(academicYear,semester,studyUnit,uniqueAssNum,assNum);
	               marker=new Marker(academicYear,semester,studyUnit,Integer.parseInt(uniqueAssNum),assNum);
	               markingDetail=new MarkingDetail(marker,academicYear,semester,studyUnit);
	               dao =new AssessmentDAO ();
	}
	public void reInitialise(String academicYear,String  semester,String studyUnit,String uniqueAssNum,String assNum){
                    initialise(academicYear,semester,studyUnit,uniqueAssNum,assNum);
                    marker=new Marker(academicYear,semester,studyUnit,Integer.parseInt(uniqueAssNum),assNum);
                    markingDetail=new MarkingDetail(marker,academicYear,semester,studyUnit);
                    dao =new AssessmentDAO ();
    }
    MarkingDetail  markingDetail;
	Marker marker;
	AssessmentDAO  dao; 
	public static String editMode="edit";
	public static String accessMode="access";
	String userId;
	Integer uniqueAssNum;
	short year;
	short semesterPeriod;
	String studyUnit,assNum;
	private List markingLanguageList;
	public void setMarkingLanguageList(List markingLanguageList){
		            this.markingLanguageList=markingLanguageList;
	}
	public List getMarkingLanguageList(){
		         return markingLanguageList;
	}
	 public void initialise(String academicYear,String  semester,String studyUnit,String uniqueAssNum,String assNum){
    	                this.year=Short.parseShort(academicYear);
    	                this.semesterPeriod=Short.parseShort(semester);
    	                this.studyUnit=studyUnit;
    	                this.uniqueAssNum=Integer.parseInt(uniqueAssNum);
    	                this.assNum=assNum;
    }
    public List getMarkingDetailList(boolean updateAllowed,boolean langDisplayed)  throws Exception{
    	                   String  loginMode="";
    	                   if(updateAllowed){
    	                	   loginMode= editMode;
    	                   }else{
    	                	   loginMode=accessMode;
    	                   }
    	                   return markingDetail.getMarkingStatistics(loginMode,assNum,langDisplayed);
    }
    public int getNumOfStudentsRegistered()throws Exception{
                     return  markingDetail.getNumOfStudentsRegistered();
    }
   public List  validateInputData(String academicYear,String semester,String studyUnit){
		               List  validationErrorList=new ArrayList(); 
		               if (academicYear==null ||academicYear.trim().equalsIgnoreCase("")){
		        	           validationErrorList.add("Academic Year is required");
		        	           return validationErrorList;
		               } else {
			                     if (!Utilities.isInteger(academicYear)){
							              validationErrorList.add("Academic Year must be numeric");
							              return validationErrorList;
			                     }
		               }
		               if (semester==null || semester.trim().equalsIgnoreCase("")){
		            	   validationErrorList.add("Semester is required");
		               }else {
			                      if (!Utilities.isInteger(semester)){
			                    	 validationErrorList.add("Semester must be numeric");
			                      }
		               }
		               if(studyUnit==null || studyUnit.trim().equalsIgnoreCase("")){
		            	          validationErrorList.add("Study Unit is required");
		            	          return  validationErrorList;
		               }else {
			                      StudyUnit studyUnitRec = new StudyUnit();
			                      ModuleDAO dao = new ModuleDAO();	
			                      try{
			                           studyUnitRec = dao.getStudyUnit(studyUnit.toUpperCase());
			                           if (studyUnitRec.getCode()==null) {
			        	                     validationErrorList.add("Study Unit not found");
			        	                     return  validationErrorList;
			                           }
			                      }catch(Exception ex){
			        	                       validationErrorList.add("An exception occured while  getting  studyUnitRec   in  ModuleDAO");
			                      }
		               }
		               if(!isStudyUnitRegPeriodExisting(academicYear,semester,studyUnit)){
		            	       validationErrorList.add("Study Unit period details not found");
		               }
		               if(!isAssessmentPlanAuthorised(academicYear,semester,studyUnit)){
		            	         validationErrorList.add("No assessment plan exists for this module.");
		               }
		               return validationErrorList;
	}
   private boolean isStudyUnitRegPeriodExisting(String academicYear,String semester,String studyUnit){
                      short acadYear=Short.parseShort(academicYear);
                      short semesterPeriod=Short.parseShort(semester);
                      return dao.periodDetailExist(studyUnit,acadYear,semesterPeriod);
}

	private boolean isAssessmentPlanAuthorised(String academicYear,String semester,String studyUnit){
		                      short acadYear=Short.parseShort(academicYear);
                              short semesterPeriod=Short.parseShort(semester);
                              return dao.isAssPlanAuthorised(studyUnit,acadYear,semesterPeriod);
    }
	public List<AssessmentDetails>  getAssessmentList (String academicYear,String semester,String studyUnit) throws Exception{
	                                    short acadYear=Short.parseShort(academicYear);
	                                    short semesterPeriod=Short.parseShort(semester);
	                                    AssessmentDAO dao=new AssessmentDAO();
	                                    List<AssessmentDetails> assessmentList=dao.getAssessmentList(acadYear, semesterPeriod,studyUnit);
	                                 return assessmentList;
    }
    public List<AssessmentDetails>  getAssessmentList () throws Exception{
                                  AssessmentDAO dao=new AssessmentDAO();
                                  List<AssessmentDetails> assessmentList=dao.getAssessmentList(year, semesterPeriod,studyUnit);
                               return assessmentList;
    }
    public 	String getAssessmentFileExt()  throws Exception{
    	                List listFileExtentions = new ArrayList();
		                listFileExtentions = dao.getFileFormats(studyUnit,uniqueAssNum);
		                String extentions = "None";
		                for (int i=0; i < listFileExtentions.size(); i++){
			                  if (i==0){
				                          extentions=listFileExtentions.get(i).toString();
			                  }else{
				                       extentions = extentions + "; " + listFileExtentions.get(i).toString(); 
			                  }			
		                 }
		               return extentions;
	}
    
    public static  int getUniqueAssignmentNum(List assessmentList, int assemssmentNr){
    	                  AssessmentDetails assessmentDetails=new AssessmentDetails();
    	                  return assessmentDetails.getUniqueAssignmentNum(assessmentList, assemssmentNr);
   }
    public String getAssessmentFormat(int uniqueNum){
    	               AssessmentDAO dao=new AssessmentDAO();
    	               return dao.getAssessmentFormat(uniqueNum);
   }
}
