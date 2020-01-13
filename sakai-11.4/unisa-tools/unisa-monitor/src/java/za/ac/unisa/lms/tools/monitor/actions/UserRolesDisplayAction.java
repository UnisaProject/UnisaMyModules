
package za.ac.unisa.lms.tools.monitor.actions;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.sakaiproject.authz.api.GroupProvider;
import org.sakaiproject.component.api.ComponentManager;

import za.ac.unisa.lms.tools.monitor.dao.DataRow;
import za.ac.unisa.lms.tools.monitor.forms.UserForm;

/**
 * MyEclipse Struts
 * Creation date: 08-18-2005
 *
 * XDoclet definition:
 * @struts:action parameter="action" validate="true"
 */
public class UserRolesDisplayAction extends DispatchAction {

	// --------------------------------------------------------- Instance Variables

	// --------------------------------------------------------- Methods

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) throws Exception {

		ActionMessages messages = new ActionMessages();

		UserForm userForm = (UserForm) form;
		if (userForm.getUserId() != null) {

			Vector<DataRow> results = new Vector<DataRow>();
			Vector<String> columns = new Vector<String>();
			DataRow row = new DataRow();
			columns.add("Realm");
			columns.add("Role");
			row.setColumns(columns);
			results.add(row);

			try {
				ComponentManager componentManager = (ComponentManager) org.sakaiproject.component.cover.ComponentManager.getInstance();
				GroupProvider provider = (GroupProvider) componentManager.get("org.sakaiproject.authz.api.GroupProvider");
				Map<?, ?> roleMap = provider.getGroupRolesForUser(userForm.getUserId());

				Iterator<?> ki = roleMap.keySet().iterator();

				while (ki.hasNext()) {
					String realm = (String) ki.next();
					String role = (String) roleMap.get(realm);
					Vector<String> values = new Vector<String>();
					values.add(realm);
					values.add(role);
					row = new DataRow();
					row.setValues(values);
					results.add(row);
				}

				request.setAttribute("results", results);
			} catch (Exception e) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("genericexception", e.getMessage()));
				e.printStackTrace();
			}
		}

		if (!messages.isEmpty()) {
			saveErrors(request, messages);
		}

		return mapping.findForward("input");


	}
}

