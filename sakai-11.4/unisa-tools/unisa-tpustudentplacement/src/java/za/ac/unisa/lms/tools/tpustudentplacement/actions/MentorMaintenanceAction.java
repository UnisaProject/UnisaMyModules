package za.ac.unisa.lms.tools.tpustudentplacement.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.DistrictDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.SupervisorDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.MentorFilteringData;
import za.ac.unisa.lms.tools.tpustudentplacement.model.Mentor;
import za.ac.unisa.lms.tools.tpustudentplacement.model.MentorModel;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.CommunicationUI;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.InfoMessagesUtil;

public class MentorMaintenanceAction  extends LookupDispatchAction{
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("extractFile", "extractFile");
		map.put("initial", "initial");
		map.put("button.display", "listMentors");
		map.put("button.back", "prevPage");
		map.put("button.searchSchool","searchSchool");
		map.put("button.save", "saveMentor");
		map.put("button.addMentor", "addMentor");
		map.put("button.link", "saveMentor");
		map.put("button.view", "selectMentor");
		map.put("getMentorData","getMentorData");
		map.put("button.select", "getMentorData");
		map.put("button.deleteMentor","deleteMentor");
		map.put("listMentors","listMentors");
		map.put("button.listmentors", "listMentors");
		map.put("button.searchDistrict","searchDistrict");
		map.put("button.removeFromSchool","removeFromSchool");
		map.put("button.linktoSchool","linkToSchool");
		map.put("button.clearSchool","clearSchool");
		map.put("mentorCountryOnchangeAction","mentorCountryOnchangeAction");
		map.put("inputCorrespondence","inputCorrespondence");
				return map;
	}
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		           StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		           studentPlacementForm.setCurrentPage("initial");
		           studentPlacementForm.setIndexNrSelected(new String[0]);
		           Mentor mentor=studentPlacementForm.getMentor();
		           mentor.refresh(studentPlacementForm);
		 		   studentPlacementForm.setCurrentPage("listMentor");
				   studentPlacementForm.setPreviousPage("inputStudentPlacement");
				   studentPlacementForm.setMentorCalledFrom("");
                   return mapping.findForward("listMentor");	
	}
	public ActionForward clearSchool(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		           StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		           studentPlacementForm.getMentorFilterData().setMentorFilterSchoolValue("");
		           studentPlacementForm.getMentorFilterData().setMentorFilterSchoolCode(0);
		           studentPlacementForm.getMentorModel().setSchoolCode(0);
		           studentPlacementForm.getMentorModel().setSchoolName("");
				   return mapping.findForward(studentPlacementForm.getCurrentPage());	
	}
	public ActionForward inputCorrespondence(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
         
             StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
             CommunicationUI communicationUI=new CommunicationUI();
             ActionMessages messages=new ActionMessages(); 
             communicationUI.buildCommunicationScreen(request, studentPlacementForm, messages);
             if(!messages.isEmpty()){
           	       addErrors(request,messages);
           	       mapping.findForward(studentPlacementForm.getCurrentPage());
           	 }
              studentPlacementForm.setCurrentPage("inputCorrespondence");
             return mapping.findForward(studentPlacementForm.getCurrentPage());	
   }

	public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		      StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			 if( (studentPlacementForm.getCurrentPage()==null)|| studentPlacementForm.getCurrentPage().trim().equals("")){
				     studentPlacementForm.setCurrentPage("initial");
			  } else 
			 if(studentPlacementForm.getCurrentPage().equals("listMentor")){
				 if(studentPlacementForm.getMentorCalledFrom().equals("")){
				    studentPlacementForm.setCurrentPage("inputStudentPlacement");
				 }else{
					    request.setAttribute("startDate",studentPlacementForm.getStudentPlacement().getStartDate());
                        request.setAttribute("endDate",studentPlacementForm.getStudentPlacement().getEndDate());
                        studentPlacementForm.setCurrentPage("editStudentPlacement");
				 }
			 }else if(studentPlacementForm.getCurrentPage().equals("initial")){
				     studentPlacementForm.setCurrentPage("inputStudentPlacement");
			 }else if(studentPlacementForm.getCurrentPage().equals("inputCorrespondence")){
				      studentPlacementForm.setCurrentPage("inputMentor");
			 }else if(studentPlacementForm.getCurrentPage().equals("inputMentor")){
				      studentPlacementForm.setListMentors( studentPlacementForm.getMentor().getMentors( studentPlacementForm.getMentorFilterData()));
                      studentPlacementForm.setCurrentPage("listMentor");
			 }
	           return mapping.findForward(studentPlacementForm.getCurrentPage());
	}
	public ActionForward searchSchool(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		        studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getMentorFilterData().getMentorFilterCountry());
		         studentPlacementForm.setSchoolFilter("");
		         return mapping.findForward("searchSchool");	
	}
		public ActionForward linkMentorToSchool(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		    StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		    ActionMessages messages = new ActionMessages();
	  	    MentorModel mentorModel=studentPlacementForm.getMentorModel();
	  	    Mentor mentor=studentPlacementForm.getMentor();
	  	    mentor.setSchoolCode(studentPlacementForm.getMentorFilterData().getMentorFilterSchoolCode());
	  	    boolean mentorExist =mentor.mentorExists(mentorModel.getMentorCode());
	  	    InfoMessagesUtil  infoMessagesUtil=new  InfoMessagesUtil();
	  	    String  err="";
			if(mentorExist&&(studentPlacementForm.getMentorFilterData().getMentorFilterSchoolCode()!=0)){
	  	    	   mentor.linkMentorToSchool(studentPlacementForm.getMentorFilterData().getMentorFilterSchoolCode(), mentorModel.getMentorCode());
	  	    }else{
	  	    	         if(!mentorExist ){
	  	    				err="The mentor you selected does not exist";
	  	    			 }
	  	    			 if(studentPlacementForm.getMentorFilterData().getMentorFilterSchoolCode()==0){
	  	    				err="Please select a school to link mentor to";
	  	    			 }
	                     addErrors(request,messages);
	  	    	       return mapping.findForward("inputMentor");	
	  	    }
            err="The mentor you selected does not exist";
            infoMessagesUtil.addMessages(messages,err);
            
	  	       studentPlacementForm.getMentorFilterData().setMentorFilter(mentorModel.getSurname());
	  	       addErrors(request,messages);
	       return listMentors( mapping,form,request,response);	
		}
	public ActionForward  addMentor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)  {
		
		         StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		         studentPlacementForm.setPreviousPage( studentPlacementForm.getCurrentPage());
			     studentPlacementForm.setCurrentPage("inputMentor");
			     Mentor mentor=new Mentor();
		         mentor.refresh(studentPlacementForm);
		  	     studentPlacementForm.setAddMentorScreen("Y");
		    	 return mapping.findForward("inputMentor");	
	}
	
	public ActionForward  removeFromSchool(
                                ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

                                   StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
                                  ActionMessages messages = new ActionMessages();
                                   MentorModel mentorModel=studentPlacementForm.getMentorModel();
                                   Mentor mentor=studentPlacementForm.getMentor();
                                   InfoMessagesUtil  infoMessagesUtil=new  InfoMessagesUtil();
                                   String err="";
                                   if(mentorModel.getSchoolCode()==0){
                                	   if(mentor.getMentor(mentorModel.getMentorCode()).getSchoolCode()==0){
                                	          err="The mentor is not linked to a school";
                                	   }else{
                                		        err="Please select a school";
                                	   }
                                		   
                                   }else{
                                	          boolean mentorExist =mentor.mentorExists(mentorModel.getMentorCode());
                                             if(mentorExist){
                                            	       mentor.removeFromSchool(mentorModel.getMentorCode());
                                                       mentorModel=mentor.getMentor(mentorModel.getMentorCode());
                                                       studentPlacementForm.setMentorModel(mentorModel);
                                                       studentPlacementForm.getMentorFilterData().setMentorFilterSchoolCode(mentorModel.getMentorCode());
                                               }else{
                          	                           err="The mentor you selected does not exist";
                                               }
                                    }
                                    infoMessagesUtil.addMessages(messages,err);
                                    if(!messages.isEmpty()){
                               			addErrors(request,messages);
                               			return mapping.findForward("inputMentor");
         	                        }
                                   studentPlacementForm.setListMentor(mentor.getMentors(studentPlacementForm.getMentorFilterData()));
	    	 return listMentors( mapping,form,request,response);			
	 }
	public ActionForward  linkToSchool(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

               StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
              ActionMessages messages = new ActionMessages();
               MentorModel mentorModel=studentPlacementForm.getMentorModel();
               Mentor mentor=studentPlacementForm.getMentor();
               InfoMessagesUtil  infoMessagesUtil=new  InfoMessagesUtil();
               String err="";
               if(mentorModel.getSchoolCode()==0){
            	     err="Please select a school ";
               }else{
                                boolean mentorExist =mentor.mentorExists(mentorModel.getMentorCode());
                                  if(mentorExist){
                                	       mentor.linkMentorToSchool(mentorModel.getSchoolCode(),mentorModel.getMentorCode());
                                	      mentorModel=mentor.getMentor(mentorModel.getMentorCode());
                                         studentPlacementForm.setMentorModel(mentorModel);
                                    }else{
               	                           err="The mentor you selected does not exist";
                                    }
                                  
               }
               infoMessagesUtil.addMessages(messages,err);
                   
                               	if(!messages.isEmpty()){
                               			addErrors(request,messages);
                               			return mapping.findForward("inputMentor");
         	                    }
                                studentPlacementForm.setListMentor(mentor.getMentors(studentPlacementForm.getMentorFilterData()));
	    	 return listMentors( mapping,form,request,response);	
    }
	public ActionForward  saveMentor(
			                 ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		        ActionMessages messages = new ActionMessages();
		  	    MentorModel mentorModel=studentPlacementForm.getMentorModel();
		  	    Mentor mentor=studentPlacementForm.getMentor();
		  	    mentor.validateMentor(mentorModel, messages);
		  	    if(messages.isEmpty()){
		  	          String  fromAddMentorScreen= studentPlacementForm.getAddMentorScreen();
		  	          if(fromAddMentorScreen.trim().equals("Y")){
		  	        	       mentor.saveMentor(mentorModel);
		  	    	  }else{
		  	        	     mentor.updateMentor(mentorModel);  
		  	         }
		  	          studentPlacementForm.getMentorFilterData().setMentorFilterCountry(mentorModel.getCountryCode());
		  	          studentPlacementForm.getMentorFilterData().setMentorFilter(mentorModel.getSurname());
		  	          studentPlacementForm.getMentorFilterData().setMentorIsTrained(mentorModel.getTrained());
		  	          studentPlacementForm.getMentorFilterData().setMentorFilterSchoolCode(mentorModel.getSchoolCode());
		  	    }else{
		  	    	     addErrors(request,messages);
		  	    	   return mapping.findForward("inputMentor");	
		  	    }
		  	     studentPlacementForm.setListMentor(mentor.getMentors(studentPlacementForm.getMentorFilterData()));
		    	 return listMentors( mapping,form,request,response);	
	}
	
	public ActionForward searchDistrict(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		try{
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		List listDistrict = new ArrayList();
		DistrictDAO dao = new DistrictDAO();
			listDistrict = dao.getDistrictList2("Y", studentPlacementForm.getMentorFilterData().getMentorFilterProvince(),studentPlacementForm.getMentorFilterData().getMentorDistrictFilter());
			String label="ALL";
			String value="0-" + studentPlacementForm.getMentorFilterData().getMentorFilterProvince();
			listDistrict.add(0,new LabelValueBean(label, value));
			studentPlacementForm.setListMentorFilterDistrict(listDistrict);	
		return mapping.findForward("listMentor");	
		}catch(Exception ex){
	        return mapping.findForward("errMentor");
		}
	}	
    public ActionForward  selectMentor(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
		  	                   ActionMessages messages = new ActionMessages();	
		  	                   studentPlacementForm.setAddMentorScreen("N");
		  	                   messages=setMentorDataToFormBean(messages,studentPlacementForm);
	                           if (!messages.isEmpty()) {
	      	                          addErrors(request,messages);
	      	                          studentPlacementForm.setCurrentPage("listMentor");
	      	                          return mapping.findForward("listMentor");				
	      	                   }   
					          studentPlacementForm.setCurrentPage("inputMentor");
					return mapping.findForward("inputMentor");
   }
    private ActionMessages setMentorDataToFormBean(ActionMessages messages,StudentPlacementForm studentPlacementForm) {
    	                           MentorModel mentorModel=null;
 	                               studentPlacementForm.setAddMentorScreen("N");
 	                               Mentor mentor=studentPlacementForm.getMentor();
 	                               String[] indexArr=studentPlacementForm.getIndexNrSelected();
                                   mentor.validateIndexArrForAddOrView(indexArr, messages);
                                   mentorModel=mentor.getSelectedMentor(studentPlacementForm);
			                       studentPlacementForm.getMentorModel().setData(mentorModel);
			                       return messages;
    }
    public ActionForward  getMentorData(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
		        ActionMessages messages = new ActionMessages();	
                  studentPlacementForm.setAddMentorScreen("N");
                  messages=setMentorDataToFormBean(messages,studentPlacementForm);
                      if (!messages.isEmpty()) {
                            addErrors(request,messages);
                            return mapping.findForward("listMentor");				
                      } 
                                StudentPlacement spl=studentPlacementForm.getStudentPlacement();
                                if( spl==null){
                            	       InfoMessagesUtil  infoMessagesUtil=new  InfoMessagesUtil();
                                       String  err="	To view a mentor select a mentor from the list then click the Edit button";
                        	               infoMessagesUtil.addMessages(messages,err);
                        	    	     
                                	 addErrors(request,messages);
                                	return mapping.findForward("listMentor");	
                                }
                                spl.setMentorCode(studentPlacementForm.getMentorModel().getMentorCode());
                                spl.setMentorName(studentPlacementForm.getMentorModel().getName());
                                studentPlacementForm.setCurrentPage(studentPlacementForm.getMentorCalledFrom());
                                studentPlacementForm.setMentorCalledFrom("");
                                if(studentPlacementForm.getStudentPlacement()!=null){
                                	studentPlacementForm.getStudentPlacement().setDatesToRequest(request);
                                }
                                return mapping.findForward(studentPlacementForm.getCurrentPage());
                    
   }
   public ActionForward  listMentors(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		                 StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		                 Mentor mentor=studentPlacementForm.getMentor();
		  	             MentorFilteringData mentorFilterData=studentPlacementForm.getMentorFilterData();
		  	             List mentorList=mentor.getMentors(mentorFilterData);
		  	             studentPlacementForm.setListMentor(mentorList);
		  	              String country=mentorFilterData.getMentorFilterCountry();
                         if (mentorList.size()>0){
    		  	                 String userId=studentPlacementForm.getUserId();
                               String fileName=getMentorDelimitedFileName(userId);
                               studentPlacementForm.getMentor().writeMentorListFile(mentorList,country,fileName);
                               studentPlacementForm.setDownloadFile(fileName);
                         }
                        studentPlacementForm.setCurrentPage("listMentor");
		  	      return mapping.findForward("listMentor");	
		}
	
      
	public ActionForward  deleteMentor (
			                      ActionMapping mapping,
			                      ActionForm form,
			                      HttpServletRequest request,
			                      HttpServletResponse response) throws Exception  {
		 
		                                  StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
		                                 ActionMessages messages = new ActionMessages();	
                                          Mentor  mentor=studentPlacementForm.getMentor();
		                                  MentorModel mentorModel=null;
		                                  String[] indexArr=studentPlacementForm.getIndexNrSelected();
		               	                  mentor.validateIndexrForSelection(indexArr, messages);
		               	                  if (!messages.isEmpty()) {
		               	                      addErrors(request,messages);
		               	                      studentPlacementForm.setCurrentPage("listMentor");
		               	                      return mapping.findForward("listMentor");				
		               	                  }
		               	                  mentorModel=mentor.getSelectedMentor(studentPlacementForm);
		               					  int selectedIndex=Integer.parseInt(indexArr[0]);
		                                  mentor.deleteMentor( mentorModel.getMentorCode());
		  	                              studentPlacementForm.setListMentor(mentor.getMentors( studentPlacementForm.getMentorFilterData()));
		                                  return listMentors( mapping,form,request,response);	
	}
	public ActionForward mentorCountryOnchangeAction(
			                     ActionMapping mapping,
			                     ActionForm form,
			                     HttpServletRequest request,
			                     HttpServletResponse response)  {
		                                    StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			        						studentPlacementForm.getMentorFilterData().getMentorFilterCountry().trim();
		              						if(! studentPlacementForm.getMentorFilterData().getMentorFilterCountry().trim().equals("1015")){
		              									studentPlacementForm.getMentorFilterData().setMentorFilterDistrictValue("All");
		              									studentPlacementForm.getMentorFilterData().setMentorFilterProvince(Short.parseShort("0"));
		              									studentPlacementForm.getMentorFilterData().setMentorFilterDistrict(Short.parseShort("0"));
		              						}
		              						studentPlacementForm.getMentorFilterData().setMentorFilter("");
		
		                      return mapping.findForward("listMentor");	
	}
	public ActionForward extractFile(ActionMapping mapping, ActionForm form,
			                         HttpServletRequest request, HttpServletResponse response)
			                         throws Exception {  
		                                                          StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                                                          String outputFileName="mentorList.txt";
		                                                          if (studentPlacementForm.getListMentor().size()>0){
		                                         		  	                String fileName=studentPlacementForm.getDownloadFile();
		                                            		                studentPlacementForm.getMentor().extractFile(request,response,fileName,outputFileName);
		                                                          }
		                                         	  return null;
		                    
		}
	public String getMentorDelimitedFileName(String userId){
                       String path = getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";
                       String fileDir = path +"/";
                       String time = (new java.text.SimpleDateFormat("yyyyMMddhhmmssss").format(new java.util.Date()).toString());
                       String     fileName = fileDir +userId +"_tpuMentorList_"+ time +".txt";	
                    return fileName;
   }

}
