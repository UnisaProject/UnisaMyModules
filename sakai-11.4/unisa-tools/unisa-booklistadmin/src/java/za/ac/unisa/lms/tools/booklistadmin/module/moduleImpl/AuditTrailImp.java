package za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl;

import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import za.ac.unisa.lms.tools.booklistadmin.dao.AuditTrailDAO;
import za.ac.unisa.lms.tools.booklistadmin.forms.BooklistAdminForm;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrailModule;

public class AuditTrailImp {
	
	AuditTrailDAO auditTrailDAO;
	public AuditTrailImp(AuditTrailDAO auditTrailDAO){
		      this.auditTrailDAO=auditTrailDAO;
	}
	public void updateAuditTrail(BooklistAdminForm booklistAdminForm, String updateInfo){
		                 AuditTrailDAO auditTrailDAO=new AuditTrailDAO();
			               AuditTrailModule auditTrail = new AuditTrailModule();
		            	   auditTrail.setNetworkId(booklistAdminForm.getNetworkId());		
		                   auditTrail.setStudyUnitCode(booklistAdminForm.getCourseId());
		                   auditTrail.setAcadYear(booklistAdminForm.getYear());
		                   auditTrail.setBookStatus(booklistAdminForm.getTypeOfBookList());
		                   auditTrail.setUpdateInfo(updateInfo);
		                   auditTrailDAO.updateAuditTrail(auditTrail);
		
	}
	public AuditTrailModule getLastUpdated (String subject,String acadyear, String typeofbooklist) throws Exception {
		          AuditTrailDAO auditTrailDAO=new AuditTrailDAO();
                 return auditTrailDAO.getLastUpdated(subject, acadyear, typeofbooklist);
    }
	public AuditTrailModule getLastUpdated (BooklistAdminForm booklistAdminForm) throws Exception {
		AuditTrailDAO auditTrailDAO=new AuditTrailDAO();
		           String courseId=booklistAdminForm.getCourseId();
		           String acadyear=booklistAdminForm.getYear();
		           String typeofbooklist=booklistAdminForm.getTypeOfBookList();
                 return auditTrailDAO.getLastUpdated(courseId, acadyear, typeofbooklist);
    }
	public void  updateAuditTrail(AuditTrailModule trail) {
                    auditTrailDAO.updateAuditTrail(trail);
   }
	public  String  getNameOfLastUpdater(AuditTrailModule auditTrailModule,
			               UserDirectoryService userDirectoryService) { 
		try{
	                               if ( auditTrailModule != null) {
		                                  String userId = auditTrailModule.getNetworkId();
		                                  User lastUpdatedUserDetails = userDirectoryService.getUserByEid(userId);
		                                  return lastUpdatedUserDetails.getDisplayName();
	                                }else{
	            	                       return "";
	                                }
		}catch(Exception ex){}
		return "";
   }
	public String  updateAuditTrail(BooklistAdminForm booklistAdminForm,String auditTrailMsgForAdminUpdate,
            String auditTrailMsgForNonAdminUpdate){
                if(booklistAdminForm.getContinueOption().equals("updatebookforcourses")){
                        booklistAdminForm.setCourseId("ADMIN");
                        updateAuditTrail(booklistAdminForm,auditTrailMsgForAdminUpdate );
                        return  "Successfully submitted";
                }else{
                          updateAuditTrail(booklistAdminForm, auditTrailMsgForNonAdminUpdate);
                }
         return "";
}

	
}
