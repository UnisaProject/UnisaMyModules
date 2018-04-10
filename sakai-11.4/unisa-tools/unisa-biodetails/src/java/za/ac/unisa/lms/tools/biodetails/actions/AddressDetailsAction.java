//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.biodetails.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.ToolManager;

import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.biodetails.dao.AddressDetailsDAO;
import za.ac.unisa.lms.tools.biodetails.forms.BioDetailsForm;
import za.ac.unisa.lms.tools.biodetails.forms.EmailUpdaterClass;
import za.ac.unisa.utils.WorkflowFile;
import Srads01h.Abean.Srads01sMntAddress;

/**
 * MyEclipse Struts
 * Creation date: 09-14-2005
 *
 * XDoclet definition:
 * @struts:action path="/ContactDetailsAction" name="bioDetailsForm" parameter="action" validate="true"
 */
@SuppressWarnings({"unused", "rawtypes"})
public class AddressDetailsAction extends LookupDispatchAction {

	// --------------------------------------------------------- Instance Variables
	
	private EventTrackingService eventTrackingService;
	private ToolManager toolManager;
	private UsageSessionService usageSessionService;

	// --------------------------------------------------------- Methods

	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;

		operListener() {
			exception = null;
		}

		public Exception getException() {
			return exception;
		}

		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
		}
	}

	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.back", "back");
		map.put("button.cancel", "cancel");
		map.put("button.continue", "nextStep");
		map.put("button.submit", "save");
		map.put("button.searchPostal", "displaySearch");
		map.put("button.searchPostSub", "displaySearch");
		map.put("button.selectedCode", "selectedCode");
		map.put("button.search", "search");
		map.put("button.searchagain", "searchAgain");
		map.put("displayStep1", "displayStep1");
		map.put("displayStep2", "displayStep2");
		map.put("nextStep", "nextStep");
		map.put("button.advisor", "advisor");
		map.put("button.request", "updateRequest");
		return map;
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		resetSearchFields(bioDetailsForm);
		resetAll(bioDetailsForm);
		return mapping.findForward("cancel");
	}

	public String physical(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		AddressDetailsDAO dao = new AddressDetailsDAO();
		/* Remove empty lines from the courier address */
		removeEmptyLines(bioDetailsForm, "P");

		if (bioDetailsForm.getPhysicalLine1()==null || "".equalsIgnoreCase(bioDetailsForm.getPhysicalLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete at least the first line of the physical address."));
				addErrors(request, messages);
				return "physical";
		}
		if (!isStreetAddressValid(bioDetailsForm.getPhysicalLine1(), "Physical", request)) {
			return "physical";
		}
		if (bioDetailsForm.getPhysicalPostalCode()==null || "".equalsIgnoreCase(bioDetailsForm.getPhysicalPostalCode().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Physical address postal code is required."));
				addErrors(request, messages);
				return "physical";
		}
		if (bioDetailsForm.getPhysicalPostalCode().trim().length()!=4){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Physical address postal code must consist of 4 digits."));
				addErrors(request, messages);
				return "physical";
		}
		bioDetailsForm.setPhysicalPostalCode(bioDetailsForm.getPhysicalPostalCode().trim());
		if (!isNumeric(bioDetailsForm.getPhysicalPostalCode())) {
			// postal code must be numeric
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Physical address postal code must be numeric."));
			addErrors(request, messages);
			return "physical";
		}
        /* Edmund 2015 - Physical Postal Code/Suburb/Town validation
         * Validating every line as the suburb/town could be on any line.
         * Note: With the Postal Suburb/Town, we expect it to be on the last line, but not the Physical or Courier.
        /* Only do for SA Addresses as we cannot validate international addresses at this stage */	
		log.debug("AddressDetailsAction - Physical - Country: " + bioDetailsForm.getCountryCode());
		if ("1015".equalsIgnoreCase(bioDetailsForm.getCountryCode())){
			log.debug("AddressDetailsAction - Physical - Country is SA - Setting up Suburb");
			if (bioDetailsForm.getPhysicalLine6()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine6().trim())){
				bioDetailsForm.setPhysicalValid(dao.isAddressValid(bioDetailsForm.getPhysicalLine6(),bioDetailsForm.getPhysicalPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isPhysicalValid() && bioDetailsForm.getPhysicalLine5()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine5().trim())){
				bioDetailsForm.setPhysicalValid(dao.isAddressValid(bioDetailsForm.getPhysicalLine5(),bioDetailsForm.getPhysicalPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isPhysicalValid() && bioDetailsForm.getPhysicalLine4()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine4().trim())){
				bioDetailsForm.setPhysicalValid(dao.isAddressValid(bioDetailsForm.getPhysicalLine4(),bioDetailsForm.getPhysicalPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isPhysicalValid() && bioDetailsForm.getPhysicalLine3()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine3().trim())){
				bioDetailsForm.setPhysicalValid(dao.isAddressValid(bioDetailsForm.getPhysicalLine3(),bioDetailsForm.getPhysicalPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isPhysicalValid() && bioDetailsForm.getPhysicalLine2()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine2().trim())){
				bioDetailsForm.setPhysicalValid(dao.isAddressValid(bioDetailsForm.getPhysicalLine2(),bioDetailsForm.getPhysicalPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isPhysicalValid() && bioDetailsForm.getPhysicalLine1()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine1().trim())){
				bioDetailsForm.setPhysicalValid(dao.isAddressValid(bioDetailsForm.getPhysicalLine1(),bioDetailsForm.getPhysicalPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
		    log.debug("AdditionsAction - Step1 - Is Physical Address Valid=" + bioDetailsForm.isPhysicalValid());
		    	
		    if (!bioDetailsForm.isPhysicalValid()){
		    	if (!dao.isCodeValid(bioDetailsForm.getPhysicalPostalCode(), bioDetailsForm.getAddressType(), "S") && !dao.isCodeValid(bioDetailsForm.getPhysicalPostalCode(), bioDetailsForm.getAddressType(), "B")){
		    		log.debug("AddressDetailsAction - Physical - physical Postal code is NOT VALID!!");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The physical address postal code has been entered incorrectly. <BR/>Note: Please select the physical address postal code using the search button below."));
					addErrors(request, messages);
					return "physical";
		    	}
		    	//Check Suburb
		    	boolean isSuburbOK = false;
		    	boolean isTownOK = true;
				if (bioDetailsForm.getPhysicalTown()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalTown().trim())){
						isTownOK = dao.isTownValid(bioDetailsForm.getPhysicalTown(),"Physical", "S");
						if (!isTownOK){
							isTownOK = dao.isTownValid(bioDetailsForm.getPhysicalTown(),"Physical", "B");
						}
				}
				if (bioDetailsForm.getPhysicalSuburb()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalSuburb().trim())){
					isSuburbOK = dao.isSuburbValid(bioDetailsForm.getPhysicalSuburb(),"Physical", "S");
					if (!isSuburbOK){
						isSuburbOK = dao.isSuburbValid(bioDetailsForm.getPhysicalSuburb(),"Physical", "B");
					}
				}
				if (!isSuburbOK){
					log.debug("AddressDetailsAction - Physical - physical Suburb is NOT VALID!!");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The physical address suburb has been entered incorrectly. Please update your physical address before continuing."));
					addErrors(request, messages);
					return "physical";
				}
				if (!isTownOK){
					log.debug("AddressDetailsAction - Physical - physical Town is NOT VALID!!");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The physical address town has been entered incorrectly. Please update your physical address before continuing."));
					addErrors(request, messages);
					return "physical";
				}
			}else{
	    		log.debug("AddressDetailsAction - Physical - physical Suburb & Postal Code do not match!!");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have not entered a physical suburb that corresponds with the postal code or the suburb has been entered incorrectly. Please retype the suburb and click on <Next> to continue."));
				addErrors(request, messages);
				return "physical";
			}
		}else{
			log.debug("AddressDetailsAction - Physical - Country is NOT SA!!! - Continue");
		}

		return "confirmphysical";
	}
	
	public String courier(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		AddressDetailsDAO dao = new AddressDetailsDAO();
		/* Remove empty lines from the courier address */
		removeEmptyLines(bioDetailsForm, "C");

		if (bioDetailsForm.getCourierLine1()==null || "".equalsIgnoreCase(bioDetailsForm.getCourierLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete at least the first line of the courier address."));
				addErrors(request, messages);
				return "courier";
		}
		if (!isStreetAddressValid(bioDetailsForm.getCourierLine1(), "Courier", request)) {
			return "courier";
		}
		
		if("1015".equals(bioDetailsForm.getCountryCode())){
			//Check for Suburb on first Courier Line
			boolean isFirstSubCheck = false;
			//Check for duplication in Courier Address
			boolean isDoubleSubCheck = false;
			String cAddress1 = "";
			String cAddress2 = "";
			String cAddress3 = "";
			String cAddress4 = "";
			String cAddress5 = "";
			String cAddress6 = "";
			
			if (bioDetailsForm.getCourierLine1()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine1().trim())){
				cAddress1 = bioDetailsForm.getCourierLine1();
				isFirstSubCheck = dao.isLineSub(cAddress1);
			}
			if (bioDetailsForm.getCourierLine2()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine2().trim())){
				cAddress2 = bioDetailsForm.getCourierLine2();
			}
			if (bioDetailsForm.getCourierLine3()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine3().trim())){
				cAddress3 = bioDetailsForm.getCourierLine3();
			}
			if (bioDetailsForm.getCourierLine4()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine4().trim())){
				cAddress4 = bioDetailsForm.getCourierLine4();
			}
			if (bioDetailsForm.getCourierLine5()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine5().trim())){
				cAddress5 = bioDetailsForm.getCourierLine5();
			}
			if (bioDetailsForm.getCourierLine6()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine6().trim())){
				cAddress6 = bioDetailsForm.getCourierLine6();
			}

			if(cAddress1 != null && !"".equalsIgnoreCase(cAddress1)){
				if(cAddress1.equalsIgnoreCase(cAddress2) || cAddress1.equalsIgnoreCase(cAddress3) || cAddress1.equalsIgnoreCase(cAddress4) || cAddress1.equalsIgnoreCase(cAddress5) || cAddress1.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AddressDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 1=" + cAddress1+", 2="+ cAddress2+", 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress2 != null && !"".equalsIgnoreCase(cAddress2)){
				if(cAddress2.equalsIgnoreCase(cAddress3) || cAddress2.equalsIgnoreCase(cAddress4) || cAddress2.equalsIgnoreCase(cAddress5) || cAddress2.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AddressDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 2="+ cAddress2+", 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress3 != null && !"".equalsIgnoreCase(cAddress3)){
				if(cAddress3.equalsIgnoreCase(cAddress4) || cAddress3.equalsIgnoreCase(cAddress5) || cAddress3.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AddressDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 3="+ cAddress3+", 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress4 != null && !"".equalsIgnoreCase(cAddress4)){
				if(cAddress4.equalsIgnoreCase(cAddress5) || cAddress4.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AddressDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 4="+ cAddress4+", 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(cAddress5 != null && !"".equalsIgnoreCase(cAddress5)){
				if(cAddress5.equalsIgnoreCase(cAddress6)){
					isDoubleSubCheck = true;
					//log.debug("AddressDetailsAction - Courier - Student Courier Address - isDoubleSubCheck="+isDoubleSubCheck+", Check: 5="+ cAddress5+", 6="+ cAddress6);
				}
			}
			if(isFirstSubCheck){
				//log.debug("AddressDetailsAction - Courier - Student Courier Address  - isFirstSub: " + isFirstSubCheck);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Courier address: The first line may not be a suburb or town. Please include a street name or additional information for the courier delivery."));
				addErrors(request, messages);
				return "courier";
			}
			if(isDoubleSubCheck){
				//log.debug("AddressDetailsAction - Courier - Student Courier Address  - isDoubleSub: " + isDoubleSubCheck);
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have a duplication in your courier address. Please go to Biographical Details or click on &lsquo;Change Address/Contact Details&rsquo; to update your courier address before continuing."));
				addErrors(request, messages);
				return "courier";
			}
		}
		
		if (bioDetailsForm.getCourierPostalCode()==null || "".equalsIgnoreCase(bioDetailsForm.getCourierPostalCode().trim())){
			if(bioDetailsForm.getCountryCode()!= null && "1015".equals(bioDetailsForm.getCountryCode())){
				// postal code required for RSA
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Courier address postal code is required."));
				addErrors(request, messages);
				return "courier";
			}
		}
		if (bioDetailsForm.getCourierPostalCode().trim().length()!=4){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Courier address postal code must consist of 4 digits."));
				addErrors(request, messages);
				return "courier";
		}
		if (!isNumeric(bioDetailsForm.getCourierPostalCode())) {
			// postal code must be numeric
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Courier address postal code must be numeric."));
			addErrors(request, messages);
			return "courier";
		}
        /* Edmund 2015 - Courier Postal Code/Suburb validation
         * Check every line as suburb/town could be on any line
         * Note: With the Postal Suburb, we expect it to be on the last line, but not the Physical or Courier.
        /* Only do for SA Addresses as we cannot validate international addresses at this stage */		
		log.debug("AddressDetailsAction - Courier - Country: " + bioDetailsForm.getCountryCode());
		if ("1015".equalsIgnoreCase(bioDetailsForm.getCountryCode())){
			log.debug("AddressDetailsAction - Courier - Country is SA - Setting up Suburb");
			if (bioDetailsForm.getCourierLine6()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine6().trim())){
				bioDetailsForm.setCourierValid(dao.isAddressValid(bioDetailsForm.getCourierLine6(),bioDetailsForm.getCourierPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isCourierValid() && bioDetailsForm.getCourierLine5()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine5().trim())){
				bioDetailsForm.setCourierValid(dao.isAddressValid(bioDetailsForm.getCourierLine5(),bioDetailsForm.getCourierPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isCourierValid() && bioDetailsForm.getCourierLine4()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine4().trim())){
				bioDetailsForm.setCourierValid(dao.isAddressValid(bioDetailsForm.getCourierLine4(),bioDetailsForm.getCourierPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isCourierValid() && bioDetailsForm.getCourierLine3()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine3().trim())){
				bioDetailsForm.setCourierValid(dao.isAddressValid(bioDetailsForm.getCourierLine3(),bioDetailsForm.getCourierPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isCourierValid() && bioDetailsForm.getCourierLine2()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine2().trim())){
				bioDetailsForm.setCourierValid(dao.isAddressValid(bioDetailsForm.getCourierLine2(),bioDetailsForm.getCourierPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
			if (!bioDetailsForm.isCourierValid() && bioDetailsForm.getCourierLine1()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine1().trim())){
				bioDetailsForm.setCourierValid(dao.isAddressValid(bioDetailsForm.getCourierLine1(),bioDetailsForm.getCourierPostalCode(),bioDetailsForm.getAddressType(), "S"));
			}
		    log.debug("AdditionsAction - Step1 - Is Courier Address Valid=" + bioDetailsForm.isPhysicalValid());
		    	
		    if (!bioDetailsForm.isCourierValid()){
		    	boolean isSubTownOK = false;
		    	if (!dao.isCodeValid(bioDetailsForm.getCourierPostalCode(), bioDetailsForm.getAddressType(), "S") && !dao.isCodeValid(bioDetailsForm.getCourierPostalCode(), bioDetailsForm.getAddressType(), "B")){
		    		log.debug("AddressDetailsAction - Courier - Courier Postal code is NOT VALID!!");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The courier address postal code has been entered incorrectly. <BR/>Note: Please select the courier address postal code using the search button below."));
					addErrors(request, messages);
					return "courier";
		    	}		    	
		    	//Check Suburb
		    	boolean isSuburbOK = false;
		    	boolean isTownOK = true;
				if (bioDetailsForm.getCourierTown()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierTown().trim())){
						isTownOK = dao.isTownValid(bioDetailsForm.getCourierTown(),"Courier", "S");
						if (!isTownOK){
							isTownOK = dao.isTownValid(bioDetailsForm.getCourierTown(),"Courier", "B");
						}
				}
				if (bioDetailsForm.getCourierSuburb()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierSuburb().trim())){
					isSuburbOK = dao.isSuburbValid(bioDetailsForm.getCourierSuburb(),"Courier", "S");
					if (!isSuburbOK){
						isSuburbOK = dao.isSuburbValid(bioDetailsForm.getCourierSuburb(),"Courier", "B");
					}
				}
				if (!isSuburbOK){
					log.debug("AddressDetailsAction - Courier - courier Suburb is NOT VALID!!");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The courier address suburb has been entered incorrectly. Please update your physical address before continuing."));
					addErrors(request, messages);
					return "courier";
				}
				if (!isTownOK){
					log.debug("AddressDetailsAction - Courier - courier Town is NOT VALID!!");
					messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "The courier address town has been entered incorrectly. Please update your physical address before continuing."));
					addErrors(request, messages);
					return "courier";
				}
			}else{
	    		log.debug("AddressDetailsAction - Courier - Courier Suburb & Postal Code do not match!!");
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "You have not entered a courier suburb that corresponds with the postal code or the suburb has been entered incorrectly. Please retype the suburb and click on <Next> to continue."));
				addErrors(request, messages);
				return "courier";
			}
		}else{
			log.debug("AddressDetailsAction - Courier - Country is NOT SA!!! - Continue");
		}
		
		if (bioDetailsForm.getContactNr()==null || "".equalsIgnoreCase(bioDetailsForm.getContactNr().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Contact number is required."));
				addErrors(request, messages);
				return "courier";
		}
		return "confirmcourier";
	}

	/* This method redirects a page to the previous page
	 according to the setup below 	*/
	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		log.debug("AddressDetailsAction - Back - Goto="+request.getParameter("goto"));

		String prevPage = "";
		if ("2a".equalsIgnoreCase(request.getParameter("goto"))
				|| "2b".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "step1";
		} else if ("step3".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "step1";
		} else if ("step4".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "step2";
		} else if ("search".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "step2";
		} else if ("select".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "step2";
		} else if ("physical".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "step1";
		} else if ("courier".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "step1";
		}else if ("savephy".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "physical";
		}else if ("savecou".equalsIgnoreCase(request.getParameter("goto"))) {
			prevPage = "courier";
		}


		return mapping.findForward(prevPage);
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String nextPage = "";
		if ("1".equalsIgnoreCase(request.getParameter("goto"))) {
			return mapping.findForward("step1");
		} else if ("2a".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = displayStep2(mapping, form, request, response);
		} else if ("2b".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = displayStep2(mapping, form, request, response);
		} else if ("3".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = displayStep3(mapping, form, request, response);
		} else if ("search".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = search(mapping, form, request, response);
		} else if ("select".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = selectedCode(mapping, form, request, response);
		} else if ("physical".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = physical(mapping, form, request, response);
		} else if ("courier".equalsIgnoreCase(request.getParameter("goto"))) {
			nextPage = courier(mapping, form, request, response);
		}

		return mapping.findForward(nextPage);
	}

	public ActionForward displaySearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		bioDetailsForm.setSearchType("");
		bioDetailsForm.setSearchSuburb("");
		bioDetailsForm.setSearchPostalCode("");
		bioDetailsForm.setSearchResult("");
		return mapping.findForward("search");
	}
	
	public ActionForward searchAgain(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response)
		    throws Exception {

		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		bioDetailsForm.setSearchType("");
		bioDetailsForm.setSearchSuburb("");
		bioDetailsForm.setSearchPostalCode("");
		bioDetailsForm.setSearchResult("");
		
		  return mapping.findForward("search");
		}

	// Search postal code on postal code or suburb criteria
	public String search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		ActionMessages messages = new ActionMessages();
		ArrayList postalList = new ArrayList();
		try {
			// Do checks on required input
			if (bioDetailsForm.getSearchType() == null
					|| "".equalsIgnoreCase(bioDetailsForm.getSearchType())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"Select to search by postal code or suburb."));
				addErrors(request, messages);
				return "search";
			}
			if ("postal".equalsIgnoreCase(bioDetailsForm.getSearchType())
					&& "".equalsIgnoreCase(bioDetailsForm.getSearchPostalCode())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage(
										"message.generalmessage",
										"You have selected to search by postal code. Please enter the postal code search criteria."));
				addErrors(request, messages);
				return "search";
			}
			if ("suburb".equalsIgnoreCase(bioDetailsForm.getSearchType())
					&& "".equalsIgnoreCase(bioDetailsForm.getSearchSuburb())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage(
										"message.generalmessage",
										"You have selected to search by suburb. Please enter the suburb search criteria."));
				addErrors(request, messages);
				return "search";
			}

			AddressDetailsDAO dao = new AddressDetailsDAO();
			// Do the search on the database
			String postalType = "";
			if ("postal".equalsIgnoreCase(bioDetailsForm.getAddressType()) || "pos".equalsIgnoreCase(bioDetailsForm.getAddressType())){
				postalType = bioDetailsForm.getPostalType();
			}else {
				postalType = "street"; //Only get Street Suburbs or Postal Codes for Physical and Courier
			}
			log.debug("AddresDetailsAction - Search - SearchType="+bioDetailsForm.getSearchType()+", AddressType="+bioDetailsForm.getAddressType()+", SearchPostalCode="+bioDetailsForm.getSearchPostalCode()+", SearchSuburb="+bioDetailsForm.getSearchSuburb()+", PostalType="+postalType);
			if ("postal".equalsIgnoreCase(bioDetailsForm.getSearchType())) {
				bioDetailsForm.setSearchSuburb("");
				postalList = dao.getPostalCodeListByCode(bioDetailsForm.getSearchType(),
						bioDetailsForm.getSearchPostalCode(), postalType, bioDetailsForm.getAddressType());
			}else {
				bioDetailsForm.setSearchPostalCode("");
				postalList = dao.getPostalCodeListBySuburb(bioDetailsForm.getSearchType(),
						bioDetailsForm.getSearchSuburb(), postalType, bioDetailsForm.getAddressType());
			}
			if (postalList.isEmpty()) {
				// no results returned give message
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage","The search returned no results. Please try again."));
				addErrors(request, messages);
			} else {
				//Add postal code list to request
				request.setAttribute("postalList", postalList);
			}
		} catch (Exception e) {
			throw e;
		}

		return "search";
	}

	//	 Postal code selected
	public String selectedCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("selectedCode - Goto="+request.getParameter("goto"));
		String returnType = "";
		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;

		String postalCode = "";
		String suburb = "";
		String town = "";
				
		log.debug("AddressDetailsAction - selectedCode - SearchResult="+bioDetailsForm.getSearchResult().trim());
		
		String []  splitAddress  = bioDetailsForm.getSearchResult().trim().split("~");
		
		log.debug("AddressDetailsAction - selectedCode - SearchResult Length="+splitAddress.length);

		if(splitAddress[0] != null && !splitAddress[0].isEmpty()){
			log.debug("AddressDetailsAction - selectedCode - SearchResult Code="+splitAddress[0]);
			postalCode = splitAddress[0];
		}
		if(splitAddress[1] != null && !splitAddress[1].isEmpty()){
			log.debug("AddressDetailsAction - selectedCode - SearchResult Suburb="+splitAddress[1]);
			suburb = splitAddress[1];
		}
		if(splitAddress.length > 2){
		if(splitAddress[2] != null && !splitAddress[2].isEmpty()){
				log.debug("AddressDetailsAction - selectedCode - SearchResult Town="+splitAddress[2]);
			town = splitAddress[2];
		}
		}
		log.debug("AddressDetailsAction - selectedCode - AddressType="+bioDetailsForm.getAddressType());
		if ("postal".equalsIgnoreCase(bioDetailsForm.getAddressType()) || "pos".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
			if ("Street".equalsIgnoreCase(bioDetailsForm.getPostalType()) || "Straat".equalsIgnoreCase(bioDetailsForm.getPostalType())){
				bioDetailsForm.setPostalStreetCode(postalCode);
				bioDetailsForm.setPostalStreetSuburb(suburb);
			}else{
				bioDetailsForm.setPostalCode(postalCode);
				bioDetailsForm.setPostalSuburb(suburb);
			}
			returnType = "step2";
		} else if ("physical".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
			bioDetailsForm.setPhysicalPostalCode(postalCode);
			bioDetailsForm.setPhysicalSuburb(suburb);
			bioDetailsForm.setPhysicalTown(town);
			returnType = "physical";
		} else {
			bioDetailsForm.setCourierPostalCode(postalCode);
			bioDetailsForm.setCourierSuburb(suburb);
			bioDetailsForm.setCourierTown(town);
			returnType = "courier";
		}
	
		log.debug("AddressDetailsAction - selectedCode - Return="+returnType);
		
		return returnType;
	}

	public ActionForward displayStep1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		resetSearchFields(bioDetailsForm);
		return mapping.findForward("step1");
	}

