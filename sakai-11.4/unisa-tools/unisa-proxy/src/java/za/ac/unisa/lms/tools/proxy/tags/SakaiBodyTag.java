package za.ac.unisa.lms.tools.proxy.tags;

import org.htmlparser.Attribute;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.util.ParserException;

public class SakaiBodyTag extends BodyTag {

	/**
	 * Eclipse Generated.
	 */
	private static final long serialVersionUID = -7670524224691620252L;
	private String onload = "";
	
	public SakaiBodyTag(String onload) {
		this.onload = onload;
	}

	public void doSemanticAction() throws ParserException {
		Attribute attrib = new Attribute();
		attrib.setName("onload=\"");
		attrib.setValue(onload+"\"");
		setAttribute(attrib);
	}
}