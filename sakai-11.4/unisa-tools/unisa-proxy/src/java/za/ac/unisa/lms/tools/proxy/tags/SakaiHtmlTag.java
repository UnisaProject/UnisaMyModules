package za.ac.unisa.lms.tools.proxy.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Node;
import org.htmlparser.NodeFactory;
import org.htmlparser.Parser;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.Html;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class SakaiHtmlTag extends Html {
	
	/**
	 * Eclipse Generated.
	 */
	private static final long serialVersionUID = -4034221952884715817L;
	private NodeFactory factory = null;

	public SakaiHtmlTag(NodeFactory factory) {
		this.factory = factory;
	}
	
	public void doSemanticAction() throws ParserException {
		
		Log log = LogFactory.getLog(this.getClass());
		
		log.debug("doSemanticAction()");
		
		NodeList children = getChildren();
		boolean foundHead = false;
		
		for (int k=0; k<children.size(); k++) {
			Node node = children.elementAt(k);
			if (node instanceof HeadTag) {
				HeadTag tag = (HeadTag) node;
				if (tag.isEmptyXmlTag()) {
					children.remove(k);
				} else {
					foundHead = true;
				}
			}
		}
		
		if (!foundHead) {
			Parser parser = new Parser();
			parser.setNodeFactory(factory);
			parser.setInputHTML("<head></head>");
			Node head = parser.parse(null).elementAt(0);
			children.prepend(head);
		} 
	}
}
