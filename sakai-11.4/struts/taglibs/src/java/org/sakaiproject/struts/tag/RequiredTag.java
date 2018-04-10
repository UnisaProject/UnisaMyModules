package org.sakaiproject.struts.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequiredTag extends BodyTagSupport {

    /**
     * Eclipse Generated.
     */
    private static final long serialVersionUID = 4039929612190738638L;

    /** Our log (commons). */
    private static Log M_log = LogFactory.getLog(RequiredTag.class);
    
    public int doStartTag() throws JspException {
        JspWriter out = this.pageContext.getOut();

        try {
            out.write("<span class=\"chefAlert\">*</span>");
        } catch (IOException e) {
            M_log.error("Exception writing starttag.", e);
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
