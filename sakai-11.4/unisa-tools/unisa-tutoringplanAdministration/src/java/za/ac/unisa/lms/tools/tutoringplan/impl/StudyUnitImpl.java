package za.ac.unisa.lms.tools.tutoringplan.impl;
import za.ac.unisa.lms.dao.general.ModuleDAO;
import za.ac.unisa.lms.domain.general.StudyUnit;
import za.ac.unisa.lms.tools.tutoringplan.dao.CollegeDAO;
public class StudyUnitImpl {
	
	public String validateStudyUnit(String studyUnitCode,short academicYear,short semester) throws Exception{
	                if (studyUnitCode==null || studyUnitCode.trim().equalsIgnoreCase("")){
		                  return "Study Unit is required.";
	                }else{
	                	   StudyUnit studyUnit = getStudyUnit(studyUnitCode);
		                   if (studyUnit.getEngLongDesc()==null){
		                	      return "Study Unit not found.";
		                   }else{
			                      return validateStudyUnitPeriod(studyUnit,academicYear,semester);
		                   }
		           }
	}
	public  StudyUnit  getStudyUnit(String studyUnitCode)  throws Exception{
                            ModuleDAO dao = new ModuleDAO();
	                        StudyUnit studyUnit = new StudyUnit();
	                        return dao.getStudyUnit(studyUnitCode);	
    }
    private String validateStudyUnitPeriod(StudyUnit studyUnit, short academicYear,short semester)throws Exception{
    	              CollegeDAO studyUnitDao = new CollegeDAO();
                      if (!studyUnitDao.isSunpdtExist(academicYear,semester, studyUnit.getCode())){
			                  return "Study Unit period details not found.";
		              }
    	              return "";
    }
	
}
