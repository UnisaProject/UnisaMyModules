<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
<body>
<logic:match name="activeDirectorySSLForm" value="true" property="SSLOkay">
	<p>NAGIOS_TEST_SUCCEEDED</p>
</logic:match>
<logic:match name="activeDirectorySSLForm" value="false" property="SSLOkay">
	<p>NAGIOS_TEST_FAILED</p>
</logic:match>
</body>
</html>

