package impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.uploadmanager.dao.ModuleDAO;
public class Module {
	
	public ArrayList getAllModuleCodes(String part)
    {
		ModuleDAO moduleDAO =new ModuleDAO();
		return moduleDAO.getAllModuleCodes(part);
	   
     }
	public 	List  getModules(String networkCode) {
		ModuleDAO moduleDAO =new ModuleDAO();
		return moduleDAO.getModules(networkCode);
   }
	
	public List getModuleLabelValueListList(String part){
		
	     Module  module=new Module();
		 List moduleList=module.getAllModuleCodes(part);
		 List moduleLabelValueBeanList=new ArrayList();
		 for (int i=0; i < moduleList.size(); i++) {
			        moduleLabelValueBeanList.add(new LabelValueBean(moduleList.get(i).toString(),
			    		        moduleList.get(i).toString()));
	     }
		return moduleLabelValueBeanList;
   }

}
