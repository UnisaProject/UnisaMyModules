package impl;
import javax.servlet.http.HttpServletRequest;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.UsageSessionService;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;
import javax.naming.NamingException;

public class LoginManager {
	
	private static final Log logger = LogFactory.getLog(LoginManager.class);

	 public void authenticateUser(UsageSessionService usageSessionService,UploadManagerForm uploadManagerForm,HttpServletRequest request) throws Exception {
	            
		              uploadManagerForm.setUserId(getUserId(uploadManagerForm,usageSessionService,request));
	 }
	 
	 public String getUserId(UploadManagerForm uploadManagerForm,UsageSessionService usageSessionService,HttpServletRequest request) throws Exception{
	    	      
		               String userID="";
	    	           if (uploadManagerForm.getFrom() == null || "".equals(uploadManagerForm.getFrom())) {
	    	                uploadManagerForm.setFrom("myUnisa");
	    	           }
	    	           try {
		                     userID = getUserIdFromMyUnisa(usageSessionService);
		                     if ((userID==null) || userID.equals("")) {
                                  userID=getUserIdFromHttpRequest(request);
                                  uploadManagerForm.setFrom("staff");
                             }
	    	            } catch(Exception ex) {
	    	                   userID = getUserIdFromHttpRequest(request);
	    	                   uploadManagerForm.setFrom("staff");
	    	              }
                        validateUserId(userID);
              return userID;
      }
	 
	 public String getUserIdFromMyUnisa(UsageSessionService usageSessionService) throws Exception{
                usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
                UsageSession usageSession = usageSessionService.getSession();
                String userID =usageSession.getUserEid();
                return userID;
	 }

	 public String getUserIdFromHttpRequest(HttpServletRequest request) throws Exception{
	    	return request.getParameter("userId").toString();
     }
	 
	 public String getPointOfLoginOfUser(HttpServletRequest request) throws Exception{
	    	  return request.getParameter("from").toString();
     }
	    
	 private void validateUserId(String userID) throws Exception{
              if ((userID==null)|| userID.equals("")){
	              throw new Exception();
              } 
     }
	     
	 public Vector findUserGroups(String userId) throws NamingException {
 	               UserGroups userGroups=new UserGroups();
                   return userGroups.findUserGroups(userId);
	 }
/*	     public  void setUserPrivileges(String userId,UploadManagerForm uploadManagerForm) throws NamingException{ 
	    	                   UserGroups userGroups=new UserGroups();
	    	                   userGroups.setUserPrivileges(userId, uploadManagerForm);
       }	*/	      
        
}
