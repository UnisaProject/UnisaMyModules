package za.ac.unisa.lms.tools.proxy.tags;

import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.ParserException;

public class SakaiScriptTag extends ScriptTag {

	/**
	 * Eclipse Generated.
	 */
	private static final long serialVersionUID = -2204521976127649241L;
	
	private String base = "";

	public SakaiScriptTag(String base) {
		this.base = base;
	}

	public void doSemanticAction() throws ParserException {
		if (getAttribute("src") != null) {
			setAttribute("src", base+getAttribute("src"));
		}
	}
}