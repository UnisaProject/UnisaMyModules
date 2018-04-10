package org.sakaiproject.struts.tag;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

/**
 * Custom tag that iterates the elements of a message collection.
 * It defaults to retrieving the messages from <code>Globals.ERROR_KEY</code>,
 * but if the message attribute is set to true then the messages will be
 * retrieved from <code>Globals.MESSAGE_KEY</code>. This is an alternative
 * to the default <code>ErrorsTag</code>.
 *
 * @version $Rev: 164530 $ $Date: 2005-04-25 04:11:07 +0100 (Mon, 25 Apr 2005) $
 * @since Struts 1.1
 */
@SuppressWarnings("serial")
public class MessagesTag extends TagSupport {

    /**
     * The message resources for this package.
     */
    protected static MessageResources messageResources =
       MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

    /**
     * Iterator of the elements of this error collection, while we are actually
     * running.
     */
    protected Iterator iterator = null;

    /**
     * The servlet context attribute key for our resources.
     */
    protected String bundle = null;

    /**
     * The session attribute key for our locale.
     */
    protected String locale = Globals.LOCALE_KEY;

    /**
     * The request attribute key for our error messages (if any).
     */
    protected String name = Globals.ERROR_KEY;

    /**
     * The name of the property for which error messages should be returned,
     * or <code>null</code> to return all errors.
     */
    protected String property = null;

    /**
     * If this is set to 'true', then the <code>Globals.MESSAGE_KEY</code> will
     * be used to retrieve the messages from scope.
     */
    protected String message = null;

    public String getBundle() {
        return (this.bundle);
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getLocale() {
        return (this.locale);
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return (this.property);
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMessage() {
        return (this.message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Construct an iterator for the specified collection, and begin
     * looping through the body once per element.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

        // Were any messages specified?
        ActionMessages messages = null;

        // Get an output stream
        JspWriter out = this.pageContext.getOut();

        // Make a local copy of the name attribute that we can modify.
        String name = this.name;

        if (message != null && "true".equalsIgnoreCase(message)) {
            name = Globals.MESSAGE_KEY;
        }

        try {
            messages = TagUtils.getInstance().getActionMessages(pageContext, name);

        } catch (JspException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        // Acquire the collection we are going to iterate over
        this.iterator = (property == null) ? messages.get() : messages.get(property);

        // Store the first value and evaluate, or skip the body if none
        while (this.iterator.hasNext()) {
        
	        ActionMessage report = (ActionMessage) this.iterator.next();
	        String msg = null;
            msg = TagUtils.getInstance().message(
	                     pageContext,
	                     bundle,
	                     locale,
	                     report.getKey(),
	                     report.getValues());
            

            String style = (name == Globals.ERROR_KEY)? style = "alertMessage": "success";
            
            try {
            	out.println("<div class=\""+style+"\">"+msg+"</div>");
            } catch (IOException e) {
            	throw new JspException(e);
            }
            
        }

        return SKIP_BODY;
    }


    /**
     * Release all allocated resources.
     */
    public void release() {
       super.release();
       iterator = null;
       bundle = null;
       locale = Globals.LOCALE_KEY;
       name = Globals.ERROR_KEY;
       property = null;
       message = null;
    }

}
