<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>
<head>
	<script type="text/javascript">
	
		function doExit(){
			window.top.location.href = "http://www.unisa.ac.za/Default.asp?Cmd=ViewContent&ContentID=24624 ";
		}
	</script>
</head>


<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="uploaddone"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="md.docs.heading"/></sakai:heading>

		<sakai:instruction>
		</sakai:instruction>

			<p><strong>
				<fmt:message key="md.upload.info1"/></br>
			</strong>
		</p>

		<sakai:actions>
			<!--<html:submit property="action"><fmt:message key="button.back"/></html:submit>-->
			<html:submit property="exit" value="Home" onclick="doExit();" />
		</sakai:actions>
		
	</html:form>
</sakai:html>