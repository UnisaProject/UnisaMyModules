<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="messagestag.source" target="messagestag">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:messages&gt; Tag</sakai:heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
</sakai:html>