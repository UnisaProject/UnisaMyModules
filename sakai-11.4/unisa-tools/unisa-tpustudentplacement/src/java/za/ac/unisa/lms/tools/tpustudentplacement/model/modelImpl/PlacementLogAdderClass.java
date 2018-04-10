package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.StudentPlacementLogDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementImage;

public class PlacementLogAdderClass {
	
	      StudentPlacementLogDAO dao;
	      private int personnelNum;
	      private int academicYear;
	      private int semester;
	      private String emailAddress;
	      private String cellNum;
	      private StudentPlacement sp;
	      public  PlacementLogAdderClass(){ 
	    	      dao=new StudentPlacementLogDAO();
	      }
	      private String  getImageForCreatePlacement(StudentPlacement spl){ 
		                String afterImage="";
                        try{
        	                   StudentPlacementImage placementImage=new StudentPlacementImage(spl);
                               afterImage=placementImage.getPlacementImage();
                               return afterImage;
                         }catch(Exception ex){
        	                       return afterImage=""; 
                         }
            }
	        public void setLogOnNewPlacement(StudentPlacementForm studentPlacementForm)throws Exception{
            	             initialiseVariasbles(studentPlacementForm);
            	             String afterImage=getImageForCreatePlacement(sp);
                             dao.addLogForCreateNewPlacementAction(sp,academicYear,semester,afterImage,personnelNum);
           }
            public void setLogOnDeletePlacement(StudentPlacementForm studentPlacementForm)throws Exception{
            	             initialiseVariasbles(studentPlacementForm);
            	             String afterImage=getImageForCreatePlacement(sp);
                             dao.addLogForDeletePlacementAction(sp,academicYear,semester,afterImage, personnelNum);
            }
            public void setLogOnEmailToStu(StudentPlacementForm studentPlacementForm)throws Exception{
            	            initialiseVariasbles(studentPlacementForm);
            	            String afterImage=getImageForCreatePlacement(sp);
                            dao.setLogOnEmailToStu(sp,academicYear,semester,emailAddress,afterImage,personnelNum);
	        }
            public void setLogOnSmsToStu(StudentPlacementForm studentPlacementForm)throws Exception{
            	             initialiseVariasbles(studentPlacementForm);
            	             String afterImage=getImageForCreatePlacement(sp);
                             dao.setLogOnSmsToStu(sp,academicYear,semester,cellNum,afterImage,personnelNum);
            }
            public void setLogOnEmailToSchool(StudentPlacementForm studentPlacementForm)throws Exception{
            	            initialiseVariasbles(studentPlacementForm);
            	            String afterImage=getImageForCreatePlacement(sp);
                            dao.setLogOnEmailToSchool(sp,academicYear,semester,emailAddress,afterImage,personnelNum);  
           }
            public void setLogOnUpdatePlacement(StudentPlacementForm studentPlacementForm)throws Exception{
            	               initialiseVariasbles(studentPlacementForm);
            	               String placementImage=studentPlacementForm.getPlacementImage();
            	               String afterImage=getImageForCreatePlacement(sp);
                               String beforeImage=placementImage;//dao.getPlacementImage(placementImageInteger.parseInt(sp.getStuNum()),sp.getSchoolCode(),sp.getModule());
                               dao.addLogForUpdatePlacementAction(sp,academicYear,semester, beforeImage, afterImage, personnelNum);
           }
           private void initialiseVariasbles(StudentPlacementForm studentPlacementForm){
                            personnelNum=Integer.parseInt(studentPlacementForm.getPersonnelNumber());
                            semester=Integer.parseInt(studentPlacementForm.getSemester());
                            academicYear=Integer.parseInt(studentPlacementForm.getAcadYear());
                            emailAddress=studentPlacementForm.getCommunicationEmailAddress();
                            cellNum=studentPlacementForm.getCommunicationCellNumber();
                            sp=studentPlacementForm.getStudentPlacement();
                            sp.setStuNum(studentPlacementForm.getStudentNr());
           }
}
