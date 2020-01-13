package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Supervisor;

import java.util.ArrayList;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Address;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.ContactDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.databaseUtils;

public class ContactImpl {
	                   ContactDAO  dao;
	                   public ContactImpl(){
	                	     dao=new ContactDAO();
	                   }
	                    public Contact getContactDetails(Integer referenceNo,Integer category) throws Exception {
	                    		             return getContactDetails(referenceNo,category);	
	              	    }
	                    public ArrayList getPostalCodeList(String searchType, String searchCriteria, String postalType) throws Exception {
	                                         return getPostalCodeList(searchType, searchCriteria, postalType);
	                    }
	                    public Address getAddress(Integer referenceNo,Integer category,Integer type) throws Exception {
	              		    		              return getAddress(referenceNo,category,type);	
	                    }
	                    public int countRecords(int reference,int category)throws Exception {
	                    	      return dao.countRecords(reference, category);
	                    }
	                    public void insertContactDetail(Supervisor supervisor)throws Exception {
	                    	            dao.insertContactDetail(supervisor);
                        }
                        public void updateContactDetail(Supervisor supervisor)throws Exception {
                        	            dao.updateContactDetail(supervisor);
           	            }
                        public  int totRecsOfAddress(int supervCode,int addressType)throws Exception {
           	                         return dao.totRecsOfAddress(supervCode,addressType);
                        }
                        public void insertPostalAddress(Supervisor supervisor)throws Exception {
           	                           dao.insertPostalAddress(supervisor);     
                        }
                        public void updatePostalAddress(Supervisor supervisor)throws Exception {
       	                               dao.updatePostalAddress(supervisor);
                        }
                        public void insertPhysicalAddress(Supervisor supervisor)throws Exception {
       	                                dao.insertPhysicalAddress(supervisor);
                        }
                        public void updatePhysicalAddress(Supervisor supervisor)throws Exception {
       	                               dao.updatePhysicalAddress(supervisor);
                        }
                        public void deleteAddress(Supervisor supervisor,int addressType)throws Exception {
       	                            dao.deleteAddress(supervisor,addressType);
                        }
                        public void updateSupervisorPostalAddress(Supervisor supervisor)throws Exception {
            	                       dao.updatePostalAddress(supervisor);
                        }
                        public void updateSupervisorPhysicalAddress(Supervisor supervisor)throws Exception {
                        	          dao.updatePhysicalAddress(supervisor);
                       }
                       public void updateContactDetails(Supervisor supervisor)throws Exception {
        	                          dao.updateContactDetail(supervisor);
                       }
                	
}
