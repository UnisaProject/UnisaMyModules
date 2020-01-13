package za.ac.unisa.lms.tools.tpustudentplacement.utils;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.PlacementListRecord;
import java.util.List;
import java.util.Iterator;

public class PlacementListRecComparator {
	   
	public boolean  PlacementListRecsEquals(PlacementListRecord originalStuPlacement,PlacementListRecord stuPlacement){
            if((originalStuPlacement.getStudentName().equals(stuPlacement.getStudentName()))&&
        		(originalStuPlacement.getModule().equals(stuPlacement.getModule()))&&
                (originalStuPlacement.getStudentNumber()==stuPlacement.getStudentNumber())&&
                (originalStuPlacement.getSchoolContactNumber().equals(stuPlacement.getSchoolContactNumber()))&&
                (originalStuPlacement.getStudentContactNumber().equals(stuPlacement.getStudentContactNumber()))&&
                (originalStuPlacement.getDistrictCode()==stuPlacement.getDistrictCode())&&
                (originalStuPlacement.getProvinceCode()==stuPlacement.getProvinceCode())&&
                (originalStuPlacement.getCountryCode()==stuPlacement.getCountryCode())&&
                (originalStuPlacement.getSchoolCode()==stuPlacement.getSchoolCode())&&
                (originalStuPlacement.getEndDate().equals(stuPlacement.getEndDate()))&&
                (originalStuPlacement.getStartDate().equals(stuPlacement.getStartDate()))&&
                (originalStuPlacement.getSupervisorCode()==stuPlacement.getSupervisorCode())&&
                (originalStuPlacement.getEvaluationMark().equals(stuPlacement.getEvaluationMark()))&&
                (originalStuPlacement.getNumberOfWeeks().equals(stuPlacement.getNumberOfWeeks()))
                ){
                	return true;
                }else{
                	return false;
                }
     }
     public int PlacementListRecsEquals(PlacementListRecord originalStuPlacement,List<PlacementListRecord> listPlacement){
    	                     Iterator i=listPlacement.iterator();
    	                     int x=0;
    	                     boolean found=false;
    	                     while(i.hasNext()){
    	                    	     PlacementListRecord stuPlacementListRec=(PlacementListRecord)i.next();
    	                    	     found=PlacementListRecsEquals(originalStuPlacement,stuPlacementListRec);
    	                    	    if(found){
    	                    	    	break;
    	                    	    }
    	                    	    x++;
    	                     }
    	                     if(!found){
    	                    	 x=-1;
    	                     }
    	                     return x;
     }

}
