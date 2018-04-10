package za.ac.unisa.lms.tools.proxy.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.ParserException;

public class SakaiInputTag extends InputTag {

	private static final long serialVersionUID = -7670524224691620252L;
	private static final Log log = LogFactory.getLog(SakaiInputTag.class);
	
	private String contextpath = "";
	private String base = "";

	public SakaiInputTag(String base, String contextpath) {
		this.base = base;
		this.contextpath = contextpath;
	}
	
	private void doOnClick() throws StringIndexOutOfBoundsException {
		String onClick = getAttribute("onClick");
		if (onClick == null) return;
		if (onClick.toLowerCase().startsWith("location")) {
			onClick = onClick.substring(9, onClick.length());
			if ((onClick.charAt(0) == '\'') || (onClick.charAt(0) == '\"')) {
				onClick = onClick.substring(1, onClick.length()-1);
			}
			if (onClick.startsWith("/")) {
				onClick = contextpath+onClick;
			} else if (onClick.indexOf("://") != -1) {
				if (onClick.indexOf(base) != -1) {
					onClick = onClick.substring(onClick.indexOf("://")+3, onClick.length());
					onClick = onClick.substring(onClick.indexOf("/")+1, onClick.length());
					onClick = contextpath+"/"+onClick;
				}
			}
			setAttribute("onClick", "location=\'"+onClick+"\'");
		}
	}
	
	public void doSemanticAction() throws ParserException {
		try {
			doOnClick();
		} catch (StringIndexOutOfBoundsException e) {
			log.error(e.getMessage());
			throw new ParserException(e);
		}
	}
}