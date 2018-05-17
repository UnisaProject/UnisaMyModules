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
            
            // Unisa Changes:2018/04/20: Make Struts tools responsive on small devices for Sakai 11.x
            // styling applies to tables only - to fix text boxes not being responsive on struts tools
            // 2018/05/17: Morphues has added borders for 'table.listHier' in Sakai 11-remove these borders here
            out.write("<style> "+
	            		 "/*table and cells for displaying a flat or hierarchical list of tabular data*/ "+
	            		 "/*see an annoucement, assignment list*/ "+
	            		 "table.listHier { "+						// 2018/05/17: All this because of unisa-discussionforums
	            		 " border:none !important; /*keep ff from giving it a border*/ "+
	            		 "} "+
	            		 "table.listHier tr td { "+ 				// 2018/05/17: All this because of unisa-discussionforums
	            		 "	border-right: none !important; "+
        				 "} "+
	            		 "table.listHier tr:nth-child(2n+1) td { "+ // 2018/05/17: All this because of unisa-discussionforums
	            		 "	border-right: none !important; "+
	            		 "} "+
            			 "@media only screen and (max-width: 800px) { "+
            			 	 "table { display: inline-table !important; "+
            		  		          "table-layout: fixed; width: 100%; } "+ 
             		  		 "table th> div { " +					
					  		  				  "word-wrap: break-word; "+	  	   
					  		  				  "white-space: -o-pre-wrap; "+ 	   
					  		  				  "white-space: -moz-pre-wrap; "+ 
					  		  				  "white-space: -webkit-pre-wrap; "+
					  		  				  "white-space: -ms-pre-wrap; "+
					  		  				  "white-space: pre-wrap; } "+
					  		 "table th { "+						
					  		  			 "word-wrap: break-word; "+  	   
					  		  			 "white-space: -o-pre-wrap; "+   
					  		  			 "white-space: -moz-pre-wrap; "+ 
					  		  			 "white-space: -webkit-pre-wrap; "+ 
					  		  			 "white-space: -ms-pre-wrap; "+
					  		  			 "white-space: pre-wrap; } "+
					  		 "table td { "+					
										 "word-wrap: break-word; "+ 	   
										 "white-space: -o-pre-wrap; "+	   
										 "white-space: -moz-pre-wrap; "+
										 "white-space: -webkit-pre-wrap; "+ 
										 "white-space: -ms-pre-wrap; "+
										 "white-space: pre-wrap; } "+            			 	 
            		  		 "table.listHier th> div { " +					
            		  		  						   "word-wrap: break-word; "+	  	   
            		  		  						   "white-space: -o-pre-wrap; "+ 	   
            		  		  						   "white-space: -moz-pre-wrap; "+ 
            		  		  						   "white-space: -webkit-pre-wrap; "+
            		  		  						   "white-space: -ms-pre-wrap; "+
            		  		  						   "white-space: pre-wrap; } "+
            		  		 "table.listHier th { "+						
            		  		  					  "word-wrap: break-word; "+  	   
            		  		  					  "white-space: -o-pre-wrap; "+   
            		  		  					  "white-space: -moz-pre-wrap; "+ 
            		  		  					  "white-space: -webkit-pre-wrap; "+ 
            		  		  					  "white-space: -ms-pre-wrap; "+
            		  		  					  "white-space: pre-wrap; } "+
            		  		 "table.listHier td { "+					
            		  		  					  "word-wrap: break-word; "+ 	   
            		  		  					  "white-space: -o-pre-wrap; "+	   
            		  		  					  "white-space: -moz-pre-wrap; "+
            		  		  					  "white-space: -webkit-pre-wrap; "+ 
            		  		  					  "white-space: -ms-pre-wrap; "+
            		  		  					  "white-space: pre-wrap; } "+
            		  		"}</style>");
            // End Unisa Changes

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
