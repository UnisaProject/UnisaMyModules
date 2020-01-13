package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.PersonnelDAO;

public class PersonnelImpl {


	               private String name;
	               private String networkCode;
	               private String workStationCode;
	               private String workStationDescr;
	               private String personnelNumber;
	               private String contractExpiryDate;
	               PersonnelDAO  dao;
	        
		       
	              public PersonnelImpl(){
	            	       dao=new PersonnelDAO();
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
	                    return dao.getPersonnelNumber(networkCode);
	     }
	     public String getPersonnelNumber(int persno) throws Exception{
                          return dao.getPersonnelNumber(persno);
         }
	     public String getNetworkCode(String personnelNumber) throws Exception{
	                     return dao.getNetworkCode(personnelNumber);
	     }
	}