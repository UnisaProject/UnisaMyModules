package za.ac.unisa.lms.tools.monitor.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.sakaiproject.component.api.ComponentManager;

public class DisplaySpringComponentsAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			ComponentManager componentManager = (ComponentManager) org.sakaiproject.component.cover.ComponentManager.getInstance();
			Set<?> components = componentManager.getRegisteredInterfaces();
			
			request.setAttribute("components", components);
		} catch (Exception e) {
			throw new Exception("Unhandled Exception", e);
		}
		
		return mapping.findForward("display");
	}

}
