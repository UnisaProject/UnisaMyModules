package org.sakaiproject.struts.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.tool.api.Tool;

public class HtmlTag extends org.apache.struts.taglib.html.HtmlTag {

    /**
     * Eclipse Generated.
     */
    private static final long serialVersionUID = 4039929612190738638L;
    
    /** Our log (commons). */
    private static Log M_log = LogFactory.getLog(HtmlTag.class);
    
    public HtmlTag() {
        super();
    }

    public int doEndTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            out.write("</div>");
            out.write("</body>");
            out.write("</html>");
        } catch (IOException e) {
            M_log.error("Exception writing endtag.", e);
        }
        return EVAL_PAGE;
    }

    public int doStartTag() throws JspException {
        JspWriter out = this.pageContext.getOut();
        String headInclude = (String) this.pageContext.getRequest().getAttribute("sakai.html.head");
        String bodyonload = (String) this.pageContext.getRequest().getAttribute("sakai.html.body.onload");
        try {
            out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
            out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n");
            out.write("<head>\n");
            
            if (headInclude != null) {
                out.write(headInclude);
            }

            out.write("</head>\n");

            out.write("<body");

            if (bodyonload != null && bodyonload.length() > 0)
            {
                out.write(" onload=\"");
                out.write(bodyonload);
                out.write("\"");
            }
            out.write(">\n");
            
            out.write("<div class=\"portletBody\">");
            
            pageContext.getRequest().setAttribute(Tool.NATIVE_URL, null);
            
        } catch (IOException e) {
            M_log.error("Exception writing starttag.", e);
        }
        return EVAL_BODY_INCLUDE;
    }
    
}
