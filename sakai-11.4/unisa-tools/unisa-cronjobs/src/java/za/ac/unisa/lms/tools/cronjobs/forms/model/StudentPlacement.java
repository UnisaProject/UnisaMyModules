package za.ac.unisa.lms.tools.cronjobs.forms.model;
import za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl.StudentPlacementImpl;

import java.util.List;
import java.util.Set;
import java.util.Set;
public class StudentPlacement {
	protected String module;
	protected Integer schoolCode;
	protected String schoolDesc;
	protected Integer supervisorCode;
	protected String supervisorName;
	protected String startDate;
	protected String endDate;
	protected String numberOfWeeks;
	protected String evaluationMark;
	protected String stuNum;
	protected String name;
	int  saCode=1025;
	StudentPlacementImpl  spImpl;
	public  StudentPlacement(){
		       spImpl=new StudentPlacementImpl();	
	}
    
    public void updateEmailToSupField(int supervCode) throws Exception{
    	            spImpl.updateEmailToSupField(supervCode);
    }
    public List getPlacementListForSuperv(int supervisorCode) throws Exception {
        return spImpl.getPlacementListForSuperv(supervisorCode);	
    }
    public int  getTotPlacmtEmailSend() throws Exception{
                   return spImpl.getTotPlacmtEmailSend();
    }
    public int  getTotPlacmtEmailNotSend() throws Exception{
                  return spImpl.getTotPlacmtEmailNotSend();
    }
    public List    getSupervCodeListEmailNotSend()throws Exception{
    	       return spImpl.getSupervCodeListEmailNotSend();
    }
    public String  getName(){
		     return name;
	}
	public void setName(String name){
		       this.name=name;
	}
	
	public void setStuNum(String stuNum){
		this.stuNum=stuNum;
	}
	public String getStuNum(){
		return stuNum;
	}
	public String getModule() {
		return module;
	}
	
	public Integer getSchoolCode() {
		return schoolCode;
	}

	public void setSchoolCode(Integer schoolCode) {
		this.schoolCode = schoolCode;
	}

	public Integer getSupervisorCode() {
		return supervisorCode;
	}

	public void setSupervisorCode(Integer supervisorCode) {
		this.supervisorCode = supervisorCode;
	}

	public void setModule(String module) {
		this.module = module;
	}	
	public String getSchoolDesc() {
		return schoolDesc;
	}
	public void setSchoolDesc(String schoolDesc) {
		this.schoolDesc = schoolDesc;
	}	
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getNumberOfWeeks() {
		return numberOfWeeks;
	}
	public void setNumberOfWeeks(String numberOfWeeks) {
		this.numberOfWeeks = numberOfWeeks;
	}
	public String getEvaluationMark() {
		return evaluationMark;
	}
	public void setEvaluationMark(String evaluationMark) {
		this.evaluationMark = evaluationMark;
	}
	
}
