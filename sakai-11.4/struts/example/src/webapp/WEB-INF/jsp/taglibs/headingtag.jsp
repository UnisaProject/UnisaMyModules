<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="headingtag.source" target="headingtagsource">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:heading&gt; Tag</sakai:heading>
	<p>The &lt;sakai:heading&gt; tag should be used to represent the title for any page / jsp for a tool.
	</p>

	<sakai:heading>This is another heading</sakai:heading>

	<p>See how similar the above heading is to the top one? Look at the source...</p>
</sakai:html>