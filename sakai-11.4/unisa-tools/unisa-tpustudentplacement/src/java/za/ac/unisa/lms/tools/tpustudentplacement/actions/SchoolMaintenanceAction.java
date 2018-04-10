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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import za.ac.unisa.lms.tools.tpustudentplacement.dao.*;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Province;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.School;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.District;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.SchoolListRecord;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.DistrictUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.schoolImpl.SchoolUI;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.studentPlacementImpl.StudentPlacementUI;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.*;
import za.ac.unisa.utils.CellPhoneVerification;

   public class SchoolMaintenanceAction extends LookupDispatchAction{
	            
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");	
		map.put("countryOnchangeAction","countryOnchangeAction");
		map.put("button.display", "display");
		map.put("button.back", "prevPage");
		map.put("button.add", "addSchool");
		map.put("button.edit", "editSchool");
		map.put("button.save", "saveSchool");	
		map.put("button.continue", "nextStep");
		map.put("button.searchPostalCode","displayPostalCodeSearch");
		map.put("button.searchDistrict","searchDistrict");
		map.put("button.select","selectSchool");
		return map;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		
		studentPlacementForm.setSchoolCalledFrom(studentPlacementForm.getCurrentPage());
		if (studentPlacementForm.getSchoolCalledFrom()==null){
			studentPlacementForm.setSchoolCalledFrom("inputStudentPlacement");
		}
		
		if (studentPlacementForm.getSchoolFilterCountry()==null || 
				studentPlacementForm.getSchoolFilterCountry().trim().equalsIgnoreCase("")){
			    //default country to south africa
			    studentPlacementForm.setSchoolFilterCountry(PlacementUtilities.getSaCode());
		}		
		String countryCode=studentPlacementForm.getSchoolFilterCountry();
		studentPlacementForm.setIndexNrSelected(new String[0]);
		studentPlacementForm.setCurrentPage("inputSchool");
		studentPlacementForm.setSchoolFilterType("All");
		studentPlacementForm.setSchoolFilterCategory("All");
		studentPlacementForm.setSchoolFilterProvince(Short.parseShort("0"));
		studentPlacementForm.setSchoolFilterDistrict(Short.parseShort("0"));
		studentPlacementForm.setSchoolFilterDistrictValue("");
		studentPlacementForm.setDistrictFilter("");
		studentPlacementForm.setSchoolFilter("");
		if (studentPlacementForm.getSchoolCalledFrom().equalsIgnoreCase("editStudentPlacement")){	
			StudentPlacementUI studentPlacement=new StudentPlacementUI();
			   if((studentPlacementForm.getListStudentPlacement()==null)||
					   (studentPlacementForm.getListStudentPlacement().size()==0)){
			               Student  student= studentPlacementForm.getStudent();
			               countryCode=student.getCountryCode(student.getNumber());
			               if(!countryCode.equals("0")){
			                       studentPlacementForm.setSchoolFilterCountry(countryCode);
			               }
		                   return display(mapping, studentPlacementForm, request, response);
		      }
			   Integer schoolCode=schoolCode=studentPlacementForm.getStudentPlacement().getSchoolCode();
			   SchoolUI schoolUI= new SchoolUI();
				if(schoolCode!=0){
				       countryCode=schoolUI.getSchoolCountry(schoolCode);
				       studentPlacementForm.setSchoolFilterCountry(countryCode);
			   }
				if(countryCode.equals(PlacementUtilities.getSaCode())){
						String schoolDistrictCode=schoolUI.getSchoolDistrictCode(schoolCode);
				        DistrictUI  districtUI =new DistrictUI();
				        District  district=districtUI.getDistrict(Short.parseShort(schoolDistrictCode));
				       studentPlacementForm.setSchoolFilterProvince(district.getProvince().getCode());
				       studentPlacementForm.setSchoolFilterDistrict(district.getCode());
				       List listDistrict = new ArrayList();
				       studentPlacementForm.setListFilterSchoolDistrict(listDistrict);
				       listDistrict = districtUI.getDistrictList2("Y", studentPlacementForm.getSchoolFilterProvince(),"");
				       studentPlacementForm.setListFilterSchoolDistrict(listDistrict);
				       studentPlacementForm.setSchoolFilterDistrictValue(district.getCode().toString() + "-" + district.getProvince().getCode().toString());
				}
				studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getPlacementFilterCountry());
				studentPlacementForm.setSchoolFilter(studentPlacementForm.getStudentPlacement().getSchoolDesc()+ "%");
				return display(mapping, studentPlacementForm, request, response);
		}
		if (studentPlacementForm.getSchoolCalledFrom().equalsIgnoreCase("inputMentor") || studentPlacementForm.getSchoolCalledFrom().equalsIgnoreCase("listMentor")){
			if(countryCode.equals(PlacementUtilities.getSaCode())){  
			      studentPlacementForm.setListFilterSchoolDistrict(studentPlacementForm.getListMentorFilterDistrict());
			      studentPlacementForm.setSchoolFilterProvince(studentPlacementForm.getMentorFilterData().getMentorFilterProvince());
			      studentPlacementForm.setSchoolFilterDistrictValue(studentPlacementForm.getMentorFilterData().getMentorFilterDistrictValue());
			      if (studentPlacementForm.getSchoolFilterDistrictValue()!=null && !studentPlacementForm.getSchoolFilterDistrictValue().equalsIgnoreCase("")){
				        return display(mapping, studentPlacementForm, request, response);
			      }
			}else{
				      studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getMentorFilterData().getMentorFilterCountry());
				      return display(mapping, studentPlacementForm, request, response);
			}
		}
		
		if (studentPlacementForm.getSchoolCalledFrom().equalsIgnoreCase("inputPlacement") || studentPlacementForm.getSchoolCalledFrom().equalsIgnoreCase("listPlacement")){
			if(countryCode.equals(PlacementUtilities.getSaCode())){  
			      studentPlacementForm.setListFilterSchoolDistrict(studentPlacementForm.getListFilterPlacementDistrict());
			      studentPlacementForm.setSchoolFilterProvince(studentPlacementForm.getPlacementFilterProvince());
			      studentPlacementForm.setSchoolFilterDistrictValue(studentPlacementForm.getPlacementFilterDistrictValue());
			      if (studentPlacementForm.getPlacementFilterDistrictValue()!=null && !studentPlacementForm.getPlacementFilterDistrictValue().equalsIgnoreCase("")){
				        return display(mapping, studentPlacementForm, request, response);
			      }
			}else{
				      studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getPlacementFilterCountry());
				      return display(mapping, studentPlacementForm, request, response);
			}
		}else{
			studentPlacementForm.setListFilterSchoolDistrict(new ArrayList());
		}
		studentPlacementForm.setCurrentPage("inputSchool");
		return mapping.findForward("inputSchool");	
	}
	public ActionForward prevPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
				if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("editSchool")){
		             	  studentPlacementForm.setCurrentPage("listSchool");
			              return mapping.findForward("listSchool");
		        }else{
			              studentPlacementForm.setCurrentPage(studentPlacementForm.getSchoolCalledFrom());
			              return mapping.findForward(studentPlacementForm.getSchoolCalledFrom());
		        }	
	}
	public ActionForward display(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 
		             StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		             ActionMessages messages = new ActionMessages();
		             String countryCode="";
		             countryCode=studentPlacementForm.getSchoolFilterCountry().trim();
		             String returnStr=studentPlacementForm.getCurrentPage();
		             SchoolUI schoolUI=new SchoolUI();
			         if(countryCode.equals(PlacementUtilities.getSaCode())){
				             returnStr= schoolUI.setNationalSchoolList(studentPlacementForm,messages,request);
				                  if (!messages.isEmpty()) {
		                                  addErrors(request,messages);
		                          }
		             }else{
		    	             studentPlacementForm.setSchoolFilterDistrictValue(null);
			                 studentPlacementForm.setSchoolFilterProvince(Short.parseShort("0"));
		    	             returnStr=schoolUI.setInternationalSchoolList(studentPlacementForm);
		             }
		             studentPlacementForm.setSchool(new School());		
		             studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		             studentPlacementForm.setCurrentPage(returnStr);
		            return mapping.findForward(returnStr);
	  }
	 
	 public ActionForward addSchool(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		    StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
			School school = new School();	
		    District district = new District();
		    Province province = new Province();
		    school.setInUse("Y");
		    school.setAgreement("Y");
		    school.setCountryCode(studentPlacementForm.getSchoolFilterCountry());
		    if(studentPlacementForm.getSchoolFilterCountry().equals(PlacementUtilities.getSaCode())){
			      province.setCode(studentPlacementForm.getSchoolFilterProvince());
		          district.setCode(studentPlacementForm.getSchoolFilterDistrict());
		          district.setDescription(studentPlacementForm.getSchoolFilterDistrictDesc());
		          district.setProvince(province);
		          school.setDistrict(district);
		          school.setPostalCode("");
		   	      List list = new ArrayList<LabelValueBean>();
		          studentPlacementForm.setListDistrict(list);
		    }
		    studentPlacementForm.setSchool(school);		
 		    studentPlacementForm.setSchoolAction("Add");
 		    studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
		    studentPlacementForm.setCurrentPage("editSchool");
		return mapping.findForward("editSchool");	
	}
	
	public ActionForward editSchool(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		ActionMessages messages = new ActionMessages();	
		
		if (studentPlacementForm.getIndexNrSelected()==null ||
				studentPlacementForm.getIndexNrSelected().length==0){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select a school to view or edit"));
			}
		if (studentPlacementForm.getIndexNrSelected()!=null &&
				studentPlacementForm.getIndexNrSelected().length>1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Please select only one school to view or edit"));
			}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			studentPlacementForm.setCurrentPage("listSchool");
			return mapping.findForward("listSchool");				
		}
		SchoolListRecord schoolListRecord = new SchoolListRecord();
		
		for (int i=0; i <studentPlacementForm.getIndexNrSelected().length; i++) {
			String array[] = studentPlacementForm.getIndexNrSelected();
			schoolListRecord = (SchoolListRecord)studentPlacementForm.getListSchool().get(Integer.parseInt(array[i]));
		}
		
		SchoolUI schoolUI = new SchoolUI();
		String countryCode=studentPlacementForm.getSchoolFilterCountry();
		School school  = schoolUI.getSchool(schoolListRecord.getCode(), null,countryCode);
		school.setCountryCode(countryCode);
		if(school.getCountryCode().equals(PlacementUtilities.getSaCode())){//only for SA
			    List listDistrict = new ArrayList();
			    DistrictUI district=new DistrictUI();
			    listDistrict = district.getDistrictList2("Y", school.getDistrict().getProvince().getCode(),"");
		        studentPlacementForm.setListDistrict(listDistrict);
		        school.setDistrictValue(school.getDistrict().getCode().toString() + "-" + school.getDistrict().getProvince().getCode().toString());
		}else{
			school.setPostalCode("");
		}
		studentPlacementForm.setSchool(school);
		studentPlacementForm.setDistrictFilter("");
		
		studentPlacementForm.setSchoolAction("Edit"); 
 		studentPlacementForm.setCurrentPage("editSchool");
		return mapping.findForward("editSchool");	
	}
	
	public ActionForward saveSchool(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		ActionMessages messages = new ActionMessages();	
		
		if (studentPlacementForm.getSchool().getName()==null || studentPlacementForm.getSchool().getName().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"School Name is required"));
		}
		if(studentPlacementForm.getSchoolFilterCountry().equals(PlacementUtilities.getSaCode())){
		       if (studentPlacementForm.getSchool().getDistrictValue()==null || studentPlacementForm.getSchool().getDistrictValue().equalsIgnoreCase("")){
			           messages.add(ActionMessages.GLOBAL_MESSAGE,
				 	                  new ActionMessage("message.generalmessage",
								      "District is required"));
		       }else{
			           int index = studentPlacementForm.getSchool().getDistrictValue().indexOf("-");
	  		           String district = studentPlacementForm.getSchool().getDistrictValue().trim().substring(0, index);
	  		           String province = studentPlacementForm.getSchool().getDistrictValue().trim().substring(index+1);
	  		           studentPlacementForm.getSchool().getDistrict().setCode(Short.parseShort(district));
	  		           studentPlacementForm.getSchool().getDistrict().getProvince().setCode(Short.parseShort(province));
		      }
		}
		if (studentPlacementForm.getSchool().getTown()==null || studentPlacementForm.getSchool().getTown().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Town is required"));
		}
		if (studentPlacementForm.getSchool().getPhysicalAddress1()==null || studentPlacementForm.getSchool().getPhysicalAddress1().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Physical Address, line 1 is required"));
		}
		if (studentPlacementForm.getSchool().getPostalAddress1()==null || studentPlacementForm.getSchool().getPostalAddress1().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"Postal Address, line 1 is required"));
		}
		if(studentPlacementForm.getSchoolFilterCountry().equals("1015")){
		       if (studentPlacementForm.getSchool().getPostalCode()==null || studentPlacementForm.getSchool().getPostalCode().trim().equalsIgnoreCase("")){
			            messages.add(ActionMessages.GLOBAL_MESSAGE,
					    new ActionMessage("message.generalmessage",
								"Postal Code is required"));
		       }		
		}
		//verify cell number
		if (studentPlacementForm.getSchool().getCellNr()!=null && !studentPlacementForm.getSchool().getCellNr().trim().equalsIgnoreCase("")){
			//Test for valid cell number
				cellphoneValidator cellVerifier = new cellphoneValidator();
				if(!cellVerifier.validCellNumber(studentPlacementForm.getSchool().getCellNr())){
				        messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Invalid Cell phone number. Cell phone number must start with a + and must be at least 12 characters long."));
				}else if (studentPlacementForm.getSchool().getCountryCode().equalsIgnoreCase(PlacementUtilities.getSaCode())){ {
			           	 //check if valid south africa cell phone number
				         CellPhoneVerification cellVerification = new CellPhoneVerification();
				            if(!cellVerification.isSaCellNumber(studentPlacementForm.getSchool().getCellNr())){
					            messages.add(ActionMessages.GLOBAL_MESSAGE,
							    new ActionMessage("message.generalmessage",
										"SA cell phone number must start with +27"));
				            }else if (!cellVerification.validSaCellNumber(studentPlacementForm.getSchool().getCellNr())){
					                messages.add(ActionMessages.GLOBAL_MESSAGE,
							        new ActionMessage("message.generalmessage",
									"Invalid SA cell phone number, wrong number range."));
				            }
			             }
				}
		}
		
		//verify landline number
	    //verify email address
		//verify email address
		EmailValidater emailValidator = new EmailValidater();
		if (studentPlacementForm.getSchool().getEmailAddress()!=null && !studentPlacementForm.getSchool().getEmailAddress().equalsIgnoreCase("")){
			if (!emailValidator.validate(studentPlacementForm.getSchool().getEmailAddress())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
									"Invalid email address."));;
			}
		}
		if (!messages.isEmpty()) {
			addErrors(request,messages);
			studentPlacementForm.setPreviousPage(studentPlacementForm.getCurrentPage());
			studentPlacementForm.setCurrentPage("editSchool");
			return mapping.findForward("editSchool");				
		}		
		
		  SchoolUI schoolUI= new SchoolUI();
		if (studentPlacementForm.getSchoolAction().equalsIgnoreCase("Add")){
			schoolUI.insertSchool(studentPlacementForm.getSchool());
		}
		if (studentPlacementForm.getSchoolAction().equalsIgnoreCase("Edit")){
			schoolUI.updateSchool(studentPlacementForm.getSchool());
		}
		return display(mapping,form,request,response);	
	}
	
	public ActionForward searchDistrictxx(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		
		return mapping.findForward("searchDistrict");	
	}
	public ActionForward searchDistrict(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
		List listDistrict = new ArrayList();
			  DistrictUI  districtUI =new DistrictUI();
		if (studentPlacementForm.getCurrentPage().equalsIgnoreCase("inputSchool") || studentPlacementForm.getCurrentPage().equalsIgnoreCase("listSchool")){
			listDistrict = districtUI.getDistrictList2("Y", studentPlacementForm.getSchoolFilterProvince(),studentPlacementForm.getDistrictFilter());
			String label="ALL";
			String value="0-" + studentPlacementForm.getSchoolFilterProvince().toString();
			listDistrict.add(0,new LabelValueBean(label, value));
			studentPlacementForm.setListFilterSchoolDistrict(listDistrict);	
		}else{			
			listDistrict = districtUI.getDistrictList2("Y", studentPlacementForm.getSchool().getDistrict().getProvince().getCode(),studentPlacementForm.getDistrictFilter());
			studentPlacementForm.setListDistrict(listDistrict);	
		}
		return mapping.findForward(studentPlacementForm.getCurrentPage());	
	}
	
	public ActionForward displayPostalCodeSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		                          StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		                          studentPlacementForm.setSearchType("");
		                          studentPlacementForm.setSearchSuburb("");
		                          studentPlacementForm.setSearchPostalCode("");
		                          studentPlacementForm.setSearchResult("");
		                          return mapping.findForward("searchPostalCode");
	}	
	
	public ActionForward selectSchool(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		                     StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
		 		             ActionMessages messages = new ActionMessages();	
 		                     SchoolUI schoolUI=new SchoolUI();
 		                     String[] indexArr=studentPlacementForm.getIndexNrSelected();
 		                     schoolUI.validateSchoolSelection(indexArr, messages);
		                     studentPlacementForm.setCurrentPage("listSchool");
			                 if (messages.isEmpty()){
			                	       String pageCalledFrom=studentPlacementForm.getSchoolCalledFrom();
	                                   SchoolListRecord school=schoolUI.getSelectedSchool(studentPlacementForm);
		                                        schoolUI.setSchoolDetailsForInputPlacement(studentPlacementForm,school);
		                               StudentPlacement stuPlacement=studentPlacementForm.getStudentPlacement();
		                               Integer schoolCode=school.getCode();
		                               if(pageCalledFrom.equals("inputMentor")||pageCalledFrom.equals("listMentor")){
		                            	   String schoolName=school.getName();
		                            	        studentPlacementForm.getMentorFilterData().setMentorFilterSchoolCode(schoolCode);
		                            	        studentPlacementForm.getMentorFilterData().setMentorFilterSchoolValue(schoolName);
		                            	        studentPlacementForm.getMentorModel().setSchoolCode(schoolCode);
		                            	        studentPlacementForm.getMentorModel().setSchoolName(schoolName);
		                            	}else{
		                                       schoolUI.setSchoolDetailsForEditPlacement(stuPlacement,schoolCode,school.getName(),pageCalledFrom);	
		                                       if(stuPlacement!=null){
		                                               stuPlacement.setTown(schoolUI.getTown(schoolCode));
		                                               stuPlacement.setDatesToRequest(request);
		                                       }
		                               		
		                               }
		                               studentPlacementForm.setCurrentPage(pageCalledFrom);
		                     }
			                 addErrors(request,messages);
		             return mapping.findForward(studentPlacementForm.getCurrentPage());
	}
	 public ActionForward countryOnchangeAction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		        StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
		        studentPlacementForm.setSchoolFilterCountry(studentPlacementForm.getSchoolFilterCountry().trim());
		        if(!studentPlacementForm.getSchoolFilterCountry().trim().equals(PlacementUtilities.getSaCode())){
		        	    studentPlacementForm.setSchoolFilterDistrict(Short.parseShort("0"));
		        	    studentPlacementForm.setSchoolFilterDistrictDesc("All");
		        	    studentPlacementForm.setSchoolFilterDistrictValue("All");
		        	    studentPlacementForm.setSchoolFilterProvince(Short.parseShort("0"));
		        	    studentPlacementForm.setCurrentPage("inputSchool");
	  		    }
		        studentPlacementForm.setSchoolFilter("");
		        studentPlacementForm.setSupervisorFilter("");
		    	return mapping.findForward("inputSchool");
	}

}
