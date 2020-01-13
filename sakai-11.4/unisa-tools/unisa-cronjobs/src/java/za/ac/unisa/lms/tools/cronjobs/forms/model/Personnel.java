package za.ac.unisa.lms.tools.cronjobs.forms.model;

import za.ac.unisa.lms.tools.cronjobs.tpuModel.tpuModelImpl.PersonnelImpl;

public class Personnel {


               protected String name;
               protected String networkCode;
               protected String workStationCode;
               protected String workStationDescr;
               protected String personnelNumber;
               protected String contractExpiryDate;
               protected String emailAddress;
               protected String contactNumber;
               PersonnelImpl pImpl;
        
	       
              public Personnel(){
	                   pImpl=new PersonnelImpl();
              }
              public String getEmailAddress(){
            	  return emailAddress;
              }
              public void setEmailAddress(String emailAddress){
            	  this.emailAddress=emailAddress;
              }
              public String getContactNumber(){
            	  return contactNumber;
              }
              public void setContactNumber(String contactNumber){
            	  this.contactNumber=contactNumber;
              }
              public void setContractExpiryDate(String contractExpiryDate){
	               this.contractExpiryDate=contractExpiryDate;
              }
              public String getContractExpiryDate(){
	                   return contractExpiryDate;
              }
              public void setName(String name){
	               this.name=name;
              }
              public String getName(){
	                  return name;
              }
              public void setNetworkCode(String networkCode){
	                       this.networkCode=networkCode;
              }
              public String getNetworkCode(){
	                     return networkCode;
              }
              public void setWorkStationCode(String workStationCode){
	                       this.workStationCode=workStationCode;
              }
              public String getWorkStationCode(){
	                     return workStationCode;
              }
              public void setWorkStationDescr(String workStationDescr){
                            this.workStationDescr=workStationDescr;
              }
              public String getWorkStationDescr(){
                       return workStationDescr;
              }
              public void setPersonnelNumber(String personnelNumber){
                    this.personnelNumber=personnelNumber;
              }
              public String getPersonnelNumber(){
                       return personnelNumber;
              }
              public String getPersonnelNumber(String networkCode) throws Exception{
                         return pImpl.getPersonnelNumber(networkCode);
              }
              public String getNetworkCode(String personnelNumber) throws Exception{
                       return pImpl.getNetworkCode(personnelNumber);
              }
}