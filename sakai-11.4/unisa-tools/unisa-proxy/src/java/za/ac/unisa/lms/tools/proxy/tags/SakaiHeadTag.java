package za.ac.unisa.lms.tools.proxy.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Parser;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class SakaiHeadTag extends HeadTag {

	/**
	 * Eclipse Generated.
	 */
	private static final long serialVersionUID = -7670524224691620252L;
	
	private String inputHtml = "";
	private String nocache = 
		"<meta http-equiv=\"cache-control\" content=\"no-cache\">"+
		"<meta http-equiv=\"pragma\" content=\"no-cache\">";
	
	public SakaiHeadTag(String html) {
		inputHtml = html;
	}

	private NodeList createNewHead() throws ParserException {
		Parser parser = new Parser();
		parser.setInputHTML(nocache+inputHtml);
		return parser.parse(null);
	}
	
	public void doSemanticAction() throws ParserException {
		
		Log log = LogFactory.getLog(this.getClass());
		
		log.debug("doSemanticAction()");
		
		NodeList nl = getChildren();
		if (nl == null) {
			nl = new NodeList();
		}
	
		for (int k=0; k<nl.size(); k++) {
			String tag = nl.elementAt(k).getText().toLowerCase();
			if (tag.startsWith("link")) {
				nl.remove(k);
			} else if (tag.startsWith("style")) {
				nl.remove(k);
			}
		}
		nl.add(createNewHead());
		
		setChildren(nl);
	}
}