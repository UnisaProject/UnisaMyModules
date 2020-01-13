package za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl;
import java.util.List;
import java.util.Set;

import java.util.Set;

import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.StudentPlacementDAO;
import za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO.databaseUtils;
import za.ac.unisa.lms.tools.cronjobs.forms.util.DateUtil;
public class StudentPlacementImpl {

	    StudentPlacementDAO dao;
	    public StudentPlacementImpl(){
		       dao=new StudentPlacementDAO();
	    }
	    public List getPlacementListForSuperv(int supervisorCode) throws Exception {
	              return dao.getPlacementListForSuperv(supervisorCode);	
        }
	    public List getSupervCodeListEmailNotSend() throws Exception{
	    	       return dao.getSupervisorCodeListEmailNotSent();
	    }
	    public void updateEmailToSupField(int supervisorCode) throws Exception{
	    	          dao.updateEmailToSupField(supervisorCode);
	    }
	    public int  getTotPlacmtEmailSend() throws Exception{
	                  return dao.getTotPlacmtEmailSend();
        }
	    public int  getTotPlacmtEmailNotSend() throws Exception{
            return dao.getTotPlacmtEmailNotSend();
        }
}
