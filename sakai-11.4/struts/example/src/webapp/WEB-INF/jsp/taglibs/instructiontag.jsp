<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar>
		<html:link action="/taglibs/index">Back</html:link>
		<html:link href="instructiontag.source" target="instructiontag">Source</html:link>
	</sakai:tool_bar>
	<sakai:heading>&lt;sakai:instruction&gt; Tag</sakai:heading>
	<sakai:instruction>This is an instruction: Eat all your food!</sakai:instruction>
	<p>And this a normal paragraph: And then some...</p>
</sakai:html>