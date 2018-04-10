package za.ac.unisa.lms.tools.finalmarkconcession.actions;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

public abstract class NameLookupDispatchAction extends LookupDispatchAction {
	
	protected ActionMapping originalMapping;
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		originalMapping = mapping;
		ActionMapping am = mapping;
		for (Iterator iter = request.getParameterMap().keySet().iterator(); iter
		.hasNext();) {
			String name = (String) iter.next();
			if (name.startsWith("action.")) {
				String p = name.substring(7);
				//mapping.setParameter(p);
				am = new ActionMapping();
				am.setParameter(p);
				am.setAttribute(mapping.getAttribute());
				am.setForward(mapping.getForward());
				am.setInclude(mapping.getInclude());
				am.setModuleConfig(mapping.getModuleConfig());
				am.setMultipartClass(mapping.getMultipartClass());
				am.setName(mapping.getName());
				am.setPath(mapping.getPath());
				am.setPrefix(mapping.getPrefix());
				am.setRoles(mapping.getRoles());
				am.setScope(mapping.getScope());
				am.setSuffix(mapping.getSuffix());
				am.setType(mapping.getType());
				am.setUnknown(mapping.getUnknown());
				am.setValidate(mapping.getValidate());
				break;
			}
		}
		
		return super.execute(am, form, request, response);
	}
	
}
