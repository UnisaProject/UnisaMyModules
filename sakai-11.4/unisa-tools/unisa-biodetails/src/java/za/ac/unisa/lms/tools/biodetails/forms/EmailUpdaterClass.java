package za.ac.unisa.lms.tools.biodetails.forms;

import za.ac.unisa.lms.tools.biodetails.dao.ContactDetailsDAO;
import Srads01h.Abean.Srads01sMntAddress;
import Srcds01h.Abean.Srcds01sMntStudContactDetail;

public class EmailUpdaterClass {
	
	
	   public  void updateEmailAddress(String studentNr,String emailAddress) throws Exception{
                      ContactDetailsDAO  dao=new ContactDetailsDAO();
                      dao.updateADRPH(studentNr,emailAddress);
                      dao.writeEmailToVault(studentNr, emailAddress);
       }  
	   public  void updateEmailAddress(BioDetailsForm bioDetailsForm) throws Exception{
	                    if((bioDetailsForm.getNonMyLifeEmail()!=null)&&
				           (!bioDetailsForm.getNonMyLifeEmail().trim().equals(""))){
                                updateEmailAddress(bioDetailsForm.getNumber(),bioDetailsForm.getNonMyLifeEmail());
                                bioDetailsForm.setEmail(bioDetailsForm.getNonMyLifeEmail());
                        }
       }
	   public void resetNonMylifeEmailAddress(BioDetailsForm bioDetailsForm){
		               bioDetailsForm.setNonMyLifeEmail("");
	   }
	   public  void updateOldEmailAddressOnForm(BioDetailsForm bioDetailsForm,Srcds01sMntStudContactDetail op) throws Exception{
                    if((bioDetailsForm.getNonMyLifeEmail()!=null)&&
	                    (!bioDetailsForm.getNonMyLifeEmail().trim().equals(""))){
                    }else{
                    	      bioDetailsForm.setOldEmail(op.getOutContactsWsAddressV2EmailAddress());
                    }
       }
	   public void setEmailAddressForAddressActionProxy( BioDetailsForm bioDetailsForm,Srads01sMntAddress op,
			                String emailAddress)throws Exception{
		                             if(emailAddress.indexOf("mylife")!=-1){
			                                op.setInWsAddressV2EmailAddress(emailAddress);
			                         }else{
				                             bioDetailsForm.setNonMyLifeEmail(emailAddress);
			                        }
	   }
	   public void setEmailAddressForContactDetailsActionProxy( BioDetailsForm bioDetailsForm,Srcds01sMntStudContactDetail op,
                        String emailAddress)throws Exception{
                                    if(emailAddress.indexOf("mylife")!=-1){
                                            op.setInWsAddressV2EmailAddress(emailAddress);
                                    }else{
	                                        bioDetailsForm.setNonMyLifeEmail(emailAddress);
                                    }
      }

}
