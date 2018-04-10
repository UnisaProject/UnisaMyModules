package za.ac.unisa.lms.tools.smsbatch.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.text.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;

import Srsms02h.Abean.Srsms02sSendSmsToStudList;
import za.ac.unisa.lms.tools.smsbatch.dao.GeneralItem;
import za.ac.unisa.lms.tools.smsbatch.dao.SmsBatchDAO;
import za.ac.unisa.lms.tools.smsbatch.dao.SmsFileDAO;
import za.ac.unisa.lms.tools.smsbatch.forms.SmsBatchForm;
import za.ac.unisa.utils.CellPhoneVerification;

/**
 * MyEclipse Struts Creation date: 05-18-2006
 *
 * XDoclet definition:
 *
 * @struts:action parameter="action" validate="true"
 * @struts:action-forward name="inputforward" path="/WEB-INF/jsp/smsinput.jsp"
 *                        contextRelative="true"
 */
public class SmsBatchAction extends LookupDispatchAction {

	// --------------------------------------------------------- Instance
	// Variables
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
		Map map = new HashMap();
		map.put("button.back", "back");
		map.put("button.cancel", "cancel");		
		map.put("button.continue", "nextStep");
		//map.put("button.send", "send");
		map.put("send", "send");
		map.put("button.delete", "delete");
		map.put("nextStep", "nextStep");
		map.put("step1", "step1");
		map.put("menu", "menu");
		map.put("button.display", "display");
		map.put("button.search", "search");
		map.put("button.searchModule", "displaySearch");
		map.put("testMultiAdd", "testMultiAdd");

