package za.ac.unisa.lms.tools.proxy.tags;

import org.htmlparser.Attribute;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.ParserException;

public class SakaiImageTag extends ImageTag {

	/**
	 * Eclipse Generated.
	 */
	private static final long serialVersionUID = 5815506805479573923L;
	
	private String contextPath = "";
	private String base = "";
	
	public SakaiImageTag(String base, String path) {
		super();
		this.base = base;
		contextPath = path;
	}

	public SakaiImageTag() {
		super();
	}
	
	private String doURLRewrite(String url) throws StringIndexOutOfBoundsException {
		if (url.startsWith("/")) {
			url = contextPath+url;
		} else if (url.indexOf("://") != -1) {
			if (url.indexOf(base) != -1) {
				url = url.substring(url.indexOf("://")+3, url.length());
				url = url.substring(url.indexOf("/")+1, url.length());
				url = contextPath+"/"+url;
			}
		}
		return url;
	}

	public void doSemanticAction() throws ParserException {
		getImageURL();
		String image = doURLRewrite(getImageURL());
		removeAttribute("src");
		Attribute attrib = new Attribute();
		attrib.setName("src=\"");
		attrib.setValue(image+"\"");
		setAttribute(attrib);
	}
}