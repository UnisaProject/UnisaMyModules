package za.ac.unisa.lms.tools.tutoringplan.impl;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.tools.tutoringplan.dao.TutoringModeDAO;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringMode;
public class TutoringModeImpl extends TutoringModeInputValidator{
	TutoringMode tutoringMode;
	TutoringModeDAO  tutoringModeDAO;
	
	public  TutoringModeImpl(TutoringMode tutoringMode){
		         super(new TutoringModeDAO());
		         this.tutoringMode=tutoringMode;
		         tutoringModeDAO=new TutoringModeDAO();
	}
	public  TutoringModeImpl(TutoringPlan tutoringplan){
		    super(new TutoringModeDAO());
		    this.tutoringPlan=tutoringplan;
		    tutoringModeDAO=new TutoringModeDAO();
	}
	public void saveTutoringMode(String studyUnitCode, Short acadYear, Short semester,String userAction) throws Exception {
	       Gencod gencod = new Gencod();
			StudentSystemGeneralDAO studentDao = new StudentSystemGeneralDAO();
			gencod = studentDao.getGenCode("181", tutoringMode.getTutorMode());
			if (gencod!=null && gencod.getEngDescription()!=null){
				tutoringMode.setTutorModeDesc(gencod.getEngDescription());
			}else {
				tutoringMode.setTutorModeDesc(tutoringMode.getTutorMode());
			}
			if (userAction.equalsIgnoreCase("add")){
				    tutoringModeDAO.addTutoringMode(studyUnitCode,acadYear,semester,tutoringMode);
			}else
			if (userAction.equalsIgnoreCase("edit")){
			    	tutoringModeDAO.updateTutoringMode(studyUnitCode,acadYear,semester,tutoringMode);
			}
	}
	TutoringPlan tutoringPlan;
	public void setTutoringplan(TutoringPlan tutoringPlan){
		               this.tutoringPlan=tutoringPlan;
	}
	public String  removeTutoringMode(String[] selectedIndexList,String studyUnitCode, Short acadYear, Short semester) throws Exception {
		String errorStr = "";
		int errorNr=0;
		for(int i=0; i < selectedIndexList.length; i++){
			TutoringMode tutorMode = new TutoringMode();
			int index = Integer.parseInt(selectedIndexList[i]);
			tutorMode = (TutoringMode) tutoringPlan.getListTutoringMode().get(index);
			if (!tutoringModeDAO.removeTutoringMode(studyUnitCode,acadYear,semester,tutorMode)){
				errorNr=errorNr +1;
				if (errorStr.equalsIgnoreCase("")){
					errorStr=tutorMode.getTutorModeDesc();
				}else {
					errorStr=tutorMode.getTutorModeDesc() + ", " + tutorMode.getTutorModeDesc();
				}				 
			}
		}
		if (errorNr>0){
			if (errorNr==1){
				return "Error removing Tutoring mode: " + errorStr + " , because groups have already been assigned using this tutoring mode";
			}else{
				return "Error removing Tutoring modes: " + errorStr + ", because groups have already been assigned using this tutoring modes";
			}			
		} 
		return "";
	}

}
