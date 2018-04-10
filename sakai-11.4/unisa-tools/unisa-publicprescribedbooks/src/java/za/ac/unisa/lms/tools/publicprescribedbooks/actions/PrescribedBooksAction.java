package za.ac.unisa.lms.tools.publicprescribedbooks.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import za.ac.unisa.lms.tools.publicprescribedbooks.dao.PrescribedBooksDAO;
import za.ac.unisa.lms.tools.publicprescribedbooks.dao.PrescribedBooksDetails;
import za.ac.unisa.lms.tools.publicprescribedbooks.forms.PublicPrescribedBooksForm;

public class PrescribedBooksAction extends LookupDispatchAction{
	protected Map<String, String> getKeyMethodMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("inputCourse","inputCourse");
		map.put("button.clear","clearForm");
		map.put("button.display","showPrescribedBooks");
		map.put("button.back","inputCourse");
		map.put("button.print","printPrescribedBooks");
		return map;
	}
	public ActionForward inputCourse(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws Exception {
		PublicPrescribedBooksForm publicPrescribedBooksForm = (PublicPrescribedBooksForm) form;
		ArrayList<LabelValueBean> years = new ArrayList<LabelValueBean>();
		String listDate;
		Integer currentYear;
		Integer previousYear;
		Integer nextYear;
		Integer currentMonth;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Calendar curDate1 = Calendar.getInstance();
		Date curDate = new Date();
		listDate = formatter.format(curDate);
		publicPrescribedBooksForm.setAcademicYear(new Integer(curDate1.get(Calendar.YEAR)).toString());
		previousYear = new Integer(curDate1.get(Calendar.YEAR)).intValue()-1;
		currentYear = new Integer(curDate1.get(Calendar.YEAR));
		nextYear = new Integer(curDate1.get(Calendar.YEAR)).intValue()+1;
		currentMonth = new Integer(curDate1.get(Calendar.MONTH));            
		years.add(new LabelValueBean(previousYear.toString(),previousYear.toString()));                        
		years.add(new LabelValueBean(currentYear.toString(),currentYear.toString()));
		years.add(new LabelValueBean(nextYear.toString(),nextYear.toString()));

		if (currentMonth > 9){
			publicPrescribedBooksForm.setAcademicYear(nextYear.toString());
		}                      
		request.setAttribute("years",years);
		publicPrescribedBooksForm.setYears(years);
		publicPrescribedBooksForm.setListDate(listDate);
		return mapping.findForward("courseinput");
	}

	public ActionForward showPrescribedBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		PublicPrescribedBooksForm publicPrescribedBooksForm = (PublicPrescribedBooksForm) form;
		PrescribedBooksDAO prescribedBooksdao = new PrescribedBooksDAO();
		ActionMessages messages = new ActionMessages();
		String subjCodes = "";
		String status ="";
		Vector<PrescribedBooksDetails> list = new Vector<PrescribedBooksDetails>();

		String unitCode1=ltrim(publicPrescribedBooksForm.getUnitCode1());
		unitCode1=rtrim(unitCode1);
		String unitCode2=ltrim (publicPrescribedBooksForm.getUnitCode2());
		unitCode2 = rtrim(unitCode2);
		String unitCode3=ltrim (publicPrescribedBooksForm.getUnitCode3());
		unitCode3 = rtrim(unitCode3);
		String unitCode4=ltrim (publicPrescribedBooksForm.getUnitCode4());
		unitCode4 = rtrim(unitCode4);
		String unitCode5=ltrim (publicPrescribedBooksForm.getUnitCode5());
		unitCode5 = rtrim(unitCode5);
		String unitCode6=ltrim (publicPrescribedBooksForm.getUnitCode6());
		unitCode6 = rtrim(unitCode6);
		String unitCode7=ltrim (publicPrescribedBooksForm.getUnitCode7());
		unitCode7 = rtrim(unitCode7);
		String unitCode8=ltrim (publicPrescribedBooksForm.getUnitCode8());
		unitCode8 = rtrim(unitCode8);
		String unitCode9=ltrim (publicPrescribedBooksForm.getUnitCode9());
		unitCode9 = rtrim(unitCode9);
		String unitCode10=ltrim (publicPrescribedBooksForm.getUnitCode10());
		unitCode10 = rtrim(unitCode10);
		String unitCode11=ltrim (publicPrescribedBooksForm.getUnitCode11());
		unitCode11 = rtrim(unitCode11);
		String unitCode12=ltrim (publicPrescribedBooksForm.getUnitCode12());
		unitCode12 = rtrim(unitCode12);

		boolean invalidCodeFound = false;
		if( unitCode1.equalsIgnoreCase("") && unitCode2.equalsIgnoreCase("") &&
				unitCode3.equalsIgnoreCase("") &&  unitCode4.equalsIgnoreCase("") &&
				unitCode5.equalsIgnoreCase("") && unitCode6.equalsIgnoreCase("") &&
				unitCode7.equalsIgnoreCase("") && unitCode8.equalsIgnoreCase("") &&
				unitCode9.equalsIgnoreCase("") && unitCode10.equalsIgnoreCase("") &&
				unitCode11.equalsIgnoreCase("") && unitCode12.equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Please enter a study unit code."));
			addErrors(request, messages);
			return inputCourse(mapping, form, request, response);        

		}

		if(!unitCode1.equalsIgnoreCase("")){
			if (unitCode1.length() > 5){
				subjCodes = unitCode1+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode1().toUpperCase()+"."));
			}
		}           
		if(!unitCode2.equalsIgnoreCase("")){
			if (unitCode2.length() > 5){
				subjCodes +=unitCode2+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode2().toUpperCase()+"."));
			}
		}
		if(!unitCode3.equalsIgnoreCase("")){
			if (unitCode3.length() > 5){
				subjCodes += unitCode3+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode3().toUpperCase()+"."));
			}
		}
		if(! unitCode4.equalsIgnoreCase("")){
			if ( unitCode4.length() > 5){
				subjCodes +=  unitCode4+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode4().toUpperCase()+"."));
			}
		}
		if(! unitCode5.equalsIgnoreCase("")){
			if ( unitCode5.length() > 5){
				subjCodes +=  unitCode5+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode5().toUpperCase()+"."));
			}
		}
		if(! unitCode6.equalsIgnoreCase("")){
			if ( unitCode6.length() > 5){
				subjCodes +=  unitCode6+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode6().toUpperCase()+"."));
			}

		}
		if(! unitCode7.equalsIgnoreCase("")){
			if ( unitCode7.length() > 5){
				subjCodes +=  unitCode7+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode7().toUpperCase()+"."));
			}

		}
		if(! unitCode8.equalsIgnoreCase("")){
			if ( unitCode8.length() > 5){
				subjCodes +=  unitCode8+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode8().toUpperCase()+"."));
			}

		}
		if(! unitCode9.equalsIgnoreCase("")){
			if ( unitCode9.length() > 5){
				subjCodes +=  unitCode9+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode9().toUpperCase()+"."));
			}
		}
		if(! unitCode10.equalsIgnoreCase("")){
			if ( unitCode10.length() > 5){
				subjCodes +=  unitCode10+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode10().toUpperCase()+"."));
			}
		}
		if(! unitCode11.equalsIgnoreCase("")){
			if ( unitCode11.length() > 5){
				subjCodes +=  unitCode11+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode11().toUpperCase()+"."));
			}
		}
		if(! unitCode12.equalsIgnoreCase("")){
			if ( unitCode12.length() > 5){
				subjCodes +=  unitCode12+",";
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Study Unit Code must be at least be 6 characters, correct or delete "+publicPrescribedBooksForm.getUnitCode12().toUpperCase()+"."));
			}
		}

		if(!messages.isEmpty()){
			addErrors(request,messages);
			return inputCourse(mapping,form,request,response);
		}
		String[] result = subjCodes.split(",");
		String courses = "";
		for(int i=0 ; i < result.length; i++){

			if(prescribedBooksdao.isCodeInValid(result[i].toUpperCase())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The code "+result[i].toUpperCase()+" you entered is not valid Unisa study unit code. Please correct the code or delete from your list."));
				invalidCodeFound = true;
			} else {
				if (courses.equalsIgnoreCase("")){
					courses += prescribedBooksdao.getCourse(result[i].toUpperCase());
				} else {
					courses += ","+prescribedBooksdao.getCourse(result[i].toUpperCase());
				}
			}
		}
		if (invalidCodeFound){
			addErrors(request,messages);
			return inputCourse(mapping,form,request,response);
		}      

		//parameter "1" represent status
		String[] subjects = courses.split(",");
		Arrays.sort(subjects);

		for(int k=0 ; k < subjects.length; k++){
			status = "";
			if (subjects[k]!= null || !subjects[k].equalsIgnoreCase("")){
				status = prescribedBooksdao.getStatus(subjects[k].toUpperCase(),publicPrescribedBooksForm.getAcademicYear());
			}
			List<?> books=null;

			int year=Integer.parseInt(publicPrescribedBooksForm.getAcademicYear());
			if(year<2011){
				if(status.equalsIgnoreCase("1")||status.equalsIgnoreCase("2")){ 
					int count = 0;                                                
					books = prescribedBooksdao.getPrescribedBooksList(publicPrescribedBooksForm.getAcademicYear(),subjects[k].toUpperCase());
					if (books.size() > 0){                                                        
						Iterator<?> i = books.iterator();
						while(i.hasNext()){
							PrescribedBooksDetails prescribedBooksDetails = (PrescribedBooksDetails) i.next();
							if (count % 2 == 0){
								prescribedBooksDetails.setColoured("1");
							} else {
								prescribedBooksDetails.setColoured("0");
							}
							list.addElement(prescribedBooksDetails);  
							count++;
						}
					}

					if (books.size() == 0){
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","No prescribed book information is currently available for "+subjects[k].toUpperCase()));
					}
				} else if(status.equalsIgnoreCase("0") || status == null || status.equalsIgnoreCase("")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","No prescribed book information is currently available for "+subjects[k].toUpperCase()));
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","There are no books prescribed for "+subjects[k].toUpperCase()));
				}

			}else {
				if(status.equalsIgnoreCase("5")||status.equalsIgnoreCase("6")){ 
					int count = 0;

					books = prescribedBooksdao.getPrescribedBooksList(publicPrescribedBooksForm.getAcademicYear(),subjects[k].toUpperCase());

					if (books.size() > 0){                                                        
						Iterator<?> i = books.iterator();
						while(i.hasNext()){
							PrescribedBooksDetails prescribedBooksDetails = (PrescribedBooksDetails) i.next();
							if (count % 2 == 0){
								prescribedBooksDetails.setColoured("1");
							} else {
								prescribedBooksDetails.setColoured("0");
							}
							list.addElement(prescribedBooksDetails);  
							count++;
						}
					}

					if (books.size() == 0){
						messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","No prescribed book information is currently available for "+subjects[k].toUpperCase()));
					}
				} else if(status.equalsIgnoreCase("0") || status == null || status.equalsIgnoreCase("") || status.equalsIgnoreCase("1")|| status.equalsIgnoreCase("2")|| status.equalsIgnoreCase("4")|| status.equalsIgnoreCase("7")){
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","No prescribed book information is currently available for "+subjects[k].toUpperCase()));
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","There are no books prescribed for "+subjects[k].toUpperCase()));
				}
			}
		}

		if (!messages.isEmpty()){ 
			addErrors(request, messages); 
		}
		publicPrescribedBooksForm.setPrescribedBooksList(null);
		if (list.size() > 0 ) {
			publicPrescribedBooksForm.setPrescribedBooksList(list);
		}                    

		return mapping.findForward("prescribedbooksviewforward");                

	}

	public ActionForward clearForm(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PublicPrescribedBooksForm publicPrescribedBooksForm = (PublicPrescribedBooksForm) form;
		publicPrescribedBooksForm.setUnitCode1("");
		publicPrescribedBooksForm.setUnitCode2("");
		publicPrescribedBooksForm.setUnitCode3("");
		publicPrescribedBooksForm.setUnitCode4("");
		publicPrescribedBooksForm.setUnitCode5("");
		publicPrescribedBooksForm.setUnitCode6("");
		publicPrescribedBooksForm.setUnitCode7("");
		publicPrescribedBooksForm.setUnitCode8("");
		publicPrescribedBooksForm.setUnitCode9("");
		publicPrescribedBooksForm.setUnitCode10("");
		publicPrescribedBooksForm.setUnitCode11("");
		publicPrescribedBooksForm.setUnitCode12("");		
		return inputCourse(mapping,form,request,response);
	}
	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
		log.info("PrescribedBooksAction: unspecified method call -no value for parameter action in request");
		return mapping.findForward("home");
	}
	public ActionForward printPrescribedBooks(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PublicPrescribedBooksForm publicPrescribedBooksForm = (PublicPrescribedBooksForm) form;
		ActionMessages messages = new ActionMessages();
		if( publicPrescribedBooksForm.getUnitCode1().equalsIgnoreCase("") && publicPrescribedBooksForm.getUnitCode2().equalsIgnoreCase("") &&
				publicPrescribedBooksForm.getUnitCode3().equalsIgnoreCase("") && publicPrescribedBooksForm.getUnitCode4().equalsIgnoreCase("") &&
				publicPrescribedBooksForm.getUnitCode5().equalsIgnoreCase("") && publicPrescribedBooksForm.getUnitCode6().equalsIgnoreCase("") &&
				publicPrescribedBooksForm.getUnitCode7().equalsIgnoreCase("") && publicPrescribedBooksForm.getUnitCode8().equalsIgnoreCase("")&&
				publicPrescribedBooksForm.getUnitCode9().equalsIgnoreCase("") && publicPrescribedBooksForm.getUnitCode10().equalsIgnoreCase("")&&
				publicPrescribedBooksForm.getUnitCode11().equalsIgnoreCase("") && publicPrescribedBooksForm.getUnitCode12().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Fill in the atleast one Study Unit Code."));
			addErrors(request, messages);

		}
		return mapping.findForward("courseinput");
	}
	/* remove leading whitespace */
	public static String ltrim(String source) {
		return source.replaceAll("^\\s+", "");
	}

	/* remove trailing whitespace */
	public static String rtrim(String source) {
		return source.replaceAll("\\s+$", "");
	}

}

