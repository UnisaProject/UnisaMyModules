package org.sakaiproject.struts.tag;

import javax.servlet.jsp.JspException;
import org.apache.struts.taglib.html.TextareaTag;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.tool.cover.ToolManager;


@SuppressWarnings("serial")
public class NewHtmlAreaTag extends TextareaTag {

	protected String renderTextareaElement() throws JspException {
		StringBuffer results = new StringBuffer("\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
		ContentHostingService   contentHostingService = (ContentHostingService)	ComponentManager.get(ContentHostingService.class.getName());
		String collectionId = contentHostingService.getSiteCollection(ToolManager.getCurrentPlacement().getContext());
		String connector = "/sakai-fck-connector/web/editor/filemanager/browser/default/connectors/jsp/connector";
		if (getStyleClass() == null) setStyleClass("block");
		setId(getProperty());
		if (getRows() == null) setRows("30");
		if (getCols() == null) setCols("65");
		
/*		results.append("<tr><td>\n");
		results.append(super.renderTextareaElement());
*/
		String textAreaGetId = getId();
		results.append("<tr><td>\n");
		results.append(super.renderTextareaElement());
		results.append("<script type=\"text/javascript\" defer=\"1\">sakai.editor.launch('"+textAreaGetId+"');</script>");	
		
/*		String editor = ServerConfigurationService.getString("struts.editor");
		if (editor.equals("FCKeditor")){
			
			results.append("\n<script type=\"text/javascript\" src=\"/library/editor/FCKeditor/fckeditor.js\"></script>\n");
			results.append("\n<script type=\"text/javascript\" language=\"JavaScript\">\n");
			results.append("function chef_setupformattedtextarea(textarea_id)\n");
			results.append("{\n");
			results.append("        var oFCKeditor = new FCKeditor(textarea_id);\n");
			results.append("        var courseId =\""+collectionId+"\"\n\n");
			results.append("        oFCKeditor.BasePath = \"/library/editor/FCKeditor/\";\n");
			results.append("\n\toFCKeditor.Config['ImageBrowserURL'] = oFCKeditor.BasePath + " +
					"\"editor/filemanager/browser/default/browser.html?Connector=" + connector + "&Type=Image&CurrentFolder=\" + courseId;");
			results.append("\n\toFCKeditor.Config['LinkBrowserURL'] = oFCKeditor.BasePath + " +
					"\"editor/filemanager/browser/default/browser.html?Connector=" + connector + "&Type=Link&CurrentFolder=\" + courseId;");
			results.append("\n\toFCKeditor.Config['FlashBrowserURL'] = oFCKeditor.BasePath + " +
					"\"editor/filemanager/browser/default/browser.html?Connector=" + connector + "&Type=Flash&CurrentFolder=\" + courseId;");
			results.append("\n\toFCKeditor.Config['ImageUploadURL'] = oFCKeditor.BasePath + " +
					"\"" + connector + "?Type=Image&Command=QuickUpload&Type=Image&CurrentFolder=\" + courseId;");
			results.append("\n\toFCKeditor.Config['FlashUploadURL'] = oFCKeditor.BasePath + " +
					"\"" + connector + "?Type=Flash&Command=QuickUpload&Type=Flash&CurrentFolder=\" + courseId;");
			results.append("\n\toFCKeditor.Config['LinkUploadURL'] = oFCKeditor.BasePath + " +
					"\"" + connector + "?Type=File&Command=QuickUpload&Type=Link&CurrentFolder=\" + courseId;\n");

			results.append("oFCKeditor.Width  = \"500X\" ;\n");
			results.append("oFCKeditor.Height = \"400\" ;\n");
			results.append("\n\toFCKeditor.Config['CustomConfigurationsPath'] = \"/library/editor/FCKeditor/config.js\";\n");
			results.append("oFCKeditor.ReplaceTextarea() ;}\n");
			results.append("</script>\n");
			results.append("<script type=\"text/javascript\" defer=\"1\">chef_setupformattedtextarea('"+getProperty()+"');</script>\n");

		} else if(editor.equals("ckeditor")){
			results.append("\n<script type=\"text/javascript\" src=\"/library/editor/ckeditor/ckeditor.js\"></script>\n");
			// The next java script if to look at the compatibility of the browsers
			results.append("\n<script type=\"text/javascript\" src=\"/library/editor/ckeditor/_samples/sample.js\"></script>\n");
			results.append("\n<script type=\"text/javascript\" language=\"JavaScript\">\n");
			results.append("\n");
			results.append("        var courseId =\""+collectionId+"\"\n\n");
			results.append("	    var BasePath = \"/library/editor/ckeditor/\";\n");
			results.append("\n        //<![CDATA[ ");
			results.append("\n        CKEDITOR.replace( '"+getProperty()+"',");
			results.append("\n      {");
			// image Browse server button
            results.append("\n          filebrowserImageBrowseUrl : BasePath + " +
					"\"js/filemanager/browser/default/browser.html?Connector=" + connector + "&Type=Image&CurrentFolder=\" + courseId,");
            //Url link Browse server button
            results.append("\n          filebrowserBrowseUrl :BasePath + " +
					"\"js/filemanager/browser/default/browser.html?Connector=" + connector + "&Type=Link&CurrentFolder=\" + courseId,");
            //Flash Browse server button
            results.append("\n          filebrowserFlashBrowseUrl : BasePath + " +
					"\"js/filemanager/browser/default/browser.html?Connector=" + connector + "&Type=Flash&CurrentFolder=\" + courseId,");
            //Quick upload file tab
            results.append("\n          filebrowserUploadUrl  : \"" + connector + "?Type=File&Command=QuickUpload&Type=Link&CurrentFolder=\" + courseId,\n");
            //Quick Upload Flash tab
            results.append("\n          filebrowserFlashUploadUrl : \"" + connector + "?Type=Flash&Command=QuickUpload&Type=Flash&CurrentFolder=\" + courseId,");
            //Quick Upload Imgae Tab
            results.append("\n          filebrowserImageUploadUrl : \"" + connector + "?Type=Image&Command=QuickUpload&Type=Image&CurrentFolder=\" + courseId,");
          	results.append("\n           height:\"500\",");
          	results.append("\n           width:\"400\"");
          	results.append("\n        });");
            results.append("          //]]>");
			
	 		results.append("</script>\n");
			
			
		}
*/
		results.append("</td></tr>\n");
		results.append("</table>\n");
		return results.toString();
	}

}
