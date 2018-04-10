<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="htmltag.source" target="htmltagsource">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:html&gt; Tag</sakai:heading>
	<p>The &lt;sakai:html&gt; tag should be the root for any jsp page running
	in the Sakai framework. It can be seen as the replacement of the 
	&lt;html&gt;&lt;body&gt; tags in Html, but will also produce certain
	style sheet elements and javascripting elements for your jsp to function
	correctly within the Sakai portal.
	</p>
</sakai:html>