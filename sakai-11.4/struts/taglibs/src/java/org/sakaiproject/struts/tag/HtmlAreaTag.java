package org.sakaiproject.struts.tag;

import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.html.TextareaTag;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.content.api.ContentHostingService;



@SuppressWarnings("serial")
public class HtmlAreaTag extends TextareaTag {

	protected String renderTextareaElement() throws JspException {
		StringBuffer results = new StringBuffer("\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
		ContentHostingService   contentHostingService = (ContentHostingService)	ComponentManager.get(ContentHostingService.class.getName());
		String collectionId = contentHostingService.getSiteCollection(ToolManager.getCurrentPlacement().getContext());
//		String connector = "/sakai-fck-connector/web/editor/filemanager/browser/default/connectors/jsp/connector";
		if (getStyleClass() == null) setStyleClass("block");
		setId(getProperty());
		if (getRows() == null) setRows("30");
		if (getCols() == null) setCols("65");
		String textAreaGetId = getId();
		results.append("<tr><td>\n");
		results.append(super.renderTextareaElement());
		results.append("<script type=\"text/javascript\" defer=\"1\">sakai.editor.launch('"+textAreaGetId+"');</script>");	
		results.append("</td></tr>\n");
		results.append("</table>\n");
		return results.toString();
	}

}
