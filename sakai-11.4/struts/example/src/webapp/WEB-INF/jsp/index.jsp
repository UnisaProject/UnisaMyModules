<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<sakai:html>

	<h3>Main Struts Example Index</h3>
	
	<ul>
		<li><html:link action="/taglibs/index">Tag Library Examples</html:link></li>
		<li><html:link action="/components/componentmanager">The Sakai Components Examples</html:link></li>
	</ul>
	
</sakai:html>
