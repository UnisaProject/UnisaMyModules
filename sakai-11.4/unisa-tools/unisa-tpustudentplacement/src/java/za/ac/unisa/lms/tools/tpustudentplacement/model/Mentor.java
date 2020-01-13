package za.ac.unisa.lms.tools.tpustudentplacement.model;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMessages;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.MentorDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.MentorFileWriter;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.MentorUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.FileExtractorClass;

public class Mentor extends MentorModel{
	    private MentorDAO mentorDAO;  
	    private MentorFileWriter mfw;
	    FileExtractorClass fileExtractorClass;
	    MentorUI mentorUI;
	    public  Mentor(){
		          mentorDAO=new MentorDAO();
		          mfw=new MentorFileWriter();
		          mentorUI=new MentorUI();
		          fileExtractorClass=new FileExtractorClass();
	    }
	    public  void validateMentor(MentorModel mentor,ActionMessages messages){
	    	            mentorUI.validateMentor(mentor, messages);
	      }
        public List   getMentors(MentorFilteringData filteringData)throws Exception{
        	//to get internationa mentors use this menthod
        	        return mentorDAO.getMentors(filteringData);
        }
        public MentorModel getSelectedMentor(StudentPlacementForm studentPlacementForm){
            return mentorUI.getSelectedMentor(studentPlacementForm);
        }
         public void validateIndexrForSelection(String[] indexArr,ActionMessages messages){
        	          mentorUI.validateIndexArrForSelection(indexArr, messages );
        }
         public void validateIndexArrForAddOrView(String[] indexArr,ActionMessages messages){ 
        	           mentorUI.validateIndexArrForAddOrView(indexArr, messages);
        }
          public MentorModel  getMentor(int mentorCode)throws Exception{
        	               mentorDAO.getMentor(mentorCode);
        	               MentorModel model=new MentorModel();
        	               model.setMentorCode(mentorDAO.getMentorCode());
        	               model.setCountryCode( mentorDAO.getCountryCode());
        	               model.setDistrictcode(mentorDAO.getDistrictcode());
        	               model.setInitials(mentorDAO.getInitials());
        	               model.setInUseFlag(mentorDAO.getInUseFlag());
        	               model.setTitle(mentorDAO.getTitle());
        	               model.setName(mentorDAO.getName());
        	               model.setOccupation(mentorDAO.getOccupation());
        	               model.setProvcode(mentorDAO.getProvcode());
        	               model.setSchoolCode(mentorDAO.getSchoolCode());
        	               model.setSurname(mentorDAO.getSurname());
        	               model.setTrained(mentorDAO.getTrained());
        	               model.setCellNumber(mentorDAO.getCellNumber());
        	               model.setPhoneNumber(mentorDAO.getPhoneNumber());
        	               model.setFaxNumber(mentorDAO.getFaxNumber());
        	               model.setEmailAddress(mentorDAO.getEmailAddress());
        	               return model;
        }
        public void saveMentor(MentorModel module)throws Exception{
        	            setData(mentorDAO,module);
        	            mentorDAO.saveMentor();
    	 }
        public void updateMentor(MentorModel module)throws Exception{
        	            setData(mentorDAO,module);
        	            mentorDAO.updateMentor();
        }
        public void setData(MentorDAO mentorDAO,MentorModel module)throws Exception{
        	             mentorDAO.setMentorCode(module.getMentorCode());
        	             mentorDAO.setCountryCode(module.getCountryCode());
                         mentorDAO.setDistrictcode(module.getDistrictcode());
                         mentorDAO.setInitials(module.getInitials());
                         mentorDAO.setInUseFlag(module.getInUseFlag());
                         mentorDAO.setTitle(module.getTitle());
                         mentorDAO.setName(module.getName());
                         mentorDAO.setOccupation(module.getOccupation());
                         mentorDAO.setProvcode(module.getProvcode());
                         mentorDAO.setSchoolCode(module.getSchoolCode());
                         mentorDAO.setSurname(module.getSurname());
                         mentorDAO.setTrained(module.getTrained());
                         mentorDAO.setCellNumber(module.getCellNumber());
                         mentorDAO.setPhoneNumber(module.getPhoneNumber());
                         mentorDAO.setFaxNumber(module.getFaxNumber());
                         mentorDAO.setEmailAddress(module.getEmailAddress());
       }
        public void deleteMentor(int mentorcode)throws Exception{
        	             mentorDAO.deleteMentor(mentorcode);
        }
        public void linkMentorToSchool(int schoolCode,int mentorCode)throws Exception{
        	             mentorDAO.linkMentorToSchool(schoolCode, mentorCode);
       }
        public void writeMentorListFile(List list,String country,String fileName){
        	           mfw.writeFile(list, fileName,country);
    	 }
        public void  extractFile(HttpServletRequest request,
                HttpServletResponse response,String fileToRead,
                String outputFileName){
        	        fileExtractorClass.extractFile(request,response,fileToRead,outputFileName);
        }
        public boolean mentorExists(int mentorCode)throws Exception{
        	              return mentorDAO.mentorExists(mentorCode);
        }
        public void removeFromSchool( int mentorCode)throws Exception{
        	           mentorDAO.removeFromSchool(mentorCode);
        }
        public void setMentorTrainedList(StudentPlacementForm studentPlacementForm){
        	          mentorUI.setMentorTrainedList(studentPlacementForm);
        }
        public void refresh(StudentPlacementForm studentPlacementForm ){
        	           mentorUI.refresh(studentPlacementForm);
       	}
    	
}
