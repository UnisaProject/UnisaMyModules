package za.ac.unisa.lms.tools.proxy.tags;

import org.htmlparser.Attribute;
import org.htmlparser.tags.FrameTag;
import org.htmlparser.util.ParserException;

public class SakaiFrameTag extends FrameTag {

	/**
	 * Eclipse generated.
	 */
	private static final long serialVersionUID = -8503915025944287361L;

	public void doSemanticAction() throws ParserException {
		Attribute attrib = getAttributeEx("src");
		attrib.setValue(getFrameLocation());
		removeAttribute("src");
		setAttributeEx(attrib);
	}
}