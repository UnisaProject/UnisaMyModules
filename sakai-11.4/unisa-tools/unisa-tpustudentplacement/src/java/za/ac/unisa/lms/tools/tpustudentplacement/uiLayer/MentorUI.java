package za.ac.unisa.lms.tools.tpustudentplacement.uiLayer;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.MentorModel;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.CommValidator;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.SelectionValidator;

public class MentorUI extends MentorModel{
	public  void validateMentor(MentorModel mentor,ActionMessages messages){
        InfoMessagesUtil  infoMessagesUtil =new InfoMessagesUtil();
        String message="Mentor initials are required";
        if (mentor.getInitials()==null || 
        		mentor.getInitials().trim().equalsIgnoreCase("")){
 		              infoMessagesUtil.addMessages(messages,message);
	    }
        if (mentor.getSurname()==null ||
        		mentor.getSurname().trim().equalsIgnoreCase("")){
				         message="Mentor surname is required";
			    	      infoMessagesUtil.addMessages(messages,message);
        }
        CommValidator comvalidator=new CommValidator();
        comvalidator.validateCellNr(mentor.getCellNumber(),mentor.getCountryCode(),messages);
        comvalidator.validateEmailAddressr(mentor.getEmailAddress(),messages);
      }
	
	   public MentorModel getSelectedMentor(StudentPlacementForm studentPlacementForm){
		                  MentorModel mentorModel= new MentorModel();
                          for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {			
                                  String array[] = studentPlacementForm.getIndexNrSelected();
                                  int index=Integer.parseInt(array[i]);
                                  mentorModel = (MentorModel)studentPlacementForm.getListMentor().get(index);			
                                  i=studentPlacementForm.getIndexNrSelected().length;
                          }
                          return mentorModel;
      }
	   public void validateIndexArrForSelection(String[] indexArr,ActionMessages messages){
                          SelectionValidator selectionValidator=new SelectionValidator();
                          String noSelectionMessage="Please select a mentor";
                          String tooManySelectionsMessage="Please select only one mentor";
                          selectionValidator.validateIndexArrForSelection(indexArr, messages,noSelectionMessage,tooManySelectionsMessage);
       }
       public void validateIndexArrForAddOrView(String[] indexArr,ActionMessages messages){ 
                         SelectionValidator selectionValidator=new SelectionValidator();
                         String messageForAddView= "Please select a mentor to view or edit";
	                     String secMessageForAddView= "Please select only one mentor to view or edit";
			             selectionValidator.validateIndexArrForAddOrView(indexArr, messages,messageForAddView,secMessageForAddView);
      }

       public void setMentorTrainedList(StudentPlacementForm studentPlacementForm){
           if((studentPlacementForm.getListMentorTrained()==null)||
	               (studentPlacementForm.getListMentorTrained().isEmpty())){
                  List listMentorTrained=new ArrayList();
                   listMentorTrained.add(new LabelValueBean("Yes", "Y"));
                  listMentorTrained.add(new LabelValueBean("No", "N"));
                  studentPlacementForm.setListMentorTrained(listMentorTrained);
        }

      }
       public void refresh(StudentPlacementForm studentPlacementForm ){
   	    	studentPlacementForm.getMentorModel().setCountryCode("1015");
           studentPlacementForm.getMentorModel().setTrained("N");;
   	       studentPlacementForm.getMentorModel().setSchoolCode(0);
   	       studentPlacementForm.getMentorModel().setSchoolName("");
   		   studentPlacementForm.getMentorModel().setEmailAddress("");
   		   studentPlacementForm.getMentorModel().setCellNumber("");
   		   studentPlacementForm.getMentorModel().setPhoneNumber("");
   		   studentPlacementForm.getMentorModel().setFaxNumber("");
   		   studentPlacementForm.getMentorModel().setInitials("");
   		   studentPlacementForm.getMentorModel().setSurname("");
   		   studentPlacementForm.getMentorModel().setTitle("");
   		   studentPlacementForm.getMentorModel().setOccupation("");
   		   studentPlacementForm.getMentorModel().setMentorCode(0);
             studentPlacementForm.getMentorFilterData().setMentorFilterCountry("1015");
           studentPlacementForm.getMentorFilterData().setMentorFilterProvince(Short.parseShort("0"));
           studentPlacementForm.getMentorFilterData().setMentorFilterDistrict(Short.parseShort("0"));
           studentPlacementForm.getMentorFilterData().setMentorFilterSchoolCode(Integer.parseInt("0"));
           studentPlacementForm.getMentorFilterData().setMentorFilterDistrictValue("");
           studentPlacementForm.getMentorFilterData().setMentorDistrictFilter("");
           studentPlacementForm.getMentorFilterData().setMentorFilterSchoolValue("");
   	}
   	
}
