package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
public class PrelimStudentPlacementImpl {
	
	
	public int getPosOfSelectedPlacement(List listPlacementListRecord,HttpServletRequest request){
		                   return Integer.parseInt(request.getParameter("indexInList"));
		                  
    }
	public String prevBtnBoundryReached(int pos){
                           String  boundryReached="N";
	                       if(pos==0){
	                    	    boundryReached="Y";
                           }
	                       return boundryReached;
	}
	public String nextBtnBoundryReached(int pos,List listOfPlacements){
                      String  boundryReached="N";
                      if( listOfPlacements!=null){
                           if(pos==(listOfPlacements.size()-1)){
                       	         boundryReached="Y";
                           }
	                   }
                       return boundryReached;
    }
	
}