		return map;
	}
	
	public ActionForward menu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Check user
		String page="";
		page=checkUser(mapping, form, request, response);
		
		return mapping.findForward(page);
	}
	
	public String checkUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		SmsBatchForm smsForm = (SmsBatchForm) form;
		ActionMessages messages = new ActionMessages();
		Cookie[] mySiteCookies = request.getCookies();
		smsForm.setNovellUserCode("");
		if (mySiteCookies != null) {
			for (int i = 0; i < mySiteCookies.length; i++) {
				Cookie co = mySiteCookies[i];
				if ("novelluser".equalsIgnoreCase(co.getName().toString()
						.trim())) {
					smsForm.setNovellUserCode(co.getValue());
				}
			}
			if ("".equals(smsForm.getNovellUserCode())) {
				/*
				 * NB NB NB NB NB NB NB NB set user code for local dev only
				 */
				//smsForm.setNovellUserCode("DLAMIW");
				//smsForm.setNovellUserCode("PRETOJ");
				//smsForm.setNovellUserCode("PENZHE");
				//smsForm.setNovellUserCode("MAMETMD");
				return "userunknown";
			}
		} else {
			return "userunknown";
		}
		
		//determine if  user is a DCM user with special rights.
		//changes 20180301
		//DCM - department 804
		SmsFileDAO fileDao = new SmsFileDAO();
		smsForm.setDcmUser(false);
		smsForm.setDcmUser(fileDao.isDCMUser(smsForm.getNovellUserCode().toUpperCase()));

		Srsms02sSendSmsToStudList op = new Srsms02sSendSmsToStudList();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInNovellCodeWsStaffEmailAddress(smsForm.getNovellUserCode().trim().toUpperCase());
		op.setInCsfClientServerCommunicationsAction("SD");

		/* goto coolgen server */
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());

		List list = new ArrayList();
		int count = op.getOutRcGroupCount();
		smsForm.setRcCount(op.getOutRcGroupCount());
		smsForm.setUserName(op.getOutSecurityWsUserName().trim());
		smsForm.setDepartment(op.getOutSmsRequestSmsMsg().trim());
		smsForm.setRcCode("");

		if (count > 1) {
			String fmt = "0.00#";
			DecimalFormat df = new DecimalFormat(fmt);
			String strRcCode;
			String strRcCodeDesc;
			String strTotalBudget;
			String strAvailBudget;
			String strLabel = "";
			strRcCode = rightPad("RC code", "&nbsp;", 8);
			strRcCodeDesc = rightPad("RC description", "&nbsp;", 42);
			strTotalBudget = leftPad("Total budget", "&nbsp;", 13);
			strAvailBudget = leftPad("Avail budget", "&nbsp;", 13);
			strLabel = strLabel.concat(strRcCode).concat(strRcCodeDesc).concat(
					strTotalBudget).concat(strAvailBudget);
			list.add(0, new LabelValueBean(strLabel, "-1"));

			for (int i = 0; i < count; i++) {
				strLabel = "";
				strRcCode = rightPad((String) op
						.getOutGRcWsStaffRcCodeMkRcCode(i).trim(), "&nbsp;", 8);
				if(op.getOutGRcDescriptionCsfStringsString60(i).length()>40){
					strRcCodeDesc = rightPad((String)op.getOutGRcDescriptionCsfStringsString60(i).substring(0,40).trim(), "&nbsp;",
							42);
				}else{
					strRcCodeDesc = rightPad((String)op.getOutGRcDescriptionCsfStringsString60(i).trim(), "&nbsp;",
						42);
				}
				strTotalBudget = leftPad(df.format(op
						.getOutGRcTotalBudgetIefSuppliedTotalCurrency(i)),
						"&nbsp;", 13);
				strAvailBudget = leftPad(df.format(op
						.getOutGRcAvailableBudgetIefSuppliedTotalCurrency(i)),
						"&nbsp;", 13);
				strLabel = strLabel.concat(strRcCode).concat(strRcCodeDesc)
						.concat(strTotalBudget).concat(strAvailBudget);
				list.add(i + 1, new LabelValueBean(strLabel, (String) op
						.getOutGRcWsStaffRcCodeMkRcCode(i).trim()));
			}

			smsForm.setRcList(list);
		} else {
			smsForm.setAvailableBudgetAmount(op
					.getOutGRcAvailableBudgetIefSuppliedTotalCurrency(0));
			smsForm.setBudgetAmount(op
					.getOutGRcTotalBudgetIefSuppliedTotalCurrency(0));
			smsForm.setRcCode(op.getOutGRcWsStaffRcCodeMkRcCode(0).trim());
			smsForm.setRcDescription(op
					.getOutGRcDescriptionCsfStringsString60(0).trim());			
		}

		String serverpath = ServerConfigurationService.getServerUrl();
		if (serverpath.contains("myqa")){
			smsForm.setSmsEnvironment("qa");
		} else if (serverpath.contains("my.unisa")){
			smsForm.setSmsEnvironment("prod");
		} else {
			smsForm.setSmsEnvironment("dev");
		}
		
		if (smsForm.getRcCode().trim().equalsIgnoreCase("") && 
				smsForm.getRcList() == null &&
				op.getOutErrorFlagCsfStringsString1().trim().equalsIgnoreCase("Y")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"You are either linked to an invalid Responsibility centre code or the Responsiblity centre has no budget available."));
			addErrors(request, messages);
		}
		if (smsForm.getRcCode().trim().equalsIgnoreCase("") && 
				smsForm.getRcList() == null &&
				op.getOutErrorFlagCsfStringsString1().trim().equalsIgnoreCase("")){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"You are not linked to an Responsibility centre code."));
			addErrors(request, messages);
		}
		
		return "firstpage";

	}

	public ActionForward step1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Check user
		SmsBatchForm smsForm = (SmsBatchForm) form;
		Cookie[] mySiteCookies = request.getCookies();
		smsForm.setNovellUserCode("");
		if (mySiteCookies != null) {
			for (int i = 0; i < mySiteCookies.length; i++) {
				Cookie co = mySiteCookies[i];
				if ("novelluser".equalsIgnoreCase(co.getName().toString()
						.trim())) {
					smsForm.setNovellUserCode(co.getValue());
				}
			}
			if ("".equals(smsForm.getNovellUserCode())) {
				/*
				 * NB NB NB NB NB NB NB NB set user code for local dev only
				 */
				//smsForm.setNovellUserCode("DLAMIW");
				//smsForm.setNovellUserCode("PRETOJ");
				//smsForm.setNovellUserCode("PENZHE");
				return mapping.findForward("userunknown");
			}
		} else {
			return mapping.findForward("userunknown");
		}

		ActionMessages messages = new ActionMessages();

		/** Do validation */
		if ("-1".equals(smsForm.getRcCode()) || "".equals(smsForm.getRcCode().trim())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Select a Responsibility code from the list."));
			addErrors(request, messages);
			return mapping.findForward("firstpage");
		}

		// Set default values
		smsForm.setReasonGc27("2 : Learner Support");
		List controlNumberList = new ArrayList<String>();
		String controlNumber="";
		for (int i=0; i <=2; i++){
			controlNumberList.add(controlNumber);
		}
		smsForm.setControlCellNumberList(controlNumberList);

		return mapping.findForward("step1forward");
	}

	public String step2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsForm = (SmsBatchForm) form;

		if ("".equals(smsForm.getNovellUserCode())
				|| smsForm.getNovellUserCode() == null) {
			return "userunknown";
		}
		ActionMessages messages = new ActionMessages();

		/** Do validation */
		if (smsForm.getRegCriteriaType() == null
				|| "".equals(smsForm.getRegCriteriaType())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Choose the registration criteria type."));
			addErrors(request, messages);
			return "step1forward";
		} else if (smsForm.getGeoCriteriaType() == null
				|| "".equals(smsForm.getGeoCriteriaType())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"Choose the geographical criteria type."));
			addErrors(request, messages);
			return "step1forward";
		} else if ("A".equalsIgnoreCase(smsForm.getRegCriteriaType())
				&& "A".equalsIgnoreCase(smsForm.getGeoCriteriaType())) {
			messages
					.add(
							ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage(
									"message.generalmessage",
									"The all registered criteria and all geographical criteria may not be selected together."));
			addErrors(request, messages);
			return "step1forward";
		} else if ("A".equalsIgnoreCase(smsForm.getRegCriteriaType())
				&& "S".equalsIgnoreCase(smsForm.getGeoCriteriaType())) {
			messages
					.add(
							ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage(
									"message.generalmessage",
									"The all registered criteria and all RSA students criteria may not be selected together."));
			addErrors(request, messages);
			return "step1forward";
		}
		/* Check criteria, if changed clear selection lists */
		if (!smsForm.getRegCriteriaType().equalsIgnoreCase(
				smsForm.getPrevRegCriteriaType())) {
			smsForm.setRegList(new ArrayList());
			smsForm.setSelectedItems("");
		}
		if (!"M".equalsIgnoreCase(smsForm.getRegCriteriaType())) {
			smsForm.setRegistrationPeriod("");
		}
		/* set previous criteria */
		smsForm.setPrevRegCriteriaType(smsForm.getRegCriteriaType());
		/* set pick lists */
		setLists(smsForm, request);
		/* set academic year */
		smsForm.setAcademicYear((short) (getCurrentYear()));

		return "step2forward";
	}

	public String step3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;

		if ("".equals(smsBatchForm.getNovellUserCode())
				|| smsBatchForm.getNovellUserCode() == null) {
			return "userunknown";
		}
		ActionMessages messages = new ActionMessages();
		boolean err = false;

		/** Do validation */

