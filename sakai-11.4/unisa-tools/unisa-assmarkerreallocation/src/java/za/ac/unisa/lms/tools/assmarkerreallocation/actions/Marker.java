package za.ac.unisa.lms.tools.assmarkerreallocation.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import za.ac.unisa.lms.tools.assmarkerreallocation.dao.MarkerDAO;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkerDetailRecord;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkerModel;

public class Marker extends MarkerModel{
	public Marker(String acadYear,String  semester,String studyUnit, Integer assessUniqueNum,String assNum){
		         dao=new MarkerDAO();
		          year=Short.parseShort(acadYear);
		          dummyAcadYear=Short.parseShort(acadYear);
                  semesterPeriod=Short.parseShort(semester);
                  this.assNum=Short.parseShort(assNum);
                  this.assessUniqueNum= assessUniqueNum;
                  this.studyUnit=studyUnit;
    }
	public Marker(String acadYear,String  semester,String studyUnit){
		       this.studyUnit=studyUnit;
		       year=Short.parseShort(acadYear);
               semesterPeriod=Short.parseShort(semester);
               dao=new MarkerDAO();
	 }
	 short year;
	 short dummyAcadYear;
     Integer assessUniqueNum;
     short semesterPeriod;
     String studyUnit;
     Short assNum;
     MarkerDAO dao;
     
     public void checkMarkerResignDate(MarkerModel marker){
         if (marker.getPerson().getResignDate()==null 
              || marker.getPerson().getResignDate().equalsIgnoreCase("") 
                 || marker.getPerson().getResignDate().equalsIgnoreCase("00010101")){
                      marker.getPerson().setResignDate("n/a");
         }
    }
    public Short  getPersonnelNum(){
		      return  Short.parseShort(getPerson().getPersonnelNumber());
	}
	public List getPotentialMarkers(String loginMode) throws Exception {
		                   return dao.getPotentialMarkers(year, semesterPeriod, studyUnit,assessUniqueNum,loginMode);
	}
	private List getCurrMarkers(String loginMode,boolean langDisplayed) throws Exception{//
	             	 return dao.getMarkers(dummyAcadYear,year,semesterPeriod,assessUniqueNum, studyUnit,loginMode,langDisplayed);
    }
	public boolean isAuthorised(String novellid) {
		              return dao.isAuthorised(novellid, studyUnit, year,semesterPeriod);
	}
	private List getMarkers(String loginMode,boolean langDisplayed) throws Exception{//use in edit
		            List listPotentialMarkers =getPotentialMarkers(loginMode);
		            List currMarkersList = getCurrMarkers(loginMode,langDisplayed);
		            for (int i=0; i < listPotentialMarkers.size(); i++){
			              MarkerModel  potentialMarker = (MarkerModel)listPotentialMarkers.get(i);
			              if (!isPotentialMarkerAcurrMarker(potentialMarker,currMarkersList)){
			                    currMarkersList.add(potentialMarker);
			              }
	                 }
		            ensureNoDuplicateMarker(currMarkersList);
		          return currMarkersList;
    }
	private boolean isPotentialMarkerAcurrMarker(MarkerModel potentialMarker, List currMarkersList){
		                  boolean markerFound = false;		
         		          for (int j=0; j < currMarkersList.size(); j++){
                               MarkerModel marker = (MarkerModel)currMarkersList.get(j);
                               if (potentialMarker.getPerson().getPersonnelNumber().trim().equals(
                            		             marker.getPerson().getPersonnelNumber().trim())){
	                                   markerFound=true;
	                                   break;
                                }				
                          }
         		          return markerFound;
	}
	private  void ensureNoDuplicateMarker(List currMarkersList){
		               List duplicatesList=new ArrayList();
		               for (int j=0; j < currMarkersList.size(); j++){
        	                    MarkerModel marker = (MarkerModel)currMarkersList.get(j);
        	                    getPosOfDuplicates(marker,currMarkersList,j,duplicatesList);
                       }
		               removeDuplicates(duplicatesList,currMarkersList);
   }
	private void removeDuplicates(List duplicatesList,List currMarkersList){
                            for (int j=0; j < duplicatesList.size(); j++){
                            	int index=Integer.parseInt(duplicatesList.get(j).toString());
                            	currMarkersList.remove(index);
                            }
  }
	private void getPosOfDuplicates(MarkerModel potentialMarker,List currMarkersList,int posToSkip,List duplicatePosList){
		            for (int j=0; j < currMarkersList.size(); j++){
		            	 MarkerModel marker = (MarkerModel)currMarkersList.get(j);
                         if(posToSkip!=j){
                                 if (potentialMarker.getPerson().getPersonnelNumber()== marker.getPerson().getPersonnelNumber()){
                            	      if(Integer.parseInt(marker.getMarkPercentage())==0){
                        	                duplicatePosList.add(j);
                            	      }
                                 }
                    	}
                   }				
   }
   public String getPrimaryLecturer()throws Exception{
                         return  dao.getPrimaryLecturer(studyUnit,year,semesterPeriod);
    } 
	public List getMarkerList(String loginMode,boolean langDisplayed) throws Exception{
		             return getMarkers(loginMode,langDisplayed);
	}
	public  List getLangLinkedWithMarker(Integer personellNum,Integer assUniqNum)throws Exception{
		              return dao.getLangLinkedWithMarker(studyUnit, year,semesterPeriod, personellNum, assUniqNum);
	}
	public  List setLangLinkedWithMarker(Integer personellNum,Integer assUniqNum)throws Exception{
         return dao.getLangLinkedWithMarker(studyUnit, year,semesterPeriod, personellNum, assUniqNum);
   }	  
}
