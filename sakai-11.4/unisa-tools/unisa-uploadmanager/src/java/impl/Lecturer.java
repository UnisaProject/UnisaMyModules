package impl;
import za.ac.unisa.lms.tools.uploadmanager.dao.LecturerDAO;
import org.apache.struts.util.LabelValueBean;
import java.util.ArrayList;
import java.util.List;
public class Lecturer {
	
		public boolean isLecturer(String networkCode)  throws Exception{
			             LecturerDAO lecturerDAO=new LecturerDAO();
			             return lecturerDAO.isLecturer(networkCode);
		}
		public List getLecturerModules(String networkCode) {
			         Module module=new Module();
                     return module.getModules(networkCode);
		}
		public List getLecModulesLabelValueList(String networkCode){
			
            Module  module=new Module();
            List moduleList=module.getModules(networkCode);
            List moduleLabelValueBeanList=new ArrayList();
            for (int i=0; i <moduleList.size(); i++) {
	                   moduleLabelValueBeanList.add(new LabelValueBean(moduleList.get(i).toString(),
	    		        moduleList.get(i).toString()));
            }
            return moduleLabelValueBeanList;
       }
		
}
