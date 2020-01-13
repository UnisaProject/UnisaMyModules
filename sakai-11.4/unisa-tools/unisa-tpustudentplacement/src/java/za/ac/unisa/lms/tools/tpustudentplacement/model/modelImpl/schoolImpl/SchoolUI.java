package za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.DistrictDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SchoolListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.SchoolValidator;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
public class SchoolUI extends SchoolImpl {

          
	                 SchoolScreenBuilder screeenBuilder;
	                 SchoolValidator schoolValidator;
	                 public  SchoolUI(){
	                	       screeenBuilder=new SchoolScreenBuilder();
	                           schoolValidator=new SchoolValidator();
	                 }
	                 public School getSchool(StudentPlacementForm studentPlacementForm) throws Exception{
		                               int schoolCode=studentPlacementForm.getCommunicationSchool();
		                               String countryCode=studentPlacementForm.getSchoolFilterCountry();
			                           return getSchool(schoolCode, null,countryCode);
			         }
	                 public void setSchoolToFormBean(StudentPlacementForm studentPlacementForm) throws Exception{
	                                   School school=getSchool(studentPlacementForm);
					                    studentPlacementForm.setSchool(school);
	                 }
	                 public void  setSchoolCommunicDataToFormBean(StudentPlacementForm studentPlacementForm)throws Exception{
	            	                  setSchoolCellNrToFormBean(studentPlacementForm);
	            	                  setSchoolEmailToFormBean(studentPlacementForm);
	                 }
	                 public void  setSchoolCellNrToFormBean(StudentPlacementForm studentPlacementForm)throws Exception{
	            	                 String cellNr=getSchoolContactNumber(studentPlacementForm.getCommunicationSchool());
		                             studentPlacementForm.setCommunicationCellNumber(cellNr);
	                 }
	                 public void  setSchoolEmailToFormBean(StudentPlacementForm studentPlacementForm)throws Exception{
	                                    String email=getEmailAddress(studentPlacementForm.getCommunicationSchool());
 	                                    studentPlacementForm.setCommunicationEmailAddress(email.trim());
                     }
	                 public void validateSchoolSelection(String[] indexArr,ActionMessages messages){ 
	          	    	               schoolValidator.validateSelection(indexArr, messages);
	          	     }
	          	     public int getSchoolProvCode(int schoolCode) throws Exception {
	          	    	              School school=getSchool(schoolCode,null);
                                     return school.getDistrict().getProvince().getCode();
	          	     }
	          	     public String getSchoolName(int schoolCode) throws Exception {
	          	    	             School school=getSchool(schoolCode,null);
                                     return school.getName();
	                 }
	          	     public  SchoolListRecord getSelectedSchool(StudentPlacementForm studentPlacementForm){
	          		                          return  screeenBuilder.getSelectedSchool(studentPlacementForm);
	   	             }
	          	     public void setSchoolDetailsForAddMentor(StudentPlacement studentPlacement,int schoolCode,
	        	           String schoolName,String pageCalledFrom){
	          		               screeenBuilder.setSchoolDetailsForEditPlacement(studentPlacement, schoolCode, schoolName, pageCalledFrom);
	                 }
	   	             public void setSchoolDetailsForEditPlacement(StudentPlacement studentPlacement,int schoolCode,
	   	                               String schoolName,String pageCalledFrom){
	                                   screeenBuilder.setSchoolDetailsForEditPlacement(studentPlacement, schoolCode, schoolName, pageCalledFrom);
	                 }
	                 public void setSchoolDetailsForInputPlacement(StudentPlacementForm studentPlacementForm,SchoolListRecord school){
	                	               screeenBuilder.setSchoolDetailsForInputPlacement(studentPlacementForm, school);
	                 }
	                 public String setNationalSchoolList(StudentPlacementForm studentPlacementForm,
		    		           ActionMessages messages,HttpServletRequest request )  throws Exception {
				                     Short province = studentPlacementForm.getSchoolFilterProvince();
			                         setSchoolDistrict(studentPlacementForm,province);
			                         checkFiltering(studentPlacementForm,messages,province);
				                     if (!messages.isEmpty()) {
				                           studentPlacementForm.setCurrentPage(studentPlacementForm.getCurrentPage());
				                           return studentPlacementForm.getCurrentPage();				
			                         }		
			                         setSchoolListFromDAO(studentPlacementForm,province);
			                         checkAfterEdit(studentPlacementForm);
			                 return "listSchool";
		            }
	                 public String setInternationalSchoolList(StudentPlacementForm studentPlacementForm)
	                            throws Exception{
		    	                    setSchoolListFromDAO(studentPlacementForm);
			                        checkAfterEdit(studentPlacementForm);
	                            return "listSchool";
	         }
		     private  void setSchoolListFromDAO(StudentPlacementForm studentPlacementForm,Short province)
		                       throws Exception{
		    	                  List list = new ArrayList<SchoolListRecord>();
		    	                   list = getSchoolList(studentPlacementForm.getSchoolFilterType(), 
						                                    studentPlacementForm.getSchoolFilterCategory(),
						                                    studentPlacementForm.getSchoolFilterCountry(),
						                                    province,
						                                    studentPlacementForm.getSchoolFilterDistrict(),
						                                    studentPlacementForm.getSchoolFilter());
				                   studentPlacementForm.setListSchool(list);
		    }
		     public List<SchoolListRecord> getSchoolProvDistData(String  country,Short province,Short district) 
                     throws Exception{
  	                                 return getSchoolList(country,province,district);
            }
		    private  void setSchoolListFromDAO(StudentPlacementForm studentPlacementForm)
	                            throws Exception{
	                                       List list = new ArrayList<SchoolListRecord>();
	                                      list = getSchoolList(studentPlacementForm.getSchoolFilterType(), 
		                                   studentPlacementForm.getSchoolFilterCategory(),
		                                   studentPlacementForm.getSchoolFilterCountry(),
		                                   studentPlacementForm.getSchoolFilter());
	                                       studentPlacementForm.setListSchool(list);
	        }
		    private void setSchoolDistrict(StudentPlacementForm studentPlacementForm,Short province)
	                  throws Exception {
		                        if (studentPlacementForm.getSchoolFilterDistrictValue()!=null && 
                                  !studentPlacementForm.getSchoolFilterDistrictValue().equalsIgnoreCase("")){
                                       int index = studentPlacementForm.getSchoolFilterDistrictValue().indexOf("-");
                                       String district = studentPlacementForm.getSchoolFilterDistrictValue().trim().substring(0, index);
                                       province = Short.parseShort(studentPlacementForm.getSchoolFilterDistrictValue().trim().substring(index+1));
                                       if (studentPlacementForm.getSchoolFilterProvince().compareTo(province)!=0 &&
                     		                   studentPlacementForm.getSchoolFilterProvince().compareTo(Short.parseShort("0"))!=0){
	                                           studentPlacementForm.setSchoolFilterDistrict(Short.parseShort("0"));
	                                           studentPlacementForm.setSchoolFilterDistrictValue("");
	                                           studentPlacementForm.setListFilterSchoolDistrict(new ArrayList());
	                                           province = studentPlacementForm.getSchoolFilterProvince();
                                       }else{
	                                              studentPlacementForm.setSchoolFilterDistrict(Short.parseShort(district));
	                                              DistrictDAO  districtDAO =new DistrictDAO();
	                              				  List listDistrict = new ArrayList();
	                                              listDistrict = districtDAO.getDistrictList2("Y", studentPlacementForm.getSchoolFilterProvince(),
	                            	              studentPlacementForm.getDistrictFilter());
	                                              String label="ALL";
	                                              String value="0-" + studentPlacementForm.getSchoolFilterProvince().toString();
	                                              listDistrict.add(0,new LabelValueBean(label, value));
	                                              studentPlacementForm.setListFilterSchoolDistrict(listDistrict);	
            	  	                     }//else

                              }//if		
	  }
		    private void checkFiltering(StudentPlacementForm studentPlacementForm,ActionMessages messages,Short province){
	              if((studentPlacementForm.getSchoolFilterDistrictValue()!=null &&
                    !studentPlacementForm.getSchoolFilterDistrictValue().trim().equalsIgnoreCase("") && 
                    !studentPlacementForm.getSchoolFilterDistrictValue().trim().substring(0, 2).equalsIgnoreCase("0-")) ||
                    (studentPlacementForm.getSchoolFilter()!=null &&
                     studentPlacementForm.getSchoolFilter().length()>3) ||
                    (province!=null && 
		               province.compareTo(Short.parseShort("0")) > 0 &&
		               studentPlacementForm.getSchoolFilter()!=null &&
		               !studentPlacementForm.getSchoolFilter().trim().equalsIgnoreCase("") &&
		               !studentPlacementForm.getSchoolFilter().equalsIgnoreCase("%"))
		           ){
		            //Filter on enough values
 		          }else{
	                    messages.add(ActionMessages.GLOBAL_MESSAGE,
			            new ActionMessage("message.generalmessage",
			             "Either filter on (1-District) or (2-Province and School Name) or (3-Filter on School name only, with at least 3 characters, e.g DUR%"));
               }
         }
		    
		    private void checkAfterEdit(StudentPlacementForm studentPlacementForm){
            	   //re-check check boxes after edit
 	             if (studentPlacementForm.getIndexNrSelected()!=null && 
                      studentPlacementForm.getIndexNrSelected().length>0){
                            studentPlacementForm.setIndexNrSelected(new String[0]);
                            for (int i=0; i < studentPlacementForm.getListSchool().size(); i++){
                                  SchoolListRecord temp = (SchoolListRecord)studentPlacementForm.getListSchool().get(i);
                                  if (temp.getCode()==studentPlacementForm.getSchool().getCode()){
                                        String[] array = new String[1];
                                        array[0]=String.valueOf(i);
                                        studentPlacementForm.setIndexNrSelected(array);
                                        i = studentPlacementForm.getListSchool().size();
                                  }//if
                             }//for
                  }//if	
  }
		    
}
