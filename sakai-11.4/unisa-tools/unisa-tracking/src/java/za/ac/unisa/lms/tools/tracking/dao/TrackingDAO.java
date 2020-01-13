package za.ac.unisa.lms.tools.tracking.dao;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import za.ac.unisa.lms.tools.tracking.actions.TrackingAction;
import za.ac.unisa.lms.tools.tracking.forms.TrackingForm;

public class TrackingDAO {

	WebServiceGateWay pGateWay = new WebServiceGateWay();
	private Log log = LogFactory.getLog(TrackingDAO.class.getName());
	
	 public ArrayList getSearch(String webServiceURL,String searchString) throws Exception {
		 int searchWebserviceId = 22222;
		 log.info("TrackingDAO - getSearch - webServiceURL: " + webServiceURL + ", searchString: " + searchString);
		  ArrayList<Object> resultList = new ArrayList<Object>();
			 ArrayList<KeyValue> resultvalues = pGateWay.getSearch(webServiceURL,searchWebserviceId, searchString, "value1","value2");
			   Iterator<KeyValue> result = resultvalues.iterator();
				 
				 while(result.hasNext()){
					
					KeyValue test2 = (KeyValue) result.next();
					resultList.add(new org.apache.struts.util.LabelValueBean(test2.getValue(),test2.getValue()));
				 }
	      return resultList ;
	 }
	 
	 public String creatingShiplistNumber(String webServiceURL , String networkId) throws Exception{
		 int webServiceID = 882045;
		 String shipListNumber = null ;
		 String creationErrorStatus = null ;
		 ArrayList shipNumber = pGateWay.CreateConsignmentListNumber(webServiceURL ,webServiceID,networkId.toUpperCase(), "id", "value1");
         Iterator it1 = shipNumber.iterator();
         if(it1.hasNext())
         {
             while(it1.hasNext()) 
             {
                 KeyValue test2 = (KeyValue)it1.next();
                 if(test2.getValue().contains("FromUser invalid"))
                 {
                     creationErrorStatus = test2.getValue();
                     log.info("TrackingDAO - creatingShiplistNumber - FromUser invalid "+creationErrorStatus);
                 } else
                 {
                     if(test2.getKey().equals("2")){
                     	creationErrorStatus  = test2.getValue();
                     }else {
                 	shipListNumber = test2.getValue();
                     }
                     log.info("TrackingDAO - creatingShiplistNumber - Successfully ShipListnumber Created "+shipListNumber);
                 }
             }
         }else{
             creationErrorStatus = pGateWay.shipListCreationCheck(webServiceURL ,webServiceID,networkId.toUpperCase());
             log.info("User not entered anything so created new ShipList number "+creationErrorStatus);
         }
		 if(shipListNumber != null && !shipListNumber.equals("")){
			 log.info("TrackingDAO - creatingShiplistNumber - creatingShiplistNumber Successful - ShipListnumber="+shipListNumber);
			 return shipListNumber ;
		 }else{
			 log.info("TrackingDAO - creatingShiplistNumber - creatingShiplistNumber Failed - creationErrorStatus="+creationErrorStatus);
			 return creationErrorStatus;
		 }
        
	 }
	 
	 public boolean checkNumbersOnly(String shipList){
		 for (int i = 0; i < shipList.length(); i++) {
			      if (!Character.isDigit(shipList.charAt(i)))
			        return false;
			    }
			    return true;
			  }

	 
	 public void setAddress(ArrayList<KeyValue> userAdress , ActionForm form){
		  //system.out.println("entered here");
		   Iterator userAdresses = userAdress.iterator();
		   TrackingForm pTrackingFrom = (TrackingForm)form ;
		   while(userAdresses.hasNext()){
				
				KeyValue test = (KeyValue) userAdresses.next();
				if(test.getValue() != null &&  ! test.getValue().isEmpty()){
				   //System.out.println("test1");
					if(!test.getValue().equals("null")){
					//system.out.println(test.getValue());
						pTrackingFrom.setUserAddress1(test.getValue());
					}else{
						pTrackingFrom.setUserAddress1("");
					}
				}else{
					//System.out.println("test1 else");
					pTrackingFrom.setUserAddress1("");
				}
				if(test.getValue1() != null &&  ! test.getValue1().isEmpty()){
					if(!test.getValue1().equals("null")){
						pTrackingFrom.setUserAddress2(test.getValue1());
					}else{
						pTrackingFrom.setUserAddress2("");
					}
				}else{
					pTrackingFrom.setUserAddress2("");
				}
				if(test.getValue2() != null &&  ! test.getValue2().isEmpty()){
					if(!test.getValue2().equals("null")){
						pTrackingFrom.setUserAddress3(test.getValue2());
					}else{
						pTrackingFrom.setUserAddress3("");
					}
				}else{
					pTrackingFrom.setUserAddress3("");
				}
				if(test.getValue3() != null &&  ! test.getValue3().isEmpty()){
					if(!test.getValue3().equals("null")){
						pTrackingFrom.setUserAddress4(test.getValue3());
					}else{
						pTrackingFrom.setUserAddress4("");
					}
				}else{
					pTrackingFrom.setUserAddress4("");
				}
				if(test.getValue4() != null &&  ! test.getValue4().isEmpty()){
					if(!test.getValue4().equals("null")){
						pTrackingFrom.setUserAddress5(test.getValue4());
					}else{
						pTrackingFrom.setUserAddress5("");
					}
				}else{
					pTrackingFrom.setUserAddress5("");
				}
				
				
			        
	     }
	   }
}