private boolean isStreetAddressValid(String addressLine, String addressType, HttpServletRequest request ){
	
	boolean result = true;
	ActionMessages messages = new ActionMessages();

	log.debug("AddressDetailsAction - isStreetAddressValid - Testing AddressLine="+addressLine);
		
		if (addressLine.toUpperCase().contains("P O BOX ")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word P O BOX."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("P.O. BOX ")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word P.O. BOX."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("POBOX ")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word PO BOX."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("PO BOX ")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word PO BOX."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains(" BOX ")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word BOX."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("POSTNET SUITE")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word POSTNET SUITE."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("POSTNETSUITE")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word POSTNET SUITE."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("PRIVATE BAG")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word PRIVATE BAG."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("PRIVATEBAG")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word PRIVATEBAG."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("POSBUS")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word POSBUS."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("PRIVAATSAK")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the word PRIVAATSAK."));
			addErrors(request, messages);
			result= false;
		}else if (startsWith("EXT ",addressLine)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Extension must be part of suburb name."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains(" EXT ")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", "Extension must be part of suburb name."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("#")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the # character."));
			addErrors(request, messages);
			result= false;
		}else if (addressLine.toUpperCase().contains("=")){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage", addressType+" address may not contain the = character."));
			addErrors(request, messages);
			result= false;
		}

		return result;

}

private boolean startsWith(String searchString, String addressLine){

	/* May not start with the searchString passed in */
	boolean result = false;
	String test = "";

	if (addressLine!= null){
		test = addressLine.toString();
	}

	if (test.length() >= searchString.length()){
		test = test.substring(0,searchString.length());
	} else{
		return false;
	}

	if (searchString.equalsIgnoreCase(test)){
		result = true;
		return result;
	}
	return result;
}

