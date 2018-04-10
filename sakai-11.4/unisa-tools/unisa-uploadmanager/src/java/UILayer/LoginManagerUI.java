package UILayer;
import java.util.Vector;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.sakaiproject.event.api.UsageSessionService;
import impl.Lecturer;
import impl.Module;
import impl.LoginManager;
import impl.UserGroups;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;
import utils.InfoMessagesUtil;
import utils.StaffType;

import org.apache.struts.action.ActionMessages;
public class LoginManagerUI {
	
	public String createMainScreen(HttpServletRequest request,UploadManagerForm uploadManagerForm,UsageSessionService usageSessionService,ActionMessages messages) throws Exception{
		    
		try {
             LoginManager  loginManager = new LoginManager();
   		     loginManager.authenticateUser(usageSessionService,uploadManagerForm,request);
        } catch(Exception ex) {
             InfoMessagesUtil.addMessages(messages,"Problem with network code");
              return "mainfilter";
          }
	   String nextScreen = getNextScreen(uploadManagerForm);
	   setFormBeanCollectionsUsedByMainScreen(uploadManagerForm);
   	                 return nextScreen;
	}
	private void  setFormBeanCollectionsUsedByMainScreen(UploadManagerForm uploadManagerForm) throws Exception {
		
		        Module module=new Module();
		        Lecturer lecturer=new Lecturer();
		        
/*		        if (uploadManagerForm.getFrom()==null){
		            uploadManagerForm.setFrom("");
		        }*/
		        
		       //&&lecturer.isLecturer(uploadManagerForm.getUserId())
		       if (uploadManagerForm.getFrom().equals("myUnisa")) {
		           uploadManagerForm.setLecturersModuleCodeList(lecturer.getLecModulesLabelValueList(uploadManagerForm.getUserId()));
                   uploadManagerForm.setYearsList(uploadManagerForm.getFromYearList());
                   uploadManagerForm.setFromSearch("lecturer");
               } else {
                   uploadManagerForm.setCourseCodeList(module.getModuleLabelValueListList(uploadManagerForm.getSearchCode()));
                   uploadManagerForm.setFromSearch("nonlecturer");
                 }
	}
	private String getNextScreen(UploadManagerForm uploadManagerForm){
		
		//String pointOfEntry,String userId

		               UserGroups userGroups = new UserGroups();
		               Vector groups = null;
					try {
						groups = userGroups.findUserGroups(uploadManagerForm.getUserId());
					} catch (NamingException e) {
				
						e.printStackTrace();
					}
		               
		           /*    if(groups != null && groups.contains("pro")){
		                      uploadManagerForm.setFrom("prod");
		               }*//*else{
		            	      setPrivilegesForNonProdStaff(groups,uploadManagerForm);
		            	      
		               }*/
		               
		            if ("myUnisa".equals(uploadManagerForm.getFrom())){
          	             return  "mainfilter";
                     } else if (groups != null && !groups.contains("pro")) {
                    	 uploadManagerForm.setUserprivilege(StaffType.NORMAL_STAFF.getValue());
                    	  return  "search";	 
                      }
		            uploadManagerForm.setUserprivilege(StaffType.ADMIN_STAFF.getValue());
		               
		               return "search";
	}

}
