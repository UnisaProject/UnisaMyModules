package za.ac.unisa.lms.tools.proxy.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;

public class SakaiLinkTag extends LinkTag {

	/**
	 * Eclipse Generated.
	 */
	private static final long serialVersionUID = -7670524224691620252L;
	private static final Log log = LogFactory.getLog(SakaiLinkTag.class);
	
	private String contextPath = "";
	private String permParm = "";
	private String base = "";

	public SakaiLinkTag(String base, String contextPath, String permParm) {
                this.base = base;
		this.permParm = permParm;
		this.contextPath = contextPath;
	}

	private String doLinkRewrite() throws StringIndexOutOfBoundsException {
	
         	String link = getAttribute("href");
 		if (link == null) return "";
		if (link.startsWith("javascript")) {
			//Do nothing, link does not get rewritten.
		} else if (link.startsWith("/")) {
			link = contextPath+link;
		} else if (link.indexOf("://") != -1) {
			if (link.indexOf(base) != -1) {
				link = link.substring(link.indexOf("://")+3, link.length());
				link = link.substring(link.indexOf("/")+1, link.length());
				link = contextPath+"/"+link;
		               if(link.contains("/portal")){
                               setAttribute("target", "_blank");
                             }	
                             } else {
                        	setAttribute("target", "_blank");
			}
		}
		if ((permParm != null) && (!permParm.equals("")) && (!link.startsWith("javascript"))) {
			if (link.indexOf("?") == -1) {
				link += "?"+permParm;
			} else {
				link += "&"+permParm;
			}
		}
    	return link;
	}
	
	public void doSemanticAction() throws ParserException {
		try {
			removeAttribute("target");
			setLink(doLinkRewrite());
		} catch (StringIndexOutOfBoundsException e) {
			log.error(e.getMessage());
			throw new ParserException(e);
		}
	}
}
