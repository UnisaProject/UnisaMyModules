package org.sakaiproject.studymaterial.utils;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.sakaiproject.studymaterial.module.StudyMaterialModel;

public class SortUtil {

	public SortUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public List<StudyMaterialModel>  sortByDate(List<StudyMaterialModel> list){
		        		            List   sortedList=new ArrayList<StudyMaterialModel>();
		                            while(!list.isEmpty()){
			                              int index=getLatestAvailDate_UnitNum(list);
			                              sortedList.add(list.get(index));
			                              list.remove(index);
		                            }
		                       return  sortedList;
		
	}
	private int getLatestAvailDate_UnitNum(List<StudyMaterialModel> list){
		int indexOfSM=0;
		DateUtil dateutil=new DateUtil();
		Date frstSmAvailDate=dateutil.getDateFromStr(list.get(0).getDateAvailable());
		String smUnitNum=list.get(0).getUnitNumber();
		String language=list.get(0).getLanguage();
		for(int x=0;x<list.size();x++){
			  StudyMaterialModel StudyMaterialModel=(StudyMaterialModel)list.get(x);
			  if(x!=0){
				  Date nextSmAvailDate=dateutil.getDateFromStr(StudyMaterialModel.getDateAvailable());
				  if(nextSmAvailDate.after(frstSmAvailDate)){
					  frstSmAvailDate=nextSmAvailDate;
					  indexOfSM=x;
					  smUnitNum=StudyMaterialModel.getUnitNumber();
					  language=list.get(x).getLanguage();
				  }else if(nextSmAvailDate.equals(frstSmAvailDate)){
					       if(Integer.parseInt(StudyMaterialModel.getUnitNumber())>Integer.parseInt(smUnitNum)){
					    	      frstSmAvailDate=nextSmAvailDate;
								  indexOfSM=x;
								  smUnitNum=StudyMaterialModel.getUnitNumber();
								  language=list.get(x).getLanguage();
					       }else if(Integer.parseInt(StudyMaterialModel.getUnitNumber())==Integer.parseInt(smUnitNum)){
					    	      if(list.get(x).getLanguage().compareToIgnoreCase(language)>0){
					    	    	  frstSmAvailDate=nextSmAvailDate;
									  indexOfSM=x;
									  smUnitNum=StudyMaterialModel.getUnitNumber();
									  language=list.get(x).getLanguage();
					    	      }
					    	      
					       }
				  }
			  }
		}
		return indexOfSM;
	}
}
