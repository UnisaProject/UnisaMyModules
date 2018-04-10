package za.ac.unisa.lms.tools.proxy.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.tags.FormTag;
import org.htmlparser.util.ParserException;

public class SakaiFormTag extends FormTag {

	/**
	 * Eclipse Generated.
	 */
	private static final long serialVersionUID = 9053472268824088309L;
	private static final Log log = LogFactory.getLog(SakaiFormTag.class);
	
	private String contextPath = "";
	private String base = "";
	
	public SakaiFormTag(String contextPath, String base) {
		this.base = base;
		this.contextPath = contextPath;
	}

	private String buildAction() {
		String action = getAttribute("action");
		
		if ((action == null) || (action.equals(""))) {
			action = "";
		} else {
    		if (action.startsWith(base)) {
    			action = action.substring(
    					base.length(), 
    					action.length());
        		action = contextPath + action;
    		} else if (action.startsWith("/")) {
    			action = contextPath+action;
    		}
		}
		return action;
	}

	public void doSemanticAction() throws ParserException {
		try {
			if (buildAction().equals("")) {
				removeAttribute("action");
			} else {
				setAttribute("action", buildAction());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ParserException(e);
		}
	}
}