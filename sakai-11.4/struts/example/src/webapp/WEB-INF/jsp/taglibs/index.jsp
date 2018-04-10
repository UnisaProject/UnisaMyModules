<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>
	<sakai:tool_bar><html:link action="/index">Back</html:link></sakai:tool_bar>
	<sakai:heading>Tag Library Index</sakai:heading>
	<ul>
		<li><html:link action="/taglibs/htmltag">sakai:html</html:link></li>
		<li><html:link action="/taglibs/headingtag">sakai:heading</html:link></li>

		<li><html:link action="/taglibs/groupheadingtag">sakai:group_heading</html:link></li>

		<li><html:link action="/taglibs/grouptabletag">sakai:group_table</html:link></li>

		<li><html:link action="/taglibs/toolbartag">sakai:tool_bar</html:link></li>
		<li><html:link action="/taglibs/instructiontag">sakai:instruction</html:link></li>
		<li><html:link action="/taglibs/requiredtag">sakai:required</html:link></li>

		<li><html:link action="/taglibs/flatlisttag">sakai:flat_list</html:link></li>
		<li><html:link action="/taglibs/messagestag">sakai:messages</html:link></li>
		<li><html:link action="/taglibs/actionstag">sakai:actions</html:link></li>

		<li><html:link action="/taglibs/htmlareatag">sakai:html_area</html:link></li>
		<li><html:link action="/taglibs/newhtmlareatag">sakai:new_html_area</html:link></li>
	</ul>
</sakai:html>