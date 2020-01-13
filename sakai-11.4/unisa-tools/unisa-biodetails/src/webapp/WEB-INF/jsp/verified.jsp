<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<html:html>
<body>

	<h3><fmt:message key="page.heading.emailadress"/></h3>
    <logic:equal name="verified" value="0">
    	<p><fmt:message key="page.notverified"/></p>
    </logic:equal>
    <logic:equal name="verified" value="1">
    	<p><fmt:message key="page.verified"/></p>
    </logic:equal>
</body>
</html:html>
