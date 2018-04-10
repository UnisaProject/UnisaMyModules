package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl;
import  za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;

public class StudentPlacementImage {
		      private String  schoolCodeImage;
		      private String  supervisorCodeImage;
		      private String  startDateImage;
		      private String  endDateImage;
		      private String  numberOfWeeksImage;
		      private String  evaluationMarkImage;
		      private String  placementImage;
		      
		      
		      private StudentPlacement sp;
		      public StudentPlacementImage(StudentPlacement sp){
		    	     this.sp=sp;
		      }
		     
		      
		     private String addFrondZeros(String str,int lenOfRequiredImage){
	        	         String localStr=str;
	        	         while(localStr.length()<lenOfRequiredImage){
	        	    	       localStr="0"+localStr;
	        	         }
	        	         return localStr;
	          }
	          public String createSchoolCodeImage(){
	              return addFrondZeros(""+sp.getSchoolCode(),9);
	          }
	          public String createSupervisorCodeImage(){
	              return addFrondZeros(""+sp.getSupervisorCode(),9);
	          }
	          public String createStartDateImageImage(){
	              return addFrondZeros(pruneDate(sp.getStartDate()),8);
	          }
	          public String createEndDateImage(){
	              return addFrondZeros(pruneDate(sp.getEndDate()),8);
	          }
	          public String createNumberOfWeeksImage(){
	        	      String numberOfWeeks=sp.getNumberOfWeeks();
	        	      if((numberOfWeeks==null)||(numberOfWeeks.equals(""))){
	        		      numberOfWeeks="00";
	        	      }
	                  return addFrondZeros(numberOfWeeks,2);
	          }
	          public String createEvaluationMarkImage(){
	        	      String evaluationMark=sp.getEvaluationMark();
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
	                    	// it then sets the data member of a placementLog object (sp)
	        	            disassemble(placementImage);
	        	              
	           }
	          private void disassemble(String  placementImage){
	        	   //breaks the afterImage  data member of a placementLog object (sp)  into it's subcomponents
	        	        if(placementImage.equals("")){
	        	  	          return;
	                    }
	                    schoolCodeImage=placementImage.substring(0,9);
	                    sp.setSchoolCode(Integer.parseInt(removeTrailingZeros(schoolCodeImage)));
	    	            supervisorCodeImage=placementImage.substring(9,18);
	    	            sp.setSupervisorCode(Integer.parseInt(removeTrailingZeros(supervisorCodeImage)));
	    	            startDateImage=placementImage.substring(18,26);
	    	            sp.setStartDate(removeTrailingZeros(startDateImage));
	    	            endDateImage=placementImage.substring(26,34);
	    	            sp.setEndDate(removeTrailingZeros(endDateImage));
	    	            numberOfWeeksImage=placementImage.substring(34,36);
	    	            sp.setNumberOfWeeks(removeTrailingZeros(numberOfWeeksImage));
	    	            evaluationMarkImage=placementImage.substring(36);
	    	            sp.setEvaluationMark(removeTrailingZeros(evaluationMarkImage));
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