private String getBoxOrStreet(String addressLine){

	String result = "S";

	if (addressLine.toUpperCase().contains("POSTNET SUITE")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("POSNET SUITE")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("PRIVATE BAG")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("PRIVATEBAG")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("BAG ")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("POSBUS")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("BUS ")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("P O BOX")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("BOX ")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("POSBUS")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("POSTE RESTANTE ")){
		result= "B";
	}else if (addressLine.toUpperCase().contains("POST RESTANTE ")){
		result= "B";
	}

	return result;

}

	public String displayStep2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		AddressDetailsDAO dao = new AddressDetailsDAO();

		log.debug("AddressDetailsAction - displayStep2 - Calling Page="+request.getParameter("page")+", Goto Page="+request.getParameter("goto")+", AddressType="+bioDetailsForm.getAddressType()+", PostalType="+bioDetailsForm.getPostalType());

		// Validate input
		messages = (ActionMessages) bioDetailsForm.validate(mapping, request);
		if (!messages.isEmpty()) {
			addErrors(request, messages);
			if ("1".equalsIgnoreCase(request.getParameter("page"))) {
				return "step1";
			} else if ("2".equalsIgnoreCase(request.getParameter("page"))) {
				return "step2";
			} else if ("3".equalsIgnoreCase(request.getParameter("page"))) {
				return "step2";
			}
		}

		/*
		 *
		 * Tests for page 2b
		 *
		 */
		if ("2b".equalsIgnoreCase(request.getParameter("goto"))) {
			bioDetailsForm.setDisclaimer("0");

			/*
			 *
			 * Test required fields
			 *
			 */
			if ("street".equalsIgnoreCase(bioDetailsForm.getPostalType()) || "straat".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
				if (bioDetailsForm.getPostalStreetLine1()==null || "".equalsIgnoreCase(bioDetailsForm.getPostalStreetLine1().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please complete at least the first line of the postal address."));
					addErrors(request, messages);
					return "step2";
				}
			}else{
				if (bioDetailsForm.getPostalLine1()==null || "".equalsIgnoreCase(bioDetailsForm.getPostalLine1().trim())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please complete at least the first line of the postal address."));
					addErrors(request, messages);
					return "step2";
				}
			}
			
			if ("street".equalsIgnoreCase(bioDetailsForm.getPostalType()) || "straat".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
				if (bioDetailsForm.getPostalStreetCode() == null
						|| "".equalsIgnoreCase(bioDetailsForm.getPostalStreetCode())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, 
							new ActionMessage("message.generalmessage", "Postal code is a required field."));
					addErrors(request, messages);
					return "step2";
				}
			}else{
				if (bioDetailsForm.getPostalCode() == null
						|| "".equalsIgnoreCase(bioDetailsForm.getPostalCode())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, 
							new ActionMessage("message.generalmessage", "Postal code is a required field."));
					addErrors(request, messages);
					return "step2";
				}
			}
			// Tests for box postal addresses
			if (!"street".equalsIgnoreCase(bioDetailsForm.getPostalType())
					&& !"straat".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
				if (bioDetailsForm.getPostalLine1() == null
						|| "".equalsIgnoreCase(bioDetailsForm.getPostalLine1())) {
					// Postnet type: postnet suite nr and private bag nr required
					if ("postnet".equalsIgnoreCase(bioDetailsForm
							.getPostalType())) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter the postnet suite number."));
						addErrors(request, messages);
						return "step2";
					}// Postnet type: postnet suite nr and private bag nr required
					if ("private".equalsIgnoreCase(bioDetailsForm
							.getPostalType())) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter the private bag number."));
						addErrors(request, messages);
						return "step2";
					}else {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Please enter the box number."));
						addErrors(request, messages);
						return "step2";
					}
					// Private bag type: private bag nr required
				} else if ("postnet".equalsIgnoreCase(bioDetailsForm.getPostalType())
						&& (bioDetailsForm.getPostalLine2() == null || "".equalsIgnoreCase(bioDetailsForm.getPostalLine2()))) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Please enter the private bag number."));
					addErrors(request, messages);
					return "step2";
				}

				/*
				 * test first character
				 *
				 */

				// Private bag type: first char of private bag nr must be alpha
				// chars
				if ("private".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
					boolean result = Pattern.matches("[a-zA-Z]", bioDetailsForm.getPostalLine1().substring(0, 1));
					if (!result) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "First character of private bag number must be an alpha character, the rest must be numeric."));
						addErrors(request, messages);
						return "step2";
					}
					// Postnet type: first char of postnet suite nr and private
					// bag nr must be alpha chars
				} else if ("postnet".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
					// Check line1
					boolean result;
					// remove march 2008
					//result = Pattern.matches("[a-zA-Z]", bioDetailsForm.getLine1().substring(0, 1));
					//if (!result) {
						//messages.add(ActionMessages.GLOBAL_MESSAGE,
						//		new ActionMessage("message.generalmessage",
						//		"Postnet Suite: First character of the postnet suite number must be an alpha character, the rest must be numeric."));
						//addErrors(request, messages);
						//return "step2";
					//}
					// Check line2
					result = Pattern.matches("[a-zA-Z]", bioDetailsForm.getPostalLine2().substring(0, 1));
					if (!result) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Private bag: First character of private bag number must be an alpha character, the rest must be numeric."));
						addErrors(request, messages);
						return "step2";
					}
				}

				/*
				 * Numeric test
				 *
				 */

				// Private box/Postnet suite type: box number must be numeric
				if (bioDetailsForm.getPostalLine1() != null
						&& "private".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().trim());
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replaceAll(" ", ""));
					if (!isNumeric(bioDetailsForm.getPostalLine1().substring(1))) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "First character of private bag number must be an alpha character, the rest must be numeric."));
						addErrors(request, messages);
						return "step2";
					}
				} else if ("postnet".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
					if (bioDetailsForm.getPostalLine1() != null) {
						bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().trim());
						bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replaceAll(" ", ""));
						// remove march 2008
						//if (!isNumeric(bioDetailsForm.getLine1().substring(1))) {
						//	messages.add(ActionMessages.GLOBAL_MESSAGE,
						//				new ActionMessage("message.generalmessage",
						//				"Postnet Suite: First character of postnet suite number must be an alpha character, the rest must be numeric."));
						//	addErrors(request, messages);
						//	return "step2";
						//}
					}
					if (bioDetailsForm.getPostalLine2() != null) {
						bioDetailsForm.setPostalLine2(bioDetailsForm.getPostalLine2().trim());
						bioDetailsForm.setPostalLine2(bioDetailsForm.getPostalLine2().replaceAll(" ", ""));
						if (!isNumeric(bioDetailsForm.getPostalLine2().substring(1))) {
							messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Private Bag: First character of private bag number must be an alpha character, the rest must be numeric."));
							addErrors(request, messages);
							return "step2";
						}
					}
				} else if (bioDetailsForm.getPostalLine1() != null) {
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().trim());
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replaceAll(" ", ""));
					if (!isNumeric(bioDetailsForm.getPostalLine1().substring(0))) {
						messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "The box number must be numeric."));
						addErrors(request, messages);
						return "step2";
					}
				}

			}
			// Tests for street postal addresses
			if ("street".equalsIgnoreCase(bioDetailsForm.getPostalType())
					|| "straat".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
				if ("street".equalsIgnoreCase(bioDetailsForm.getPostalType())
						|| "straat".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
					/*
					 * Remove any suburbs the student added from the street
					 * address lines if possible
					 */
					removeSuburb(bioDetailsForm);
				}
				/* Remove empty lines from the street address */
				removeEmptyLines(bioDetailsForm, "S");

				if (!isStreetAddressValid(bioDetailsForm.getPostalStreetLine1(), "Postal", request)) {
					return "step2";
				}
				if (!isStreetAddressValid(bioDetailsForm.getPostalStreetLine2(), "Postal", request)) {
					return "step2";
				}
			}

		} // End tests for page 2b

		/*
		 *
		 * Tests for page 2a
		 *
		 */
		if ("2a".equalsIgnoreCase(request.getParameter("goto"))) {
			log.debug("AddressDetailsAction - displayStep2 - Inside 2a");
			/* International addresses: workflow */
			if ("iAddress".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				// go to advisor assistance
				setAdvisorAddresses(bioDetailsForm);
				ArrayList countryList = new ArrayList();
				countryList = dao.getCountryCodeList();

				if (countryList.isEmpty()) {
					// no results returned give message
					messages.add(ActionMessages.GLOBAL_MESSAGE, 
						new ActionMessage("message.generalmessage","The Country list returned no results. Please try again."));
						addErrors(request, messages);
				} else {
					//Add country list to request
					request.setAttribute("countryList", countryList);
				}
				return "advisor";
			}
			if ("physical".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				return "physical";
			}
			if ("courier".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				return "courier";
			}
			if ("postal".equalsIgnoreCase(bioDetailsForm.getAddressType()) || "pos".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				// physical address can not be empty if postal address is going
				// to be changed.
				// This is a student //System requirement from F465;
				boolean error = true;
				for (int i = 0; i < bioDetailsForm.getPhysical().size(); i++) {
					if (bioDetailsForm.getPhysical().get(i).toString() != null
							&& !"".equals(bioDetailsForm.getPhysical().get(i).toString())) {
						error = false;
						break;
					}
				}
				if (error) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
							"No physical address on record, please update your physical address."));
					addErrors(request, messages);
					return "step1";
				}

			}
			//Remove "P O BOX" etc from address lines before changing as only numbers should be entered.
			log.debug("AddressDetailsAction - displayStep2 - Goto 2a - PostalType="+bioDetailsForm.getPostalType()+", AddressType="+bioDetailsForm.getAddressType());
			if ("po".equalsIgnoreCase(bioDetailsForm.getPostalType()) && ("postal".equalsIgnoreCase(bioDetailsForm.getAddressType()) || "pos".equalsIgnoreCase(bioDetailsForm.getAddressType()))) {
				log.debug("AddressDetailsAction - displayStep2 - Goto 2a - PostalLine1="+bioDetailsForm.getPostalLine1());
				 if (bioDetailsForm.getPostalLine1().contains("P O BOX ")){
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replace("P O BOX ", ""));
				}else if (bioDetailsForm.getPostalLine1().contains("PRIVATE BAG ")){
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replace("PRIVATE BAG ", ""));
				}else if (bioDetailsForm.getPostalLine1().contains("CLUSTER BOX ")){
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replace("CLUSTER BOX ", ""));
				}else if (bioDetailsForm.getPostalLine1().contains("POSTNET SUITE ")){
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replace("POSTNET SUITE ", ""));
					bioDetailsForm.setPostalLine2(bioDetailsForm.getPostalLine2().replace("PRIVATE BAG ", ""));
				}else if (bioDetailsForm.getPostalLine1().contains("POSBUS ")){
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replace("POSBUS ", ""));
				}else if (bioDetailsForm.getPostalLine1().contains("PRIVAATSAK ")){
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replace("PRIVAATSAK ", ""));
				}else if (bioDetailsForm.getPostalLine1().contains("POSNET SUITE ")){
					bioDetailsForm.setPostalLine1(bioDetailsForm.getPostalLine1().replace("POSNET SUITE ", ""));
					bioDetailsForm.setPostalLine2(bioDetailsForm.getPostalLine2().replace("PRIVAATSAK ", ""));
				}
			}
			log.debug("AddressDetailsAction - displayStep2 - Goto 2a - Setting Disclaimer to '0' and returning to Step2?");

			bioDetailsForm.setDisclaimer("0");
			return "step2";
		} else {
			// if no errors go to confirmation screen
			log.debug("AddressDetailsAction - displayStep2 - Goto 2a - No Addresss Errors, Goto Confirmation");

			return "confirm";
		}

	}

	private boolean isNumeric(String testString){

		boolean result = true;

		try{
			Long.parseLong(testString);
		} catch (NumberFormatException e) {
			result = false;
		}

		return result;

	}

	private void removeSuburb(BioDetailsForm form){
		String test ="";

		/* May not contain the searcString passed in */
		for (int i = 2; i < 4; i++) {
			if (i==2 && form.getPostalLine2()!= null){
				test = form.getPostalLine2().replaceAll(" ","");
			}else if (i==3 && form.getPostalLine3()!= null){
				test = form.getPostalLine3().replaceAll(" ","");
			}
			if (!"".equals(test)) {
				String suburbTest = form.getPostalSuburb().replaceAll(" ","");
				if (test.equalsIgnoreCase(suburbTest)){
					if(i==2){
						form.setPostalLine2("");
					} else if(i==3){
						form.setPostalLine3("");
					}
				}
			}
		}
	}

	private void removeEmptyLines(BioDetailsForm bioDetailsForm, String addressType){
		/* if street address remove all empty lines
		 	S = street address
		 	P = Physical address
		 	C = Courier address
		 	*/
		int lineAdded = 0;
		String line1 = "";
		String line2 = "";
		String line3 = "";
		String line4 = "";
		String line5 = "";
		String line6 = "";
		String test = "";

			for (int i = 1; i < 7; i++) {
				if("S".equalsIgnoreCase(addressType)){
					if(i==1){
						test = bioDetailsForm.getPostalLine1();
					}else if (i==2){
						test = bioDetailsForm.getPostalLine2();
					}else if (i==3){
						test = bioDetailsForm.getPostalLine3();
					}else if (i==4){
						test = bioDetailsForm.getPostalLine4();
					}else if (i==5){
						test = bioDetailsForm.getPostalLine5();
					}else if (i==6){
						test = bioDetailsForm.getPostalLine6();
					}
				}else if ("P".equalsIgnoreCase(addressType)){
					if(i==1){
						test = bioDetailsForm.getPhysicalLine1();
					}else if (i==2){
						test = bioDetailsForm.getPhysicalLine2();
					}else if (i==3){
						test = bioDetailsForm.getPhysicalLine3();
					}else if (i==4){
						test = bioDetailsForm.getPhysicalLine4();
					}else if (i==5){
						test = bioDetailsForm.getPhysicalLine5();
					}else if (i==6){
						test = bioDetailsForm.getPhysicalLine6();
					}
				}else if ("C".equalsIgnoreCase(addressType)){
					if(i==1){
						test = bioDetailsForm.getCourierLine1();
					}else if (i==2){
						test = bioDetailsForm.getCourierLine2();
					}else if (i==3){
						test = bioDetailsForm.getCourierLine3();
					}else if (i==4){
						test = bioDetailsForm.getCourierLine4();
					}else if (i==5){
						test = bioDetailsForm.getCourierLine5();
					}else if (i==6){
						test = bioDetailsForm.getCourierLine6();
					}
				}
				if (test!=null && !"".equalsIgnoreCase(test.trim())){
					lineAdded = lineAdded +1;
					if(lineAdded==1){
						line1 = test.trim();
					}else if (lineAdded==2){
						line2 = test.trim();
					}else if (lineAdded==3){
						line3 = test.trim();
					}else if (lineAdded==4){
						line4 = test.trim();
					}else if (lineAdded==5){
						line5 = test.trim();
					}else if (lineAdded==6){
						line6 = test.trim();
					}
				}
			}
			if("S".equalsIgnoreCase(addressType)){
				bioDetailsForm.setPostalLine1(line1.toUpperCase());
				bioDetailsForm.setPostalLine2(line2.toUpperCase());
				bioDetailsForm.setPostalLine3(line3.toUpperCase());
				bioDetailsForm.setPostalLine4(line4.toUpperCase());
				bioDetailsForm.setPostalLine5(line5.toUpperCase());
				bioDetailsForm.setPostalLine6(line6.toUpperCase());
			}else if ("P".equalsIgnoreCase(addressType)){
				bioDetailsForm.setPhysicalLine1(line1.toUpperCase());
				bioDetailsForm.setPhysicalLine2(line2.toUpperCase());
				bioDetailsForm.setPhysicalLine3(line3.toUpperCase());
				bioDetailsForm.setPhysicalLine4(line4.toUpperCase());
				bioDetailsForm.setPhysicalLine5(line5.toUpperCase());
				bioDetailsForm.setPhysicalLine6(line6.toUpperCase());
			}else if ("C".equalsIgnoreCase(addressType)){
				bioDetailsForm.setCourierLine1(line1.toUpperCase());
				bioDetailsForm.setCourierLine2(line2.toUpperCase());
				bioDetailsForm.setCourierLine3(line3.toUpperCase());
				bioDetailsForm.setCourierLine4(line4.toUpperCase());
				bioDetailsForm.setCourierLine5(line5.toUpperCase());
				bioDetailsForm.setCourierLine6(line6.toUpperCase());
			}
	}


	public String displayStep3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			log.debug("displayStep3 - Going to Confirm");
		return "confirm";
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		AddressDetailsDAO dao = new AddressDetailsDAO();
		
		Log log = LogFactory.getLog(BioDetailsAction.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		//startSession(bioDetailsForm.getNumber(),request.getRemoteAddr(),request.getHeader("user-agent"));

		// International addresses: workflow
		if ("iAddress".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
			//got to advisor assistance
			setAdvisorAddresses(bioDetailsForm);
			ArrayList countryList = new ArrayList();
			countryList = dao.getCountryCodeList();

			if (countryList.isEmpty()) {
				// no results returned give message
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage","The Country list returned no results. Please try again."));
					addErrors(request, messages);
			} else {
				//Add country list to request
				request.setAttribute("countryList", countryList);
			}
			return mapping.findForward("advisor");
		}
		if (bioDetailsForm == null ){
			//critical error, NEVER suppose to happen, session might be empty
			return mapping.findForward("home");
		}
		
		if (!"1".equals(bioDetailsForm.getDisclaimer())){
			if ("courier".equalsIgnoreCase(bioDetailsForm.getAddressType())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Please select \"Cancel\" if you do not assume responsibility for the changes made to your address details. Note that all changes will then be ignored. Else, select the option provided at the bottom of this screen."));
				addErrors(request, messages);
				log.debug("Save - Courier - Return to confirmcourier");
				return mapping.findForward("confirmcourier");
			}else if ("physical".equalsIgnoreCase(bioDetailsForm.getAddressType())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Please select \"Cancel\" if you do not assume responsibility for the changes made to your address details. Note that all changes will then be ignored. Else, select the option provided at the bottom of this screen."));
				addErrors(request, messages);
				log.debug("Save - Courier - Return to confirmphysical");
				return mapping.findForward("confirmphysical");
			}else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
							"Please select \"Cancel\" if you do not assume responsibility for the changes made to your address details. Note that all changes will then be ignored. Else, select the option provided at the bottom of this screen."));
				addErrors(request, messages);
				log.debug("Save - Postal - Return to confirm");
				return mapping.findForward("confirm");
			}
		}

		/* SA addresses go to proxy */
		log.debug("Save - saving Address via Srads01sMntAddress");
		Srads01sMntAddress op = new Srads01sMntAddress();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("U");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInWsAddressV2ReferenceNo(Integer.parseInt(bioDetailsForm
				.getNumber()));
		op.setInWsAddressV2Category(Short.parseShort("1"));
		op.setInWsAddressV2Type(Short.parseShort("1"));
		op.setInWsCountryCode("1015");
		op.setInSecurityWsUserNumber(9999);

		/* NB: Contact details have to be set by default for proxy */
		op.setInWsAddressV2HomeNumber(bioDetailsForm.getHomeNr());
		op.setInWsAddressV2WorkNumber(bioDetailsForm.getWorkNr());
		op.setInWsAddressV2FaxNumber(bioDetailsForm.getFaxNr());
		op.setInWsAddressV2CellNumber(bioDetailsForm.getCellNr());
		String emailAddress=bioDetailsForm.getEmail();
		EmailUpdaterClass emailUpdater=new EmailUpdaterClass();
		emailUpdater.setEmailAddressForAddressActionProxy(bioDetailsForm,op,emailAddress);
		
		////////////////////////////////////////////////////
		/*  POSTAL SA address is changed
		 *  send physical along as the proxy expects it. */
		////////////////////////////////////////////////////
		if ("postal".equalsIgnoreCase(bioDetailsForm.getAddressType())||"pos".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
			//Delivery type: S=street, B=box
			if("straat".equalsIgnoreCase(bioDetailsForm.getPostalType()) || "street".equalsIgnoreCase(bioDetailsForm.getPostalType()) ){
				op.setInPOBoxCsfStringsString1("S");
			}else{
				op.setInPOBoxCsfStringsString1("B");
			}
			// ----Physical has to go as well
			for (int i = 0; i < bioDetailsForm.getPhysical().size(); i++) {
				if (i == 0) {
					op.setInPhysicalWsAddressV2AddressLine1(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getPhysicalSuburb().trim());
					}
					if (bioDetailsForm.getPhysicalTown()!= null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysicalTown().trim());
					}
				} else if (i == 1) {
						op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysicalSuburb().trim());
					}
					if (bioDetailsForm.getPhysicalTown()!= null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysicalTown().trim());
					}
				} else if (i == 2) {
						op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysicalSuburb().trim());
					}
					if (bioDetailsForm.getPhysicalTown()!= null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysicalTown().trim());
					}
				} else if (i == 3) {
						op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysicalSuburb().trim());
					}
					if (bioDetailsForm.getPhysicalTown()!= null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysicalTown().trim());
					}
				} else if (i == 4) {
						op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysicalSuburb().trim());
					}
				} else if (i == 5) {
						op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysical().get(i).toString());
				}
			}
			
			op.setInPhysicalWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getPhysicalPostalCode()));
			
			// ------Courier has to go as well
			for (int i = 0; i < bioDetailsForm.getCourier().size(); i++) {
				if (i == 0) {
					op.setInCourierWsAddressV2AddressLine1(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getCourierSuburb().trim());
					}
					if (bioDetailsForm.getCourierTown()!= null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourierTown().trim());
					}
				} else if (i == 1) {
						op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourierSuburb().trim());
					}
					if (bioDetailsForm.getCourierTown()!= null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourierTown().trim());
					}
				} else if (i == 2) {
						op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourierSuburb().trim());
					}
					if (bioDetailsForm.getCourierTown()!= null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourierTown().trim());
					}
				} else if (i == 3) {
						op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourierSuburb().trim());
					}
					if (bioDetailsForm.getCourierTown()!= null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourierTown().trim());
					}
				} else if (i == 4) {
						op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourierSuburb().trim());
					}
				} else if (i == 5) {
						op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourier().get(i).toString());
				}
			}

			op.setInCourierWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getCourierPostalCode()));
			op.setInPhysicalWsAddressV2CourierContactNo(bioDetailsForm.getContactNr());

			/* -------- Set new postal Address */
			boolean isPostalSuburbSet = false;

			// First line of address (Trim all lines to 28 Characters)
			if ("street".equalsIgnoreCase(bioDetailsForm.getPostalType()) || "straat".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
				if (bioDetailsForm.getPostalStreetLine1().length()<=28){
					op.setInWsAddressV2AddressLine1(bioDetailsForm.getPostalStreetLine1().trim());
				}else{
					op.setInWsAddressV2AddressLine1(bioDetailsForm.getPostalStreetLine1().substring(0,28));
				}
			}else if ("po".equalsIgnoreCase(bioDetailsForm.getPostalType()) && "postal".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				if (bioDetailsForm.getPostalLine1().length()<=20){
					op.setInWsAddressV2AddressLine1("P O BOX "+bioDetailsForm.getPostalLine1().trim());
				}else{
					op.setInWsAddressV2AddressLine1("P O BOX "+bioDetailsForm.getPostalLine1().substring(0,20));
				}
			}else if ("private".equalsIgnoreCase(bioDetailsForm.getPostalType())&& "postal".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				if (bioDetailsForm.getPostalLine1().length()<=16){
					op.setInWsAddressV2AddressLine1("PRIVATE BAG "+bioDetailsForm.getPostalLine1().trim());
				}else{
					op.setInWsAddressV2AddressLine1("PRIVATE BAG "+bioDetailsForm.getPostalLine1().substring(0,16));
				}
			}else if ("cluster".equalsIgnoreCase(bioDetailsForm.getPostalType())&& "postal".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				if (bioDetailsForm.getPostalLine1().length()<=16){
					op.setInWsAddressV2AddressLine1("CLUSTER BOX "+bioDetailsForm.getPostalLine1().trim());
				}else{
					op.setInWsAddressV2AddressLine1("CLUSTER BOX "+bioDetailsForm.getPostalLine1().substring(0,16));
				}
			}else if ("postnet".equalsIgnoreCase(bioDetailsForm.getPostalType())&& "postal".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				if (bioDetailsForm.getPostalLine1().length()<=14){
					op.setInWsAddressV2AddressLine1("POSTNET SUITE "+bioDetailsForm.getPostalLine1().trim());
				}else{
					op.setInWsAddressV2AddressLine1("POSTNET SUITE "+bioDetailsForm.getPostalLine1().substring(0,14));
				}
			}else if ("po".equalsIgnoreCase(bioDetailsForm.getPostalType()) && "pos".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				if (bioDetailsForm.getPostalLine1().length()<=21){
					op.setInWsAddressV2AddressLine1("POSBUS "+bioDetailsForm.getPostalLine1().trim());
				}else{
					op.setInWsAddressV2AddressLine1("POSBUS "+bioDetailsForm.getPostalLine1().substring(0,21));
				}
			}else if ("private".equalsIgnoreCase(bioDetailsForm.getPostalType()) && "pos".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				if (bioDetailsForm.getPostalLine1().length()<=17){
					op.setInWsAddressV2AddressLine1("PRIVAATSAK "+bioDetailsForm.getPostalLine1().trim());
				}else{
					op.setInWsAddressV2AddressLine1("PRIVAATSAK "+bioDetailsForm.getPostalLine1().substring(0,17));
				}
			}else if ("postnet".equalsIgnoreCase(bioDetailsForm.getPostalType()) && "pos".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
				if (bioDetailsForm.getPostalLine1().length()<=15){
					op.setInWsAddressV2AddressLine1("POSNET SUITE "+bioDetailsForm.getPostalLine1().trim());
				}else{
					op.setInWsAddressV2AddressLine1("POSNET SUITE "+bioDetailsForm.getPostalLine1().substring(0,15));
				}
			}
			// Rest of address lines - 
			
			log.debug("AddressDetailsAction - Save - Postal Update - AddressType="+bioDetailsForm.getAddressType());
			if ("street".equalsIgnoreCase(bioDetailsForm.getPostalType()) || "straat".equalsIgnoreCase(bioDetailsForm.getPostalType())) {
				if (bioDetailsForm.getPostalStreetLine2()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPostalStreetLine2().trim())){
					if (bioDetailsForm.getPostalStreetLine2().length()<=28){
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalStreetLine2().trim());
					}else{
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalStreetLine2().substring(0,28));
					}
				}else{
					/* Streets get suburb on next empty line */
					if (!isPostalSuburbSet){
						if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalStreetSuburb().trim());
							isPostalSuburbSet = true;
						}
					}
				}
				if (bioDetailsForm.getPostalStreetLine3()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPostalStreetLine3().trim())){
					if (bioDetailsForm.getPostalStreetLine3().length()<=28){
						op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostalStreetLine3().trim());
					}else{
						op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostalStreetLine3().substring(0,28));
					}
				}else{
					/* Streets get suburb on next empty line */
					if (!isPostalSuburbSet){
						if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostalStreetSuburb().trim());
							isPostalSuburbSet = true;
						}
					}
				}
				if (bioDetailsForm.getPostalStreetLine4()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPostalStreetLine4().trim())){
					if (bioDetailsForm.getPostalStreetLine4().length()<=28){
						op.setInWsAddressV2AddressLine4(bioDetailsForm.getPostalStreetLine4().trim());
					}else{
						op.setInWsAddressV2AddressLine4(bioDetailsForm.getPostalStreetLine4().substring(0,28));
					}
				}else{
					/* Streets get suburb on next empty line */
					if (!isPostalSuburbSet){
						if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine4(bioDetailsForm.getPostalStreetSuburb().trim());
							isPostalSuburbSet = true;
						}
					}
				}
				if (bioDetailsForm.getPostalStreetLine5()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPostalStreetLine5().trim())){
					if (bioDetailsForm.getPostalStreetLine5().length()<=28){
						op.setInWsAddressV2AddressLine5(bioDetailsForm.getPostalStreetLine5().trim());
					}else{
						op.setInWsAddressV2AddressLine5(bioDetailsForm.getPostalStreetLine5().substring(0,28));
					}
				}else{
					/* Streets get suburb on next empty line */
					if (!isPostalSuburbSet){
						if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine5(bioDetailsForm.getPostalStreetSuburb().trim());
							isPostalSuburbSet = true;
						}
					}
				}
				if (bioDetailsForm.getPostalStreetLine6()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPostalStreetLine6().trim())){
					if (bioDetailsForm.getPostalStreetLine6().length()<=28){
						op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostalStreetLine6().trim());
					}else{
						op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostalStreetLine6().substring(0,28));
					}
				}else{
					/* Streets get suburb on next empty line */
					if (!isPostalSuburbSet){
						if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostalStreetSuburb().trim());
							isPostalSuburbSet = true;
						}
					}
				}
				short streetPostalCode;
				log.debug("AddressDetailsAction - Save - Postal Update - Street Postal Code="+bioDetailsForm.getPostalStreetCode().trim());
				if("".equals(bioDetailsForm.getPostalStreetCode().trim())){
					streetPostalCode = Short.parseShort("0000");
				}else{
					streetPostalCode = Short.parseShort(bioDetailsForm.getPostalStreetCode());
				}
				op.setInWsAddressV2PostalCode(streetPostalCode);
				log.debug("AddressDetailsAction - Save - Postal Update - Street Postal Code Final="+streetPostalCode);
				
			}else{
				if("postnet".equalsIgnoreCase(bioDetailsForm.getPostalType())){
					if (bioDetailsForm.getPostalLine2()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPostalLine2().trim())){
					if ("pos".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
							if (bioDetailsForm.getPostalLine2().length()<=17){
								op.setInWsAddressV2AddressLine2("PRIVAATSAK "+bioDetailsForm.getPostalLine2().trim());
							}else{
								op.setInWsAddressV2AddressLine2("PRIVAATSAK "+bioDetailsForm.getPostalLine2().substring(0,17));
							}
						}else {
							if (bioDetailsForm.getPostalLine2().length()<=16){
								op.setInWsAddressV2AddressLine2("PRIVATE BAG "+bioDetailsForm.getPostalLine2().trim());
							}else{
								op.setInWsAddressV2AddressLine2("PRIVATE BAG "+bioDetailsForm.getPostalLine2().substring(0,16));
							}
						}
					}
						
						// Postnet gets suburb on third line
						if (!isPostalSuburbSet){
							if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
								op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostalSuburb().trim());
								isPostalSuburbSet = true;
							}
						}
				}else{
					// All boxes except postnet, get suburb on second line
					if (!isPostalSuburbSet){
						if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
							op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalSuburb().trim());
							isPostalSuburbSet = true;
						}
					}
				}
				short postalCode;
				log.debug("AddressDetailsAction - Save - Postal Update - PostBox Postal Code="+bioDetailsForm.getPostalCode().trim());
				if("".equals(bioDetailsForm.getPostalCode().trim())){
					postalCode = Short.parseShort("0000");
				}else{
					postalCode = Short.parseShort(bioDetailsForm.getPostalCode());
				}
				op.setInWsAddressV2PostalCode(postalCode);
				log.debug("AddressDetailsAction - Save - Postal Update - PostBox Postal Code Final="+postalCode);	
			}

		////////////////////////////////////////////////////////////////////
		/* PHYSICAL address was changed
		 * send postal along as the proxy expects it. */
		////////////////////////////////////////////////////////////////////
		} else if ("physical".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
			log.debug("AddressDetailsAction - DisplayStep3 - Physical - Start to Proxy");
			/* Postal has to go as well */
			log.debug("AddressDetailsAction - DisplayStep3 - Send Postal Address First");
			log.debug("AddressDetailsAction - DisplayStep3 - PostalSuburb="+bioDetailsForm.getPostalSuburb().trim());
			log.debug("AddressDetailsAction - DisplayStep3 - PostalStreetSuburb="+bioDetailsForm.getPostalStreetSuburb().trim());
			log.debug("AddressDetailsAction - DisplayStep3 - Postal.size="+bioDetailsForm.getPostal().size());
			
			String physical_PostalLine1="";
			for (int i = 0; i < bioDetailsForm.getPostal().size(); i++) {
				if (i == 0) {
					physical_PostalLine1=bioDetailsForm.getPostal().get(i).toString();
					op.setInWsAddressV2AddressLine1(bioDetailsForm.getPostal().get(i).toString());
					log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line1="+bioDetailsForm.getPostal().get(i).toString());
					if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalSuburb().trim());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostalSuburb().trim());
					}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalStreetSuburb().trim());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostalStreetSuburb().trim());
					}
				} else if (i == 1) {
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostal().get(i).toString());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostal().get(i).toString());
					if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
						op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostalSuburb().trim());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostalSuburb().trim());
					}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
						op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostalStreetSuburb().trim());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostalStreetSuburb().trim());
					}
				} else if (i == 2) {
						op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostal().get(i).toString());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line3="+bioDetailsForm.getPostal().get(i).toString());
					if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
						op.setInWsAddressV2AddressLine4(bioDetailsForm.getPostalSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line3="+bioDetailsForm.getPostalSuburb().trim());
						}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
						op.setInWsAddressV2AddressLine4(bioDetailsForm.getPostalStreetSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line3="+bioDetailsForm.getPostalStreetSuburb().trim());
						}
				} else if (i == 3) {
						op.setInWsAddressV2AddressLine4(bioDetailsForm.getPostal().get(i).toString());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line4="+bioDetailsForm.getPostal().get(i).toString());
					if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
						op.setInWsAddressV2AddressLine5(bioDetailsForm.getPostalSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line4="+bioDetailsForm.getPostalSuburb().trim());
						}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
						op.setInWsAddressV2AddressLine5(bioDetailsForm.getPostalStreetSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line4="+bioDetailsForm.getPostalStreetSuburb().trim());
						}
				} else if (i == 4) {
						op.setInWsAddressV2AddressLine5(bioDetailsForm.getPostal().get(i).toString());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line5="+bioDetailsForm.getPostal().get(i).toString());
					if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
						op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostalSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line5="+bioDetailsForm.getPostalSuburb().trim());
						}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
						op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostalStreetSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line5="+bioDetailsForm.getPostalStreetSuburb().trim());
						}
				} else if (i == 5) {
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line6="+bioDetailsForm.getPostal().get(i).toString());
						if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
							op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostalSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line6="+bioDetailsForm.getPostalSuburb().trim());
						}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostalStreetSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line6="+bioDetailsForm.getPostalStreetSuburb().trim());
						}
					}
				}
			if (bioDetailsForm.getPostalCode() != null && !"".equals(bioDetailsForm.getPostalCode().trim())){
				op.setInWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getPostalCode().trim()));
				log.debug("AddressDetailsAction - DisplayStep3 - PostalCode="+bioDetailsForm.getPostalCode().trim());
			}else if (bioDetailsForm.getPostalStreetCode() != null && !"".equals(bioDetailsForm.getPostalStreetCode().trim())){
				op.setInWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getPostalStreetCode().trim()));
				log.debug("AddressDetailsAction - DisplayStep3 - PostalCodeStreet="+bioDetailsForm.getPostalStreetCode().trim());
			}
			if("S".equalsIgnoreCase(getBoxOrStreet(physical_PostalLine1))){
				op.setInPOBoxCsfStringsString1("S");
				log.debug("AddressDetailsAction - DisplayStep3 - getBoxOrStreet - ?S="+getBoxOrStreet(physical_PostalLine1));
			}else{
				op.setInPOBoxCsfStringsString1("B");
				log.debug("AddressDetailsAction - DisplayStep3 - getBoxOrStreet - ?B="+getBoxOrStreet(physical_PostalLine1));
			}
			log.debug("AddressDetailsAction - DisplayStep3 - Send Postal Address Done");
			
			log.debug("AddressDetailsAction - DisplayStep3 - Send Courier Address Second");
			// ------Courier has to go as well
			for (int i = 0; i < bioDetailsForm.getCourier().size(); i++) {
				if (i == 0) {
					op.setInCourierWsAddressV2AddressLine1(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getCourierSuburb().trim());
					}
					if (bioDetailsForm.getCourierTown()!= null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourierTown().trim());
					}
				} else if (i == 1) {
						op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourierSuburb().trim());
					}
					if (bioDetailsForm.getCourierTown()!= null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourierTown().trim());
					}
				} else if (i == 2) {
						op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourierSuburb().trim());
					}
					if (bioDetailsForm.getCourierTown()!= null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourierTown().trim());
					}
				} else if (i == 3) {
						op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourierSuburb().trim());
					}
					if (bioDetailsForm.getCourierTown()!= null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourierTown().trim());
					}
				} else if (i == 4) {
						op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourier().get(i).toString());
					if (bioDetailsForm.getCourierSuburb()!= null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourierSuburb().trim());
					}
				} else if (i == 5) {
						op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourier().get(i).toString());
				}
			}
			op.setInCourierWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getCourierPostalCode()));
			op.setInPhysicalWsAddressV2CourierContactNo(bioDetailsForm.getContactNr());
			
			/* -------- Set new physical Address */
			log.debug("AddressDetailsAction - DisplayStep3 - Send Physical Address Last");
			boolean isPhysicalSuburbSet = false;
			boolean isPhysicalTownSet = false;
			
			if (bioDetailsForm.getPhysicalLine1()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine1().trim())){
				if (bioDetailsForm.getPhysicalLine1().length()<=28){
					op.setInPhysicalWsAddressV2AddressLine1(bioDetailsForm.getPhysicalLine1().trim());
				}else{
					op.setInPhysicalWsAddressV2AddressLine1(bioDetailsForm.getPhysicalLine1().substring(0,28));
				}
			}
			if (bioDetailsForm.getPhysicalLine2()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine2().trim())){
				if (bioDetailsForm.getPhysicalLine2().length()<=28){
					op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getPhysicalLine2().trim());
				}else{
					op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getPhysicalLine2().substring(0,28));
				}
			}else{
				if (!isPhysicalSuburbSet){
					if (bioDetailsForm.getPhysicalSuburb() != null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getPhysicalSuburb().trim());
						isPhysicalSuburbSet = true;
					}
				}else if (isPhysicalSuburbSet && !isPhysicalTownSet){
					if (bioDetailsForm.getPhysicalTown() != null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getPhysicalTown().trim());
						isPhysicalTownSet = true;
					}
				}
			}
			if (bioDetailsForm.getPhysicalLine3()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine3().trim())){
				if (bioDetailsForm.getPhysicalLine3().length()<=28){
					op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysicalLine3().trim());
				}else{
					op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysicalLine3().substring(0,28));
				}
			}else{
				if (!isPhysicalSuburbSet){
					if (bioDetailsForm.getPhysicalSuburb() != null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysicalSuburb().trim());
						isPhysicalSuburbSet = true;
					}
				}else if (isPhysicalSuburbSet && !isPhysicalTownSet){
					if (bioDetailsForm.getPhysicalTown() != null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysicalTown().trim());
						isPhysicalTownSet = true;
					}
				}
			}
			if (bioDetailsForm.getPhysicalLine4()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine4().trim())){
				if (bioDetailsForm.getPhysicalLine4().length()<=28){
					op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysicalLine4().trim());
				}else{
					op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysicalLine4().substring(0,28));
				}
			}else{
				if (!isPhysicalSuburbSet){
					if (bioDetailsForm.getPhysicalSuburb() != null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysicalSuburb().trim());
						isPhysicalSuburbSet = true;
					}
				}else if (isPhysicalSuburbSet && !isPhysicalTownSet){
					if (bioDetailsForm.getPhysicalTown() != null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysicalTown().trim());
						isPhysicalTownSet = true;
					}
				}
			}
			if (bioDetailsForm.getPhysicalLine5()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine5().trim())){
				if (bioDetailsForm.getPhysicalLine5().length()<=28){
					op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysicalLine5().trim());
				}else{
					op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysicalLine5().substring(0,28));
				}
			}else{
				if (!isPhysicalSuburbSet){
					if (bioDetailsForm.getPhysicalSuburb() != null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysicalSuburb().trim());
						isPhysicalSuburbSet = true;
					}
				}else if (isPhysicalSuburbSet && !isPhysicalTownSet){
					if (bioDetailsForm.getPhysicalTown() != null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysicalTown().trim());
						isPhysicalTownSet = true;
					}
				}
			}
			if (bioDetailsForm.getPhysicalLine6()!=null && !"".equalsIgnoreCase(bioDetailsForm.getPhysicalLine6().trim())){
				if (bioDetailsForm.getPhysicalLine6().length()<=28){
					op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysicalLine6().trim());
				}else{
					op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysicalLine6().substring(0,28));
				}
			}else{
				if (!isPhysicalSuburbSet){
					if (bioDetailsForm.getPhysicalSuburb() != null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysicalSuburb().trim());
						isPhysicalSuburbSet = true;
					}
				}else if (isPhysicalSuburbSet && !isPhysicalTownSet){
					if (bioDetailsForm.getPhysicalTown() != null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysicalTown().trim());
						isPhysicalTownSet = true;
					}
				}
			}
			if("".equals(bioDetailsForm.getPhysicalPostalCode().trim())){
				op.setInPhysicalWsAddressV2PostalCode(Short.parseShort("0000"));
			}else{
				op.setInPhysicalWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getPhysicalPostalCode()));
			}
			
		////////////////////////////////////////////////////////////////////
		/* COURIER address was changed
		 * send postal and physical along as the proxy expects it. */
		////////////////////////////////////////////////////////////////////
		} else if ("courier".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
			log.debug("AddressDetailsAction - DisplayStep3 - Courier - Start to Proxy");
			/* Postal has to go as well */
			log.debug("AddressDetailsAction - DisplayStep3 - Send Postal Address First");
			String courier_PostalLine1="";
			for (int i = 0; i < bioDetailsForm.getPostal().size(); i++) {
				if (i == 0) {
					op.setInWsAddressV2AddressLine1(bioDetailsForm.getPostal().get(i).toString());
					courier_PostalLine1=bioDetailsForm.getPostal().get(i).toString();
					log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line1="+bioDetailsForm.getPostal().get(i).toString());
					if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalSuburb().trim());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostalSuburb().trim());
					}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalStreetSuburb().trim());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostalStreetSuburb().trim());
					}
				} else if (i == 1) {
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostal().get(i).toString());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostal().get(i).toString());
					if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalSuburb().trim());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostalSuburb().trim());
					}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
						op.setInWsAddressV2AddressLine2(bioDetailsForm.getPostalStreetSuburb().trim());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line2="+bioDetailsForm.getPostalStreetSuburb().trim());
					}
				} else if (i == 2) {
						op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostal().get(i).toString());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line3="+bioDetailsForm.getPostal().get(i).toString());
						if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
							op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostalSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line3="+bioDetailsForm.getPostalSuburb().trim());
						}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine3(bioDetailsForm.getPostalStreetSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line3="+bioDetailsForm.getPostalStreetSuburb().trim());
						}
				} else if (i == 3) {
						op.setInWsAddressV2AddressLine4(bioDetailsForm.getPostal().get(i).toString());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line4="+bioDetailsForm.getPostal().get(i).toString());
						if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
							op.setInWsAddressV2AddressLine4(bioDetailsForm.getPostalSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line4="+bioDetailsForm.getPostalSuburb().trim());
						}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine4(bioDetailsForm.getPostalStreetSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line4="+bioDetailsForm.getPostalStreetSuburb().trim());
						}
				} else if (i == 4) {
						op.setInWsAddressV2AddressLine5(bioDetailsForm.getPostal().get(i).toString());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line5="+bioDetailsForm.getPostal().get(i).toString());
						if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
							op.setInWsAddressV2AddressLine5(bioDetailsForm.getPostalSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line5="+bioDetailsForm.getPostalSuburb().trim());
						}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine5(bioDetailsForm.getPostalStreetSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line5="+bioDetailsForm.getPostalStreetSuburb().trim());
						}
				} else if (i == 5) {
						op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostal().get(i).toString());
						log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line6="+bioDetailsForm.getPostal().get(i).toString());
						if (bioDetailsForm.getPostalSuburb() != null && !"".equals(bioDetailsForm.getPostalSuburb().trim())){
							op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostalSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line6="+bioDetailsForm.getPostalSuburb().trim());
						}else if (bioDetailsForm.getPostalStreetSuburb() != null && !"".equals(bioDetailsForm.getPostalStreetSuburb().trim())){
							op.setInWsAddressV2AddressLine6(bioDetailsForm.getPostalStreetSuburb().trim());
							log.debug("AddressDetailsAction - DisplayStep3 - Set Postal Line6="+bioDetailsForm.getPostalStreetSuburb().trim());
						}
					}
				}
			if (bioDetailsForm.getPostalCode() != null && !"".equals(bioDetailsForm.getPostalCode().trim())){
				op.setInWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getPostalCode().trim()));
				log.debug("AddressDetailsAction - DisplayStep3 - PostalCode="+bioDetailsForm.getPostalCode().trim());
			}else if (bioDetailsForm.getPostalStreetCode() != null && !"".equals(bioDetailsForm.getPostalStreetCode().trim())){
				op.setInWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getPostalStreetCode().trim()));
				log.debug("AddressDetailsAction - DisplayStep3 - PostalCodeStreet="+bioDetailsForm.getPostalStreetCode().trim());
			}
			if("S".equalsIgnoreCase(getBoxOrStreet(courier_PostalLine1))){
				op.setInPOBoxCsfStringsString1("S");
				log.debug("AddressDetailsAction - DisplayStep3 - getBoxOrStreet - ?S="+getBoxOrStreet(courier_PostalLine1));
			}else{
				op.setInPOBoxCsfStringsString1("B");
				log.debug("AddressDetailsAction - DisplayStep3 - getBoxOrStreet - ?B="+getBoxOrStreet(courier_PostalLine1));
			}
			log.debug("AddressDetailsAction - DisplayStep3 - Send Postal Address Done");
			
			// ----Physical has to go as well
			for (int i = 0; i < bioDetailsForm.getPhysical().size(); i++) {
				if (i == 0) {
					op.setInPhysicalWsAddressV2AddressLine1(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getPhysicalSuburb().trim());
					}
					if (bioDetailsForm.getPhysicalTown()!= null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysicalTown().trim());
					}
				} else if (i == 1) {
						op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysicalSuburb().trim());
					}
					if (bioDetailsForm.getPhysicalTown()!= null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysicalTown().trim());
					}
				} else if (i == 2) {
						op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysicalSuburb().trim());
					}
					if (bioDetailsForm.getPhysicalTown()!= null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysicalTown().trim());
					}
				} else if (i == 3) {
						op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysicalSuburb().trim());
					}
					if (bioDetailsForm.getPhysicalTown()!= null && !"".equals(bioDetailsForm.getPhysicalTown().trim())){
						op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysicalTown().trim());
					}
				} else if (i == 4) {
						op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getPhysical().get(i).toString());
					if (bioDetailsForm.getPhysicalSuburb()!= null && !"".equals(bioDetailsForm.getPhysicalSuburb().trim())){
						op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysicalSuburb().trim());
					}
				} else if (i == 5) {
						op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getPhysical().get(i).toString());
				}
			}
			
			op.setInPhysicalWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getPhysicalPostalCode()));
			
			/* -------- Set new Courier Address */
			boolean isCourierSuburbSet = false;
			boolean isCourierTownSet = false;
			
			if (bioDetailsForm.getCourierLine1()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine1().trim())){
				if (bioDetailsForm.getCourierLine1().length()<=28){
					op.setInCourierWsAddressV2AddressLine1(bioDetailsForm.getCourierLine1().trim());
				}else{
					op.setInCourierWsAddressV2AddressLine1(bioDetailsForm.getCourierLine1().substring(0,28));
				}
			}
			if (bioDetailsForm.getCourierLine2()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine2().trim())){
				if (bioDetailsForm.getCourierLine2().length()<=28){
					op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getCourierLine2().trim());
				}else{
					op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getCourierLine2().substring(0,28));
				}
			}else{
				if (!isCourierSuburbSet){
					if (bioDetailsForm.getCourierSuburb() != null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getCourierSuburb().trim());
						isCourierSuburbSet = true;
					}
				}else if (isCourierSuburbSet && !isCourierTownSet){
					if (bioDetailsForm.getCourierTown() != null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getCourierTown().trim());
						isCourierTownSet = true;
					}
				}
			}
			if (bioDetailsForm.getCourierLine3()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine3().trim())){
				if (bioDetailsForm.getCourierLine3().length()<=28){
					op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourierLine3().trim());
				}else{
					op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourierLine3().substring(0,28));
				}
			}else{
				if (!isCourierSuburbSet){
					if (bioDetailsForm.getCourierSuburb() != null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourierSuburb().trim());
						isCourierSuburbSet = true;
					}
				}else if (isCourierSuburbSet && !isCourierTownSet){
					if (bioDetailsForm.getCourierTown() != null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getCourierTown().trim());
						isCourierTownSet = true;
					}
				}
			}
			if (bioDetailsForm.getCourierLine4()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine4().trim())){
				if (bioDetailsForm.getCourierLine4().length()<=28){
					op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourierLine4().trim());
				}else{
					op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourierLine4().substring(0,28));
				}
			}else{
				if (!isCourierSuburbSet){
					if (bioDetailsForm.getCourierSuburb() != null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourierSuburb().trim());
						isCourierSuburbSet = true;
					}
				}else if (isCourierSuburbSet && !isCourierTownSet){
					if (bioDetailsForm.getCourierTown() != null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getCourierTown().trim());
						isCourierTownSet = true;
					}
				}
			}
			if (bioDetailsForm.getCourierLine5()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine5().trim())){
				if (bioDetailsForm.getCourierLine5().length()<=28){
					op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourierLine5().trim());
				}else{
					op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourierLine5().substring(0,28));
				}
			}else{
				if (!isCourierSuburbSet){
					if (bioDetailsForm.getCourierSuburb() != null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourierSuburb().trim());
						isCourierSuburbSet = true;
					}
				}else if (isCourierSuburbSet && !isCourierTownSet){
					if (bioDetailsForm.getCourierTown() != null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getCourierTown().trim());
						isCourierTownSet = true;
					}
				}
			}
			if (bioDetailsForm.getCourierLine6()!=null && !"".equalsIgnoreCase(bioDetailsForm.getCourierLine6().trim())){
				if (bioDetailsForm.getCourierLine6().length()<=28){
					op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourierLine6().trim());
				}else{
					op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourierLine6().substring(0,28));
				}
			}else{
				if (!isCourierSuburbSet){
					if (bioDetailsForm.getCourierSuburb() != null && !"".equals(bioDetailsForm.getCourierSuburb().trim())){
						op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourierSuburb().trim());
						isCourierSuburbSet = true;
					}
				}else if (isCourierSuburbSet && !isCourierTownSet){
					if (bioDetailsForm.getCourierTown() != null && !"".equals(bioDetailsForm.getCourierTown().trim())){
						op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getCourierTown().trim());
						isCourierTownSet = true;
					}
				}
			}
			if("".equals(bioDetailsForm.getCourierPostalCode().trim())){
				op.setInCourierWsAddressV2PostalCode(Short.parseShort("0000"));
			}else{
				op.setInCourierWsAddressV2PostalCode(Short.parseShort(bioDetailsForm.getCourierPostalCode()));
			}
			op.setInPhysicalWsAddressV2CourierContactNo(bioDetailsForm.getContactNr());
			op.setInCourierWsAddressV2CourierContactNo(bioDetailsForm.getContactNr());
		}
		
		/* goto Gen proxy*/
		op.execute();
		emailUpdater.updateEmailAddress(bioDetailsForm);
		emailUpdater.resetNonMylifeEmailAddress(bioDetailsForm);

		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());

		String Errormsg = op.getOutCsfStringsString500();
		if ((Errormsg != null) && (!Errormsg.equals(""))) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", Errormsg));
			addErrors(request, messages);
			log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber());
			log.info("ADDRESS coolgen error ="+Errormsg);
			log.info("ADDRESS box/street ="+op.getOutPOBoxCsfStringsString1());
			if(op.getOutWsAddressV2AddressLine1()!= null){
				log.info("ADDRESS line1 ="+op.getOutWsAddressV2AddressLine1());
			}
			if(op.getOutWsAddressV2AddressLine2()!= null){
				log.info("ADDRESS line2 ="+op.getOutWsAddressV2AddressLine2());
			}
			if(op.getOutWsAddressV2AddressLine3()!= null){
				log.info("ADDRESS line3 ="+op.getOutWsAddressV2AddressLine3());
			}
			if(op.getOutWsAddressV2AddressLine4()!= null){
				log.info("ADDRESS line4 ="+op.getOutWsAddressV2AddressLine4());
			}
			if(op.getOutWsAddressV2AddressLine5()!= null){
				log.info("ADDRESS line5 ="+op.getOutWsAddressV2AddressLine5());
			}
			if(op.getOutWsAddressV2AddressLine6()!= null){
				log.info("ADDRESS line6 ="+op.getOutWsAddressV2AddressLine6());
			}
			log.info("ADDRESS postal code ="+op.getOutWsAddressV2PostalCode());
			if(op.getOutPhysicalWsAddressV2AddressLine1()!= null){
				log.info("ADDRESS phys line1 ="+op.getOutPhysicalWsAddressV2AddressLine1());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine2()!= null){
				log.info("ADDRESS phys line2 ="+op.getOutPhysicalWsAddressV2AddressLine2());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine3()!= null){
				log.info("ADDRESS phys line3 ="+op.getOutPhysicalWsAddressV2AddressLine3());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine4()!= null){
				log.info("ADDRESS phys line4 ="+op.getOutPhysicalWsAddressV2AddressLine4());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine5()!= null){
				log.info("ADDRESS phys line5 ="+op.getOutPhysicalWsAddressV2AddressLine5());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine6()!= null){
				log.info("ADDRESS phys line6 ="+op.getOutPhysicalWsAddressV2AddressLine6());
			}
			log.info("ADDRESS phys postal code ="+op.getOutPhysicalWsAddressV2PostalCode());
			if(op.getOutCourierWsAddressV2AddressLine1()!= null){
				log.info("ADDRESS courier line1 ="+op.getOutCourierWsAddressV2AddressLine1());
			}
			if(op.getOutCourierWsAddressV2AddressLine2()!= null){
				log.info("ADDRESS courier line2 ="+op.getOutCourierWsAddressV2AddressLine2());
			}
			if(op.getOutCourierWsAddressV2AddressLine3()!= null){
				log.info("ADDRESS courier line3 ="+op.getOutCourierWsAddressV2AddressLine3());
			}
			if(op.getOutCourierWsAddressV2AddressLine4()!= null){
				log.info("ADDRESS courier line4 ="+op.getOutCourierWsAddressV2AddressLine4());
			}
			if(op.getOutCourierWsAddressV2AddressLine5()!= null){
				log.info("ADDRESS courier line5 ="+op.getOutCourierWsAddressV2AddressLine5());
			}
			if(op.getOutCourierWsAddressV2AddressLine6()!= null){
				log.info("ADDRESS courier line6 ="+op.getOutCourierWsAddressV2AddressLine6());
			}
			log.info("ADDRESS courier postal code ="+op.getOutCourierWsAddressV2PostalCode());
			log.info("ADDRESS ="+Errormsg);

				if ("physical".equalsIgnoreCase(bioDetailsForm.getAddressType())){
					return mapping.findForward("confirmphysical");
				}else if ("courier".equalsIgnoreCase(bioDetailsForm.getAddressType())){
					return mapping.findForward("confirmcourier");
				}
				
		}else {
			/* Write flag 97 */
			dao.writeStudentFlag(bioDetailsForm.getNumber(),"97");

			if ("physical".equalsIgnoreCase(bioDetailsForm.getAddressType())){
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage","Your physical address was updated successfully."));
			}else if ("courier".equalsIgnoreCase(bioDetailsForm.getAddressType())){
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage","Your courier address was updated successfully."));
			}else{
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage","Your postal address was updated successfully."));
			}
			addMessages(request, messages);
			bioDetailsForm.setDisclaimer("");
		
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
  		eventTrackingService.post(
  				eventTrackingService.newEvent(EventTrackingTypes.EVENT_BIODETAILS_ADDRESSCHANGE, toolManager.getCurrentPlacement().getContext()+"/"+bioDetailsForm.getNumber(), false),usageSession);

  			log.debug("Save - Final - Return to home");
			return mapping.findForward("home");
		}

		// write workflow ????
		log.debug("Save - Final - Return to confirm");
		return mapping.findForward("confirm");

	}
	
	//Reset Advisor Address lines
	private void resetAdvisorAddresses(BioDetailsForm form){
		log.debug("AddressDetailsAction - resetAdvisorAddresses");
		
		form.setAdvisorPostalLine1("");
		form.setAdvisorPostalLine2("");
		form.setAdvisorPostalLine3("");
		form.setAdvisorPostalLine4("");
		form.setAdvisorPostalLine5("");
		form.setAdvisorPostalLine6("");
		form.setAdvisorPostalCode("");
		form.setAdvisorPhysicalLine1("");
		form.setAdvisorPhysicalLine2("");
		form.setAdvisorPhysicalLine3("");
		form.setAdvisorPhysicalLine4("");
		form.setAdvisorPhysicalLine5("");
		form.setAdvisorPhysicalLine6("");
		form.setAdvisorPhysicalPostalCode("");
		form.setAdvisorCourierLine1("");
		form.setAdvisorCourierLine2("");
		form.setAdvisorCourierLine3("");
		form.setAdvisorCourierLine4("");
		form.setAdvisorCourierLine5("");
		form.setAdvisorCourierLine6("");
		form.setAdvisorCourierPostalCode("");
		form.setAdvisorContactNr("");
	}
		
	//Set Advisor Address lines so workflow can show old vs new
	private void setAdvisorAddresses(BioDetailsForm form){
		log.debug("AddressDetailsAction - advisor");
		
		resetAdvisorAddresses(form);

		boolean isSuburbSet = false;
		if (form.getPostalLine1() != null && !form.getPostalLine1().toString().trim().equals("")){
			form.setAdvisorPostalLine1(form.getPostalLine1().toString().trim());
		}else if (form.getPostalStreetLine1() != null && !form.getPostalStreetLine1().toString().trim().equals("")){
			form.setAdvisorPostalLine1(form.getPostalStreetLine1().toString().trim());
		}
		if (form.getPostalLine2() != null && !form.getPostalLine2().toString().trim().equals("")){
			form.setAdvisorPostalLine2(form.getPostalLine2().toString().trim());
		}else if (form.getPostalStreetLine2() != null && !form.getPostalStreetLine2().toString().trim().equals("")){
			form.setAdvisorPostalLine2(form.getPostalStreetLine2().toString().trim());
		}else{
			if (form.getPostalSuburb() != null && !form.getPostalSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine2(form.getPostalSuburb().toString().trim());
				isSuburbSet = true;
			}else if (form.getPostalStreetSuburb() != null && !form.getPostalStreetSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine2(form.getPostalStreetSuburb().toString().trim());
				isSuburbSet = true;
			}
		}
		if (form.getPostalLine3() != null && !form.getPostalLine3().toString().trim().equals("")){
			form.setAdvisorPostalLine3(form.getPostalLine3().toString().trim());
		}else if (form.getPostalStreetLine3() != null && !form.getPostalStreetLine3().toString().trim().equals("")){
			form.setAdvisorPostalLine3(form.getPostalStreetLine3().toString().trim());
		}else{
			if (!isSuburbSet && form.getPostalSuburb() != null && !form.getPostalSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine3(form.getPostalSuburb().toString().trim());
				isSuburbSet = true;
			}else if (!isSuburbSet && form.getPostalStreetSuburb() != null && !form.getPostalStreetSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine3(form.getPostalStreetSuburb().toString().trim());
				isSuburbSet = true;
			}
		}
		if (form.getPostalLine4() != null && !form.getPostalLine4().toString().trim().equals("")){
			form.setAdvisorPostalLine4(form.getPostalLine4().toString().trim());
		}else if (form.getPostalStreetLine4() != null && !form.getPostalStreetLine4().toString().trim().equals("")){
			form.setAdvisorPostalLine4(form.getPostalStreetLine4().toString().trim());
		}else{
			if (!isSuburbSet && form.getPostalSuburb() != null && !form.getPostalSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine4(form.getPostalSuburb().toString().trim());
				isSuburbSet = true;
			}else if (!isSuburbSet && form.getPostalStreetSuburb() != null && !form.getPostalStreetSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine4(form.getPostalStreetSuburb().toString().trim());
				isSuburbSet = true;
			}
		}
		if (form.getPostalLine5() != null && !form.getPostalLine5().toString().trim().equals("")){
			form.setAdvisorPostalLine5(form.getPostalLine5().toString().trim());
		}else if (form.getPostalStreetLine5() != null && !form.getPostalStreetLine5().toString().trim().equals("")){
			form.setAdvisorPostalLine5(form.getPostalStreetLine5().toString().trim());
		}else{
			if (!isSuburbSet && form.getPostalSuburb() != null && !form.getPostalSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine5(form.getPostalSuburb().toString().trim());
				isSuburbSet = true;
			}else if (!isSuburbSet && form.getPostalStreetSuburb() != null && !form.getPostalStreetSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine5(form.getPostalStreetSuburb().toString().trim());
				isSuburbSet = true;
			}
		}
		if (form.getPostalLine6() != null && !form.getPostalLine6().toString().trim().equals("")){
			form.setAdvisorPostalLine6(form.getPostalLine6().toString().trim());
		}else if (form.getPostalStreetLine6() != null && !form.getPostalStreetLine6().toString().trim().equals("")){
			form.setAdvisorPostalLine6(form.getPostalStreetLine6().toString().trim());
		}else{
			if (!isSuburbSet && form.getPostalSuburb() != null && !form.getPostalSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine6(form.getPostalSuburb().toString().trim());
				isSuburbSet = true;
			}else if (!isSuburbSet && form.getPostalStreetSuburb() != null && !form.getPostalStreetSuburb().toString().trim().equals("")){
				form.setAdvisorPostalLine6(form.getPostalStreetSuburb().toString().trim());
				isSuburbSet = true;
			}
		}
		if (form.getPostalCode() != null && !form.getPostalCode().toString().trim().equals("")){
			form.setAdvisorPostalCode(form.getPostalCode());
		}
		if (form.getPostalStreetCode() != null && !form.getPostalStreetCode().toString().trim().equals("")){
			form.setAdvisorPostalCode(form.getPostalStreetCode());
		}

		boolean isPhysicalSuburbSet = false;
		boolean isPhysicalTownSet = false;
		if (form.getPhysicalLine1() != null && !form.getPhysicalLine1().toString().trim().equals("")){
			form.setAdvisorPhysicalLine1(form.getPhysicalLine1().toString().trim());
		}
		if (form.getPhysicalLine2() != null && !form.getPhysicalLine2().toString().trim().equals("")){
			form.setAdvisorPhysicalLine2(form.getPhysicalLine2().toString().trim());
		}else{
			if (!isPhysicalSuburbSet && form.getPhysicalSuburb() != null && !form.getPhysicalSuburb().toString().trim().equals("")){
				form.setAdvisorPhysicalLine2(form.getPhysicalSuburb().toString().trim());
				isPhysicalSuburbSet = true;
			}
		}
		if (form.getPhysicalLine3() != null && !form.getPhysicalLine3().toString().trim().equals("")){
			form.setAdvisorPhysicalLine3(form.getPhysicalLine3().toString().trim());
		}else{
			if (!isPhysicalSuburbSet && form.getPhysicalSuburb() != null && !form.getPhysicalSuburb().toString().trim().equals("")){
				form.setAdvisorPhysicalLine3(form.getPhysicalSuburb().toString().trim());
				isPhysicalSuburbSet = true;
			}else if (isPhysicalSuburbSet && form.getPhysicalTown() != null && !form.getPhysicalTown().toString().trim().equals("")){
				form.setAdvisorPhysicalLine3(form.getPhysicalTown().toString().trim());
				isPhysicalTownSet = true;
			}
		}
		if (form.getPhysicalLine4() != null && !form.getPhysicalLine4().toString().trim().equals("")){
			form.setAdvisorPhysicalLine4(form.getPhysicalLine4().toString().trim());
		}else{
			if (!isPhysicalSuburbSet && form.getPhysicalSuburb() != null && !form.getPhysicalSuburb().toString().trim().equals("")){
				form.setAdvisorPhysicalLine4(form.getPhysicalSuburb().toString().trim());
				isPhysicalSuburbSet = true;
			}else if (isPhysicalSuburbSet && !isPhysicalTownSet && form.getPhysicalTown() != null && !form.getPhysicalTown().toString().trim().equals("")){
				form.setAdvisorPhysicalLine4(form.getPhysicalTown().toString().trim());
				isPhysicalTownSet = true;
			}
		}
		if (form.getPhysicalLine5() != null && !form.getPhysicalLine5().toString().trim().equals("")){
			form.setAdvisorPhysicalLine5(form.getPhysicalLine5().toString().trim());
		}else{
			if (!isPhysicalSuburbSet && form.getPhysicalSuburb() != null && !form.getPhysicalSuburb().toString().trim().equals("")){
				form.setAdvisorPhysicalLine5(form.getPhysicalSuburb().toString().trim());
				isPhysicalSuburbSet = true;
			}else if (isPhysicalSuburbSet && !isPhysicalTownSet && form.getPhysicalTown() != null && !form.getPhysicalTown().toString().trim().equals("")){
				form.setAdvisorPhysicalLine5(form.getPhysicalTown().toString().trim());
				isPhysicalTownSet = true;
			}
		}
		if (form.getPhysicalLine6() != null && !form.getPhysicalLine6().toString().trim().equals("")){
			form.setAdvisorPhysicalLine6(form.getPhysicalLine6().toString().trim());
		}else{
			if (!isPhysicalSuburbSet && form.getPhysicalSuburb() != null && !form.getPhysicalSuburb().toString().trim().equals("")){
				form.setAdvisorPhysicalLine6(form.getPhysicalSuburb().toString().trim());
				isPhysicalSuburbSet = true;
			}else if (isPhysicalSuburbSet && !isPhysicalTownSet && form.getPhysicalTown() != null && !form.getPhysicalTown().toString().trim().equals("")){
				form.setAdvisorPhysicalLine6(form.getPhysicalTown().toString().trim());
				isPhysicalTownSet = true;
			}
		}
		if (form.getPhysicalPostalCode() != null && !form.getPhysicalPostalCode().toString().trim().equals("")){
			form.setAdvisorPhysicalPostalCode(form.getPhysicalPostalCode());
		}
		
		boolean isCourierSuburbSet = false;
		boolean isCourierTownSet = false;
		if (form.getCourierLine1() != null && !form.getCourierLine1().toString().trim().equals("")){
			form.setAdvisorCourierLine1(form.getCourierLine1().toString().trim());
		}
		if (form.getCourierLine2() != null && !form.getCourierLine2().toString().trim().equals("")){
			form.setAdvisorCourierLine2(form.getCourierLine2().toString().trim());
		}else{
			if (!isCourierSuburbSet && form.getCourierSuburb() != null && !form.getCourierSuburb().toString().trim().equals("")){
				form.setAdvisorCourierLine2(form.getCourierSuburb().toString().trim());
				isCourierSuburbSet = true;
			}
		}
		if (form.getCourierLine3() != null && !form.getCourierLine3().toString().trim().equals("")){
			form.setAdvisorCourierLine3(form.getCourierLine3().toString().trim());
		}else{
			if (!isCourierSuburbSet && form.getCourierSuburb() != null && !form.getCourierSuburb().toString().trim().equals("")){
				form.setAdvisorCourierLine3(form.getCourierSuburb().toString().trim());
				isCourierSuburbSet = true;
			}else if (isCourierSuburbSet && form.getCourierTown() != null && !form.getCourierTown().toString().trim().equals("")){
				form.setAdvisorCourierLine3(form.getCourierTown().toString().trim());
				isCourierTownSet = true;
			}
		}
		if (form.getCourierLine4() != null && !form.getCourierLine4().toString().trim().equals("")){
			form.setAdvisorCourierLine4(form.getCourierLine4().toString().trim());
		}else{
			if (!isCourierSuburbSet && form.getCourierSuburb() != null && !form.getCourierSuburb().toString().trim().equals("")){
				form.setAdvisorCourierLine4(form.getCourierSuburb().toString().trim());
				isCourierSuburbSet = true;
			}else if (isCourierSuburbSet && !isCourierTownSet && form.getCourierTown() != null && !form.getCourierTown().toString().trim().equals("")){
				form.setAdvisorCourierLine4(form.getCourierTown().toString().trim());
				isCourierTownSet = true;
			}
		}
		if (form.getCourierLine5() != null && !form.getCourierLine5().toString().trim().equals("")){
			form.setAdvisorCourierLine5(form.getCourierLine5().toString().trim());
		}else{
			if (!isCourierSuburbSet && form.getCourierSuburb() != null && !form.getCourierSuburb().toString().trim().equals("")){
				form.setAdvisorCourierLine5(form.getCourierSuburb().toString().trim());
				isCourierSuburbSet = true;
			}else if (isCourierSuburbSet && !isCourierTownSet && form.getCourierTown() != null && !form.getCourierTown().toString().trim().equals("")){
				form.setAdvisorCourierLine5(form.getCourierTown().toString().trim());
				isCourierTownSet = true;
			}
		}
		if (form.getCourierLine6() != null && !form.getCourierLine6().toString().trim().equals("")){
			form.setAdvisorCourierLine6(form.getCourierLine6().toString().trim());
		}else{
			if (!isCourierSuburbSet && form.getCourierSuburb() != null && !form.getCourierSuburb().toString().trim().equals("")){
				form.setAdvisorCourierLine6(form.getCourierSuburb().toString().trim());
				isCourierSuburbSet = true;
			}else if (isCourierSuburbSet && !isCourierTownSet && form.getCourierTown() != null && !form.getCourierTown().toString().trim().equals("")){
				form.setAdvisorCourierLine6(form.getCourierTown().toString().trim());
				isCourierTownSet = true;
			}
		}
		if (form.getCourierPostalCode() != null && !form.getCourierPostalCode().toString().trim().equals("")){
			form.setAdvisorCourierPostalCode(form.getCourierPostalCode());
		}
	}
    
	public ActionForward advisor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			ActionMessages messages = new ActionMessages();
			BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
			AddressDetailsDAO dao = new AddressDetailsDAO();
			
		log.debug("AddressDetailsAction - advisor - Calling setAdvisorAddresses");
			setAdvisorAddresses(bioDetailsForm);
			
			ArrayList countryList = new ArrayList();
			countryList = dao.getCountryCodeList();

			if (countryList.isEmpty()) {
				// no results returned give message
				messages.add(ActionMessages.GLOBAL_MESSAGE, 
					new ActionMessage("message.generalmessage","The Country list returned no results. Please try again."));
					addErrors(request, messages);
			} else {
				//Add country list to request
				request.setAttribute("countryList", countryList);
			}
		return mapping.findForward("advisor");
	}

	public ActionForward updateRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response )throws Exception {

		log.debug("AddressDetailsAction - updateRequest");
		
		ActionMessages messages = new ActionMessages();
		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		AddressDetailsDAO dao = new AddressDetailsDAO();
		
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();
		eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);

		ArrayList countryList = new ArrayList();
		countryList = dao.getCountryCodeList();

		if (countryList.isEmpty()) {
			// no results returned give message
			messages.add(ActionMessages.GLOBAL_MESSAGE, 
				new ActionMessage("message.generalmessage","The Country list returned no results. Please try again."));
				addErrors(request, messages);
		} else {
			//Add country list to request
			request.setAttribute("countryList", countryList);
		}

		Calendar effectiveDate = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		int month = 0;
		int day = 0;
		int year = 0;
		// Change of address effective date number must be numeric
		try {
			year = Integer.parseInt(bioDetailsForm.getAdrYear());
			month = Integer.parseInt(bioDetailsForm.getAdrMonth());
			day = Integer.parseInt(bioDetailsForm.getAdrDay());
		} catch (NumberFormatException e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Change of address effective date must be numeric."));
			addErrors(request, messages);
			return mapping.findForward("advisor");
		}
		if (month < 1 || month>12){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Address effective date: Invalid month."));
			addErrors(request, messages);
			return mapping.findForward("advisor");
		}if (day < 1 || day>31){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Address effective date: Invalid day."));
			addErrors(request, messages);
			return mapping.findForward("advisor");
		}
		effectiveDate.set(year,month-1,day);
		if (effectiveDate.before(today)){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
						"Address effective date invalid."));
			addErrors(request, messages);
			return mapping.findForward("advisor");
		}
		
		//Remove empty lines from addresses. Do it n-times to make sure there wasn't more than one empty line
		RemoveEmptyAdvisorLines(request, response, form, 5);
		
		if (bioDetailsForm.getAdvisorPostalLine1() == null || "".equalsIgnoreCase(bioDetailsForm.getAdvisorPostalLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete at least the first line of the postal address."));
			addErrors(request, messages);
			return mapping.findForward("advisor");
		}
		if (bioDetailsForm.getAdvisorPhysicalLine1()== null || "".equalsIgnoreCase(bioDetailsForm.getAdvisorPhysicalLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete at least the first line of the physical address."));
			addErrors(request, messages);
			return mapping.findForward("advisor");
		}
		if (bioDetailsForm.getAdvisorCourierLine1()== null || "".equalsIgnoreCase(bioDetailsForm.getAdvisorCourierLine1().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please complete at least the first line of the courier address."));
			addErrors(request, messages);
			return mapping.findForward("advisor");
		}
		if (bioDetailsForm.getAdvisorContactNr()==null || "".equalsIgnoreCase(bioDetailsForm.getAdvisorContactNr().trim())){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",	"Courier contact number is required."));
			addErrors(request, messages);
			return mapping.findForward("advisor");
		}
		
		if (!"iAddress".equalsIgnoreCase(bioDetailsForm.getAddressType())) {

			//if postal address not empty, postal code can't be empty
			if(bioDetailsForm.getAdvisorPostalCode() == null || "".equals(bioDetailsForm.getAdvisorPostalCode())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",	"Postal address postal code is required."));
				addErrors(request, messages);
				return mapping.findForward("advisor");
			}
				 if (!isNumeric(bioDetailsForm.getAdvisorPostalCode())){
					 messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Postal adddress postal code must be numeric."));
						addErrors(request, messages);
						return mapping.findForward("advisor");
				 }

			//if physical address not empty, postal code can't be empty
			if(bioDetailsForm.getAdvisorPhysicalPostalCode() == null || "".equals(bioDetailsForm.getAdvisorPhysicalPostalCode())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",	"Physical address postal code is required."));
				addErrors(request, messages);
				return mapping.findForward("advisor");
			}
					if (!isNumeric(bioDetailsForm.getAdvisorPhysicalPostalCode())){
					 messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Physical address postal code must be numeric."));
						addErrors(request, messages);
						return mapping.findForward("advisor");
					}

			//if courier address not empty, postal code can't be empty
			if(bioDetailsForm.getAdvisorCourierPostalCode() == null || "".equals(bioDetailsForm.getAdvisorCourierPostalCode())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",	"Courier address postal code is required."));
				addErrors(request, messages);
				return mapping.findForward("advisor");
			}
					if (!isNumeric(bioDetailsForm.getAdvisorCourierPostalCode())){
					 messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage", "Courier address postal code must be numeric."));
						addErrors(request, messages);
						return mapping.findForward("advisor");
					}

		}else{ //International Addresses
			log.debug("AddressDetailsAction - updateRequest - CountryCode="+bioDetailsForm.getAddressCountryCode());
			if(bioDetailsForm.getAddressCountryCode() == null || "".equals(bioDetailsForm.getAddressCountryCode()) || "-1".equals(bioDetailsForm.getAddressCountryCode())){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
					 new ActionMessage("message.generalmessage", "Please select the country where material should be delivered. Students residing in South Africa may not enter an international address."));
				addErrors(request, messages);
				return mapping.findForward("advisor");
			}
		}
		
		if ("iAddress".equalsIgnoreCase(bioDetailsForm.getAddressType())) {
			//International addresses from the Advisor page are updated immediately, same as in Registrations.
			log.debug("AddressDetailsAction - updateRequest - Calling updateInternational");
			String result=updateInternational(request, response, form);
			
			//Check for Errors
			if (!"OK".equalsIgnoreCase(result)){
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", result));
				addErrors(request, messages);
				return mapping.findForward("advisor");
			}
			
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"Update International Address Details request has been successfully submitted."));
		}else{
			//If address update is sent to the advisors, create a workflow/uniflow document. Database is not updated.
		writeWorkflow(bioDetailsForm);
				
				messages.add(ActionMessages.GLOBAL_MESSAGE,
				new ActionMessage("message.generalmessage",
					"Advisor assistance request has been successfully submitted."));
		}
		addMessages(request, messages);
		resetSearchFields(bioDetailsForm);
	log.debug("AddressDetailsAction - updateRequest - Reset Advisor");
		resetAdvisorAddresses(bioDetailsForm);

		eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_BIODETAILS_ADDRESSCHANGEWORKFLOW, toolManager.getCurrentPlacement().getContext()+"/"+bioDetailsForm.getNumber(), false),usageSession);

		return mapping.findForward("cancel");
	}

	private void RemoveEmptyAdvisorLines(HttpServletRequest request, HttpServletResponse response, ActionForm form, int counter) {
		
		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		
		for (int i=0; i < counter; i++){
			
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines");
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PostalLine6="+bioDetailsForm.getAdvisorPostalLine6());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PostalLine5="+bioDetailsForm.getAdvisorPostalLine5());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PostalLine4="+bioDetailsForm.getAdvisorPostalLine4());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PostalLine3="+bioDetailsForm.getAdvisorPostalLine3());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PostalLine2="+bioDetailsForm.getAdvisorPostalLine2());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PostalLine1="+bioDetailsForm.getAdvisorPostalLine1());
			
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PhysicalLine6="+bioDetailsForm.getAdvisorPhysicalLine6());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PhysicalLine5="+bioDetailsForm.getAdvisorPhysicalLine5());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PhysicalLine4="+bioDetailsForm.getAdvisorPhysicalLine4());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PhysicalLine3="+bioDetailsForm.getAdvisorPhysicalLine3());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PhysicalLine2="+bioDetailsForm.getAdvisorPhysicalLine2());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - PhysicalLine1="+bioDetailsForm.getAdvisorPhysicalLine1());
			
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - CourierLine6="+bioDetailsForm.getAdvisorCourierLine6());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - CourierLine5="+bioDetailsForm.getAdvisorCourierLine5());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - CourierLine4="+bioDetailsForm.getAdvisorCourierLine4());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - CourierLine3="+bioDetailsForm.getAdvisorCourierLine3());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - CourierLine2="+bioDetailsForm.getAdvisorCourierLine2());
			log.debug("AddressDetailsAction - RemoveEmptyAdvisorLines - CourierLine1="+bioDetailsForm.getAdvisorCourierLine1());
			
			// Remove Empty lines of Postal address by moving all lines up into empty lines
			if ((bioDetailsForm.getAdvisorPostalLine6() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine6().trim()))
				&& (bioDetailsForm.getAdvisorPostalLine5() == null || "".equals(bioDetailsForm.getAdvisorPostalLine5().trim()))){
				bioDetailsForm.setAdvisorPostalLine5(bioDetailsForm.getAdvisorPostalLine6().trim());
				bioDetailsForm.setAdvisorPostalLine6("");
			}
			if (bioDetailsForm.getAdvisorPostalLine5() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine5().trim())
				&& bioDetailsForm.getAdvisorPostalLine4() == null || "".equals(bioDetailsForm.getAdvisorPostalLine4().trim())){
				bioDetailsForm.setAdvisorPostalLine4(bioDetailsForm.getAdvisorPostalLine5().trim());
				bioDetailsForm.setAdvisorPostalLine5("");
			}
			if ((bioDetailsForm.getAdvisorPostalLine4() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine4().trim()))
				&& (bioDetailsForm.getAdvisorPostalLine3() == null || "".equals(bioDetailsForm.getAdvisorPostalLine3().trim()))){
				bioDetailsForm.setAdvisorPostalLine3(bioDetailsForm.getAdvisorPostalLine4().trim());
				bioDetailsForm.setAdvisorPostalLine4("");
			}
			if ((bioDetailsForm.getAdvisorPostalLine3() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine3().trim()))
				&& (bioDetailsForm.getAdvisorPostalLine2() == null || "".equals(bioDetailsForm.getAdvisorPostalLine2().trim()))){
				bioDetailsForm.setAdvisorPostalLine2(bioDetailsForm.getAdvisorPostalLine3().trim());
				bioDetailsForm.setAdvisorPostalLine3("");
			}
			if ((bioDetailsForm.getAdvisorPostalLine2() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine2().trim()))
				&& (bioDetailsForm.getAdvisorPostalLine1() == null || "".equals(bioDetailsForm.getAdvisorPostalLine1().trim()))){
				bioDetailsForm.setAdvisorPostalLine1(bioDetailsForm.getAdvisorPostalLine2().trim());
				bioDetailsForm.setAdvisorPostalLine2("");
			}
	
			// Remove Empty lines of Physical address by moving all lines up into empty lines
			if ((bioDetailsForm.getAdvisorPhysicalLine6() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine6().trim()))
				&& (bioDetailsForm.getAdvisorPhysicalLine5() == null || "".equals(bioDetailsForm.getAdvisorPhysicalLine5().trim()))){
				bioDetailsForm.setAdvisorPhysicalLine5(bioDetailsForm.getAdvisorPhysicalLine6().trim());
				bioDetailsForm.setAdvisorPhysicalLine6("");
			}
			if ((bioDetailsForm.getAdvisorPhysicalLine5() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine5().trim()))
				&& (bioDetailsForm.getAdvisorPhysicalLine4() == null || "".equals(bioDetailsForm.getAdvisorPhysicalLine4().trim()))){
				bioDetailsForm.setAdvisorPhysicalLine4(bioDetailsForm.getAdvisorPhysicalLine5().trim());
				bioDetailsForm.setAdvisorPhysicalLine5("");
			}
			if ((bioDetailsForm.getAdvisorPhysicalLine4() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine4().trim()))
				&& (bioDetailsForm.getAdvisorPhysicalLine3() == null || "".equals(bioDetailsForm.getAdvisorPhysicalLine3().trim()))){
				bioDetailsForm.setAdvisorPhysicalLine3(bioDetailsForm.getAdvisorPhysicalLine4().trim());
				bioDetailsForm.setAdvisorPhysicalLine4("");
			}
			if ((bioDetailsForm.getAdvisorPhysicalLine3() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine3().trim()))
				&& (bioDetailsForm.getAdvisorPhysicalLine2() == null || "".equals(bioDetailsForm.getAdvisorPhysicalLine2().trim()))){
				bioDetailsForm.setAdvisorPhysicalLine2(bioDetailsForm.getAdvisorPhysicalLine3().trim());
				bioDetailsForm.setAdvisorPhysicalLine3("");
			}
			if ((bioDetailsForm.getAdvisorPhysicalLine2() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine2().trim()))
				&& (bioDetailsForm.getAdvisorPhysicalLine1() == null || "".equals(bioDetailsForm.getAdvisorPhysicalLine1().trim()))){
				bioDetailsForm.setAdvisorPhysicalLine1(bioDetailsForm.getAdvisorPhysicalLine2().trim());
				bioDetailsForm.setAdvisorPhysicalLine2("");
			}
	
			// Remove Empty lines of Courier address by moving all lines up into empty lines
			if ((bioDetailsForm.getAdvisorCourierLine6() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine6().trim()))
				&& (bioDetailsForm.getAdvisorCourierLine5() == null || "".equals(bioDetailsForm.getAdvisorCourierLine5().trim()))){
				bioDetailsForm.setAdvisorCourierLine5(bioDetailsForm.getAdvisorCourierLine6().trim());
				bioDetailsForm.setAdvisorCourierLine6("");
			}
			if ((bioDetailsForm.getAdvisorCourierLine5() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine5().trim()))
				&& (bioDetailsForm.getAdvisorCourierLine4() == null || "".equals(bioDetailsForm.getAdvisorCourierLine4().trim()))){
				bioDetailsForm.setAdvisorCourierLine4(bioDetailsForm.getAdvisorCourierLine5().trim());
				bioDetailsForm.setAdvisorCourierLine5("");
			}
			if ((bioDetailsForm.getAdvisorCourierLine4() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine4().trim()))
				&& (bioDetailsForm.getAdvisorCourierLine3() == null || "".equals(bioDetailsForm.getAdvisorCourierLine3().trim()))){
				bioDetailsForm.setAdvisorCourierLine3(bioDetailsForm.getAdvisorCourierLine4().trim());
				bioDetailsForm.setAdvisorCourierLine4("");
			}
			if ((bioDetailsForm.getAdvisorCourierLine3() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine3().trim()))
				&& (bioDetailsForm.getAdvisorCourierLine2() == null || "".equals(bioDetailsForm.getAdvisorCourierLine2().trim()))){
				bioDetailsForm.setAdvisorCourierLine2(bioDetailsForm.getAdvisorCourierLine3().trim());
				bioDetailsForm.setAdvisorCourierLine3("");
			}
			if ((bioDetailsForm.getAdvisorCourierLine2() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine2().trim()))
				&& (bioDetailsForm.getAdvisorCourierLine1() == null || "".equals(bioDetailsForm.getAdvisorCourierLine1().trim()))){
				bioDetailsForm.setAdvisorCourierLine1(bioDetailsForm.getAdvisorCourierLine2().trim());
				bioDetailsForm.setAdvisorCourierLine2("");
			}
		}
	}

	private String updateInternational(HttpServletRequest request, HttpServletResponse response, ActionForm form) throws Exception {
		
		String result="OK";
		ActionMessages messages = new ActionMessages();
		BioDetailsForm bioDetailsForm = (BioDetailsForm) form;
		AddressDetailsDAO dao = new AddressDetailsDAO();
		
		Log log = LogFactory.getLog(BioDetailsAction.class);
		usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
		UsageSession usageSession = usageSessionService.getSession();


		/* International addresses go to proxy */
		log.debug("updateInternational - saving Internation Address via Srads01sMntAddress");
		Srads01sMntAddress op = new Srads01sMntAddress();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("U");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInWsAddressV2ReferenceNo(Integer.parseInt(bioDetailsForm.getNumber()));
		op.setInWsAddressV2Category(Short.parseShort("1"));
		op.setInWsAddressV2Type(Short.parseShort("1"));
		op.setInWsCountryCode(bioDetailsForm.getAddressCountryCode());
		op.setInSecurityWsUserNumber(9999);

		/* NB: Contact details have to be set by default for proxy */
		op.setInWsAddressV2HomeNumber(bioDetailsForm.getHomeNr());
		op.setInWsAddressV2WorkNumber(bioDetailsForm.getWorkNr());
		op.setInWsAddressV2FaxNumber(bioDetailsForm.getFaxNr());
		op.setInWsAddressV2CellNumber(bioDetailsForm.getCellNr());
		String emailAddress=bioDetailsForm.getEmail();
		EmailUpdaterClass emailUpdater=new EmailUpdaterClass();
		emailUpdater.setEmailAddressForAddressActionProxy(bioDetailsForm,op,emailAddress);
		
		//Set populated Address lines and make sure they are not longer than 28 characters for proxy

		//By default International addresses are "Street" addresses as these should not be tested for "POBox" etc in the Proxy
		op.setInPOBoxCsfStringsString1("S");
		
		/* -------- Set new International Postal Address */
		if (bioDetailsForm.getAdvisorPostalLine1() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine1().trim())){
			if (bioDetailsForm.getAdvisorPostalLine1().length()<=28){
				op.setInWsAddressV2AddressLine1(bioDetailsForm.getAdvisorPostalLine1().trim());
			}else{
				op.setInWsAddressV2AddressLine1(bioDetailsForm.getAdvisorPostalLine1().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPostalLine2() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine2().trim())){
			if (bioDetailsForm.getAdvisorPostalLine2().length()<=28){
				op.setInWsAddressV2AddressLine2(bioDetailsForm.getAdvisorPostalLine2().trim());
			}else{
				op.setInWsAddressV2AddressLine2(bioDetailsForm.getAdvisorPostalLine2().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPostalLine3() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine3().trim())){
			if (bioDetailsForm.getAdvisorPostalLine3().length()<=28){
				op.setInWsAddressV2AddressLine3(bioDetailsForm.getAdvisorPostalLine3().trim());
			}else{
				op.setInWsAddressV2AddressLine3(bioDetailsForm.getAdvisorPostalLine3().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPostalLine4() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine4().trim())){
			if (bioDetailsForm.getAdvisorPostalLine4().length()<=28){
				op.setInWsAddressV2AddressLine4(bioDetailsForm.getAdvisorPostalLine4().trim());
			}else{
				op.setInWsAddressV2AddressLine4(bioDetailsForm.getAdvisorPostalLine4().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPostalLine5() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine5().trim())){
			if (bioDetailsForm.getAdvisorPostalLine5().length()<=28){
				op.setInWsAddressV2AddressLine5(bioDetailsForm.getAdvisorPostalLine5().trim());
			}else{
				op.setInWsAddressV2AddressLine5(bioDetailsForm.getAdvisorPostalLine5().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPostalLine6() != null && !"".equals(bioDetailsForm.getAdvisorPostalLine6().trim())){
			if (bioDetailsForm.getAdvisorPostalLine6().length()<=28){
				op.setInWsAddressV2AddressLine6(bioDetailsForm.getAdvisorPostalLine6().trim());
			}else{
				op.setInWsAddressV2AddressLine6(bioDetailsForm.getAdvisorPostalLine6().substring(0,28));
			}
		}
		op.setInWsAddressV2PostalCode(Short.parseShort("0000"));

		/* -------- Set new International Physical Address */
		if (bioDetailsForm.getAdvisorPhysicalLine1() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine1().trim())){
			if (bioDetailsForm.getAdvisorPhysicalLine1().length()<=28){
				op.setInPhysicalWsAddressV2AddressLine1(bioDetailsForm.getAdvisorPhysicalLine1().trim());
			}else{
				op.setInPhysicalWsAddressV2AddressLine1(bioDetailsForm.getAdvisorPhysicalLine1().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPhysicalLine2() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine2().trim())){
			if (bioDetailsForm.getAdvisorPhysicalLine2().length()<=28){
				op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getAdvisorPhysicalLine2().trim());
			}else{
				op.setInPhysicalWsAddressV2AddressLine2(bioDetailsForm.getAdvisorPhysicalLine2().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPhysicalLine3() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine3().trim())){
			if (bioDetailsForm.getAdvisorPhysicalLine3().length()<=28){
				op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getAdvisorPhysicalLine3().trim());
			}else{
				op.setInPhysicalWsAddressV2AddressLine3(bioDetailsForm.getAdvisorPhysicalLine3().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPhysicalLine4() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine4().trim())){
			if (bioDetailsForm.getAdvisorPhysicalLine4().length()<=28){
				op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getAdvisorPhysicalLine4().trim());
			}else{
				op.setInPhysicalWsAddressV2AddressLine4(bioDetailsForm.getAdvisorPhysicalLine4().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPhysicalLine5() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine5().trim())){
			if (bioDetailsForm.getAdvisorPhysicalLine5().length()<=28){
				op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getAdvisorPhysicalLine5().trim());
			}else{
				op.setInPhysicalWsAddressV2AddressLine5(bioDetailsForm.getAdvisorPhysicalLine5().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorPhysicalLine6() != null && !"".equals(bioDetailsForm.getAdvisorPhysicalLine6().trim())){
			if (bioDetailsForm.getAdvisorPhysicalLine6().length()<=28){
				op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getAdvisorPhysicalLine6().trim());
			}else{
				op.setInPhysicalWsAddressV2AddressLine6(bioDetailsForm.getAdvisorPhysicalLine6().substring(0,28));
			}
		}
		op.setInPhysicalWsAddressV2PostalCode(Short.parseShort("0000"));
		op.setInPhysicalWsAddressV2CourierContactNo(bioDetailsForm.getAdvisorContactNr());

		/* -------- Set new International Courier Address */
		if (bioDetailsForm.getAdvisorCourierLine1() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine1().trim())){
			if (bioDetailsForm.getAdvisorCourierLine1().length()<=28){
				op.setInCourierWsAddressV2AddressLine1(bioDetailsForm.getAdvisorCourierLine1().trim());
			}else{
				op.setInCourierWsAddressV2AddressLine1(bioDetailsForm.getAdvisorCourierLine1().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorCourierLine2() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine2().trim())){
			if (bioDetailsForm.getAdvisorCourierLine2().length()<=28){
				op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getAdvisorCourierLine2().trim());
			}else{
				op.setInCourierWsAddressV2AddressLine2(bioDetailsForm.getAdvisorCourierLine2().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorCourierLine3() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine3().trim())){
			if (bioDetailsForm.getAdvisorCourierLine3().length()<=28){
				op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getAdvisorCourierLine3().trim());
			}else{
				op.setInCourierWsAddressV2AddressLine3(bioDetailsForm.getAdvisorCourierLine3().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorCourierLine4() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine4().trim())){
			if (bioDetailsForm.getAdvisorCourierLine4().length()<=28){
				op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getAdvisorCourierLine4().trim());
			}else{
				op.setInCourierWsAddressV2AddressLine4(bioDetailsForm.getAdvisorCourierLine4().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorCourierLine5() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine5().trim())){
			if (bioDetailsForm.getAdvisorCourierLine5().length()<=28){
				op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getAdvisorCourierLine5().trim());
			}else{
				op.setInCourierWsAddressV2AddressLine5(bioDetailsForm.getAdvisorCourierLine5().substring(0,28));
			}
		}
		if (bioDetailsForm.getAdvisorCourierLine6() != null && !"".equals(bioDetailsForm.getAdvisorCourierLine6().trim())){
			if (bioDetailsForm.getAdvisorCourierLine6().length()<=28){
				op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getAdvisorCourierLine6().trim());
			}else{
				op.setInCourierWsAddressV2AddressLine6(bioDetailsForm.getAdvisorCourierLine6().substring(0,28));
			}
		}

		op.setInCourierWsAddressV2PostalCode(Short.parseShort("0000"));
		op.setInCourierWsAddressV2CourierContactNo(bioDetailsForm.getAdvisorContactNr());

		log.debug("AddressDetailsAction - updateInternational");
		log.debug("AddressDetailsAction - updateInternational - PostalLine6="+bioDetailsForm.getAdvisorPostalLine6());
		log.debug("AddressDetailsAction - updateInternational - PostalLine5="+bioDetailsForm.getAdvisorPostalLine5());
		log.debug("AddressDetailsAction - updateInternational - PostalLine4="+bioDetailsForm.getAdvisorPostalLine4());
		log.debug("AddressDetailsAction - updateInternational - PostalLine3="+bioDetailsForm.getAdvisorPostalLine3());
		log.debug("AddressDetailsAction - updateInternational - PostalLine2="+bioDetailsForm.getAdvisorPostalLine2());
		log.debug("AddressDetailsAction - updateInternational - PostalLine1="+bioDetailsForm.getAdvisorPostalLine1());
		
		log.debug("AddressDetailsAction - updateInternational - PhysicalLine6="+bioDetailsForm.getAdvisorPhysicalLine6());
		log.debug("AddressDetailsAction - updateInternational - PhysicalLine5="+bioDetailsForm.getAdvisorPhysicalLine5());
		log.debug("AddressDetailsAction - updateInternational - PhysicalLine4="+bioDetailsForm.getAdvisorPhysicalLine4());
		log.debug("AddressDetailsAction - updateInternational - PhysicalLine3="+bioDetailsForm.getAdvisorPhysicalLine3());
		log.debug("AddressDetailsAction - updateInternational - PhysicalLine2="+bioDetailsForm.getAdvisorPhysicalLine2());
		log.debug("AddressDetailsAction - updateInternational - PhysicalLine1="+bioDetailsForm.getAdvisorPhysicalLine1());
		
		log.debug("AddressDetailsAction - updateInternational - CourierLine6="+bioDetailsForm.getAdvisorCourierLine6());
		log.debug("AddressDetailsAction - updateInternational - CourierLine5="+bioDetailsForm.getAdvisorCourierLine5());
		log.debug("AddressDetailsAction - updateInternational - CourierLine4="+bioDetailsForm.getAdvisorCourierLine4());
		log.debug("AddressDetailsAction - updateInternational - CourierLine3="+bioDetailsForm.getAdvisorCourierLine3());
		log.debug("AddressDetailsAction - updateInternational - CourierLine2="+bioDetailsForm.getAdvisorCourierLine2());
		log.debug("AddressDetailsAction - updateInternational - CourierLine1="+bioDetailsForm.getAdvisorCourierLine1());

		
		//Do Update
		/* goto Gen proxy*/
		op.execute();
		emailUpdater.updateEmailAddress(bioDetailsForm);
		emailUpdater.resetNonMylifeEmailAddress(bioDetailsForm);
		
		//Check Update output for errors
		if (opl.getException() != null)	throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		
		String Errormsg = op.getOutCsfStringsString500();
		if ((Errormsg != null) && (!Errormsg.equals(""))) {
			result = Errormsg;
			log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber());
			log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+","+Errormsg);
			log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS box/street ="+op.getOutPOBoxCsfStringsString1());
			if(op.getOutWsAddressV2AddressLine1()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS line1 ="+op.getOutWsAddressV2AddressLine1());
			}
			if(op.getOutWsAddressV2AddressLine2()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS line2 ="+op.getOutWsAddressV2AddressLine2());
			}
			if(op.getOutWsAddressV2AddressLine3()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS line3 ="+op.getOutWsAddressV2AddressLine3());
			}
			if(op.getOutWsAddressV2AddressLine4()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS line4 ="+op.getOutWsAddressV2AddressLine4());
			}
			if(op.getOutWsAddressV2AddressLine5()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS line5 ="+op.getOutWsAddressV2AddressLine5());
			}
			if(op.getOutWsAddressV2AddressLine6()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS line6 ="+op.getOutWsAddressV2AddressLine6());
			}
			log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS postal code ="+op.getOutWsAddressV2PostalCode());
			if(op.getOutPhysicalWsAddressV2AddressLine1()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS phys line1 ="+op.getOutPhysicalWsAddressV2AddressLine1());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine2()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS phys line2 ="+op.getOutPhysicalWsAddressV2AddressLine2());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine3()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS phys line3 ="+op.getOutPhysicalWsAddressV2AddressLine3());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine4()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS phys line4 ="+op.getOutPhysicalWsAddressV2AddressLine4());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine5()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS phys line5 ="+op.getOutPhysicalWsAddressV2AddressLine5());
			}
			if(op.getOutPhysicalWsAddressV2AddressLine6()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS phys line6 ="+op.getOutPhysicalWsAddressV2AddressLine6());
			}
			log.info("ADDRESS phys postal code ="+op.getOutPhysicalWsAddressV2PostalCode());
			if(op.getOutCourierWsAddressV2AddressLine1()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS courier line1 ="+op.getOutCourierWsAddressV2AddressLine1());
			}
			if(op.getOutCourierWsAddressV2AddressLine2()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS courier line2 ="+op.getOutCourierWsAddressV2AddressLine2());
			}
			if(op.getOutCourierWsAddressV2AddressLine3()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS courier line3 ="+op.getOutCourierWsAddressV2AddressLine3());
			}
			if(op.getOutCourierWsAddressV2AddressLine4()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS courier line4 ="+op.getOutCourierWsAddressV2AddressLine4());
			}
			if(op.getOutCourierWsAddressV2AddressLine5()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS courier line5 ="+op.getOutCourierWsAddressV2AddressLine5());
			}
			if(op.getOutCourierWsAddressV2AddressLine6()!= null){
				log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS courier line6 ="+op.getOutCourierWsAddressV2AddressLine6());
			}
			log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS courier postal code ="+op.getOutCourierWsAddressV2PostalCode());
			log.info("ADDRESS coolgen error for studnr="+bioDetailsForm.getNumber()+" ADDRESS ="+Errormsg);
		}else {
			/* Write flag 97 */
			dao.writeStudentFlag(bioDetailsForm.getNumber(),"97");
			
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
			eventTrackingService.post(
				eventTrackingService.newEvent(EventTrackingTypes.EVENT_BIODETAILS_ADDRESSCHANGE, toolManager.getCurrentPlacement().getContext()+"/"+bioDetailsForm.getNumber(), false),usageSession);
				log.debug("Save - Final - Return to home");
		}
		
		return result;
	}
	
	//Reset search fields
	private void resetSearchFields(BioDetailsForm bioDetailsForm)
			throws Exception {
		
	log.debug("AddressDetailsAction - resetSearchFields");
		
		bioDetailsForm.setPostalType("");
		bioDetailsForm.setAddressType("");
		bioDetailsForm.setSearchType("");
		bioDetailsForm.setSearchSuburb("");
		bioDetailsForm.setSearchPostalCode("");
		bioDetailsForm.setSearchResult("");

	}

	//Reset All fields
	private void resetAll(BioDetailsForm bioDetailsForm) throws Exception {
		
	log.debug("AddressDetailsAction - resetSearchFields");
		
		bioDetailsForm.setPostalType("");
		bioDetailsForm.setAddressType("");
		bioDetailsForm.setSearchType("");
		bioDetailsForm.setSearchSuburb("");
		bioDetailsForm.setSearchPostalCode("");
		bioDetailsForm.setSearchResult("");
		bioDetailsForm.setPostalLine1("");
		bioDetailsForm.setPostalLine2("");
		bioDetailsForm.setPostalLine3("");
		bioDetailsForm.setPostalLine4("");
		bioDetailsForm.setPostalLine5("");
		bioDetailsForm.setPostalLine6("");
		bioDetailsForm.setPostalSuburb("");
		bioDetailsForm.setPostalCode("");
		bioDetailsForm.setPostalStreetLine1("");
		bioDetailsForm.setPostalStreetLine2("");
		bioDetailsForm.setPostalStreetLine3("");
		bioDetailsForm.setPostalStreetLine4("");
		bioDetailsForm.setPostalStreetLine5("");
		bioDetailsForm.setPostalStreetLine6("");
		bioDetailsForm.setPostalStreetSuburb("");
		bioDetailsForm.setPostalStreetCode("");
		bioDetailsForm.setPhysicalLine1("");
		bioDetailsForm.setPhysicalLine2("");
		bioDetailsForm.setPhysicalLine3("");
		bioDetailsForm.setPhysicalLine4("");
		bioDetailsForm.setPhysicalLine5("");
		bioDetailsForm.setPhysicalLine6("");
		bioDetailsForm.setPhysicalSuburb("");
		bioDetailsForm.setPhysicalPostalCode("");
		bioDetailsForm.setCourierLine1("");
		bioDetailsForm.setCourierLine2("");
		bioDetailsForm.setCourierLine3("");
		bioDetailsForm.setCourierLine4("");
		bioDetailsForm.setCourierLine5("");
		bioDetailsForm.setCourierLine6("");
		bioDetailsForm.setCourierSuburb("");
		bioDetailsForm.setCourierPostalCode("");
		bioDetailsForm.setContactNr("");
		
		bioDetailsForm.setAdvisorPostalLine1("");
		bioDetailsForm.setAdvisorPostalLine2("");
		bioDetailsForm.setAdvisorPostalLine3("");
		bioDetailsForm.setAdvisorPostalLine4("");
		bioDetailsForm.setAdvisorPostalLine5("");
		bioDetailsForm.setAdvisorPostalLine6("");
		bioDetailsForm.setAdvisorPostalSuburb("");
		bioDetailsForm.setAdvisorPostalCode("");
		bioDetailsForm.setAdvisorPhysicalLine1("");
		bioDetailsForm.setAdvisorPhysicalLine2("");
		bioDetailsForm.setAdvisorPhysicalLine3("");
		bioDetailsForm.setAdvisorPhysicalLine4("");
		bioDetailsForm.setAdvisorPhysicalLine5("");
		bioDetailsForm.setAdvisorPhysicalLine6("");
		bioDetailsForm.setAdvisorPhysicalSuburb("");
		bioDetailsForm.setAdvisorPhysicalPostalCode("");
		bioDetailsForm.setAdvisorCourierLine1("");
		bioDetailsForm.setAdvisorCourierLine2("");
		bioDetailsForm.setAdvisorCourierLine3("");
		bioDetailsForm.setAdvisorCourierLine4("");
		bioDetailsForm.setAdvisorCourierLine5("");
		bioDetailsForm.setAdvisorCourierLine6("");
		bioDetailsForm.setAdvisorCourierSuburb("");
		bioDetailsForm.setAdvisorCourierPostalCode("");
		bioDetailsForm.setAdvisorContactNr("");
	}

	private void writeWorkflow(BioDetailsForm bioDetailsForm) throws Exception {

		String date = (new java.text.SimpleDateFormat(
				"EEEEE dd MMMMM yyyy hh:mm:ss").format(new java.util.Date()).toString());
		//String time = (new java.text.SimpleDateFormat("hhmmss").format(new java.util.Date()).toString());
		String fileName = "";

		/* Setup file according to Local or Foreign */
		if (bioDetailsForm.getNumber().length()==7){
			 	fileName = "addrch_"+bioDetailsForm.getForeign()+"_0";
		}else{
				fileName = "addrch_"+bioDetailsForm.getForeign()+"_";
		}
		fileName = fileName + bioDetailsForm.getNumber();
		WorkflowFile file = new WorkflowFile(fileName);
		file.add(" The following request was received through myUnisa: \r\n");
		file.add(" Type of request: Change address details \r\n");
		file.add(" Request received on " + date + "\r\n\r\n");
		file.add(" Student Number: "+bioDetailsForm.getNumber()+"\r\n");
		file.add(" Degree:  "+bioDetailsForm.getQual().getQualCode()+"  " + bioDetailsForm.getQual().getQualDesc()+"\r\n");
		file.add(" Country Code:  "+bioDetailsForm.getCountryCode()+"  " + bioDetailsForm.getForeign()+ "\r\n\r\n");
		file.add(" Details:\r\n");
		file.add(" ----------------------------------------\r\n");
		file.add(" Change of address with effect from "+bioDetailsForm.getAdrYear()+"/"+bioDetailsForm.getAdrMonth()+"/"+bioDetailsForm.getAdrDay()+"\r\n\r\n");

		if ("iAddress".equals(bioDetailsForm.getAddressType())){
	        bioDetailsForm.setAddressForeign("F");//Foreign
	    	file.add(" Current Country Code:  "+bioDetailsForm.getCountryCode()+"  " + bioDetailsForm.getForeign()+ "\r\n");
	    	file.add(" New Country Code:  "+bioDetailsForm.getAddressCountryCode()+"  " + bioDetailsForm.getAddressForeign()+ "\r\n\r\n");
        }        
		
		file.add(" Current Postal Address\r\n");
		for (int i = 0; i < bioDetailsForm.getPostal().size(); i++) {
			file.add(" " +bioDetailsForm.getPostal().get(i).toString()+"\r\n");
			log.debug("AddressDetailsAction - WriteWorkflow - Current Postal Address="+bioDetailsForm.getPostal().get(i).toString());
		}
		log.debug("AddressDetailsAction - WriteWorkflow - Current Postal Code="+bioDetailsForm.getPostalCode());
		log.debug("AddressDetailsAction - WriteWorkflow - Current Postal Street Code="+bioDetailsForm.getPostalStreetCode());
		if (bioDetailsForm.getPostalCode() != null && !bioDetailsForm.getPostalCode().equalsIgnoreCase("")){
			file.add(" Postal Code: "+bioDetailsForm.getPostalCode()+"\r\n\n");
		}else if (bioDetailsForm.getPostalStreetCode() != null && !bioDetailsForm.getPostalStreetCode().equalsIgnoreCase("")){
			file.add(" Postal Code: "+bioDetailsForm.getPostalStreetCode()+"\r\n\n");
		}else{
			file.add(" Postal Code: 0000\r\n\n");
		}
			
		file.add(" New Postal Address\r\n");
		if (bioDetailsForm.getAdvisorPostalLine1() != null && !bioDetailsForm.getAdvisorPostalLine1().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPostalLine1()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPostalLine2() != null && !bioDetailsForm.getAdvisorPostalLine2().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPostalLine2()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPostalLine3() != null && !bioDetailsForm.getAdvisorPostalLine3().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPostalLine3()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPostalLine4() != null && !bioDetailsForm.getAdvisorPostalLine4().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPostalLine4()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPostalLine5() != null && !bioDetailsForm.getAdvisorPostalLine5().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPostalLine5()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPostalLine6() != null && !bioDetailsForm.getAdvisorPostalLine6().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPostalLine6()+"\r\n");
		}
		if (!"iAddress".equals(bioDetailsForm.getAddressType())){
			if(bioDetailsForm.getAdvisorPostalCode() !=null && !bioDetailsForm.getAdvisorPostalCode().equalsIgnoreCase("")){
				file.add(" Postal Code: "+bioDetailsForm.getAdvisorPostalCode()+"\r\n\n");
			}
		}
		
		file.add("\r\n"+" Current Physical Address\r\n");
		for (int i = 0; i < bioDetailsForm.getPhysical().size(); i++) {
			file.add(" " +bioDetailsForm.getPhysical().get(i).toString()+"\r\n");
			log.debug("AddressDetailsAction - WriteWorkflow - Current Physical Address="+bioDetailsForm.getPhysical().get(i).toString());
		}
		log.debug("AddressDetailsAction - WriteWorkflow - Current Physical Postal Code="+bioDetailsForm.getPhysicalPostalCode());
		if (bioDetailsForm.getPhysicalPostalCode() != null && !bioDetailsForm.getPhysicalPostalCode().equalsIgnoreCase("")){
			file.add(" Postal Code: "+bioDetailsForm.getPhysicalPostalCode()+"\r\n\n");
		}
		file.add(" New Physical Address\r\n");
		if (bioDetailsForm.getAdvisorPhysicalLine1() != null && !bioDetailsForm.getAdvisorPhysicalLine1().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPhysicalLine1()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPhysicalLine2() != null && !bioDetailsForm.getAdvisorPhysicalLine2().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPhysicalLine2()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPhysicalLine3() != null && !bioDetailsForm.getAdvisorPhysicalLine3().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPhysicalLine3()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPhysicalLine4() != null && !bioDetailsForm.getAdvisorPhysicalLine4().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPhysicalLine4()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPhysicalLine5() != null && !bioDetailsForm.getAdvisorPhysicalLine5().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPhysicalLine5()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorPhysicalLine6() != null && !bioDetailsForm.getAdvisorPhysicalLine6().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorPhysicalLine6()+"\r\n");
		}
		if (!"iAddress".equals(bioDetailsForm.getAddressType())){
			if(bioDetailsForm.getAdvisorPhysicalPostalCode() !=null && !bioDetailsForm.getAdvisorPhysicalPostalCode().equalsIgnoreCase("")){
				file.add(" Postal Code: "+bioDetailsForm.getAdvisorPhysicalPostalCode()+"\r\n\n");
			}
		}
		
		file.add("\r\n"+" Current Courier Address\r\n");
		for (int i = 0; i < bioDetailsForm.getCourier().size(); i++) {
			file.add(" " +bioDetailsForm.getCourier().get(i).toString()+"\r\n");
			log.debug("AddressDetailsAction - WriteWorkflow - Current Courier Address="+bioDetailsForm.getCourier().get(i).toString());
		}
		log.debug("AddressDetailsAction - WriteWorkflow - Current Courier Postal Code="+bioDetailsForm.getPhysicalPostalCode());
		if (bioDetailsForm.getCourierPostalCode() != null && !bioDetailsForm.getCourierPostalCode().equalsIgnoreCase("")){
			file.add(" Postal Code: "+bioDetailsForm.getCourierPostalCode()+"\r\n\n");
		}
		file.add(" New Courier Address\r\n");
		if (bioDetailsForm.getAdvisorCourierLine1() != null && !bioDetailsForm.getAdvisorCourierLine1().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorCourierLine1()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorCourierLine2() != null && !bioDetailsForm.getAdvisorCourierLine2().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorCourierLine2()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorCourierLine3() != null && !bioDetailsForm.getAdvisorCourierLine3().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorCourierLine3()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorCourierLine4() != null && !bioDetailsForm.getAdvisorCourierLine4().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorCourierLine4()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorCourierLine5() != null && !bioDetailsForm.getAdvisorCourierLine5().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorCourierLine5()+"\r\n");
		}
		if (bioDetailsForm.getAdvisorCourierLine6() != null && !bioDetailsForm.getAdvisorCourierLine6().equalsIgnoreCase("")){
			file.add(" " +bioDetailsForm.getAdvisorCourierLine6()+"\r\n");
		}
		if (!"iAddress".equals(bioDetailsForm.getAddressType())){
			if(bioDetailsForm.getAdvisorCourierPostalCode() !=null && !bioDetailsForm.getAdvisorCourierPostalCode().equalsIgnoreCase("")){
				file.add(" Postal Code: "+bioDetailsForm.getAdvisorCourierPostalCode()+"\r\n\n");
			}
		}
		file.add(" Contact number : " +bioDetailsForm.getAdvisorContactNr()+"\r\n");
		file.close();
	}

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){

		log.info("AddressDetailsAction: unspecified method call -no value for parameter in request");
		log.debug("AddressDetailsAction - unspecified");
		return mapping.findForward("home");

	}

}
