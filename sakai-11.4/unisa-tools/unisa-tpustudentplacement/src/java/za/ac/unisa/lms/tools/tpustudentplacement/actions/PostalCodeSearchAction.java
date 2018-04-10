package za.ac.unisa.lms.tools.tpustudentplacement.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import za.ac.unisa.lms.tools.tpustudentplacement.dao.ContactDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacementForm;

public class PostalCodeSearchAction extends LookupDispatchAction{
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
			// TODO Auto-generated method stub
			Map map = new HashMap();
			map.put("initial", "initial");	
			map.put("button.display", "display");
			map.put("button.back", "prevPage");		
			map.put("button.continue", "selectPostalCode");
			map.put("button.searchPostalCode","displayPostalCodeSearch");	
			return map;
		}
		
		public ActionForward initial(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;	
			
			studentPlacementForm.setPostalCalledFrom(studentPlacementForm.getCurrentPage());
			studentPlacementForm.setSearchType("");
			studentPlacementForm.setSearchSuburb("");
			studentPlacementForm.setSearchPostalCode("");
			studentPlacementForm.setSearchResult("");
			studentPlacementForm.setPostalBoxType("B");
			studentPlacementForm.setCurrentPage("searchPostalCode");
			return mapping.findForward("searchPostalCode");
		}
		
		public ActionForward prevPage(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;		
			
			studentPlacementForm.setCurrentPage(studentPlacementForm.getPostalCalledFrom());
			return mapping.findForward(studentPlacementForm.getPostalCalledFrom());
		}
		
		public ActionForward display(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			ActionMessages messages = new ActionMessages();
			ArrayList postalList = new ArrayList();
			try {
				// Do checks on required input
				if (studentPlacementForm.getPostalBoxType() == null
						|| "".equalsIgnoreCase(studentPlacementForm.getPostalBoxType())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage",
							"Select the Postal box Type"));
//					addErrors(request, messages);
//					studentPlacementForm.setCurrentPage("searchPostalCode");
//					return mapping.findForward("searchPostalCode");
				}
				if (studentPlacementForm.getSearchType() == null
						|| "".equalsIgnoreCase(studentPlacementForm.getSearchType())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage",
							"Select to search by postal code or suburb."));
//					addErrors(request, messages);
//					studentPlacementForm.setCurrentPage("searchPostalCode");
//					return mapping.findForward("searchPostalCode");
				}
				if ("postal".equalsIgnoreCase(studentPlacementForm.getSearchType())
						&& "".equalsIgnoreCase(studentPlacementForm.getSearchPostalCode())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage(
											"message.generalmessage",
											"You have selected to search by postal code. Please enter the postal code search criteria."));
//					addErrors(request, messages);
//					studentPlacementForm.setCurrentPage("searchPostalCode");
//					return mapping.findForward("searchPostalCode");
				}
				if ("suburb".equalsIgnoreCase(studentPlacementForm.getSearchType())
						&& "".equalsIgnoreCase(studentPlacementForm.getSearchSuburb())) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage(
											"message.generalmessage",
											"You have selected to search by suburb. Please enter the suburb search criteria."));
//					addErrors(request, messages);
//					studentPlacementForm.setCurrentPage("searchPostalCode");
//					return mapping.findForward("searchPostalCode");
				}
				
				if (!messages.isEmpty()) {
					addErrors(request,messages);
					studentPlacementForm.setCurrentPage("searchPostalCode");
					return mapping.findForward("searchPostalCode");			
				}

				ContactDAO dao = new ContactDAO();

				// Do the search on the database
				if ("postal".equalsIgnoreCase(studentPlacementForm.getSearchType())) {
					studentPlacementForm.setSearchSuburb("");
					postalList = dao.getPostalCodeList(studentPlacementForm.getSearchType(),
							studentPlacementForm.getSearchPostalCode(), studentPlacementForm.getPostalBoxType());
				} else {
					studentPlacementForm.setSearchPostalCode("");
					postalList = dao.getPostalCodeList(studentPlacementForm.getSearchType(),
							studentPlacementForm.getSearchSuburb(), studentPlacementForm.getPostalBoxType());
				}
				if (postalList.isEmpty()) {
					// no results returned give message
					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.generalmessage",
							"The search returned no results. Please try again."));
					addErrors(request, messages);
				} else {
					//Add postal code list to request
					request.setAttribute("postalList", postalList);
				}
			} catch (Exception e) {
				throw e;
			}
			studentPlacementForm.setCurrentPage("searchPostalCode");
			return mapping.findForward("searchPostalCode");
		}		
		
//		 Postal code selected
		public ActionForward selectPostalCode(
				ActionMapping mapping,
				ActionForm form,
				HttpServletRequest request,
				HttpServletResponse response) throws Exception {

			StudentPlacementForm studentPlacementForm = (StudentPlacementForm) form;
			ActionMessages messages = new ActionMessages();
			
			if (studentPlacementForm.getSearchResult() == null
					|| "".equalsIgnoreCase(studentPlacementForm.getSearchResult())) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"message.generalmessage",
						"Select a postal code/town combination from the list."));
				addErrors(request, messages);
				return display(mapping,form,request,response);
			}
			
			if (studentPlacementForm.getPostalCalledFrom().equalsIgnoreCase("editSupervisor")){
				studentPlacementForm.getSupervisor().setPostalCode(studentPlacementForm.getSearchResult()
						.substring(0, 4));
				studentPlacementForm.setCurrentPage("editSupervisor");
				return mapping.findForward("editSupervisor");
			}
			studentPlacementForm.getSchool().setPostalCode(studentPlacementForm.getSearchResult()
					.substring(0, 4));
			studentPlacementForm.setCurrentPage("editSchool");
			return mapping.findForward("editSchool");
		}		
		
}
