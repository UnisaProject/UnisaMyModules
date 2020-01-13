package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import java.util.ArrayList;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.Address;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.ContactImpl;

public class ContactUI {
    
	        ContactImpl contactImpl;
	       public  ContactUI(){
		              contactImpl=new ContactImpl();
	       }
	
	
	       public Contact getContactDetails(Integer referenceNo,Integer category) throws Exception {
                 return contactImpl.getContactDetails(referenceNo,category);	
           }
           public ArrayList getPostalCodeList(String searchType, String searchCriteria, String postalType) throws Exception {
                          return contactImpl.getPostalCodeList(searchType, searchCriteria, postalType);
           }
           public Address getAddress(Integer referenceNo,Integer category,Integer type) throws Exception {
        	                 return contactImpl.getAddress(referenceNo,category,type);	
           }
           public int countRecords(int reference,int category)throws Exception {
                       return contactImpl.countRecords(reference, category);
           }
           public void insertContactDetail(Supervisor supervisor)throws Exception {
        	                contactImpl.insertContactDetail(supervisor);
           }
           public void updateContactDetail(Supervisor supervisor)throws Exception {
        	                contactImpl.updateContactDetail(supervisor);
          }
           public  int totRecsOfAddress(int supervCode,int addressType)throws Exception {
        	             return contactImpl.totRecsOfAddress(supervCode,addressType);
           }
           public void insertPostalAddress(Supervisor supervisor)throws Exception {
        	                contactImpl.insertPostalAddress(supervisor);     
           }
           public void updatePostalAddress(Supervisor supervisor)throws Exception {
    	                  contactImpl.updatePostalAddress(supervisor);
           }
           public void insertPhysicalAddress(Supervisor supervisor)throws Exception {
    	                  contactImpl.insertPhysicalAddress(supervisor);
          }
          public void updatePhysicalAddress(Supervisor supervisor)throws Exception {
    	                  contactImpl.updatePhysicalAddress(supervisor);
          }
          public void deleteAddress(Supervisor supervisor,int addressType)throws Exception {
    	                  contactImpl.deleteAddress(supervisor,addressType);
          }
          public void updateSupervisorPostalAddress(Supervisor supervisor)throws Exception {
        	            contactImpl.updatePostalAddress(supervisor);
          }
          public void updateSupervisorPhysicalAddress(Supervisor supervisor)throws Exception {
        	              contactImpl.updatePhysicalAddress(supervisor);
          }
          public void updateContactDetails(Supervisor supervisor)throws Exception {
        	               contactImpl.updateContactDetail(supervisor);
          }

}
