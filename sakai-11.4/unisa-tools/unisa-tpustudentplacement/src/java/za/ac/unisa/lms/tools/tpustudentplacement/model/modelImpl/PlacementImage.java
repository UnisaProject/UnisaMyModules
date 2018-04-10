package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.model.StudentPlacementLog;
public class PlacementImage {
	      private String  schoolCodeImage;
	      private String  supervisorCodeImage;
	      private String  startDateImage;
	      private String  endDateImage;
	      private String  numberOfWeeksImage;
	      private String  evaluationMarkImage;
	      private String  placementImage;
	      
	      
	      private StudentPlacementLog spl;
	      public PlacementImage(StudentPlacementLog spl){
	    	     this.spl=spl;
	      }
	     
	      
	     private String addFrondZeros(String str,int lenOfRequiredImage){
        	         String localStr=str;
        	         while(localStr.length()<lenOfRequiredImage){
        	    	       localStr="0"+localStr;
        	         }
        	         return localStr;
          }
          public String createSchoolCodeImage(){
              return addFrondZeros(""+spl.getSchoolCode(),9);
          }
          public String createSupervisorCodeImage(){
              return addFrondZeros(""+spl.getSupervisorCode(),9);
          }
          public String createStartDateImageImage(){
              return addFrondZeros(pruneDate(spl.getStartDate()),8);
          }
          public String createEndDateImage(){
              return addFrondZeros(pruneDate(spl.getEndDate()),8);
          }
          public String createNumberOfWeeksImage(){
        	      String numberOfWeeks=spl.getNumberOfWeeks();
        	      if((numberOfWeeks==null)||(numberOfWeeks.equals(""))){
        		      numberOfWeeks="00";
        	      }
                  return addFrondZeros(numberOfWeeks,2);
          }
          public String createEvaluationMarkImage(){
        	      String evaluationMark=spl.getEvaluationMark();
        	      if((evaluationMark==null)||(evaluationMark.equals(""))){
        		      evaluationMark="000";
        	      }
                  return addFrondZeros(evaluationMark,3);
          }
          public void createPlacementImage(){
        	             schoolCodeImage=createSchoolCodeImage();
        	             supervisorCodeImage=createSupervisorCodeImage();
        	             startDateImage=createStartDateImageImage();
        	             endDateImage=createEndDateImage();
        	             numberOfWeeksImage=createNumberOfWeeksImage();
        	             evaluationMarkImage=createEvaluationMarkImage();
        	             placementImage=schoolCodeImage+supervisorCodeImage+startDateImage+
        	                             endDateImage+numberOfWeeksImage+evaluationMarkImage;
        	             
          }
          public String getPlacementImage(){
        	              createPlacementImage();
        	              return  placementImage;
          }
          public void disassembleImage(String  placementImage){
         	            //breaks a given placement image into it's subcomponents
                    	// it then sets the data member of a placementLog object (spl)
        	            disassemble(placementImage);
        	              
           }
          public void disassembleAfterImage(){
	            //breaks a placement image member of   into it's subcomponents
        	     String afterImageStr=spl.getAfterImage();
        	     disassemble(afterImageStr); 
	      }
          private void disassemble(String  placementImage){
        	   //breaks the afterImage  data member of a placementLog object (spl)  into it's subcomponents
        	        if(placementImage.equals("")){
        	  	          return;
                    }
                    schoolCodeImage=placementImage.substring(0,9);
                    spl.setSchoolCode(Integer.parseInt(removeTrailingZeros(schoolCodeImage)));
    	            supervisorCodeImage=placementImage.substring(9,18);
    	            spl.setSupervisorCode(Integer.parseInt(removeTrailingZeros(supervisorCodeImage)));
    	            startDateImage=placementImage.substring(18,26);
    	            spl.setStartDate(removeTrailingZeros(startDateImage));
    	            endDateImage=placementImage.substring(26,34);
    	            spl.setEndDate(removeTrailingZeros(endDateImage));
    	            numberOfWeeksImage=placementImage.substring(34,36);
    	            spl.setNumberOfWeeks(removeTrailingZeros(numberOfWeeksImage));
    	            evaluationMarkImage=placementImage.substring(36);
    	            spl.setEvaluationMark(removeTrailingZeros(evaluationMarkImage));
          }

           private String removeTrailingZeros(String str){
        	                  int startIndex=-1;
        	                  for (int x=0;x<str.length();x++){
        	                	     if(str.charAt(x)!='0'){
        	                	    	 startIndex=x;
        	                	    	 break;
        	                	     }
        	                  }
        	                  if(startIndex!=-1){
        	                	  return   str.substring(startIndex);
        	                  }else{
        	                	  return "";
        	                  }
            }
          private String pruneDate(String dateStr){
        	               String newDateStr="";
        	               if((dateStr!=null &&(!dateStr.equals("")))){
        	                     newDateStr=dateStr.substring(0,4)+dateStr.substring(5,7)+dateStr.substring(8);
        	               }
        	    return newDateStr;
          }
          
          
}