//		if (!"".equals(smsBatchForm.getControllCellNr().trim())) {
//			/* check cell nr */
//			messages = (ActionMessages) smsBatchForm.validate(mapping, request);
//			if (!messages.isEmpty()) {
//				addErrors(request, messages);
//				return "step2forward";
//			}
//		} else {
//			smsBatchForm.setControllCellNr(smsBatchForm.getControllCellNr()
//					.trim());
//		}
		for (int i=0; i < smsBatchForm.getControlCellNumberList().size(); i++){
			String ControllCellNumber = smsBatchForm.getControlCellNumberList().get(i).toString().trim();
			if (ControllCellNumber==null || ControllCellNumber.trim().equalsIgnoreCase("")){			
			}else{
				CellPhoneVerification verifyCell = new CellPhoneVerification();
				
				if (!verifyCell.isCellNumber(ControllCellNumber)){
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage", "Invalid control cell number."));
					i = smsBatchForm.getControlCellNumberList().size();
				}else{
					if (verifyCell.isSaCellNumber(ControllCellNumber)){
						if (!verifyCell.validSaCellNumber(ControllCellNumber)){
							messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
									"message.generalmessage", "Invalid control cell number."));
							i = smsBatchForm.getControlCellNumberList().size();
						}						
					}
				}			
			}
		}
		if (!messages.isEmpty()) {
			addErrors(request, messages);
			return "step2forward";
		}
		/* check message */
		if (smsBatchForm.getMessage() == null
				|| "".equals(smsBatchForm.getMessage().trim())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "SMS message can not be empty"));
			addErrors(request, messages);
			return "step2forward";
		} else {
			smsBatchForm.setMessage(smsBatchForm.getMessage().trim());
		}
		if (smsBatchForm.getMessage().length() > 160){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage", "SMS message may not exceed 160 characters"));
			addErrors(request, messages);
			return "step2forward";
		}
		/* Check geographical criteria */
		if (!"A".equalsIgnoreCase(smsBatchForm.getGeoCriteriaType())) {
			if ("C".equalsIgnoreCase(smsBatchForm.getGeoCriteriaType())
					&& isSelectEmpty(smsBatchForm.getGeoSelection())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"Select at least one country as criteria."));
				addErrors(request, messages);
				err = true;
			} else if ("C".equalsIgnoreCase(smsBatchForm.getGeoCriteriaType())
					&& (((String[])smsBatchForm.getGeoSelection()).length > 20)) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage",
							"Select at most 20 countries with one request."));
					addErrors(request, messages);
					err = true;
			} else if ("R".equalsIgnoreCase(smsBatchForm.getGeoCriteriaType())
					&& isSelectEmpty(smsBatchForm.getGeoSelection())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"Select at least one region as criteria."));
				addErrors(request, messages);
				err = true;
			} else if ("R".equalsIgnoreCase(smsBatchForm.getGeoCriteriaType())
					&& (((String[])smsBatchForm.getGeoSelection()).length > 20)) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage",
							"Select at most 20 regions with one request."));
					addErrors(request, messages);
					err = true;
			} else if ("M".equalsIgnoreCase(smsBatchForm.getGeoCriteriaType())
					&& isSelectEmpty(smsBatchForm.getGeoSelection())) {
				messages
						.add(
								ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("message.generalmessage",
										"Select at least one magisterial district as criteria."));
				addErrors(request, messages);
				err = true;
			} else if ("M".equalsIgnoreCase(smsBatchForm.getGeoCriteriaType())
					&& (((String[])smsBatchForm.getGeoSelection()).length > 20)) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage",
							"Select at most 20 magisterial districts with one request."));
					addErrors(request, messages);
					err = true;
			}
		}
		if (err)
			return "step2forward";

		/** Check registration criteria */
		if (!"A".equalsIgnoreCase(smsBatchForm.getRegCriteriaType())) {
			if ("M".equalsIgnoreCase(smsBatchForm.getRegCriteriaType())) {

				//remove all spaces from study unit list
				if (smsBatchForm.getSelectedItems().length() > 0) {
					smsBatchForm.setSelectedItems(smsBatchForm.getSelectedItems().replaceAll(" ",""));
				}

				if ("Select a registration period".equals(smsBatchForm
						.getRegistrationPeriod())
						|| "".equals(smsBatchForm.getRegistrationPeriod())) {
					messages
							.add(
									ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Select a registration period from the list."));
					addErrors(request, messages);
					err = true;
				} else if (smsBatchForm.getSelectedItems().length() == 0) {
					messages
							.add(
									ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Select at least one module as registration criteria."));
					addErrors(request, messages);
					err = true;
				//} else if (smsBatchForm.getSelectedItems().length() > 20) {
					} else if (smsBatchForm.getSelectedItems().length() > 159) {
					messages
							.add(
									ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Select at most 20 modules as registration criteria."));
					addErrors(request, messages);
					err = true;
				} else {
					/* test length of string */
					String testString = smsBatchForm.getSelectedItems()
							.replaceAll(",", "");
					if ((testString.length() % 7) > 0) {
						messages
								.add(
										ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage(
												"message.generalmessage",
												"Invalid module list. Module codes must have a length of 7 characters, seperated by a comma."));
						addErrors(request, messages);
						return "step2forward";
					}
					/* remove duplicates */
					smsBatchForm.setSelectedItems(removeDuplicates(smsBatchForm
							.getSelectedItems(), smsBatchForm
							.getRegCriteriaType()));
					try {
						// Do study unit validation
						List itemList = new ArrayList();
						List regList = new ArrayList();
						itemList = setupItemList(smsBatchForm
								.getSelectedItems(), smsBatchForm
								.getRegCriteriaType());
						for (int i = 0; i < itemList.size(); i++) {
							SmsBatchDAO dao = new SmsBatchDAO();
							GeneralItem item = new GeneralItem();
							item = dao.getModuleDetail(request, itemList.get(i)
									.toString(),
									smsBatchForm.getAcademicYear(),
									smsBatchForm.getRegistrationPeriod()
											.substring(0, 1));
							if (item.getCode() == null) {
								messages
										.add(
												ActionMessages.GLOBAL_MESSAGE,
												new ActionMessage(
														"message.generalmessage",
														itemList.get(i)
																.toString()
																.toUpperCase()
																+ " : Invalid module code or invalid module code for registration period."));
								addErrors(request, messages);
								err = true;
								break;
								// regList.add(new
								// LabelValueBean(itemList.get(i).toString().toUpperCase()+"
								// : Invalid
								// Module",itemList.get(i).toString()));
							} else {
								regList.add(new LabelValueBean(item.getCode()
										.toString()
										+ " : " + item.getDesc(), item
										.getCode().toString()
										+ " : " + item.getDesc()));
							}
						}
						smsBatchForm.setRegList(regList);
					} catch (StringIndexOutOfBoundsException e) {
						messages
								.add(
										ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage(
												"message.generalmessage",
												"Invalid module list. Module codes must have a length of 7 characters, seperated by a comma."));
						addErrors(request, messages);
						err = true;
					}
				}
			} else if ("Q".equalsIgnoreCase(smsBatchForm.getRegCriteriaType())) {				
				if (smsBatchForm.getSelectedItems().length() == 0) {
					messages
							.add(
									ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"Select at least one qualification as registration criteria."));
					addErrors(request, messages);
					err = true;
				}
				//else if (smsBatchForm.getSelectedItems().length() > 20) {
				else if (smsBatchForm.getSelectedItems().length() > 95) {
					messages
							.add(
									ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("message.generalmessage",
											"A maximum of 16 qualifications as registration criteria is allowed per sms."
											+ "  If you require the sms to be sent to more than 16 qualification codes you will "
											+ "need to send another sms to the remaining qualification codes."));
					addErrors(request, messages);
					err = true;
				} else {
					/* test length of string */
					String testString = smsBatchForm.getSelectedItems()
							.replaceAll(",", "");
					if ((testString.length() % 5) > 0) {
						messages
								.add(
										ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage(
												"message.generalmessage",
												"Invalid qualification list. Qualification codes must have a length of 5 numbers, seperated by a comma."));
						addErrors(request, messages);
						return "step2forward";
					}
					/* remove duplicates */
					smsBatchForm.setSelectedItems(removeDuplicates(smsBatchForm
							.getSelectedItems(), smsBatchForm
							.getRegCriteriaType()));
					try {
						// Do qualification validation
						List itemList = new ArrayList();
						List regList = new ArrayList();
						itemList = setupItemList(smsBatchForm
								.getSelectedItems(), smsBatchForm
								.getRegCriteriaType());
						for (int i = 0; i < itemList.size(); i++) {
							SmsBatchDAO dao = new SmsBatchDAO();
							GeneralItem item = new GeneralItem();
							item = dao.getQualificationDetail(request, itemList
									.get(i).toString());
							if (item.getCode() == null) {
								messages
										.add(
												ActionMessages.GLOBAL_MESSAGE,
												new ActionMessage(
														"message.generalmessage",
														itemList.get(i)
																.toString()
																.toUpperCase()
																+ " : Invalid qualification list. Qualification codes must have a length of 5 numbers, seperated by a comma."));
								addErrors(request, messages);
								err = true;
								break;
								// regList.add(new
								// LabelValueBean(itemList.get(i).toString().toUpperCase()+"
								// : Invalid
								// Qualification",itemList.get(i).toString()));
							} else {
								regList.add(new LabelValueBean(item.getCode()
										.toString()
										+ " : " + item.getDesc(), item
										.getCode().toString()
										+ " : " + item.getDesc()));
							}
						}
						smsBatchForm.setRegList(regList);
					} catch (StringIndexOutOfBoundsException e) {
						messages
								.add(
										ActionMessages.GLOBAL_MESSAGE,
										new ActionMessage(
												"message.generalmessage",
												"Invalid qualification list. Qualification codes must have a length of 5 numbers, seperated by a comma."));
						addErrors(request, messages);
						err = true;
					}
				}
			}
		}

		if (err)
			return "step2forward";

		return "step3forward";
	}

	public String display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;

		if ("".equals(smsBatchForm.getNovellUserCode())
				|| smsBatchForm.getNovellUserCode() == null) {
			return "userunknown";
		}

		ActionMessages messages = new ActionMessages();
		smsBatchForm.setSmsWasSend(false);

		Srsms02sSendSmsToStudList op = new Srsms02sSendSmsToStudList();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInNovellCodeWsStaffEmailAddress(smsBatchForm.getNovellUserCode()
				.toUpperCase());
		/*
		 * Very important This variable determines whether the sms's will be
		 * send N = do NOT send Y = send sms's
		 */
		op.setInSendSmsCsfStringsString1("N");
		op.setInCsfClientServerCommunicationsAction("S");
		op.setInSmsRequestMkRcCode(smsBatchForm.getRcCode());
		op.setInMagisterialCriteriaTypeCsfStringsString1(smsBatchForm
				.getGeoCriteriaType());
		op.setInRegistrationCriteriaTypeCsfStringsString1(smsBatchForm
				.getRegCriteriaType());
		op.setInWsStudyUnitPeriodDetailMkAcademicYear(smsBatchForm
				.getAcademicYear());
		/*
		 * Seperate code and description Currently concatenated as one from the
		 * input
		 */
		GeneralItem genItem = new GeneralItem();
		/* reg period */
		if (smsBatchForm.getRegistrationPeriod() != null
				&& !"".equals(smsBatchForm.getRegistrationPeriod())) {
			genItem = getItem(smsBatchForm.getRegistrationPeriod());
			op.setInWsStudyUnitPeriodDetailMkAcademicPeriod(Short
					.parseShort(genItem.getCode()));
		}
		/* reason code */
		if (smsBatchForm.getDepartment().equalsIgnoreCase("ICT")){
			genItem = getItem(smsBatchForm.getReasonGc27());
			op.setInSmsRequestReasonGc27(Integer.parseInt(genItem.getCode()));
		}else{
			/*default reason to 2 - Learner Support if not ICT department*/
			op.setInSmsRequestReasonGc27(2);
		}

		op.setInSmsRequestSmsMsg(smsBatchForm.getMessage().trim());
		List controlCellPhoneList = new ArrayList<String>();	//Change additional control cell numbers
		
		for (int i=0; i < smsBatchForm.getControlCellNumberList().size(); i++){
			if (smsBatchForm.getControlCellNumberList().get(i)!=null){
				if (!smsBatchForm.getControlCellNumberList().get(i).toString().trim().equalsIgnoreCase("")){
					controlCellPhoneList.add(smsBatchForm.getControlCellNumberList().get(i).toString().trim());
				}
			}
		}
		for (int i=controlCellPhoneList.size(); i<3; i++){
			controlCellPhoneList.add("");
		}
		op
		.setInSmsRequestControlCellNr(controlCellPhoneList.get(0).toString()
				.trim());
		op.setInSmsRequestControlCellNr2(controlCellPhoneList.get(1).toString()
		.trim());
		op.setInSmsRequestControlCellNr3(controlCellPhoneList.get(2).toString()
		.trim());
		op.setInSmsRequestProgramName("STD_BATCH");

		/** setup selection criteria */
		int count = 0;
		for (int i = 0; i < smsBatchForm.getGeoSelection().length; i++) {
			if (smsBatchForm.getGeoSelection()[i] != null
					&& !"".equals(smsBatchForm.getGeoSelection()[i])) {
				/* Seperate code and description */
				genItem = getItem(smsBatchForm.getGeoSelection()[i].trim());
				op.setInMagisterialGpCsfStringsString15(count, genItem
						.getCode());
				count = count + 1;
			}
		}
		count = 0;
		List itemList = new ArrayList();
		itemList = setupItemList(smsBatchForm.getSelectedItems(), smsBatchForm
				.getRegCriteriaType());
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i) != null && !"".equals(itemList.get(i))) {
				op.setInRegistrationGpCsfStringsString15(count, itemList.get(i)
						.toString().toUpperCase());
				count = count + 1;
			}
		}

		/* goto coolgen server */
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());

		if ("Y".equalsIgnoreCase(op.getOutErrorFlagCsfStringsString1().trim())) {
			String Errormsg = op.getOutCsfStringsString500().trim();
			if ((Errormsg != null) && (!Errormsg.equals(""))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.coolgenerror", Errormsg));
				addErrors(request, messages);
				smsBatchForm.setCanSendSms(false);
				return "displayforward";
			}
		}
		/** Check sms count */
		if (op.getOutSmsRequestMessageCnt() <= 0) {
			messages
					.add(
							ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"SMS message can not be send. No students found for the selected criteria."));
			addErrors(request, messages);
			smsBatchForm.setCanSendSms(false);
			return "displayforward";
		}

		smsBatchForm.setCanSendSms(true);
		smsBatchForm.setSmsWasSend(false);
		smsBatchForm.setCostPerSms(op.getOutSmsCostCostPerSms());
		smsBatchForm.setAvailableBudgetAmount(op
				.getOutAvailableBudgetAmountIefSuppliedTotalCurrency());
		smsBatchForm.setBudgetAmount(op
				.getOutBudgetAmountIefSuppliedTotalCurrency());
		smsBatchForm.setTotalCost(op.getOutTotalCostIefSuppliedTotalCurrency());
		smsBatchForm.setRcCode(op.getOutSmsRequestMkRcCode());
		smsBatchForm
				.setRcDescription(op.getOutRcDesriptionCsfStringsString60());
		smsBatchForm.setDataFileName(op
				.getOutFileNameWizfuncReportingControlPathAndFilename());
		smsBatchForm.setMessageCount(op.getOutSmsRequestMessageCnt());
		
		//Johanet change 20151102
		//edit gen proxy to bring back selection criteria
		//sql query on selection criteria and message
		//warning if already exists - possible duplicate sms
		smsBatchForm.setSelectionCriteria(op.getOutSmsRequestSelectionCriteria());
		if (smsBatchForm.getSelectionCriteria()!=null && !smsBatchForm.getSelectionCriteria().equalsIgnoreCase("")) {
			SmsBatchDAO dao = new SmsBatchDAO();
			String possibleDuplicates = dao.getPossibleDuplicateSMS(smsBatchForm.getSelectionCriteria(), smsBatchForm.getMessage());
			if (!possibleDuplicates.equalsIgnoreCase("")){
				messages
				.add(
						ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage",
								"Warning: A similar SMS has already been requested today, batch number/(s): " + possibleDuplicates + ". Please use the Query SMS System option to verify the status of the SMS already requested. Do not Send SMS again if it has already been requested, SMS's can take a while to be processed by the Service Provider."));
				addMessages(request, messages);
			}
		}	
		

		return "displayforward";
	}

	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;

		if ("".equals(smsBatchForm.getNovellUserCode())
				|| smsBatchForm.getNovellUserCode() == null) {
			return mapping.findForward("userunknown");
		}

		ActionMessages messages = new ActionMessages();

		/** Check sms count */
		if (smsBatchForm.getMessageCount() <= 0) {
			messages
					.add(
							ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage",
									"SMS message can not be send. No students found for the selected criteria."));
			addErrors(request, messages);
			smsBatchForm.setCanSendSms(false);
			return mapping.findForward("displayforward");
		}
		/** Check total cost*/
        if(smsBatchForm.getTotalCost()==0){
                        messages.add(ActionMessages.GLOBAL_MESSAGE,
                                                        new ActionMessage("message.generalmessage",
                                                        "SMS message can not be send. Total cost can not be zero."));
                        addErrors(request, messages);
                        smsBatchForm.setCanSendSms(false);
                        return mapping.findForward("displayforward");
        }
		/** Check data file name */
		if ("".equals(smsBatchForm.getDataFileName().trim())) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.generalmessage",
					"SMS message can not be send. Data file name missing."));
			addErrors(request, messages);
			smsBatchForm.setCanSendSms(false);
			return mapping.findForward("displayforward");
		}

		Srsms02sSendSmsToStudList op = new Srsms02sSendSmsToStudList();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInNovellCodeWsStaffEmailAddress(smsBatchForm.getNovellUserCode()
				.toUpperCase());
		/*
		 * Very important This variable determines whether the sms's will be
		 * send N = do NOT send Y = send sms's
		 */
		op.setInSendSmsCsfStringsString1("Y");
		op.setInCsfClientServerCommunicationsAction("S");
		op.setInSmsRequestMkRcCode(smsBatchForm.getRcCode());
		op.setInMagisterialCriteriaTypeCsfStringsString1(smsBatchForm
				.getGeoCriteriaType());
		op.setInRegistrationCriteriaTypeCsfStringsString1(smsBatchForm
				.getRegCriteriaType());
		op.setInWsStudyUnitPeriodDetailMkAcademicYear(smsBatchForm
				.getAcademicYear());
		op.setInFileNameWizfuncReportingControlPathAndFilename(smsBatchForm
				.getDataFileName());
		op.setInSmsRequestMkDepartmentCode(smsBatchForm.getDeptCode());
		op.setInSmsRequestMkPersNr(smsBatchForm.getPersonnelNr());
		/*
		 * Seperate code and description Currently concatenated as one from the
		 * input
		 */
		GeneralItem genItem = new GeneralItem();
		/* reg period */
		if (smsBatchForm.getRegistrationPeriod() != null
				&& !"".equals(smsBatchForm.getRegistrationPeriod())) {
			genItem = getItem(smsBatchForm.getRegistrationPeriod());
			op.setInWsStudyUnitPeriodDetailMkAcademicPeriod(Short
					.parseShort(genItem.getCode()));
		}
		/* reason code */
		if (smsBatchForm.getDepartment().equalsIgnoreCase("ICT")){
			genItem = getItem(smsBatchForm.getReasonGc27());
			op.setInSmsRequestReasonGc27(Integer.parseInt(genItem.getCode()));
		}else{
			/*default reason to 2 - Learner Support if not ICT department*/
			op.setInSmsRequestReasonGc27(2);
		}


		op.setInSmsRequestSmsMsg(smsBatchForm.getMessage().trim());
		List controlCellPhoneList = new ArrayList<String>();  //Change additional control cell numbers
		
		for (int i=0; i < smsBatchForm.getControlCellNumberList().size(); i++){
			if (smsBatchForm.getControlCellNumberList().get(i)!=null){
				if (!smsBatchForm.getControlCellNumberList().get(i).toString().trim().equalsIgnoreCase("")){
					controlCellPhoneList.add(smsBatchForm.getControlCellNumberList().get(i).toString().trim());
				}
			}
		}
		for (int i=controlCellPhoneList.size(); i<3; i++){
			controlCellPhoneList.add("");
		}
		op
				.setInSmsRequestControlCellNr(controlCellPhoneList.get(0).toString()
						.trim());
		op.setInSmsRequestControlCellNr2(controlCellPhoneList.get(1).toString()
				.trim());
		op.setInSmsRequestControlCellNr3(controlCellPhoneList.get(2).toString()
				.trim());
		op.setInSmsRequestProgramName("STD_BATCH");
		op.setInSmsRequestMessageCnt(smsBatchForm.getMessageCount());

		/** setup selection criteria */
		int count = 0;
		for (int i = 0; i < smsBatchForm.getGeoSelection().length; i++) {
			if (smsBatchForm.getGeoSelection()[i] != null
					&& !"".equals(smsBatchForm.getGeoSelection()[i])) {
				/* Seperate code and description */
				genItem = getItem(smsBatchForm.getGeoSelection()[i].trim());
				op.setInMagisterialGpCsfStringsString15(count, genItem
						.getCode());
				count = count + 1;
			}
		}
		count = 0;
		for (int i = 0; i < smsBatchForm.getRegList().size(); i++) {
			if (smsBatchForm.getRegList().get(i) != null
					&& !"".equals(smsBatchForm.getRegList().get(i))) {
				LabelValueBean item = new LabelValueBean();
				item = (LabelValueBean) smsBatchForm.getRegList().get(i);
				/* Seperate code and description */
				genItem = getItem(item.getValue().trim());
				op.setInRegistrationGpCsfStringsString15(count, genItem
						.getCode().toUpperCase());
				count = count + 1;
			}
		}

		/* goto coolgen server */
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());

		if ("Y".equalsIgnoreCase(op.getOutErrorFlagCsfStringsString1().trim())) {
			String Errormsg = op.getOutCsfStringsString500().trim();
			if ((Errormsg != null) && (!Errormsg.equals(""))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.coolgenerror", Errormsg));
				addErrors(request, messages);
				return mapping.findForward("displayforward");
			}
		}

		smsBatchForm.setCanSendSms(true);
		smsBatchForm.setCostPerSms(op.getOutSmsCostCostPerSms());
		smsBatchForm.setAvailableBudgetAmount(op
				.getOutAvailableBudgetAmountIefSuppliedTotalCurrency());
		smsBatchForm.setBudgetAmount(op
				.getOutBudgetAmountIefSuppliedTotalCurrency());
		smsBatchForm.setTotalCost(op.getOutTotalCostIefSuppliedTotalCurrency());
		smsBatchForm.setRcCode(op.getOutSmsRequestMkRcCode());
		// smsBatchForm.setRcDescription(op.getOutRcDesriptionCsfStringsString60());
		smsBatchForm.setMessageCount(op.getOutSmsRequestMessageCnt());
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"message.generalmessage", op.getOutCsfStringsString500()));
		addMessages(request, messages);
		smsBatchForm.setSmsWasSend(true);
		smsBatchForm.setCanSendSms(false);
		smsBatchForm.setDataFileName("");
		smsBatchForm.setBatchNr(0);
		// smsBatchForm.setMessageCount(0);

		return mapping.findForward("displayforward");
	}

	public ActionForward displaySearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;
		/*
		 * if (!"".equalsIgnoreCase(smsBatchForm.getSearchType()) &&
		 * !"".equalsIgnoreCase(smsBatchForm.getSearchCode())) { return
		 * mapping.findForward(search(mapping, form, request, response)); } if
		 * (!"".equalsIgnoreCase(smsBatchForm.getSearchType()) &&
		 * !"".equalsIgnoreCase(smsBatchForm.getSearchDescription())) { return
		 * mapping.findForward(search(mapping, form, request, response)); }
		 */
		ActionMessages messages = new ActionMessages();
		if ("M".equalsIgnoreCase(smsBatchForm.getRegCriteriaType())) {
			if ("Select a registration period".equals(smsBatchForm
					.getRegistrationPeriod())
					|| "".equals(smsBatchForm.getRegistrationPeriod())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"Select a registration period from the list."));
				addErrors(request, messages);
				return mapping.findForward(step2(mapping, form, request,
						response));
			}
		}
		smsBatchForm.setSearchType("code");
		return mapping.findForward("searchforward");
	}

	// Search modules on code or description
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;
		ActionMessages messages = new ActionMessages();
		ArrayList searchList = new ArrayList();

		try {
			// Do checks on required input
			if (smsBatchForm.getSearchType() == null
					|| "".equalsIgnoreCase(smsBatchForm.getSearchType())) {
				String err = "";
				if ("M".equalsIgnoreCase(smsBatchForm.getRegCriteriaType())) {
					err = "Select to search by module code or description.";
				} else {
					err = "Select to search by qualification code or description.";
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", err));
				addErrors(request, messages);
				return mapping.findForward("searchforward");
			}
			if ("code".equalsIgnoreCase(smsBatchForm.getSearchType())
					&& "".equalsIgnoreCase(smsBatchForm.getSearchCode())) {
				String err = "";
				if ("M".equalsIgnoreCase(smsBatchForm.getRegCriteriaType())) {
					err = "You have selected to search by module code. Please enter the module code search criteria.";
				} else {
					err = "You have selected to search by qualification code. Please enter the qualification code search criteria.";
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", err));
				addErrors(request, messages);
				return mapping.findForward("searchforward");
			}
			if ("description".equalsIgnoreCase(smsBatchForm.getSearchType())
					&& "".equalsIgnoreCase(smsBatchForm.getSearchDescription())) {
				String err = "";
				if ("M".equalsIgnoreCase(smsBatchForm.getRegCriteriaType())) {
					err = "You have selected to search by module description. Please enter the description search criteria.";
				} else {
					err = "You have selected to search by qualification description. Please enter the description search criteria.";
				}
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage", err));
				addErrors(request, messages);
				return mapping.findForward("searchforward");
			}

			SmsBatchDAO dao = new SmsBatchDAO();

			// Do the search on the database
			if ("code".equalsIgnoreCase(smsBatchForm.getSearchType())) {
				// smsBatchForm.setSearchDescription("");
				searchList = dao.getSearchCodeList(request, smsBatchForm
						.getSearchType(), smsBatchForm.getSearchCode(),
						smsBatchForm.getRegCriteriaType(), smsBatchForm
								.getAcademicYear(), smsBatchForm
								.getRegistrationPeriod());
			} else {
				// smsBatchForm.setSearchCode("");
				searchList = dao.getSearchCodeList(request, smsBatchForm
						.getSearchType(), smsBatchForm.getSearchDescription(),
						smsBatchForm.getRegCriteriaType(), smsBatchForm
								.getAcademicYear(), smsBatchForm
								.getRegistrationPeriod());
			}
			if (searchList.isEmpty()) {
				// no results returned give message
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"The search returned no results. Please try again."));
				addErrors(request, messages);
			} else {
				// Add list to request
				request.setAttribute("searchList", searchList);
			}
		} catch (Exception e) {
			throw e;
		}
		/* Clear previous selection */
		smsBatchForm.setSelectedSearchList(new String[15]);
		smsBatchForm.setRegSelection(new String[15]);
		// smsBatchForm.setSearchCode("");
		// smsBatchForm.setSearchDescription("");

		return mapping.findForward("searchforward");
	}

	public String selectedCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;
		List list = new ArrayList();
		list = smsBatchForm.getRegList();
		for (int i = 0; i < smsBatchForm.getSelectedSearchList().length; i++) {
			boolean add = true;
			if ((smsBatchForm.getSelectedSearchList())[i] != null
					&& !"".equals((smsBatchForm.getSelectedSearchList())[i])) {
				// check for duplicates
				GeneralItem item = new GeneralItem();
				item = getItem(smsBatchForm.getSelectedSearchList()[i]);
				if (smsBatchForm.getSelectedItems().toUpperCase().indexOf(
						item.getCode()) != -1) {
					add = false;
				}
				if (add) {
					if (smsBatchForm.getSelectedItems().length() > 0) {
						smsBatchForm.setSelectedItems(smsBatchForm
								.getSelectedItems()
								+ "," + item.getCode());
					} else {
						smsBatchForm.setSelectedItems(item.getCode());
					}
				}
			}
		}
		smsBatchForm.setRegList(list);
		/* clear selection criteria */
		smsBatchForm.setSearchDescription("");
		smsBatchForm.setSearchCode("");
		return "step2forward";
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;
		List list = new ArrayList();
		list = smsBatchForm.getRegList();
		for (int i = 0; i < smsBatchForm.getRegSelection().length; i++) {
			if ((smsBatchForm.getRegSelection())[i] != null
					&& !"".equals((smsBatchForm.getRegSelection())[i])) {
				listloop: for (int j = 0; j < list.size(); j++) {
					LabelValueBean item = new LabelValueBean();
					item = (LabelValueBean) list.get(j);
					if (item.getValue().equals(
							(smsBatchForm.getRegSelection())[i])) {
						list.remove(j);
						break listloop;
					}
				}
			}
		}
		smsBatchForm.setRegList(list);
		/* set all lists */
		setLists(smsBatchForm, request);

		return mapping.findForward("step2forward");
	}

	public ActionForward nextStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String nextPage = "";
		if ("1".equalsIgnoreCase(request.getParameter("page"))) {
			nextPage = step2(mapping, form, request, response);
		} else if ("2".equalsIgnoreCase(request.getParameter("page"))) {
			nextPage = step3(mapping, form, request, response);
			if ("step2forward".equalsIgnoreCase(nextPage)) {
				/** setup all lists before returning */
				setLists((SmsBatchForm) form, request);
			}
		} else if ("search".equalsIgnoreCase(request.getParameter("page"))) {
			// nextPage = search(mapping, form, request, response);
			if ("step2forward".equalsIgnoreCase(nextPage)) {
				/** setup all lists before returning */
				setLists((SmsBatchForm) form, request);
			}
		} else if ("select".equalsIgnoreCase(request.getParameter("page"))) {
			nextPage = selectedCode(mapping, form, request, response);
			if ("step2forward".equalsIgnoreCase(nextPage)) {
				/** setup all lists before returning */
				setLists((SmsBatchForm) form, request);
			}
		} else if ("3".equalsIgnoreCase(request.getParameter("page"))) {
			nextPage = display(mapping, form, request, response);
		} else if ("display".equalsIgnoreCase(request.getParameter("page"))) {
			nextPage = display(mapping, form, request, response);

		}
		return mapping.findForward(nextPage);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String prevPage = "";
		if ("2".equalsIgnoreCase(request.getParameter("page"))) {
			prevPage = "step1forward";
		} else if ("3".equalsIgnoreCase(request.getParameter("page"))) {
			prevPage = step2(mapping, form, request, response);
		} else if ("search".equalsIgnoreCase(request.getParameter("page"))
				|| "select".equalsIgnoreCase(request.getParameter("page"))) {
			prevPage = step2(mapping, form, request, response);
		} else if ("display".equalsIgnoreCase(request.getParameter("page"))) {
			/*
			 * if (smsBatchForm.isSmsWasSend()){ // End of process : sms message
			 * was send // Clear all variables reset(smsBatchForm); prevPage =
			 * "step1forward"; } else{
			 */
			prevPage = step2(mapping, form, request, response);
		}

		return mapping.findForward(prevPage);
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SmsBatchForm smsBatchForm = (SmsBatchForm) form;
		String prevPage = "";

		if ("search".equalsIgnoreCase(request.getParameter("page"))
				|| "select".equalsIgnoreCase(request.getParameter("page"))) {
			/* Clear selection */
			smsBatchForm.setSelectedSearchList(new String[15]);
			smsBatchForm.setRegSelection(new String[15]);
			prevPage = step2(mapping, form, request, response);
		} else {
			// Clear all fields	
			smsBatchForm.setCanSendSms(false);
			smsBatchForm.setSmsWasSend(false);
			smsBatchForm.setRegCriteriaType("");
			smsBatchForm.setGeoCriteriaType("");
			smsBatchForm.setRegList(new ArrayList());
			smsBatchForm.setMessageCount(0);
			smsBatchForm.setControllCellNr("");
			smsBatchForm.setDataFileName("");
			smsBatchForm.setDeptCode(Short.parseShort("0"));
			smsBatchForm.setGeoSelection(new String[15]);
			smsBatchForm.setMessage("");
			smsBatchForm.setSearchCode("");
			smsBatchForm.setSearchDescription("");
			smsBatchForm.setSelectionString("");
			smsBatchForm.setRegistrationPeriod("");
			smsBatchForm.setSelectedItems("");
			smsBatchForm.setReasonGc27("2 : Learner Support");
			smsBatchForm.setFileContentType("");
			prevPage = checkUser(mapping, form, request, response);
		}

		return mapping.findForward(prevPage);
	}

	private static String rightPad(String stringToPad, String padder, int size) {
		if (padder.length() == 0) {
			return stringToPad;
		}
		String replaceStr = " ";
		String oldStr = " ";
		for (int i = 2; i < 10; i++) {
			oldStr = oldStr + " ";
			stringToPad = stringToPad.replaceAll(oldStr, replaceStr);
		}
		StringBuffer strb = new StringBuffer(stringToPad);

		int padLength;
		padLength = size - strb.length();

		for (int i = 0; i < padLength; i++) {
			strb.append(padder);
		}
		return strb.toString();
	}

	private static String leftPad(String stringToPad, String padder, int size) {
		if (padder.length() == 0) {
			return stringToPad;
		}
		String replaceStr = " ";
		String oldStr = " ";
		for (int i = 2; i < 10; i++) {
			oldStr = oldStr + " ";
			stringToPad = stringToPad.replaceAll(oldStr, replaceStr);
		}
		StringBuffer strb = new StringBuffer(stringToPad);

		int padLength;
		padLength = size - strb.length();

		for (int i = 0; i < padLength; i++) {
			strb.insert(0, padder);
		}
		return strb.toString();
	}

	private void setLists(SmsBatchForm smsForm, HttpServletRequest request)
			throws Exception {
		/** Set reason code list */
		SmsBatchDAO dao = new SmsBatchDAO();
		GeneralItem genItem = new GeneralItem();
		List tempReasonList = new ArrayList();
		List reasonList = new ArrayList();
		tempReasonList = dao.getGeneralCodesList(request,
				"27", 1);
		for (int i=0; i< tempReasonList.size();i++){
			genItem = getItem(((LabelValueBean)(tempReasonList.get(i))).getValue());
			if (genItem.getCode().equals("2") || genItem.getCode().equals("9999")) {
				reasonList.add(tempReasonList.get(i));
			}
		}
		request.setAttribute("reasonList", reasonList);
		/** Set registration period list */
		if ("M".equalsIgnoreCase(smsForm.getRegCriteriaType())) {
			List list = new ArrayList();
			list = dao.getGeneralCodesList(request, "29", 2);
			list.add(0, new LabelValueBean("Select a registration period",
					"Select a registration period"));
			request.setAttribute("regPeriodList", list);
		}
		/** Set countries list */
		if ("C".equalsIgnoreCase(smsForm.getGeoCriteriaType())) {
			request.setAttribute("geoList", dao.getCountries(request));
		}
		/** Set regions list */
		if ("R".equalsIgnoreCase(smsForm.getGeoCriteriaType())) {
			request.setAttribute("geoList", dao.getRegions(request));
		}
		/** Set mag districts list */
		if ("M".equalsIgnoreCase(smsForm.getGeoCriteriaType())) {
			request.setAttribute("geoList", dao.getDistricts(request));
		}
		if (smsForm.getRegList() == null) {
			smsForm.setRegList(new ArrayList());
		}
	}

	private boolean isSelectEmpty(String[] selection) {
		boolean result = true;

		for (int i = 0; i < selection.length; i++) {
			if (selection[i] != null && !"".equals(selection[i])) {
				return false;
			}
		}

		return result;
	}

	private void reset(SmsBatchForm smsForm) {
		smsForm = new SmsBatchForm();
	}

	private GeneralItem getItem(String inputStr) {
		GeneralItem item = new GeneralItem();
		int pos = 0;

		pos = inputStr.indexOf(":");
		item.setDesc(inputStr.substring(pos + 2, inputStr.length()));
		item.setCode(inputStr.substring(0, pos - 1));
		return item;
	}

	private List setupItemList(String inputStr, String type) throws Exception {
		List list = new ArrayList();
		int pos = 0;
		int len = 0;

		if ("M".equalsIgnoreCase(type)) {
			len = 7;
		} else if ("Q".equalsIgnoreCase(type)) {
			len = 5;
		}
		try {
			if (inputStr.length() == len) {
				/* One module/qualification only */
				list.add(inputStr);
				return list;
			} else {
				pos = inputStr.indexOf(",");
				while (pos != -1) {
					list.add(inputStr.substring(0, len));
					inputStr = inputStr.substring(pos + 1, inputStr.length());
					if (inputStr.length() == len) {
						list.add(inputStr);
					}
					pos = inputStr.indexOf(",");
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return list;
	}

	private int getCurrentYear() {
		int currentYear = 0;
		/* jan = 0, Feb=1 , Nov=10, Dec=11 etc */
		if (Calendar.getInstance().get(Calendar.MONTH) < 11) {
			currentYear = Calendar.getInstance().get(Calendar.YEAR);
		} else {
			/* Removed for test purposes +1 */
			currentYear = (Calendar.getInstance().get(Calendar.YEAR) + 1);
		}
		return currentYear;
	}

	private String removeDuplicates(String selectionList, String regCriteriaType)
			throws Exception {

		String finalList = "";
		List itemList = new ArrayList();

		itemList = setupItemList(selectionList, regCriteriaType);
		for (int i = 0; i < itemList.size(); i++) {
			String element = itemList.get(i).toString().toUpperCase();
			/* chk if already in final list */
			if (finalList.indexOf(element) == -1) {
				/* Add to final list */
				if (i == 0) {
					finalList = element;
				} else {
					finalList = finalList + "," + element;
				}
			}
		}

		return finalList;

	}

}

