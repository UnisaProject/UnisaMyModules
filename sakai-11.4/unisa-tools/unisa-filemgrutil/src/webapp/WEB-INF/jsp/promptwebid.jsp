<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.filemgrutil.ApplicationResources"/>

<sakai:html>
	<html:form action="/filemanager.do">
		<sakai:messages/>
		<sakai:messages message="true"/>
		
		<sakai:heading>
   			<fmt:message key ="filemanager.webinputprompt.instruction"/><br>
		</sakai:heading>
		<sakai:group_table>
			<tr>
				<td><fmt:message key = "filemanagerutil.label.webId"></fmt:message></td>
				<td><html:text property="webId" size="10" maxlength="9"></html:text></td>
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
				<fmt:message key="button.submit"/>
			</html:submit>
			<html:submit property="action">
				<fmt:message key="button.back"/>
			</html:submit>				
		</sakai:actions>
	</html:form>
</sakai:html>
</html>