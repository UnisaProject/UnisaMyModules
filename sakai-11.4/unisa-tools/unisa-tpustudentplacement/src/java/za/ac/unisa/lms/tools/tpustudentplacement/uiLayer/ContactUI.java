package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;

import java.util.ArrayList;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Address;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.ContactImpl;

public class ContactUI {
	
	
	           ContactImpl contactImpl;
	           public ContactUI(){
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

}
