package za.ac.unisa.lms.tools.assmarkerreallocation.actions;
import java.util.ArrayList;
import java.util.List;
import za.ac.unisa.lms.tools.assmarkerreallocation.dao.AssMarkerReallocationDao;
import za.ac.unisa.lms.tools.assmarkerreallocation.dao.MarkingDetailDAO;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkerDetailRecord;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkerModel;

public class MarkingDetail {

	 int totalStudentSubmitted=0;	
	 int totalMarkPerc=0;
	 int studentsRegistered=0;
	 Short  acadyear;
     Short semesterPeriod;
     String  studyUnit;
     MarkingDetailDAO dao;
     Marker marker;
     public  MarkingDetail( Marker marker,String  year,String  period,String  studyUnit) {
		         dao=new  MarkingDetailDAO();
		         acadyear=Short.parseShort(year);
		         semesterPeriod=Short.parseShort(period);
		         this.studyUnit=studyUnit;
		         this.marker=marker;
	}
     public  MarkingDetail(String  year,String  period,String  studyUnit) {
         dao=new MarkingDetailDAO();
         acadyear=Short.parseShort(year);
         semesterPeriod=Short.parseShort(period);
         this.studyUnit=studyUnit;
     }
	//get number of students registered
	public int getNumOfStudentsRegistered()throws Exception{
		             return  dao.getStudentsRegistered(studyUnit,acadyear,semesterPeriod);
	}
	public void getSubmmitionDetails(List markerDetailList){
	                  //get total number of student assignments submitted
	                  //get total mark percentage assigned to markers
	                  for (int i=0; i < markerDetailList.size(); i++){
		                 MarkerDetailRecord markerDetail = new MarkerDetailRecord();
		                 markerDetail = (MarkerDetailRecord)markerDetailList.get(i);
		                 totalStudentSubmitted = totalStudentSubmitted + markerDetail.getStudentSubmit();
		                 totalMarkPerc=totalMarkPerc + Integer.parseInt(markerDetail.getMarker().getMarkPercentage().trim());			
	                 }
	}
	public void getMarkedPercentages(List markerDetailList)throws Exception{
	                 //if no mark percentage allocated, allocate 100% to primary lecturer. To Do!!!
	                 String primaryLecturer = "";
	                 if (totalMarkPerc==0){	
		                    primaryLecturer = marker.getPrimaryLecturer();
	                 }
	                 for (int i=0; i < markerDetailList.size(); i++){
		                  MarkerDetailRecord markerDetail = new MarkerDetailRecord();
		                  markerDetail = (MarkerDetailRecord)markerDetailList.get(i);
		                  markerDetail.setActualMarkedPerc(0);
		                  if (totalStudentSubmitted > 0) {	
		                    	//double percentage = ((Double.parseDouble(String.valueOf(markerDetail.getMarked()))/Double.parseDouble(String.valueOf(totalStudentSubmitted)))*100);
			                    int percentage = (int)(((((double)markerDetail.getMarked())/((double)totalStudentSubmitted))* 100) + 0.5);
			                    markerDetail.setActualMarkedPerc(percentage);
		                  }
		                  if (totalMarkPerc==0 && markerDetail.getMarker().getPerson().getNovellUserId().equalsIgnoreCase(primaryLecturer)){
			                     markerDetail.getMarker().setMarkPercentage("100");
		                  }
	                }
	}
	public List getMarkerDetailRecordList(String loginMode,String assNum,boolean langDisplayed) throws Exception{
		                 List markerList=marker.getMarkerList(loginMode,langDisplayed);
                         List markerDetailList = new ArrayList();
                         for (int i=0; i < markerList.size(); i++){
                                MarkerDetailRecord markerDetail = new MarkerDetailRecord();
                                MarkerModel markerModel = new MarkerModel();
                                markerModel = (MarkerModel)markerList.get(i);
                                marker.checkMarkerResignDate(markerModel);
                                Integer persno=Integer.parseInt(markerModel.getPerson().getPersonnelNumber());
                                Short assNumber=Short.parseShort(assNum);
                                markerDetail=getMarkerDetail(persno,assNumber);
                                markerDetail.setMarker(markerModel);
                                markerDetailList .add(markerDetail);			
                        }
                        Utilities.sortList(markerDetailList);
                        return markerDetailList;
   }
	public List getMarkingStatistics(String loginMode,String assNum,boolean langDisplayed) throws Exception{
	                   List markerDetailList= getMarkerDetailRecordList(loginMode,assNum,langDisplayed);
	                   getSubmmitionDetails(markerDetailList);
	                   getMarkedPercentages(markerDetailList);
	                   return markerDetailList;
   }
	
  public void updateMarkingRecords(List markingDetailList,String uniqueNr,boolean markingLnagExist) throws Exception{
	                 for (int i=0; i < markingDetailList.size(); i++){
			               MarkerDetailRecord markerDetail = (MarkerDetailRecord)markingDetailList.get(i);
			              MarkerModel markerModel=markerDetail.getMarker();
			              String status=markerModel.getStatus();
			                       String persno=markerModel.getPerson().getPersonnelNumber();
			                       AssMarkerReallocationDao dao = new AssMarkerReallocationDao();
			                       dao.updateAssMrk(acadyear,semesterPeriod,Integer.parseInt(uniqueNr),Integer.parseInt(persno),
					                       Short.parseShort(markerModel.getMarkPercentage()));
			                            MarkingLanguage  markingLang=new MarkingLanguage(studyUnit,acadyear,semesterPeriod,persno,uniqueNr);
			                           if(!(markerModel.getPrevMarkPercentage().trim().equals("0")&&markerModel.getMarkPercentage().trim().equals("0"))
			                            			 && (!status.equalsIgnoreCase("Inactive"))){
			                            	      if(markingLnagExist&&(markerModel.getChosenMarkingLanguageList()!=null)&&(markerModel.getChosenMarkingLanguageList().length!=0)){
				                                         markingLang.linkLangWithMarker(markerModel.getChosenMarkingLanguageList());
		                                          }
			                            	 }
		           }
  }
  public MarkerDetailRecord getMarkerDetail(Integer personeelNum,Short assNum)throws Exception{
                           return  dao.getMarkerDetail(studyUnit,acadyear,semesterPeriod, assNum, personeelNum);
  }

}
