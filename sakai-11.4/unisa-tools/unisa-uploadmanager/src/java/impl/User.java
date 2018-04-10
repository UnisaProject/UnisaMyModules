package impl;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;

public class User {

	    public String authenticateUser(String  userId,UploadManagerForm uploadManagerForm,             
			              UsageSessionService usageSessionService)throws Exception{
		                      if(userId==null||userId.equals("")){
		                      	  userId=getUserId(usageSessionService).toUpperCase();
       	                      }
                          return userId;
		 }
	     public String getUserId(UsageSessionService usageSessionService) throws Exception{
		                    usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
                            UsageSession usageSession = usageSessionService.getSession();
                            String userID =usageSession.getUserEid();
                         return userID;
        }
}
